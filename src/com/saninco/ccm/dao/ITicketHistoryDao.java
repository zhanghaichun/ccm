package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.TicketHistory;

public interface ITicketHistoryDao {
	
	public AttachmentPoint findAttachmentPointById(Integer id);

	public abstract void save(TicketHistory transientInstance);

	public abstract void delete(TicketHistory persistentInstance);

	public abstract TicketHistory findById(java.lang.Integer id);

	public abstract List findByExample(TicketHistory instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByPriorityIdFrom(Object priorityIdFrom);

	public abstract List findByPriorityIdTo(Object priorityIdTo);

	public abstract List findBySeverityIdFrom(Object severityIdFrom);

	public abstract List findBySeverityIdTo(Object severityIdTo);

	public abstract List findByTicketStatusIdFrom(Object ticketStatusIdFrom);

	public abstract List findByTicketStatusIdTo(Object ticketStatusIdTo);

	public abstract List findByTitle(Object title);

	public abstract List findByContent(Object content);

	public abstract List findByComment(Object comment);

	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findAll();

	public abstract TicketHistory merge(TicketHistory detachedInstance);

	public abstract void attachDirty(TicketHistory instance);

	public abstract void attachClean(TicketHistory instance);

}