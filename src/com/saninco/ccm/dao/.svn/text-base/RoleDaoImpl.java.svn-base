/**
 * 
 */
package com.saninco.ccm.dao;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.RoleFunction;
import com.saninco.ccm.vo.SearchRoleVO;

/**
 * @author xinyu.Liu
 */
public class RoleDaoImpl extends HibernateDaoSupport implements IRoleDao {
	private final Logger logger = Logger.getLogger(this.getClass());

	public List<Object[]> getRole() {
		logger.info("Enter method getRole");
		Session session = getSession();
		try {
			List<Object[]> list = (List<Object[]>) session.createSQLQuery("select r.id,r.role_name from role r where r.rec_active_flag='Y' order by r.role_name asc  ").list();
			logger.info("Exit method getRole");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * Search Role List Totail Number [Chao.Liu]
	 * @return
	 */
	public Integer getSearchRoleTotalNO(SearchRoleVO rvo) {
		logger.info("Enter method getSearchRoleTotalNO.");
		final String sql = this.getSearchRoleTotalNOQueryString(rvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return new Integer(l.get(0).toString());
		    }
		});
		logger.info("Exit method getSearchRoleTotalNO.");
		return count;
	}
	private String getSearchRoleTotalNOQueryString(SearchRoleVO rvo){
		logger.info("Enter method getSearchRoleTotalNOQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) from `role` ");
		sb.append("where rec_active_flag = 'Y' ");
		if(rvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(rvo.getFilter());		
		}	
		logger.info("Exit method getSearchRoleTotalNOQueryString.");
		return sb.toString();
	}
	/**
	 * Search Role List Info [Chao.Liu]
	 * @return
	 */
	public List<String> getSearchRole(SearchRoleVO rvo){
		logger.info("Enter method getSearchRole");
		final String sql = this.getSearchRoleQueryString(rvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return l;
		    }
		});
		logger.info("Exit method getSearchRole");
		return list;
	}
	private String getSearchRoleQueryString(SearchRoleVO rvo){
		logger.info("Enter method getSearchRoleQueryString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:',id,', ");
		sb.append("rname:\"',toJSON(role_name is null,'',role_name),'\" ");
		sb.append("}') from `role` ");
		sb.append("where rec_active_flag = 'Y' ");
		if(rvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(rvo.getFilter());		
		}	
		sb.append(" order by " + rvo.getSortField() + " " + rvo.getSortingDirection());
		sb.append(" LIMIT " + rvo.getStartIndex() + "," + rvo.getRecPerPage());
		logger.info("Exit method getSearchRoleQueryString.");
		return sb.toString();
	}
	/**
	 * Delete One Role Info [Chao.Liu]
	 * @param rid
	 */
	public void deleteRole(Integer rid){
		logger.info("Enter method deleteRole.");
		Role r = (Role) this.getHibernateTemplate().get(Role.class, rid);
		r.setRecActiveFlag("N");
		logger.info("Exit method deleteRole.");
	}
	/**
	 * Check Name No Repeat [Chao.Liu]
	 * @param rname
	 * @return
	 */
	public Integer getRoleName(String rname){
		logger.info("Enter method deleteRole.");
		
		String queryString = "from Role r where r.roleName=? and r.recActiveFlag='Y'";
		Session session = getSession();
		try {
			Query q = session.createQuery(queryString);
			q.setString(0, rname);
			List r = q.list();
			
			logger.info("Exit method deleteRole.");
			return r.size();
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * Get Object Class [Chao.Liu]
	 * @param c
	 * @param id
	 * @return Class
	 */
	public Object getObject(Class c,Integer id){
		return this.getHibernateTemplate().get(c, id);
	}
	/**
	 * Update Object Class [Chao.Liu]
	 * @param o
	 */
	public void updateObject(Object o){
		this.getHibernateTemplate().update(o);
	}
	/**
	 * Save Object Class [Chao.Liu]
	 * @param o
	 */
	public void saveObject(Object o){
		this.getHibernateTemplate().save(o);
	}
	/**
	 * Get Function List Info [Chao.Liu]
	 * @return
	 */
	public List getFunctionList(){
		logger.info("Enter method getFunctionList");
		final String sql = this.getFunctionListQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return l;
		    }
		});
		logger.info("Exit method getFunctionList");
		return list;
	}
	private String getFunctionListQueryString(){
		logger.info("Enter method getFunctionListQueryString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("key:',id,', ");
		sb.append("value:\"',toJSON(function_name is null,'',function_name),'\" ");
		sb.append("}') from `function` ");
		sb.append("where function_active_flag = 'Y' ");
		sb.append("GROUP BY function_name ");
		logger.info("Exit method getFunctionListQueryString.");
		return sb.toString();
	}
	/**
	 * Get Role And Function List Info [Chao.Liu]
	 * @param rid
	 * @return
	 */
	public List getRoleFunctionList(Integer rid){
		logger.info("Enter method getRoleFunctionList");
		final String sql = this.getRoleFunctionListQueryString(rid);
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return l;
		    }
		});
		logger.info("Exit method getRoleFunctionList");
		return list;
	}
	private String getRoleFunctionListQueryString(Integer rid){
		logger.info("Enter method getRoleFunctionListQueryString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("key:',f.id,', ");
		sb.append("value:\"',toJSON(f.function_name is null,'',f.function_name),'\" ");
		sb.append("}') from `role_function` rf,`function` f ");
		sb.append("where rf.role_id = " + rid + " ");
		sb.append("and rf.function_id = f.id ");
		sb.append("and rf.rec_active_flag = 'Y' ");
		sb.append("GROUP BY f.function_name ");
		logger.info("Exit method getRoleFunctionListQueryString.");
		return sb.toString();
	}
	/**
	 * Get RoleFunction By Role Id And Function Id [Chao.Liu]
	 * @param rid
	 * @param fid
	 * @return RoleFunction
	 */
	public RoleFunction getRoleFunByRidAndFid(Integer rid,String fid){
		logger.info("Enter method getRoleFunByRidAndFid.");
		String hql = "from RoleFunction rf where rf.role.id="+rid+" and rf.function.id="+fid+" and rf.recActiveFlag='Y'";
		RoleFunction rf = (RoleFunction) this.getHibernateTemplate().find(hql).get(0);
		logger.info("Exit method getRoleFunByRidAndFid.");
		return rf;
	}
	/**
	 * Delete Role The EX expanding[KuoZhan] Method [Chao.Liu]
	 * @param rid
	 * @return
	 */
	public List<RoleFunction> getRoleFunByRid(Integer rid){
		logger.info("Enter method getRoleFunByRidAndFid.");
		String hql = "from RoleFunction rf where rf.role.id="+rid+" and rf.recActiveFlag='Y'";
		List<RoleFunction> l = (List<RoleFunction>) this.getHibernateTemplate().find(hql);
		logger.info("Exit method getRoleFunByRidAndFid.");
		return l;
	}
	
}
