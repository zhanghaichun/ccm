package com.saninco.ccm.model;

import java.util.Set;

/**
 * InvoiceStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class InvoiceStatus extends AbstractInvoiceStatus implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public InvoiceStatus() {
	}

	/** full constructor */
	public InvoiceStatus(String invoiceStatusName, Set invoices,
			Set transactionHistories) {
		super(invoiceStatusName, invoices, transactionHistories);
	}

}
