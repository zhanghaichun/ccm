package com.saninco.ccm.action.rateSearch;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.rateSearch.IRateSearchService;
import com.saninco.ccm.util.ExcelFileUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.rateSearch.SearchRateSearchVO;

public class RateSearchAction extends CcmActionSupport {

    /**
     * 
     */
    private static final long serialVersionUID = 8135676753830252984L;

    private IRateSearchService rateSearchService;
    private SearchRateSearchVO searchRateSearchVO;

    private List<String> titles;

    private List<MapEntryVO<String, String>> subProductList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> summaryVendorNameList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> vendorNameList = new ArrayList<MapEntryVO<String, String>>();
    private List<MapEntryVO<String, String>> contractOrTariffNameList = new ArrayList<MapEntryVO<String, String>>(); 
    private List<String> uploadsFileName;
    private List<File> uploads;
    private Map<String,Object> returnMap;
    private final Logger logger = Logger.getLogger(this.getClass());
    private String errorFileName;
    private String referenceType;
    @Override
    public String exec() throws Exception {
        logger.info("Enter method exec.");

        // Sub Product
        this.subProductList = rateSearchService.listSubProduct();
        
        // Summary Vendor Name
        this.summaryVendorNameList = rateSearchService.listSummaryVendorName();
        
        // Vendor Name 主要的逻辑是增加了对 vendor_group_vendor 表的查找。
        this.vendorNameList = rateSearchService.listVendorName();
        
        // Contract #/Tariff Reference
        this.contractOrTariffNameList = rateSearchService.listContractOrTariffName(null);

        
        
        logger.info("Exit method exec.");
        return SUCCESS;
    }
    
    /**
     * Filter rule's references.
     * @return
     * @throws Exception
     */
    public String filterRulesReference() throws Exception {
    	 	
    	// Contract #/Tariff Reference
    	this.contractOrTariffNameList = rateSearchService.
        									listContractOrTariffName( searchRateSearchVO.getReferenceType() );
    	
    	
        // 将 rule reference 转换成 json 格式数据
        // 返回到前台。
        JSONArray references = JSONArray.fromObject( this.contractOrTariffNameList );
        this.writeOutputStream(references.toString());
        
    	return null;
    	
    }


    /**
     * 查询 Rate Search 列表分页信息。
     */
    public String countRateSearchPageNo() throws Exception {

        logger.info("Enter method countRateSearchPageNo.");
        String result = null;
        try{
            result = rateSearchService.countRateSearchPageNo(searchRateSearchVO);
        }catch(Exception e){
            logger.error("countRateSearchPageNo error: ", e);
            result = "{error:\"countRateSearchPageNo error: "+e.getMessage()+"\"}";
        }
        this.writeOutputStream(result); 
        logger.info("Exit method countRateSearchPageNo.");
        return null;
    }

    /**
     * Contract Summary List counts。
     */
    public String countContractSummaryPageNo() throws Exception {

        logger.info("Enter method countContractSummaryPageNo.");
        String result = null;
        try{
            result = rateSearchService.countContractSummaryPageNo(searchRateSearchVO);
        }catch(Exception e){
            logger.error("countContractSummaryPageNo error: ", e);
            result = "{error:\"countContractSummaryPageNo error: "+e.getMessage()+"\"}";
        }
        this.writeOutputStream(result); 
        logger.info("Exit method countContractSummaryPageNo.");
        return null;
    }


    public String listRateSearch() throws Exception {
        logger.info("Enter method listRateSearch.");
        String result = null;
        try{
            result = rateSearchService.listRateSearch(searchRateSearchVO);
        }catch(Exception e){
            logger.error("listRateSearch error: ", e);
            result = "{error:\"listRateSearch error: "+e.getMessage()+"\"}";
        }
        this.writeOutputStream(result); 
        logger.info("Exit method listRateSearch.");
        return null;
    }

