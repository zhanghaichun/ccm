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
 * TaxCode entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.TaxCode
 * @author MyEclipse Persistence Tools
 */

public class TaxCodeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(TaxCodeDAO.class);
	// property constants
	public static final String TAX_CODE = "taxCode";
	public static final String TAX_CODE_DESCRIPTION = "taxCodeDescription";
	public static final String TAX_RATE = "taxRate";

	protected void initDao() {
		// do nothing
	}

	public void save(TaxCode transientInstance) {
		log.debug("saving TaxCode instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TaxCode persistentInstance) {
		log.debug("deleting TaxCode instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TaxCode findById(java.lang.Integer id) {
		log.debug("getting TaxCode instance with id: " + id);
		try {
			TaxCode instance = (TaxCode) getHibernateTemplate().get(
					"com.saninco.ccm.model.TaxCode", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TaxCode instance) {
		log.debug("finding TaxCode instance by example");
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
		log.debug("finding TaxCode instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TaxCode as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTaxCode(Object taxCode) {
		return findByProperty(TAX_CODE, taxCode);
	}

	public List findByTaxCodeDescription(Object taxCodeDescription) {
		return findByProperty(TAX_CODE_DESCRIPTION, taxCodeDescription);
	}

	public List findByTaxRate(Object taxRate) {
		return findByProperty(TAX_RATE, taxRate);
	}

	public List findAll() {
		log.debug("finding all TaxCode instances");
		try {
			String queryString = "from TaxCode";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TaxCode merge(TaxCode detachedInstance) {
		log.debug("merging TaxCode instance");
		try {
			TaxCode result = (TaxCode) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TaxCode instance) {
		log.debug("attaching dirty TaxCode instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TaxCode instance) {
		log.debug("attaching clean TaxCode instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TaxCodeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TaxCodeDAO) ctx.getBean("TaxCodeDAO");
	}
}