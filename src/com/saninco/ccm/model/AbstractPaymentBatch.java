package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractPaymentBatch entity provides the base persistence definition of the
 * PaymentBatch entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractPaymentBatch implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer fileName;
	private String batchStatus;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	private Set payments = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractPaymentBatch() {
	}

	/** full constructor */
	public AbstractPaymentBatch(Integer fileName, String batchStatus,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Set payments) {
		this.fileName = fileName;
		this.batchStatus = batchStatus;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.payments = payments;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFileName() {
		return this.fileName;
	}

	public void setFileName(Integer fileName) {
		this.fileName = fileName;
	}

	public String getBatchStatus() {
		return this.batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
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

	public Set getPayments() {
		return this.payments;
	}

	public void setPayments(Set payments) {
		this.payments = payments;
	}

	public String toString() {
		return "AbstractPaymentBatch ["
				+ (batchStatus != null ? "batchStatus=" + batchStatus + ", "
						: "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						: "") + "]";
	}



}