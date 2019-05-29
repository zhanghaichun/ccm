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
 * A data access object (DAO) providing persistence and search support for Ban
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.saninco.ccm.model.Ban
 * @author MyEclipse Persistence Tools
 */

public class BanDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(BanDAO.class);
	// property constants
	public static final String ACCOUNT_NUMBER = "accountNumber";
	public static final String ACNA = "acna";
	public static final String CCNA = "ccna";
	public static final String CIC = "cic";
	public static final String ICOID = "icoid";
	public static final String ICSC = "icsc";
	public static final String OCC = "occ";
	public static final String LATA = "lata";
	public static final String STATE_LEVEL_CC = "stateLevelCc";
	public static final String PRODUCT_TYPE = "productType";
	public static final String ACCOUNT_STRUCTURE_IND = "accountStructureInd";
	public static final String BSA_IND = "bsaInd";
	public static final String AP_SUPPLIER_NUMBER = "apSupplierNumber";
	public static final String AP_SUPPLIER_NAME = "apSupplierName";
	public static final String AP_SUPPLIER_SITE = "apSupplierSite";
	public static final String AP_CUSTOMER_NAME = "apCustomerName";
	public static final String BAN_PRIMIARY_CONTACT_ID = "banPrimiaryContactId";
	public static final String BAN_PAYMENT_CONTACT_ID = "banPaymentContactId";
	public static final String BAN_DISPUTE_CONTACT_ID = "banDisputeContactId";
	public static final String BAN_TARIFF_CONTACT_ID = "banTariffContactId";
	public static final String BAN_BILLING_CONTACT_ID = "banBillingContactId";
	public static final String USAGE_BACKBILL_MONTHS = "usageBackbillMonths";
	public static final String BILL_DAY = "billDay";
	public static final String INVOICE_FREQUENCY = "invoiceFrequency";
	public static final String BILLING_CENTER = "billingCenter";
	public static final String LINE_OF_BUSINESS = "lineOfBusiness";
	public static final String NOTES = "notes";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String BAN_HIST_ID = "banHistId";
	public static final String HIST_VENDOR_ID = "histVendorId";
	public static final String HIST_BAN_ID = "histBanId";

	protected void initDao() {
		// do nothing
	}

	public void save(Ban transientInstance) {
		log.debug("saving Ban instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Ban persistentInstance) {
		log.debug("deleting Ban instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Ban findById(java.lang.Integer id) {
		log.debug("getting Ban instance with id: " + id);
		try {
			Ban instance = (Ban) getHibernateTemplate().get(
					"com.saninco.ccm.model.Ban", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Ban instance) {
		log.debug("finding Ban instance by example");
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
		log.debug("finding Ban instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Ban as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAccountNumber(Object accountNumber) {
		return findByProperty(ACCOUNT_NUMBER, accountNumber);
	}

	public List findByAcna(Object acna) {
		return findByProperty(ACNA, acna);
	}

	public List findByCcna(Object ccna) {
		return findByProperty(CCNA, ccna);
	}

	public List findByCic(Object cic) {
		return findByProperty(CIC, cic);
	}

	public List findByIcoid(Object icoid) {
		return findByProperty(ICOID, icoid);
	}

	public List findByIcsc(Object icsc) {
		return findByProperty(ICSC, icsc);
	}

	public List findByOcc(Object occ) {
		return findByProperty(OCC, occ);
	}

	public List findByLata(Object lata) {
		return findByProperty(LATA, lata);
	}

	public List findByStateLevelCc(Object stateLevelCc) {
		return findByProperty(STATE_LEVEL_CC, stateLevelCc);
	}

	public List findByProductType(Object productType) {
		return findByProperty(PRODUCT_TYPE, productType);
	}

	public List findByAccountStructureInd(Object accountStructureInd) {
		return findByProperty(ACCOUNT_STRUCTURE_IND, accountStructureInd);
	}

	public List findByBsaInd(Object bsaInd) {
		return findByProperty(BSA_IND, bsaInd);
	}

	public List findByApSupplierNumber(Object apSupplierNumber) {
		return findByProperty(AP_SUPPLIER_NUMBER, apSupplierNumber);
	}

	public List findByApSupplierName(Object apSupplierName) {
		return findByProperty(AP_SUPPLIER_NAME, apSupplierName);
	}

	public List findByApSupplierSite(Object apSupplierSite) {
		return findByProperty(AP_SUPPLIER_SITE, apSupplierSite);
	}

	public List findByApCustomerName(Object apCustomerName) {
		return findByProperty(AP_CUSTOMER_NAME, apCustomerName);
	}

	public List findByBanPrimiaryContactId(Object banPrimiaryContactId) {
		return findByProperty(BAN_PRIMIARY_CONTACT_ID, banPrimiaryContactId);
	}

	public List findByBanPaymentContactId(Object banPaymentContactId) {
		return findByProperty(BAN_PAYMENT_CONTACT_ID, banPaymentContactId);
	}

	public List findByBanDisputeContactId(Object banDisputeContactId) {
		return findByProperty(BAN_DISPUTE_CONTACT_ID, banDisputeContactId);
	}

	public List findByBanTariffContactId(Object banTariffContactId) {
		return findByProperty(BAN_TARIFF_CONTACT_ID, banTariffContactId);
	}

	public List findByBanBillingContactId(Object banBillingContactId) {
		return findByProperty(BAN_BILLING_CONTACT_ID, banBillingContactId);
	}

	public List findByUsageBackbillMonths(Object usageBackbillMonths) {
		return findByProperty(USAGE_BACKBILL_MONTHS, usageBackbillMonths);
	}

	public List findByBillDay(Object billDay) {
		return findByProperty(BILL_DAY, billDay);
	}

	public List findByInvoiceFrequency(Object invoiceFrequency) {
		return findByProperty(INVOICE_FREQUENCY, invoiceFrequency);
	}

	public List findByBillingCenter(Object billingCenter) {
		return findByProperty(BILLING_CENTER, billingCenter);
	}

	public List findByLineOfBusiness(Object lineOfBusiness) {
		return findByProperty(LINE_OF_BUSINESS, lineOfBusiness);
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

	public List findByBanHistId(Object banHistId) {
		return findByProperty(BAN_HIST_ID, banHistId);
	}

	public List findByHistVendorId(Object histVendorId) {
		return findByProperty(HIST_VENDOR_ID, histVendorId);
	}

	public List findByHistBanId(Object histBanId) {
		return findByProperty(HIST_BAN_ID, histBanId);
	}

	public List findAll() {
		log.debug("finding all Ban instances");
		try {
			String queryString = "from Ban";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Ban merge(Ban detachedInstance) {
		log.debug("merging Ban instance");
		try {
			Ban result = (Ban) getHibernateTemplate().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Ban instance) {
		log.debug("attaching dirty Ban instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Ban instance) {
		log.debug("attaching clean Ban instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static BanDAO getFromApplicationContext(ApplicationContext ctx) {
		return (BanDAO) ctx.getBean("BanDAO");
	}
}