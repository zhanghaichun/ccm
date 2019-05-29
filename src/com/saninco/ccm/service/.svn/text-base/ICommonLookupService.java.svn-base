/**
 * 
 */
package com.saninco.ccm.service;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.TariffLink;
import com.saninco.ccm.model.Theme;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author joe.yang
 */
public interface ICommonLookupService {
	
	public List<MapEntryVO<String, String>> getInvoiceSt() throws BPLException;
	
	public List<MapEntryVO<String, String>> getCurrency() throws BPLException;
	
	public List<MapEntryVO<String, String>> getInvoiceStatus() throws BPLException;
	
	public List<MapEntryVO<String, String>> getPaymentStatus() throws BPLException;
	
	public List<MapEntryVO<String, String>> getRole() throws BPLException;
	
	public List<MapEntryVO<String, String>> getLabels() throws BPLException;
	
	public List<MapEntryVO<String, String>> getAllFunctions() throws BPLException;
	
	public List<MapEntryVO<String, String>> getAccountCode() throws BPLException;
	
	public List<MapEntryVO<String, String>> getAllAccountCode() throws BPLException;
	
	public List<MapEntryVO<String, String>> getChargeType() throws BPLException;
	
	public List<MapEntryVO<String, String>> getTaxCode() throws BPLException;
	
	public void saveUploadAttachFileByBanExemption (List<File> files,List<String> fileNames, Map<String, String> params );
	
	public void uploadExemption (Map<String, String> params ) throws BPLException;

	
	public List<MapEntryVO<String, String>> getDisputeStatus() throws BPLException;
	
	public List<MapEntryVO<String, String>> getDisputeReason() throws BPLException;

	public List<MapEntryVO<String, String>> getAnalyst() throws BPLException ;
	
	public List<MapEntryVO<String, String>> getCreditStatus() throws BPLException;
	
	public List<MapEntryVO<String, String>> getTicketStatus() throws BPLException;
	
	public List<MapEntryVO<String, String>> getPriority() throws BPLException;
	
	public List<MapEntryVO<String, String>> getSeverity() throws BPLException;
	
	public List<MapEntryVO<String, String>> getRtagsNameList() throws BPLException;
	
	public List<MapEntryVO<String, String>> getCreatedBy() throws BPLException;
	
	public List<MapEntryVO<String, String>> getAllUserList() throws BPLException;

	public List<MapEntryVO<String, String>> getAllVendors() throws BPLException;
	
	public List<MapEntryVO<String, String>> getAllBansByVendorId(Integer vendorId) throws BPLException;
	
	public List<MapEntryVO<String, String>> getUserPreviledgedVendors() throws BPLException;
	
	public List<MapEntryVO<String, String>> getUserPreviledgedBanByVendorId(Integer vendorId) throws BPLException;
	
	public List<MapEntryVO<String, String>> getAllOriginators() throws BPLException;
	
	public String getUserPreviledgedBansJsonStrByVendorId(Integer vendorId) throws BPLException;
	
	public String getUserPreviledgedBansJsonStrByVendorId0(Integer vendorId) throws BPLException;
	
	public String getAllBansJsonStrByVendorId(Integer vendorId) throws BPLException;
	
	public String getUserPreviledgedVendorsJson() throws BPLException;
	
	public String getUserWorcspaceJson() throws BPLException;
	
	public List<MapEntryVO<String, String>> getAnalysts() throws BPLException;
	
	public String getMyWorkspaceAllCountArray()throws BPLException;
	
	public String getDisputesBucketCount()throws BPLException;
		
	public String getMyWorkspaceBackMockup();
	
	public String getUserDelegationList(WorkspaceVO workspaceVO) throws BPLException;
	
	public String getMyWorkspaceSupervisor();
	
	public String getMyWorkspaceMyTeamUserCounts(WorkspaceVO workspaceVO) throws BPLException;
	
	public String searchWorkspaceMissing (WorkspaceVO workspaceVO) throws BPLException;
	
	public String searchWorkspaceProsing (WorkspaceVO workspaceVO) throws BPLException;
	
	public String getUsersJsonStrBySupervisorUserId(int supervisorUserId) throws BPLException;
	
	public void saveUploadFile(String tableName,Integer id,List<File> files,List<String> fileNames);
	
	public AttachmentPoint createAttachmentPoint(String tableName);
	
	public AttachmentPoint saveUploadFileAndGetPointId(String tableName,List<File> files,List<String> fileNames);
	
	public AttachmentPoint saveUploadFileAndGetPointId(String tableName,List<File> files,List<String> fileNames,String attachmentPointId);
	
	public List<TariffLink> saveUploadFileToTariffLink(List<File> files,List<String> fileNames, TariffLink TariffLink);
	
