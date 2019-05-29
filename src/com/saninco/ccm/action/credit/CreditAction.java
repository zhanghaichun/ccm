/**
 * 
 */
package com.saninco.ccm.action.credit;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Credit;
import com.saninco.ccm.service.credit.ICreditDetailService;
import com.saninco.ccm.service.credit.ICreditService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchCreditVO;
import com.saninco.ccm.vo.SearchVO;

/**
 * This is page include file viewCreditDetails.action info --Chao.Liu
 * @author xinyu.Liu
 * 
 * @see pendingCredit is below --pengfei.wang
 * add getCreditAssignmentSearchTotalPageNo(); beijing 2010-4-16 Jian.Dong
 * add searchCreditAssignment(); beijing 2010-4-16 Jian.Dong
 */
public class CreditAction extends CcmActionSupport {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass()); 
	
	private ICreditService creditService;
	private IQuicklinkService quicklinkService;
	private SearchCreditVO searchCreditVO;
	
	private String quicklinkName;
	private String queryString;
	private String selVendorId;
	
	private List<String> titles;
	
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();	
	private List<MapEntryVO<String, String>> creditStatusList = new ArrayList<MapEntryVO<String, String>>();

	/*****************[Chao.Liu]***************************/
	private Integer creditId; // Accept creditId in the page
	private Integer disputeId; // Accept disputeId in the page
	private Integer reconcileId; // Accept reconcileId in the page
	private Double rbalance; // Accept rbalance in the page
	private Double amount; // Accept amount in the page
	private String notes; // Accept notes in the page
	private Credit credit = new Credit();
	private ICreditDetailService creditDetailService;
	private SearchVO v = new SearchVO();
	

	/**
	 * This is the action method redirect viewCreditDetailMainPanel.jsp
	 * It's take with the CreditDetaile data result page
	 * @return
	 * @throws Exception
	 */
	public String viewCreditDetails() throws Exception {
		logger.info("Enter method viewCreditDetails.");
		populateCreditDetailsLookupList();
		credit = creditDetailService.getCredit(creditId);
		logger.info("Exit method viewCreditDetails.");
		return SUCCESS;
	}
	
	private void populateCreditDetailsLookupList() throws BPLException {
		logger.info("Enter method populateCreditDetailsLookupList.");
		if(testWeb) populateCreditDetailsLookupListMockup();
		else{
			this.quicklinkList = quicklinkService.getUserQuicklinks(EnumQuicklinkType.CREDIT);
		}
		logger.info("Exit method populateCreditDetailsLookupList.");
	}
	
	private void populateCreditDetailsLookupListMockup(){
		logger.info("Enter method populateCreditDetailsLookupListMockup.");
		MapEntryVO<String, String> quicklink = new MapEntryVO<String, String>("1", "Bell Aliant");
		this.vendorList.add(quicklink);
		quicklink = new MapEntryVO<String, String>("2", "Bell Ontario");
		this.vendorList.add(quicklink);
		quicklink = new MapEntryVO<String, String>("3", "Metafore");
		this.vendorList.add(quicklink);
		logger.info("Exit method populateCreditDetailsLookupListMockup.");
	}
	
	/**
	 * Automatically generate creditSearchExcel.xls file server and turn it into a stream output to the client io
	 * 
	 */
	public String saveExcelBySearchCredit() throws Exception{
		logger.info("Exit method saveExcelBySearchCredit.");
		FileInputStream fis = null;
		try {
			String fileName = "creditSearchExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath("/excel.xls");
			String excelPath = creditService.createCreditToExcel(searchCreditVO, excelDirPath, titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Exception e) {
			logger.error("saveExcelBySearchCredit error: ", e);
		}
		logger.info("Exit method saveExcelBySearchCredit.");
		return null;
	}
	
	/**
	 * Search present page data total number 
	 * @return
	 * @throws Exception
	 */
	public String creditDetailsTotalNo() throws Exception {
		logger.info("Enter method getCreditSearchTotalPageNo.");
		try{
			String result = creditDetailService.getCreditSearchTotalPageNo(v, creditId);
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("creditDetailsTotalNo error: ", e);
		}
		logger.info("Exit method getPendingCreditSearchTotalPageNo.");
		return null;
	}
	/**
	 * Get the Dispute data info collection
	 * @return
	 */
	public String creditDetailsDisputeList(){
		logger.info("Enter method getCreditSearchTotalPageNo.");
		try{
			String result = creditDetailService.getDisputeList(v, creditId);
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("creditDetailsDisputeList error: ", e);
		}
		logger.info("Exit method getPendingCreditSearchTotalPageNo.");
		return null;
	}
	/**
	 * Get the Reconcile data info collection 
	 * @return
	 */
	public String creditDetailsReconcileList(){
		logger.info("Enter method creditDetailsReconcileList.");
		
		try{
			String result = creditDetailService.getReconcileList(v, creditId);
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("creditDetailsReconcileList error: ", e);
		}
		
		logger.info("Exit method creditDetailsReconcileList.");
		return null;
	}
	
	/**
	 * Clear one data in the Reconcile_Table
	 * @return
	 */
	public String cancelForReconcileByCredit(){
		logger.info("Enter method cancelForReconcileByCredit.");
		
		try{
			String result = creditDetailService.cancelForReconcileByCredit(creditId,disputeId,reconcileId,rbalance);
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("cancelForReconcileByCredit error: ", e);
		}
		
		logger.info("Exit method cancelForReconcileByCredit.");
		return null;
	}
	/**
	 * add one data in the Reconcile_Table
	 * @return
	 */
	public String addReconcileData(){
		logger.info("Enter method addReconcileData.");
		
		try{
			String result = 
				creditDetailService.addOneReconcileData(
						notes,creditId,disputeId,reconcileId,amount);
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("addReconcileData error: ", e);
		}
		
		logger.info("Exit method addReconcileData.");
		return null;
	}
	
	
	/**
	 * 
	 */
	public CreditAction() {
	}

	/* 
	 * Go to search page.
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		populateCreditSearchLookupList();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	private void populateCreditSearchLookupList() throws BPLException {
		logger.info("Enter method populateCreditSearchLookupList.");
		if(testWeb) populateCreditSearchLookupListMockup();
		else{
			this.vendorList = commonLookupService.getUserPreviledgedVendors();
			//this.quicklinkList = quicklinkService.getUserQuicklinks(EnumQuicklinkType.CREDIT);
			this.creditStatusList = commonLookupService.getCreditStatus();
		}
		logger.info("Exit method populateCreditSearchLookupList.");
	}
	
	private void populateCreditSearchLookupListMockup(){
		logger.info("Enter method populateCreditSearchLookupListMockup.");
		MapEntryVO<String, String> vendor = new MapEntryVO<String, String>("1", "Bell Aliant");
		this.vendorList.add(vendor);
		vendor = new MapEntryVO<String, String>("2", "Bell Ontario");
		this.vendorList.add(vendor);
		vendor = new MapEntryVO<String, String>("3", "Metafore");
		this.vendorList.add(vendor);
		MapEntryVO<String, String> ban = new MapEntryVO<String, String>("1", "345657");
		this.banList.add(ban);
		ban = new MapEntryVO<String, String>("2", "6677585");
		this.banList.add(ban);
		ban = new MapEntryVO<String, String>("3", "8867748");
		this.banList.add(ban);
		MapEntryVO<String, String> status = new MapEntryVO<String, String>("1", "New");
		this.creditStatusList.add(status);
		vendor = new MapEntryVO<String, String>("2", "Loaded");
		this.creditStatusList.add(status);
		vendor = new MapEntryVO<String, String>("3", "Closed");
		this.creditStatusList.add(status);
		logger.info("Exit method populateCreditSearchLookupListMockup.");
	}
	
	/**
	 * Do save query and return the saved query name displaying on the quick link column 
	 * in the left side panel.
	 * 
	 * @return
	 * @throws Exception
	 */
	public String saveCreditSearch() throws Exception {
		logger.info("Enter method saveCreditSearchQuery.");
		try{
			String result = saveSearchQueryCall();
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("saveCreditSearchQuery error: ", e);
		}
		logger.info("Exit method saveCreditSearchQuery.");
		return null;
	}
	
	private String saveSearchQueryCall(){
		logger.info("Enter method saveSearchQueryCall.");
		String s = null;
		if(testWeb) s = saveSearchQueryCallMockup();
		else {
			try {
				s = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.CREDIT);
			} catch (BPLException e) {
				logger.error("saveSearchQueryCall error: ", e);
				s = "{error:\"Save SearchQuery error: "+e.getMessage()+"\"}";
			} catch(RuntimeException e){
				logger.error(e);
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				s = "{error:\"Save SearchQuery error: "+bpe.getMessage()+"\"}";
			}
		}
		logger.info("Exit method saveSearchQueryCall.");
		return s;
	}
	
	private String saveSearchQueryCallMockup(){
		return "{id:1,name:\"credit search 1\"}";
	}
	
	/**
	 * Get user vendor list.
	 * @return
	 */
	public String getUserPreviledgedVendorList() throws Exception {
		logger.info("Enter method getUserPreviledgedVendorList.");
		String result = null;
		try{
			result = getUserPreviledgedVendorListCall();
		}catch(Exception e){
			logger.error("getUserPreviledgedVendorList error: ", e);
		}
		this.writeOutputStream(result);
		logger.info("Exit method getUserPreviledgedVendorList.");
		return null;
	}
	
	private String getUserPreviledgedVendorListCall(){
		logger.info("Enter method getUserPreviledgedVendorListCall.");
		String s = null;
		if(testWeb) s = "[{id:1,name:\"Bell Maliant\"},{id:2,name:\"Bell Ontario\"},{id:3,name:\"Metafore\"}]";
		else {
			try {
				s = this.commonLookupService.getUserPreviledgedVendorsJson();
			} catch (BPLException e) {
				logger.error("getUserPreviledgedVendorListCall error: ", e);
				s = "{error:\"getUserPreviledgedVendorListCall error: "+e.getMessage()+"\"}";
			}
		}
		logger.info("Exit method getUserPreviledgedVendorListCall.");
		return s;
	}
	
	/**
	 * Get Ban list by vendor id.
	 * @return BAN JSON string.
	 */
