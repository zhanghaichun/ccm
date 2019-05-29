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
 * BanStatus entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.BanStatus
 * @author MyEclipse Persistence Tools
 */

public class BanStatusDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BanStatusDAO.class);
	// property constants
	public static final String BAN_STATUS_NAME = "banStatusName";

	protected void initDao() {
		// do nothing
	}

	public void save(BanStatus transientInstance) {
		log.debug("saving BanStatus instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BanStatus persistentInstance) {
		log.debug("deleting BanStatus instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BanStatus findById(java.lang.Integer id) {
		log.debug("getting BanStatus instance with id: " + id);
		try {
			BanStatus instance = (BanStatus) getHibernateTemplate().get(
					"com.saninco.ccm.model.BanStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BanStatus instance) {
		log.debug("finding BanStatus instance by example");
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
		log.debug("finding BanStatus instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BanStatus as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBanStatusName(Object banStatusName) {
		return findByProperty(BAN_STATUS_NAME, banStatusName);
	}

	public List findAll() {
		log.debug("finding all BanStatus instances");
		try {
			String queryString = "from BanStatus";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BanStatus merge(BanStatus detachedInstance) {
		log.debug("merging BanStatus instance");
		try {
			BanStatus result = (BanStatus) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BanStatus instance) {
		log.debug("attaching dirty BanStatus instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BanStatus instance) {
		log.debug("attaching clean BanStatus instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BanStatusDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BanStatusDAO) ctx.getBean("BanStatusDAO");
	}
}