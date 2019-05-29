/**
 * 
 */
package com.saninco.ccm.service.payment;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.SearchPaymentVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author Joe.Yang
 * add getCreditAssignmentSearchTotalPageNo(); beijing 2010-4-16 Jian.Dong
 * add searchCreditAssignment(); beijing 2010-4-16 Jian.Dong
 */
public interface IPaymentService {
	// public String savePaymentSearchQuicklink(String quicklinkName, String queryString) throws BPLException;
	 public String searchPayment(SearchPaymentVO searchPaymentVO) throws BPLException;
	 public String getPaymentSearchTotalPageNo(SearchPaymentVO searchPaymentVO) throws BPLException;
	 public String searchPaymentAssignment(SearchPaymentVO searchPaymentVO) throws BPLException;
	 public String getPaymentAssignmentSearchTotalPageNo( SearchPaymentVO searchPaymentVO)throws BPLException;
	 /**
	 * @author Chao.Liu
	 * Search Payment By Payment Number
	 * @return
	 * @throws Exception
	 */
	public String searchPaymentByPName(SearchPaymentVO svo)throws BPLException;
	/**
	 * @author Chao.Liu
	 * Edit Payment
	 * @return
	 * @throws Exception
	 */
	public String paymentPTConfirm(SearchPaymentVO svo)throws BPLException;
	/**
	 * 
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getPaymentFileTotalNO(SearchPaymentVO svo)throws BPLException;
	/**
	 * 
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String searchPaymentFileList(SearchPaymentVO svo)throws BPLException;
	/**
	 * 
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String deletePaymentFileByFId(SearchPaymentVO svo)throws BPLException;
	
	public String getPaymentWorkCount(WorkspaceVO wVO) throws BPLException;
	public String searchPaymentWorkCount(WorkspaceVO wVO) throws BPLException;
}
