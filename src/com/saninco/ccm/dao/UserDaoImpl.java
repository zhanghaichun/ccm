/**
 * 
 */
package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UsernameNotFoundException;

import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.Theme;
import com.saninco.ccm.model.User;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchUserListVO;

/**
 * 
 * (Optimization of complete by xinyu.Liu)
 * 
 */
public class UserDaoImpl extends HibernateDaoSupport implements IUserDao {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	private static final String searchUserRoleView = "select f.id from ((((user u join user_role ur on(u.id=ur.user_id and ur.rec_active_flag='Y')) join role r on (r.id=ur.role_id and r.rec_active_flag='Y')) join role_function rf on(r.id=rf.role_id and rf.rec_active_flag='Y')) join function f on(f.id=rf.function_id and f.function_active_flag='Y'))";
	
	public UserDaoImpl() {
	}
	
	public List findAll() {
		logger.debug("finding all User instances");
		List users = null;
		Session session = getSession();
		try {
			String queryString = "select u.id,u.first_name,u.last_name from user u where u.rec_active_flag='Y' and u.system_user_flag!='Y' and u.active_flag!='N' order by u.first_name, u.last_name asc ";
			users = session.createSQLQuery(queryString).list();
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
		} finally {
			releaseSession(session);
		}
		return users;
	}
	
	public List<User> findDeligationUser() {
		logger.debug("finding all User instances");
		List<User> users = null;
		try {
			String queryString = "from User u where u.recActiveFlag='Y' and u.systemUserFlag!='Y' and u.id !=" + SystemUtil.getCurrentUserId();
			users = getHibernateTemplate().find(queryString);
		} catch (RuntimeException re) {
			logger.error("find all failed", re);
		}
		return users;
	}
	
	public User findById(java.lang.Integer id) {
		logger.debug("getting User instance with id: " + id);
		try {
			User instance = (User) getHibernateTemplate().get(
					"com.saninco.ccm.model.User", id);
			return instance;
		} catch (RuntimeException re) {
			logger.error("get failed", re);
			throw re;
		}
	}
	/* (non-Javadoc)
	 * @see com.saninco.ccm.dao.IUserDao#getUser(java.lang.String)
	 */
	public User getUser(String userName) {
		List<?> usersList = getHibernateTemplate().find("from com.saninco.ccm.model.User u where u.userName='"+userName+"'");
		if(usersList != null && usersList.size() == 1) {
			return (User) usersList.get(0);
		}
		
		return null;
	}
	
	public User getUser(Integer userId) {
		List<?> usersList = getHibernateTemplate().find("from User where id=?", userId);
		if(usersList != null && usersList.size() == 1) {
			return (User) usersList.get(0);
		}
		
		return null;
	}

	public UserDetails loadUserByUsername(String arg0)
			throws UsernameNotFoundException, DataAccessException {
		return null;
	}

	public void updateUser(User user) {
		getHibernateTemplate().update(user);		
	}

	@SuppressWarnings("unchecked")
	public List<User> findUsersByEmail(String email) {
		if(email == null) return null;
		email = email.toLowerCase();
		return (List<User>) getHibernateTemplate().find(
				"from User where lower(email)=? and (recActiveFlag='Y') ", email);
	}

	public Integer addUser(User user) {	
		HibernateTemplate ht = this.getHibernateTemplate();
		ht.save(user);
		
		return user.getId();
	}

	public void deleteUser(User user) {
		Integer userId = user.getId();
		User existingUser = this.getUser(userId);
		existingUser.setRecActiveFlag("N");
		getHibernateTemplate().update(existingUser);		
	}

	
	@SuppressWarnings("unchecked")
	public List<Role> getAllRoles(){		
		List<Role> roles = getHibernateTemplate().find(
				"select d from Role d order by d.name desc");
		return roles;		
    }
	
	public User getUser(String firstName, String lastName) {
		
		List<?> usersList = getHibernateTemplate().find(
				"from User where firstName=? and lastName=?", new Object[] {firstName, lastName});
		
		if(usersList != null && usersList.size() == 1) {
			return (User) usersList.get(0);
		}		
		return null;
	}

