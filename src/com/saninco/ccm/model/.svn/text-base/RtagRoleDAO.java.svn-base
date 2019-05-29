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
 * RtagRole entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.RtagRole
 * @author MyEclipse Persistence Tools
 */

public class RtagRoleDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(RtagRoleDAO.class);
	// property constants
	public static final String ACCESS_LEVEL = "accessLevel";
	public static final String SEND_EMAIL = "sendEmail";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(RtagRole transientInstance) {
		log.debug("saving RtagRole instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(RtagRole persistentInstance) {
		log.debug("deleting RtagRole instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public RtagRole findById(java.lang.Integer id) {
		log.debug("getting RtagRole instance with id: " + id);
		try {
			RtagRole instance = (RtagRole) getHibernateTemplate().get(
					"com.saninco.ccm.model.RtagRole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(RtagRole instance) {
		log.debug("finding RtagRole instance by example");
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
		log.debug("finding RtagRole instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from RtagRole as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccessLevel(Object accessLevel) {
		return findByProperty(ACCESS_LEVEL, accessLevel);
	}

	public List findBySendEmail(Object sendEmail) {
		return findByProperty(SEND_EMAIL, sendEmail);
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
		log.debug("finding all RtagRole instances");
		try {
			String queryString = "from RtagRole";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public RtagRole merge(RtagRole detachedInstance) {
		log.debug("merging RtagRole instance");
		try {
			RtagRole result = (RtagRole) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(RtagRole instance) {
		log.debug("attaching dirty RtagRole instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(RtagRole instance) {
		log.debug("attaching clean RtagRole instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static RtagRoleDAO getFromApplicationContext(ApplicationContext ctx) {
		return (RtagRoleDAO) ctx.getBean("RtagRoleDAO");
	}
}