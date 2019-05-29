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
 * DisputeStatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.DisputeStatus
 * @author MyEclipse Persistence Tools
 */

public class DisputeStatusDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(DisputeStatusDAO.class);
	// property constants
	public static final String DISPUTE_STATUS_NAME = "disputeStatusName";

	protected void initDao() {
		// do nothing
	}

	public void save(DisputeStatus transientInstance) {
		log.debug("saving DisputeStatus instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DisputeStatus persistentInstance) {
		log.debug("deleting DisputeStatus instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DisputeStatus findById(java.lang.Integer id) {
		log.debug("getting DisputeStatus instance with id: " + id);
		try {
			DisputeStatus instance = (DisputeStatus) getHibernateTemplate()
					.get("com.saninco.ccm.model.DisputeStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DisputeStatus instance) {
		log.debug("finding DisputeStatus instance by example");
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
		log.debug("finding DisputeStatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DisputeStatus as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDisputeStatusName(Object disputeStatusName) {
		return findByProperty(DISPUTE_STATUS_NAME, disputeStatusName);
	}

	public List findAll() {
		log.debug("finding all DisputeStatus instances");
		try {
			String queryString = "from DisputeStatus";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DisputeStatus merge(DisputeStatus detachedInstance) {
		log.debug("merging DisputeStatus instance");
		try {
			DisputeStatus result = (DisputeStatus) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DisputeStatus instance) {
		log.debug("attaching dirty DisputeStatus instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DisputeStatus instance) {
		log.debug("attaching clean DisputeStatus instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DisputeStatusDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (DisputeStatusDAO) ctx.getBean("DisputeStatusDAO");
	}
}