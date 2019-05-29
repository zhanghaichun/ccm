package com.saninco.ccm.dao;

import java.math.BigInteger;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.UserDelegation;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.ProfileVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * @author Jian.Dong
 * */
public class UserDelegationDaoImpl extends HibernateDaoSupport implements
		IUserDelegationDao {

	private final Log logger = LogFactory.getLog(this.getClass());

	/**
	 * Assign users access to my
	 */
	public List findMyDesignateUsers(int userId) {
		logger.info("Enter method findDesignateUserIds.");
		String currentDateStr = SystemUtil.parseString(new Date());
		String sql = "select o.from_user_id,u.user_name from user_delegation o,user u where o.from_user_id=u.id and o.to_user_id="
				+ userId + " and datediff(o.start_date, '" + currentDateStr + "')<=0"
				+ " and datediff(o.end_date, '" + currentDateStr + "')>=0"
				+ " and o.rec_active_flag='Y'";
		Session session = getSession();
		try {
			List result = session.createSQLQuery(sql).list();
			logger.info("Exit method findDesignateUserIds.");
			return result;
		} finally {
			releaseSession(session);
		}
	}
	
	/**Obtain the query string
	 * created by wei.su
	 * @param profileVO
	 * @param userId
	 * @return
	 */
	private String getMyDesignateSearchQueryString(ProfileVO profileVO, int userId){
		logger.info("Enter method getDesignateSearchQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',user_delegation.id,',to_user_id:',user_delegation.to_user_id,',user_name:\"',user.user_name,");
		sb.append("'\",start_date:\"',if(user_delegation.start_date is null,'',user_delegation.start_date),'\",end_date:\"',if(user_delegation.end_date is null,'',user_delegation.end_date),");
		sb.append("'\",first_name:\"',if(user.first_name is null,'',user.first_name),'\",last_name:\"',if(user.last_name is null,'',user.last_name),");
		sb.append("'\"}') a ");
		sb.append("from user_delegation join user ");
		sb.append("where ");
		sb.append("user_delegation.from_user_id="+SystemUtil.getCurrentUserId()+" ");
		sb.append(" and user_delegation.rec_active_flag='Y'");
		sb.append(" and user_delegation.to_user_id=user.id");
		if(profileVO.getSortField()!=null)
			sb.append(" order by "+profileVO.getSortField()+" ");
		if(profileVO.getSortingDirection()!=null) 
			sb.append(profileVO.getSortingDirection()+" ");
		sb.append("limit " + profileVO.getStartIndex());
		sb.append(","+profileVO.getRecPerPage()); 

		logger.info("Exit method getDesignateSearchQueryString.");
		return sb.toString();
	}
	
	public long findDelegationToUserId(String currentAnalystId) {
		logger.info("Enter method findDelegationToUserId.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from user_delegation ud ");
		sb.append("where ud.rec_active_flag = 'Y' ");
		sb.append("and ud.to_user_id = " + SystemUtil.getCurrentUserId() + " ");
		sb.append("and ud.from_user_id = " + currentAnalystId + " ");
		sb.append("and datediff(ud.start_date, now()) <= 0 "); 
		sb.append("and datediff(ud.end_date,  now()) >= 0 "); 
		Session session = getSession();
		try {
			BigInteger count = (BigInteger)session.createSQLQuery(sb.toString()).uniqueResult();
			logger.info("Exit method findDelegationToUserId.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}

	/**According to the record number. Conditions
	 * created by wei.su
	 */
	public long getNumberOfMyDesignate(ProfileVO profileVO,int userId) {
		logger.info("Enter method getNumberOfDesignate.");
		StringBuffer strSQL = new StringBuffer(400);

		strSQL.append("select count(1) from user_delegation where from_user_id=");
		strSQL.append(SystemUtil.getCurrentUserId());
		strSQL.append(" and rec_active_flag='Y'"); 
		Session session = getSession();
		try {
			BigInteger count = (BigInteger)session.createSQLQuery(strSQL.toString()).uniqueResult();
			logger.info("Exit method getNumberOfDesignate.");
			return count.longValue();
		} finally {
			releaseSession(session);
		}
	}
	
	/**According to the list set a record and returns
	 * created by wei.su
	 */
	public List<String> searchMyDesignate(ProfileVO profileVO,int userId) {
		logger.info("Enter method SearchDesignate.");
		final String sql = this.getMyDesignateSearchQueryString(profileVO, userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method SearchDesignate.");
		return list;
	}
	
	public List<Object[]> getMyDesignateUsers()
	{
		logger.info("Enter method getMyDesignateUsers.");
		String currentDateStr = SystemUtil.parseString(new Date());
		String sql = "select u.id,concat(u.first_name,' ',u.last_name) from user_delegation o,user u where o.from_user_id=u.id and o.to_user_id="
				+ SystemUtil.getCurrentUserId() + " and datediff(o.start_date, '" + currentDateStr + "')<=0"//o.created_timestamp = DATE(NOW()) AND o.start_date<=DATE(NOW())  AND o.end_date>=DATE(NOW()) and
				+ " and datediff(o.end_date, '" + currentDateStr + "')>=0"
				+ " and o.rec_active_flag='Y'"
		        + " and u.system_user_flag not like 'Y'"
				+ " AND u.active_flag =  'Y'";
		Session session = getSession();
		try {
			List<Object[]> result = session.createSQLQuery(sql).list();
			logger.info("Exit method getMyDesignateUsers.");
			return result;
		} finally {
			releaseSession(session);
		}
		
	}
	
	public UserDelegation getOneUserDelegation(ProfileVO profileVO) {
		logger.debug("finding UserDelegation");
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("from UserDelegation as d where d.userByToUserId ="+ profileVO.getDelegateToUserId());
			sb.append(" and d.startDate = '" + profileVO.getFromDate()+"'");
			sb.append(" and d.endDate = '" + profileVO.getToDate()+"'");
			List<UserDelegation> ul = (List<UserDelegation>)getHibernateTemplate().find(sb.toString());
			return ul.get(0);
		} catch (RuntimeException re) {
			logger.error("find UserDelegation error", re);
			throw re;
		}
	}

	
	/**Save a data in the database
	 * created by wei.su
	 */
	public void save(UserDelegation transientInstance) {
		//log.debug("saving UserDelegation instance");
		try {
			getHibernateTemplate().save(transientInstance);
			//log.debug("save successful");
		} catch (RuntimeException re) {
			//log.error("save failed", re);
			throw re;
		}
	}
	
	/**Delete records from a database
	 * created by wei.su
	 */
	public void delete(UserDelegation persistentInstance) {
		//log.debug("deleting UserDelegation instance");
		try {
			getHibernateTemplate().delete(persistentInstance);
			//log.debug("delete successful");
		} catch (RuntimeException re) {
			//log.error("delete failed", re);
			throw re;
		}
	}
	
	/**According to a record Id
	 * created by wei.su
	 */
	public UserDelegation findById(java.lang.Integer id) {
		//log.debug("getting UserDelegation instance with id: " + id);
		try {
			UserDelegation instance = (UserDelegation) getHibernateTemplate()
					.get("com.saninco.ccm.model.UserDelegation", id);
			return instance;
		} catch (RuntimeException re) {
			//log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * created by wei.su
	 */
	public UserDelegation merge(UserDelegation detachedInstance) {
		//log.debug("merging UserDelegation instance");
		try {
			UserDelegation result = (UserDelegation) getHibernateTemplate()
					.merge(detachedInstance);
			//log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			//log.error("merge failed", re);
			throw re;
		}
	}
	
	
	
	public List<String> searchUserDelegationAssignment() {
		logger.info("Enter method searchUserDelegationAssignment.");
		StringBuilder sql = new StringBuilder();
//		sql.append(this.getAssignmentSearchSelectCause());
//		sql.append(this.getAssignmentWhereCause());
//		sql.append(svo.getOrderByCause(assignmentSearchAlias));
//		sql.append(svo.getLimitCause());
		Session session = getSession();
		try {
			Query query = session.createSQLQuery(sql.toString());
			query.setInteger(0, SystemUtil.getCurrentUserId());
			List<String> result = (List<String>)query.list();
			logger.info("Exit method searchUserDelegationAssignment.");
			return result;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * @author pengfei.wang
	 * To show for all the User name Backup of the list
	 * */
	public List findMyWorkSparsUsers() {
		logger.info("Enter method findDesignateUserIds.");
		String currentDateStr = SystemUtil.parseString(new Date());
		String sql = "SELECT `user`.id,concat(first_name,' ',last_name) FROM `user` WHERE "+SystemUtil.getCurrentUserId()+" =  `user`.backup_user_id and `user`.rec_active_flag='Y' and `user`.system_user_flag not like 'Y' and `user`.active_flag =  'Y'";
		Session session = getSession();
		try {
			List result = session.createSQLQuery(sql).list();
			logger.info("Exit method findDesignateUserIds.");
			return result;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * @author pengfei.wang
	 * To show for all the User name Backup of the list
	 * */
	public List findBBMyWorkSparsUsers() {
		logger.info("Enter method findDesignateUserIds.");
		String currentDateStr = SystemUtil.parseString(new Date());
		String sql = "SELECT `user`.id,concat(first_name,' ',last_name) FROM `user` WHERE "+SystemUtil.getCurrentUserId()+" =  `user`.backup_user_id and `user`.rec_active_flag='Y' and `user`.system_user_flag not like 'Y' and `user`.active_flag =  'Y'" +
					" and id IN (SELECT DISTINCT b.approver1_id FROM ban b where b.rec_active_flag = 'Y' and b.ban_status_id = 1 and b.master_ban_flag = 'Y'" +
					" UNION SELECT DISTINCT b.approver2_id FROM ban b where b.rec_active_flag = 'Y' and b.ban_status_id = 1 and b.master_ban_flag = 'Y'" +
					" UNION SELECT DISTINCT b.approver3_id FROM ban b where b.rec_active_flag = 'Y' and b.ban_status_id = 1 and b.master_ban_flag = 'Y'" +
					" UNION SELECT DISTINCT b.approver4_id FROM ban b where b.rec_active_flag = 'Y' and b.ban_status_id = 1 and b.master_ban_flag = 'Y')";
		Session session = getSession();
		try {
			List result = session.createSQLQuery(sql).list();
			logger.info("Exit method findDesignateUserIds.");
			return result;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * @author pengfei.wang
	 * With his Supervisor to show all of the User name list
	 * */
	public List findMyWorkSparsSerUsers() {
		logger.info("Enter method findDesignateUserIds.");
		String currentDateStr = SystemUtil.parseString(new Date());
		//update by mingyang.Li 2012.03.09 start
		//String sql = "SELECT `user`.id, concat(first_name,' ',last_name) FROM `user` WHERE `user`.supervisor_user_id =  "+SystemUtil.getCurrentUserId()+" AND `user`.rec_active_flag =  'Y' and `user`.system_user_flag not like 'Y' and  `user`.active_flag =  'Y'";
		String sql = "SELECT u.id, concat(u.first_name, ' ', u.last_name) FROM `user` u " +
					 "WHERE u.supervisor_user_id = "+SystemUtil.getCurrentUserId()+" AND u.rec_active_flag = 'Y' AND u.system_user_flag NOT LIKE 'Y' AND u.active_flag = 'Y'" +
					 " union " +
					 "SELECT u.id, concat(u.first_name, ' ', u.last_name) FROM `user` u " +
					 "WHERE u.rec_active_flag = 'Y' AND u.system_user_flag NOT LIKE 'Y' AND u.active_flag = 'Y' AND u.id in (SELECT v.from_user_id FROM view_bucket v WHERE v.rec_active_flag = u.rec_active_flag AND v.to_user_id = "+SystemUtil.getCurrentUserId()+");";
		//update by mingyang.Li 2012.03.09 end
		Session session = getSession();
		try {
			List result = session.createSQLQuery(sql).list();
			logger.info("Exit method findDesignateUserIds.");
			return result;
		} finally {
			releaseSession(session);
		}
	}
	
	
	
	

}
