/**
 * @author xinyu.Liu
 * 
 */
package com.saninco.ccm.vo;

import java.io.Serializable;
import java.util.Date;

import com.saninco.ccm.util.SystemUtil;

public class SearchDisputeVO extends SearchVO implements Serializable {
	private static final long serialVersionUID = -4891774932870661027L;

	private String vendorId;
	private String banId;
	// amount
	private String leastAmount;
	private String greatestAmount;

	private String disputeReasonId;
	private String disputeStatusId;
	private String proposalId;

	// invoice due date
	private String disputeDateStartsOn;
	private String disputeDateEndsOn;
	private String disputeDateWiPastNumOfDays;

	private String disputeNumber;
	private String claimNumber;
	private String analyst;
	private String ticketNumber;

	private String reconcileAmount;
	private String notes;
	private String notesTwo;
	private Double amount;
	
	private String boxId;
	private String attachmentPointId;
	private String accountCodeId;
	private String balanceAmount;
	private String bo;
	private String invoiceId;
	
	private String attachmentFileId;
	
	/**
	 * 
	 */
	private String tid;

	// view detail
	private Integer reconcileId;
	private Integer disputeId;
	private Integer creditId;
	private Integer creditForReconcileAmount;
	private String creditNotes;
	private String isShortPaid;
	private String isRecurring;
	private String disputeActionRequestId;
	private String disputeActionRequestReplyId;
	
	private Integer userId;
	
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SearchDisputeVO() {
	}

	/**
	 * @return the vendorId
	 */
	public String getVendorId() {
		return vendorId;
	}

	/**
	 * @param vendorId
	 *            the vendorId to set
	 */
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	/**
	 * @return the banId
	 */
	public String getBanId() {
		return banId;
	}

	/**
	 * @param banId
	 *            the banId to set
	 */
	public void setBanId(String banId) {
		this.banId = banId;
	}

	/**
	 * @return the disputeNumber
	 */
	public String getDisputeNumber() {
		if (disputeNumber == null)
			return null;
		return disputeNumber.trim();
	}

	/**
	 * @param disputeNumber
	 *            the disputeNumber to set
	 */
	public void setDisputeNumber(String disputeNumber) {
		this.disputeNumber = disputeNumber;
	}

	/**
	 * @return the statusId
	 */
	public String getClaimNumber() {
		if (claimNumber == null)
			return null;
		return claimNumber.trim();
	}

