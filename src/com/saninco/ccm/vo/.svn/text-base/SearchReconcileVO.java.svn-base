package com.saninco.ccm.vo;

import java.io.Serializable;
import java.util.Date;

import com.saninco.ccm.util.SystemUtil;

/**
 * @author su,wei delete
 *         recPerPage,pageNo,sortField,sortingDirection,getStartIndex();
 *         Jian.Dong
 */
public class SearchReconcileVO extends SearchVO implements Serializable {

	private static final long serialVersionUID = 12323344L;

	private String vendorId;
	private String banId;
	private String analyst;
	private String analystId;
	private String crearted_by;

	private String invoiceNumber;
	private String disputeNumber;
	private String creditNumber;
	private String claimNumber;
	private String ticketNumber;

	// reconcile amount due
	private String leastAmount;
	private String greatestAmount;
	// reconcile due date
	private String reconcileDueStartsOn;
	private String reconcileDueEndsOn;
	private String reconcileDueWiPastNumOfDays;
	private String reconcileDueWiNextNumOfDays;

	public String getAnalystId() {
		return analystId;
	}

	public void setAnalystId(String analystId) {
		this.analystId = analystId.trim();
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId.trim();
	}

	public String getBanId() {
		return banId;
	}

	public void setBanId(String banId) {
		this.banId = banId.trim();
	}

	public String getInvoiceNumber() {
		if (invoiceNumber == null)
			return null;
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber.trim();
	}

	public String getDisputeNumber() {
		if (disputeNumber == null)
			return null;
		return disputeNumber;
	}

	public void setDisputeNumber(String disputeNumber) {
		this.disputeNumber = disputeNumber.trim();
	}

	public String getCreditNumber() {
		if (creditNumber == null)
			return null;
		return creditNumber;
	}

	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber.trim();
	}

	public String getClaimNumber() {
		if (claimNumber == null)
			return null;
		return claimNumber;
	}

	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber.trim();
	}

	public String getTicketNumber() {
		if (ticketNumber == null)
			return null;
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber.trim();
	}

	public String getLeastAmount() {
		return leastAmount;
	}

	public void setLeastAmount(String leastAmount) {
		this.leastAmount = leastAmount.trim();
	}

	public String getGreatestAmount() {
		return greatestAmount;
	}

	public void setGreatestAmount(String greatestAmount) {
		this.greatestAmount = greatestAmount.trim();
	}

	public String getReconcileDueStartsOn() {
		return reconcileDueStartsOn;
	}

	public void setReconcileDueStartsOn(String reconcileDueStartsOn) {
		this.reconcileDueStartsOn = reconcileDueStartsOn.trim();
	}

	public String getReconcileDueEndsOn() {
		return reconcileDueEndsOn;
	}

	public void setReconcileDueEndsOn(String reconcileDueEndsOn) {
		this.reconcileDueEndsOn = reconcileDueEndsOn.trim();
	}

	public String getReconcileDueWiPastNumOfDays() {
		return reconcileDueWiPastNumOfDays;
	}

	public void setReconcileDueWiPastNumOfDays(
			String reconcileDueWiPastNumOfDays) {
		this.reconcileDueWiPastNumOfDays = reconcileDueWiPastNumOfDays.trim();
	}

	public String getReconcileDueWiNextNumOfDays() {
		return reconcileDueWiNextNumOfDays;
	}

	public void setReconcileDueWiNextNumOfDays(
			String reconcileDueWiNextNumOfDays) {
		this.reconcileDueWiNextNumOfDays = reconcileDueWiNextNumOfDays.trim();
	}

	/**
	 * @return the reconcileDueStartsOn
	 */
	public String calReconcileDueStartsOn() {
		String r = null;
		if (this.reconcileDueStartsOn != null)
			r = "'" + this.reconcileDueStartsOn + "'";
		else if (this.reconcileDueWiPastNumOfDays != null) {
			r = "ADDDATE('" + SystemUtil.parseString(new Date())
					+ "', INTERVAL -" + reconcileDueWiPastNumOfDays + " DAY)";
		} else if (this.reconcileDueWiNextNumOfDays != null) {
			r = SystemUtil.parseString(new Date());
		}
		return r;
	}

	/**
	 * @return the reconcileDueEndsOn
	 */
	public String calReconcileDueEndsOn() {
		String r = null;
		if (this.reconcileDueEndsOn != null)
			r = "'" + this.reconcileDueEndsOn + "'";
		else if (this.reconcileDueWiNextNumOfDays != null) {
			r = "ADDDATE('" + SystemUtil.parseString(new Date())
					+ "', INTERVAL " + reconcileDueWiNextNumOfDays + " DAY)";
		}
		return r;
	}

	public String getAnalyst() {
		return analyst;
	}

	public void setAnalyst(String analyst) {
		this.analyst = analyst.trim();
	}

	public String getCrearted_by() {
		return crearted_by;
	}

	public void setCrearted_by(String crearted_by) {
		this.crearted_by = crearted_by.trim();
	}

	public String toString() {
		return "SearchReconcileVO [analyst=" + analyst + ", analystId="
				+ analystId + ", banId=" + banId + ", claimNumber="
				+ claimNumber + ", crearted_by=" + crearted_by
				+ ", creditNumber=" + creditNumber + ", disputeNumber="
				+ disputeNumber + ", greatestAmount=" + greatestAmount
				+ ", invoiceNumber=" + invoiceNumber + ", leastAmount="
				+ leastAmount + ", reconcileDueEndsOn=" + reconcileDueEndsOn
				+ ", reconcileDueStartsOn=" + reconcileDueStartsOn
				+ ", reconcileDueWiNextNumOfDays="
				+ reconcileDueWiNextNumOfDays
				+ ", reconcileDueWiPastNumOfDays="
				+ reconcileDueWiPastNumOfDays + ", ticketNumber="
				+ ticketNumber + ", vendorId=" + vendorId + "]";
	}

}
