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
 * InvoiceSt entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.InvoiceSt
 * @author MyEclipse Persistence Tools
 */

public class InvoiceStDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(InvoiceStDAO.class);
	// property constants
	public static final String INVOICE_NUMBER = "invoiceNumber";
	public static final String INVOICE_PREVIOUS_BALANCE = "invoicePreviousBalance";
	public static final String INVOICE_PREVIOUS_PAYMENT = "invoicePreviousPayment";
	public static final String INVOICE_BALANCE_FORWARD = "invoiceBalanceForward";
	public static final String INVOICE_CURRENT_DUE = "invoiceCurrentDue";
	public static final String INVOICE_MIN_DUE = "invoiceMinDue";
	public static final String INVOICE_TOTAL_DUE = "invoiceTotalDue";
	public static final String INVOICE_STATUS = "invoiceStatus";
	public static final String BAR_CODE = "barCode";
	public static final String STAFF_NAME = "staffName";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(InvoiceSt transientInstance) {
		log.debug("saving InvoiceSt instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(InvoiceSt persistentInstance) {
		log.debug("deleting InvoiceSt instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public InvoiceSt findById(java.lang.Integer id) {
		log.debug("getting InvoiceSt instance with id: " + id);
		try {
			InvoiceSt instance = (InvoiceSt) getHibernateTemplate().get(
					"com.saninco.ccm.model.InvoiceSt", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(InvoiceSt instance) {
		log.debug("finding InvoiceSt instance by example");
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
		log.debug("finding InvoiceSt instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from InvoiceSt as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByInvoiceNumber(Object invoiceNumber) {
		return findByProperty(INVOICE_NUMBER, invoiceNumber);
	}

	public List findByInvoicePreviousBalance(Object invoicePreviousBalance) {
		return findByProperty(INVOICE_PREVIOUS_BALANCE, invoicePreviousBalance);
	}

	public List findByInvoicePreviousPayment(Object invoicePreviousPayment) {
		return findByProperty(INVOICE_PREVIOUS_PAYMENT, invoicePreviousPayment);
	}

	public List findByInvoiceBalanceForward(Object invoiceBalanceForward) {
		return findByProperty(INVOICE_BALANCE_FORWARD, invoiceBalanceForward);
	}

	public List findByInvoiceCurrentDue(Object invoiceCurrentDue) {
		return findByProperty(INVOICE_CURRENT_DUE, invoiceCurrentDue);
	}

	public List findByInvoiceMinDue(Object invoiceMinDue) {
		return findByProperty(INVOICE_MIN_DUE, invoiceMinDue);
	}

	public List findByInvoiceTotalDue(Object invoiceTotalDue) {
		return findByProperty(INVOICE_TOTAL_DUE, invoiceTotalDue);
	}

	public List findByInvoiceStatus(Object invoiceStatus) {
		return findByProperty(INVOICE_STATUS, invoiceStatus);
	}

	public List findByBarCode(Object barCode) {
		return findByProperty(BAR_CODE, barCode);
	}

	public List findByStaffName(Object staffName) {
		return findByProperty(STAFF_NAME, staffName);
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
		log.debug("finding all InvoiceSt instances");
		try {
			String queryString = "from InvoiceSt";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public InvoiceSt merge(InvoiceSt detachedInstance) {
		log.debug("merging InvoiceSt instance");
		try {
			InvoiceSt result = (InvoiceSt) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(InvoiceSt instance) {
		log.debug("attaching dirty InvoiceSt instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(InvoiceSt instance) {
		log.debug("attaching clean InvoiceSt instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InvoiceStDAO getFromApplicationContext(ApplicationContext ctx) {
		return (InvoiceStDAO) ctx.getBean("InvoiceStDAO");
	}
}