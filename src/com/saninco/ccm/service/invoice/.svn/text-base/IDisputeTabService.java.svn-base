package com.saninco.ccm.service.invoice;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.SearchInvoiceVO;

public interface IDisputeTabService {

	public Integer saveProposalToInvoiceItem(SearchInvoiceVO searchInvoiceVO) throws RuntimeException;
	public String searchDisputeTabOfInoiceDetail(SearchInvoiceVO searchInvoiceVO)throws BPLException;
	public String getDisputeTabTotalNoOfInoiceDetail(SearchInvoiceVO searchInvoiceVO)throws BPLException;
	public void updateDisputeTabMoveSelectGroupOfInvoiceDetail(String[] proposalIds,int disputeId,double totalDisputeAmount,String emailFlag) throws RuntimeException;
	public void updateDisputeTabMoveSelectGroupOfInvoiceDetailRight(String[] proposalIds,int disputeId,int fromDisputeId,double totalDisputeAmount,String flagShortpaid) throws RuntimeException;
	public void updateDisputeTabRemoveSelectedForThisGroupOfInvoiceDetail(String[] proposalIds,int disputeId,double totalDisputeAmount) throws RuntimeException;
	public void updateDisputeTabGreateOneGroupOfInvoiceDetail(String[] proposalIds,int disputeReasonId,int disputeStatusId,int invoiceId,double totalDisputeAmount,String emailFlag) throws RuntimeException;
	public void updateDisputeTabGreateOneGroupOfInvoiceDetailRight(String[] proposalIds,int disputeReasonId,int disputeStatusId,int fromDisputeId,int invoiceId,double totalDisputeAmount) throws Exception;
	public String getDisputeTotalAmountByInvoiceId(int invoiceId) throws BPLException;
	public void deleteProposalFromProposalList(String proposalId,String invoiceItemId,String disputeReasonText)throws RuntimeException;
	public String getDisputeTotalNoByInvoiceId(SearchInvoiceVO searchInvoiceVO)throws BPLException;
	public String searchDisputesByInvoiceId(SearchInvoiceVO searchInvoiceVO)throws Exception;
	public String getProposalsTotalNoOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(SearchInvoiceVO searchInvoiceVO)throws BPLException ;
	public String searchProposalsOfInoiceDetailDisputeTabByInvoiceIdAndDisputeId(SearchInvoiceVO searchInvoiceVO)throws BPLException;
	public String saveExcelByProposalListInDisputeTabByInvoiceIdLeftToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath, List<String> titles) throws BPLException;
	public String saveExcelByProposalListInDisputeTabByInvoiceIdAndDiputeIdRightToExcel(SearchInvoiceVO searchInvoiceVO,String excelDirPath, List<String> titles) throws BPLException;
	public String findByInvoiceIdAndReturnJSON(SearchInvoiceVO searchInvoiceVO)throws Exception;
	public String getOriginatorListAndReturnJSON(SearchInvoiceVO searchInvoiceVO)throws Exception;
	// by hongtao 2011-09-19 
	public void updateDispute(String ticketNumber,String claimNumber, int disputeId,String disputeNotes,int originatorId,String flagRecurring,String flagShortpaid,String confidenceLevel,String emailCopyAddress,Double reservedAmount,String emailFlag) throws RuntimeException;
	public void updateDisputeTabGreateNewGroupForEachSelectedOfInvoiceDetail(String proposalData,Integer invoiceId,String emailFlag) throws Exception;
	public void deleteDisputeOfInvoiceDetail(String[] proposalIds,Integer disputeId,double totalDisputeAmount) throws RuntimeException;
	public String getProposalByProposalId(Long ProposalId)throws Exception;
	public void updateProposalByProposalId(SearchInvoiceVO searchInvoiceVO)throws Exception;
	public String getAnnexsTotalOfProposalByProposalId(SearchInvoiceVO svo) throws BPLException;
	public String searchAnnexsOfProposalByProposalId(SearchInvoiceVO svo) throws BPLException;
	public void deleteAnnexsById(int Id) throws BPLException;
	public void updateDisputeAmountByDiputeIdAndDisputeAmount(Integer disputeId,Double disputeAmont) throws RuntimeException;
	public String getAnnexsTotalOfDisputeByDisputeId(SearchInvoiceVO svo) throws BPLException;
	public String searchAnnexsOfDisputeByDisputeId(SearchInvoiceVO svo) throws BPLException;
	public void deleteAnnexsByIdOfDispute(int Id) throws RuntimeException;
	public String getProposalDataLeftByInvoiceId(SearchInvoiceVO searchInvoiceVO,String[] proposalIds)throws BPLException;
	public String getProposalDataRightByInvoiceIdAndDisputeId(SearchInvoiceVO searchInvoiceVO)throws BPLException;
}
