package com.saninco.ccm.action.contractRules;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.contractRules.IContractRulesService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.contractRules.SearchContractRulesVO;

public class ContractRulesAction extends CcmActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 8135676753830252984L;
    
    private final Logger logger = Logger.getLogger(this.getClass());

    private IContractRulesService contractRulesService;
    private SearchContractRulesVO searchContractRulesVO;

    private List<String> titles;

    private List<MapEntryVO<String, String>> summaryVendorNameList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> keyFieldList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> subProductList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> contractNameList = new ArrayList<MapEntryVO<String, String>>();

    @Override
    public String exec() throws Exception {
        logger.info("Enter method exec.");

        // Summary Vendor Name
        this.summaryVendorNameList = contractRulesService.listSummaryVendorName();
        

        // Key Field
        this.keyFieldList = contractRulesService.listKeyField();

        // Sub Product
        this.subProductList = contractRulesService.listSubProduct();

        // Contract Name
        this.contractNameList = contractRulesService.listContractName();
        
        logger.info("Exit method exec.");
        return SUCCESS;
    }

    /**
     * 查询 Contract Rules 列表分页信息。
     */
    public String countContractRulesViewListPageNo() throws Exception {

        logger.info("Enter method countContractRulesViewListPageNo.");
        String result = null;
        try{
            result = contractRulesService.countContractRulesViewListPageNo(searchContractRulesVO);
        }catch(Exception e){
            logger.error("countContractRulesViewListPageNo error: ", e);
            result = "{error:\"countContractRulesViewListPageNo error: "+e.getMessage()+"\"}";
        }
        this.writeOutputStream(result); 
        logger.info("Exit method countContractRulesViewListPageNo.");
        return null;
    }

    /**
     * 查询 Contract Rules 列表信息。
     */
    public String listContractRules() throws Exception {
        logger.info("Enter method listContractRules.");
        String result = null;
        try{
            result = contractRulesService.listContractRules(searchContractRulesVO);
        }catch(Exception e){
            logger.error("listContractRules error: ", e);
            result = "{error:\"listContractRules error: "+e.getMessage()+"\"}";
        }
        this.writeOutputStream(result); 
        logger.info("Exit method listContractRules.");
        return null;
    }

    /**
     * Contract Rules 列内容下载
     */
    public String downloadContractRulesToExcel() throws Exception {
        
        logger.info("Enter method downloadContractRulesToExcel.");
        FileInputStream fis = null;
        try {
            String fileName = "Contract Validation Rules.xls";
            String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
            String excelPath = contractRulesService.downloadContractRulesToExcel(searchContractRulesVO, excelDirPath, titles);
        
            fis = new FileInputStream(excelPath);
            writeFile(fileName,fis);
        }catch (Throwable e) {
            logger.error("downloadContractRulesToExcel error: ", e);
        }
        logger.info("Exit method downloadContractRulesToExcel.");
        
        return null;
    }



	/**
	 * @return the searchContractRulesVO
	 */
	public SearchContractRulesVO getSearchContractRulesVO() {
		return searchContractRulesVO;
	}

	/**
	 * @param searchContractRulesVO the searchContractRulesVO to set
	 */
	public void setSearchContractRulesVO(SearchContractRulesVO searchContractRulesVO) {
		this.searchContractRulesVO = searchContractRulesVO;
	}

	/**
	 * @return the contractRulesService
	 */
	public IContractRulesService getContractRulesService() {
		return contractRulesService;
	}



	/**
	 * @param contractRulesService the contractRulesService to set
	 */
	public void setContractRulesService(IContractRulesService contractRulesService) {
		this.contractRulesService = contractRulesService;
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
	 * @return the summaryVendorNameList
	 */
	public List<MapEntryVO<String, String>> getSummaryVendorNameList() {
		return summaryVendorNameList;
	}

	/**
	 * @param summaryVendorNameList the summaryVendorNameList to set
	 */
	public void setSummaryVendorNameList(List<MapEntryVO<String, String>> summaryVendorNameList) {
		this.summaryVendorNameList = summaryVendorNameList;
	}

	/**
	 * @return the keyFieldList
	 */
	public List<MapEntryVO<String, String>> getKeyFieldList() {
		return keyFieldList;
	}

	/**
	 * @param keyFieldList the keyFieldList to set
	 */
	public void setKeyFieldList(List<MapEntryVO<String, String>> keyFieldList) {
		this.keyFieldList = keyFieldList;
	}

	/**
	 * @return the subProductList
	 */
	public List<MapEntryVO<String, String>> getSubProductList() {
		return subProductList;
	}

	/**
	 * @param subProductList the subProductList to set
	 */
	public void setSubProductList(List<MapEntryVO<String, String>> subProductList) {
		this.subProductList = subProductList;
	}

	/**
	 * @return the contractNameList
	 */
	public List<MapEntryVO<String, String>> getContractNameList() {
		return contractNameList;
	}

	/**
	 * @param contractNameList the contractNameList to set
	 */
	public void setContractNameList(List<MapEntryVO<String, String>> contractNameList) {
		this.contractNameList = contractNameList;
	}
    
    
}