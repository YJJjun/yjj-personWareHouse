package com.yjj.services;

import com.yjj.bean.Student;
import com.yjj.mapper.StuMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StuServiceImpl implements StuServices {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public void insertStudent(Student student) {
        stuMapper.insertStudent(student);
    }
}
