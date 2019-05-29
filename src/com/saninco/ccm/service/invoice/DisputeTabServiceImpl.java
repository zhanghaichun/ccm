package com.saninco.ccm.service.invoice;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IAccountCodelDao;
import com.saninco.ccm.dao.IAttachmentFileDao;
import com.saninco.ccm.dao.IBanDao;
import com.saninco.ccm.dao.ICurrencyDao;
import com.saninco.ccm.dao.IDisputeDao;
import com.saninco.ccm.dao.IDisputeReasonDao;
import com.saninco.ccm.dao.IDisputeStatusDao;
import com.saninco.ccm.dao.IInvoiceDao;
import com.saninco.ccm.dao.IInvoiceDetailDao;
import com.saninco.ccm.dao.IOriginatorDao;
import com.saninco.ccm.dao.IProposalDao;
import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.dao.SysConfigDAOImpl;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AccountCode;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.DisputeReason;
import com.saninco.ccm.model.DisputeStatus;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.Originator;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.model.ScoaSource;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchInvoiceVO;

public class DisputeTabServiceImpl implements IDisputeTabService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private IBanDao banDao;
	private IProposalDao proposalDao;
	private ICurrencyDao currencyDao;
	private IOriginatorDao originatorDao;
	private IInvoiceDao invoiceDao;
	private IDisputeDao disputeDao;
	private IDisputeReasonDao disputeReasonDao;
	private IDisputeStatusDao disputeStatusDao;
	private IAccountCodelDao accountCodelDao;
	private IAttachmentFileDao attachmentFileDao;
	private IInvoiceDetailDao invoiceDetailDao;
	private ISysConfigDAO sysConfigDAO;
	
	/**
	 * 
	 * @author wei.su
	 * @param searchInvoiceVO
	 */
	public Integer saveProposalToInvoiceItem(SearchInvoiceVO searchInvoiceVO) throws RuntimeException
	{
		Integer proposalId=null;
		@SuppressWarnings("unused")
		DisputeReason disputeReason=disputeReasonDao.findById(Integer.valueOf(searchInvoiceVO.getDisputeReason()));
		@SuppressWarnings("unused")
		Originator originator=originatorDao.findById(Integer.valueOf(searchInvoiceVO.getOriginatorId()));
		@SuppressWarnings("unused")
		AccountCode accountCode=accountCodelDao.findById(Integer.valueOf(searchInvoiceVO.getAccountCodeId()));
		Invoice invoice=invoiceDao.findById(Integer.valueOf(searchInvoiceVO.getInvoiceId()));
		Proposal proposal=new Proposal();
		ScoaSource scoaSource=new ScoaSource();
		proposal.setNotes(searchInvoiceVO.getNote());
		proposal.setCreatedBy(SystemUtil.getCurrentUserId());
		proposal.setCreatedTimestamp(new Date());
		proposal.setModifiedBy(SystemUtil.getCurrentUserId());
		proposal.setModifiedTimestamp(new Date());
		proposal.setDescription(searchInvoiceVO.getDescription());
		proposal.setDisputeReason(disputeReason);
		proposal.setOriginator(originator);
		proposal.setAccountCode(accountCode);
		scoaSource.setId(730101);
		proposal.setScoaSource(scoaSource);
		proposal.setProposalFlag("1");
		proposal.setCircuitNumber(searchInvoiceVO.getCricuitNumber());
		proposal.setServiceType(searchInvoiceVO.getServiceType());
		proposal.setInvoice(invoice);
//		if(searchInvoiceVO.getDisputeAmount()==""){
//			proposal.setDisputeAmount(Double.valueOf(0));
//		}else{
			proposal.setDisputeAmount(Double.valueOf(searchInvoiceVO.getDisputeAmount()));
//		}
		proposal.setRecActiveFlag("Y");
		proposal.setInvoice(invoice);
		try {
			proposalId = proposalDao.saveProposal(proposal);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		return proposalId;
	}
	
	/**
	 * @author wei.su
	 * Get Invoice Page dispute Tab Info Totail Number
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getDisputeTabTotalNoOfInoiceDetail(SearchInvoiceVO searchInvoiceVO)throws BPLException {
		logger.info(CcmFormat.formatEnterLog( "Enter method getDisputeTabTotalNoOfInoiceDetail.", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		try {
			Integer totalResult = proposalDao.getDisputeTabTotalNoOfInoiceDetail(searchInvoiceVO);
			sb.append(searchInvoiceVO.getTotalPageNoJson(totalResult));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author wei.su
	 * Get Invoice Page dispute Tab Info List
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String searchDisputeTabOfInoiceDetail(SearchInvoiceVO searchInvoiceVO)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method searchDisputeTabOfInoiceDetail.", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<String> cls = null;
		try {
			cls = proposalDao.searchDisputeTabOfInoiceDetail(searchInvoiceVO);
			sb.append(searchInvoiceVO.getListJsonCompatible(cls));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author wei.su
	 * Get Invoice Page dispute Tab Info List
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getProposalDataLeftByInvoiceId(SearchInvoiceVO searchInvoiceVO,String[] proposalIds)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getProposalDataLeftByInvoiceId.", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		List<String> cls = null;
		String sProposalIds="";
		for (int i = 0; i < proposalIds.length; i++) {
			if (i == (proposalIds.length - 1)) {
				sProposalIds = sProposalIds + proposalIds[i];
				break;
			}
			sProposalIds =sProposalIds+ proposalIds[i]+",";
		}
		try {
			System.out.println(proposalIds.toString());
			cls = proposalDao.getProposalDataLeftByInvoiceId(searchInvoiceVO, sProposalIds);
			sb.append(searchInvoiceVO.getListJsonCompatible(cls));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		} 
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author wei.su
	 * Get Invoice Page dispute Tab Info List
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getProposalDataRightByInvoiceIdAndDisputeId(SearchInvoiceVO searchInvoiceVO)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getProposalDataRightByInvoiceIdAndDisputeId.", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		StringBuffer proposalIds = new StringBuffer();
		String[] proposalsId = searchInvoiceVO.getProposalsId();
		List<String> cls = null;
		for (int i = 0; i < proposalsId.length; i++) {
			if(i != 0) proposalIds.append(",");
			proposalIds.append(proposalsId[i]);
		}
		try {
			cls = proposalDao.getProposalDataRightByInvoiceIdAndDisputeId(searchInvoiceVO, proposalIds.toString());
			sb.append(searchInvoiceVO.getListJsonCompatible(cls));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		} 
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * According to the selected proposalId, the record of changes to the list under disputeid selected disputeid 
	 * @author wei.su
	 * @param proposalIds
	 * @param disputeId
	 * @throws BPLException
	 */
	public void updateDisputeTabMoveSelectGroupOfInvoiceDetail(String[] proposalIds,int disputeId,double totalDisputeAmount,String emailFlag) throws RuntimeException{
		logger.info(CcmFormat.formatEnterLog("Enter method updateDisputeTabMoveSelectGroupOfInvoiceDetail.", totalDisputeAmount));
		String sProposalIds="";
		Dispute dispute = new Dispute();
		for (int i = 0; i < proposalIds.length; i++) {
			if (i == (proposalIds.length - 1)) {
				sProposalIds = sProposalIds + proposalIds[i];
				break;
			}
			 sProposalIds =sProposalIds+ proposalIds[i]+",";
		}
		try {
			dispute = disputeDao.findById(disputeId);
			dispute.setDisputeAmount(totalDisputeAmount+(dispute.getDisputeAmount()==null?0.00:dispute.getDisputeAmount()));
			if("Y".equals(dispute.getFlagShortpaid())){
				dispute.setReserveAmount(getReserveAmount(dispute.getConfidenceLevel(),dispute.getDisputeAmount()));
				dispute.setOutstandingReserveAmount(dispute.getReserveAmount());
			}
			dispute.setModifiedBy(SystemUtil.getCurrentUserId());
			dispute.setModifiedTimestamp(new Date());
			dispute.setEmailFlag(emailFlag == null ? dispute.getEmailFlag() : emailFlag);
			proposalDao.updateDisputeTabOfInvoiceDetail(sProposalIds, disputeId);
			disputeDao.merge(dispute);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	
	/**
	 * According to the selected proposalId, the record of changes to the list under disputeid selected disputeid 
	 * @author wei.su
	 * @param proposalIds
	 * @param disputeId
	 * @throws BPLException
	 */
	public void updateDisputeTabMoveSelectGroupOfInvoiceDetailRight(String[] proposalIds,int disputeId,int fromDisputeId,double totalDisputeAmount,String flagShortpaid) throws RuntimeException{
		logger.info(CcmFormat.formatEnterLog("Enter method updateDisputeTabMoveSelectGroupOfInvoiceDetail.", ""));
		String sProposalIds="";
		Dispute disputeTo = new Dispute();
		Dispute disputeFrom = new Dispute();
		for (int i = 0; i < proposalIds.length; i++) {
			if (i == (proposalIds.length - 1)) {
				sProposalIds = sProposalIds + proposalIds[i];
				break;
			}
			 sProposalIds =sProposalIds+ proposalIds[i]+",";
		}
		try {
			disputeTo = disputeDao.findById(disputeId);
			disputeFrom = disputeDao.findById(fromDisputeId);
			disputeTo.setDisputeAmount(totalDisputeAmount+(disputeTo.getDisputeAmount()==null?0.00:disputeTo.getDisputeAmount()));
			if("Y".equals(disputeTo.getFlagShortpaid()))
			{
				disputeTo.setReserveAmount(getReserveAmount(disputeTo.getConfidenceLevel(),disputeTo.getDisputeAmount()));
			}else{
				disputeTo.setReserveAmount(0d);
			}
			disputeTo.setOutstandingReserveAmount(disputeTo.getReserveAmount());
			disputeTo.setModifiedBy(SystemUtil.getCurrentUserId());
			disputeTo.setModifiedTimestamp(new Date());
			disputeFrom.setDisputeAmount((disputeFrom.getDisputeAmount()==null?0.00:disputeFrom.getDisputeAmount())-totalDisputeAmount);
			if("Y".equals(flagShortpaid))
			{
				disputeFrom.setReserveAmount(getReserveAmount(disputeFrom.getConfidenceLevel(),disputeFrom.getDisputeAmount()));
			}else{
				disputeFrom.setReserveAmount(0d);
			}
			disputeFrom.setOutstandingReserveAmount(disputeFrom.getReserveAmount());
			disputeFrom.setModifiedBy(SystemUtil.getCurrentUserId());
			disputeFrom.setModifiedTimestamp(new Date());
			proposalDao.updateDisputeTabOfInvoiceDetail(sProposalIds, disputeId);
			disputeDao.merge(disputeTo);
			disputeDao.merge(disputeFrom);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	private Double getReserveAmount(Integer confidenceLevel,
			Double disputeAmount) {
        confidenceLevel = (confidenceLevel == null ? 0 : confidenceLevel);
        
        //update mingyang.li by 2016-11-01 start
//		if(confidenceLevel > 0 && confidenceLevel < 100){
//			if(confidenceLevel.intValue() >= 2){
//				return (disputeAmount == null ? 0 : disputeAmount)*0.5;
//			}else{
//				return (disputeAmount == null ? 0 : disputeAmount);
//			}
//		}else{
//			return 0d;
//		}
		
		if ( confidenceLevel == 0 ) 
        {
        	return (disputeAmount == null ? 0 : disputeAmount)*1;
        }
        else if (confidenceLevel == 100)
        {
        	return (disputeAmount == null ? 0 : disputeAmount)*0;
        }
        else
        {
        	return (disputeAmount == null ? 0 : disputeAmount)*0.5;
        }
		//update mingyang.li by 2016-11-01 end
	}

	/**
	 * The proposal will be selected from the current dispute removed 
	 * @author wei.su
	 * @param proposalIds
	 * @throws BPLException
	 */
	public void updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail(String[] proposalIds,int disputeId,double totalDisputeAmount) throws RuntimeException{
		
		logger.info(CcmFormat.formatEnterLog("Enter method updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail.", ""));
		String sProposalIds="";
		Dispute dispute = new Dispute();
		for (int i = 0; i < proposalIds.length; i++) {
			if (i == (proposalIds.length - 1)) {
				sProposalIds = sProposalIds + proposalIds[i];
				break;
			}
			 sProposalIds =sProposalIds+ proposalIds[i]+",";
		}
		try {
			dispute = disputeDao.findById(disputeId);
			dispute.setModifiedBy(SystemUtil.getCurrentUserId());
			dispute.setModifiedTimestamp(new Date());
			dispute.setDisputeAmount((dispute.getDisputeAmount()==null?0.00:dispute.getDisputeAmount())- totalDisputeAmount);
			proposalDao.updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail(sProposalIds);
			disputeDao.merge(dispute);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	
	/**
	 * Delete the current dispute and the proposal remove the dispute in the current group
	 * @author wei.su
	 * @param proposalIds
	 * @throws BPLException
	 */
	public void deleteDisputeOfInvoiceDetail(String[] proposalIds,Integer disputeId,double totalDisputeAmount) throws RuntimeException{
		logger.info(CcmFormat.formatEnterLog("Enter method deleteDisputeOfInvoiceDetail.", ""));
		String sProposalIds="";
		Dispute dispute = new Dispute();
		for (int i = 0; i < proposalIds.length; i++) {
			if (i == (proposalIds.length - 1)) {
				sProposalIds = sProposalIds + proposalIds[i];
				break;
			}
			 sProposalIds =sProposalIds+ proposalIds[i]+",";
		}
		System.out.println(sProposalIds);
		dispute = disputeDao.findById(disputeId);
		dispute.setDisputeAmount(0.00);
		dispute.setModifiedBy(SystemUtil.getCurrentUserId());
		dispute.setModifiedTimestamp(new Date());
		try {
			if("".endsWith(sProposalIds)){
				dispute.setRecActiveFlag("N");
				proposalDao.updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetailByDisputeId(disputeId);
				disputeDao.merge(dispute);	
			}else{
				dispute.setRecActiveFlag("N");
				proposalDao.updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetailByDisputeId(disputeId);
				disputeDao.merge(dispute);
			}
	
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * A new record, and the disputeIndex will be selected instead of the
	 * proposal the disputeid
	 * @author wei.su
	 * @param proposalIds
	 * @param calimNumber
	 * @throws BPLException
	 */
	public synchronized void updateDisputeTabGreateOneGroupOfInvoiceDetail(String[] proposalIds,int disputeReasonId,int disputeStatusId,int invoiceId,double totalDisputeAmount,String emailFlag) throws RuntimeException{
		logger.info(CcmFormat.formatEnterLog("Enter method updateDisputeTabGreateOneGroupOfInvoiceDetail.", ""));
		System.out.println(proposalIds[0]);
		String sProposalIds="";
		String proposalIdForGetDisputeReasonAcronym="";
		for (int i = 0; i < proposalIds.length; i++) {
			if (i == (proposalIds.length - 1)) {
				sProposalIds = sProposalIds + proposalIds[i];
				break;
			}
			 sProposalIds =sProposalIds+ proposalIds[i]+",";
		}
		if(sProposalIds.indexOf(",") != -1){
			proposalIdForGetDisputeReasonAcronym = sProposalIds.substring(0,sProposalIds.indexOf(","));
		}else{
			proposalIdForGetDisputeReasonAcronym = sProposalIds;
		}
		StringBuffer calimNumber=new StringBuffer();
		Dispute dispute =new Dispute();
		Invoice invoice=new Invoice();
		DisputeStatus disputeStatus=new DisputeStatus();
		DisputeReason disputeReason=new DisputeReason();
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfInputSS = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String date=sdfInput.format(new Date());
		String datess=sdfInputSS.format(new Date());
		//CalimNumber set of values
		calimNumber.append(""+invoiceDao.getVendorAcronymByInvoiceId(Integer.valueOf(invoiceId))+"-");
		calimNumber.append(""+disputeReasonDao.getDisputeReasonAcronymByProrosalId(Integer.valueOf(proposalIdForGetDisputeReasonAcronym))+"-");
		calimNumber.append(""+date+"-");
		calimNumber.append(""+(proposalDao.getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId(calimNumber.toString())));
		invoice=invoiceDao.findById(Integer.valueOf(invoiceId));
		disputeReason=disputeReasonDao.findById(Integer.valueOf(disputeReasonId));
		disputeStatus=disputeStatusDao.findById(1);
		dispute.setClaimNumber(calimNumber.toString());
		dispute.setDisputeReason(disputeReason);
		dispute.setCreatedBy(SystemUtil.getCurrentUserId());
		dispute.setCreatedTimestamp(new Date());
		dispute.setModifiedTimestamp(new Date());
		dispute.setModifiedBy(SystemUtil.getCurrentUserId());
		dispute.setRecActiveFlag("Y");
		dispute.setFlagRecurring("N");
		dispute.setFlagShortpaid("N");
		dispute.setEmailCopyAddress(sysConfigDAO.getSystemDisputeEmailConfig().getSystemDisputeEmailCopyToServer());
		dispute.setDisputeStatus(disputeStatus);
		dispute.setStatusTimestamp(new Date());
		String disputeNumber="D"+datess.toString()+"";
		dispute.setDisputeNumber(disputeNumber);
		dispute.setDisputeAmount(Double.valueOf(totalDisputeAmount));
		dispute.setInvoice(invoice);
		dispute.setOutstandingReserveAmount(dispute.getReserveAmount());
		dispute.setEmailFlag(emailFlag);
		try {
			proposalDao.updateDisputeTabOfInvoiceDetail(sProposalIds, disputeDao.saveAndReturnDisputeId(dispute));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * A new record, and the disputeIndex will be selected instead of the
	 * proposal the disputeid
	 * @author wei.su
	 * @param proposalIds
	 * @param calimNumber
	 * @throws BPLException
	 */
	public synchronized void updateDisputeTabGreateOneGroupOfInvoiceDetailRight(String[] proposalIds,int disputeReasonId,int disputeStatusId,int fromDisputeId,int invoiceId,double totalDisputeAmount) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Enter method updateDisputeTabGreateOneGroupOfInvoiceDetail.", ""));
		String sProposalIds="";
		String proposalIdForGetDisputeReasonAcronym="";
		for (int i = 0; i < proposalIds.length; i++) {
			if (i == (proposalIds.length - 1)) {
				sProposalIds = sProposalIds + proposalIds[i];
				break;
			}
			 sProposalIds =sProposalIds+ proposalIds[i]+",";
		}
		if(sProposalIds.indexOf(",") != -1){
			proposalIdForGetDisputeReasonAcronym = sProposalIds.substring(0,sProposalIds.indexOf(","));
		}else{
			proposalIdForGetDisputeReasonAcronym = sProposalIds;
		}
		StringBuffer calimNumber=new StringBuffer();
		Dispute dispute =new Dispute();
		Dispute disputeFrom = new Dispute();
		Invoice invoice=new Invoice();
		DisputeStatus disputeStatus=new DisputeStatus();
		DisputeReason disputeReason=new DisputeReason();
		SimpleDateFormat sdfInput = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat sdfInputSS = new SimpleDateFormat("yyyyMMddHHmmssSS");
		String date=sdfInput.format(new Date());
		String datess=sdfInputSS.format(new Date());
		calimNumber.append(""+invoiceDao.getVendorAcronymByInvoiceId(Integer.valueOf(invoiceId))+"-");
		calimNumber.append(""+disputeReasonDao.getDisputeReasonAcronymByProrosalId(Integer.valueOf(proposalIdForGetDisputeReasonAcronym))+"-");
		calimNumber.append(""+date+"-");
		calimNumber.append(""+(proposalDao.getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId(calimNumber.toString())));
		invoice=invoiceDao.findById(Integer.valueOf(invoiceId));
		disputeReason=disputeReasonDao.findById(Integer.valueOf(disputeReasonId));
		disputeStatus=disputeStatusDao.findById(1);
		disputeFrom = disputeDao.findById(fromDisputeId);
		dispute.setClaimNumber(calimNumber.toString());
		dispute.setDisputeReason(disputeReason);
		dispute.setCreatedBy(SystemUtil.getCurrentUserId());
		dispute.setCreatedTimestamp(new Date());
		dispute.setModifiedTimestamp(new Date());
		dispute.setRecActiveFlag("Y");
		dispute.setFlagRecurring("N");
		dispute.setFlagShortpaid("N");
		dispute.setEmailCopyAddress(sysConfigDAO.getSystemDisputeEmailConfig().getSystemDisputeEmailCopyToServer());
		dispute.setDisputeStatus(disputeStatus);
		dispute.setStatusTimestamp(new Date());
		String disputeNumber="D"+datess.toString()+"";
		dispute.setDisputeNumber(disputeNumber);
		dispute.setDisputeAmount(totalDisputeAmount);
		disputeFrom.setDisputeAmount(disputeFrom.getDisputeAmount()-totalDisputeAmount);
		if("Y".equals(disputeFrom.getFlagShortpaid())){
			disputeFrom.setReserveAmount(getReserveAmount(disputeFrom.getConfidenceLevel(),disputeFrom.getDisputeAmount()));
			disputeFrom.setOutstandingReserveAmount(disputeFrom.getReserveAmount());
		}
		dispute.setInvoice(invoice);
		dispute.setReserveAmount(0d);
		dispute.setOutstandingReserveAmount(0d);
		try {
			proposalDao.updateDisputeTabOfInvoiceDetail(sProposalIds, disputeDao.saveAndReturnDisputeId(dispute));
			disputeDao.merge(disputeFrom);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		
	}
	
	/**
	 * A new record, and the disputeIndex will be selected instead of the
	 * proposal the disputeid
	 * @author wei.su
	 * @param proposalIds
	 * @param calimNumber
	 * @throws BPLException
	 */
	public synchronized void updateDisputeTabGreateNewGroupForEachSelectedOfInvoiceDetail(String proposalData,Integer invoiceId,String emailFlag) throws Exception{
		logger.info(CcmFormat.formatEnterLog("Enter method updateDisputeTabGreateNewGroupForEachSelectedOfInvoiceDetail.", ""));
		System.out.println(proposalData);
		String [] proposalDataArr1=proposalData.split("\\$");
		for (int i = 0; i < proposalDataArr1.length; i++) {
			try {
				String[] proposalDataArr2=((proposalDataArr1[i].toString()).split("\\,"));
				System.out.println(proposalDataArr2.toString());
				Integer proposalId=Integer.valueOf(proposalDataArr2[0]);
				Integer disputeReasonId=Integer.valueOf(proposalDataArr2[1]);
				StringBuffer calimNumber=new StringBuffer();
				Dispute dispute =new Dispute();
				Invoice invoice=new Invoice();
				DisputeStatus disputeStatus=new DisputeStatus();
				DisputeReason disputeReason=new DisputeReason();
				SimpleDateFormat sdfInput = new SimpleDateFormat("yyyyMMdd");
				SimpleDateFormat sdfInputSS = new SimpleDateFormat("yyyyMMddHHmmssSS");
				String date=sdfInput.format(new Date());
				String datess=sdfInputSS.format(new Date());
				//CalimNumber set of values
				calimNumber.append(""+invoiceDao.getVendorAcronymByInvoiceId(Integer.valueOf(invoiceId))+"-");
				calimNumber.append(""+disputeReasonDao.getDisputeReasonAcronymByProrosalId(proposalId)+"-");
				calimNumber.append(""+date+"-");
				calimNumber.append(""+(proposalDao.getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId(calimNumber.toString())));
				invoice=invoiceDao.findById(Integer.valueOf(invoiceId));
				disputeReason=disputeReasonDao.findById(Integer.valueOf(disputeReasonId));
				disputeStatus=disputeStatusDao.findById(1);
				dispute.setClaimNumber(calimNumber.toString());
				dispute.setDisputeReason(disputeReason);
				dispute.setCreatedBy(SystemUtil.getCurrentUserId());
				dispute.setCreatedTimestamp(new Date());
				dispute.setModifiedTimestamp(new Date());
				dispute.setRecActiveFlag("Y");
				dispute.setDisputeAmount(Double.valueOf(proposalDataArr2[2]));
				String disputeNumber="D"+datess.toString()+"";
				dispute.setDisputeNumber(disputeNumber);
				dispute.setFlagRecurring("N");
				dispute.setFlagShortpaid("N");
				dispute.setEmailCopyAddress(sysConfigDAO.getSystemDisputeEmailConfig().getSystemDisputeEmailCopyToServer());
				dispute.setInvoice(invoice);
				dispute.setDisputeStatus(disputeStatus);
				dispute.setStatusTimestamp(new Date());
				dispute.setOutstandingReserveAmount(dispute.getReserveAmount());
				dispute.setEmailFlag(emailFlag);
				proposalDao.updateDisputeTabOfInvoiceDetail(proposalId.toString(), disputeDao.saveAndReturnDisputeId(dispute));
			} catch (NumberFormatException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			}
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Get the disputeamount basis
	 * @author wei.su
	 */
	public String getDisputeTotalAmountByInvoiceId(int invoiceId) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Enter method getDisputeTotalAmountByInvoiceId.", ""));
		String disputeAmount="0";
		List list = new ArrayList();
		try {
			list=disputeDao.getDisputeTotalAmountByInvoiceId(invoiceId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		} 
		if (list.get(0) == null) {
			logger.info(CcmFormat.formatExitLog());
			return disputeAmount;
		} else {
			logger.info(CcmFormat.formatExitLog());
			return list.get(0).toString();
		}
	}
	

	/**
	 * delete proposal from proposallist
	 * @author wei.su
	 * @throws RuntimeException
	 */
	public void deleteProposalFromProposalList(String proposalIds,String invoiceItemIds,String disputeReasonText)throws RuntimeException{
		logger.info(CcmFormat.formatEnterLog("Enter method deleteProposalFromProposalList.", ""));
		Proposal proposal = new Proposal();
		String[] invoiceItems = invoiceItemIds.split(",");
		String[] proposals = proposalIds.split(",");
		for(int i=0; i<proposals.length; i++){			
			String proposalId = proposals[i];
			String invoiceItemId;
			if(" ".equals(invoiceItems[i])){			
				invoiceItemId = "";
			}else{
				invoiceItemId = invoiceItems[i];
			}
			try {
				if("".equals(invoiceItemId)){
					proposal=proposalDao.findById(Long.valueOf(proposalId));
					Originator originator = proposal.getOriginator(); 
					if(!("".equals(disputeReasonText))){
						proposal.setDisputeReasonText(disputeReasonText);
					}
					proposal.setRecActiveFlag("N");
					proposalDao.merge(proposal);
				}else{
					proposal=proposalDao.findById(Long.valueOf(proposalId));
					double disputeAmount;
					double paymentAmount;
					if(null==proposal.getDisputeAmount()){
						disputeAmount=0.00;
					}else{
						disputeAmount=proposal.getDisputeAmount();
					}
					if(null==proposal.getPaymentAmount()){
						paymentAmount=0.00;
					}else{
						paymentAmount=proposal.getPaymentAmount();
					}
					if(!("".equals(disputeReasonText))){
						proposal.setDisputeReasonText(disputeReasonText);
					}
					proposal.setPaymentAmount((disputeAmount+paymentAmount));
					proposal.setDisputeReason(null);
					proposal.setDisputeAmount(0.00);
					proposal.setModifiedBy(SystemUtil.getCurrentUserId());
					proposal.setModifiedTimestamp(new Date());
					proposal.setNotes("");
					proposal.setAttachmentPoint(null);
					proposalDao.merge(proposal);
				}
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			}
		}
		logger.info(CcmFormat.formatExitLog());
	}
	 
	/**
	 * According to invoiceId get all the dispute amount
	 * @author wei.su
	 */
	public String getDisputeTotalNoByInvoiceId(SearchInvoiceVO searchInvoiceVO)throws BPLException{

		logger.info(CcmFormat.formatEnterLog("Enter method getDisputeTotalNoByInvoiceId.", ""));
		StringBuffer sb=new StringBuffer();
		int count =0;
		try {
			count=disputeDao.getDisputeTotalNoByInvoiceId(searchInvoiceVO);
			sb.append(searchInvoiceVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * According to invoiceId get all of the dispute
	 * @author wei.su
	 * @throws Exception 
	 */
	public String searchDisputesByInvoiceId(SearchInvoiceVO searchInvoiceVO)throws Exception{
		logger.info(CcmFormat.formatEnterLog("Enter method searchDisputesByInvoiceId.", searchInvoiceVO));
		List<String> l = new ArrayList<String>();
		StringBuffer sb=new StringBuffer();
		try {
			l = disputeDao.searchDisputesByInvoiceId(searchInvoiceVO);
			sb.append(searchInvoiceVO.getListJsonCompatible(l));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * According to invoiceId get all of the dispute
	 * @author wei.su
	 * @throws Exception 
	 */
	public String findByInvoiceIdAndReturnJSON(SearchInvoiceVO searchInvoiceVO)throws Exception{
		logger.info(CcmFormat.formatEnterLog("Enter method findByInvoiceIdAndReturnJSON.", searchInvoiceVO));
		List<String> l = new ArrayList<String>();
		StringBuffer sb=new StringBuffer();
		try {
			l = disputeDao.findByInvoiceIdAndReturnStringList(searchInvoiceVO);
			sb.append(searchInvoiceVO.getListJsonCompatible(l));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * get all of the originator return json
	 * @author wei.su
	 * @throws Exception 
	 */
	public String getOriginatorListAndReturnJSON(SearchInvoiceVO searchInvoiceVO)throws Exception{
		logger.info(CcmFormat.formatEnterLog("Enter method getOriginatorListAndReturnJSON.", searchInvoiceVO));
		List<String> l = new ArrayList<String>();
		StringBuffer sb=new StringBuffer();
		try {
			l = originatorDao.getOriginatorAndReturnStringList(searchInvoiceVO);
			sb.append(searchInvoiceVO.getListJsonCompatible(l));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author wei.su
	 * According to disputeId and invoiceId get meet the conditions of the proposal Totail Number
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(SearchInvoiceVO searchInvoiceVO)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.", searchInvoiceVO));
		StringBuffer sb = new StringBuffer();
		try {
			Integer totalResult = proposalDao.getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(searchInvoiceVO);
			sb.append(searchInvoiceVO.getTotalPageNoJson(totalResult));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author wei.su
	 * According to disputeId and invoiceId get meet the conditions of the proposal
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(SearchInvoiceVO searchInvoiceVO)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Enter method searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId.", ""));
		StringBuffer sb = new StringBuffer();
		List<String> cls = null;
		try {
			cls = proposalDao.searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(searchInvoiceVO);
			sb.append(searchInvoiceVO.getListJsonCompatible(cls));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}


	/**
	 * @author wei.su
	 * Down Excel
	 * */
	public String saveExcelByProposalListInDisputeTabByInvoiceIdLeftToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Exit method createPaymentToExcel.", searchInvoiceVO));

		String invoiceId = searchInvoiceVO.getInvoiceId();

		Invoice invoiveModel = invoiceDetailDao.findInvoiceById(Integer.parseInt(invoiceId));

		// Invoice Number.
		String invoiceNumber = invoiveModel.getInvoiceNumber();
		
		try {
			
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = proposalDao.getDisputeTabTotalNoOfInoiceDetail(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, invoiceNumber);
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = proposalDao.getProposalsOfInoiceDetailDisputeTabByInvoiceIdDownExcel(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = proposalDao.getProposalsOfInoiceDetailDisputeTabByInvoiceIdDownExcel(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list
								.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 25, 25, 25, 25, 25, 25, 25,
					25, 25, 20, 20 });
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	/**
	 * @author wei.su
	 * Down Excel
	 * */
	public String saveExcelByProposalListInDisputeTabByInvoiceIdAndDiputeIdRightToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Exit method saveExcelByProposalListInDisputeTabByInvoiceIdAndDiputeIdRightToExcel.", searchInvoiceVO));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = proposalDao.getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(searchInvoiceVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "proposal0");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchInvoiceVO.setPageNo(1);
				searchInvoiceVO.setRecPerPage((int) count);
				list = proposalDao.getProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeIdDownExcel(searchInvoiceVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchInvoiceVO.setPageNo(j + 1);
					searchInvoiceVO.setRecPerPage(recPerPage);
					list = proposalDao.getProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeIdDownExcel(searchInvoiceVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list
								.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 25, 25, 25, 25, 25, 25, 25,
					25, 25, 20, 20 });
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	/***
	 * @author wei.su
	 * Modify dispute basic information
     * update 2011-09-19 by hongtao
	 * @throws RuntimeException
	 */
	public void updateDispute(String ticketNumber,String claimNumber, int disputeId,String disputeNotes,int originatorId,String flagRecurring,String flagShortpaid,String confidenceLevel,String emailCopyAddress,Double reservedAmount,String emailFlag) throws RuntimeException{
		logger.info(CcmFormat.formatEnterLog("Exit method updateDispute.", ""));
		try {
			Dispute dispute = new Dispute();
			dispute = disputeDao.findById(disputeId);
			dispute.setTicketNumber(ticketNumber);
			dispute.setClaimNumber(claimNumber);
			dispute.setNotes(disputeNotes);
			dispute.setOriginator(originatorDao.findById(originatorId));
			dispute.setEmailCopyAddress(emailCopyAddress);
			dispute.setEmailFlag(emailFlag);
			if("Y".equals(flagRecurring)){
				dispute.setFlagRecurring(flagRecurring);
			}else{
				dispute.setFlagRecurring("N");
			}
			if("Y".equals(flagShortpaid)){
				dispute.setFlagShortpaid(flagShortpaid);
				dispute.setModifiedBy(SystemUtil.getCurrentUserId());
				dispute.setModifiedTimestamp(new Date());
				dispute.setReserveAmount(reservedAmount);
				dispute.setOutstandingReserveAmount(dispute.getReserveAmount());
				dispute.setConfidenceLevel(new Double(confidenceLevel).intValue());
			}else{
				dispute.setModifiedBy(SystemUtil.getCurrentUserId());
				dispute.setModifiedTimestamp(new Date());
				dispute.setFlagShortpaid("N");
				dispute.setReserveAmount(reservedAmount);
				dispute.setOutstandingReserveAmount(dispute.getReserveAmount());
				dispute.setConfidenceLevel(new Double(confidenceLevel).intValue());
			}
			if(confidenceLevel==""){
				dispute.setConfidenceLevel(0);
			}else{
				dispute.setConfidenceLevel(new Double(confidenceLevel).intValue());
			}
			disputeDao.merge(dispute);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * get proposal by proposalId
	 * @author wei.su
	 * @throws Exception 
	 */
	public String getProposalByProposalId(Long ProposalId)throws Exception{
		logger.info(CcmFormat.formatEnterLog("Enter method getProposalByProposalId.", ""));
		String proposalJson = "";
		Proposal proposal = new Proposal();
		try {
			proposal = proposalDao.findById(ProposalId);
			proposalJson="{disputeId:\""+(proposal.getDispute()==null?"":proposal.getDispute().getId())+"\",attachmentPointId:\""+(proposal.getAttachmentPoint()==null?"":proposal.getAttachmentPoint().getId())+"\",proposalId:\""+proposal.getId()+"\",disputeAmount:\""+(proposal.getDisputeAmount()==null?"":CcmFormat.formatBigDouble(proposal.getDisputeAmount()))+"\",notes:\""+(proposal.getNotes()==null?"":CcmUtil.string2JsonAll(proposal.getNotes().toString()))+"\",sCOA:\""+(proposal.getAccountCode()==null?"":proposal.getAccountCode().getId().toString())+"\",originatorId:\""+(proposal.getOriginator()==null?"":proposal.getOriginator().getId().toString())+"\",categoryId:\""+(proposal.getDisputeReason()==null?"":proposal.getDisputeReason().getId().toString())+"\",cricuitNumber:\""+(proposal.getCircuitNumber()==null?"":CcmUtil.string2JsonAll(proposal.getCircuitNumber()))+"\",serviceType:\""+(proposal.getServiceType()==null?"":CcmUtil.string2JsonAll(proposal.getServiceType()))+"\"}";
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return proposalJson;
	}
	
	/**
	 * get proposal by proposalId
	 * @author wei.su
	 * @throws Exception 
	 */
	public void updateProposalByProposalId(SearchInvoiceVO searchInvoiceVO)throws Exception{
		logger.info(CcmFormat.formatEnterLog("Enter method getProposalByProposalId.", searchInvoiceVO));
		Proposal proposal=new Proposal();
		ScoaSource scoaSource=new ScoaSource();
		DisputeReason disputeReason=disputeReasonDao.findById(Integer.valueOf(searchInvoiceVO.getDisputeReason()));
		@SuppressWarnings("unused")
		Originator originator=originatorDao.findById(Integer.valueOf(searchInvoiceVO.getOriginatorId()));
		@SuppressWarnings("unused")
		AccountCode accountCode=accountCodelDao.findById(Integer.valueOf(searchInvoiceVO.getAccountCodeId()));
		proposal = proposalDao.findById(Long.valueOf(searchInvoiceVO.getProposalId()));
		Double newAmount = Double.valueOf(searchInvoiceVO.getDisputeAmount());
		Double amountChang = newAmount - (proposal.getDisputeAmount() == null ? 0 : proposal.getDisputeAmount());
		Dispute d = proposal.getDispute();
		if(d != null){
			d.setDisputeAmount((d.getDisputeAmount() == null ? 0d : d.getDisputeAmount()) + amountChang);
			if("Y".equals(d.getFlagShortpaid())){
				d.setReserveAmount(getReserveAmount(d.getConfidenceLevel(),d.getDisputeAmount()));
				d.setOutstandingReserveAmount(d.getReserveAmount());
			}
			d.setModifiedBy(SystemUtil.getCurrentUserId());
			d.setModifiedTimestamp(new Date());
			disputeDao.merge(d);
		}
		proposal.setDisputeAmount(newAmount);
		proposal.setNotes(searchInvoiceVO.getNote());
		proposal.setModifiedBy(SystemUtil.getCurrentUserId());
		proposal.setModifiedTimestamp(new Date());
		proposal.setDisputeReason(disputeReason);
		proposal.setOriginator(originator);
		proposal.setAccountCode(accountCode);
		scoaSource.setId(730201);
		proposal.setScoaSource(scoaSource);
		proposal.setCircuitNumber(searchInvoiceVO.getCricuitNumber());
		proposal.setServiceType(searchInvoiceVO.getServiceType());
		try {
			proposal = proposalDao.merge(proposal);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
	}
	/**
	 * According to proposalId proposal "get the number of collection accessories
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getAnnexsTotalOfProposalByProposalId(SearchInvoiceVO svo) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Enter method getAnnexsTotalOfProposalByProposalId.", svo));
		StringBuffer sb = new StringBuffer();
		try {
			Integer totalResult = proposalDao.getAnnexsTotalOfProposalByProposalId(svo);
			sb.append(svo.getTotalPageNoJson(totalResult));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * According to proposalId proposal "get the attachment
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	@SuppressWarnings("unchecked")
	public String searchAnnexsOfProposalByProposalId(SearchInvoiceVO svo) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Enter method searchAnnexsOfProposalByProposalId.", svo));
		StringBuffer sb = new StringBuffer();
		List<String> cls = null;
		try {
			cls = proposalDao.searchAnnexsOfProposalByProposalId(svo);
			sb.append(svo.getListJsonCompatible(cls));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();	
	}
	
	/**
	 * Delete this annex. According to the Id
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public void deleteAnnexsById(int Id) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Enter method deleteAnnexsById.", ""));
		AttachmentFile attachmentFile = new AttachmentFile();
		try {
			 attachmentFile = attachmentFileDao.findById(Id);
			 attachmentFile.setRecActiveFlag("N");
			 attachmentFile.setModifiedBy(SystemUtil.getCurrentUserId());
			 attachmentFile.setModifiedTimestamp(new Date());
			 attachmentFileDao.merge(attachmentFile);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * According to disputeId dispute "get the number of collection accessories
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	public String getAnnexsTotalOfDisputeByDisputeId(SearchInvoiceVO svo) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Enter method getAnnexsTotalOfDisputeByDisputeId.", svo));
		StringBuffer sb = new StringBuffer();
		try {
			Integer totalResult = disputeDao.getAnnexsTotalOfDisputeByDisputeId(svo);
			sb.append(svo.getTotalPageNoJson(totalResult));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * According to disputeId dispute "get the attachment
	 * @param svo
	 * @return
	 * @throws BPLException
	 */
	@SuppressWarnings("unchecked")
	public String searchAnnexsOfDisputeByDisputeId(SearchInvoiceVO svo) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Enter method searchAnnexsOfDisputeByDisputeId.", svo));
		StringBuffer sb = new StringBuffer();
		List<String> cls = null;
		try {
			cls = disputeDao.searchAnnexsOfDisputeByDisputeId(svo);
			sb.append(svo.getListJsonCompatible(cls));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();	
	}
	
	/**
	 * Delete this annex. According to the Id of dispute
	 * @param svo
	 * @return
	 * @throws RuntimeException
	 */
	public void deleteAnnexsByIdOfDispute(int Id) throws RuntimeException{
		logger.info(CcmFormat.formatEnterLog("Enter method deleteAnnexsByIdOfDispute.", ""));
		AttachmentFile attachmentFile = new AttachmentFile();
		try {
			 attachmentFile = attachmentFileDao.findById(Id);
			 attachmentFile.setRecActiveFlag("N");
			 attachmentFile.setModifiedBy(SystemUtil.getCurrentUserId());
			 attachmentFile.setModifiedTimestamp(new Date());
			 attachmentFileDao.merge(attachmentFile);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		} 
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * updateDisputeAmountByDiputeIdAndDisputeAmount
	 * @param svo
	 * @return
	 * @throws RuntimeException
	 */
	@SuppressWarnings("unchecked")
	public void updateDisputeAmountByDiputeIdAndDisputeAmount(Integer disputeId,Double disputeAmont) throws RuntimeException{
		logger.info(CcmFormat.formatEnterLog("Enter method updateDisputeAmountByDiputeIdAndDisputeAmount.", ""));
		Dispute dispute = new Dispute();
		try {
			 dispute = disputeDao.findById(disputeId);
			 dispute.setDisputeAmount(disputeAmont);
			 dispute.setReserveAmount(getReserveAmount(dispute.getConfidenceLevel(), dispute.getDisputeAmount()));
			 dispute.setOutstandingReserveAmount(dispute.getReserveAmount());
			 dispute.setModifiedBy(SystemUtil.getCurrentUserId());
			 dispute.setModifiedTimestamp(new Date());
			 disputeDao.merge(dispute);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	
	public IAttachmentFileDao getAttachmentFileDao() {
		return attachmentFileDao;
	}

	public void setAttachmentFileDao(IAttachmentFileDao attachmentFileDao) {
		this.attachmentFileDao = attachmentFileDao;
	}

	public IDisputeStatusDao getDisputeStatusDao() {
		return disputeStatusDao;
	}

	public void setDisputeStatusDao(IDisputeStatusDao disputeStatusDao) {
		this.disputeStatusDao = disputeStatusDao;
	}

	public IAccountCodelDao getAccountCodelDao() {
		return accountCodelDao;
	}

	public void setAccountCodelDao(IAccountCodelDao accountCodelDao) {
		this.accountCodelDao = accountCodelDao;
	}

	public IDisputeReasonDao getDisputeReasonDao() {
		return disputeReasonDao;
	}

	public void setDisputeReasonDao(IDisputeReasonDao disputeReasonDao) {
		this.disputeReasonDao = disputeReasonDao;
	}

	public IDisputeDao getDisputeDao() {
		return disputeDao;
	}

	public void setDisputeDao(IDisputeDao disputeDao) {
		this.disputeDao = disputeDao;
	}

	public IInvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	public void setInvoiceDao(IInvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	public IBanDao getBanDao() {
		return banDao;
	}

	public void setBanDao(IBanDao banDao) {
		this.banDao = banDao;
	}

	public ICurrencyDao getCurrencyDao() {
		return currencyDao;
	}

	public void setCurrencyDao(ICurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	public IOriginatorDao getOriginatorDao() {
		return originatorDao;
	}
	
	/* Invoice Detail Dao. */
	public IInvoiceDetailDao getInvoiceDetailDao() {
		return invoiceDetailDao;
	}

	public void setInvoiceDetailDao(IInvoiceDetailDao invoiceDetailDao) {
		this.invoiceDetailDao = invoiceDetailDao;
	}


	public void setOriginatorDao(IOriginatorDao originatorDao) {
		this.originatorDao = originatorDao;
	}

	public IProposalDao getProposalDao() {
		return proposalDao;
	}

	public void setProposalDao(IProposalDao proposalDao) {
		this.proposalDao = proposalDao;
	}

	public ISysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(ISysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}
	
}
