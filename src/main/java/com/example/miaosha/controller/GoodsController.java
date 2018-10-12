package com.example.miaosha.controller;

import com.example.miaosha.domain.MiaoshaUser;
import com.example.miaosha.redis.GoodsKey;
import com.example.miaosha.redis.MiaoshaUserKey;
import com.example.miaosha.redis.RedisService;
import com.example.miaosha.result.Result;
import com.example.miaosha.service.GoodsService;
import com.example.miaosha.service.MiaoshaUserService;
import com.example.miaosha.vo.GoodsVo;
import com.example.miaosha.vo.LoginVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.context.webflux.SpringWebFluxContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

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

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    //没有 页面缓存 的方法
    /*@RequestMapping("/to_list")
    public String list(Model model, MiaoshaUser user){
        //因为配置了 webconfig 重写的ArgumentResolver() 方法 所以不需要再一直获取token 参数 和校验了
        //查询商品列表
        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGetGoodsVo();
        model.addAttribute("goodsList", goodsList);
        return "goods_list";
    }*/

    //使用 页面缓存 的方法
    @RequestMapping(value = "/to_list", produces = "text/html; Charset=UTF-8")
    @ResponseBody
    public String list(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user){

        model.addAttribute("user", user);
        List<GoodsVo> goodsList = goodsService.listGetGoodsVo();
        model.addAttribute("goodsList", goodsList);
        //取缓存
        String html = redisService.get(GoodsKey.getGoodsList, "", String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }
        //手动渲染 SpringWebContext 过时，可以用WebContext
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_list", ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList, "", html);
        }
        return html;
    }

    //没有url缓存的方法
    /*@RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model, MiaoshaUser user,
                         @PathVariable("goodsId") Long goodsId){
        model.addAttribute("user", user);
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);

        //秒杀什么时候开始
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;  //秒杀状态
        int remainSeconds = 0;  //秒杀还剩多少秒
        if(now < startAt){ //秒杀没有开始 倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now)/1000);
        } else if(now > endAt) { //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else{  //秒杀正在进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }
        model.addAttribute("goods", goods);
        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);
        return "goods_detail";
    }*/

    @RequestMapping(value = "/to_detail/{goodsId}", produces = "text/html; Charset=UTF-8")
    @ResponseBody
    public String detail(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user,
                         @PathVariable("goodsId") Long goodsId){
        model.addAttribute("user", user);

        //取缓存
        String html = redisService.get(GoodsKey.getGoodsDetail, ""+goodsId, String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        //手动渲染
        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods", goods);

        //秒杀什么时候开始
        long startAt = goods.getStartDate().getTime();
        long endAt = goods.getEndDate().getTime();
        long now = System.currentTimeMillis();

        int miaoshaStatus = 0;  //秒杀状态
        int remainSeconds = 0;  //秒杀还剩多少秒
        if(now < startAt){ //秒杀没有开始 倒计时
            miaoshaStatus = 0;
            remainSeconds = (int)((startAt - now)/1000);
        } else if(now > endAt) { //秒杀已经结束
            miaoshaStatus = 2;
            remainSeconds = -1;
        } else{  //秒杀正在进行中
            miaoshaStatus = 1;
            remainSeconds = 0;
        }

        model.addAttribute("miaoshaStatus", miaoshaStatus);
        model.addAttribute("remainSeconds", remainSeconds);

        //手动渲染
        WebContext ctx = new WebContext(request, response, request.getServletContext(), request.getLocale(), model.asMap());
        html = thymeleafViewResolver.getTemplateEngine().process("goods_detail", ctx);
        if(!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail, ""+goodsId, html);
        }
        return html;
    }


    //controller中的方法分为两大类
    //1. rest api json输出 2.页面
    /*@RequestMapping("/to_list")
    public String list(Model model, HttpServletResponse response,
                          @CookieValue(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String cookieToken,
                          @RequestParam(value = MiaoshaUserService.COOKIE_NAME_TOKEN, required = false) String paramToken){
        //有些时候，手机端会把token放到参数里，并不会放到cookie中，所以兼容这种情况，再从参数中获取一边，只需要设置优先级就可以了，参数中的优先级 高于 cookie中
        //参数判断
        if (StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return "login";  //重新登录
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        MiaoshaUser miaoshaUser = miaoshaUserService.getByToken(token, response);
        model.addAttribute("user", miaoshaUser);
        return "goods_list";
    }*/




}
