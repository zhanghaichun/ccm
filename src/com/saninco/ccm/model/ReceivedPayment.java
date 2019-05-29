package com.saninco.ccm.model;

import java.util.Date;

/**
 * ReceivedPayment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class ReceivedPayment extends AbstractReceivedPayment implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public ReceivedPayment() {
	}

	/** full constructor */
	public ReceivedPayment(AttachmentPoint attachmentPoint, Vendor vendor,
			Invoice invoice, Ban ban, Payment payment, Double paymentAmount,
			String referenceNumber, Date receivedDate, String notes,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		super(attachmentPoint, vendor, invoice, ban, payment, paymentAmount,
				referenceNumber, receivedDate, notes, createdTimestamp,
				createdBy, modifiedTimestamp, modifiedBy, recActiveFlag);
	}

}
