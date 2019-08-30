package com.camelot.order.export.service.base;

import com.camelot.common.bean.ExecuteResult;
import com.camelot.common.bean.Page;
import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: [增删改查接口]</p >
 *
 * @author < a href=" ">sunxiaozhe</ a>
 * @version 1.0
 * 北京柯莱特科技有限公司 易用云
 * @ClassName BaseService
 * Created on 2018/10/17.
 */
public interface BaseService<VO extends Serializable> {
    public ExecuteResult<VO> add(VO vo) throws Exception;
    public ExecuteResult<VO> update(VO vo) throws Exception;
    public ExecuteResult<Integer> delete(Long id);
    public ExecuteResult<VO> queryById(Long id);
    public ExecuteResult<List<VO>> queryList(VO vo);
    public ExecuteResult<PageInfo<VO>> queryListByPage(VO vo, Page<VO> page);

}