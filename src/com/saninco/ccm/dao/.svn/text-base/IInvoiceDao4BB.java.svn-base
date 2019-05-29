/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.User;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author Joe.Yang
 *
 * add getAssignmentCount(); beijing 2010-4-16 Jian.Dong
 * add searchInvoiceAssignment(); beijing 2010-4-16 Jian.Dong
 */
public interface IInvoiceDao4BB {
	public List<String> searchInvoice(SearchInvoiceVO searchInvoiceVO, int userId);
	public List<Object[]> searchInvoiceByObject(SearchInvoiceVO sio, int userId);
	public long getNumberOfInvoices(SearchInvoiceVO searchInvoiceVO, int userId);
	public long getAssignmentCount(int currentUserId);
	public List<String> searchInvoiceAssignment(SearchInvoiceVO svo);
	public long getInvoiceWorkCount(WorkspaceVO wVO);
	public List<String> searchInvoiceWorkCount(WorkspaceVO wVO);
	@SuppressWarnings("unchecked")
	public void  saveDataByXml(String sqlStr);
	public List<String> searchWorkspase(WorkspaceVO wvo);
	public long getWorkspaseCount(WorkspaceVO wvo);
	public User findUserById(Integer id);
	public List<String> searchWorkspaseProcess(WorkspaceVO wvo);
	public long getWorkspaseProcessCount(WorkspaceVO wvo);
	public Invoice findById(java.lang.Integer id);
	public String getVendorAcronymByInvoiceId(int invoiceId);
	public String getVendorIdByInvoiceId(int invoiceId);
	public List<Object[]> searchInvoicesForApproval(SearchInvoiceVO svo);
	public List<Invoice> searchInvoiceObjectsForApproval(String invoiceIds);
	public int getCountOfInvoicesForApproval(SearchInvoiceVO svo);
	public List<Object[]> getLast4InvoicesForReferenceApproval(final SearchInvoiceVO svo);
	public Invoice merge(Invoice detachedInstance);
	public void attachDirty(Invoice instance);
	public void attachClean(Invoice instance);
	public List<Object[]> getSummaryForApproveAll(String invoiceIds);
}
