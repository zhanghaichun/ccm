/**
 * 
 */
package com.saninco.ccm.service.invoice;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IInvoiceDao4BB;
import com.saninco.ccm.dao.IUserDelegationDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.User;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * @author Joe.Yang
 *  add getInvoiceAssignmentSearchTotalPageNo(); beijing 2010-4-16 Jian.Dong
 *	add searchInvoiceAssignment(); beijing 2010-4-16 Jian.Dong
 */
public class InvoiceService4BBImpl implements IInvoiceService4BB {
	private final Logger logger = Logger.getLogger(this.getClass());

	private IInvoiceDao4BB invoiceDao4BB;
	private IUserDelegationDao userDelegationDao;
	
	public InvoiceService4BBImpl() {
	}
	
	/**
	 * Get total count of invoices waiting for approval.
	 * Return integer.
	 */
	public int getCountOfInvoicesForApproval(SearchInvoiceVO searchInvoiceVO)
			throws BPLException {
		logger.info("Enter method getCountOfInvoicesForApproval.");
		try {
			int count = invoiceDao4BB.getCountOfInvoicesForApproval(searchInvoiceVO);
			logger.info("Exit method getCountOfInvoicesForApproval.");
			return count;
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	public User findUserById(Integer id) throws BPLException {
		logger.info("Enter method findUserById.");
		try {
			User user = invoiceDao4BB.findUserById(id);
			logger.info("Exit method findUserById.");
			return user;
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	/**
	 * Get total count of invoices waiting for approval.
	 * Return integer.
	 */
	public List getBackMockup()
			throws BPLException {
		logger.info("Enter method getBackMockup.");
		try {
			List users = userDelegationDao.findBBMyWorkSparsUsers();
			logger.info("Exit method getBackMockup.");
			return users;
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	/**
	 * Search for all invoices for approval by current user.
	 */
	public List<Object[]> searchInvoicesForApproval(SearchInvoiceVO svo)
			throws BPLException {
		logger.info("Enter method searchInvoicesForApproval.");
		try {
			List<Object[]> l = invoiceDao4BB.searchInvoicesForApproval(svo);
			logger.info("Exit method searchInvoicesForApproval.");
			return l;
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	/**
	 * Search all invoices reference i.e. the last three invoices for approval reference by approval invoice id.
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public List<List<Object[]>> searchInvoicesForApprovalAndReference(SearchInvoiceVO svo)
		throws BPLException {
		logger.info("Enter method searchInvoicesForApprovalAndReference.");
		List<Object[]> l = null;
		try {
			l = invoiceDao4BB.getLast4InvoicesForReferenceApproval(svo);
			if(l==null||l.size()==0) throw new Exception();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		List<Object[]> ll = new ArrayList<Object[]>();
//		ll.add(l.get(0));	update by mingyang.li 2013-08-06
		List<Object[]> ll1 = null;
		if (l.size() > 1)
			ll1 = new ArrayList<Object[]>();
		for (int i = 0; i < l.size(); i++){
			//	update by mingyang.li 2013-08-06 start
			Object[] oa = l.get(i);	
			if(oa[7].toString().equals(svo.getInvoiceId())){
				ll.add(l.get(i));
			}else{
				ll1.add(l.get(i));
			}
			//	update by mingyang.li 2013-08-06 end
		}
		List<List<Object[]>> ll2 = new ArrayList<List<Object[]>>();
		ll2.add(ll);
		if (ll1 != null)
			ll2.add(ll1);
		logger.info("Exit method searchInvoicesForApprovalAndReference.");
		return ll2;
	}
	
	
	/**
	 * Upon approving all listed invoices in UI, system call getSummaryForApproveAll to report to user 
	 * the count of invoices, total payment, total dispute will be approved.
	 */
	public List<Object[]> getSummaryForApproveAll(String invoiceIds) throws BPLException{
		logger.info("Enter method getSummaryForApproveAll.");
		try {
			List<Object[]> l = this.invoiceDao4BB.getSummaryForApproveAll(invoiceIds);
			logger.info("Exit method getSummaryForApproveAll.");
			return l;
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}

	/**
	 * Get all invoice objects by invoice id csv string.
	 * @param invoiceIds
	 * @return
	 * @throws BPLException
	 */
	public List<Invoice> searchInvoiceObjectsForApproval(String invoiceIds) throws BPLException {
		logger.info("Enter method searchInvoiceObjectsForApproval.");
		try {
			List<Invoice> l = this.invoiceDao4BB.searchInvoiceObjectsForApproval(invoiceIds);
			logger.info("Exit method searchInvoiceObjectsForApproval.");
			return l;
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}

	/**
	 * @return the invoiceDao4BB
	 */
	public IInvoiceDao4BB getInvoiceDao4BB() {
		return invoiceDao4BB;
	}

	/**
	 * @param invoiceDao4BB the invoiceDao4BB to set
	 */
	public void setInvoiceDao4BB(IInvoiceDao4BB invoiceDao4BB) {
		this.invoiceDao4BB = invoiceDao4BB;
	}
	
	
	public void setUserDelegationDao(IUserDelegationDao userDelegationDao) {
		this.userDelegationDao = userDelegationDao;
	}

	public IUserDelegationDao getUserDelegationDao() {
		return userDelegationDao;
	}
	
}
