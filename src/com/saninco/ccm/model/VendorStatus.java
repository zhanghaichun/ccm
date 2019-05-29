package com.saninco.ccm.model;

import java.util.Set;

/**
 * VendorStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class VendorStatus extends AbstractVendorStatus implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public VendorStatus() {
	}

	/** full constructor */
	public VendorStatus(String vendorStatusName, Set vendors) {
		super(vendorStatusName, vendors);
	}

}
