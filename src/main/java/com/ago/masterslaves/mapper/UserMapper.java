package com.ago.masterslaves.mapper;

import com.ago.masterslaves.bean.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserMapper {

    void save(User user);

    void update(User user);

    List<User> selectAll(User user);

    List<User> selectByCondition(User user);
}
