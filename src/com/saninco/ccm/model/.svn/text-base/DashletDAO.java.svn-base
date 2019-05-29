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
 * Dashlet entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.Dashlet
 * @author MyEclipse Persistence Tools
 */

public class DashletDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(DashletDAO.class);
	// property constants
	public static final String DASHLET_NAME = "dashletName";

	protected void initDao() {
		// do nothing
	}

	public void save(Dashlet transientInstance) {
		log.debug("saving Dashlet instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Dashlet persistentInstance) {
		log.debug("deleting Dashlet instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Dashlet findById(java.lang.Integer id) {
		log.debug("getting Dashlet instance with id: " + id);
		try {
			Dashlet instance = (Dashlet) getHibernateTemplate().get(
					"com.saninco.ccm.model.Dashlet", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Dashlet instance) {
		log.debug("finding Dashlet instance by example");
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
		log.debug("finding Dashlet instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Dashlet as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDashletName(Object dashletName) {
		return findByProperty(DASHLET_NAME, dashletName);
	}

	public List findAll() {
		log.debug("finding all Dashlet instances");
		try {
			String queryString = "from Dashlet";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Dashlet merge(Dashlet detachedInstance) {
		log.debug("merging Dashlet instance");
		try {
			Dashlet result = (Dashlet) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Dashlet instance) {
		log.debug("attaching dirty Dashlet instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Dashlet instance) {
		log.debug("attaching clean Dashlet instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DashletDAO getFromApplicationContext(ApplicationContext ctx) {
		return (DashletDAO) ctx.getBean("DashletDAO");
	}
}