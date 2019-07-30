package com.sherlock.miaosha.service;

import com.sherlock.miaosha.dao.UserDao;
import com.sherlock.miaosha.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: miaosha
 * @description:
 * @author: Mr.Jiang
 * @create: 2019-07-22 16:08
 **/
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(int id){
        return userDao.getById(id);
    }

    @Transactional
    public boolean tx() {
        User user1 = new User();
        user1.setId(1);
        user1.setName("zhangsan");
        userDao.insert(user1);

        User user2 = new User();
        user2.setId(1410080408);
        user2.setName("zhangsan");
        userDao.insert(user2);
        return true;
    }
}
