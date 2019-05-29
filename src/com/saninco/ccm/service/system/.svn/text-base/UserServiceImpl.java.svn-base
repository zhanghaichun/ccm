package com.saninco.ccm.service.system;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Hibernate;

import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.dao.IUserDelegationDao;
import com.saninco.ccm.dao.IUserLoginPictureDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.UserDelegation;
import com.saninco.ccm.model.UserLoginPicture;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ProfileVO;

public class UserServiceImpl implements IUserService {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IUserLoginPictureDao userLoginPictureDao;
	private IUserDao userDao;
	private IUserDelegationDao userDelegationDao;
	public IUserDelegationDao getUserDelegationDao() {
		return userDelegationDao;
	}

	public void setUserDelegationDao(IUserDelegationDao userDelegationDao) {
		this.userDelegationDao = userDelegationDao;
	}

	public IUserLoginPictureDao getUserLoginPictureDao() {
		return userLoginPictureDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserLoginPictureDao(IUserLoginPictureDao userLoginPictureDao) {
		this.userLoginPictureDao = userLoginPictureDao;
	}
	
	public void saveLoginPicture(File fileData) throws BPLException {
		logger.info("Enter method saveLoginPicture.");
		InputStream is = null;
		try {
			is = new FileInputStream(fileData);
			UserLoginPicture ulp = new UserLoginPicture();
			Blob img = Hibernate.createBlob(is);
			ulp.setCreatedBy(SystemUtil.getCurrentUserId());
			ulp.setCreatedTimestamp(new Date());
			ulp.setFileContent(img);
			ulp.setUser(SystemUtil.getCurrentUser());
//			ulp.setRecActiveFlag("Y");
			userLoginPictureDao.save(ulp);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					logger.error(CcmFormat.formatErrorLog(e));
				}
			}
		}
		logger.info("Exit method saveLoginPicture.");
	}

	public String getUserPicturesJson() throws BPLException {
		logger.info("Enter method saveLoginPicture.");
		StringBuilder sb = new StringBuilder();
		try {
			List list = (List)userLoginPictureDao.findByProperty("user", userDao.loadUser(SystemUtil.getCurrentUserId()));
			sb.append("[");
			for(Object o : list){
				UserLoginPicture ulp = (UserLoginPicture)o;
				sb.append(ulp.getId());
				sb.append(",");
			}
			if(sb.length() > 1)sb.deleteCharAt(sb.length()-1);
			sb.append("]");
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method saveLoginPicture.");
		return sb.toString();
	}
	
	public Blob getPicture(Integer id) throws BPLException {
		logger.info("Enter method getPictureStream.");
		Blob img = null;
		try {
			UserLoginPicture ulp = userLoginPictureDao.findById(id);
			img = (ulp == null)?null:ulp.getFileContent();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getPictureStream.");
		return img;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public String deleteUserPicture(Integer pictureId) throws BPLException {
		logger.info("Enter method saveLoginPicture.");
		String result = null;
		try {
			userLoginPictureDao.delete(pictureId);
			result = "{success : 'success'}";
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method saveLoginPicture.");
		return result;
	}
	
	
	/**
	 * Get userdelegation list
	 * @param profileVO
	 * @return
	 * @throws BPLException
	 */
	public String searchUserDelegation(ProfileVO profileVO)throws BPLException {
		logger.info("Enter method searchReconcile.");
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = userDelegationDao.searchMyDesignate(profileVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(list!=null&&list.size()>0){
			sb.append("{begin:");
			sb.append(profileVO.getStartIndex()+1);
			sb.append(",end:");
			int size = list.size();
			sb.append(profileVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				if(i!=0) sb.append(",");
				sb.append(list.get(i).toString());
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info("Exit method searchReconcile.");
		return sb.toString();
	}
	
	public List<MapEntryVO<String, String>> getUsers() throws BPLException {
		logger.info("Enter method getUsers.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List<User> iList = null;
		try {
			iList = userDao.findDeligationUser();
			for(User i : iList){
				MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), ""+i.getFirstName()+" "+i.getLastName()+"");
				if(!(m.getValue().equals("system"))){
					l.add(m);
				}
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getUsers.");
		return l;
	}
	


	
//	//created by wei.su
//	public String getUserDelegationList(ProfileVO profileVO) throws BPLException
//	{
//		logger.info("Enter method searchCreditVO.");
//		StringBuffer sb = new StringBuffer();
//		List<String> list = null;
//		try {
//			list = userDelegationDao.searchMyDesignate(profileVO, SystemUtil.getCurrentUserId());
//		} catch (RuntimeException e) {
//			logger.error(CcmFormat.formatErrorLog(e));
//			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
//			throw bpe;
//		}
//		if(list!=null&&list.size()>0){
//			sb.append("{begin:");
//			sb.append(profileVO.getStartIndex()+1);
//			sb.append(",end:");
//			int size = list.size();
//			sb.append(profileVO.getStartIndex()+size);
//			sb.append(",data:[");
//			for(int i = 0; i<size; i++){
//				if(i!=0) sb.append(",");
//				sb.append(list.get(i).toString());
//			}
//			sb.append("]");
//		}else{
//			sb.append("{error:\"failed to query.\"");
//		}
//		sb.append("}");
//		logger.info("Exit method searchReconcileVO.");
//		return sb.toString();
//	}
	
	/***
	 * created by wei.su
	 * get UserDelegation Search Total Page No
	 */
	public String getUserDelegationSearchTotalPageNo(ProfileVO profileVO)throws BPLException
	{
		
		logger.info(CcmFormat.formatEnterLog("Enter method getUserDelegationSearchTotalPageNo.",profileVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = userDelegationDao.getNumberOfMyDesignate(profileVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)profileVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author wei.su
	 * @param profileVO
	 * search UserDelegation Assignment
	 */
	public String searchUserDelegationAssignment(ProfileVO profileVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method searchUserDelegationAssignment.", profileVO));
		String s;
		List<String> list = null;
		try {
			list = userDelegationDao.searchUserDelegationAssignment();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		s = profileVO.getListJson(list);
		logger.info(CcmFormat.formatExitLog());
		return s;
	}
	
	/**
	 * update userdelegation 
	 */
	//created by wei.su
	public String updateUserDelegation(ProfileVO profileVO)throws Exception
	{
		logger.info("Enter method updateUserDelegation.");
		// ProfileVO profileVO;
		Date statrDate = new Date();
		Date endDate = new Date();
		Date now = new Date();
		statrDate = CcmFormat.pase(profileVO.getFromDate(),"yyyy/MM/dd");
		endDate = CcmFormat.pase(profileVO.getToDate(), "yyyy/MM/dd");
		if(statrDate.compareTo(endDate)>0)
			return "To Date is not mor than From Date";
		if((now.compareTo(statrDate))>1){
			return "From Date is not more than Now";
		}
		if(now.compareTo(endDate)>0)
			return "To Date is not more than Now";
		if (profileVO.getFlagOfEdit() == 0) {
			UserDelegation userDelegation = new UserDelegation();
			userDelegation.setCreatedBy(SystemUtil.getCurrentUserId());
			userDelegation.setCreatedTimestamp(new Date());
			userDelegation.setEndDate(endDate);
			userDelegation.setStartDate(statrDate);
			userDelegation.setRecActiveFlag("Y");
			userDelegation.setModifiedBy(SystemUtil.getCurrentUserId());
			userDelegation.setModifiedTimestamp(new Date());
			userDelegation.setUserByFromUserId(userDao.getUser(SystemUtil
					.getCurrentUserId()));
			userDelegation.setUserByToUserId(userDao.getUser(profileVO
					.getDelegateToUserId().intValue()));
			try {
				userDelegationDao.save(userDelegation);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(
						ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
		}
		else
		{
			UserDelegation userDelegation = userDelegationDao.findById(profileVO.getUserDelegationId().intValue());
			User user = userDao.findById(profileVO.getDelegateToUserId().intValue());
			userDelegation.setUserByToUserId(user);
			userDelegation.setStartDate(statrDate);
			userDelegation.setEndDate(endDate);
			try {
				userDelegationDao.merge(userDelegation);			
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(
						ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			 
		}
		logger.info("Exit method updateUserDelegation.");
		return "null";
	}
	

	public Long getPictureCount() throws BPLException {
		Long l = userLoginPictureDao.findByCountProperty("user",userDao.loadUser(SystemUtil.getCurrentUserId()));
		return l;
	}
	
	public void deleteUserDelegation(ProfileVO profileVO)throws RuntimeException
	{
		logger.info(CcmFormat.formatEnterLog("Enter method deleteUserDelegation.", profileVO));
		
		try {

			UserDelegation Delegation=userDelegationDao.findById(profileVO.getUserDelegationId().intValue());

			Delegation.setRecActiveFlag("N");
			userDelegationDao.merge(Delegation);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			 
		}
		logger.info(CcmFormat.formatExitLog());
		
	}
	
	/**
	 * Get User Login Picture Id List By UserId [Chao.Liu]
	 * @param id
	 * @return
	 */
	public String getUserLogonPictureIdList(Integer uid)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getUserLogonPictureIdList.", ""));
		StringBuilder sb = new StringBuilder();
		try {
			List<String> list = (List)userLoginPictureDao.getPicIdByUid(uid);
			
			if(list == null || list.size() < 1){
				sb.append("{isNull:true}");
			}else{
				sb.append("[");
				
				int isAddComma = 0;
				for(String s : list){
					sb.append(s);
					if(isAddComma < (list.size()-1)){
						sb.append(",");
					}
					isAddComma++;
				}
				sb.append("]");
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * Get User Login Picture Id List By UserId [Chao.Liu]
	 * @param uname
	 * @return
	 * @throws BPLException
	 */
	public String getUserLogonPictureIdList(String uname)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getUserLogonPictureIdList.", ""));
		StringBuilder sb = new StringBuilder();
		try {
			User u = userDao.getUser(uname);
			if(u != null){
				List<String> list = (List)userLoginPictureDao.getPicIdByUid(u.getId());
				
				if(list == null || list.size() < 1){
					sb.append("{isNull:true}");
				}else{
					sb.append("[");
					
					int isAddComma = 0;
					for(String s : list){
						sb.append(s);
						if(isAddComma < (list.size()-1)){
							sb.append(",");
						}
						isAddComma++;
					}
					sb.append("]");
				}
			}else{
				sb.append("{isNull:true}");
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			sb.append("{isNull:true}");
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
}
