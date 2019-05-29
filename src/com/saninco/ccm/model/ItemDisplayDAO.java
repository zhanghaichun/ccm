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
 * ItemDisplay entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.ItemDisplay
 * @author MyEclipse Persistence Tools
 */

public class ItemDisplayDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ItemDisplayDAO.class);
	// property constants
	public static final String DISPLAY_TITLE = "displayTitle";
	public static final String DISPLAY_FORMAT = "displayFormat";
	public static final String DISPLAY_ORDER = "displayOrder";
	public static final String DISPLAY_WIDTH = "displayWidth";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(ItemDisplay transientInstance) {
		log.debug("saving ItemDisplay instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ItemDisplay persistentInstance) {
		log.debug("deleting ItemDisplay instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ItemDisplay findById(java.lang.Integer id) {
		log.debug("getting ItemDisplay instance with id: " + id);
		try {
			ItemDisplay instance = (ItemDisplay) getHibernateTemplate().get(
					"com.saninco.ccm.model.ItemDisplay", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ItemDisplay instance) {
		log.debug("finding ItemDisplay instance by example");
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
		log.debug("finding ItemDisplay instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from ItemDisplay as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDisplayTitle(Object displayTitle) {
		return findByProperty(DISPLAY_TITLE, displayTitle);
	}

	public List findByDisplayFormat(Object displayFormat) {
		return findByProperty(DISPLAY_FORMAT, displayFormat);
	}

	public List findByDisplayOrder(Object displayOrder) {
		return findByProperty(DISPLAY_ORDER, displayOrder);
	}

	public List findByDisplayWidth(Object displayWidth) {
		return findByProperty(DISPLAY_WIDTH, displayWidth);
	}

	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	public List findAll() {
		log.debug("finding all ItemDisplay instances");
		try {
			String queryString = "from ItemDisplay";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ItemDisplay merge(ItemDisplay detachedInstance) {
		log.debug("merging ItemDisplay instance");
		try {
			ItemDisplay result = (ItemDisplay) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ItemDisplay instance) {
		log.debug("attaching dirty ItemDisplay instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ItemDisplay instance) {
		log.debug("attaching clean ItemDisplay instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ItemDisplayDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ItemDisplayDAO) ctx.getBean("ItemDisplayDAO");
	}
}