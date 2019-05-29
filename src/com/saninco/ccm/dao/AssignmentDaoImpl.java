package com.saninco.ccm.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.vo.AssignmentVO;
/**
 * 
 * @author wei.su
 *
 */
public class AssignmentDaoImpl extends HibernateDaoSupport implements IAssignmentDao {
	
	private String getAssignmentSearchQueryString(AssignmentVO assignmentVO){
		StringBuffer sb = new StringBuffer("select concat(");
		sb.append("'{id:\"',IF(ba.id IS NULL,'',ba.id),");
		sb.append("'\",vendorId:\"',IF(v.id IS NULL,'',v.id),");
		sb.append("'\",vendor:\"',IF(v.vendor_name IS NULL,'',v.vendor_name),");
		sb.append("'\",banId:\"',IF(ba.id IS NULL,'',ba.id),");
		sb.append("'\",ban:\"',IF(ba.account_number IS NULL,'',ba.account_number),");
		sb.append("'\",AnalystId:\"',IF(ba.analyst_id IS NULL,'',ba.analyst_id),");
		sb.append("'\",Approver1Id:\"',IF(ba.approver1_id IS NULL,'',ba.approver1_id),");
		sb.append("'\",Approver2Id:\"',IF(ba.approver2_id IS NULL,'',ba.approver2_id),");
		sb.append("'\",Approver3Id:\"',IF(ba.approver3_id IS NULL,'',ba.approver3_id),");
		sb.append("'\",Analyst:\"',IF(ba.analyst_id IS NULL,'',CONCAT(analyst.first_name,blank_space(),analyst.last_name) ),");
		sb.append("'\",Approver1:\"',IF(ba.approver1_id IS NULL,'',CONCAT(approver1.first_name,blank_space(),approver1.last_name)),");
		sb.append("'\",Approver2:\"',IF(ba.approver2_id IS NULL,'',CONCAT(approver2.first_name,blank_space(),approver2.last_name)),");
		sb.append("'\",Approver3:\"',IF(ba.approver3_id IS NULL,'',CONCAT(approver3.first_name,blank_space(),approver3.last_name)),");
		sb.append("'\"}') ");
		sb.append(" FROM ((((((ban ba LEFT JOIN user analyst ON(ba.analyst_id=analyst.id)) LEFT JOIN user approver1 ON(ba.approver1_id=approver1.id)) LEFT JOIN user approver2 ON(ba.approver2_id=approver2.id)) LEFT JOIN user approver3 ON(ba.approver3_id=approver3.id)) LEFT JOIN vendor v ON(ba.vendor_id=v.id)) ) ");
		sb.append(" where " );
		if(assignmentVO.getFilter()!=null){
				sb.append(assignmentVO.getFilter());
				sb.append(" and ");
			}	
		sb.append("ba.rec_active_flag='Y'");
		sb.append(" order by " + assignmentVO.getSortField() + " " + assignmentVO.getSortingDirection());
		sb.append(" LIMIT " + assignmentVO.getStartIndex() + "," + assignmentVO.getRecPerPage());
		return sb.toString();
	}

	
	public long getNumberOfAssignment(
			AssignmentVO assignmentVO) {
		logger.info("Enter method getNumberOfContactLogs.");
		StringBuffer strSQL = new StringBuffer(400);
		strSQL.append("select count(1) FROM ((((((ban ba LEFT JOIN user analyst ON(ba.analyst_id=analyst.id)) LEFT JOIN user approver1 ON(ba.approver1_id=approver1.id)) LEFT JOIN user approver2 ON(ba.approver2_id=approver2.id)) LEFT JOIN user approver3 ON(ba.approver3_id=approver3.id)) LEFT JOIN vendor v ON(ba.vendor_id=v.id))) where ");
		if(assignmentVO.getFilter()!=null){
			strSQL.append(assignmentVO.getFilter());
			strSQL.append(" and ");
			}			
		strSQL.append("ba.rec_active_flag='Y'");
		Session session = getSession();
		try {
			BigInteger count = (BigInteger)session.createSQLQuery(strSQL.toString()).uniqueResult();
			logger.info("Exit method getNumberOfAssignment.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}
	
	
	public List searchAssignments( AssignmentVO assignmentVO) {
		logger.info("Enter method searchAssignments.");
		final String sql = this.getAssignmentSearchQueryString(assignmentVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List list = (List)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method searchAssignments.");
		return list;
	}
	
	public long getNumberOfAssignmentByVendorIdAndBanId(AssignmentVO assignmentVO) {
		logger.info("Enter method getNumberOfAssignmentByVendorIdAndBanId.");
		StringBuffer strSQL = new StringBuffer(400);
		strSQL.append("select count(*) FROM ban  WHERE ban.vendor_id =  '"+assignmentVO.getVendorId()+"' AND ban.id =  '"+assignmentVO.getBanId()+"' ");
		strSQL.append(" and ban.rec_active_flag='Y'");
		Session session = getSession();
		try {
			BigInteger count = (BigInteger)session.createSQLQuery(strSQL.toString()).uniqueResult();
			logger.info("Exit method getNumberOfAssignmentByVendorIdAndBanId.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}

}
