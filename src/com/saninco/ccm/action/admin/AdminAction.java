package com.saninco.ccm.action.admin;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.dao.DaoUtil;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.filter.AllGranted;
import com.saninco.ccm.filter.AnyGranted;
import com.saninco.ccm.model.TariffLink;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.service.vendor.IVendorMainService;
import com.saninco.ccm.vo.ContactVO;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.SearchUserListVO;

/**
 * @author Chao.Liu
 *
 */
public class AdminAction extends CcmActionSupport {
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IQuicklinkService quicklinkService;
	private IVendorMainService vendorMainsService;
	
	private Vendor vendor = new Vendor();
	
	private ContactVO cvo = new ContactVO();
	private SearchUserListVO uvo;
	
	private Integer vendorMainId;
	private String UpvendorAcronym;
	private String UpsummartVendorName;
	private Integer UpvendorStatusSelect;
	private Integer deleteConcateId;
	private String aVendorName;
	private String channel;
	private String lpcRate;
	private String aChannel;
	private String aVendorAcronym;
	private String aSummartVendorName;
	private Integer aVendorStatus;
	private String aLpcRate;
	private Integer coutactVendorId;
	private String UpvendorvendorNameTxt;
	private Integer vendorTowId;
	private String vendorId;
	private List<String> titles;
	private List<File> uploads;
	private List<String> uploadsFileName;
	private Integer vendorCircuitId;
	private String uploadNotes;
	private Integer banExemptionId;
	private Integer banId;
	private String termStartDate;
	private String termEndDate;
	private String invoiceDate;
	
	private List<MapEntryVO<String, String>> vendorStatusList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> backupUserList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> supervisorUserList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banStatus = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> invoiceChannel = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> paymentMethod = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> invoiceFormat = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> lineofBbusiness = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> analyst = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> analystByRoleId = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> Approver1 = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> Approver2 = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> Approver3 = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> rtagsNameList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> rtagsList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> rolesList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> quicklinkList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banInactiveReason = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> scoaCodeList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> taxCodeList = new ArrayList<MapEntryVO<String, String>>();
	
	private List<MapEntryVO<String, String>> secuirtyuserList = new ArrayList<MapEntryVO<String, String>>();
	
	private Map<String, String> banExemption = new HashMap<String, String>(); 
	




	public AdminAction() {
		
	}
	
	@AnyGranted(value="FUNCTION_4100,FUNCTION_4110,FUNCTION_4120,FUNCTION_4130,FUNCTION_4140,FUNCTION_4200,FUNCTION_4400,FUNCTION_4500,FUNCTION_4700,FUNCTION_4800,FUNCTION_4900")
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		downloadList();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	private void downloadList() throws BPLException {
		this.backupUserList = commonLookupService.getCreatedBy();
		this.supervisorUserList = this.backupUserList;
		this.secuirtyuserList=this.backupUserList;
//		this.secuirtyuserList=commonLookupService.getAllUserList(); �ɲ�ѯ system user .
		this.uvo = new SearchUserListVO();
		this.banStatus = commonLookupService.getBanStatus(null);
		this.invoiceChannel = commonLookupService.getInvoiceChannel();
		this.paymentMethod = commonLookupService.getPaymentMethod();
		this.invoiceFormat = commonLookupService.getInvoiceFormat();
		this.lineofBbusiness = commonLookupService.getLineofBbusiness();
		this.vendorStatusList = commonLookupService.getVendorsStatus();
		this.vendorList = commonLookupService.getUserPreviledgedVendors();
		this.analyst = commonLookupService.getAnalyst();
		this.analystByRoleId = commonLookupService.getAnalystByRoleId();
		this.Approver1 = commonLookupService.getApprover1ByRoleId();
		this.Approver2 = commonLookupService.getApprover2ByRoleId();
		this.Approver3 = commonLookupService.getApprover3ByRoleId();
		this.rtagsList = commonLookupService.getRtagsNameList();
		this.rolesList = commonLookupService.getRole();
		this.rtagsNameList = commonLookupService.getRtagsNameList();
		this.quicklinkList = quicklinkService.getUserQuicklinks();
		this.banInactiveReason=commonLookupService.getBanInactiveReason();
		this.scoaCodeList = commonLookupService.getAccountCode();
		this.taxCodeList = commonLookupService.getTaxCode();
		
		
	}
	/**
	 *  objective:Preserves vendor
	 *  executive:AVendorName will aVendorAcronym, aSummartVendorName, aVendorStatus save to vendor
	 * @author pengfei.wang
	 * */
	public String getSaveVendor() throws Exception{
		logger.info("Enter method getSaveVendor.");
		String result = null;
		try{
			result = vendorMainsService.saveVendor(aVendorName, aVendorAcronym, aSummartVendorName, aVendorStatus, aChannel,aLpcRate);
		}catch(Exception e){
			logger.error("getSaveVendor error: ", e);
			result = "{error:\"getSaveVendor error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getSaveVendor.");
		this.writeOutputStream(result);
		return null;
	}
	
