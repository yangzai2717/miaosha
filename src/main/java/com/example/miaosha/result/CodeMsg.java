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
    public static CodeMsg SERVER_ERROE = new CodeMsg(500100, "服务端异常");

    //登陆模块

    private CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }


    public String getMsg() {
        return msg;
    }

}
