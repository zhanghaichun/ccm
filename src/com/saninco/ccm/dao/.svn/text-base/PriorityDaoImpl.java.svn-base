package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Priority;
 
/**
 * @author xinyu.Liu
 *
 */
public class PriorityDaoImpl extends HibernateDaoSupport implements IPriorityDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	public List<Priority> getPriority() {
		logger.info("Enter method getPriority");
		List<Priority> list = (List<Priority>)this.getHibernateTemplate().find("from Priority order by priorityName asc ");
		logger.info("Exit method getPriority");
		return list;
	}

}
