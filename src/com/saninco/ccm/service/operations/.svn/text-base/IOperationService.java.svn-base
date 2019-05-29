/**
 * 
 */
package com.saninco.ccm.service.operations;

import java.util.Date;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.OperationsVO;


/**
 * @author Jian.Dong
 */
public interface IOperationService {

	public void saveOperations(Integer vendorId, Integer banId, String barcode, Date invoiceDate);
	
	public void addInvoiceStVData(OperationsVO operationsVO);
	
	public void deleteInvoiceStVData(OperationsVO operationsVO) throws BPLException;
	
	public String operationServiceByBarCode(OperationsVO operationsVO) throws BPLException;
	
	public String operationServiceFindBarCode(OperationsVO operationsVO) throws BPLException;
	
}
