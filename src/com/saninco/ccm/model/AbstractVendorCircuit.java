package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractVendorCircuit entity provides the base persistence definition of the
 * VendorCircuit entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractVendorCircuit implements java.io.Serializable {

	// Fields

	private Integer id;
	private CircuitStatus circuitStatus;
	private Vendor vendor;
	private Ban ban;
	private String accountNumber;
	private String circuitNumber;
	private String strippedCircuitNumber;
	private String internalCircuitNumber;
	private String strippedInternalCircuitNumber;
	private String circuitType;
	private String circuitTypeDesc;
	private String circuitDescription;
	private String serviceType;
	private String serviceCategory;
	private Double mileage;
	private String speed;
	private String tn;
	private String length;
	private Double rate;
	private Double quantity;
	private Double mrc;
	private Double nrc;
	private Double installationCharge;
	private Double terminationCharge;
	private Double installationConstructionCharge;
	private Double installationInsideWiringCharge;
	private String billingNote;
	private Date startBillingDate;
	private Date turnUpDate;
	private Date stopBilllingDate;
	private Date installationRequestedDate;
	private Date installationPromisedDate;
	private Date installationCompletedDate;
	private Date disconnectionRequestedDate;
	private Date disconnectionPromisedDate;
	private Date disconnectionCompletedDate;
	private Date changeRequestedDate;
	private Date changePromisedDate;
	private Date changeCompletedDate;
	private Date lastUpdatedDate;
	private String installationSoNum;
	private String disconnectionSoNum;
	private String installationPon;
	private String disconnectionPon;
	private String AAddress;
	private String ACity;
	private String APostalCode;
	private String AProvince;
	private String ACountry;
	private String ACompany;
	private String ZAddress;
	private String ZCity;
	private String ZPostalCode;
	private String ZProvince;
	private String ZCountry;
	private String ZCompany;
	private String source;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String activeFlag;
	private String recActiveFlag;

	private String carrierCktId;
	private String rogersCircuitId;
	private String rogersId;
	private String customerName;
	private Date circuitActiveStartDate;
	private Date circuitDisconnectedDate;
	private String orderNumber;
	private String asrNumber;
	private String vendorPon;
	private Date firstInvoiceDate;
	private Date latestInvoiceDate;
	private Date lastModifiedDate;
	private Date contractExpireDate;
	private String billingAccountNumber;
	private String customerId;
	private String status;
	private String vendorName;
	
	
	// Constructors

	/** default constructor */
	public AbstractVendorCircuit() {
	}

	/** full constructor */
	public AbstractVendorCircuit(CircuitStatus circuitStatus, Vendor vendor,
			Ban ban, String accountNumber, String circuitNumber,
			String strippedCircuitNumber, String internalCircuitNumber,
			String strippedInternalCircuitNumber, String circuitType,
			String circuitTypeDesc, String circuitDescription, String serviceType,
			String serviceCategory, Double mileage, String speed, String tn,
			String length, Double rate, Double quantity, Double mrc,
			Double nrc, Double installationCharge, Double terminationCharge,
			Double installationConstructionCharge,
			Double installationInsideWiringCharge, String billingNote,
			Date startBillingDate, Date turnUpDate, Date stopBilllingDate,
			Date installationRequestedDate, Date installationPromisedDate,
			Date installationCompletedDate, Date disconnectionRequestedDate,
			Date disconnectionPromisedDate, Date disconnectionCompletedDate,
			Date changeRequestedDate, Date changePromisedDate,
			Date changeCompletedDate, Date lastUpdatedDate,
			String installationSoNum, String disconnectionSoNum,
			String installationPon, String disconnectionPon, String AAddress,
			String ACity, String APostalCode, String AProvince,
			String ACountry, String ACompany, String ZAddress, String ZCity,
			String ZPostalCode, String ZProvince, String ZCountry,
			String ZCompany, String source, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String activeFlag, String recActiveFlag, Date lastModifiedDate,Date contractExpireDate,
			String billAccountNumber, String customerId, String status, String vendorPon, Date circuitActiveStartDate,
			String vendorName, String customerName, Date circuitDisconnectedDate) {
		this.circuitStatus = circuitStatus;
		this.vendor = vendor;
		this.ban = ban;
		this.accountNumber = accountNumber;
		this.circuitNumber = circuitNumber;
		this.strippedCircuitNumber = strippedCircuitNumber;
		this.internalCircuitNumber = internalCircuitNumber;
		this.strippedInternalCircuitNumber = strippedInternalCircuitNumber;
		this.circuitType = circuitType;
		this.circuitTypeDesc = circuitTypeDesc;
		this.circuitDescription = circuitDescription;
		this.serviceType = serviceType;
		this.serviceCategory = serviceCategory;
		this.mileage = mileage;
		this.speed = speed;
		this.tn = tn;
		this.length = length;
		this.rate = rate;
		this.quantity = quantity;
		this.mrc = mrc;
		this.nrc = nrc;
		this.installationCharge = installationCharge;
		this.terminationCharge = terminationCharge;
		this.installationConstructionCharge = installationConstructionCharge;
		this.installationInsideWiringCharge = installationInsideWiringCharge;
		this.billingNote = billingNote;
		this.startBillingDate = startBillingDate;
		this.turnUpDate = turnUpDate;
		this.stopBilllingDate = stopBilllingDate;
		this.installationRequestedDate = installationRequestedDate;
		this.installationPromisedDate = installationPromisedDate;
		this.installationCompletedDate = installationCompletedDate;
		this.disconnectionRequestedDate = disconnectionRequestedDate;
		this.disconnectionPromisedDate = disconnectionPromisedDate;
		this.disconnectionCompletedDate = disconnectionCompletedDate;
		this.changeRequestedDate = changeRequestedDate;
		this.changePromisedDate = changePromisedDate;
		this.changeCompletedDate = changeCompletedDate;
		this.lastUpdatedDate = lastUpdatedDate;
		this.installationSoNum = installationSoNum;
		this.disconnectionSoNum = disconnectionSoNum;
		this.installationPon = installationPon;
		this.disconnectionPon = disconnectionPon;
		this.AAddress = AAddress;
		this.ACity = ACity;
		this.APostalCode = APostalCode;
		this.AProvince = AProvince;
		this.ACountry = ACountry;
		this.ACompany = ACompany;
		this.ZAddress = ZAddress;
		this.ZCity = ZCity;
		this.ZPostalCode = ZPostalCode;
		this.ZProvince = ZProvince;
		this.ZCountry = ZCountry;
		this.ZCompany = ZCompany;
		this.source = source;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.activeFlag = activeFlag;
		this.recActiveFlag = recActiveFlag;
		this.lastModifiedDate=lastModifiedDate;
		this.contractExpireDate=contractExpireDate;
		this.billingAccountNumber=billAccountNumber;
		this.customerId = customerId;
		this.status = status;
		this.vendorPon = vendorPon;
		this.circuitActiveStartDate = circuitActiveStartDate;
		this.vendorName = vendorName;
		this.customerName = customerName;
		this.circuitDisconnectedDate = circuitDisconnectedDate;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CircuitStatus getCircuitStatus() {
		return this.circuitStatus;
	}

	public void setCircuitStatus(CircuitStatus circuitStatus) {
		this.circuitStatus = circuitStatus;
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

	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
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

	public String getInternalCircuitNumber() {
		return this.internalCircuitNumber;
	}

	public void setInternalCircuitNumber(String internalCircuitNumber) {
		this.internalCircuitNumber = internalCircuitNumber;
	}

	public String getStrippedInternalCircuitNumber() {
		return this.strippedInternalCircuitNumber;
	}

	public void setStrippedInternalCircuitNumber(
			String strippedInternalCircuitNumber) {
		this.strippedInternalCircuitNumber = strippedInternalCircuitNumber;
	}

	public String getCircuitType() {
		return this.circuitType;
	}

	public void setCircuitType(String circuitType) {
		this.circuitType = circuitType;
	}

	public String getCircuitTypeDesc() {
		return this.circuitTypeDesc;
	}

	public void setCircuitTypeDesc(String circuitTypeDesc) {
		this.circuitTypeDesc = circuitTypeDesc;
	}

	
	public String getCircuitDescription() {
		return circuitDescription;
	}

	public void setCircuitDescription(String circuitDescription) {
		this.circuitDescription = circuitDescription;
	}

	public String getServiceType() {
		return this.serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getServiceCategory() {
		return this.serviceCategory;
	}

	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}

	public Double getMileage() {
		return this.mileage;
	}

	public void setMileage(Double mileage) {
		this.mileage = mileage;
	}

	public String getSpeed() {
		return this.speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getTn() {
		return this.tn;
	}

	public void setTn(String tn) {
		this.tn = tn;
	}

	public String getLength() {
		return this.length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public Double getRate() {
		return this.rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	public Double getMrc() {
		return this.mrc;
	}

	public void setMrc(Double mrc) {
		this.mrc = mrc;
	}

	public Double getNrc() {
		return this.nrc;
	}

	public void setNrc(Double nrc) {
		this.nrc = nrc;
	}

	public Double getInstallationCharge() {
		return this.installationCharge;
	}

	public void setInstallationCharge(Double installationCharge) {
		this.installationCharge = installationCharge;
	}

	public Double getTerminationCharge() {
		return this.terminationCharge;
	}

	public void setTerminationCharge(Double terminationCharge) {
		this.terminationCharge = terminationCharge;
	}

	public Double getInstallationConstructionCharge() {
		return this.installationConstructionCharge;
	}

	public void setInstallationConstructionCharge(
			Double installationConstructionCharge) {
		this.installationConstructionCharge = installationConstructionCharge;
	}

	public Double getInstallationInsideWiringCharge() {
		return this.installationInsideWiringCharge;
	}

	public void setInstallationInsideWiringCharge(
			Double installationInsideWiringCharge) {
		this.installationInsideWiringCharge = installationInsideWiringCharge;
	}

	public String getBillingNote() {
		return this.billingNote;
	}

	public void setBillingNote(String billingNote) {
		this.billingNote = billingNote;
	}

	public Date getStartBillingDate() {
		return this.startBillingDate;
	}

	public void setStartBillingDate(Date startBillingDate) {
		this.startBillingDate = startBillingDate;
	}

	public Date getTurnUpDate() {
		return this.turnUpDate;
	}

	public void setTurnUpDate(Date turnUpDate) {
		this.turnUpDate = turnUpDate;
	}

	public Date getStopBilllingDate() {
		return this.stopBilllingDate;
	}

	public void setStopBilllingDate(Date stopBilllingDate) {
		this.stopBilllingDate = stopBilllingDate;
	}

	public Date getInstallationRequestedDate() {
		return this.installationRequestedDate;
	}

	public void setInstallationRequestedDate(Date installationRequestedDate) {
		this.installationRequestedDate = installationRequestedDate;
	}

	public Date getInstallationPromisedDate() {
		return this.installationPromisedDate;
	}

	public void setInstallationPromisedDate(Date installationPromisedDate) {
		this.installationPromisedDate = installationPromisedDate;
	}

	public Date getInstallationCompletedDate() {
		return this.installationCompletedDate;
	}

	public void setInstallationCompletedDate(Date installationCompletedDate) {
		this.installationCompletedDate = installationCompletedDate;
	}

	public Date getDisconnectionRequestedDate() {
		return this.disconnectionRequestedDate;
	}

	public void setDisconnectionRequestedDate(Date disconnectionRequestedDate) {
		this.disconnectionRequestedDate = disconnectionRequestedDate;
	}

	public Date getDisconnectionPromisedDate() {
		return this.disconnectionPromisedDate;
	}

	public void setDisconnectionPromisedDate(Date disconnectionPromisedDate) {
		this.disconnectionPromisedDate = disconnectionPromisedDate;
	}

	public Date getDisconnectionCompletedDate() {
		return this.disconnectionCompletedDate;
	}

	public void setDisconnectionCompletedDate(Date disconnectionCompletedDate) {
		this.disconnectionCompletedDate = disconnectionCompletedDate;
	}

	public Date getChangeRequestedDate() {
		return this.changeRequestedDate;
	}

	public void setChangeRequestedDate(Date changeRequestedDate) {
		this.changeRequestedDate = changeRequestedDate;
	}

	public Date getChangePromisedDate() {
		return this.changePromisedDate;
	}

	public void setChangePromisedDate(Date changePromisedDate) {
		this.changePromisedDate = changePromisedDate;
	}

	public Date getChangeCompletedDate() {
		return this.changeCompletedDate;
	}

	public void setChangeCompletedDate(Date changeCompletedDate) {
		this.changeCompletedDate = changeCompletedDate;
	}

	public Date getLastUpdatedDate() {
		return this.lastUpdatedDate;
	}

	public void setLastUpdatedDate(Date lastUpdatedDate) {
		this.lastUpdatedDate = lastUpdatedDate;
	}

	public String getInstallationSoNum() {
		return this.installationSoNum;
	}

	public void setInstallationSoNum(String installationSoNum) {
		this.installationSoNum = installationSoNum;
	}

	public String getDisconnectionSoNum() {
		return this.disconnectionSoNum;
	}

	public void setDisconnectionSoNum(String disconnectionSoNum) {
		this.disconnectionSoNum = disconnectionSoNum;
	}

	public String getInstallationPon() {
		return this.installationPon;
	}

	public void setInstallationPon(String installationPon) {
		this.installationPon = installationPon;
	}

	public String getDisconnectionPon() {
		return this.disconnectionPon;
	}

	public void setDisconnectionPon(String disconnectionPon) {
		this.disconnectionPon = disconnectionPon;
	}

	public String getAAddress() {
		return this.AAddress;
	}

	public void setAAddress(String AAddress) {
		this.AAddress = AAddress;
	}

	public String getACity() {
		return this.ACity;
	}

	public void setACity(String ACity) {
		this.ACity = ACity;
	}

	public String getAPostalCode() {
		return this.APostalCode;
	}

	public void setAPostalCode(String APostalCode) {
		this.APostalCode = APostalCode;
	}

	public String getAProvince() {
		return this.AProvince;
	}

	public void setAProvince(String AProvince) {
		this.AProvince = AProvince;
	}

	public String getACountry() {
		return this.ACountry;
	}

	public void setACountry(String ACountry) {
		this.ACountry = ACountry;
	}

	public String getACompany() {
		return this.ACompany;
	}

	public void setACompany(String ACompany) {
		this.ACompany = ACompany;
	}

	public String getZAddress() {
		return this.ZAddress;
	}

	public void setZAddress(String ZAddress) {
		this.ZAddress = ZAddress;
	}

	public String getZCity() {
		return this.ZCity;
	}

	public void setZCity(String ZCity) {
		this.ZCity = ZCity;
	}

	public String getZPostalCode() {
		return this.ZPostalCode;
	}

	public void setZPostalCode(String ZPostalCode) {
		this.ZPostalCode = ZPostalCode;
	}

	public String getZProvince() {
		return this.ZProvince;
	}

	public void setZProvince(String ZProvince) {
		this.ZProvince = ZProvince;
	}

	public String getZCountry() {
		return this.ZCountry;
	}

	public void setZCountry(String ZCountry) {
		this.ZCountry = ZCountry;
	}

	public String getZCompany() {
		return this.ZCompany;
	}

	public void setZCompany(String ZCompany) {
		this.ZCompany = ZCompany;
	}

	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
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
		return recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public String getCarrierCktId() {
		return carrierCktId;
	}

	public void setCarrierCktId(String carrierCktId) {
		this.carrierCktId = carrierCktId;
	}

	public String getRogersCircuitId() {
		return rogersCircuitId;
	}

	public void setRogersCircuitId(String rogersCircuitId) {
		this.rogersCircuitId = rogersCircuitId;
	}

	public String getRogersId() {
		return rogersId;
	}

	public void setRogersId(String rogersId) {
		this.rogersId = rogersId;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public Date getCircuitActiveStartDate() {
		return circuitActiveStartDate;
	}

	public void setCircuitActiveStartDate(Date circuitActiveStartDate) {
		this.circuitActiveStartDate = circuitActiveStartDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCircuitDisconnectedDate() {
		return circuitDisconnectedDate;
	}

	public void setCircuitDisconnectedDate(Date circuitDisconnectedDate) {
		this.circuitDisconnectedDate = circuitDisconnectedDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getAsrNumber() {
		return asrNumber;
	}

	public void setAsrNumber(String asrNumber) {
		this.asrNumber = asrNumber;
	}

	public String getVendorPon() {
		return vendorPon;
	}

	public void setVendorPon(String vendorPon) {
		this.vendorPon = vendorPon;
	}

	public Date getFirstInvoiceDate() {
		return firstInvoiceDate;
	}

	public void setFirstInvoiceDate(Date firstInvoiceDate) {
		this.firstInvoiceDate = firstInvoiceDate;
	}

	public Date getLatestInvoiceDate() {
		return latestInvoiceDate;
	}

	public void setLatestInvoiceDate(Date latestInvoiceDate) {
		this.latestInvoiceDate = latestInvoiceDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public Date getContractExpireDate() {
		return contractExpireDate;
	}

	public void setContractExpireDate(Date contractExpireDate) {
		this.contractExpireDate = contractExpireDate;
	}

	public String getBillingAccountNumber() {
		return billingAccountNumber;
	}

	public void setBillingAccountNumber(String billingAccountNumber) {
		this.billingAccountNumber = billingAccountNumber;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

}