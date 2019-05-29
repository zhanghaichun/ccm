package com.saninco.ccm.model;

import java.util.Set;

/**
 * TicketStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class TicketStatus extends AbstractTicketStatus implements
		java.io.Serializable {

	// Constructors

	/** default constructor */
	public TicketStatus() {
	}

	/** full constructor */
	public TicketStatus(String ticketStatusName, Set tickets) {
		super(ticketStatusName, tickets);
	}

}
