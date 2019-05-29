package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractReconcile entity provides the base persistence definition of the
 * Reconcile entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractReconcile implements java.io.Serializable {

	// Fields

	private Integer id;
	private AttachmentPoint attachmentPoint;
	private Dispute dispute;
	private AccountCode accountCode;
	private ReconcileStatus reconcileStatus;
	private Payment payment;
	private Date reconcileDate;
	private Double reconcileAmount;
	private Double releasedReserveAmount;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private String notes;
	
	private Proposal creditProposal;
	private Proposal disputeProposal;
	private Invoice creditInvoice;
	

	// Constructors

	/** default constructor */
	public AbstractReconcile() {
	}

	/** full constructor */
	public AbstractReconcile(AttachmentPoint attachmentPoint, Dispute dispute,
			AccountCode accountCode, ReconcileStatus reconcileStatus,
			Credit credit, Payment payment, Date reconcileDate,
			Double reconcileAmount, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag,
			String notes) {
		this.attachmentPoint = attachmentPoint;
		this.dispute = dispute;
		this.accountCode = accountCode;
		this.reconcileStatus = reconcileStatus; 
		this.payment = payment;
		this.reconcileDate = reconcileDate;
		this.reconcileAmount = reconcileAmount;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.notes = notes;
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

	public Dispute getDispute() {
		return this.dispute;
	}

	public void setDispute(Dispute dispute) {
		this.dispute = dispute;
	}

	public AccountCode getAccountCode() {
		return this.accountCode;
	}

	public void setAccountCode(AccountCode accountCode) {
		this.accountCode = accountCode;
	}

	public ReconcileStatus getReconcileStatus() {
		return this.reconcileStatus;
	}

	public void setReconcileStatus(ReconcileStatus reconcileStatus) {
		this.reconcileStatus = reconcileStatus;
	} 

	public Payment getPayment() {
		return this.payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public Date getReconcileDate() {
		return this.reconcileDate;
	}

	public void setReconcileDate(Date reconcileDate) {
		this.reconcileDate = reconcileDate;
	}

	public Double getReconcileAmount() {
		return this.reconcileAmount;
	}

	public void setReconcileAmount(Double reconcileAmount) {
		this.reconcileAmount = reconcileAmount;
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

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String toString() {
		return "AbstractReconcile ["
				+ (accountCode != null ? "accountCode=" + accountCode + ", "
						: "")
				+ (attachmentPoint != null ? "attachmentPoint="
						+ attachmentPoint + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "") 
				+ (dispute != null ? "dispute=" + dispute + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (notes != null ? "notes=" + notes + ", " : "")
				+ (payment != null ? "payment=" + payment + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (reconcileAmount != null ? "reconcileAmount="
						+ reconcileAmount + ", " : "")
				+ (reconcileDate != null ? "reconcileDate=" + reconcileDate
						+ ", " : "")
				+ (reconcileStatus != null ? "reconcileStatus="
						+ reconcileStatus : "") + "]";
	}

	public Proposal getCreditProposal() {
		return creditProposal;
	}

	public void setCreditProposal(Proposal creditProposal) {
		this.creditProposal = creditProposal;
	}

	public Proposal getDisputeProposal() {
		return disputeProposal;
	}

	public void setDisputeProposal(Proposal disputeProposal) {
		this.disputeProposal = disputeProposal;
	}

	public Invoice getCreditInvoice() {
		return creditInvoice;
	}

	public void setCreditInvoice(Invoice creditInvoice) {
		this.creditInvoice = creditInvoice;
	}

	public Double getReleasedReserveAmount() {
		return releasedReserveAmount;
	}

	public void setReleasedReserveAmount(Double releasedReserveAmount) {
		this.releasedReserveAmount = releasedReserveAmount;
	}


}