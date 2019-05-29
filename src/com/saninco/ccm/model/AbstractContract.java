package com.saninco.ccm.model;

import java.util.Date;

public abstract  class AbstractContract implements java.io.Serializable{
	private  Integer id	;
	private  Integer circuitId;	                            
	private  Integer attachmentPointId;	
	private  String vendorName;
	private  String monthToMonth;	
	private  Date contractStart;	
	private  Date contractEnd;	
	private  String nameOfAgreement;	
	private  String serviceTerm;	
	private  String renewalTerm;	
	private  String purpose;	
	private  String serviceProductDescription;	
	private  String description;
	private  String source;	
	private  String commitment;	
	private  String revenue;	
	private  String termination;
	private  String rogersEndUser;	
	private  String customerTermKeyTerm;	
	private  String commentsTerm;	
	private  String sheet;	
	private  String contract;
	private  String asr;	
	private  String status;	
	private  String recActiveFlag;
	private  Date createdTimestamp;	
	private  Integer  createdBy;	
	private  Date modifiedTimestamp;	
	private  Integer modifiedBy;
	
	public AbstractContract() {
	}

	/** full constructor */
	public AbstractContract(Integer id,
			Integer circuitId,	                            
			Integer attachmentPointId,	
			String vendorName,
			String monthToMonth,	
			Date contractStart,	
			Date contractEnd,	
			String nameOfAgreement,	
			String serviceTerm,	
			String renewalTerm,	
			String purpose,	
			String serviceProductDescription,	
			String description,
			String source,	
			String commitment,	
			String revenue,	
			String termination,
			String rogersEndUser,	
			String customerTermKeyTerm,	
			String commentsTerm,	
			String sheet,	
			String contract,
			String asr,	
			String status,	
			String recActiveFlag,
			Date createdTimestamp,	
			Integer  createdBy,	
			Date modifiedTimestamp,	
			Integer modifiedBy){
		    this.id = id;
			this.circuitId = circuitId;           
			this.attachmentPointId = attachmentPointId;
			this.vendorName = vendorName;
			this.monthToMonth = monthToMonth;
			this.contractStart = contractStart;
			this.contractEnd = contractEnd;
			this.nameOfAgreement = nameOfAgreement;
			this.serviceTerm = serviceTerm;
			this.renewalTerm = renewalTerm;
			this.purpose = purpose;
			this.serviceProductDescription = serviceProductDescription;
			this.description = description;
			this.source = source;
			this.commitment = commitment;
			this.revenue = revenue;
			this.termination = termination;
			this.rogersEndUser = rogersEndUser;
			this.customerTermKeyTerm = customerTermKeyTerm;
			this.commentsTerm = commentsTerm;
			this.sheet = sheet;
			this.contract = contract;
			this.asr = asr;
			this.status = status;
			this.recActiveFlag = recActiveFlag;
			this.createdTimestamp = createdTimestamp;
			this.createdBy = createdBy;
			this.modifiedTimestamp = modifiedTimestamp;
			this.modifiedBy = modifiedBy;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCircuitId() {
		return circuitId;
	}

	public void setCircuitId(Integer circuitId) {
		this.circuitId = circuitId;
	}

	public Integer getAttachmentPointId() {
		return attachmentPointId;
	}

	public void setAttachmentPointId(Integer attachmentPointId) {
		this.attachmentPointId = attachmentPointId;
	}

	public String getVendorName() {
		return vendorName;
	}

	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}

	public String getMonthToMonth() {
		return monthToMonth;
	}

	public void setMonthToMonth(String monthToMonth) {
		this.monthToMonth = monthToMonth;
	}

	public Date getContractStart() {
		return contractStart;
	}

	public void setContractStart(Date contractStart) {
		this.contractStart = contractStart;
	}

	public Date getContractEnd() {
		return contractEnd;
	}

	public void setContractEnd(Date contractEnd) {
		this.contractEnd = contractEnd;
	}

	public String getNameOfAgreement() {
		return nameOfAgreement;
	}

	public void setNameOfAgreement(String nameOfAgreement) {
		this.nameOfAgreement = nameOfAgreement;
	}

	public String getServiceTerm() {
		return serviceTerm;
	}

	public void setServiceTerm(String serviceTerm) {
		this.serviceTerm = serviceTerm;
	}

	public String getRenewalTerm() {
		return renewalTerm;
	}

	public void setRenewalTerm(String renewalTerm) {
		this.renewalTerm = renewalTerm;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getServiceProductDescription() {
		return serviceProductDescription;
	}

	public void setServiceProductDescription(String serviceProductDescription) {
		this.serviceProductDescription = serviceProductDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getCommitment() {
		return commitment;
	}

	public void setCommitment(String commitment) {
		this.commitment = commitment;
	}

	public String getRevenue() {
		return revenue;
	}

	public void setRevenue(String revenue) {
		this.revenue = revenue;
	}

	public String getTermination() {
		return termination;
	}

	public void setTermination(String termination) {
		this.termination = termination;
	}

	public String getRogersEndUser() {
		return rogersEndUser;
	}

	public void setRogersEndUser(String rogersEndUser) {
		this.rogersEndUser = rogersEndUser;
	}

	public String getCustomerTermKeyTerm() {
		return customerTermKeyTerm;
	}

	public void setCustomerTermKeyTerm(String customerTermKeyTerm) {
		this.customerTermKeyTerm = customerTermKeyTerm;
	}

	public String getCommentsTerm() {
		return commentsTerm;
	}

	public void setCommentsTerm(String commentsTerm) {
		this.commentsTerm = commentsTerm;
	}

	public String getSheet() {
		return sheet;
	}

	public void setSheet(String sheet) {
		this.sheet = sheet;
	}

	public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public String getAsr() {
		return asr;
	}

	public void setAsr(String asr) {
		this.asr = asr;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRecActiveFlag() {
		return recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public Date getCreatedTimestamp() {
		return createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Integer getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTimestamp() {
		return modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

}
