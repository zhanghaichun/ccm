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
 * LabelType entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.LabelType
 * @author MyEclipse Persistence Tools
 */

public class LabelTypeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(LabelTypeDAO.class);
	// property constants
	public static final String LABEL_TYPE_NAME = "labelTypeName";

	protected void initDao() {
		// do nothing
	}

	public void save(LabelType transientInstance) {
		log.debug("saving LabelType instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(LabelType persistentInstance) {
		log.debug("deleting LabelType instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LabelType findById(java.lang.Integer id) {
		log.debug("getting LabelType instance with id: " + id);
		try {
			LabelType instance = (LabelType) getHibernateTemplate().get(
					"com.saninco.ccm.model.LabelType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(LabelType instance) {
		log.debug("finding LabelType instance by example");
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
		log.debug("finding LabelType instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from LabelType as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLabelTypeName(Object labelTypeName) {
		return findByProperty(LABEL_TYPE_NAME, labelTypeName);
	}

	public List findAll() {
		log.debug("finding all LabelType instances");
		try {
			String queryString = "from LabelType";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public LabelType merge(LabelType detachedInstance) {
		log.debug("merging LabelType instance");
		try {
			LabelType result = (LabelType) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(LabelType instance) {
		log.debug("attaching dirty LabelType instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(LabelType instance) {
		log.debug("attaching clean LabelType instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static LabelTypeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (LabelTypeDAO) ctx.getBean("LabelTypeDAO");
	}
}