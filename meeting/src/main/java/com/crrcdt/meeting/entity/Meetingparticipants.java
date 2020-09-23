package com.crrcdt.meeting.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
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
@ApiModel(value="Meetingparticipants对象", description="")
public class Meetingparticipants implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer meetingid;

    private Integer employeeid;

    public Meetingparticipants(Integer meetingid, Integer employeeid) {
        this.meetingid = meetingid;
        this.employeeid = employeeid;
    }

    public Meetingparticipants() {
    }
}
