package com.crrcdt.meeting.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lj on 2020/9/18.
 * @version 1.0
 */
@RestController
public class Hello {
    @GetMapping("/hello")
    private String hello(){
        return "hello ssm";
    }
}

