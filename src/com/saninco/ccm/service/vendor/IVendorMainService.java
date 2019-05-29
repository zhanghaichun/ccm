package com.saninco.ccm.service.vendor;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.TariffLink;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.vo.ContactVO;
import com.saninco.ccm.vo.SearchVendorVo;

public interface IVendorMainService {
	public String getVendorTotalPageNo(ContactVO cvo) throws BPLException;

	public String SearchVendorList(ContactVO cvo) throws BPLException;
	public String getTariffslPageNo(ContactVO cvo) throws BPLException;
	
	public String getVendorByvendorId(Integer id) throws BPLException;
	public String getTariffList(ContactVO cvo) throws BPLException ;
	
	public String getBanExemptionCertificateTotalPageNo(ContactVO cvo,String invoiceDate) throws BPLException;
	public String getSearchBanExemptionCertificate(ContactVO cvo,String invoiceDate) throws BPLException ;
	public void deleteBanExemptionCertificate(Integer id)throws BPLException;
	
	
	public String getRecurringChargeTotalPageNo(SearchVendorVo svvo) throws BPLException ;
	public String getRecurringChargeList(SearchVendorVo svvo) throws BPLException ;
	public String getSearchSortProposalTotalPageNo(SearchVendorVo svvo) throws BPLException ;
	public String getSearchSortProposalTable(SearchVendorVo svvo) throws BPLException ;
	public String getSearchPaymentTotalPageNo(SearchVendorVo svvo) throws BPLException ;
	public String getSearchPaymentTable(SearchVendorVo svvo) throws BPLException ;
	public String getAllChargeTotalPageNo(SearchVendorVo svvo) throws BPLException ;
	public String getAllChargesList(SearchVendorVo svvo) throws BPLException ;
	
	public void updateVendorMainData(String UpvendorvendorNameTxt,Integer vendorId,String vendorAcronymM,String summaryVendorNameM,Integer vendorStatusM,String channel ,String lpcRate) throws BPLException;
	
	public void deleteConcate(Integer concateId)throws BPLException;
	
	public String addConcate(ContactVO cvo)throws BPLException;
	
	public String searchContact(ContactVO cvo)throws BPLException;
	
	public void updateContact(ContactVO cvo) throws BPLException;
	
	public String saveVendor(String vendorName,String vendorAcronym,String summartVendorName,int vendorStatus,String channel,String lpcRate)throws BPLException;
	
	public Integer getCoutactVendorId()throws BPLException;
	
	public String saveExcelByContact(ContactVO cvo,String excelDirPath,List<String> titles) throws BPLException ;
	
	public void deleteVendor(Integer id)throws BPLException;
	
	public String createAllChargesToExcel(SearchVendorVo svvo,String excelDirPath,List<String> titles) throws BPLException ;
	
	public String createRecurringChargeToExcel(SearchVendorVo svvo,String excelDirPath,List<String> titles) throws BPLException ;
	
	public String createSortProposalToExcel(SearchVendorVo svvo,String excelDirPath,List<String> titles) throws BPLException ;

	public String createPaymentlToExcel(SearchVendorVo svvo,String excelDirPath,List<String> titles) throws BPLException ;
}
