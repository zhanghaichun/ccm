/**
 * 
 */
package com.saninco.ccm.service.invoice;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.User;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author Joe.Yang
 *  add getInvoiceAssignmentSearchTotalPageNo(); beijing 2010-4-16 Jian.Dong
 *	add searchInvoiceAssignment(); beijing 2010-4-16 Jian.Dong
 */
public interface IInvoiceService4BB {
	 public int getCountOfInvoicesForApproval(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	 public List<Object[]> searchInvoicesForApproval(final SearchInvoiceVO svo) throws BPLException;
	 public List<List<Object[]>> searchInvoicesForApprovalAndReference(SearchInvoiceVO svo) throws BPLException;
	 public List<Object[]> getSummaryForApproveAll(String invoiceIds) throws BPLException;
	 public List<Invoice> searchInvoiceObjectsForApproval(String invoiceIds) throws BPLException;
	 public List getBackMockup() throws BPLException;
	 public User findUserById(Integer id) throws BPLException;
}
