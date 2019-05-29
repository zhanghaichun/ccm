package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import com.saninco.ccm.exception.BPLException;

public interface IApDao {
	public List<String> getListOfHeaders() throws SQLException;
	public List<String> getListOfDetails(int currentPaymentId)throws SQLException;
	public String getTrailer()throws SQLException;
	public String getApOutboundFeedDirectory() throws SQLException;
	public int getAPBatchId() throws SQLException;
	public int[] getAPPaymentIds(int currentPaymentStatus) throws SQLException;
	public int[] getPaymentDetailIds(int currentPaymentId) throws SQLException;
	public String getApInboundFeedDirectory() throws SQLException;
	public int[] getAPConfirmations() throws SQLException;
	public int[] getRemittanceIdsByStatus(String currentStatus) throws SQLException;
	public String getCheckNumberById(int currentPaymentId) throws SQLException;
	public int[] getAPExceptionInvoice() throws SQLException;
	public int[] getAPExceptionStatus() throws SQLException;
	public int[] getAPExceptionAmount() throws SQLException;
	public int[] getAPExceptionSupplier() throws SQLException;
	public int[] getAPExceptionStatusRe() throws SQLException;
	public int[] getAPExceptionAmountRe() throws SQLException;
	public int[] getAPExceptionSupplierRe() throws SQLException;
	public int[] getAPExceptionMultiPayRe() throws SQLException;
	public Date getPaidDateById(int currentPaymentId) throws SQLException, ParseException;
}

