package com.saninco.ccm.model;

import java.util.List;
import java.util.Set;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

/**
 * A data access object (DAO) providing persistence and search support for
 * PaymentTerm entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.PaymentTerm
 * @author MyEclipse Persistence Tools
 */

public class PaymentTermDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PaymentTermDAO.class);
	// property constants
	public static final String PAYMENT_TERM_CODE = "paymentTermCode";
	public static final String PAYMENT_TERM_DESCRIPTION = "paymentTermDescription";

	protected void initDao() {
		// do nothing
	}

	public void save(PaymentTerm transientInstance) {
		log.debug("saving PaymentTerm instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PaymentTerm persistentInstance) {
		log.debug("deleting PaymentTerm instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PaymentTerm findById(java.lang.Integer id) {
		log.debug("getting PaymentTerm instance with id: " + id);
		try {
			PaymentTerm instance = (PaymentTerm) getHibernateTemplate().get(
					"com.saninco.ccm.model.PaymentTerm", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PaymentTerm instance) {
		log.debug("finding PaymentTerm instance by example");
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
		log.debug("finding PaymentTerm instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from PaymentTerm as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPaymentTermCode(Object paymentTermCode) {
		return findByProperty(PAYMENT_TERM_CODE, paymentTermCode);
	}

	public List findByPaymentTermDescription(Object paymentTermDescription) {
		return findByProperty(PAYMENT_TERM_DESCRIPTION, paymentTermDescription);
	}

	public List findAll() {
		log.debug("finding all PaymentTerm instances");
		try {
			String queryString = "from PaymentTerm";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PaymentTerm merge(PaymentTerm detachedInstance) {
		log.debug("merging PaymentTerm instance");
		try {
			PaymentTerm result = (PaymentTerm) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PaymentTerm instance) {
		log.debug("attaching dirty PaymentTerm instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PaymentTerm instance) {
		log.debug("attaching clean PaymentTerm instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PaymentTermDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PaymentTermDAO) ctx.getBean("PaymentTermDAO");
	}
}