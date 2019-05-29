package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Dashboard;
import com.saninco.ccm.util.SystemUtil;
/**
 * 
 * @author Chao.Liu
 *
 */
public class DashboardDaoImpl extends HibernateDaoSupport implements IDashboardDao {
	/**
	 * @author Chao.Liu
	 * Get Dashboard By User Id
	 * @return
	 */
	public List<Dashboard> getDashboardByUserId(){
		String queryString = "from Dashboard d where d.userId = " + SystemUtil.getCurrentUserId() + " order by d.sort asc";
		List<Dashboard> l = this.getHibernateTemplate().find(queryString);
		
		return l;
	}

	// Created By Chao.Liu
	public void save(Object o){
		this.getHibernateTemplate().save(o);
	} 
	public Object get(Class c,Integer id){
		return this.getHibernateTemplate().get(c, id);
	}
	public void update(Object o){
		this.getHibernateTemplate().update(o);
	}
	public void delete(Object o){
		this.getHibernateTemplate().delete(o);
	}
}
