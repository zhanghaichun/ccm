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
 * TransactionHistory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.TransactionHistory
 * @author MyEclipse Persistence Tools
 */

public class TransactionHistoryDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(TransactionHistoryDAO.class);
	// property constants
	public static final String NOTES = "notes";
	public static final String CREATED_BY = "createdBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(TransactionHistory transientInstance) {
		log.debug("saving TransactionHistory instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TransactionHistory persistentInstance) {
		log.debug("deleting TransactionHistory instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TransactionHistory findById(java.lang.Integer id) {
		log.debug("getting TransactionHistory instance with id: " + id);
		try {
			TransactionHistory instance = (TransactionHistory) getHibernateTemplate()
					.get("com.saninco.ccm.model.TransactionHistory", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TransactionHistory instance) {
		log.debug("finding TransactionHistory instance by example");
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
		log.debug("finding TransactionHistory instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TransactionHistory as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByNotes(Object notes) {
		return findByProperty(NOTES, notes);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	public List findAll() {
		log.debug("finding all TransactionHistory instances");
		try {
			String queryString = "from TransactionHistory";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TransactionHistory merge(TransactionHistory detachedInstance) {
		log.debug("merging TransactionHistory instance");
		try {
			TransactionHistory result = (TransactionHistory) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TransactionHistory instance) {
		log.debug("attaching dirty TransactionHistory instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TransactionHistory instance) {
		log.debug("attaching clean TransactionHistory instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TransactionHistoryDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (TransactionHistoryDAO) ctx.getBean("TransactionHistoryDAO");
	}
}