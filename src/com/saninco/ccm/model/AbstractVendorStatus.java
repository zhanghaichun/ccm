package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractVendorStatus entity provides the base persistence definition of the
 * VendorStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractVendorStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String vendorStatusName;

	private Set vendors = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractVendorStatus() {
	}

	/** full constructor */
	public AbstractVendorStatus(String vendorStatusName, Set vendors) {
		this.vendorStatusName = vendorStatusName;
		this.vendors = vendors;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getVendorStatusName() {
		return this.vendorStatusName;
	}

	public void setVendorStatusName(String vendorStatusName) {
		this.vendorStatusName = vendorStatusName;
	}

	public Set getVendors() {
		return this.vendors;
	}

	public void setVendors(Set vendors) {
		this.vendors = vendors;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractVendorStatus [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (vendorStatusName != null)
			builder.append("vendorStatusName=").append(vendorStatusName);
		builder.append("]");
		return builder.toString();
	}



}