	public void saveUploadFileToTariffLinkByVendor (List<File> files,List<String> fileNames, TariffLink params ) ;
	
	public List<AttachmentFile> saveUploadFileToPointId(AttachmentPoint ap,List<File> files,List<String> fileNames);
	
	public List<MapEntryVO<String, String>> getDisputeByInvoiceId(int invoiceId) throws BPLException;
 
	public List<MapEntryVO<String, String>> getThemes() throws BPLException;
	
	public Theme getUserTheme() throws BPLException;
	
	public String getAttachmentPointIdCountService( SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String searchAttachmentPointIdService(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public List searchAttachmentPointIdList(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	
	
	public String getDisputeAttachmentPointIdCountService( SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String getDisputeSearchAttachmentPointIdService(SearchInvoiceVO searchInvoiceVO) throws BPLException;
	
	public String changeUserTheme(Integer themeId);
 
	public void saveEvent(String messageType, String eventMessage,String keyData,String eventData );

	public boolean validatePreviledgeByBan(Ban ban) throws BPLException;
	
	public boolean validatePreviledgeByVendor(Vendor vendor) throws BPLException;
	
	public boolean validatePreviledgeByVendorAndBan(Ban ban, Vendor vendor) throws BPLException;
	
	public List<MapEntryVO<String, String>> getBanStatus(Integer banStatusId) throws BPLException;
	
	public List<MapEntryVO<String, String>> getInvoiceChannel() throws BPLException;
	
	public List<MapEntryVO<String, String>> getPaymentMethod() throws BPLException;
	
	public List<MapEntryVO<String, String>> getInvoiceFormat() throws BPLException;
	
	public List<MapEntryVO<String, String>> getLineofBbusiness() throws BPLException;
	
	public List<MapEntryVO<String, String>> getBanInactiveReason() throws BPLException;
	
	public List<MapEntryVO<String, String>> getContact() throws BPLException;
	
	public List<MapEntryVO<String, String>> getAccountType() throws BPLException;
	
	public List<MapEntryVO<String, String>> getInvoiceFrequency() throws BPLException;
	
	public List<MapEntryVO<String, String>> getPaymentTerm() throws BPLException;
	
	public List<MapEntryVO<String, String>> getPaymentGroup() throws BPLException;
	
	public List<MapEntryVO<String, String>> getAllBans()  throws BPLException;
	
	public boolean validateBackupAndDelegation(String currentAnalystId) throws BPLException;

	public List<MapEntryVO<String, String>> getVendorsStatus() throws BPLException;
	
	public List<MapEntryVO<String, String>> getAnalystByRoleId() throws BPLException;
	
	public List<MapEntryVO<String, String>> getApprover1ByRoleId() throws BPLException;
	
	public List<MapEntryVO<String, String>> getApprover2ByRoleId() throws BPLException ;
	
	public List<MapEntryVO<String, String>> getApprover3ByRoleId() throws BPLException;

	public List<MapEntryVO<String, String>> getNYList()throws BPLException;

	public List<MapEntryVO<String, String>> getYNList()throws BPLException;
	
	public List<MapEntryVO<String, String>> getMNList()throws BPLException;

	public List<MapEntryVO<String, String>> scoaCodeTypeList()throws BPLException;

	public List<MapEntryVO<String, String>> getAccountCodeListByDisputeProposals(Integer id)throws BPLException;

	public void saveInvoiceUserActionLog(Integer invoiceId) throws BPLException;
	
	public void saveDisputeUserActionLog(Integer disputeId) throws BPLException;

	public void saveLogOutUserActionLog() throws BPLException;
	
	public List<MapEntryVO<String, String>> getSearchConditionBans()  throws BPLException;
	
	public List<MapEntryVO<String, String>> getSearchConditionVendors() throws BPLException;
	
	public List<MapEntryVO<String, String>> getSearchConditionSummaryVendors() throws BPLException;
	
	public List<MapEntryVO<String, String>> getSearchConditionProduct() throws BPLException;
	
	public List<MapEntryVO<String, String>> getApprover4ByRoleId() throws BPLException;
	
	public List<MapEntryVO<String, String>> getAutoPayCountList() throws BPLException;
	
	public List<MapEntryVO<String, String>> getSearchConditioncustomers()  throws BPLException;

	public Map<String, String> findSysConfig();

	public String isFileDownLoad (int id,String token) throws BPLException;
	
	public Map <String,String> selectFileDownLoad (int id);
	
	public Integer findfileDownloadJournal (int id) throws BPLException;
	
	public void savefileDownloadJournal(String loginFlag,int fileDownloadId,String ip) throws BPLException;
	
}
