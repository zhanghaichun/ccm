package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.UserDelegation;
import com.saninco.ccm.vo.ProfileVO;

public interface IUserDelegationDao {
	
	public List findMyDesignateUsers(int userId);
	//created by wei.su
	public long getNumberOfMyDesignate(ProfileVO profileVO,int userId);
	//created by wei.su
	public List<String> searchMyDesignate(ProfileVO profileVO,int userId);
	//created by wei.su
	public List getMyDesignateUsers();
	//created by wei.su
	public UserDelegation findById(java.lang.Integer id);
	//created by wei.su
	public void delete(UserDelegation persistentInstance);
	//created by wei.su
	public void save(UserDelegation transientInstance);
	//created by wei.su
	public UserDelegation merge(UserDelegation detachedInstance);
	
	public List<String> searchUserDelegationAssignment();
	
	public long findDelegationToUserId(String currentAnalystId);
	
	public List findMyWorkSparsUsers();

	public List findBBMyWorkSparsUsers();
	
	public UserDelegation getOneUserDelegation(ProfileVO profileVO);
	
	public List findMyWorkSparsSerUsers();
}
