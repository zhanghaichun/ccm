package com.saninco.ccm.service.ban;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;

import com.saninco.ccm.dao.IBanDao;
import com.saninco.ccm.dao.IUserDelegationDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.InvoiceStructure;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.BanVO;
import com.saninco.ccm.vo.SearchVO;

/**
 * @Author chao.liu (Optimization of complete by xinyu.Liu)
 */
public class BanServiceImpl implements IBanService {

	private final Logger logger = Logger.getLogger(this.getClass());

	private IBanDao banDao;
	private IUserDelegationDao userDelegationDao;

	public BanServiceImpl() {

	}

	/**
	 * @auchor xinyu.Liu (ccm/ban/banDetailPage.js) Verify whether the repeated
	 *         account_number
	 * 
	 */
	public String serachAccountNumberRepeat(BanVO bvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(
				"Verify whether the repeated account_number.", bvo));
		String number = "{count:0}";
		try {
			long count = banDao.getNumberOfAccountNumber(bvo);
			if (count != 0)
				number = "{count:1}";
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return number;
	}

	/**
	 * @auchor chao.Liu (ccm/ban/banAdminTab.js) Get Ban Total Number
	 * 
	 */
	public String getBanTotalNO(BanVO bvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get Ban Total Number.", bvo));
		String json = "";
		try {
			long count = banDao.getBanTotalNO(bvo);
			json = bvo.getTotalPageNoJson(count);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return json;
	}

	/**
	 * @auchor chao.Liu (ccm/ban/banAdminTab.js) Search Ban List Info
	 * 
	 */
	public String searchBanList(BanVO bvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Ban List Info.", bvo));
		String json = "";
		try {
			List list = banDao.searchBanList(bvo);
			json = bvo.getListJsonCompatible(list);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return json;
	}
	
	/**
	 * @author dongjian
	 * */
	public String getInvoiceFormatsByLineOsBussinessAndChannel(
			String lineOfBussiness, String invoiceChannel) throws BPLException {
		String result = null;
		try {
			List invoiceFormatList = banDao.getInvoiceFormatJsonByLineOsBussinessAndChannel(lineOfBussiness,invoiceChannel);
			StringBuilder invoiceFormatSB = new StringBuilder();
			invoiceFormatSB.append("[");
			if (invoiceFormatList != null && invoiceFormatList.size() > 0) {
				for (int i = 0; i < invoiceFormatList.size(); i++) {
					if (i != 0){
						invoiceFormatSB.append(",");
					}
					Object[] r = (Object[])invoiceFormatList.get(i);
					Object r1 = r[1];
					if (r1 instanceof Blob) {
						r1 = SearchVO.getBlobString((Blob) r1);
					}
					invoiceFormatSB.append(r1);
				}
			}
			invoiceFormatSB.append("]");
			
			result = "{formatList:"+invoiceFormatSB.toString()+"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BPLException(e.getMessage());
		}
		return result;
	}
	
	/**
	 * @author dongjian
	 * */
	public String getInvoiceChannelAndFormatAndScoaJsonByLineOsBussiness(
			String lineOfBussiness) throws BPLException {
		String result = null;
		try {
			List invoiceChannelList = banDao.getInvoiceChannelJsonByLineOsBussiness(lineOfBussiness);
			StringBuilder invoiceChanneSB = new StringBuilder();
			invoiceChanneSB.append("[");
			if (invoiceChannelList != null && invoiceChannelList.size() > 0) {
				for (int i = 0; i < invoiceChannelList.size(); i++) {
					if (i != 0){
						invoiceChanneSB.append(",");
					}
					Object[] r = (Object[])invoiceChannelList.get(i);
					Object r1 = r[1];
					if (r1 instanceof Blob) {
						r1 = SearchVO.getBlobString((Blob) r1);
					}
					invoiceChanneSB.append(r1);
				}
			}
			invoiceChanneSB.append("]");
			
			List scoaList = banDao.getScoaCodeTypeJsonByLineOsBussiness(lineOfBussiness);
			StringBuilder scoaSB = new StringBuilder();
			scoaSB.append("[");
			if (scoaList != null && scoaList.size() > 0) {
				for (int i = 0; i < scoaList.size(); i++) {
					if (i != 0){
						scoaSB.append(",");
					}
					Object[] r = (Object[])scoaList.get(i);
					Object r1 = r[1];
					if (r1 instanceof Blob) {
						r1 = SearchVO.getBlobString((Blob) r1);
					}
					scoaSB.append(r1);
				}
			}
			scoaSB.append("]");

			result = "{channeList:"+invoiceChanneSB.toString()+",scoaList:"+scoaSB+"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new BPLException(e.getMessage());
		}
		return result;
	}

	/**
	 * @auchor chao.Liu (ccm/ban/banAdminTab.js) DownLoad Ban Excel
	 * 
	 */
	public String saveDownloadBanToExcel(BanVO bvo, String excelDirPath,
			List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("DownLoad Ban Excel.", bvo,
				excelDirPath, titles));
		List<Object[]> list = null;
		long count = 0;
		int recPerPage = 1000;
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			count = banDao.getBanTotalNO(bvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "ban");
			jExcelUtil.removeFieldName(titles);
			jExcelUtil.writeTitle(0, titles);
			long totlePage = (count + recPerPage - 1) / recPerPage;
			for (int j = 0; j < totlePage; j++) {
				bvo.setPageNo(j + 1);
				bvo.setRecPerPage(recPerPage);
				list = banDao.searchBanListExcel(bvo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
				}
			}
			jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40,
					40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40,
					40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40,
					40, 40, 40 });
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}

	/**
	 * @auchor chao.Liu (ccm/ban/banAdminTab.js) Delete Ban By Ban Id
	 * 
	 */
	public String deleteBanById(BanVO bvo) {
		logger.info(CcmFormat.formatEnterLog("Delete Ban By Ban Id.", bvo));
		String json = "";
		try {
			Ban ban = banDao.findById(bvo.getBanId());
			ban.setRecActiveFlag("N");
			json = "{error:false}";
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException rte = new RuntimeException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw rte;
		}
		logger.info(CcmFormat.formatExitLog());
		return json;
	}

	/**
	 * Get Ban By Id
	 * 
	 * @Author Chao.Liu Date: Sep 29, 2010
	 * @param BanVO
	 */
	public Ban getBanById(BanVO bvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get Ban By Id.", bvo));
		Ban b = banDao.findById(bvo.getBanId());
		logger.info(CcmFormat.formatExitLog());
		return b;
	}

	/**
	 * @auchor chao.Liu (banDetailPage.jsp) Save Ban
	 * 
	 */
	public String saveBanToBanDetailPage(Ban b) {
		logger.info(CcmFormat.formatEnterLog("Save Ban.", b));
		StringBuffer sb = new StringBuffer();
		try {
			
			Integer count = 0;
			//��ban����ʧЧʱ��Ҫ����֤  add by mingyang.li 20120913
			if(b.getBanStatus().getId().intValue() == 2){
				count = banDao.getInvoiceByBan(b);
				if(count.intValue()!=0){
					count = 0;
					sb.append("\"Can't inactive BAN with unfinished invoice.\"");
					return sb.toString();
				}
			}
			count = banDao.getBanCountByBanName(b);
			if (count.intValue() == 0) {
				int invoiceStructureId = -1;
				toEmendBan(b);
				b.setStatusTimestamp(new Date());
				b.setModifiedBy(SystemUtil.getCurrentUserId());
				b.setModifiedTimestamp(new Date());
				if (b.getId() == null) { // New Ban
					if(b.getAnalystId() != null){
						b.setAnalyst(banDao.getAnalystName(b.getAnalystId()));
					}
					b.setId(banDao.findMaxId()+1);
					b.setBanCreateDate(new Date());
					b.setCreatedTimestamp(new Date());
					b.setCreatedBy(SystemUtil.getCurrentUserId());
					b.setMasterBanFlag("Y");
					b.setRecActiveFlag("Y");
					if("Y".equals(b.getAutopayFlag())){
						b.setAutopayCount(0);
						b.setAutopayMaxCount(Integer.valueOf(banDao.getAutoPayMaxCount()));
					}
					if (b.getVendor() != null && b.getVendor().getId() != null) {
						b.setCcmVendorName(banDao.getVendorNameByVendorId(b.getVendor().getId()));
					}
					if (b.getInvoiceFormat() != null && b.getInvoiceFormat().getId() != null) {
						int fid = b.getInvoiceFormat().getId();
						if(fid == 6){
							invoiceStructureId = 31;
						} else if(fid == 9){
							invoiceStructureId = 30;
						} else if(fid == 15){
							invoiceStructureId = 28;
						} else if(fid == 12){
							invoiceStructureId = 27;
						} else if(fid == 11){
							invoiceStructureId = 25;
						} else if(fid == 1 || fid == 2 
								|| fid == 4 || fid == 5 
								|| fid == 7 || fid == 8 
								|| fid == 10 || fid == 13){
							if(!"RBS - CABLE".equalsIgnoreCase(b.getLineOfBusiness()) 
									&& !"RBS - CANX".equalsIgnoreCase(b.getLineOfBusiness())
									&& !"Power supply".equalsIgnoreCase(b.getLineOfBusiness()) 
									&& !"Power supply - Bulk".equalsIgnoreCase(b.getLineOfBusiness()) 
									&& !"Power supply - Poles".equalsIgnoreCase(b.getLineOfBusiness())){
								invoiceStructureId = 2;
							
							// update by mingyang.li 20161101 start
							}else if("RBS - CABLE".equalsIgnoreCase(b.getLineOfBusiness()) || "RBS - CANX".equalsIgnoreCase(b.getLineOfBusiness())){
								invoiceStructureId = 5;
							}
							// update by mingyang.li 20161101 end
						} 
						
						// update by mingyang.li 20161101 start
//						if("RBS - CABLE".equalsIgnoreCase(b.getLineOfBusiness()) || "RBS - CANX".equalsIgnoreCase(b.getLineOfBusiness())){
//							invoiceStructureId = 5;
//						}
						// update by mingyang.li 20161101 end
						
						if("Power supply".equalsIgnoreCase(b.getLineOfBusiness()) || "Power supply - Bulk".equalsIgnoreCase(b.getLineOfBusiness()) || "Power supply - Poles".equalsIgnoreCase(b.getLineOfBusiness())){
							invoiceStructureId = 3;
						} 
					}
					if(invoiceStructureId > 0)b.setInvoiceStructure(new InvoiceStructure(invoiceStructureId));
					b.setPaperAccountNumber(b.getPaperAccountNumber());
					banDao.save(b);
				} else { // Update Ban
					Ban ban = banDao.findById(b.getId());
					if(b.getAnalystId() != null){
						ban.setAnalyst(banDao.getAnalystName(b.getAnalystId()));
					}
					ban.setVendor(b.getVendor());
					ban.setAccountNumber(b.getAccountNumber());
					ban.setApSupplierNumber(b.getApSupplierNumber());
					ban.setApSupplierName(b.getApSupplierName());
					ban.setApSupplierSite(b.getApSupplierSite());
					ban.setApCustomerName(b.getApCustomerName());
					ban.setMibFlag(b.getMibFlag());
					ban.setBanStatus(b.getBanStatus());
					ban.setAnalystId(b.getAnalystId());
					ban.setApprover1Id(b.getApprover1Id());
					ban.setApprover2Id(b.getApprover2Id());
					ban.setApprover3Id(b.getApprover3Id());
					ban.setLineOfBusiness(b.getLineOfBusiness());
					ban.setInvoiceChannel(b.getInvoiceChannel());
					ban.setInvoiceFormat(b.getInvoiceFormat());
					ban.setManualAdjustmentApprovalFlag(b.getManualAdjustmentApprovalFlag());
					ban.setExternalApproveFlag(b.getExternalApproveFlag()); // External Approve Flag
					ban.setMaxApprovalLevel(b.getMaxApprovalLevel());
					ban.setCurrency(b.getCurrency());
					ban.setOriginalAccountNumber(b.getOriginalAccountNumber());
					ban.setPaperAccountNumber(b.getPaperAccountNumber());
					ban.setUsageBackbillMonths(b.getUsageBackbillMonths());
					ban.setBillDay(b.getBillDay());
					ban.setCompany(b.getCompany());
					ban.setLocation(b.getLocation());
					ban.setDepartment(b.getDepartment());
					ban.setChannel(b.getChannel());
					ban.setBanPrimiaryContactId(b.getBanPrimiaryContactId());
					ban.setBanPaymentContactId(b.getBanPaymentContactId());
					ban.setBanDisputeContactId(b.getBanDisputeContactId());
					ban.setBanTariffContactId(b.getBanTariffContactId());
					ban.setBanBillingContactId(b.getBanBillingContactId());
					ban.setAccountType(b.getAccountType());
					ban.setInvoiceFrequency(b.getInvoiceFrequency());
					ban.setPaymentMethod(b.getPaymentMethod());
					ban.setPaymentTerm(b.getPaymentTerm());
					ban.setNotes(b.getNotes());
					ban.setNotice(b.getNotice());
					ban.setPaymentGroup(b.getPaymentGroup());
					ban.setCreatedBanFlag(b.getCreatedBanFlag());
					ban.setScoaCodeType(b.getScoaCodeType());
					ban.setStatusTimestamp(new Date());
					ban.setModifiedBy(SystemUtil.getCurrentUserId());
					ban.setModifiedTimestamp(new Date());
					ban.setLevel1ApprovalFlag(b.getLevel1ApprovalFlag());
					if(ban.getMissingEmailflag() != null && !ban.getMissingEmailflag().equals(b.getMissingEmailflag())){
						ban.setMissingEmailflag(b.getMissingEmailflag());
						ban.setSystemModifiedMIFFlag("N");
					}
					ban.setApprover4Id(b.getApprover4Id());
					ban.setDaysToDue(b.getDaysToDue());
					ban.setBanInactiveReason(b.getBanInactiveReason());
					ban.setBillTo(b.getBillTo());
					ban.setTaxRegistrationNumber(b.getTaxRegistrationNumber());
					if(b.getBanStatus().getId() == 5){
						ban.setRejectNotes(b.getRejectNotes());
					}else{
						ban.setRejectNotes("");
					}
					ban.setTaxRegistrationNumber(b.getTaxRegistrationNumber());
					if("N".equals(ban.getAutopayFlag()) && "Y".equals(b.getAutopayFlag())){
						ban.setAutopayCount(0);
						ban.setAutopayMaxCount(Integer.valueOf(banDao.getAutoPayMaxCount()));
					}
					ban.setAutopayFlag(b.getAutopayFlag());
					if (StringUtils.hasLength(ban.getMissingEmailflag()) && "N".equals(ban.getMissingEmailflag())) {
						banDao.updateInvoiceAndEmail(ban);
					}
					banDao.updateInvoiceByBan(b.getAnalystId(),b.getId());
					
					if (b.getVendor() != null && b.getVendor().getId() != null) {
						ban.setCcmVendorName(banDao.getVendorNameByVendorId(b.getVendor().getId()));
					}
					if (b.getInvoiceFormat() != null && b.getInvoiceFormat().getId() != null) {
						int fid = b.getInvoiceFormat().getId();
						if(fid == 6){
							invoiceStructureId = 31;
						} else if(fid == 9){
							invoiceStructureId = 30;
						} else if(fid == 15){
							invoiceStructureId = 28;
						} else if(fid == 12){
							invoiceStructureId = 27;
						} else if(fid == 11){
							invoiceStructureId = 25;
						} else if(fid == 1 || fid == 2 
								|| fid == 4 || fid == 5 
								|| fid == 7 || fid == 8 
								|| fid == 10 || fid == 13){
							if(!"RBS - CABLE".equalsIgnoreCase(b.getLineOfBusiness()) 
									&& !"RBS - CANX".equalsIgnoreCase(b.getLineOfBusiness())
									&& !"Power supply".equalsIgnoreCase(b.getLineOfBusiness()) 
									&& !"Power supply - Bulk".equalsIgnoreCase(b.getLineOfBusiness()) 
									&& !"Power supply - Poles".equalsIgnoreCase(b.getLineOfBusiness())){
								invoiceStructureId = 2;
							// update by mingyang.li 20161101 start
							}else if("RBS - CABLE".equalsIgnoreCase(b.getLineOfBusiness()) || "RBS - CANX".equalsIgnoreCase(b.getLineOfBusiness())){
								invoiceStructureId = 5;
							}
							// update by mingyang.li 20161101 end
						} 
//						 update wenbo.zhang   2016-10-27
						
//						if("RBS - CABLE".equalsIgnoreCase(b.getLineOfBusiness()) || "RBS - CANX".equalsIgnoreCase(b.getLineOfBusiness())){
//							invoiceStructureId = 5;
//						}
						if("Power supply".equalsIgnoreCase(b.getLineOfBusiness()) || "Power supply - Bulk".equalsIgnoreCase(b.getLineOfBusiness()) || "Power supply - Poles".equalsIgnoreCase(b.getLineOfBusiness())){
							invoiceStructureId = 3;
						} 
					}
					if(invoiceStructureId > 0)ban.setInvoiceStructure(new InvoiceStructure(invoiceStructureId));
					banDao.merge(ban);
					
					if(ban.getBanStatus().getId() == 1){
						banDao.updateInvoicesForBan(b);
					}
					
				}
				sb.append("Success");
			} else {
				sb.append("\"Ban Repeat!\"");
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException rte = new RuntimeException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw rte;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	private Ban toEmendBan(Ban b) {
		if (b.getInvoiceFormat() != null
				&& b.getInvoiceFormat().getId() == null)
			b.setInvoiceFormat(null);
		if (b.getBanStatus() != null && b.getBanStatus().getId() == null)
			b.setBanStatus(null);
		if (b.getBanInactiveReason() != null && b.getBanInactiveReason().getId() == null)
			b.setBanInactiveReason(null);
		if (b.getInvoiceChannel() != null
				&& b.getInvoiceChannel().getId() == null)
			b.setInvoiceChannel(null);
		if (b.getCurrency() != null && b.getCurrency().getId() == null)
			b.setCurrency(null);
		if (b.getAccountType() != null && b.getAccountType().getId() == null)
			b.setAccountType(null);
		if (b.getVendor() != null && b.getVendor().getId() == null)
			b.setVendor(null);
		if (b.getPaymentGroup() != null && b.getPaymentGroup().getId() == null)
			b.setPaymentGroup(null);
		if (b.getInvoiceStructure() != null
				&& b.getInvoiceStructure().getId() == null)
			b.setInvoiceStructure(null);
		if (b.getPaymentMethod() != null
				&& b.getPaymentMethod().getId() == null)
			b.setPaymentMethod(null);
		if (b.getPaymentTerm() != null && b.getPaymentTerm().getId() == null)
			b.setPaymentTerm(null);
		return b;
	}

	/**
	 * @auchor chao.Liu (banDetailPage.jsp) Get Ban By Id
	 * 
	 */
	public String getContactByVendorId(BanVO bvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get Ban By Id.", bvo));
		StringBuffer sb = new StringBuffer("[");
		try {
			List<Object[]> cls = banDao.getContactByVendorId(bvo);
			for (Object[] c : cls) {
				String s = (String) c[1];
				s=s.replace("\"", "'");
				sb.append("{id:\"" + c[0] + "\",value:\"" + s + "\"},");
			}
			if (sb.length() > 1)
				sb.deleteCharAt(sb.length() - 1);
			sb.append("]");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public IBanDao getBanDao() {
		return banDao;
	}

	public void setBanDao(IBanDao banDao) {
		this.banDao = banDao;
	}

	public String doValidationAccountExis(String accNoName) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("doValidationAccountExis.",
				accNoName));
		String r = null;
		try {
			Long count = banDao.getAccCountByName(accNoName);
			if (count > 0) {
				r = "{data:false}";
			} else {
				r = "{data:true}";
			}
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return r;
	}

	public String getMaxApprovalLevel() throws BPLException {
		return banDao.getApprovalLevel();
	}
	
	public String getIsBackupFlag(Ban b) throws BPLException {
		Integer usid;
		List backUpUsers = userDelegationDao.findMyWorkSparsUsers();
		for(Object user : backUpUsers){
			Object[] userFields = (Object[])user;
			usid = (Integer)userFields[0];
			if(b.getCreatedBy().equals(usid)){
				return "Y";
			}
		}
		List myTeamUsers = userDelegationDao.findMyWorkSparsSerUsers();
		for(Object user : myTeamUsers){
			Object[] userFields = (Object[])user;
			usid = (Integer)userFields[0];
			if(b.getCreatedBy().equals(usid)){
				return "Y";
			}
		}
		return "N";
	}

	public void updateBanStatusId(Integer banId,Integer banStatusId,String rejectNotes)throws BPLException{
		banDao.updateBanStatusId(banId,banStatusId,rejectNotes);
	}
	
	public void deleteBanById(Integer banId)throws BPLException{
		banDao.deleteBanById(banId);
	}

	public IUserDelegationDao getUserDelegationDao() {
		return userDelegationDao;
	}

	public void setUserDelegationDao(IUserDelegationDao userDelegationDao) {
		this.userDelegationDao = userDelegationDao;
	}
	

}
