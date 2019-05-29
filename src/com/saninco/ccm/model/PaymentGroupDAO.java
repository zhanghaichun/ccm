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
 * PaymentGroup entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.saninco.ccm.model.PaymentGroup
 * @author MyEclipse Persistence Tools
 */

public class PaymentGroupDAO extends HibernateDaoSupport {
	private static final Log log = LogFactory.getLog(PaymentGroupDAO.class);
	// property constants
	public static final String PAYMENT_GROUP_CODE = "paymentGroupCode";
	public static final String PAYMENT_GROUP_DESCRIPTION = "paymentGroupDescription";

	protected void initDao() {
		// do nothing
	}

	public void save(PaymentGroup transientInstance) {
		log.debug("saving PaymentGroup instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PaymentGroup persistentInstance) {
		log.debug("deleting PaymentGroup instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public PaymentGroup findById(java.lang.Integer id) {
		log.debug("getting PaymentGroup instance with id: " + id);
		try {
			PaymentGroup instance = (PaymentGroup) getHibernateTemplate().get(
					"com.saninco.ccm.model.PaymentGroup", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PaymentGroup instance) {
		log.debug("finding PaymentGroup instance by example");
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
		log.debug("finding PaymentGroup instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PaymentGroup as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByPaymentGroupCode(Object paymentGroupCode) {
		return findByProperty(PAYMENT_GROUP_CODE, paymentGroupCode);
	}

	public List findByPaymentGroupDescription(Object paymentGroupDescription) {
		return findByProperty(PAYMENT_GROUP_DESCRIPTION,
				paymentGroupDescription);
	}

	public List findAll() {
		log.debug("finding all PaymentGroup instances");
		try {
			String queryString = "from PaymentGroup";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PaymentGroup merge(PaymentGroup detachedInstance) {
		log.debug("merging PaymentGroup instance");
		try {
			PaymentGroup result = (PaymentGroup) getHibernateTemplate().merge(
					detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PaymentGroup instance) {
		log.debug("attaching dirty PaymentGroup instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PaymentGroup instance) {
		log.debug("attaching clean PaymentGroup instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PaymentGroupDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PaymentGroupDAO) ctx.getBean("PaymentGroupDAO");
	}
}