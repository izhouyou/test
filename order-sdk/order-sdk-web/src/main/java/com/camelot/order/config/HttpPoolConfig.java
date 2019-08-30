/**
 * <p>Description: []</p>
 *
 * @ClassName HttpPoolConfig
 * Created on 2018/12/5.
 * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 */
package com.camelot.order.config;


import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * <p>Description: []</p>
 * @ClassName HttpPool
 * Created on 2018/12/4.
 *
 * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
 * @version 1.0
 *   北京柯莱特科技有限公司 易用云
 */
@Configuration
public class HttpPoolConfig {

    @Bean
    public HttpClient httpClient(){
        // 生成默認請求配置
        RequestConfig.Builder requestConfigBuilder = RequestConfig.custom();
        // 超時時間
        requestConfigBuilder.setSocketTimeout(5 * 1000);
        // 連接時間
        requestConfigBuilder.setConnectTimeout(5 * 1000);
        RequestConfig defaultRequestConfig = requestConfigBuilder.build();
        // 連接池配置
        // 長連接保持30秒
        final PoolingHttpClientConnectionManager pollingConnectionManager = new PoolingHttpClientConnectionManager(30, TimeUnit.MILLISECONDS);
        // 總連接數
        pollingConnectionManager.setMaxTotal(5000);
        // 同路由的併發數
        pollingConnectionManager.setDefaultMaxPerRoute(100);

        // httpclient 配置
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 保持長連接配置，需要在頭添加Keep-Alive
        httpClientBuilder.setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy());
        httpClientBuilder.setConnectionManager(pollingConnectionManager);
        httpClientBuilder.setDefaultRequestConfig(defaultRequestConfig);
        HttpClient client = httpClientBuilder.build();

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("feign-httpclent").daemon(true).build());

        // 啟動定時器，定時回收過期的連接
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                pollingConnectionManager.closeExpiredConnections();
                pollingConnectionManager.closeIdleConnections(5, TimeUnit.SECONDS);
            }
        }, 10 * 1000, 5 * 1000, TimeUnit.MILLISECONDS);

        System.out.println("===== Apache httpclient 初始化連接池===");

        return client;
    }


}