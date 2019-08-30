/**
 * <p>Description: []</p>
 *
 * @ClassName ApiLog
 * Created on 2018/12/7.
 * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 */
package com.camelot.order.common.annotation;

        import java.lang.annotation.*;

/**
 * <p>Description: []</p>
 * @ClassName ApiLog
 * Created on 2018/12/7.
 *
 * @author <a href="mailto: sunxianwei@camelotchina.com">sunxiaozhe</a>
 * @version 1.0
 *   北京柯莱特科技有限公司 易用云
 */
@Target({ ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogInfo {
    /**
     * 操作类型,新增用户?删除用户 ?调用xx服务?使用接口?...
     *
     * @return
     */
    public String operation();

}
