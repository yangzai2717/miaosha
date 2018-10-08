package com.example.miaosha.controller;

import com.example.miaosha.domain.MiaoshaUser;
import com.example.miaosha.redis.MiaoshaUserKey;
import com.example.miaosha.redis.RedisService;
import com.example.miaosha.result.Result;
import com.example.miaosha.service.MiaoshaUserService;
import com.example.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/goods")
public class GoodsController {

    private static Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;


    //controller中的方法分为两大类
    //1. rest api json输出 2.页面
    @RequestMapping("/to_list")
    public String toLogin(Model model,
                          @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
                          @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String paramToken){
        //有些时候，手机端会把token放到参数里，并不会放到cookie中，所以兼容这种情况，再从参数中获取一边，只需要设置优先级就可以了，参数中的优先级 高于 cookie中
        //参数判断
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return "login";  //重新登录
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(token);
        model.addAttribute("user", miaoshaUser);
        return "goods_list";
    }



}
