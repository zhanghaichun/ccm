package com.saninco.ccm.action.ban;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.filter.AllGranted;
import com.saninco.ccm.filter.AnyGranted;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.BanStatus;
import com.saninco.ccm.model.Currency;
import com.saninco.ccm.model.TariffLink;
import com.saninco.ccm.service.ban.IBanService;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.BanVO;
import com.saninco.ccm.vo.MapEntryVO;

/**
 * @author Chao.Liu (Optimization of complete by xinyu.Liu)
 */
public class BanDetailAction extends CcmActionSupport {
	
	private static final long serialVersionUID = -7778316099624206431L;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IBanService banService;
	
	private Ban b = new Ban();
	private BanVO bvo = new BanVO();
	private String accNoName;
	private List<File> uploads;
	private List<String> uploadsFileName;
	private Integer banId;
	private String uploadNotes;
	private String maxApprovalLevel;
	private String rejectNotes;
	private Integer banStatusId;
	private Integer currentUserId;
	private String isApprove;
	


	private List<MapEntryVO<String, String>> banStatus = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> invoiceChannel = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> paymentMethod = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> invoiceFormat = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> lineofBbusiness = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> analyst = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> approver1 = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> approver2 = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> approver3 = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> currency = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> accountType = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> invoiceFrequency = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> paymentTerm = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> paymentGroup = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> nyList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> ynList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> mnList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> scoaCodeTypeList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> approver4 = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banInactiveReason = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> autoPayCountList = new ArrayList<MapEntryVO<String, String>>();
	
	public BanDetailAction() {}
	