//	public String getBanListByVendorId() throws Exception {
//		logger.info("Enter method getBanListByVendorId.");
//		String result = null;
//		try{
//			result = getBanListByVendorIdCall();
//		}catch(Exception e){
//			logger.error("getBanListByVendorId error: ", e);
//		}
//		this.writeOutputStream(result);
//		logger.info("Exit method getBanListByVendorId.");
//		return null;
//	}
//	private String getBanListByVendorIdCall(){
//		logger.info("Enter method getBanListByVendorIdCall.");
//		String s = null;
//		if(testWeb) s = "[{id:1,no:\"345657\"},{id:2,no:\"6677585\"},{id:3,no:\"8867748\"}]";
//		else {
//			try {
//				s = this.commonLookupService.getUserPreviledgedBansJsonStrByVendorId(new Integer(this.selVendorId));
//			} catch (BPLException e) {
//				logger.error("getBanListByVendorIdCall error: ", e);
//				s = "{error:\"getBanListByVendorIdCall error: "+e.getMessage()+"\"}";
//			}
//		}
//		logger.info("Exit method getBanListByVendorIdCall.");
//		return s;
//	}

	/**
	 * Delete By Chao.Liu On The 10/08/25 PM
	 * Do search credit action.
	 * @return
	 * @throws Exception
	 */
