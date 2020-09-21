package com.crrcdt.meeting.entity;

import lombok.Data;

/**
 * @author lj on 2020/9/21.
 * @version 1.0
 */
@Data
public class ConditionVo {
   private String employeename;
   private String username;
   private String status;

   public ConditionVo(String employeename, String username, String status) {
      this.employeename = employeename;
      this.username = username;
      this.status = status;
   }

   public ConditionVo() {
   }
}
