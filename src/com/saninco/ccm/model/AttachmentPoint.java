package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * AttachmentPoint entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class AttachmentPoint extends AbstractAttachmentPoint implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public AttachmentPoint() {
	}

	/** full constructor */
	public AttachmentPoint(String tableName, Integer tableIdValue,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Set proposals,
			Set disputes, Set reconciles, Set payments,
			Set transactionHistories, Set invoices, Set credits,
			Set attachmentFiles, Set receivedPayments) {
		super(tableName, tableIdValue, createdTimestamp, createdBy,
				modifiedTimestamp, modifiedBy, recActiveFlag, proposals,
				disputes, reconciles, payments, transactionHistories, invoices,
				credits, attachmentFiles, receivedPayments);
	}

}
