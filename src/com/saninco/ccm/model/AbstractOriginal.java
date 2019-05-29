package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractOriginal entity provides the base persistence definition of the
 * Original entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractOriginal implements java.io.Serializable {

	// Fields

	private Integer id;
	private Invoice invoice;
	private String fileName;
	private String filePath;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private String barCode;

	// Constructors

	/** default constructor */
	public AbstractOriginal() {
	}

	/** full constructor */
	public AbstractOriginal(Invoice invoice, String fileName, String filePath,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		this.invoice = invoice;
		this.fileName = fileName;
		this.filePath = filePath;
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

	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
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
		return "AbstractOriginal ["
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (filePath != null ? "filePath=" + filePath + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoice != null ? "invoice=" + invoice + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						: "") + "]";
	}

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

}