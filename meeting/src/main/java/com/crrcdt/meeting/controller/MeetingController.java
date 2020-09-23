package com.crrcdt.meeting.controller;


import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crrcdt.meeting.constant.MeetingStatus;
import com.crrcdt.meeting.entity.*;
import com.crrcdt.meeting.service.*;
import com.crrcdt.meeting.utils.currentUser;
import freemarker.template.utility.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 *  会议预定模块
 * </p>
 *
 * @author liujun
 * @since 2020-09-20
 */
@Controller
@RequestMapping("/meeting")
public class MeetingController {
    @Autowired
    MeetingroomService meetingroomService;
    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    MeetingService meetingService;
    @Autowired
    MeetingparticipantsService meetingparticipantsService;

    /**
     * 跳转到添加会议室页面
     * @return
     */
    @RequestMapping("/addmeetingroom")
   public String toAddMeeting(){
       return "/meeting/addmeetingroom";
   }

    /**
     * 添加会议室
     * @param meetingroom
     * @param session
     * @param request
     * @param map
     * @return
     */
    @PostMapping("/doAddMr")
    public ModelAndView doAddMr(Meetingroom meetingroom, HttpSession session, HttpServletRequest request, HashMap<String,Object> map){
        currentUser.setLoginUserMap(session,request);
        final boolean res = meetingroomService.save(meetingroom);
        if(res){
            map.put("msg","添加会议成功！");
        }else {
            map.put("msg","添加会议失败！");
        }
        return new ModelAndView("/meeting/addmeetingroom",map);
    }
    @RequestMapping("/viewMeetingroom")
    public ModelAndView viewMeetingroom(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                        @RequestParam(value = "size", defaultValue = "3") Integer size,
            HttpSession session, HttpServletRequest request, HashMap<String,Object> map){
        final Page<Meetingroom> meetingroomPage = new Page<>(page,size);
        final IPage<Meetingroom> meetingroomIPage = meetingroomService.page(meetingroomPage, null);
        currentUser.setLoginUserMap(session,request);
        map.put("mrs",meetingroomIPage.getRecords());
        map.put("emps",meetingroomIPage.getRecords());
        map.put("currentPage", page);
        map.put("size", size);
        map.put("totalPage", (meetingroomIPage.getTotal() % size)== 0 ? (meetingroomIPage.getTotal() / size) : (meetingroomIPage.getTotal() / size)+1  );
        return new ModelAndView("/meeting/meetingrooms",map);
    }

    /**
     * 查看会议详情
     * @param roomid
     * @param map
     * @return
     */
    @RequestMapping("/roomdetails")
    public ModelAndView toRoomDetails(@RequestParam("roomid") Integer roomid,HashMap<String,Object> map){
        final Meetingroom meetingroom = meetingroomService.getById(roomid);
        map.put("mr",meetingroom);
        return new ModelAndView("/meeting/roomdetails",map);
    }

    /**
     * 修改会议详情
     * @param meetingroom
     * @param session
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/updateroom")
    public ModelAndView updateroom(Meetingroom meetingroom, HttpSession session, HttpServletRequest request,HashMap<String,Object> map){
        currentUser.setLoginUserMap(session,request);
        final boolean res = meetingroomService.updateById(meetingroom);
        if(res){
            map.put("msg","会议室修改成功！！");
        }
        else {
            map.put("msg","会议室修改失败！！");
        }
        map.put("mr",meetingroom);
        return new ModelAndView("/meeting/roomdetails",map);
    }
    @RequestMapping("/bookmeeting")
    public ModelAndView tobookMeeting(HashMap<String,Object> map){
        final List<Meetingroom> meetingrooms = meetingroomService.list(null);
        map.put("mrs",meetingrooms);
        return new ModelAndView("/meeting/bookmeeting",map);
    }
    @ResponseBody
    @RequestMapping("/alldeps")
    public List<Department> getAlldeps(){
        final List<Department> departments = departmentService.list(null);
        return departments;
    }
    @ResponseBody
    @RequestMapping("/getempbydepid")
    public List<Employee> getAlldeps(@RequestParam("depId") Integer depId){
        final List<Employee> employees = employeeService.list(new QueryWrapper<Employee>().eq("departmentid", depId));
        return employees;
    }
//    @PostMapping("/doAddMeeting")
//    public ModelAndView toDoAddMeeting(@RequestParam("mps") List<Integer> mps,
//                                       @RequestParam("meetingname") String meetingname,
//                                       @RequestParam("numberofparticipants") Integer numberofparticipants,
//                                       @RequestParam("starttime") String starttime,
//                                       @RequestParam("endtime") String endtime,
//                                       @RequestParam("roomid") Integer roomid,
//                                       @RequestParam("description") String description,
//                                                   HashMap<String,Object> map){
//        final List<Meetingroom> meetingrooms = meetingroomService.list(null);
//        map.put("mrs",meetingrooms);
//        return new ModelAndView("/meeting/bookmeeting",map);
//    }

    /**
     * 新增一个预定会议
     * @param meetingVo
     * @param map
     * @return
     */
@PostMapping("/doAddMeeting")
@Transactional
public ModelAndView toDoAddMeeting(MeetingVo meetingVo,
                                   HashMap<String,Object> map,
                                   HttpServletRequest request){
        final Employee currentEmployee = currentUser.getCurrentEmployee(request);
        final Meeting meeting = new Meeting();
        meeting.setStarttime(DateUtil.parse(meetingVo.getStarttime()));
        meeting.setEndtime(DateUtil.parse(meetingVo.getEndtime()));
        meeting.setStatus(MeetingStatus.NO_START_STATUS);
        meeting.setMeetingname(meetingVo.getMeetingname());
        meeting.setReservationistid(currentEmployee.getEmployeeid());
        meeting.setDescription(meetingVo.getDescription());
        meeting.setNumberofparticipants(meetingVo.getNumberofparticipants());
        meeting.setRoomid(meetingVo.getRoomid());
        meeting.setReservationtime(new Date());
        final boolean res = meetingService.save(meeting);
        if(res){
            final Integer meetingid = meeting.getMeetingid();
            final List<Meetingparticipants> meetingparticipants = meetingVo.getMps().stream().map(empId -> new Meetingparticipants(meetingid, empId)).collect(Collectors.toList());
            if(meetingparticipants!=null){
                final boolean result = meetingparticipantsService.saveBatch(meetingparticipants);
                Assert.isTrue(result,"添加预定会议失败哦!!");
                map.put("msg","添加预定会议成功!!");
            }
        }else {
            map.put("msg","添加预定会议失败!!");
        }
    final List<Meetingroom> meetingrooms = meetingroomService.list(null);
    map.put("mrs",meetingrooms);
    return new ModelAndView("/meeting/bookmeeting",map);
}

