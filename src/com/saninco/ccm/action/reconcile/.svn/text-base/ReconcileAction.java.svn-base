package com.saninco.ccm.action.reconcile;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.reconcile.IReconcileService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchReconcileVO;

/**
 * @author wei.su (Optimization of complete by xinyu.Liu)
 *
 */
public class ReconcileAction extends CcmActionSupport {

	private static final Long serialVersionUID=45471L;
	private final Logger logger = Logger.getLogger(this.getClass()); 
	private IReconcileService reconcileService;
	private IQuicklinkService quicklinkService;
	private SearchReconcileVO searchReconcileVO;
	//private IReconcileService reconcileService;
	
	private String quicklinkName;
	private String queryString;
	private String selVendorId;
	//delete reconcile Parameters
	private int reconcileId;
	private String disputeNumber;
	private String creditNumber;
	private int disputeId;
	private int creditId;
	private String amount;
	
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> analystList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> disputeStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> currencyList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> invoiceStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();

	/**
	 * 
	 * @return
	 */
	public ReconcileAction() {
	}

	/**
	 * Get reconcile search total result number and page.
	 * @return
	 * @throws Exception
	 */
	public String getReconcileAssignmentSearchTotalPageNo() throws Exception{
		logger.info("Enter method getReconcileAssignmentSearchTotalPageNo.");
		String result = null;
		try{
			result = reconcileService.getReconcileSearchTotalPageNo(searchReconcileVO);
		}catch(Exception e){
			logger.error("getReconcileAssignmentSearchTotalPageNo error: ", e);
			result = "{error:\"getReconcileAssignmentSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getReconcileAssignmentSearchTotalPageNo.");
		return null;
	}
	
	/**
	 * Get credit search total result number and page.
	 * @return
	 * @throws Exception
	 */
	public String getReconcileSearchTotalPageNo() throws Exception{
		logger.info("Enter method getReconcileSearchTotalPageNo.");
		String result = null;
		try{
			result = reconcileService.getReconcileSearchTotalPageNo(searchReconcileVO);
		}catch(Exception e){
			logger.error("getReconcileSearchTotalPageNo error: ", e);
			result = "{error:\"getReconcileSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getReconcileSearchTotalPageNo.");
		return null;
	}
	
	/**
	 * Do search reconcile action.
	 * @return
	 * @throws Exception
	 */
	public String searchReconcile() throws Exception {
		logger.info("Enter method searchReconcile.");
		String result = null;
		try{
			result = reconcileService.searchReconcile(searchReconcileVO);
		}catch(Exception e){
			logger.error("searchReconcile error: ", e);
			result = "{error:\"searchReconcile error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchReconcile.");
		return null;
	}
	
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		populateReconcileSearchLookupList();
		logger.info("Exit method exec.");
		return SUCCESS;
	}

	//Obtain public object
	private void populateReconcileSearchLookupList() throws BPLException {
		logger.info("Enter method populateReconcileSearchLookupList.");
			this.vendorList = commonLookupService.getUserPreviledgedVendors();
			this.disputeStatusList = commonLookupService.getDisputeStatus();
			this.currencyList = commonLookupService.getCurrency();
			this.invoiceStatusList = commonLookupService.getInvoiceStatus();
			this.analystList = commonLookupService.getAnalyst();
			this.quicklinkList = quicklinkService.getUserQuicklinks(EnumQuicklinkType.RECONCILE);
		logger.info("Exit method populateReconcileSearchLookupList.");
	}
	
	/**
	 * Do save query and return the saved query name displaying on the quick link column 
	 * in the left side panel.
	 * @return
	 * @throws Exception
	 */
	public String saveReconcileSearch() throws Exception {
		logger.info("Enter method saveReconcileSearchQuery.");
		String result = null;
		try{
			result = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.RECONCILE);
		}catch(Exception e){
			logger.error("saveReconcileSearchQuery error: ", e);
			result = "{error:\"saveReconcileSearchQuery: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveReconcileSearchQuery.");
		return null;
	}
	
	public String updateCreditOrDisputebalanceRollback() throws Exception {
		logger.info("Enter method exec.");
		try {
			Number number = NumberFormat.getNumberInstance().parse(getAmount());
			reconcileService.updateCreditOrDisputebalanceRollback(getReconcileId(), getDisputeNumber(), getCreditNumber(), getDisputeId(), getCreditId(), number.doubleValue());
		} catch (RuntimeException e) {
			logger.error("saveSearchQueryCall error: ", e);
		}
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	public IReconcileService getReconcileService() {
		return reconcileService;
	}

	public void setReconcileService(IReconcileService reconcileService) {
		this.reconcileService = reconcileService;
	}

	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public SearchReconcileVO getSearchReconcileVO() {
		return searchReconcileVO;
	}

	public void setSearchReconcileVO(SearchReconcileVO searchReconcileVO) {
		this.searchReconcileVO = searchReconcileVO;
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

	public List<MapEntryVO<String, String>> getDisputeStatusList() {
		return disputeStatusList;
	}

	public void setDisputeStatusList(
			List<MapEntryVO<String, String>> disputeStatusList) {
		this.disputeStatusList = disputeStatusList;
	}

	public List<MapEntryVO<String, String>> getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(List<MapEntryVO<String, String>> currencyList) {
		this.currencyList = currencyList;
	}

	public List<MapEntryVO<String, String>> getInvoiceStatusList() {
		return invoiceStatusList;
	}

	public void setInvoiceStatusList(
			List<MapEntryVO<String, String>> invoiceStatusList) {
		this.invoiceStatusList = invoiceStatusList;
	}

	public List<MapEntryVO<String, String>> getAnalystList() {
		return analystList;
	}

	public void setAnalystList(List<MapEntryVO<String, String>> analystList) {
		this.analystList = analystList;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}

	public int getReconcileId() {
		return reconcileId;
	}

	public void setReconcileId(int reconcileId) {
		this.reconcileId = reconcileId;
	}

	public String getDisputeNumber() {
		return disputeNumber;
	}

	public void setDisputeNumber(String disputeNumber) {
		this.disputeNumber = disputeNumber;
	}

	public String getCreditNumber() {
		return creditNumber;
	}

	public void setCreditNumber(String creditNumber) {
		this.creditNumber = creditNumber;
	}

	public int getDisputeId() {
		return disputeId;
	}

	public void setDisputeId(int disputeId) {
		this.disputeId = disputeId;
	}

	public int getCreditId() {
		return creditId;
	}

	public void setCreditId(int creditId) {
		this.creditId = creditId;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}
}
