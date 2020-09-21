package com.crrcdt.meeting.controller;

import com.crrcdt.meeting.entity.Employee;
import com.crrcdt.meeting.utils.currentUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @author lj on 2020/9/20.
 * @version 1.0
 */
@Controller
public class LoginController {
    @RequestMapping("/toLogin")
    public String toLogin(){
        return "/common/login";
    }

    @RequestMapping({"/","/index"})
    public String toIndex(){
        return "/ftl/login";
    }

    @RequestMapping({"/changepassword"})
    public String toChangePassword(HttpSession httpSession, HttpServletRequest request){
        currentUser.setLoginUserMap(httpSession,request);
        return "/common/changepassword";
    }
    @RequestMapping("/loginOut")
    public String loginOut(HttpSession httpSession, HttpServletRequest request){
        return "/common/login";
    }

}
