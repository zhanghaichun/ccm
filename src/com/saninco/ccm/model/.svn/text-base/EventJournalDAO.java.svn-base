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
 * EventJournal entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.EventJournal
 * @author MyEclipse Persistence Tools
 */

public class EventJournalDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(EventJournalDAO.class);
	// property constants
	public static final String EVENT_MESSAGE = "eventMessage";
	public static final String EVENT_DATA = "eventData";
	public static final String IP_ADDRESS = "ipAddress";
	public static final String CREATED_BY = "createdBy";

	protected void initDao() {
		// do nothing
	}

	public void save(EventJournal transientInstance) {
		log.debug("saving EventJournal instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(EventJournal persistentInstance) {
		log.debug("deleting EventJournal instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public EventJournal findById(java.lang.Integer id) {
		log.debug("getting EventJournal instance with id: " + id);
		try {
			EventJournal instance = (EventJournal) getHibernateTemplate().get(
					"com.saninco.ccm.model.EventJournal", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(EventJournal instance) {
		log.debug("finding EventJournal instance by example");
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
		log.debug("finding EventJournal instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from EventJournal as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByEventMessage(Object eventMessage) {
		return findByProperty(EVENT_MESSAGE, eventMessage);
	}

	public List findByEventData(Object eventData) {
		return findByProperty(EVENT_DATA, eventData);
	}

	public List findByIpAddress(Object ipAddress) {
		return findByProperty(IP_ADDRESS, ipAddress);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findAll() {
		log.debug("finding all EventJournal instances");
		try {
			String queryString = "from EventJournal";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public EventJournal merge(EventJournal detachedInstance) {
		log.debug("merging EventJournal instance");
		try {
			EventJournal result = (EventJournal) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(EventJournal instance) {
		log.debug("attaching dirty EventJournal instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(EventJournal instance) {
		log.debug("attaching clean EventJournal instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static EventJournalDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (EventJournalDAO) ctx.getBean("EventJournalDAO");
	}
}