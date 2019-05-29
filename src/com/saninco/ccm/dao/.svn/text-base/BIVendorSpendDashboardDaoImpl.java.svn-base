package com.saninco.ccm.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIDashboardListby;
import com.saninco.ccm.model.bi.BIVendorSpendDashboard;
import com.saninco.ccm.vo.dashboard.DashboardChartDataVO;
import com.saninco.ccm.vo.dashboard.DashboardListbyVO;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIVendorSpendDashboard entities. Transaction control of the save(), update()
 * and delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIVendorSpendDashboard
 * @author MyEclipse Persistence Tools
 */
public class BIVendorSpendDashboardDaoImpl extends HibernateDaoSupport implements IBIVendorSpendDashboardDao{
	private static final Log log = LogFactory
			.getLog(BIVendorSpendDashboardDaoImpl.class);
	// property constants
	public static final String YEAR_AND_MONTH = "yearAndMonth";
	public static final String QUARTER = "quarter";
	public static final String AMOUNT = "amount";
	public static final String YEAR = "year";

	
	
	public void save(BIVendorSpendDashboard transientInstance) {
		log.debug("saving BIVendorSpendDashboard instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BIVendorSpendDashboard persistentInstance) {
		log.debug("deleting BIVendorSpendDashboard instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BIVendorSpendDashboard findById(java.lang.Integer id) {
		log.debug("getting BIVendorSpendDashboard instance with id: " + id);
		try {
			BIVendorSpendDashboard instance = (BIVendorSpendDashboard) getSession()
					.get("com.saninco.ccm.model.bi.BIVendorSpendDashboard", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BIVendorSpendDashboard instance) {
		log.debug("finding BIVendorSpendDashboard instance by example");
		try {
			List results = getSession()
					.createCriteria(
							"com.saninco.ccm.model.bi.BIVendorSpendDashboard")
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
		log.debug("finding BIVendorSpendDashboard instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BIVendorSpendDashboard as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByYearAndMonth(Object yearAndMonth) {
		return findByProperty(YEAR_AND_MONTH, yearAndMonth);
	}

	public List findByQuarter(Object quarter) {
		return findByProperty(QUARTER, quarter);
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByYear(Object year) {
		return findByProperty(YEAR, year);
	}

	public List findAll() {
		log.debug("finding all BIVendorSpendDashboard instances");
		try {
			String queryString = "from BIVendorSpendDashboard";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BIVendorSpendDashboard merge(BIVendorSpendDashboard detachedInstance) {
		log.debug("merging BIVendorSpendDashboard instance");
		try {
			BIVendorSpendDashboard result = (BIVendorSpendDashboard) getSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIVendorSpendDashboard instance) {
		log.debug("attaching dirty BIVendorSpendDashboard instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIVendorSpendDashboard instance) {
		log.debug("attaching clean BIVendorSpendDashboard instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}