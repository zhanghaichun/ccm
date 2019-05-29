/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Currency;

/**
 * @author Joe.Yang
 *
 */
public class CurrencyDaoImpl extends HibernateDaoSupport implements ICurrencyDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	/**
	 * 
	 */
	public CurrencyDaoImpl() {
	}

	public List<Currency> getCurrency() {
		logger.info("Enter method getCurrency");
		List<Currency> l = (List<Currency>)this.getHibernateTemplate().find("from Currency");
		logger.info("Exit method getCurrency");
		return l;
	}
	
	public Currency findById(java.lang.Integer id) {

		Currency instance = (Currency) getHibernateTemplate().get(
				"com.saninco.ccm.model.Currency", id);
		return instance;

	}

}
