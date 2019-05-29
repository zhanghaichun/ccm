package com.saninco.ccm.action.pricelist;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.pricelist.IPricelistService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.pricelist.SearchPricelistVO;

public class PricelistAction extends CcmActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8135676753830252984L;
	
	private final Logger logger = Logger.getLogger(this.getClass());

	private IPricelistService pricelistService;
	private SearchPricelistVO searchPricelistVO;

	private List<String> titles;

	private List<MapEntryVO<String, String>> serviceTypeList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> bandList = new ArrayList<MapEntryVO<String, String>>();
	
	

	/**
	 * @return the pricelistService
	 */
	public IPricelistService getPricelistService() {
		return pricelistService;
	}



	/**
	 * @param pricelistService the pricelistService to set
	 */
	public void setPricelistService(IPricelistService pricelistService) {
		this.pricelistService = pricelistService;
	}



	/**
	 * @return the searchPricelistVO
	 */
	public SearchPricelistVO getSearchPricelistVO() {
		return searchPricelistVO;
	}



	/**
	 * @param searchPricelistVO the searchPricelistVO to set
	 */
	public void setSearchPricelistVO(SearchPricelistVO searchPricelistVO) {
		this.searchPricelistVO = searchPricelistVO;
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
	 * @return the serviceTypeList
	 */
	public List<MapEntryVO<String, String>> getServiceTypeList() {
		return serviceTypeList;
	}



	/**
	 * @param serviceTypeList the serviceTypeList to set
	 */
	public void setServiceTypeList(List<MapEntryVO<String, String>> serviceTypeList) {
		this.serviceTypeList = serviceTypeList;
	}



	/**
	 * @return the bandList
	 */
	public List<MapEntryVO<String, String>> getBandList() {
		return bandList;
	}



	/**
	 * @param bandList the bandList to set
	 */
	public void setBandList(List<MapEntryVO<String, String>> bandList) {
		this.bandList = bandList;
	}



	@Override
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		
		// Service Type
		this.serviceTypeList = pricelistService.getServiceTypeListMap();

		// Band
		this.bandList = pricelistService.getBandListMap();
		
		logger.info("Exit method exec.");
		return SUCCESS;
	}

	/**
	 * 查询 Pricelist 列表分页信息。
	 */
	public String getPricelistViewListPageNo() throws Exception {
		logger.info("Enter method getPricelistViewListPageNo.");
		String result = null;
		try{
			result = pricelistService.getPricelistViewListPageNo(searchPricelistVO);
		}catch(Exception e){
			logger.error("getPricelistViewListPageNo error: ", e);
			result = "{error:\"getPricelistViewListPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getPricelistViewListPageNo.");
		return null;
	}
	
	/**
	 * 查询 Pricelist 列表数据信息。
	 */
	public String searchPricelistList() throws Exception {
		logger.info("Enter method searchPricelistList.");
		String result = null;
		try{
			result = pricelistService.searchPricelistList(searchPricelistVO);
		}catch(Exception e){
			logger.error("searchPricelistList error: ", e);
			result = "{error:\"searchPricelistList error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchPricelistList.");
		return null;
	}

	/**
	 * 下载 pricelist 列表
	 */
	public String downloadPricelistToExcel() throws Exception {
		
		logger.info("Enter method downloadPricelistToExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "Pricelist.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = pricelistService.downloadPricelistToExcel(searchPricelistVO, excelDirPath, titles);
		
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("downloadPricelistToExcel error: ", e);
		}
		logger.info("Exit method downloadPricelistToExcel.");
		
		return null;
	}
}