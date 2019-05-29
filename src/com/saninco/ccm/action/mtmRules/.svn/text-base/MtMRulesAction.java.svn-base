package com.saninco.ccm.action.mtmRules;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.mtmRules.IMtMRulesService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.mtmRules.SearchMtMRulesVO;

public class MtMRulesAction extends CcmActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 8135676753830252984L;
    
    private final Logger logger = Logger.getLogger(this.getClass());

    private IMtMRulesService mtmRulesService;
    private SearchMtMRulesVO searchMtMRulesVO;

    private List<String> titles;

    private List<MapEntryVO<String, String>> chargeTypeList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> rateStatusList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> keyFieldList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> subProductList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> summaryVendorNameList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> vendorNameList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> mtmNameList = new ArrayList<MapEntryVO<String, String>>();

    @Override
    public String exec() throws Exception {
        logger.info("Enter method exec.");

        // Charge Type
        this.chargeTypeList = mtmRulesService.listChargeType();

        // Key Field
        this.keyFieldList = mtmRulesService.listKeyField();

        // Sub Product
        this.subProductList = mtmRulesService.listSubProduct();
        
        // Summary Vendor Name
        this.summaryVendorNameList = mtmRulesService.listSummaryVendorName();
        
        // Vendor Name 主要的逻辑是增加了对 vendor_group_vendor 表的查找。
        this.vendorNameList = mtmRulesService.listVendorName();
        
        this.rateStatusList = new ArrayList<MapEntryVO<String, String>>();
        rateStatusList.add(new MapEntryVO<String, String>("Active","Active"));
        rateStatusList.add(new MapEntryVO<String, String>("Inactive","Inactive"));
        
        
        logger.info("Exit method exec.");
        return SUCCESS;
    }

    /**
     * 查询 MtM Rules 列表分页信息。
     */
    public String countMtMRulesViewListPageNo() throws Exception {

        logger.info("Enter method countMtMRulesViewListPageNo.");
        String result = null;
        try{
            result = mtmRulesService.countMtMRulesViewListPageNo(searchMtMRulesVO);
        }catch(Exception e){
            logger.error("countMtMRulesViewListPageNo error: ", e);
            result = "{error:\"countMtMRulesViewListPageNo error: "+e.getMessage()+"\"}";
        }
        this.writeOutputStream(result); 
        logger.info("Exit method countMtMRulesViewListPageNo.");
        return null;
    }

    /**
     * 查询 MtM Rules 列表信息。
     */
    public String listMtMRules() throws Exception {
        logger.info("Enter method listMtMRules.");
        String result = null;
        try{
            result = mtmRulesService.listMtMRules(searchMtMRulesVO);
        }catch(Exception e){
            logger.error("listMtMRules error: ", e);
            result = "{error:\"listMtMRules error: "+e.getMessage()+"\"}";
        }
        this.writeOutputStream(result); 
        logger.info("Exit method listMtMRules.");
        return null;
    }

    /**
     * MtM Rules 列内容下载
     */
    public String downloadMtMRulesToExcel() throws Exception {
        
        logger.info("Enter method downloadMtMRulesToExcel.");
        FileInputStream fis = null;
        try {
            String fileName = "MtM Validation Rules.xls";
            String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
            String excelPath = mtmRulesService.downloadMtMRulesToExcel(searchMtMRulesVO, excelDirPath, titles);
        
            fis = new FileInputStream(excelPath);
            writeFile(fileName,fis);
        }catch (Throwable e) {
            logger.error("downloadMtMRulesToExcel error: ", e);
        }
        logger.info("Exit method downloadMtMRulesToExcel.");
        
        return null;
    }
    
    

	public IMtMRulesService getMtmRulesService() {
		return mtmRulesService;
	}

	public void setMtmRulesService(IMtMRulesService mtmRulesService) {
		this.mtmRulesService = mtmRulesService;
	}

	public SearchMtMRulesVO getSearchMtMRulesVO() {
		return searchMtMRulesVO;
	}

	public void setSearchMtMRulesVO(SearchMtMRulesVO searchMtMRulesVO) {
		this.searchMtMRulesVO = searchMtMRulesVO;
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
	 * @return the chargeTypeList
	 */
	public List<MapEntryVO<String, String>> getChargeTypeList() {
		return chargeTypeList;
	}

	/**
	 * @param chargeTypeList the chargeTypeList to set
	 */
	public void setChargeTypeList(List<MapEntryVO<String, String>> chargeTypeList) {
		this.chargeTypeList = chargeTypeList;
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
	 * @return the summaryVendorNameList
	 */
	public synchronized List<MapEntryVO<String, String>> getSummaryVendorNameList() {
		return summaryVendorNameList;
	}

	/**
	 * @param summaryVendorNameList the summaryVendorNameList to set
	 */
	public synchronized void setSummaryVendorNameList(List<MapEntryVO<String, String>> summaryVendorNameList) {
		this.summaryVendorNameList = summaryVendorNameList;
	}

	/**
	 * @return the vendorNameList
	 */
	public synchronized List<MapEntryVO<String, String>> getVendorNameList() {
		return vendorNameList;
	}

	/**
	 * @param vendorNameList the vendorNameList to set
	 */
	public synchronized void setVendorNameList(List<MapEntryVO<String, String>> vendorNameList) {
		this.vendorNameList = vendorNameList;
	}

	/**
	 * @return the MtMNameList
	 */
	public synchronized List<MapEntryVO<String, String>> getMtMNameList() {
		return mtmNameList;
	}

	/**
	 * @param mtmNameList the mtmNameList to set
	 */
	public synchronized void setMtMNameList(List<MapEntryVO<String, String>> mtmNameList) {
		this.mtmNameList = mtmNameList;
	}

	public List<MapEntryVO<String, String>> getRateStatusList() {
		return rateStatusList;
	}

	public void setRateStatusList(List<MapEntryVO<String, String>> rateStatusList) {
		this.rateStatusList = rateStatusList;
	}
	
	
    
    
}