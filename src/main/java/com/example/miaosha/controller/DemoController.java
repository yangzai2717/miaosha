package com.example.miaosha.controller;

import com.example.miaosha.domain.MiaoshaUser;
import com.example.miaosha.domain.User;
import com.example.miaosha.rabbitmq.MQSender;
import com.example.miaosha.redis.RedisService;
import com.example.miaosha.redis.UserKey;
import com.example.miaosha.result.CodeMsg;
import com.example.miaosha.result.Result;
import com.example.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/27 11:30
 * @Description:
 */
@Controller
@RequestMapping("/demo")
public class DemoController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @Autowired
    MQSender mqSender;

    /*@RequestMapping("/mq")
    @ResponseBody
    Result<String> mq(){
        mqSender.send("hello pyy");
        return Result.success("hello world");
    }
    @RequestMapping("/mq/topic")
    @ResponseBody
    Result<String> topic(){
        mqSender.sendTopic("hello pyy");
        return Result.success("hello world");
    }

    @RequestMapping("/mq/fanout")
    @ResponseBody
    Result<String> fanout(){
        mqSender.sendFanout("hello pyy");
        return Result.success("hello world");
    }*/

    //controller中的方法分为两大类
    //1. rest api json输出 2.页面
    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        return Result.success("hello.imooc");
        //return new Result(0, "success", "hello");
    }

    @RequestMapping("/helloError")
    @ResponseBody
    public Result<String> error(){
        return Result.error(CodeMsg.SERVER_ERROE);
        //return new Result(501, "session失效");
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        model.addAttribute("name", "pyy");
        return "hello";
    }

    @RequestMapping("/db/get")
    @ResponseBody
    public Result<User> dbGet(){
        User user = userService.getById(1);
        return Result.success(user);
    }

    @RequestMapping("/db/tx")
    @ResponseBody
    public Result<Boolean> dbTx(){
        Boolean b = userService.tx();
        return Result.success(b);
    }

    @RequestMapping("/redis/get")
    @ResponseBody
    public Result<User> redisGet(){
        User user = redisService.get(UserKey.getById,""+1, User.class);
        return Result.success(user);
    }

    @RequestMapping("/redis/set")
    @ResponseBody
    public Result<Boolean> redisSet(){
        User user = new User();
        user.setId(1);
        user.setName("1111111");
        redisService.set(UserKey.getById, ""+1, user);
        return Result.success(true);
    }

    @RequestMapping("/info")
    @ResponseBody
    public Result<MiaoshaUser> info(Model model, MiaoshaUser user){
        model.addAttribute("user", user);
        return Result.success(user);
    }
}
