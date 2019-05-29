package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractEmail entity provides the base persistence definition of the Email
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractEmail implements java.io.Serializable {

	// Fields

	private Integer id;
	private AttachmentPoint attachmentPoint;
	private String subject;
	private String toAddress;
	private String ccAddress;
	private String bccAddress;
	private String content;
	private String emailStatus;
	private String systemMessage;
	private Date sentDatetime;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private String emailType;
	private String replyTo;
	private String notes;
	private Integer referenceId;
 
	// Constructors

	/** default constructor */
	public AbstractEmail() {
	}

	/** full constructor */
	public AbstractEmail(AttachmentPoint attachmentPoint, String subject,
			String toAddress, String ccAddress, String bccAddress,
			String content, String emailStatus, String systemMessage,
			Date sentDatetime, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag) {
		this.attachmentPoint = attachmentPoint;
		this.subject = subject;
		this.toAddress = toAddress;
		this.ccAddress = ccAddress;
		this.bccAddress = bccAddress;
		this.content = content;
		this.emailStatus = emailStatus;
		this.systemMessage = systemMessage;
		this.sentDatetime = sentDatetime;
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

	public AttachmentPoint getAttachmentPoint() {
		return this.attachmentPoint;
	}

	public void setAttachmentPoint(AttachmentPoint attachmentPoint) {
		this.attachmentPoint = attachmentPoint;
	}

	public String getSubject() {
		return this.subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getToAddress() {
		return this.toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String getCcAddress() {
		return this.ccAddress;
	}

	public void setCcAddress(String ccAddress) {
		this.ccAddress = ccAddress;
	}

	public String getBccAddress() {
		return this.bccAddress;
	}

	public void setBccAddress(String bccAddress) {
		this.bccAddress = bccAddress;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmailStatus() {
		return this.emailStatus;
	}

	public void setEmailStatus(String emailStatus) {
		this.emailStatus = emailStatus;
	}

	public String getSystemMessage() {
		return this.systemMessage;
	}

	public void setSystemMessage(String systemMessage) {
		this.systemMessage = systemMessage;
	}

	public Date getSentDatetime() {
		return this.sentDatetime;
	}

	public void setSentDatetime(Date sentDatetime) {
		this.sentDatetime = sentDatetime;
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
		return "AbstractEmail ["
				+ (attachmentPoint != null ? "attachmentPoint="
						+ attachmentPoint + ", " : "")
				+ (bccAddress != null ? "bccAddress=" + bccAddress + ", " : "")
				+ (ccAddress != null ? "ccAddress=" + ccAddress + ", " : "")
				+ (content != null ? "content=" + content + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (emailStatus != null ? "emailStatus=" + emailStatus + ", "
						: "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (sentDatetime != null ? "sentDatetime=" + sentDatetime + ", "
						: "")
				+ (subject != null ? "subject=" + subject + ", " : "")
				+ (systemMessage != null ? "systemMessage=" + systemMessage
						+ ", " : "")
				+ (toAddress != null ? "toAddress=" + toAddress : "") + "]";
	}

	public String getEmailType() {
		return emailType;
	}

	public void setEmailType(String emailType) {
		this.emailType = emailType;
	}

	public Integer getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Integer referenceId) {
		this.referenceId = referenceId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getReplyTo() {
		return replyTo;
	}

	public void setReplyTo(String replyTo) {
		this.replyTo = replyTo;
	}

}