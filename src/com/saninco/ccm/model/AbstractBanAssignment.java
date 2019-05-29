package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractBanAssignment entity provides the base persistence definition of the
 * BanAssignment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractBanAssignment implements java.io.Serializable {

	// Fields

	private Integer id;
	private Vendor vendor;
	private Ban ban;
	private Integer analystId;
	private Integer approver1Id;
	private Integer approver2Id;
	private Integer approver3Id;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractBanAssignment() {
	}

	/** full constructor */
	public AbstractBanAssignment(Vendor vendor, Ban ban, Integer analystId,
			Integer approver1Id, Integer approver2Id, Integer approver3Id,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		this.vendor = vendor;
		this.ban = ban;
		this.analystId = analystId;
		this.approver1Id = approver1Id;
		this.approver2Id = approver2Id;
		this.approver3Id = approver3Id;
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

	public Integer getAnalystId() {
		return this.analystId;
	}

	public void setAnalystId(Integer analystId) {
		this.analystId = analystId;
	}

	public Integer getApprover1Id() {
		return this.approver1Id;
	}

	public void setApprover1Id(Integer approver1Id) {
		this.approver1Id = approver1Id;
	}

	public Integer getApprover2Id() {
		return this.approver2Id;
	}

	public void setApprover2Id(Integer approver2Id) {
		this.approver2Id = approver2Id;
	}

	public Integer getApprover3Id() {
		return this.approver3Id;
	}

	public void setApprover3Id(Integer approver3Id) {
		this.approver3Id = approver3Id;
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
		return "AbstractBanAssignment [analystId=" + analystId
				+ ", approver1Id=" + approver1Id + ", approver2Id="
				+ approver2Id + ", approver3Id=" + approver3Id + ", ban=" + ban
				+ ", createdBy=" + createdBy + ", createdTimestamp="
				+ createdTimestamp + ", id=" + id + ", modifiedBy="
				+ modifiedBy + ", modifiedTimestamp=" + modifiedTimestamp
				+ ", recActiveFlag=" + recActiveFlag + ", vendor=" + vendor
				+ "]";
	}

}