    /*Download Rate Search*/
    public String downloadRateSearch() throws Exception {
        
        logger.info("Enter method downloadRateSearch.");
        FileInputStream fis = null;
        try {
            String fileName = "Rate Search.xls";
            String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
            String excelPath = rateSearchService.downloadRateSearch(searchRateSearchVO, excelDirPath, titles);
        
            fis = new FileInputStream(excelPath);
            writeFile(fileName,fis);
        }catch (Throwable e) {
            logger.error("downloadRateSearch error: ", e);
        }
        logger.info("Exit method downloadRateSearch.");
        
        return null;
    }


    /*Download Contract Summary*/
    public String downloadContractSummary() throws Exception {
        
        logger.info("Enter method downloadContractSummary.");
        FileInputStream fis = null;
        try {
            String fileName = "Contract Summary.xls";
            String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
            String excelPath = rateSearchService.downloadContractSummary(searchRateSearchVO, excelDirPath, titles);
        
            fis = new FileInputStream(excelPath);
            writeFile(fileName,fis);
        }catch (Throwable e) {
            logger.error("downloadContractSummary error: ", e);
        }
        logger.info("Exit method downloadContractSummary.");
        
        return null;
    }

    /* Search Contract Summary List */
    public String listContractSummary() throws Exception {
        logger.info("Enter method listContractSummary.");
        String result = null;
        try{
            result = rateSearchService.listContractSummary(searchRateSearchVO);
        }catch(Exception e){
            logger.error("listContractSummary error: ", e);
            result = "{error:\"listContractSummary error: "+e.getMessage()+"\"}";
        }
        this.writeOutputStream(result); 
        logger.info("Exit method listContractSummary.");
        return null;
    }
    
    public String uploadRate () throws Exception {
		logger.info("Enter method uploadMasterInventory.");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String previewFileName = df.format(new Date())+"_"+uploadsFileName.get(uploadsFileName.size()-1);
		String errorExcelDirPath = request.getSession().getServletContext().getRealPath("/")+ "previews\\" + previewFileName;
		File file = uploads.get(uploads.size()-1);
		returnMap = new HashMap<String,Object>();
		if(file.length() > 5242880){
			returnMap.put("maxSize", "Y");
		}else{
			returnMap.put("maxSize", "N");
			Map<String,String> map = rateSearchService.uploadRateInventory(uploads.get(uploads.size()-1),uploadsFileName.get(uploadsFileName.size()-1),errorExcelDirPath);
			if(map.get("isSuccess") == null || "".equals(map.get("isSuccess"))){
				returnMap.put("validationFailure", "Y");
			}else{
				returnMap.put("validationFailure", "N");
			}
			returnMap.put("isSuccess", map.get("isSuccess"));
		}
		returnMap.put("errorFilePath", previewFileName);
		logger.info("Exit method uploadMasterInventory.");
		return SUCCESS;
	}

    public String downloadRateTemplate() throws Exception {
        
        logger.info("Enter method downloadRateTemplate.");
        FileInputStream fis = null;
        try {
            String fileName = "Rate Template.xlsx";
            String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
            String excelPath = rateSearchService.downloadRateTemplate(searchRateSearchVO, excelDirPath);
            if(searchRateSearchVO.getReferenceType().equals("Tariff")){
            	fileName = "Tariff Rate Template.xlsx";
	   		 }else if(searchRateSearchVO.getReferenceType().equals("MtM")){
	   			fileName = "Month to Month Rate Template.xlsx";
	   		 }else if(searchRateSearchVO.getReferenceType().equals("Contract")){
	   			fileName = "Contract Rate Template.xlsx";
	   		 }
            fis = new FileInputStream(excelPath);
            writeFile(fileName,fis);
        }catch (Throwable e) {
            logger.error("downloadRateTemplate error: ", e);
        }
        logger.info("Exit method downloadRateTemplate.");
        
        return null;
    }
    
