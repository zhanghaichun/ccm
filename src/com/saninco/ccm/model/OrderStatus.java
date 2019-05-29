package com.saninco.ccm.model;

import java.util.Set;

/**
 * OrderStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class OrderStatus extends AbstractOrderStatus implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public OrderStatus() {
	}

	/** minimal constructor */
	public OrderStatus(Integer id) {
		super(id);
	}

	/** full constructor */
	public OrderStatus(Integer id, String orderStatusName, Set vendorActivities) {
		super(id, orderStatusName, vendorActivities);
	}

}
