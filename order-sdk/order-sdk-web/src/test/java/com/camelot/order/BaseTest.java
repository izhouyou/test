package com.camelot.order;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.order.export.service.NewSalesOrderExportService;
import com.camelot.order.export.snapshot.NewSalesOrderVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApplication.class)
public class BaseTest {

    @Autowired
    private NewSalesOrderExportService newSalesOrderExportService;

    @Test
    public void  testData(){
        // 查询订单
        ExecuteResult<NewSalesOrderVO> executeResult = newSalesOrderExportService.queryById(2356929330212242430L);
        NewSalesOrderVO newSalesOrderVO = executeResult.getResult();
        // 扣除库存数量
        ExecuteResult<String> salesVolumeVOExecuteResult = newSalesOrderExportService.reduceWarhouseGoodsAmount(newSalesOrderVO);

    }


}
