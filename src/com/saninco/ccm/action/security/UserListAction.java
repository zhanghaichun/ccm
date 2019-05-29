package com.saninco.ccm.action.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.security.IUserListService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchUserListVO;

/**
 * 
 * @author chao.Liu (Optimization of complete by xinyu.Liu)
 *
 */
public class UserListAction extends CcmActionSupport {
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
 
	private IQuicklinkService quicklinkService;
	private IUserListService userListService;
	
	private SearchUserListVO uvo;
	
	private String quicklinkName;
	private String queryString;
	
	private List<MapEntryVO<String, String>> backupUserList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> supervisorUserList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	
	public UserListAction(){
		
	}
	
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		this.backupUserList = commonLookupService.getCreatedBy();
		this.supervisorUserList  = commonLookupService.getCreatedBy();
		this.uvo = new SearchUserListVO();
		this.quicklinkList = quicklinkService.getUserQuicklinks(EnumQuicklinkType.User);
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	/**
	 * @author chao.Liu (searchUserList.js)
	 * Search User Total Number
	 * 
	 */
	public String doStarSearchUserListTotalNumber()throws Exception {
		logger.info("Enter method doStarSearchUserListTotalNumber.");
		String result = null;
		try{
			result = userListService.getUserListTotailNumber(uvo);
		}catch(Exception e){
			logger.error("doStarSearchUserListTotalNumber error: ", e);
			result = "{error:\"Search User List Total NO Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doStarSearchUserListTotalNumber.");
		return null;
	}

	/**
	 * @author chao.Liu (searchUserList.js)
	 * Search User List Info
	 * 
	 */
	public String doSearchUserList()throws Exception {
		logger.info("Enter method doSearchUserList.");
		String result = null;
		try{
			result = userListService.getUserList(uvo);
		}catch(Exception e){
			logger.error("doSearchUserList error: ", e);
			result = "{error:\"Search User List Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchUserList.");
		return null;
	}

	/**
	 * Save QuickLink
	 */
	public String doSaveSearchUser() throws Exception {
		logger.info("Enter method doSaveSearchUser.");
		String result = null;
		try{
			result = saveSearchQueryCall();
		}catch(Exception e){
			result = "{error:\"Save User Query Error!\"}";
			logger.error("doSaveSearchUser error: ", e);
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSaveSearchUser.");
		return null;
	}
	
	private String saveSearchQueryCall(){
		logger.info("Enter method saveSearchQueryCall.");
		String s = null;
		try{
			s = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.User);
		} catch (BPLException e) {
			logger.error("saveSearchQueryCall error: ", e);
			s = "{error:\"Save SearchQuery error: "+e.getMessage()+"\"}";
		} catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			s = "{error:\"Save SearchQuery error: "+bpe.getMessage()+"\"}";
		}
		logger.info("Exit method saveSearchQueryCall.");
		return s;
	}
	
	/**
	 * @author chao.Liu (searchUserList.js)
	 * Delete One User
	 * 
	 */
	public String doDeleteUser()throws Exception {
		logger.info("Enter method doDeleteUser.");
		String result = null;
		try{
			result = userListService.deleteUser(uvo);
		}catch(Exception e){
			logger.error("doDeleteUser error: ", e);
			result = "{error:\"Delete User Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doDeleteUser.");
		return null;
	}
	
	/**
	 * @author chao.Liu (searchUserList.js)
	 * Check Repeat User Or Delete User
	 * 
	 */
	public String isShowOrDel()throws Exception {
		logger.info("Enter method isShowOrDel.");
		String result = null;
		try{
			result = userListService.getUserListCount(uvo);
		}catch(Exception e){
			logger.error("isShowOrDel error: ", e);
			result = "{error:\"Show Or Delete Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method isShowOrDel.");
		return null;
	}
	
	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public List<MapEntryVO<String, String>> getBackupUserList() {
		return backupUserList;
	}

	public void setBackupUserList(
			List<MapEntryVO<String, String>> backupUserList) {
		this.backupUserList = backupUserList;
	}

	public List<MapEntryVO<String, String>> getSupervisorUserList() {
		return supervisorUserList;
	}

	public void setSupervisorUserList(
			List<MapEntryVO<String, String>> supervisorUserList) {
		this.supervisorUserList = supervisorUserList;
	}

	public SearchUserListVO getUvo() {
		return uvo;
	}

	public void setUvo(SearchUserListVO uvo) {
		this.uvo = uvo;
	}

	public IUserListService getUserListService() {
		return userListService;
	}

	public void setUserListService(IUserListService userListService) {
		this.userListService = userListService;
	}

	public String getQuicklinkName() {
		return quicklinkName;
	}

	public void setQuicklinkName(String quicklinkName) {
		this.quicklinkName = quicklinkName;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}

}
