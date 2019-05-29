package com.saninco.ccm.model.bi;

import java.util.Set;

/**
 * BIVendor entity. @author MyEclipse Persistence Tools
 */
public class BIVendor extends AbstractBIVendor implements java.io.Serializable {

	// Constructors
	private String labelName;
	
	/** default constructor */
	public BIVendor() {
	}

	/** minimal constructor */
	public BIVendor(Integer id) {
		super(id);
	}

	/** full constructor */
	public BIVendor(Integer id, String vendorName) {
		super(id, vendorName);
	}

	public String getLabelName() {
		return labelName == null ? this.getVendorName() : labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