	/***
	 * GET ALL USERS BY WEI.SU NO Parameters
	 */
	public List<Object[]> getAnalyst() {
		logger.info("Enter method searchBanList.");
		String sql = this.getAnalystSqlString();
		Session session = getSession();
		try {
			List<Object[]> l = (List<Object[]>) session.createSQLQuery(sql).list();
			logger.info("Exit method searchBanList.");
			return l;
		} finally {
			releaseSession(session);
		}
	}
	private String getAnalystSqlString(){
		StringBuffer sb = new StringBuffer("select ");
		sb.append("id,concat(toJSON(first_name is null,'',first_name),' ',toJSON(last_name is null,'',last_name)) ");
		sb.append("from user ");
		sb.append("where active_flag = 'Y' ");
		sb.append("and system_user_flag != 'Y' ");
		sb.append("and rec_active_flag = 'Y' ");
		sb.append("order by concat(toJSON(first_name is null,'',first_name),' ',toJSON(last_name is null,'',last_name))");
		
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> getUsers(String userName) {
		return (List<User>)getHibernateTemplate().find(
				"from User where userName=?", userName);
	}
	/**
	 * @author wei.su
	 * getUsersBySupervisorUserId
	 */
	@SuppressWarnings("unchecked")
	public List getUsersBySupervisorUserId(int supervisorUserId) {
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select u.id,u.first_name,u.last_name from user u where u.rec_active_flag='Y' and u.id=(select uu.supervisor_user_id from user uu where uu.id="+supervisorUserId+"").list();
			return l;
		} finally {
			releaseSession(session);
		}
	}
	/**
	 * @author wei.su
	 * getUsersBySupervisorUserId
	 */
	@SuppressWarnings("unchecked")
	public List<User> getUsersWithOutSupervisorUserId(int supervisorUserId) {
		return (List<User>)getHibernateTemplate().find(
				"from com.saninco.ccm.model.User u where u.recActiveFlag='Y' and u.id !=(select uu.id from com.saninco.ccm.model.User uu where uu.supervisorUserId=?)", supervisorUserId);
	}
	public User loadUser(Integer userId) {
		User user = (User)getHibernateTemplate().load(User.class, userId);
		return user;
	}
	/**
	 * Get User List Totail Number [Chao.Liu]
	 * @param uvo
	 * @return
	 */
	public Integer getUserListTotailNumber(SearchUserListVO uvo){
		logger.info("Enter method getUserListTotailNumber.");

		final String sql = getUserListTotailNumberSqlString(uvo);
		HibernateTemplate template = this.getHibernateTemplate();
		Integer count = (Integer) template.execute(new HibernateCallback() {
			@SuppressWarnings("unchecked")
			public Object doInHibernate(Session session)
					throws HibernateException, SQLException {
				List l = session.createSQLQuery(sql).list();
				return new Integer(l.get(0).toString());
			}
		});

		logger.info("Exit method getUserListTotailNumber.");
		return count;
	}
	private String getUserListTotailNumberSqlString(SearchUserListVO uvo){
		logger.info("Enter method getUserListTotailNumberSqlString.");
		StringBuffer sb = new StringBuffer();
		sb.append("select count(1) ");
		sb.append(this.getUserListWhereSqlString(uvo));
		logger.info("Exit method getUserListTotailNumberSqlString.");
		return sb.toString();
	}
	
	/**
	 * Get User List Info [Chao.Liu]
	 * @param uvo
	 */
	public List<String> getUserList(SearchUserListVO uvo){
		logger.info("Enter method getUserList.");
		final String queryString = getUserListSqlString(uvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template.execute(new HibernateCallback() {
			public Object doInHibernate(Session session)throws HibernateException, SQLException {
				return session.createSQLQuery(queryString).list();
			}
		});
		logger.info("Exit method getUserList.");
		return list;
	}

	private String getUserListSqlString(SearchUserListVO uvo) {
		logger.info("Enter method getUserListSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{ ");
		sb.append("id:\"',cast(u.id as char),'\", ");
		sb.append("fname:\"',toJSON(u.first_name is null,'',u.first_name),'\" , ");
		sb.append("lname:\"',toJSON(u.last_name is null,'',u.last_name ),'\", ");
		sb.append("initials:\"',toJSON(u.initials is null,'',u.initials),'\", ");
		sb.append("uname:\"',toJSON(u.user_name is null,'',u.user_name),'\", ");
		sb.append("backup:\"',toJSON(bu.first_name is null,'',bu.first_name),' ',if(bu.last_name is null,'',bu.last_name),'\", ");
		sb.append("supervisor:\"',toJSON(su.first_name is null,'',su.first_name ),' ',if(su.last_name is null,'',su.last_name ),'\" ");
		sb.append("}') ");
		sb.append(this.getUserListWhereSqlString(uvo));
		sb.append(" order by " + uvo.getSortField() + " " + uvo.getSortingDirection());
		sb.append(" LIMIT " + uvo.getStartIndex() + "," + uvo.getRecPerPage());
		logger.info("Exit method getUserListSqlString.");
		return sb.toString();
	}
	
	private String getUserListWhereSqlString(SearchUserListVO uvo) {
		logger.info("Enter method getUserListWhereSqlString.");
		StringBuffer sb = new StringBuffer();
		sb.append("from ((`user` u left join `user` bu on (u.backup_user_id = bu.id)) left join `user` su on (u.supervisor_user_id = su.id)) ,(select id,CONCAT(if(first_name is null,'',first_name),' ',if(last_name is null,'',last_name)) as uname from `user`) ufl  ");
		sb.append("where u.id = ufl.id ");
		if(uvo.getFilter()!=null){
			sb.append(" and ");
			sb.append(uvo.getFilter());	
		}
		if(uvo.getUserName() != null){
			sb.append("and u.user_name like '%"+DaoUtil.ccmEscape(uvo.getUserName())+"%' ");
		}
		if(uvo.getInitials() != null){
			sb.append("and u.initials like '%"+DaoUtil.ccmEscape(uvo.getInitials())+"%' ");
		}
		if(uvo.getEmail() != null){
			sb.append("and u.email like '%"+DaoUtil.ccmEscape(uvo.getEmail())+"%' ");
		}
		if(uvo.getBackupUserId() != null){
			sb.append("and u.backup_user_id = "+uvo.getBackupUserId()+" ");
		}
		if(uvo.getSupervisorId() != null){
			sb.append("and u.supervisor_user_id = "+uvo.getSupervisorId()+" ");
		}
		if(uvo.getName() != null){
			while(uvo.getName().indexOf(" ") > 0){
				uvo.setName(uvo.getName().replaceAll(" ", "%"));
			}
			sb.append("and ufl.uname like '%"+DaoUtil.ccmEscape(uvo.getName())+"%' ");
		}
		if(uvo.getPhone() != null){
			sb.append("and (u.primary_phone like '%"+uvo.getPhone()+"%' ");
			sb.append("or u.office_phone like '%"+uvo.getPhone()+"%' ");
			sb.append("or u.cell_phone like '%"+uvo.getPhone()+"%' ");
			sb.append("or u.fax_number like '%"+uvo.getPhone()+"%')");
		}
		if(uvo.getUserCreateWiPastNumOfDays() != null){
			sb.append(" and datediff(u.created_timestamp, ADDDATE( now(), INTERVAL -" + uvo.getUserCreateWiPastNumOfDays() + " DAY))>=0 and datediff(u.created_timestamp, ADDDATE( now(), INTERVAL 0 DAY))<=0");
		}else{
			if(uvo.getCreateBeginDate() != null){
				sb.append(" and datediff( u.created_timestamp, '" + uvo.getCreateBeginDate() + "')>=0");
			}
			if(uvo.getCreatedEndDate() != null){
				sb.append(" and datediff( u.created_timestamp, '" + uvo.getCreatedEndDate() + "')<=0");
			}
		}
		if(uvo.getUserModifiedWiPastNumOfDays() != null){
			sb.append(" and datediff(u.modified_timestamp, ADDDATE( now(), INTERVAL -" + uvo.getUserModifiedWiPastNumOfDays() + " DAY))>=0 and datediff(u.modified_timestamp, ADDDATE( now(), INTERVAL 0 DAY))<=0");
		}else{
			if(uvo.getModifiedBeginDate() != null){
				sb.append(" and datediff( u.modified_timestamp, '" + uvo.getModifiedBeginDate() + "')>=0");
			}
			if(uvo.getModifiedEndDate() != null){
				sb.append(" and datediff( u.modified_timestamp, '" + uvo.getModifiedEndDate() + "')<=0");
			}
		}
		sb.append(" and (u.system_user_flag != 'Y' or u.system_user_flag is null) ");
		sb.append(" and u.rec_active_flag = 'Y' ");
		logger.info("Exit method getUserListWhereSqlString.");
		return sb.toString();
	}
	
	public Integer getUserCountByUS(SearchUserListVO uvo){
		List ul = getHibernateTemplate().find("from User where supervisor_user_id=? and rec_active_flag = 'Y' and system_user_flag = 'N' ", uvo.getUid());
		return ul.size(); 
	}
	public void updateUSByUserId(SearchUserListVO uvo){
		logger.info("Enter method updateUSByUserId.");
		final String sql = "update user set supervisor_user_id = "+uvo.getSuId()+" where supervisor_user_id = "+uvo.getUid()+" and rec_active_flag = 'Y'";
		Session session = getSession();
		try {
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateUSByUserId.");
	}
	
	/**
	 * Search User Name List 
	 * @param uvo
	 * @return
	 */
	public List<String> getUserIdAndName(SearchUserListVO uvo){
		logger.info("Enter method getUserIdAndName.");
		final String queryString = getUserIdAndNameSqlString(uvo);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> list = (List<String>) template
				.execute(new HibernateCallback() {
					public Object doInHibernate(Session session)
							throws HibernateException, SQLException {
						return session.createSQLQuery(queryString).list();
					}
				});

		logger.info("Exit method getUserIdAndName.");
		return list;
	}
	
	private String getUserIdAndNameSqlString(SearchUserListVO uvo) {
		logger.info("Enter method getUserIdAndNameSqlString.");
		StringBuffer sb = new StringBuffer("select CONCAT('{key:',id,', ");
		sb.append(" value:\"',if(user_name is null,'',user_name),'\" ");
		sb.append(" }') from user ");
		sb.append(" where rec_active_flag = 'Y' ");
		sb.append(" and id != "+uvo.getUid()+" ");
		sb.append(" and system_user_flag = 'N' ");
		sb.append(" order by user_name asc ");
		logger.info("Exit method getUserIdAndNameSqlString.");
		return sb.toString();
	}

	public Integer findAttachmentPointId(String tableName, Integer id) {
		logger.info("Enter method findAttachmentPointId.");
		String sql = "select t.attachment_point_id from "+tableName+" t where t.id="+id;
		Session session = getSession();
		try {
			Integer apid = (Integer)session.createSQLQuery(sql).uniqueResult();
			logger.info("Exit method findAttachmentPointId.");
			return apid;
		} finally {
			releaseSession(session);
		}
	}

	public void updateAttachmentPointId(String tableName, Integer id, Integer newId) {
		logger.info("Enter method updateAttachmentPointId.");
		String sql = "update "+tableName+" t set t.attachment_point_id="+newId+" where t.id="+id;
		Session session = getSession();
		try {
			session.createSQLQuery(sql).executeUpdate();
		} finally {
			releaseSession(session);
		}
		logger.info("Exit method updateAttachmentPointId."); 
	}

	public List<Theme> findThemes() {
		logger.info("Enter method findThemes."); 
		Session session = getSession();
		try {
			List<Theme> list = (List<Theme>)session.createQuery("from com.saninco.ccm.model.Theme order by id").list();
			logger.info("Exit method findThemes.");
			return list;
		} finally {
			releaseSession(session);
		}
	}
	
	public Theme findThemesById(Integer themeId) {
		logger.info("Enter method findThemesById."); 
		Session session = getSession();
		try {
			return (Theme)session.get(Theme.class, themeId);
		} finally {
			releaseSession(session);
		}
	}

	public Theme getTheme() {
		Session session = getSession();
		try {
			List l = session.createSQLQuery("select t.theme_path,t.theme_name,t.id from user u,theme t where u.id="+SystemUtil.getCurrentUserId() + " and u.theme_id=t.id").list();
			Object[] rs = (Object[]) l.get(0);
			Theme t = new Theme();
			t.setThemePath((String) rs[0]);
			t.setThemeName((String) rs[1]);
			t.setId((Integer) rs[2]);
			return t;
		} finally {
			releaseSession(session);
		}
	}

	public Theme updateUserTheme(Integer themeId,Integer randowThemeId) {
		Session session = getSession();
		try {
			String sql = "";
			if(themeId.intValue() == 0){
				sql = "update user u set u.theme_id="+themeId + " , random_theme_id="+randowThemeId+" where u.id="+SystemUtil.getCurrentUserId();
			}else{
				sql = "update user u set u.theme_id="+themeId + " where u.id="+SystemUtil.getCurrentUserId();
			}
			session.createSQLQuery(sql).executeUpdate();
			return (Theme)session.get(Theme.class, themeId);
		} finally {
			releaseSession(session);
		}
	}
	
	public Theme updateUserRandomTheme(Integer themeId,Integer userId) {
		Session session = getSession();
		try {
			String sql = "update user u set random_theme_id="+themeId + " where u.id="+userId;
			session.createSQLQuery(sql).executeUpdate();
			return (Theme)session.get(Theme.class, themeId);
		} finally {
			releaseSession(session);
		}
	}

	public List getUserFunctions(Integer id) {
		Session session = getSession();
		try {
			String sql =  searchUserRoleView+" where u.id="+id;
			List l = session.createSQLQuery(sql).list();
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public List getAllFunctions() {
		Session session = getSession();
		try {
			return session.createSQLQuery("select f.id from function f where f.function_active_flag='Y'").list();
		} finally {
			releaseSession(session);
		}
	}
	
	public List<Object[]> getAnalystByRoleId() {
		Session session = getSession();
		try {
			List<Object[]> l = (List<Object[]>)session.createSQLQuery(
					"select u.id,u.first_name,u.last_name from user as u left join user_role as ur on u.id = ur.user_id " +
			"where ur.role_id = 1 and u.system_user_flag = 'N' and u.rec_active_flag = 'Y' ").list();
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public List<Object[]> getApprover1ByRoleId() {
		Session session = getSession();
		try {
			List<Object[]> l = (List<Object[]>)session.createSQLQuery(
					"select u.id,u.first_name,u.last_name from user as u left join user_role as ur on u.id = ur.user_id " +
			"where ur.role_id = 2 and u.system_user_flag = 'N' and u.rec_active_flag = 'Y' and ur.rec_active_flag = 'Y' ").list();
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public List<Object[]> getApprover2ByRoleId() {
		Session session = getSession();
		try {
			List<Object[]> l = (List<Object[]>)session.createSQLQuery(
					"select u.id,u.first_name,u.last_name from user as u left join user_role as ur on u.id = ur.user_id " +
			"where ur.role_id = 3 and u.system_user_flag = 'N' and u.rec_active_flag = 'Y' and ur.rec_active_flag = 'Y' ").list();
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public List<Object[]> getApprover3ByRoleId() {
		Session session = getSession();
		try {
			List<Object[]> l = (List<Object[]>)session.createSQLQuery(
					"select u.id,u.first_name,u.last_name from user as u left join user_role as ur on u.id = ur.user_id " +
			"where ur.role_id = 4 and u.system_user_flag = 'N' and u.rec_active_flag = 'Y' and ur.rec_active_flag = 'Y' ").list();
			return l;
		} finally {
			releaseSession(session);
		}
	}

	public List<Object[]> getApprover4ByRoleId() {
		Session session = getSession();
		try {
			List<Object[]> l = (List<Object[]>)session.createSQLQuery(
					"select u.id,u.first_name,u.last_name from user as u left join user_role as ur on u.id = ur.user_id " +
			"where ur.role_id = 10 and u.system_user_flag = 'N' and u.rec_active_flag = 'Y' and ur.rec_active_flag = 'Y' ").list();
			return l;
		} finally {
			releaseSession(session);
		}
	}
	
}
