package com.crrcdt.meeting.utils;

import com.crrcdt.meeting.entity.Employee;

import javax.servlet.http.HttpServletRequest;

/**得到当前的用户
 * @author lj on 2020/9/20.
 * @version 1.0
 */
public class  currentUser {
    public static Employee getCurrentEmployee(HttpServletRequest request){
        final Employee user = (Employee)request.getSession().getAttribute("loginUser");
         return user;
    }
}
