package com.saninco.ccm.action.tariffRules;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.tariffRules.ITariffRulesService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.tariffRules.SearchTariffRulesVO;

public class TariffRulesAction extends CcmActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 8135676753830252984L;
    
    private final Logger logger = Logger.getLogger(this.getClass());

    private ITariffRulesService tariffRulesService;
    private SearchTariffRulesVO searchTariffRulesVO;

    private List<String> titles;

    private List<MapEntryVO<String, String>> chargeTypeList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> keyFieldList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> subProductList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> summaryVendorNameList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> vendorNameList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> tariffNameList = new ArrayList<MapEntryVO<String, String>>();

    @Override
    public String exec() throws Exception {
        logger.info("Enter method exec.");

        // Charge Type
        this.chargeTypeList = tariffRulesService.listChargeType();

        // Key Field
        this.keyFieldList = tariffRulesService.listKeyField();

        // Sub Product
        this.subProductList = tariffRulesService.listSubProduct();
        
        // Summary Vendor Name
        this.summaryVendorNameList = tariffRulesService.listSummaryVendorName();
        
        // Vendor Name 主要的逻辑是增加了对 vendor_group_vendor 表的查找。
        this.vendorNameList = tariffRulesService.listVendorName();
        
        // Tariff Name
        this.tariffNameList = tariffRulesService.listTariffName();
        
        
        logger.info("Exit method exec.");
        return SUCCESS;
    }

    /**
     * 查询 Tariff Rules 列表分页信息。
     */
    public String countTariffRulesViewListPageNo() throws Exception {

        logger.info("Enter method countTariffRulesViewListPageNo.");
        String result = null;
        try{
            result = tariffRulesService.countTariffRulesViewListPageNo(searchTariffRulesVO);
        }catch(Exception e){
            logger.error("countTariffRulesViewListPageNo error: ", e);
            result = "{error:\"countTariffRulesViewListPageNo error: "+e.getMessage()+"\"}";
        }
        this.writeOutputStream(result); 
        logger.info("Exit method countTariffRulesViewListPageNo.");
        return null;
    }

    /**
     * 查询 Tariff Rules 列表信息。
     */
    public String listTariffRules() throws Exception {
        logger.info("Enter method listTariffRules.");
        String result = null;
        try{
            result = tariffRulesService.listTariffRules(searchTariffRulesVO);
        }catch(Exception e){
            logger.error("listTariffRules error: ", e);
            result = "{error:\"listTariffRules error: "+e.getMessage()+"\"}";
        }
        this.writeOutputStream(result); 
        logger.info("Exit method listTariffRules.");
        return null;
    }

    /**
     * Tariff Rules 列内容下载
     */
    public String downloadTariffRulesToExcel() throws Exception {
        
        logger.info("Enter method downloadTariffRulesToExcel.");
        FileInputStream fis = null;
        try {
            String fileName = "Tariff Validation Rules.xls";
            String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
            String excelPath = tariffRulesService.downloadTariffRulesToExcel(searchTariffRulesVO, excelDirPath, titles);
        
            fis = new FileInputStream(excelPath);
            writeFile(fileName,fis);
        }catch (Throwable e) {
            logger.error("downloadTariffRulesToExcel error: ", e);
        }
        logger.info("Exit method downloadTariffRulesToExcel.");
        
        return null;
    }
    
    

	/**
	 * @return the searchTariffRulesVO
	 */
	public  SearchTariffRulesVO getSearchTariffRulesVO() {
		return searchTariffRulesVO;
	}

	/**
	 * @param searchTariffRulesVO the searchTariffRulesVO to set
	 */
	public  void setSearchTariffRulesVO(SearchTariffRulesVO searchTariffRulesVO) {
		this.searchTariffRulesVO = searchTariffRulesVO;
	}

	/**
	 * @return the tariffRulesService
	 */
	public ITariffRulesService getTariffRulesService() {
		return tariffRulesService;
	}

	/**
	 * @param tariffRulesService the tariffRulesService to set
	 */
	public void setTariffRulesService(ITariffRulesService tariffRulesService) {
		this.tariffRulesService = tariffRulesService;
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
	 * @return the tariffNameList
	 */
	public synchronized List<MapEntryVO<String, String>> getTariffNameList() {
		return tariffNameList;
	}

	/**
	 * @param tariffNameList the tariffNameList to set
	 */
	public synchronized void setTariffNameList(List<MapEntryVO<String, String>> tariffNameList) {
		this.tariffNameList = tariffNameList;
	}
	
	
    
    
}