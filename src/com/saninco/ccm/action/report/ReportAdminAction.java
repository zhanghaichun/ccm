/**
 * 
 */
package com.saninco.ccm.action.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.report.IReportAdminService;
import com.saninco.ccm.service.rtag.IRtagService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ViewReportAdminVO;

/**
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 * 
 */
public class ReportAdminAction extends CcmActionSupport {
	
	private static final long serialVersionUID = -7778316099624206431L;
	
	private final Logger logger = Logger.getLogger(this.getClass());

	private IRtagService rtagService;
	private IReportAdminService reportAdminService;

	private String rtagId;
	private String reportId;
	private String rtagRoleId;
	private String teamId;
	private String fromId;
	private String toId;
	private boolean isMuckup = false;

	private ViewReportAdminVO viewReportAdminVO;
	
	private List<MapEntryVO<String, String>> rtagsNameList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> rtagsList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> rolesList = new ArrayList<MapEntryVO<String, String>>();

	/**
	 * 
	 */
	public ReportAdminAction() {
	}

	/*
	 * Go to search page.
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		populatePaymentLookupData();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	public String updateTeam() throws Exception{
		logger.info("Enter method updateTeam.");
		String result = "{}";
		try {
			result = reportAdminService.updateTeam(teamId,fromId,toId);
		} catch (Exception e) {
			logger.error("updateTeam error: ", e);
			result = "{error:\"updateTeam error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method updateTeam.");
		return null;
	}
	
	public String addTeam() throws Exception{
		logger.info("Enter method addTeam.");
		String result = "{}";
		try {
			result = reportAdminService.addTeam(fromId,toId);
		} catch (Exception e) {
			logger.error("addTeam error: ", e);
			result = "{error:\"addTeam error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method addTeam.");
		return null;
	}

	/**
	 * Get quicklinkList and actionList and form data
	 */
	private void populatePaymentLookupData() throws BPLException {
		logger.info("Enter method populatePaymentLookupData.");
		this.rtagsList = commonLookupService.getRtagsNameList();
		this.rolesList = commonLookupService.getRole();
		if (isMuckup) {
			this.rtagsNameList = this.getRtagsNameListMuckup();
		} else {
			this.rtagsNameList = commonLookupService.getRtagsNameList();
		}
		logger.info("Exit method populatePaymentLookupData.");
	}

