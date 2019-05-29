package com.saninco.ccm.vo.contractRules;

import java.io.Serializable;

import com.saninco.ccm.vo.SearchVO;

public class SearchContractRulesVO extends SearchVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1921389964027595017L;

    

    private String usoc;
    private String usocLongDescription;
    private String strippedCircuitNumber;
    private String subProduct;
    private String contractName;
    private String summaryVendorName;
    private String keyField;
    private String itemDescription;
    private String lineItemCode;
    private String lineItemCodeDescription;

    private String rate;
    private String effectiveDate;

    private String expirationPeriod;
	/**
	 * @return the usoc
	 */
	public String getUsoc() {
		return usoc;
	}
	/**
	 * @param usoc the usoc to set
	 */
	public void setUsoc(String usoc) {
		this.usoc = usoc;
	}
	/**
	 * @return the usocLongDescription
	 */
	public String getUsocLongDescription() {
		return usocLongDescription;
	}
	/**
	 * @param usocLongDescription the usocLongDescription to set
	 */
	public void setUsocLongDescription(String usocLongDescription) {
		this.usocLongDescription = usocLongDescription;
	}
	/**
	 * @return the strippedCircuitNumber
	 */
	public String getStrippedCircuitNumber() {
		return strippedCircuitNumber;
	}
	/**
	 * @param strippedCircuitNumber the strippedCircuitNumber to set
	 */
	public void setStrippedCircuitNumber(String strippedCircuitNumber) {
		this.strippedCircuitNumber = strippedCircuitNumber;
	}
	/**
	 * @return the subProduct
	 */
	public String getSubProduct() {
		return subProduct;
	}
	/**
	 * @param subProduct the subProduct to set
	 */
	public void setSubProduct(String subProduct) {
		this.subProduct = subProduct;
	}
	/**
	 * @return the contractName
	 */
	public String getContractName() {
		return contractName;
	}
	/**
	 * @param contractName the contractName to set
	 */
	public void setContractName(String contractName) {
		this.contractName = contractName;
	}
	/**
	 * @return the summaryVendorName
	 */
	public String getSummaryVendorName() {
		return summaryVendorName;
	}
	/**
	 * @param summaryVendorName the summaryVendorName to set
	 */
	public void setSummaryVendorName(String summaryVendorName) {
		this.summaryVendorName = summaryVendorName;
	}
	/**
	 * @return the keyField
	 */
	public String getKeyField() {
		return keyField;
	}
	/**
	 * @param keyField the keyField to set
	 */
	public void setKeyField(String keyField) {
		this.keyField = keyField;
	}
	/**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}
	/**
	 * @param itemDescription the itemDescription to set
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}
	/**
	 * @return the lineItemCode
	 */
	public String getLineItemCode() {
		return lineItemCode;
	}
	/**
	 * @param lineItemCode the lineItemCode to set
	 */
	public void setLineItemCode(String lineItemCode) {
		this.lineItemCode = lineItemCode;
	}
	/**
	 * @return the lineItemCodeDescription
	 */
	public String getLineItemCodeDescription() {
		return lineItemCodeDescription;
	}
	/**
	 * @param lineItemCodeDescription the lineItemCodeDescription to set
	 */
	public void setLineItemCodeDescription(String lineItemCodeDescription) {
		this.lineItemCodeDescription = lineItemCodeDescription;
	}
	/**
	 * @return the rate
	 */
	public String getRate() {
		return rate;
	}
	/**
	 * @param rate the rate to set
	 */
	public void setRate(String rate) {
		this.rate = rate;
	}
	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @return the expirationPeriod
	 */
	public String getExpirationPeriod() {
		return expirationPeriod;
	}
	/**
	 * @param expirationPeriod the expirationPeriod to set
	 */
	public void setExpirationPeriod(String expirationPeriod) {
		this.expirationPeriod = expirationPeriod;
	}
	
}