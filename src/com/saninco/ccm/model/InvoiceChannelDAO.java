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
 * InvoiceChannel entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.InvoiceChannel
 * @author MyEclipse Persistence Tools
 */

public class InvoiceChannelDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(InvoiceChannelDAO.class);
	// property constants
	public static final String INVOICE_CHANNEL_NAME = "invoiceChannelName";

	protected void initDao() {
		// do nothing
	}

	public void save(InvoiceChannel transientInstance) {
		log.debug("saving InvoiceChannel instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InvoiceChannel persistentInstance) {
		log.debug("deleting InvoiceChannel instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InvoiceChannel findById(java.lang.Integer id) {
		log.debug("getting InvoiceChannel instance with id: " + id);
		try {
			InvoiceChannel instance = (InvoiceChannel) getHibernateTemplate()
					.get("com.saninco.ccm.model.InvoiceChannel", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InvoiceChannel instance) {
		log.debug("finding InvoiceChannel instance by example");
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
		log.debug("finding InvoiceChannel instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from InvoiceChannel as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByInvoiceChannelName(Object invoiceChannelName) {
		return findByProperty(INVOICE_CHANNEL_NAME, invoiceChannelName);
	}

	public List findAll() {
		log.debug("finding all InvoiceChannel instances");
		try {
			String queryString = "from InvoiceChannel";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InvoiceChannel merge(InvoiceChannel detachedInstance) {
		log.debug("merging InvoiceChannel instance");
		try {
			InvoiceChannel result = (InvoiceChannel) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InvoiceChannel instance) {
		log.debug("attaching dirty InvoiceChannel instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InvoiceChannel instance) {
		log.debug("attaching clean InvoiceChannel instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InvoiceChannelDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (InvoiceChannelDAO) ctx.getBean("InvoiceChannelDAO");
	}
}