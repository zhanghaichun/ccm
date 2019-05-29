/**
 * 
 */
package com.saninco.ccm.service.ticket;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.SearchTicketVO;

/**
 * @author Chao.Liu (Optimization of complete by xinyu.Liu)
 * 
 */
public interface ITicketService {
	
	 public String searchTicket(SearchTicketVO searchTicketVO) throws BPLException;
	 
	 public String getTicketSearchTotalPageNo(SearchTicketVO searchTicketVO) throws BPLException;
	 
	 public String createTicketToExcel(SearchTicketVO searchTicketVO,String excelDirPath,List<String> titles) throws BPLException;
	 
}
