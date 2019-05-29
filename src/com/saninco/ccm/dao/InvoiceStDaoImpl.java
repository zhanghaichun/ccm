/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.BarCode;
import com.saninco.ccm.model.InvoiceSt;
import com.saninco.ccm.model.InvoiceStDAO;
import com.saninco.ccm.model.Role;

/**
 * @author Jian.Dong
 */
public class InvoiceStDaoImpl extends HibernateDaoSupport implements IInvoiceStDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final Log log = LogFactory.getLog(InvoiceStDAO.class);
	
	public List<InvoiceSt> getInvoiceSt(){
		logger.info("Enter method getInvoiceSt");
		List<InvoiceSt> list = (List<InvoiceSt>)this.getHibernateTemplate().find("from InvoiceSt i where i.recActiveFlag='Y' ");
		logger.info("Exit method getInvoiceSt");
		return list;
	}
	
	public void save(BarCode bc) {
		logger.info("Enter method save.");
		getHibernateTemplate().save(bc);
		logger.info("Exit method save.");
	}
	
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding InvoiceSt instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from InvoiceSt as model where model."
					+ propertyName + "= ?";
			return getHibernateTemplate().find(queryString, value);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
	
	
}
