/**
 * 
 */
package com.saninco.ccm.service.ticket;

import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.ITicketDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchTicketVO;

/**
 * 
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 * 
 */
public class TicketServiceImpl implements ITicketService {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private ITicketDao ticketDao;

	public TicketServiceImpl() {
		
	}
	
	/**
	 * @author chao.liu (searchTicket.js)
	 * Search invoices by SearchTicketVO.
	 * Return JSON string.
	 */
	public String searchTicket(SearchTicketVO searchTicketVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search invoices by SearchTicketVO.", searchTicketVO));
		StringBuffer sb = new StringBuffer();
		try {
			List<String> l = ticketDao.searchTicket(searchTicketVO, SystemUtil.getCurrentUserId());
			sb.append(searchTicketVO.getListJsonCompatible(l));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author chao.liu (searchTicket.js)
	 * Get total page number and result number.
	 * Return JSON string.
	 */
	public String getTicketSearchTotalPageNo(SearchTicketVO searchTicketVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get total page number and result number.", searchTicketVO));
		StringBuffer sb = new StringBuffer();
		try {
			long count = ticketDao.getNumberOfTickets(searchTicketVO, SystemUtil.getCurrentUserId());
			sb.append(searchTicketVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author chao.liu (searchTicket.js)
	 * DownLoad Ticket Excel
	 * 
	 */
	public String createTicketToExcel(SearchTicketVO searchTicketVO,String excelDirPath,List<String> titles) throws BPLException  {
		logger.info(CcmFormat.formatEnterLog("DownLoad Ticket Excel.", searchTicketVO , excelDirPath , titles));
		List<Object[]> list = null;
		long count = 0;
		int recPerPage = 100;
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			count = ticketDao.getNumberOfTickets(searchTicketVO, SystemUtil.getCurrentUserId());
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,"ticket");
			jExcelUtil.writeTitle(0, titles);
			long totlePage = (count + recPerPage - 1) / recPerPage;
			for(int j = 0 ; j < totlePage; j++){
				searchTicketVO.setPageNo(j + 1);
				searchTicketVO.setRecPerPage(recPerPage);
				list = ticketDao.searchTicketByObject(searchTicketVO,SystemUtil.getCurrentUserId());
				for(int i = 0; i < list.size(); i++){
					jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i)); 
				}
			}
			jExcelUtil.setColumnView(new int[]{25,20,24,20,23,30,25,25});
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	public void setTicketDao(ITicketDao invoiceDao) {
		this.ticketDao = invoiceDao;
	}

}
