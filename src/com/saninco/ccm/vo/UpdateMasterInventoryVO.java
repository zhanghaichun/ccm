package com.saninco.ccm.vo;

import java.io.Serializable;

public class UpdateMasterInventoryVO  extends SearchVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4740645297641800376L;
	
	private String id;
	private String strippedCircuitNumber;
	private String uniqueCircuitId;
	private String serviceId;
	private String serviceIdMrr;
	private String serviceIdMrrProvince;
	private String circuitStatus;
	private String accessType;
	private String installDate;
	private String orderNumber;
	private String orderType;
	private String quoteNumber;
	private String disconnectionDate;
	private String validationSourceSystem;
	private String costType;
	private String serviceDescription;
	private String productCategory;
	private String subProductCategory;
	private String project;
	private String projectCategoryStatus;
	private String aStreetNumber;
	private String aStreetName;
	private String aUnit;
	private String aCity;
	private String aProvince;
	private String aPostalCode;
	private String aCountry;
	private String zStreetNumber;
	private String zStreetName;
	private String zUnit;
	private String zCity;
	private String zProvince;
	private String zPostalCode;
	private String zCountry;
	private String region;
	private String tariffPage;
	private String servingWireCentre;
	private String aggregatorCid;
	private String timeSlotVlanAssignment;
	private String comments;
	private String trunkGroupClli;
	private String customerBillingAccount;
	private String businessSegment;
	private String endUser;
	private String scoa;
	private String owner;
	private String ownerEmail;
	private String lastSignoffDate;
	private String multiplier;
	private String usoc;
	private String rate;
	private String rateEffectiveDate;
	private String circuitTerm;
	private String contractNumber;
	private String expiryDate;
	private String rateStatus;
	private String intercompanyBusinessUnit;
	private String intercompanyChannel;
	private String fsaCode;
	private String serviceabilityFibre;
	private String serviceabilityCable;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getStrippedCircuitNumber() {
		return strippedCircuitNumber;
	}

	public void setStrippedCircuitNumber(String strippedCircuitNumber) {
		this.strippedCircuitNumber = strippedCircuitNumber;
	}

	public String getUniqueCircuitId() {
		return uniqueCircuitId;
	}

	public void setUniqueCircuitId(String uniqueCircuitId) {
		this.uniqueCircuitId = uniqueCircuitId;
	}

	public String getServiceId() {
		return serviceId;
	}

	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	public String getServiceIdMrr() {
		return serviceIdMrr;
	}

	public void setServiceIdMrr(String serviceIdMrr) {
		this.serviceIdMrr = serviceIdMrr;
	}

	public String getCircuitStatus() {
		return circuitStatus;
	}

	public void setCircuitStatus(String circuitStatus) {
		this.circuitStatus = circuitStatus;
	}

	public String getAccessType() {
		return accessType;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public String getInstallDate() {
		return installDate;
	}

	public void setInstallDate(String installDate) {
		this.installDate = installDate;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public String getQuoteNumber() {
		return quoteNumber;
	}

	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}

	public String getDisconnectionDate() {
		return disconnectionDate;
	}

	public void setDisconnectionDate(String disconnectionDate) {
		this.disconnectionDate = disconnectionDate;
	}

	public String getValidationSourceSystem() {
		return validationSourceSystem;
	}

	public void setValidationSourceSystem(String validationSourceSystem) {
		this.validationSourceSystem = validationSourceSystem;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getServiceDescription() {
		return serviceDescription;
	}

	public void setServiceDescription(String serviceDescription) {
		this.serviceDescription = serviceDescription;
	}

	public String getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}

	public String getSubProductCategory() {
		return subProductCategory;
	}

	public void setSubProductCategory(String subProductCategory) {
		this.subProductCategory = subProductCategory;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public String getaStreetNumber() {
		return aStreetNumber;
	}

	public void setaStreetNumber(String aStreetNumber) {
		this.aStreetNumber = aStreetNumber;
	}

	public String getaStreetName() {
		return aStreetName;
	}

	public void setaStreetName(String aStreetName) {
		this.aStreetName = aStreetName;
	}

	public String getaUnit() {
		return aUnit;
	}

	public void setaUnit(String aUnit) {
		this.aUnit = aUnit;
	}

	public String getaCity() {
		return aCity;
	}

	public void setaCity(String aCity) {
		this.aCity = aCity;
	}

	public String getaProvince() {
		return aProvince;
	}

	public void setaProvince(String aProvince) {
		this.aProvince = aProvince;
	}

	public String getaPostalCode() {
		return aPostalCode;
	}

	public void setaPostalCode(String aPostalCode) {
		this.aPostalCode = aPostalCode;
	}

	public String getaCountry() {
		return aCountry;
	}

	public void setaCountry(String aCountry) {
		this.aCountry = aCountry;
	}

	public String getzStreetNumber() {
		return zStreetNumber;
	}

	public void setzStreetNumber(String zStreetNumber) {
		this.zStreetNumber = zStreetNumber;
	}

	public String getzStreetName() {
		return zStreetName;
	}

	public void setzStreetName(String zStreetName) {
		this.zStreetName = zStreetName;
	}

	public String getzUnit() {
		return zUnit;
	}

	public void setzUnit(String zUnit) {
		this.zUnit = zUnit;
	}

	public String getzCity() {
		return zCity;
	}

	public void setzCity(String zCity) {
		this.zCity = zCity;
	}

	public String getzProvince() {
		return zProvince;
	}

	public void setzProvince(String zProvince) {
		this.zProvince = zProvince;
	}

	public String getzPostalCode() {
		return zPostalCode;
	}

	public void setzPostalCode(String zPostalCode) {
		this.zPostalCode = zPostalCode;
	}

	public String getzCountry() {
		return zCountry;
	}

	public void setzCountry(String zCountry) {
		this.zCountry = zCountry;
	}

	public String getTariffPage() {
		return tariffPage;
	}

	public void setTariffPage(String tariffPage) {
		this.tariffPage = tariffPage;
	}

	public String getServingWireCentre() {
		return servingWireCentre;
	}

	public void setServingWireCentre(String servingWireCentre) {
		this.servingWireCentre = servingWireCentre;
	}

	public String getAggregatorCid() {
		return aggregatorCid;
	}

	public void setAggregatorCid(String aggregatorCid) {
		this.aggregatorCid = aggregatorCid;
	}

	public String getTimeSlotVlanAssignment() {
		return timeSlotVlanAssignment;
	}

	public void setTimeSlotVlanAssignment(String timeSlotVlanAssignment) {
		this.timeSlotVlanAssignment = timeSlotVlanAssignment;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getTrunkGroupClli() {
		return trunkGroupClli;
	}

	public void setTrunkGroupClli(String trunkGroupClli) {
		this.trunkGroupClli = trunkGroupClli;
	}

	public String getCustomerBillingAccount() {
		return customerBillingAccount;
	}

	public void setCustomerBillingAccount(String customerBillingAccount) {
		this.customerBillingAccount = customerBillingAccount;
	}

	public String getBusinessSegment() {
		return businessSegment;
	}

	public void setBusinessSegment(String businessSegment) {
		this.businessSegment = businessSegment;
	}

	public String getEndUser() {
		return endUser;
	}

	public void setEndUser(String endUser) {
		this.endUser = endUser;
	}

	public String getScoa() {
		return scoa;
	}

	public void setScoa(String scoa) {
		this.scoa = scoa;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwnerEmail() {
		return ownerEmail;
	}

	public void setOwnerEmail(String ownerEmail) {
		this.ownerEmail = ownerEmail;
	}

	public String getLastSignoffDate() {
		return lastSignoffDate;
	}

	public void setLastSignoffDate(String lastSignoffDate) {
		this.lastSignoffDate = lastSignoffDate;
	}

	public String getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(String multiplier) {
		this.multiplier = multiplier;
	}

	public String getUsoc() {
		return usoc;
	}

	public void setUsoc(String usoc) {
		this.usoc = usoc;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getRateEffectiveDate() {
		return rateEffectiveDate;
	}

	public void setRateEffectiveDate(String rateEffectiveDate) {
		this.rateEffectiveDate = rateEffectiveDate;
	}

	public String getCircuitTerm() {
		return circuitTerm;
	}

	public void setCircuitTerm(String circuitTerm) {
		this.circuitTerm = circuitTerm;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public String getRateStatus() {
		return rateStatus;
	}

	public void setRateStatus(String rateStatus) {
		this.rateStatus = rateStatus;
	}

	public String getIntercompanyBusinessUnit() {
		return intercompanyBusinessUnit;
	}

	public void setIntercompanyBusinessUnit(String intercompanyBusinessUnit) {
		this.intercompanyBusinessUnit = intercompanyBusinessUnit;
	}

	public String getIntercompanyChannel() {
		return intercompanyChannel;
	}

	public void setIntercompanyChannel(String intercompanyChannel) {
		this.intercompanyChannel = intercompanyChannel;
	}

	public String getProjectCategoryStatus() {
		return projectCategoryStatus;
	}

	public void setProjectCategoryStatus(String projectCategoryStatus) {
		this.projectCategoryStatus = projectCategoryStatus;
	}

	public String getServiceIdMrrProvince() {
		return serviceIdMrrProvince;
	}

	public void setServiceIdMrrProvince(String serviceIdMrrProvince) {
		this.serviceIdMrrProvince = serviceIdMrrProvince;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getFsaCode() {
		return fsaCode;
	}

	public void setFsaCode(String fsaCode) {
		this.fsaCode = fsaCode;
	}

	public String getServiceabilityFibre() {
		return serviceabilityFibre;
	}

	public void setServiceabilityFibre(String serviceabilityFibre) {
		this.serviceabilityFibre = serviceabilityFibre;
	}

	public String getServiceabilityCable() {
		return serviceabilityCable;
	}

	public void setServiceabilityCable(String serviceabilityCable) {
		this.serviceabilityCable = serviceabilityCable;
	}
	
	
	
	
	

}
