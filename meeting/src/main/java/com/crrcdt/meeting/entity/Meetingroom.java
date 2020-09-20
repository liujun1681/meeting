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
@ApiModel(value="Meetingroom对象", description="")
public class Meetingroom implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "roomid", type = IdType.AUTO)
    private Integer roomid;

    private Integer roomnum;

    private String roomname;

    private Integer capacity;

    private String status;

    private String description;


}
