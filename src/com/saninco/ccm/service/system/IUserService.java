package com.saninco.ccm.service.system;

import java.io.File;
import java.sql.Blob;
import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ProfileVO;

/**
 * @author Jian.Dong
 * */
public interface IUserService {
	
	/**
	 * save picture
	 * */
	public void saveLoginPicture(File fileData) throws BPLException;
	/**
	 * return user pictures json
	 * */
	public String getUserPicturesJson() throws BPLException;

	/**
	 * get picture by id 
	 * */
	public Blob getPicture(Integer pictureId) throws BPLException;

	/**
	 * delete picture
	 * */
	public String deleteUserPicture(Integer pictureId) throws BPLException;
	//created by wei.su
	//public String getUserDelegationList(ProfileVO profileVO) throws BPLException;
	//created by wei.su
	public String getUserDelegationSearchTotalPageNo(ProfileVO profileVO)throws BPLException;
	
	//created by wei.su
	//public ProfileVO getUserDelegationById(ProfileVO profileVO ,int id)throws BPLException;
	
	//created by wei.su
	public void deleteUserDelegation(ProfileVO profileVO)throws BPLException;
	
	//created by wei.su
	public String updateUserDelegation(ProfileVO profileVO)throws Exception;
	
	public String searchUserDelegation(ProfileVO profileVO) throws BPLException;
	
	
	public String searchUserDelegationAssignment(ProfileVO profileVO) throws BPLException;
	/**
	 * get user picture count
	 * */
	public Long getPictureCount() throws BPLException;
	/**
	 * Get User Login Picture Id List By UserId [Chao.Liu]
	 * @param id
	 * @return
	 */
	public String getUserLogonPictureIdList(Integer uid)throws BPLException;
	/**
	 * Get User Login Picture Id List By UserId [Chao.Liu]
	 * @param uname
	 * @return
	 * @throws BPLException
	 */
	public String getUserLogonPictureIdList(String uname)throws BPLException;
	public List<MapEntryVO<String, String>> getUsers() throws BPLException;
}
