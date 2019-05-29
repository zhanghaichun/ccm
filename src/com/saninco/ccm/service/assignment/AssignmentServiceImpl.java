package com.saninco.ccm.service.assignment;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.lob.SerializableBlob;

import com.saninco.ccm.dao.IAssignmentDao;
import com.saninco.ccm.dao.IBanDao;
import com.saninco.ccm.dao.IVendorDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.util.BlobUtil;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.AssignmentVO;
/**
 * 
 * @author wei.su
 */
public class AssignmentServiceImpl implements IAssignmentService {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IAssignmentDao assignmentDao;
	private IVendorDao vendorDao;
	private IBanDao banDao;
	
	
	public AssignmentServiceImpl() {
	}
	
	public IVendorDao getVendorDao() {
		return vendorDao;
	}


	public void setVendorDao(IVendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}


	public IBanDao getBanDao() {
		return banDao;
	}


	public void setBanDao(IBanDao banDao) {
		this.banDao = banDao;
	}


	public IAssignmentDao getAssignmentDao() {
		return assignmentDao;
	}


	public void setAssignmentDao(IAssignmentDao assignmentDao) {
		this.assignmentDao = assignmentDao;
	}


	/**
	 * Search assignment by assignmentVO.
	 * Return JSON string.
	 */
	@SuppressWarnings("unchecked")
	public String searchAssignment(AssignmentVO assignmentVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Assignment Work Count View List", assignmentVO));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = assignmentDao.searchAssignments(assignmentVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(list!=null&&list.size()>0){
			sb.append("{begin:");
			sb.append(assignmentVO.getStartIndex()+1);
			sb.append(",end:");
			int size = list.size();
			sb.append(assignmentVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				Object str = list.get(i);
				if(str == null){
					break;
				}else {
					if(str instanceof SerializableBlob){
						str = BlobUtil.getBlobString((SerializableBlob)str);
					}
					if(i!=0) sb.append(",");
					sb.append(str.toString());
				}
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	
	/**
	 * Get total page number and result number.
	 * Return JSON string.
	 */
	public String getAssignmentSearchTotalPageNo(AssignmentVO assignmentVO)	throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Assignment Work Count total No", assignmentVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = assignmentDao.getNumberOfAssignment(assignmentVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)assignmentVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * Get total page number and result number.
	 * Return JSON string.
	 */
	public String getNumberOfAssignmentByVendorIdAndBanId(AssignmentVO assignmentVO)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Number Of Assignment By VendorId And BanId", assignmentVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = assignmentDao.getNumberOfAssignmentByVendorIdAndBanId(assignmentVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append(""+count+"");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * To add a new database records
	 * @param assignmentVO
	 */
	public void save(AssignmentVO assignmentVO){
		logger.info(CcmFormat.formatEnterLog("save Assignment", assignmentVO));
		if("all".equals(assignmentVO.getVendorId()))
		{
			assignmentVO.setVendorId("0");
		}
		Vendor vendor=vendorDao.findById(Integer.valueOf(assignmentVO.getVendorId()));
		if("all".equals(assignmentVO.getBanId()))
		{
			assignmentVO.setBanId("0");
		}
		System.out.println(assignmentVO.getBanId());
		Ban ban=banDao.findById(Integer.valueOf(assignmentVO.getBanId()));
		Ban banAssignment=new Ban();
		banAssignment.setVendor(vendor);
		banAssignment.setCreatedBy(SystemUtil.getCurrentUserId());
		banAssignment.setCreatedTimestamp(new Date());
		banAssignment.setModifiedBy(SystemUtil.getCurrentUserId());
		banAssignment.setModifiedTimestamp(new Date());
		banAssignment.setRecActiveFlag("Y");
		if("all".equals(assignmentVO.getAnalystId()))
		{
			banAssignment.setAnalystId(null);
		}
		else
		{
			banAssignment.setAnalystId(Integer.valueOf(assignmentVO.getAnalystId()));
		}
		
		if("all".equals(assignmentVO.getApprover1Id()))
		{
			banAssignment.setApprover1Id(null);
		}
		else
		{
			banAssignment.setApprover1Id(Integer.valueOf(assignmentVO.getApprover1Id()));
		}
		
		if("all".equals(assignmentVO.getApprover2Id()))
		{
			banAssignment.setApprover2Id(null);
		}
		else
		{
			banAssignment.setApprover2Id(Integer.valueOf(assignmentVO.getApprover2Id()));
		}
		
		if("all".equals(assignmentVO.getApprover3Id()))
		{
			banAssignment.setApprover3Id(null);
		}
		else
		{
			banAssignment.setApprover3Id(Integer.valueOf(assignmentVO.getApprover3Id()));
		}
		banDao.save(banAssignment);
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * According to this iD modification
	 * @param assignmentVO
	 */
	public void update(AssignmentVO assignmentVO) throws Exception{
		logger.info(CcmFormat.formatEnterLog("update Assignment", assignmentVO));
		Vendor vendor;
		Ban ban;
		Ban banAssignment=new Ban();
		try {
			banAssignment = banDao.findById(Integer.valueOf(assignmentVO.getBanAssignmentId()));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		banAssignment.setModifiedBy(SystemUtil.getCurrentUserId());
		banAssignment.setModifiedTimestamp(new Date());
		if("all".equals(assignmentVO.getApprover1Id()))
		{
			banAssignment.setApprover1Id(null);
		}
		else
		{
			banAssignment.setApprover1Id(Integer.valueOf(assignmentVO.getApprover1Id()));
		}
		if("all".equals(assignmentVO.getApprover2Id()))
		{
			banAssignment.setApprover2Id(null);
		}
		else
		{
			banAssignment.setApprover2Id(Integer.valueOf(assignmentVO.getApprover2Id()));
		}
		if("all".equals(assignmentVO.getApprover3Id()))
		{
			banAssignment.setApprover3Id(null);
		}
		else
		{
			banAssignment.setApprover3Id(Integer.valueOf(assignmentVO.getApprover3Id()));
		}

		try {
			banDao.merge(banAssignment);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * According to delete a record iD
	 * @param assignmentVO
	 */
	public void delete(int id) throws RuntimeException{
		logger.info(CcmFormat.formatEnterLog("de;ete Assignment", id));
		Ban banAssignment=new Ban();
		try {
			banAssignment =banDao.findById(id);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		banAssignment.setRecActiveFlag("N");
		banDao.merge(banAssignment);
		logger.info(CcmFormat.formatExitLog());
	}
	
}
