package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractOrderStatus entity provides the base persistence definition of the
 * OrderStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractOrderStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String orderStatusName;
	private Set vendorActivities = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractOrderStatus() {
	}

	/** minimal constructor */
	public AbstractOrderStatus(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractOrderStatus(Integer id, String orderStatusName,
			Set vendorActivities) {
		this.id = id;
		this.orderStatusName = orderStatusName;
		this.vendorActivities = vendorActivities;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderStatusName() {
		return this.orderStatusName;
	}

	public void setOrderStatusName(String orderStatusName) {
		this.orderStatusName = orderStatusName;
	}

	public Set getVendorActivities() {
		return this.vendorActivities;
	}

	public void setVendorActivities(Set vendorActivities) {
		this.vendorActivities = vendorActivities;
	}

}