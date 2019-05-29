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
 * InternalInvoiceStatus entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.InternalInvoiceStatus
 * @author MyEclipse Persistence Tools
 */

public class InternalInvoiceStatusDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory
			.getLog(InternalInvoiceStatusDAO.class);
	// property constants
	public static final String STATUS_NAME = "statusName";

	protected void initDao() {
		// do nothing
	}

	public void save(InternalInvoiceStatus transientInstance) {
		log.debug("saving InternalInvoiceStatus instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InternalInvoiceStatus persistentInstance) {
		log.debug("deleting InternalInvoiceStatus instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InternalInvoiceStatus findById(java.lang.Integer id) {
		log.debug("getting InternalInvoiceStatus instance with id: " + id);
		try {
			InternalInvoiceStatus instance = (InternalInvoiceStatus) getHibernateTemplate()
					.get("com.saninco.ccm.model.InternalInvoiceStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InternalInvoiceStatus instance) {
		log.debug("finding InternalInvoiceStatus instance by example");
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
		log.debug("finding InternalInvoiceStatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from InternalInvoiceStatus as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByStatusName(Object statusName) {
		return findByProperty(STATUS_NAME, statusName);
	}

	public List findAll() {
		log.debug("finding all InternalInvoiceStatus instances");
		try {
			String queryString = "from InternalInvoiceStatus";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InternalInvoiceStatus merge(InternalInvoiceStatus detachedInstance) {
		log.debug("merging InternalInvoiceStatus instance");
		try {
			InternalInvoiceStatus result = (InternalInvoiceStatus) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InternalInvoiceStatus instance) {
		log.debug("attaching dirty InternalInvoiceStatus instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InternalInvoiceStatus instance) {
		log.debug("attaching clean InternalInvoiceStatus instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InternalInvoiceStatusDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (InternalInvoiceStatusDAO) ctx
				.getBean("InternalInvoiceStatusDAO");
	}
}