/**
 * 
 */
package com.saninco.ccm.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.saninco.ccm.util.SystemUtil;

/**
 * @author xinyu.Liu
 * 
 */
public class OperationsVO extends SearchVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4891774932870661027L;

	private String barCode;
	private String invoiceBalanceForward;
	private String invoiceCurrentDue;
	private String invoiceTotalDue;
	private String invoiceStInId;
	private String invoicePreviousPayment;
	private String invoiceDueDate;

	public String getBarCode() {
		return barCode;
	}

	public void setBarCode(String barCode) {
		this.barCode = barCode;
	}

	public String getInvoiceBalanceForward() {
		return invoiceBalanceForward;
	}

	public void setInvoiceBalanceForward(String invoiceBalanceForward) {
		this.invoiceBalanceForward = invoiceBalanceForward;
	}

	public String getInvoiceCurrentDue() {
		return invoiceCurrentDue;
	}

	public void setInvoiceCurrentDue(String invoiceCurrentDue) {
		this.invoiceCurrentDue = invoiceCurrentDue;
	}

	public String getInvoiceTotalDue() {
		return invoiceTotalDue;
	}

	public void setInvoiceTotalDue(String invoiceTotalDue) {
		this.invoiceTotalDue = invoiceTotalDue;
	}

	public String getInvoiceStInId() {
		return invoiceStInId;
	}

	public void setInvoiceStInId(String invoiceStInId) {
		this.invoiceStInId = invoiceStInId;
	}

	public String getInvoicePreviousPayment() {
		return invoicePreviousPayment;
	}

	public void setInvoicePreviousPayment(String invoicePreviousPayment) {
		this.invoicePreviousPayment = invoicePreviousPayment;
	}

	public String getInvoiceDueDate() {
		return invoiceDueDate;
	}

	public void setInvoiceDueDate(String invoiceDueDate) {
		this.invoiceDueDate = invoiceDueDate;
	}

}
