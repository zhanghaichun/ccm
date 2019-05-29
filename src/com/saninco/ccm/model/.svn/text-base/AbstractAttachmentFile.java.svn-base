package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractAttachmentFile entity provides the base persistence definition of the
 * AttachmentFile entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractAttachmentFile implements java.io.Serializable {

	// Fields

	private Integer id;
	private AttachmentPoint attachmentPoint;
	private String filePath;
	private String fileName;
	private String notes;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;
	private String systemUploadFlag;
	private String telarixReportFlag;
	

	// Constructors

	/** default constructor */
	public AbstractAttachmentFile() {
	}

	/** full constructor */
	public AbstractAttachmentFile(AttachmentPoint attachmentPoint,
			String filePath, String fileName, String notes, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag) {
		this.attachmentPoint = attachmentPoint;
		this.filePath = filePath;
		this.fileName = fileName;
		this.notes = notes;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.systemUploadFlag = systemUploadFlag;
		this.telarixReportFlag = telarixReportFlag;
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

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
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

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
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

	public String getSystemUploadFlag() {
		return systemUploadFlag;
	}

	public void setSystemUploadFlag(String systemUploadFlag) {
		this.systemUploadFlag = systemUploadFlag;
	}

	public String getTelarixReportFlag() {
		return telarixReportFlag;
	}

	public void setTelarixReportFlag(String telarixReportFlag) {
		this.telarixReportFlag = telarixReportFlag;
	}

	public String toString() {
		return "AbstractAttachmentFile ["
				+ (attachmentPoint != null ? "attachmentPoint="
						+ attachmentPoint + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (fileName != null ? "fileName=" + fileName + ", " : "")
				+ (filePath != null ? "filePath=" + filePath + ", " : "")
				+ (systemUploadFlag != null ? "systemUploadFlag=" + systemUploadFlag + ", " : "")
				+ (telarixReportFlag != null ? "telarixReportFlag=" + telarixReportFlag + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						: "") + "]";
	}

}