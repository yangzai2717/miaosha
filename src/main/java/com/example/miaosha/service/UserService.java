package com.example.miaosha.service;

import com.example.miaosha.dao.UserDao;
import com.example.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: 庞洋洋
 * @Date: 2018/9/28 15:09
 * @Description:
 */
@Service
public class UserService {

    @Autowired
    public UserDao userDao;

    public User getById(int id){
        return userDao.getByid(id);
    }

    @Transactional
    public boolean tx(){
        User u1 = new User();
        u1.setId(2);
        u1.setName("2222");
        userDao.insert(u1);

        User u2 = new User();
        u2.setId(1);
        u2.setName("1111");
        userDao.insert(u2);

        return true;
    }

}
