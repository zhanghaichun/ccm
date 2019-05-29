package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.Dispute;
import com.saninco.ccm.model.Proposal;
import com.saninco.ccm.vo.SearchInvoiceVO;

public interface IProposalDao {

	public void save(Proposal transientInstance);
	public Integer saveProposal(Proposal transientInstance);
	public Integer getDisputeTabTotalNoOfInoiceDetail(SearchInvoiceVO svo);
	public List<String> searchDisputeTabOfInoiceDetail(SearchInvoiceVO svo);
	public void updateDisputeTabOfInvoiceDetail(String proposalIds,int disputeId);
	public Integer getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId(int vendorId,int disputeReasonId);
	public Integer getDisputeIndexInOnDayOfInoiceDetailByVendorIdAndDisputeReasonId(String claimNumber);
	public Proposal findById(java.lang.Long id);
	public Proposal merge(Proposal detachedInstance);
	public void updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetailByDisputeId(Integer disputeId);
	public Integer getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(SearchInvoiceVO svo);
	public List<String> searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(SearchInvoiceVO svo);
	public void updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail(String proposalIds);
	public List getProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeIdDownExcel(SearchInvoiceVO svo);
	public List getProposalsOfInoiceDetailDisputeTabByInvoiceIdDownExcel(SearchInvoiceVO svo);
	public Integer getAnnexsTotalOfProposalByProposalId(SearchInvoiceVO svo);
	public List searchAnnexsOfProposalByProposalId(SearchInvoiceVO svo);
	public List<String> getProposalDataLeftByInvoiceId(SearchInvoiceVO svo,String sProposalIds);
	public Integer getProposalsNumberOfDisputeToExcelForSendEmailByDisputeId(Dispute dispute);
	public List<String> getProposalDataRightByInvoiceIdAndDisputeId(SearchInvoiceVO svo,String proposalIds);
	public List searchProposalsOfDisputeToExcelForSendEmailByDisputeId(Dispute dispute);
	public void commit();
	public Double getAountByInvoiceAndAccount(int invoiceId, Integer id);
	public void updateAccountByInvoiceAndAccount(int invoiceId, Integer id, Integer integer);
}
