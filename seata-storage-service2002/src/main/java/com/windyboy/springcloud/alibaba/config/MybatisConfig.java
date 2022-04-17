package com.windyboy.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.windyboy.springcloud.alibaba.dao")
public class MybatisConfig {
}
