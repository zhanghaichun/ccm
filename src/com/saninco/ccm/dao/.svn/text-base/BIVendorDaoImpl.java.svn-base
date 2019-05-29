package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIVendor;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIVendor entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIVendor
 * @author MyEclipse Persistence Tools
 */
public class BIVendorDaoImpl extends HibernateDaoSupport implements IBIVendorDao {
	private static final Log log = LogFactory.getLog(BIVendorDaoImpl.class);
	// property constants
	public static final String VENDOR_NAME = "vendorName";

	public void save(BIVendor transientInstance) {
		log.debug("saving BIVendor instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BIVendor persistentInstance) {
		log.debug("deleting BIVendor instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BIVendor findById(java.lang.Integer id) {
		log.debug("getting BIVendor instance with id: " + id);
		try {
			BIVendor instance = (BIVendor) getSession().get(
					"com.saninco.ccm.model.bi.BIVendor", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BIVendor instance) {
		log.debug("finding BIVendor instance by example");
		try {
			List results = getSession()
					.createCriteria("com.saninco.ccm.model.bi.BIVendor")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BIVendor instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BIVendor as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByVendorName(Object vendorName) {
		return findByProperty(VENDOR_NAME, vendorName);
	}

	public List findAll() {
		log.debug("finding all BIVendor instances");
		try {
			String queryString = "from BIVendor";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BIVendor merge(BIVendor detachedInstance) {
		log.debug("merging BIVendor instance");
		try {
			BIVendor result = (BIVendor) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIVendor instance) {
		log.debug("attaching dirty BIVendor instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIVendor instance) {
		log.debug("attaching clean BIVendor instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}