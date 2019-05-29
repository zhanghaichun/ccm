package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractPriority entity provides the base persistence definition of the
 * Priority entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractPriority implements java.io.Serializable {

	// Fields

	private Integer id;
	private String priorityName;
	
	private Set tickets = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractPriority() {
	}

	/** full constructor */
	public AbstractPriority(String priorityName, Set tickets) {
		this.priorityName = priorityName;
		this.tickets = tickets;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPriorityName() {
		return this.priorityName;
	}

	public void setPriorityName(String priorityName) {
		this.priorityName = priorityName;
	}

	public Set getTickets() {
		return this.tickets;
	}

	public void setTickets(Set tickets) {
		this.tickets = tickets;
	}

	public String toString() {
		return "AbstractPriority [" + (id != null ? "id=" + id + ", " : "")
				+ (priorityName != null ? "priorityName=" + priorityName : "")
				+ "]";
	}

}