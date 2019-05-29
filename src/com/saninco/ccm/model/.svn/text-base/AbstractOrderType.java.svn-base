package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractOrderType entity provides the base persistence definition of the
 * OrderType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractOrderType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String orderTypeName;
	private Set vendorActivitiesForRevisedOrderTypeId = new HashSet(0);
	private Set vendorActivitiesForOrderTypeId = new HashSet(0);
	private Set vendorActivitiesForInitialOrderTypeId = new HashSet(0);

	// Constructors

	/** default constructor */
	public AbstractOrderType() {
	}

	/** minimal constructor */
	public AbstractOrderType(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractOrderType(Integer id, String orderTypeName,
			Set vendorActivitiesForRevisedOrderTypeId,
			Set vendorActivitiesForOrderTypeId,
			Set vendorActivitiesForInitialOrderTypeId) {
		this.id = id;
		this.orderTypeName = orderTypeName;
		this.vendorActivitiesForRevisedOrderTypeId = vendorActivitiesForRevisedOrderTypeId;
		this.vendorActivitiesForOrderTypeId = vendorActivitiesForOrderTypeId;
		this.vendorActivitiesForInitialOrderTypeId = vendorActivitiesForInitialOrderTypeId;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderTypeName() {
		return this.orderTypeName;
	}

	public void setOrderTypeName(String orderTypeName) {
		this.orderTypeName = orderTypeName;
	}

	public Set getVendorActivitiesForRevisedOrderTypeId() {
		return this.vendorActivitiesForRevisedOrderTypeId;
	}

	public void setVendorActivitiesForRevisedOrderTypeId(
			Set vendorActivitiesForRevisedOrderTypeId) {
		this.vendorActivitiesForRevisedOrderTypeId = vendorActivitiesForRevisedOrderTypeId;
	}

	public Set getVendorActivitiesForOrderTypeId() {
		return this.vendorActivitiesForOrderTypeId;
	}

	public void setVendorActivitiesForOrderTypeId(
			Set vendorActivitiesForOrderTypeId) {
		this.vendorActivitiesForOrderTypeId = vendorActivitiesForOrderTypeId;
	}

	public Set getVendorActivitiesForInitialOrderTypeId() {
		return this.vendorActivitiesForInitialOrderTypeId;
	}

	public void setVendorActivitiesForInitialOrderTypeId(
			Set vendorActivitiesForInitialOrderTypeId) {
		this.vendorActivitiesForInitialOrderTypeId = vendorActivitiesForInitialOrderTypeId;
	}

}