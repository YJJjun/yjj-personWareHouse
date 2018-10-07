package com.yjj.service_Impl;

import com.yjj.pojo.TbItem;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class test {


    @Test
     public void selectItem(){

         ApplicationContext ac = new ClassPathXmlApplicationContext("spring/spring-mybatis.xml");
         ItemServiceImpl itemService = (ItemServiceImpl) ac.getBean("itemService");
         TbItem item = itemService.selectItemById(536563);
         System.out.println(item);

     }
}
