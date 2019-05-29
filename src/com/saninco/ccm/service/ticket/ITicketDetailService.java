/**
 * 
 */
package com.saninco.ccm.service.ticket;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Ticket;
import com.saninco.ccm.model.User;
import com.saninco.ccm.vo.SearchTicketVO;

/**
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 * 
 */
public interface ITicketDetailService {
	
	public void saveTicket(SearchTicketVO searchTicketVO);
	
	public Ticket selectTicket(String ticketId) throws BPLException;
	
	public User selectUser(Ticket ticket) throws BPLException;
	
	public void updateTicket(SearchTicketVO searchTicketVO);
	
	public String searchTicketDetail(SearchTicketVO searchTicketVO) throws BPLException;
	
	public String getTicketDetailSearchTotalPageNo(SearchTicketVO searchTicketVO) throws BPLException;
	
	public AttachmentPoint findAttachmentPoint(Integer attachmentPointId) throws BPLException ;
	
}
