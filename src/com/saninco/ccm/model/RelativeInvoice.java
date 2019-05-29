package com.saninco.ccm.model;

import java.util.Date;

/**
 * RelativeInvoice entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class RelativeInvoice extends AbstractRelativeInvoice implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public RelativeInvoice() {
	}

	/** full constructor */
	public RelativeInvoice(Invoice invoiceByInvoice2Id,
			Invoice invoiceByInvoice1Id, String notes, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy,
			String recActiveFlag) {
		super(invoiceByInvoice2Id, invoiceByInvoice1Id, notes,
				createdTimestamp, createdBy, modifiedTimestamp, modifiedBy,
				recActiveFlag);
	}

}
