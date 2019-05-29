package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * Payment entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Payment extends AbstractPayment implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Payment() {
	}

	/** full constructor */
	public Payment(PaymentBatch paymentBatch, WorkflowAction workflowAction,
			AttachmentPoint attachmentPoint, User userByWorkflowUserId,
			Invoice invoice, User userByAssignmentUserId,
			PaymentStatus paymentStatus, String paymentTransactionNumber,
			Date paymentDate, Date paidDate, Double paymentAmount,
			String paymentReferenceCode, String description,
			String flagWorkspace, Date createdTimestamp, Integer createdBy,
			Date modifiedTimestamp, Integer modifiedBy, String recActiveFlag,
			Date statusTimestamp, Integer receivedPaymentId,
			Integer histPaymentId, Set receivedPayments, Set proposals,
			Set paymentDetails, Set transactionHistories, Set reconciles) {
		super(paymentBatch, workflowAction, attachmentPoint,
				userByWorkflowUserId, invoice, userByAssignmentUserId,
				paymentStatus, paymentTransactionNumber, paymentDate, paidDate,
				paymentAmount, paymentReferenceCode, description,
				flagWorkspace, createdTimestamp, createdBy, modifiedTimestamp,
				modifiedBy, recActiveFlag, statusTimestamp, receivedPaymentId,
				histPaymentId, receivedPayments, proposals, paymentDetails,
				transactionHistories, reconciles);
	}

}
