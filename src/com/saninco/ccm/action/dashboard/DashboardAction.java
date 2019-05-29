package com.saninco.ccm.action.dashboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.model.bi.BIBudget;
import com.saninco.ccm.model.bi.BIProduct;
import com.saninco.ccm.model.bi.BIQuoteProduct;
import com.saninco.ccm.model.bi.BITimePeriod;
import com.saninco.ccm.service.dashboard.IDashboardService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.vo.DashboardVO;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.dashboard.UserDashboardVO;

/**
 * @see Modified By Chao.Liu On The 2010/09/11 AM [Add Or Update Logger]
 * @author Chao.Liu
 *
 */
public class DashboardAction extends CcmActionSupport {
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	private IQuicklinkService quicklinkService;
	private IDashboardService dashboardService;
	private DashboardVO dvo;
	private String timePeriodListJson;
	private String timePeriodListQuoteJson;
	private String timePeriodListGlobalJson;
	private String productListJson;
	private String productLabelListJson;
	private String provinceListJson;
	private String budgetListJson;
	private String budgetOwnerListJson;
	private String vendorListJson;
	private String auditReferenceListJson;
	private String userDashboardJson;
	private String globalDashboardControlListJson;
	private String dashboardModuleListJson;
	private String quoteProductListJson;
	private String quoteProductLabelListJson;
	private String quoteAgingListJson;
	private String quoteIssuedStatusListJson;
	private String quoteNetTypeListJson;
	private String quoteVendorListJson;
	private String quoteVendorResposeStatusListJson;
	private Integer userDashboardModuleId;
	private Integer userDashboardId;
	private String controlKey;
	private Object controlValue[];
	private String viewMode;
	private String controlFilterKey;
	private Integer dashboardModuleId;
	private String userDashboardName;
	private String moduleShowPercentFlag;
	private String userDashboardModuleName;
	private Boolean isMobile = false;
	private Integer sortNo;
	
	private String dashboardNameSort;
	private String dateCreatedSort;
	private Integer currentPage;
	private Integer pageRecords;
	private Boolean isFirstDateCreatedSort;
	
	private UserDashboardVO userDashboard; 
	public String pageHTML;
	
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	

	private List<Map<String, Object>> dashboardListInfo = new ArrayList<Map<String, Object>>();
	
	/**
	 * @return the dashboardName
	 */
	public String getDashboardNameSort() {
		return dashboardNameSort;
	}

	/**
	 * @param dashboardName the dashboardName to set
	 */
	public void setDashboardNameSort(String dashboardNameSort) {
		this.dashboardNameSort = dashboardNameSort;
	}

	/**
	 * @return the dateCreated
	 */
	public String getDateCreatedSort() {
		return dateCreatedSort;
	}

	/**
	 * @param dateCreated the dateCreated to set
	 */
	public void setDateCreatedSort(String dateCreatedSort) {
		this.dateCreatedSort = dateCreatedSort;
	}

	/**
	 * @return the currentPage
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	/**
	 * @return the pageRecords
	 */
	public Integer getPageRecords() {
		return pageRecords;
	}

	/**
	 * @param pageRecords the pageRecords to set
	 */
	public void setPageRecords(Integer pageRecords) {
		this.pageRecords = pageRecords;
	}
	
	
	/**
	 * @return the isFirstDateCreatedSort
	 */
	public Boolean getIsFirstDateCreatedSort() {
		return isFirstDateCreatedSort;
	}

	/**
	 * @param isFirstDateCreatedSort the isFirstDateCreatedSort to set
	 */
	public void setIsFirstDateCreatedSort(Boolean isFirstDateCreatedSort) {
		this.isFirstDateCreatedSort = isFirstDateCreatedSort;
	}

