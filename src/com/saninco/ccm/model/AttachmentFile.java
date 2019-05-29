package com.saninco.ccm.model;

import java.util.Date;

/**
 * AttachmentFile entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AttachmentFile extends AbstractAttachmentFile implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AttachmentFile() {
	}

	/** full constructor */
	public AttachmentFile(AttachmentPoint attachmentPoint, String filePath,
			String fileName, String notes, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag) {
		super(attachmentPoint, filePath, fileName, notes, createdTimestamp, createdBy,
				modifiedTimestamp, modifiedBy, recActiveFlag);
	}

}
