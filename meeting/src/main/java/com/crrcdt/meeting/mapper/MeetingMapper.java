package com.crrcdt.meeting.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crrcdt.meeting.entity.Meeting;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.crrcdt.meeting.entity.MeetingConditionVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author liujun
 * @since 2020-09-20
 */
@Mapper
@Component
public interface MeetingMapper extends BaseMapper<Meeting> {

    IPage<Meeting> getPageMeetings(Page<Meeting> meetingPage, @Param("meetingConditionVo") MeetingConditionVo meetingConditionVo);
}
