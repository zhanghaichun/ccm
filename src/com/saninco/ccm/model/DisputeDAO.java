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
 * Dispute entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.Dispute
 * @author MyEclipse Persistence Tools
 */

public class DisputeDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(DisputeDAO.class);
	// property constants
	public static final String DISPUTE_BALANCE = "disputeBalance";
	public static final String DISPUTE_AMOUNT = "disputeAmount";
	public static final String DISPUTE_NUMBER = "disputeNumber";
	public static final String CLAIM_NUMBER = "claimNumber";
	public static final String TICKET_NUMBER = "ticketNumber";
	public static final String NOTES = "notes";
	public static final String FLAG_WORKSPACE = "flagWorkspace";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String FLAG_SHORTPAID = "flagShortpaid";
	public static final String CONFIDENCE_LEVEL = "confidenceLevel";
	public static final String FLAG_RECURRING = "flagRecurring";
	public static final String RESERVE_AMOUNT = "reserveAmount";
	public static final String WIN_AMOUNT = "winAmount";
	public static final String PAYBACK_AMOUNT = "paybackAmount";
	public static final String CLOSE_AMOUNT = "closeAmount";
	public static final String HIST_DISPUTE_ID = "histDisputeId";

	protected void initDao() {
		// do nothing
	}

	public void save(Dispute transientInstance) {
		log.debug("saving Dispute instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Dispute persistentInstance) {
		log.debug("deleting Dispute instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Dispute findById(java.lang.Integer id) {
		log.debug("getting Dispute instance with id: " + id);
		try {
			Dispute instance = (Dispute) getHibernateTemplate().get(
					"com.saninco.ccm.model.Dispute", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Dispute instance) {
		log.debug("finding Dispute instance by example");
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
		log.debug("finding Dispute instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Dispute as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDisputeBalance(Object disputeBalance) {
		return findByProperty(DISPUTE_BALANCE, disputeBalance);
	}

	public List findByDisputeAmount(Object disputeAmount) {
		return findByProperty(DISPUTE_AMOUNT, disputeAmount);
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

	public List findByFlagWorkspace(Object flagWorkspace) {
		return findByProperty(FLAG_WORKSPACE, flagWorkspace);
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

	public List findByFlagShortpaid(Object flagShortpaid) {
		return findByProperty(FLAG_SHORTPAID, flagShortpaid);
	}

	public List findByConfidenceLevel(Object confidenceLevel) {
		return findByProperty(CONFIDENCE_LEVEL, confidenceLevel);
	}

	public List findByFlagRecurring(Object flagRecurring) {
		return findByProperty(FLAG_RECURRING, flagRecurring);
	}

	public List findByReserveAmount(Object reserveAmount) {
		return findByProperty(RESERVE_AMOUNT, reserveAmount);
	}

	public List findByWinAmount(Object winAmount) {
		return findByProperty(WIN_AMOUNT, winAmount);
	}

	public List findByPaybackAmount(Object paybackAmount) {
		return findByProperty(PAYBACK_AMOUNT, paybackAmount);
	}

	public List findByCloseAmount(Object closeAmount) {
		return findByProperty(CLOSE_AMOUNT, closeAmount);
	}

	public List findByHistDisputeId(Object histDisputeId) {
		return findByProperty(HIST_DISPUTE_ID, histDisputeId);
	}

	public List findAll() {
		log.debug("finding all Dispute instances");
		try {
			String queryString = "from Dispute";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Dispute merge(Dispute detachedInstance) {
		log.debug("merging Dispute instance");
		try {
			Dispute result = (Dispute) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Dispute instance) {
		log.debug("attaching dirty Dispute instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Dispute instance) {
		log.debug("attaching clean Dispute instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static DisputeDAO getFromApplicationContext(ApplicationContext ctx) {
		return (DisputeDAO) ctx.getBean("DisputeDAO");
	}
}