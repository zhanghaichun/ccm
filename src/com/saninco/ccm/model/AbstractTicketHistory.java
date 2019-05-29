package com.saninco.ccm.model;

import java.util.Date;

/**
 * AbstractTicketHistory entity provides the base persistence definition of the
 * TicketHistory entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractTicketHistory implements java.io.Serializable {

	// Fields

	private Integer id;
	private Ticket ticket;
	private Integer priorityIdFrom;
	private Integer priorityIdTo;
	private Integer severityIdFrom;
	private Integer severityIdTo;
	private Integer ticketStatusIdFrom;
	private Integer ticketStatusIdTo;
	private String title;
	private String content;
	private String comment;
	private Date createdTimestamp;
	private Integer createdBy;
	private AttachmentPoint attachmentPoint;

	// Constructors

	/** default constructor */
	public AbstractTicketHistory() {
	}

	/** full constructor */
	public AbstractTicketHistory(Ticket ticket, Integer priorityIdFrom,
			Integer priorityIdTo, Integer severityIdFrom, Integer severityIdTo,
			Integer ticketStatusIdFrom, Integer ticketStatusIdTo, String title,
			String content, String comment, Date createdTimestamp,
			Integer createdBy) {
		this.ticket = ticket;
		this.priorityIdFrom = priorityIdFrom;
		this.priorityIdTo = priorityIdTo;
		this.severityIdFrom = severityIdFrom;
		this.severityIdTo = severityIdTo;
		this.ticketStatusIdFrom = ticketStatusIdFrom;
		this.ticketStatusIdTo = ticketStatusIdTo;
		this.title = title;
		this.content = content;
		this.comment = comment;
		this.createdTimestamp = createdTimestamp;
		this.createdBy = createdBy;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Ticket getTicket() {
		return this.ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public Integer getPriorityIdFrom() {
		return this.priorityIdFrom;
	}

	public void setPriorityIdFrom(Integer priorityIdFrom) {
		this.priorityIdFrom = priorityIdFrom;
	}

	public Integer getPriorityIdTo() {
		return this.priorityIdTo;
	}

	public void setPriorityIdTo(Integer priorityIdTo) {
		this.priorityIdTo = priorityIdTo;
	}

	public Integer getSeverityIdFrom() {
		return this.severityIdFrom;
	}

	public void setSeverityIdFrom(Integer severityIdFrom) {
		this.severityIdFrom = severityIdFrom;
	}

	public Integer getSeverityIdTo() {
		return this.severityIdTo;
	}

	public void setSeverityIdTo(Integer severityIdTo) {
		this.severityIdTo = severityIdTo;
	}

	public Integer getTicketStatusIdFrom() {
		return this.ticketStatusIdFrom;
	}

	public void setTicketStatusIdFrom(Integer ticketStatusIdFrom) {
		this.ticketStatusIdFrom = ticketStatusIdFrom;
	}

	public Integer getTicketStatusIdTo() {
		return this.ticketStatusIdTo;
	}

	public void setTicketStatusIdTo(Integer ticketStatusIdTo) {
		this.ticketStatusIdTo = ticketStatusIdTo;
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

	public String getComment() {
		return this.comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
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

	public String toString() {
		return "AbstractTicketHistory ["
				+ (comment != null ? "comment=" + comment + ", " : "")
				+ (content != null ? "content=" + content + ", " : "")
				+ (createdBy != null ? "createdBy=" + createdBy + ", " : "")
				+ (createdTimestamp != null ? "createdTimestamp="
						+ createdTimestamp + ", " : "")
				+ (id != null ? "id=" + id + ", " : "")
				+ (priorityIdFrom != null ? "priorityIdFrom=" + priorityIdFrom
						+ ", " : "")
				+ (priorityIdTo != null ? "priorityIdTo=" + priorityIdTo + ", "
						: "")
				+ (severityIdFrom != null ? "severityIdFrom=" + severityIdFrom
						+ ", " : "")
				+ (severityIdTo != null ? "severityIdTo=" + severityIdTo + ", "
						: "")
				+ (ticket != null ? "ticket=" + ticket + ", " : "")
				+ (ticketStatusIdFrom != null ? "ticketStatusIdFrom="
						+ ticketStatusIdFrom + ", " : "")
				+ (ticketStatusIdTo != null ? "ticketStatusIdTo="
						+ ticketStatusIdTo + ", " : "")
				+ (title != null ? "title=" + title : "") + "]";
	}

	public AttachmentPoint getAttachmentPoint() {
		return attachmentPoint;
	}

	public void setAttachmentPoint(AttachmentPoint attachmentPoint) {
		this.attachmentPoint = attachmentPoint;
	}


}