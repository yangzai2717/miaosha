package com.example.miaosha.exception;

import com.example.miaosha.result.CodeMsg;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/8 11:10
 * @Description:
 */
public class GlobalException extends RuntimeException{

    private CodeMsg cm;

    public GlobalException(CodeMsg cm){
        super(cm.toString());
        this.cm = cm;
    }

    public CodeMsg getCm() {
        return cm;
    }

}
