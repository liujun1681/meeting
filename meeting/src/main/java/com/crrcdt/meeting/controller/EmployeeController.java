package com.crrcdt.meeting.controller;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
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
                                     HttpServletRequest request,Map<String, Object> map){
        //先得到我预定会议的ids
        final Employee employee = currentUser.getCurrentEmployee(request);
        final Page<Meeting> picturePage = new Page<>(page,size);
        final IPage<Meeting> meetingIPage = meetingService.page(picturePage, new QueryWrapper<Meeting>().eq("reservationistid", employee.getEmployeeid()));
        meetingIPage.getRecords().stream().forEach(meeting -> {
            final String roomname = meetingroomService.list(new QueryWrapper<Meetingroom>().eq("roomid", meeting.getRoomid())).get(0).getRoomname();
            meeting.setRoomName(roomname);
        });
        map.put("meetings",meetingIPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("totalPage", (meetingIPage.getTotal() / size) );
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
                                     @RequestParam(value = "size", defaultValue = "3") Integer size,
                                     HttpServletRequest request,Map<String, Object> map){
        //先得到我要参加的会议的ids
        final Employee currentEmployee = currentUser.getCurrentEmployee(request);
        final List<Meetingparticipants> meetingparticipants = meetingparticipantsService.list(new QueryWrapper<Meetingparticipants>().eq("employeeid", currentEmployee.getEmployeeid()));
        final List<Integer> meetingIds = meetingparticipants.stream().map(m -> m.getMeetingid()).distinct().collect(Collectors.toList());
        final Page<Meeting> picturePage = new Page<>(page,size);
        final IPage<Meeting> meetingIPage = meetingService.page(picturePage, new QueryWrapper<Meeting>().in("meetingid", meetingIds).eq("status", MeetingStatus.START_STATUS));
        meetingIPage.getRecords().stream().forEach(meeting -> {
            final String roomname = meetingroomService.list(new QueryWrapper<Meetingroom>().eq("roomid", meeting.getRoomid())).get(0).getRoomname();
            final List<Employee> employeeList = employeeService.list(new QueryWrapper<Employee>().eq("employeeid", meeting.getReservationistid()));
            meeting.setReservationistName(employeeList.get(0).getEmployeename());
            meeting.setRoomName(roomname);
        });
        map.put("meetings",meetingIPage);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("totalPage", (meetingIPage.getTotal() / size) );
        return new ModelAndView( "/my/mymeetings",map);
    }

}

