package com.saninco.ccm.service.mtmRules;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.mtmRules.IMtMRulesDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.mtmRules.SearchMtMRulesVO;

public class MtMRulesServiceImpl implements IMtMRulesService {

    private final Logger logger = Logger.getLogger(this.getClass());

    private IMtMRulesDao mtmRulesDao;
    
    public MtMRulesServiceImpl() { }

    /**
     * 获取页面中 Charge Type 下拉列表中的内容。
     */
    public List<MapEntryVO<String, String>> listChargeType() throws BPLException {
        logger.info("Enter method listChargeType.");
        List<MapEntryVO<String, String>> chargeTypeMapList = null;
        List<String> chargeTypeList = null;
        
        try {
            
        	chargeTypeList = mtmRulesDao.listChargeType();
        	chargeTypeMapList = new ArrayList<MapEntryVO<String, String>>(chargeTypeList.size());
        } catch(Exception e) {
            
            logger.error("",e);
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        for(String listItem : chargeTypeList){
            
            if(! ( listItem.equals("ALL") ) ){
                MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
                chargeTypeMapList.add(m);
            }   
        }
        
        logger.info("Exit method listChargeType.");
        return chargeTypeMapList;
    }

    /**
     * 获取页面中 Key Field 下拉列表中的内容。
     */
    public List<MapEntryVO<String, String>> listKeyField() throws BPLException {
        logger.info("Enter method listKeyField.");
        List<MapEntryVO<String, String>> keyFieldMapList = null;
        List<String> keyFieldList = null;
        
        try {
            
        	keyFieldList = mtmRulesDao.listKeyField();
            keyFieldMapList = new ArrayList<MapEntryVO<String, String>>(keyFieldList.size());
        } catch(Exception e) {
            
            logger.error("",e);
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        for(String listItem : keyFieldList){
            
            if(! ( listItem.equals("ALL") ) ){
                MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
                keyFieldMapList.add(m);
            }   
        }
        
        logger.info("Exit method listKeyField.");
        return keyFieldMapList;
    }

    /**
     * 获取页面中 Sub Product 下拉列表中的内容。
     */
    public List<MapEntryVO<String, String>> listSubProduct() throws BPLException {
        logger.info("Enter method listSubProduct.");
        List<MapEntryVO<String, String>> subProductMapList = null;
        List<String> subProductList = null;
        
        try {
            
        	subProductList = mtmRulesDao.listSubProduct();
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
            
        	summaryVendorNameList = mtmRulesDao.listSummaryVendorName();
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
        List<Object[]> vendorNameList = null;
        
        try {
            
        	vendorNameList = mtmRulesDao.listVendorName();
        	vendorNameMapList = new ArrayList<MapEntryVO<String, String>>(vendorNameList.size());
        } catch(Exception e) {
            
            logger.error("",e);
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        for(Object listItem : vendorNameList){
            Object[] v = (Object[])listItem;
            if(! ( v[1].toString().equals("ALL") ) ){
                MapEntryVO<String, String> m = new MapEntryVO<String, String>( ( (Integer)v[0] ).toString(), (String)v[1] );
                vendorNameMapList.add(m);
            }   
        }
        
        logger.info("Exit method listVendorName.");
        return vendorNameMapList;
    }

    /**
     * 获取 MtM Rules 列表分页显示信息。
     */
    public String countMtMRulesViewListPageNo(SearchMtMRulesVO searchMtMRulesVO) throws BPLException {

        logger.info(CcmFormat.formatEnterLog("Count page number of MtM rules list item", searchMtMRulesVO));
        StringBuffer sb = new StringBuffer();
        long count = 0;
        try {

            // MtM Rules 记录的条数。
            count = mtmRulesDao.countMtMRulesViewListPageNo(searchMtMRulesVO);
        } catch (RuntimeException e) {

            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }

        sb.append( searchMtMRulesVO.getTotalPageNoJson(count) );
        
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
    }

    /**
     * 获取 MtM rules 列表中的数据信息。
     */
    public String listMtMRules(SearchMtMRulesVO searchMtMRulesVO) throws BPLException {

        logger.info(CcmFormat.formatEnterLog("Search MtM rules View List", searchMtMRulesVO));
        StringBuffer sb = new StringBuffer();
        List<String> mtmRulesList = null;
        try {
            
            mtmRulesList = mtmRulesDao.listMtMRules(searchMtMRulesVO);

            sb.append(searchMtMRulesVO.getListJsonCompatible(mtmRulesList));
        } catch (Exception e) {
            
            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
    }

    /**
     * 下载 MtM rules列表
     */
     public String downloadMtMRulesToExcel(SearchMtMRulesVO searchMtMRulesVO,String excelDirPath, List<String> titles) throws BPLException {

        logger.info(CcmFormat.formatEnterLog(""));
        try {
            List<Object[]> list = null;
            JExcelUtil jExcelUtil = new JExcelUtil();
            long count = 0;
            int recPerPage = 100;
            // 获取当前查询的总条数。
            count = mtmRulesDao.countMtMRulesViewListPageNo(searchMtMRulesVO);
            
            jExcelUtil.createWritableWorkbook(excelDirPath);
            // 设置第一个sheet的名字
            jExcelUtil.createWritableSheet(0, "MtM Validation Rules");
            
            // 将列表中的titles写入到Excel中。
            jExcelUtil.writeTitle(0, titles);
            
            if (count <= recPerPage) {
                searchMtMRulesVO.setPageNo(1);
                searchMtMRulesVO.setRecPerPage((int) count);

                list = mtmRulesDao.downloadMtMRulesToExcel(searchMtMRulesVO);
                for (int i = 0; i < list.size(); i++) {
                    jExcelUtil.writeLine(i + 1, list.get(i));
                }
            } else {
                
                long totlePage = (count + recPerPage - 1) / recPerPage;
                for (int j = 0; j < totlePage; j++) {
                    searchMtMRulesVO.setPageNo(j + 1);
                    searchMtMRulesVO.setRecPerPage(recPerPage);
                    list = mtmRulesDao.downloadMtMRulesToExcel(searchMtMRulesVO);
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

	public IMtMRulesDao getMtmRulesDao() {
		return mtmRulesDao;
	}

	public void setMtmRulesDao(IMtMRulesDao mtmRulesDao) {
		this.mtmRulesDao = mtmRulesDao;
	}
    

    
}