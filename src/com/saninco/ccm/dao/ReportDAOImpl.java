package com.saninco.ccm.dao;

import java.io.File;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.Rtag;
import com.saninco.ccm.model.RtagDAO;
import com.saninco.ccm.model.RtagRole;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchReportUserVO;
import com.saninco.ccm.vo.ViewReportAdminVO;
import com.saninco.ccm.vo.ViewSecurityIpVo;

/**
 * @author david.zhang
 *
 */
public class ReportDAOImpl extends HibernateDaoSupport implements IReportDAO {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final Log log = LogFactory.getLog(RtagDAO.class);

	/* (non-Javadoc)
	 * get BAN Metrics Report List from DB
	 * @see com.saninco.ccm.dao.IReportDAO#getBANMetricsList()
	 */
	@SuppressWarnings("unchecked")
	public List getBANMetricsList(final String sql) {
		
		logger.debug("get BAN Metrics Report List");
		
		return (List) this.getHibernateTemplate().execute(new HibernateCallback() {
			public Object doInHibernate(Session session) {

				try
				{
					Query query = session.createSQLQuery( sql );
					
					List list = query.list();
					
					logger.debug("get BAN Metrics Report List successful, size is " + list.size());
					return list;
				}
				catch(RuntimeException re)
				{
					logger.error("get BAN Metrics Report List failed" , re);
					throw re;
				}
				
			}
		});
		
	}
	/**
	 * By hongtao 2011-09-20
	 */
		public void deleteReport(String createdReportId,String deleteReport){
		logger.info("Enter method deleteReport.");
		final String queryString=deleteReportQueryString(createdReportId,deleteReport);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	
		logger.info("Enter method deleteReport.");
	}
	/**
	 *  By hongtao 2011-09-20
	 * @param createdReportId
	 * @param deleteReport
	 * @return
	 */
	private String deleteReportQueryString(String createdReportId,String deleteReport){
		logger.info("Enter method deleteReportQueryString.");
		StringBuffer sb = new StringBuffer("update created_report cr ");
		sb.append(" set cr.rec_active_flag='N'");
		sb.append(" where cr.id="+createdReportId);
		sb.append(" and cr.created_by="+deleteReport);
		sb.append(" and cr.rec_active_flag='Y' ;");
		return sb.toString();
	}
	
	public void deleteTagsInTagsAndRoles(ViewReportAdminVO viewReportAdminVO){
		logger.info("Enter method deleteTagsInTagsAndRoles.");
		final String queryString = deleteTagsInTagsAndRolesQueryString(viewReportAdminVO);
		HibernateTemplate template = this.getHibernateTemplate();
		
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method deleteTagsInTagsAndRoles.");
	}
	
