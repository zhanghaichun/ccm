package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractProposal entity provides the base persistence definition of the
 * Proposal entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractProposal implements java.io.Serializable {

	// Fields

	private Long id;
	private Proposal parentProposal;
	private AttachmentPoint attachmentPoint;
	private Originator originator;
	private Dispute dispute;
	private AccountCode accountCode;
	private DisputeReason disputeReason;
	private InvoiceItem invoiceItem;
	private Invoice invoice;
	private Payment payment;
	private ScoaSource scoaSource;
	private Double paymentAmount;
	private Double disputeAmount;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private String description;
	private String notes;
	private String disputeReasonText;

	private String proposalFlag;
	private String itemName;
	private String circuitNumber;
	private String serviceType;
	private String chargeType;

	private ItemType itemType;
	
	
	private Double creditAmount;
	private Double balanceAmount;
	private Double rate;
	private Double quantity;
	private Double minutes;
	private Double numberOfCalls;
	private Double mileage;
	
	private String address;
	private String usoc;
	private String usocDescription;
	private String country;
	private String direction;
	private String billingNumber;
	private String asg;
	private String jurisdiction;
	private String cellularIndicator;
	

	// Constructors

	/** default constructor */
	public AbstractProposal() {
	}

	public String getProposalFlag() {
		return proposalFlag;
	}

	public void setProposalFlag(String proposalFlag) {
		this.proposalFlag = proposalFlag;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getCircuitNumber() {
		return circuitNumber;
	}

	public void setCircuitNumber(String circuitNumber) {
		this.circuitNumber = circuitNumber;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getChargeType() {
		return chargeType;
	}

	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}

	public ItemType getItemType() {
		return itemType;
	}

	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}

	public Proposal getParentProposal() {
		return parentProposal;
	}

	public void setParentProposal(Proposal parentProposal) {
		this.parentProposal = parentProposal;
	}

	/** full constructor */
	public AbstractProposal(AttachmentPoint attachmentPoint,
			Originator originator, Dispute dispute, AccountCode accountCode,
			DisputeReason disputeReason, InvoiceItem invoiceItem,
			Invoice invoice, Payment payment,ScoaSource scoaSource, Double paymentAmount,
			Double disputeAmount, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag,
			String description) {
		this.attachmentPoint = attachmentPoint;
		this.originator = originator;
		this.dispute = dispute;
		this.accountCode = accountCode;
		this.disputeReason = disputeReason;
		this.invoiceItem = invoiceItem;
		this.invoice = invoice;
		this.payment = payment;
		this.scoaSource = scoaSource;
		this.paymentAmount = paymentAmount;
		this.disputeAmount = disputeAmount;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.description = description;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AttachmentPoint getAttachmentPoint() {
		return this.attachmentPoint;
	}

	public void setAttachmentPoint(AttachmentPoint attachmentPoint) {
		this.attachmentPoint = attachmentPoint;
	}

	public Originator getOriginator() {
		return this.originator;
	}

	public void setOriginator(Originator originator) {
		this.originator = originator;
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

	public DisputeReason getDisputeReason() {
		return this.disputeReason;
	}

	public void setDisputeReason(DisputeReason disputeReason) {
		this.disputeReason = disputeReason;
	}

	public InvoiceItem getInvoiceItem() {
		return this.invoiceItem;
	}

	public void setInvoiceItem(InvoiceItem invoiceItem) {
		this.invoiceItem = invoiceItem;
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
	
	public ScoaSource getScoaSource() {
		return scoaSource;
	}

	public void setScoaSource(ScoaSource scoaSource) {
		this.scoaSource = scoaSource;
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

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String toString() {
		return "AbstractProposal ["
				+ (accountCode != null ? "accountCode=" + accountCode + ", "
						: "")
				+ (attachmentPoint != null ? "attachmentPoint="
						+ attachmentPoint + ", " : "")
				+ (chargeType != null ? "chargeType=" + chargeType + ", " : "")
				+ (circuitNumber != null ? "circuitNumber=" + circuitNumber
						+ ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (description != null ? "description=" + description + ", "
						: "")
				+ (dispute != null ? "dispute=" + dispute + ", " : "")
				+ (disputeAmount != null ? "disputeAmount=" + disputeAmount
						+ ", " : "")
				+ (disputeReason != null ? "disputeReason=" + disputeReason
						+ ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (invoice != null ? "invoice=" + invoice + ", " : "")
				+ (invoiceItem != null ? "invoiceItem=" + invoiceItem + ", "
						: "")
				+ (itemName != null ? "itemName=" + itemName + ", " : "")
				+ (itemType != null ? "itemType=" + itemType + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (originator != null ? "originator=" + originator + ", " : "")
				+ (parentProposal != null ? "parentProposal=" + parentProposal
						+ ", " : "")
				+ (payment != null ? "payment=" + payment + ", " : "")
				+ (scoaSource != null ? "scoaSource=" + scoaSource + ", " : "")
				+ (paymentAmount != null ? "paymentAmount=" + paymentAmount
						+ ", " : "")
				+ (proposalFlag != null ? "proposalFlag=" + proposalFlag + ", "
						: "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (serviceType != null ? "serviceType=" + serviceType : "")
				+ "]";
	}

	public Double getCreditAmount() {
		return creditAmount;
	}

	public void setCreditAmount(Double creditAmount) {
		this.creditAmount = creditAmount;
	}

	public Double getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(Double balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getQuantity() {
		return quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getMinutes() {
		return minutes;
	}

	public void setMinutes(Double minutes) {
		this.minutes = minutes;
	}

	public Double getNumberOfCalls() {
		return numberOfCalls;
	}

	public void setNumberOfCalls(Double numberOfCalls) {
		this.numberOfCalls = numberOfCalls;
	}

	public Double getMileage() {
		return mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getUsoc() {
		return usoc;
	}

	public void setUsoc(String usoc) {
		this.usoc = usoc;
	}

	public String getUsocDescription() {
		return usocDescription;
	}

	public void setUsocDescription(String usocDescription) {
		this.usocDescription = usocDescription;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getBillingNumber() {
		return billingNumber;
	}

	public void setBillingNumber(String billingNumber) {
		this.billingNumber = billingNumber;
	}

	public String getAsg() {
		return asg;
	}

	public void setAsg(String asg) {
		this.asg = asg;
	}

	public String getJurisdiction() {
		return jurisdiction;
	}

	public void setJurisdiction(String jurisdiction) {
		this.jurisdiction = jurisdiction;
	}

	public String getCellularIndicator() {
		return cellularIndicator;
	}

	public void setCellularIndicator(String cellularIndicator) {
		this.cellularIndicator = cellularIndicator;
	}

	public String getDisputeReasonText() {
		return disputeReasonText;
	}

	public void setDisputeReasonText(String disputeReasonText) {
		this.disputeReasonText = disputeReasonText;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}



}