/**
 * 
 */
package com.saninco.ccm.vo;

import java.io.Serializable;
import java.util.Date;

import com.saninco.ccm.util.SystemUtil;

/**
 * @author xinyu.Liu
 * 
 *         add userId beijing 2010-4-16 Jian.Dong delete
 *         recPerPage,pageNo,sortField,sortingDirection,getStartIndex();
 *         Jian.Dong
 */
public class SearchCreditVO extends SearchVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4891774932870661027L;
	private String vendorId;
	private String banId;
	private String creditStatusId;
	// Number
	private String invoiceNumber;
	private String disputeNumber;
	private String creditNumber;
	// Credit amount
	private String creditLeastAmount;
	private String creditGreatestAmount;
	// Credit date
	private String creditDateStartsOn;
	private String creditDateEndsOn;
	private String creditDateWiPastNumOfDays;

	private Integer userId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public SearchCreditVO() {
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
	 * @return the creditStatusId
	 */
	public String getCreditStatusId() {
		return creditStatusId;
	}

	/**
	 * @param creditStatusId
	 *            the creditStatusId to set
	 */
	public void setCreditStatusId(String creditStatusId) {
		this.creditStatusId = creditStatusId.trim();
	}

	public String calCreditDateStartsOn() {
		String r = null;
		if (this.creditDateStartsOn != null)
			r = "'" + this.creditDateStartsOn + "'";
		else {
			if (this.creditDateWiPastNumOfDays != null)
				r = "ADDDATE('" + SystemUtil.parseString(new Date())
						+ "', INTERVAL -" + creditDateWiPastNumOfDays + " DAY)";
		}
		return r;
	}

	public String calCreditDateEndsOn() {
		String r = null;
		if (this.creditDateEndsOn != null)
			r = "'" + this.creditDateEndsOn + "'";
		return r;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber.trim();
	}

	public String getDisputeNumber() {
		return disputeNumber;
	}

	public void setDisputeNumber(String disputeNumber) {
		this.disputeNumber = disputeNumber.trim();
	}

	public String getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber.trim();
	}

	public String getCreditLeastAmount() {
		return creditLeastAmount;
	}

	public void setCreditLeastAmount(String creditLeastAmount) {
		this.creditLeastAmount = creditLeastAmount.trim();
	}

	public String getCreditGreatestAmount() {
		return creditGreatestAmount;
	}

	public void setCreditGreatestAmount(String creditGreatestAmount) {
		this.creditGreatestAmount = creditGreatestAmount.trim();
	}

	public String getCreditDateStartsOn() {
		return creditDateStartsOn;
	}

	public void setCreditDateStartsOn(String creditDateStartsOn) {
		this.creditDateStartsOn = creditDateStartsOn.trim();
	}

	public String getCreditDateEndsOn() {
		return creditDateEndsOn;
	}

	public void setCreditDateEndsOn(String creditDateEndsOn) {
		this.creditDateEndsOn = creditDateEndsOn.trim();
	}

	public String getCreditDateWiPastNumOfDays() {
		return creditDateWiPastNumOfDays;
	}

	public void setCreditDateWiPastNumOfDays(String creditDateWiPastNumOfDays) {
		this.creditDateWiPastNumOfDays = creditDateWiPastNumOfDays.trim();
	}

	public String toString() {
		return "SearchCreditVO [banId=" + banId + ", creditDateEndsOn="
				+ creditDateEndsOn + ", creditDateStartsOn="
				+ creditDateStartsOn + ", creditDateWiPastNumOfDays="
				+ creditDateWiPastNumOfDays + ", creditGreatestAmount="
				+ creditGreatestAmount + ", creditLeastAmount="
				+ creditLeastAmount + ", creditNumber=" + creditNumber
				+ ", creditStatusId=" + creditStatusId + ", disputeNumber="
				+ disputeNumber + ", invoiceNumber=" + invoiceNumber
				+ ", userId=" + userId + ", vendorId=" + vendorId + "]";
	}

}
