package com.ago.masterslaves;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,MybatisAutoConfiguration.class})
//@ComponentScan("com.ago.masterslaves.*")
@MapperScan(value = "com.ago.masterslaves.mapper")
@EnableConfigurationProperties
public class MasterSlavesApplication {
    public static void main(String[] args) {
        SpringApplication.run(MasterSlavesApplication.class, args);
    }
}
