package com.saninco.ccm.vo;

import java.io.Serializable;

public class SearchTicketVO extends SearchVO implements Serializable {
	private static final long serialVersionUID = -4891774932870661027L;

	private String ticketId;

	private String ticketNumber;
	private String createdById;
	private String modifiedById;
	// ticket due date
	private String ticketDueStartsOn;
	private String ticketDueEndsOn;
	private String ticketDueWiPastNumOfDays;

	private String priorityId;
	private String severityId;
	private String statusId;

	private String title;
	private String content;
	private String comment;
	private String attachmentPointId;
	private String ticketHistoryId;

	public SearchTicketVO() {
	}

	/**
	 * @return the ticketDueStartsOn
	 */
	public String getTicketDueStartsOn() {
		return ticketDueStartsOn;
	}

	/**
	 * @param ticketDueStartsOn
	 *            the ticketDueStartsOn to set
	 */
	public void setTicketDueStartsOn(String ticketDueStartsOn) {
		this.ticketDueStartsOn = ticketDueStartsOn.trim();
	}

	/**
	 * @return the ticketDueEndsOn
	 */
	public String getTicketDueEndsOn() {
		return ticketDueEndsOn;
	}

	/**
	 * @param ticketDueEndsOn
	 *            the ticketDueEndsOn to set
	 */
	public void setTicketDueEndsOn(String ticketDueEndsOn) {
		this.ticketDueEndsOn = ticketDueEndsOn.trim();
	}

	/**
	 * @return the ticketDueWiPastNumOfDays
	 */
	public String getTicketDueWiPastNumOfDays() {
		return ticketDueWiPastNumOfDays;
	}

	/**
	 * @param ticketDueWiPastNumOfDays
	 *            the ticketDueWiPastNumOfDays to set
	 */
	public void setTicketDueWiPastNumOfDays(String ticketDueWiPastNumOfDays) {
		this.ticketDueWiPastNumOfDays = ticketDueWiPastNumOfDays.trim();
	}

	public String getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(String priorityId) {
		this.priorityId = priorityId.trim();
	}

	public String getSeverityId() {
		return severityId;
	}

	public void setSeverityId(String severityId) {
		this.severityId = severityId.trim();
	}

	public String getStatusId() {
		return statusId;
	}

	public void setStatusId(String statusId) {
		this.statusId = statusId.trim();
	}

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId.trim();
	}

	public String getCreatedById() {
		return createdById;
	}

	public void setCreatedById(String createdById) {
		this.createdById = createdById.trim();
	}

	public String getModifiedById() {
		return modifiedById;
	}

	public void setModifiedById(String modifiedById) {
		this.modifiedById = modifiedById.trim();
	}

	public String getTicketNumber() {
		return ticketNumber;
	}

	public void setTicketNumber(String ticketNumber) {
		this.ticketNumber = ticketNumber.trim();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title.trim();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content.trim();
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment.trim();
	}

	public String toString() {
		return "SearchTicketVO [comment=" + comment + ", content=" + content
				+ ", createdById=" + createdById + ", modifiedById="
				+ modifiedById + ", priorityId=" + priorityId + ", severityId="
				+ severityId + ", statusId=" + statusId + ", ticketDueEndsOn="
				+ ticketDueEndsOn + ", ticketDueStartsOn=" + ticketDueStartsOn
				+ ", ticketDueWiPastNumOfDays=" + ticketDueWiPastNumOfDays
				+ ", ticketId=" + ticketId + ", ticketNumber=" + ticketNumber
				+ ", title=" + title + "]";
	}

	public String getAttachmentPointId() {
		return attachmentPointId;
	}

	public void setAttachmentPointId(String attachmentPointId) {
		this.attachmentPointId = attachmentPointId;
	}

	public String getTicketHistoryId() {
		return ticketHistoryId;
	}

	public void setTicketHistoryId(String ticketHistoryId) {
		this.ticketHistoryId = ticketHistoryId;
	}


}