    public String downloadRateBlankTemplate() throws Exception {
    	
    	logger.info("Enter method downloadRateBlankTemplate.");
    	FileInputStream fis = null;
    	try {
    		String fileName = "Rate Template.xlsx";
    		String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
    		String excelPath = rateSearchService.downloadRateBlankTemplate(referenceType, excelDirPath);
    		if(referenceType.equals("Tariff")){
    			fileName = "Tariff Rate Template.xlsx";
    		}else if(referenceType.equals("MtM")){
    			fileName = "Month to Month Rate Template.xlsx";
    		}else if(referenceType.equals("Contract")){
    			fileName = "Contract Rate Template.xlsx";
    		}
    		fis = new FileInputStream(excelPath);
    		writeFile(fileName,fis);
    	}catch (Throwable e) {
    		logger.error("downloadRateBlankTemplate error: ", e);
    	}
    	logger.info("Exit method downloadRateBlankTemplate.");
    	
    	return null;
    }

    public String downLoadRateErrorFile()throws Exception{
		logger.info("Exit method downLoadRateErrorFile.");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(request.getSession().getServletContext().getRealPath("/")+ "previews\\" + errorFileName);
			String fileName = "Rate File Import Error(s).";
			if(errorFileName.endsWith(ExcelFileUtil.EXTENSION_XLS)){
				fileName+=ExcelFileUtil.EXTENSION_XLS;
			}else{
				fileName+=ExcelFileUtil.EXTENSION_XLSX;
			}
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("downLoadRateErrorFile error: ", e);
		}
		logger.info("Exit method downLoadRateErrorFile.");
		return null;
	}
    
	/**
	 * @return the rateSearchService
	 */
	public IRateSearchService getRateSearchService() {
		return rateSearchService;
	}

	/**
	 * @param rateSearchService the rateSearchService to set
	 */
	public void setRateSearchService(IRateSearchService rateSearchService) {
		this.rateSearchService = rateSearchService;
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
	 * @return the vendorNameList
	 */
	public List<MapEntryVO<String, String>> getVendorNameList() {
		return vendorNameList;
	}

	/**
	 * @param vendorNameList the vendorNameList to set
	 */
	public void setVendorNameList(List<MapEntryVO<String, String>> vendorNameList) {
		this.vendorNameList = vendorNameList;
	}

	/**
	 * @return the contractOrTariffNameList
	 */
	public List<MapEntryVO<String, String>> getContractOrTariffNameList() {
		return contractOrTariffNameList;
	}

	/**
	 * @param contractOrTariffNameList the contractOrTariffNameList to set
	 */
	public void setContractOrTariffNameList(List<MapEntryVO<String, String>> contractOrTariffNameList) {
		this.contractOrTariffNameList = contractOrTariffNameList;
	}


	/**
	 * @return the searchRateSearchVO
	 */
	public SearchRateSearchVO getSearchRateSearchVO() {
		return searchRateSearchVO;
	}


	/**
	 * @param searchRateSearchVO the searchRateSearchVO to set
	 */
	public void setSearchRateSearchVO(SearchRateSearchVO searchRateSearchVO) {
		this.searchRateSearchVO = searchRateSearchVO;
	}

	public List<String> getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(List<String> uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public List<File> getUploads() {
		return uploads;
	}

	public void setUploads(List<File> uploads) {
		this.uploads = uploads;
	}

	public Map<String, Object> getReturnMap() {
		return returnMap;
	}

	public void setReturnMap(Map<String, Object> returnMap) {
		this.returnMap = returnMap;
	}

	public String getErrorFileName() {
		return errorFileName;
	}
	
	public void setErrorFileName(String errorFileName) {
		this.errorFileName = errorFileName;
	}

	public String getReferenceType() {
		return referenceType;
	}

	public void setReferenceType(String referenceType) {
		this.referenceType = referenceType;
	}
    
}