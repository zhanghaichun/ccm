package com.saninco.ccm.vo.pricelist;

import java.io.Serializable;

import com.saninco.ccm.vo.SearchVO;

public class SearchPricelistVO extends SearchVO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6513371160753018124L;
	
	private String fileName;
	
	private String serviceType;
	private String effectiveDate;
	private String modeOfBand;
	/**
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}
	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	/**
	 * @return the serviceType
	 */
	public String getServiceType() {
		return serviceType;
	}
	/**
	 * @param serviceType the serviceType to set
	 */
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
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
	 * @return the modeOfBand
	 */
	public String getModeOfBand() {
		return modeOfBand;
	}
	/**
	 * @param modeOfBand the modeOfBand to set
	 */
	public void setModeOfBand(String modeOfBand) {
		this.modeOfBand = modeOfBand;
	}
	
	
}