	/**
	 * @param statusId
	 *            the statusId to set
	 */
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}

	/**
	 * @return the disputeDateStartsOn
	 */
	public String getDisputeDateStartsOn() {
		return disputeDateStartsOn;
	}

	/**
	 * @param disputeDateStartsOn
	 *            the disputeDateStartsOn to set
	 */
	public void setDisputeDateStartsOn(String disputeDateStartsOn) {
		this.disputeDateStartsOn = disputeDateStartsOn;
	}

	/**
	 * @return the disputeDateEndsOn
	 */
	public String getDisputeDateEndsOn() {
		return disputeDateEndsOn;
	}

	/**
	 * @param disputeDateEndsOn
	 *            the disputeDateEndsOn to set
	 */
	public void setDisputeDateEndsOn(String disputeDateEndsOn) {
		this.disputeDateEndsOn = disputeDateEndsOn;
	}

	/**
	 * @return the disputeDateWiPastNumOfDays
	 */
	public String getDisputeDateWiPastNumOfDays() {
		return disputeDateWiPastNumOfDays;
	}

	/**
	 * @param disputeDateWiPastNumOfDays
	 *            the disputeDateWiPastNumOfDays to set
	 */
	public void setDisputeDateWiPastNumOfDays(String disputeDateWiPastNumOfDays) {
		this.disputeDateWiPastNumOfDays = disputeDateWiPastNumOfDays;
	}

	/**
	 * @return the leastAmount
	 */
	public String getLeastAmount() {
		return leastAmount;
	}

	/**
	 * @param leastAmount
	 *            the leastAmount to set
	 */
	public void setLeastAmount(String leastAmount) {
		this.leastAmount = leastAmount;
	}

	/**
	 * @return the greatestAmount
	 */
	public String getGreatestAmount() {
		return greatestAmount;
	}

	/**
	 * @param greatestAmount
	 *            the greatestAmount to set
	 */
	public void setGreatestAmount(String greatestAmount) {
		this.greatestAmount = greatestAmount;
	}

	/**
	 * @return the invoiceDueStartsOn
	 */
	public String calInvoiceDueStartsOn() {
		String r = null;
		if (this.disputeDateStartsOn != null)
			r = "'" + this.disputeDateStartsOn + "'";
		else if (this.disputeDateWiPastNumOfDays != null) {
			r = "ADDDATE('" + SystemUtil.parseString(new Date())
					+ "', INTERVAL -" + disputeDateWiPastNumOfDays + " DAY)";
		}
		return r;
	}

	/**
	 * @return the invoiceDueEndsOn
	 */
	public String calInvoiceDueEndsOn() {
		String r = null;
		if (this.disputeDateEndsOn != null)
			r = "'" + this.disputeDateEndsOn + "'";
		return r;
	}

	/**
	 * @return the disputeReasonId
	 */
	public String getDisputeReasonId() {
		return disputeReasonId;
	}

	/**
	 * @param disputeReasonId
	 *            the disputeReasonId to set
	 */
	public void setDisputeReasonId(String disputeReasonId) {
		this.disputeReasonId = disputeReasonId;
	}

	/**
	 * @return the disputeStatusId
	 */
	public String getDisputeStatusId() {
		return disputeStatusId;
	}

	/**
	 * @param disputeStatusId
	 *            the disputeStatusId to set
	 */
	public void setDisputeStatusId(String disputeStatusId) {
		this.disputeStatusId = disputeStatusId;
	}

	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	/**
	 * @return the disputeId
	 */
	public int getDisputeId() {
		return disputeId;
	}

	/**
	 * @param disputeId
	 *            the disputeId to set
	 */
	public void setDisputeId(int disputeId) {
		this.disputeId = disputeId;
	}

	/**
	 * @return the creditId
	 */
	public int getCreditId() {
		return creditId;
	}

	/**
	 * @param creditId
	 *            the creditId to set
	 */
	public void setCreditId(int creditId) {
		this.creditId = creditId;
	}

	/**
	 * @return the creditForReconcileAmount
	 */
	public int getCreditForReconcileAmount() {
		return creditForReconcileAmount;
	}

	/**
	 * @param creditForReconcileAmount
	 *            the creditForReconcileAmount to set
	 */
	public void setCreditForReconcileAmount(int creditForReconcileAmount) {
		this.creditForReconcileAmount = creditForReconcileAmount;
	}

	/**
	 * @return the creditNotes
	 */
	public String getCreditNotes() {
		return creditNotes;
	}

	/**
	 * @param creditNotes
	 *            the creditNotes to set
	 */
	public void setCreditNotes(String creditNotes) {
		this.creditNotes = creditNotes;
	}

	/**
	 * @return the ticketNumber
	 */
	public String getTicketNumber() {
		if (ticketNumber == null)
			return null;
		return ticketNumber.trim();
	}

	/**
	 * @param ticketNumber
	 *            the ticketNumber to set
	 */
	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber;
	}

	/**
	 * @return the reconcileId
	 */
	public int getReconcileId() {
		return reconcileId;
	}

	/**
	 * @param reconcileId
	 *            the reconcileId to set
	 */
	public void setReconcileId(int reconcileId) {
		this.reconcileId = reconcileId;
	}

	/**
	 * @return the isShortPaid
	 */
	public String getIsShortPaid() {
		if (isShortPaid != null)
			if (isShortPaid.equals("Yes"))
				return "'Y'";
			else
				return "'N'";
		else
			return isShortPaid;
	}

	/**
	 * @param isShortPaid
	 *            the isShortPaid to set
	 */
	public void setIsShortPaid(String isShortPaid) {
		this.isShortPaid = isShortPaid;
	}

	/**
	 * @return the isRecurring
	 */
	public String getIsRecurring() {
		if (isRecurring != null) {
			if (isRecurring.equals("Yes"))
				return "'Y'";
			else
				return "'N'";
		} else
			return isRecurring;
	}

	/**
	 * @param isRecurring
	 *            the isRecurring to set
	 */
	public void setIsRecurring(String isRecurring) {
		this.isRecurring = isRecurring;
	}

	/**
	 * @return the analyst
	 */
	public String getAnalyst() {
		return analyst;
	}

	/**
	 * @param analyst
	 *            the analyst to set
	 */
	public void setAnalyst(String analyst) {
		this.analyst = analyst;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public void setReconcileId(Integer reconcileId) {
		this.reconcileId = reconcileId;
	}

	public void setDisputeId(Integer disputeId) {
		this.disputeId = disputeId;
	}

	public void setCreditId(Integer creditId) {
		this.creditId = creditId;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public void setCreditForReconcileAmount(Integer creditForReconcileAmount) {
		this.creditForReconcileAmount = creditForReconcileAmount;
	}

	/**
	 * @return the reconcileAmount
	 */
	public String getReconcileAmount() {
		return reconcileAmount;
	}

	/**
	 * @param reconcileAmount
	 *            the reconcileAmount to set
	 */
	public void setReconcileAmount(String reconcileAmount) {
		this.reconcileAmount = reconcileAmount;
	}

	public String toString() {
		return "SearchDisputeVO [amount=" + amount + ", analyst=" + analyst
				+ ", banId=" + banId + ", claimNumber=" + claimNumber
				+ ", creditForReconcileAmount=" + creditForReconcileAmount
				+ ", creditId=" + creditId + ", creditNotes=" + creditNotes
				+ ", disputeDateEndsOn=" + disputeDateEndsOn
				+ ", disputeDateStartsOn=" + disputeDateStartsOn
				+ ", disputeDateWiPastNumOfDays=" + disputeDateWiPastNumOfDays
				+ ", disputeId=" + disputeId + ", disputeNumber="
				+ disputeNumber + ", disputeReasonId=" + disputeReasonId
				+ ", disputeStatusId=" + disputeStatusId + ", greatestAmount="
				+ greatestAmount + ", isRecurring=" + isRecurring
				+ ", isShortPaid=" + isShortPaid + ", leastAmount="
				+ leastAmount + ", notes=" + notes + ", reconcileAmount="
				+ reconcileAmount + ", reconcileId=" + reconcileId
				+ ", ticketNumber=" + ticketNumber + ", tid=" + tid
				+ ", userId=" + userId + ", vendorId=" + vendorId + "]";
	}

	public String getBoxId() {
		return boxId;
	}

	public void setBoxId(String boxId) {
		this.boxId = boxId;
	}

	public String getAttachmentPointId() {
		return attachmentPointId;
	}

	public void setAttachmentPointId(String attachmentPointId) {
		this.attachmentPointId = attachmentPointId;
	}

	public String getNotesTwo() {
		return notesTwo;
	}

	public void setNotesTwo(String notesTwo) {
		this.notesTwo = notesTwo;
	}

	public String getAccountCodeId() {
		return accountCodeId;
	}

	public void setAccountCodeId(String accountCodeId) {
		this.accountCodeId = accountCodeId;
	}

	public String getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(String balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public String getBo() {
		return bo;
	}

	public void setBo(String bo) {
		this.bo = bo;
	}

	public String getInvoiceId() {
		return invoiceId;
	}

	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}

	public String getAttachmentFileId() {
		return attachmentFileId;
	}

	public void setAttachmentFileId(String attachmentFileId) {
		this.attachmentFileId = attachmentFileId;
	}
	
	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	public String getDisputeActionRequestId() {
		return disputeActionRequestId;
	}

	public void setDisputeActionRequestId(String disputeActionRequestId) {
		this.disputeActionRequestId = disputeActionRequestId;
	}

	public String getDisputeActionRequestReplyId() {
		return disputeActionRequestReplyId;
	}

	public void setDisputeActionRequestReplyId(String disputeActionRequestReplyId) {
		this.disputeActionRequestReplyId = disputeActionRequestReplyId;
	}
	
}
