package com.saninco.ccm.action.profile;

import java.io.File;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.system.IUserService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ProfileVO;

/**
 * profile action class.
 * 
 * @author Jian.Dong  (Optimization of complete by xinyu.Liu)
 * 
 */
public class ProfileAction extends CcmActionSupport {

	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	private IQuicklinkService quicklinkService;
	
	private ProfileVO profileVO;
	private File fileData;
	private String fileDataFileName;
	private IUserService userService;
	private Integer pictureId;
	private Integer uid;
	private String uname;
	
	private List<String> fileNameList = new ArrayList<String>();
	private List<MapEntryVO<String, String>> userList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();

	public ProfileAction() {
		fileNameList.add("bmp");
		fileNameList.add("png");
		fileNameList.add("gif");
		fileNameList.add("jpeg");
		fileNameList.add("jpg"); 
	}
	
	/**
	 * to profile page
	 * */
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		userList = userService.getUsers();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	/**
	 * upload picture
	 * */
	public String uploadLoginPicture() throws Exception {
		logger.info("Enter method uploadLoginPicture.");
		String result = INPUT;
		try {
			if(valiFileData() && validataFileCount()){
				userService.saveLoginPicture(fileData);
				fileData.deleteOnExit();
				result = SUCCESS;
			}
		} catch (RuntimeException e) {
			logger.error("uploadLoginPicture error:", e);
			result = INPUT;
		}
		logger.info("Exit method uploadLoginPicture.");
		return result;
	}

	/**
	 * validata file object
	 * */
	private boolean valiFileData() {
		logger.info("Enter method valiFileData.");
		if(fileData == null || !fileData.exists()){
			addActionError(getText("page.upload.img.error.file.data.null"));
			return false;
		} 
		if(fileData.length() > 1024*1024*10){
			addActionError(getText("struts.messages.error.file.too.large"));
			return false;
		}
		int index = fileDataFileName.indexOf(".");
		String an = fileDataFileName.substring(index+1, fileDataFileName.length()).toLowerCase();
		if(index == -1 || !fileNameList.contains(an)){
			addActionError(getText("struts.messages.error.content.type.not.allowed"));
			return false;
		}
		logger.info("Exit method valiFileData.");
		return true;
	}
	
	/**
	 * validata user image count
	 * */
	private boolean validataFileCount() throws BPLException {
		logger.info("Enter method validataFileCount.");
		Long count = userService.getPictureCount();
		if(count < 5) return true;
		addActionError(getText("page.upload.img.error.count.outOfMax"));
		logger.info("Exit method validataFileCount.");
		return false;
	}

	/**
	 * get user pictures id json
	 * */
	public String getUserPictures() throws Exception {
		logger.info("Enter method getUserPictures.");
		String result = null;
		try {
			result = userService.getUserPicturesJson();
		} catch (BPLException e) {
			logger.error("getUserPictures error: ", e);
			result = "{error:\"getUserPictures error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getUserPictures.");
		return null;
	}
	
	/**
	 * delete picture
	 * */
	public String deleteUserPicture() throws Exception {
		logger.info("Enter method deleteUserPicture.");
		String result = null;
		try {
			result = userService.deleteUserPicture(pictureId);
		} catch (BPLException e) {
			logger.error("deleteUserPicture error: ", e);
			result = "{error:\"deleteUserPicture error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method deleteUserPicture.");
		return null;
	}
	
	/**
	 * show picture bu id
	 * */
	public String showUserPicture() throws Exception {
		logger.info("Enter method showUserPicture.");
		try {
			Blob b = userService.getPicture(pictureId);
			if(b!=null)this.writeBlobImage(b);
		} catch (BPLException e) {
			logger.error("showUserPicture error: ", e);
		}
		logger.info("Exit method showUserPicture.");
		return null;
	}

