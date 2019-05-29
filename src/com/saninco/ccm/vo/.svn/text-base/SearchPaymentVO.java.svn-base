package com.saninco.ccm.vo;

import java.io.Serializable;
import java.util.Date;

import com.saninco.ccm.util.SystemUtil;

/**
 * @author Joe.Yang
 * 
 *         add userId beijing 2010-4-16 Jian.Dong delete
 *         recPerPage,pageNo,sortField,sortingDirection,getStartIndex();
 *         Jian.Dong
 */
public class SearchPaymentVO extends SearchVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5319007026175999502L;
	/**
	 * 
	 */

	// vendor and ban
	private String vendorId;
	private String banId;

	// Transaction Info
	private String currencyId;
	private String paymentTransactionNumber;
	private String statusId;
	private String referenceNumber;
	private String paymentApproverId;

	// paid date
	private String paidDateStartsOn;
	private String paidDateEndsOn;
	private String paidWithinNumOfDays;
	// payment date
	private String paymentDateStartsOn;
	private String paymentDateEndsOn;
	private String paymentWithinPastNumOfDays;
	private String paymentWithinNextNumOfDays;

	// amount
	private String leastAmount;
	private String greatestAmount;

	// 
	private String paymentReferenceCode;
	private String notes;
	private String paidDate;
	private Integer workflowUserId;
	private Integer pid;
	
	private Integer attachmentPointId;
	private Integer attachmentFileId;
	
	private Integer userId;

	public SearchPaymentVO() {
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
		this.vendorId = vendorId.trim();
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
		this.banId = banId.trim();
	}

	/**
	 * @return the currencyId
	 */
	public String getCurrencyId() {
		return currencyId;
	}

	/**
	 * @param currencyId
	 *            the currencyId to set
	 */
	public void setCurrencyId(String currencyId) {
		this.currencyId = currencyId.trim();
	}

	/**
	 * @return the paymentTransactionNumber
	 */
	public String getPaymentTransactionNumber() {
		return paymentTransactionNumber;
	}

	/**
	 * @param paymentTransactionNumber
	 *            the paymentTransactionNumber to set
	 */
	public void setPaymentTransactionNumber(String paymentTransactionNumber) {
		this.paymentTransactionNumber = paymentTransactionNumber.trim();
	}

	/**
	 * @return the statusId
	 */
	public String getStatusId() {
		return statusId;
	}

	/**
	 * @param statusId
	 *            the statusId to set
	 */
	public void setStatusId(String statusId) {
		this.statusId = statusId.trim();
	}

	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @param referenceNumber
	 *            the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber.trim();
	}

	/**
	 * @return the paymentApproverId
	 */
	public String getPaymentApproverId() {
		return paymentApproverId;
	}

	/**
	 * @param paymentApproverId
	 *            the paymentApproverId to set
	 */
	public void setPaymentApproverId(String paymentApproverId) {
		this.paymentApproverId = paymentApproverId.trim();
	}

	/**
	 * @return the paidDateStartsOn
	 */
	public String getPaidDateStartsOn() {
		return paidDateStartsOn;
	}

	/**
	 * @param paidDateStartsOn
	 *            the paidDateStartsOn to set
	 */
	public void setPaidDateStartsOn(String paidDateStartsOn) {
		this.paidDateStartsOn = paidDateStartsOn.trim();
	}

	/**
	 * @return the paidDateEndsOn
	 */
	public String getPaidDateEndsOn() {
		return paidDateEndsOn;
	}

	/**
	 * @param paidDateEndsOn
	 *            the paidDateEndsOn to set
	 */
	public void setPaidDateEndsOn(String paidDateEndsOn) {
		this.paidDateEndsOn = paidDateEndsOn.trim();
	}

	/**
	 * @return the paidWithinNumOfDays
	 */
	public String getPaidWithinNumOfDays() {
		return paidWithinNumOfDays;
	}

	/**
	 * @param paidWithinNumOfDays
	 *            the paidWithinNumOfDays to set
	 */
	public void setPaidWithinNumOfDays(String paidWithinNumOfDays) {
		this.paidWithinNumOfDays = paidWithinNumOfDays.trim();
	}

	/**
	 * @return the paymentDateStartsOn
	 */
	public String getPaymentDateStartsOn() {
		return paymentDateStartsOn;
	}

	/**
	 * @param paymentDateStartsOn
	 *            the paymentDateStartsOn to set
	 */
	public void setPaymentDateStartsOn(String paymentDateStartsOn) {
		this.paymentDateStartsOn = paymentDateStartsOn.trim();
	}

	/**
	 * @return the paymentDateEndsOn
	 */
	public String getPaymentDateEndsOn() {
		return paymentDateEndsOn;
	}

	/**
	 * @param paymentDateEndsOn
	 *            the paymentDateEndsOn to set
	 */
	public void setPaymentDateEndsOn(String paymentDateEndsOn) {
		this.paymentDateEndsOn = paymentDateEndsOn.trim();
	}

	/**
	 * @return the paymentWithinPastNumOfDays
	 */
	public String getPaymentWithinPastNumOfDays() {
		return paymentWithinPastNumOfDays;
	}

	/**
	 * @param paymentWithinPastNumOfDays
	 *            the paymentWithinPastNumOfDays to set
	 */
	public void setPaymentWithinPastNumOfDays(String paymentWithinPastNumOfDays) {
		this.paymentWithinPastNumOfDays = paymentWithinPastNumOfDays.trim();
	}

	/**
	 * @return the paymentWithinNextNumOfDays
	 */
	public String getPaymentWithinNextNumOfDays() {
		return paymentWithinNextNumOfDays;
	}

	/**
	 * @param paymentWithinNextNumOfDays
	 *            the paymentWithinNextNumOfDays to set
	 */
	public void setPaymentWithinNextNumOfDays(String paymentWithinNextNumOfDays) {
		this.paymentWithinNextNumOfDays = paymentWithinNextNumOfDays.trim();
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
		this.leastAmount = leastAmount.trim();
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
		this.greatestAmount = greatestAmount.trim();
	}

	// modified by xinyu.Liu on 2010.5.12 for CCM-108
	public String calPaymentDateStartsOn() {

		String r = null;
		if (this.paymentDateStartsOn != null)
			r = "'" + this.paymentDateStartsOn + "'";
		else if (this.paymentWithinPastNumOfDays != null) {
			r = "ADDDATE('" + SystemUtil.parseString(new Date())
					+ "', INTERVAL -" + paymentWithinPastNumOfDays + " DAY)";
		} else if (this.paymentWithinNextNumOfDays != null) {
			r = "'" + SystemUtil.parseString(new Date()) + "'";
		}
		return r;
	}

	public String calPaymentDateEndsOn() {

		String r = null;
		if (this.paymentDateEndsOn != null)
			r = "'" + this.paymentDateEndsOn + "'";
		else if (this.paymentWithinNextNumOfDays != null) {
			r = "ADDDATE('" + SystemUtil.parseString(new Date())
					+ "', INTERVAL " + paymentWithinNextNumOfDays + " DAY)";
		}
		return r;

	}

	public String calPaidDateStartsOn() {

		String r = null;
		if (this.paidDateStartsOn != null)
			r = "'" + this.paidDateStartsOn + "'";
		else {
			if (this.paidWithinNumOfDays != null)
				r = "ADDDATE('" + SystemUtil.parseString(new Date())
						+ "', INTERVAL -" + paidWithinNumOfDays + " DAY)";
		}
		return r;
	}

	public String calPaidDateEndsOn() {

		String r = null;
		if (this.paidDateEndsOn != null)
			r = "'" + this.paidDateEndsOn + "'";
		return r;

	}

	public String getPaymentReferenceCode() {
		return paymentReferenceCode;
	}

	public void setPaymentReferenceCode(String paymentReferenceCode) {
		this.paymentReferenceCode = paymentReferenceCode;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public Integer getWorkflowUserId() {
		return workflowUserId;
	}

	public void setWorkflowUserId(Integer workflowUserId) {
		this.workflowUserId = workflowUserId;
	}

	public Integer getPid() {
		return pid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public String getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(String paidDate) {
		this.paidDate = paidDate;
	}

	public Integer getAttachmentPointId() {
		return attachmentPointId;
	}

	public void setAttachmentPointId(Integer attachmentPointId) {
		this.attachmentPointId = attachmentPointId;
	}

	public Integer getAttachmentFileId() {
		return attachmentFileId;
	}

	public void setAttachmentFileId(Integer attachmentFileId) {
		this.attachmentFileId = attachmentFileId;
	}

	public String toString() {
		return "SearchPaymentVO [banId=" + banId + ", currencyId=" + currencyId
				+ ", greatestAmount=" + greatestAmount + ", leastAmount="
				+ leastAmount + ", notes=" + notes + ", paidDate=" + paidDate
				+ ", paidDateEndsOn=" + paidDateEndsOn + ", paidDateStartsOn="
				+ paidDateStartsOn + ", paidWithinNumOfDays="
				+ paidWithinNumOfDays + ", paymentApproverId="
				+ paymentApproverId + ", paymentDateEndsOn="
				+ paymentDateEndsOn + ", paymentDateStartsOn="
				+ paymentDateStartsOn + ", paymentReferenceCode="
				+ paymentReferenceCode + ", paymentTransactionNumber="
				+ paymentTransactionNumber + ", paymentWithinNextNumOfDays="
				+ paymentWithinNextNumOfDays + ", paymentWithinPastNumOfDays="
				+ paymentWithinPastNumOfDays + ", pid=" + pid
				+ ", referenceNumber=" + referenceNumber + ", statusId="
				+ statusId + ", userId=" + userId + ", vendorId=" + vendorId
				+ ", workflowUserId=" + workflowUserId + "]";
	}

}
