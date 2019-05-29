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
 * InvoiceFormat entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.InvoiceFormat
 * @author MyEclipse Persistence Tools
 */

public class InvoiceFormatDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(InvoiceFormatDAO.class);
	// property constants
	public static final String INVOICE_FORMAT_CODE = "invoiceFormatCode";

	protected void initDao() {
		// do nothing
	}

	public void save(InvoiceFormat transientInstance) {
		log.debug("saving InvoiceFormat instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InvoiceFormat persistentInstance) {
		log.debug("deleting InvoiceFormat instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InvoiceFormat findById(java.lang.Integer id) {
		log.debug("getting InvoiceFormat instance with id: " + id);
		try {
			InvoiceFormat instance = (InvoiceFormat) getHibernateTemplate()
					.get("com.saninco.ccm.model.InvoiceFormat", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InvoiceFormat instance) {
		log.debug("finding InvoiceFormat instance by example");
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
		log.debug("finding InvoiceFormat instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from InvoiceFormat as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByInvoiceFormatCode(Object invoiceFormatCode) {
		return findByProperty(INVOICE_FORMAT_CODE, invoiceFormatCode);
	}

	public List findAll() {
		log.debug("finding all InvoiceFormat instances");
		try {
			String queryString = "from InvoiceFormat";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InvoiceFormat merge(InvoiceFormat detachedInstance) {
		log.debug("merging InvoiceFormat instance");
		try {
			InvoiceFormat result = (InvoiceFormat) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InvoiceFormat instance) {
		log.debug("attaching dirty InvoiceFormat instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InvoiceFormat instance) {
		log.debug("attaching clean InvoiceFormat instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InvoiceFormatDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (InvoiceFormatDAO) ctx.getBean("InvoiceFormatDAO");
	}
}