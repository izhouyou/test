package com.camelot.order.service.impl;


import com.camelot.order.domain.CustomerDomain;
import com.camelot.order.export.snapshot.CustomerVO;
import com.camelot.order.service.CustomerService;
import com.camelot.order.service.base.BaseServiceImpl;
import org.springframework.stereotype.Service;


@Service("customerService")
public class CustomerServiceImpl extends BaseServiceImpl<CustomerVO, CustomerDomain> implements CustomerService {


}