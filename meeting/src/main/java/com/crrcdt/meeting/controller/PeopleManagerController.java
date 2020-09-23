package com.crrcdt.meeting.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.crrcdt.meeting.constant.UserStatus;
import com.crrcdt.meeting.entity.ConditionVo;
import com.crrcdt.meeting.entity.Department;
import com.crrcdt.meeting.entity.Employee;
import com.crrcdt.meeting.entity.Role;
import com.crrcdt.meeting.service.DepartmentService;
import com.crrcdt.meeting.service.EmployeeService;
import com.crrcdt.meeting.service.RoleService;
import com.crrcdt.meeting.utils.currentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * 人员管理模块
 * @author lj on 2020/9/21.
 * @version 1.0
 */
@Controller
@RequestMapping("/peopleManager")
public class PeopleManagerController {

    @Autowired
    DepartmentService departmentService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    RoleService roleService;

    /**
     * 跳转到注册页面
     * @param httpSession
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/register")
    public ModelAndView toRegister(HttpSession httpSession, HttpServletRequest request,
                                   Map<String, Object> map){
        currentUser.setLoginUserMap(httpSession,request);
        final List<Department> departments = departmentService.list(null);
        map.put("deps",departments);
        final List<Role> roles = roleService.list(null);
        map.put("roles",roles);
        return new ModelAndView("/employee/register",map);
    }
    @PostMapping("/doReg")
    public ModelAndView toDoRegister(Employee employee, HttpSession httpSession, HttpServletRequest request,
                                     Map<String, Object> map){
        currentUser.setLoginUserMap(httpSession,request);
        final boolean res = employeeService.save(employee);
        if(res){
            map.put("msg","添加成员成功！等待管理源审批");
        }
       else {
            map.put("msg","添加成员失败！");
        }
        return new ModelAndView("/employee/register",map);
    }

    /**
     * 跳转到部门页面
     * @param map
     * @return
     */
    @RequestMapping("/departments")
    public ModelAndView toDepartments( Map<String, Object> map){
        final List<Department> departments = departmentService.list(null);
        map.put("deps",departments);
        return new ModelAndView("/employee/departments",map);
    }

    @ResponseBody
    @RequestMapping("/updateDepartments")
    public String updateDepartments(@RequestParam("id") Integer depId,@RequestParam("name") String depName){
        final Department department = new Department();
        department.setDepartmentid(depId);
        department.setDepartmentname(depName);
        final boolean res = departmentService.updateById(department);
        if(res){
            return "success";
        }
            return "fail";
        }

    @RequestMapping("/addDepartments")
    public ModelAndView updateDepartments(@RequestParam("departmentname") String departmentname, Map<String, Object> map){
        final List<Department> departments = departmentService.list(null);
        if(departments.contains(departmentname)){
            map.put("msg","新增失败！该部门已经存在！");
            return new ModelAndView("/employee/departments",map);
        }
        final Department department = new Department();
        department.setDepartmentname(departmentname);
        final boolean res = departmentService.save(department);
        if(res){
            map.put("msg","新增成功！");
        }else {
            map.put("msg","新增失败！");
        }
        final List<Department> departmentList = departmentService.list(null);
        map.put("deps",departmentList);
        return new ModelAndView("/employee/departments",map);
    }
    @RequestMapping("/deleteDepartments")
    public ModelAndView deleteDepartments(@RequestParam("departmentid") Integer departmentid,Map<String, Object> map){
        final boolean res = departmentService.removeById(departmentid);
        if(res){
            map.put("msg","删除成功！");
        }else {
            map.put("msg","删除失败！");
        }
        final List<Department> departmentList = departmentService.list(null);
        map.put("deps",departmentList);
        return new ModelAndView("/employee/departments",map);
    }

