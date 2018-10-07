package com.example.miaosha.util;

import org.thymeleaf.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {

    //以1开口，并且后面是10个数字
    private static final Pattern mobile_pattern = Pattern.compile("1\\d{10}");

    public static  boolean isMobile(String src){
        if(StringUtils.isEmpty(src)){
            return false;
        }
        Matcher m = mobile_pattern.matcher(src);
        return m.matches();
    }

    public static void main(String[] args) {
        System.out.println(isMobile("18912341234"));
    }
}
