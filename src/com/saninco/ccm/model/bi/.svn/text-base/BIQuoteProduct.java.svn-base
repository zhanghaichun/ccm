package com.saninco.ccm.model.bi;

public class BIQuoteProduct extends AbstractBIQuoteProduct implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1350341472662639391L;
	private String labelName;

	/** default constructor */
	public BIQuoteProduct() {
	}

	/** full constructor */
	public BIQuoteProduct(String productName, String mediaLabel,
			String functionLabel, String generationLabel) {
		super(productName, mediaLabel, functionLabel, generationLabel);
	}

	public String getLabelName() {
		return labelName == null ? this.getProductName() : labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}
