package com.camelot.order.export.service;

import java.util.List;

import com.camelot.order.export.vo.DictValueVO;


public interface DictValueExportService {

	/**
	 *@Description 根据字典类型获取该类型下的字典值集合(跨服务)
	 *@author xupengfei
	 *@Data 2019年4月2日 
	 * @param type
	 * @return
	 */
	public List<DictValueVO> getDictValueListByFeign(Integer type);
	
	
	/**
	 *@Description 根据字典类型获取该类型下的字典值集合(Redis)
	 *@author xupengfei
	 *@Date 2019年4月27日 
	 * @param type
	 * @return
	 */
	public List<DictValueVO> getDictValueListByRedis(Integer type);
	
	/**
	 *@Description 根据字典类型及字典值ID获取字典值名称
	 *@author xupengfei
	 *@Data 2019年4月2日 
	 * @param type
	 * @param id
	 * @return
	 */
	public String getDictValueNameById(Integer type,Integer id);
	
	
	/**
	 *@Description 根据字典类型及字典值编码获取字典值ID
	 *@author xupengfei
	 *@Data 2019年4月2日 
	 * @param type
	 * @param number
	 * @return
	 */
	public Long getDictValueIdByNumber(Integer type,String number);
}
