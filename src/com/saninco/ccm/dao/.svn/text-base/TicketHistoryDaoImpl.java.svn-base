package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.TicketHistory;

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

public class TicketHistoryDaoImpl extends HibernateDaoSupport implements ITicketHistoryDao {
	private final Logger log = Logger.getLogger(this.getClass());
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
	
	public AttachmentPoint findAttachmentPointById(java.lang.Integer id) {
		log.debug("getting AttachmentPoint instance with id: " + id);
		try {
			AttachmentPoint instance = (AttachmentPoint) getHibernateTemplate()
					.get("com.saninco.ccm.model.AttachmentPoint", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#save(com.saninco.ccm.model.TicketHistory)
	 */
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

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#delete(com.saninco.ccm.model.TicketHistory)
	 */
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

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findById(java.lang.Integer)
	 */
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

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findByExample(com.saninco.ccm.model.TicketHistory)
	 */
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

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findByProperty(java.lang.String, java.lang.Object)
	 */
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

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findByPriorityIdFrom(java.lang.Object)
	 */
	public List findByPriorityIdFrom(Object priorityIdFrom) {
		return findByProperty(PRIORITY_ID_FROM, priorityIdFrom);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findByPriorityIdTo(java.lang.Object)
	 */
	public List findByPriorityIdTo(Object priorityIdTo) {
		return findByProperty(PRIORITY_ID_TO, priorityIdTo);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findBySeverityIdFrom(java.lang.Object)
	 */
	public List findBySeverityIdFrom(Object severityIdFrom) {
		return findByProperty(SEVERITY_ID_FROM, severityIdFrom);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findBySeverityIdTo(java.lang.Object)
	 */
	public List findBySeverityIdTo(Object severityIdTo) {
		return findByProperty(SEVERITY_ID_TO, severityIdTo);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findByTicketStatusIdFrom(java.lang.Object)
	 */
	public List findByTicketStatusIdFrom(Object ticketStatusIdFrom) {
		return findByProperty(TICKET_STATUS_ID_FROM, ticketStatusIdFrom);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findByTicketStatusIdTo(java.lang.Object)
	 */
	public List findByTicketStatusIdTo(Object ticketStatusIdTo) {
		return findByProperty(TICKET_STATUS_ID_TO, ticketStatusIdTo);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findByTitle(java.lang.Object)
	 */
	public List findByTitle(Object title) {
		return findByProperty(TITLE, title);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findByContent(java.lang.Object)
	 */
	public List findByContent(Object content) {
		return findByProperty(CONTENT, content);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findByComment(java.lang.Object)
	 */
	public List findByComment(Object comment) {
		return findByProperty(COMMENT, comment);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findByCreatedBy(java.lang.Object)
	 */
	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#findAll()
	 */
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

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#merge(com.saninco.ccm.model.TicketHistory)
	 */
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

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#attachDirty(com.saninco.ccm.model.TicketHistory)
	 */
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

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketHistoryDao#attachClean(com.saninco.ccm.model.TicketHistory)
	 */
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

	public static ITicketHistoryDao getFromApplicationContext(
			ApplicationContext ctx) {
		return (ITicketHistoryDao) ctx.getBean("TicketHistoryDAO");
	}
}