package com.saninco.ccm.model.bi;

import java.util.Set;

/**
 * BIProduct entity. @author MyEclipse Persistence Tools
 */
public class BIProduct extends AbstractBIProduct implements
		java.io.Serializable {

	// Constructors
	private String labelName;

	/** default constructor */
	public BIProduct() {
	}

	/** full constructor */
	public BIProduct(String productName, String mediaLabel,
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
