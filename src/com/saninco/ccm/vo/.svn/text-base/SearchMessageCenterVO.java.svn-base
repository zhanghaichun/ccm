package com.saninco.ccm.vo;

import java.io.Serializable;

public class SearchMessageCenterVO  extends SearchVO implements Serializable{
	private static final long serialVersionUID = -7626890427937754592L;
	private Integer id;
    private Integer referenceTypeId;
    private Integer createdBy;
    private String createdTimesFrom;
    private String createdTimesTo;
    private String favoriteFlag;
    private String privateFlag;
    private String notes;
    private String reference_number;
    
	public String getReference_number() {
		return reference_number;
	}
	public void setReference_number(String referenceNumber) {
		reference_number = referenceNumber;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getReferenceTypeId() {
		return referenceTypeId;
	}
	public void setReferenceTypeId(Integer referenceTypeId) {
		this.referenceTypeId = referenceTypeId;
	}
	public Integer getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}
	public String getCreatedTimesFrom() {
		String r = null;
		if (this.createdTimesFrom != null){
			r = " str_to_date('"+ this.createdTimesFrom +"','%Y/%m/%d') ";
		}
		return r;
	}
	public void setCreatedTimesFrom(String createdTimesFrom) {
		this.createdTimesFrom = createdTimesFrom;
	}
	public String getCreatedTimesTo() {
		String r = null;
		if (this.createdTimesTo != null){
			r = " str_to_date('"+ this.createdTimesTo +"','%Y/%m/%d') ";
		}
		return r;
	}
	public void setCreatedTimesTo(String createdTimesTo) {
		this.createdTimesTo = createdTimesTo;
	}
	public String getFavoriteFlag() {
		return favoriteFlag;
	}
	public void setFavoriteFlag(String favoriteFlag) {
		this.favoriteFlag = favoriteFlag;
	}
	public String getPrivateFlag() {
		return privateFlag;
	}
	public void setPrivateFlag(String privateFlag) {
		this.privateFlag = privateFlag;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

    
    
}
