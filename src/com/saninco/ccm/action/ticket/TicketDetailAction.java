/**
 * 
 */
package com.saninco.ccm.action.ticket;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Ticket;
import com.saninco.ccm.model.TicketHistory;
import com.saninco.ccm.model.User;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.ticket.ITicketDetailService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchTicketVO;

/**
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 * 
 */
public class TicketDetailAction extends CcmActionSupport {
	
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private ITicketDetailService ticketDetailService;
	private IQuicklinkService quicklinkService;

	private String quicklinkName;
	private String queryString;
	private String selVendorId;
	
	private String ticketId;
	
	private SearchTicketVO searchTicketVO;
	
	private User user = new User();
	private Ticket ticket = new Ticket();
	private TicketHistory ticketHistory = new TicketHistory();
	
	private List<MapEntryVO<String, String>> priorityList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> severityList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> ticketStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	
	private Integer workflowActionId;
	private List<File> uploads;
	private List<String> uploadsFileName;
	private String uploadsMessage;
	
	private Integer attachmentPointId;

	public TicketDetailAction() {
	}

	/* 
	 * Go to search page.
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		populateTicketDetailLookupList();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	private void populateTicketDetailLookupList() throws BPLException {
		logger.info("Enter method populateSeverityPriorityInfo.");
		if(ticketId !=null){
			ticket = ticketDetailService.selectTicket(ticketId);
			user = ticketDetailService.selectUser(ticket);
		}
		this.priorityList = commonLookupService.getPriority();
		this.ticketStatusList = commonLookupService.getTicketStatus();
		this.severityList = commonLookupService.getSeverity();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		logger.info("Exit method populateSeverityPriorityInfo.");
	}
	
	/**
	 * @author xinyu.Liu (viewTicketDetail.js)
	 * save Ticket to database 
	 * 
	 */
	public String saveTicket() throws Exception {
		logger.info("Enter method saveTicket.");
		String result = "{}";
		try{
			ticketDetailService.saveTicket(searchTicketVO);
		}catch(Exception e){
			logger.error("saveTicket error: ", e);
			result = "{error:\"updateTicket error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Enter method saveTicket.");
		return null ;
	}
	
	/**
	 * @author xinyu.Liu (viewTicketDetail.js)
	 * update Ticket to database 
	 * 
	 */
	public String updateTicket() throws Exception {
		logger.info("Enter method updateTicket.");
		String result = "{}";
		try{
			ticketDetailService.updateTicket(searchTicketVO);
		}catch(Exception e){
			logger.error("updateTicket error: ", e);
			result = "{error:\"updateTicket error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Enter method updateTicket.");
		return null ;
	}
	
	/**
	 * @author xinyu.Liu (viewTicketDetail.js)
	 * Get ticketDetail search total result number and page. 
	 * 
	 */
	public String getTicketDetailSearchTotalPageNo() throws Exception{
		logger.info("Enter method getTicketDetailSearchTotalPageNo.");
		String result = null;
		try{
			result = ticketDetailService.getTicketDetailSearchTotalPageNo(searchTicketVO);
		}catch(Exception e){
			logger.error("getTicketDetailSearchTotalPageNo error: ", e);
			result = "{error:\"getTicketDetailSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTicketDetailSearchTotalPageNo.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewTicketDetail.js)
	 * Do search ticket action. 
	 *
	 */
	public String searchTicketDetail() throws Exception {
		logger.info("Enter method searchTicket.");
		String result = null;
		try{
			result = ticketDetailService.searchTicketDetail(searchTicketVO);
		}catch(Exception e){
			logger.error("searchTicket error: ", e);
			result = "{error:\"Search TicketServiceCall error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchTicket.");
		return null;
	}
	
	/**
	 * @author xinyu.Liu (viewTicketDetail.js)
	 * Add a Ticket History 
	 * 
	 */
	public String doAddTicketHistory()throws Exception {
		logger.info("Enter method doAddTicketHistory.");
		try{
			if("".equals(searchTicketVO.getAttachmentPointId())){
				AttachmentPoint point = commonLookupService.saveUploadFileAndGetPointId("ticket_history",uploads,uploadsFileName);
				this.attachmentPointId = point.getId();
			}else{
				AttachmentPoint attachmentPoint = ticketDetailService.findAttachmentPoint(Integer.parseInt(searchTicketVO.getAttachmentPointId()));
				commonLookupService.saveUploadFileToPointId(attachmentPoint, uploads, uploadsFileName);
				this.attachmentPointId = Integer.parseInt(searchTicketVO.getAttachmentPointId());
			}
		}catch(Exception e){
			logger.error("doAddTicketHistory error: ", e);
			uploadsMessage = "doAddTicketHistory Error!";
			return INPUT;
		}
		logger.info("Exit method doAddTicketHistory.");
		return SUCCESS;
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

	public String getSelVendorId() {
		return selVendorId;
	}

	public void setSelVendorId(String selVendorId) {
		this.selVendorId = selVendorId;
	}

	public Logger getLogger() {
		return logger;
	}

	public List<MapEntryVO<String, String>> getTicketStatusList() {
		return ticketStatusList;
	}

	public void setTicketStatusList(
			List<MapEntryVO<String, String>> ticketStatusList) {
		this.ticketStatusList = ticketStatusList;
	}

	public List<MapEntryVO<String, String>> getPriorityList() {
		return priorityList;
	}

	public void setPriorityList(List<MapEntryVO<String, String>> priorityList) {
		this.priorityList = priorityList;
	}

	public List<MapEntryVO<String, String>> getSeverityList() {
		return severityList;
	}

	public void setSeverityList(List<MapEntryVO<String, String>> severityList) {
		this.severityList = severityList;
	}

	public ITicketDetailService getTicketDetailService() {
		return ticketDetailService;
	}

	public void setTicketDetailService(ITicketDetailService ticketDetailService) {
		this.ticketDetailService = ticketDetailService;
	}
	
	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public TicketHistory getTicketHistory() {
		return ticketHistory;
	}

	public void setTicketHistory(TicketHistory ticketHistory) {
		this.ticketHistory = ticketHistory;
	}

	public SearchTicketVO getSearchTicketVO() {
		return searchTicketVO;
	}

	public void setSearchTicketVO(SearchTicketVO searchTicketVO) {
		this.searchTicketVO = searchTicketVO;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}

	public Integer getWorkflowActionId() {
		return workflowActionId;
	}

	public void setWorkflowActionId(Integer workflowActionId) {
		this.workflowActionId = workflowActionId;
	}

	public List<File> getUploads() {
		return uploads;
	}

	public void setUploads(List<File> uploads) {
		this.uploads = uploads;
	}

	public List<String> getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(List<String> uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public String getUploadsMessage() {
		return uploadsMessage;
	}

	public void setUploadsMessage(String uploadsMessage) {
		this.uploadsMessage = uploadsMessage;
	}

	public Integer getAttachmentPointId() {
		return attachmentPointId;
	}

	public void setAttachmentPointId(Integer attachmentPointId) {
		this.attachmentPointId = attachmentPointId;
	}
	
}
