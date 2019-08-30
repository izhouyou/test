package com.camelot.order.feign.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

public class WarehouseGoodsVO{

	/**门店库存信息表主键id*/
	@ApiModelProperty(value = "门店库存信息表主键id", required = false)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long warehouseGoodsId;
	/**门店ID*/
	@ApiModelProperty(value = "门店ID", required = false)
	private Integer storeId;
	/**门店编码*/
	@ApiModelProperty(value = "门店编码", required = false)
	private String storeNumber;
	/**门店渠道编码*/
	@ApiModelProperty(value = "门店渠道编码", required = false)
	private String storeChannelNumber;
	/**门店名称*/
	@ApiModelProperty(value = "门店名称", required = false)
	private String storeName;
	/**仓库ID*/
	@ApiModelProperty(value = "仓库ID", required = false)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long warehouseId;
	/**仓库编码*/
	@ApiModelProperty(value = "仓库编码", required = false)
	private String warehouseCode;
	/**仓库名称*/
	@ApiModelProperty(value = "仓库名称", required = false)
	private String warehouseName;
	/**大区id*/
	@ApiModelProperty(value = "大区id", required = false)
	private Integer firstOrgId;
	/**大区编码*/
	@ApiModelProperty(value = "大区编码", required = false)
	private String firstOrgNumber;
	/**大区名称*/
	@ApiModelProperty(value = "大区名称", required = false)
	private String firstOrgName;
	/**区域id*/
	@ApiModelProperty(value = "区域id", required = false)
	private Integer secondOrgId;
	/**区域编码*/
	@ApiModelProperty(value = "区域编码", required = false)
	private String secondOrgNumber;
	/**区域名称*/
	@ApiModelProperty(value = "区域名称", required = false)
	private String secondOrgName;
	/**城市id*/
	@ApiModelProperty(value = "城市id", required = false)
	private Integer thirdOrgId;
	/**城市编码*/
	@ApiModelProperty(value = "城市编码", required = false)
	private String thirdOrgNumber;
	/**城市名称*/
	@ApiModelProperty(value = "城市名称", required = false)
	private String thirdOrgName;
	/**商品一级分类id*/
	@ApiModelProperty(value = "商品一级分类id", required = false)
	private Integer firstCategoryId;
	/**商品一级分类编码*/
	@ApiModelProperty(value = "商品一级分类编码", required = false)
	private String firstCategoryNumber;
	/**商品一级分类名称*/
	@ApiModelProperty(value = "商品一级分类名称", required = false)
	private String firstCategoryName;
	/**商品二级分类id*/
	@ApiModelProperty(value = "商品二级分类id", required = false)
	private Integer secondCategoryId;
	/**商品二级分类编码*/
	@ApiModelProperty(value = "商品二级分类编码", required = false)
	private String secondCategoryNumber;
	/**商品二级分类名称*/
	@ApiModelProperty(value = "商品二级分类名称", required = false)
	private String secondCategoryName;
	/**商品三级分类id*/
	@ApiModelProperty(value = "商品三级分类id", required = false)
	private Integer thirdCategoryId;
	/**商品三级分类编码*/
	@ApiModelProperty(value = "商品三级分类编码", required = false)
	private String thirdCategoryNumber;
	/**商品三级级分类名称*/
	@ApiModelProperty(value = "商品三级级分类名称", required = false)
	private String thirdCategoryName;
	/**商品四级分类id*/
	@ApiModelProperty(value = "商品四级分类id", required = false)
	private Integer fourCategoryId;
	/**商品四级分类编码*/
	@ApiModelProperty(value = "商品四级分类编码", required = false)
	private String fourCategoryNumber;
	/**商品四级分类名称*/
	@ApiModelProperty(value = "商品四级分类名称", required = false)
	private String fourCategoryName;
	/**商品五级分类id*/
	@ApiModelProperty(value = "商品五级分类id", required = false)
	private Integer fiveCategoryId;
	/**商品五级分类编码*/
	@ApiModelProperty(value = "商品五级分类编码", required = false)
	private String fiveCategoryNumber;
	/**商品五级级分类名称*/
	@ApiModelProperty(value = "商品五级级分类名称", required = false)
	private String fiveCategoryName;
	/**商品id*/
	@ApiModelProperty(value = "商品id", required = false)
	private Integer goodsId;
	/**商品名称*/
	@ApiModelProperty(value = "商品名称", required = false)
	private String goodsName;
	/**商品编号*/
	@ApiModelProperty(value = "商品编号", required = false)
	private String goodsNumber;
	/**商品69码*/
	@ApiModelProperty(value = "商品69码", required = false)
	private String goodsBarcode;
	/**商品SN*/
	@ApiModelProperty(value = "商品SN", required = false)
	private String goodsSn;
	/**车架号*/
	@ApiModelProperty(value = "车架号", required = false)
	private String goodsFrameNumber;
	/**有无序列(Y：有序；N：无序)*/
	@ApiModelProperty(value = "有无序列(Y：有序；N：无序)", required = false)
	private String orderlyFlag;
	/**库存量*/
	@ApiModelProperty(value = "库存量", required = false)
	private Integer inventory;
	/**版本号*/
	@ApiModelProperty(value = "版本号", required = false)
	private Integer version;
	/**流水号(防重复提交)*/
	@ApiModelProperty(value = "流水号(防重复提交)", required = false)
	@JsonSerialize(using = ToStringSerializer.class)
	private Long transactionId;
	/**创建时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(value = "创建时间", required = false)
	private java.util.Date createTime;
	/**修改时间*/
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	@ApiModelProperty(value = "修改时间", required = false)
	private java.util.Date updateDate;
	/** 商品名称/商品编号 */
	@ApiModelProperty(value = "商品名称/商品编号", required = false)
	private String goodsSearch;
	/**颜色id*/
	@ApiModelProperty(value = "颜色id", required = false)
	private Integer goodsAttribute;
	/**颜色编码*/
	@ApiModelProperty(value = "颜色编码", required = false)
	private String goodsAttributeNumber;
	/**颜色名称*/
	@ApiModelProperty(value = "颜色名称", required = false)
	private String goodsAttributeValue;
	/** 商品分类id */
	@ApiModelProperty(value = "商品分类id", required = false)
	private Integer goodsCategoryId;
	/** 商品二级分类id集合 */
	@ApiModelProperty(value = "商品二级分类id集合", required = false)
	private List<Integer> secondCategoryIdList;
	/** 商品三级分类id集合 */
	@ApiModelProperty(value = "商品三级分类id集合", required = false)
	private List<Integer> thirdCategoryIdList;
	/**合伙人id*/
	@ApiModelProperty(value = "合伙人id", required = false)
	private Integer partnerId;
	/**合伙人编码*/
	@ApiModelProperty(value = "合伙人编码", required = false)
	private String partnerNumber;
	/**合伙人名称*/
	@ApiModelProperty(value = "合伙人名称", required = false)
	private String partnerName;
	/**加盟商id*/
	@ApiModelProperty(value = "加盟商id", required = false)
	private Integer traderId;
	/**加盟商编码*/
	@ApiModelProperty(value = "加盟商编码", required = false)
	private String traderNumber;
	/**加盟商名称*/
	@ApiModelProperty(value = "加盟商名称", required = false)
	private String traderName;
	/**颜色id集合*/
	@ApiModelProperty(value = "颜色id集合", required = false)
	private List<Integer> goodsAttributeList;
	/** 查询条件-门店id串 */
	private String storeIds;
	/**颜色id字符串*/
	@ApiModelProperty(value = "颜色id字符串", required = false)
	private String goodsAttributeIds;
	/** 商品三级分类id字符串 */
	@ApiModelProperty(value = "商品三级分类id字符串", required = false)
	private String thirdCategoryIds;
	/** 库存集合 */
	@ApiModelProperty(value = "库存商品列表集合", required = false)
	private List<WarehouseGoodsVO> listWarehouseGoods;
	/** 商品SN号和69码的拼接数据 */
	@ApiModelProperty(value = "商品SN号和69码的拼接数据", required = false)
	private List<Long> notContainIdList;
	/** 是否显示库存量为0的数据:1不显示 */
	private Integer zeroFlag;

