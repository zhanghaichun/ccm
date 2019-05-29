package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractCircuitStatus entity provides the base persistence definition of the
 * CircuitStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractCircuitStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String circuitStatusName;
	private Set vendorCircuits = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractCircuitStatus() {
	}

	/** minimal constructor */
	public AbstractCircuitStatus(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractCircuitStatus(Integer id, String circuitStatusName,
			Set vendorCircuits) {
		this.id = id;
		this.circuitStatusName = circuitStatusName;
		this.vendorCircuits = vendorCircuits;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCircuitStatusName() {
		return this.circuitStatusName;
	}

	public void setCircuitStatusName(String circuitStatusName) {
		this.circuitStatusName = circuitStatusName;
	}

	public Set getVendorCircuits() {
		return this.vendorCircuits;
	}

	public void setVendorCircuits(Set vendorCircuits) {
		this.vendorCircuits = vendorCircuits;
	}

}