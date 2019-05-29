package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIDashboardModule;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIDashboardModule entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIDashboardModule
 * @author MyEclipse Persistence Tools
 */
public class BIDashboardModuleDaoImpl extends HibernateDaoSupport implements IBIDashboardModuleDao {
	private static final Log log = LogFactory
			.getLog(BIDashboardModuleDaoImpl.class);
	// property constants
	public static final String MODULE_NAME = "moduleName";

	public void save(BIDashboardModule transientInstance) {
		log.debug("saving BIDashboardModule instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BIDashboardModule persistentInstance) {
		log.debug("deleting BIDashboardModule instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BIDashboardModule findById(java.lang.Integer id) {
		log.debug("getting BIDashboardModule instance with id: " + id);
		try {
			BIDashboardModule instance = (BIDashboardModule) getSession().get(
					"com.saninco.ccm.model.bi.BIDashboardModule", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BIDashboardModule instance) {
		log.debug("finding BIDashboardModule instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.saninco.ccm.model.bi.BIDashboardModule")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BIDashboardModule instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BIDashboardModule as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByModuleName(Object moduleName) {
		return findByProperty(MODULE_NAME, moduleName);
	}

	public List findAll() {
		log.debug("finding all BIDashboardModule instances");
		try {
			String queryString = "from BIDashboardModule";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BIDashboardModule merge(BIDashboardModule detachedInstance) {
		log.debug("merging BIDashboardModule instance");
		try {
			BIDashboardModule result = (BIDashboardModule) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIDashboardModule instance) {
		log.debug("attaching dirty BIDashboardModule instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIDashboardModule instance) {
		log.debug("attaching clean BIDashboardModule instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}