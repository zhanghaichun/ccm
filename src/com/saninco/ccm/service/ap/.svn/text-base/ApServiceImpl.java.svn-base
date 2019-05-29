package com.saninco.ccm.service.ap;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IApDao;
import com.saninco.ccm.dao.IPaymentBatchDao;
import com.saninco.ccm.dao.IPaymentDao;
import com.saninco.ccm.dao.IPaymentDetailDao;
import com.saninco.ccm.dao.IRemittance;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.dao.IWorkflowActionDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.model.PaymentBatch;
import com.saninco.ccm.model.PaymentDetail;
import com.saninco.ccm.model.Remittance;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;

public class ApServiceImpl implements IApService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	private IApDao apDao;
    private IPaymentBatchDao paymentBatchDao;
    private IPaymentDetailDao paymentDetailDao;
    private IRemittance remittanceDao;
	private IPaymentDao paymentDao;
	private IWorkflowActionDao workflowActionDao;
	
	public ApServiceImpl() {
	}
	
    /**Get the Ap Outbound Feed Directory.
     * Return a String.
     */
	public String getApOutboundFeedDirectory() throws BPLException {
	
	logger.info("Enter method getApOutboundFeedDirectory.");	
	String apOutboundFeedDirectory = null;
	try{
		apOutboundFeedDirectory = apDao.getApOutboundFeedDirectory();
	}catch(RuntimeException e){
		logger.error(CcmFormat.formatErrorLog(e));
		BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		throw bpe;
	}
	catch(SQLException e){
		logger.error(CcmFormat.formatErrorLog(e));
		System.out.println(e);
	}
	logger.info("Exit method getApOutboundFeedDirectory.");
	return apOutboundFeedDirectory;
	}
	
	
    /**Create a new paymentBatch.
     * No return value.
     */
	
	//After getting the header records, create a payment_batch record
	//Set batch_status = 1 (Created) in the batch
	//Set created_timestamp = sdf.format(cal.getTime())
	//Set created_by = 0
	//Commit

	public void createPaymentBatch()throws BPLException{
		logger.info("Enter method createPaymentBatch.");
		PaymentBatch pb = new PaymentBatch();
		pb.setBatchStatus("1");
		pb.setCreatedBy(0);
		pb.setCreatedTimestamp(new Date());
		pb.setModifiedBy(0);
		pb.setModifiedTimestamp(new Date());

		try{
		paymentBatchDao.save(pb);
		}
		catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method createPaymentBatch.");
	}
	
	
    /**Get the Id of the new Payment Batch created.
     * Return int.
     */
	//The ap_header_view also select the payment_ids of selected payments
	//Every payment belongs to the batch just created
	//Select the last created batch, max(created_timestamp). Get the batch id
	//New method here: get the latest created payment_batch id: int pbid = getAPBatchId()
	
	public int getAPBatchId() throws BPLException{
		
		logger.info("Enter method getAPBatchId.");	
		int batchId = 0;
		try{
			batchId = apDao.getAPBatchId();
		}catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		catch(SQLException e){
			logger.error(CcmFormat.formatErrorLog(e));
			System.out.println(e);
		}
		logger.info("Exit method getAPBatchId.");
		return batchId;
	}
	
	
	
    /**Assign the batchId to ready payments
     * No return value.
     */
	
	//New method here: get the selected payments from ap_header_view: int[] apPaymentIds = getAPPaymentIds()
	//Update the payments one by one: using the batch id (loop)
	//Payment p(n) = paymentDao.findById(apPaymentIds(n))
	//Update payment_batch_id = pbid
	//Set modified_timestamp = cal.getTime()
	//Set modified_by = 0
	//Commit
	
	public void updateAPBatchId(int pbid) throws BPLException{
		logger.info("Enter method updateAPBatchId.");
		
		try{
			
		int[] apPaymentIds = this.getAPPaymentIds(20);
		
		for (int i=0; i<apPaymentIds.length; i++){
			logger.info("Updating payment_batch_id for payment_id: "+apPaymentIds[i]);
			Payment ptemp = paymentDao.findById(apPaymentIds[i]);
			ptemp.setPaymentBatch(paymentBatchDao.findById(pbid));
			ptemp.setModifiedBy(0);
			ptemp.setModifiedTimestamp(new Date());
			paymentDao.update(ptemp);
		}
		
		} catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method updateAPBatchId.");
	}
	
    /**Get the payments ready to be sent (Status = 20).
     * Return an array of int.
     */
	//New method here: get the selected payments from ap_header_view: int[] apPaymentIds = getAPPaymentIds()
	
	public int[] getAPPaymentIds(int currentPaymentStatus) throws BPLException{
		
		logger.info("Enter method getAPPaymentIds.");	
		int[] apPaymentIds = null;
		try{
			apPaymentIds = apDao.getAPPaymentIds(currentPaymentStatus);
		}catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		catch(SQLException e){
			logger.error(CcmFormat.formatErrorLog(e));
			System.out.println(e);
		}
		logger.info("Exit method getAPPaymentIds.");
		return apPaymentIds;
	}
	

	/*After generating the file,
	 * Update the payment_batch record pb
	 * Set 2=Transactions collected, 3=File created, 4=File sent
	 * Set modified_timestamp = sdf.format(cal.getTime())
	 * Set modified_by = 0
	 * Commit
	 * */
	
	public void updateAPBatchStatus(String newBatchStatus, int currentBatchId) throws BPLException{
		logger.info("Enter method updateAPBatchStatus.");
		PaymentBatch pb = paymentBatchDao.findById(currentBatchId);
		pb.setBatchStatus(newBatchStatus);
		pb.setModifiedBy(0);
		pb.setModifiedTimestamp(new Date());
		try{
		paymentBatchDao.update(pb);
		}
		catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method updateAPBatchStatus.");
	}
	

	
	
	//For each payments sent, update status.
	//Update the payments one by one: using the batch id (loop)
	//Payment p(n) = paymentDao.findById(apPaymentIds(n))
	//Update payment_status_id = 21 (Sending to AP), 22(Sent to AP) 
	//Set modified_timestamp = sdf.format(cal.getTime())
	//Set modified_by = 0
	//Commit
	
	public void updatePaymentStatus(int currentPaymentStatus, int newPaymentStatus) throws BPLException{
		logger.info("Enter method updatePaymentStatus.");
		
		try{
		//Actually should be the payments which have been assigned with current batchId.
		//this.getAPPaymentIds(int batchId);
		//Will be modified later.
			

	    int[] apPaymentIds = this.getAPPaymentIds(currentPaymentStatus);

		for (int i=0; i<apPaymentIds.length; i++){
			Payment ptemp = paymentDao.findById(apPaymentIds[i]);
			ptemp.setPaymentStatus(paymentDao.findPaymentStatusById(newPaymentStatus));
			ptemp.setModifiedBy(0);
			ptemp.setModifiedTimestamp(new Date());
			ptemp.setStatusTimestamp(new Date());
			paymentDao.update(ptemp);
		}
		
		} catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method updatePaymentStatus.");
	}
	

    /**Get all header records matching search conditions.
     * Return a list of strings.
     */
	public List<String> getListOfHeaders() throws BPLException {
	
	logger.info("Enter method getListOfHeaders.");	
	List<String> listOfHeaders = null;
	try{
		listOfHeaders = apDao.getListOfHeaders();
	}catch(RuntimeException e){
		logger.error(CcmFormat.formatErrorLog(e));
		BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		throw bpe;
	}
	catch(SQLException e){
		logger.error(CcmFormat.formatErrorLog(e));
		System.out.println(e);
	}
	logger.info("Exit method getListOfHeaders.");
	return listOfHeaders;
	}

	/**Get all related payment detail lines of an invoice.
	 * Return a list of strings.
	 */
	public List<String> getListOfDetails(int currentPaymentId) throws BPLException{
		
		logger.info("Enter method getListOfDetails.");
		List<String> listOfDetails = null;
		try{
			listOfDetails = apDao.getListOfDetails(currentPaymentId);
		}catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		catch(SQLException e){
			logger.error(CcmFormat.formatErrorLog(e));
			System.out.println(e);
		}
		logger.info("Exit method getListOfDetails.");
		return listOfDetails;
	}
	
	/**Get all related payment detail Ids of an invoice.
	 * Return a list of strings.
	 */
	public int[] getPaymentDetailIds(int currentPaymentId) throws BPLException {
		
		logger.info("Enter method getPaymentDetailIds.");	
		int[] paymentDetailIds = null;
		try{
			paymentDetailIds = apDao.getPaymentDetailIds(currentPaymentId);
		}catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		catch(SQLException e){
			logger.error(CcmFormat.formatErrorLog(e));
			System.out.println(e);
		}
		logger.info("Exit method getPaymentDetailIds.");
		return paymentDetailIds;
	}
	
	
	/**Update a sequence number to a payment detail.
	 * No return value.
	 */
	public void updateLineNumber(int paymentDetailId, int sequence) throws BPLException{
		logger.info("Enter method updateLineNumber.");
		
		try{
            //Assign the sequence to the payment_detail specified.
		
			PaymentDetail pd = paymentDetailDao.findById(paymentDetailId);
			pd.setLineNumber(sequence);
			pd.setModifiedBy(0);
			pd.setModifiedTimestamp(new Date());
			paymentDetailDao.update(pd);
		
		} catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method updateLineNumbers.");
	}
	
	
	
	/**Get the trailer record for all invoices.
	 * Return a string.
	 */
	public String getTrailer() throws BPLException{
		
		logger.info("Enter method getTrailer.");
		String trailer = new String();
		try{
			trailer  = apDao.getTrailer();
		}catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		catch(SQLException e){
			logger.error(CcmFormat.formatErrorLog(e));
			System.out.println(e);
		}
		logger.info("Exit method getTrailer.");
		return trailer;
	}
	
	
	
    /**Get the Ap Inbound Feed Directory.
     * Return a String.
     */
	public String getApInboundFeedDirectory() throws BPLException {
	
	logger.info("Enter method getApInboundFeedDirectory.");	
	String apInboundFeedDirectory = null;
	try{
		apInboundFeedDirectory = apDao.getApInboundFeedDirectory();
	}catch(RuntimeException e){
		logger.error(e);
		BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		throw bpe;
	}
	catch(SQLException e){
		logger.error(e);
		System.out.println(e);
	}
	logger.info("Exit method getApInboundFeedDirectory.");
	return apInboundFeedDirectory;
	}
	
	/**Update one remittance line.
	 * No return value.
	 */
	
	public void updateRemittance(String[] remittanceRow) throws BPLException, ParseException{
		logger.info("Enter method updateRemittance.");
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd"); 
		Remittance re = new Remittance();
		  //remittanceRow[1],[2],[3],[4],[5]
		  //remittance_status,status_timestamp,create and modified timestamp,created and modified by
		  //rec_active_flag 
		re.setInvoiceNumber(remittanceRow[1]);
	    re.setApSupplierNumber(remittanceRow[2]);
	    re.setPaymentReferenceCode(remittanceRow[3]);
	    re.setPaymentAmount(Double.parseDouble(remittanceRow[4]));
	    //deal with "20100914"
	    //Add - after 2010
	    //Add - after 09
	    String dateString = remittanceRow[5].substring(0,4)+"-"+remittanceRow[5].substring(4,6)+"-"+remittanceRow[5].substring(6);
	    re.setPaidDate(df.parse(dateString));
		re.setRemittanceStatus("1");
		re.setStatusTimestamp(new Date());
		re.setCreatedBy(0);
		re.setCreatedTimestamp(new Date());
		re.setModifiedBy(0);
		re.setModifiedTimestamp(new Date());
		re.setRecActiveFlag("Y");

		try{
		remittanceDao.save(re);
		}
		catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method updateRemittance.");
		
	}
	
	/**Get all payments confirmed by remittance.
	 * Return int[] of paymentIds.
	 */
	
	public int[] getAPConfirmations() throws BPLException{
		
		logger.info("Enter method getAPConfirmations.");
		int[] confirmedPayments = null;
		try{
			confirmedPayments = apDao.getAPConfirmations();
		}catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}
		logger.info("Exit method getAPConfirmations.");
		return confirmedPayments;
		
	}
	
	/**Update status of all payments.
	 * No return value.
	 */
	
	public void updatePaymentStatus(int[] pids, int newStatus) throws BPLException{
		
		logger.info("Enter method updatePaymentStatus.");
		
		try{


		for (int i=0; i<pids.length; i++){
			Payment ptemp = paymentDao.findById(pids[i]);
			ptemp.setPaymentStatus(paymentDao.findPaymentStatusById(newStatus));
			ptemp.setModifiedBy(0);
			ptemp.setModifiedTimestamp(new Date());
			ptemp.setStatusTimestamp(new Date());
			paymentDao.update(ptemp);
		}
		
		} catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method updatePaymentStatus.");
	}
	
	/**Update status of all processed remittance records.
	 * No return value.
	 */
	
	public void updateRemittanceStatus (String oldStatus, String newStatus) throws BPLException{
		logger.info("Enter method updateRemittanceStatus.");
		try{
		int[] remittanceIds = apDao.getRemittanceIdsByStatus(oldStatus);
		for (int i=0; i<remittanceIds.length; i++){
			Remittance re = remittanceDao.findById(remittanceIds[i]);
			re.setRemittanceStatus(newStatus);
			re.setStatusTimestamp(new Date());
			re.setModifiedBy(0);
			re.setModifiedTimestamp(new Date());
			remittanceDao.update(re);
		}	
		}
		catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}
		logger.info("Exit method updateRemittanceStatus.");
	}
	
	
	/**Update check number, paid_date and workflow_action_id for paid payments.
	 * No return value.
	 */
	
	public void updateCheckNumbers(int[] confirmedPayments) throws BPLException, ParseException{
		logger.info("Enter method updateCheckNumbers.");
		
		try{

		for (int i=0; i<confirmedPayments.length; i++){
			Payment ptemp = paymentDao.findById(confirmedPayments[i]);
			String checkNumber = apDao.getCheckNumberById(confirmedPayments[i]);
			ptemp.setPaymentReferenceCode(checkNumber);
			ptemp.setWorkflowAction(workflowActionDao.findById(4));
			logger.info("Workflow_action_should be: " + workflowActionDao.findById(4).getId());
			logger.info("Workflow_action_id after updating: " + ptemp.getWorkflowAction().getId());
			ptemp.setPaidDate(apDao.getPaidDateById(confirmedPayments[i]));
			logger.info("Paid Date should be: " + apDao.getPaidDateById(confirmedPayments[i]));
			logger.info("Paid Date after updating: " + ptemp.getPaidDate());
			ptemp.setModifiedBy(0);
			ptemp.setModifiedTimestamp(new Date());
			paymentDao.update(ptemp);
		}
		
		} catch(RuntimeException e){
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}		
		
		logger.info("Exit method updateCheckNumbers.");
		
	}
	
	/**Get remittances with invoice_number exception.
	 * return int[].
	 */
	public int[] getAPExceptionInvoice() throws BPLException{
		
		logger.info("Enter method getAPExceptionInvoice.");
		int[] exceptionRemittances = null;
		try{
			exceptionRemittances = apDao.getAPExceptionInvoice();
		}catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}
		logger.info("Exit method getAPExceptionInvoice.");
		return exceptionRemittances;
		
	}
	
	/**Get payments with payment_status exception (don't match with remittance).
	 * return int[].
	 */
	public int[] getAPExceptionStatus() throws BPLException{
		
		logger.info("Enter method getAPExceptionStatus.");
		int[] exceptionPayments = null;
		try{
			exceptionPayments = apDao.getAPExceptionStatus();
		}catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}
		logger.info("Exit method getAPExceptionStatus.");
		return exceptionPayments;
		
	}
	
	/**Get payments with payment_amount exception (don't match with remittance).
	 * return int[].
	 */
	public int[] getAPExceptionAmount() throws BPLException{
		
		logger.info("Enter method getAPExceptionAmount.");
		int[] exceptionPayments = null;
		try{
			exceptionPayments = apDao.getAPExceptionAmount();
		}catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}
		logger.info("Exit method getAPExceptionAmount.");
		return exceptionPayments;
		
	}
	
	/**Get payments with ap_supplier_number exception (don't match with remittance).
	 * return int[].
	 */
	public int[] getAPExceptionSupplier() throws BPLException{
		
		logger.info("Enter method getAPExceptionSupplier.");
		int[] exceptionPayments = null;
		try{
			exceptionPayments = apDao.getAPExceptionSupplier();
		}catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}
		logger.info("Exit method getAPExceptionSupplier.");
		return exceptionPayments;
	}
	
	
	/**Get remittances with payment_status exception (don't match with payments).
	 * return int[].
	 */
	public int[] getAPExceptionStatusRe() throws BPLException{
		
		logger.info("Enter method getAPExceptionStatusRe.");
		int[] exceptionRemittances = null;
		try{
			exceptionRemittances = apDao.getAPExceptionStatusRe();
		}catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}
		logger.info("Exit method getAPExceptionStatusRe.");
		return exceptionRemittances;
		
	}
	
	/**Get remittances with multiple payments exception.
	 * return int[].
	 */
	public int[] getAPExceptionMultiPayRe() throws BPLException{
	
		logger.info("Enter method getAPExceptionMultiPayRe.");
		int[] exceptionRemittances = null;
		try{
			exceptionRemittances = apDao.getAPExceptionMultiPayRe();
		}catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}
		logger.info("Exit method getAPExceptionMultiPayRe.");
		return exceptionRemittances;
	}
	
	
	/**Get remittances with payment_amount exception (don't match with payments).
	 * return int[].
	 */
	public int[] getAPExceptionAmountRe() throws BPLException{
		
		logger.info("Enter method getAPExceptionAmountRe.");
		int[] exceptionRemittances = null;
		try{
			exceptionRemittances = apDao.getAPExceptionAmountRe();
		}catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}
		logger.info("Exit method getAPExceptionAmountRe.");
		return exceptionRemittances;
		
	}
	
	
	/**Get remittances with ap_supplier_number exception (don't match with payments).
	 * return int[].
	 */
	public int[] getAPExceptionSupplierRe() throws BPLException{
		
		logger.info("Enter method getAPExceptionSupplierRe.");
		int[] exceptionRemittances = null;
		try{
			exceptionRemittances = apDao.getAPExceptionSupplierRe();
		}catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}catch(SQLException e){
			logger.error(e);
			System.out.println(e);
		}
		logger.info("Exit method getAPExceptionSupplierRe.");
		return exceptionRemittances;
	}
	
	/**Update remittance status by Id.
	 * No return value.
	 */
	public void updateRemittanceStatus(int[] remittanceIds, String remittanceStatus, String description) throws BPLException{
		
		logger.info("Enter method updateRemittanceStatus.");
		try{
	
		for (int i=0; i<remittanceIds.length; i++){
			Remittance re = remittanceDao.findById(remittanceIds[i]);
			re.setRemittanceStatus(remittanceStatus);
			re.setStatusTimestamp(new Date());
			if(re.getProcessDescription()!= null){
				StringBuffer sb = new StringBuffer(re.getProcessDescription());
				sb.append(description);
				re.setProcessDescription(sb.toString());
			}
			else{
				re.setProcessDescription(description);
			}
			
		    re.setModifiedBy(0);
		    re.setModifiedTimestamp(new Date());
		    remittanceDao.update(re);
		 }	
		}catch(RuntimeException e){
			logger.error(e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method updateRemittanceStatus.");
	}
	


	public IApDao getApDao() {
		return apDao;
	}

	public void setApDao(IApDao apDao) {
		this.apDao = apDao;
	}

	
	/**
	 * @return the paymentBatchDao
	 */
	public IPaymentBatchDao getPaymentBatchDao() {
		return paymentBatchDao;
	}

	/**
	 * @param paymentBatchDao the paymentBatchDao to set
	 */
	public void setPaymentBatchDao(IPaymentBatchDao paymentBatchDao) {
		this.paymentBatchDao = paymentBatchDao;
	}

	/**
	 * @return the paymentDetailDao
	 */
	public IPaymentDetailDao getPaymentDetailDao() {
		return paymentDetailDao;
	}

	/**
	 * @param paymentDetailDao the paymentDetailDao to set
	 */
	public void setPaymentDetailDao(IPaymentDetailDao paymentDetailDao) {
		this.paymentDetailDao = paymentDetailDao;
	}

	/**
	 * @return the paymentDao
	 */
	public IPaymentDao getPaymentDao() {
		return paymentDao;
	}

	/**
	 * @param paymentDao the paymentDao to set
	 */
	public void setPaymentDao(IPaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	/**
	 * @return the remittanceDao
	 */
	public IRemittance getRemittanceDao() {
		return remittanceDao;
	}

	/**
	 * @param remittanceDao the remittanceDao to set
	 */
	public void setRemittanceDao(IRemittance remittanceDao) {
		this.remittanceDao = remittanceDao;
	}


	/**
	 * @return the workflowActionDao
	 */
	public IWorkflowActionDao getWorkflowActionDao() {
		return workflowActionDao;
	}

	/**
	 * @param workflowActionDao the workflowActionDao to set
	 */
	public void setWorkflowActionDao(IWorkflowActionDao workflowActionDao) {
		this.workflowActionDao = workflowActionDao;
	}
	
}

