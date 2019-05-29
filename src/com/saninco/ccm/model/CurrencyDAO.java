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
 * Currency entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.Currency
 * @author MyEclipse Persistence Tools
 */

public class CurrencyDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(CurrencyDAO.class);
	// property constants
	public static final String CURRENCY_NAME = "currencyName";
	public static final String CURRENCY_DESCRIPTION = "currencyDescription";

	protected void initDao() {
		// do nothing
	}

	public void save(Currency transientInstance) {
		log.debug("saving Currency instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Currency persistentInstance) {
		log.debug("deleting Currency instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Currency findById(java.lang.Integer id) {
		log.debug("getting Currency instance with id: " + id);
		try {
			Currency instance = (Currency) getHibernateTemplate().get(
					"com.saninco.ccm.model.Currency", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Currency instance) {
		log.debug("finding Currency instance by example");
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
		log.debug("finding Currency instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Currency as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCurrencyName(Object currencyName) {
		return findByProperty(CURRENCY_NAME, currencyName);
	}

	public List findByCurrencyDescription(Object currencyDescription) {
		return findByProperty(CURRENCY_DESCRIPTION, currencyDescription);
	}

	public List findAll() {
		log.debug("finding all Currency instances");
		try {
			String queryString = "from Currency";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Currency merge(Currency detachedInstance) {
		log.debug("merging Currency instance");
		try {
			Currency result = (Currency) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Currency instance) {
		log.debug("attaching dirty Currency instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Currency instance) {
		log.debug("attaching clean Currency instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CurrencyDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CurrencyDAO) ctx.getBean("CurrencyDAO");
	}
}