package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Severity;
 
/**
 * @author xinyu.Liu
 *
 */
public class SeverityDaoImpl extends HibernateDaoSupport implements ISeverityDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	public List<Severity> getSeverity() {
		logger.info("Enter method getSeverity");
		List<Severity> list = (List<Severity>)this.getHibernateTemplate().find("from Severity order by severityName asc");
		logger.info("Exit method getSeverity");
		return list;
	}

}
