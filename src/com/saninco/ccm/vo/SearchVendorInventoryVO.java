package com.saninco.ccm.vo;

import java.io.Serializable;
/*
 * create by donghao.guo
 */
public class SearchVendorInventoryVO extends SearchVO implements Serializable{

	
	private static final long serialVersionUID = -7626890427937756247L;
	private Integer inventoryId;
	private Integer vendorId;
	private Integer productId;
	private Integer accessId;
	private Integer bandwidthId;
	private Integer qosId;
	private String quoteNumber;
	private String dateIssuedFrom;
	private String dateIssuedTo;
	private String expiryDateFrom;
	private String expiryDateTo;
	private Double mrcFrom;
	private Double mrcTo;
	private Double nrcFrom;
	private Double nrcTo;
	private Double constructionCostFrom;
	private Double constructionCostTo;
	private Integer termId;
	private String unit;
	private String streetNumber;
	private String streetName;
	private String city;
	private String postalCode;
	private String province;
	private String country;
	private Double latitudeFrom;
	private Double latitudeTo;
	private Double longitudeFrom;
	private Double longitudeTo;
	private String opportunityName;
	public Integer getInventoryId() {
		return inventoryId;
	}
	public void setInventoryId(Integer inventoryId) {
		this.inventoryId = inventoryId;
	}
	public Integer getVendorId() {
		return vendorId;
	}
	public void setVendorId(Integer vendorId) {
		this.vendorId = vendorId;
	}
	public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}
	public Integer getAccessId() {
		return accessId;
	}
	public void setAccessId(Integer accessId) {
		this.accessId = accessId;
	}
	public Integer getBandwidthId() {
		return bandwidthId;
	}
	public void setBandwidthId(Integer bandwidthId) {
		this.bandwidthId = bandwidthId;
	}
	public Integer getQosId() {
		return qosId;
	}
	public void setQosId(Integer qosId) {
		this.qosId = qosId;
	}
	public String getQuoteNumber() {
		return quoteNumber;
	}
	public void setQuoteNumber(String quoteNumber) {
		this.quoteNumber = quoteNumber;
	}
	public String getDateIssuedFrom() {
		String r = null;
		if (this.dateIssuedFrom != null){
			r = " str_to_date('"+ this.dateIssuedFrom +"','%Y/%m/%d') ";
		}
		return r;
	}
	public void setDateIssuedFrom(String dateIssuedFrom) {
		this.dateIssuedFrom = dateIssuedFrom;
	}
	public String getDateIssuedTo() {
		String r = null;
		if (this.dateIssuedTo != null){
			r = " str_to_date('"+ this.dateIssuedTo +"','%Y/%m/%d') ";
		}
		return r;
	}
	public void setDateIssuedTo(String dateIssuedTo) {
		this.dateIssuedTo = dateIssuedTo;
	}
	public String getExpiryDateFrom() {
		String r = null;
		if (this.expiryDateFrom != null){
			r = " str_to_date('"+ this.expiryDateFrom +"','%Y/%m/%d') ";
		}
		return r;
	}
	public void setExpiryDateFrom(String expiryDateFrom) {
		this.expiryDateFrom = expiryDateFrom;
	}
	public String getExpiryDateTo() {
		String r = null;
		if (this.expiryDateTo != null){
			r = " str_to_date('"+ this.expiryDateTo +"','%Y/%m/%d') ";
		}
		return r;
	}
	public void setExpiryDateTo(String expiryDateTo) {
		this.expiryDateTo = expiryDateTo;
	}
	public Double getMrcFrom() {
		return mrcFrom;
	}
	public void setMrcFrom(Double mrcFrom) {
		this.mrcFrom = mrcFrom;
	}
	public Double getMrcTo() {
		return mrcTo;
	}
	public void setMrcTo(Double mrcTo) {
		this.mrcTo = mrcTo;
	}
	public Double getNrcFrom() {
		return nrcFrom;
	}
	public void setNrcFrom(Double nrcFrom) {
		this.nrcFrom = nrcFrom;
	}
	public Double getNrcTo() {
		return nrcTo;
	}
	public void setNrcTo(Double nrcTo) {
		this.nrcTo = nrcTo;
	}
	public Double getConstructionCostFrom() {
		return constructionCostFrom;
	}
	public void setConstructionCostFrom(Double constructionCostFrom) {
		this.constructionCostFrom = constructionCostFrom;
	}
	public Double getConstructionCostTo() {
		return constructionCostTo;
	}
	public void setConstructionCostTo(Double constructionCostTo) {
		this.constructionCostTo = constructionCostTo;
	}
	public Integer getTermId() {
		return termId;
	}
	public void setTermId(Integer termId) {
		this.termId = termId;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getStreetNumber() {
		return streetNumber;
	}
	public void setStreetNumber(String streetNumber) {
		this.streetNumber = streetNumber;
	}
	public String getStreetName() {
		return streetName;
	}
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Double getLatitudeFrom() {
		return latitudeFrom;
	}
	public void setLatitudeFrom(Double latitudeFrom) {
		this.latitudeFrom = latitudeFrom;
	}
	public Double getLatitudeTo() {
		return latitudeTo;
	}
	public void setLatitudeTo(Double latitudeTo) {
		this.latitudeTo = latitudeTo;
	}
	public Double getLongitudeFrom() {
		return longitudeFrom;
	}
	public void setLongitudeFrom(Double longitudeFrom) {
		this.longitudeFrom = longitudeFrom;
	}
	public Double getLongitudeTo() {
		return longitudeTo;
	}
	public void setLongitudeTo(Double longitudeTo) {
		this.longitudeTo = longitudeTo;
	}
	public String getOpportunityName() {
		return opportunityName;
	}
	public void setOpportunityName(String opportunityName) {
		this.opportunityName = opportunityName;
	}
	
	
}
