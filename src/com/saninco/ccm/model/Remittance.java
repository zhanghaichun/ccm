package com.saninco.ccm.model;

import java.util.Date;

/**
 * Remittance entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Remittance extends AbstractRemittance implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public Remittance() {
	}

	/** full constructor */
	public Remittance(Invoice invoice, Payment payment, String invoiceNumber,
			String apSupplierNumber, Date paidDate, Double paymentAmount,
			String remittanceStatus, Date statusTimestamp,
			String processDescription, String paymentReferenceCode,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag) {
		super(invoice, payment, invoiceNumber, apSupplierNumber, paidDate,
				paymentAmount, remittanceStatus, statusTimestamp,
				processDescription, paymentReferenceCode, createdTimestamp,
				createdBy, modifiedTimestamp, modifiedBy, recActiveFlag);
	}

}
