package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.TicketStatus;
 
/**
 * Spring Hibernate DAO class for TicketStatus based on MyEclipse generated 
 * DAO class with reverse engineering.
 * 
 * @author xinyu.Liu
 *
 */
@SuppressWarnings("unchecked")
public class TicketStatusDaoImpl extends HibernateDaoSupport implements ITicketStatusDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	// property constants
	public static final String TICKET_STATUS_NAME = "ticketStatusName";

	protected void initDao() {
		// do nothing
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketSta#save(com.saninco.ccm.model.TicketStatus)
	 */
	public void save(TicketStatus transientInstance) {
		logger.debug("saving TicketStatus instance");
		try {
			getHibernateTemplate().save(transientInstance);
			logger.debug("save successful");
		} catch (RuntimeException re) {
			logger.error("save failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketSta#delete(com.saninco.ccm.model.TicketStatus)
	 */
	public void delete(TicketStatus persistentInstance) {
		logger.debug("deleting TicketStatus instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			logger.debug("delete successful");
		} catch (RuntimeException re) {
			logger.error("delete failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketSta#findById(java.lang.Integer)
	 */
	public TicketStatus findById(java.lang.Integer id) {
		logger.debug("getting TicketStatus instance with id: " + id);
		try {
			TicketStatus instance = (TicketStatus) getHibernateTemplate().get(
					"com.saninco.ccm.model.TicketStatus", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketSta#findByExample(com.saninco.ccm.model.TicketStatus)
	 */
	public List findByExample(TicketStatus instance) {
		logger.debug("finding TicketStatus instance by example");
		try {
			List results = getHibernateTemplate().findByExample(instance);
			logger.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			logger.error("find by example failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketSta#findByProperty(java.lang.String, java.lang.Object)
	 */
	public List findByProperty(String propertyName, Object value) {
		logger.debug("finding TicketStatus instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from TicketStatus as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			logger.error("find by property name failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketSta#findByTicketStatusName(java.lang.Object)
	 */
	public List findByTicketStatusName(Object ticketStatusName) {
		return findByProperty(TICKET_STATUS_NAME, ticketStatusName);
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketSta#findAll()
	 */
	public List findAll() {
		logger.debug("finding all TicketStatus instances");
		try {
			String queryString = "from TicketStatus order by ticketStatusName asc ";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketSta#merge(com.saninco.ccm.model.TicketStatus)
	 */
	public TicketStatus merge(TicketStatus detachedInstance) {
		logger.debug("merging TicketStatus instance");
		try {
			TicketStatus result = (TicketStatus) getHibernateTemplate().merge(
					detachedInstance);
			logger.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			logger.error("merge failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketSta#attachDirty(com.saninco.ccm.model.TicketStatus)
	 */
	public void attachDirty(TicketStatus instance) {
		logger.debug("attaching dirty TicketStatus instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ITicketSta#attachClean(com.saninco.ccm.model.TicketStatus)
	 */
	public void attachClean(TicketStatus instance) {
		logger.debug("attaching clean TicketStatus instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			logger.debug("attach successful");
		} catch (RuntimeException re) {
			logger.error("attach failed", re);
			throw re;
		}
	}

}
