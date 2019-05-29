package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractEventJournal entity provides the base persistence definition of the
 * EventJournal entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractEventJournal implements java.io.Serializable {

	// Fields

	private Integer id;
	private String eventType;
	private String messageType;
	private String keyData;
	private String eventMessage;
	private String eventData;
	private String ipAddress;
	private Date createdTimestamp;
	private Integer createdBy;

	// Constructors

	/** default constructor */
	public AbstractEventJournal() {
	}

	/** full constructor */
	public AbstractEventJournal(String eventType, String eventMessage,
			String eventData, String ipAddress, Date createdTimestamp,
			Integer createdBy) {
		this.eventType = eventType;
		this.eventMessage = eventMessage;
		this.eventData = eventData;
		this.ipAddress = ipAddress;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEventType() {
		return this.eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getEventMessage() {
		return this.eventMessage;
	}

	public void setEventMessage(String eventMessage) {
		this.eventMessage = eventMessage;
	}

	public String getEventData() {
		return this.eventData;
	}

	public void setEventData(String eventData) {
		this.eventData = eventData;
	}

	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
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

	public String toString() {
		return "AbstractEventJournal ["
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (eventData != null ? "eventData=" + eventData + ", " : "")
				+ (eventMessage != null ? "eventMessage=" + eventMessage + ", "
						: "")
				+ (eventType != null ? "eventType=" + eventType + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (ipAddress != null ? "ipAddress=" + ipAddress : "") + "]";
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getKeyData() {
		return keyData;
	}

	public void setKeyData(String keyData) {
		this.keyData = keyData;
	}



}