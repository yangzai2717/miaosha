package com.example.miaosha.result;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/27 15:32
 * @Description:
 */
public class CodeMsg {

    private int code;
    private String msg;

    //通用异常 （好处是只需要一个地方维护就可以了  很方便）
    public static CodeMsg SUCCESS = new CodeMsg(0, "success");
    public static CodeMsg SERVER_ERROE = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROE = new CodeMsg(500101, "参数校验异常：%s");  //带了一个参数
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg ACCESS_LIMIT_REQUEST = new CodeMsg(500103, "访问太频繁了");

    //登录模块 500200
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg PASSWORD_EMPTY = new CodeMsg(500211, "密码不能为空");
    public static CodeMsg MOBILE_EMPTY = new CodeMsg(500212, "手机号不能为空");
    public static CodeMsg MOBILE_ERROR = new CodeMsg(500213, "手机号格式错误");
    public static CodeMsg MOBILE_NOT_EXIST = new CodeMsg(500214, "手机号不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500215, "密码错误");


    //秒杀模块 500500
    public static CodeMsg MIAO_SHA_OVER = new CodeMsg(500500, "商品已经秒杀完毕");
    public static CodeMsg REPEATE_MIAOSHA = new CodeMsg(500501, "不能重复秒杀");

    //订单模块 500400
    public static CodeMsg ORDER_NOT_EXIST = new CodeMsg(500400, "订单不存在");

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg fillArgs(Object...args){
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
