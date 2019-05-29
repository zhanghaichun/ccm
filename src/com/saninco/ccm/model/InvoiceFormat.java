package com.saninco.ccm.model;

import java.util.Set;

/**
 * InvoiceFormat entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class InvoiceFormat extends AbstractInvoiceFormat implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public InvoiceFormat() {
	}

	/** full constructor */
	public InvoiceFormat(String invoiceFormatCode, Set bans) {
		super(invoiceFormatCode, bans);
	}

}
