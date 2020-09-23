package com.crrcdt.meeting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujun
 * @since 2020-09-20
 */
@Data
public class MeetingVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String meetingname;

    private Integer roomid;

    private Integer numberofparticipants;

    private String starttime;

    private String endtime;

    private String description;

    private List<Integer> mps;
}
