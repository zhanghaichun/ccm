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
 * InvoiceStatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.InvoiceStatus
 * @author MyEclipse Persistence Tools
 */

public class InvoiceStatusDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(InvoiceStatusDAO.class);
	// property constants
	public static final String INVOICE_STATUS_NAME = "invoiceStatusName";

	protected void initDao() {
		// do nothing
	}

	public void save(InvoiceStatus transientInstance) {
		log.debug("saving InvoiceStatus instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InvoiceStatus persistentInstance) {
		log.debug("deleting InvoiceStatus instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InvoiceStatus findById(java.lang.Integer id) {
		log.debug("getting InvoiceStatus instance with id: " + id);
		try {
			InvoiceStatus instance = (InvoiceStatus) getHibernateTemplate()
					.get("com.saninco.ccm.model.InvoiceStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InvoiceStatus instance) {
		log.debug("finding InvoiceStatus instance by example");
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
		log.debug("finding InvoiceStatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from InvoiceStatus as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByInvoiceStatusName(Object invoiceStatusName) {
		return findByProperty(INVOICE_STATUS_NAME, invoiceStatusName);
	}

	public List findAll() {
		log.debug("finding all InvoiceStatus instances");
		try {
			String queryString = "from InvoiceStatus";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InvoiceStatus merge(InvoiceStatus detachedInstance) {
		log.debug("merging InvoiceStatus instance");
		try {
			InvoiceStatus result = (InvoiceStatus) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InvoiceStatus instance) {
		log.debug("attaching dirty InvoiceStatus instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InvoiceStatus instance) {
		log.debug("attaching clean InvoiceStatus instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InvoiceStatusDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (InvoiceStatusDAO) ctx.getBean("InvoiceStatusDAO");
	}
}