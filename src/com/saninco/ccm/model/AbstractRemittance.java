package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractRemittance entity provides the base persistence definition of the
 * Remittance entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractRemittance implements java.io.Serializable {

	// Fields

	private Integer id;
	private Invoice invoice;
	private Payment payment;
	private String invoiceNumber;
	private String apSupplierNumber;
	private Date paidDate;
	private Double paymentAmount;
	private String remittanceStatus;
	private Date statusTimestamp;
	private String processDescription;
	private String paymentReferenceCode;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractRemittance() {
	}

	/** full constructor */
	public AbstractRemittance(Invoice invoice, Payment payment,
			String invoiceNumber, String apSupplierNumber, Date paidDate,
			Double paymentAmount, String remittanceStatus,
			Date statusTimestamp, String processDescription,
			String paymentReferenceCode, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag) {
		this.invoice = invoice;
		this.payment = payment;
		this.invoiceNumber = invoiceNumber;
		this.apSupplierNumber = apSupplierNumber;
		this.paidDate = paidDate;
		this.paymentAmount = paymentAmount;
		this.remittanceStatus = remittanceStatus;
		this.statusTimestamp = statusTimestamp;
		this.processDescription = processDescription;
		this.paymentReferenceCode = paymentReferenceCode;
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

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public String getInvoiceNumber() {
		return this.invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getApSupplierNumber() {
		return this.apSupplierNumber;
	}

	public void setApSupplierNumber(String apSupplierNumber) {
		this.apSupplierNumber = apSupplierNumber;
	}

	public Date getPaidDate() {
		return this.paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	public Double getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getRemittanceStatus() {
		return this.remittanceStatus;
	}

	public void setRemittanceStatus(String remittanceStatus) {
		this.remittanceStatus = remittanceStatus;
	}

	public Date getStatusTimestamp() {
		return this.statusTimestamp;
	}

	public void setStatusTimestamp(Date statusTimestamp) {
		this.statusTimestamp = statusTimestamp;
	}

	public String getProcessDescription() {
		return this.processDescription;
	}

	public void setProcessDescription(String processDescription) {
		this.processDescription = processDescription;
	}

	public String getPaymentReferenceCode() {
		return this.paymentReferenceCode;
	}

	public void setPaymentReferenceCode(String paymentReferenceCode) {
		this.paymentReferenceCode = paymentReferenceCode;
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

}