package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.bi.BIAuditReference;

/**
 * A data access object (DAO) providing persistence and search support for
 * BIAuditReference entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.bi.BIAuditReference
 * @author MyEclipse Persistence Tools
 */
public class BIAuditReferenceDaoImpl extends HibernateDaoSupport implements IBIAuditReferenceDao{
	private static final Log log = LogFactory.getLog(BIAuditReferenceDaoImpl.class);
	// property constants
	public static final String NAME = "name";
	public static final String REFERENCE_TYPE_NAME = "referenceTypeName";
	public static final String REFERENCE_ID = "referenceId";

	public void save(BIAuditReference transientInstance) {
		log.debug("saving BIAuditReference instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(BIAuditReference persistentInstance) {
		log.debug("deleting BIAuditReference instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BIAuditReference findById(java.lang.Integer id) {
		log.debug("getting BIAuditReference instance with id: " + id);
		try {
			BIAuditReference instance = (BIAuditReference) getHibernateTemplate().get(
					"com.saninco.ccm.model.bi.BIAuditReference", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(BIAuditReference instance) {
		log.debug("finding BIAuditReference instance by example");
		Session session = getSession();
		try {
			List results = session
					.createCriteria("com.saninco.ccm.model.bi.BIAuditReference")
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
		log.debug("finding BIAuditReference instance with property: "
				+ propertyName + ", value: " + value);
		Session session = getSession();
		try {
			String queryString = "from BIAuditReference as model where model."
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

	public List findByReferenceTypeName(Object referenceTypeName) {
		return findByProperty(REFERENCE_TYPE_NAME, referenceTypeName);
	}

	public List findByReferenceId(Object referenceId) {
		return findByProperty(REFERENCE_ID, referenceId);
	}

	public List findAll() {
		log.debug("finding all BIAuditReference instances");
		Session session = getSession();
		try {
			String queryString = "from BIAuditReference";
			Query queryObject = session.createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public BIAuditReference merge(BIAuditReference detachedInstance) {
		log.debug("merging BIAuditReference instance");
		try {
			BIAuditReference result = (BIAuditReference) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(BIAuditReference instance) {
		log.debug("attaching dirty BIAuditReference instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(BIAuditReference instance) {
		log.debug("attaching clean BIAuditReference instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}