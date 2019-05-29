package com.saninco.ccm.model;

import java.util.Date;
import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Credit entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.Credit
 * @author MyEclipse Persistence Tools
 */

public class CreditDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(CreditDAO.class);
	// property constants
	public static final String CREDIT_AMOUNT = "creditAmount";
	public static final String CREDIT_BALANCE = "creditBalance";
	public static final String REFERENCE_NUMBER = "referenceNumber";
	public static final String DISPUTE_NUMBER = "disputeNumber";
	public static final String CLAIM_NUMBER = "claimNumber";
	public static final String TICKET_NUMBER = "ticketNumber";
	public static final String NOTES = "notes";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String DISPUTE_CATEGORY = "disputeCategory";
	public static final String CIRCUIT_NUMBER = "circuitNumber";

	protected void initDao() {
		// do nothing
	}

	public void save(Credit transientInstance) {
		log.debug("saving Credit instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Credit persistentInstance) {
		log.debug("deleting Credit instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Credit findById(java.lang.Integer id) {
		log.debug("getting Credit instance with id: " + id);
		try {
			Credit instance = (Credit) getHibernateTemplate().get(
					"com.saninco.ccm.model.Credit", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Credit instance) {
		log.debug("finding Credit instance by example");
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
		log.debug("finding Credit instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Credit as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCreditAmount(Object creditAmount) {
		return findByProperty(CREDIT_AMOUNT, creditAmount);
	}

	public List findByCreditBalance(Object creditBalance) {
		return findByProperty(CREDIT_BALANCE, creditBalance);
	}

	public List findByReferenceNumber(Object referenceNumber) {
		return findByProperty(REFERENCE_NUMBER, referenceNumber);
	}

	public List findByDisputeNumber(Object disputeNumber) {
		return findByProperty(DISPUTE_NUMBER, disputeNumber);
	}

	public List findByClaimNumber(Object claimNumber) {
		return findByProperty(CLAIM_NUMBER, claimNumber);
	}

	public List findByTicketNumber(Object ticketNumber) {
		return findByProperty(TICKET_NUMBER, ticketNumber);
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

	public List findByDisputeCategory(Object disputeCategory) {
		return findByProperty(DISPUTE_CATEGORY, disputeCategory);
	}

	public List findByCircuitNumber(Object circuitNumber) {
		return findByProperty(CIRCUIT_NUMBER, circuitNumber);
	}

	public List findAll() {
		log.debug("finding all Credit instances");
		try {
			String queryString = "from Credit";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Credit merge(Credit detachedInstance) {
		log.debug("merging Credit instance");
		try {
			Credit result = (Credit) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Credit instance) {
		log.debug("attaching dirty Credit instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Credit instance) {
		log.debug("attaching clean Credit instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static CreditDAO getFromApplicationContext(ApplicationContext ctx) {
		return (CreditDAO) ctx.getBean("CreditDAO");
	}
}