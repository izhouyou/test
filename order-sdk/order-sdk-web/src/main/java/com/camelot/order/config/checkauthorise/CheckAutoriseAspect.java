package com.camelot.order.config.checkauthorise;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.AjaxInfo;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.export.vo.param.BaseVO;
import com.camelot.order.feign.config.FeignStoreService;

@Aspect
@Component
public class CheckAutoriseAspect {
	
	//本地异常日志记录对象    
    private static final Logger logger = LoggerFactory.getLogger(CheckAutoriseAspect.class);
    
    @Autowired
    private FeignStoreService feignStoreService;
    @Autowired
	RedisTemplate<String, Object> redisTemplate;
   
    
    //Controller层切点    
    // @Pointcut("execution(* com.camelot.order.api..*.*(..))")
    @Pointcut("@annotation(UserAuthoriseController)")
    public void controllerAspect() {
    }
    
    @Around("controllerAspect()")
    public Object doBefore(ProceedingJoinPoint  joinPoint) throws Throwable {
    	HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    	try {
    		// userId
    		String userId = request.getHeader("userId");
    		if(Utility.isEmpty(userId)) {
    			logger.error("异常信息:{}","请求头中数据为空");
    			return joinPoint.proceed();
    		}
            // Redis载体初始化
	    	ValueOperations<String, Object> opsForValue = redisTemplate.opsForValue();
	    	// 获取数据
	    	Object jsonData = opsForValue.get(userId);
	    	if(Utility.isEmpty(jsonData)) {
		    	// 调用sysbase服务获取用户权限
		    	AjaxInfo ajaxInfo = feignStoreService.getDataPermission(Long.valueOf(userId));
		    	Object data = ajaxInfo.getData();
		    	if(Utility.isNotEmpty(data)) {
		    		BaseVO feginBaseVO = JSONObject.parseObject(JSONObject.toJSONString(data), BaseVO.class);
		    		String orgStr = feginBaseVO.getOrgStr();
		    		String storeStr = feginBaseVO.getStoreStr();
		    		Object[] args = joinPoint.getArgs();//参数
		    		if(Utility.isNotEmpty(orgStr) || Utility.isNotEmpty(storeStr)) {
		    			for(Object arg : args) {
			                if(arg instanceof BaseVO) {
			                	BaseVO vo = (BaseVO)arg;
			                	vo.setOrgStr(feginBaseVO.getOrgStr());
			                	vo.setStoreStr(feginBaseVO.getStoreStr());
			                }
		    			}
		    			return joinPoint.proceed();
		    		}
		    	} else {
		    		logger.error("异常信息:{}","调用sysbase服务获取用户权限数据失败");
		    		return joinPoint.proceed();
		    	}
	    	} else {
	    		BaseVO feginBaseVO = JSONObject.parseObject(jsonData.toString(), BaseVO.class);
	    		String orgStr = feginBaseVO.getOrgStr();
	    		String storeStr = feginBaseVO.getStoreStr();
	    		Object[] args = joinPoint.getArgs();//参数
	    		if(Utility.isNotEmpty(orgStr) || Utility.isNotEmpty(storeStr)) {
	    			for(Object arg : args) {
		                if(arg instanceof BaseVO) {
		                	BaseVO vo = (BaseVO)arg;
		                	vo.setOrgStr(feginBaseVO.getOrgStr());
		                	vo.setStoreStr(feginBaseVO.getStoreStr());
		                }
	    			}
	    			return joinPoint.proceed();
	    		} else {
	    			logger.error("异常信息:{}",userId + ":redis内数据为空");
	    		}
	    	}
    	} catch (Exception e) {
            //记录本地异常日志    
            logger.error("异常信息:{}", e.toString());
            return joinPoint.proceed();
        }
		return joinPoint.proceed();
    }

}
