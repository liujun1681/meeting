package com.crrcdt.meeting.mapper;

import com.crrcdt.meeting.model.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author lj on 2020/9/18.
 * @version 1.0
 */
@Mapper
public interface UserMapper {
   User getUserById(Integer id);
}
