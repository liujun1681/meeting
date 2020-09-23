package com.crrcdt.meeting.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crrcdt.meeting.entity.Meeting;
import com.baomidou.mybatisplus.extension.service.IService;
import com.crrcdt.meeting.entity.MeetingConditionVo;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author liujun
 * @since 2020-09-20
 */

public interface MeetingService extends IService<Meeting> {

    IPage<Meeting> getPageMeetings(Page<Meeting> meetingPage, MeetingConditionVo meetingConditionVo);
}
