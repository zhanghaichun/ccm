package com.saninco.ccm.service.contractRules;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.contractRules.IContractRulesDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.contractRules.SearchContractRulesVO;

public class ContractRulesServiceImpl implements IContractRulesService {

    private final Logger logger = Logger.getLogger(this.getClass());

    private IContractRulesDao contractRulesDao;
    
    public ContractRulesServiceImpl() { }

    /**
     * 获取页面中 Summary Vendor Name 下拉列表中的内容。
     */
    public List<MapEntryVO<String, String>> listSummaryVendorName() throws BPLException {
        logger.info("Enter method listSummaryVendorName.");
        List<MapEntryVO<String, String>> summaryVendorNameMapList = null;
        List<String> summaryVendorNameList = null;
        
        try {
            
        	summaryVendorNameList = contractRulesDao.listSummaryVendorName();
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
     * 获取页面中 Key Field 下拉列表中的内容。
     */
    public List<MapEntryVO<String, String>> listKeyField() throws BPLException {
        logger.info("Enter method listKeyField.");
        List<MapEntryVO<String, String>> keyFieldMapList = null;
        List<String> keyFieldList = null;
        
        try {
            
        	keyFieldList = contractRulesDao.listKeyField();
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
            
        	subProductList = contractRulesDao.listSubProduct();
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
     * 获取页面中 Contract Name 下拉列表中的内容。
     */
    public List<MapEntryVO<String, String>> listContractName() throws BPLException {
        logger.info("Enter method listContractName.");
        List<MapEntryVO<String, String>> contractNameMapList = null;
        List<String> ContractNameList = null;
        
        try {
            
        	ContractNameList = contractRulesDao.listContractName();
            contractNameMapList = new ArrayList<MapEntryVO<String, String>>(ContractNameList.size());
        } catch(Exception e) {
            
            logger.error("",e);
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        
        for(String listItem : ContractNameList){
            
            if(! ( listItem.equals("ALL") ) ){
                MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
                contractNameMapList.add(m);
            }   
        }
        
        logger.info("Exit method listContractName.");
        return contractNameMapList;
    }

    /**
     * 获取 Contract Rules 列表分页显示信息。
     */
    public String countContractRulesViewListPageNo(SearchContractRulesVO searchContractRulesVO) throws BPLException {

        logger.info(CcmFormat.formatEnterLog("Count page number of contract rules list item", searchContractRulesVO));
        StringBuffer sb = new StringBuffer();
        long count = 0;
        try {

            // Contract Rules 记录的条数。
            count = contractRulesDao.countContractRulesViewListPageNo(searchContractRulesVO);
        } catch (RuntimeException e) {

            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }

        sb.append( searchContractRulesVO.getTotalPageNoJson(count) );
        
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
    }

    /**
     * 获取contract rules 列表中的数据信息。
     */
    public String listContractRules(SearchContractRulesVO searchContractRulesVO) throws BPLException {

        logger.info(CcmFormat.formatEnterLog("Search Contract rules View List", searchContractRulesVO));
        StringBuffer sb = new StringBuffer();
        List<String> contractRulesList = null;
        try {
            
        	contractRulesList = contractRulesDao.listContractRules(searchContractRulesVO);
            sb.append(searchContractRulesVO.getListJsonCompatible(contractRulesList));
        } catch (Exception e) {
            
            logger.error(CcmFormat.formatErrorLog(e));
            BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
            throw bpe;
        }
        logger.info(CcmFormat.formatExitLog());
        return sb.toString();
    }

    /**
     * 下载contract rules列表
     */
    public String downloadContractRulesToExcel(SearchContractRulesVO searchContractRulesVO,String excelDirPath, List<String> titles) throws BPLException {

        logger.info(CcmFormat.formatEnterLog(""));
        try {
            List<Object[]> list = null;
            JExcelUtil jExcelUtil = new JExcelUtil();
            long count = 0;
            int recPerPage = 100;
            // 获取当前查询的总条数。
            count = contractRulesDao.countContractRulesViewListPageNo(searchContractRulesVO);
            
            jExcelUtil.createWritableWorkbook(excelDirPath);
            // 设置第一个sheet的名字
            jExcelUtil.createWritableSheet(0, "Contract Validation Rules");
            
            // 将列表中的titles写入到Excel中。
            jExcelUtil.writeTitle(0, titles);
            
            if (count <= recPerPage) {
                searchContractRulesVO.setPageNo(1);
                searchContractRulesVO.setRecPerPage((int) count);

                list = contractRulesDao.downloadContractRulesToExcel(searchContractRulesVO);
                for (int i = 0; i < list.size(); i++) {
                    jExcelUtil.writeLine(i + 1, list.get(i));
                }
            } else {
                
                long totlePage = (count + recPerPage - 1) / recPerPage;
                for (int j = 0; j < totlePage; j++) {
                    searchContractRulesVO.setPageNo(j + 1);
                    searchContractRulesVO.setRecPerPage(recPerPage);
                    list = contractRulesDao.downloadContractRulesToExcel(searchContractRulesVO);
                    for (int i = 0; i < list.size(); i++) {
                        jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
                    }
                }
            }
            
            // 设置每一列的宽度。
            jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40});
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
	 * @return the contractRulesDao
	 */
	public IContractRulesDao getContractRulesDao() {
		return contractRulesDao;
	}

	/**
	 * @param contractRulesDao the contractRulesDao to set
	 */
	public void setContractRulesDao(IContractRulesDao contractRulesDao) {
		this.contractRulesDao = contractRulesDao;
	}
    
    
    

    
}