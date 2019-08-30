package com.camelot.order.common;

/**
 * Created by jh on 2018/10/17.
 */

public class Constants {

	public static final String SUCCESS_CODE = "0";

	public static final String EMPTY_CODE = "1";
	    
	public static final String ERROR_CODE="-1";

    public static final String SAVE_SUCCESS = "保存成功";

    public static final String SAVE_FAILURE = "保存失败";

    public static final String UPDATE_SUCCESS = "更新成功";

    public static final String UPDATE_FAILURE = "更新失败";

    public static final String DELETE_SUCCESS = "删除成功";

    public static final String DELETE_FAILURE = "删除失败";

    public static final String QUERY_SUCCESS = "查询成功";

    public static final String QUERY_FAILURE = "查询失败";

    public static final String NET_DELAY = "网络不稳定,请稍后重试!";

    public static final Integer DELFLG_NORMAL = 0; //删除标记:正常
    
    public static final Integer DELFLG_DELETE = 1; //删除标记:已删除


    public static final Long WORKER_ID = 0l;


    public static final Long DATACENTER_ID = 0l;


    /**
     * 200:验证成功!
     * 201:不存在!
     * 202:无效!
     * 203:已使用!
     * 204:已作废!
     * 205:预约码已过期!
     * 206:预约码不适用于您购买的商品!
     * 500:系统异常!
     */
    public static final Integer VERIFY_CODE_200 = 200;
    public static final Integer VERIFY_CODE_201 = 201;
    public static final Integer VERIFY_CODE_202 = 202;
    public static final Integer VERIFY_CODE_203 = 203;
    public static final Integer VERIFY_CODE_204 = 204;
    public static final Integer VERIFY_CODE_205 = 205;
    public static final Integer VERIFY_CODE_206 = 206;
    public static final Integer VERIFY_CODE_500 = 500;

    /**门店 查询所有的id标识*/
    public static final Integer STORE_ID_ALL = -9999;

    /**库存强校验标识*/
    public static final Integer VERIFY = 0;
}
