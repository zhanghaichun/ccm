package com.saninco.ccm.model;

import java.util.Set;

/**
 * Originator entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Originator extends AbstractOriginator implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public Originator() {
	}

	/** full constructor */
	public Originator(String originatorName, Set proposals, Set disputes) {
		super(originatorName, proposals, disputes);
	}

}
