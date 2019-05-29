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
 * DisputeReason entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.DisputeReason
 * @author MyEclipse Persistence Tools
 */

public class DisputeReasonDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(DisputeReasonDAO.class);
	// property constants
	public static final String DISPUTE_REASON_NAME = "disputeReasonName";
	public static final String DISPUTE_REASON_ACRONYM = "disputeReasonAcronym";
	public static final String DISPUTE_REASON_DISCRIPTION = "disputeReasonDiscription";

	protected void initDao() {
		// do nothing
	}

	public void save(DisputeReason transientInstance) {
		log.debug("saving DisputeReason instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(DisputeReason persistentInstance) {
		log.debug("deleting DisputeReason instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public DisputeReason findById(java.lang.Integer id) {
		log.debug("getting DisputeReason instance with id: " + id);
		try {
			DisputeReason instance = (DisputeReason) getHibernateTemplate()
					.get("com.saninco.ccm.model.DisputeReason", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(DisputeReason instance) {
		log.debug("finding DisputeReason instance by example");
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
		log.debug("finding DisputeReason instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from DisputeReason as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDisputeReasonName(Object disputeReasonName) {
		return findByProperty(DISPUTE_REASON_NAME, disputeReasonName);
	}

	public List findByDisputeReasonAcronym(Object disputeReasonAcronym) {
		return findByProperty(DISPUTE_REASON_ACRONYM, disputeReasonAcronym);
	}

	public List findByDisputeReasonDiscription(Object disputeReasonDiscription) {
		return findByProperty(DISPUTE_REASON_DISCRIPTION,
				disputeReasonDiscription);
	}

	public List findAll() {
		log.debug("finding all DisputeReason instances");
		try {
			String queryString = "from DisputeReason";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public DisputeReason merge(DisputeReason detachedInstance) {
		log.debug("merging DisputeReason instance");
		try {
			DisputeReason result = (DisputeReason) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(DisputeReason instance) {
		log.debug("attaching dirty DisputeReason instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(DisputeReason instance) {
		log.debug("attaching clean DisputeReason instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DisputeReasonDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (DisputeReasonDAO) ctx.getBean("DisputeReasonDAO");
	}
}