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
 * PaymentMethod entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.PaymentMethod
 * @author MyEclipse Persistence Tools
 */

public class PaymentMethodDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PaymentMethodDAO.class);
	// property constants
	public static final String PAYMENT_METHOD_CODE = "paymentMethodCode";
	public static final String PAYMENT_METHOD_DESCRIPTION = "paymentMethodDescription";

	protected void initDao() {
		// do nothing
	}

	public void save(PaymentMethod transientInstance) {
		log.debug("saving PaymentMethod instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PaymentMethod persistentInstance) {
		log.debug("deleting PaymentMethod instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PaymentMethod findById(java.lang.Integer id) {
		log.debug("getting PaymentMethod instance with id: " + id);
		try {
			PaymentMethod instance = (PaymentMethod) getHibernateTemplate()
					.get("com.saninco.ccm.model.PaymentMethod", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PaymentMethod instance) {
		log.debug("finding PaymentMethod instance by example");
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
		log.debug("finding PaymentMethod instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PaymentMethod as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPaymentMethodCode(Object paymentMethodCode) {
		return findByProperty(PAYMENT_METHOD_CODE, paymentMethodCode);
	}

	public List findByPaymentMethodDescription(Object paymentMethodDescription) {
		return findByProperty(PAYMENT_METHOD_DESCRIPTION,
				paymentMethodDescription);
	}

	public List findAll() {
		log.debug("finding all PaymentMethod instances");
		try {
			String queryString = "from PaymentMethod";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PaymentMethod merge(PaymentMethod detachedInstance) {
		log.debug("merging PaymentMethod instance");
		try {
			PaymentMethod result = (PaymentMethod) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PaymentMethod instance) {
		log.debug("attaching dirty PaymentMethod instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PaymentMethod instance) {
		log.debug("attaching clean PaymentMethod instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PaymentMethodDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PaymentMethodDAO) ctx.getBean("PaymentMethodDAO");
	}
}