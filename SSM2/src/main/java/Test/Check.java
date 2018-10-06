package Test;

import com.yjj.bean.Student;
import com.yjj.controller.StudentController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Check {
    public static void main(String[] args){
        ApplicationContext ac = new ClassPathXmlApplicationContext("Spring-Mybatis.xml");
        StudentController sc = (StudentController) ac.getBean("studentController2");
        Student student = new Student("yjjxxxx",111);
        sc.insertStudent(student);
    }
}
