package com.camelot.order.common.utils;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>Title: ReflexObjectUtil.java</p>
 * <p>Description: 反射处理Bean 获取对象的属性值</p>
 * @author zhouyou
 * @date 2019年3月10日
 * @version 1.0
 */

public class ReflexObjectUtil {
	private static List<Object> packagingClass = new ArrayList<>();
    static {
        packagingClass.add(Byte.class);
        packagingClass.add(Short.class);
        packagingClass.add(Integer.class);
        packagingClass.add(Long.class);
        packagingClass.add(Float.class);
        packagingClass.add(Double.class);
        packagingClass.add(Character.class);
        packagingClass.add(Boolean.class);
        packagingClass.add(String.class);
    }
    /**
     * 将指定对象的属性 全部转换成 目标对象的String类型
     *
     * @param sourceObj 来源对象
     * @param desObj 目标对象
     * @return
     */
    public static Object copyObject(Object sourceObj, Object desObj) {
        // 获取来源类对象
        Class sourceObjClass = sourceObj.getClass();
        /* 来源类中的所有属性集合 */
        Field[] sourceObjClassDeclaredFields = sourceObjClass.getDeclaredFields();

        // 获取目标类对象
        Class desObjClass = desObj.getClass();
        /* 目标类中的所有属性集合 */
        Field[] desObjClassDeclaredFields = desObjClass.getDeclaredFields();

        for (int i = 0; i < sourceObjClassDeclaredFields.length; i++) {
            Field sourceObjClassDeclaredField = sourceObjClassDeclaredFields[i];
            // 设置属性可访问
            sourceObjClassDeclaredField.setAccessible(true);
            // 获取来源类的属性值
            Object sourceVal = null;
            try {
                sourceVal = sourceObjClassDeclaredField.get(sourceObj);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (null !=sourceVal) {
                for (int j = 0; j < desObjClassDeclaredFields.length; j++) {
                    try {
                        Field desObjClassDeclaredField = desObjClassDeclaredFields[j];
                        // 设置属性可访问
                        desObjClassDeclaredField.setAccessible(true);
                        // 属性名相同
                        if (desObjClassDeclaredField.getName().equals(sourceObjClassDeclaredField.getName())) {
                            // 获取来源类属性的对象类型
                            Class<?> type = sourceObjClassDeclaredField.getType();
                            // 如果是基本类型/包装类型/BigDecimal 直接转换为 String
                            if (type.isPrimitive() || packagingClass.contains(type)|| type.equals(BigDecimal.class)) {
                                sourceVal = String.valueOf(sourceVal);
                            } else if (type.equals(Date.class)) {
                                Date date = (Date) sourceVal;
                                sourceVal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
                            }
                            // 设置目标类的属性值
                            desObjClassDeclaredField.set(desObj, sourceVal);
                        }
                    } catch (IllegalArgumentException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return desObj;
    }
}
