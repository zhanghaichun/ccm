package com.saninco.ccm.model.bi;

public class BIQuoteAging extends AbstractBIQuoteAging implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7776838257412458846L;
	
	// Constructors
	private String labelName;
	
	/** default constructor */
	public BIQuoteAging() {
	}

	/** full constructor */
	public BIQuoteAging(String agingRangeName) {
		super(agingRangeName);
	}

	public String getLabelName() {
		return labelName == null ? this.getAgingRangeName() : labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

}
