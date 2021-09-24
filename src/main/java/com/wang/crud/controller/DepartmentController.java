package com.wang.crud.controller;

import com.wang.crud.bean.Msg;
import com.wang.crud.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @ResponseBody
    @RequestMapping("/depts")
    public Msg getDept(){
       List list= departmentService.getDepts();
     return Msg.success().add("depts",list);
    }
}
