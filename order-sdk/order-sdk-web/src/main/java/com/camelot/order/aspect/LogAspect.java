/**
 * <p>Description: []</p>
 *
 * @ClassName LogAspect
 * Created on 2018/12/7.
 * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 */
package com.camelot.order.aspect;

import com.alibaba.fastjson.JSONObject;
import com.camelot.order.common.annotation.LogInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * <p>Description: [controller 日志]</p>
 * @ClassName ControllerLogAspect
 * Created on 2018/11/13.
 *
 * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 */
@Aspect
@Component
public class LogAspect {

    private Logger logger = LoggerFactory.getLogger(LogAspect.class);


    /**开始时间*/
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("@annotation(com.camelot.order.common.annotation.LogInfo)")
    public void log(){}

    @Before("log()")
    public void doBefore(JoinPoint joinPoint) throws Throwable{
        logger.info("===========================do Before start==================================");
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String operation = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazz = method.getParameterTypes();
                if (clazz.length == arguments.length) {
                    operation = method.getAnnotation(LogInfo.class).operation();
                    break;
                }
            }
        }
        StringBuilder paramsBuf = new StringBuilder();
        for (Object arg : arguments) {
            paramsBuf.append(arg);
            paramsBuf.append("&");
        }
        logger.info(request.getRequestURL().toString()+ "|" + targetName + "|" + methodName + "|" + paramsBuf.toString() + "|" + operation + "|" + (System.currentTimeMillis() - startTime.get()) + "ms");
        logger.info("===========================do Before end==================================");
    }

    @AfterReturning(pointcut = "log()", returning = "result")
    public void doAfterReturning(JoinPoint joinPoint, Object result) throws Throwable{
        logger.info("===========================do AfterReturning start==================================");
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class<?> targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String operation = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class<?>[] clazz = method.getParameterTypes();
                if (clazz.length == arguments.length) {
                    operation = method.getAnnotation(LogInfo.class).operation();
                    break;
                }
            }
        }
        StringBuilder paramsBuf = new StringBuilder();
        for (Object arg : arguments) {
            paramsBuf.append(arg);
            paramsBuf.append("&");
        }
        logger.info(request.getRequestURL().toString()+ "|" + targetName + "|" + methodName + "|" + paramsBuf.toString() + "|" + operation + "|" + JSONObject.toJSON(result) + "|" + (System.currentTimeMillis() - startTime.get()) + "ms");
        logger.info("===========================do AfterReturning end==================================");
    }
    @AfterThrowing(pointcut = "log()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        logger.info("===========================do AfterThrowing start==================================");
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        try {
            String targetName = joinPoint.getTarget().getClass().getName();
            String className = joinPoint.getTarget().getClass().getName();
            String methodName = joinPoint.getSignature().getName();
            Object[] arguments = joinPoint.getArgs();
            Class<?> targetClass = Class.forName(targetName);
            Method[] methods = targetClass.getMethods();
            String operation = "";
            for (Method method : methods) {
                if (method.getName().equals(methodName)) {
                    Class<?>[] clazz = method.getParameterTypes();
                    if (clazz.length == arguments.length) {
                        operation = method.getAnnotation(LogInfo.class).operation();
                        break;
                    }
                }
            }
            StringBuilder paramsBuf = new StringBuilder();
            for (Object arg : arguments) {
                paramsBuf.append(arg);
                paramsBuf.append("&");
            }

            logger.info(request.getRequestURL().toString()+ "|" + targetName + "|" + methodName + "|" + paramsBuf.toString() + "|" + operation + "|" + e.getMessage() + "|" + (System.currentTimeMillis() - startTime.get()) + "ms");
            logger.info("===========================do AfterThrowing start==================================");
        } catch (Exception ex) {
            logger.info(e.getMessage() + "|" + (System.currentTimeMillis() - startTime.get()));
            logger.info("===========================do AfterThrowing start==================================");
        }
    }

}