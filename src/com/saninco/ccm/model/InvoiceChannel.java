package com.saninco.ccm.model;

import java.util.Set;

/**
 * InvoiceChannel entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class InvoiceChannel extends AbstractInvoiceChannel implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public InvoiceChannel() {
	}

	/** full constructor */
	public InvoiceChannel(String invoiceChannelName, Set bans) {
		super(invoiceChannelName, bans);
	}

}
