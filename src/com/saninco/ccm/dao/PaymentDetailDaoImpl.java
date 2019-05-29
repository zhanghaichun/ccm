/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.LockMode;
import org.hibernate.FlushMode;
import org.springframework.context.ApplicationContext;
import org.springframework.orm.hibernate3.HibernateAccessor;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.saninco.ccm.model.PaymentDetailDAO;
import com.saninco.ccm.model.PaymentDetail;

/**
 * @author Qiao
 * 
 */
public class PaymentDetailDaoImpl extends HibernateDaoSupport implements
		IPaymentDetailDao {
	private static final Log log = LogFactory.getLog(PaymentDetailDAO.class);
	// property constants
	public static final String AMOUNT = "amount";
	public static final String LINE_TYPE_LOOKUP_CODE = "lineTypeLookupCode";
	public static final String LINE_NUMBER = "lineNumber";
	public static final String DESCRIPTION = "description";
	public static final String CREATED_BY = "createdBy";
	public static final String MODIFIED_BY = "modifiedBy";
	public static final String REC_ACTIVE_FLAG = "recActiveFlag";

	protected void initDao() {
		// do nothing
	}

	public void save(PaymentDetail transientInstance) {
		log.debug("saving PaymentDetail instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(PaymentDetail persistentInstance) {
		log.debug("deleting PaymentDetail instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	@Transactional(readOnly = false)
	public void update(PaymentDetail persistentInstance) {
		log.debug("updating PaymentDetail instance");
		try {
			this.getHibernateTemplate().setFlushMode(HibernateAccessor.FLUSH_AUTO);
			this.getHibernateTemplate().update(persistentInstance);
			log.debug("update successful");
		} catch (RuntimeException re) {
			log.error("update failed", re);
			throw re;
		}
	}

	public PaymentDetail findById(java.lang.Integer id) {
		log.debug("getting PaymentDetail instance with id: " + id);
		try {
			PaymentDetail instance = (PaymentDetail) getHibernateTemplate()
					.get("com.saninco.ccm.model.PaymentDetail", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByExample(PaymentDetail instance) {
		log.debug("finding PaymentDetail instance by example");
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
		log.debug("finding PaymentDetail instance with property: "
				+ propertyName + ", value: " + value);
		try {
			String queryString = "from PaymentDetail as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	public List<PaymentDetail> findByPaymentIdAndLine(String paymentId,
			String lineOfBusiness) {
		try {
			String queryString = "from PaymentDetail as model where model.payment.id="+Integer.parseInt(paymentId)+" and model.recActiveFlag='Y' and model.lineNumber=?";
			return (List<PaymentDetail>)getHibernateTemplate().find(queryString, Integer.parseInt(lineOfBusiness));
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByAmount(Object amount) {
		return findByProperty(AMOUNT, amount);
	}

	public List findByLineTypeLookupCode(Object lineTypeLookupCode) {
		return findByProperty(LINE_TYPE_LOOKUP_CODE, lineTypeLookupCode);
	}

	public List findByLineNumber(Object lineNumber) {
		return findByProperty(LINE_NUMBER, lineNumber);
	}

	public List findByDescription(Object description) {
		return findByProperty(DESCRIPTION, description);
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
		log.debug("finding all PaymentDetail instances");
		try {
			String queryString = "from PaymentDetail";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public PaymentDetail merge(PaymentDetail detachedInstance) {
		log.debug("merging PaymentDetail instance");
		try {
			PaymentDetail result = (PaymentDetail) getHibernateTemplate()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(PaymentDetail instance) {
		log.debug("attaching dirty PaymentDetail instance");
		try {
			getHibernateTemplate().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(PaymentDetail instance) {
		log.debug("attaching clean PaymentDetail instance");
		try {
			getHibernateTemplate().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public static PaymentDetailDAO getFromApplicationContext(
			ApplicationContext ctx) {
		return (PaymentDetailDAO) ctx.getBean("PaymentDetailDAO");
	}

	

}
