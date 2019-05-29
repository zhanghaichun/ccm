package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Rtag;
import com.saninco.ccm.model.RtagReport;


/**
 * 
 * @author Chao.Liu
 *
 */
public class RtagDaoImpl extends HibernateDaoSupport implements IRtagDao {
	private final Logger logger = Logger.getLogger(this.getClass());
	/**
	 * Rtag Object List
	 * @return
	 */
	public List<Object[]> getRtag() {
		logger.info("Enter method getAccountCode");
		Session session = getSession();
		try {
			List<Object[]> list = (List<Object[]>) session.createSQLQuery("select r.id, r.rtag_name from rtag r where r.rec_active_flag='Y' order by r.rtag_name asc ").list();
			logger.info("Exit method getAccountCode");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * Get Function List Info [Chao.Liu]
	 * @return
	 */
	public List<String> getRoprtsNameList(){
		logger.info("Enter method getRoprtsNameList");
		final String sql = this.getRoprtsNameListQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return l;
		    }
		});
		logger.info("Exit method getRoprtsNameList");
		return list;
	}
	private String getRoprtsNameListQueryString(){
		logger.info("Enter method getFunctionListQueryString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("key:',id,', ");
		sb.append("value:\"',if(report_name is null,'',report_name),'\" ");
		sb.append("}') from `report` ");
		sb.append("where rec_active_flag = 'Y' ");
		logger.info("Exit method getFunctionListQueryString.");
		return sb.toString();
	}
	/**
	 * Get Rtag And Roprts Name List Info [Chao.Liu]
	 * @param rid
	 * @return
	 */
	public List<String> getRtagRoprtsNameList(Integer rid){
		logger.info("Enter method getRtagRoprtsNameList");
		final String sql = this.getRtagRoprtsNameListQueryString(rid);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	List l = session.createSQLQuery(sql).list();
		    	return l;
		    }
		});
		logger.info("Exit method getRtagRoprtsNameList");
		return list;
	}
	private String getRtagRoprtsNameListQueryString(Integer rid){
		logger.info("Enter method getRtagRoprtsNameListQueryString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("key:',r.id,', ");
		sb.append("value:\"',if(r.report_name is null,'',r.report_name),'\" ");
		sb.append("}') from `report` r , `rtag_report` rr ");
		sb.append("where rr.rtag_id = " + rid + " ");
		sb.append("and rr.report_id = r.id ");
		sb.append("and rr.rec_active_flag = 'Y' ");
		logger.info("Exit method getRtagRoprtsNameListQueryString.");
		return sb.toString();
	}
	/**
	 * Get RtagReport List Count [Chao.Liu]
	 * @param rtid
	 * @param reid
	 * @return
	 */
	public Integer getRtagAndReportCount(Integer rtid, String reid){
		logger.info("Enter method getRtagRoprtsNameListQueryString.");
		String queryString = "from RtagReport rr where rr.rtag="+rtid+" and rr.report="+reid+" and rr.recActiveFlag = 'Y'";
		List l = this.getHibernateTemplate().find(queryString);
		logger.info("Exit method getRtagRoprtsNameListQueryString.");
		return l.size();
	}
	/**
	 * Get RtagReport List Info
	 * @param rtid
	 * @param reid
	 * @return
	 */
	public List<RtagReport> getRtagReportListByRtid(Integer rtid){
		logger.info("Enter method getRtagReportListByRtidAndReid.");
		String queryString = "from RtagReport rr where rr.rtag="+rtid+" and rr.recActiveFlag = 'Y'";
		List<RtagReport> l = this.getHibernateTemplate().find(queryString);
		logger.info("Exit method getRtagReportListByRtidAndReid.");
		return l;
	}
	
	public void saveObject(Object o){
		this.getHibernateTemplate().save(o);
	}
	public Object getObject(Class c, Integer id){
		return this.getHibernateTemplate().get(c, id);
	}
	public void updateObject(Object o){
		this.getHibernateTemplate().update(o);
	}
}
