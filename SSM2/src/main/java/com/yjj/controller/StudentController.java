package com.yjj.controller;

import com.yjj.bean.Student;
import com.yjj.services.StuServiceImpl;
import com.yjj.services.StuServices;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class StudentController {

    @Autowired(required = false)
    private StuServices stuService;



    @RequestMapping(value = "/Regist",method = RequestMethod.POST)
    public String  insertStudent(Student student){
          stuService.insertStudent(student);
          return "forward:regist.html";
    }


}
