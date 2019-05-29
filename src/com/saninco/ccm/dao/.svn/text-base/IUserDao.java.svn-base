/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import org.springframework.security.userdetails.UserDetailsService;

import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.Theme;
import com.saninco.ccm.model.User;
import com.saninco.ccm.vo.SearchUserListVO;

/**
 * 
 * UserDao type. (Optimization of complete by xinyu.Liu)
 *
 */
public interface IUserDao extends UserDetailsService {
	
	public User getUser(String userName);
	
	public void updateUser(User user);
	
	public User findById(java.lang.Integer id);
	
	public List<User> findUsersByEmail(String email);
	
	public Integer addUser(User user);	
	
	public void deleteUser(User user);
	
	public User loadUser(Integer userId); 
	
	public User getUser(Integer userId); 
	
	public List<Role> getAllRoles();
	
	public User getUser(String firstName, String lastName);
	
	public List<Object[]> getAnalyst();
	
	public List<User> getUsers(String userName);
	
	public List findAll();	
	
	public List<User> findDeligationUser() ;
	
	public Integer getUserListTotailNumber(SearchUserListVO uvo);
	
	public List<String> getUserList(SearchUserListVO uvo);
	
	public List<String> getUserIdAndName(SearchUserListVO uvo);
	
	public Integer getUserCountByUS(SearchUserListVO uvo);
	
	public void updateUSByUserId(SearchUserListVO uvo);
	
	public List<User> getUsersBySupervisorUserId(int supervisorUserId);
	
	public Integer findAttachmentPointId(String tableName, Integer id);
	
	public void updateAttachmentPointId(String tableName, Integer id, Integer newId);
	
	public List<Theme> findThemes();
	
	public Theme getTheme();

	public Theme findThemesById(Integer themeId);
	
	public Theme updateUserTheme(Integer themeId,Integer randowThemeId);
	
	public Theme updateUserRandomTheme(Integer themeId,Integer userId);

	public List getUserFunctions(Integer id);
	
	public List getAllFunctions();
	
	public List<Object[]> getAnalystByRoleId();
	
	public List<Object[]> getApprover1ByRoleId();
	
	public List<Object[]> getApprover2ByRoleId();
	
	public List<Object[]> getApprover3ByRoleId();
	
	public List<Object[]> getApprover4ByRoleId();
	
}
