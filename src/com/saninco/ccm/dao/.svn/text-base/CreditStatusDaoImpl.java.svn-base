package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.CreditStatus;
 

/**
 * @author xinyu.Liu
 *
 */
public class CreditStatusDaoImpl extends HibernateDaoSupport implements ICreditStatusDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	public List<CreditStatus> getCreditStatus() {
		logger.info("Enter method getCreditStatus");
		List<CreditStatus> list = (List<CreditStatus>)this.getHibernateTemplate().find("from CreditStatus");
		logger.info("Exit method getCreditStatus");
		return list;
	}

}
