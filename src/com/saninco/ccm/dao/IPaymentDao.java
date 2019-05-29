/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

//import com.saninco.ccm.action.payment.PaymentAction;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.Payment;
import com.saninco.ccm.model.PaymentMethod;
import com.saninco.ccm.model.PaymentStatus;
import com.saninco.ccm.vo.SearchPaymentVO;
import com.saninco.ccm.vo.ViewPaymentDetailVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author Joe.Yang
 * add getAssignmentCount(); beijing 2010-4-16 Jian.Dong
 * add searchPaymentAssignment(); beijing 2010-4-16 Jian.Dong
 * 
 * add getNumberOfPaymentDetails(); beijing 2010-4-21 xinyu.Liu
 * add searchPaymentDetails(); beijing 2010-4-21 xinyu.Liu
 * add getNumberOfPaymentHistory(); beijing 2010-4-22 xinyu.Liu
 * add searchPaymentHistory(); beijing 2010-4-22 xinyu.Liu
 * add findById(); beijing 2010-4-22 xinyu.Liu
 * add getPaymentAction(); beijing 2010-4-23 xinyu.Liu
 * add findPaymentHistoryById(); beijing 2010-4-23 xinyu.Liu
 * add savePaymentHistory(); beijing 2010-4-23 xinyu.Liu
 * add findPaymentActionById(); beijing 2010-4-23 xinyu.Liu
 * add findPaymentStatusById(); beijing 2010-4-23 xinyu.Liu
 */
public interface IPaymentDao {
	
	public List<String> searchPayment(SearchPaymentVO searchPaymentVO, int userId);
	
	public long getNumberOfPayments(SearchPaymentVO searchPaymentVO, int userId);
	
	public long getAssignmentCount(int currentUserId);
	
	public List<String> searchPaymentAssignment(SearchPaymentVO svo);
	
	public long getNumberOfPaymentDetails(ViewPaymentDetailVO viewPaymentDetailVO);
	
	public List<String> searchPaymentDetails(ViewPaymentDetailVO viewPaymentDetailVO);
	
//	public long getNumberOfPaymentHistory(ViewPaymentDetailVO viewPaymentDetailVO);
	
//	public List<String> searchPaymentHistory(ViewPaymentDetailVO viewPaymentDetailVO);
	
	public Payment findById(Integer id);
	
	public List<String> getPaymentAction(String paymentId);
	/**
	 * @author Chao.Liu
	 * @param name
	 * @return
	 */
	public List getPaymentByName(String name);
	/**
	 * @Author Chao.liu Sep 25, 2010
	 * @return
	 */
	public List<PaymentMethod> getPaymentMethod();
	/**
	 * 
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @param svo
	 * @return
	 */
	public long getPaymentFileTotalNO(SearchPaymentVO svo);
	/**
	 * 
	 * @Author Chao.Liu Date: Sep 30, 2010
	 * @param svo
	 * @return
	 */
	public List searchPaymentFileList(SearchPaymentVO svo);
	public void save(Object o);
	public Object get(Class c,Integer id);
	public void update(Object o);
//	public PaymentHistory findPaymentHistoryById(Integer id);
	
//	public void savePaymentHistory(PaymentHistory transientInstance);
	
//	public PaymentAction findPaymentActionById(Integer id);
	
	public PaymentStatus findPaymentStatusById(Integer id);
	
	public long getPaymentWorkCount(WorkspaceVO wVO);
	public List<String> searchPaymentWorkCount(WorkspaceVO wVO);
	public void merge(Payment payment) ;
	
}
