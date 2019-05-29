package com.saninco.ccm.model;

import java.util.Date;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * Remittance entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.Remittance
 * @author MyEclipse Persistence Tools
 */

public class RemittanceDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(RemittanceDAO.class);
	// property constants
	public static final String INVOICE_NUMBER = "invoiceNumber";
	public static final String AP_SUPPLIER_NUMBER = "apSupplierNumber";
	public static final String PAYMENT_AMOUNT = "paymentAmount";
	public static final String REMITTANCE_STATUS = "remittanceStatus";
	public static final String PROCESS_DESCRIPTION = "processDescription";
	public static final String PAYMENT_REFERENCE_CODE = "paymentReferenceCode";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	public void save(Remittance transientInstance) {
		log.debug("saving Remittance instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Remittance persistentInstance) {
		log.debug("deleting Remittance instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Remittance findById(java.lang.Integer id) {
		log.debug("getting Remittance instance with id: " + id);
		try {
			Remittance instance = (Remittance) getSession().get(
					"com.saninco.ccm.model.Remittance", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Remittance instance) {
		log.debug("finding Remittance instance by example");
		try {
			List results = getSession().createCriteria(
					"com.saninco.ccm.model.Remittance").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Remittance instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Remittance as model where model."
					+ propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByInvoiceNumber(Object invoiceNumber) {
		return findByProperty(INVOICE_NUMBER, invoiceNumber);
	}

	public List findByApSupplierNumber(Object apSupplierNumber) {
		return findByProperty(AP_SUPPLIER_NUMBER, apSupplierNumber);
	}

	public List findByPaymentAmount(Object paymentAmount) {
		return findByProperty(PAYMENT_AMOUNT, paymentAmount);
	}

	public List findByRemittanceStatus(Object remittanceStatus) {
		return findByProperty(REMITTANCE_STATUS, remittanceStatus);
	}

	public List findByProcessDescription(Object processDescription) {
		return findByProperty(PROCESS_DESCRIPTION, processDescription);
	}

	public List findByPaymentReferenceCode(Object paymentReferenceCode) {
		return findByProperty(PAYMENT_REFERENCE_CODE, paymentReferenceCode);
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
		log.debug("finding all Remittance instances");
		try {
			String queryString = "from Remittance";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Remittance merge(Remittance detachedInstance) {
		log.debug("merging Remittance instance");
		try {
			Remittance result = (Remittance) getSession().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Remittance instance) {
		log.debug("attaching dirty Remittance instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Remittance instance) {
		log.debug("attaching clean Remittance instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}