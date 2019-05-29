/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Function;

/**
 * @author xinyu.Liu
 */
public class FunctionDaoImpl extends HibernateDaoSupport implements IFunctionDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	public List<Function> getFunction() {
		logger.info("Enter method getFunction");
		List<Function> list = (List<Function>)this.getHibernateTemplate().find("from Function f where f.functionActiveFlag='Y' order by f.functionName asc ");
		logger.info("Exit method getFunction");
		return list;
	}
	
}
