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
 * Severity entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.Severity
 * @author MyEclipse Persistence Tools
 */

public class SeverityDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(SeverityDAO.class);
	// property constants
	public static final String SEVERITY_NAME = "severityName";

	protected void initDao() {
		// do nothing
	}

	public void save(Severity transientInstance) {
		log.debug("saving Severity instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Severity persistentInstance) {
		log.debug("deleting Severity instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Severity findById(java.lang.Integer id) {
		log.debug("getting Severity instance with id: " + id);
		try {
			Severity instance = (Severity) getHibernateTemplate().get(
					"com.saninco.ccm.model.Severity", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Severity instance) {
		log.debug("finding Severity instance by example");
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
		log.debug("finding Severity instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Severity as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySeverityName(Object severityName) {
		return findByProperty(SEVERITY_NAME, severityName);
	}

	public List findAll() {
		log.debug("finding all Severity instances");
		try {
			String queryString = "from Severity";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Severity merge(Severity detachedInstance) {
		log.debug("merging Severity instance");
		try {
			Severity result = (Severity) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Severity instance) {
		log.debug("attaching dirty Severity instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Severity instance) {
		log.debug("attaching clean Severity instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static SeverityDAO getFromApplicationContext(ApplicationContext ctx) {
		return (SeverityDAO) ctx.getBean("SeverityDAO");
	}
}