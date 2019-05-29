package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractCredit entity provides the base persistence definition of the Credit
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractCredit implements java.io.Serializable {

	// Fields

	private Integer id;
	private AttachmentPoint attachmentPoint;
	private AccountCode accountCode;
	private Vendor vendor;
	private Invoice invoice;
	private Ban ban;
	private CreditStatus creditStatus;
	private Double creditAmount;
	private Double creditBalance;
	private String referenceNumber;
	private String disputeNumber;
	private String claimNumber;
	private String ticketNumber;
	private Date creditDate;
	private String notes;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private Date statusTimestamp;
	private String disputeCategory;
	private String circuitNumber;
 
	// Constructors

	/** default constructor */
	public AbstractCredit() {
	}

	/** full constructor */
	public AbstractCredit(AttachmentPoint attachmentPoint,
			AccountCode accountCode, Vendor vendor, Invoice invoice, Ban ban,
			CreditStatus creditStatus, Double creditAmount,
			Double creditBalance, String referenceNumber, String disputeNumber,
			String claimNumber, String ticketNumber, Date creditDate,
			String notes, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag,
			Date statusTimestamp, String disputeCategory, String circuitNumber,
			Set reconciles) {
		this.attachmentPoint = attachmentPoint;
		this.accountCode = accountCode;
		this.vendor = vendor;
		this.invoice = invoice;
		this.ban = ban;
		this.creditStatus = creditStatus;
		this.creditAmount = creditAmount;
		this.creditBalance = creditBalance;
		this.referenceNumber = referenceNumber;
		this.disputeNumber = disputeNumber;
		this.claimNumber = claimNumber;
		this.ticketNumber = ticketNumber;
		this.creditDate = creditDate;
		this.notes = notes;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.statusTimestamp = statusTimestamp;
		this.disputeCategory = disputeCategory;
		this.circuitNumber = circuitNumber;
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

	public AccountCode getAccountCode() {
		return this.accountCode;
	}

	public void setAccountCode(AccountCode accountCode) {
		this.accountCode = accountCode;
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

	public CreditStatus getCreditStatus() {
		return this.creditStatus;
	}

	public void setCreditStatus(CreditStatus creditStatus) {
		this.creditStatus = creditStatus;
	}

	public Double getCreditAmount() {
		return this.creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Double getCreditBalance() {
		return this.creditBalance;
	}

	public void setCreditBalance(Double creditBalance) {
		this.creditBalance = creditBalance;
	}

	public String getReferenceNumber() {
		return this.referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	public String getDisputeNumber() {
		return this.disputeNumber;
	}

	public void setDisputeNumber(String disputeNumber) {
		this.disputeNumber = disputeNumber;
	}

	public String getClaimNumber() {
		return this.claimNumber;
	}

	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}

	public String getTicketNumber() {
		return this.ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	public Date getCreditDate() {
		return this.creditDate;
	}

	public void setCreditDate(Date creditDate) {
		this.creditDate = creditDate;
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

	public Date getStatusTimestamp() {
		return this.statusTimestamp;
	}

	public void setStatusTimestamp(Date statusTimestamp) {
		this.statusTimestamp = statusTimestamp;
	}

	public String getDisputeCategory() {
		return this.disputeCategory;
	}

	public void setDisputeCategory(String disputeCategory) {
		this.disputeCategory = disputeCategory;
	}

	public String getCircuitNumber() {
		return this.circuitNumber;
	}

	public void setCircuitNumber(String circuitNumber) {
		this.circuitNumber = circuitNumber;
	}
 

	public String toString() {
		return "AbstractCredit ["
				+ (accountCode != null ? "accountCode=" + accountCode + ", "
						: "")
				+ (attachmentPoint != null ? "attachmentPoint="
						+ attachmentPoint + ", " : "")
				+ (ban != null ? "ban=" + ban + ", " : "")
				+ (circuitNumber != null ? "circuitNumber=" + circuitNumber
						+ ", " : "")
				+ (claimNumber != null ? "claimNumber=" + claimNumber + ", "
						: "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (creditAmount != null ? "creditAmount=" + creditAmount + ", "
						: "")
				+ (creditBalance != null ? "creditBalance=" + creditBalance
						+ ", " : "")
				+ (creditDate != null ? "creditDate=" + creditDate + ", " : "")
				+ (creditStatus != null ? "creditStatus=" + creditStatus + ", "
						: "")
				+ (disputeCategory != null ? "disputeCategory="
						+ disputeCategory + ", " : "")
				+ (disputeNumber != null ? "disputeNumber=" + disputeNumber
						+ ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoice != null ? "invoice=" + invoice + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (notes != null ? "notes=" + notes + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (referenceNumber != null ? "referenceNumber="
						+ referenceNumber + ", " : "")
				+ (statusTimestamp != null ? "statusTimestamp="
						+ statusTimestamp + ", " : "")
				+ (ticketNumber != null ? "ticketNumber=" + ticketNumber + ", "
						: "") + (vendor != null ? "vendor=" + vendor : "")
				+ "]";
	}
 

}