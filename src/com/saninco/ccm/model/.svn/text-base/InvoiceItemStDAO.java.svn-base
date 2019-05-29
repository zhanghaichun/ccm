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
 * InvoiceItemSt entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.InvoiceItemSt
 * @author MyEclipse Persistence Tools
 */

public class InvoiceItemStDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(InvoiceItemStDAO.class);
	// property constants
	public static final String DISQUALIFIED_FLAG = "disqualifiedFlag";
	public static final String SEQUENCE = "sequence";
	public static final String ITEM_NAME = "itemName";
	public static final String ITEM_AMOUNT = "itemAmount";
	public static final String RATE = "rate";
	public static final String ORIGINAL_AMOUNT = "originalAmount";
	public static final String DISCOUNT = "discount";
	public static final String QUANTITY = "quantity";
	public static final String CIRCUIT_NUMBER = "circuitNumber";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(InvoiceItemSt transientInstance) {
		log.debug("saving InvoiceItemSt instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InvoiceItemSt persistentInstance) {
		log.debug("deleting InvoiceItemSt instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InvoiceItemSt findById(java.lang.Long id) {
		log.debug("getting InvoiceItemSt instance with id: " + id);
		try {
			InvoiceItemSt instance = (InvoiceItemSt) getHibernateTemplate()
					.get("com.saninco.ccm.model.InvoiceItemSt", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InvoiceItemSt instance) {
		log.debug("finding InvoiceItemSt instance by example");
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
		log.debug("finding InvoiceItemSt instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from InvoiceItemSt as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByDisqualifiedFlag(Object disqualifiedFlag) {
		return findByProperty(DISQUALIFIED_FLAG, disqualifiedFlag);
	}

	public List findBySequence(Object sequence) {
		return findByProperty(SEQUENCE, sequence);
	}

	public List findByItemName(Object itemName) {
		return findByProperty(ITEM_NAME, itemName);
	}

	public List findByItemAmount(Object itemAmount) {
		return findByProperty(ITEM_AMOUNT, itemAmount);
	}

	public List findByRate(Object rate) {
		return findByProperty(RATE, rate);
	}

	public List findByOriginalAmount(Object originalAmount) {
		return findByProperty(ORIGINAL_AMOUNT, originalAmount);
	}

	public List findByDiscount(Object discount) {
		return findByProperty(DISCOUNT, discount);
	}

	public List findByQuantity(Object quantity) {
		return findByProperty(QUANTITY, quantity);
	}

	public List findByCircuitNumber(Object circuitNumber) {
		return findByProperty(CIRCUIT_NUMBER, circuitNumber);
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
		log.debug("finding all InvoiceItemSt instances");
		try {
			String queryString = "from InvoiceItemSt";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InvoiceItemSt merge(InvoiceItemSt detachedInstance) {
		log.debug("merging InvoiceItemSt instance");
		try {
			InvoiceItemSt result = (InvoiceItemSt) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InvoiceItemSt instance) {
		log.debug("attaching dirty InvoiceItemSt instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InvoiceItemSt instance) {
		log.debug("attaching clean InvoiceItemSt instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InvoiceItemStDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (InvoiceItemStDAO) ctx.getBean("InvoiceItemStDAO");
	}
}