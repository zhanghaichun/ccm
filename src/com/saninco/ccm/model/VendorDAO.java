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
 * Vendor entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.Vendor
 * @author MyEclipse Persistence Tools
 */

public class VendorDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(VendorDAO.class);
	// property constants
	public static final String VENDOR_NAME = "vendorName";
	public static final String VENDOR_PRIMARY_CONTACT_ID = "vendorPrimaryContactId";
	public static final String VENDOR_DISPUTE_CONTACT_ID = "vendorDisputeContactId";
	public static final String VENDOR_PAYMENT_CONTACT_ID = "vendorPaymentContactId";
	public static final String VENDOR_TARIFF_CONTACT_ID = "vendorTariffContactId";
	public static final String VENDOR_BILLING_CONTACT_ID = "vendorBillingContactId";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String SUMMARY_VENDOR_NAME = "summaryVendorName";
	public static final String VENDOR_ACRONYM = "vendorAcronym";
	public static final String VENDOR_HIST_ID = "vendorHistId";
	public static final String HIST_VENDOR_ID = "histVendorId";

	protected void initDao() {
		// do nothing
	}

	public void save(Vendor transientInstance) {
		log.debug("saving Vendor instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Vendor persistentInstance) {
		log.debug("deleting Vendor instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Vendor findById(java.lang.Integer id) {
		log.debug("getting Vendor instance with id: " + id);
		try {
			Vendor instance = (Vendor) getHibernateTemplate().get(
					"com.saninco.ccm.model.Vendor", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Vendor instance) {
		log.debug("finding Vendor instance by example");
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
		log.debug("finding Vendor instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Vendor as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByVendorName(Object vendorName) {
		return findByProperty(VENDOR_NAME, vendorName);
	}

	public List findByVendorPrimaryContactId(Object vendorPrimaryContactId) {
		return findByProperty(VENDOR_PRIMARY_CONTACT_ID, vendorPrimaryContactId);
	}

	public List findByVendorDisputeContactId(Object vendorDisputeContactId) {
		return findByProperty(VENDOR_DISPUTE_CONTACT_ID, vendorDisputeContactId);
	}

	public List findByVendorPaymentContactId(Object vendorPaymentContactId) {
		return findByProperty(VENDOR_PAYMENT_CONTACT_ID, vendorPaymentContactId);
	}

	public List findByVendorTariffContactId(Object vendorTariffContactId) {
		return findByProperty(VENDOR_TARIFF_CONTACT_ID, vendorTariffContactId);
	}

	public List findByVendorBillingContactId(Object vendorBillingContactId) {
		return findByProperty(VENDOR_BILLING_CONTACT_ID, vendorBillingContactId);
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

	public List findBySummaryVendorName(Object summaryVendorName) {
		return findByProperty(SUMMARY_VENDOR_NAME, summaryVendorName);
	}

	public List findByVendorAcronym(Object vendorAcronym) {
		return findByProperty(VENDOR_ACRONYM, vendorAcronym);
	}

	public List findByVendorHistId(Object vendorHistId) {
		return findByProperty(VENDOR_HIST_ID, vendorHistId);
	}

	public List findByHistVendorId(Object histVendorId) {
		return findByProperty(HIST_VENDOR_ID, histVendorId);
	}

	public List findAll() {
		log.debug("finding all Vendor instances");
		try {
			String queryString = "from Vendor";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Vendor merge(Vendor detachedInstance) {
		log.debug("merging Vendor instance");
		try {
			Vendor result = (Vendor) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Vendor instance) {
		log.debug("attaching dirty Vendor instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Vendor instance) {
		log.debug("attaching clean Vendor instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static VendorDAO getFromApplicationContext(ApplicationContext ctx) {
		return (VendorDAO) ctx.getBean("VendorDAO");
	}
}