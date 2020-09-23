package com.crrcdt.meeting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author liujun
 * @since 2020-09-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Meeting对象", description="")
public class Meeting implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "meetingid", type = IdType.AUTO)
    private Integer meetingid;

    private String meetingname;

    private Integer roomid;

    private Integer reservationistid;

    private Integer numberofparticipants;

    private Date starttime;

    private Date endtime;

    private Date reservationtime;

    private Date canceledtime;

    private String description;

    private String status;

    private String canceledreason;

    @TableField(exist = false)
    private String roomName;
    @TableField(exist = false)
    private String reservationistName;
}
