package com.crrcdt.meeting.service;

import com.crrcdt.meeting.mapper.UserMapper;
import com.crrcdt.meeting.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author lj on 2020/9/18.
 * @version 1.0
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;
    public User getUserById(Integer id ) {
        return userMapper.getUserById(id);
    }
}
