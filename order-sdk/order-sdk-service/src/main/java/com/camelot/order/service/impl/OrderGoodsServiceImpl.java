package com.camelot.order.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.common.Constants;
import com.camelot.order.common.utils.BeanUtil;
import com.camelot.order.common.utils.Log;
import com.camelot.order.common.utils.Utility;
import com.camelot.order.dao.OrderGoodsDAO;
import com.camelot.order.domain.OrderGoodsDomain;
import com.camelot.order.export.vo.OrderGoodsVO;
import com.camelot.order.service.OrderGoodsService;
import com.camelot.order.service.base.BaseServiceImpl;
import com.github.pagehelper.PageInfo;

@Service("orderGoodsService")
public class OrderGoodsServiceImpl extends BaseServiceImpl<OrderGoodsVO, OrderGoodsDomain> implements OrderGoodsService {

}
