package com.yjj.mapper;

import com.yjj.bean.Student;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


@Repository
public interface StuMapper {
    void insertStudent(Student student);
}
