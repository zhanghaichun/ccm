package com.saninco.ccm.model;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * UserCrossApp entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.UserCrossApp
 * @author MyEclipse Persistence Tools
 */

public class UserCrossAppDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(UserCrossAppDAO.class);
	// property constants
	public static final String WIKI_USERNAME = "wikiUsername";
	public static final String WIKI_PASSWORD = "wikiPassword";

	protected void initDao() {
		// do nothing
	}

	public void save(UserCrossApp transientInstance) {
		log.debug("saving UserCrossApp instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserCrossApp persistentInstance) {
		log.debug("deleting UserCrossApp instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserCrossApp findById(java.lang.Integer id) {
		log.debug("getting UserCrossApp instance with id: " + id);
		try {
			UserCrossApp instance = (UserCrossApp) getHibernateTemplate().get(
					"com.saninco.ccm.model.UserCrossApp", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserCrossApp instance) {
		log.debug("finding UserCrossApp instance by example");
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
		log.debug("finding UserCrossApp instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from UserCrossApp as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByWikiUsername(Object wikiUsername) {
		return findByProperty(WIKI_USERNAME, wikiUsername);
	}

	public List findByWikiPassword(Object wikiPassword) {
		return findByProperty(WIKI_PASSWORD, wikiPassword);
	}

	public List findAll() {
		log.debug("finding all UserCrossApp instances");
		try {
			String queryString = "from UserCrossApp";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserCrossApp merge(UserCrossApp detachedInstance) {
		log.debug("merging UserCrossApp instance");
		try {
			UserCrossApp result = (UserCrossApp) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserCrossApp instance) {
		log.debug("attaching dirty UserCrossApp instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserCrossApp instance) {
		log.debug("attaching clean UserCrossApp instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserCrossAppDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (UserCrossAppDAO) ctx.getBean("UserCrossAppDAO");
	}
}