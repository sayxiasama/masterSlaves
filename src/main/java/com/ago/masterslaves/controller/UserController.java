package com.ago.masterslaves.controller;

import com.ago.masterslaves.bean.User;
import com.ago.masterslaves.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName:UserControllre
 * @Describe:
 * @Data:2020/3/2016:00
 * @Author:Ago
 * @Version 1.0
 */
@RestController
@RequestMapping("/masterSlaves")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/get")
    public List<User> getUserList(User user){
        return userService.selectAll(user);
    }

    @RequestMapping("/getByCondition")
    public List<User> getByCondition(User user){
        return userService.selectByCondition(user);
    }

    @RequestMapping("/save")
    public String save(User user){
        userService.save(user);
        return "success";
    }

    @RequestMapping("/update")
    public String update(User user){
        userService.update(user);
        return "success";
    }

}
