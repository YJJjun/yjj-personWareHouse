#### ssm框架整合

##### spring springmvc mybatis

##### 一：创建maven工程

##### 二：导入必须的依赖包

~~~xml
<properties>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <junit.version>4.12</junit.version>
    <spring.version>4.3.14.RELEASE</spring.version>
    <commons-dbcp.version>1.2.2</commons-dbcp.version>
    <commons-pool.version>1.5.4</commons-pool.version>
    <mysql.version>5.1.38</mysql.version>
    <mybatis.version>3.4.1</mybatis.version>
    <mybatis-spring.version>1.3.1</mybatis-spring.version>
  </properties>

  <dependencies>
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>${junit.version}</version>
      <scope>test</scope>
    </dependency>
    <dependency>
      <groupId>commons-dbcp</groupId>
      <artifactId>commons-dbcp</artifactId>
      <version>${commons-dbcp.version}</version>
    </dependency>
    <dependency>
      <groupId>commons-pool</groupId>
      <artifactId>commons-pool</artifactId>
      <version>${commons-pool.version}</version>
    </dependency>
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>${mysql.version}</version>
    </dependency>
    <!-- spring jar -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-orm</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <!-- spring用到的相关 jar -->
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.8.4</version>
    </dependency>
    <!-- 引入mybatis核心包 -->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>${mybatis.version}</version>
    </dependency>
    <!-- 引入spring集成mybatis的包 -->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>${mybatis-spring.version}</version>
    </dependency>
    <!-- spring测试框架 -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-test</artifactId>
      <version>${spring.version}</version>
      <scope>test</scope>
    </dependency>

    <!-- servlet jar包 -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>jstl</artifactId>
      <version>1.2</version>
    </dependency>
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>javax.servlet-api</artifactId>
      <version>3.1.0</version>
      <scope>provided</scope>
    </dependency>
    <!-- el表达式 -->
    <dependency>
      <groupId>taglibs</groupId>
      <artifactId>standard</artifactId>
      <version>1.1.2</version>
    </dependency>
    <!-- log4J日志管理 -->
    <dependency>
      <groupId>log4j</groupId>
      <artifactId>log4j</artifactId>
      <version>1.2.17</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.6.1</version>
    </dependency>
  </dependencies>
~~~



##### 三：完善工程目录结构以及对应权限

![1531793314080](E:\gg15\1531793314080.png)

#### 四：编写web.xml文件

~~~xml
<web-app>
  <display-name>Archetype Created Web Application</display-name>
  <!--配置容器-->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>
      classpath:spring-mybatis.xml,
      classpath:spring-tx.xml
    </param-value>
  </context-param>


  <!--解决乱码-->
  <filter>
    <filter-name>SetCharacterEncoding</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>SetCharacterEncoding</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>


  <!--spring context 监听器-->
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>

  <!--spring mvc-->
  <servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:spring-mvc.xml</param-value>
    </init-param>
  </servlet>
  <servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>*.do</url-pattern>
  </servlet-mapping>

  <!--欢迎页面-->
</web-app>
~~~

#### 五：编写spring-mybatis.xml配置文件

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/context
        http://www.springframework.org/schema/context/spring-context.xsd">

<!--扫描spring注解的包-->
    <context:component-scan base-package="net.togogo"/>
    
    <!--读取链接数据库的配置信息-->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    
    <!--配置数据源-->
    <bean id="dataSource" class="org.apache.commons.dbcp.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}"></property>
        <property name="url" value="${jdbc.url}"></property>
        <property name="username" value="${jdbc.username}"></property>
        <property name="password" value="${jdbc.password}"></property>
    </bean>

    <!--构建sqlsessionfactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="dataSource" ref="dataSource"></property>
        <property name="mapperLocations" value="classpath:/mapper/*Mapper.xml"></property>
    </bean>
    
    <!--向spring容器注入mapper类对象-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="net.togogo.mapper"></property>
    </bean>
</beans>
~~~

#### 六：配置spring-tx.xml配置文件

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
	http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
	http://www.springframework.org/schema/aop
	http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
	http://www.springframework.org/schema/tx
	http://www.springframework.org/schema/tx/spring-tx-3.0.xsd">


    <!--事务管理器-->
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource"/>
    </bean>

    <!-- 配置事务特性 -->
    <tx:advice id="tx" transaction-manager="transactionManager">
        <tx:attributes>
            <tx:method name="insert*" propagation="REQUIRED" />
            <tx:method name="save*" propagation="REQUIRED" />
            <tx:method name="update*" propagation="REQUIRED" />
            <tx:method name="delete*" propagation="REQUIRED" />
            <tx:method name="do*" propagation="REQUIRED" />
            <tx:method name="get*" read-only="true" />
            <tx:method name="find*" read-only="true" />
            <tx:method name="list*" read-only="true" />
        </tx:attributes>
    </tx:advice>

    <!--配置切面关系-->
    <aop:config>
        <!--获取需要拦截的类-->
        <aop:pointcut id="txcut" expression="(*net.togogo.service.*Service.*(..))"/>
        <!--切面管理-->
        <aop:advisor advice-ref="tx" pointcut-ref="txcut"></aop:advisor>
    </aop:config>
