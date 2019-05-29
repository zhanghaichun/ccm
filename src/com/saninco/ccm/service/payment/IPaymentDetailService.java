/**
 * 
 */
package com.saninco.ccm.service.payment;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ViewPaymentDetailVO;

/**
 * @author xinyu.Liu
 * 
 */
public interface IPaymentDetailService {
	
	public Payment findPayment(String paymentId) throws BPLException;
	
	public String getPaymentDetailsTotalPageNo(ViewPaymentDetailVO viewPaymentDetailVO) throws BPLException;
	
	public String searchPaymentDetails(ViewPaymentDetailVO viewPaymentDetailVO) throws BPLException;
	
	public List<MapEntryVO<String, String>> getPaymentAction(String paymentId) throws BPLException; 
	
}
