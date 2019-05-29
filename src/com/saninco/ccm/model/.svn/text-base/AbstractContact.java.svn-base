package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractContact entity provides the base persistence definition of the
 * Contact entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractContact implements java.io.Serializable {

	// Fields

	private Integer id;
	private Vendor vendor;
	private String firstName;
	private String lastName;
	private String attentionName;
	private String coName;
	private String address1;
	private String address2;
	private String city;
	private String province;
	private String country;
	private String postalCode;
	private String primaryPhone;
	private String otherPhone;
	private String officePhone;
	private String cellPhone;
	private String faxNumber;
	private String email;
	private String locationCode;
	private Integer supervisorId;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractContact() {
	}

	/** full constructor */
	public AbstractContact(Vendor vendor, String firstName, String lastName,
			String attentionName, String coName, String address1,
			String address2, String city, String province, String country,
			String postalCode, String primaryPhone, String otherPhone,
			String officePhone, String cellPhone, String faxNumber,
			String email, String locationCode, Integer supervisorId,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		this.vendor = vendor;
		this.firstName = firstName;
		this.lastName = lastName;
		this.attentionName = attentionName;
		this.coName = coName;
		this.address1 = address1;
		this.address2 = address2;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postalCode = postalCode;
		this.primaryPhone = primaryPhone;
		this.otherPhone = otherPhone;
		this.officePhone = officePhone;
		this.cellPhone = cellPhone;
		this.faxNumber = faxNumber;
		this.email = email;
		this.locationCode = locationCode;
		this.supervisorId = supervisorId;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Vendor getVendor() {
		return this.vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAttentionName() {
		return this.attentionName;
	}

	public void setAttentionName(String attentionName) {
		this.attentionName = attentionName;
	}

	public String getCoName() {
		return this.coName;
	}

	public void setCoName(String coName) {
		this.coName = coName;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPostalCode() {
		return this.postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getPrimaryPhone() {
		return this.primaryPhone;
	}

	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}

	public String getOtherPhone() {
		return this.otherPhone;
	}

	public void setOtherPhone(String otherPhone) {
		this.otherPhone = otherPhone;
	}

	public String getOfficePhone() {
		return this.officePhone;
	}

	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}

	public String getCellPhone() {
		return this.cellPhone;
	}

	public void setCellPhone(String cellPhone) {
		this.cellPhone = cellPhone;
	}

	public String getFaxNumber() {
		return this.faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocationCode() {
		return this.locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public Integer getSupervisorId() {
		return this.supervisorId;
	}

	public void setSupervisorId(Integer supervisorId) {
		this.supervisorId = supervisorId;
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

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractContact [");
		if (address1 != null)
			builder.append("address1=").append(address1).append(", ");
		if (address2 != null)
			builder.append("address2=").append(address2).append(", ");
		if (attentionName != null)
			builder.append("attentionName=").append(attentionName).append(", ");
		if (cellPhone != null)
			builder.append("cellPhone=").append(cellPhone).append(", ");
		if (city != null)
			builder.append("city=").append(city).append(", ");
		if (coName != null)
			builder.append("coName=").append(coName).append(", ");
		if (country != null)
			builder.append("country=").append(country).append(", ");
		if (createdBy != null)
			builder.append("createdBy=").append(createdBy).append(", ");
		if (createdTimestamp != null)
			builder.append("createdTimestamp=").append(createdTimestamp)
					.append(", ");
		if (email != null)
			builder.append("email=").append(email).append(", ");
		if (faxNumber != null)
			builder.append("faxNumber=").append(faxNumber).append(", ");
		if (firstName != null)
			builder.append("firstName=").append(firstName).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (lastName != null)
			builder.append("lastName=").append(lastName).append(", ");
		if (locationCode != null)
			builder.append("locationCode=").append(locationCode).append(", ");
		if (modifiedBy != null)
			builder.append("modifiedBy=").append(modifiedBy).append(", ");
		if (modifiedTimestamp != null)
			builder.append("modifiedTimestamp=").append(modifiedTimestamp)
					.append(", ");
		if (officePhone != null)
			builder.append("officePhone=").append(officePhone).append(", ");
		if (otherPhone != null)
			builder.append("otherPhone=").append(otherPhone).append(", ");
		if (postalCode != null)
			builder.append("postalCode=").append(postalCode).append(", ");
		if (primaryPhone != null)
			builder.append("primaryPhone=").append(primaryPhone).append(", ");
		if (province != null)
			builder.append("province=").append(province).append(", ");
		if (recActiveFlag != null)
			builder.append("recActiveFlag=").append(recActiveFlag).append(", ");
		if (supervisorId != null)
			builder.append("supervisorId=").append(supervisorId).append(", ");
		if (vendor != null)
			builder.append("vendor=").append(vendor);
		builder.append("]");
		return builder.toString();
	}

	

}