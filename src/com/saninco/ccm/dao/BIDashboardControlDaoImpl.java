package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIDashboardControl;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIDashboardControl entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIDashboardControl
 * @author MyEclipse Persistence Tools
 */
public class BIDashboardControlDaoImpl extends HibernateDaoSupport implements IBIDashboardControlDao {
	
	private static final Log log = LogFactory
			.getLog(BIDashboardControlDaoImpl.class);
	// property constants
	public static final String CONTROL_NAME = "controlName";
	public static final String CONTROL_KEY = "controlKey";

	public void save(BIDashboardControl transientInstance) {
		log.debug("saving BIDashboardControl instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BIDashboardControl persistentInstance) {
		log.debug("deleting BIDashboardControl instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BIDashboardControl findById(java.lang.Integer id) {
		log.debug("getting BIDashboardControl instance with id: " + id);
		try {
			BIDashboardControl instance = (BIDashboardControl) getHibernateTemplate()
					.get("com.saninco.ccm.model.bi.BIDashboardControl", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BIDashboardControl instance) {
		log.debug("finding BIDashboardControl instance by example");
		Session session = this.getSession();
		try {
			List results = session
					.createCriteria(
							"com.saninco.ccm.model.bi.BIDashboardControl")
					.add(Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BIDashboardControl instance with property: "
				+ propertyName + ", value: " + value);
		Session session = this.getSession();
		try {
			String queryString = "from BIDashboardControl as model where model."
					+ propertyName + "= ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public List findByControlName(Object controlName) {
		return findByProperty(CONTROL_NAME, controlName);
	}

	public List findByControlKey(Object controlKey) {
		return findByProperty(CONTROL_KEY, controlKey);
	}

	public List findAll() {
		log.debug("finding all BIDashboardControl instances");
		Session session = this.getSession();
		try {
			String queryString = "from BIDashboardControl";
			Query queryObject = session.createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public BIDashboardControl merge(BIDashboardControl detachedInstance) {
		log.debug("merging BIDashboardControl instance");
		try {
			BIDashboardControl result = (BIDashboardControl) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIDashboardControl instance) {
		log.debug("attaching dirty BIDashboardControl instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIDashboardControl instance) {
		log.debug("attaching clean BIDashboardControl instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}