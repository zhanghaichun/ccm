/**
 * 
 */
package com.saninco.ccm.service.ticket;

import java.util.Date;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.dao.ITicketDao;
import com.saninco.ccm.dao.ITicketHistoryDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Email;
import com.saninco.ccm.model.Priority;
import com.saninco.ccm.model.Severity;
import com.saninco.ccm.model.SysConfig;
import com.saninco.ccm.model.Ticket;
import com.saninco.ccm.model.TicketHistory;
import com.saninco.ccm.model.TicketStatus;
import com.saninco.ccm.model.User;
import com.saninco.ccm.service.email.ISendEmailService;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemEmailConfig;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchTicketVO;

/**
 * Service class for manipulating Ticket and TicketHistory.
 * 
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 *
 */
public class TicketDetailServiceImpl implements ITicketDetailService {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private ITicketDao ticketDao;
	private ISysConfigDAO sysConfigDAO;
	private ITicketHistoryDao ticketHistoryDao;
	private ISendEmailService sendEmailService;

	public TicketDetailServiceImpl() {
	} 
	
	/**
	 * @author xinyu.Liu (viewTicketDetail.js)
	 * Find AttachmentPoint object and returns 
	 * 
	 */
	public AttachmentPoint findAttachmentPoint(Integer attachmentPointId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Find AttachmentPoint Object", attachmentPointId));
		AttachmentPoint attachmentPoint = new AttachmentPoint();
		try {
			attachmentPoint = ticketHistoryDao.findAttachmentPointById(attachmentPointId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return attachmentPoint;
	}
	
	/**
	 * @author xinyu.Liu (viewTicketDetail.js)
	 * Get total page number and result number. 
	 * 
	 */
	public String getTicketDetailSearchTotalPageNo(SearchTicketVO searchTicketVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search TicketDetail Total Page No", searchTicketVO));
		StringBuffer sb = new StringBuffer();
		try {
			long count = ticketDao.getNumberOfTicketDetail(searchTicketVO.getTicketId());
			sb.append(searchTicketVO.getTotalPageNoJson(count));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewTicketDetail.js)
	 * update Ticket and add history 
	 * 
	 */
	public void updateTicket(SearchTicketVO searchTicketVO) {
		logger.info(CcmFormat.formatEnterLog("update Ticket Data and Add Ticket History Data", searchTicketVO));
		TicketHistory ticketHistory = new TicketHistory();
		Priority priority = new Priority();
		Severity severity = new Severity();
		TicketStatus ticketStatus = new TicketStatus();
		try{
			Ticket ticket = ticketDao.findById(Integer.parseInt(searchTicketVO.getTicketId()));
			
			//add ticket TicketHistory
			ticketHistory.setContent(searchTicketVO.getContent());
			ticketHistory.setComment(searchTicketVO.getComment());
			ticketHistory.setTitle(searchTicketVO.getTitle());
			ticketHistory.setTicket(ticket);
			ticketHistory.setCreatedBy(SystemUtil.getCurrentUserId());
			ticketHistory.setCreatedTimestamp(new Date());
			
			//If you have uploaded attachments to add
			if(!"".equals(searchTicketVO.getAttachmentPointId()) && searchTicketVO.getAttachmentPointId() != null){
				AttachmentPoint attachmentPoint = new AttachmentPoint();
				attachmentPoint = ticketDao.findAttachmentPointById(Integer.parseInt(searchTicketVO.getAttachmentPointId()));
				ticketHistory.setAttachmentPoint(attachmentPoint);
			}
	
			ticketHistory.setTicketStatusIdFrom(ticket.getTicketStatus().getId());
			ticketHistory.setTicketStatusIdTo(Integer.parseInt(searchTicketVO.getStatusId()));
			ticketHistory.setPriorityIdFrom(ticket.getPriority().getId());
			ticketHistory.setPriorityIdTo(Integer.parseInt(searchTicketVO.getPriorityId()));
			ticketHistory.setSeverityIdFrom(ticket.getSeverity().getId());
			ticketHistory.setSeverityIdTo(Integer.parseInt(searchTicketVO.getSeverityId()));
			ticketHistoryDao.save(ticketHistory);
			
			//update ticket 
			priority = ticketDao.findPriorityById(Integer.parseInt(searchTicketVO.getPriorityId()));
			severity = ticketDao.findSeverityById(Integer.parseInt(searchTicketVO.getSeverityId()));
			ticketStatus = ticketDao.findTicketStatusById(Integer.parseInt(searchTicketVO.getStatusId()));
			
			ticket.setPriority(priority);
			ticket.setSeverity(severity);
			ticket.setTicketStatus(ticketStatus);
			ticket.setTitle(searchTicketVO.getTitle());
			ticket.setContent(searchTicketVO.getContent());
			ticket.setModifiedBy(SystemUtil.getCurrentUserId());
			ticket.setModifiedTimestamp(new Date());
			ticketDao.save(ticket);
			
			// Add By Chao.Liu
			this.sendTicketEmail("U",ticket,searchTicketVO); 
			
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewTicketDetail.js)
	 * Determine whether there Ticket
	 * YES: true
	 * No: false
	 * 
	 */
	public void saveTicket(SearchTicketVO searchTicketVO) {
		logger.info(CcmFormat.formatEnterLog("Add Ticket Data and Add Ticket History Data", searchTicketVO));
		Ticket ticket = new Ticket();
		Priority priority = new Priority();
		Severity severity = new Severity();
		TicketStatus ticketStatus = new TicketStatus();
		try{
			priority = ticketDao.findPriorityById(Integer.parseInt(searchTicketVO.getPriorityId()));
			severity = ticketDao.findSeverityById(Integer.parseInt(searchTicketVO.getSeverityId()));
			ticketStatus = ticketDao.findTicketStatusById(Integer.parseInt(searchTicketVO.getStatusId()));
			
			//Create a Ticket
			ticket.setTitle(searchTicketVO.getTitle());
			ticket.setContent(searchTicketVO.getContent());
			ticket.setPriority(priority);
			ticket.setSeverity(severity);
			ticket.setTicketStatus(ticketStatus);
			ticket.setCreatedBy(SystemUtil.getCurrentUserId());
			ticket.setCreatedTimestamp(new Date());
			ticket.setModifiedBy(SystemUtil.getCurrentUserId());
			ticket.setModifiedTimestamp(new Date());
			ticket.setRecActiveFlag("Y");
			ticketDao.save(ticket);
			
			//Create a TicketHistory
			TicketHistory ticketHistory = new TicketHistory();
			ticketHistory.setComment(searchTicketVO.getComment());
			ticketHistory.setContent(ticket.getContent());
			ticketHistory.setTitle(ticket.getTitle());
			ticketHistory.setTicket(ticket);
			ticketHistory.setCreatedBy(SystemUtil.getCurrentUserId());
			ticketHistory.setCreatedTimestamp(new Date());
			
			//If you have uploaded attachments to add
			if(!"".equals(searchTicketVO.getAttachmentPointId()) && searchTicketVO.getAttachmentPointId() != null){
				AttachmentPoint attachmentPoint = new AttachmentPoint();
				attachmentPoint = ticketDao.findAttachmentPointById(Integer.parseInt(searchTicketVO.getAttachmentPointId()));
				ticketHistory.setAttachmentPoint(attachmentPoint);
			}
			
			ticketHistory.setTicketStatusIdTo(ticket.getTicketStatus().getId());
			ticketHistory.setPriorityIdTo(ticket.getPriority().getId());
			ticketHistory.setSeverityIdTo(ticket.getSeverity().getId());
			ticketHistoryDao.save(ticketHistory);
			
			// Add By Chao.Liu
			this.sendTicketEmail("N",ticket,searchTicketVO); 
			
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * chao.Liu
	 * 
	 */
	private void sendTicketEmail(String o,Ticket t,SearchTicketVO searchTicketVO) throws MessagingException{
		String subject = o+"|"+t.getId()+"|"+t.getPriority().getPriorityName()+"|"+t.getSeverity().getSeverityName()+"|"+t.getTitle();
		String text = "Ticket Content:"+t.getContent()+",Ticket Comment:"+searchTicketVO.getComment();
		
		SysConfig sc = (SysConfig) ticketDao.get(SysConfig.class, 20);
		sendEmailService.sendTicketEmail(subject, text, sc.getValue());
		
	}
	
	/**
	 * @author xinyu.Liu (TicketDetailAction.java)
	 * Found Ticket by TicketId
	 */
	public Ticket selectTicket(String ticketId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("find Ticket Object", ticketId));
		Ticket ticket = new Ticket();
		try {
			ticket = ticketDao.findById(Integer.parseInt(ticketId));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return ticket;
	}
	
	/**
	 * @author xinyu.Liu (TicketDetailAction.java)
	 * Found Ticket by Ticket
	 */
	public User selectUser(Ticket ticket) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("find User Object", ticket));
		User user = new User();
		try {
			user = ticketDao.findUserById(ticket.getCreatedBy());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return user;
	}
	
	/**
	 * @author xinyu.Liu (viewTicketDetail.js)
	 * Search ticket List . 
	 * 
	 */
	public String searchTicketDetail(SearchTicketVO searchTicketVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Ticket List", searchTicketVO));
		StringBuffer sb = new StringBuffer();
		try {
			List<String> list = ticketDao.searchTicketDetail(searchTicketVO);
			sb.append(searchTicketVO.getListJsonCompatible(list));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public ITicketDao getTicketDao() {
		return ticketDao;
	}

	public void setTicketDao(ITicketDao ticketDao) {
		this.ticketDao = ticketDao;
	}

	public ITicketHistoryDao getTicketHistoryDao() {
		return ticketHistoryDao;
	}

	public void setTicketHistoryDao(ITicketHistoryDao ticketHistoryDao) {
		this.ticketHistoryDao = ticketHistoryDao;
	}

	public ISysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(ISysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	public ISendEmailService getSendEmailService() {
		return sendEmailService;
	}

	public void setSendEmailService(ISendEmailService sendEmailService) {
		this.sendEmailService = sendEmailService;
	} 
	
}