	@AnyGranted(value="FUNCTION_4900")
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		try {
			this.initBan();
			this.initDownlist();
			//yongzhen 2013/11/11
			maxApprovalLevel=banService.getMaxApprovalLevel();
		} catch (Exception e) {
			logger.error("exec error: ", e);
		}
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	public String banViewDetail() throws Exception {
		logger.info("Enter method exec.");
		try {
			this.initBan();
			this.initDownlist();
			//yongzhen 2013/11/11
			maxApprovalLevel=banService.getMaxApprovalLevel();
		} catch (Exception e) {
			logger.error("exec error: ", e);
		}
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	private void initDownlist() throws BPLException{
		this.paymentTerm = commonLookupService.getPaymentTerm();
		this.paymentGroup = commonLookupService.getPaymentGroup();
		this.invoiceFrequency = commonLookupService.getInvoiceFrequency();
		this.accountType = commonLookupService.getAccountType();
		this.currency = commonLookupService.getCurrency();
		this.nyList = commonLookupService.getNYList();
		this.ynList = commonLookupService.getYNList();
		this.mnList = commonLookupService.getMNList();
		this.scoaCodeTypeList = commonLookupService.scoaCodeTypeList();
		this.vendorList = commonLookupService.getUserPreviledgedVendors();
		this.analyst=commonLookupService.getAnalyst();	
		this.banStatus=commonLookupService.getBanStatus(b.getBanStatus().getId());
		this.invoiceChannel=commonLookupService.getInvoiceChannel();	
		this.paymentMethod=commonLookupService.getPaymentMethod();	
		this.invoiceFormat=commonLookupService.getInvoiceFormat();	
		this.lineofBbusiness=commonLookupService.getLineofBbusiness();	
		this.banInactiveReason=commonLookupService.getBanInactiveReason();	
		this.approver1 = commonLookupService.getApprover1ByRoleId();
		this.approver2 = commonLookupService.getApprover2ByRoleId();
		this.approver3 = commonLookupService.getApprover3ByRoleId();
		this.approver4 = commonLookupService.getApprover4ByRoleId();
		this.autoPayCountList = commonLookupService.getAutoPayCountList();
	}
	
	private void initBan() throws BPLException{
		if(bvo.getBanId() != null){
			b = banService.getBanById(bvo);
			String isBackup = banService.getIsBackupFlag(b);
			bvo.setIsBackup(isBackup);
		}else{
			List<String> authorities = SystemUtil.getCurrentUserAuthorities();
			for(int i=0;i<authorities.size();i++){
				if("FUNCTION_4900".equals(authorities.get(i))){
					b.setBanStatus(new BanStatus(1));
					break;
				}
			}
			if(b.getBanStatus() == null){
				b.setBanStatus(new BanStatus(4));
			}
			b.setCurrency(new Currency(1));
		}
		currentUserId = SystemUtil.getCurrentUserId();
//		if(b.getDaysToDue() == null)b.setDaysToDue(30);
	}
	
	/**
	 * @auchor xinyu.Liu (ccm/ban/banDetailPage.js)
	 * Verify whether the repeated account_number
	 * 
	 */
	public String serachAccountNumber()throws Exception {
		logger.info("Enter method serachAccountNumber.");
		String result = null;
		try{
			result = banService.serachAccountNumberRepeat(bvo);
		}catch(Exception e){
			logger.error("serachAccountNumber error: ", e);
			result = "{error:\"serachAccountNumber Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method serachAccountNumber.");
		return null;
	}
	
	/**
	 * @auchor chao.Liu (banDetailPage.jsp)
	 * Save Ban
	 * 
	 */
	public String doUploadAttachFileByBan() {
		logger.info("Enter method doUploadAttachFile.");
		try {
			TariffLink parmas = new TariffLink();
			parmas.setBanId(banId);
			parmas.setNotes(uploadNotes);
			commonLookupService.saveUploadFileToTariffLinkByVendor(uploads,uploadsFileName, parmas);
			
		} catch (Exception e) {
			logger.error("doUploadAttachFile error: ", e);
			return INPUT;
		}
		logger.info("Exit method doUploadAttachFile.");
		return SUCCESS;
	}
	/**
	 * @auchor chao.Liu (banDetailPage.jsp)
	 * Save Ban
	 * 
	 */
	public String saveBanToBanDetailPage()throws Exception {
		logger.info("Enter method saveBanToBanDetailPage.");
		try{
			if("".equals(b.getLevel1ApprovalFlag())){
				b.setLevel1ApprovalFlag("N");
			}
			bvo.setPageMessage(banService.saveBanToBanDetailPage(b));
			if(!bvo.getPageMessage().equals("Success")){
				return INPUT;
			}
		}catch(Exception e){
			bvo.setPageMessage("\"Save Ban To Ban Detail Page Error!\"");
			logger.error("saveBanToBanDetailPage error: ", e);
			return INPUT;
		}
		logger.info("Exit method saveBanToBanDetailPage.");
		return SUCCESS;
	}
	
	/**
	 * @auchor chao.Liu (banDetailPage.jsp)
	 * Get Ban By Id
	 * 
	 */
	public String getContactByVendorId()throws Exception {
		logger.info("Enter method getContactByVendorId.");
		String result = null;
		try{
			result = banService.getContactByVendorId(bvo);
		}catch(Exception e){
			logger.error("getContactByVendorId error: ", e);
			result = "{error:\"Get Contact Info By Vendor Id Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getContactByVendorId.");
		return null;
	}
	
	
	
	/**
	 * @auchor chao.Liu (banDetailPage.js)
	 * Get Ban By Id
	 * doValidationAccountExis
	 */
	public String doValidationAccountExis()throws Exception {
		logger.info("Enter method doValidationAccountExis.");
		String result = "{data:false}";
		try{
			result = banService.doValidationAccountExis(accNoName);
		}catch(Exception e){
			logger.error("doValidationAccountExis error: ", e);
			result = "{error:\" Validation Account Exis Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doValidationAccountExis.");
		return null;
	}

	public String updateBanStatusId()throws Exception {
		banService.updateBanStatusId(banId,banStatusId,rejectNotes);
		return null;
	}
	
	public String deleteBanById()throws Exception {
		banService.deleteBanById(banId);
		return null;
	}
	
	public IBanService getBanService() {
		return banService;
	}

	public void setBanService(IBanService banService) {
		this.banService = banService;
	}

	public BanVO getBvo() {
		return bvo;
	}

	public void setBvo(BanVO bvo) {
		this.bvo = bvo;
	}

	public List<MapEntryVO<String, String>> getBanStatus() {
		return banStatus;
	}

	public void setBanStatus(List<MapEntryVO<String, String>> banStatus) {
		this.banStatus = banStatus;
	}

	public List<MapEntryVO<String, String>> getInvoiceChannel() {
		return invoiceChannel;
	}

	public void setInvoiceChannel(List<MapEntryVO<String, String>> invoiceChannel) {
		this.invoiceChannel = invoiceChannel;
	}

	public List<MapEntryVO<String, String>> getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(List<MapEntryVO<String, String>> paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public List<MapEntryVO<String, String>> getInvoiceFormat() {
		return invoiceFormat;
	}

	public void setInvoiceFormat(List<MapEntryVO<String, String>> invoiceFormat) {
		this.invoiceFormat = invoiceFormat;
	}

	public List<MapEntryVO<String, String>> getLineofBbusiness() {
		return lineofBbusiness;
	}

	public void setLineofBbusiness(List<MapEntryVO<String, String>> lineofBbusiness) {
		this.lineofBbusiness = lineofBbusiness;
	}

	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}

	public List<MapEntryVO<String, String>> getAnalyst() {
		return analyst;
	}

	public void setAnalyst(List<MapEntryVO<String, String>> analyst) {
		this.analyst = analyst;
	}

	public List<MapEntryVO<String, String>> getCurrency() {
		return currency;
	}

	public void setCurrency(List<MapEntryVO<String, String>> currency) {
		this.currency = currency;
	}

	public List<MapEntryVO<String, String>> getAccountType() {
		return accountType;
	}

	public void setAccountType(List<MapEntryVO<String, String>> accountType) {
		this.accountType = accountType;
	}

	public List<MapEntryVO<String, String>> getInvoiceFrequency() {
		return invoiceFrequency;
	}

	public void setInvoiceFrequency(
			List<MapEntryVO<String, String>> invoiceFrequency) {
		this.invoiceFrequency = invoiceFrequency;
	}

	public List<MapEntryVO<String, String>> getPaymentTerm() {
		return paymentTerm;
	}

	public void setPaymentTerm(List<MapEntryVO<String, String>> paymentTerm) {
		this.paymentTerm = paymentTerm;
	}

	public List<MapEntryVO<String, String>> getPaymentGroup() {
		return paymentGroup;
	}

	public void setPaymentGroup(List<MapEntryVO<String, String>> paymentGroup) {
		this.paymentGroup = paymentGroup;
	}

	public Ban getB() {
		return b;
	}

	public void setB(Ban b) {
		this.b = b;
	}

	public List<MapEntryVO<String, String>> getApprover1() {
		return approver1;
	}

	public void setApprover1(List<MapEntryVO<String, String>> approver1) {
		this.approver1 = approver1;
	}

	public List<MapEntryVO<String, String>> getApprover2() {
		return approver2;
	}

	public void setApprover2(List<MapEntryVO<String, String>> approver2) {
		this.approver2 = approver2;
	}

	public List<MapEntryVO<String, String>> getApprover3() {
		return approver3;
	}

	public void setApprover3(List<MapEntryVO<String, String>> approver3) {
		this.approver3 = approver3;
	}

	public String getAccNoName() {
		return accNoName;
	}

	public void setAccNoName(String accNoName) {
		this.accNoName = accNoName;
	}

	public List<MapEntryVO<String, String>> getNyList() {
		return nyList;
	}

	public void setNyList(List<MapEntryVO<String, String>> nyList) {
		this.nyList = nyList;
	}

	public List<MapEntryVO<String, String>> getYnList() {
		return ynList;
	}

	public void setYnList(List<MapEntryVO<String, String>> ynList) {
		this.ynList = ynList;
	}
	
	public List<MapEntryVO<String, String>> getMnList() {
		return mnList;
	}

	public void setMnList(List<MapEntryVO<String, String>> mnList) {
		this.mnList = mnList;
	}

	public List<MapEntryVO<String, String>> getScoaCodeTypeList() {
		return scoaCodeTypeList;
	}

	public void setScoaCodeTypeList(
			List<MapEntryVO<String, String>> scoaCodeTypeList) {
		this.scoaCodeTypeList = scoaCodeTypeList;
	}

	public List<MapEntryVO<String, String>> getApprover4() {
		return approver4;
	}

	public void setApprover4(List<MapEntryVO<String, String>> approver4) {
		this.approver4 = approver4;
	}
	
	public List<MapEntryVO<String, String>> getBanInactiveReason() {
		return banInactiveReason;
	}

	public void setBanInactiveReason(
			List<MapEntryVO<String, String>> banInactiveReason) {
		this.banInactiveReason = banInactiveReason;
	}

	public List<File> getUploads() {
		return uploads;
	}

	public void setUploads(List<File> uploads) {
		this.uploads = uploads;
	}

	public List<String> getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(List<String> uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public Integer getBanId() {
		return banId;
	}

	public void setBanId(Integer banId) {
		this.banId = banId;
	}

	public String getUploadNotes() {
		return uploadNotes;
	}

	public void setUploadNotes(String uploadNotes) {
		this.uploadNotes = uploadNotes;
	}
	
	public String getMaxApprovalLevel() {
		return maxApprovalLevel;
	}

	public void setMaxApprovalLevel(String maxApprovalLevel) {
		this.maxApprovalLevel = maxApprovalLevel;
	}

	public List<MapEntryVO<String, String>> getAutoPayCountList() {
		return autoPayCountList;
	}

	public void setAutoPayCountList(
			List<MapEntryVO<String, String>> autoPayCountList) {
		this.autoPayCountList = autoPayCountList;
	}

	public Integer getBanStatusId() {
		return banStatusId;
	}

	public void setBanStatusId(Integer banStatusId) {
		this.banStatusId = banStatusId;
	}

	public Integer getCurrentUserId() {
		return currentUserId;
	}

	public void setCurrentUserId(Integer currentUserId) {
		this.currentUserId = currentUserId;
	}

	public String getRejectNotes() {
		return rejectNotes;
	}

	public void setRejectNotes(String rejectNotes) {
		this.rejectNotes = rejectNotes;
	}

	public String getIsApprove() {
		return isApprove;
	}

	public void setIsApprove(String isApprove) {
		this.isApprove = isApprove;
	}
	
}
