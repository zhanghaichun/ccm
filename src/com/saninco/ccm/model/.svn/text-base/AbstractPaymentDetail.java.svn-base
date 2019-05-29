package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractPaymentDetail entity provides the base persistence definition of the
 * PaymentDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractPaymentDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private AccountCode accountCode;
	private TaxCode taxCode;
	private Payment payment;
	private Double amount;
	private String lineTypeLookupCode;
	private Integer lineNumber;
	private String description;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractPaymentDetail() {
	}

	/** full constructor */
	public AbstractPaymentDetail(AccountCode accountCode, TaxCode taxCode,
			Payment payment, Double amount, String lineTypeLookupCode,
			Integer lineNumber, String description, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag) {
		this.accountCode = accountCode;
		this.taxCode = taxCode;
		this.payment = payment;
		this.amount = amount;
		this.lineTypeLookupCode = lineTypeLookupCode;
		this.lineNumber = lineNumber;
		this.description = description;
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

	public AccountCode getAccountCode() {
		return this.accountCode;
	}

	public void setAccountCode(AccountCode accountCode) {
		this.accountCode = accountCode;
	}

	public TaxCode getTaxCode() {
		return this.taxCode;
	}

	public void setTaxCode(TaxCode taxCode) {
		this.taxCode = taxCode;
	}

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getLineTypeLookupCode() {
		return this.lineTypeLookupCode;
	}

	public void setLineTypeLookupCode(String lineTypeLookupCode) {
		this.lineTypeLookupCode = lineTypeLookupCode;
	}

	public Integer getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(Integer lineNumber) {
		this.lineNumber = lineNumber;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	@Override
	public String toString() {
		return "AbstractPaymentDetail ["
				+ (accountCode != null ? "accountCode=" + accountCode + ", "
						: "")
				+ (amount != null ? "amount=" + amount + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (description != null ? "description=" + description + ", "
						: "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (lineNumber != null ? "lineNumber=" + lineNumber + ", " : "")
				+ (lineTypeLookupCode != null ? "lineTypeLookupCode="
						+ lineTypeLookupCode + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (payment != null ? "payment=" + payment + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (taxCode != null ? "taxCode=" + taxCode : "") + "]";
	}



}