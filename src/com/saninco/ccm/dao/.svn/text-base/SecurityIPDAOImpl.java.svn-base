package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.RtagDAO;
import com.saninco.ccm.model.SecurityIp;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.ViewSecurityIpVo;

public class SecurityIPDAOImpl extends HibernateDaoSupport implements ISecurityIPDAO {
	private final Logger logger = Logger.getLogger(this.getClass());
	private static final Log log = LogFactory.getLog(RtagDAO.class);
	
	public long getSeacurityTotalPageNo(ViewSecurityIpVo viewSecurityIpVo) {
		logger.info("Enter method getSeacurityTotalPageNoDao.");
		final String sql = this.getSeacurityTotalPageNoString(viewSecurityIpVo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method getSeacurityTotalPageNoDao.");
		return count;
	}
	
	private String getSeacurityTotalPageNoString(ViewSecurityIpVo vra) {
		logger.info("Enter method getSeacurityTotalPageNoDaoString.");
		StringBuffer sb = new StringBuffer("select count(1) from (security_ip si left join user u1 on si.user_id = u1.id) left join user u2 on si.created_by = u2.id where si.rec_active_flag='Y'");
		if(vra.getFilter()!=null){
			sb.append(" and  ");
			sb.append(vra.getFilter());
		}	
		logger.info("Exit method getSeacurityTotalPageNoDaoString.");
		return sb.toString();
	}
	
	public List<String> searchSecurityIp(ViewSecurityIpVo viewSecurityIpVo) {
		logger.info("Enter method searchSecurityIp.Dao");
		final String sql = this.searchSecurityIpString(viewSecurityIpVo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method searchSecurityIp.Dao");
		return list;
	}
	
	private String searchSecurityIpString(ViewSecurityIpVo vra) {
		logger.info("Enter method searchSecurityIpString.");
		
		StringBuffer sb = new StringBuffer("SELECT concat('{id:', si.id,");
		sb.append(" ',ip:\"',concat(si.ip_a,if((si.ip_b = '' or si.ip_b is null),'',concat('~',si.ip_b))),");
		sb.append(" '\",ipa:\"',ifnull(si.ip_a,''),");
		sb.append(" '\",ipb:\"',ifnull(si.ip_b,''),");
		sb.append(" '\",iprangeflag:\"',si.ip_range_flag,");
		sb.append(" '\",username:\"',if(si.user_id IS NULL,' ',concat(u1.first_name, ' ', u1.last_name)),");
		sb.append(" '\",startdate:\"',if(si.start_date IS NULL,' ',si.start_date),");
		sb.append(" '\",enddate:\"',if(si.end_date IS NUll,' ',si.end_date),");
		sb.append(" '\",modified_timestamp:\"',if(si.modified_timestamp IS NUll,' ',si.modified_timestamp),");
		sb.append(" '\",modified_by:\"',if(si.modified_by IS NULL,' ',concat(u2.first_name, ' ', u2.last_name)),");
		sb.append(" '\",activeFlag:\"',if((si.active_flag = '' OR si.active_flag is null),'N',si.active_flag),");
		sb.append(" '\"}')");
		sb.append(" FROM (security_ip si left join user u1 on si.user_id = u1.id) left join user u2 on si.modified_by = u2.id ");
		sb.append(" WHERE si.rec_active_flag='Y' ");
		if(vra.getFilter()!=null){
			sb.append(" and  ");
			sb.append(vra.getFilter());
		}	
		sb.append(" group by si.id ");
		sb.append(vra.getOrderByCause(null) + " ");
		sb.append(vra.getLimitCause() + " ");
		logger.info("Enter method searchSecurityIpString.");
		return sb.toString();
	}
	
	public void updateSecurity(ViewSecurityIpVo viewSecurityIpVo) {
		String aString=null;
		if("".equals(viewSecurityIpVo.getSecurityStartDate())){
			aString=null;
		}else {
			aString="'"+viewSecurityIpVo.getSecurityStartDate()+"'";
		}
		String bString=null;
		if("".equals(viewSecurityIpVo.getSecurityEndDate())){
			bString=null;
		}else {
			bString="'"+viewSecurityIpVo.getSecurityEndDate()+"'";
		}
		Integer c=null;
		if("all".equals(viewSecurityIpVo.getSecurityUserId())){
			c=null;
		}else {
			c=Integer.parseInt(viewSecurityIpVo.getSecurityUserId());
		}
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		StringBuffer sb = new StringBuffer("update security_ip si");
		sb.append(" set ip_a='" +formatIp(viewSecurityIpVo.getSecurityIpA())+"',ip_b='"+formatIp(viewSecurityIpVo.getSecurityIpB())+"',si.user_id="+c+",si.user_name="+(c == null?c:"(select u.user_name from user u where u.id = "+c+")")+",si.start_date="+aString+",si.end_date="+bString+",si.modified_by="+SystemUtil.getCurrentUser().getId()+",si.modified_timestamp='"+datetime+"',si.rec_active_flag='Y',si.active_flag='"+viewSecurityIpVo.getActiveFlag()+"'");
		sb.append(" where si.id='"+viewSecurityIpVo.getSecurityId()+"'");
		
//		StringBuffer sb = new StringBuffer("update security_ip si");
//		sb.append(" set user_id="+c+", ip='" +securityIp+"',start_date="+aString+",end_date="+bString+",modified_by="+SystemUtil.getCurrentUser().getId()+",modified_timestamp='"+datetime+"',rec_active_flag = 'Y'");
//		sb.append(" where id="+securityId);
		final String queryString = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	}
	
	public void reactiveSecurity(ViewSecurityIpVo viewSecurityIpVo) {
		logger.info("Enter method reactiveSecurity.");
		StringBuffer sb = new StringBuffer("update security_ip si set si.rec_active_flag = 'Y' where si.id='"+viewSecurityIpVo.getSecurityId()+"'");
		final String queryString = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		logger.info("Enter method reactiveSecurity.");
	}
	
	public void delSecurityIp(ViewSecurityIpVo viewSecurityIpVo) {
		logger.info("Enter method delSecurityIp.");

		final String queryString=delSecurityIpString(Integer.parseInt(viewSecurityIpVo.getSecurityId()));
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	
		logger.info("Enter method delSecurityIp.");
	}
	
	private String delSecurityIpString(int securityId){
		logger.info("Enter method delSecurityIpString.");
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		StringBuffer sb = new StringBuffer("update security_ip ");
		sb.append(" set rec_active_flag='N',modified_by="+SystemUtil.getCurrentUser().getId()+",modified_timestamp='"+datetime+"'");
		sb.append(" where id= "+securityId);
		return sb.toString();
	}
	
	public void addSecurityIp(ViewSecurityIpVo viewSecurityIpVo) {
		String aString=null;
		if("".equals(viewSecurityIpVo.getSecurityStartDate())){
			aString=null;
		}else {
			aString="'"+viewSecurityIpVo.getSecurityStartDate()+"'";
		}
		String bString=null;
		if("".equals(viewSecurityIpVo.getSecurityEndDate())){
			bString=null;
		}else {
			bString="'"+viewSecurityIpVo.getSecurityEndDate()+"'";
		}
		Integer c=null;
		if("all".equals(viewSecurityIpVo.getSecurityUserId())){
			c=null;
		}else {
			c=Integer.parseInt(viewSecurityIpVo.getSecurityUserId());
		}
		if("N".equals(viewSecurityIpVo.getIpRangeFlag())){
			viewSecurityIpVo.setSecurityIpB("");
		}
		
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		StringBuffer sb = new StringBuffer("insert into security_ip(ip_a,ip_b,user_id,user_name,start_date,end_date,created_by, created_timestamp, modified_by, modified_timestamp,active_flag,ip_range_flag) values");
		sb.append("('"+formatIp(viewSecurityIpVo.getSecurityIpA())+"','"+formatIp(viewSecurityIpVo.getSecurityIpB())+"',"+c+",(select user_name from user u where u.id = "+c+"),"+aString+","+bString+","+SystemUtil.getCurrentUser().getId()+",'"+datetime+"',"+SystemUtil.getCurrentUser().getId()+",'"+datetime+"','"+viewSecurityIpVo.getActiveFlag()+"','"+viewSecurityIpVo.getIpRangeFlag()+"')");
		final String queryString = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
	}
	
	private String formatIp(String ip){
		if("".equals(ip)){
			return "";
		}
		String[] listIp = ip.split("\\.");
		StringBuffer formatedAfterIp = new StringBuffer("");
		for(String item : listIp){
			String regex = item.substring(0, 1) + "{" + item.length() + "}";  
			if(item.matches(regex) && item.indexOf("0") != -1){
				item = "0";
				formatedAfterIp.append(item);
				formatedAfterIp.append(".");
			}else{
				item = item.replaceAll("^(0+)", "");
				formatedAfterIp.append(item);
				formatedAfterIp.append(".");
			}
		}
		return formatedAfterIp.toString().substring(0,formatedAfterIp.length()-1);
	} 
	
	public Integer findSecurityIp(String securityIp,String securityStartDate,String securityEndDate){
//		StringBuffer sb = new StringBuffer("select count(1) from security_ip si where si.ip = '"+securityIp+"' and si.start_date = '"+securityStartDate+"' and si.end_date = '"+securityEndDate+"' and si.rec_active_flag='Y' ");
		StringBuffer sb = new StringBuffer("select count(1) from security_ip si where si.ip = '"+securityIp+"' ");

		logger.info("Enter method findSecurityIp.");
		final String sql = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method findSecurityIp.");
		return count;
	}
	
	public void addSecurityIpTrueOrFalse(String securityIp,
			String securityStartDate, String securityEndDate,String securityUserName) {
		
		String aString=null;
		if("".equals(securityStartDate)){
			aString=null;
		}else {
			aString="'"+securityStartDate+"'";
		}
		String bString=null;
		if("".equals(securityEndDate)){
			bString=null;
		}else {
			bString="'"+securityEndDate+"'";
		}
		Integer c=null;
		if("all".equals(securityUserName)){
			c=null;
		}else {
			c=Integer.parseInt(securityUserName);
		}
		SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String datetime = tempDate.format(new java.util.Date());
		
		StringBuffer sb = new StringBuffer("update security_ip si , user u");
		sb.append(" set ip_a='" +securityIp+"',si.user_id="+c+",si.user_name=u.user_name,si.start_date="+aString+",si.end_date="+bString+",si.modified_by="+SystemUtil.getCurrentUser().getId()+",si.modified_timestamp='"+datetime+"',si.rec_active_flag='Y'");
		sb.append(" where si.user_id = u.id and si.ip='"+securityIp+"'");
		final String queryString = sb.toString();
		HibernateTemplate template = this.getHibernateTemplate();
		template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).executeUpdate();
			}
		});
		
	}
	public List<SecurityIp> checkSecuirtyRecActiveFlageN(ViewSecurityIpVo viewSecurityIpVo) {
		logger.info("Enter method checkSecuirtyRecActiveFlageN.");
		String securityUserId = "all".equals(viewSecurityIpVo.getSecurityUserId())?"= ''":"= "+viewSecurityIpVo.getSecurityUserId();
		String ipB;
		if("Y".equals(viewSecurityIpVo.getIpRangeFlag())){
			ipB = (viewSecurityIpVo.getSecurityIpB() == null || "".equals(viewSecurityIpVo.getSecurityIpB()))? "= ''":("= '"+viewSecurityIpVo.getSecurityIpB()+"'");
		}else{
			ipB = "= ''";
		}
		HibernateTemplate template = this.getHibernateTemplate();
		String queryString = "from SecurityIp where ipA = '"+formatIp(viewSecurityIpVo.getSecurityIpA())+"' and ifnull(ipB,'') "+formatIp(ipB)+" and ifnull(userId,'') "+securityUserId;
		List<SecurityIp> list = (List<SecurityIp>) template.find(queryString);
		logger.info("Exit method checkSecuirtyRecActiveFlageN.");
		return list;
	}

}

