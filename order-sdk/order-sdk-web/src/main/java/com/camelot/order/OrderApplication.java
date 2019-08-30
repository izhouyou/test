package com.camelot.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@EnableFeignClients
//@SpringBootApplication
@EnableEurekaClient
@EnableSwagger2
@MapperScan("com.camelot.order.dao")
@EnableTransactionManagement
//@Import({RedisUtil.class, RedisTxUtil.class})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class OrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderApplication.class, args);
	}
}
