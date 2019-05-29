package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractUserDelegation entity provides the base persistence definition of the
 * UserDelegation entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserDelegation implements java.io.Serializable {

	// Fields

	private Integer id;
	private User userByFromUserId;
	private User userByToUserId;
	private Date startDate;
	private Date endDate;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	// Constructors

	/** default constructor */
	public AbstractUserDelegation() {
	}

	/** minimal constructor */
	public AbstractUserDelegation(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractUserDelegation(Integer id, User userByFromUserId,
			User userByToUserId, Date startDate, Date endDate,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		this.id = id;
		this.userByFromUserId = userByFromUserId;
		this.userByToUserId = userByToUserId;
		this.startDate = startDate;
		this.endDate = endDate;
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

	public User getUserByFromUserId() {
		return this.userByFromUserId;
	}

	public void setUserByFromUserId(User userByFromUserId) {
		this.userByFromUserId = userByFromUserId;
	}

	public User getUserByToUserId() {
		return this.userByToUserId;
	}

	public void setUserByToUserId(User userByToUserId) {
		this.userByToUserId = userByToUserId;
	}

	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractUserDelegation [");
		if (createdBy != null)
			builder.append("createdBy=").append(createdBy).append(", ");
		if (createdTimestamp != null)
			builder.append("createdTimestamp=").append(createdTimestamp)
					.append(", ");
		if (endDate != null)
			builder.append("endDate=").append(endDate).append(", ");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (modifiedBy != null)
			builder.append("modifiedBy=").append(modifiedBy).append(", ");
		if (modifiedTimestamp != null)
			builder.append("modifiedTimestamp=").append(modifiedTimestamp)
					.append(", ");
		if (recActiveFlag != null)
			builder.append("recActiveFlag=").append(recActiveFlag).append(", ");
		if (startDate != null)
			builder.append("startDate=").append(startDate).append(", ");
		if (userByFromUserId != null)
			builder.append("userByFromUserId=").append(userByFromUserId)
					.append(", ");
		if (userByToUserId != null)
			builder.append("userByToUserId=").append(userByToUserId);
		builder.append("]");
		return builder.toString();
	}

}