package com.saninco.ccm.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * AbstractTicket entity provides the base persistence definition of the Ticket
 * entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTicket implements java.io.Serializable {

	// Fields

	private Integer id;
	private TicketStatus ticketStatus;
	private Priority priority;
	private Severity severity;
	private String title;
	private String content;
	private Date createdTimestamp;
	private Integer createdBy;
	private Date modifiedTimestamp;
	private Integer modifiedBy;
	private String recActiveFlag;

	private Set ticketHistories = new HashSet(0);
	// Constructors

	/** default constructor */
	public AbstractTicket() {
	}

	/** full constructor */
	public AbstractTicket(TicketStatus ticketStatus, Priority priority,
			Severity severity, String title, String content,
			Date createdTimestamp, Integer createdBy, Date modifiedTimestamp,
			Integer modifiedBy, String recActiveFlag, Set ticketHistories) {
		this.ticketStatus = ticketStatus;
		this.priority = priority;
		this.severity = severity;
		this.title = title;
		this.content = content;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
		this.modifiedTimestamp = modifiedTimestamp;
		this.modifiedBy = modifiedBy;
		this.recActiveFlag = recActiveFlag;
		this.ticketHistories = ticketHistories;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TicketStatus getTicketStatus() {
		return this.ticketStatus;
	}

	public void setTicketStatus(TicketStatus ticketStatus) {
		this.ticketStatus = ticketStatus;
	}

	public Priority getPriority() {
		return this.priority;
	}

	public void setPriority(Priority priority) {
		this.priority = priority;
	}

	public Severity getSeverity() {
		return this.severity;
	}

	public void setSeverity(Severity severity) {
		this.severity = severity;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatedTimestamp() {
		return this.createdTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.createdTimestamp = createdTimestamp;
	}

	public Integer getCreatedBy() {
		return this.createdBy;
	}

	public void setCreatedBy(Integer createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedTimestamp() {
		return this.modifiedTimestamp;
	}

	public void setModifiedTimestamp(Date modifiedTimestamp) {
		this.modifiedTimestamp = modifiedTimestamp;
	}

	public Integer getModifiedBy() {
		return this.modifiedBy;
	}

	public void setModifiedBy(Integer modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getRecActiveFlag() {
		return this.recActiveFlag;
	}

	public void setRecActiveFlag(String recActiveFlag) {
		this.recActiveFlag = recActiveFlag;
	}

	public Set getTicketHistories() {
		return this.ticketHistories;
	}

	public void setTicketHistories(Set ticketHistories) {
		this.ticketHistories = ticketHistories;
	}

	public String toString() {
		return "AbstractTicket ["
				+ (content != null ? "content=" + content + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (modifiedBy != null ? "modifiedBy=" + modifiedBy + ", " : "")
				+ (modifiedTimestamp != null ? "modifiedTimestamp="
						+ modifiedTimestamp + ", " : "")
				+ (priority != null ? "priority=" + priority + ", " : "")
				+ (recActiveFlag != null ? "recActiveFlag=" + recActiveFlag
						+ ", " : "")
				+ (severity != null ? "severity=" + severity + ", " : "")
				+ (ticketStatus != null ? "ticketStatus=" + ticketStatus + ", "
						: "") + (title != null ? "title=" + title : "") + "]";
	}



}