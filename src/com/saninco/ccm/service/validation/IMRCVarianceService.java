package com.saninco.ccm.service.validation;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInvoiceVO;


public interface IMRCVarianceService {

	public String countMRCVarianceNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public String listMRCVarianceListings(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public List<MapEntryVO<String, String>> listVarianceReasons() throws BPLException;

	public List<Map<String, String>> getMasterInventoryDetails(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public String countVarianceItemsNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public String listVarianceItemsListings(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public String countTwoMonthsVarianceProposalItemsNo(SearchInvoiceVO searchInvoiceVO) throws BPLException;

	public String listTwoMonthsVarianceProposalItemsListings(SearchInvoiceVO searchInvoiceVO) throws BPLException;
}
