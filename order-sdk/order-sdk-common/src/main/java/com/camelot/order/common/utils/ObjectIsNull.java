/**
 * <p>Description: []</p>
 *
 * @ClassName ObjectIsNull
 * Created on 2018/11/30.
 * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 */
package com.camelot.order.common.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;

/**
 * <p>Description: []</p>
 * @ClassName ObjectIsNull
 * Created on 2018/11/30.
 *
 * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
 * @version 1.0
 *   北京柯莱特科技有限公司 易用云
 */
public class ObjectIsNull {
    public static boolean checkObjFieldIsNull(Object obj) throws IllegalAccessException {
        for(Field f : obj.getClass().getDeclaredFields()){
            f.setAccessible(true);
            //这里忽略static final 类型的属性，如若不需要可以去掉
            if(Modifier.isFinal(f.getModifiers())&&Modifier.isStatic(f.getModifiers())){
                continue;
            }
            if(!isEmpty(f.get(obj))){
                return false;
            }
            f.setAccessible(false);
        }
        return true;
    }
    //判断是否为空
    public static boolean isEmpty(Object obj) {
        if (obj == null) {
            return true;

        } else if (obj instanceof String && (obj.toString().trim().equals(""))) {
            return true;

        } else if (obj instanceof Number && ((Number) obj).doubleValue() < 0) {
            return true;

        } else if (obj instanceof Collection && ((Collection) obj).isEmpty()) {
            return true;

        } else if (obj instanceof Map && ((Map) obj).isEmpty()) {
            return true;

        } else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
            return true;

        }
        return false;
    }

}
