package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIBudget;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIBudget entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIBudget
 * @author MyEclipse Persistence Tools
 */
public class BIBudgetDaoImpl extends HibernateDaoSupport implements IBIBudgetDao{
	private static final Log log = LogFactory.getLog(BIBudgetDaoImpl.class);
	// property constants
	public static final String NAME = "name";

	public void save(BIBudget transientInstance) {
		log.debug("saving BIBudget instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BIBudget persistentInstance) {
		log.debug("deleting BIBudget instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BIBudget findById(java.lang.Integer id) {
		log.debug("getting BIBudget instance with id: " + id);
		try {
			BIBudget instance = (BIBudget) getHibernateTemplate().get(
					"com.saninco.ccm.model.bi.BIBudget", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BIBudget instance) {
		log.debug("finding BIBudget instance by example");
		Session session = this.getSession();
		try {
			List results = session
					.createCriteria("com.saninco.ccm.model.bi.BIBudget")
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
		log.debug("finding BIBudget instance with property: " + propertyName
				+ ", value: " + value);
		Session session = this.getSession();
		try {
			String queryString = "from BIBudget as model where model."
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

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findAll() {
		log.debug("finding all BIBudget instances");
		Session session = this.getSession();
		try {
			String queryString = "from BIBudget as model where model.recActiveFlag = 'Y'";
			Query queryObject = session.createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}
	
	public List<BIBudget> findByOwner(String owner) {
		log.debug("enter findByOwner");
		Session session = this.getSession();
		try {
			String queryString = "from BIBudget as model where model.budgetOwner = ? and model.recActiveFlag = 'Y'";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, owner);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("findByOwner failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public BIBudget merge(BIBudget detachedInstance) {
		log.debug("merging BIBudget instance");
		try {
			BIBudget result = (BIBudget) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIBudget instance) {
		log.debug("attaching dirty BIBudget instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIBudget instance) {
		log.debug("attaching clean BIBudget instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}