/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.PaymentStatus;

/**
 * @author Joe.Yang
 *
 */
public class PaymentStatusDaoImpl extends HibernateDaoSupport implements
		IPaymentStatusDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 */
	public PaymentStatusDaoImpl() {
	}

	public List<PaymentStatus> getPaymentStatus() {
		logger.info("Enter method getPaymentStatus");
		List<PaymentStatus> l = (List<PaymentStatus>)this.getHibernateTemplate().find("from PaymentStatus order by paymentStatusName asc ");
		logger.info("Exit method getPaymentStatus");
		return l;
	}

}
