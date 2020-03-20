package com.ago.masterslaves.service;

import com.ago.masterslaves.bean.User;

import java.util.List;

public interface UserService {

    void save(User user);

    void update(User user);

    List<User> selectAll(User user);

    List<User> selectByCondition(User user);

}
