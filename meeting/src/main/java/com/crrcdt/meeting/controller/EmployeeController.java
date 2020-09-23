package com.crrcdt.meeting.controller;


import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crrcdt.meeting.constant.MeetingStatus;
import com.crrcdt.meeting.entity.Employee;
import com.crrcdt.meeting.entity.Meeting;
import com.crrcdt.meeting.entity.Meetingparticipants;
import com.crrcdt.meeting.entity.Meetingroom;
import com.crrcdt.meeting.service.EmployeeService;
import com.crrcdt.meeting.service.MeetingService;
import com.crrcdt.meeting.service.MeetingparticipantsService;
import com.crrcdt.meeting.service.MeetingroomService;
import com.crrcdt.meeting.utils.currentUser;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 * 个人中心模块
 * </p>
 *
 * @author liujun
 * @since 2020-09-20
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;
    @Autowired
    MeetingparticipantsService meetingparticipantsService;
    @Autowired
    MeetingService meetingService;
    @Autowired
    MeetingroomService meetingroomService;
    @RequestMapping({"/updatePassword"})
    public ModelAndView updatePassword(@RequestParam("originPassword")String originPassword,
                                       @RequestParam("newPassword")String newPassword,
                                       @RequestParam("confirmPassword")String confirmPassword,
                                       HttpSession httpSession, HttpServletRequest request,
                                       Map<String, Object> map){
        final Employee loginUser = (Employee) request.getSession().getAttribute("loginUser");
        currentUser.setLoginUserMap(httpSession,request);
        if(loginUser.getPassword().equals(newPassword)||!newPassword.equals(confirmPassword)||!loginUser.getPassword().equals(originPassword)){
            map.put("msg","用户原密码验证错误或前后两次密码不一致！");
            return new ModelAndView("/common/changepassword",map);
        }
        final Employee employee = new Employee();
        employee.setEmployeeid(loginUser.getEmployeeid());
        employee.setPassword(newPassword);
        final boolean res = employeeService.updateById(employee);
        if(res){
            map.put("msg","密码修改成功！");
        }else {
            map.put("msg","密码修改失败！");
        }
        return new ModelAndView("/common/changepassword",map);
    }

    /**
     * 我预定的会议
     * @return
     */
    @RequestMapping({"/mybookings"})
    public ModelAndView tomyBookings(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "3") Integer size,
                                     HttpSession httpSession,
                                     HttpServletRequest request,Map<String, Object> map){
        currentUser.setLoginUserMap(httpSession,request);
        //先得到我预定会议的ids
        final Employee employee = currentUser.getCurrentEmployee(request);
        final Page<Meeting> picturePage = new Page<>(page,size);
        final IPage<Meeting> meetingIPage = meetingService.page(picturePage, new QueryWrapper<Meeting>().eq("reservationistid", employee.getEmployeeid()).eq("status",MeetingStatus.NO_START_STATUS));
        meetingIPage.getRecords().stream().forEach(meeting -> {
            final String roomname = meetingroomService.list(new QueryWrapper<Meetingroom>().eq("roomid", meeting.getRoomid())).get(0).getRoomname();
            meeting.setRoomName(roomname);
        });
        map.put("meetings",meetingIPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("totalPage",(meetingIPage.getTotal() % size)== 0 ? (meetingIPage.getTotal() / size) : (meetingIPage.getTotal() / size)+1 );
        return new ModelAndView( "/my/mybookings",map);
    }

    /**
     * 我要参加的会议
     * @param page
     * @param size
     * @param request
     * @param map
     * @return
     */
    @RequestMapping({"/myParticipateIn"})
    public ModelAndView tomyParticipateIn(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                     @RequestParam(value = "size", defaultValue = "3") Integer size, HttpSession httpSession,
                                     HttpServletRequest request,Map<String, Object> map){
        currentUser.setLoginUserMap(httpSession,request);
        //先得到我要参加的会议的ids
        final Employee currentEmployee = currentUser.getCurrentEmployee(request);
        final List<Meetingparticipants> meetingparticipants = meetingparticipantsService.list(new QueryWrapper<Meetingparticipants>().eq("employeeid", currentEmployee.getEmployeeid()));
        final List<Integer> meetingIds = meetingparticipants.stream().map(m -> m.getMeetingid()).distinct().collect(Collectors.toList());
        final Page<Meeting> picturePage = new Page<>(page,size);
        final IPage<Meeting> meetingIPage = meetingService.page(picturePage, new QueryWrapper<Meeting>().in("meetingid", meetingIds).eq("status", MeetingStatus.NO_START_STATUS));
        meetingIPage.getRecords().stream().forEach(meeting -> {
            final String roomname = meetingroomService.list(new QueryWrapper<Meetingroom>().eq("roomid", meeting.getRoomid())).get(0).getRoomname();
            final List<Employee> employeeList = employeeService.list(new QueryWrapper<Employee>().eq("employeeid", meeting.getReservationistid()));
            meeting.setReservationistName(employeeList.get(0).getEmployeename());
            meeting.setRoomName(roomname);
        });
        map.put("meetings",meetingIPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("totalPage", (meetingIPage.getTotal() % size)== 0 ? (meetingIPage.getTotal() / size) : (meetingIPage.getTotal() / size)+1);
        return new ModelAndView( "/my/mymeetings",map);
    }


    /**
     * 最新通知
     * @param httpSession
     * @param request
     * @param map
     * @return
     */
    @RequestMapping({"/newNoticeMeetings"})
    public ModelAndView toNextSevenDaysMeetings( HttpSession httpSession,HttpServletRequest request,Map<String, Object> map){
        currentUser.setLoginUserMap(httpSession,request);
        //先得到我要参加的会议的ids
        final Employee currentEmployee = currentUser.getCurrentEmployee(request);
        final List<Meetingparticipants> meetingparticipants = meetingparticipantsService.list(new QueryWrapper<Meetingparticipants>().eq("employeeid", currentEmployee.getEmployeeid()));
        final List<Integer> meetingIds = meetingparticipants.stream().map(m -> m.getMeetingid()).distinct().collect(Collectors.toList());
//        DateUtil.offset(date, DateField.DAY_OF_MONTH, 2);
        String now = DateUtil.now();
        final DateTime dateTime = DateUtil.parse(now);
        final List<Meeting> meetings = meetingService.list(new QueryWrapper<Meeting>().in("meetingid", meetingIds)
                .between("starttime", dateTime, DateUtil.offset(dateTime, DateField.DAY_OF_MONTH, 7)));
        if(meetings!=null){
            meetings.stream().forEach(meeting -> {
                final String roomname = meetingroomService.list(new QueryWrapper<Meetingroom>().eq("roomid", meeting.getRoomid())).get(0).getRoomname();
                final List<Employee> employeeList = employeeService.list(new QueryWrapper<Employee>().eq("employeeid", meeting.getReservationistid()));
                meeting.setReservationistName(employeeList.get(0).getEmployeename());
                meeting.setRoomName(roomname);
            });
        }
        //未来七天
        final List<Meeting> nextSevenMeetings = meetings.stream().filter(meeting -> MeetingStatus.NO_START_STATUS.equals(meeting.getStatus())).collect(Collectors.toList());
        map.put("nextSevenMeetings",nextSevenMeetings);
        //七天内取消的会议
        final List<Meeting> cancelMeetings = meetings.stream().filter(meeting -> MeetingStatus.CANCEL_STATUS.equals(meeting.getStatus())).collect(Collectors.toList());
        map.put("cancelMeetings",cancelMeetings);
        return new ModelAndView( "/my/notifications",map);
    }


    /**
     * 会议详情
     * @param meetingId
     * @param httpSession
     * @param request
     * @param map
     * @return
     */
    @RequestMapping({"/viewMeetins"})
    public ModelAndView toViewMeetins(@RequestParam("meetingId") Integer meetingId, HttpSession httpSession, HttpServletRequest request,Map<String, Object> map){
        currentUser.setLoginUserMap(httpSession,request);
        final Employee currentEmployee = currentUser.getCurrentEmployee(request);
        final Meeting meeting = meetingService.getById(meetingId);
        //得到参会人员
        final List<Meetingparticipants> meetingparticipants = meetingparticipantsService.list(new QueryWrapper<Meetingparticipants>().eq("meetingid", meeting.getMeetingid()));
        final List<Integer> employeeIds = meetingparticipants.stream().map(Meetingparticipants::getEmployeeid).collect(Collectors.toList());
        final List<Employee> employeeList = employeeService.list(new QueryWrapper<Employee>().in("employeeid", employeeIds));
        map.put("meeting",meeting);
        map.put("employeeList",employeeList);
        return new ModelAndView("/my/mymeetingdetails",map);
    }

    /**
     * 取消会议跳转
     * @param meetingId
     * @param httpSession
     * @param request
     * @param map
     * @return
     */
    @RequestMapping({"/cancelmeeting"})
    public ModelAndView toCancelmeeting(@RequestParam("meetingId") Integer meetingId, HttpSession httpSession, HttpServletRequest request,Map<String, Object> map){
        currentUser.setLoginUserMap(httpSession,request);
        final Meeting meeting = meetingService.getById(meetingId);
        map.put("meeting",meeting);
        return new ModelAndView("/my/cancelmeeting",map);
    }
    @PostMapping("/cancel")
    public ModelAndView cancelmeeting(@RequestParam("meetingId") Integer meetingId, @RequestParam("canceledreason") String canceledreason,
                                      HttpSession httpSession, HttpServletRequest request,Map<String, Object> map){
        currentUser.setLoginUserMap(httpSession,request);
        final Meeting meeting = meetingService.getById(meetingId);
        final Meeting updateMeeting = new Meeting();
        updateMeeting.setMeetingid(meeting.getMeetingid());
        updateMeeting.setCanceledtime(new Date());
        updateMeeting.setCanceledreason(canceledreason);
        updateMeeting.setStatus(MeetingStatus.CANCEL_STATUS);
        final boolean res = meetingService.updateById(updateMeeting);
        if(res){
            map.put("msg","撤销会议成功！");
        }
        else {
            map.put("msg","撤销会议失败！");
        }
        map.put("meeting",meeting);
        return new ModelAndView("/my/cancelmeeting",map);
    }
}

