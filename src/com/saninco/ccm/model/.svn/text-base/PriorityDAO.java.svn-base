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
 * Priority entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.Priority
 * @author MyEclipse Persistence Tools
 */

public class PriorityDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PriorityDAO.class);
	// property constants
	public static final String PRIORITY_NAME = "priorityName";

	protected void initDao() {
		// do nothing
	}

	public void save(Priority transientInstance) {
		log.debug("saving Priority instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Priority persistentInstance) {
		log.debug("deleting Priority instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Priority findById(java.lang.Integer id) {
		log.debug("getting Priority instance with id: " + id);
		try {
			Priority instance = (Priority) getHibernateTemplate().get(
					"com.saninco.ccm.model.Priority", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Priority instance) {
		log.debug("finding Priority instance by example");
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
		log.debug("finding Priority instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Priority as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPriorityName(Object priorityName) {
		return findByProperty(PRIORITY_NAME, priorityName);
	}

	public List findAll() {
		log.debug("finding all Priority instances");
		try {
			String queryString = "from Priority";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Priority merge(Priority detachedInstance) {
		log.debug("merging Priority instance");
		try {
			Priority result = (Priority) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Priority instance) {
		log.debug("attaching dirty Priority instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Priority instance) {
		log.debug("attaching clean Priority instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PriorityDAO getFromApplicationContext(ApplicationContext ctx) {
		return (PriorityDAO) ctx.getBean("PriorityDAO");
	}
}