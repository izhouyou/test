package com.camelot.order.export.vo.feignvo;

import java.io.Serializable;
import java.util.Date;

public class GoodsCategoryVO implements Serializable {

	private static final long serialVersionUID = 1L;
	/**主键*/
	private Long goodsCategoryId;
	/**父id*/
	private Long parentId;
	/**分类编码*/
	private String categoryNumber;
	/**分类名称*/
	private String categoryName;
	/**排序*/
	private Long sort;
	/**分类状态：0为启用；1为停用*/
	private Long categoryStatus;
	/**删除标识（0代表未删除； 1代表已删除）*/
	private Integer delFlg;
	/**创建人id*/
	private Long createUserId;
	/**修改人id*/
	private Long updateUserId;
	/**创建时间*/
	private Date createDate;
	/**修改时间*/
	private Date modifyDate;
	
	public Long getGoodsCategoryId(){
		return goodsCategoryId;
	}
	
	public void setGoodsCategoryId(Long goodsCategoryId){
		this.goodsCategoryId = goodsCategoryId;
	}

	public Long getParentId(){
		return parentId;
	}
	
	public void setParentId(Long parentId){
		this.parentId = parentId;
	}

	public String getCategoryNumber(){
		return categoryNumber;
	}
	
	public void setCategoryNumber(String categoryNumber){
		this.categoryNumber = categoryNumber;
	}

	public String getCategoryName(){
		return categoryName;
	}
	
	public void setCategoryName(String categoryName){
		this.categoryName = categoryName;
	}

	public Long getSort(){
		return sort;
	}
	
	public void setSort(Long sort){
		this.sort = sort;
	}

	public Long getCategoryStatus(){
		return categoryStatus;
	}
	
	public void setCategoryStatus(Long categoryStatus){
		this.categoryStatus = categoryStatus;
	}

	public Integer getDelFlg(){
		return delFlg;
	}
	
	public void setDelFlg(Integer delFlg){
		this.delFlg = delFlg;
	}

	public Long getCreateUserId(){
		return createUserId;
	}
	
	public void setCreateUserId(Long createUserId){
		this.createUserId = createUserId;
	}

	public Long getUpdateUserId(){
		return updateUserId;
	}
	
	public void setUpdateUserId(Long updateUserId){
		this.updateUserId = updateUserId;
	}

	public Date getCreateDate(){
		return createDate;
	}
	
	public void setCreateDate(Date createDate){
		this.createDate = createDate;
	}

	public Date getModifyDate(){
		return modifyDate;
	}
	
	public void setModifyDate(Date modifyDate){
		this.modifyDate = modifyDate;
	}

}
