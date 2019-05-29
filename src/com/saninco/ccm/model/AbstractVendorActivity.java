package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractVendorActivity entity provides the base persistence definition of the
 * VendorActivity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractVendorActivity implements java.io.Serializable {

	// Fields

	private Integer id;
	private Ban ban;
	private OrderType orderTypeByInitialOrderTypeId;
	private OrderType orderTypeByRevisedOrderTypeId;
	private OrderStatus orderStatus;
	private OrderType orderTypeByOrderTypeId;
	private VendorCircuit vendorCircuit;
	private String accountNumber;
	private String vendorOrderNumber;
	private String circuitNumber;
	private String strippedCircuitNumber;
	private String strippedInternalCircuitNumber;
	private String asrNumber;
	private String pon;
	private String orderRevision;
	private String orderRequestor;
	private String orderSender;
	private String orderDesc;
	private String orderNote;
	private String carrierOrderNumber;
	private Date orderDate;
	private Date requestedDate;
	private Date promisedDate;
	private Date completedDate;
	private Date lastUpdatedDate;
	private String serviceCategory;
	private String project;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String activeFlag;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractVendorActivity() {
	}

	/** full constructor */
	public AbstractVendorActivity(Ban ban,
			OrderType orderTypeByInitialOrderTypeId,
			OrderType orderTypeByRevisedOrderTypeId, OrderStatus orderStatus,
			OrderType orderTypeByOrderTypeId, VendorCircuit vendorCircuit,
			String accountNumber, String vendorOrderNumber,
			String circuitNumber, String strippedCircuitNumber,
			String strippedInternalCircuitNumber, String asrNumber, String pon,
			String orderRevision, String orderRequestor, String orderSender,
			String orderDesc, String orderNote, String carrierOrderNumber,
			Date orderDate, Date requestedDate, Date promisedDate,
			Date completedDate, Date lastUpdatedDate, String serviceCategory,
			String project, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String activeFlag,
			String recActiveFlag) {
		this.ban = ban;
		this.orderTypeByInitialOrderTypeId = orderTypeByInitialOrderTypeId;
		this.orderTypeByRevisedOrderTypeId = orderTypeByRevisedOrderTypeId;
		this.orderStatus = orderStatus;
		this.orderTypeByOrderTypeId = orderTypeByOrderTypeId;
		this.vendorCircuit = vendorCircuit;
		this.accountNumber = accountNumber;
		this.vendorOrderNumber = vendorOrderNumber;
		this.circuitNumber = circuitNumber;
		this.strippedCircuitNumber = strippedCircuitNumber;
		this.strippedInternalCircuitNumber = strippedInternalCircuitNumber;
		this.asrNumber = asrNumber;
		this.pon = pon;
		this.orderRevision = orderRevision;
		this.orderRequestor = orderRequestor;
		this.orderSender = orderSender;
		this.orderDesc = orderDesc;
		this.orderNote = orderNote;
		this.carrierOrderNumber = carrierOrderNumber;
		this.orderDate = orderDate;
		this.requestedDate = requestedDate;
		this.promisedDate = promisedDate;
		this.completedDate = completedDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.serviceCategory = serviceCategory;
		this.project = project;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.activeFlag = activeFlag;
		this.recActiveFlag = recActiveFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Ban getBan() {
		return this.ban;
	}

	public void setBan(Ban ban) {
		this.ban = ban;
	}

	public OrderType getOrderTypeByInitialOrderTypeId() {
		return this.orderTypeByInitialOrderTypeId;
	}

	public void setOrderTypeByInitialOrderTypeId(
			OrderType orderTypeByInitialOrderTypeId) {
		this.orderTypeByInitialOrderTypeId = orderTypeByInitialOrderTypeId;
	}

	public OrderType getOrderTypeByRevisedOrderTypeId() {
		return this.orderTypeByRevisedOrderTypeId;
	}

	public void setOrderTypeByRevisedOrderTypeId(
			OrderType orderTypeByRevisedOrderTypeId) {
		this.orderTypeByRevisedOrderTypeId = orderTypeByRevisedOrderTypeId;
	}

	public OrderStatus getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public OrderType getOrderTypeByOrderTypeId() {
		return this.orderTypeByOrderTypeId;
	}

	public void setOrderTypeByOrderTypeId(OrderType orderTypeByOrderTypeId) {
		this.orderTypeByOrderTypeId = orderTypeByOrderTypeId;
	}

	public VendorCircuit getVendorCircuit() {
		return this.vendorCircuit;
	}

	public void setVendorCircuit(VendorCircuit vendorCircuit) {
		this.vendorCircuit = vendorCircuit;
	}

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getVendorOrderNumber() {
		return this.vendorOrderNumber;
	}

	public void setVendorOrderNumber(String vendorOrderNumber) {
		this.vendorOrderNumber = vendorOrderNumber;
	}

	public String getCircuitNumber() {
		return this.circuitNumber;
	}

	public void setCircuitNumber(String circuitNumber) {
		this.circuitNumber = circuitNumber;
	}

	public String getStrippedCircuitNumber() {
		return this.strippedCircuitNumber;
	}

	public void setStrippedCircuitNumber(String strippedCircuitNumber) {
		this.strippedCircuitNumber = strippedCircuitNumber;
	}

	public String getStrippedInternalCircuitNumber() {
		return this.strippedInternalCircuitNumber;
	}

	public void setStrippedInternalCircuitNumber(
			String strippedInternalCircuitNumber) {
		this.strippedInternalCircuitNumber = strippedInternalCircuitNumber;
	}

	public String getAsrNumber() {
		return this.asrNumber;
	}

	public void setAsrNumber(String asrNumber) {
		this.asrNumber = asrNumber;
	}

	public String getPon() {
		return this.pon;
	}

	public void setPon(String pon) {
		this.pon = pon;
	}

	public String getOrderRevision() {
		return this.orderRevision;
	}

	public void setOrderRevision(String orderRevision) {
		this.orderRevision = orderRevision;
	}

	public String getOrderRequestor() {
		return this.orderRequestor;
	}

	public void setOrderRequestor(String orderRequestor) {
		this.orderRequestor = orderRequestor;
	}

	public String getOrderSender() {
		return this.orderSender;
	}

	public void setOrderSender(String orderSender) {
		this.orderSender = orderSender;
	}

	public String getOrderDesc() {
		return this.orderDesc;
	}

	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}

	public String getOrderNote() {
		return this.orderNote;
	}

	public void setOrderNote(String orderNote) {
		this.orderNote = orderNote;
	}

	public String getCarrierOrderNumber() {
		return this.carrierOrderNumber;
	}

	public void setCarrierOrderNumber(String carrierOrderNumber) {
		this.carrierOrderNumber = carrierOrderNumber;
	}

	public Date getOrderDate() {
		return this.orderDate;
	}

	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}

	public Date getRequestedDate() {
		return this.requestedDate;
	}

	public void setRequestedDate(Date requestedDate) {
		this.requestedDate = requestedDate;
	}

	public Date getPromisedDate() {
		return this.promisedDate;
	}

	public void setPromisedDate(Date promisedDate) {
		this.promisedDate = promisedDate;
	}

	public Date getCompletedDate() {
		return this.completedDate;
	}

	public void setCompletedDate(Date completedDate) {
		this.completedDate = completedDate;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getServiceCategory() {
		return this.serviceCategory;
	}

	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	public String getProject() {
		return this.project;
	}

	public void setProject(String project) {
		this.project = project;
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

	public String getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

}