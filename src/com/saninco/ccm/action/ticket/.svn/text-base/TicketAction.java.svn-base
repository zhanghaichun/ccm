package com.saninco.ccm.action.ticket;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.ticket.ITicketService;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchTicketVO;

/**
 * 
 * @author chao.Liu (Optimization of complete by xinyu.Liu)
 *
 */
public class TicketAction extends CcmActionSupport {
	
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private ICommonLookupService commonLookupService;
	private ITicketService ticketService;
	private IQuicklinkService quicklinkService;
	
	private String quicklinkName;
	private String queryString;
	
	private List<String> titles;
	private SearchTicketVO searchTicketVO;
	
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> ticketSeverityList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> ticketCreatedByList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> ticketPriorityList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> ticketStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> ticketModifiedList = new ArrayList<MapEntryVO<String, String>>();
	
	public TicketAction() {
	}
	
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		populateTicketSearchLookupList();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	private void populateTicketSearchLookupList() throws BPLException {
		logger.info("Enter method populateTicketSearchLookupList.");
		this.ticketCreatedByList = commonLookupService.getCreatedBy();
		this.ticketModifiedList  = commonLookupService.getCreatedBy();
		this.ticketPriorityList = commonLookupService.getPriority(); 
		this.ticketSeverityList = commonLookupService.getSeverity();
		this.ticketStatusList = commonLookupService.getTicketStatus();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		logger.info("Exit method populateTicketSearchLookupList.");
	}
	
	/**
	 * @author chao.liu (searchTicket.js)
	 * Search ticket
	 * 
	 */
	public String searchTicket() throws Exception {
		logger.info("Enter method searchTicket.");
		String result = null;
		try{
			result = ticketService.searchTicket(searchTicketVO);
		}catch(Exception e){
			result = "{error:\"Search Ticket List Error!\"}";
			logger.error("searchTicket error: ", e);
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchTicket.");
		return null;
	}
	
	/**
	 * @author chao.liu (searchTicket.js)
	 * Get ticket search total result number and page.
	 * 
	 */
	public String getTicketSearchTotalPageNo() throws Exception{
		logger.info("Enter method getTicketSearchTotalPageNo.");
		String result = null;
		try{
			result = ticketService.getTicketSearchTotalPageNo(searchTicketVO);
		}catch(Exception e){
			result = "{error:\"Get Ticket Total NO Error!\"}";
			logger.error("getTicketSearchTotalPageNo error: ", e);
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTicketSearchTotalPageNo.");
		return null;
	}


	
	/**
	 * @author chao.liu (searchTicket.js)
	 * Do save query and return the saved query name displaying on the quick link column 
	 * in the left side panel.
	 * 
	 */
	public String saveTicketSearch() throws Exception {
		logger.info("Enter method saveSearchQuery.");
		String result = null;
		try{
			result = this.quicklinkService.saveSearchQuicklink(this.quicklinkName, this.queryString, EnumQuicklinkType.TICKET);
		}catch(Exception e){
			result = "{error:\" Save Ticket Query Error!\"}";
			logger.error("saveSearchQuery error: ", e);
		}
		this.writeOutputStream(result);	
		logger.info("Exit method saveSearchQuery.");
		return null;
	}

	/**
	 * @author chao.liu (searchTicket.js)
	 * DownLoad Ticket Excel
	 * 
	 */
	public String saveExcelBySearchTicket() throws Exception {
		logger.info("Exit method saveExcelBySearchTicket.");
		FileInputStream fis = null;
		try {
			String fileName = "ticketSearchExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = ticketService.createTicketToExcel(searchTicketVO,excelDirPath,titles);

			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelBySearchTicket error: ", e);
		}
		logger.info("Exit method saveExcelBySearchTicket.");
		return null;
	}
	
	public ICommonLookupService getCommonLookupService() {
		return commonLookupService;
	}

	public void setCommonLookupService(ICommonLookupService commonLookupService) {
		this.commonLookupService = commonLookupService;
	}
	
	public ITicketService getTicketService() {
		return ticketService;
	}

	public void setTicketService(ITicketService ticketService) {
		this.ticketService = ticketService;
	}

	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
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

	public SearchTicketVO getSearchTicketVO() {
		return searchTicketVO;
	}

	public void setSearchTicketVO(SearchTicketVO searchTicketVO) {
		this.searchTicketVO = searchTicketVO;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}

	public List<MapEntryVO<String, String>> getTicketSeverityList() {
		return ticketSeverityList;
	}

	public void setTicketSeverityList(
			List<MapEntryVO<String, String>> ticketSeverityList) {
		this.ticketSeverityList = ticketSeverityList;
	}

	public List<MapEntryVO<String, String>> getTicketCreatedByList() {
		return ticketCreatedByList;
	}

	public void setTicketCreatedByList(
			List<MapEntryVO<String, String>> ticketCreatedByList) {
		this.ticketCreatedByList = ticketCreatedByList;
	}

	public List<MapEntryVO<String, String>> getTicketPriorityList() {
		return ticketPriorityList;
	}

	public void setTicketPriorityList(
			List<MapEntryVO<String, String>> ticketPriorityList) {
		this.ticketPriorityList = ticketPriorityList;
	}

	public List<MapEntryVO<String, String>> getTicketStatusList() {
		return ticketStatusList;
	}

	public void setTicketStatusList(
			List<MapEntryVO<String, String>> ticketStatusList) {
		this.ticketStatusList = ticketStatusList;
	}

	public List<MapEntryVO<String, String>> getTicketModifiedList() {
		return ticketModifiedList;
	}

	public void setTicketModifiedList(
			List<MapEntryVO<String, String>> ticketModifiedList) {
		this.ticketModifiedList = ticketModifiedList;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}
	
}
