package com.saninco.ccm.model.bi;

public class BIQuoteNetType extends AbstractBIQuoteNetType implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2996465557686852731L;
	// Constructors
	private String labelName;
	
	/** default constructor */
	public BIQuoteNetType() {
	}

	/** full constructor */
	public BIQuoteNetType(String netTypeName) {
		super(netTypeName);
	}

	public String getLabelName() {
		return labelName == null ? this.getNetTypeName() : labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
