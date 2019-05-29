package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractSeverity entity provides the base persistence definition of the
 * Severity entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractSeverity implements java.io.Serializable {

	// Fields

	private Integer id;
	private String severityName;

	private Set tickets = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractSeverity() {
	}

	/** full constructor */
	public AbstractSeverity(String severityName, Set tickets) {
		this.severityName = severityName;
		this.tickets = tickets;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSeverityName() {
		return this.severityName;
	}

	public void setSeverityName(String severityName) {
		this.severityName = severityName;
	}

	public Set getTickets() {
		return this.tickets;
	}

	public void setTickets(Set tickets) {
		this.tickets = tickets;
	}

	public String toString() {
		return "AbstractSeverity [" + (id != null ? "id=" + id + ", " : "")
				+ (severityName != null ? "severityName=" + severityName : "")
				+ "]";
	}

}