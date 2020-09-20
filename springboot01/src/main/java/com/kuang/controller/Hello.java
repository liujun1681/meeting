package com.kuang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/hello")
public class Hello {
    @RequestMapping("/hello")
    @ResponseBody
    public String testHello(){
        return "hello springboot";
    }
}
