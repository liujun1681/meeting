package com.crrcdt.meeting.entity;

import lombok.Data;

/**
 * @author lj on 2020/9/22.
 * @version 1.0
 */
@Data
public class MeetingConditionVo {
    private String meetingname;
    private String roomname;
    private String reservername;
    private String reservefromdate;
    private String reservetodate;
    private String meetingfromdate;
    private String meetingtodate;
}
