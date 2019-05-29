package com.saninco.ccm.service.rateSearch;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.rateSearch.IRateSearchDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.ExcelBatchDaoDelegate;
import com.saninco.ccm.util.ExcelBatchHelper;
import com.saninco.ccm.util.JExcelFileUtil;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.rateSearch.SearchRateSearchVO;

public class RateSearchServiceImpl implements IRateSearchService {

    private final Logger logger = Logger.getLogger(this.getClass());

    private IRateSearchDao rateSearchDao;
    private ExcelBatchDaoDelegate rateExcelTariffBatchDao;
    private ExcelBatchDaoDelegate rateExcelMtMBatchDao;
    private ExcelBatchDaoDelegate rateExcelContractBatchDao;
    
    private ExcelBatchHelper excelBatchHelper;
    
    public RateSearchServiceImpl() { }
    
    /**
     * Sub Product。
     */
    public List<MapEntryVO<String, String>> listSubProduct() throws BPLException {
        logger.info("Enter method listSubProduct.");
        List<MapEntryVO<String, String>> subProductMapList = null;
        List<String> subProductList = null;
        
        try {
            
            subProductList = rateSearchDao.listSubProduct();
            subProductMapList = new ArrayList<MapEntryVO<String, String>>(subProductList.size());
        } catch(Exception e) {
            
            logger.error("",e);
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        for(String listItem : subProductList){
            
            if(! ( listItem.equals("ALL") ) ){
                MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
                subProductMapList.add(m);
            }   
        }
        
        logger.info("Exit method listSubProduct.");
        return subProductMapList;
    }
    
    /**
     * 获取页面中 Summary Vendor Name 下拉列表中的内容。
     */
    public List<MapEntryVO<String, String>> listSummaryVendorName() throws BPLException {
        logger.info("Enter method listSummaryVendorName.");
        List<MapEntryVO<String, String>> summaryVendorNameMapList = null;
        List<String> summaryVendorNameList = null;
        
        try {
            
            summaryVendorNameList = rateSearchDao.listSummaryVendorName();
            summaryVendorNameMapList = new ArrayList<MapEntryVO<String, String>>(summaryVendorNameList.size());
        } catch(Exception e) {
            
            logger.error("",e);
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        for(String listItem : summaryVendorNameList){
            
            if(! ( listItem.equals("ALL") ) ){
                MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
                summaryVendorNameMapList.add(m);
            }   
        }
        
        logger.info("Exit method listSummaryVendorName.");
        return summaryVendorNameMapList;
    }
    
    /**
     * 获取页面中 Vendor Name 下拉列表中的内容。
     */
    public List<MapEntryVO<String, String>> listVendorName() throws BPLException {
        logger.info("Enter method listVendorName.");
        List<MapEntryVO<String, String>> vendorNameMapList = null;
        List<String> vendorNameList = null;
        
        try {
            
            vendorNameList = rateSearchDao.listVendorName();
            vendorNameMapList = new ArrayList<MapEntryVO<String, String>>(vendorNameList.size());
        } catch(Exception e) {
            
            logger.error("",e);
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        for(String listItem : vendorNameList){
//            Object[] v = (Object[])listItem;
            if(! ( listItem.toString().equals("ALL") ) ){
//                MapEntryVO<String, String> m = new MapEntryVO<String, String>( ( (Integer)v[0] ).toString(), (String)v[1] );
                MapEntryVO<String, String> m = new MapEntryVO<String, String>( listItem, listItem );
                vendorNameMapList.add(m);
            }   
        }
        
        logger.info("Exit method listVendorName.");
        return vendorNameMapList;
    }
    
    /**
     * Contract # /Tariff Reference。
     */
    public List<MapEntryVO<String, String>> listContractOrTariffName(String referenceType) throws BPLException {
        logger.info("Enter method listContractOrTariffName.");
        List<MapEntryVO<String, String>> contractOrTariffNameMapList = null;
        List<String> contractOrTariffNameList = null;
        
        try {
            
            contractOrTariffNameList = rateSearchDao.listContractOrTariffName(referenceType);
            contractOrTariffNameMapList = new ArrayList<MapEntryVO<String, String>>(contractOrTariffNameList.size());
        } catch(Exception e) {
            
            logger.error("",e);
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        for(String listItem : contractOrTariffNameList){
            
            if(! ( listItem.equals("ALL") ) ){
                MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
                contractOrTariffNameMapList.add(m);
            }   
        }
        
        logger.info("Exit method listContractOrTariffName.");
        return contractOrTariffNameMapList;
    }

    /**
     * 获取 rate search 列表分页显示信息。
     */
    public String countRateSearchPageNo(SearchRateSearchVO searchRateSearchVO) throws BPLException {

        logger.info(CcmFormat.formatEnterLog("Rate Search page number", searchRateSearchVO));
        StringBuffer sb = new StringBuffer();
        long count = 0;
        try {

            // rate search 记录的条数。
            count = rateSearchDao.countRateSearchPageNo(searchRateSearchVO);
        } catch (RuntimeException e) {

            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }

        sb.append( searchRateSearchVO.getTotalPageNoJson(count) );
        
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
    }

    public String countContractSummaryPageNo(SearchRateSearchVO searchRateSearchVO) throws BPLException {

        StringBuffer sb = new StringBuffer();
        long count = 0;
        try {

            // rate search 记录的条数。
            count = rateSearchDao.countContractSummaryPageNo(searchRateSearchVO);
        } catch (RuntimeException e) {

            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }

        sb.append( searchRateSearchVO.getTotalPageNoJson(count) );
        
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
    }

    public String listRateSearch(SearchRateSearchVO searchRateSearchVO) throws BPLException {

        StringBuffer sb = new StringBuffer();
        List<String> rateSearchList = null;
        try {
            
            rateSearchList = rateSearchDao.listRateSearch(searchRateSearchVO);

            sb.append(searchRateSearchVO.getListJsonCompatible(rateSearchList));
        } catch (Exception e) {
            
            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
    }

     public String downloadRateSearch(SearchRateSearchVO searchRateSearchVO,String excelDirPath, List<String> titles) throws BPLException {

        logger.info(CcmFormat.formatEnterLog(""));
        try {
            List<Object[]> list = null;
            JExcelUtil jExcelUtil = new JExcelUtil();
            long count = 0;
            int recPerPage = 100;
            // 获取当前查询的总条数。
            count = rateSearchDao.countRateSearchPageNo(searchRateSearchVO);
            
            jExcelUtil.createWritableWorkbook(excelDirPath);
            // 设置第一个sheet的名字
            jExcelUtil.createWritableSheet(0, "Rate Search");
            
            // 将列表中的titles写入到Excel中。
            jExcelUtil.writeTitle(0, titles);
            
            if (count <= recPerPage) {
                searchRateSearchVO.setPageNo(1);
                searchRateSearchVO.setRecPerPage((int) count);

                list = rateSearchDao.downloadRateSearch(searchRateSearchVO);
                for (int i = 0; i < list.size(); i++) {
                    jExcelUtil.writeLine(i + 1, list.get(i));
                }
            } else {
                
                long totlePage = (count + recPerPage - 1) / recPerPage;
                for (int j = 0; j < totlePage; j++) {
                    searchRateSearchVO.setPageNo(j + 1);
                    searchRateSearchVO.setRecPerPage(recPerPage);
                    list = rateSearchDao.downloadRateSearch(searchRateSearchVO);
                    for (int i = 0; i < list.size(); i++) {
                        jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
                    }
                }
            }
            
            // 设置每一列的宽度。
            jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40});
            jExcelUtil.write();
            jExcelUtil.close();
            
        } catch (Throwable e) {
            
            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        logger.info(CcmFormat.formatExitLog());
        return excelDirPath;
    }
     
     public String downloadRateTemplate(SearchRateSearchVO searchRateSearchVO,String excelDirPath) throws BPLException {
    	 
    	 logger.info(CcmFormat.formatEnterLog(""));
    	 try {
    		 List<Object[]> list = null;
    		 JExcelFileUtil jUtil = new JExcelFileUtil();
    		 String keyField = "Key Field";
    		 long count = 0;
    		 int recPerPage = 100;
    		 Integer referenceTypeId = null;
    		 // 获取当前查询的总条数。
    		 count = rateSearchDao.countRateSearchPageNo(searchRateSearchVO);
    		 
//    		 jExcelUtil.createWritableWorkbook(excelDirPath);
//    		 // 设置第一个sheet的名字
//    		 jExcelUtil.createWritableSheet(0, "Rate Template");
    		 
    		 // 将列表中的titles写入到Excel中。
    		 List<String> titleList = null;
    		 if(searchRateSearchVO.getReferenceType().equals("Tariff")){
    			 titleList = getTariffTitles();
    			 referenceTypeId = 2;
    		 }else if(searchRateSearchVO.getReferenceType().equals("MtM")){
    			 titleList = getMtMTitles();
    			 referenceTypeId = 18;
    		 }else if(searchRateSearchVO.getReferenceType().equals("Contract")){
    			 titleList = getContractTitles();
    			 referenceTypeId = 3;
    		 }
    		 jUtil.createExcelFile(excelDirPath, titleList,"Rate");
//    		 jExcelUtil.writeTitle(0, titleList);
    		 
    		 jUtil.createNewSheet(keyField);
    		 jUtil.setSheet(1);
    		 List<String> keyFieldTitleList = new ArrayList<String>();
    		 keyFieldTitleList.add(keyField);
    		 jUtil.createSheetTitle(keyFieldTitleList);
    		 list = rateSearchDao.queryKeyFieldListByReferenceType(referenceTypeId);
			 for (int i = 0; i < list.size(); i++) {
				 jUtil.writeLine(i + 1, list.get(i));
			 }
			 jUtil.setSheet(0);
    		 if(searchRateSearchVO.getReferenceType().equals("Tariff")){
    			 jUtil.setDropDownBox(1, (int)count,2,2,keyField,list.size()+1);
    		 }else if(searchRateSearchVO.getReferenceType().equals("MtM")){
    			 jUtil.setDropDownBox(1, (int)count,3,3,keyField,list.size()+1);
    		 }else if(searchRateSearchVO.getReferenceType().equals("Contract")){
    			 jUtil.setDropDownBox(1, (int)count,3,3,keyField,list.size()+1);
    		 }
    		 if (count <= recPerPage) {
    			 searchRateSearchVO.setPageNo(1);
    			 searchRateSearchVO.setRecPerPage((int) count);
    			 
    			 list = rateSearchDao.downloadRateTemplate(searchRateSearchVO);
    			 for (int i = 0; i < list.size(); i++) {
    				 jUtil.writeLine(i + 1, list.get(i));
    			 }
    		 } else {
    			 
    			 long totlePage = (count + recPerPage - 1) / recPerPage;
    			 for (int j = 0; j < totlePage; j++) {
    				 searchRateSearchVO.setPageNo(j + 1);
    				 searchRateSearchVO.setRecPerPage(recPerPage);
    				 list = rateSearchDao.downloadRateTemplate(searchRateSearchVO);
    				 for (int i = 0; i < list.size(); i++) {
    					 jUtil.writeLine(j * recPerPage + i + 1, list.get(i));
    				 }
    			 }
    		 }
    		 
    		 // 设置每一列的宽度。
//    		 jExcelUtil.setColumnView(new int[] {40, 40, 40, 40, 40, 40, 40, 40, 40, 40,40, 40, 40, 40, 40, 40, 40, 40, 40, 40,40, 40, 40, 40, 40, 40, 40, 40, 40, 40,40});
    		 jUtil.write();
//    		 jExcelUtil.close();
    		 
    	 } catch (Throwable e) {
    		 
    		 logger.error(CcmFormat.formatErrorLog(e));
    		 BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
    		 throw bpe;
    	 }
    	 
    	 logger.info(CcmFormat.formatExitLog());
    	 return excelDirPath;
     }
     
     public String downloadRateBlankTemplate(String referenceType,String excelDirPath) throws BPLException {
    	 
    	 logger.info(CcmFormat.formatEnterLog(""));
    	 try {
    		 List<Object[]> list = null;
    		 JExcelFileUtil jUtil = new JExcelFileUtil();
    		 String keyField = "Key Field";
    		 long count = 100;
    		 Integer referenceTypeId = null;
    		 // 将列表中的titles写入到Excel中。
    		 List<String> titleList = null;
    		 if(referenceType.equals("Tariff")){
    			 titleList = getTariffTitles();
    			 referenceTypeId = 2;
    		 }else if(referenceType.equals("MtM")){
    			 titleList = getMtMTitles();
    			 referenceTypeId = 18;
    		 }else if(referenceType.equals("Contract")){
    			 titleList = getContractTitles();
    			 referenceTypeId = 3;
    		 }
    		 jUtil.createExcelFile(excelDirPath, titleList,"Rate");
    		 
    		 jUtil.createNewSheet(keyField);
    		 jUtil.setSheet(1);
    		 List<String> keyFieldTitleList = new ArrayList<String>();
    		 keyFieldTitleList.add(keyField);
    		 jUtil.createSheetTitle(keyFieldTitleList);
    		 list = rateSearchDao.queryKeyFieldListByReferenceType(referenceTypeId);
			 for (int i = 0; i < list.size(); i++) {
				 jUtil.writeLine(i + 1, list.get(i));
			 }
			 jUtil.setSheet(0);
    		 if(referenceType.equals("Tariff")){
    			 jUtil.setDropDownBox(1, (int)count,2,2,keyField,list.size()+1);
    		 }else if(referenceType.equals("MtM")){
    			 jUtil.setDropDownBox(1, (int)count,3,3,keyField,list.size()+1);
    		 }else if(referenceType.equals("Contract")){
    			 jUtil.setDropDownBox(1, (int)count,3,3,keyField,list.size()+1);
    		 }
    		 jUtil.write();
    		 
    	 } catch (Throwable e) {
    		 logger.error(CcmFormat.formatErrorLog(e));
    		 BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
    		 throw bpe;
    	 }
    	 
    	 logger.info(CcmFormat.formatExitLog());
    	 return excelDirPath;
     }

    public String listContractSummary(SearchRateSearchVO searchRateSearchVO) throws BPLException {

        StringBuffer sb = new StringBuffer();
        List<String> contractSummaryList = null;
        try {
            
            contractSummaryList = rateSearchDao.listContractSummary(searchRateSearchVO);

            sb.append(searchRateSearchVO.getListJsonCompatible(contractSummaryList));
        } catch (Exception e) {
            
            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
    }

     public String downloadContractSummary(SearchRateSearchVO searchRateSearchVO,String excelDirPath, List<String> titles) throws BPLException {

        logger.info(CcmFormat.formatEnterLog(""));
        try {
            List<Object[]> list = null;
            JExcelUtil jExcelUtil = new JExcelUtil();
            long count = 0;
            int recPerPage = 100;
            // 获取当前查询的总条数。
            count = rateSearchDao.countContractSummaryPageNo(searchRateSearchVO);
            
            jExcelUtil.createWritableWorkbook(excelDirPath);
            // 设置第一个sheet的名字
            jExcelUtil.createWritableSheet(0, "Contract Summary");
            
            // 将列表中的titles写入到Excel中。
            jExcelUtil.writeTitle(0, titles);
            
            if (count <= recPerPage) {
                searchRateSearchVO.setPageNo(1);
                searchRateSearchVO.setRecPerPage((int) count);

                list = rateSearchDao.downloadContractSummary(searchRateSearchVO);
                for (int i = 0; i < list.size(); i++) {
                    jExcelUtil.writeLine(i + 1, list.get(i));
                }
            } else {
                
                long totlePage = (count + recPerPage - 1) / recPerPage;
                for (int j = 0; j < totlePage; j++) {
                    searchRateSearchVO.setPageNo(j + 1);
                    searchRateSearchVO.setRecPerPage(recPerPage);
                    list = rateSearchDao.downloadContractSummary(searchRateSearchVO);
                    for (int i = 0; i < list.size(); i++) {
                        jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
                    }
                }
            }
            
            // 设置每一列的宽度。
            jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40});
            jExcelUtil.write();
            jExcelUtil.close();
            
        } catch (Throwable e) {
            
            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        logger.info(CcmFormat.formatExitLog());
        return excelDirPath;
    }

	/**
	 * @return the rateSearchDao
	 */
	public IRateSearchDao getRateSearchDao() {
		return rateSearchDao;
	}

	/**
	 * @param rateSearchDao the rateSearchDao to set
	 */
	public void setRateSearchDao(IRateSearchDao rateSearchDao) {
		this.rateSearchDao = rateSearchDao;
	}
    
	public Map<String,String> uploadRateInventory(File file,String fileName,String errorExcelDirPath){
		Map<String,String> returnMap = new HashMap<String,String>();
		try    
        {   
			excelBatchHelper = new ExcelBatchHelper(file, fileName);
			Map<String,Object> excelDataMap = excelBatchHelper.getExcelDataMap();
			List<String> columnList = null;
			columnList = (List<String>) excelDataMap.get("columnList");
			if(columnList.containsAll(getTariffTitles())){
				excelBatchHelper.setExcelBatchDaoDelegate(rateExcelTariffBatchDao);
			}else if(columnList.containsAll(getMtMTitles())){
				excelBatchHelper.setExcelBatchDaoDelegate(rateExcelMtMBatchDao);
			}else if(columnList.containsAll(getContractTitles())){
				excelBatchHelper.setExcelBatchDaoDelegate(rateExcelContractBatchDao);
			}
			returnMap = excelBatchHelper.uploadExcelBatchHelper(errorExcelDirPath);
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return returnMap;
	}

	public List<String> getTariffTitles(){
		List<String> tariffTitles = new ArrayList<String>();
		tariffTitles.add("ID");
		tariffTitles.add("Charge Type %Contains%");
		tariffTitles.add("Key Field*");
		tariffTitles.add("Rate Effective Date");
		tariffTitles.add("Summary Vendor Name (SVN) %Contains%");
		tariffTitles.add("Vendor Name (VN) %Contains%");
		tariffTitles.add("USOC");
		tariffTitles.add("USOC Description");
		tariffTitles.add("Sub Product");
		tariffTitles.add("Line Item Code Description");
		tariffTitles.add("Line Item Code");
		tariffTitles.add("Item Type");
		tariffTitles.add("Item Description");
		tariffTitles.add("Qty Begin");
		tariffTitles.add("Qty End");
		tariffTitles.add("Tariff File Name");
		tariffTitles.add("Tariff Name");
		tariffTitles.add("Base Amount");
		tariffTitles.add("Multiplier");
		tariffTitles.add("Rate");
		tariffTitles.add("Details from Tariff");
		tariffTitles.add("Tariff Page");
		tariffTitles.add("Part Section");
		tariffTitles.add("Item #");
		tariffTitles.add("CRTC #");
		tariffTitles.add("Discount");
		tariffTitles.add("Exclusion Ban");
		tariffTitles.add("Exclusion Item Description");
		tariffTitles.add("Notes");
		return tariffTitles;
	}
	public List<String> getMtMTitles(){
		List<String> tariffTitles = new ArrayList<String>();
		tariffTitles.add("ID");
		tariffTitles.add("Charge Type");
		tariffTitles.add("Summary Vendor Name (SVN) %Contains%");
		tariffTitles.add("Key Field*");
		tariffTitles.add("USOC");
		tariffTitles.add("USOC Long Description");
		tariffTitles.add("Stripped Circuit Number");
		tariffTitles.add("Sub Product");
		tariffTitles.add("Rate");
		tariffTitles.add("Effective Date");
		tariffTitles.add("Term");
		tariffTitles.add("Item Description");
		tariffTitles.add("Line Item Code");
		tariffTitles.add("Line Item Code Description");
		return tariffTitles;
	}
	public List<String> getContractTitles(){
		List<String> tariffTitles = new ArrayList<String>();
		tariffTitles.add("ID");
		tariffTitles.add("Summary Vendor Name (SVN) %Contains%");
		tariffTitles.add("Charge Type");
		tariffTitles.add("Key Field*");
		tariffTitles.add("USOC");
		tariffTitles.add("USOC Description");
		tariffTitles.add("Stripped Circuit Number");
		tariffTitles.add("Sub Product");
		tariffTitles.add("Rate");
		tariffTitles.add("Rate Effective Date");
		tariffTitles.add("Term (Months)");
		tariffTitles.add("Renewal Term after Term Expiration");
		tariffTitles.add("Early Termination Fee");
		tariffTitles.add("Item Description");
		tariffTitles.add("Contract Name");
		tariffTitles.add("Contract Service Schedule Name");
		tariffTitles.add("Line Item Code");
		tariffTitles.add("Line Item Code Description");
		tariffTitles.add("Qty Begin");
		tariffTitles.add("Qty End");
		tariffTitles.add("MMBC");
		tariffTitles.add("Discount %");
		tariffTitles.add("Notes");
		return tariffTitles;
	}
	
	public ExcelBatchHelper getExcelBatchHelper() {
		return excelBatchHelper;
	}

	public void setExcelBatchHelper(ExcelBatchHelper excelBatchHelper) {
		this.excelBatchHelper = excelBatchHelper;
	}

	public ExcelBatchDaoDelegate getRateExcelTariffBatchDao() {
		return rateExcelTariffBatchDao;
	}

	public void setRateExcelTariffBatchDao(
			ExcelBatchDaoDelegate rateExcelTariffBatchDao) {
		this.rateExcelTariffBatchDao = rateExcelTariffBatchDao;
	}

	public ExcelBatchDaoDelegate getRateExcelMtMBatchDao() {
		return rateExcelMtMBatchDao;
	}

	public void setRateExcelMtMBatchDao(ExcelBatchDaoDelegate rateExcelMtMBatchDao) {
		this.rateExcelMtMBatchDao = rateExcelMtMBatchDao;
	}

	public ExcelBatchDaoDelegate getRateExcelContractBatchDao() {
		return rateExcelContractBatchDao;
	}

	public void setRateExcelContractBatchDao(
			ExcelBatchDaoDelegate rateExcelContractBatchDao) {
		this.rateExcelContractBatchDao = rateExcelContractBatchDao;
	}

    
}