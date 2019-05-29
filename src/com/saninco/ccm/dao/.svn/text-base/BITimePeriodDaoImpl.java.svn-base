package com.saninco.ccm.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BITimePeriod;

/**
 * A data access object (DAO) providing persistence and search support for
 * BITimePeriod entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BITimePeriod
 * @author MyEclipse Persistence Tools
 */
public class BITimePeriodDaoImpl extends HibernateDaoSupport implements IBITimePeriodDao {
	private static final Log log = LogFactory.getLog(BITimePeriodDaoImpl.class);
	// property constants
	public static final String LAST_PERIOD_TYPE = "lastPeriodType";
	public static final String LAST_PERIOD_NUMBER = "lastPeriodNumber";
	public static final String LIST_FLAG = "listFlag";

	public void save(BITimePeriod transientInstance) {
		log.debug("saving BITimePeriod instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BITimePeriod persistentInstance) {
		log.debug("deleting BITimePeriod instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BITimePeriod findById(java.lang.Integer id) {
		log.debug("getting BITimePeriod instance with id: " + id);
		try {
			BITimePeriod instance = (BITimePeriod) getSession().get(
					"com.saninco.ccm.model.bi.BITimePeriod", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BITimePeriod instance) {
		log.debug("finding BITimePeriod instance by example");
		try {
			List results = getSession()
					.createCriteria("com.saninco.ccm.model.bi.BITimePeriod")
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
		log.debug("finding BITimePeriod instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from BITimePeriod as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLastPeriodType(Object lastPeriodType) {
		return findByProperty(LAST_PERIOD_TYPE, lastPeriodType);
	}

	public List findByLastPeriodNumber(Object lastPeriodNumber) {
		return findByProperty(LAST_PERIOD_NUMBER, lastPeriodNumber);
	}

	public List findByListFlag(Object listFlag) {
		return findByProperty(LIST_FLAG, listFlag);
	}

	public List findAll() {
		log.debug("finding all BITimePeriod instances");
		try {
			String queryString = "from BITimePeriod";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
	
	/**
	 * 查询  tiem period List 因为需要where条件，将findAll 更替为 HQL 方式
	 */
	@SuppressWarnings("unchecked")
	public List<BITimePeriod> hqlFindTimePeriodList(String listType) {
		logger.info("Enter method findTimePeriodList.");
		Session session = getSession();
		try {
			
			String dashboardModuleType = "0"; // 默认为global listType 
			
			if("unQuoteType".equals(listType)) {
				dashboardModuleType = "1"; // 表示 dashboard module id 是 1|2|3 的类型 (unQuoteType)
			} else if("quoteType".equals(listType)) {
				dashboardModuleType = "4"; // 表示 dashboard module id 是 4|5 的类型 (quoteType)
			}
			
			StringBuffer sb = new StringBuffer();
			sb.append(" SELECT tp.id AS id,");
			sb.append(" tp.last_period_type AS lastPeriodType,");
			sb.append(" tp.last_period_number AS lastPeriodNumber,");
			sb.append(" tp.list_flag AS listFlag,");
			sb.append(" (SELECT bi_db.FN_GET_TIME_PERIOD_NAME(tp.id, "+ dashboardModuleType + ")) AS name");
			sb.append(" FROM bi_db.time_period tp WHERE (SELECT bi_db.FN_GET_TIME_PERIOD_NAME(tp.id, "+ dashboardModuleType + ")) is not null");
			List<Object[]> l = session.createSQLQuery(sb.toString()).list();
			List<BITimePeriod> resultList = new ArrayList<BITimePeriod>();
			if(l != null && l.size() > 0) {
				for(int i = 0; i < l.size(); i++){
					BITimePeriod b = new BITimePeriod();
					Object[] tp = l.get(i);
					b.setId((Integer)tp[0]);
					b.setLastPeriodType(tp[1].toString());
					b.setLastPeriodNumber((Integer)tp[2]);
					b.setListFlag(tp[3].toString());
					b.setName(tp[4].toString());
					resultList.add(b);
				}
			}
			
			logger.info("Exit method findTimePeriodList.");
			return resultList;
			
		} finally {
			releaseSession(session);
		}
	}

	public BITimePeriod merge(BITimePeriod detachedInstance) {
		log.debug("merging BITimePeriod instance");
		try {
			BITimePeriod result = (BITimePeriod) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BITimePeriod instance) {
		log.debug("attaching dirty BITimePeriod instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BITimePeriod instance) {
		log.debug("attaching clean BITimePeriod instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}