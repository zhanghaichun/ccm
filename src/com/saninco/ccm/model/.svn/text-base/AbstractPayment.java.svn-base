package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractPayment entity provides the base persistence definition of the
 * Payment entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractPayment implements java.io.Serializable {

	// Fields

	private Integer id;
	private PaymentBatch paymentBatch;
	private WorkflowAction workflowAction;
	private AttachmentPoint attachmentPoint;
	private User userByWorkflowUserId;
	private Invoice invoice;
	private User userByAssignmentUserId;
	private PaymentStatus paymentStatus;
	private String paymentTransactionNumber;
	private Date paymentDate;
	private Date paidDate;
	private Double paymentAmount;
	private String paymentReferenceCode;
	private String description;
	private String flagWorkspace;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private Date statusTimestamp;
	private Integer receivedPaymentId;
	private Integer histPaymentId;

	private Set receivedPayments = new HashSet(0);
	private Set proposals = new HashSet(0);
	private Set paymentDetails = new HashSet(0);
	private Set transactionHistories = new HashSet(0);
	private Set reconciles = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractPayment() {
	}

	/** full constructor */
	public AbstractPayment(PaymentBatch paymentBatch,
			WorkflowAction workflowAction, AttachmentPoint attachmentPoint,
			User userByWorkflowUserId, Invoice invoice,
			User userByAssignmentUserId, PaymentStatus paymentStatus,
			String paymentTransactionNumber, Date paymentDate, Date paidDate,
			Double paymentAmount, String paymentReferenceCode,
			String description, String flagWorkspace, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag, Date statusTimestamp,
			Integer receivedPaymentId, Integer histPaymentId,
			Set receivedPayments, Set proposals, Set paymentDetails,
			Set transactionHistories, Set reconciles) {
		this.paymentBatch = paymentBatch;
		this.workflowAction = workflowAction;
		this.attachmentPoint = attachmentPoint;
		this.userByWorkflowUserId = userByWorkflowUserId;
		this.invoice = invoice;
		this.userByAssignmentUserId = userByAssignmentUserId;
		this.paymentStatus = paymentStatus;
		this.paymentTransactionNumber = paymentTransactionNumber;
		this.paymentDate = paymentDate;
		this.paidDate = paidDate;
		this.paymentAmount = paymentAmount;
		this.paymentReferenceCode = paymentReferenceCode;
		this.description = description;
		this.flagWorkspace = flagWorkspace;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.statusTimestamp = statusTimestamp;
		this.receivedPaymentId = receivedPaymentId;
		this.histPaymentId = histPaymentId;
		this.receivedPayments = receivedPayments;
		this.proposals = proposals;
		this.paymentDetails = paymentDetails;
		this.transactionHistories = transactionHistories;
		this.reconciles = reconciles;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public PaymentBatch getPaymentBatch() {
		return this.paymentBatch;
	}

	public void setPaymentBatch(PaymentBatch paymentBatch) {
		this.paymentBatch = paymentBatch;
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

	public User getUserByWorkflowUserId() {
		return this.userByWorkflowUserId;
	}

	public void setUserByWorkflowUserId(User userByWorkflowUserId) {
		this.userByWorkflowUserId = userByWorkflowUserId;
	}

	public Invoice getInvoice() {
		return this.invoice;
	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public User getUserByAssignmentUserId() {
		return this.userByAssignmentUserId;
	}

	public void setUserByAssignmentUserId(User userByAssignmentUserId) {
		this.userByAssignmentUserId = userByAssignmentUserId;
	}

	public PaymentStatus getPaymentStatus() {
		return this.paymentStatus;
	}

	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getPaymentTransactionNumber() {
		return this.paymentTransactionNumber;
	}

	public void setPaymentTransactionNumber(String paymentTransactionNumber) {
		this.paymentTransactionNumber = paymentTransactionNumber;
	}

	public Date getPaymentDate() {
		return this.paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
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

	public String getPaymentReferenceCode() {
		return this.paymentReferenceCode;
	}

	public void setPaymentReferenceCode(String paymentReferenceCode) {
		this.paymentReferenceCode = paymentReferenceCode;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFlagWorkspace() {
		return this.flagWorkspace;
	}

	public void setFlagWorkspace(String flagWorkspace) {
		this.flagWorkspace = flagWorkspace;
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

	public Integer getReceivedPaymentId() {
		return this.receivedPaymentId;
	}

	public void setReceivedPaymentId(Integer receivedPaymentId) {
		this.receivedPaymentId = receivedPaymentId;
	}

	public Integer getHistPaymentId() {
		return this.histPaymentId;
	}

	public void setHistPaymentId(Integer histPaymentId) {
		this.histPaymentId = histPaymentId;
	}

	public Set getReceivedPayments() {
		return this.receivedPayments;
	}

	public void setReceivedPayments(Set receivedPayments) {
		this.receivedPayments = receivedPayments;
	}

	public Set getProposals() {
		return this.proposals;
	}

	public void setProposals(Set proposals) {
		this.proposals = proposals;
	}

	public Set getPaymentDetails() {
		return this.paymentDetails;
	}

	public void setPaymentDetails(Set paymentDetails) {
		this.paymentDetails = paymentDetails;
	}

	public Set getTransactionHistories() {
		return this.transactionHistories;
	}

	public void setTransactionHistories(Set transactionHistories) {
		this.transactionHistories = transactionHistories;
	}

	public Set getReconciles() {
		return this.reconciles;
	}

	public void setReconciles(Set reconciles) {
		this.reconciles = reconciles;
	}

	@Override
	public String toString() {
		return "AbstractPayment ["
				+ (attachmentPoint != null ? "attachmentPoint="
						+ attachmentPoint + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (description != null ? "description=" + description + ", "
						: "")
				+ (flagWorkspace != null ? "flagWorkspace=" + flagWorkspace
						+ ", " : "")
				+ (histPaymentId != null ? "histPaymentId=" + histPaymentId
						+ ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoice != null ? "invoice=" + invoice + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (paidDate != null ? "paidDate=" + paidDate + ", " : "")
				+ (paymentAmount != null ? "paymentAmount=" + paymentAmount
						+ ", " : "")
				+ (paymentBatch != null ? "paymentBatch=" + paymentBatch + ", "
						: "")
				+ (paymentDate != null ? "paymentDate=" + paymentDate + ", "
						: "")
				+ (paymentReferenceCode != null ? "paymentReferenceCode="
						+ paymentReferenceCode + ", " : "")
				+ (paymentStatus != null ? "paymentStatus=" + paymentStatus
						+ ", " : "")
				+ (paymentTransactionNumber != null ? "paymentTransactionNumber="
						+ paymentTransactionNumber + ", "
						: "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (receivedPaymentId != null ? "receivedPaymentId="
						+ receivedPaymentId + ", " : "")
				+ (statusTimestamp != null ? "statusTimestamp="
						+ statusTimestamp + ", " : "")
				+ (userByAssignmentUserId != null ? "userByAssignmentUserId="
						+ userByAssignmentUserId + ", " : "")
				+ (userByWorkflowUserId != null ? "userByWorkflowUserId="
						+ userByWorkflowUserId + ", " : "")
				+ (workflowAction != null ? "workflowAction=" + workflowAction
						: "") + "]";
	}

}