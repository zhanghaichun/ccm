package com.saninco.ccm.model;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Rtag
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.saninco.ccm.model.Rtag
 * @author MyEclipse Persistence Tools
 */

public class RtagDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(RtagDAO.class);
	// property constants
	public static final String RTAG_NAME = "rtagName";
	public static final String RTAG_COLOR = "rtagColor";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(Rtag transientInstance) {
		log.debug("saving Rtag instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Rtag persistentInstance) {
		log.debug("deleting Rtag instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Rtag findById(java.lang.Integer id) {
		log.debug("getting Rtag instance with id: " + id);
		try {
			Rtag instance = (Rtag) getHibernateTemplate().get(
					"com.saninco.ccm.model.Rtag", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Rtag instance) {
		log.debug("finding Rtag instance by example");
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
		log.debug("finding Rtag instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Rtag as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByRtagName(Object rtagName) {
		return findByProperty(RTAG_NAME, rtagName);
	}

	public List findByRtagColor(Object rtagColor) {
		return findByProperty(RTAG_COLOR, rtagColor);
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
		log.debug("finding all Rtag instances");
		try {
			String queryString = "from Rtag";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Rtag merge(Rtag detachedInstance) {
		log.debug("merging Rtag instance");
		try {
			Rtag result = (Rtag) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Rtag instance) {
		log.debug("attaching dirty Rtag instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Rtag instance) {
		log.debug("attaching clean Rtag instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RtagDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RtagDAO) ctx.getBean("RtagDAO");
	}
}