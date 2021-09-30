package com.wang.crud.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wang.crud.bean.Employee;
import com.wang.crud.bean.Msg;
import com.wang.crud.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    EmployeeService employeeService;

    //@RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 5);
        List<Employee> emps = employeeService.getAll();
        PageInfo pageInfo = new PageInfo(emps, 5);
        model.addAttribute("page", pageInfo);
        return "list";
    }

    @ResponseBody
    @RequestMapping("/emps")
    public Msg getPagesWithJson(@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {
        PageHelper.startPage(pageNum, 5);
        List<Employee> emps = employeeService.getAll();
        PageInfo pages = new PageInfo(emps, 5);
        return Msg.success().add("pages", pages);
    }

    @RequestMapping(value = "/emp", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveEmp(Employee employee) {
        employeeService.saveEmp(employee);
        return Msg.success();
    }

    @ResponseBody
    @RequestMapping("/checkUser")
    public Msg checkUser(String empName) {
        Boolean b = employeeService.checkUser(empName);
        if (b) {
            return Msg.success();
        } else {
            return Msg.fail();
        }

    }

    @RequestMapping(value = "/emp/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id) {
        Employee employee = employeeService.getEmp(id);
        return Msg.success().add("emp", employee);
    }

    @RequestMapping(value = "/emp/{empId}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg updateEmp(Employee employee) {
        employeeService.updateEmp(employee);
        return Msg.success();

    }

    @ResponseBody
    @RequestMapping(value = "/emp/{ids}", method = RequestMethod.DELETE)
    public Msg deletEmp(@PathVariable("ids") String ids) {
        int res = 0;
        if (ids.contains("-")) {
            String[] strings = ids.split("-");
            ArrayList<Integer> list = new ArrayList<>();
            for (String s: strings){
                int i = Integer.parseInt(s);
                list.add(i);
            }
            res = employeeService.deletByIds(list);
        } else {
            int id = Integer.parseInt(ids);
            res = employeeService.deletById(id);
        }
        if (res != 0) {
            return Msg.success();
        } else {
            return Msg.fail();
        }

    }
}