	public String doUploadAttachFileByVendor() {
		logger.info("Enter method doUploadAttachFile.");
		try {
			TariffLink parmas = new TariffLink();
			parmas.setVendorId(vendorCircuitId);
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
	 * @author pengfei.wang
	 * objective:Modify a Contact
	 * executive:Cvo will exist in data storage jump to the background
	 * */
	public String getEditContact() throws Exception{
		logger.info("Enter method getEditContact.");
		try{
			vendorMainsService.updateContact(cvo);
		}catch(Exception e){
			logger.error("getEditContact error: ", e);
		}
		logger.info("Exit method getEditContact.");
		return SUCCESS;
	}
	
	public void getVerifyContact() throws Exception{
		logger.info("Enter method getVerifyContact.");
		String result = null;
		try{
			result = vendorMainsService.searchContact(cvo);
		}catch(Exception e){
			logger.error("getVerifyContact error: ", e);
			result = "{error:\"getVerifyContact error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method getVerifyContact.");
	}
	
	/**
	 * @author pengfei.wang
	 * objective:Add a Contact
	 * executive:Cvo will exist in data storage jump to the background
	 * */
	public String getAddContact() throws Exception{
		logger.info("Enter method getAddContact.");
		String result;
		try{
			vendorMainsService.addConcate(cvo);
		}catch(Exception e){
			logger.error("getAddContact error: ", e);
			result = "{error:\"getAddContact error: "+e.getMessage()+"\"}";
			this.writeOutputStream(result);
		}
		logger.info("Exit method getAddContact.");
		return SUCCESS;
	}
	
	/**
	 * @author pengfei.wang
	 * objective:According to the id deletes a Contact
	 * executive:Will get id to delete this background, and then a contact
	 * */
	public String getDeleteContact() throws Exception{
		logger.info("Enter method getDeleteContact.");
		String result;
		try{
			vendorMainsService.deleteConcate(deleteConcateId);
		}catch(Exception e){
			logger.error("getDeleteContact error: ", e);
			result = "{error:\"getDeleteContact error: "+e.getMessage()+"\"}";
			this.writeOutputStream(result);
		}
		logger.info("Exit method getDeleteContact.");
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * objective:According to the id deletes a Contact
	 * executive:Will get id to delete this background, and then a vendor
	 * */
	public String getDeleteVendor() throws Exception{
		logger.info("Enter method getDeleteVendor.");
		String result;
		try{
			vendorMainsService.deleteVendor(vendorTowId);
		}catch(Exception e){
			logger.error("getDeleteVendor error: ", e);
			result = "{error:\"getDeleteVendor error: "+e.getMessage()+"\"}";
			this.writeOutputStream(result);
		}
		logger.info("Exit method getDeleteVendor.");
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * objective:According to the vendor id modified with
	 * executive:Will get id and to modify the content to change the background
	 * */
	public String getUpdateVendor() throws Exception{
		logger.info("Enter method getUpdateVendor.");
		String result;
		try{
			vendorMainsService.updateVendorMainData(UpvendorvendorNameTxt,vendorMainId, UpvendorAcronym, UpsummartVendorName, UpvendorStatusSelect, channel,lpcRate);
		}catch(Exception e){
			logger.error("getUpdateVendor error: ", e);
			result = "{error:\"getUpdateVendor error: "+e.getMessage()+"\"}";
			this.writeOutputStream(result);
		}
		logger.info("Exit method getUpdateVendor.");
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * objective:Query with data
	 * executive:According to background check id on that data
	 * */
	public String getVendorSearchList()  throws Exception {
		logger.info("Enter method getVendorSearchList.");
		String result;
		try{
			result = vendorMainsService.getVendorByvendorId(vendorMainId);
		}catch(Exception e){
			logger.error("getVendorSearchList error: ", e);
			result = "{error:\"getVendorSearchList error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getVendorSearchList.");
		this.writeOutputStream(result);
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Contact with vendor query data in the table
	 */
	public String getSearchVendorMain()  throws Exception {
		logger.info("Enter method getSearchVendorMain.");
		String result;
		try{
			result = vendorMainsService.SearchVendorList(cvo);
		}catch(Exception e){
			logger.error("doSearchVendorMain error: ", e);
			result = "{error:\"getSearchVendorMain error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getSearchVendorMain.");
		this.writeOutputStream(result);
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * @return
	 * @throws Exception
	 * Contact with vendor statistics of the table
	 * */
	public String getVendorMainTotalPageNo() throws Exception{
		logger.info("Enter method getVendorMainTotalPageNo.");
		String result;
		try{
			result = vendorMainsService.getVendorTotalPageNo(cvo);
		}catch(Exception e){
			logger.error("getVendorMainTotalPageNo error: ", e);
			result = "{error:\"getVendorMainTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getVendorMainTotalPageNo.");
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * @throws Exception
	 * Contact with contact query data in the table
	 */
	public String getSearchContactVendorMain()  throws Exception {
		logger.info("Enter method getSearchContactVendorMain.");
		String result;
		try{
			cvo.setVendor((vendorMainsService.getCoutactVendorId()).toString());
			result = vendorMainsService.SearchVendorList(cvo);
		}catch(Exception e){
			logger.error("getSearchContactVendorMain error: ", e);
			result = "{error:\"getSearchContactVendorMain error: "+e.getMessage()+"\"}";
		}
		logger.info("Exit method getSearchContactVendorMain.");
		this.writeOutputStream(result);
		return null;
	}
	
	public String getTariffsTotalPageNo() throws Exception{
		logger.info("Enter method getTariffsTotalPageNo.");
		String result;
		try{
			result = vendorMainsService.getTariffslPageNo(cvo);
		}catch(Exception e){
			logger.error("getTariffsTotalPageNo error: ", e);
			result = "{error:\"getTariffsTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTariffsTotalPageNo.");
		return null;
	}
	
	public String getTariffs()  throws Exception {
		logger.info("Enter method getTariffs.");
		String result;
		try{
			result = vendorMainsService.getTariffList(cvo);
		}catch(Exception e){
			logger.error("getTariffs error: ", e);
			result = "{error:\"getTariffs error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTariffs.");
		return null;
	}
	public String getBanExemptionCertificateTotalPageNo() throws Exception{
		logger.info("Enter method getBanExemptionCertificateTotalPageNo.");
		String result;
		try{
			result = vendorMainsService.getBanExemptionCertificateTotalPageNo(cvo,invoiceDate);
		}catch(Exception e){
			logger.error("getBanExemptionCertificateTotalPageNo error: ", e);
			result = "{error:\"getBanExemptionCertificateTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getBanExemptionCertificateTotalPageNo.");
		return null;
	}
	public String doUploadAttachFileByBanExemption() {
		logger.info("Enter method doUploadAttachFileByExemption.");
		try {
			
			banExemption.put("termStartDate", termStartDate);
			banExemption.put("termEndDate", termEndDate);
			banExemption.put("banId", banId.toString());
			banExemption.put("notes", DaoUtil.ccmEscape2(uploadNotes));
			commonLookupService.saveUploadAttachFileByBanExemption(uploads,uploadsFileName, banExemption);
			
		} catch (Exception e) {
			logger.error("doUploadAttachFileByExemption error: ", e);
			return INPUT;
		}
		logger.info("Exit method doUploadAttachFileByExemption.");
		return SUCCESS;
	}
	public String doUploadExemption() throws Exception{
		logger.info("Enter method doUploadExemption.");
		String result;
		try {
			
			banExemption.put("termStartDate", termStartDate);
			banExemption.put("termEndDate", termEndDate);
			banExemption.put("banExemptionId", banExemptionId.toString());
			commonLookupService.uploadExemption(banExemption);
			result = "{success:\"success\"}";
			
		} catch (Exception e) {
			logger.error("doUploadExemption error: ", e);
			result = "{error:\"doUploadExemption error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doUploadExemption.");
		return null;
	}
	public String getSearchBanExemptionCertificate()  throws Exception {
		logger.info("Enter method getSearchBanExemptionCertificate.");
		String result;
		try{
			result = vendorMainsService.getSearchBanExemptionCertificate(cvo,invoiceDate);
		}catch(Exception e){
			logger.error("getSearchBanExemptionCertificate error: ", e);
			result = "{error:\"getSearchBanExemptionCertificate error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getSearchBanExemptionCertificate.");
		return null;
	}
	
	public String deleteBanExemptionCertificate() {
		logger.info("Enter method deleteBanExemptionCertificate.");
		try {
			vendorMainsService.deleteBanExemptionCertificate(banExemptionId);
		} catch (Exception e) {
			logger.error("deleteBanExemptionCertificate error: ", e);
			return INPUT;
		}
		logger.info("Exit method deleteBanExemptionCertificate.");
		return SUCCESS;
	}
	
	/**
	 * @author pengfei.wang
	 * @throws Exception
	 * Contact with contact statistics of the table
	 * */
	public String getContactVendorMainTotalPageNo() throws Exception{
		logger.info("Enter method getContactVendorMainTotalPageNo.");
		String result;
		try{
			cvo.setVendor((vendorMainsService.getCoutactVendorId()).toString());
			result = vendorMainsService.getVendorTotalPageNo(cvo);
		}catch(Exception e){
			logger.error("getContactVendorMainTotalPageNo error: ", e);
			result = "{error:\"getContactVendorMainTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getContactVendorMainTotalPageNo.");
		return null;
	}
	
	/**
	 * @author pengfei.wang
	 * add a vendor
	 * */
	public String getAddVendorContact() throws Exception{
		logger.info("Enter method getAddVendorContact.");
		String result;
		try{
			cvo.setVendor((vendorMainsService.getCoutactVendorId()).toString());
			result=vendorMainsService.addConcate(cvo);
		}catch(Exception e){
			logger.error("getContactVendorMainTotalPageNo error: ", e);
			result = "{error:\"getContactVendorMainTotalPageNo error: "+e.getMessage()+"\"}";
			this.writeOutputStream(result);	
		}
		logger.info("Exit method getAddVendorContact.");
		return SUCCESS;
	}
	
	/**
	 * @author Pengfei.Wang
	 * DownLoad Manually Created contact Excel
	 * @return
	 * @throws Exception
	 */
	public String getExcelByNewContact()throws Exception{
		logger.info("Exit method saveExcelByPaymentTabMisc.");
		FileInputStream fis = null;
		try {
			cvo.setVendor((vendorMainsService.getCoutactVendorId()).toString());
			String fileName = "NewContact.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = vendorMainsService.saveExcelByContact(cvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByPaymentTabMisc error: ", e);
		}
		logger.info("Exit method saveExcelByPaymentTabMisc.");
		return null;
	}
	
	/**
	 * @author Pengfei.Wang
	 * DownLoad Manually Created contact Excel
	 * @return
	 * @throws Exception
	 */
	public String getExcelByContact()throws Exception{
		logger.info("Exit method saveExcelByPaymentTabMisc.");
		FileInputStream fis = null;
		try {			
			String fileName = "Contact.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = vendorMainsService.saveExcelByContact(cvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("saveExcelByPaymentTabMisc error: ", e);
		}
		logger.info("Exit method saveExcelByPaymentTabMisc.");
		return null;
	}
	
	public SearchUserListVO getUvo() {
		return uvo;
	}

	public void setUvo(SearchUserListVO uvo) {
		this.uvo = uvo;
	}

	public List<MapEntryVO<String, String>> getBackupUserList() {
		return backupUserList;
	}

	public void setBackupUserList(
			List<MapEntryVO<String, String>> backupUserList) {
		this.backupUserList = backupUserList;
	}

	public List<MapEntryVO<String, String>> getSupervisorUserList() {
		return supervisorUserList;
	}

	public void setSupervisorUserList(
			List<MapEntryVO<String, String>> supervisorUserList) {
		this.supervisorUserList = supervisorUserList;
	}
	
	public List<MapEntryVO<String, String>> getAnalystByRoleId() {
		return analystByRoleId;
	}

	public void setAnalystByRoleId(List<MapEntryVO<String, String>> analystByRoleId) {
		this.analystByRoleId = analystByRoleId;
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
	
	public List<MapEntryVO<String, String>> getApprover1() {
		return Approver1;
	}

	public void setApprover1(List<MapEntryVO<String, String>> approver1) {
		Approver1 = approver1;
	}

	public List<MapEntryVO<String, String>> getApprover2() {
		return Approver2;
	}

	public void setApprover3(List<MapEntryVO<String, String>> approver3) {
		Approver3 = approver3;
	}

	public void setApprover2(List<MapEntryVO<String, String>> approver2) {
		Approver2 = approver2;
	}

	public List<MapEntryVO<String, String>> getApprover3() {
		return Approver3;
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

	public List<MapEntryVO<String, String>> getRtagsNameList() {
		return rtagsNameList;
	}

	public void setRtagsNameList(List<MapEntryVO<String, String>> rtagsNameList) {
		this.rtagsNameList = rtagsNameList;
	}

	public List<MapEntryVO<String, String>> getRtagsList() {
		return rtagsList;
	}

	public void setRtagsList(List<MapEntryVO<String, String>> rtagsList) {
		this.rtagsList = rtagsList;
	}

	public List<MapEntryVO<String, String>> getRolesList() {
		return rolesList;
	}

	public void setRolesList(List<MapEntryVO<String, String>> rolesList) {
		this.rolesList = rolesList;
	}

	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public List<MapEntryVO<String, String>> getQuicklinkList() {
		return quicklinkList;
	}

	public void setQuicklinkList(List<MapEntryVO<String, String>> quicklinkList) {
		this.quicklinkList = quicklinkList;
	}


	public List<MapEntryVO<String, String>> getBanInactiveReason() {
		return banInactiveReason;
	}

	public List<MapEntryVO<String, String>> getTaxCodeList() {
		return taxCodeList;
	}

	public void setTaxCodeList(List<MapEntryVO<String, String>> taxCodeList) {
		this.taxCodeList = taxCodeList;
	}

	public List<MapEntryVO<String, String>> getScoaCodeList() {
		return scoaCodeList;
	}

	public void setScoaCodeList(List<MapEntryVO<String, String>> scoaCodeList) {
		this.scoaCodeList = scoaCodeList;
	}

	public void setBanInactiveReason(
			List<MapEntryVO<String, String>> banInactiveReason) {
		this.banInactiveReason = banInactiveReason;
	}

	public List<MapEntryVO<String, String>> getVendorStatusList() {
		return vendorStatusList;
	}

	public void setVendorStatusList(
			List<MapEntryVO<String, String>> vendorStatusList) {
		this.vendorStatusList = vendorStatusList;
	}

	public IVendorMainService getVendorMainsService() {
		return vendorMainsService;
	}

	public void setVendorMainsService(IVendorMainService vendorMainsService) {
		this.vendorMainsService = vendorMainsService;
	}

	public ContactVO getCvo() {
		return cvo;
	}

	public void setCvo(ContactVO cvo) {
		this.cvo = cvo;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public Integer getVendorMainId() {
		return vendorMainId;
	}

	public void setVendorMainId(Integer vendorMainId) {
		this.vendorMainId = vendorMainId;
	}

	public String getUpvendorAcronym() {
		return UpvendorAcronym;
	}

	public void setUpvendorAcronym(String upvendorAcronym) {
		UpvendorAcronym = upvendorAcronym;
	}

	public String getUpsummartVendorName() {
		return UpsummartVendorName;
	}

	public void setUpsummartVendorName(String upsummartVendorName) {
		UpsummartVendorName = upsummartVendorName;
	}

	public Integer getUpvendorStatusSelect() {
		return UpvendorStatusSelect;
	}

	public void setUpvendorStatusSelect(Integer upvendorStatusSelect) {
		UpvendorStatusSelect = upvendorStatusSelect;
	}

	public Integer getDeleteConcateId() {
		return deleteConcateId;
	}

	public void setDeleteConcateId(Integer deleteConcateId) {
		this.deleteConcateId = deleteConcateId;
	}

	public String getAVendorName() {
		return aVendorName;
	}

	public void setAVendorName(String vendorName) {
		aVendorName = vendorName;
	}

	public String getAVendorAcronym() {
		return aVendorAcronym;
	}

	public void setAVendorAcronym(String vendorAcronym) {
		aVendorAcronym = vendorAcronym;
	}

	public String getASummartVendorName() {
		return aSummartVendorName;
	}

	public void setASummartVendorName(String summartVendorName) {
		aSummartVendorName = summartVendorName;
	}

	public Integer getAVendorStatus() {
		return aVendorStatus;
	}

	public void setAVendorStatus(Integer vendorStatus) {
		aVendorStatus = vendorStatus;
	}

	public Integer getCoutactVendorId() {
		return coutactVendorId;
	}

	public void setCoutactVendorId(Integer coutactVendorId) {
		this.coutactVendorId = coutactVendorId;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
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

	public Integer getVendorCircuitId() {
		return vendorCircuitId;
	}

	public void setVendorCircuitId(Integer vendorCircuitId) {
		this.vendorCircuitId = vendorCircuitId;
	}

	public String getUploadNotes() {
		return uploadNotes;
	}

	public void setUploadNotes(String uploadNotes) {
		this.uploadNotes = uploadNotes;
	}

	public String getUpvendorvendorNameTxt() {
		return UpvendorvendorNameTxt;
	}

	public void setUpvendorvendorNameTxt(String upvendorvendorNameTxt) {
		UpvendorvendorNameTxt = upvendorvendorNameTxt;
	}

	public Integer getVendorTowId() {
		return vendorTowId;
	}

	public void setVendorTowId(Integer vendorTowId) {
		this.vendorTowId = vendorTowId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getAChannel() {
		return aChannel;
	}

	public void setAChannel(String aChannel) {
		this.aChannel = aChannel;
	}
	
	
	public String getaLpcRate() {
		return aLpcRate;
	}

	public String getLpcRate() {
		return lpcRate;
	}

	public void setLpcRate(String lpcRate) {
		this.lpcRate = lpcRate;
	}

	public void setaLpcRate(String aLpcRate) {
		this.aLpcRate = aLpcRate;
	}

	public List<MapEntryVO<String, String>> getSecuirtyuserList() {
		return secuirtyuserList;
	}

	public void setSecuirtyuserList(
			List<MapEntryVO<String, String>> secuirtyuserList) {
		this.secuirtyuserList = secuirtyuserList;
	}

	public Integer getBanExemptionId() {
		return banExemptionId;
	}

	public void setBanExemptionId(Integer banExemptionId) {
		this.banExemptionId = banExemptionId;
	}

	public Integer getBanId() {
		return banId;
	}

	public void setBanId(Integer banId) {
		this.banId = banId;
	}

	public Map<String, String> getBanExemption() {
		return banExemption;
	}

	public void setBanExemption(Map<String, String> banExemption) {
		this.banExemption = banExemption;
	}

	public String getTermStartDate() {
		return termStartDate;
	}

	public void setTermStartDate(String termStartDate) {
		this.termStartDate = termStartDate;
	}

	public String getTermEndDate() {
		return termEndDate;
	}

	public void setTermEndDate(String termEndDate) {
		this.termEndDate = termEndDate;
	}

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}


	
}
