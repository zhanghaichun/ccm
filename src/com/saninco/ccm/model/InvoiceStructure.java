package com.saninco.ccm.model;

import java.util.Set;

/**
 * InvoiceStructure entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class InvoiceStructure extends AbstractInvoiceStructure implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public InvoiceStructure() {
		
	}
	
	/** default constructor */
	public InvoiceStructure(Integer id) {
		super(id);
	}

	/** full constructor */
	public InvoiceStructure(String invoiceStructureCode, Set itemDisplaies,
			Set itemStructures, Set bans) {
		super(invoiceStructureCode, itemDisplaies, itemStructures, bans);
	}

}
