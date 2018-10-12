package com.example.miaosha.service;

import com.example.miaosha.dao.MiaoshaUserDao;
import com.example.miaosha.domain.MiaoshaUser;
import com.example.miaosha.exception.GlobalException;
import com.example.miaosha.redis.MiaoshaUserKey;
import com.example.miaosha.redis.RedisService;
import com.example.miaosha.result.CodeMsg;
import com.example.miaosha.util.MD5Util;
import com.example.miaosha.util.UUIDUtil;
import com.example.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class MiaoshaUserService {

    public static final String COOKIE_NAME_TOKEN = "token";

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

    @Autowired
    RedisService redisService;

    /**
     * 对象级缓存  查询很简单 但是增量操作的时候  也需要同步跟新redis 里面的数据
     * @param id
     * @return
     */
     public MiaoshaUser getById(Long id){
         //取缓存
         MiaoshaUser user = redisService.get(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);
         if(user != null){
             return user;
         }

         user = miaoshaUserDao.getById(id);
         if (user != null){
             redisService.set(MiaoshaUserKey.getById, ""+id, MiaoshaUser.class);
         }
         return user;
     }

     //对象级缓存 再增量操作的时候 是需要同步更新 redis 的
     public boolean updatePassword(String token, long id, String newPassword){
         //取user对象
         MiaoshaUser user = getById(id);
         if (user == null){
             throw  new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
         }
         //更新数据库
         MiaoshaUser toBeUpdate = new MiaoshaUser();
         toBeUpdate.setId(id);
         toBeUpdate.setPassword(MD5Util.formPassToDBPass(newPassword, user.getSalt()));
         miaoshaUserDao.update(toBeUpdate);
         //处理缓存
         redisService.del(MiaoshaUserKey.getById, ""+id);
         user.setPassword(toBeUpdate.getPassword());
         redisService.set(MiaoshaUserKey.token, token, user);
         return true;
     }

     public MiaoshaUser getByToken(String token, HttpServletResponse response){
         if (StringUtils.isEmpty(token)){
             return  null;
         }
         //根据token获取对象  这里就是对象级缓存
         MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
         addCookie(user, response, token);  //通过重新写入的方式来，修改cookie中的过期时间
         return user;
     }

    public String login(HttpServletResponse response, LoginVo loginVo) {
         if(loginVo == null){
             throw new GlobalException(CodeMsg.SERVER_ERROE);
         }
         String mobile = loginVo.getMobile();
         String formPass = loginVo.getPassword();
         //判断手机号是否存在
        MiaoshaUser user = miaoshaUserDao.getById(Long.parseLong(mobile));
        if (user == null){
           throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!dbPass.equals(calcPass)){
            throw new GlobalException(CodeMsg.PASSWORD_ERROR);
        }
        //登录成功后要生成 cookie
        String token = UUIDUtil.uuid();
        addCookie(user, response, token);
        return token;
    }

    private void addCookie(MiaoshaUser user, HttpServletResponse response, String token){
        //我们需要标识下 该token属于哪个用户 ，所以需要把对应关系 保存到redis中
        redisService.set(MiaoshaUserKey.token,token, user);
        //生成cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/"); //需要将cookie保存到根目录，如果不设置，当跳转的时候不会携带cookie
        response.addCookie(cookie);
    }
}
