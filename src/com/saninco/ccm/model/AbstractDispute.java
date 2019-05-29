package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractDispute entity provides the base persistence definition of the
 * Dispute entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractDispute implements java.io.Serializable {

	// Fields

	private Integer id;
	private WorkflowAction workflowAction;
	private AttachmentPoint attachmentPoint;
	private User userByWorkflowUserId;
	private Originator originator;
	private DisputeReason disputeReason;
	private DisputeStatus disputeStatus;
	private Invoice invoice;
	private User userByAssignmentUserId;
	private Double disputeBalance;
	private Double disputeAmount;
	private String disputeNumber;
	private String claimNumber;
	private String ticketNumber;
	private String notes;
	private String flagWorkspace;
	private Date createdTimestamp;
	private Integer createdBy;
	private User createdUser;
	private User modifiedUser;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private Date statusTimestamp;
	private String flagShortpaid;
	private Date closeDate;
	private Integer confidenceLevel;
	private String flagRecurring;
	private String emailCopyAddress;
	private Double reserveAmount;
	private Double outstandingReserveAmount;
	private Double winAmount;
	private Double paybackAmount;
	private Double closeAmount;
	private Date winDate;
	private Date paybackDate;
	private Integer histDisputeId;
	private String emailFlag;

	private Set transactionHistories = new HashSet(0);
	private Set reconciles = new HashSet(0);
	private Set proposals = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractDispute() {
	}

	/** full constructor */
	public AbstractDispute(WorkflowAction workflowAction,
			AttachmentPoint attachmentPoint, User userByWorkflowUserId,
			Originator originator, DisputeReason disputeReason,
			DisputeStatus disputeStatus, Invoice invoice,
			User userByAssignmentUserId, Double disputeBalance,
			Double disputeAmount, String disputeNumber, String claimNumber,
			String ticketNumber, String notes, String flagWorkspace,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Date statusTimestamp,
			String flagShortpaid, Date closeDate, Integer confidenceLevel,
			String flagRecurring, Double reserveAmount, Double winAmount,
			Double paybackAmount, Double closeAmount, Date winDate,
			Date paybackDate, Integer histDisputeId, Set transactionHistories,
			Set reconciles, Set proposals, String emailFlag) {
		this.workflowAction = workflowAction;
		this.attachmentPoint = attachmentPoint;
		this.userByWorkflowUserId = userByWorkflowUserId;
		this.originator = originator;
		this.disputeReason = disputeReason;
		this.disputeStatus = disputeStatus;
		this.invoice = invoice;
		this.userByAssignmentUserId = userByAssignmentUserId;
		this.disputeBalance = disputeBalance;
		this.disputeAmount = disputeAmount;
		this.disputeNumber = disputeNumber;
		this.claimNumber = claimNumber;
		this.ticketNumber = ticketNumber;
		this.notes = notes;
		this.flagWorkspace = flagWorkspace;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.statusTimestamp = statusTimestamp;
		this.flagShortpaid = flagShortpaid;
		this.closeDate = closeDate;
		this.confidenceLevel = confidenceLevel;
		this.flagRecurring = flagRecurring;
		this.reserveAmount = reserveAmount;
		this.winAmount = winAmount;
		this.paybackAmount = paybackAmount;
		this.closeAmount = closeAmount;
		this.winDate = winDate;
		this.paybackDate = paybackDate;
		this.histDisputeId = histDisputeId;
		this.transactionHistories = transactionHistories;
		this.reconciles = reconciles;
		this.proposals = proposals;
		this.emailFlag = emailFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	/**
	 * @return the createdUser
	 */
	public User getCreatedUser() {
		return createdUser;
	}

	/**
	 * @param createdUser
	 *            the createdUser to set
	 */
	public void setCreatedUser(User createdUser) {
		this.createdUser = createdUser;
	}

	public Originator getOriginator() {
		return this.originator;
	}

	public void setOriginator(Originator originator) {
		this.originator = originator;
	}

	public DisputeReason getDisputeReason() {
		return this.disputeReason;
	}

	public void setDisputeReason(DisputeReason disputeReason) {
		this.disputeReason = disputeReason;
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

	public User getUserByAssignmentUserId() {
		return this.userByAssignmentUserId;
	}

	public void setUserByAssignmentUserId(User userByAssignmentUserId) {
		this.userByAssignmentUserId = userByAssignmentUserId;
	}

	public Double getDisputeBalance() {
		return this.disputeBalance;
	}

	public void setDisputeBalance(Double disputeBalance) {
		this.disputeBalance = disputeBalance;
	}

	public Double getDisputeAmount() {
		return this.disputeAmount;
	}

	public void setDisputeAmount(Double disputeAmount) {
		this.disputeAmount = disputeAmount;
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

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public String getFlagShortpaid() {
		return this.flagShortpaid;
	}

	public void setFlagShortpaid(String flagShortpaid) {
		this.flagShortpaid = flagShortpaid;
	}

	public Date getCloseDate() {
		return this.closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public Integer getConfidenceLevel() {
		return this.confidenceLevel;
	}

	public void setConfidenceLevel(Integer confidenceLevel) {
		this.confidenceLevel = confidenceLevel;
	}

	public String getFlagRecurring() {
		return this.flagRecurring;
	}

	public void setFlagRecurring(String flagRecurring) {
		this.flagRecurring = flagRecurring;
	}

	public Double getReserveAmount() {
		return this.reserveAmount;
	}

	public void setReserveAmount(Double reserveAmount) {
		this.reserveAmount = reserveAmount;
	}

	public Double getWinAmount() {
		return this.winAmount;
	}

	public void setWinAmount(Double winAmount) {
		this.winAmount = winAmount;
	}

	public Double getPaybackAmount() {
		return this.paybackAmount;
	}

	public void setPaybackAmount(Double paybackAmount) {
		this.paybackAmount = paybackAmount;
	}

	public Double getCloseAmount() {
		return this.closeAmount;
	}

	public void setCloseAmount(Double closeAmount) {
		this.closeAmount = closeAmount;
	}

	public Date getWinDate() {
		return this.winDate;
	}

	public void setWinDate(Date winDate) {
		this.winDate = winDate;
	}

	public Date getPaybackDate() {
		return this.paybackDate;
	}

	public void setPaybackDate(Date paybackDate) {
		this.paybackDate = paybackDate;
	}

	public Integer getHistDisputeId() {
		return this.histDisputeId;
	}

	public void setHistDisputeId(Integer histDisputeId) {
		this.histDisputeId = histDisputeId;
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

	public Set getProposals() {
		return this.proposals;
	}

	public void setProposals(Set proposals) {
		this.proposals = proposals;
	}

	/**
	 * @return the modifiedUser
	 */
	public User getModifiedUser() {
		return modifiedUser;
	}

	/**
	 * @param modifiedUser
	 *            the modifiedUser to set
	 */
	public void setModifiedUser(User modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	/**
	 * @return the emailCopyAddress
	 */
	public String getEmailCopyAddress() {
		return emailCopyAddress;
	}

	/**
	 * @param emailCopyAddress
	 *            the emailCopyAddress to set
	 */
	public void setEmailCopyAddress(String emailCopyAddress) {
		this.emailCopyAddress = emailCopyAddress;
	}

	@Override
	public String toString() {
		return "AbstractDispute ["
				+ (attachmentPoint != null ? "attachmentPoint="
						+ attachmentPoint + ", " : "")
				+ (claimNumber != null ? "claimNumber=" + claimNumber + ", "
						: "")
				+ (closeAmount != null ? "closeAmount=" + closeAmount + ", "
						: "")
				+ (closeDate != null ? "closeDate=" + closeDate + ", " : "")
				+ (confidenceLevel != null ? "confidenceLevel="
						+ confidenceLevel + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (createdUser != null ? "createdUser=" + createdUser + ", "
						: "")
				+ (disputeAmount != null ? "disputeAmount=" + disputeAmount
						+ ", " : "")
				+ (disputeBalance != null ? "disputeBalance=" + disputeBalance
						+ ", " : "")
				+ (disputeNumber != null ? "disputeNumber=" + disputeNumber
						+ ", " : "")
				+ (disputeReason != null ? "disputeReason=" + disputeReason
						+ ", " : "")
				+ (disputeStatus != null ? "disputeStatus=" + disputeStatus
						+ ", " : "")
				+ (emailCopyAddress != null ? "emailCopyAddress="
						+ emailCopyAddress + ", " : "")
				+ (flagRecurring != null ? "flagRecurring=" + flagRecurring
						+ ", " : "")
				+ (flagShortpaid != null ? "flagShortpaid=" + flagShortpaid
						+ ", " : "")
				+ (flagWorkspace != null ? "flagWorkspace=" + flagWorkspace
						+ ", " : "")
				+ (histDisputeId != null ? "histDisputeId=" + histDisputeId
						+ ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoice != null ? "invoice=" + invoice + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (modifiedUser != null ? "modifiedUser=" + modifiedUser + ", "
						: "")
				+ (notes != null ? "notes=" + notes + ", " : "")
				+ (originator != null ? "originator=" + originator + ", " : "")
				+ (paybackAmount != null ? "paybackAmount=" + paybackAmount
						+ ", " : "")
				+ (paybackDate != null ? "paybackDate=" + paybackDate + ", "
						: "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (reserveAmount != null ? "reserveAmount=" + reserveAmount
						+ ", " : "")
				+ (statusTimestamp != null ? "statusTimestamp="
						+ statusTimestamp + ", " : "")
				+ (ticketNumber != null ? "ticketNumber=" + ticketNumber + ", "
						: "")
				+ (userByAssignmentUserId != null ? "userByAssignmentUserId="
						+ userByAssignmentUserId + ", " : "")
				+ (userByWorkflowUserId != null ? "userByWorkflowUserId="
						+ userByWorkflowUserId + ", " : "")
				+ (winAmount != null ? "winAmount=" + winAmount + ", " : "")
				+ (winDate != null ? "winDate=" + winDate + ", " : "")
				+ (emailFlag != null ? "emailFlag=" + emailFlag + ", " : "")
				+ (workflowAction != null ? "workflowAction=" + workflowAction
						: "") + "]";
	}

	public Double getOutstandingReserveAmount() {
		return outstandingReserveAmount;
	}

	public void setOutstandingReserveAmount(Double outstandingReserveAmount) {
		this.outstandingReserveAmount = outstandingReserveAmount;
	}

	public String getEmailFlag() {
		return emailFlag;
	}

	public void setEmailFlag(String emailFlag) {
		this.emailFlag = emailFlag;
	}
}