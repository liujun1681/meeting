package com.crrcdt.meeting.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crrcdt.meeting.entity.Employee;
import com.crrcdt.meeting.mapper.EmployeeMapper;
import com.crrcdt.meeting.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujun
 * @since 2020-09-20
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService, UserDetailsService {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    HttpSession session;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {// 通过用户名查询用户
        Employee employee = employeeService.getOne(new QueryWrapper<Employee>().eq("username", s));

        // 放入session
        session.setAttribute("loginUser", employee);

        //创建一个新的UserDetails对象，最后验证登陆的需要
        UserDetails userDetails = null;
        if (employee != null) {
            System.out.println("未加密："+employee.getPassword());
            String BCryptPassword = new BCryptPasswordEncoder().encode(employee.getPassword());
            // 登录后会将登录密码进行加密，然后比对数据库中的密码，数据库密码需要加密存储！（这里很恶心！）
            String password = employee.getPassword();

            //创建一个集合来存放权限
            Collection<GrantedAuthority> authorities = getAuthorities(employee);
            //实例化UserDetails对象
            userDetails = new org.springframework.security.core.userdetails.User(s, BCryptPassword,
                    true,
                    true,
                    true,
                    true, authorities);
        }
        return userDetails;
    }
    private Collection<GrantedAuthority> getAuthorities (Employee employee){
        final String role = employee.getRole();
        List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
        authList.add(new SimpleGrantedAuthority("ROLE_" +("1".equals(role) ? "管理员":"普通用户")));
        return authList;
    }
}
