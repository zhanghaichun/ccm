/**
 * 
 */
package com.saninco.ccm.service.credit;

import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.ICreditDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Credit;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchCreditVO;

/**
 * Modified Logger by Chao.liu 10/08/23 AM
 * @author xinyu.Liu
 *  @see pendingCredit is below --pengfei.wang
 *	add searchCreditAssignment(); beijing 2010-4-16 Jian.Dong
 *  add getCreditAssignmentSearchTotalPageNo(); beijing 2010-4-16 Jian.Dong
 */
public class CreditServiceImpl implements ICreditService {
	private final Logger logger = Logger.getLogger(this.getClass());

	private ICreditDao creditDao;

	public CreditServiceImpl() {
	} 
	
	/**
	 * create Credit To Excel
	 */
	public String createCreditToExcel(SearchCreditVO searchCreditVO,String excelDirPath,List<String> titles) throws BPLException  {
		List<Object[]> list = null;
		long count = 0;
		int recPerPage = 100;
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			count = creditDao.getNumberOfCredit(searchCreditVO, SystemUtil.getCurrentUserId());
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,"credit0");
			jExcelUtil.writeTitle(0, titles);
			if(count <= recPerPage){
				searchCreditVO.setPageNo(1);
				searchCreditVO.setRecPerPage((int)count);
				list = creditDao.searchCreditByObject(searchCreditVO,SystemUtil.getCurrentUserId());
				for(int i=0;i<list.size();i++){
					jExcelUtil.writeLine(i+1, list.get(i)); 
				}
			}else{
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for(int j = 0 ; j < totlePage; j++){
					searchCreditVO.setPageNo(j + 1);
					searchCreditVO.setRecPerPage(recPerPage);
					list = creditDao.searchCreditByObject(searchCreditVO,SystemUtil.getCurrentUserId());
					for(int i = 0; i < list.size(); i++){
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i)); 
					}
				}
			}
			jExcelUtil.setColumnView(new int[]{22,30,30,24,22,30,27});
			//jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method createCreditToExcel.");
		return excelDirPath;
	}
	
	/**
	 * Delete By Chao.Liu On The 10/08/25 PM
	 * Search credit by SearchCreditVO.
	 * Return JSON string.
	 */
//	public String searchCredit(SearchCreditVO searchCreditVO) throws BPLException {
//		StringBuffer sb = new StringBuffer();
//		List<String> list = null;
//		try {
//			list = creditDao.searchCredit(searchCreditVO, SystemUtil.getCurrentUserId());
//		} catch (RuntimeException e) {
//			logger.error(CcmFormat.formatErrorLog(e));
//			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
//			throw bpe;
//		}
//		if(list!=null&&list.size()>0){
//			sb.append("{begin:");
//			sb.append(searchCreditVO.getStartIndex()+1);
//			sb.append(",end:");
//			int size = list.size();
//			sb.append(searchCreditVO.getStartIndex()+size);
//			sb.append(",data:[");
//			for(int i = 0; i<size; i++){
//				Object str = list.get(i);
//				if(str == null){
//					break;
//				}else {
//					if(str instanceof SerializableBlob){
//						str = BlobUtil.getBlobString((SerializableBlob)str);
//					}
//					if(i!=0) sb.append(",");
//					sb.append(str.toString());
//				}
//			}
//			sb.append("]");
//		}else{
//			sb.append("{error:\"failed to query.\"");
//		}
//		sb.append("}");
//		logger.info("Exit method searchCreditVO.");
//		return sb.toString();
//	}
	/**
	 * Delete By Chao.Liu On The 10/08/25 PM
	 * Get total page number and result number.
	 * Return JSON string.
	 */
//	public String getCreditSearchTotalPageNo(SearchCreditVO searchCreditVO)
//			throws BPLException {
//		StringBuffer sb = new StringBuffer();
//		long count = 0;
//		try {
//			count = creditDao.getNumberOfCredit(searchCreditVO, SystemUtil.getCurrentUserId());
//		} catch (RuntimeException e) {
//			logger.error(CcmFormat.formatErrorLog(e));
//			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
//			throw bpe;
//		}
//		//Example: "{totalResultNo:4,totalPageNo:1}"
//		sb.append("{totalResultNo:");
//		sb.append(count);
//		sb.append(",totalPageNo:");
//		int tp = (int) Math.ceil((double)count / (double)searchCreditVO.getRecPerPage());
//		sb.append(tp);
//		sb.append("}");
//		logger.info("Exit method getCreditSearchTotalPageNo.");
//		return sb.toString();
//	}
//	
	/**
	 * ***********************************************
	 * @author pengfei.wang
	 * create pending
	 * */
	//CreditAction.java connection here
	//paging
	public String getPendingCreditTotalPageNo(SearchCreditVO searchCreditVO)
			throws BPLException {
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = creditDao.getPendingNumberOfCredit(searchCreditVO, SystemUtil.getCurrentUserId());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)searchCreditVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info("Exit method getPendingCreditTotalPageNo.");
		return sb.toString();
	}
	//CreditAction.java connection here
	//search Credit table
	public String pendingCredit(SearchCreditVO searchCreditVO)
			throws BPLException {
		StringBuffer sb = new StringBuffer();
		List<?> l = null;
		try {
			l = creditDao.findPendingCredits(searchCreditVO, SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(searchCreditVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchCreditVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				if(i!=0) sb.append(",");
				sb.append(l.get(i).toString());
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info("Exit method pendingCredit.");
		return sb.toString();
	}
	
	/**
	 * @param creditDao the creditDao to set
	 */
	public void setCreditDao(ICreditDao creditDao) {
		this.creditDao = creditDao;
	}


	/**
	 * Search credit Assignment by SearchCreditVO.
	 * Return JSON string.
	 */
	public String searchCreditAssignment(SearchCreditVO svo) throws BPLException {
		String s;
		List<String> list = null;
		try {
			list = creditDao.searchCreditAssignment(svo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		s = svo.getListJson(list);
		logger.info("Exit method searchCreditAssignment.");
		return s;
	}
	
	/**
	 * Search credit Assignment TotalPageNo.
	 * Return JSON string.
	 */
	public String getCreditAssignmentSearchTotalPageNo(SearchCreditVO svo) throws BPLException {
		long totalResult = 0;
		try {
			totalResult = creditDao.getAssignmentCount(svo.getUserId());
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = svo.getTotalPageNoJson(totalResult);
		logger.info("Exit method getCreditAssignmentSearchTotalPageNo.");
		return s;
	}
	
	/**
	 * update by wei.su craedeted
	 * CreditbalanceRollback from Reconcile
	 */
	public void UpdateCreditbalanceRollback(Credit credit) throws BPLException {
	
		try {
			creditDao.updateCreditBalanceRollback(credit);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method UpdateCreditbalanceRollback.");
		
	}
}
