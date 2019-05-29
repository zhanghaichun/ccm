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
 * PasswordHistory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.PasswordHistory
 * @author MyEclipse Persistence Tools
 */

public class PasswordHistoryDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PasswordHistoryDAO.class);
	// property constants
	public static final String PASSWORD = "password";
	public static final String CREATED_BY = "createdBy";

	protected void initDao() {
		// do nothing
	}

	public void save(PasswordHistory transientInstance) {
		log.debug("saving PasswordHistory instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PasswordHistory persistentInstance) {
		log.debug("deleting PasswordHistory instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PasswordHistory findById(java.lang.Integer id) {
		log.debug("getting PasswordHistory instance with id: " + id);
		try {
			PasswordHistory instance = (PasswordHistory) getHibernateTemplate()
					.get("com.saninco.ccm.model.PasswordHistory", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PasswordHistory instance) {
		log.debug("finding PasswordHistory instance by example");
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
		log.debug("finding PasswordHistory instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PasswordHistory as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findAll() {
		log.debug("finding all PasswordHistory instances");
		try {
			String queryString = "from PasswordHistory";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PasswordHistory merge(PasswordHistory detachedInstance) {
		log.debug("merging PasswordHistory instance");
		try {
			PasswordHistory result = (PasswordHistory) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PasswordHistory instance) {
		log.debug("attaching dirty PasswordHistory instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PasswordHistory instance) {
		log.debug("attaching clean PasswordHistory instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PasswordHistoryDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PasswordHistoryDAO) ctx.getBean("PasswordHistoryDAO");
	}
}