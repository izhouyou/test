package com.camelot.order.common.utils;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BeanUtil {

    //TODO: 测试
    public static <T> T copyPropertes(Class<T> clazz, Object from) {
        return JSON.parseObject(JSON.toJSONString(from), clazz);
    }

    public static <T> List<T> copyList(Class<T> clazz, List<?> from) {
        List<T> parseArray = JSON.parseArray(JSON.toJSONString(from), clazz);
        if (parseArray == null) {
            parseArray = new ArrayList<T>();
        }
        return parseArray;
    }
}
