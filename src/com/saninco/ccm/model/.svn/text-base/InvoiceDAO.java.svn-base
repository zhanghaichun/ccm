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
 * Invoice entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.Invoice
 * @author MyEclipse Persistence Tools
 */

public class InvoiceDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(InvoiceDAO.class);
	// property constants
	public static final String INVOICE_NUMBER = "invoiceNumber";
	public static final String INVOICE_PREVIOUS_BALANCE = "invoicePreviousBalance";
	public static final String INVOICE_PREVIOUS_PAYMENT = "invoicePreviousPayment";
	public static final String INVOICE_BALANCE_FORWARD = "invoiceBalanceForward";
	public static final String INVOICE_ADJUSTMENT = "invoiceAdjustment";
	public static final String INVOICE_LATE_PAYMENT_CHARGE = "invoiceLatePaymentCharge";
	public static final String INVOICE_CREDIT = "invoiceCredit";
	public static final String INVOICE_CURRENT_DUE = "invoiceCurrentDue";
	public static final String INVOICE_MIN_DUE = "invoiceMinDue";
	public static final String INVOICE_TOTAL_DUE = "invoiceTotalDue";
	public static final String FLAG_WORKSPACE = "flagWorkspace";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";
	public static final String LAST_TOTAL_DUE = "lastTotalDue";
	public static final String LAST_PAYMENT = "lastPayment";
	public static final String LAST_DISPUTE = "lastDispute";
	public static final String INVOICE_MRC = "invoiceMrc";
	public static final String INVOICE_OCC = "invoiceOcc";
	public static final String INVOICE_USAGE = "invoiceUsage";
	public static final String INVOICE_TAX = "invoiceTax";
	public static final String INVOICE_SURCHARGE = "invoiceSurcharge";
	public static final String INVOICE_REGULATION_FEE = "invoiceRegulationFee";
	public static final String ASSIGNED_ANALYST_ID = "assignedAnalystId";
	public static final String PAYMENT_AMOUNT = "paymentAmount";
	public static final String DISPUTE_AMOUNT = "disputeAmount";
	public static final String MISC_ADJUSTMENT_AMOUNT = "miscAdjustmentAmount";
	public static final String BAR_CODE = "barCode";
	public static final String HIST_INVOICE_ID = "histInvoiceId";
	public static final String HIST_BAN_ID = "histBanId";
	public static final String HIST_VENDOR_ID = "histVendorId";
	public static final String NOTES = "notes";

	protected void initDao() {
		// do nothing
	}

	public void save(Invoice transientInstance) {
		log.debug("saving Invoice instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Invoice persistentInstance) {
		log.debug("deleting Invoice instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Invoice findById(java.lang.Integer id) {
		log.debug("getting Invoice instance with id: " + id);
		try {
			Invoice instance = (Invoice) getHibernateTemplate().get(
					"com.saninco.ccm.model.Invoice", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Invoice instance) {
		log.debug("finding Invoice instance by example");
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
		log.debug("finding Invoice instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from Invoice as model where model."
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

	public List findByInvoiceAdjustment(Object invoiceAdjustment) {
		return findByProperty(INVOICE_ADJUSTMENT, invoiceAdjustment);
	}

	public List findByInvoiceLatePaymentCharge(Object invoiceLatePaymentCharge) {
		return findByProperty(INVOICE_LATE_PAYMENT_CHARGE,
				invoiceLatePaymentCharge);
	}

	public List findByInvoiceCredit(Object invoiceCredit) {
		return findByProperty(INVOICE_CREDIT, invoiceCredit);
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

	public List findByLastTotalDue(Object lastTotalDue) {
		return findByProperty(LAST_TOTAL_DUE, lastTotalDue);
	}

	public List findByLastPayment(Object lastPayment) {
		return findByProperty(LAST_PAYMENT, lastPayment);
	}

	public List findByLastDispute(Object lastDispute) {
		return findByProperty(LAST_DISPUTE, lastDispute);
	}

	public List findByInvoiceMrc(Object invoiceMrc) {
		return findByProperty(INVOICE_MRC, invoiceMrc);
	}

	public List findByInvoiceOcc(Object invoiceOcc) {
		return findByProperty(INVOICE_OCC, invoiceOcc);
	}

	public List findByInvoiceUsage(Object invoiceUsage) {
		return findByProperty(INVOICE_USAGE, invoiceUsage);
	}

	public List findByInvoiceTax(Object invoiceTax) {
		return findByProperty(INVOICE_TAX, invoiceTax);
	}

	public List findByInvoiceSurcharge(Object invoiceSurcharge) {
		return findByProperty(INVOICE_SURCHARGE, invoiceSurcharge);
	}

	public List findByInvoiceRegulationFee(Object invoiceRegulationFee) {
		return findByProperty(INVOICE_REGULATION_FEE, invoiceRegulationFee);
	}

	public List findByAssignedAnalystId(Object assignedAnalystId) {
		return findByProperty(ASSIGNED_ANALYST_ID, assignedAnalystId);
	}

	public List findByPaymentAmount(Object paymentAmount) {
		return findByProperty(PAYMENT_AMOUNT, paymentAmount);
	}

	public List findByDisputeAmount(Object disputeAmount) {
		return findByProperty(DISPUTE_AMOUNT, disputeAmount);
	}

	public List findByMiscAdjustmentAmount(Object miscAdjustmentAmount) {
		return findByProperty(MISC_ADJUSTMENT_AMOUNT, miscAdjustmentAmount);
	}

	public List findByBarCode(Object barCode) {
		return findByProperty(BAR_CODE, barCode);
	}

	public List findByHistInvoiceId(Object histInvoiceId) {
		return findByProperty(HIST_INVOICE_ID, histInvoiceId);
	}

	public List findByHistBanId(Object histBanId) {
		return findByProperty(HIST_BAN_ID, histBanId);
	}

	public List findByHistVendorId(Object histVendorId) {
		return findByProperty(HIST_VENDOR_ID, histVendorId);
	}

	public List findByNotes(Object notes) {
		return findByProperty(NOTES, notes);
	}

	public List findAll() {
		log.debug("finding all Invoice instances");
		try {
			String queryString = "from Invoice";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public Invoice merge(Invoice detachedInstance) {
		log.debug("merging Invoice instance");
		try {
			Invoice result = (Invoice) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(Invoice instance) {
		log.debug("attaching dirty Invoice instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Invoice instance) {
		log.debug("attaching clean Invoice instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static InvoiceDAO getFromApplicationContext(ApplicationContext ctx) {
		return (InvoiceDAO) ctx.getBean("InvoiceDAO");
	}
}