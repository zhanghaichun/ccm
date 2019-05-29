package com.saninco.ccm.dao.validation;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.vo.SearchInvoiceVO;

public interface IMRCVarianceDao {
	public long countMRCVarianceNo(SearchInvoiceVO searchInvoiceVO);

	public List<String> listMRCVarianceListings(SearchInvoiceVO searchInvoiceVO);

	public List<String> listVarianceReasons();

	public List<Map<String, String>> getMasterInventoryDetails(SearchInvoiceVO searchInvoiceVO);

	public long countVarianceItemsNo(SearchInvoiceVO searchInvoiceVO);

	public List<String> listVarianceItemsListings(SearchInvoiceVO searchInvoiceVO);

	public long countTwoMonthsVarianceProposalItemsNo(SearchInvoiceVO searchInvoiceVO);

	public List<String> listTwoMonthsVarianceProposalItemsListings(SearchInvoiceVO searchInvoiceVO);
}