    /**
     * 跳转到审核用户页面
     * @param map
     * @return
     */
    @RequestMapping("/approveaccount")
    public ModelAndView toApproveaccount(Map<String, Object> map){
        final List<Employee> employees = employeeService.list(new QueryWrapper<Employee>().eq("status", UserStatus.UNCHECK_USER));
        map.put("emps",employees);
        return new ModelAndView("/employee/approveaccount",map);
    }
    /**
     * 审核用户页面
     * @param map
     * @return
     */
    @RequestMapping("/updatestatus")
    public ModelAndView updatestatus(Employee employee,Map<String, Object> map,HttpServletRequest request,HttpSession session){
        final Employee currentEmployee = currentUser.getCurrentEmployee(request);
        map.put("employee",currentEmployee);
        final boolean res = employeeService.updateById(employee);
        if(res){
            map.put("msg","处理成功！");
        }
        final List<Employee> employees = employeeService.list(new QueryWrapper<Employee>().eq("status", UserStatus.UNCHECK_USER));
        map.put("emps",employees);
        return new ModelAndView("/employee/approveaccount",map);
    }

    /**
     * 跳转到搜索员工页面
     * @param page
     * @param size
     * @param map
     * @return
     */
    @RequestMapping("/searchemployees")
    public ModelAndView toSearchemployees(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                          @RequestParam(value = "size", defaultValue = "5") Integer size,
                                          Map<String, Object> map){
        final ConditionVo conditionVo = new ConditionVo("", "", "1");
        map.put("condition",conditionVo);
        final Page<Employee> employeePage = new Page<>(page,size);
        final IPage<Employee> employeeIPage = employeeService.page(employeePage, new QueryWrapper<Employee>().eq("status","1"));
        map.put("emps",employeeIPage.getRecords());
        map.put("currentPage", page);
        map.put("size", size);
        map.put("totalPage", (employeeIPage.getTotal() % size)== 0 ? (employeeIPage.getTotal() / size) : (employeeIPage.getTotal() / size)+1  );
        return new ModelAndView("/employee/searchemployees",map);
    }
    @RequestMapping("/searchempByCondition")
    public ModelAndView searchempByCondition(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                             @RequestParam(value = "size", defaultValue = "5") Integer size,
                                             @RequestParam("employeename") String employeename,
                                             @RequestParam("username") String username,
                                             @RequestParam("status") String status,
                                             Map<String, Object> map,HttpSession session){
        //判断seesion中有信息？

        final String msg = (String) session.getAttribute("msg");
        if(msg!=null){
            map.put("msg","关闭账号成功!!!");
            session.removeAttribute("msg");
        }
        final ConditionVo conditionVo = new ConditionVo();
        conditionVo.setEmployeename(employeename);
        conditionVo.setStatus(status);
        conditionVo.setUsername(username);
        map.put("condition",conditionVo);
        final Page<Employee> employeePage = new Page<>(page,size);
        final IPage<Employee> employeeIPage = employeeService.page(employeePage, new QueryWrapper<Employee>().like("employeename",employeename)
                .like("username",username).eq("status",status));
        map.put("emps",employeeIPage.getRecords());
        map.put("currentPage", page);
        map.put("size", size);
        map.put("totalPage", (employeeIPage.getTotal() % size)== 0 ? (employeeIPage.getTotal() / size) : (employeeIPage.getTotal() / size)+1 );
        return new ModelAndView("/employee/searchemployees",map);
    }

    @RequestMapping("/updateemp")
    public String updateemp(@RequestParam("username")String username,
                            @RequestParam("employeename")String employeename,
                            @RequestParam("id") Integer empId,HttpSession session){
        final Employee employee = new Employee();
        employee.setEmployeeid(empId);
        employee.setStatus(UserStatus.CHECK_PASS_FAIL);
        final boolean res = employeeService.updateById(employee);
        if(res){
            session.setAttribute("msg","关闭账号成功！");
        }
        else {
            session.setAttribute("msg","关闭账号失败！");
        }
        return "redirect:/peopleManager/searchempByCondition?status=1&username="+username+"&employeename="+employeename;
}
}

