/**
 * 
 */
package com.saninco.ccm.service.credit;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Credit;
import com.saninco.ccm.vo.SearchCreditVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

/**
 * @author xinyu.Liu
 * 
 * add searchCreditAssignment(); beijing 2010-4-16 Jian.Dong
 * add getCreditAssignmentSearchTotalPageNo(); beijing 2010-4-16 Jian.Dong
 */
public interface ICreditService {
	
	public String createCreditToExcel(SearchCreditVO searchCreditVO,String excelDirPath,List<String> titles) throws BPLException;
	
	// Delete By Chao.Liu On The 10/08/25 PM
//	public String searchCredit(SearchCreditVO searchCreditVO) throws BPLException;
//	public String getCreditSearchTotalPageNo(SearchCreditVO searchCreditVO) throws BPLException;
	
	//create pendingDispute by pengfei.wang
	 public String pendingCredit(SearchCreditVO searchCreditVO) throws BPLException;
	 public String getPendingCreditTotalPageNo(SearchCreditVO searchCreditVO) throws BPLException;

	public String searchCreditAssignment(SearchCreditVO searchCreditVO) throws BPLException;

	public String getCreditAssignmentSearchTotalPageNo(SearchCreditVO searchCreditVO) throws BPLException;

	
	/**
	 * update by wei.su craedeted
	 * CreditbalanceRollback from Reconcile
	 */
	public void UpdateCreditbalanceRollback(Credit credit) throws BPLException;

	
	
}
