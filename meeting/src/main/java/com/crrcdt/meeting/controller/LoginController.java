package com.crrcdt.meeting.controller;

import com.crrcdt.meeting.entity.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

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

    /**
     * 跳转页面
     * @param httpSession
     * @param request
     * @return
     */
    @RequestMapping({"/notifications"})
    public ModelAndView toNotifications(HttpSession httpSession, HttpServletRequest request, Map<String, Object> map){
        final Employee loginUser = (Employee) request.getSession().getAttribute("loginUser");
        map.put("employee", loginUser);
        return new ModelAndView("/common/notifications",map);
    }
    @RequestMapping({"/changepassword"})
    public String toChangePassword(){
        return "/common/changepassword";
    }
}
