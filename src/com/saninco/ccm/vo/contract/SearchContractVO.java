package com.saninco.ccm.vo.contract;

import java.io.Serializable;
import java.util.Date;

import com.saninco.ccm.vo.SearchVO;

public class SearchContractVO extends SearchVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1921389964027595017L;

	private String internalId; // Internal id #
	private String supplierContractNumber; // Supplier master contract #
	private String nameOfAgreement; // Name of agreement
	private String carrierEntityName; // Carrier entity name
	private String carrierAddress; // Carrier address
	private String rogersLegalEntityName; // Rogers legal entity name
	private String carrierType; // Carrier type
	private String agreementType; // Agreement type
	private String productsOrServices; // Products/Services
	private String schedules; // schedules
	private String termCombined; // TERM

	private String amendmentDate; // Amendment date.
	private String contractSignedDate; // Contract signed date.
	private String termExpiry; // Term expiry.
	
	private String circuitNumber;
	private String terminationDate;
	/**
	 * @return the internalId
	 */
	public String getInternalId() {
		return internalId;
	}
	/**
	 * @param internalId the internalId to set
	 */
	public void setInternalId(String internalId) {
		this.internalId = internalId;
	}
	/**
	 * @return the supplierContractNumber
	 */
	public String getSupplierContractNumber() {
		return supplierContractNumber;
	}
	/**
	 * @param supplierContractNumber the supplierContractNumber to set
	 */
	public void setSupplierContractNumber(String supplierContractNumber) {
		this.supplierContractNumber = supplierContractNumber;
	}
	/**
	 * @return the nameOfAgreement
	 */
	public String getNameOfAgreement() {
		return nameOfAgreement;
	}
	/**
	 * @param nameOfAgreement the nameOfAgreement to set
	 */
	public void setNameOfAgreement(String nameOfAgreement) {
		this.nameOfAgreement = nameOfAgreement;
	}
	/**
	 * @return the carrierEntityName
	 */
	public String getCarrierEntityName() {
		return carrierEntityName;
	}
	/**
	 * @param carrierEntityName the carrierEntityName to set
	 */
	public void setCarrierEntityName(String carrierEntityName) {
		this.carrierEntityName = carrierEntityName;
	}
	/**
	 * @return the carrierAddress
	 */
	public String getCarrierAddress() {
		return carrierAddress;
	}
	/**
	 * @param carrierAddress the carrierAddress to set
	 */
	public void setCarrierAddress(String carrierAddress) {
		this.carrierAddress = carrierAddress;
	}
	/**
	 * @return the rogersLegalEntityName
	 */
	public String getRogersLegalEntityName() {
		return rogersLegalEntityName;
	}
	/**
	 * @param rogersLegalEntityName the rogersLegalEntityName to set
	 */
	public void setRogersLegalEntityName(String rogersLegalEntityName) {
		this.rogersLegalEntityName = rogersLegalEntityName;
	}
	/**
	 * @return the carrierType
	 */
	public String getCarrierType() {
		return carrierType;
	}
	/**
	 * @param carrierType the carrierType to set
	 */
	public void setCarrierType(String carrierType) {
		this.carrierType = carrierType;
	}
	/**
	 * @return the agreementType
	 */
	public String getAgreementType() {
		return agreementType;
	}
	/**
	 * @param agreementType the agreementType to set
	 */
	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}
	/**
	 * @return the productsOrServices
	 */
	public String getProductsOrServices() {
		return productsOrServices;
	}
	/**
	 * @param productsOrServices the productsOrServices to set
	 */
	public void setProductsOrServices(String productsOrServices) {
		this.productsOrServices = productsOrServices;
	}
	/**
	 * @return the schedules
	 */
	public String getSchedules() {
		return schedules;
	}
	/**
	 * @param schedules the schedules to set
	 */
	public void setSchedules(String schedules) {
		this.schedules = schedules;
	}
	/**
	 * @return the termCombined
	 */
	public String getTermCombined() {
		return termCombined;
	}
	/**
	 * @param termCombined the termCombined to set
	 */
	public void setTermCombined(String termCombined) {
		this.termCombined = termCombined;
	}
	/**
	 * @return the amendmentDate
	 */
	public String getAmendmentDate() {
		return amendmentDate;
	}
	/**
	 * @param amendmentDate the amendmentDate to set
	 */
	public void setAmendmentDate(String amendmentDate) {
		this.amendmentDate = amendmentDate;
	}
	/**
	 * @return the contractSignedDate
	 */
	public String getContractSignedDate() {
		return contractSignedDate;
	}
	/**
	 * @param contractSignedDate the contractSignedDate to set
	 */
	public void setContractSignedDate(String contractSignedDate) {
		this.contractSignedDate = contractSignedDate;
	}
	/**
	 * @return the termExpiry
	 */
	public String getTermExpiry() {
		return termExpiry;
	}
	/**
	 * @param termExpiry the termExpiry to set
	 */
	public void setTermExpiry(String termExpiry) {
		this.termExpiry = termExpiry;
	}
	
	public String getCircuitNumber() {
		return circuitNumber;
	}
	public void setCircuitNumber(String circuitNumber) {
		this.circuitNumber = circuitNumber;
	}
	public String getTerminationDate() {
		return terminationDate;
	}
	public void setTerminationDate(String terminationDate) {
		this.terminationDate = terminationDate;
	}
	
	



}