	/**
	 * @author chao.Liu (viewRtagReportAdmin.js)
	 * Search Reports Name List
	 * 
	 */
	public String searchReportsSelect() throws Exception {
		logger.info("Enter method searchReportsSelect.");
		String result = null;
		try {
			result = rtagService.getReportsOptionList(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("searchReportsSelect error: ", e);
			result = "{error:\"searchReportsSelect: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchReportsSelect.");
		return null;
	}
	
	/**
	 * @author chao.Liu (viewRtagReportAdmin.js)
	 * Search Tags Name List
	 * 
	 */
	public String searchTagsSelect() throws Exception {
		logger.info("Enter method searchTagsSelect.");
		String result = null;
		try {
			result = rtagService.getTagsNameList();
		} catch (Exception e) {
			logger.error("searchTagsSelect error: ", e);
			result = "{error:\"searchTagsSelect: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchTagsSelect.");
		return null;
	}

	/**
	 * @author chao.Liu (viewRtagReportAdmin.js)
	 * Save Report Name List
	 * 
	 */
	public String saveReports() throws Exception {
		logger.info("Enter method saveReports.");
		String result = null;
		try {
			result = rtagService.saveReports(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("saveReports error: ", e);
			result = "{error:\"saveReports: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method saveReports.");
		return null;
	}

	private List<MapEntryVO<String, String>> getRtagsNameListMuckup() {
		logger.info("Enter method getRtagsNameListMuckup.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		for (int i = 0; i < 5; i++) {
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i + "", "a" + i);
			l.add(m);
		}
		logger.info("Exit method getRtagsNameListMuckup.");
		return l;
	}

	/**
	 * doValidateUserReportFunction
	 */
	public String doValidateUserReportFunction() throws Exception {
		logger.info("Enter method doValidateUserReportFunction.");
		String result = "{data:false}";
		try {
			result = reportAdminService.doValidateUserReportFunction(reportId);
		} catch (Exception e) {
			logger.error("doValidateUserReportFunction error: ", e);
			result = "{error:\"doValidateUserReportFunction error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method doValidateUserReportFunction.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Revises a data in the rtag table
	 * 
	 */
	public String upReportTags() throws Exception {
		logger.info("Enter method upReportTags.");
		String result = "{}";
		try {
			reportAdminService.updateReportTags(rtagId, viewReportAdminVO);
		} catch (Exception e) {
			logger.error("upReportTags error: ", e);
			result = "{error:\"upReportTags error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method upReportTags.");
		return null;
	}

	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Deletes a data in the rtag table rtag.setRecActiveFlag("N");
	 * 
	 */
	public String delReportTags() throws Exception {
		logger.info("Enter method delReportTags.");
		String result = null;
		try {
			reportAdminService.deleteReportTags(rtagId);
			result = reportAdminService.splitList(commonLookupService.getRtagsNameList());
		} catch (Exception e) {
			logger.error("delReportTags error: ", e);
			result = "{error:\"delReportTags error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method delReportTags.");
		return null;
	}

	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Increases a data in the rtag table
	 * 
	 */
	public String addReportAdminTags() throws Exception {
		logger.info("Enter method addReportAdminTags.");
		String result = null;
		try {
			reportAdminService.addReportTags(viewReportAdminVO);
			result = reportAdminService.splitList(commonLookupService.getRtagsNameList());
		} catch (Exception e) {
			logger.error("addReportAdminTags error: ", e);
			result = "{error:\"addReportAdminTags error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method addReportAdminTags.");
		return null;
	}

	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Get the Delegation data
	 * 
	 */
	public String searchReportAdminTags() throws Exception {
		logger.info("Enter method searchReportAdminTags.");
		String result = null;
		try {
			result = reportAdminService.searchReportAdminTags(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("searchReportAdminTags error: ", e);
			result = "{error:\"searchReportAdminTags error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchReportAdminTags.");
		return null;
	}
	
	
	public String searchTeams() throws Exception {
		logger.info("Enter method searchTeams.");
		String result = null;
		try {
			result = reportAdminService.searchTeams(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("searchReportAdminTags error: ", e);
			result = "{error:\"searchTeams error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchTeams.");
		return null;
	}

	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Get the number of pages in the Report Admin Tags
	 * 
	 */
	public String getReportAdminTagsTotalPageNo() throws Exception {
		logger.info("Enter method getReportAdminTagsTotalPageNo.");
		String result = null;
		try {
			result = reportAdminService.getReportAdminTagsTotalPageNo(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("getReportAdminTagsTotalPageNo error: ", e);
			result = "{error:\"getReportAdminTagsTotalPageNo error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getReportAdminTagsTotalPageNo.");
		return null;
	}
	
	public String getTeamTotalPageNo() throws Exception {
		logger.info("Enter method getTeamTotalPageNo.");
		String result = null;
		try {
			result = reportAdminService.getTeamTotalPageNo(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("getTeamTotalPageNo error: ", e);
			result = "{error:\"getTeamTotalPageNo error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getReportAdminTagsTotalPageNo.");
		return null;
	}
	
	
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Revises a data in the rtagRole table
	 * 
	 */
	public String upReportTagsAndRoles() throws Exception {
		logger.info("Enter method upReportTagsAndRoles.");
		String result = "{}";
		try {
			reportAdminService.updateReportTagsAndRoles(rtagRoleId, viewReportAdminVO);
		} catch (Exception e) {
			logger.error("upReportTagsAndRoles error: ", e);
			result = "{error:\"upReportTagsAndRoles error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method upReportTagsAndRoles.");
		return null;
	}

	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Deletes a data in the rtagRole table rtagRole.setRecActiveFlag("N");
	 * 
	 */
	public String delReportTagsAndRoles() throws Exception {
		logger.info("Enter method delReportTagsAndRoles.");
		String result = "{}";
		try {
			reportAdminService.deleteReportTagsAndRoles(rtagRoleId);
		} catch (Exception e) {
			logger.error("delReportTagsAndRoles error: ", e);
			result = "{error:\"delReportTagsAndRoles error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method delReportTagsAndRoles.");
		return null;
	}
	
	public String delTeam() throws Exception {
		logger.info("Enter method delTeam.");
		String result = "{}";
		try {
			reportAdminService.delTeam(rtagRoleId);
		} catch (Exception e) {
			logger.error("delTeam error: ", e);
			result = "{error:\"delTeam error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method delTeam.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * The same data already exists.
	 * 
	 */
	public String reportAdminTagsAndRolesJudgementData() throws Exception {
		logger.info("Enter method reportAdminTagsAndRolesJudgementData.");
		String result = null;
		try {
			result = reportAdminService.judgementDataTagsAndRoles(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("reportAdminTagsAndRolesJudgementData error: ", e);
			result = "{error:\"reportAdminTagsAndRolesJudgementData error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method reportAdminTagsAndRolesJudgementData.");
		return null;
	}

	/**
	 * @author xinyu.Liu (viewReportAdmin.js) 
	 * Increases a data in the rtagRole table
	 * 
	 */
	public String addReportAdminTagsAndRoles() throws Exception {
		logger.info("Enter method addReportAdminTagsAndRoles.");
		String result = "{}";
		try {
			reportAdminService.addReportTagsAndRoles(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("addReportAdminTagsAndRoles error: ", e);
			result = "{error:\"addReportAdminTagsAndRoles error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method addReportAdminTagsAndRoles.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Get the Delegation data
	 * 
	 */
	public String searchReportAdminTagsAndRoles() throws Exception {
		logger.info("Enter method searchReportAdminTagsAndRoles.");
		String result = null;
		try {
			result = reportAdminService.searchReportAdminTagsAndRoles(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("searchReportAdminTagsAndRoles error: ", e);
			result = "{error:\"searchReportAdminTagsAndRoles error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchReportAdminTagsAndRoles.");
		return null;
	}

	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Get the number of pages in the Report Admin Tags
	 * 
	 */
	public String getReportAdminTagsAndRolesTotalPageNo() throws Exception {
		logger.info("Enter method getReportAdminTagsAndRolesTotalPageNo.");
		String result = null;
		try {
			result = reportAdminService.getReportAdminTagsAndRolesTotalPageNo(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("getReportAdminTagsAndRolesTotalPageNo error: ", e);
			result = "{error:\"getReportAdminTagsAndRolesTotalPageNo error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getReportAdminTagsAndRolesTotalPageNo.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * validation Tags Data
	 * 
	 */
	 public String validationTags() throws Exception {
        logger.info("Enter method validationTags.");                                  
        String result = "{data:false}";                                                         
        try{
        	boolean bo = reportAdminService.validationTagsData(viewReportAdminVO); 
            if(bo) result = "{data:true}";       
        }catch(Exception e){
            logger.error("validationTags error: ", e);
            result = "{error:\"validationTags error: "+e.getMessage()+"\"}";   
        }
        this.writeOutputStream(result);                                                                  
        logger.info("Exit method validationTags.");                                    
        return null;                                                                                            
    }
	 
	 /**
	  * @author xinyu.Liu (viewReportAdmin.js)
	  * association Delete Tags
	  * 
	  */
	public String associationDeleteTags() throws Exception {
		logger.info("Enter method associationDeleteTags.");
		 String result = "{}";
		try {
			reportAdminService.associationDeleteTagsInTagsAndRoles(viewReportAdminVO);
		} catch (Exception e) {
			logger.error("associationDeleteTags error: ", e);
			result = "{error:\"associationDeleteTags error: "+e.getMessage()+"\"}";  
		}
		this.writeOutputStream(result);  
		logger.info("Exit method associationDeleteTags.");
		return null;
	}

	public IReportAdminService getReportAdminService() {
		return reportAdminService;
	}

	public void setReportAdminService(IReportAdminService reportAdminService) {
		this.reportAdminService = reportAdminService;
	}

	public ViewReportAdminVO getViewReportAdminVO() {
		return viewReportAdminVO;
	}

	public void setViewReportAdminVO(ViewReportAdminVO viewReportAdminVO) {
		this.viewReportAdminVO = viewReportAdminVO;
	}

	public String getRtagId() {
		return rtagId;
	}

	public void setRtagId(String rtagId) {
		this.rtagId = rtagId;
	}

	public List<MapEntryVO<String, String>> getRtagsNameList() {
		return rtagsNameList;
	}

	public void setRtagsNameList(List<MapEntryVO<String, String>> rtagsNameList) {
		this.rtagsNameList = rtagsNameList;
	}
 
	public void setRtagService(IRtagService rtagService) {
		this.rtagService = rtagService;
	}

	public String getRtagRoleId() {
		return rtagRoleId;
	}

	public void setRtagRoleId(String rtagRoleId) {
		this.rtagRoleId = rtagRoleId;
	}

	public List<MapEntryVO<String, String>> getRtagsList() {
		return rtagsList;
	}

	public void setRtagsList(List<MapEntryVO<String, String>> rtagsList) {
		this.rtagsList = rtagsList;
	}

	public List<MapEntryVO<String, String>> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<MapEntryVO<String, String>> rolesList) {
		this.rolesList = rolesList;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
	}

	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}
	
	
}
