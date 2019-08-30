package com.camelot.order.service.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import com.camelot.order.common.utils.Log;
import com.camelot.order.dao.TransregionalInfoDAO;
import com.camelot.order.domain.TransregionalInfoDomain;
import com.camelot.order.export.snapshot.TransregionalInfoVO;
import com.camelot.order.service.TransregionalInfoService;
import com.camelot.order.service.base.BaseServiceImpl;


@Service("transregionalInfoService")
public class TransregionalInfoServiceImpl extends BaseServiceImpl<TransregionalInfoVO, TransregionalInfoDomain> implements TransregionalInfoService {

	private static Logger log = Log.get(TransregionalInfoServiceImpl.class);

	@Resource
	private TransregionalInfoDAO dao;


}