	public Long getWarehouseGoodsId() {
		return warehouseGoodsId;
	}

	public void setWarehouseGoodsId(Long warehouseGoodsId) {
		this.warehouseGoodsId = warehouseGoodsId;
	}

	public Integer getStoreId() {
		return storeId;
	}

	public void setStoreId(Integer storeId) {
		this.storeId = storeId;
	}

	public String getStoreNumber() {
		return storeNumber;
	}

	public void setStoreNumber(String storeNumber) {
		this.storeNumber = storeNumber;
	}

	public String getStoreChannelNumber() {
		return storeChannelNumber;
	}

	public void setStoreChannelNumber(String storeChannelNumber) {
		this.storeChannelNumber = storeChannelNumber;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getWarehouseCode() {
		return warehouseCode;
	}

	public void setWarehouseCode(String warehouseCode) {
		this.warehouseCode = warehouseCode;
	}

	public String getWarehouseName() {
		return warehouseName;
	}

	public void setWarehouseName(String warehouseName) {
		this.warehouseName = warehouseName;
	}

	public Integer getFirstOrgId() {
		return firstOrgId;
	}

	public void setFirstOrgId(Integer firstOrgId) {
		this.firstOrgId = firstOrgId;
	}

	public String getFirstOrgNumber() {
		return firstOrgNumber;
	}

	public void setFirstOrgNumber(String firstOrgNumber) {
		this.firstOrgNumber = firstOrgNumber;
	}

	public String getFirstOrgName() {
		return firstOrgName;
	}

	public void setFirstOrgName(String firstOrgName) {
		this.firstOrgName = firstOrgName;
	}

	public Integer getSecondOrgId() {
		return secondOrgId;
	}

	public void setSecondOrgId(Integer secondOrgId) {
		this.secondOrgId = secondOrgId;
	}

	public String getSecondOrgNumber() {
		return secondOrgNumber;
	}

	public void setSecondOrgNumber(String secondOrgNumber) {
		this.secondOrgNumber = secondOrgNumber;
	}

	public String getSecondOrgName() {
		return secondOrgName;
	}

	public void setSecondOrgName(String secondOrgName) {
		this.secondOrgName = secondOrgName;
	}

	public Integer getThirdOrgId() {
		return thirdOrgId;
	}

	public void setThirdOrgId(Integer thirdOrgId) {
		this.thirdOrgId = thirdOrgId;
	}

	public String getThirdOrgNumber() {
		return thirdOrgNumber;
	}

	public void setThirdOrgNumber(String thirdOrgNumber) {
		this.thirdOrgNumber = thirdOrgNumber;
	}

	public String getThirdOrgName() {
		return thirdOrgName;
	}

	public void setThirdOrgName(String thirdOrgName) {
		this.thirdOrgName = thirdOrgName;
	}

	public Integer getFirstCategoryId() {
		return firstCategoryId;
	}

	public void setFirstCategoryId(Integer firstCategoryId) {
		this.firstCategoryId = firstCategoryId;
	}

	public String getFirstCategoryNumber() {
		return firstCategoryNumber;
	}

	public void setFirstCategoryNumber(String firstCategoryNumber) {
		this.firstCategoryNumber = firstCategoryNumber;
	}

	public String getFirstCategoryName() {
		return firstCategoryName;
	}

	public void setFirstCategoryName(String firstCategoryName) {
		this.firstCategoryName = firstCategoryName;
	}

	public Integer getSecondCategoryId() {
		return secondCategoryId;
	}

	public void setSecondCategoryId(Integer secondCategoryId) {
		this.secondCategoryId = secondCategoryId;
	}

	public String getSecondCategoryNumber() {
		return secondCategoryNumber;
	}

	public void setSecondCategoryNumber(String secondCategoryNumber) {
		this.secondCategoryNumber = secondCategoryNumber;
	}

	public String getSecondCategoryName() {
		return secondCategoryName;
	}

	public void setSecondCategoryName(String secondCategoryName) {
		this.secondCategoryName = secondCategoryName;
	}

	public Integer getThirdCategoryId() {
		return thirdCategoryId;
	}

	public void setThirdCategoryId(Integer thirdCategoryId) {
		this.thirdCategoryId = thirdCategoryId;
	}

	public String getThirdCategoryNumber() {
		return thirdCategoryNumber;
	}

	public void setThirdCategoryNumber(String thirdCategoryNumber) {
		this.thirdCategoryNumber = thirdCategoryNumber;
	}

	public String getThirdCategoryName() {
		return thirdCategoryName;
	}

	public void setThirdCategoryName(String thirdCategoryName) {
		this.thirdCategoryName = thirdCategoryName;
	}

	public Integer getFourCategoryId() {
		return fourCategoryId;
	}

	public void setFourCategoryId(Integer fourCategoryId) {
		this.fourCategoryId = fourCategoryId;
	}

	public String getFourCategoryNumber() {
		return fourCategoryNumber;
	}

	public void setFourCategoryNumber(String fourCategoryNumber) {
		this.fourCategoryNumber = fourCategoryNumber;
	}

	public String getFourCategoryName() {
		return fourCategoryName;
	}

	public void setFourCategoryName(String fourCategoryName) {
		this.fourCategoryName = fourCategoryName;
	}

	public Integer getFiveCategoryId() {
		return fiveCategoryId;
	}

	public void setFiveCategoryId(Integer fiveCategoryId) {
		this.fiveCategoryId = fiveCategoryId;
	}

	public String getFiveCategoryNumber() {
		return fiveCategoryNumber;
	}

	public void setFiveCategoryNumber(String fiveCategoryNumber) {
		this.fiveCategoryNumber = fiveCategoryNumber;
	}

	public String getFiveCategoryName() {
		return fiveCategoryName;
	}

	public void setFiveCategoryName(String fiveCategoryName) {
		this.fiveCategoryName = fiveCategoryName;
	}

	public Integer getGoodsId() {
		return goodsId;
	}

	public void setGoodsId(Integer goodsId) {
		this.goodsId = goodsId;
	}

	public String getGoodsName() {
		return goodsName;
	}

	public void setGoodsName(String goodsName) {
		this.goodsName = goodsName;
	}

	public String getGoodsNumber() {
		return goodsNumber;
	}

	public void setGoodsNumber(String goodsNumber) {
		this.goodsNumber = goodsNumber;
	}

	public String getGoodsBarcode() {
		return goodsBarcode;
	}

	public void setGoodsBarcode(String goodsBarcode) {
		this.goodsBarcode = goodsBarcode;
	}

	public String getGoodsSn() {
		return goodsSn;
	}

	public void setGoodsSn(String goodsSn) {
		this.goodsSn = goodsSn;
	}

	public String getGoodsFrameNumber() {
		return goodsFrameNumber;
	}

	public void setGoodsFrameNumber(String goodsFrameNumber) {
		this.goodsFrameNumber = goodsFrameNumber;
	}

	public String getOrderlyFlag() {
		return orderlyFlag;
	}

	public void setOrderlyFlag(String orderlyFlag) {
		this.orderlyFlag = orderlyFlag;
	}

	public Integer getInventory() {
		return inventory;
	}

	public void setInventory(Integer inventory) {
		this.inventory = inventory;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(Long transactionId) {
		this.transactionId = transactionId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getGoodsSearch() {
		return goodsSearch;
	}

	public void setGoodsSearch(String goodsSearch) {
		this.goodsSearch = goodsSearch;
	}

	public Integer getGoodsAttribute() {
		return goodsAttribute;
	}

	public void setGoodsAttribute(Integer goodsAttribute) {
		this.goodsAttribute = goodsAttribute;
	}

	public String getGoodsAttributeNumber() {
		return goodsAttributeNumber;
	}

	public void setGoodsAttributeNumber(String goodsAttributeNumber) {
		this.goodsAttributeNumber = goodsAttributeNumber;
	}

	public String getGoodsAttributeValue() {
		return goodsAttributeValue;
	}

	public void setGoodsAttributeValue(String goodsAttributeValue) {
		this.goodsAttributeValue = goodsAttributeValue;
	}

	public Integer getGoodsCategoryId() {
		return goodsCategoryId;
	}

	public void setGoodsCategoryId(Integer goodsCategoryId) {
		this.goodsCategoryId = goodsCategoryId;
	}

	public List<Integer> getSecondCategoryIdList() {
		return secondCategoryIdList;
	}

	public void setSecondCategoryIdList(List<Integer> secondCategoryIdList) {
		this.secondCategoryIdList = secondCategoryIdList;
	}

	public List<Integer> getThirdCategoryIdList() {
		return thirdCategoryIdList;
	}

	public void setThirdCategoryIdList(List<Integer> thirdCategoryIdList) {
		this.thirdCategoryIdList = thirdCategoryIdList;
	}

	public Integer getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Integer partnerId) {
		this.partnerId = partnerId;
	}

	public String getPartnerNumber() {
		return partnerNumber;
	}

	public void setPartnerNumber(String partnerNumber) {
		this.partnerNumber = partnerNumber;
	}

	public String getPartnerName() {
		return partnerName;
	}

	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public String getTraderNumber() {
		return traderNumber;
	}

	public void setTraderNumber(String traderNumber) {
		this.traderNumber = traderNumber;
	}

	public String getTraderName() {
		return traderName;
	}

	public void setTraderName(String traderName) {
		this.traderName = traderName;
	}

	public List<Integer> getGoodsAttributeList() {
		return goodsAttributeList;
	}

	public void setGoodsAttributeList(List<Integer> goodsAttributeList) {
		this.goodsAttributeList = goodsAttributeList;
	}

	public String getStoreIds() {
		return storeIds;
	}

	public void setStoreIds(String storeIds) {
		this.storeIds = storeIds;
	}

	public String getGoodsAttributeIds() {
		return goodsAttributeIds;
	}

	public void setGoodsAttributeIds(String goodsAttributeIds) {
		this.goodsAttributeIds = goodsAttributeIds;
	}

	public String getThirdCategoryIds() {
		return thirdCategoryIds;
	}

	public void setThirdCategoryIds(String thirdCategoryIds) {
		this.thirdCategoryIds = thirdCategoryIds;
	}

	public List<WarehouseGoodsVO> getListWarehouseGoods() {
		return listWarehouseGoods;
	}

	public void setListWarehouseGoods(List<WarehouseGoodsVO> listWarehouseGoods) {
		this.listWarehouseGoods = listWarehouseGoods;
	}

	public List<Long> getNotContainIdList() {
		return notContainIdList;
	}

	public void setNotContainIdList(List<Long> notContainIdList) {
		this.notContainIdList = notContainIdList;
	}

	public Integer getZeroFlag() {
		return zeroFlag;
	}

	public void setZeroFlag(Integer zeroFlag) {
		this.zeroFlag = zeroFlag;
	}
}
