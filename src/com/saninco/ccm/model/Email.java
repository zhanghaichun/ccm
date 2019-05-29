package com.saninco.ccm.model;

import java.util.Date;

/**
 * Email entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Email extends AbstractEmail implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Email() {
	}

	/** full constructor */
	public Email(AttachmentPoint attachmentPoint, String subject,
			String toAddress, String ccAddress, String bccAddress,
			String content, String emailStatus, String systemMessage,
			Date sentDatetime, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag) {
		super(attachmentPoint, subject, toAddress, ccAddress, bccAddress,
				content, emailStatus, systemMessage, sentDatetime,
				createdTimestamp, createdBy, modifiedTimestamp, modifiedBy,
				recActiveFlag);
	}

}