</beans>
~~~

#### 七：编写jdbc.properties配置文件

~~~properties
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/gg15?useUnicode=true&characterEncoding=utf8&autoReconnect=true
jdbc.username=root
jdbc.password=123456
~~~

#### 八：编写与数据库对应的实体类

~~~java
package net.togogo.bean;

public class Stu {
    private int id;
    private String name;
    private String password;
    private String role;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Stu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

~~~

#### 九：编写与实体对应的接口

~~~java
package net.togogo.mapper;

import net.togogo.bean.Stu;
import org.springframework.stereotype.Repository;

@Repository
public interface StuMapper {
    //注册 insert
    void insert(Stu stu);
}
~~~

#### 十：编写映射文件StuMapper.xml

~~~xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper
        PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-mapper.dtd">

<!--配置命名空间-->
<mapper namespace="net.togogo.mapper.StuMapper">
    <insert id="insert" parameterType="net.togogo.bean.Stu">
        insert into t_stu
        (name, password, role)values (
        #{name},#{password},#{role}
        );
    </insert>
</mapper>
~~~



#### 十一：编写StuService

~~~java
package net.togogo.service;

import net.togogo.bean.Stu;

public interface StuService {

    //注册 insert
    void insert(Stu stu);
}
~~~

#### 十二：编写StuServiceImpl

~~~java
package net.togogo.service.impl;

import net.togogo.bean.Stu;
import net.togogo.mapper.StuMapper;
import net.togogo.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    public void insert(Stu stu) {
        stuMapper.insert(stu);
    }
}
~~~

#### 十三：编写StuController.java

~~~java
package net.togogo.controller;

import net.togogo.bean.Stu;
import net.togogo.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class StuController {

    @Autowired( required = false)
    private StuService stuService;

    /*注册*/
    //localhost:8080/ssm/save.do
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(Stu stu){
        stuService.insert(stu);
        return "/index.jsp";
    }
}
~~~



#### 十四：编写regist.html文件

~~~html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>注册</title>
</head>
<body>
    <form action="/ssm/save.do" method="post">
        username:<input type="text" name="name" placeholder="请输入用户名"><br>
        userpowd:<input type="password" name="password" placeholder="请输入密码"><br>
        userrole:<input type="text" name="role" placeholder="请输入您的背景"><br>
        <input type="submit" value="注册">
    </form>
</body>
</html>
~~~

#### 十五：编写日志文件log4j.properties

~~~log
log4j.rootLogger=info, console, debug, app, error
  
###Console ###  
log4j.appender.console = org.apache.log4j.ConsoleAppender
log4j.appender.console.Target = System.out
log4j.appender.console.layout = org.apache.log4j.PatternLayout
log4j.appender.console.layout.ConversionPattern = %d %p[%C:%L]- %m%n
  
### debug ###    
log4j.appender.debug = org.apache.log4j.DailyRollingFileAppender
log4j.appender.debug.File = log/debug.log
log4j.appender.debug.Append = true
log4j.appender.debug.Threshold = DEBUG
log4j.appender.debug.DatePattern='.'yyyy-MM-dd
log4j.appender.debug.layout = org.apache.log4j.PatternLayout
log4j.appender.debug.layout.ConversionPattern = %d %p[%c:%L] - %m%n
  
### app ###    
log4j.appender.app = org.apache.log4j.DailyRollingFileAppender
log4j.appender.app.File = log/app.log
log4j.appender.app.Append = true
log4j.appender.app.Threshold = INFO
log4j.appender.app.DatePattern='.'yyyy-MM-dd
log4j.appender.app.layout = org.apache.log4j.PatternLayout
log4j.appender.app.layout.ConversionPattern = %d %p[%c:%L] - %m%n
  
### Error ###  
log4j.appender.error = org.apache.log4j.DailyRollingFileAppender
log4j.appender.error.File = log/error.log
log4j.appender.error.Append = true
log4j.appender.error.Threshold = ERROR
log4j.appender.error.DatePattern='.'yyyy-MM-dd
log4j.appender.error.layout = org.apache.log4j.PatternLayout
log4j.appender.error.layout.ConversionPattern =%d %p[%c:%L] - %m%n

~~~

