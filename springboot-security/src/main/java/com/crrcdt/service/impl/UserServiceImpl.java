package com.crrcdt.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.crrcdt.entity.Role;
import com.crrcdt.entity.User;
import com.crrcdt.mapper.UserMapper;
import com.crrcdt.service.RoleService;
import com.crrcdt.service.UserService;
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
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujun
 * @since 2020-09-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {
    @Autowired
    UserService userService;
    @Autowired
    RoleService roleService;
    @Autowired
    HttpSession session;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {// 通过用户名查询用户
        User user = userService.getOne(new QueryWrapper<User>().eq("user", s));

        // 放入session
        session.setAttribute("loginUser", user);

        //创建一个新的UserDetails对象，最后验证登陆的需要
        UserDetails userDetails = null;
        if (user != null) {
            //System.out.println("未加密："+user.getPassword());
//            String BCryptPassword = new BCryptPasswordEncoder().encode(user.getPassword());
            // 登录后会将登录密码进行加密，然后比对数据库中的密码，数据库密码需要加密存储！（这里很恶心！）
            String password = user.getPassword();

            //创建一个集合来存放权限
            Collection<GrantedAuthority> authorities = getAuthorities(user);
            //实例化UserDetails对象
            userDetails = new org.springframework.security.core.userdetails.User(s, password,
                    true,
                    true,
                    true,
                    true, authorities);
        }
        return userDetails;
    }
        private Collection<GrantedAuthority> getAuthorities (User user){
            final String roles = user.getRoles();
            final List<String> list = Arrays.asList(roles.split(","));
            List<GrantedAuthority> authList = new ArrayList<GrantedAuthority>();
            final List<Role> roleList = list.stream().map(entity -> roleService.getOne(new QueryWrapper<Role>().eq("id", entity))).collect(Collectors.toList());
            roleList.forEach(role -> authList.add(new SimpleGrantedAuthority("ROLE_" + role.getRoleName())));
            return authList;
        }
    }
