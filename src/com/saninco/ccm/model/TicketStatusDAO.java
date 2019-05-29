package com.saninco.ccm.model;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * TicketStatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.TicketStatus
 * @author MyEclipse Persistence Tools
 */

public class TicketStatusDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(TicketStatusDAO.class);
	// property constants
	public static final String TICKET_STATUS_NAME = "ticketStatusName";

	protected void initDao() {
		// do nothing
	}

	public void save(TicketStatus transientInstance) {
		log.debug("saving TicketStatus instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TicketStatus persistentInstance) {
		log.debug("deleting TicketStatus instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TicketStatus findById(java.lang.Integer id) {
		log.debug("getting TicketStatus instance with id: " + id);
		try {
			TicketStatus instance = (TicketStatus) getHibernateTemplate().get(
					"com.saninco.ccm.model.TicketStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TicketStatus instance) {
		log.debug("finding TicketStatus instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding TicketStatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TicketStatus as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTicketStatusName(Object ticketStatusName) {
		return findByProperty(TICKET_STATUS_NAME, ticketStatusName);
	}

	public List findAll() {
		log.debug("finding all TicketStatus instances");
		try {
			String queryString = "from TicketStatus";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TicketStatus merge(TicketStatus detachedInstance) {
		log.debug("merging TicketStatus instance");
		try {
			TicketStatus result = (TicketStatus) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TicketStatus instance) {
		log.debug("attaching dirty TicketStatus instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TicketStatus instance) {
		log.debug("attaching clean TicketStatus instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TicketStatusDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TicketStatusDAO) ctx.getBean("TicketStatusDAO");
	}
}