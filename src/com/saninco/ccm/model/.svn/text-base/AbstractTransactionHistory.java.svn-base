package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractTransactionHistory entity provides the base persistence definition of
 * the TransactionHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTransactionHistory implements
		java.io.Serializable {

	// Fields

	private Integer id;
	private InvoiceStatus invoiceStatus;
	private WorkflowAction workflowAction;
	private AttachmentPoint attachmentPoint;
	private User user;
	private Dispute dispute;
	private InternalInvoiceStatus internalInvoiceStatus;
	private DisputeStatus disputeStatus;
	private Invoice invoice;
	private Payment payment;
	private PaymentStatus paymentStatus;
	private String notes;
	private Date createdTimestamp;
	private Integer createdBy;
	private String recActiveFlag;
	private String workflowUserName;
	
	private Double paymentAmount;
	private Double disputeAmount;
	private Double miscAdjustmentAmount;

	// Constructors

	/** default constructor */
	public AbstractTransactionHistory() {
	}

	/** full constructor */
	public AbstractTransactionHistory(InvoiceStatus invoiceStatus,
			WorkflowAction workflowAction, AttachmentPoint attachmentPoint,
			User user, Dispute dispute,
			InternalInvoiceStatus internalInvoiceStatus,
			DisputeStatus disputeStatus, Invoice invoice, Payment payment,
			PaymentStatus paymentStatus, String notes, Date createdTimestamp,
			Integer createdBy, String recActiveFlag,String workflowUserName) {
		this.invoiceStatus = invoiceStatus;
		this.workflowAction = workflowAction;
		this.attachmentPoint = attachmentPoint;
		this.user = user;
		this.dispute = dispute;
		this.internalInvoiceStatus = internalInvoiceStatus;
		this.disputeStatus = disputeStatus;
		this.invoice = invoice;
		this.payment = payment;
		this.paymentStatus = paymentStatus;
		this.notes = notes;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.recActiveFlag = recActiveFlag;
		this.workflowUserName = workflowUserName;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public InvoiceStatus getInvoiceStatus() {
		return this.invoiceStatus;
	}

	public void setInvoiceStatus(InvoiceStatus invoiceStatus) {
		this.invoiceStatus = invoiceStatus;
	}

	public WorkflowAction getWorkflowAction() {
		return this.workflowAction;
	}

	public void setWorkflowAction(WorkflowAction workflowAction) {
		this.workflowAction = workflowAction;
	}

	public AttachmentPoint getAttachmentPoint() {
		return this.attachmentPoint;
	}

	public void setAttachmentPoint(AttachmentPoint attachmentPoint) {
		this.attachmentPoint = attachmentPoint;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Dispute getDispute() {
		return this.dispute;
	}

	public void setDispute(Dispute dispute) {
		this.dispute = dispute;
	}

	public InternalInvoiceStatus getInternalInvoiceStatus() {
		return this.internalInvoiceStatus;
	}

	public void setInternalInvoiceStatus(
			InternalInvoiceStatus internalInvoiceStatus) {
		this.internalInvoiceStatus = internalInvoiceStatus;
	}

	public DisputeStatus getDisputeStatus() {
		return this.disputeStatus;
	}

	public void setDisputeStatus(DisputeStatus disputeStatus) {
		this.disputeStatus = disputeStatus;
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

	public PaymentStatus getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
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

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}
	
	public Double getPaymentAmount() {
		return paymentAmount;
	}
	
	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	
	public Double getDisputeAmount() {
		return disputeAmount;
	}
	
	public void setDisputeAmount(Double disputeAmount) {
		this.disputeAmount = disputeAmount;
	}
	
	public Double getMiscAdjustmentAmount() {
		return miscAdjustmentAmount;
	}
	
	public void setMiscAdjustmentAmount(Double miscAdjustmentAmount) {
		this.miscAdjustmentAmount = miscAdjustmentAmount;
	}

	public String getWorkflowUserName() {
		return workflowUserName;
	}

	public void setWorkflowUserName(String workflowUserName) {
		this.workflowUserName = workflowUserName;
	}

	public String toString() {
		return "AbstractTransactionHistory ["
				+ (attachmentPoint != null ? "attachmentPoint="
						+ attachmentPoint + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (dispute != null ? "dispute=" + dispute + ", " : "")
				+ (disputeStatus != null ? "disputeStatus=" + disputeStatus
						+ ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (internalInvoiceStatus != null ? "internalInvoiceStatus="
						+ internalInvoiceStatus + ", " : "")
				+ (invoice != null ? "invoice=" + invoice + ", " : "")
				+ (invoiceStatus != null ? "invoiceStatus=" + invoiceStatus
						+ ", " : "")
				+ (notes != null ? "notes=" + notes + ", " : "")
				+ (payment != null ? "payment=" + payment + ", " : "")
				+ (paymentStatus != null ? "paymentStatus=" + paymentStatus
						+ ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (workflowUserName != null ? "workflowUserName=" + workflowUserName
				+ ", " : "")
				+ (user != null ? "user=" + user + ", " : "")
				+ (workflowAction != null ? "workflowAction=" + workflowAction
						: "") + "]";
	}

}