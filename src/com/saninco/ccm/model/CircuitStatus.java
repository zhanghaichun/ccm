package com.saninco.ccm.model;

import java.util.Set;

/**
 * CircuitStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class CircuitStatus extends AbstractCircuitStatus implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public CircuitStatus() {
	}

	/** minimal constructor */
	public CircuitStatus(Integer id) {
		super(id);
	}

	/** full constructor */
	public CircuitStatus(Integer id, String circuitStatusName,
			Set vendorCircuits) {
		super(id, circuitStatusName, vendorCircuits);
	}

}
