package com.saninco.ccm.model;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * TicketHistory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.TicketHistory
 * @author MyEclipse Persistence Tools
 */

public class TicketHistoryDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(TicketHistoryDAO.class);
	// property constants
	public static final String PRIORITY_ID_FROM = "priorityIdFrom";
	public static final String PRIORITY_ID_TO = "priorityIdTo";
	public static final String SEVERITY_ID_FROM = "severityIdFrom";
	public static final String SEVERITY_ID_TO = "severityIdTo";
	public static final String TICKET_STATUS_ID_FROM = "ticketStatusIdFrom";
	public static final String TICKET_STATUS_ID_TO = "ticketStatusIdTo";
	public static final String TITLE = "title";
	public static final String CONTENT = "content";
	public static final String COMMENT = "comment";
	public static final String CREATED_BY = "createdBy";

	protected void initDao() {
		// do nothing
	}

	public void save(TicketHistory transientInstance) {
		log.debug("saving TicketHistory instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TicketHistory persistentInstance) {
		log.debug("deleting TicketHistory instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TicketHistory findById(java.lang.Integer id) {
		log.debug("getting TicketHistory instance with id: " + id);
		try {
			TicketHistory instance = (TicketHistory) getHibernateTemplate()
					.get("com.saninco.ccm.model.TicketHistory", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TicketHistory instance) {
		log.debug("finding TicketHistory instance by example");
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
		log.debug("finding TicketHistory instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TicketHistory as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPriorityIdFrom(Object priorityIdFrom) {
		return findByProperty(PRIORITY_ID_FROM, priorityIdFrom);
	}

	public List findByPriorityIdTo(Object priorityIdTo) {
		return findByProperty(PRIORITY_ID_TO, priorityIdTo);
	}

	public List findBySeverityIdFrom(Object severityIdFrom) {
		return findByProperty(SEVERITY_ID_FROM, severityIdFrom);
	}

	public List findBySeverityIdTo(Object severityIdTo) {
		return findByProperty(SEVERITY_ID_TO, severityIdTo);
	}

	public List findByTicketStatusIdFrom(Object ticketStatusIdFrom) {
		return findByProperty(TICKET_STATUS_ID_FROM, ticketStatusIdFrom);
	}

	public List findByTicketStatusIdTo(Object ticketStatusIdTo) {
		return findByProperty(TICKET_STATUS_ID_TO, ticketStatusIdTo);
	}

	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	public List findByComment(Object comment) {
		return findByProperty(COMMENT, comment);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findAll() {
		log.debug("finding all TicketHistory instances");
		try {
			String queryString = "from TicketHistory";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TicketHistory merge(TicketHistory detachedInstance) {
		log.debug("merging TicketHistory instance");
		try {
			TicketHistory result = (TicketHistory) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TicketHistory instance) {
		log.debug("attaching dirty TicketHistory instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TicketHistory instance) {
		log.debug("attaching clean TicketHistory instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TicketHistoryDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TicketHistoryDAO) ctx.getBean("TicketHistoryDAO");
	}
}