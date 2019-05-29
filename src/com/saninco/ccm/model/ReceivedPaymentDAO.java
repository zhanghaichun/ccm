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
 * ReceivedPayment entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.ReceivedPayment
 * @author MyEclipse Persistence Tools
 */

public class ReceivedPaymentDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(ReceivedPaymentDAO.class);
	// property constants
	public static final String PAYMENT_AMOUNT = "paymentAmount";
	public static final String REFERENCE_NUMBER = "referenceNumber";
	public static final String NOTES = "notes";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(ReceivedPayment transientInstance) {
		log.debug("saving ReceivedPayment instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(ReceivedPayment persistentInstance) {
		log.debug("deleting ReceivedPayment instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ReceivedPayment findById(java.lang.Integer id) {
		log.debug("getting ReceivedPayment instance with id: " + id);
		try {
			ReceivedPayment instance = (ReceivedPayment) getHibernateTemplate()
					.get("com.saninco.ccm.model.ReceivedPayment", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(ReceivedPayment instance) {
		log.debug("finding ReceivedPayment instance by example");
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
		log.debug("finding ReceivedPayment instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from ReceivedPayment as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPaymentAmount(Object paymentAmount) {
		return findByProperty(PAYMENT_AMOUNT, paymentAmount);
	}

	public List findByReferenceNumber(Object referenceNumber) {
		return findByProperty(REFERENCE_NUMBER, referenceNumber);
	}

	public List findByNotes(Object notes) {
		return findByProperty(NOTES, notes);
	}

	public List findByCreatedBy(Object createdBy) {
		return findByProperty(CREATED_BY, createdBy);
	}

	public List findByModifiedBy(Object modifiedBy) {
		return findByProperty(MODIFIED_BY, modifiedBy);
	}

	public List findByRecActiveFlag(Object recActiveFlag) {
		return findByProperty(REC_ACTIVE_FLAG, recActiveFlag);
	}

	public List findAll() {
		log.debug("finding all ReceivedPayment instances");
		try {
			String queryString = "from ReceivedPayment";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public ReceivedPayment merge(ReceivedPayment detachedInstance) {
		log.debug("merging ReceivedPayment instance");
		try {
			ReceivedPayment result = (ReceivedPayment) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(ReceivedPayment instance) {
		log.debug("attaching dirty ReceivedPayment instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(ReceivedPayment instance) {
		log.debug("attaching clean ReceivedPayment instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static ReceivedPaymentDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (ReceivedPaymentDAO) ctx.getBean("ReceivedPaymentDAO");
	}
}