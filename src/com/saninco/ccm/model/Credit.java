package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * Credit entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Credit extends AbstractCredit implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Credit() {
	}

	/** full constructor */
	public Credit(AttachmentPoint attachmentPoint, AccountCode accountCode,
			Vendor vendor, Invoice invoice, Ban ban, CreditStatus creditStatus,
			Double creditAmount, Double creditBalance, String referenceNumber,
			String disputeNumber, String claimNumber, String ticketNumber,
			Date creditDate, String notes, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag, Date statusTimestamp, String disputeCategory,
			String circuitNumber, Set reconciles) {
		super(attachmentPoint, accountCode, vendor, invoice, ban, creditStatus,
				creditAmount, creditBalance, referenceNumber, disputeNumber,
				claimNumber, ticketNumber, creditDate, notes, createdTimestamp,
				createdBy, modifiedTimestamp, modifiedBy, recActiveFlag,
				statusTimestamp, disputeCategory, circuitNumber, reconciles);
	}

}
