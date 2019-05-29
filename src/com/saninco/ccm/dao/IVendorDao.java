/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.Contact;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.model.VendorStatus;
import com.saninco.ccm.vo.ContactVO;
import com.saninco.ccm.vo.SearchVendorVo;


public interface IVendorDao {
	
	public long getContactCount(ContactVO cvo);
	
	public long getTariffsNO(ContactVO cvo) ;
	
	public long getBanExemptionCertificateNO(ContactVO cvo,String invoiceDate);
	
	public long getRecurringChargeTotal(SearchVendorVo svvo) ;

	public List<String> searchRecurringCharge(SearchVendorVo svvo) ;
	
	public long getSearchSortProposalTotalPageNo(SearchVendorVo svvo) ;

	public List<String> getSearchSortProposalTable(SearchVendorVo svvo) ;
	
	public long getSearchPaymentTotalPageNo(SearchVendorVo svvo) ;
	
	public List<String> getSearchPaymentTable(SearchVendorVo svvo) ;
	
	public long getAllChargeTotal(SearchVendorVo svvo) ;
	
	public List getVendors();

	public List<Object[]> getUserPreviledgedVendors(Integer userId);

	public Vendor findById(java.lang.Integer id);

	public Vendor load(Integer vendorId);

	public List<VendorStatus> getVendorStatus();

	public long getVendorContactNO(ContactVO cvo);

	public List<String> searchVendorContact(ContactVO cvo);
	
	public List<String> searchTariff(ContactVO cvo);
	
	public List<String> searchBanExemptionCertificate(ContactVO cvo,String invoiceDate);
	
	public void deleteBanExemptionCertificate(Integer id);
	
	public List<String> searchAllCharges(SearchVendorVo svvo);

	public void updateVendorMainData(String UpvendorvendorNameTxt,Integer vendorId, String vendorAcronymM, String summaryVendorNameM,Integer vendorStatusM,String channel,String lpcRate);

	public void delectContact(Integer contactId);

	public void save(Contact transientInstance);

	public void editContact(ContactVO cvo);

	public void saveVendor(Vendor vendor);

	public VendorStatus findByStatusId(java.lang.Integer id);

	public Integer findMaxId();

	public List contactList(ContactVO cvo);

	public void deleteVendor(Integer id);
	
	public List<Object[]> getSearchConditionVendors();
	
	public List<Object[]> getSearchConditionSummaryVendors();
	
	public List<Object[]> searchAllChargesToExcel(SearchVendorVo svvo);
	
	public List<Object[]> searchRecurringChargeToExcel(SearchVendorVo svvo);

	public List<Object[]> searchSortProposalToExcel(SearchVendorVo svvo);

	public List<Object[]> searchPaymentToExcel(SearchVendorVo svvo);
	
}
