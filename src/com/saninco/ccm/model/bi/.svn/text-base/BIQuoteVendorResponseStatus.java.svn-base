package com.saninco.ccm.model.bi;

public class BIQuoteVendorResponseStatus extends AbstractBIQuoteIssuedStatus implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 774352872207360466L;
	// Constructors
	private String labelName;
	
	/** default constructor */
	public BIQuoteVendorResponseStatus() {
	}

	/** full constructor */
	public BIQuoteVendorResponseStatus(String quoteStatusName) {
		super(quoteStatusName);
	}

	public String getLabelName() {
		return labelName == null ? this.getQuoteStatusName() : labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
