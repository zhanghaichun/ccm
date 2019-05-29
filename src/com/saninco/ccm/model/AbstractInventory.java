package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractInventory entity provides the base persistence definition of the
 * Inventory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractInventory implements java.io.Serializable {

	// Fields

	private Integer id;
	private Vendor vendor;
	private Ban ban;
	private String itemKey;
	private String circuitNumber;
	private Date startDate;
	private Date endDate;
	private Double rate;
	private Double mrc;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractInventory() {
	}

	/** full constructor */
	public AbstractInventory(Vendor vendor, Ban ban, String itemKey,
			String circuitNumber, Date startDate, Date endDate, Double rate,
			Double mrc, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag) {
		this.vendor = vendor;
		this.ban = ban;
		this.itemKey = itemKey;
		this.circuitNumber = circuitNumber;
		this.startDate = startDate;
		this.endDate = endDate;
		this.rate = rate;
		this.mrc = mrc;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Ban getBan() {
		return this.ban;
	}

	public void setBan(Ban ban) {
		this.ban = ban;
	}

	public String getItemKey() {
		return this.itemKey;
	}

	public void setItemKey(String itemKey) {
		this.itemKey = itemKey;
	}

	public String getCircuitNumber() {
		return this.circuitNumber;
	}

	public void setCircuitNumber(String circuitNumber) {
		this.circuitNumber = circuitNumber;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getMrc() {
		return this.mrc;
	}

	public void setMrc(Double mrc) {
		this.mrc = mrc;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTimestamp() {
		return this.modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public String toString() {
		return "AbstractInventory ["
				+ (ban != null ? "ban=" + ban + ", " : "")
				+ (circuitNumber != null ? "circuitNumber=" + circuitNumber
						+ ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (endDate != null ? "endDate=" + endDate + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (itemKey != null ? "itemKey=" + itemKey + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (mrc != null ? "mrc=" + mrc + ", " : "")
				+ (rate != null ? "rate=" + rate + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (startDate != null ? "startDate=" + startDate + ", " : "")
				+ (vendor != null ? "vendor=" + vendor : "") + "]";
	}

}