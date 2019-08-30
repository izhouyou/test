//package com.camelot.order.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.EnableWebMvc;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
///**
// * <p>Description: []</p>
// * @ClassName CorsConfig
// * Created on 2018/11/19.
// *
// * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
// * @version 1.0
// *   北京柯莱特科技有限公司 易用云
// */
//@Configuration
//@EnableWebMvc
//public class CorsConfig implements WebMvcConfigurer{
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//                //设置允许跨域请求的域名
//                //放行哪些原始域
//                .allowedOrigins("*")
//                //是否发送Cookie信息
//                .allowCredentials(true)
//                //放行哪些原始域(请求方式)
////                .allowedMethods("OPTIONS", "HEAD", "GET", "POST", "PUT", "DELETE", "PATCH")
//                .allowedMethods("*")
//                //放行哪些原始域(头部信息)
//                .allowedHeaders("*")
//                //暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
//                //跨域允许时间
//                .maxAge(18000);
//    }
//
//
//}
