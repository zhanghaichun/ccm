package com.saninco.ccm.service.tariffRules;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.tariffRules.ITariffRulesDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.tariffRules.SearchTariffRulesVO;

public class TariffRulesServiceImpl implements ITariffRulesService {

    private final Logger logger = Logger.getLogger(this.getClass());

    private ITariffRulesDao tariffRulesDao;
    
    public TariffRulesServiceImpl() { }

    /**
     * 获取页面中 Charge Type 下拉列表中的内容。
     */
    public List<MapEntryVO<String, String>> listChargeType() throws BPLException {
        logger.info("Enter method listChargeType.");
        List<MapEntryVO<String, String>> chargeTypeMapList = null;
        List<String> chargeTypeList = null;
        
        try {
            
        	chargeTypeList = tariffRulesDao.listChargeType();
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
            
        	keyFieldList = tariffRulesDao.listKeyField();
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
            
        	subProductList = tariffRulesDao.listSubProduct();
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
            
        	summaryVendorNameList = tariffRulesDao.listSummaryVendorName();
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
            
        	vendorNameList = tariffRulesDao.listVendorName();
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
     * 获取页面中 Tariff Name 下拉列表中的内容。
     */
    public List<MapEntryVO<String, String>> listTariffName() throws BPLException {
        logger.info("Enter method listTariffName.");
        List<MapEntryVO<String, String>> tariffNameMapList = null;
        List<String> tariffNameList = null;
        
        try {
            
        	tariffNameList = tariffRulesDao.listTariffName();
        	tariffNameMapList = new ArrayList<MapEntryVO<String, String>>(tariffNameList.size());
        } catch(Exception e) {
            
            logger.error("",e);
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        for(String listItem : tariffNameList){
            
            if(! ( listItem.equals("ALL") ) ){
                MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
                tariffNameMapList.add(m);
            }   
        }
        
        logger.info("Exit method listTariffName.");
        return tariffNameMapList;
    }

    /**
     * 获取 Tariff Rules 列表分页显示信息。
     */
    public String countTariffRulesViewListPageNo(SearchTariffRulesVO searchTariffRulesVO) throws BPLException {

        logger.info(CcmFormat.formatEnterLog("Count page number of tariff rules list item", searchTariffRulesVO));
        StringBuffer sb = new StringBuffer();
        long count = 0;
        try {

            // Tariff Rules 记录的条数。
            count = tariffRulesDao.countTariffRulesViewListPageNo(searchTariffRulesVO);
        } catch (RuntimeException e) {

            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }

        sb.append( searchTariffRulesVO.getTotalPageNoJson(count) );
        
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
    }

    /**
     * 获取 tariff rules 列表中的数据信息。
     */
    public String listTariffRules(SearchTariffRulesVO searchTariffRulesVO) throws BPLException {

        logger.info(CcmFormat.formatEnterLog("Search tariff rules View List", searchTariffRulesVO));
        StringBuffer sb = new StringBuffer();
        List<String> tariffRulesList = null;
        try {
            
            tariffRulesList = tariffRulesDao.listTariffRules(searchTariffRulesVO);

            sb.append(searchTariffRulesVO.getListJsonCompatible(tariffRulesList));
        } catch (Exception e) {
            
            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
    }

    /**
     * 下载 tariff rules列表
     */
     public String downloadTariffRulesToExcel(SearchTariffRulesVO searchTariffRulesVO,String excelDirPath, List<String> titles) throws BPLException {

        logger.info(CcmFormat.formatEnterLog(""));
        try {
            List<Object[]> list = null;
            JExcelUtil jExcelUtil = new JExcelUtil();
            long count = 0;
            int recPerPage = 100;
            // 获取当前查询的总条数。
            count = tariffRulesDao.countTariffRulesViewListPageNo(searchTariffRulesVO);
            
            jExcelUtil.createWritableWorkbook(excelDirPath);
            // 设置第一个sheet的名字
            jExcelUtil.createWritableSheet(0, "Tariff Validation Rules");
            
            // 将列表中的titles写入到Excel中。
            jExcelUtil.writeTitle(0, titles);
            
            if (count <= recPerPage) {
                searchTariffRulesVO.setPageNo(1);
                searchTariffRulesVO.setRecPerPage((int) count);

                list = tariffRulesDao.downloadTariffRulesToExcel(searchTariffRulesVO);
                for (int i = 0; i < list.size(); i++) {
                    jExcelUtil.writeLine(i + 1, list.get(i));
                }
            } else {
                
                long totlePage = (count + recPerPage - 1) / recPerPage;
                for (int j = 0; j < totlePage; j++) {
                    searchTariffRulesVO.setPageNo(j + 1);
                    searchTariffRulesVO.setRecPerPage(recPerPage);
                    list = tariffRulesDao.downloadTariffRulesToExcel(searchTariffRulesVO);
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
	 * @return the tariffRulesDao
	 */
	public ITariffRulesDao getTariffRulesDao() {
		return tariffRulesDao;
	}

	/**
	 * @param tariffRulesDao the tariffRulesDao to set
	 */
	public void setTariffRulesDao(ITariffRulesDao tariffRulesDao) {
		this.tariffRulesDao = tariffRulesDao;
	}
    
    

    
}