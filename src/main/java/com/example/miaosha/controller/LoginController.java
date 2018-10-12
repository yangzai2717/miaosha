package com.example.miaosha.controller;

import com.example.miaosha.redis.RedisService;
import com.example.miaosha.result.CodeMsg;
import com.example.miaosha.result.Result;
import com.example.miaosha.service.MiaoshaUserService;
import com.example.miaosha.util.ValidatorUtil;
import com.example.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/27 11:30
 * @Description:
 */
@Controller
@RequestMapping("/login")
public class LoginController {

    private static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;


    //controller中的方法分为两大类
    //1. rest api json输出 2.页面
    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public Result<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo){
        logger.info(loginVo.toString());


        String token = miaoshaUserService.login(response, loginVo);
        return Result.success(token);

        //参数校验  不需要了 因为有了jsr参数校验
        /*String inputPass = loginVo.getPassword();
        String mobile = loginVo.getMobile();
        if (StringUtils.isEmpty(inputPass)){
            return Result.error(CodeMsg.PASSWORD_EMPTY);
        }
        if (StringUtils.isEmpty(mobile)){
            return Result.error(CodeMsg.MOBILE_EMPTY);
        }
        if(!ValidatorUtil.isMobile(mobile)){
            return Result.error(CodeMsg.MOBILE_ERROR);
        }*/


        //登录 增加了 全局异常处理，所以下面的就不需要了
        /*CodeMsg codeMsg = miaoshaUserService.login(loginVo);
        if(codeMsg.getCode() == 0){
            return Result.success(true);
        }else {
            return Result.error(codeMsg);
        }*/



    }


}
