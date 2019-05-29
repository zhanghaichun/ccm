/**
 * 
 */
package com.saninco.ccm.action.vendor;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.vendor.IVendorMainService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchVendorVo;

/**
 * This is page include file viewCreditDetails.action info --Chao.Liu
 * @author xinyu.Liu
 * 
 * @see pendingCredit is below --pengfei.wang
 * add getCreditAssignmentSearchTotalPageNo(); beijing 2010-4-16 Jian.Dong
 * add searchCreditAssignment(); beijing 2010-4-16 Jian.Dong
 */
public class VendorAction extends CcmActionSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6116016090321192405L;
	
	private SearchVendorVo svvo = new SearchVendorVo();
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private IVendorMainService vendorMainsService;
	private String vendorId;
	private String banId;
	private List<String> titles;
	private String excelName;
	
	/**
	 * 
	 */
	@Override
	public String exec() throws Exception {
		// TODO Auto-generated method stub
		this.vendorList = commonLookupService.getUserPreviledgedVendors();
		this.banList = commonLookupService.getSearchConditionBans();
		return SUCCESS;
	}
	
	public String getAllChargeTotalPageNo() throws Exception{
		logger.info("Enter method getAllChargeTotalPageNo.");
		String result;
		try{
			result = vendorMainsService.getAllChargeTotalPageNo(svvo);
		}catch(Exception e){
			logger.error("getAllChargeTotalPageNo error: ", e);
			result = "{error:\"getAllChargeTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getAllChargeTotalPageNo.");
		return null;
	}
	
	public String getAllCharges()  throws Exception {
		logger.info("Enter method getAllCharges.");
		String result;
		try{
			result = vendorMainsService.getAllChargesList(svvo);
		}catch(Exception e){
			logger.error("getTariffs error: ", e);
			result = "{error:\"getTariffs error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getAllCharges.");
		return null;
	}
	
	public String getRecurringChargeTotalPageNo() throws Exception{
		logger.info("Enter method getRecurringChargeTotalPageNo.");
		String result;
		try{
			result = vendorMainsService.getRecurringChargeTotalPageNo(svvo);
		}catch(Exception e){
			logger.error("getTariffsTotalPageNo error: ", e);
			result = "{error:\"getTariffsTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getRecurringChargeTotalPageNo.");
		return null;
	}
	
	public String getRecurringCharges()  throws Exception {
		logger.info("Enter method getRecurringCharges.");
		String result;
		try{
			result = vendorMainsService.getRecurringChargeList(svvo);
		}catch(Exception e){
			logger.error("getTariffs error: ", e);
			result = "{error:\"getTariffs error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getRecurringCharges.");
		return null;
	}
	
	public String getSearchSortProposalTotalPageNo() throws Exception{
		logger.info("Enter method getSearchDescriptionTotalPageNo.");
		String result;
		try{
			result = vendorMainsService.getSearchSortProposalTotalPageNo(svvo);
		}catch(Exception e){
			logger.error("getTariffsTotalPageNo error: ", e);
			result = "{error:\"getTariffsTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getSearchDescriptionTotalPageNo.");
		return null;
	}
	
	public String getSearchSortProposalTable()  throws Exception {
		logger.info("Enter method getSearchDescription.");
		String result;
		try{
			result = vendorMainsService.getSearchSortProposalTable(svvo);
		}catch(Exception e){
			logger.error("getTariffs error: ", e);
			result = "{error:\"getTariffs error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getSearchDescription.");
		return null;
	}
	
	public String getSearchPaymentTotalPageNo() throws Exception{
		logger.info("Enter method getSearchPaymentTotalPageNo.");
		String result;
		try{
			result = vendorMainsService.getSearchPaymentTotalPageNo(svvo);
		}catch(Exception e){
			logger.error("getTariffsTotalPageNo error: ", e);
			result = "{error:\"getTariffsTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getSearchPaymentTotalPageNo.");
		return null;
	}
	
	public String getSearchPaymentTable()  throws Exception {
		logger.info("Enter method getSearchPaymentTable.");
		String result;
		try{
			result = vendorMainsService.getSearchPaymentTable(svvo);
		}catch(Exception e){
			logger.error("getTariffs error: ", e);
			result = "{error:\"getTariffs error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getSearchPaymentTable.");
		return null;
	}
	
	/**
	 * @author mingyang.li (viewVendorDetail.js)
	 * 
	 */
	public String saveExcelByAllCharges() throws Exception{
		logger.info("Exit method saveExcelBySearchInvoice.");
		FileInputStream fis = null;
		try {
			String fileName = "AllChargesExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = vendorMainsService.createAllChargesToExcel(svvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySearchInvoice error: ", e);
		}
		logger.info("Exit method saveExcelBySearchInvoice.");
		return null;
	}
	
	/**
	 * @author mingyang.li (viewVendorDetail.js)
	 * 
	 */
	public String saveExcelByRecurringCharge() throws Exception{
		logger.info("Exit method saveExcelByRecurringCharge.");
		FileInputStream fis = null;
		try {
			String fileName = "RecurringChargeExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = vendorMainsService.createRecurringChargeToExcel(svvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByRecurringCharge error: ", e);
		}
		logger.info("Exit method saveExcelByRecurringCharge.");
		return null;
	}
	
	/**
	 * @author mingyang.li (viewVendorDetail.js)
	 * 
	 */
	public String saveExcelBySortProposal() throws Exception{
		logger.info("Exit method saveExcelBySortProposal.");
		FileInputStream fis = null;
		try {
			String fileName = "invoiceSearchExcel.xls";
			if( excelName != null && excelName != ""){
				fileName = excelName+".xls";
			}
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = vendorMainsService.createSortProposalToExcel(svvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySortProposal error: ", e);
		}
		logger.info("Exit method saveExcelBySortProposal.");
		return null;
	}
	
	/**
	 * @author mingyang.li (viewVendorDetail.js)
	 * 
	 */
	public String saveExcelByPayment() throws Exception{
		logger.info("Exit method saveExcelByPayment.");
		FileInputStream fis = null;
		try {
			String fileName = "PaymentExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = vendorMainsService.createPaymentlToExcel(svvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByPayment error: ", e);
		}
		logger.info("Exit method saveExcelByPayment.");
		return null;
	}
	
	public String getVendorId() {
		return vendorId;
	}
	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}
	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}
	public SearchVendorVo getSvvo() {
		return svvo;
	}
	public void setSvvo(SearchVendorVo svvo) {
		this.svvo = svvo;
	}

	public IVendorMainService getVendorMainsService() {
		return vendorMainsService;
	}

	public void setVendorMainsService(IVendorMainService vendorMainsService) {
		this.vendorMainsService = vendorMainsService;
	}

	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}

	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}

	public String getBanId() {
		return banId;
	}

	public void setBanId(String banId) {
		this.banId = banId;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public String getExcelName() {
		return excelName;
	}

	public void setExcelName(String excelName) {
		this.excelName = excelName;
	}

	
}
