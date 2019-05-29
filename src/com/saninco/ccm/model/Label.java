package com.saninco.ccm.model;

import java.util.Set;

/**
 * Label entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Label extends AbstractLabel implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Label() {
	}

	/** full constructor */
	public Label(LabelType labelType, String labelName, String iconFilePath,
			Set invoiceLabels, Set itemLabels) {
		super(labelType, labelName, iconFilePath, invoiceLabels, itemLabels);
	}

}
