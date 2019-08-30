package com.camelot.order.service.impl;

import org.springframework.stereotype.Service;

import com.camelot.order.domain.NotBuyDomain;
import com.camelot.order.export.vo.NotBuyVO;
import com.camelot.order.service.NotBuyService;
import com.camelot.order.service.base.BaseServiceImpl;

@Service("notBuyService")
public class NotBuyServiceImpl extends BaseServiceImpl<NotBuyVO, NotBuyDomain> implements NotBuyService {

}
