package com.saninco.ccm.model;

import java.util.HashSet;
import java.util.Set;

/**
 * AbstractEventType entity provides the base persistence definition of the
 * EventType entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractEventType implements java.io.Serializable {

	// Fields

	private Integer id;
	private String eventTypeName;
	private String eventTypeDescription;

	private Set eventJournals = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractEventType() {
	}

	/** full constructor */
	public AbstractEventType(String eventTypeName, String eventTypeDescription,
			Set eventJournals) {
		this.eventTypeName = eventTypeName;
		this.eventTypeDescription = eventTypeDescription;
		this.eventJournals = eventJournals;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEventTypeName() {
		return this.eventTypeName;
	}

	public void setEventTypeName(String eventTypeName) {
		this.eventTypeName = eventTypeName;
	}

	public String getEventTypeDescription() {
		return this.eventTypeDescription;
	}

	public void setEventTypeDescription(String eventTypeDescription) {
		this.eventTypeDescription = eventTypeDescription;
	}

	public Set getEventJournals() {
		return this.eventJournals;
	}

	public void setEventJournals(Set eventJournals) {
		this.eventJournals = eventJournals;
	}

	public String toString() {
		return "AbstractEventType ["
				+ (eventTypeDescription != null ? "eventTypeDescription="
						+ eventTypeDescription + ", " : "")
				+ (eventTypeName != null ? "eventTypeName=" + eventTypeName
						+ ", " : "") + (id != null ? "id=" + id : "") + "]";
	}



}