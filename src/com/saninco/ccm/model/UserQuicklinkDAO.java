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
 * UserQuicklink entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.UserQuicklink
 * @author MyEclipse Persistence Tools
 */

public class UserQuicklinkDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(UserQuicklinkDAO.class);
	// property constants
	public static final String QUICKLINK_NAME = "quicklinkName";
	public static final String REQUEST_STRING = "requestString";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String QUICKLINK_TYPE = "quicklinkType";

	protected void initDao() {
		// do nothing
	}

	public void save(UserQuicklink transientInstance) {
		log.debug("saving UserQuicklink instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(UserQuicklink persistentInstance) {
		log.debug("deleting UserQuicklink instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public UserQuicklink findById(java.lang.Integer id) {
		log.debug("getting UserQuicklink instance with id: " + id);
		try {
			UserQuicklink instance = (UserQuicklink) getHibernateTemplate()
					.get("com.saninco.ccm.model.UserQuicklink", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(UserQuicklink instance) {
		log.debug("finding UserQuicklink instance by example");
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
		log.debug("finding UserQuicklink instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from UserQuicklink as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByQuicklinkName(Object quicklinkName) {
		return findByProperty(QUICKLINK_NAME, quicklinkName);
	}

	public List findByRequestString(Object requestString) {
		return findByProperty(REQUEST_STRING, requestString);
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

	public List findByQuicklinkType(Object quicklinkType) {
		return findByProperty(QUICKLINK_TYPE, quicklinkType);
	}

	public List findAll() {
		log.debug("finding all UserQuicklink instances");
		try {
			String queryString = "from UserQuicklink";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public UserQuicklink merge(UserQuicklink detachedInstance) {
		log.debug("merging UserQuicklink instance");
		try {
			UserQuicklink result = (UserQuicklink) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(UserQuicklink instance) {
		log.debug("attaching dirty UserQuicklink instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(UserQuicklink instance) {
		log.debug("attaching clean UserQuicklink instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static UserQuicklinkDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (UserQuicklinkDAO) ctx.getBean("UserQuicklinkDAO");
	}
}