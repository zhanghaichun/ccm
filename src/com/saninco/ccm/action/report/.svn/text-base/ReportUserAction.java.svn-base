/**
 * 
 */
package com.saninco.ccm.action.report;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.filter.AllGranted;
import com.saninco.ccm.filter.AnyGranted;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.report.IReportExcelService;
import com.saninco.ccm.service.report.IReportUserService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchReportUserVO;

/**
 * @author Chao.Liu (Optimization of complete by xinyu.Liu)
 * 
 */
public class ReportUserAction extends CcmActionSupport {
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	private IQuicklinkService quicklinkService;
	
	private IReportUserService reportUserService;
	private IReportExcelService reportExcelService;
	private SearchReportUserVO rvo = new SearchReportUserVO();
	private boolean isMockup = false;
	private String reportId;
	private String createdReportId;
	private List<MapEntryVO<String, String>> anaylystUserList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> scoaCodeList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> chargeTypeList = new ArrayList<MapEntryVO<String, String>>();
	
	public ReportUserAction() {}
	/**
	 * By hongtao 2011-09-20
	 * @return
	 * @throws Exception
	 */
	public String deleteReport()throws Exception{
		logger.info("Enter method deleteReport.");
		String result = "";
		try{
			result = reportUserService.deleteReport(createdReportId);
		}
		catch(BPLException e){
			logger.error("getMyWorkspace error: ", e);
			result = "{error:\"deleteReport error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method deleteReport.");
		return null;
	
	}
	
	/**
	 * Get Report List Totail Number
	 * @return
	 * @throws Exception
	 */
	public String getReportTotalNOTags()throws Exception {
			logger.info("Enter method doSearchRoleTotalNO.");
			String result = "";
			try {
				if (isMockup) {
					result = this.getReportTotalNOTagsMockup();
				} else {
					result = reportUserService.getReportNameAndColorTotailNO(rvo);
				}
			}catch (BPLException e) {
				logger.error("getMyWorkspace error: ", e);
				result = "{error:\"doSearchRoleTotalNO error: " + e.getMessage() + "\"}";
			}
			this.writeOutputStream(result);	
			logger.info("Exit method doSearchRoleTotalNO.");
			return null;
	}
	
	private String getReportTotalNOTagsMockup(){
		return "{totalResultNo:4,totalPageNo:1}";
	}
	
	/**
	 * Search Report List Info
	 * @return
	 * @throws Exception
	 */
	public String searchReportTags()throws Exception {
			logger.info("Enter method doSearchRoleTotalNO.");
			String result = "";
			try {
				if(isMockup){
					result = this.searchReportTagsMockup();
				}else{
					result = reportUserService.searchReportNameAndColorList(rvo);
				}
			}catch (BPLException e) {
				logger.error("getMyWorkspace error: ", e);
				result = "{error:\"doSearchRoleTotalNO error: " + e.getMessage() + "\"}";
			}
			this.writeOutputStream(result);	
			logger.info("Exit method doSearchRoleTotalNO.");
			return null;
	}
	private String searchReportTagsMockup(){
		return "{begin:1,end:4,data:[" +
				"{id:1,rname:\"report_1\",color:\"#ffccff\",alevel:2}" +
				",{id:2,rname:\"report_2\",color:\"#ff0000\",alevel:3}" +
				",{id:3,rname:\"report_3\",color:\"#bbbbbb\",alevel:3}" +
				",{id:4,rname:\"report_4\",color:\"#cccfff\",alevel:2}" +
				"]}";
	}
	
