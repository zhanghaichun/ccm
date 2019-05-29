package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIDashboardListby;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIDashboardListby entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIDashboardListby
 * @author MyEclipse Persistence Tools
 */
public class BIDashboardListbyDaoImpl extends HibernateDaoSupport implements IBIDashboardListbyDao {
	private static final Log log = LogFactory
			.getLog(BIDashboardListbyDaoImpl.class);
	// property constants
	public static final String LISTBY_NAME = "listbyName";

	public List<BIDashboardListby> getListbyByMinNumber(String dashboardControlKey, Integer minNumber) {
		log.debug("getListbyByMinNumber finding BIDashboardListby instance with property: dashboardControlId value: " + dashboardControlKey +
				",minNumber value: " + minNumber);
		try {
			String queryString = "from BIDashboardListby as model where model.controlKey= ? and model.minNumber <= ? and model.isNoConditionFlag = 'N'";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, dashboardControlKey);
			queryObject.setParameter(1, minNumber);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("getListbyByMinNumber failed", re);
			throw re;
		}
	}
	
	public List<BIDashboardListby> getListbyByControlKey(String dashboardControlKey) {
		log.debug("getListbyByControlKey finding BIDashboardListby instance with property: dashboardControlId value: " + dashboardControlKey);
		try {
			String queryString = "from BIDashboardListby as model where model.controlKey= ? and model.isNoConditionFlag = 'Y'";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, dashboardControlKey);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("getListbyByControlKey failed", re);
			throw re;
		}
	}
	
	public List<BIDashboardListby> getAllNoConditionListby() {
		log.debug("getAllNoConditionListby finding BIDashboardListby where no condition");
		try {
			String queryString = "from BIDashboardListby as model where model.isNoConditionFlag = 'Y'";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("getAllNoConditionListby failed", re);
			throw re;
		}
	}
	
	public BIDashboardListby findById(java.lang.Integer id) {
		log.debug("getting BIDashboardListby instance with id: " + id);
		try {
			BIDashboardListby instance = (BIDashboardListby) getSession().get(
					"com.saninco.ccm.model.bi.BIDashboardListby", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BIDashboardListby instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BIDashboardListby as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findAll() {
		log.debug("finding all BIDashboardListby instances");
		try {
			String queryString = "from BIDashboardListby";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BIDashboardListby merge(BIDashboardListby detachedInstance) {
		log.debug("merging BIDashboardListby instance");
		try {
			BIDashboardListby result = (BIDashboardListby) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIDashboardListby instance) {
		log.debug("attaching dirty BIDashboardListby instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIDashboardListby instance) {
		log.debug("attaching clean BIDashboardListby instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}