	/**
	 * Do search userDelegation action.
	 * @return
	 * @throws Exception
	 */
	public String searchUserDelegation() throws Exception {
		logger.info("Enter method searchUserDelegation.");
		String result = null;
		try{
			result = userService.searchUserDelegation(profileVO);
		}catch(Exception e){
			logger.error("searchUserDelegation error: ", e);
			result = "{error:\"searchUserDelegation error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchUserDelegation.");
		return null;
	}
	
	/**
	 * Get userDelegation search total result number and page.
	 * @return
	 * @throws Exception
	 */
	public String getUserDelegationTotalPageNo() throws Exception{
		logger.info("Enter method getUserDelegationTotalPageNo.");
		String result = null;
		try{
			result = userService.getUserDelegationSearchTotalPageNo(profileVO);
		}catch(Exception e){
			logger.error("getUserDelegationTotalPageNo error: ", e);
			result = "{error:\"getUserDelegationTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getUserDelegationTotalPageNo.");
		return null;
	}
	
	/**A delegation of the users and the related
	 * @return
	 * @throws Exception
	 */
	public String saveUserDelegation() throws Exception {
		logger.info("Enter method saveUserDelegation.");
		String result = null;
		try{
			result = userService.updateUserDelegation(profileVO);	
		}catch(Exception e){
			logger.error("saveUserDelegation error: ", e);
			result = "{error:\"saveUserDelegation error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveUserDelegation.");
		return null;
	}
	
	/**
	 * Get User Login Picture Id List By UserName [Chao.Liu]
	 * @return
	 * @throws Exception
	 */
	public String getUserLoginPicByUname()throws Exception {
		logger.info("Enter method getUserLoginPicByUname.");
		String result = null;
		try{
			result = userService.getUserLogonPictureIdList(uname);
		}catch(Exception e){
			logger.error("getUserLoginPicByUname error: ", e);
			result = "{error:\"getUserLoginPicByUname error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getUserLoginPicByUname.");
		return null;
	}
	
	/**Delete a delegation of the users and the relate
	 * @return
	 * @throws Exception
	 */
	public String deleteUserDelegation() throws Exception {
		logger.info("Enter method deleteUserDelegation.");
		try{
			userService.deleteUserDelegation(profileVO);	
		}catch(Exception e){
			logger.error("deleteUserDelegation error: ", e);
		}
		logger.info("Exit method deleteUserDelegation.");
		return null;
	}
	
	/**
	 * When click edit from database records and get a page bind! 
	 * @param profileVO
	 * @return
	 * @throws Exception
	 */
	public String searchUserDelegationAssignment() throws Exception {
		logger.info("Enter method searchInvoiceAssignment.");
		String result = null;
		try {
			result = userService.searchUserDelegationAssignment(profileVO);
		} catch (BPLException e) {
			logger.error("searchUserDelegationAssignment error: ", e);
			result = "{error:\"searchUserDelegationAssignment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchUserDelegationAssignment.");
		return null;
	}

	public List<MapEntryVO<String, String>> getUserList() {
		return userList;
	}

	public void setUserList(List<MapEntryVO<String, String>> userList) {
		this.userList = userList;
	}

	public ProfileVO getProfileVO() {
		return profileVO;
	}

	public void setProfileVO(ProfileVO profileVO) {
		this.profileVO = profileVO;
	}

	public File getFileData() {
		return fileData;
	}

	public void setFileData(File fileData) {
		this.fileData = fileData;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public Integer getPictureId() {
		return pictureId;
	}

	public void setPictureId(Integer pictureId) {
		this.pictureId = pictureId;
	}

	public IUserService getUserService() {
		return userService;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getUname() {
		return uname;
	}

	public void setUname(String uname) {
		this.uname = uname.trim();
	}

	public String getFileDataFileName() {
		return fileDataFileName;
	}

	public void setFileDataFileName(String fileDataFileName) {
		this.fileDataFileName = fileDataFileName;
	}

	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}
	
}
