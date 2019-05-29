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
 * Reconcile entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.Reconcile
 * @author MyEclipse Persistence Tools
 */

public class ReconcileDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ReconcileDAO.class);
	// property constants
	public static final String RECONCILE_AMOUNT = "reconcileAmount";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String NOTES = "notes";

	protected void initDao() {
		// do nothing
	}

	public void save(Reconcile transientInstance) {
		log.debug("saving Reconcile instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Reconcile persistentInstance) {
		log.debug("deleting Reconcile instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Reconcile findById(java.lang.Integer id) {
		log.debug("getting Reconcile instance with id: " + id);
		try {
			Reconcile instance = (Reconcile) getHibernateTemplate().get(
					"com.saninco.ccm.model.Reconcile", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Reconcile instance) {
		log.debug("finding Reconcile instance by example");
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
		log.debug("finding Reconcile instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Reconcile as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByReconcileAmount(Object reconcileAmount) {
		return findByProperty(RECONCILE_AMOUNT, reconcileAmount);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	public List findByNotes(Object notes) {
		return findByProperty(NOTES, notes);
	}

	public List findAll() {
		log.debug("finding all Reconcile instances");
		try {
			String queryString = "from Reconcile";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Reconcile merge(Reconcile detachedInstance) {
		log.debug("merging Reconcile instance");
		try {
			Reconcile result = (Reconcile) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Reconcile instance) {
		log.debug("attaching dirty Reconcile instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Reconcile instance) {
		log.debug("attaching clean Reconcile instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ReconcileDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ReconcileDAO) ctx.getBean("ReconcileDAO");
	}
}