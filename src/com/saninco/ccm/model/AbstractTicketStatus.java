package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractTicketStatus entity provides the base persistence definition of the
 * TicketStatus entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTicketStatus implements java.io.Serializable {

	// Fields

	private Integer id;
	private String ticketStatusName;

	private Set tickets = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractTicketStatus() {
	}

	/** full constructor */
	public AbstractTicketStatus(String ticketStatusName, Set tickets) {
		this.ticketStatusName = ticketStatusName;
		this.tickets = tickets;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTicketStatusName() {
		return this.ticketStatusName;
	}

	public void setTicketStatusName(String ticketStatusName) {
		this.ticketStatusName = ticketStatusName;
	}

	public Set getTickets() {
		return this.tickets;
	}

	public void setTickets(Set tickets) {
		this.tickets = tickets;
	}

	public String toString() {
		return "AbstractTicketStatus ["
				+ (id != null ? "id=" + id + ", " : "")
				+ (ticketStatusName != null ? "ticketStatusName="
						+ ticketStatusName : "") + "]";
	}



}