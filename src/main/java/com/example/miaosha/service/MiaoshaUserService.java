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

     public MiaoshaUser getById(Long id){
         return miaoshaUserDao.getById(id);
     }

     public MiaoshaUser getByToken(String token){
         if (StringUtils.isEmpty(token)){
             return  null;
         }
         return redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
     }

    public boolean login(HttpServletResponse response, LoginVo loginVo) {
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
        //我们需要标识下 该token属于哪个用户 ，所以需要把对应关系 保存到redis中
        redisService.set(MiaoshaUserKey.token,token, user);
        //生成cookie
        Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/"); //需要将cookie保存到根目录，如果不设置，当跳转的时候不会携带cookie
        response.addCookie(cookie);
        return true;
    }
}
