package com.saninco.ccm.model;

import java.util.Date;
import java.util.Set;

/**
 * Invoice entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Invoice extends AbstractInvoice implements java.io.Serializable {

	// Constructors

	private static final long serialVersionUID = 1L;

	/** default constructor */
	public Invoice() {
	}

	/** full constructor */
	public Invoice(InvoiceStatus invoiceStatus, WorkflowAction workflowAction,
			AttachmentPoint attachmentPoint, User userByWorkflowUserId,
			InternalInvoiceStatus internalInvoiceStatus, Vendor vendor,
			Ban ban, User userByAssignmentUserId, String invoiceNumber,
			Date invoiceReceiveDate, Date invoiceLoadDate, Date invoiceDate,
			Date invoiceDueDate, Double invoicePreviousBalance,
			Double invoicePreviousPayment, Double invoiceBalanceForward,
			Double invoiceAdjustment, Double invoiceLatePaymentCharge,
			Double invoiceCredit, Double invoiceCurrentDue,
			Double invoiceMinDue, Double invoiceTotalDue, String flagWorkspace,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Double lastTotalDue,
			Double lastPayment, Double lastDispute, Double invoiceMrc,
			Double invoiceOcc, Double invoiceUsage, Double invoiceTax,
			Double invoiceSurcharge, Double invoiceRegulationFee,
			Integer assignedAnalystId, Date statusTimestamp,
			Date invoiceExpectingDate, Double paymentAmount,
			Double disputeAmount, Double miscAdjustmentAmount, String barCode,
			Integer histInvoiceId, Integer histBanId, Integer histVendorId,
			String notes, Date invoiceStartDate, Date invoiceEndDate,
			String boxNumber, String numberOfBoxes,
			Set originals, Set invoiceLabels,
			Set relativeInvoicesForInvoice1Id, Set transactionHistories,
			Set payments, Set receivedPayments, Set disputes, Set proposals,
			Set relativeInvoicesForInvoice2Id, Set credits, Set invoiceItems,Set invoiceNotes,
			String messageId,String channelId) {
		super(invoiceStatus, workflowAction, attachmentPoint,
				userByWorkflowUserId, internalInvoiceStatus, vendor, ban,
				userByAssignmentUserId, invoiceNumber, invoiceReceiveDate,
				invoiceLoadDate, invoiceDate, invoiceDueDate,
				invoicePreviousBalance, invoicePreviousPayment,
				invoiceBalanceForward, invoiceAdjustment,
				invoiceLatePaymentCharge, invoiceCredit, invoiceCurrentDue,
				invoiceMinDue, invoiceTotalDue, flagWorkspace,
				createdTimestamp, createdBy, modifiedTimestamp, modifiedBy,
				recActiveFlag, lastTotalDue, lastPayment, lastDispute,
				invoiceMrc, invoiceOcc, invoiceUsage, invoiceTax,
				invoiceSurcharge, invoiceRegulationFee, assignedAnalystId,
				statusTimestamp, invoiceExpectingDate, paymentAmount,
				disputeAmount, miscAdjustmentAmount, barCode, histInvoiceId,
				histBanId, histVendorId, notes, invoiceStartDate,
				invoiceEndDate, boxNumber, numberOfBoxes, originals, invoiceLabels,
				relativeInvoicesForInvoice1Id, transactionHistories, payments,
				receivedPayments, disputes, proposals,
				relativeInvoicesForInvoice2Id, credits, invoiceItems,invoiceNotes,messageId,channelId);
	}

}
