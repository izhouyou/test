package com.camelot.order.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>Description: [log日志工厂类]</p>
 */
public class Log {

    /**
     * <p>Discription:[获得Logger]</p>
     *
     * @param clazz 日志发出的类
     * @return
     */
    public static Logger get(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    /**
     * <p>Discription:[获得Logger]</p>
     *
     * @param name 自定义的日志发出者名称
     * @return
     */
    public static Logger get(String name) {
        return LoggerFactory.getLogger(name);
    }

    /**
     * <p>Discription:[获得日志，自动判定日志发出者]</p>
     *
     * @return
     */
    public static Logger get() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return LoggerFactory.getLogger(stackTrace[2].getClassName());
    }

    /**
     * <p>Discription:[Trace等级日志，小于debug--由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！]</p>
     *
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void trace(String format, Object... arguments) {
        trace(innerGet(), format, arguments);
    }

    /**
     * <p>Discription:[Trace等级日志，小于Debug]</p>
     *
     * @param log       日志对象
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void trace(Logger log, String format, Object... arguments) {
        log.trace(format, arguments);
    }

    /**
     * <p>Discription:[Debug等级日志，小于Info--由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！]</p>
     *
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void debug(String format, Object... arguments) {
        debug(innerGet(), format, arguments);
    }

    /**
     * <p>Discription:[Debug等级日志，小于Info]</p>
     *
     * @param log       日志对象
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void debug(Logger log, String format, Object... arguments) {
        log.debug(format, arguments);
    }

    /**
     * <p>Discription:[Info等级日志，小于Warn--由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！]</p>
     *
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void info(String format, Object... arguments) {
        info(innerGet(), format, arguments);
    }

    /**
     * <p>Discription:[Info等级日志，小于Warn]</p>
     *
     * @param log       日志对象
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void info(Logger log, String format, Object... arguments) {
        log.info(format, arguments);
    }

    /**
     * <p>Discription:[Warn等级日志，小于Error--由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！]</p>
     *
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void warn(String format, Object... arguments) {
        warn(innerGet(), format, arguments);
    }

    /**
     * <p>Discription:[Warn等级日志，小于Error]</p>
     *
     * @param log       日志对象
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void warn(Logger log, String format, Object... arguments) {
        log.warn(format, arguments);
    }

    /**
     * <p>Discription:[Warn等级日志，小于Error--由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！]</p>
     *
     * @param e         需在日志中堆栈打印的异常
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void warn(Throwable e, String format, Object... arguments) {
        warn(innerGet(), e, format(format, arguments));
    }

    /**
     * <p>Discription:[Warn等级日志，小于Error]</p>
     *
     * @param log       日志对象
     * @param e         需在日志中堆栈打印的异常
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void warn(Logger log, Throwable e, String format, Object... arguments) {
        log.warn(format(format, arguments), e);
    }

    /**
     * <p>Discription:[Error等级日志--由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！]</p>
     *
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void error(String format, Object... arguments) {
        error(innerGet(), format, arguments);
    }

    /**
     * <p>Discription:[Error等级日志]</p>
     *
     * @param log       日志对象
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void error(Logger log, String format, Object... arguments) {
        log.error(format, arguments);
    }

    /**
     * <p>Discription:[Error等级日志--由于动态获取Logger，效率较低，建议在非频繁调用的情况下使用！！]</p>
     *
     * @param e         需在日志中堆栈打印的异常
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void error(Throwable e, String format, Object... arguments) {
        error(innerGet(), e, format(format, arguments));
    }

    /**
     * <p>Discription:[Error等级日志 ]</p>
     *
     * @param log       日志对象
     * @param e         需在日志中堆栈打印的异常
     * @param format    格式文本，{} 代表变量
     * @param arguments 变量对应的参数
     */
    public static void error(Logger log, Throwable e, String format, Object... arguments) {
        log.error(format(format, arguments), e);
    }

    /**
     * <p>Discription:[格式化文本]</p>
     *
     * @param template 文本模板，被替换的部分用 {} 表示
     * @param values   参数值
     * @return 格式化后的文本
     */
    private static String format(String template, Object... values) {
        return String.format(template.replace("{}", "%s"), values);
    }

    /**
     * <p>Discription:[获得日志，自动判定日志发出者]</p>
     *
     * @return
     */
    private static Logger innerGet() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        return LoggerFactory.getLogger(stackTrace[3].getClassName());
    }

}

