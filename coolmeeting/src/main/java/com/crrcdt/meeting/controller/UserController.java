package com.crrcdt.meeting.controller;

import com.crrcdt.meeting.model.User;
import com.crrcdt.meeting.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lj on 2020/9/18.
 * @version 1.0
 */
@RestController
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("/getUserById")
    private void getUserById(Integer id){
      User user= userService.getUserById(id);
        System.out.println("user = " + user);
//        return user;
    }
}
