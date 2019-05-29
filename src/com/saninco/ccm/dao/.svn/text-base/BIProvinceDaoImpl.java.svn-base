package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIProvince;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIProvince entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIProvince
 * @author MyEclipse Persistence Tools
 */
public class BIProvinceDaoImpl extends HibernateDaoSupport implements IBIProvinceDao {
	private static final Log log = LogFactory.getLog(BIProvinceDaoImpl.class);
	// property constants
	public static final String PROVINCE_NAME = "provinceName";
	public static final String PROVINCE_ACRONYM = "provinceAcronym";

	public void save(BIProvince transientInstance) {
		log.debug("saving BIProvince instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BIProvince persistentInstance) {
		log.debug("deleting BIProvince instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BIProvince findById(java.lang.Integer id) {
		log.debug("getting BIProvince instance with id: " + id);
		try {
			BIProvince instance = (BIProvince) getSession().get(
					"com.saninco.ccm.model.bi.BIProvince", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BIProvince instance) {
		log.debug("finding BIProvince instance by example");
		try {
			List results = getSession()
					.createCriteria("com.saninco.ccm.model.bi.BIProvince")
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
		log.debug("finding BIProvince instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from BIProvince as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByProvinceName(Object provinceName) {
		return findByProperty(PROVINCE_NAME, provinceName);
	}

	public List findByProvinceAcronym(Object provinceAcronym) {
		return findByProperty(PROVINCE_ACRONYM, provinceAcronym);
	}

	public List findAll() {
		log.debug("finding all BIProvince instances");
		try {
			String queryString = "from BIProvince";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BIProvince merge(BIProvince detachedInstance) {
		log.debug("merging BIProvince instance");
		try {
			BIProvince result = (BIProvince) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIProvince instance) {
		log.debug("attaching dirty BIProvince instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIProvince instance) {
		log.debug("attaching clean BIProvince instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}