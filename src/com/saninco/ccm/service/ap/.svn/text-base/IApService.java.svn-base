package com.saninco.ccm.service.ap;

import com.saninco.ccm.exception.BPLException;

import java.text.ParseException;
import java.util.List;

public interface IApService {
	public String getApOutboundFeedDirectory() throws BPLException;
	public List<String> getListOfHeaders() throws BPLException;
	public List<String> getListOfDetails(int currentPaymentId) throws BPLException;
	public String getTrailer() throws BPLException;
	public void createPaymentBatch()throws BPLException;
	public int getAPBatchId() throws BPLException;
	public void updateAPBatchId(int pbid) throws BPLException;
	public void updateAPBatchStatus(String batchStatus, int currentBatchId) throws BPLException;
	public void updatePaymentStatus(int currentPaymentStatus, int newPaymentStatus) throws BPLException;
	public int[] getAPPaymentIds(int currentPaymentStatus) throws BPLException;
	public int[] getPaymentDetailIds(int currentPaymentId) throws BPLException;
	public void updateLineNumber(int paymentDetailId, int sequence) throws BPLException;
	public String getApInboundFeedDirectory() throws BPLException;
	public void updateRemittance(String[] remittanceRow) throws BPLException,ParseException;
	public int[] getAPConfirmations() throws BPLException;
	public void updatePaymentStatus(int[] pids, int newStatus) throws BPLException;
	public void updateRemittanceStatus (String oldStatus, String newStatus) throws BPLException;
	public void updateCheckNumbers(int[] confirmedPayments) throws BPLException , ParseException;
	public int[] getAPExceptionInvoice() throws BPLException;
	public int[] getAPExceptionStatus() throws BPLException;
	public int[] getAPExceptionAmount() throws BPLException;
	public int[] getAPExceptionSupplier() throws BPLException;
	public int[] getAPExceptionStatusRe() throws BPLException;
	public int[] getAPExceptionAmountRe() throws BPLException;
	public int[] getAPExceptionSupplierRe() throws BPLException;
	public int[] getAPExceptionMultiPayRe() throws BPLException;
	public void updateRemittanceStatus(int[] remittanceIds, String remittanceStatus, String description) throws BPLException;
}