//	public String searchCredit() throws Exception {
//		logger.info("Enter method searchCredit.");
//		try{
//			String result = searchCreditServiceCall();
//			this.writeOutputStream(result);	
//		}catch(Exception e){
//			logger.error("searchCredit error: ", e);
//		}
//		logger.info("Exit method searchCredit.");
//		return null;
//	}
//	
//	private String searchCreditServiceCall(){
//		logger.info("Enter method searchCreditServiceCall.");
//		String s = null;
//		if(testWeb) s = searchCreditServiceCallMockup();
//		else {
//			try {
//				s = creditService.searchCredit(searchCreditVO);
//			} catch (BPLException e) {
//				logger.error("searchCredit error: ", e);
//				s = "{error:\"Search credit error: "+e.getMessage()+"\"}";
//			}
//		}
//		logger.info("Exit method searchCreditServiceCall.");
//		return s;
//	}
	
	/**
	 * Do search user Credit Assignment action.
	 */
	public String searchCreditAssignment() throws Exception {
		logger.info("Enter method searchCreditAssignment.");
		String result;
		try {
			result = creditService.searchCreditAssignment(searchCreditVO);
		} catch (BPLException e) {
			logger.error("search CreditAssignment error: ", e);
			result = "{error:\"Search CreditAssignment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchCreditAssignment.");
		return null;
	}
	/**
	 * Get credit search total result number and page.
	 * @return
	 * @throws Exception
	 */
	public String getCreditAssignmentSearchTotalPageNo() throws Exception{
		logger.info("Enter method getCreditAssignmentSearchTotalPageNo.");
		String result;
		try {
			result = creditService.getCreditAssignmentSearchTotalPageNo(searchCreditVO);
		} catch (BPLException e) {
			logger.error("getCreditAssignmentSearchTotalPageNo error: ", e);
			result = "{error:\"getCreditAssignmentSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getCreditAssignmentSearchTotalPageNo.");
		return null;
	}
	/**
	 * Delete By Chao.Liu On The 10/08/25 PM
	 * Get credit search total result number and page.
	 * @return
	 * @throws Exception
	 */
