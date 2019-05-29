package com.saninco.ccm.action.contract;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.contract.IContractService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.contract.SearchContractVO;

public class ContractAction extends CcmActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8135676753830252984L;
	
	private final Logger logger = Logger.getLogger(this.getClass());

	private IContractService contractService;
	private SearchContractVO searchContractVO;

	private List<String> titles;

	private List<MapEntryVO<String, String>> carrierEntityNameList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> carrierTypeList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> agreementTypeList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> productsServicesList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> termCombinedList = new ArrayList<MapEntryVO<String, String>>();
	
	

	/**
	 * @return the contractService
	 */
	public IContractService getContractService() {
		return contractService;
	}



	/**
	 * @param contractService the contractService to set
	 */
	public void setContractService(IContractService contractService) {
		this.contractService = contractService;
	}
	
	

	/**
	 * @return the searchContractVO
	 */
	public SearchContractVO getSearchContractVO() {
		return searchContractVO;
	}



	/**
	 * @param searchContractVO the searchContractVO to set
	 */
	public void setSearchContractVO(SearchContractVO searchContractVO) {
		this.searchContractVO = searchContractVO;
	}

	

	/**
	 * @return the titles
	 */
	public List<String> getTitles() {
		return titles;
	}



	/**
	 * @param titles the titles to set
	 */
	public void setTitles(List<String> titles) {
		this.titles = titles;
	}



	/**
	 * @return the carrierEntityNameList
	 */
	public List<MapEntryVO<String, String>> getCarrierEntityNameList() {
		return carrierEntityNameList;
	}



	/**
	 * @param carrierEntityNameList the carrierEntityNameList to set
	 */
	public void setCarrierEntityNameList(
			List<MapEntryVO<String, String>> carrierEntityNameList) {
		this.carrierEntityNameList = carrierEntityNameList;
	}



	/**
	 * @return the carrierTypeList
	 */
	public List<MapEntryVO<String, String>> getCarrierTypeList() {
		return carrierTypeList;
	}



	/**
	 * @param carrierTypeList the carrierTypeList to set
	 */
	public void setCarrierTypeList(List<MapEntryVO<String, String>> carrierTypeList) {
		this.carrierTypeList = carrierTypeList;
	}



	/**
	 * @return the agreementTypeList
	 */
	public List<MapEntryVO<String, String>> getAgreementTypeList() {
		return agreementTypeList;
	}



	/**
	 * @param agreementTypeList the agreementTypeList to set
	 */
	public void setAgreementTypeList(
			List<MapEntryVO<String, String>> agreementTypeList) {
		this.agreementTypeList = agreementTypeList;
	}



	/**
	 * @return the productsServicesList
	 */
	public List<MapEntryVO<String, String>> getProductsServicesList() {
		return productsServicesList;
	}



	/**
	 * @param productsServicesList the productsServicesList to set
	 */
	public void setProductsServicesList(
			List<MapEntryVO<String, String>> productsServicesList) {
		this.productsServicesList = productsServicesList;
	}



	/**
	 * @return the termCombinedList
	 */
	public List<MapEntryVO<String, String>> getTermCombinedList() {
		return termCombinedList;
	}



	/**
	 * @param termCombinedList the termCombinedList to set
	 */
	public void setTermCombinedList(
			List<MapEntryVO<String, String>> termCombinedList) {
		this.termCombinedList = termCombinedList;
	}



	/**
	 * 返回jsp页面和页面中所需的数据
	 * 1, Carrier Entity Name list
	 * 2, Carrier Type list
	 * 3, Agreement Type list
	 * 4, Products/Services list
	 * 5, Term Combined list
	 * 以上五条都是搜索条件中下拉列表中的数据。
	 * @return [description]
	 * @throws Exception     [description]
	 */
	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");

		// Carrier Entity Name
		this.carrierEntityNameList = contractService.getCarrierEntityNameListMap();

		// Carrier Type
		this.carrierTypeList = contractService.getCarrierTypeListMap();

		// Agreement Type
		this.agreementTypeList = contractService.getAgreementTypeListMap();

		// Products/Services
		this.productsServicesList = contractService.getProductsServicesListMap();

		// Term Combined
		this.termCombinedList = contractService.getTermCombinedListMap();
		
		logger.info("Exit method exec.");
		return SUCCESS;
	}

	/**
	 * 查询 Contract 列表分页信息。
	 */
	public String getContractViewListPageNo() throws Exception {
		logger.info("Enter method getContractViewListPageNo.");
		String result = null;
		try{
			result = contractService.getContractViewListPageNo(searchContractVO);
		}catch(Exception e){
			logger.error("getContractViewListPageNo error: ", e);
			result = "{error:\"getContractViewListPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getContractViewListPageNo.");
		return null;
	}

	/**
	 * 查询 Contract 列表信息。
	 */
	public String searchContractList() throws Exception {
		logger.info("Enter method searchContractList.");
		String result = null;
		try{
			result = contractService.searchContractList(searchContractVO);
		}catch(Exception e){
			logger.error("searchContractList error: ", e);
			result = "{error:\"searchContractList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchContractList.");
		return null;
	}
	
	/**
	 * 查询 Termination Penalty 列表分页信息
	 */
	public String getTerminationPenaltyListNo() throws Exception {
		logger.info("Enter method getTerminationPenaltyListNo.");
		String result = null;
		try{
			result = contractService.getTerminationPenaltyListNo(searchContractVO);
		}catch(Exception e){
			logger.error("getTerminationPenaltyListNo error: ", e);
			result = "{error:\"getTerminationPenaltyListNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getTerminationPenaltyListNo.");
		return null;
	}
	
	/**
	 * 查询 Termination Penalty 列表信息
	 */
	public String searchTerminationPenaltyList() throws Exception {
		logger.info("Enter method searchTerminationPenaltyList.");
		String result = null;
		try{
			result = contractService.searchTerminationPenaltyList(searchContractVO);
		}catch(Exception e){
			logger.error("searchTerminationPenaltyList error: ", e);
			result = "{error:\"searchTerminationPenaltyList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchTerminationPenaltyList.");
		return null;
	}

	/**
	 * Contract 列表下载
	 */
	public String downloadContractToExcel() throws Exception {
		
		logger.info("Enter method downloadContractToExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "Contract.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = contractService.downloadContractToExcel(searchContractVO, excelDirPath, titles);
		
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("downloadContractToExcel error: ", e);
		}
		logger.info("Exit method downloadContractToExcel.");
		
		return null;
	}
}