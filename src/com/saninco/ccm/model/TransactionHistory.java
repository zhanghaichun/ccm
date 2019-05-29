package com.saninco.ccm.model;

import java.util.Date;

/**
 * TransactionHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TransactionHistory extends AbstractTransactionHistory implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TransactionHistory() {
	}

	/** full constructor */
	public TransactionHistory(InvoiceStatus invoiceStatus,
			WorkflowAction workflowAction, AttachmentPoint attachmentPoint,
			User user, Dispute dispute,
			InternalInvoiceStatus internalInvoiceStatus,
			DisputeStatus disputeStatus, Invoice invoice, Payment payment,
			PaymentStatus paymentStatus, String notes, Date createdTimestamp,
			Integer createdBy, String recActiveFlag,String workflowUserName) {
		super(invoiceStatus, workflowAction, attachmentPoint, user, dispute,
				internalInvoiceStatus, disputeStatus, invoice, payment,
				paymentStatus, notes, createdTimestamp, createdBy,
				recActiveFlag,workflowUserName);
	}

}
