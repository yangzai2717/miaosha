package com.example.miaosha.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/10/12 10:17
 * @Description: 连接数据库util
 */
public class DBUtil {

    private static Properties props ;

    static {
        InputStream in = DBUtil.class.getClassLoader().getResourceAsStream("application.properties");
        props = new Properties();
        try {
            props.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConn() throws Exception{
        String url = props.getProperty("spring.datasource.url");
        String username = props.getProperty("spring.datasource.username");
        String password = props.getProperty("spring.datasource.password");
        String driver = props.getProperty("spring.datasource.driver-class-name");
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
}
