package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractRelativeInvoice entity provides the base persistence definition of
 * the RelativeInvoice entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractRelativeInvoice implements java.io.Serializable {

	// Fields

	private Integer id;
	private Invoice invoiceByInvoice2Id;
	private Invoice invoiceByInvoice1Id;
	private String notes;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractRelativeInvoice() {
	}

	/** full constructor */
	public AbstractRelativeInvoice(Invoice invoiceByInvoice2Id,
			Invoice invoiceByInvoice1Id, String notes, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag) {
		this.invoiceByInvoice2Id = invoiceByInvoice2Id;
		this.invoiceByInvoice1Id = invoiceByInvoice1Id;
		this.notes = notes;
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

	public Invoice getInvoiceByInvoice2Id() {
		return this.invoiceByInvoice2Id;
	}

	public void setInvoiceByInvoice2Id(Invoice invoiceByInvoice2Id) {
		this.invoiceByInvoice2Id = invoiceByInvoice2Id;
	}

	public Invoice getInvoiceByInvoice1Id() {
		return this.invoiceByInvoice1Id;
	}

	public void setInvoiceByInvoice1Id(Invoice invoiceByInvoice1Id) {
		this.invoiceByInvoice1Id = invoiceByInvoice1Id;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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
		return "AbstractRelativeInvoice ["
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoiceByInvoice1Id != null ? "invoiceByInvoice1Id="
						+ invoiceByInvoice1Id + ", " : "")
				+ (invoiceByInvoice2Id != null ? "invoiceByInvoice2Id="
						+ invoiceByInvoice2Id + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (notes != null ? "notes=" + notes + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						: "") + "]";
	}
}