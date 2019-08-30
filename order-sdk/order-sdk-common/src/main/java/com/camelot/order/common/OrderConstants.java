package com.camelot.order.common;

/**
 * 销售订单/退货订单/收款单/未购买上报 相关常量
 *
 * @author xupengfei
 * @Description
 * @Date 2019年4月4日
 */
public class OrderConstants {

    /**
     * 订单类型:销售订单
     */
    public static final String ORDER_TYPE_SALES = "SO";

    /**
     * 订单类型:退货订单
     */
    public static final String ORDER_TYPE_RETURN = "RO";


    /**
     * 订单类型:收款单
     */
    public static final String ORDER_TYPE_RECEIPT = "PR";

    /**
     * 字典类型:支付方式
     */
    public static final Integer ORDER_DICT_PAYMENT = 24;
    /**
     * 字典类型:支付渠道
     */
    public static final Integer ORDER_DICT_PAYCHANNEL = 29;

    /**
     * 字典类型:订单来源
     */
    public static final Integer ORDER_DICT_ORDER_SOURCE = 27;
    /**
     * 字典值:订单来源:线下门店
     */
    public static final Integer ORDER_SOURCE_STORE = 100;

    /**
     * 字典类型:订单状态
     */
    public static final Integer ORDER_DICT_ORDER_STATUS = 30;

    /**
     * 字典类型:消费者来源
     */
    public static final Integer ORDER_DICT_CUSTOMER_SOURCE = 5;

    /**
     * 字典类型:退货原因
     */
    public static final Integer ORDER_DICT_RETURN_REASON = 26;

    /**
     * 字典类型:未购买原因/取消订单原因
     */
    public static final Integer ORDER_DICT_NOT_BUY_REASON = 25;
    /**
     * 字典类型:支付状态
     */
    public static final Integer ORDER_DICT_PAYMENT_STATUS = 28;
    /** 字典类型:优惠码类型 */
    public static final Integer ACT_DICT_COUPON_TYPE = 7;

    //订单状态

    /**
     * 已提交
     */
    public static final Integer ORDER_STATUS_SUBMISSION = 108;
    /**
     * 已提交
     */
    public static final String ORDER_STATUS_SUBMISSION_NAME = "已提交";

    /**
     * 未支付
     */
    public static final Integer ORDER_STATUS_NOPAY = 109;

    /**
     * 已取消
     */
    public static final Integer ORDER_STATUS_CANCLE = 110;
    /**
     * 已取消
     */
    public static final String ORDER_STATUS_CANCLE_NAME = "已取消";

    /**
     * 已完成
     */
    public static final Integer ORDER_STATUS_FINISH = 111;
    /**
     * 已完成
     */
    public static final String ORDER_STATUS_FINISH_NAME = "已完成";

    /**
     * 已退货
     */
    public static final Integer ORDER_STATUS_RETURN = 112;

    /**
     * 销售订单默认初始版本号
     */
    public static final Integer ORDER_DEFAULT_VERSION = 1;

    /**
     * 收款单默认状态:已支付
     */
    public static final Integer DEFAULT_RECEIPT_STATUS = 102;
    /**
     * 收款单默认状态:已支付
     */
    public static final String DEFAULT_RECEIPT_STATUS_NAME = "已支付";
    /**
     * 收款单默认状态:未支付
     */
    public static final Integer DEFAULT_RECEIPT_NOPAY = 103;
    /**
     * 收款单默认状态:未支付
     */
    public static final String DEFAULT_RECEIPT_NOPAY_NAME = "未支付";

    /**
     * 活动编码前缀
     */
    public static final String ACTIVITY_CODE_PREFIX = "SPS666";

    /**
     * 是合伙人
     */
    public static final Integer PARTNER_PERSON_TYPE = 1;

    /**
     * 字典类型:商品属性颜色
     */
    public static final Integer GOODS_DICT_GOODS_ATTRIBUTE = 33;

    /**
     * 订单状态值
     */
    public static final String ORDER_STATUS_FINISH_VALUE = "已完成";

    /**
     * (Redis)字典表数据
     */
    public static final String DICT_TYPE_VALUE = "DICT_TYPE_VALUE_";
    /**
     * 整车分类id
     */
    public static final Integer GOODS_FIRST_CATEGORY_ID = 1;
    /**
     * 周边分类id
     */
    public static final Integer MERCH_CATEGORY_ID = 2;
    /**
     * 活动码类型
     */
    public static final Integer ACTIVITY_TYPE = 7;
    /**
     * 星期
     */
    public static final String[] WEEK_DAYS = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
    /**
     * 定时任务执行时间
     */
    public static final String STATIC_TASK = "0 0 * * * ?";
    /** 订单支付状态-已支付 */
    public static final Integer PAYMENT_STATUS_PAID = 102;

    /**
     * 字典类型:退货状态-已退货
     */
    public static final Integer ORDER_DICT_RUTURNYES = 227;
    /**
     * 字典类型:退货状态-已退货
     */
    public static final String ORDER_DICT_RUTURNYES_NAME = "已退货";

    /**
     * 字典类型:退货状态-未退货
     */
    public static final Integer ORDER_DICT_RUTURNNO = 226;
    /**
     * 字典类型:退货状态-未退货
     */
    public static final String ORDER_DICT_RUTURNNO_NAME = "未退货";

    /** 字典类型:是否激活 */
    public static final Integer ACTIVATION_FLAG_TYPE = 59;
    
    /** 字典类型:问题类型 */
    public static final Integer PROBLEM_TYPE = 14;


    /**
     * 销售统计分类:根据消费者来源
     */
    public static final String STATS_BY_SOURCE = "SOURCE";

    /**
     * 销售统计分类:根据车型
     */
    public static final String STATS_BY_CATEGORY = "CATEGORY";

    /**
     * 销售统计分类:根据活动
     */
    public static final String STATS_BY_ACTIVE = "ACTIVE";

    /**
     * 销售退货统计分类:根据退货原因
     */
    public static final String STATS_BY_REASON = "ACTIVE";
    
    /** 
     * 金额单位(万元) 
     */
    public static final String MONEY_UNIT = "";

}
