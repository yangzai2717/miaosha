package com.example.miaosha.config;

import com.example.miaosha.domain.MiaoshaUser;
import com.example.miaosha.service.MiaoshaUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/8 16:09
 * @Description:
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {


    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
       Class<?> clazz = methodParameter.getParameterType();  //获取我们参数的类型
        return clazz == MiaoshaUser.class;
    }

    @Nullable
    @Override
    public Object resolveArgument(MethodParameter methodParameter, @Nullable ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, @Nullable WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class); //取出 request 和 response
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        //从reques中取出 参数
        String paramToken = request.getParameter(MiaoshaUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(request, MiaoshaUserService.COOKIE_NAME_TOKEN);
        if(StringUtils.isEmpty(cookieToken) && StringUtils.isEmpty(paramToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return miaoshaUserService.getByToken(token, response);
    }

    //遍历cookie中所有的指，取出我们所需要的 及 token
    private String getCookieValue(HttpServletRequest request, String cookieName){
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies){
            if (cookie.getName().equals(cookieName)){
                return cookie.getValue();
            }
        }
        return  null;
    }
}
