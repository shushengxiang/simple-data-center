package com.example.controller;

import com.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description
 * @Author shushengxiang
 * @Date 2020/4/3 16:20
 **/
@RestController
public class UserController {

    @Autowired
    private User user;


    @GetMapping("/v1/user")
    public User getUser(){
        return user;
    }
}
