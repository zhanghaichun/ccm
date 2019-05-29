package com.saninco.ccm.model;

import java.io.Serializable;
import java.util.Date;

public abstract class AbstractInvoiceNotes implements Serializable {
	private Integer id;
	private Invoice invoice;
	private Ban ban;
	private String notes;
	private User user;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String rec_active_flag;
	private String privateFlag;
	private Integer attachment_point_id;
	
	public AbstractInvoiceNotes(){};
	
	public AbstractInvoiceNotes(Invoice invoice,Ban ban,String notes,User user,Date createdTimestamp
								,Integer createdBy,Date modifiedTimestamp,Integer modifiedBy,String rec_active_flag,Integer attachment_point_id,String privateFlag){
		this.invoice=invoice;
		this.ban=ban;
		this.notes=notes;
		this.user=user;
		this.createdTimestamp=createdTimestamp;
		this.createdBy=createdBy;
		this.modifiedTimestamp=modifiedTimestamp;
		this.modifiedBy=modifiedBy;
		this.rec_active_flag=rec_active_flag;
		this.attachment_point_id= attachment_point_id;
		this.privateFlag= privateFlag;
		
	}
	

	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Invoice getInvoice() {
		return invoice;
	}
	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}
	public Ban getBan() {
		return ban;
	}
	public void setBan(Ban ban) {
		this.ban = ban;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
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
	public String getRec_active_flag() {
		return rec_active_flag;
	}
	public void setRec_active_flag(String recActiveFlag) {
		rec_active_flag = recActiveFlag;
	}

	public Integer getAttachment_point_id() {
		return attachment_point_id;
	}

	public void setAttachment_point_id(Integer attachmentPointId) {
		attachment_point_id = attachmentPointId;
	}

	public String getPrivateFlag() {
		return privateFlag;
	}

	public void setPrivateFlag(String privateFlag) {
		this.privateFlag = privateFlag;
	}

	
//	public String toString() {
//		return "AbstractInvoiceNote ["
//				+ (invoice != null ? "invoice="
//						+ invoice + ", " : "")
//				+ (ban != null ? "ban="
//						+ ban + ", " : "")
//				+ (notes != null ? "notes=" + notes + ", " : "")
//				+ (user != null ? "user=" + user + ", " : "")
//				+ (createdTimestamp != null ? "createdTimestamp="
//						+ createdTimestamp + ", " : "")
//				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
//				+ (createdTimestamp != null ? "createdTimestamp="
//						+ createdTimestamp + ", " : "")
//				+ (createdBy != null ? "createdBy=" + createdBy
//						+ ", " : "")
//				+ (modifiedTimestamp != null ? "modifiedTimestamp="
//						+ modifiedTimestamp + ", " : "")
//				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy
//						+ ", " : "")
//				+ (rec_active_flag != null ? "rec_active_flag=" + rec_active_flag
//						+ ", " : "") + "]";
//	}
	
}
