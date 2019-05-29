package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.TicketStatus;


/**
 * Spring Hibernate DAO interface for TicketStatus based on MyEclipse generated 
 * DAO class with reverse engineering.
 * 
 * @author xinyu.Liu
 */
@SuppressWarnings("unchecked")
public interface ITicketStatusDao {
	public abstract void save(TicketStatus transientInstance);

	public abstract void delete(TicketStatus persistentInstance);

	public abstract TicketStatus findById(java.lang.Integer id);

	public abstract List findByExample(TicketStatus instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByTicketStatusName(Object ticketStatusName);

	public abstract List findAll();

	public abstract TicketStatus merge(TicketStatus detachedInstance);

	public abstract void attachDirty(TicketStatus instance);

	public abstract void attachClean(TicketStatus instance);

}