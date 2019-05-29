package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractInvoice entity provides the base persistence definition of the
 * Invoice entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractInvoice implements java.io.Serializable {

	// Fields

	private Integer id;
	private InvoiceStatus invoiceStatus;
	private WorkflowAction workflowAction;
	private AttachmentPoint attachmentPoint;
	private User userByWorkflowUserId;
	private InternalInvoiceStatus internalInvoiceStatus;
	private Vendor vendor;
	private Ban ban;
	private User userByAssignmentUserId;
	private InvoiceStructure invoiceStructure;
 
	private String invoiceNumber;
	private Date invoiceReceiveDate;
	private Date invoiceLoadDate;
	private Date invoiceDate;
	private Date invoiceDueDate;
	private Double invoicePreviousBalance;
	private Double invoicePreviousPayment;
	private Double invoiceBalanceForward;
	private Double invoiceAdjustment;
	private Double invoiceLatePaymentCharge;
	private Double invoiceCredit;
	private Double invoiceCurrentDue;
	private Double invoiceMinDue;
	private Double invoiceTotalDue;
	private String flagWorkspace;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private Double lastTotalDue;
	private Double lastPayment;
	private Double lastDispute;
	private Double invoiceMrc;
	private Double invoiceOcc;
	private Double invoiceUsage;
	private Double invoiceTax;
	private Double invoiceSurcharge;
	private Double invoiceRegulationFee;
	private Integer assignedAnalystId;
	private Date statusTimestamp;
	private Date invoiceExpectingDate;
	private Double paymentAmount;
	private Double disputeAmount;
	private Double miscAdjustmentAmount;
	private Double calculatedCurrentDue;
	private Double facepageDifference;
	private Double invoicePreviousAdjustment;
	private String barCode;
	private Integer histInvoiceId;
	private String notes;
	private String primaryNumber;
	private Date invoiceStartDate;
	private Date invoiceEndDate;
	private String boxNumber;
	private String numberOfBoxes;
	
	private String messageId;
	private String channelId;
	
	private Set originals = new HashSet(0);
	private Set invoiceLabels = new HashSet(0);
	private Set relativeInvoicesForInvoice1Id = new HashSet(0);
	private Set transactionHistories = new HashSet(0);
	private Set payments = new HashSet(0);
	private Set receivedPayments = new HashSet(0);
	private Set disputes = new HashSet(0);
	private Set emails = new HashSet(0);
	private Set proposals = new HashSet(0);
	private Set relativeInvoicesForInvoice2Id = new HashSet(0);
	private Set credits = new HashSet(0);
	private Set invoiceItems = new HashSet(0);
	private Set invoiceNotes=new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractInvoice() {
	}

	public Double getCalculatedCurrentDue() {
		return calculatedCurrentDue;
	}

	public void setCalculatedCurrentDue(Double calculatedCurrentDue) {
		this.calculatedCurrentDue = calculatedCurrentDue;
	}

	public Double getFacepageDifference() {
		return facepageDifference;
	}

	public void setFacepageDifference(Double facepageDifference) {
		this.facepageDifference = facepageDifference;
	}

	public Set getEmails() {
		return emails;
	}

	public void setEmails(Set emails) {
		this.emails = emails;
	}

	/** full constructor */
	public AbstractInvoice(InvoiceStatus invoiceStatus,
			WorkflowAction workflowAction, AttachmentPoint attachmentPoint,
			User userByWorkflowUserId,
			InternalInvoiceStatus internalInvoiceStatus, Vendor vendor,
			Ban ban, User userByAssignmentUserId, String invoiceNumber,
			Date invoiceReceiveDate, Date invoiceLoadDate, Date invoiceDate,
			Date invoiceDueDate, Double invoicePreviousBalance,
			Double invoicePreviousPayment, Double invoiceBalanceForward,
			Double invoiceAdjustment, Double invoiceLatePaymentCharge,
			Double invoiceCredit, Double invoiceCurrentDue,
			Double invoiceMinDue, Double invoiceTotalDue, String flagWorkspace,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Double lastTotalDue,
			Double lastPayment, Double lastDispute, Double invoiceMrc,
			Double invoiceOcc, Double invoiceUsage, Double invoiceTax,
			Double invoiceSurcharge, Double invoiceRegulationFee,
			Integer assignedAnalystId, Date statusTimestamp,
			Date invoiceExpectingDate, Double paymentAmount,
			Double disputeAmount, Double miscAdjustmentAmount, String barCode,
			Integer histInvoiceId, Integer histBanId, Integer histVendorId,
			String notes, Date invoiceStartDate, Date invoiceEndDate,
			String boxNumber,String numberOfBoxes,
			Set originals, Set invoiceLabels,
			Set relativeInvoicesForInvoice1Id, Set transactionHistories,
			Set payments, Set receivedPayments, Set disputes, Set proposals,
			Set relativeInvoicesForInvoice2Id, Set credits, Set invoiceItems,Set invoiceNotes,
			String messageId,String channelId) {
		this.invoiceStatus = invoiceStatus;
		this.workflowAction = workflowAction;
		this.attachmentPoint = attachmentPoint;
		this.userByWorkflowUserId = userByWorkflowUserId;
		this.internalInvoiceStatus = internalInvoiceStatus;
		this.vendor = vendor;
		this.ban = ban;
		this.userByAssignmentUserId = userByAssignmentUserId;
		this.invoiceNumber = invoiceNumber;
		this.invoiceReceiveDate = invoiceReceiveDate;
		this.invoiceLoadDate = invoiceLoadDate;
		this.invoiceDate = invoiceDate;
		this.invoiceDueDate = invoiceDueDate;
		this.invoicePreviousBalance = invoicePreviousBalance;
		this.invoicePreviousPayment = invoicePreviousPayment;
		this.invoiceBalanceForward = invoiceBalanceForward;
		this.invoiceAdjustment = invoiceAdjustment;
		this.invoiceLatePaymentCharge = invoiceLatePaymentCharge;
		this.invoiceCredit = invoiceCredit;
		this.invoiceCurrentDue = invoiceCurrentDue;
		this.invoiceMinDue = invoiceMinDue;
		this.invoiceTotalDue = invoiceTotalDue;
		this.flagWorkspace = flagWorkspace;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.lastTotalDue = lastTotalDue;
		this.lastPayment = lastPayment;
		this.lastDispute = lastDispute;
		this.invoiceMrc = invoiceMrc;
		this.invoiceOcc = invoiceOcc;
		this.invoiceUsage = invoiceUsage;
		this.invoiceTax = invoiceTax;
		this.invoiceSurcharge = invoiceSurcharge;
		this.invoiceRegulationFee = invoiceRegulationFee;
		this.assignedAnalystId = assignedAnalystId;
		this.statusTimestamp = statusTimestamp;
		this.invoiceExpectingDate = invoiceExpectingDate;
		this.paymentAmount = paymentAmount;
		this.disputeAmount = disputeAmount;
		this.miscAdjustmentAmount = miscAdjustmentAmount;
		this.barCode = barCode;
		this.histInvoiceId = histInvoiceId;
		this.notes = notes;
		this.invoiceStartDate = invoiceStartDate;
		this.invoiceEndDate = invoiceEndDate;
		this.originals = originals;
		this.invoiceLabels = invoiceLabels;
		this.relativeInvoicesForInvoice1Id = relativeInvoicesForInvoice1Id;
		this.transactionHistories = transactionHistories;
		this.payments = payments;
		this.receivedPayments = receivedPayments;
		this.disputes = disputes;
		this.proposals = proposals;
		this.relativeInvoicesForInvoice2Id = relativeInvoicesForInvoice2Id;
		this.credits = credits;
		this.invoiceItems = invoiceItems;
		this.boxNumber = boxNumber;
		this.numberOfBoxes = numberOfBoxes;
		this.invoiceNotes=invoiceNotes;
		this.messageId=messageId;
		this.channelId=channelId;
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

	public User getUserByWorkflowUserId() {
		return this.userByWorkflowUserId;
	}

	public void setUserByWorkflowUserId(User userByWorkflowUserId) {
		this.userByWorkflowUserId = userByWorkflowUserId;
	}

	public InternalInvoiceStatus getInternalInvoiceStatus() {
		return this.internalInvoiceStatus;
	}

	public void setInternalInvoiceStatus(
			InternalInvoiceStatus internalInvoiceStatus) {
		this.internalInvoiceStatus = internalInvoiceStatus;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Ban getBan() {
		return this.ban;
	}

	public void setBan(Ban ban) {
		this.ban = ban;
	}

	public User getUserByAssignmentUserId() {
		return this.userByAssignmentUserId;
	}

	public void setUserByAssignmentUserId(User userByAssignmentUserId) {
		this.userByAssignmentUserId = userByAssignmentUserId;
	}

	public String getInvoiceNumber() {
		return this.invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public Date getInvoiceReceiveDate() {
		return this.invoiceReceiveDate;
	}

	public void setInvoiceReceiveDate(Date invoiceReceiveDate) {
		this.invoiceReceiveDate = invoiceReceiveDate;
	}

	public Date getInvoiceLoadDate() {
		return this.invoiceLoadDate;
	}

	public void setInvoiceLoadDate(Date invoiceLoadDate) {
		this.invoiceLoadDate = invoiceLoadDate;
	}

	public Date getInvoiceDate() {
		return this.invoiceDate;
	}

	public void setInvoiceDate(Date invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public Date getInvoiceDueDate() {
		return this.invoiceDueDate;
	}

	public void setInvoiceDueDate(Date invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}

	public Double getInvoicePreviousBalance() {
		return this.invoicePreviousBalance;
	}

	public void setInvoicePreviousBalance(Double invoicePreviousBalance) {
		this.invoicePreviousBalance = invoicePreviousBalance;
	}

	public Double getInvoicePreviousPayment() {
		return this.invoicePreviousPayment;
	}

	public void setInvoicePreviousPayment(Double invoicePreviousPayment) {
		this.invoicePreviousPayment = invoicePreviousPayment;
	}

	public Double getInvoiceBalanceForward() {
		return this.invoiceBalanceForward;
	}

	public void setInvoiceBalanceForward(Double invoiceBalanceForward) {
		this.invoiceBalanceForward = invoiceBalanceForward;
	}

	public Double getInvoiceAdjustment() {
		return this.invoiceAdjustment;
	}

	public void setInvoiceAdjustment(Double invoiceAdjustment) {
		this.invoiceAdjustment = invoiceAdjustment;
	}

	public Double getInvoiceLatePaymentCharge() {
		return this.invoiceLatePaymentCharge;
	}

	public void setInvoiceLatePaymentCharge(Double invoiceLatePaymentCharge) {
		this.invoiceLatePaymentCharge = invoiceLatePaymentCharge;
	}

	public Double getInvoiceCredit() {
		return this.invoiceCredit;
	}

	public void setInvoiceCredit(Double invoiceCredit) {
		this.invoiceCredit = invoiceCredit;
	}

	public Double getInvoiceCurrentDue() {
		return this.invoiceCurrentDue;
	}

	public void setInvoiceCurrentDue(Double invoiceCurrentDue) {
		this.invoiceCurrentDue = invoiceCurrentDue;
	}

	public Double getInvoiceMinDue() {
		return this.invoiceMinDue;
	}

	public void setInvoiceMinDue(Double invoiceMinDue) {
		this.invoiceMinDue = invoiceMinDue;
	}

	public Double getInvoiceTotalDue() {
		return this.invoiceTotalDue;
	}

	public void setInvoiceTotalDue(Double invoiceTotalDue) {
		this.invoiceTotalDue = invoiceTotalDue;
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

	public Double getLastTotalDue() {
		return this.lastTotalDue;
	}

	public void setLastTotalDue(Double lastTotalDue) {
		this.lastTotalDue = lastTotalDue;
	}

	public Double getLastPayment() {
		return this.lastPayment;
	}

	public void setLastPayment(Double lastPayment) {
		this.lastPayment = lastPayment;
	}

	public Double getLastDispute() {
		return this.lastDispute;
	}

	public void setLastDispute(Double lastDispute) {
		this.lastDispute = lastDispute;
	}

	public Double getInvoiceMrc() {
		return this.invoiceMrc;
	}

	public void setInvoiceMrc(Double invoiceMrc) {
		this.invoiceMrc = invoiceMrc;
	}

	public Double getInvoiceOcc() {
		return this.invoiceOcc;
	}

	public void setInvoiceOcc(Double invoiceOcc) {
		this.invoiceOcc = invoiceOcc;
	}

	public Double getInvoiceUsage() {
		return this.invoiceUsage;
	}

	public void setInvoiceUsage(Double invoiceUsage) {
		this.invoiceUsage = invoiceUsage;
	}

	public Double getInvoiceTax() {
		return this.invoiceTax;
	}

	public void setInvoiceTax(Double invoiceTax) {
		this.invoiceTax = invoiceTax;
	}

	public Double getInvoiceSurcharge() {
		return this.invoiceSurcharge;
	}

	public void setInvoiceSurcharge(Double invoiceSurcharge) {
		this.invoiceSurcharge = invoiceSurcharge;
	}

	public Double getInvoiceRegulationFee() {
		return this.invoiceRegulationFee;
	}

	public void setInvoiceRegulationFee(Double invoiceRegulationFee) {
		this.invoiceRegulationFee = invoiceRegulationFee;
	}

	public Integer getAssignedAnalystId() {
		return this.assignedAnalystId;
	}

	public void setAssignedAnalystId(Integer assignedAnalystId) {
		this.assignedAnalystId = assignedAnalystId;
	}

	public Date getStatusTimestamp() {
		return this.statusTimestamp;
	}

	public void setStatusTimestamp(Date statusTimestamp) {
		this.statusTimestamp = statusTimestamp;
	}

	public Date getInvoiceExpectingDate() {
		return this.invoiceExpectingDate;
	}

	public void setInvoiceExpectingDate(Date invoiceExpectingDate) {
		this.invoiceExpectingDate = invoiceExpectingDate;
	}

	public Double getPaymentAmount() {
		return this.paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public Double getDisputeAmount() {
		return this.disputeAmount;
	}

	public void setDisputeAmount(Double disputeAmount) {
		this.disputeAmount = disputeAmount;
	}

	public Double getMiscAdjustmentAmount() {
		return this.miscAdjustmentAmount;
	}

	public void setMiscAdjustmentAmount(Double miscAdjustmentAmount) {
		this.miscAdjustmentAmount = miscAdjustmentAmount;
	}

	public String getBarCode() {
		return this.barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public Integer getHistInvoiceId() {
		return this.histInvoiceId;
	}

	public void setHistInvoiceId(Integer histInvoiceId) {
		this.histInvoiceId = histInvoiceId;
	}

	public String getNotes() {
		return this.notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Date getInvoiceStartDate() {
		return this.invoiceStartDate;
	}

	public void setInvoiceStartDate(Date invoiceStartDate) {
		this.invoiceStartDate = invoiceStartDate;
	}

	public Date getInvoiceEndDate() {
		return this.invoiceEndDate;
	}

	public void setInvoiceEndDate(Date invoiceEndDate) {
		this.invoiceEndDate = invoiceEndDate;
	}

	public Set getOriginals() {
		return this.originals;
	}

	public void setOriginals(Set originals) {
		this.originals = originals;
	}

	public Set getInvoiceLabels() {
		return this.invoiceLabels;
	}

	public void setInvoiceLabels(Set invoiceLabels) {
		this.invoiceLabels = invoiceLabels;
	}

	public Set getRelativeInvoicesForInvoice1Id() {
		return this.relativeInvoicesForInvoice1Id;
	}

	public void setRelativeInvoicesForInvoice1Id(
			Set relativeInvoicesForInvoice1Id) {
		this.relativeInvoicesForInvoice1Id = relativeInvoicesForInvoice1Id;
	}

	public Set getTransactionHistories() {
		return this.transactionHistories;
	}

	public void setTransactionHistories(Set transactionHistories) {
		this.transactionHistories = transactionHistories;
	}

	public Set getPayments() {
		return this.payments;
	}

	public void setPayments(Set payments) {
		this.payments = payments;
	}

	public Set getReceivedPayments() {
		return this.receivedPayments;
	}

	public void setReceivedPayments(Set receivedPayments) {
		this.receivedPayments = receivedPayments;
	}

	public Set getDisputes() {
		return this.disputes;
	}

	public void setDisputes(Set disputes) {
		this.disputes = disputes;
	}

	public Set getProposals() {
		return this.proposals;
	}

	public void setProposals(Set proposals) {
		this.proposals = proposals;
	}

	public Set getRelativeInvoicesForInvoice2Id() {
		return this.relativeInvoicesForInvoice2Id;
	}

	public void setRelativeInvoicesForInvoice2Id(
			Set relativeInvoicesForInvoice2Id) {
		this.relativeInvoicesForInvoice2Id = relativeInvoicesForInvoice2Id;
	}

	public Set getCredits() {
		return this.credits;
	}

	public void setCredits(Set credits) {
		this.credits = credits;
	}

	public Set getInvoiceItems() {
		return this.invoiceItems;
	}

	public void setInvoiceItems(Set invoiceItems) {
		this.invoiceItems = invoiceItems;
	}

	/**
	 * @return the primaryNumber
	 */
	public String getPrimaryNumber() {
		return primaryNumber;
	}

	/**
	 * @param primaryNumber
	 *            the primaryNumber to set
	 */
	public void setPrimaryNumber(String primaryNumber) {
		this.primaryNumber = primaryNumber;
	}
	
	
	

	public Set getInvoiceNotes() {
		return invoiceNotes;
	}

	public void setInvoiceNotes(Set invoiceNotes) {
		this.invoiceNotes = invoiceNotes;
	}

	
	public String toString() {
		return "AbstractInvoice ["
				+ (assignedAnalystId != null ? "assignedAnalystId="
						+ assignedAnalystId + ", " : "")
				+ (attachmentPoint != null ? "attachmentPoint="
						+ attachmentPoint + ", " : "")
				+ (ban != null ? "ban=" + ban + ", " : "")
				+ (barCode != null ? "barCode=" + barCode + ", " : "")
				+ (calculatedCurrentDue != null ? "calculatedCurrentDue="
						+ calculatedCurrentDue + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (disputeAmount != null ? "disputeAmount=" + disputeAmount
						+ ", " : "")
				+ (facepageDifference != null ? "facepageDifference="
						+ facepageDifference + ", " : "")
				+ (flagWorkspace != null ? "flagWorkspace=" + flagWorkspace
						+ ", " : "")
				+ (histInvoiceId != null ? "histInvoiceId=" + histInvoiceId
						+ ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (internalInvoiceStatus != null ? "internalInvoiceStatus="
						+ internalInvoiceStatus + ", " : "")
				+ (invoiceAdjustment != null ? "invoiceAdjustment="
						+ invoiceAdjustment + ", " : "")
				+ (invoiceBalanceForward != null ? "invoiceBalanceForward="
						+ invoiceBalanceForward + ", " : "")
				+ (invoiceCredit != null ? "invoiceCredit=" + invoiceCredit
						+ ", " : "")
				+ (invoiceCurrentDue != null ? "invoiceCurrentDue="
						+ invoiceCurrentDue + ", " : "")
				+ (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", "
						: "")
				+ (invoiceDueDate != null ? "invoiceDueDate=" + invoiceDueDate
						+ ", " : "")
				+ (invoiceEndDate != null ? "invoiceEndDate=" + invoiceEndDate
						+ ", " : "")
				+ (invoiceExpectingDate != null ? "invoiceExpectingDate="
						+ invoiceExpectingDate + ", " : "")
				+ (invoiceLatePaymentCharge != null ? "invoiceLatePaymentCharge="
						+ invoiceLatePaymentCharge + ", "
						: "")
				+ (invoiceLoadDate != null ? "invoiceLoadDate="
						+ invoiceLoadDate + ", " : "")
				+ (invoiceMinDue != null ? "invoiceMinDue=" + invoiceMinDue
						+ ", " : "")
				+ (invoiceMrc != null ? "invoiceMrc=" + invoiceMrc + ", " : "")
				+ (invoiceNumber != null ? "invoiceNumber=" + invoiceNumber
						+ ", " : "")
				+ (invoiceOcc != null ? "invoiceOcc=" + invoiceOcc + ", " : "")
				+ (invoicePreviousBalance != null ? "invoicePreviousBalance="
						+ invoicePreviousBalance + ", " : "")
				+ (invoicePreviousPayment != null ? "invoicePreviousPayment="
						+ invoicePreviousPayment + ", " : "")
				+ (invoiceReceiveDate != null ? "invoiceReceiveDate="
						+ invoiceReceiveDate + ", " : "")
				+ (invoiceRegulationFee != null ? "invoiceRegulationFee="
						+ invoiceRegulationFee + ", " : "")
				+ (invoiceStartDate != null ? "invoiceStartDate="
						+ invoiceStartDate + ", " : "")
				+ (invoiceStatus != null ? "invoiceStatus=" + invoiceStatus
						+ ", " : "")
				+ (invoiceSurcharge != null ? "invoiceSurcharge="
						+ invoiceSurcharge + ", " : "")
				+ (invoiceTax != null ? "invoiceTax=" + invoiceTax + ", " : "")
				+ (invoiceTotalDue != null ? "invoiceTotalDue="
						+ invoiceTotalDue + ", " : "")
				+ (invoiceUsage != null ? "invoiceUsage=" + invoiceUsage + ", "
						: "")
				+ (lastDispute != null ? "lastDispute=" + lastDispute + ", "
						: "")
				+ (lastPayment != null ? "lastPayment=" + lastPayment + ", "
						: "")
				+ (lastTotalDue != null ? "lastTotalDue=" + lastTotalDue + ", "
						: "")
				+ (miscAdjustmentAmount != null ? "miscAdjustmentAmount="
						+ miscAdjustmentAmount + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (notes != null ? "notes=" + notes + ", " : "")
				+ (paymentAmount != null ? "paymentAmount=" + paymentAmount
						+ ", " : "")
				+ (primaryNumber != null ? "primaryNumber=" + primaryNumber
						+ ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (statusTimestamp != null ? "statusTimestamp="
						+ statusTimestamp + ", " : "")
				+ (userByAssignmentUserId != null ? "userByAssignmentUserId="
						+ userByAssignmentUserId + ", " : "")
				+ (userByWorkflowUserId != null ? "userByWorkflowUserId="
						+ userByWorkflowUserId + ", " : "")
				+ (vendor != null ? "vendor=" + vendor + ", " : "")
				+ (workflowAction != null ? "workflowAction=" + workflowAction
						: "")
				+(invoiceNotes!=null ? "invoiceNotes=" +invoiceNotes
						: "")+ "]";
	}

	public Double getInvoicePreviousAdjustment() {
		return invoicePreviousAdjustment;
	}

	public void setInvoicePreviousAdjustment(Double invoicePreviousAdjustment) {
		this.invoicePreviousAdjustment = invoicePreviousAdjustment;
	}

	public InvoiceStructure getInvoiceStructure() {
		return invoiceStructure;
	}

	public void setInvoiceStructure(InvoiceStructure invoiceStructure) {
		this.invoiceStructure = invoiceStructure;
	}

	public String getBoxNumber() {
		return boxNumber;
	}

	public void setBoxNumber(String boxNumber) {
		this.boxNumber = boxNumber;
	}

	public String getNumberOfBoxes() {
		return numberOfBoxes;
	}

	public void setNumberOfBoxes(String numberOfBoxes) {
		this.numberOfBoxes = numberOfBoxes;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

}