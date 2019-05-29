package com.saninco.ccm.model;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for Theme
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.saninco.ccm.model.Theme
 * @author MyEclipse Persistence Tools
 */

public class ThemeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ThemeDAO.class);
	// property constants
	public static final String THEME_NAME = "themeName";
	public static final String THEME_PATH = "themePath";

	protected void initDao() {
		// do nothing
	}

	public void save(Theme transientInstance) {
		log.debug("saving Theme instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Theme persistentInstance) {
		log.debug("deleting Theme instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Theme findById(java.lang.Integer id) {
		log.debug("getting Theme instance with id: " + id);
		try {
			Theme instance = (Theme) getHibernateTemplate().get(
					"com.saninco.ccm.model.Theme", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Theme instance) {
		log.debug("finding Theme instance by example");
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
		log.debug("finding Theme instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Theme as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByThemeName(Object themeName) {
		return findByProperty(THEME_NAME, themeName);
	}

	public List findByThemePath(Object themePath) {
		return findByProperty(THEME_PATH, themePath);
	}

	public List findAll() {
		log.debug("finding all Theme instances");
		try {
			String queryString = "from Theme";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Theme merge(Theme detachedInstance) {
		log.debug("merging Theme instance");
		try {
			Theme result = (Theme) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Theme instance) {
		log.debug("attaching dirty Theme instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Theme instance) {
		log.debug("attaching clean Theme instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ThemeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (ThemeDAO) ctx.getBean("ThemeDAO");
	}
}