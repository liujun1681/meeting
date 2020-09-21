package com.crrcdt.meeting.utils;

import com.crrcdt.meeting.entity.Employee;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**得到当前的用户
 * @author lj on 2020/9/20.
 * @version 1.0
 */
public class  currentUser {
    /**
     * 得到当前的用户
     * @param request
     * @return
     */
    public static Employee getCurrentEmployee(HttpServletRequest request){
        final Employee user = (Employee)request.getSession().getAttribute("loginUser");
         return user;
    }

    /**
     * 设置当前会话的用户
     * @param httpSession
     * @param request
     */
    public static void setLoginUserMap(HttpSession httpSession,HttpServletRequest request){
        final Employee loginUser = (Employee) request.getSession().getAttribute("loginUser");
        httpSession.setAttribute("employee",loginUser);
    }

    }
