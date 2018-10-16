package com.example.miaosha.exception;

import com.example.miaosha.result.CodeMsg;
import com.example.miaosha.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/8 10:33
 * @Description: 全局异常处理  2
 */

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result<String> exceptionHandler(HttpServletRequest request, Exception e){
        if(e instanceof GlobalException){
            e.printStackTrace();
            GlobalException ex = (GlobalException) e;
            return Result.error(ex.getCm());
        }
        if(e instanceof BindException){
            e.printStackTrace();
            BindException ex = (BindException) e;
            List<ObjectError> errors = ex.getAllErrors();
            ObjectError error = errors.get(0);
            String message = error.getDefaultMessage();
            return Result.error(CodeMsg.BIND_ERROE.fillArgs(message));
        }else{
            e.printStackTrace();
            return Result.error(CodeMsg.SERVER_ERROE);
        }
    }

}
