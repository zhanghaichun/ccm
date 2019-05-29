package com.saninco.ccm.target.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchVO;

public class SearchTargetVO extends SearchVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7114200802404926024L;
	private String vendorId;
	private String banId;
	private String circuitNumber;
	private BigDecimal vPercent;
	private String chargeTypeSelect;
	private String createdBy;
	private Double targetAmount;
	private Double changeAmount;
	private BigDecimal changePercentage;
	private String targetPeriodStartsOn;
	private String targetPeriodEndsOn;
	private String createdDateStartsOn;
	private String createdEndsOn;
	private Integer targetId;
	private boolean historicalInvoice;
	
	
	
	public Integer getTargetId() {
		return targetId;
	}

	public void setTargetId(Integer targetId) {
		this.targetId = targetId;
	}

	public String getCircuitNumber() {
		return circuitNumber;
	}

	public void setCircuitNumber(String circuitNumber) {
		this.circuitNumber = circuitNumber;
	}

	public BigDecimal getvPercent() {
		return vPercent;
	}

	public void setvPercent(BigDecimal vPercent) {
		this.vPercent = vPercent;
	}

	public String getChargeTypeSelect() {
		return chargeTypeSelect;
	}

	public void setChargeTypeSelect(String chargeTypeSelect) {
		this.chargeTypeSelect = chargeTypeSelect;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
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

	public String getTargetPeriodStartsOn() {
		return targetPeriodStartsOn;
	}

	public void setTargetPeriodStartsOn(String targetPeriodStartsOn) {
		this.targetPeriodStartsOn = targetPeriodStartsOn;
	}

	public String getTargetPeriodEndsOn() {
		return targetPeriodEndsOn;
	}

	public void setTargetPeriodEndsOn(String targetPeriodEndsOn) {
		this.targetPeriodEndsOn = targetPeriodEndsOn;
	}

	public String getCreatedDateStartsOn() {
		return createdDateStartsOn;
	}

	public void setCreatedDateStartsOn(String createdDateStartsOn) {
		this.createdDateStartsOn = createdDateStartsOn;
	}

	public String getCreatedEndsOn() {
		return createdEndsOn;
	}

	public void setCreatedEndsOn(String createdEndsOn) {
		this.createdEndsOn = createdEndsOn;
	}

	public SearchTargetVO() {
	}

	/**
	 * @return the vendorId
	 */
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId
	 *            the vendorId to set
	 */
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId.trim();
	}

	/**
	 * @return the banId
	 */
	public String getBanId() {
		return banId;
	}

	/**
	 * @param banId the banId to set
	 */
	public void setBanId(String banId) {
		this.banId = banId==null ? null : banId.trim();
	}

	public boolean isHistoricalInvoice() {
		return historicalInvoice;
	}

	public void setHistoricalInvoice(boolean historicalInvoice) {
		this.historicalInvoice = historicalInvoice;
	}

	
	
	

	
}
