package com.crrcdt.meeting.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crrcdt.meeting.entity.Meeting;
import com.crrcdt.meeting.entity.MeetingConditionVo;
import com.crrcdt.meeting.mapper.MeetingMapper;
import com.crrcdt.meeting.service.MeetingService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author liujun
 * @since 2020-09-20
 */
@Service
public class MeetingServiceImpl extends ServiceImpl<MeetingMapper, Meeting> implements MeetingService {

    @Autowired
    MeetingMapper meetingMapper;
    @Override
    public IPage<Meeting> getPageMeetings(Page<Meeting> meetingPage, MeetingConditionVo meetingConditionVo) {
        IPage<Meeting> meetingIPage=meetingMapper.getPageMeetings(meetingPage,meetingConditionVo);
        return meetingIPage;
    }
}
