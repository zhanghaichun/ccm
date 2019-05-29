/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.vo.BanVO;
import com.saninco.ccm.vo.SearchReportUserVO;
import com.saninco.ccm.vo.WorkspaceVO;

public interface IBanDao {
	
	public void updateInvoiceByBan(Integer analystId,Integer banId);
	
	public long getNumberOfAccountNumber(BanVO bvo);
	
	public List getBanByVendorId(Integer vendorId);
	
	public List getUserPreviledgedBanByVendorId(Integer userId, Integer vendorId);
	
	public abstract void save(Ban transientInstance);

	public abstract void delete(Ban persistentInstance);

	public abstract Ban findById(java.lang.Integer id);

	public abstract List findByExample(Ban instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByAccountNumber(Object accountNumber);

	public abstract List findByBanPrimiaryContactId(Object banPrimiaryContactId);

	public abstract List findByBanDefaultPayeeId(Object banDefaultPayeeId);

	public abstract List findByBanAddress(Object banAddress);

	public abstract List findByBanDisputeEmail(Object banDisputeEmail);

	public abstract List findByCreatedBy(Object createdBy);

	public abstract List findByModifiedBy(Object modifiedBy);

	public abstract List findByRecActiveFlag(Object recActiveFlag);

	public abstract List findAll();

	public abstract Ban merge(Ban detachedInstance);

	public abstract void attachDirty(Ban instance);
	
	public List<Ban> getAllBan();
	
	public void saveBanExemption(Map<String, String> banExemption);
	
	public void updateBanExemption(Map<String, String> banExemption);
	
	public String getVendorNameByBanId(SearchReportUserVO rvo);
	
	public List<Object[]> getBanStatus(Integer banStatusId);

	public List getLineofBbusiness();
	
	public List getBanInactiveReason();

	public long getBanTotalNO(BanVO bvo);

	public List searchBanList(BanVO bvo);

	public List getAccountType();

	public List getInvoiceFrequency();

	public List getPaymentTerm();

	public List getPaymentGroup();

	public List searchBanListExcel(BanVO bvo);

	public Integer getBanCountByBanName(Ban b);

	public void updateInvoicesForBan(Ban b);

	public List<Object[]> getContactByVendorId(BanVO bvo);
	
	public abstract void attachClean(Ban instance);
	
	public abstract Ban load(Integer banId);
	
	public String getValidateBanLock(Integer banId);
	
	public Integer findMaxId();
	
	public String getAnalystName(Integer analystId);

	public Long getAccCountByName(String accNoName);

	public String getVendorNameByVendorId(Integer id);

	public List getInvoiceChannelJsonByLineOsBussiness(
			String lineOfBussiness);

	public List getScoaCodeTypeJsonByLineOsBussiness(String lineOfBussiness);

	public List getInvoiceFormatJsonByLineOsBussinessAndChannel(
			String lineOfBussiness, String invoiceChannel);
	public List<Ban> getSearchConditionBan();
	
	public void updateInvoiceAndEmail(Ban ban);
	
	public Integer getInvoiceByBan(Ban b);
	
	public String getApprovalLevel();
	
	public String getAutoPayMaxCount();
	

	public long getBanWorkCountNOPaymentInException(WorkspaceVO wvo,Integer banStatusId);
	
	public void updateBanStatusId(Integer banId,Integer banStatusId,String rejectNotes);
	
	public void deleteBanById(Integer banId);
}