	private String deleteTagsInTagsAndRolesQueryString(ViewReportAdminVO var){
		logger.info("Enter method deleteTagsInTagsAndRolesQueryString.");
		StringBuffer sb = new StringBuffer("update rtag_role r set ");
		sb.append(" r.rec_active_flag = 'N' ");
		sb.append(" , r.modified_timestamp = now() ");
		sb.append(" , r.modified_by = '" + SystemUtil.getCurrentUserId() + "' ");
		sb.append(" where r.rec_active_flag='Y' and r.rtag_id = " + var.getRtagId() + " ");
		logger.info("Exit method deleteTagsInTagsAndRolesQueryString.");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IReportDao#getNumberOfReportAdminTagsAndRoles(com.saninco.ccm.vo.ViewReportAdminVO)
	 */
	public long getNumberOfReportAdminTagsAndRoles(ViewReportAdminVO viewReportAdminVO) {
		logger.info("Enter method getNumberOfReportAdminTagsAndRoles.");
		final String sql = this.getReportAdminTagsAndRolesNumberQueryString(viewReportAdminVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfReportAdminTagsAndRoles.");
		return count;
	}

	private String getReportAdminTagsAndRolesNumberQueryString(ViewReportAdminVO vra) {
		logger.info("Enter method getReportAdminTagsAndRolesNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from ((rtag_role rr left join rtag r on (rr.rtag_id = r.id)) join role ro on (rr.role_id = ro.id))  ");
		sb.append("where ");
		if(vra.getFilter()!=null){
			sb.append(vra.getFilter());
			sb.append(" and ");
		}	
		sb.append("rr.rec_active_flag='Y' ");
		logger.info("Exit method getReportAdminTagsAndRolesNumberQueryString.");
		return sb.toString();
	}
	
	/**
	 * get Report Admin Tags And Roles List ### (com.saninco.ccm.vo.ViewReportAdminVO)
	 */
	public List<String> getReportAdminTagsAndRoles(ViewReportAdminVO viewReportAdminVO) {
		logger.info("Enter method getReportAdminTagsAndRoles.");
		final String sql = this.getReportAdminTagsAndRolesQueryString(viewReportAdminVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getReportAdminTagsAndRoles.");
		return list;
	}

	private String getReportAdminTagsAndRolesQueryString(ViewReportAdminVO vra) {
		logger.info("Enter method getReportAdminTagsAndRolesQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',rr.id,',tags:\"',if(r.rtag_name is null,'',r.rtag_name),");
		sb.append("'\",roles:\"',if(ro.role_name is null,'',ro.role_name),");
		sb.append("'\",accessLevel:\"',if(rr.access_level is null,'',rr.access_level),");
		sb.append("'\",email:\"',if(rr.send_email is null,'',rr.send_email),");
		sb.append("'\"}') a ");
		sb.append("from ((rtag_role rr left join rtag r on (rr.rtag_id = r.id)) join role ro on (rr.role_id = ro.id)) ");
		sb.append("where ");
		if(vra.getFilter()!=null){
			sb.append(vra.getFilter());
			sb.append(" and ");
		}	
		sb.append("rr.rec_active_flag='Y' ");
		sb.append(vra.getOrderByCause(null) + " ");
		sb.append(vra.getLimitCause() + " ");
		logger.info("Enter method getReportAdminTagsAndRolesQueryString.");
		return sb.toString();
	}
	
	public long findTagsAndRolesNumber(ViewReportAdminVO viewReportAdminVO){
		logger.info("Enter method findTagsAndRolesNumber.");
		final String sql = this.getTagsAndRolesNumberQueryString(viewReportAdminVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method findTagsAndRolesNumber.");
		return count;
	}

	private String getTagsAndRolesNumberQueryString(ViewReportAdminVO vra) {
		logger.info("Enter method getTagsAndRolesNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(" from rtag_role rr ");
		sb.append(" where rr.rec_active_flag='Y' ");
		sb.append(" and rr.rtag_id = " + vra.getRtagId() + " ");
		sb.append(" and rr.role_id = " + vra.getRoleId() + " ");
		sb.append(" and rr.access_level = " + vra.getAccessLevel() + " ");
		sb.append(" and rr.send_email = " + vra.getEmail() + " ");
		logger.info("Exit method getTagsAndRolesNumberQueryString.");
		return sb.toString();
	}
	
	public long getTagsNumberByRtagRole(ViewReportAdminVO viewReportAdminVO){
		logger.info("Enter method getTagsNumberByRtagRole.");
		final String sql = this.getTagsNumberByRtagRoleQueryString(viewReportAdminVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method getTagsNumberByRtagRole.");
		return count;
	}
	
	private String getTagsNumberByRtagRoleQueryString(ViewReportAdminVO vra) {
		logger.info("Enter method getTagsNumberByRtagRoleQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append(" from rtag_role r ");
		sb.append(" where r.rec_active_flag = 'Y' ");
		sb.append(" and r.rtag_id = " + vra.getRtagId() + " ");
		logger.info("Exit method getTagsNumberByRtagRoleQueryString.");
		return sb.toString();
	}
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IReportDao#getNumberOfReportAdminTags(com.saninco.ccm.vo.ViewReportAdminVO)
	 */
	public long getNumberOfReportAdminTags(ViewReportAdminVO viewReportAdminVO) {
		logger.info("Enter method getNumberOfReportAdminTags.");
		final String sql = this.getReportAdminTagsNumberQueryString(viewReportAdminVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfReportAdminTags.");
		return count;
	}

	private String getReportAdminTagsNumberQueryString(ViewReportAdminVO vra) {
		logger.info("Enter method getReportAdminTagsNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from rtag r ");
		sb.append("where ");
		if(vra.getFilter()!=null){
			sb.append(vra.getFilter());
			sb.append(" and ");
		}	
		sb.append("r.rec_active_flag='Y' ");
		logger.info("Exit method getReportAdminTagsNumberQueryString.");
		return sb.toString();
	}
	
	/**
	 * get Report Admin Tags List ### (com.saninco.ccm.vo.ViewReportAdminVO)
	 */
	public List<String> getReportAdminTags(ViewReportAdminVO viewReportAdminVO) {
		logger.info("Enter method getReportAdminTags.");
		final String sql = this.getReportAdminTagsQueryString(viewReportAdminVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getReportAdminTags.");
		return list;
	}
	
	public List<String> searchTeams(ViewReportAdminVO viewReportAdminVO) {
		logger.info("Enter method searchTeams.");
		final String sql = this.getSearchTeamsQueryString(viewReportAdminVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchTeams.");
		return list;
	}
	
	private String getSearchTeamsQueryString(ViewReportAdminVO vra) {
		logger.info("Enter method getSearchTeamsQueryString.");
		
		StringBuffer sb = new StringBuffer("SELECT concat('{id:', v.id,");
		sb.append(" ',fromName:\"',if(v.from_user_id IS NULL,' ',concat(u1.first_name,' ', u1.last_name)),");
		sb.append(" '\",toName:\"',if(v.to_user_id IS NULL,'',concat(u2.first_name,' ',u2.last_name)),");
		sb.append(" '\",fromId:\"',v.from_user_id,");
		sb.append(" '\",toId:\"',v.to_user_id,");
		sb.append(" '\"}')");
		sb.append(" FROM view_bucket v,user u1,user u2");
		sb.append(" WHERE v.from_user_id=u1.id");
		sb.append(" AND v.to_user_id = u2.id and");
		if(vra.getFilter()!=null){
			sb.append(vra.getFilter());
			sb.append(" and ");
		}
		sb.append(" v.rec_active_flag='Y' group by v.id ");

		sb.append(vra.getOrderByCause(null) + " ");
		sb.append(vra.getLimitCause() + " ");
		logger.info("Enter method getSearchTeamsQueryString.");
		return sb.toString();
	}

	private String getReportAdminTagsQueryString(ViewReportAdminVO vra) {
		logger.info("Enter method getReportAdminTagsQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',r.id,',name:\"',if(r.rtag_name is null,'',r.rtag_name),");
		sb.append("'\",color:\"',if(r.rtag_color is null,'',r.rtag_color),");
		sb.append("'\",ofReports:\"',count(rr.id),");
		sb.append("'\"}') a ");
		sb.append(" from  rtag r left join rtag_report rr on (rr.rtag_id=r.id and rr.rec_active_flag='Y') ");
		sb.append("where ");
		if(vra.getFilter()!=null){
			sb.append(vra.getFilter());
			sb.append(" and ");
		}	
		//modified by xinyu.Liu on 2010.7.29 for CCMUAT-94
		sb.append(" r.rec_active_flag='Y' group by r.id ");

		sb.append(vra.getOrderByCause(null) + " ");
		sb.append(vra.getLimitCause() + " ");
		logger.info("Enter method getReportAdminTagsQueryString.");
		return sb.toString();
	}
	
	/**
	 * save Rtag
	 */
	public void saveRtag(Rtag transientInstance) {
		log.debug("saving Rtag instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * find Rtag By Id
	 */
	public Rtag findRtagById(Integer id) {
		log.debug("getting Rtag instance with id: " + id);
		try {
			Rtag instance = (Rtag) getHibernateTemplate().get(
					"com.saninco.ccm.model.Rtag", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * save RtagRole
	 */
	public void saveRtagRole(RtagRole transientInstance) {
		log.debug("saving RtagRole instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * find RtagRole By Id
	 */
	public RtagRole findRtagRoleById(Integer id) {
		log.debug("getting RtagRole instance with id: " + id);
		try {
			RtagRole instance = (RtagRole) getHibernateTemplate().get(
					"com.saninco.ccm.model.RtagRole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * find Role By Id
	 */
	public Role findRoleById(Integer id) {
		log.debug("getting Role instance with id: " + id);
		try {
			Role instance = (Role) getHibernateTemplate().get(
					"com.saninco.ccm.model.Role", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	/**
	 * @author Chao.Liu
	 * @param jasperName
	 * @param excelPath
	 */
	public void saveIReportExcel(String jasperName,String excelPath){
		
		JExcelApiExporter excel = new JExcelApiExporter();
		excel.setParameter(JRExporterParameter.JASPER_PRINT, this.getJasperPrint(jasperName)); 
		
		this.printExcel(excel, excelPath);
	}
	public void saveIReportExcel(String[] jasperNames,String excelPath){
		
		List<JasperPrint> jls = new ArrayList<JasperPrint>();
		for(String jasperName : jasperNames){
			jls.add(this.getJasperPrint(jasperName));
		}
		
		JExcelApiExporter excel = new JExcelApiExporter();
		excel.setParameter(JRExporterParameter.JASPER_PRINT_LIST, jls); 
		
		this.printExcel(excel, excelPath);
	}
	private void printExcel(JExcelApiExporter excel,String excelPath){
		excel.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, excelPath); 
		try {
			excel.exportReport(); 
		} catch (JRException e) { 
			e.printStackTrace(); 
		} 
	}
	private JasperPrint getJasperPrint(String jasperName){
		JasperReport report = null; 
		JasperPrint jasperPrint = null;
		
		File file = new File(this.formatPath(jasperName)); 		
		try { 
			report = (JasperReport) JRLoader.loadObject(file); 
		} catch (JRException e) { 
			e.printStackTrace(); 
		} 
		Map<String, String> params = new HashMap<String, String>(); 
		try { 
			jasperPrint = JasperFillManager.fillReport(report, params, this.getSession().connection());  
		} catch (JRException e) { 
			e.printStackTrace(); 
		} catch (Throwable th){
			th.printStackTrace();
		}
		
		return jasperPrint;
	}
	public String formatPath(String fileName){
		String path = getClass().getClassLoader().getResource("iReportXML/"+fileName).getPath();
		while(path.indexOf("%20") > 0){
			path = path.replaceAll("%20", " ");
		}
		return path;
	}
	/**
	 * @author Chao.Liu
	 * Get IReport DownExcel Info
	 * @param runType
	 * @return
	 */
	public List<Object[]> getIReportInfos(String runType){
		String queryString = this.getIReportInfosSqlString(runType);
		List<Object[]> los = this.getHibernateTemplate().find(queryString);
		return los;
	}
	private String getIReportInfosSqlString(String runType){
		StringBuffer sb = new StringBuffer("select ");
		sb.append("r.id, ");
		sb.append("cr.id, ");
		sb.append("cr.createdReportName, ");
		sb.append("cr.filePath, ");
		sb.append("cr.fileName, ");
		sb.append("cr.createdBy, ");
		sb.append("u.email, ");
		sb.append("cr.sendEmail ");
		sb.append("from Report r , CreatedReport cr, User u ");
		sb.append("where cr.createdBy = u.id ");
		sb.append("and r.id = cr.report.id ");
		sb.append("and cr.reportStatus = 1 ");
		sb.append("and r.frequency = '"+runType+"' ");
		sb.append("and cr.recActiveFlag = 'Y' ");
		sb.append("order by cr.createdBy  ");
		
		return sb.toString();
	}

	/**
	 * Search Report Name Totail Number [Chao.Liu]
	 * @param rvo
	 * @return
	 */
	public Integer getReportNameTotailNO(SearchReportUserVO rvo){
		logger.info("Enter method getReportNameTotailNO.");
		final String sql = this.getReportNameTotailNOQueryString(rvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Object o = (Object) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list().get(0);
			}
		});
		Integer count = new Integer(o.toString());
		logger.info("Exit method getReportNameTotailNO.");
		return count;
	}
	private String getReportNameTotailNOQueryString(SearchReportUserVO rvo){
		logger.info("Enter method getReportNameTotailNOQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) from report re where re.rec_active_flag = 'Y' " + (rvo.getFilter() == null || "".equals(rvo.getFilter()) ? "" : "and " + rvo.getFilter()) + " and re.id in ");
		sb.append("(select tr.report_id from rtag_report tr where tr.rec_active_flag = 'Y' and tr.rtag_id in ");
		sb.append("(select rr.rtag_id from rtag_role rr where rr.rec_active_flag = 'Y' and rr.access_level in ('2','3','4') and rr.role_id in ");
		sb.append("(select ur.role_id from user_role ur where ur.rec_active_flag = 'Y' and ur.user_id="+SystemUtil.getCurrentUserId()+")))");
		logger.info("Enter method getReportNameTotailNOQueryString.");
		return sb.toString();
	}
	/**
	 * Search Report Name Info List [Chao.Liu]
	 * @param rvo
	 * @return
	 */
	public List searchReportNameList(SearchReportUserVO rvo){
		logger.info("Enter method searchReportNameList.");
		final String sql = this.searchReportNameListQueryString(rvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchReportNameList.");
		return list;
	}
	private String searchReportNameListQueryString(SearchReportUserVO rvo){
		logger.info("Enter method searchReportNameListQueryString.");
		StringBuffer sb = new StringBuffer("select re.id rid,re.report_name rname,toJSON(re.p_from_date is null,'N',re.p_from_date)," +
				"toJSON(re.p_to_date is null,'N',re.p_to_date)," +
				"toJSON(re.p_analyst_id is null,'N',re.p_analyst_id)," +
				"toJSON(re.p_only_user is null,'N',re.p_only_user)," +
				"toJSON(re.p_ban_id is null,'N',re.p_ban_id)," +
				"toJSON(re.p_key is null,'',re.p_key), " +
				//wenbo.zhang 20190305 start
				"toJSON(re.p_login is null,'N',re.p_login), " +
				"toJSON(re.p_effective_hour is null,'N',re.p_effective_hour), " +
				"toJSON(re.p_download_times is null,'N',re.p_download_times), " +			
				"toJSON(re.p_email is null,'N',re.p_email) " +
				//wenbo.zhang 20190305 end
				
				" from report re where re.rec_active_flag = 'Y' " + (rvo.getFilter() == null || "".equals(rvo.getFilter()) ? "" : "and " + rvo.getFilter()) + " and re.id in ");
		sb.append("(select tr.report_id from rtag_report tr where tr.rec_active_flag = 'Y' and tr.rtag_id in ");
		sb.append("(select rr.rtag_id from rtag_role rr where rr.rec_active_flag = 'Y' and rr.access_level in ('2','3','4') and rr.role_id in ");
		sb.append("(select ur.role_id from user_role ur where ur.rec_active_flag = 'Y' and ur.user_id="+SystemUtil.getCurrentUserId()+")))");
		sb.append(rvo.getOrderByCause(null));
		sb.append(rvo.getLimitCause());
		        
		logger.info("Enter method searchReportNameListQueryString.");
		return sb.toString();
	}
	 
	/**
	 * Check Name Is Repeat
	 * @param tableName
	 * @param fieldName
	 * @param name
	 * @return
	 */
	public Integer getNameIsRepeat(String tableName,String fieldName,String name){
		logger.info("Enter method getNameIsRepeat.");
		final String sql = this.getNameIsRepeatSqlString(tableName,fieldName,name);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list().size();
			}
		});
		logger.info("Exit method getNameIsRepeat.");
		return count;
	}
	private String getNameIsRepeatSqlString(String tableName,String fieldName,String name){
		logger.info("Enter method getNameIsRepeatSqlString.");
		StringBuffer sb = new StringBuffer("select * from "+tableName+" as o ");
		sb.append("where  1 = 1 ");
		sb.append("and o."+fieldName+"='"+name+"' ");
		sb.append("and o.rec_active_flag = 'Y' ");
		
		logger.info("Enter method getNameIsRepeatSqlString.");
		return sb.toString();
	}
	/**
	 * Update Created Report Status [Chao.Liu]
	 * @param crid
	 * @param stutas
	 */
	public void updateCreatedReport(Object crid,Object stutas){
		Session session = getSession();
		try {
			final String sql = "update created_report set report_status='"+stutas+"', modified_timestamp = now() where id = "+crid;
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}
	public void updatePathAndName(Object path,Object name,Object id){
		Session session = getSession();
		try {
			final String sql = "update `created_report` set file_path = '"+path+"' , file_name = '"+name+"' where id = '"+id+"'";
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
	}
	public Object getObject(Class c , Integer id){
		return this.getHibernateTemplate().get(Class.class, id);
	}
	public void saveObject(Object o){
		this.getHibernateTemplate().save(o);
	}
	
	public void updateObject(Object o){
		this.getHibernateTemplate().update(o);
	}
	/**
	 * Search Rtarg Color By Rtarg ID
	 * @param rid
	 * @return
	 */
	public List<String> SearchRtargColorByReportId(Object rid){
		logger.info("Enter method SearchRtargColorByReportId.");
		final String sql = this.SearchRtargColorByReportIdSql(rid);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method SearchRtargColorByReportId.");
		return list;
	}
	private String SearchRtargColorByReportIdSql(Object rid){
		logger.info("Enter method SearchRtargColorByReportIdSql.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("color:\"',if(r.rtag_color is null,'',r.rtag_color),'\" ");
		sb.append("}') from `rtag` r , `rtag_report` rr ");
		sb.append("where rr.report_id = " + rid.toString() + " ");
		sb.append("and rr.rtag_id = r.id ");
		sb.append("and rr.rec_active_flag = 'Y' ");
		logger.info("Enter method SearchRtargColorByReportIdSql.");
		return sb.toString();
	}
	/**
	 * 
	 * */
	public List<String> SearchViewColor(Object rid) {
		logger.info("Enter method SearchViewColor.");
		final String sql = this.SearchViewColorSql(rid);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method SearchViewColor.");
		return list;
	}
	private String SearchViewColorSql(Object rid){
		logger.info("Enter method SearchViewColorSql.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		
		sb.append("color:\"',if(r.rtag_color is null,'',r.rtag_color),'\" ");
		sb.append("}') from `rtag` r , `rtag_report` rr ");
		sb.append("where rr.report_id = " + rid.toString() + " ");
		sb.append("and rr.rtag_id = r.id ");
		sb.append("and rr.rec_active_flag = 'Y' ");

		logger.info("Enter method SearchViewColorSql.");
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * Statistics have to ReportName
	 * */
	public Integer getViewReportNameTotailNo(SearchReportUserVO rvo) {
		logger.info("Enter method getViewReportNameTotailNo.");
		final String sql = this.getViewReportNameTotailNoOQueryString(rvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Object o = (Object) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list().get(0);
			}
		});
		Integer count = new Integer(o.toString());
		logger.info("Exit method getViewReportNameTotailNo.");
		return count;
	}
	
	private String getViewReportNameTotailNoOQueryString(SearchReportUserVO rvo){
		logger.info("Enter method getReportNameTotailNOQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) from report re where re.rec_active_flag = 'Y' " + (rvo.getFilter() == null || "".equals(rvo.getFilter()) ? "" : "and " + rvo.getFilter()) + " and re.id in ");
		sb.append("(select tr.report_id from rtag_report tr where tr.rec_active_flag = 'Y' and tr.rtag_id in ");
		sb.append("(select rr.rtag_id from rtag_role rr where rr.rec_active_flag = 'Y' and rr.role_id in ");
		sb.append("(select ur.role_id from user_role ur where ur.rec_active_flag = 'Y' and ur.user_id="+SystemUtil.getCurrentUserId()+")))");
		logger.info("Enter method getReportNameTotailNOQueryString.");
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * ReportName inquires
	 * */
	public List searchViewReportName(SearchReportUserVO rvo) {
		logger.info("Enter method searchViewReportName.");
		final String sql = this.searchViewReportNameQueryString(rvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchViewReportName.");
		return list;
	}
	private String searchViewReportNameQueryString(SearchReportUserVO rvo){
		logger.info("Enter method searchViewReportNameQueryString.");
		StringBuffer sb = new StringBuffer("select re.id rid,re.report_name rname from report re where re.rec_active_flag = 'Y' " + (rvo.getFilter() == null || "".equals(rvo.getFilter()) ? "" : "and " + rvo.getFilter()) + " and re.id in ");
		sb.append("(select tr.report_id from rtag_report tr where tr.rec_active_flag = 'Y' and tr.rtag_id in ");
		sb.append("(select rr.rtag_id from rtag_role rr where rr.rec_active_flag = 'Y' and rr.role_id in ");
		sb.append("(select ur.role_id from user_role ur where ur.rec_active_flag = 'Y' and ur.user_id="+SystemUtil.getCurrentUserId()+")))");
		sb.append(rvo.getOrderByCause(null));
		sb.append(rvo.getLimitCause());
		logger.info("Enter method searchViewReportNameQueryString.");
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * ViewReportTab common queries
	 * */
	private String viewReportNameListPublicQueryString(SearchReportUserVO rvo){
		logger.info("Enter method viewReportNameListPublicQueryString.");
		StringBuffer sb = new StringBuffer();
		
		sb.append("select urore.report_id,urore.access_level from ( ");
		sb.append("select ");
		sb.append("uro.user_id, ");
		sb.append("rre.report_id, ");
		sb.append("max(access_level) access_level ");
		sb.append("from ");
		sb.append("user_role as uro, ");
		sb.append("rtag_role as rro, ");
		sb.append("rtag_report as rre, ");
		sb.append("role as ro, ");
		sb.append("rtag as rt ");
		sb.append("where uro.role_id = rro.role_id ");
		sb.append("and rro.rtag_id = rre.rtag_id ");
		sb.append("and ro.id = rro.role_id ");
		sb.append("and rt.id = rre.rtag_id ");
		sb.append("and ro.rec_active_flag = 'Y' ");
		sb.append("and rt.rec_active_flag = 'Y' ");
		sb.append("and rro.rec_active_flag = 'Y' ");
		sb.append("and rre.rec_active_flag = 'Y' ");
		sb.append("GROUP BY uro.user_id, rre.report_id ");
		sb.append(") as urore ");
		sb.append("where  urore.user_id="+rvo.getCurUid()+" and urore.access_level>=1 ");
		sb.append(") as reurore ");
		sb.append("where  1 = 1  ");
		if(rvo.getFilter()!=null){
				sb.append(" and ");
				sb.append(rvo.getFilter());		
			}	
		sb.append("and re.id=reurore.report_id ");
		sb.append("and re.rec_active_flag = 'Y' ");
		        
		logger.info("Enter method viewReportNameListPublicQueryString.");
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * Created_report query
	 * */
	public List<String> SearchViewNameReport(SearchReportUserVO rvo,String loginUser) {
		logger.info("Enter method SearchViewNameReport.");
		final String queryString = SearchViewNameReportSqlString(rvo,loginUser);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method SearchViewNameReport.");
		return list;
	}
	
	/**
	 * @author pengfei.wang
	 * searchWorkspase queryString
	 * */
	private String SearchViewNameReportSqlString(SearchReportUserVO rvo,String longUser) {
		logger.info("Enter method SearchViewNameReportSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',created_report.id,'\", ");
		sb.append("owner_flag:\"',if(created_report.created_by = "+longUser+",0,1) ,'\", ");
		sb.append("report_id:\"',created_report.report_id,'\", ");
		sb.append("created_report_name:\"',toJSON(created_report.created_report_name is null,'',created_report.created_report_name),'\", ");
		sb.append("report_format:\"',toJSON(created_report.report_format is null,'',created_report.report_format ),'\", ");
		sb.append("created_timestamp:\"',toJSON(created_report.created_timestamp is null,'',cr" +
				"eated_report.created_timestamp ),'\", ");
		sb.append("report_status:\"',toJSON(created_report.report_status is null,'',created_report.report_status ),'\", ");
		sb.append("file_path:\"',toJSON(created_report.file_path is null,'',created_report.file_path ),'\", ");
		sb.append("file_name:\"',toJSON(created_report.file_name is null,'',created_report.file_name ),'\", ");
		sb.append("user_name:\"',toJSON(first_name is null,'',first_name),' ',toJSON(last_name is null,'',last_name),'\" ");
		sb.append("}')  from created_report ,`user` ");
		sb.append("where created_report.report_id = '"+rvo.getReportId()+"' ");
		sb.append(" and (( created_report.created_by=0  or   created_report.sharing_flag =  'Y' ) or (created_report.sharing_flag ='N' and created_report.created_by="+SystemUtil.getCurrentUserId()+") ) ");
		sb.append(" and  created_report.created_by =  `user`.id ");
		sb.append(" and  `user`.rec_active_flag =  'Y' ");
		sb.append(" and created_report.rec_active_flag='Y' ");
		if(rvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(rvo.getFilter());
		}
		sb.append(" order by " + rvo.getSortField() + " " + rvo.getSortingDirection());
		sb.append(" LIMIT " + rvo.getStartIndex() + "," + rvo.getRecPerPage());
		logger.info("Exit method SearchViewNameReportSqlString.");
		return sb.toString();
	}
	
	/**
	 * @author pengfei.wang
	 * Created_report inquiry statistics
	 * */
	public Integer getViewNameReportTotailNo(SearchReportUserVO rvo) {
		logger.info("Enter method getViewNameReportTotailNo.");
		final String sql = getViewNameReportTotailNoSqlStrings(rvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});
		logger.info("Exit method getViewNameReportTotailNo.");
		return count;
	}
	

	/**
	 * @author pengfei.wang
	 * The paging quertString
	 * */
	private String getViewNameReportTotailNoSqlStrings(SearchReportUserVO rvo) {
		logger.info("Enter method getInvoiceWorkCountNO.");
		StringBuffer sb = new StringBuffer("select count(1) from created_report ,`user`  ");
		sb.append("where created_report.report_id =  '"+rvo.getReportId()+"' ");
		sb.append(" and (( created_report.created_by=0  or   created_report.sharing_flag =  'Y' ) or (created_report.sharing_flag ='N' and created_report.created_by="+SystemUtil.getCurrentUserId()+") ) ");
		sb.append(" and  created_report.created_by =  `user`.id ");
		sb.append(" and  `user`.rec_active_flag =  'Y' ");
		sb.append(" and created_report.rec_active_flag='Y' ");
		if(rvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(rvo.getFilter());
		}
		logger.info("Exit method getInvoiceWorkCountNO.");
		return sb.toString();
	}

	public java.lang.Character getUserMaxLevel(String reportId) {
		logger.info("Enter method getUserMaxLevel.");
		StringBuilder sb = new StringBuilder();
		sb.append("select max(tr.access_level) from rtag_role tr where tr.rec_active_flag = 'Y' and tr.role_id in ");
		sb.append("(select ur.role_id from user_role ur where ur.rec_active_flag = 'Y' and ur.user_id="+SystemUtil.getCurrentUserId()+") ");
		sb.append("and tr.rtag_id in (select rr.rtag_id from rtag_report rr where rr.report_id="+reportId+")");
		Session session = getSession();
		try {
			java.lang.Character r = (java.lang.Character)session.createSQLQuery(sb.toString()).uniqueResult();
			logger.info("Exit method getUserMaxLevel.");
			return r;
		} finally {
			releaseSession(session);
		}
	}
	
	public List getReportParameter(String reportId) {
		logger.info("Enter method getReportParameter.");
		final String sql = this.searchReportParameterListQueryString(reportId);		
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getReportParameter.");
		return list;
	}
	
	private String searchReportParameterListQueryString(String rvo){
		logger.info("Enter method searchReportParameterListQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',id,',report_id:',report_id,',view_name:\"',toJSON(view_name is null,'',view_name),'\",table_filed:\"',toJSON(table_filed is null,'',table_filed),'\",filed_type:\"',toJSON(filed_type is null,'',filed_type),'\",value:\"',toJSON(value is null,'',value),'\"}') "
				+" from report_parameter rp where rp.rec_active_flag = 'Y' and rp.report_id = " + rvo + " order by id");		
		logger.info("Exit method searchReportParameterListQueryString.");
		return sb.toString();
	}
	
	public long getTeamTotalPageNo(ViewReportAdminVO viewReportAdminVO) {
		logger.info("Enter method getNumberOfReportAdminTagsAndRoles.");
		final String sql = this.getTeamTotalPageNoString(viewReportAdminVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfReportAdminTagsAndRoles.");
		return count;
	}
	
	private String getTeamTotalPageNoString(ViewReportAdminVO vra) {
		logger.info("Enter method getReportAdminTagsAndRolesNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) from view_bucket v where v.rec_active_flag='Y';");
		logger.info("Exit method getReportAdminTagsAndRolesNumberQueryString.");
		return sb.toString();
	}
	
	public void delTeam(int teamId) {
		logger.info("Enter method delTeam.");

		final String queryString=delTeamString(teamId);
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	
		logger.info("Enter method delTeam.");
	}
	
	private String delTeamString(int teamId){
		logger.info("Enter method delTeamString.");
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		StringBuffer sb = new StringBuffer("update view_bucket v ");
		sb.append(" set v.rec_active_flag='N',v.modified_by="+SystemUtil.getCurrentUser().getId()+",v.modified_timestamp='"+datetime+"'");
		sb.append(" where v.id= "+teamId);
		return sb.toString();
	}
	
	public void updateTeam(int teamId, int fromId, int toId) {
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		StringBuffer sb = new StringBuffer("update view_bucket v");
		sb.append(" set v.from_user_id=" +fromId+",v.to_user_id="+toId+",v.modified_by="+SystemUtil.getCurrentUser().getId()+",v.modified_timestamp='"+datetime+"'");
		sb.append(" where v.id="+teamId);
		final String queryString = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	}
	
	public void addTeam(int fromId, int toId) {
		
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		StringBuffer sb = new StringBuffer("insert into view_bucket(from_user_id,to_user_id,created_by, created_timestamp, modified_by, modified_timestamp) values");
		sb.append("("+fromId+","+toId+","+SystemUtil.getCurrentUser().getId()+",'"+datetime+"',"+SystemUtil.getCurrentUser().getId()+",'"+datetime+"')");
		final String queryString = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	}
	
	public Integer findTeamByfromIdAndToId(int fromId, int toId) {
		
		StringBuffer sb = new StringBuffer("select count(1) from view_bucket vb where vb.from_user_id = "+fromId+" and vb.to_user_id = "+toId+" and vb.rec_active_flag='Y' ");
		logger.info("Enter method findTeamByfromIdAndToId.");
		final String sql = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method findTagsAndRolesNumber.");
		return count;
	}
	

	public Map<String, String> selectFileDownLoad(int id) {
		Session session = getSession();
		String sql = "select file_path,file_name,effective_hour, CONVERT(login_flag USING utf8) COLLATE utf8_unicode_ci login_flag,download_times from file_download where id = " + id + " and rec_active_flag = 'Y'";
		try {
			List<Map<String, String>> List = session.createSQLQuery(sql).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			Map<String, String> map = List.get(0);

			return map;
		} finally {
			releaseSession(session);
		}
	}
	
	public void savefileDownloadJournal(String loginFlag,int fileDownloadId,String ip) {
		StringBuffer sb = new StringBuffer("insert into file_download_journal(file_download_id,user_id,ip_address,download_date,rec_active_flag) values");
		sb.append("("+fileDownloadId );
		if ("Y".equals(loginFlag)) {
			sb.append(","+SystemUtil.getCurrentUser().getId());
		}else {
			sb.append(",null");
		}
		sb.append(",'"+ip+"',now(),'Y')");
		final String queryString = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	}
	public Integer findfileDownloadJournal (int id){
		StringBuffer sb = new StringBuffer("select count(1) from file_download_journal  where file_download_id = "+id+" and rec_active_flag='Y' ");
		logger.info("Enter method findfileDownloadJournal.");
		final String sql = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method findfileDownloadJournal.");
		return count;
	}
	
	public Map<String, String> selectFileDownLoadVerifyValue(int id) {
		logger.info("Enter method selectFileDownLoadVerifyValue.");
		Session session = getSession();
		String sql = "select (select md5(concat(cr.created_by,cr.file_name,f.created_timestamp, cr.id)) from  created_report cr where cr.file_download_id = "+id+" and cr.rec_active_flag = 'Y' ) token , f.effective_hour hour, IF((f.effective_hour - (TIMESTAMPDIFF(MINUTE, f.created_timestamp, now()) / 60)) >0,'Y','N') isHour,CONVERT(f.login_flag USING utf8) COLLATE utf8_unicode_ci loginFlag from file_download f where f.id = " + id +" and f.rec_active_flag = 'Y'";
		try {
			List<Map<String, String>> List = session.createSQLQuery(sql).setResultTransformer(
					Transformers.ALIAS_TO_ENTITY_MAP).list();
			Map<String, String> map = List.get(0);
			logger.info("Exit method selectFileDownLoadVerifyValue.");
			return map;
		} finally {
			releaseSession(session);
			
		}
		
	}
}
