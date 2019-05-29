package com.saninco.ccm.model;

import java.util.Date;


/**
 * UserActionLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class UserActionLog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private Integer userId;
	private String activityType;
	private String url;
	private String message;
	private Integer invoiceId;
	private Integer disputeId;
	private Date createdTimestamp;
	private Integer createdBy;
	private String recActiveFlag;
	
	/** default constructor */
	public UserActionLog() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(Integer invoiceId) {
		this.invoiceId = invoiceId;
	}

	public Integer getDisputeId() {
		return disputeId;
	}

	public void setDisputeId(Integer disputeId) {
		this.disputeId = disputeId;
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

	public String getRecActiveFlag() {
		return recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
