package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractReceivedPayment entity provides the base persistence definition of
 * the ReceivedPayment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractReceivedPayment implements java.io.Serializable {

	// Fields

	private Integer id;
	private AttachmentPoint attachmentPoint;
	private Vendor vendor;
	private Invoice invoice;
	private Ban ban;
	private Payment payment;
	private Double paymentAmount;
	private String referenceNumber;
	private Date receivedDate;
	private String notes;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractReceivedPayment() {
	}

	/** full constructor */
	public AbstractReceivedPayment(AttachmentPoint attachmentPoint,
			Vendor vendor, Invoice invoice, Ban ban, Payment payment,
			Double paymentAmount, String referenceNumber, Date receivedDate,
			String notes, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag) {
		this.attachmentPoint = attachmentPoint;
		this.vendor = vendor;
		this.invoice = invoice;
		this.ban = ban;
		this.payment = payment;
		this.paymentAmount = paymentAmount;
		this.referenceNumber = referenceNumber;
		this.receivedDate = receivedDate;
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

	public AttachmentPoint getAttachmentPoint() {
		return this.attachmentPoint;
	}

	public void setAttachmentPoint(AttachmentPoint attachmentPoint) {
		this.attachmentPoint = attachmentPoint;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Ban getBan() {
		return this.ban;
	}

	public void setBan(Ban ban) {
		this.ban = ban;
	}

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Double getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getReferenceNumber() {
		return this.referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public Date getReceivedDate() {
		return this.receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
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
		return "AbstractReceivedPayment ["
				+ (attachmentPoint != null ? "attachmentPoint="
						+ attachmentPoint + ", " : "")
				+ (ban != null ? "ban=" + ban + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoice != null ? "invoice=" + invoice + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (notes != null ? "notes=" + notes + ", " : "")
				+ (payment != null ? "payment=" + payment + ", " : "")
				+ (paymentAmount != null ? "paymentAmount=" + paymentAmount
						+ ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (receivedDate != null ? "receivedDate=" + receivedDate + ", "
						: "")
				+ (referenceNumber != null ? "referenceNumber="
						+ referenceNumber + ", " : "")
				+ (vendor != null ? "vendor=" + vendor : "") + "]";
	}

}