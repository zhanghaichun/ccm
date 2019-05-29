package com.saninco.ccm.model.bi;

public class BIQuoteIssuedStatus extends AbstractBIQuoteIssuedStatus implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4409142988072426961L;
	
	// Constructors
	private String labelName;
	
	/** default constructor */
	public BIQuoteIssuedStatus() {
	}

	/** full constructor */
	public BIQuoteIssuedStatus(String quoteStatusName) {
		super(quoteStatusName);
	}

	public String getLabelName() {
		return labelName == null ? this.getQuoteStatusName() : labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
