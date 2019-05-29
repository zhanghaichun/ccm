package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Function;
import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.SecurityIp;
import com.saninco.ccm.model.SysConfig;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.UserDAO;
import com.saninco.ccm.model.UserDelegation;
import com.saninco.ccm.model.UserPrivilege;
import com.saninco.ccm.model.UserRole;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.ViewSecurityVO;

/**
 * 
 * (Optimization of complete by xinyu.Liu)
 *
 */
public class SecurityManagementDaoImpl extends HibernateDaoSupport implements
		ISecurityManagementDao {

	private static final Log log = LogFactory.getLog(UserDAO.class);

	public SecurityManagementDaoImpl() {

	}
	
	/**
	 * Locate the UserRole RoleId under UserId and, returns the List 
	 */
	public List<String> findUserRoleByUserIdAndRoleId(Integer userId, Integer roleId, boolean bo) {
		logger.info("Enter method findUserRoleByUserIdAndRoleId.");
		final String sql = this.getUserRoleByUserNumberQueryString(userId, roleId, bo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method findUserRoleByUserIdAndRoleId.");
		return list;
	}

	private String getUserRoleByUserNumberQueryString(Integer userId,Integer roleId, boolean bo) {
		logger.info("Enter method getUserRoleByUserNumberQueryString.");
		StringBuffer sb = new StringBuffer("select ur.id a ");
		sb.append("from user_role ur ");
		sb.append("where ur.user_id = " + userId + " and ur.role_id = " + roleId + " ");
		if (bo) sb.append("and ur.rec_active_flag = 'Y' ");
		logger.info("Exit method getUserRoleByUserNumberQueryString.");
		return sb.toString();
	}

	/**
	 * get Delegation List By UserId ### (com.saninco.ccm.vo.ViewSecurityVO)
	 */
	public List<String> getDelegationByUserId(ViewSecurityVO viewSecurityVO) {
		logger.info("Enter method getDelegationByUserId.");
		final String sql = this.getDelegationQueryString(viewSecurityVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getDelegationByUserId.");
		return list;
	}

	private String getDelegationQueryString(ViewSecurityVO vvo) {
		logger.info("Enter method getDelegationQueryString.");
		StringBuffer sb = new StringBuffer("select concat('{id:',ud.id,',toUser:\"',concat(u.first_name,' ',u.last_name),");
		sb.append("'\",startTime:\"',if(ud.start_date is null,'',ud.start_date),");
		sb.append("'\",endTime:\"',if(ud.end_date is null,'',ud.end_date),");
		sb.append("'\"}') a ");
		sb.append("from user_delegation ud, user u ");
		sb.append("where ud.rec_active_flag = 'Y' and ud.to_user_id = u.id ");
		if (vvo.getUserId() != null) sb.append(" and ud.from_user_id = " + vvo.getUserId() + " ");
		sb.append(vvo.getOrderByCause(null) + " ");
		sb.append(vvo.getLimitCause() + " ");
		logger.info("Enter method getDelegationQueryString.");
		return sb.toString();
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ISecurityManagementDao#getNumberOfDelegation(com.saninco.ccm.vo.ViewSecurityVO)
	 */
	public long getNumberOfDelegation(ViewSecurityVO viewSecurityVO) {
		logger.info("Enter method getNumberOfDelegation.");
		final String sql = this.getDelegationNumberQueryString(viewSecurityVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfDelegation.");
		return count;
	}

	private String getDelegationNumberQueryString(ViewSecurityVO vvo) {
		logger.info("Enter method getDelegationNumberQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from user_delegation ud, user u ");
		sb.append("where ud.rec_active_flag = 'Y' and ud.to_user_id = u.id ");
		if (vvo.getUserId() != null) sb.append(" and ud.from_user_id = " + vvo.getUserId() + " ");
		logger.info("Exit method getDelegationNumberQueryString.");
		return sb.toString();
	}

	/**
	 * get UserRoleId List by userId
	 */
	public List<String> getUserRoleId(int userId) {
		logger.info("Enter method getUserRoleId.");
		final String sql = this.getUserRoleQueryStringAssignment(userId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getUserRoleId.");
		return list;
	}

	private String getUserRoleQueryStringAssignment(int userId) {
		logger.info("Enter method getUserRoleQueryStringAssignment.");
		StringBuffer sb = new StringBuffer("select ur.role_id a ");
		sb.append("from user_role ur ");
		sb.append("where ur.rec_active_flag = 'Y' ");
		if (String.valueOf(userId) != null) sb.append(" and ur.user_id = " + userId + " ");
		logger.info("Enter method getUserRoleQueryStringAssignment.");
		return sb.toString();
	}
	
	public long getNumberOfUserPrivilege(ViewSecurityVO viewSecurityVO){
		logger.info("Enter method getNumberOfUserPrivilege.");
		final String sql = this.getNumberOfUserPrivilegeQueryString(viewSecurityVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfUserPrivilege.");
		return count;
	}
	
	private String getNumberOfUserPrivilegeQueryString(ViewSecurityVO vvo) {
		logger.info("Enter method getNumberOfUserPrivilegeQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from user_privilege u ");
		sb.append("where u.rec_active_flag = 'Y' ");
		sb.append(" and u.user_id = " + vvo.getUserId() + " ");
		sb.append(" and u.ban_id = " + vvo.getPreviledgeBanId() + " ");
		sb.append(" and u.vendor_id = " + vvo.getPreviledgeVendorId() + " ");
		logger.info("Exit method getNumberOfUserPrivilegeQueryString.");
		return sb.toString();
	}
	
	public long getNumberOfUserName(ViewSecurityVO viewSecurityVO){
		logger.info("Enter method getNumberOfUserName.");
		final String sql = this.getNumberOfUserNameQueryString(viewSecurityVO);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfUserName.");
		return count;
	}
	
	private String getNumberOfUserNameQueryString(ViewSecurityVO vvo) {
		logger.info("Enter method getNumberOfUserNameQueryString.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from user u ");
		sb.append("where u.user_name = '" + vvo.getUserName() + "' ");
		if(vvo.getUserId() != null) sb.append(" and u.id <> " + vvo.getUserId() + " ");
		logger.info("Exit method getNumberOfUserNameQueryString.");
		return sb.toString();
	}


	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.ISecurityManagementDao#getNumberOfVendorBanPreviledge(com.saninco.ccm.vo.ViewSecurityVO)
	 */
	public long getNumberOfVendorBanPreviledge(ViewSecurityVO vvo) {
		logger.info("Enter method getNumberOfVendorBanPreviledge.");
		final String sql = this.getVendorBanNumberQueryStringPreviledge(vvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		logger.info("Exit method getNumberOfVendorBanPreviledge.");
		return count;
	}

	private String getVendorBanNumberQueryStringPreviledge(ViewSecurityVO vvo) {
		logger.info("Enter method getVendorBanNumberQueryStringPreviledge.");
		StringBuffer sb = new StringBuffer("select count(1) a ");
		sb.append("from ((user_privilege u left join vendor v on(u.vendor_id=v.id)) left join ban b on (u.ban_id=b.id)) ");
		sb.append("where u.rec_active_flag='Y' ");
		if(vvo.getUserId() != null) sb.append(" and u.user_id=" + vvo.getUserId() + " ");
		logger.info("Exit method getVendorBanNumberQueryStringPreviledge.");
		return sb.toString();
	}

	/**
	 * get Previledge VerderBan ByUserId ### (com.saninco.ccm.vo.ViewSecurityVO)
	 */
	public List<String> getVerderBanByUserIdPreviledge(ViewSecurityVO viewSecurityVO) {
		logger.info("Enter method getVerderBanByUserIdPreviledge.");
		final String sql = this.getVerderBanQueryStringPreviledge(viewSecurityVO);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				return session.createSQLQuery(sql).list();
			}
		});
		logger.info("Exit method getVerderBanByUserIdPreviledge.");
		return list;
	}

	private String getVerderBanQueryStringPreviledge(ViewSecurityVO vvo) {
		logger.info("Enter method getVerderBanQueryStringPreviledge.");
		StringBuffer sb = new StringBuffer("select concat('{id:',u.id,',vendor:\"',if(v.vendor_name is null,'',v.vendor_name),");
		sb.append("'\",ban:\"',if(b.account_number is null,'',b.account_number),");
		sb.append("'\"}') a ");
		sb.append("from ((user_privilege u left join vendor v on(u.vendor_id=v.id)) left join ban b on (u.ban_id=b.id)) ");
		sb.append("where u.rec_active_flag='Y' ");
		if(vvo.getUserId() != null) sb.append(" and u.user_id=" + vvo.getUserId() + " ");
		if(vvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(vvo.getFilter());
		}
		sb.append(vvo.getOrderByCause(null) + " ");
		sb.append(vvo.getLimitCause() + " ");
		logger.info("Enter method getVerderBanQueryStringPreviledge.");
		return sb.toString();
	}
	
	public List findByProperty(String propertyName, Object value,String propertyName2, Object value2,String propertyName3, Object value3,String propertyName4, Object value4) {
		log.debug("finding UserPrivilege instance with property");
		try {
			String queryString = "from UserPrivilege as model where model."
					+ propertyName + "= ? and model."+ propertyName2 + "= ? and model."+ propertyName3 + "= ? and model."+ propertyName4 + "= ? ";
			return getHibernateTemplate().find(queryString, new Object[]{value,value2,value3,value4});
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * find User Date By id
	 */
	public User findUserById(Integer id) {
		log.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get("com.saninco.ccm.model.User", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * find Role All return List
	 */
	public List<Role> findRoleAll() {
		log.debug("finding all Role instances");
		try {
			String queryString = "from Role";
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * System does not contain the userId and find the userList
	 */
	public List<User> findUserNotInUserId(String userId) {
		logger.debug("finding NotInUserId User instances");
		List<User> users = null;
		try {
			String queryString = "from User u where u.recActiveFlag='Y' and u.activeFlag!='N' and u.systemUserFlag NOT LIKE 'Y' and id NOT IN (0," + userId + ") order by u.firstName , u.lastName asc ";
			users = getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			logger.error("find NotInUserId failed", re);
		}
		return users;
	}

	/**
	 * save UserDelegation by UserDelegation
	 */
	public void saveDelegation(UserDelegation transientInstance) {
		log.debug("saving UserDelegation instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * find UserDelegation By Id
	 */
	public UserDelegation findDelegationById(Integer id) {
		log.debug("getting UserDelegation instance with id: " + id);
		try {
			UserDelegation instance = (UserDelegation) getHibernateTemplate().get("com.saninco.ccm.model.UserDelegation", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * save userRole by UserRole
	 */
	public void saveUserRole(UserRole transientInstance) {
		log.debug("saving UserRole instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/**
	 * find UserPrivilege By Id
	 */
	public UserPrivilege findUserPrevilegeById(Integer id) {
		log.debug("getting UserPrivilege instance with id: " + id);
		try {
			UserPrivilege instance = (UserPrivilege) getHibernateTemplate().get("com.saninco.ccm.model.UserPrivilege", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public UserPrivilege findUserPrivilegeById(Integer id){
		log.debug("getting UserPrivilege instance with id: " + id);
		try {
			UserPrivilege instance = (UserPrivilege) getHibernateTemplate().get("com.saninco.ccm.model.UserPrivilege", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * find Vendor By Id
	 */
	public Vendor findVendorById(Integer id) {
		log.debug("getting Vendor instance with id: " + id);
		try {
			Vendor instance = (Vendor) getHibernateTemplate().get("com.saninco.ccm.model.Vendor", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * find ban by id
	 */
	public Ban findBanById(Integer id) {
		logger.debug("getting Ban instance with id: " + id);
		try {
			Ban instance = (Ban) getHibernateTemplate().get("com.saninco.ccm.model.Ban", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}

	/**
	 * save UserPrivilege by UserPrivilege
	 */
	public void savePrevilege(UserPrivilege transientInstance) {
		log.debug("saving UserPrivilege instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public void savePrivilege(UserPrivilege transientInstance) {
		log.debug("saving UserPrivilege instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	public UserPrivilege findUserPrivilegeByBan(Ban b) {
		log.debug("finding UserPrivilege instance By Ban");
		try {
			List ups = getHibernateTemplate().find("from com.saninco.ccm.model.UserPrivilege up where up.ban=? and up.user=? and up.recActiveFlag = 'Y' ", new Object[]{b,SystemUtil.getCurrentUser()});
			log.debug("find successful");
			if(ups != null && ups.size() > 0){
				return (UserPrivilege) ups.get(0);
			}else{
				return null;
			}
		} catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
	}
	
	public UserPrivilege findUserPrivilegeByVendor(Vendor v) {
		log.debug("finding UserPrivilege instance By Vendor");
		try {
			List ups = getHibernateTemplate().find("from com.saninco.ccm.model.UserPrivilege up where up.vendor=? and up.user=? and (up.ban is null or up.ban=0) and up.recActiveFlag = 'Y' ", new Object[]{v,SystemUtil.getCurrentUser()});
			log.debug("find successful");
			if(ups != null && ups.size() > 0){
				return (UserPrivilege) ups.get(0);
			}else{
				return null;
			}
		} catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
	}
	
	public UserPrivilege findUserPrivilege() {
		log.debug("finding UserPrivilege instance");
		try {
			List ups = getHibernateTemplate().find("from com.saninco.ccm.model.UserPrivilege up where (up.ban is null or up.ban=0) and (up.vendor is null or up.vendor=0) and up.user=? and up.recActiveFlag = 'Y' ",SystemUtil.getCurrentUser());
			log.debug("find successful");
			if(ups != null && ups.size() > 0){
				return (UserPrivilege) ups.get(0);
			}else{
				return null;
			}
		} catch (RuntimeException re) {
			log.error("find failed", re);
			throw re;
		}
	}
	
	/**
	 * find Role By Id
	 */
	public Role findRoleById(Integer id) {
		log.debug("getting Role instance with id: " + id);
		try {
			Role instance = (Role) getHibernateTemplate().get("com.saninco.ccm.model.Role", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * find UserRole By Id
	 */
	public UserRole findUserRoleById(Integer id) {
		log.debug("getting UserRole instance with id: " + id);
		try {
			UserRole instance = (UserRole) getHibernateTemplate().get("com.saninco.ccm.model.UserRole", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	/**
	 * save User
	 */
	public void saveUser(User transientInstance) {
		log.debug("saving User instance");
		try {
			getHibernateTemplate().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}
	
	/**
	 * find Function By Id
	 */
	public Function findFunctionById(Integer id) {
		log.debug("getting Function instance with id: " + id);
		try {
			Function instance = (Function) getHibernateTemplate().get(
					"com.saninco.ccm.model.Function", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}
	
	public List<SecurityIp> getSecurityIPList() {
		logger.info("Enter method getSecurityIPList.");
		HibernateTemplate template = this.getHibernateTemplate();
		String queryString = "from SecurityIp where recActiveFlag = 'Y' order by ip_b , ip_a ,userId desc";
		List<SecurityIp> list = (List<SecurityIp>) template.find(queryString);
		logger.info("Exit method getSecurityIPList.");
		return list;
	}
	
	public String searchBBLoginFlag(){
		logger.info("Enter method searchBBLoginFlag.");
		HibernateTemplate template = this.getHibernateTemplate();
		String queryString = "from SysConfig where parameter = 'BB_Login_Flag'";
		List<SysConfig> list = (List<SysConfig>) template.find(queryString);
		SysConfig s = list.size()==1 ? list.get(0) : null;
		logger.info("Exit method searchBBLoginFlag.");
		return s!=null ? s.getValue() : "N";
	}

	public String verifyOperatePermission(ViewSecurityVO viewSecurityVO) {
		final String sql = "SELECT count(1) FROM (   (   (   (   user u JOIN user_role ur"
			+ " ON (u.id = ur.user_id AND ur.rec_active_flag = 'Y')) JOIN role r ON (r.id = ur.role_id AND r.rec_active_flag = 'Y')) "
			+ " JOIN role_function rf ON (r.id = rf.role_id AND rf.rec_active_flag = 'Y')) JOIN function f ON (f.id = rf.function_id AND f.function_active_flag = 'Y'))"
			+ " WHERE u.id = " + viewSecurityVO.getUserId() + " AND f.id = " + viewSecurityVO.getFunctionId();
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session) throws HibernateException, SQLException {
				List list = session.createSQLQuery(sql).list();
				return new Integer(list.get(0).toString());
			}
		});
		return count>0 ? "{data:true}" : "{data:false}";
	}
}
