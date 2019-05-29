/**
 * 
 */
package com.saninco.ccm.action.invoice;

import java.util.ArrayList;
import java.util.List;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.User;
import com.saninco.ccm.service.email.ISendEmailService;
import com.saninco.ccm.service.invoice.IInvoiceDetailService;
import com.saninco.ccm.service.invoice.IInvoiceService4BB;
import com.saninco.ccm.util.Constants;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * CCM Invoice approval action class for manager to approve/reject/hold invoice proposal with Blackberry.
 * 
 * @author joe.yang
 *
 */
public class InvoiceAction4BB extends CcmActionSupport {
	private IInvoiceService4BB invoiceService4BB;
	private IInvoiceDetailService invoiceDetailService;
	private ISendEmailService sendEmailService;
	private SearchInvoiceVO svo;
	private int totalPageNumber;
	private List<Object[]> invoicesForApproval;
	private List<Object[]> summaryForApproval;
	private List<Object[]> invoicesForReference;
	private List<Object[]> backUpUsers;
	private String invoiceIds;
	private String oldStatusIds;
	private String successMessage;
	private String failureMessage = "";
	private User user;
	private boolean DEBUG = false;
	
	/* (non-Javadoc)
	 * @see com.saninco.ccm.action.CcmActionSupport#exec()
	 */
	@Override
	public String exec() throws Exception {
		try
		{
			backUpUsers = this.invoiceService4BB.getBackMockup();
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			return "errPage";
		}
		
		return SUCCESS;
	}
	
	public String getInvoices() throws Exception {
		if(svo==null){
			svo = new SearchInvoiceVO();
		}
		if (svo.getPageNo() == 0) {
			svo.setPageNo(Constants.DEFAULT_START_PAGE_NO);
		}
		if(svo.getSortField()==null){
			svo.setSortField(Constants.DEFAULT_INVOICE_SORT_FIELD);
			svo.setSortingDirection(Constants.DEFAULT_INVOICE_SORT_DIRECTION);
		}
		svo.setRecPerPage(Constants.DEFAULT_REC_PER_PAGE);
		if(DEBUG){
			getMockupInvoicesForApproval();
			return SUCCESS;
		}
		int count = this.invoiceService4BB.getCountOfInvoicesForApproval(svo);
		if(count==0){
			totalPageNumber = 0;
		}else{
			totalPageNumber = (int) Math.ceil((double)count / (double)svo.getRecPerPage());
			invoicesForApproval = this.invoiceService4BB.searchInvoicesForApproval(svo);
			user = this.invoiceService4BB.findUserById(svo.getUserId());
			invoiceIds = extractInvoiceIds();
			oldStatusIds = extractInvoiceStatusIds();
		}
		return SUCCESS;
	}
	
	public String searchInvoice() throws Exception {
		return SUCCESS;
	}
	
	private void getMockupInvoicesForApproval(){
		invoicesForApproval = new ArrayList<Object[]>();
		Object[] a = new Object[]{new Integer(1),"AB12345","Bell Ontario","8123456",new Double(112300.00),new Double(0.00)};
		invoicesForApproval.add(a);
		a = new Object[]{new Integer(2),"AB12346","Bell Ontario","8123457",new Double(102100.00),new Double(0.00)};
		invoicesForApproval.add(a);
		a = new Object[]{new Integer(3),"AB12346","Bell Ontario","8123457",new Double(102100.00),new Double(0.00)};
		invoicesForApproval.add(a);
		a = new Object[]{new Integer(4),"AB12346","Bell Ontario","8123457",new Double(102100.00),new Double(0.00)};
		invoicesForApproval.add(a);
		a = new Object[]{new Integer(5),"AB12346","Bell Ontario","8123457",new Double(102100.00),new Double(0.00)};
		invoicesForApproval.add(a);
		a = new Object[]{new Integer(6),"AB12346","Bell Ontario","8123457",new Double(102100.00),new Double(0.00)};
		invoicesForApproval.add(a);
		a = new Object[]{new Integer(7),"AB12346","Bell Ontario","8123457",new Double(102100.00),new Double(0.00)};
		invoicesForApproval.add(a);
		int count = invoicesForApproval.size();
		totalPageNumber = (int) Math.ceil((double)count / (double)svo.getRecPerPage());
		invoiceIds = extractInvoiceIds();
		oldStatusIds = extractInvoiceStatusIds();
	}
	
	private String extractInvoiceIds(){
		StringBuffer sb = new StringBuffer();
		for(Object[] o : invoicesForApproval){
			sb.append(((sb.length()==0)?"":",")+o[0]);
		}
		return sb.toString();
	}
	private String extractInvoiceStatusIds(){
		StringBuffer sb = new StringBuffer();
		for(Object[] o : invoicesForApproval){
			sb.append(((sb.length()==0)?"":",")+o[6]);
		}
		return sb.toString();
	}
	