    /**
     * 跳转到搜索会议页面
     * @param page
     * @param size
     * @param map
     * @return
     */
    @RequestMapping("/searchmeetings")
    public ModelAndView toSearchMeetings(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "5") Integer size,
                                         HashMap<String,Object> map){
        final Page<Meeting> meetingPage = new Page<>(page,size);
        final IPage<Meeting> meetingIPage = meetingService.page(meetingPage, null);
        meetingIPage.getRecords().stream().forEach(meeting -> {
                    final List<Meetingroom> meetingrooms = meetingroomService.list(new QueryWrapper<Meetingroom>().eq("roomid", meeting.getRoomid()));
                    if(meetingrooms!=null){
                        meeting.setRoomName(meetingrooms.get(0).getRoomname());
                    }
            final List<Employee> employees = employeeService.list(new QueryWrapper<Employee>().eq("employeeid", meeting.getReservationistid()));
                    if(employees!=null){
                        meeting.setReservationistName(employees.get(0).getEmployeename());
                    }
        });
        final MeetingConditionVo meetingConditionVo = new MeetingConditionVo();
        map.put("meetingConditionVo",meetingConditionVo);
        map.put("meetings",meetingIPage.getRecords());
        map.put("currentPage", page);
        map.put("size", size);
        map.put("totalPage", (meetingIPage.getTotal() % size)== 0 ? (meetingIPage.getTotal() / size) : (meetingIPage.getTotal() / size)+1  );
        return new ModelAndView("/meeting/searchmeetings",map);
    }

    /**
     * 进行搜索 返回分页结果
     * @param meetingConditionVo
     * @param page
     * @param size
     * @param map
     * @return
     */
    @GetMapping("/doSearchmeetings")
    public ModelAndView doSearchmeetings(MeetingConditionVo meetingConditionVo,
                                         @RequestParam(value = "page", defaultValue = "1") Integer page,
                                         @RequestParam(value = "size", defaultValue = "5") Integer size,
                                         HashMap<String,Object> map){
        final Page<Meeting> meetingPage = new Page<>(page,size);
        IPage<Meeting> meetingIPage = meetingService.getPageMeetings(meetingPage,meetingConditionVo);
        meetingIPage.getRecords().stream().forEach(meeting -> {
            final List<Meetingroom> meetingrooms = meetingroomService.list(new QueryWrapper<Meetingroom>().eq("roomid", meeting.getRoomid()));
            if(meetingrooms!=null){
                meeting.setRoomName(meetingrooms.get(0).getRoomname());
            }
            final List<Employee> employees = employeeService.list(new QueryWrapper<Employee>().eq("employeeid", meeting.getReservationistid()));
            if(employees!=null){
                meeting.setReservationistName(employees.get(0).getEmployeename());
            }
        });
        map.put("meetingConditionVo",meetingConditionVo);
        map.put("meetings",meetingIPage.getRecords());
        map.put("currentPage", page);
        map.put("size", size);
        map.put("totalPage", (meetingIPage.getTotal() % size)== 0 ? (meetingIPage.getTotal() / size) : (meetingIPage.getTotal() / size)+1  );
        return new ModelAndView("/meeting/searchmeetings",map);
    }


}

