/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.saninco.ccm.model.Remittance;
import com.saninco.ccm.model.RemittanceDAO;

/**
 * @author Qiao
 *
 */
public class RemittanceDaoImpl extends HibernateDaoSupport implements IRemittance {

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
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(Remittance persistentInstance) {
		log.debug("deleting Remittance instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	@Transactional(readOnly = false)
	public void update(Remittance persistentInstance){
		log.debug("updating Remittance instance");
		try {
			this.getHibernateTemplate().setFlushMode(HibernateAccessor.FLUSH_AUTO);
			this.getHibernateTemplate().update(persistentInstance);
			log.debug("update successful");
		}catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public Remittance findById(java.lang.Integer id) {
		log.debug("getting Remittance instance with id: " + id);
		try {
			Remittance instance = (Remittance) getHibernateTemplate().get(
					"com.saninco.ccm.model.Remittance", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(Remittance instance) {
		log.debug("finding Remittance instance by example");
		Session session = getSession();
		try {
			List results = session.createCriteria(
					"com.saninco.ccm.model.Remittance").add(
					Example.create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Remittance instance with property: " + propertyName
				+ ", value: " + value);
		Session session = getSession();
		try {
			String queryString = "from Remittance as model where model."
					+ propertyName + "= ?";
			Query queryObject = session.createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		} finally {
			releaseSession(session);
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
		Session session = getSession();
		try {
			String queryString = "from Remittance";
			Query queryObject = session.createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		} finally {
			releaseSession(session);
		}
	}

	public Remittance merge(Remittance detachedInstance) {
		log.debug("merging Remittance instance");
		try {
			Remittance result = (Remittance) getHibernateTemplate().merge(
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
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Remittance instance) {
		log.debug("attaching clean Remittance instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
	
	public static RemittanceDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (RemittanceDAO) ctx.getBean("RemittanceDAO");
	}

}