	public String viewInvoice() throws Exception {
		try {
			// validate invoice approval privilege , if invalid will throw Exception and contains error message
			invoiceDetailService.checkWorkflowPrivilegeAndShowMessage(Integer.valueOf(svo.getInvoiceId()));
			List<List<Object[]>> l = this.invoiceService4BB.searchInvoicesForApprovalAndReference(svo);
			this.invoicesForApproval = l.get(0);
			Object[] oa = this.invoicesForApproval.get(0);
			svo.setInvoiceNumber(oa[0].toString());
			svo.setStatusId(oa[6].toString());
			if(Double.parseDouble(oa[4].toString())<0){
				svo.setPaymentAmount(null);
			}else{
				svo.setPaymentAmount(oa[4].toString());
			}
			user = this.invoiceService4BB.findUserById(svo.getUserId());
			if(l.size()>1) this.invoicesForReference = l.get(1);
			return SUCCESS;
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			return "errPage";
		}
	}
	
	public String approveInvoice() throws Exception {
		try {
			//this.invoiceService.updateInvoiceForApprovalRejectOrHold(svo, "Approve");
			Invoice invoice = invoiceDetailService.getInvoiceByInvoiceId(svo.getInvoiceId());
			if(valiApprove(invoice)){
				
				   invoice = this.invoiceDetailService.updateInvoiceWorkflow(invoice, 1, svo.getNote(), svo.getStatusId());
				   int statusId = invoice.getInvoiceStatus().getId();
				   
				   if(statusId == 25 || statusId == 29 || statusId == 33){
					   if (invoice.getUserByAssignmentUserId().getId()!= null) {
						   sendEmailService.sendApproveEmailToAssignmentUser(invoice);
					   }
				    }
			}else{
			  return INPUT;
			}
			this.successMessage = getText("invoice.approve.success.message",new String []{""+1});
			return SUCCESS;
			
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			List<List<Object[]>> l = this.invoiceService4BB.searchInvoicesForApprovalAndReference(svo);
			this.invoicesForApproval = l.get(0);
			if(l.size()>1) this.invoicesForReference = l.get(1);
			return INPUT;
		}
	}
	
	private boolean valiApprove(Invoice invoice2) throws BPLException {
		boolean flag = true;
		//invoice下有没有close的dispute.并且这个dispute 下有open 的action request.
		boolean isOpen = invoiceDetailService.getInvoiceDisputeStatusCloseAndActionRequestOpenCount(invoice2);	
		
		if(isOpen){
			failureMessage += "\"Please update disputes that have open action request before approval.\"";
			return false;
		}
		// ban 下的dispute 超过60天
		boolean isDueDate = invoiceDetailService.getBanDisputeDueDate(invoice2.getId());
		if(isDueDate){
			failureMessage += "\"Please update disputes that are over 60 days before approval.\"";
			return false;
		}
		failureMessage += "\"\"";
		return flag ;
	}
	
	public String confirmApproveAllInvoices() throws Exception {
		this.summaryForApproval = this.invoiceService4BB.getSummaryForApproveAll(invoiceIds);
		user = this.invoiceService4BB.findUserById(svo.getUserId());
		return SUCCESS;
	}
	private String findOldStatusByInvoiceId(Integer invoiceId) {
		String oldStatusId = "-1";
		if (invoiceId != null && invoiceIds != null && oldStatusIds != null) {
			String[] invoiceIdsArray = invoiceIds.split(",");
			String[] oldStatusIdsArray = oldStatusIds.split(",");
			
			if (invoiceIdsArray.length == oldStatusIdsArray.length) {
				for (int i = 0; i < invoiceIdsArray.length; i++) {
					if (invoiceIdsArray[i] != null && invoiceIdsArray[i].equals(invoiceId.toString())) {
						return oldStatusIdsArray[i];
					}
				}
			}
		}
		return oldStatusId;
	}
	public String approveAllInvoices() throws Exception {
		try {
			//int c = this.invoiceService4BB.updateInvoiceForApproveAll(svo, invoiceIds);
			List<Invoice> invoices = this.invoiceService4BB.searchInvoiceObjectsForApproval(invoiceIds);
			int c = 0;
			for(Invoice i : invoices){
				try {
					String oldStatusId = findOldStatusByInvoiceId(i.getId());
					this.invoiceDetailService.updateInvoiceWorkflow(i, 1 , svo.getNote(), oldStatusId);
					c++;
				} catch (Exception e) {
					logger.error("Failed to approve invoice "+i.getInvoiceNumber(), e);
					this.failureMessage +=  "Failed to approve invoice "+i.getInvoiceNumber()+
					" ("+e.getMessage()+")<br>";
				}
			}
			String msgKey = "invoices.approve.success.message";
			if(c==1) msgKey = "invoice.approve.success.message";
			this.successMessage = getText(msgKey,new String []{""+c});
			return SUCCESS;
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			this.summaryForApproval = this.invoiceService4BB.getSummaryForApproveAll(invoiceIds);
			return INPUT;
		}
	}
	
