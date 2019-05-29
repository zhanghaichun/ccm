package com.saninco.ccm.model;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * TariffLink entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.TariffLink
 * @author MyEclipse Persistence Tools
 */

public class TariffLinkDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(TariffLinkDAO.class);
	// property constants
	public static final String ITEM_KEY = "itemKey";
	public static final String TARIFF_NAME = "tariffName";
	public static final String TARIFF_PATH = "tariffPath";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(TariffLink transientInstance) {
		log.debug("saving TariffLink instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(TariffLink persistentInstance) {
		log.debug("deleting TariffLink instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TariffLink findById(java.lang.Integer id) {
		log.debug("getting TariffLink instance with id: " + id);
		try {
			TariffLink instance = (TariffLink) getHibernateTemplate().get(
					"com.saninco.ccm.model.TariffLink", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(TariffLink instance) {
		log.debug("finding TariffLink instance by example");
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
		log.debug("finding TariffLink instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from TariffLink as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByItemKey(Object itemKey) {
		return findByProperty(ITEM_KEY, itemKey);
	}

	public List findByTariffName(Object tariffName) {
		return findByProperty(TARIFF_NAME, tariffName);
	}

	public List findByTariffPath(Object tariffPath) {
		return findByProperty(TARIFF_PATH, tariffPath);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	public List findAll() {
		log.debug("finding all TariffLink instances");
		try {
			String queryString = "from TariffLink";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public TariffLink merge(TariffLink detachedInstance) {
		log.debug("merging TariffLink instance");
		try {
			TariffLink result = (TariffLink) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(TariffLink instance) {
		log.debug("attaching dirty TariffLink instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TariffLink instance) {
		log.debug("attaching clean TariffLink instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static TariffLinkDAO getFromApplicationContext(ApplicationContext ctx) {
		return (TariffLinkDAO) ctx.getBean("TariffLinkDAO");
	}
}