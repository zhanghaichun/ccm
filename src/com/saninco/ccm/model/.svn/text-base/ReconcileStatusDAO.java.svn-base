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
 * ReconcileStatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.ReconcileStatus
 * @author MyEclipse Persistence Tools
 */

public class ReconcileStatusDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ReconcileStatusDAO.class);
	// property constants
	public static final String RECONCILE_STATUS_NAME = "reconcileStatusName";

	protected void initDao() {
		// do nothing
	}

	public void save(ReconcileStatus transientInstance) {
		log.debug("saving ReconcileStatus instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ReconcileStatus persistentInstance) {
		log.debug("deleting ReconcileStatus instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ReconcileStatus findById(java.lang.Integer id) {
		log.debug("getting ReconcileStatus instance with id: " + id);
		try {
			ReconcileStatus instance = (ReconcileStatus) getHibernateTemplate()
					.get("com.saninco.ccm.model.ReconcileStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ReconcileStatus instance) {
		log.debug("finding ReconcileStatus instance by example");
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
		log.debug("finding ReconcileStatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ReconcileStatus as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByReconcileStatusName(Object reconcileStatusName) {
		return findByProperty(RECONCILE_STATUS_NAME, reconcileStatusName);
	}

	public List findAll() {
		log.debug("finding all ReconcileStatus instances");
		try {
			String queryString = "from ReconcileStatus";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ReconcileStatus merge(ReconcileStatus detachedInstance) {
		log.debug("merging ReconcileStatus instance");
		try {
			ReconcileStatus result = (ReconcileStatus) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ReconcileStatus instance) {
		log.debug("attaching dirty ReconcileStatus instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReconcileStatus instance) {
		log.debug("attaching clean ReconcileStatus instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ReconcileStatusDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ReconcileStatusDAO) ctx.getBean("ReconcileStatusDAO");
	}
}