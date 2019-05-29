/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.InvoiceStatus;

/**
 * @author Joe.Yang
 *
 */
public class InvoiceStatusDaoImpl extends HibernateDaoSupport implements
		IInvoiceStatusDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 */
	public InvoiceStatusDaoImpl() {
	}

	public List<InvoiceStatus> getInvoiceStatus() {
		logger.info("Enter method getInvoiceStatus");
		List<InvoiceStatus> l = (List<InvoiceStatus>)this.getHibernateTemplate().find("from InvoiceStatus order by invoiceStatusName asc ");
		logger.info("Exit method getInvoiceStatus");
		return l;
	}

	public InvoiceStatus find(Integer invoiceStatusId) {
		return (InvoiceStatus) getHibernateTemplate().get(InvoiceStatus.class, invoiceStatusId);
	}

	public InvoiceStatus load(Integer newStatusId) {
		return (InvoiceStatus) getHibernateTemplate().load(InvoiceStatus.class, newStatusId);
	}

}
