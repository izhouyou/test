package com.camelot.order.service.impl.export;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class RedisIDService {

    @Autowired
    RedisTemplate<String, Object> redisTemplate;

    private final String orderIdKey = "order";

    private final String returnOrderIdKey = "returnOrder";

    private final String receiptOrderIdKey = "receipt";

    private final String customerPrimaryId = "10";


    /**
     *@Description 获取销售订单序列号
     *@author xupengfei
     *@Date 2019年4月27日 
     * @return
     */
    public String getOrderId() {
        Long incr = getIncrease(orderIdKey, getCurrent2TodayEndMillisTime());
        if(incr==0) {
            incr = getIncrease(orderIdKey, getCurrent2TodayEndMillisTime());//从00001开始
        }
        DecimalFormat df=new DecimalFormat("00000");//五位序列号
        return df.format(incr);
    }
    /**
     *@Description 获取退货订单序列号
     *@author xupengfei
     *@Date 2019年4月27日 
     * @return
     */
    public String getReturnOrderId() {

        Long incr = getIncrease(returnOrderIdKey, getCurrent2TodayEndMillisTime());
        if(incr==0) {
            incr = getIncrease(returnOrderIdKey, getCurrent2TodayEndMillisTime());//从00001开始
        }
        DecimalFormat df=new DecimalFormat("00000");//五位序列号
        return df.format(incr);
    }
    /**
     *@Description 获取收款单序列号
     *@author xupengfei
     *@Date 2019年4月27日 
     * @return
     */
    public String getReceiptId() {

        Long incr = getIncrease(receiptOrderIdKey, getCurrent2TodayEndMillisTime());
        if(incr==0) {
            incr = getIncrease(receiptOrderIdKey, getCurrent2TodayEndMillisTime());//从00001开始
        }
        DecimalFormat df=new DecimalFormat("00000");//五位序列号
        return df.format(incr);
    }


    /**
     *@Description 每日凌晨0点更新
     *@author xupengfei
     *@Date 2019年4月27日 
     * @param key
     * @param liveTime
     * @return
     */
    public Long getIncrease(String key, long liveTime) {
        RedisAtomicLong entityIdCounter = new RedisAtomicLong(key, redisTemplate.getConnectionFactory());
        Long increment = entityIdCounter.getAndIncrement();

        if ((null == increment || increment.longValue() == 0) && liveTime > 0) {//初始设置过期时间
            entityIdCounter.expire(liveTime, TimeUnit.MILLISECONDS);//单位毫秒
        }
        return increment;
    }

    //计算现在到今天结束的毫秒数
    public Long getCurrent2TodayEndMillisTime() {
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTimeInMillis()-new Date().getTime();
    }

    public boolean set(String key, String value) {
        return true;
    }

    public Long getCustomerId() {
        Long incr = getIncrease(customerPrimaryId, getCurrent2TodayEndMillisTime());
        if(incr==0) {
            incr = getIncrease(customerPrimaryId, getCurrent2TodayEndMillisTime());//从00001开始
        }
        DecimalFormat df=new DecimalFormat("00000");//五位序列号
        String format = df.format(incr);
        return Long.valueOf(format);
    }


}
