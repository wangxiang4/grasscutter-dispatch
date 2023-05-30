package com.tianli.dispatch.config

import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Configuration

@Configuration
@MapperScan("com.tianli.dispatch.mapper")
class MyBatisConfig
