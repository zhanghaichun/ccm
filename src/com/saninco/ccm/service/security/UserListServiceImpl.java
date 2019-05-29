package com.saninco.ccm.service.security;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.User;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchUserListVO;

/**
 * 
 * @author chao.Liu (Optimization of complete by xinyu.Liu)
 *
 */
public class UserListServiceImpl implements IUserListService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	private IUserDao userDao;
	
	public UserListServiceImpl(){
		
	}
	
	/**
	 * @author chao.Liu (searchUserList.js)
	 * Get User List Totail Number 
	 * 
	 */
	public String getUserListTotailNumber(SearchUserListVO uvo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get User List Totail Number.", uvo));
		StringBuffer sb = new StringBuffer();
		try {
			long count = userDao.getUserListTotailNumber(uvo);
			sb.append(uvo.getTotalPageNoJson(count));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author chao.Liu (searchUserList.js)
	 * Get User List Info 
	 * 
	 */
	public String getUserList(SearchUserListVO uvo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get User List Info.", uvo));
		StringBuffer sb = new StringBuffer();
		try {
			List<String> list = userDao.getUserList(uvo);
			sb.append(uvo.getListJsonCompatible(list));
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author chao.Liu (searchUserList.js)
	 * Update User RecActiveFlag Value to "N" 
	 * 
	 */
	public String deleteUser(SearchUserListVO uvo) {
		logger.info(CcmFormat.formatEnterLog("Update User RecActiveFlag Value to 'N'.", uvo));
		StringBuffer sb = new StringBuffer();
		if(uvo.getSuId() != null) userDao.updateUSByUserId(uvo);
		try {
			User u = userDao.getUser(uvo.getUid());
			u.setModifiedBy(SystemUtil.getCurrentUserId());
			u.setModifiedTimestamp(new Date());
			u.setRecActiveFlag("N");
			userDao.updateUser(u);
			sb.append("{error:false}");
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			sb.append("{error:\"Delete is failure.\"}");
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author chao.Liu (searchUserList.js)
	 * Get user List Count By User Id 
	 * 
	 */
	public String getUserListCount(SearchUserListVO uvo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get user List Count By User Id.", uvo));
		StringBuffer sb = new StringBuffer();
		try {
			Integer count = userDao.getUserCountByUS(uvo);
			if(count > 0){
				sb.append("{uc:\"show\",uid:"+uvo.getUid()+",unames:[");
				List<String> suls = userDao.getUserIdAndName(uvo);
				for(int i=0; i<suls.size(); i++){
					if(i != 0){sb.append(",");}
					sb.append(suls.get(i));
				}
				sb.append("]}");
			}else{
				sb.append("{uc:\"del\",uid:"+uvo.getUid()+"}");
			}
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			sb.append("{error:\"getUserListCount is failure.\"}");
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	
}
