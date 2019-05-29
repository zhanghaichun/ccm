package com.saninco.ccm.service.quoteInventory;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.quoteInventory.IQuoteInventoryDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.quoteInventory.SearchQuoteVO;

public class QuoteInventoryServiceImpl implements IQuoteInventoryService {
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IQuoteInventoryDao quoteInventoryDao;
	
	public IQuoteInventoryDao getQuoteInventoryDao() {
		return quoteInventoryDao;
	}

	public void setQuoteInventoryDao(IQuoteInventoryDao quoteInventoryDao) {
		this.quoteInventoryDao = quoteInventoryDao;
	}
	

	public QuoteInventoryServiceImpl() { }
	
	/**
	 * 获取 provider list map.用于前台的 Provider 下拉列表过滤信息。
	 */
	public List<MapEntryVO<String, String>> getProviderListMap() throws BPLException {
		logger.info("Enter method getProviderListMap.");
		List<MapEntryVO<String, String>> providerListMap = null;
		List<String> providerList = null;
		
		try {
			
			providerList = quoteInventoryDao.getProviderList();
			providerListMap = new ArrayList<MapEntryVO<String, String>>(providerList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : providerList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            providerListMap.add(m);
        	}	
        }
		
		logger.info("Exit method getProviderListMap.");
		return providerListMap;
	}
	
	/**
	 * 获取 product list map.用于前台的 Product 下拉列表过滤信息。
	 */
	public List<MapEntryVO<String, String>> getProductListMap() throws BPLException {
		logger.info("Enter method getProviderList.");
		List<MapEntryVO<String, String>> productListMap = null;
		List<String> productList = null;
		
		try {
			
			productList = quoteInventoryDao.getProductList();
			productListMap = new ArrayList<MapEntryVO<String, String>>(productList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : productList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            productListMap.add(m);
        	}	
        }
		
		logger.info("Exit method getProviderList.");
		return productListMap;
	}
	
	/**
	 * 获取 Quote Inventory 列表信息。
	 */
	public String searchQuoteInventoryList(SearchQuoteVO searchQuoteVO) throws BPLException {
		
		logger.info(CcmFormat.formatEnterLog("Search Quote Inventory View List", searchQuoteVO));
		StringBuffer sb = new StringBuffer();
		List<String> quoteInventoryList = null;
		try {
			
			quoteInventoryList = quoteInventoryDao.searchQuoteInventoryList(searchQuoteVO);
			sb.append(searchQuoteVO.getListJsonCompatible(quoteInventoryList));
		} catch (Exception e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * 获取 Quote Inventory 列表分页显示信息。
	 */
	public String getQuoteInventoryViewListPageNo(SearchQuoteVO searchQuoteVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get Quote Inventory View List Page No", searchQuoteVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			// Quote Inventory 记录的条数。
			count = quoteInventoryDao.getQuoteInventoryViewListPageNo(searchQuoteVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		sb.append( searchQuoteVO.getTotalPageNoJson(count) );
		
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	

	/**
	 * 将 quote inventory 列表下载为 exce了文件。
	 */
	public String downloadQuoteInventoryToExcel(SearchQuoteVO searchQuoteVO,
			String excelDirPath, List<String> titles, List<Integer> ids) throws BPLException {
		
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			// 获取当前查询的总条数。
			count = quoteInventoryDao.getQuoteInventoryViewListPageNo(searchQuoteVO);
			
			jExcelUtil.createWritableWorkbook(excelDirPath);
			// 设置第一个sheet的名字
			jExcelUtil.createWritableSheet(0, "Quote Inventory");
			
			// 将列表中的titles写入到Excel中。
			jExcelUtil.writeTitle(0, titles);
			
			if (count <= recPerPage) {
				searchQuoteVO.setPageNo(1);
				searchQuoteVO.setRecPerPage((int) count);
				list = quoteInventoryDao.downloadQuoteInventoryToExcel(searchQuoteVO, ids);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchQuoteVO.setPageNo(j + 1);
					searchQuoteVO.setRecPerPage(recPerPage);
					list = quoteInventoryDao.downloadQuoteInventoryToExcel(searchQuoteVO, ids);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			
			// 设置每一列的宽度。
			jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40});
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
	 * 将Quote Inventory 表中信息的title信息当做Excel模板下载下来
	 */
	public String downloadQuoteInventoryTemplateToExcel(String excelDirPath, List<String> titles) throws BPLException {
		
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			
			
			jExcelUtil.createWritableWorkbook(excelDirPath);
			// 设置第一个sheet的名字
			jExcelUtil.createWritableSheet(0, "Quote Inventory Template");
			
			// 将列表中的titles写入到Excel中。
			jExcelUtil.writeTitle(0, titles);
			
			
			
			// 设置每一列的宽度。
			jExcelUtil.setColumnView(new int[] { 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40, 40});
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
	
}