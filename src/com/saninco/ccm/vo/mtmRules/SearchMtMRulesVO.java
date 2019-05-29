package com.saninco.ccm.vo.mtmRules;

import java.io.Serializable;

import com.saninco.ccm.vo.SearchVO;

public class SearchMtMRulesVO extends SearchVO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1921389964027595017L;

    private String summaryVendorName;
    private String ruleID;
    private String vendorName;
    private String mtmName;
    private String subProduct;
    private String keyField;
    private String chargeType;
    private String usoc;
    private String usocDescription;
    private String itemDescription;
    private String lineItemCode;
    private String lineItemCodeDescription;
    private String strippedCircuitNumber;
    private String rate;
    private String rateEffectiveDate;
    private String term;
    private String rateStatus;
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
	 * @return the vendorName
	 */
	public String getVendorName() {
		return vendorName;
	}
	/**
	 * @param vendorName the vendorName to set
	 */
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	/**
	 * @return the mtmName
	 */
	public String getMtMName() {
		return mtmName;
	}
	/**
	 * @param mtmName the tariffName to set
	 */
	public void setTariffName(String mtmName) {
		this.mtmName = mtmName;
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
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}
	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
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
	 * @return the usocDescription
	 */
	public String getUsocDescription() {
		return usocDescription;
	}
	/**
	 * @param usocDescription the usocDescription to set
	 */
	public void setUsocDescription(String usocDescription) {
		this.usocDescription = usocDescription;
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
	public String getStrippedCircuitNumber() {
		return strippedCircuitNumber;
	}
	public void setStrippedCircuitNumber(String strippedCircuitNumber) {
		this.strippedCircuitNumber = strippedCircuitNumber;
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
	public String getTerm() {
		return term;
	}
	public void setTerm(String term) {
		this.term = term;
	}
	public String getRateStatus() {
		return rateStatus;
	}
	public void setRateStatus(String rateStatus) {
		this.rateStatus = rateStatus;
	}
	public String getRuleID() {
		return ruleID;
	}
	public void setRuleID(String ruleID) {
		this.ruleID = ruleID;
	}
    
    
    

}