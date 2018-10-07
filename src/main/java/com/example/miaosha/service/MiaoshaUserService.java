package com.example.miaosha.service;

import com.example.miaosha.dao.MiaoshaUserDao;
import com.example.miaosha.domain.MiaoshaUser;
import com.example.miaosha.result.CodeMsg;
import com.example.miaosha.util.MD5Util;
import com.example.miaosha.vo.LoginVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MiaoshaUserService {

    @Autowired
    MiaoshaUserDao miaoshaUserDao;

     public MiaoshaUser getById(Long id){
         return miaoshaUserDao.getById(id);
     }

    public CodeMsg login(LoginVo loginVo) {
         if(loginVo == null){
             return CodeMsg.SERVER_ERROE;
         }
         String mobile = loginVo.getMobile();
         String formPass = loginVo.getPassword();
         //判断手机号是否存在
        MiaoshaUser user = miaoshaUserDao.getById(Long.parseLong(mobile));
        if (user == null){
            return CodeMsg.MOBILE_NOT_EXIST;
        }
        //验证密码
        String dbPass = user.getPassword();
        String saltDB = user.getSalt();
        String calcPass = MD5Util.formPassToDBPass(formPass, saltDB);
        if(!dbPass.equals(calcPass)){
            return CodeMsg.PASSWORD_ERROR;
        }
        return CodeMsg.SUCCESS;
    }
}
