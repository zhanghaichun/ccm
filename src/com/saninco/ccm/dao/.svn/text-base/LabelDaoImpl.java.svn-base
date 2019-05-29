/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Label;

/**
 * @author xinyu.Liu
 */
public class LabelDaoImpl extends HibernateDaoSupport implements ILabelDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	public List<Label> getLabel() {
		logger.info("Enter method getLabel");
		List<Label> list = (List<Label>)this.getHibernateTemplate().find("from Label ");
		logger.info("Exit method getLabel");
		return list;
	}
	
}