	public String confirmRejectInvoice() throws Exception {
		return SUCCESS;
	}
	
	public String confirmApproveInvoice() throws Exception {
		return SUCCESS;
	}
	
	public String rejectInvoice() throws Exception {
		try {
			Invoice invoice = invoiceDetailService.getInvoiceByInvoiceId(svo.getInvoiceId());
			this.invoiceDetailService.updateInvoiceWorkflow(invoice, 3 , svo.getNote(), svo.getStatusId());
//			this.invoiceService.updateInvoiceForApprovalRejectOrHold(svo, "Reject");
			this.successMessage = getText("invoice.reject.success.message",new String []{""+1});
			return SUCCESS;
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			List<List<Object[]>> l = this.invoiceService4BB.searchInvoicesForApprovalAndReference(svo);
			this.invoicesForApproval = l.get(0);
			if(l.size()>1) this.invoicesForReference = l.get(1);
			return INPUT;
		}
	}
	
	public String holdInvoice() throws Exception {
		try {
			Invoice invoice = invoiceDetailService.getInvoiceByInvoiceId(svo.getInvoiceId());
			this.invoiceDetailService.updateInvoiceWorkflow(invoice, 2 , svo.getNote(), svo.getStatusId());
//			this.invoiceService.updateInvoiceForApprovalRejectOrHold(svo, "Hold");
			this.successMessage = getText("invoice.hold.success.message",new String []{""+1});
			return SUCCESS;
		} catch (Exception e) {
			this.addActionError(e.getMessage());
			List<List<Object[]>> l = this.invoiceService4BB.searchInvoicesForApprovalAndReference(svo);
			this.invoicesForApproval = l.get(0);
			if(l.size()>1) this.invoicesForReference = l.get(1);
			return INPUT;
		}
	}

	/**
	 * @param invoiceService the invoiceService to set
	 */
	public void setInvoiceService4BB(IInvoiceService4BB invoiceService4BB) {
		this.invoiceService4BB = invoiceService4BB;
	}

	/**
	 * @return the svo
	 */
	public SearchInvoiceVO getSvo() {
		return svo;
	}

	/**
	 * @param svo the svo to set
	 */
	public void setSvo(SearchInvoiceVO svo) {
		this.svo = svo;
	}

	/**
	 * @return the totalPageNumber
	 */
	public int getTotalPageNumber() {
		return totalPageNumber;
	}

	/**
	 * @return the invoicesForApproval
	 */
	public List<Object[]> getInvoicesForApproval() {
		return invoicesForApproval;
	}

	/**
	 * @return the invoiceIds
	 */
	public String getInvoiceIds() {
		return invoiceIds;
	}

	/**
	 * @param invoiceIds the invoiceIds to set
	 */
	public void setInvoiceIds(String invoiceIds) {
		this.invoiceIds = invoiceIds;
	}

	/**
	 * @return the summaryForApproval
	 */
	public List<Object[]> getSummaryForApproval() {
		return summaryForApproval;
	}

	/**
	 * @return the successMessage
	 */
	public String getSuccessMessage() {
		return successMessage;
	}

	/**
	 * @return the invoicesForReference
	 */
	public List<Object[]> getInvoicesForReference() {
		return invoicesForReference;
	}

	/**
	 * @param invoiceDetailService the invoiceDetailService to set
	 */
	public void setInvoiceDetailService(IInvoiceDetailService invoiceDetailService) {
		this.invoiceDetailService = invoiceDetailService;
	}

	/**
	 * @param invoicesForApproval the invoicesForApproval to set
	 */
	public void setInvoicesForApproval(List<Object[]> invoicesForApproval) {
		this.invoicesForApproval = invoicesForApproval;
	}

	/**
	 * @param invoicesForReference the invoicesForReference to set
	 */
	public void setInvoicesForReference(List<Object[]> invoicesForReference) {
		this.invoicesForReference = invoicesForReference;
	}
	
	public String getFailureMessage() {
		return failureMessage;
	}

	public String getOldStatusIds() {
		return oldStatusIds;
	}

	public void setOldStatusIds(String oldStatusIds) {
		this.oldStatusIds = oldStatusIds;
	}

	public List<Object[]> getBackUpUsers() {
		return backUpUsers;
	}

	public void setBackUpUsers(List<Object[]> backUpUsers) {
		this.backUpUsers = backUpUsers;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ISendEmailService getSendEmailService() {
		return sendEmailService;
	}

	public void setSendEmailService(ISendEmailService sendEmailService) {
		this.sendEmailService = sendEmailService;
	}
	
	

}
