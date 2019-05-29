package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractUserQuicklink entity provides the base persistence definition of the
 * UserQuicklink entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserQuicklink implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String quicklinkType;
	private String quicklinkName;
	private String requestString;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractUserQuicklink() {
	}

	/** minimal constructor */
	public AbstractUserQuicklink(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractUserQuicklink(Integer id, User user, String quicklinkName,
			String requestString, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag) {
		this.id = id;
		this.user = user;
		this.quicklinkName = quicklinkName;
		this.requestString = requestString;
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

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getQuicklinkName() {
		return this.quicklinkName;
	}

	public void setQuicklinkName(String quicklinkName) {
		this.quicklinkName = quicklinkName;
	}

	public String getRequestString() {
		return this.requestString;
	}

	public void setRequestString(String requestString) {
		this.requestString = requestString;
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

	public String getQuicklinkType() {
		return quicklinkType;
	}

	public void setQuicklinkType(String quicklinkType) {
		this.quicklinkType = quicklinkType;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractUserQuicklink [");
		if (createdBy != null)
			builder.append("createdBy=").append(createdBy).append(", ");
		if (createdTimestamp != null)
			builder.append("createdTimestamp=").append(createdTimestamp)
					.append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (modifiedBy != null)
			builder.append("modifiedBy=").append(modifiedBy).append(", ");
		if (modifiedTimestamp != null)
			builder.append("modifiedTimestamp=").append(modifiedTimestamp)
					.append(", ");
		if (quicklinkName != null)
			builder.append("quicklinkName=").append(quicklinkName).append(", ");
		if (quicklinkType != null)
			builder.append("quicklinkType=").append(quicklinkType).append(", ");
		if (recActiveFlag != null)
			builder.append("recActiveFlag=").append(recActiveFlag).append(", ");
		if (requestString != null)
			builder.append("requestString=").append(requestString).append(", ");
		if (user != null)
			builder.append("user=").append(user);
		builder.append("]");
		return builder.toString();
	}



}