//	public String getCreditSearchTotalPageNo() throws Exception{
//		logger.info("Enter method getCreditSearchTotalPageNo.");
//		try{
//			String result = getCreditSearchTotalPageNoServiceCall();
//			this.writeOutputStream(result);	
//		}catch(Exception e){
//			e.printStackTrace();
//			logger.error("getCreditSearchTotalPageNo error: ", e);
//		}
//		logger.info("Exit method getCreditSearchTotalPageNo.");
//		return null;
//	}
//	private String getCreditSearchTotalPageNoServiceCall(){
//		logger.info("Enter method getCreditSearchTotalPageNoServiceCall.");
//		String s = null;
//		if(testWeb) s = "{totalResultNo:4,totalPageNo:1}";
//		else {
//			try {
//				s = creditService.getCreditSearchTotalPageNo(searchCreditVO);
//			} catch (BPLException e) {
//				logger.error("getCreditSearchTotalPageNo error: ", e);
//				s = "{error:\"getCreditSearchTotalPageNo error: "+e.getMessage()+"\"}";
//			}
//		}
//		logger.info("Exit method getCreditSearchTotalPageNoServiceCall.");
//		return s;
//	}
	
	private String searchCreditServiceCallMockup() {
		return "{begin:1,end:4," +
				"data:[{id:1,no:\"INV3414859\",vendor:\"Bell Aliant\",ban:\"30092944\",date:\"2009/06/18\",due:\"2009/07/18\",total:\"$1,196.37\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}," +
				"{id:2,no:\"INV3411085\",vendor:\"Bell Aliant\",ban:\"30092944\",date:\"2009/03/18\",due:\"2009/04/18\",total:\"$482.58\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}," +
				"{id:3,no:\"INV3412591\",vendor:\"Bell Aliant\",ban:\"30092944\",date:\"2009/04/18\",due:\"2009/05/18\",total:\"$715.99\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}," +
				"{id:4,no:\"INV3413728\",vendor:\"Bell Aliant\",ban:\"30092944\",date:\"2009/05/18\",due:\"2009/06/18\",total:\"$951.73\",status:\"Loaded\",n:\"N\",c:\"N\",d:\"N\",o:\"N\"}]}";
	}
	
	/**
	 * ***************************************************************
	 * @author wangpengfei
	 * create pending 
	 * */
	
	//pendingCredit.js, jump to it
	public String PendingsearchCredit() throws Exception {
		logger.info("Enter method searchCredit.");
		try{
			String result = pendingCreditServiceCall();
			this.writeOutputStream(result);	
		}catch(Exception e){
			logger.error("searchCredit error: ", e);
		}
		logger.info("Exit method searchCredit.");
		return null;
	}
	
	//Connection to PendingsearchCredit
	private String pendingCreditServiceCall(){
		logger.info("Enter method searchCreditServiceCall.");
		String s = null;
			try {
				s = creditService.pendingCredit(searchCreditVO);
			} catch (BPLException e) {
				logger.error("searchInvoice error: ", e);
				s = "{error:\"Search invoice error: "+e.getMessage()+"\"}";
			}
		logger.info("Exit method searchCreditServiceCall.");
		return s;
	}
	
	//paging
	public String getPendingCreditSearchTotalPageNo() throws Exception{
		logger.info("Enter method getCreditSearchTotalPageNo.");
		try{
			String result = getPendingPageNoServiceCall();
			this.writeOutputStream(result);	
		}catch(Exception e){
			e.printStackTrace();
			logger.error("getCreditSearchTotalPageNo error: ", e);
		}
		logger.info("Exit method getPendingCreditSearchTotalPageNo.");
		return null;
	}
	
	//Connectionget to PendingCreditSearchTotalPageNo
	private String getPendingPageNoServiceCall(){
		logger.info("Enter method getPendingPageNoServiceCall.");
		String t = null;
		if(testWeb) t = "{totalResultNo:4,totalPageNo:1}";
		else {
			try {
				t = creditService.getPendingCreditTotalPageNo(searchCreditVO);
			} catch (BPLException e) {	
				logger.error("getPendingPageNoServiceCall error: ", e);
				t = "{error:\"getPendingPageNoServiceCall error: "+e.getMessage()+"\"}";
			}
		}
		return t;
	}
	
	/*******************[CreditDetail Chao.Liu]**********************/
 

	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public SearchCreditVO getSearchCreditVO() {
		return searchCreditVO;
	}

	public void setSearchCreditVO(SearchCreditVO searchCreditVO) {
		this.searchCreditVO = searchCreditVO;
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

	public String getSelVendorId() {
		return selVendorId;
	}

	public void setSelVendorId(String selVendorId) {
		this.selVendorId = selVendorId;
	}

	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}

	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}

	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}

	public Logger getLogger() {
		return logger;
	}

	public List<MapEntryVO<String, String>> getCreditStatusList() {
		return creditStatusList;
	}

	public void setCreditStatusList(
			List<MapEntryVO<String, String>> creditStatusList) {
		this.creditStatusList = creditStatusList;
	}

	public ICreditService getCreditService() {
		return creditService;
	}

	public void setCreditService(ICreditService creditService) {
		this.creditService = creditService;
	}
	/*
	 * Getter And Setter
	 */
	/**
	 * 
	 */
	public Integer getCreditId() {
		return creditId;
	}
	public void setCreditId(Integer creditId) {
		this.creditId = creditId;
	}
	/**
	 * 
	 * @return
	 */
	public Credit getCredit() {
		return credit;
	}
	public void setCredit(Credit credit) {
		this.credit = credit;
	}
	/**
	 * 
	 * @return
	 */
	public ICreditDetailService getCreditDetailService() {
		return creditDetailService;
	}
	public void setCreditDetailService(ICreditDetailService creditDetailService) {
		this.creditDetailService = creditDetailService;
	}
	/**
	 * 
	 * @return
	 */
	public SearchVO getV() {
		return v;
	}
	public void setV(SearchVO v) {
		this.v = v;
	}
	/**
	 * 
	 * @return
	 */
	public Integer getDisputeId() {
		return disputeId;
	}
	public void setDisputeId(Integer disputeId) {
		this.disputeId = disputeId;
	}
	/**
	 * 
	 * @return
	 */
	public Integer getReconcileId() {
		return reconcileId;
	}
	public void setReconcileId(Integer reconcileId) {
		this.reconcileId = reconcileId;
	}
	/**
	 * 
	 * @return
	 */
	public Double getRbalance() {
		return rbalance;
	}
	public void setRbalance(Double rbalance) {
		this.rbalance = rbalance;
	}
	/**
	 * 
	 * @return
	 */
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/**
	 * 
	 * @return
	 */
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
}
