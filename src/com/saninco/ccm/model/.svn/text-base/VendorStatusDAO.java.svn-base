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
 * VendorStatus entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.VendorStatus
 * @author MyEclipse Persistence Tools
 */

public class VendorStatusDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(VendorStatusDAO.class);
	// property constants
	public static final String VENDOR_STATUS_NAME = "vendorStatusName";

	protected void initDao() {
		// do nothing
	}

	public void save(VendorStatus transientInstance) {
		log.debug("saving VendorStatus instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(VendorStatus persistentInstance) {
		log.debug("deleting VendorStatus instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public VendorStatus findById(java.lang.Integer id) {
		log.debug("getting VendorStatus instance with id: " + id);
		try {
			VendorStatus instance = (VendorStatus) getHibernateTemplate().get(
					"com.saninco.ccm.model.VendorStatus", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(VendorStatus instance) {
		log.debug("finding VendorStatus instance by example");
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
		log.debug("finding VendorStatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from VendorStatus as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByVendorStatusName(Object vendorStatusName) {
		return findByProperty(VENDOR_STATUS_NAME, vendorStatusName);
	}

	public List findAll() {
		log.debug("finding all VendorStatus instances");
		try {
			String queryString = "from VendorStatus";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public VendorStatus merge(VendorStatus detachedInstance) {
		log.debug("merging VendorStatus instance");
		try {
			VendorStatus result = (VendorStatus) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(VendorStatus instance) {
		log.debug("attaching dirty VendorStatus instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(VendorStatus instance) {
		log.debug("attaching clean VendorStatus instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static VendorStatusDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (VendorStatusDAO) ctx.getBean("VendorStatusDAO");
	}
}