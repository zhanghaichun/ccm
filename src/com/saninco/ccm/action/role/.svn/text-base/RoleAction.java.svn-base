package com.saninco.ccm.action.role;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.role.IRoleService;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.vo.SearchRoleVO;
/**
 * 
 * @author Chao.Liu (Optimization of complete by xinyu.Liu)
 * 
 */
public class RoleAction extends CcmActionSupport {
	
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private SearchRoleVO rvo = new SearchRoleVO();
	
	private boolean isMockup = false;
	
	private IRoleService roleService;
	
	/**
	 * 
	 */
	public RoleAction() {
	}

	@Override
	public String exec() throws Exception {
		return SUCCESS;
	}
	
	/**
	 * Search Role List Total Number
	 * @return
	 * @throws Exception
	 */
	public String doSearchRoleTotalNO()throws Exception {
		logger.info("Enter method doSearchRoleTotalNO.");
		String result = "";
		try {
			if(isMockup){
				result = doSearchRoleTotalNOMockup();
			}else{
				result = roleService.getSearchRoleTotalNO(rvo);
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Role Totail NO. Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchRoleTotalNO.");
		return null;
	}
	
	/**
	 * Search Role List Info
	 * @return
	 * @throws Exception
	 */
	public String doSearchRole()throws Exception {
		logger.info("Enter method doSearchRole.");
		String result = "";
		try {
			if(isMockup){
				result = doSearchRoleMockup();
			}else{
				result = roleService.getSearchRole(rvo);
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Role List Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchRole.");
		return null;
	}
	
	/**
	 * Delete One Role Info
	 * @return
	 * @throws Exception
	 */
	public String deleteRole()throws Exception {
		logger.info("Enter method deleteRole.");
		String result = "";
		try {
			if(isMockup){
				result = deleteRoleMockup();
			}else{
				result = roleService.deleteRole(rvo.getId());
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Delete Role Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method deleteRole.");
		return null;
	}
	
	/**
	 * Search Function List Info
	 * @return
	 * @throws Exception
	 */
	public String searchFunction()throws Exception {
		logger.info("Enter method searchFunction.");
		String result = "";
		try {
			if(isMockup){
				result = searchFunctionMockup();
			}else{
				result = roleService.getFunctionList(rvo);
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Search Function Error!\"}";
		}	
		this.writeOutputStream(result);
		logger.info("Exit method searchFunction.");
		return null;
	}
	
	/**
	 * Update or add one role
	 * @return
	 * @throws Exception
	 */
	public String saveName()throws Exception {
		logger.info("Enter method saveName.");
		String result = "";
		try {
			if(isMockup){
				result = saveNameMockup();
			}else{
				result = roleService.saveRoleName(rvo);
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Add Or Update Role Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveName.");
		return null;
	}
	
	/**
	 * Add Function Or Delete Function
	 * @return
	 * @throws Exception
	 */
	public String addFunOrDel()throws Exception {
		logger.info("Enter method addFunOrDel.");
		String result = "";
		try {
			if(isMockup){
				result = addFunOrDelMockup();
			}else{
				result = roleService.addFunOrDel(rvo);
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Add Or Delete Function Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method addFunOrDel.");
		return null;
	}
	
	private String doSearchRoleTotalNOMockup(){
		return "{totalResultNo:4,totalPageNo:1}";
	}
	
	private String doSearchRoleMockup(){
		return "{begin:1,end:4,data:[" +
				"{id:1,rname:\"aaa\"}" +
				",{id:2,rname:\"bbb\"}" +
				",{id:3,rname:\"ccc\"}" +
				",{id:4,rname:\"ddd\"}" +
				"]}";
	}
	
	private String deleteRoleMockup(){
		return "{error:\"This is Mockup Info!\"}";
	}
	
	private String searchFunctionMockup(){
		if(rvo.getIsSave() != null){
			return "{s1:[" +
				"{key:1,value:\"aaa\"}," +
				"{key:3,value:\"ccc\"}" +
				"],s2:[" +
				"{key:4,value:\"ddd\"}," +
				"{key:7,value:\"ggg\"}" +
				"]}";
		}else{
			return "{s1:[" +
				"{key:1,value:\"aaa\"}," +
				"{key:3,value:\"ccc\"}" +
				"]}";
		}
		
	}
	
	private String saveNameMockup(){
		if(rvo.getIsSave() != null){
			return "{error:\"Updata is success!\"}";
		}else{
			return "{error:\"Add is success!\"}";
		}
	}
	
	private String addFunOrDelMockup(){
		if(rvo.getIsAddOrDel().equals("Add")){
			return "{error:\"Add Function is success!\"}";
		}else{
			return "{error:\"Delete Function is success!\"}";
		}
	}
	
	public SearchRoleVO getRvo() {
		return rvo;
	}

	public void setRvo(SearchRoleVO rvo) {
		this.rvo = rvo;
	}

	public void setRoleService(IRoleService roleService) {
		this.roleService = roleService;
	}
}