	/**
	 * Save One new created Report
	 * @return
	 * @throws Exception
	 */
	public String saveCreatedReport()throws Exception {
		logger.info("Enter method saveCreatedReport.");
		String result = "";
		if(isMockup){
			result = "{error:\"This is Action Info!\"}";
		}else{
			result = reportUserService.saveCreatedReport(rvo);
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveCreatedReport.");
		return null;
	}
	
	/**
	 * Check Name Is Repeat
	 * @return
	 * @throws Exception
	 */
	public String checkNameIsRepeat()throws Exception {
		logger.info("Enter method saveCreatedReport.");
		String result = "";
		if(isMockup){
			result = "{error:\"This is No Repeat!\"}";
		}else{
			result = reportUserService.getNameIsRepeat(rvo);
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveCreatedReport.");
		return null;
	}
	
	/**
	 * @Author Chao.Liu Date: Nov 3, 2010
	 * @Belong To 
	 * @return
	 * @throws Exception
	 */
	public String getVendorNameByBanId()throws Exception {
		logger.info("Enter method saveCreatedReport.");
		String result = "";
		try {
			if (isMockup) {
				result = "{error:\"Get Vendor Name Error!\"}";
			} else {
				result = reportUserService.getVendorNameByBanId(rvo);
			}
		} catch (BPLException e) {
			logger.error("getMyWorkspace error: ", e);
			result = "{error:\"Get Vendor Name Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveCreatedReport.");
		return null;
	}

	@AnyGranted(value="FUNCTION_3100,FUNCTION_3200")
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		this.anaylystUserList = commonLookupService.getCreatedBy();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		this.banList = commonLookupService.getAllBans();
		this.scoaCodeList = commonLookupService.getAllAccountCode();
		this.chargeTypeList = commonLookupService.getChargeType();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	/**
	 * pengfei.wang
	 * @return
	 * @throws Exception
	 * Report Name query statistics
	 */
	public String getViewReportTotalNOTags()throws Exception {
		logger.info("Enter method getViewReportTotalNOTags.");
		String result = "";
		try {
			if (isMockup) {
				result = this.getReportTotalNOTagsMockup();
			} else {
				result = reportUserService.getViewZReportNameAndColorTotailNo(rvo);
			}
		}catch (BPLException e) {
			logger.error("getMyWorkspace error: ", e);
			result = "{error:\"getViewReportTotalNOTags error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getViewReportTotalNOTags.");
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Name query
	 */
	public String searchViewReportTags()throws Exception {
		logger.info("Enter method searchViewReportTags.");
		String result = "";
		try {
			if(isMockup){
				result = this.searchReportTagsMockup();
			}else{
				result = reportUserService.serchViewReportNameColorList(rvo);
			}
		}catch (BPLException e) {
			logger.error("getMyWorkspace error: ", e);
			result = "{error:\"searchViewReportTags error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchViewReportTags.");
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * @throws Exception
	 * Jump to getViewNameTotalNOTags performs all inquiries, conditions of the paging inquires research method.-return
	 */
	public String getViewNameTotalNOTags()  throws Exception {
		logger.info("Enter method getViewNameTotalNOTags.");
		String result = null;
		try{
			rvo.setReportId(Integer.parseInt(reportId));
			result = reportUserService.getViewNameTotalPageNo(rvo);
		}catch(Exception e){
			logger.error("getViewNameTotalNOTags error: ", e);
			result = "{error:\"getViewNameTotalNOTags error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getViewNameTotalNOTags.");
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Jump to viewSearchName performs all inquiries, conditions inquire method.-return
	 */
	public String viewSearchName()  throws Exception {
		logger.info("Enter method viewSearchName.");
		String result = null;
		try{
			rvo.setReportId(Integer.parseInt(reportId));
			result = reportUserService.searcgViewNameList(rvo);
		}catch(Exception e){
			logger.error("viewSearchName error: ", e);
			result = "{error:\"viewSearchName error: " + e.getMessage() + "\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method viewSearchName.");
		return null;
	}
	
	/**
	 * doReportParameterFunction
	 */
	public String doReportParameterFunction() throws Exception {
		logger.info("Enter method doReportParameterFunction.");
		String result = "{data:false}";
		try {
			result = reportUserService.doReportParameterFunction(reportId);
		} catch (Exception e) {
			logger.error("doReportParameterFunction error: ", e);
			result = "{error:\"doReportParameterFunction error: "+e.getMessage()+"\"}";   
		}
		this.writeOutputStream(result);
		logger.info("Exit method doReportParameterFunction.");
		return null;
	}
	
	public SearchReportUserVO getRvo() {
		return rvo;
	}

	public void setRvo(SearchReportUserVO rvo) {
		this.rvo = rvo;
	}

	public List<MapEntryVO<String, String>> getAnaylystUserList() {
		return anaylystUserList;
	}

	public void setAnaylystUserList(
			List<MapEntryVO<String, String>> anaylystUserList) {
		this.anaylystUserList = anaylystUserList;
	}

	public void setReportUserService(IReportUserService reportUserService) {
		this.reportUserService = reportUserService;
	}

	public IReportExcelService getReportExcelService() {
		return reportExcelService;
	}

	public void setReportExcelService(IReportExcelService reportExcelService) {
		this.reportExcelService = reportExcelService;
	}

	public String getReportId() {
		return reportId;
	}

	public void setReportId(String reportId) {
		this.reportId = reportId;
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

	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}

	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}

	public String getCreatedReportId() {
		return createdReportId;
	}

	public void setCreatedReportId(String createdReportId) {
		this.createdReportId = createdReportId;
	}
	public List<MapEntryVO<String, String>> getScoaCodeList() {
		return scoaCodeList;
	}
	public void setScoaCodeList(List<MapEntryVO<String, String>> scoaCodeList) {
		this.scoaCodeList = scoaCodeList;
	}
	public List<MapEntryVO<String, String>> getChargeTypeList() {
		return chargeTypeList;
	}
	public void setChargeTypeList(List<MapEntryVO<String, String>> chargeTypeList) {
		this.chargeTypeList = chargeTypeList;
	} 
	
}
