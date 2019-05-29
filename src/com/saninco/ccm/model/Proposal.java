package com.saninco.ccm.model;

import java.util.Date;

/**
 * Proposal entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Proposal extends AbstractProposal implements java.io.Serializable {

	private static final long serialVersionUID = 2L;

	/** default constructor */
	public Proposal() {
	}

	/** full constructor */
	public Proposal(AttachmentPoint attachmentPoint, Originator originator,
			Dispute dispute, AccountCode accountCode,
			DisputeReason disputeReason, InvoiceItem invoiceItem,
			Invoice invoice, Payment payment,ScoaSource scoaSource, Double paymentAmount,
			Double disputeAmount, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag,
			String description) {
		super(attachmentPoint, originator, dispute, accountCode, disputeReason,
				invoiceItem, invoice, payment,scoaSource, paymentAmount, disputeAmount,
				createdTimestamp, createdBy, modifiedTimestamp, modifiedBy,
				recActiveFlag, description);
	}

}
