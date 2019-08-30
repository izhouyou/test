package com.camelot.order.config.elasticConfig;

import com.alibaba.fastjson.JSONObject;
import com.camelot.order.common.utils.IpUtil;
import com.camelot.order.export.vo.elasticsearch.OperationLog;

import javassist.*;
import javassist.bytecode.CodeAttribute;
import javassist.bytecode.LocalVariableAttribute;
import javassist.bytecode.MethodInfo;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


@Aspect
@Component
public class SystemLogAspect {

    //本地异常日志记录对象    
    private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

    //切入到操作日志数据,来源于spring-logback.xml
    private static final Logger oper_logger = LoggerFactory.getLogger("oper_file");

    //Service层切点    
    @Pointcut("@annotation(com.camelot.order.config.elasticConfig.SystemServiceLog)")
    public void serviceAspect() {

    }

    //Controller层切点    
    // @Pointcut("execution(* com.camelot.order.api..*.*(..))")
    @Pointcut("@annotation(com.camelot.order.config.elasticConfig.SystemControllerLog)")
    public void controllerAspect() {
    }

    /**
     * 前置通知 用于拦截Controller层记录用户的操作
     *
     * @param joinPoint 切点
     */
    @Before("controllerAspect()")
    public void doBefore(JoinPoint joinPoint) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //请求的IP
        String ip = IpUtil.getRealIP(request);
        try {

            String classType = joinPoint.getTarget().getClass().getName();
            Class<?> clazz = Class.forName(classType);
            String clazzName = clazz.getName();
            String methodName = joinPoint.getSignature().getName(); //获取方法名称
            Object[] args = joinPoint.getArgs();//参数
            Map<String, Object> nameAndArgs = getFieldsName(this.getClass(), clazzName, methodName, args);//获取被切参数名称及参数值
//            Object info = nameAndArgs.get("activityJson");
//            String s = JSONObject.toJSONString(info);
//            OperationLog operationLog = JSONObject.parseObject(info.toString(), OperationLog.class);
            OperationLog operationLog = JSONObject.parseArray(JSONObject.toJSONString(args),OperationLog.class).get(0);
            
            // 获取操作数据
            Date date = new Date();

            operationLog.setCreateTime(date);
            // operationLog.setOperationDescription(joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()");//请求方法
            operationLog.setBusinessDescription(getControllerMethodDescription(joinPoint));// 方法描述
            operationLog.setIp(ip);// TODO: IP地址
            String jsonData = JSONObject.toJSONString(operationLog);

            // 存储数据到指定的log文件夹下来源于spring-logback.xml
            oper_logger.info(jsonData);

        } catch (Exception e) {
            //记录本地异常日志    
            logger.error("异常信息:{}", e.getMessage());
        }
    }

    /**
     * 异常通知 用于拦截service层记录异常日志
     *
     * @param joinPoint
     * @param e
     */
    @AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        //读取session中的用户
        //HttpSession session = request.getSession();
        // User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
        //获取请求ip    
        String ip = request.getRemoteAddr();
        //获取用户请求方法的参数并序列化为JSON格式字符串    
        String params = "";
        if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
            for (int i = 0; i < joinPoint.getArgs().length; i++) {
                params += JSONObject.toJSONString(joinPoint.getArgs()[i]) + ";";
            }
        }
        try {
            logger.error("异常代码:" + e.getClass().getName());
            logger.error("异常信息:" + e.getMessage());
            logger.error("异常方法:" + (joinPoint.getTarget().getClass().getName() + "." + joinPoint.getSignature().getName() + "()"));
            logger.error("方法描述:" + getServiceMthodDescription(joinPoint));
            logger.error("请求IP:" + ip);
            logger.error("请求参数:" + params);
        } catch (Exception ex) {
            logger.error("异常信息:{}", ex.getMessage());
        }
        logger.error("异常方法:{}异常代码:{}异常信息:{}参数:{}", joinPoint.getTarget().getClass().getName() + joinPoint.getSignature().getName(), e.getClass().getName(), e.getMessage(), params);

    }


    /**
     * 获取注解中对方法的描述信息 用于service层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemServiceLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 获取注解中对方法的描述信息 用于Controller层注解
     *
     * @param joinPoint 切点
     * @return 方法描述
     * @throws Exception
     */
    public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
        String targetName = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        Object[] arguments = joinPoint.getArgs();
        Class targetClass = Class.forName(targetName);
        Method[] methods = targetClass.getMethods();
        String description = "";
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                Class[] clazzs = method.getParameterTypes();
                if (clazzs.length == arguments.length) {
                    description = method.getAnnotation(SystemControllerLog.class).description();
                    break;
                }
            }
        }
        return description;
    }

    /**
     * 通过反射机制 获取被切参数名以及参数值
     *
     * @param cls
     * @param clazzName
     * @param methodName
     * @param args
     * @return
     * @throws NotFoundException
     */
    private Map<String, Object> getFieldsName(Class cls, String clazzName, String methodName, Object[] args) throws NotFoundException {
        Map<String, Object> map = new HashMap<String, Object>();

        ClassPool pool = ClassPool.getDefault();
        //ClassClassPath classPath = new ClassClassPath(this.getClass());
        ClassClassPath classPath = new ClassClassPath(cls);
        pool.insertClassPath(classPath);

        CtClass cc = pool.get(clazzName);
        CtMethod cm = cc.getDeclaredMethod(methodName);
        MethodInfo methodInfo = cm.getMethodInfo();
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        LocalVariableAttribute attr = (LocalVariableAttribute) codeAttribute.getAttribute(LocalVariableAttribute.tag);
        if (attr == null) {
            // exception
        }
        // String[] paramNames = new String[cm.getParameterTypes().length];
        int pos = Modifier.isStatic(cm.getModifiers()) ? 0 : 1;
        for (int i = 0; i < cm.getParameterTypes().length; i++) {
            map.put(attr.variableName(i + pos), args[i]);//paramNames即参数名
        }
        return map;
    }



}