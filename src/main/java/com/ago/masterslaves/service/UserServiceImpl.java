package com.ago.masterslaves.service;

import com.ago.masterslaves.bean.User;
import com.ago.masterslaves.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName:UserServiceimpl
 * @Describe:
 * @Data:2020/3/1914:46
 * @Author:Ago
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements  UserService{

    @Autowired
    private UserMapper userDao;

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public List<User> selectAll(User user) {
        return userDao.selectAll(user);
    }

    @Override
    public List<User> selectByCondition(User user) {
        return userDao.selectByCondition(user);
    }
}
