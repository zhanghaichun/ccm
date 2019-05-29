/**
 * 
 */
package com.saninco.ccm.service.invoice;

import java.sql.SQLException;
import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author xinyu.Liu
 * 
 */
public interface IInvoiceService {
	
	 public String searchInvoice(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	 
	 public String getInvoiceSearchTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	 
	 public String getInvoiceAssignmentSearchTotalPageNo( SearchInvoiceVO searchInvoiceVO) throws BPLException;
	 
	 public String getPastDueCount(WorkspaceVO wVO) throws BPLException;
	 public String searchInvoiceAssignment(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	 
	 public String getInvoiceWorkCount(WorkspaceVO wVO) throws BPLException;
	 
	 // External Approve Invoice Page Number.
	 public String countExternalApproveInvoicePageNo(WorkspaceVO wVO) throws BPLException;
	 
	 // External Approve Invoice list items.
	 public String listExternalApproveInvoiceItems(WorkspaceVO wVO) throws BPLException;
	 
	 // Download External Approve Invoices.
	 public String downloadExternalApproveInvoices(WorkspaceVO wvo, String excelDirPath, List<String> titles) throws BPLException;
	 
	 public String searchInvoiceWorkCount(WorkspaceVO wVO) throws BPLException;
	 
	 public String searchInvoiceByIName(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	 
	 public String searchInvoiceByInvoiceNumber(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	 
	 public String getMissingWorkCount(WorkspaceVO wVO) throws BPLException;
	 
	 public String getProcessWorkCount(WorkspaceVO wVO) throws BPLException;
	 
	 public String createInvoiceToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath,List<String> titles) throws BPLException;

	public String getUnknownWorkCount(WorkspaceVO wvo) throws BPLException;

	public String searchUnknownInvoices(WorkspaceVO wvo)throws BPLException;
	
	public String getMissingInvoicesExcel(WorkspaceVO wvo, String excelDirPath, List<String> titles)throws BPLException;

	public String searchPreloadInvoiceWorkCount(WorkspaceVO wVO) throws BPLException;
	
	public String searchInvoiceRejectWorkCount(WorkspaceVO wVO) throws BPLException;
	
	public String searchPastDue(WorkspaceVO wVO) throws BPLException;
	
	public String getPreloadInvoiceWorkCount(WorkspaceVO wVO) throws BPLException;
	
	public String getInvoiceRejectWorkCount(WorkspaceVO wVO) throws BPLException;
	
	public String getPreloadInvoicesExcel(WorkspaceVO wvo, String excelDirPath, List<String> titles) throws BPLException;
	
	public String getDueDaysInvoicesExcel(WorkspaceVO wvo, String excelDirPath, List<String> titles) throws BPLException;
	
	public String removeMissingInvoice(Invoice invoice,String missingEmailflag) throws BPLException;
	
	public String removeUnknownInvoice(Invoice invoice) throws BPLException;
	
	public String getUnknownInvoicesExcel(WorkspaceVO wvo, String excelDirPath, List<String> titles,List<Integer> ids)throws BPLException;
	
	public String getInvoiceByNumberSearchTotalPageNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String searchInvoiceByINumber(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String updateInvoiceOrfindInvoiceStatus(SearchInvoiceVO searchInvoiceVO);
	
	public String closeInvoice(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String removeInvoice(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String throughAPPaymentCloseInvoice(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String searchInvoiceForPRJ(SearchInvoiceVO searchInvoiceVO)throws BPLException;

	public String searchInvoiceForPRS(SearchInvoiceVO searchInvoiceVO)throws BPLException;

	public String pRSConfirmed(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public String updateSearchForScoa(SearchInvoiceVO searchInvoiceVO)throws BPLException;
	
	public String tRegularCloseInvoice(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public String cancelInvoice(SearchInvoiceVO searchInvoiceVO)throws BPLException;
	
	public String searchInvoiceLabel(String labelIds)throws BPLException;
	
	public String saveModifyPreviousAdjustment(SearchInvoiceVO searchInvoiceVO) throws BPLException, SQLException;
	
	public String saveMoveCurrentToAdjustment(SearchInvoiceVO searchInvoiceVO) throws BPLException, SQLException;

	public String downloadUsageReport(Integer invoiceId,String filePath) throws BPLException, SQLException;
	
	public void createUsageInvoiceNote(String filePath) throws BPLException, SQLException;

	public void deleteInvoiceNote(String attachmentFileId) throws BPLException, SQLException;
}