	public DashboardAction() {}
	
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		
		pageHTML = dashboardService.saveAndGetPageHtml();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		logger.info("Exit method exec.");
		return SUCCESS;
	}

	/**
	 * 查询Dashboard List 中所需要的数据。
	 * @return [description]
	 * @throws Exception     [description]
	 */
	public String searchDashboardListInfo() throws Exception {
		logger.info("Enter method searchDashboardListInfo.");
		
		dashboardListInfo = dashboardService.searchDashboardListInfo(dashboardNameSort, dateCreatedSort, currentPage, pageRecords);
		
		logger.info("Exit method searchDashboardListInfo.");
		
		if (isMobile) {
			return MOBILE;
		} else {
			return SUCCESS;
		}
		
	}

	/**
	 * 通过特定的查询条件和排序条件来查询列表信息。
	 * @return [description]
	 * @throws Exception     [description]
	 */
	public String searchDashboardListByConditions() throws Exception {
		logger.info("Enter method searchDashboardListByConditions.");
		String dashboardListItems = null;

		dashboardListItems = dashboardService.searchDashboardListByConditions(dashboardNameSort, dateCreatedSort, currentPage, pageRecords);

		
		this.writeOutputStream(dashboardListItems);
		logger.info("Exit method searchDashboardListByConditions.");
		
		return null;
		
	}

	/**
	 * 删除 dashboard 列表项。
	 * @return [description]
	 * @throws Exception     [description]
	 */
	public String deleteDashboardListItem() throws Exception {

		logger.info("Enter method deleteDashboardListItem.");
		String result = null;
		try{
			result = dashboardService.deleteDashboardListItem( userDashboardId );
		}catch(Exception e){
			logger.error("deleteDashboardListItem error: ", e);
			result = "{error:\"deleteDashboardListItem error: "+e.getMessage()+"\"}";
		}
			
		logger.info("Exit method deleteDashboardListItem.");

		return null;
	}
	
	/**
	 * 获得数据库中相关 dashboard list item 的总条数。
	 * @return
	 * @throws Exception
	 */
	public String getTotalRecordsNo() throws Exception {
		logger.info("Enter method getTotalRecordsNo.");
		String resultCount = null;
		try{
			Integer count = dashboardService.getTotalRecordsNo();
			// 保证返回的数据是JSON格式的。
			resultCount = "{\"totalCount\": "+ count +"}";
		}catch(Exception e){
			logger.error("getTotalRecordsNo error: ", e);
			resultCount = "{\"error\":\"getTotalRecordsNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(resultCount); // 直接向前台返回一个JSON类型的数据。
		logger.info("Exit method getTotalRecordsNo.");
		return null;
	}
	
	public String dashboard() throws Exception {
		logger.info("Enter method dashboard.");
		try{
			this.getDashboardFilterReference();
			userDashboard = dashboardService.getUserDashboard();
			userDashboardJson = JSONObject.fromObject(userDashboard).toString();
			
		} catch (Exception e) {
			logger.error("dashboard error: ", e);
		}
		
		logger.info("Exit method dashboard.");
		return SUCCESS;
	}
	
	public String addNewDashboard() throws Exception {
		
		logger.info("Enter method addNewDashboard.");
		try{
			this.getDashboardFilterReference();
			userDashboardId = dashboardService.addNewDashboard().getId();
		} catch (Exception e) {
			logger.error("addNewDashboard error: ", e);
		}
		
		logger.info("Exit method addNewDashboard.");
		return SUCCESS;
	}
	
	
	public void updateUserDashboardModuleMoveNewSort() throws Exception {
		
		logger.info("Enter method updateUserDashboardModuleMoveNewSort.");
		try{
			dashboardService.updateUserDashboardModuleMoveNewSort(userDashboardModuleId, sortNo);
		} catch (Exception e) {
			logger.error("updateUserDashboardModuleMoveNewSort error: ", e);
		}
		
		logger.info("Exit method updateUserDashboardModuleMoveNewSort.");
	}
	
	
	public void updateUserDashboardName() throws Exception {
		
		logger.info("Enter method updateUserDashboardName.");
		String result = null;
		try{
			result = "{\"data\": \""+dashboardService.updateUserDashboardName(userDashboardId, userDashboardName)+"\"}";
		} catch (Exception e) {
			logger.error("updateUserDashboardName error: ", e);
			result = "{\"error\":\"getChartDataByUserModuleId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method updateUserDashboardName.");
	}
	
	public void updateUserDashboardModuleShowPercent() throws Exception {
		
		logger.info("Enter method updateUserDashboardModuleShowPercent.");
		try{
			dashboardService.updateUserDashboardModuleShowPercent(userDashboardModuleId, moduleShowPercentFlag);
		} catch (Exception e) {
			logger.error("updateUserDashboardModuleShowPercent error: ", e);
		}
		logger.info("Exit method updateUserDashboardModuleShowPercent.");
	}
	
	
	public void updateUserDashboardModuleName() throws Exception {
		
		logger.info("Enter method updateUserDashboardModuleName.");
		String result = null;
		try{
			result = "{\"data\": \""+dashboardService.updateUserDashboardModuleName(userDashboardModuleId, userDashboardModuleName)+"\"}";
		} catch (Exception e) {
			logger.error("updateUserDashboardModuleName error: ", e);
			result = "{\"error\":\"updateUserDashboardModuleName error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method updateUserDashboardModuleName.");
	}
	
	public String showDashboardDetail() throws Exception {
		
		logger.info("Enter method showDashboardDetail.");
		try{
			this.getDashboardFilterReference();
			userDashboard = dashboardService.updateAndGetUserDashboardById(userDashboardId);
			userDashboardJson = JSONObject.fromObject(userDashboard).toString();
		} catch (Exception e) {
			logger.error("showDashboardDetail error: ", e);
			
		}
		
		logger.info("Exit method showDashboardDetail.");
		
		if (isMobile) {
			return MOBILE;
		} else {
			return SUCCESS;
		}
	}
	
	public String getChartDataByUserModuleId() throws Exception {
		logger.info("Enter method getChartDataByUserModuleId.");
		String result = null;
		try{
			result = "{\"data\": "+JSONArray.fromObject(this.dashboardService.getUserDashboardModuleData(this.userDashboardModuleId)).toString()+"}";
		}catch(Exception e){
			logger.error("getChartDataByUserModuleId error: ", e);
			result = "{\"error\":\"getChartDataByUserModuleId error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getChartDataByUserModuleId.");
		return null;
	}
	
	public String saveControlData() throws Exception {
		logger.info("Enter method saveControlData.");
		String result = null;
		try{
			result = "{\"data\": "+JSONObject.fromObject(this.dashboardService.saveControlData(this.userDashboardModuleId, controlKey, controlValue)).toString()+"}";
		}catch(Exception e){
			logger.error("saveControlData error: ", e);
			result = "{error:\"saveControlData error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method saveControlData.");
		return null;
	}
	
	public String changeUserDashboardViewMode() throws Exception {
		logger.info("Enter method changeUserDashboardViewMode.");
		try{
			this.dashboardService.updateUserDashboardViewMode(this.userDashboardModuleId, viewMode);
		}catch(Exception e){
			logger.error("changeUserDashboardViewMode error: ", e);
		}
		logger.info("Exit method changeUserDashboardViewMode.");
		return null;
	}
	
	public String removeUserDashboardViewMode() throws Exception {
		logger.info("Enter method removeUserDashboardViewMode.");
		try{
			this.dashboardService.updateAndDeleteUserDashboardModule(this.userDashboardModuleId);
		}catch(Exception e){
			logger.error("removeUserDashboardViewMode error: ", e);
		}
		logger.info("Exit method removeUserDashboardViewMode.");
		return null;
	}
	
	public String addNewDashboarModule() throws Exception {
		logger.info("Enter method addNewDashboarModule.");
		String result = null;
		try{
			result = "{\"data\": "+JSONObject.fromObject(this.dashboardService.addNewDashboarModule(this.userDashboardModuleId, dashboardModuleId)).toString()+"}";
		}catch(Exception e){
			logger.error("addNewDashboarModule error: ", e);
			result = "{error:\"addNewDashboarModule error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method addNewDashboarModule.");
		return null;
	}
	
	public String changeUserDashboardListby() throws Exception {
		logger.info("Enter method changeUserDashboardListby.");
		String result = null;
		try{
			result = "{\"data\": "+JSONObject.fromObject(this.dashboardService.updateUserDashboardListBy(userDashboardModuleId, controlKey, controlFilterKey)).toString()+"}";
		}catch(Exception e){
			logger.error("changeUserDashboardListby error: ", e);
			result = "{error:\"changeUserDashboardListby error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method changeUserDashboardListby.");
		return null;
	}
	
	/**
	 * Save Dashboard Position
	 * @return
	 * @throws Exception
	 */
	public String saveDashboardPosition()throws Exception {
		logger.info("Enter method saveDashboardPosition.");
		String result = "";
		try{
			result = dashboardService.saveDashboardPosition(dvo);
		}catch(Exception e){
			logger.error(CcmFormat.formatErrorLog(e));
			result = "{error:\"Save Dashboard Position Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveDashboardPosition.");
		return null;
	}
	/**
	 * User Time Scale
	 * @author Chao.Liu Date: Aug 28, 2010
	 * @return
	 * @throws Exception
	 */
	public String userInvoiceAtDashletDayChart() throws Exception {
		logger.info("Enter method InvoiceAtTransactionDayChart.");
		try {
			JFreeChart chart = dashboardService.getUserInvoiceAtDashletDayChart();
			response.setContentType("image/jpeg");
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 0.5f,chart,450,250,null);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
		}
		logger.info("Exit method InvoiceAtTransactionDayChart.");
		return null;
	}
	/**
	 * System Time Scale
	 * @author Chao.Liu Date: Aug 28, 2010
	 * @return
	 * @throws Exception
	 */
	public String invoiceAtDashletDayChart() throws Exception {
		logger.info("Enter method InvoiceAtTransactionDayChart.");
		try {
			JFreeChart chart = dashboardService.getInvoiceAtDashletDayChart();
			response.setContentType("image/jpeg");
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 0.5f,chart,450,250,null);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
		}
		logger.info("Exit method InvoiceAtTransactionDayChart.");
		return null;
	}
	/**
	 * System Time
	 * @author Chao.Liu Date: Aug 28, 2010
	 * @return
	 * @throws Exception
	 */
	public String invoiceAtTransactionDayChart() throws Exception {
		logger.info("Enter method InvoiceAtTransactionDayChart.");
		try {
			JFreeChart chart = dashboardService.getInvoiceAtTransactionDayChart();
			response.setContentType("image/jpeg");
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 0.5f,chart,450,250,null);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
		}
		logger.info("Exit method InvoiceAtTransactionDayChart.");
		return null;
	}
	/**
	 * User Time
	 * @author Chao.Liu Date: Aug 28, 2010
	 * @return
	 * @throws Exception
	 */
	public String userInvoiceAtTransactionDayChart() throws Exception {
		logger.info("Enter method InvoiceAtTransactionDayChart.");
		try {
			JFreeChart chart = dashboardService.getUserInvoiceAtTransactionDayChart();
			response.setContentType("image/jpeg");
			ChartUtilities.writeChartAsJPEG(response.getOutputStream(), 0.5f,chart,450,250,null);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
		}
		logger.info("Exit method InvoiceAtTransactionDayChart.");
		return null;
	}
	
	private void getDashboardFilterReference() {
		
		//timePeriodListJson = JSONArray.fromObject(dashboardService.getAllTimePeriod()).toString();  // 因为需要where条件 所以注释掉了默认  findAll 方式
		timePeriodListJson = JSONArray.fromObject(dashboardService.hqlFindTimePeriodList("unQuoteType")).toString();  
		timePeriodListQuoteJson = JSONArray.fromObject(dashboardService.hqlFindTimePeriodList("quoteType")).toString(); 
		timePeriodListGlobalJson = JSONArray.fromObject(dashboardService.hqlFindTimePeriodList("globalType")).toString(); 
		provinceListJson = JSONArray.fromObject(dashboardService.getAllProvince()).toString();
		vendorListJson = JSONArray.fromObject(dashboardService.getAllVendor()).toString();
		List<BIProduct> productList = dashboardService.getAllProduct();
		productListJson = JSONArray.fromObject(productList).toString();
		productLabelListJson = getProductLabelsByProducts(productList);
		auditReferenceListJson = JSONArray.fromObject(dashboardService.getAllAuditReference()).toString();
		List<BIBudget> budgetList = dashboardService.getAllBudget();
		budgetListJson = JSONArray.fromObject(budgetList).toString();
		budgetOwnerListJson = getBudgetOwnerJsonByBudgetList(budgetList);
		globalDashboardControlListJson = JSONArray.fromObject(dashboardService.getGlobalDashboardControl()).toString();
		dashboardModuleListJson = JSONArray.fromObject(dashboardService.getAllDashboardModule()).toString();
		quoteAgingListJson = JSONArray.fromObject(dashboardService.getAllQuoteAging()).toString();
		quoteIssuedStatusListJson = JSONArray.fromObject(dashboardService.getAllQuoteIssuedStatus()).toString();
		quoteNetTypeListJson = JSONArray.fromObject(dashboardService.getAllQuoteNetType()).toString();
		List<BIQuoteProduct> quoteProductList = dashboardService.getAllQuoteProduct();
		quoteProductListJson = JSONArray.fromObject(quoteProductList).toString();
		quoteProductLabelListJson = getQuoteProductLabelsByQuoteProducts(quoteProductList);
		quoteVendorListJson = JSONArray.fromObject(dashboardService.getAllQuoteVendor()).toString();
		quoteVendorResposeStatusListJson = JSONArray.fromObject(dashboardService.getAllQuoteVendorResponseStatus()).toString();
	}

	private String getBudgetOwnerJsonByBudgetList(List<BIBudget> budgetList) {
		Set<String> ownerSet = new TreeSet<String>(); 
		if (budgetList.size() > 0) {
			for (BIBudget b : budgetList) {
				if (b.getBudgetOwner() != null && b.getBudgetOwner().length() > 0) {
					ownerSet.add(b.getBudgetOwner());
				}
			}
		}
		return JSONArray.fromObject(ownerSet).toString();
	}
	
	private String getProductLabelsByProducts(List<BIProduct> productList) {
		Set<String> labelSet = new TreeSet<String>(); 
		if (productList.size() > 0) {
			for (BIProduct bp : productList) {
				if (bp.getFunctionLabel() != null && bp.getFunctionLabel().length() > 0)
					labelSet.add(bp.getFunctionLabel());
				if (bp.getGenerationLabel() != null && bp.getGenerationLabel().length() > 0)
					labelSet.add(bp.getGenerationLabel());
				if (bp.getMediaLabel() != null && bp.getMediaLabel().length() > 0)
					labelSet.add(bp.getMediaLabel());
			}
		}
		return JSONArray.fromObject(labelSet).toString();
	}
	
	private String getQuoteProductLabelsByQuoteProducts(List<BIQuoteProduct> productList) {
		Set<String> labelSet = new TreeSet<String>(); 
		if (productList.size() > 0) {
			for (BIQuoteProduct bp : productList) {
				if (bp.getFunctionLabel() != null && bp.getFunctionLabel().length() > 0)
					labelSet.add(bp.getFunctionLabel());
				if (bp.getGenerationLabel() != null && bp.getGenerationLabel().length() > 0)
					labelSet.add(bp.getGenerationLabel());
				if (bp.getMediaLabel() != null && bp.getMediaLabel().length() > 0)
					labelSet.add(bp.getMediaLabel());
			}
		}
		return JSONArray.fromObject(labelSet).toString();
	}
	
	
	
	public IDashboardService getDashboardService() { return dashboardService; } 
	public void setDashboardService(IDashboardService dashboardService) { this.dashboardService = dashboardService; } 
	public String getPageHTML() { return pageHTML; } 
	public void setPageHTML(String pageHTML) { this.pageHTML = pageHTML; } 
	public DashboardVO getDvo() { return dvo; } 
	public void setDvo(DashboardVO dvo) { this.dvo = dvo; }

	public IQuicklinkService getQuicklinkService() { return quicklinkService; } 
	public void setQuicklinkService(IQuicklinkService quicklinkService) { this.quicklinkService = quicklinkService; } 
	public List<MapEntryVO<String, String>> getQuicklinkList() { return quicklinkList; } 
	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) { this.quicklinkList = quicklinkList; }

	public String getTimePeriodListJson() {
		return timePeriodListJson;
	}

	public String getProductListJson() {
		return productListJson;
	}

	public String getProductLabelListJson() {
		return productLabelListJson;
	}
	
	public String getVendorListJson() {
		return vendorListJson;
	}

	public String getAuditReferenceListJson() {
		return auditReferenceListJson;
	}

	public UserDashboardVO getUserDashboard() {
		return userDashboard;
	}

	public String getUserDashboardJson() {
		return userDashboardJson;
	}

	public String getProvinceListJson() {
		return provinceListJson;
	}

	public Integer getUserDashboardModuleId() {
		return userDashboardModuleId;
	}

	public void setUserDashboardModuleId(Integer userDashboardModuleId) {
		this.userDashboardModuleId = userDashboardModuleId;
	}

	public List<Map<String, Object>> getDashboardListInfo() {
		return dashboardListInfo;
	}

	public void setDashboardListInfo(List<Map<String, Object>> dashboardListInfo) {
		this.dashboardListInfo = dashboardListInfo;
	}

	public Integer getUserDashboardId() {
		return userDashboardId;
	}

	public void setUserDashboardId(Integer userDashboardId) {
		this.userDashboardId = userDashboardId;
	}

	public String getGlobalDashboardControlListJson() {
		return globalDashboardControlListJson;
	}

	public String getControlKey() {
		return controlKey;
	}

	public void setControlKey(String controlKey) {
		this.controlKey = controlKey;
	}

	public Object[] getControlValue() {
		return controlValue;
	}

	public void setControlValue(Object[] controlValue) {
		this.controlValue = controlValue;
	}

	public void setViewMode(String viewMode) {
		this.viewMode = viewMode;
	}

	public void setControlFilterKey(String controlFilterKey) {
		this.controlFilterKey = controlFilterKey;
	}

	public void setDashboardModuleId(Integer dashboardModuleId) {
		this.dashboardModuleId = dashboardModuleId;
	}

	public void setUserDashboardName(String userDashboardName) {
		this.userDashboardName = userDashboardName;
	}

	public String getBudgetListJson() {
		return budgetListJson;
	}

	public String getBudgetOwnerListJson() {
		return budgetOwnerListJson;
	}

	public String getDashboardModuleListJson() {
		return dashboardModuleListJson;
	}

	public Boolean getIsMobile() {
		return isMobile;
	}

	public void setIsMobile(Boolean isMobile) {
		this.isMobile = isMobile;
	}

	public void setSortNo(Integer sortNo) {
		this.sortNo = sortNo;
	}

	public void setUserDashboardModuleName(String userDashboardModuleName) {
		this.userDashboardModuleName = userDashboardModuleName;
	}

	public void setModuleShowPercentFlag(String moduleShowPercentFlag) {
		this.moduleShowPercentFlag = moduleShowPercentFlag;
	}

	public String getQuoteProductListJson() {
		return quoteProductListJson;
	}

	public String getQuoteProductLabelListJson() {
		return quoteProductLabelListJson;
	}

	public String getQuoteAgingListJson() {
		return quoteAgingListJson;
	}

	public String getQuoteIssuedStatusListJson() {
		return quoteIssuedStatusListJson;
	}

	public String getQuoteNetTypeListJson() {
		return quoteNetTypeListJson;
	}

	public String getQuoteVendorListJson() {
		return quoteVendorListJson;
	}

	public String getQuoteVendorResposeStatusListJson() {
		return quoteVendorResposeStatusListJson;
	}

	public String getTimePeriodListQuoteJson() {
		return timePeriodListQuoteJson;
	}

	public void setTimePeriodListQuoteJson(String timePeriodListQuoteJson) {
		this.timePeriodListQuoteJson = timePeriodListQuoteJson;
	}

	public String getTimePeriodListGlobalJson() {
		return timePeriodListGlobalJson;
	}

	public void setTimePeriodListGlobalJson(String timePeriodListGlobalJson) {
		this.timePeriodListGlobalJson = timePeriodListGlobalJson;
	}
	
	

}
