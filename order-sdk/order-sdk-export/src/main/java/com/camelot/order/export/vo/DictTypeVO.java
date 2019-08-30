package com.camelot.order.export.vo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DictTypeVO implements Serializable{

	/**主键*/
	private Integer dictTypeId;
	/**字典类型编号*/
	private Integer dictTypeNumber;
	/**数据类型名称*/
	private String dictTypeName;
	/**字典描述*/
	private String description;
	/**删除状态(0 保留 1删除)*/
	private Integer delFlg;
	/**创建人*/
	private Integer createUserId;
	/**修改人*/
	private Integer updateUserId;
	/**创建时间*/
	private Date createDate;
	/**修改时间*/
	private Date modifyDate;
	/**数据字典值信息*/
	private List<DictValueVO> DictValueList;


	private String dictTypeIds;
	private List<Integer> list;

	public List<Integer> getList() {
		return list;
	}

	public void setList(List<Integer> list) {
		this.list = list;
	}

	public Integer getDictTypeId(){
		return dictTypeId;
	}
	
	public void setDictTypeId(Integer dictTypeId){
		this.dictTypeId = dictTypeId;
	}

	public Integer getDictTypeNumber(){
		return dictTypeNumber;
	}
	
	public void setDictTypeNumber(Integer dictTypeNumber){
		this.dictTypeNumber = dictTypeNumber;
	}

	public String getDictTypeName(){
		return dictTypeName;
	}
	
	public void setDictTypeName(String dictTypeName){
		this.dictTypeName = dictTypeName;
	}

	public String getDescription(){
		return description;
	}
	
	public void setDescription(String description){
		this.description = description;
	}

	public Integer getDelFlg(){
		return delFlg;
	}
	
	public void setDelFlg(Integer delFlg){
		this.delFlg = delFlg;
	}

	public Integer getCreateUserId(){
		return createUserId;
	}
	
	public void setCreateUserId(Integer createUserId){
		this.createUserId = createUserId;
	}

	public Integer getUpdateUserId(){
		return updateUserId;
	}
	
	public void setUpdateUserId(Integer updateUserId){
		this.updateUserId = updateUserId;
	}

	public java.util.Date getCreateDate(){
		return createDate;
	}
	
	public void setCreateDate(java.util.Date createDate){
		this.createDate = createDate;
	}

	public java.util.Date getModifyDate(){
		return modifyDate;
	}
	
	public void setModifyDate(java.util.Date modifyDate){
		this.modifyDate = modifyDate;
	}

	public List<DictValueVO> getDictValueList() {
		return DictValueList;
	}

	public void setDictValueList(List<DictValueVO> dictValueList) {
		DictValueList = dictValueList;
	}

	public String getDictTypeIds() {
		return dictTypeIds;
	}

	public void setDictTypeIds(String dictTypeIds) {
		this.dictTypeIds = dictTypeIds;
	}
}
