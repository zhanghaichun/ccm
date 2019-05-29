package com.saninco.ccm.model;

import java.math.BigDecimal;
import java.util.Date;

public class AbstractTarget implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8762936940335430031L;
	private Integer id;
	private Integer vendorId;
	private Integer banId	;
	private String strippedCircuitNumber;
	private Integer chargeTypeId	;
	private Integer periodFrom	;
	private Integer periodTo	;
	private Integer allowedPeriodShift	;
	private Double targetAmount	;
	private Double changeAmount	;
	private BigDecimal changePercentage	;
	private BigDecimal allowedVariationPercentage	;
	private String source	;
	private String recActiveFlag	;
	private Date createdTimestamp	;
	private Integer createdBy	;
	private Date modifiedTimestamp	;
	private Integer modifiedBy;
	
	public AbstractTarget(){
		
	}

	public AbstractTarget(Integer id, Integer vendorId, Integer banId,
			String strippedCircuitNumber, Integer chargeTypeId,
			Integer periodFrom, Integer periodTo, Integer allowedPeriodShift,
			Double targetAmount, Double changeAmount,
			BigDecimal changePercentage, BigDecimal allowedVariationPercentage,
			String source, String recActiveFlag, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy) {
		super();
		this.id = id;
		this.vendorId = vendorId;
		this.banId = banId;
		this.strippedCircuitNumber = strippedCircuitNumber;
		this.chargeTypeId = chargeTypeId;
		this.periodFrom = periodFrom;
		this.periodTo = periodTo;
		this.allowedPeriodShift = allowedPeriodShift;
		this.targetAmount = targetAmount;
		this.changeAmount = changeAmount;
		this.changePercentage = changePercentage;
		this.allowedVariationPercentage = allowedVariationPercentage;
		this.source = source;
		this.recActiveFlag = recActiveFlag;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVendorId() {
		return vendorId;
	}

	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}

	public Integer getBanId() {
		return banId;
	}

	public void setBanId(Integer banId) {
		this.banId = banId;
	}

	public String getStrippedCircuitNumber() {
		return strippedCircuitNumber;
	}

	public void setStrippedCircuitNumber(String strippedCircuitNumber) {
		this.strippedCircuitNumber = strippedCircuitNumber;
	}

	public Integer getChargeTypeId() {
		return chargeTypeId;
	}

	public void setChargeTypeId(Integer chargeTypeId) {
		this.chargeTypeId = chargeTypeId;
	}

	public Integer getPeriodFrom() {
		return periodFrom;
	}

	public void setPeriodFrom(Integer periodFrom) {
		this.periodFrom = periodFrom;
	}

	public Integer getPeriodTo() {
		return periodTo;
	}

	public void setPeriodTo(Integer periodTo) {
		this.periodTo = periodTo;
	}

	public Integer getAllowedPeriodShift() {
		return allowedPeriodShift;
	}

	public void setAllowedPeriodShift(Integer allowedPeriodShift) {
		this.allowedPeriodShift = allowedPeriodShift;
	}

	public Double getTargetAmount() {
		return targetAmount;
	}

	public void setTargetAmount(Double targetAmount) {
		this.targetAmount = targetAmount;
	}

	public Double getChangeAmount() {
		return changeAmount;
	}

	public void setChangeAmount(Double changeAmount) {
		this.changeAmount = changeAmount;
	}

	public BigDecimal getChangePercentage() {
		return changePercentage;
	}

	public void setChangePercentage(BigDecimal changePercentage) {
		this.changePercentage = changePercentage;
	}

	public BigDecimal getAllowedVariationPercentage() {
		return allowedVariationPercentage;
	}

	public void setAllowedVariationPercentage(BigDecimal allowedVariationPercentage) {
		this.allowedVariationPercentage = allowedVariationPercentage;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRecActiveFlag() {
		return recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Date getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
	

	

}
