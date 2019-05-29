package com.saninco.ccm.service.contractTariffPriceList;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.contractTariffPriceList.IContractTariffPriceListDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.contractTariffPriceList.SearchContractTariffPriceListVO;
import com.saninco.ccm.vo.quoteInventory.SearchQuoteVO;

public class ContractTariffPriceListServiceImpl implements IContractTariffPriceListService {
	private final Logger logger = Logger.getLogger(this.getClass());
	

	private IContractTariffPriceListDao contractTariffPriceListDao;

	/**
	 * @return the contractTariffPriceListDao
	 */
	public IContractTariffPriceListDao getContractTariffPriceListDao() {
		return contractTariffPriceListDao;
	}

	/**
	 * @param contractTariffPriceListDao the contractTariffPriceListDao to set
	 */
	public void setContractTariffPriceListDao(
			IContractTariffPriceListDao contractTariffPriceListDao) {
		this.contractTariffPriceListDao = contractTariffPriceListDao;
	}

	public ContractTariffPriceListServiceImpl() { }
	
	/**
	 * 获取 Contract List 列表信息。
	 */
	public String getContractList(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException {
		
		logger.info(CcmFormat.formatEnterLog("Search Quote Inventory View List", searchContractTariffPriceListVO));
		StringBuffer sb = new StringBuffer();
		List<String> contractList = null;
		try {
			
			contractList = contractTariffPriceListDao.getContractList(searchContractTariffPriceListVO, searchQuoteVO);
			sb.append(searchContractTariffPriceListVO.getListJsonCompatible(contractList));
		} catch (Exception e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * 查询Contract 列表的分页信息。
	 */
	public String getContractListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException {
		
		logger.info(CcmFormat.formatEnterLog("Get Contract View List Page No", searchContractTariffPriceListVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			// Contract List 记录的条数。
			count = contractTariffPriceListDao.getContractListPageNo(searchContractTariffPriceListVO, searchQuoteVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		sb.append( searchContractTariffPriceListVO.getTotalPageNoJson(count) );
		
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * 下载 Contract List 文件到 Excel 文件中。
	 */
	public String downloadContractList(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO, String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			// 获取当前查询的总条数。
			count = contractTariffPriceListDao.getContractListPageNo(searchContractTariffPriceListVO, searchQuoteVO);
			
			jExcelUtil.createWritableWorkbook(excelDirPath);
			
			if ( searchContractTariffPriceListVO.getIsExpiryDateContract() != null && searchContractTariffPriceListVO.getIsExpiryDateContract() == true ) {
				jExcelUtil.createWritableSheet(0, "Expiry Date Contracts");
			} else {
				// 设置第一个sheet的名字
				jExcelUtil.createWritableSheet(0, "Contracts");
			}
			
			
			// 将列表中的titles写入到Excel中。
			jExcelUtil.writeTitle(0, titles);
			
			if (count <= recPerPage) {
				searchContractTariffPriceListVO.setPageNo(1);
				searchContractTariffPriceListVO.setRecPerPage((int) count);
				list = contractTariffPriceListDao.downloadContractList(searchContractTariffPriceListVO, searchQuoteVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchContractTariffPriceListVO.setPageNo(j + 1);
					searchContractTariffPriceListVO.setRecPerPage(recPerPage);
					list = contractTariffPriceListDao.downloadContractList(searchContractTariffPriceListVO, searchQuoteVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			
			// 设置每一列的宽度。
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30, 30, 30, 30, 60, 30});
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
	 * 获取已经过期的 Contracts 的个数。
	 */
	public Long getContractCountsByExpiryDate(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException {
		logger.info("Get Expiry Date Contract Count Method");
		long count = 0;
		try{
			count = contractTariffPriceListDao.getContractCountsByExpiryDate(searchContractTariffPriceListVO, searchQuoteVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		logger.info(CcmFormat.formatExitLog());
		
		return count;
	}
	
	/**
	 * 查询Tariff 列表的分页信息。
	 */
	public String getTariffListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get Tariff View List Page No", searchContractTariffPriceListVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			// Tariff List 记录的条数。
			count = contractTariffPriceListDao.getTariffListPageNo(searchContractTariffPriceListVO, searchQuoteVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		sb.append( searchContractTariffPriceListVO.getTotalPageNoJson(count) );
		
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * 查询Tariff 列表。
	 */
	public String getTariffList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException {
		logger.info(" Enter Method getTariffList. ");
		StringBuffer sb = new StringBuffer();
		List<String> tariffList = null;
		try {
			
			tariffList = contractTariffPriceListDao.getTariffList(searchContractTariffPriceListVO, searchQuoteVO);
			sb.append(searchContractTariffPriceListVO.getListJsonCompatible(tariffList));
		} catch (Exception e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(" Exit Method getTariffList. ");
		return sb.toString();
	}


	/**
	 * 下载 Tariff List 列表到Excel文件中.
	 */
	public String downloadTariffList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO, String excelDirPath, List<String> titles) throws BPLException {
		logger.info("Enter Method downloadTariffList");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			// 获取当前查询的总条数。
			count = contractTariffPriceListDao.getTariffListPageNo(searchContractTariffPriceListVO, searchQuoteVO);
			
			jExcelUtil.createWritableWorkbook(excelDirPath);
			// 设置第一个sheet的名字
			jExcelUtil.createWritableSheet(0, "Tariff");
			
			// 将列表中的titles写入到Excel中。
			jExcelUtil.writeTitle(0, titles);
			
			if (count <= recPerPage) {
				searchContractTariffPriceListVO.setPageNo(1);
				searchContractTariffPriceListVO.setRecPerPage((int) count);
				list = contractTariffPriceListDao.downloadTariffList(searchContractTariffPriceListVO, searchQuoteVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchContractTariffPriceListVO.setPageNo(j + 1);
					searchContractTariffPriceListVO.setRecPerPage(recPerPage);
					list = contractTariffPriceListDao.downloadTariffList(searchContractTariffPriceListVO, searchQuoteVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			
			// 设置每一列的宽度。
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 30, 30, 30, 40, 30, 30, 40, 40, 30});
			jExcelUtil.write();
			jExcelUtil.close();
			
		} catch (Throwable e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		logger.info("Exit Method downloadTariffList");
		return excelDirPath;
	}


	/**
	 * 查询Price List 列表的分页信息。
	 */
	public String getPriceListListPageNo( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException {
		logger.info(" Enter Method getPriceListListPageNo ");
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			// Tariff List 记录的条数。
			count = contractTariffPriceListDao.getPriceListListPageNo(searchContractTariffPriceListVO, searchQuoteVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		sb.append( searchContractTariffPriceListVO.getTotalPageNoJson(count) );
		
		logger.info(" Exit Method getPriceListListPageNo ");
		return sb.toString();
	}

	/**
	 * 查询Price List 列表。
	 */
	public String getPriceListList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO) throws BPLException {
		logger.info(" Enter Method getPriceListList. ");
		StringBuffer sb = new StringBuffer();
		List<String> priceListList = null;
		try {
			
			priceListList = contractTariffPriceListDao.getPriceListList(searchContractTariffPriceListVO, searchQuoteVO);
			sb.append(searchContractTariffPriceListVO.getListJsonCompatible(priceListList));
		} catch (Exception e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(" Exit Method getPriceListList. ");
		return sb.toString();
	}


	/**
	 * 下载 Price List 列表到Excel文件中.
	 */
	public String downloadPriceListList( SearchContractTariffPriceListVO searchContractTariffPriceListVO, SearchQuoteVO searchQuoteVO, String excelDirPath, List<String> titles) throws BPLException {
		logger.info("Enter Method downloadPriceListList");
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			// 获取当前查询的总条数。
			count = contractTariffPriceListDao.getPriceListListPageNo(searchContractTariffPriceListVO, searchQuoteVO);
			
			jExcelUtil.createWritableWorkbook(excelDirPath);
			// 设置第一个sheet的名字
			jExcelUtil.createWritableSheet(0, "Price List");
			
			// 将列表中的titles写入到Excel中。
			jExcelUtil.writeTitle(0, titles);
			
			if (count <= recPerPage) {
				searchContractTariffPriceListVO.setPageNo(1);
				searchContractTariffPriceListVO.setRecPerPage((int) count);
				list = contractTariffPriceListDao.downloadPriceListList(searchContractTariffPriceListVO, searchQuoteVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchContractTariffPriceListVO.setPageNo(j + 1);
					searchContractTariffPriceListVO.setRecPerPage(recPerPage);
					list = contractTariffPriceListDao.downloadPriceListList(searchContractTariffPriceListVO, searchQuoteVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			
			// 设置每一列的宽度。
			jExcelUtil.setColumnView(new int[] { 30, 30, 30, 40, 40, 30, 30});
			jExcelUtil.write();
			jExcelUtil.close();
			
		} catch (Throwable e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		logger.info("Exit Method downloadPriceListList");
		return excelDirPath;
	}


	/**
	 * 获取 product component 列表。
	 */
	public List<MapEntryVO<String, String>> getProductComponentMapList() throws BPLException {
		logger.info("Enter method getProductComponentMapList.");
		List<MapEntryVO<String, String>> productComponentMapList = null;
		List<String> productComponentList = null;
		
		try {
			
			productComponentList = contractTariffPriceListDao.getProductComponentList();
			productComponentMapList = new ArrayList<MapEntryVO<String, String>>(productComponentList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : productComponentList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            productComponentMapList.add(m);
        	}	
        }
		
		logger.info("Exit method getProductComponentMapList.");
		return productComponentMapList;
	}


	/**
	 * 获取 product 列表。
	 */
	public List<MapEntryVO<String, String>> getProductMapList() throws BPLException {
		logger.info("Enter method getProviderListMap.");
		List<MapEntryVO<String, String>> productMapList = null;
		List<String> productList = null;
		
		try {
			
			productList = contractTariffPriceListDao.getProductList();
			productMapList = new ArrayList<MapEntryVO<String, String>>(productList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : productList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            productMapList.add(m);
        	}	
        }
		
		logger.info("Exit method getProviderListMap.");
		return productMapList;
	}
	
	/**
	 * 查询 Rate Discrepancy 列表。
	 */
	public String getRateDiscrepancyList( SearchContractTariffPriceListVO searchContractTariffPriceListVO) throws BPLException {
		logger.info(" Enter Method getRateDiscrepancyList. ");
		StringBuffer sb = new StringBuffer();
		List<String> rateDiscrepancyListList = null;
		try {
			
			rateDiscrepancyListList = contractTariffPriceListDao.getRateDiscrepancyList(searchContractTariffPriceListVO);
			sb.append(searchContractTariffPriceListVO.getListJsonCompatible(rateDiscrepancyListList));
		} catch (Exception e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(" Exit Method getRateDiscrepancyList. ");
		return sb.toString();
	}
	
	/**
	 * 查询Rate Discrepancy 列表的分页信息。
	 */
	public String getRateDiscrepancyListPageNo(SearchContractTariffPriceListVO searchContractTariffPriceListVO) throws BPLException {
		
		logger.info(CcmFormat.formatEnterLog("Get Contract View List Page No", searchContractTariffPriceListVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			// Contract List 记录的条数。
			count = contractTariffPriceListDao.getRateDiscrepancyListPageNo(searchContractTariffPriceListVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		sb.append( searchContractTariffPriceListVO.getTotalPageNoJson(count) );
		
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * 导出 Rate Discrepancy 到 excel
	 * @param searchContractTariffPriceListVO
	 * @param excelDirPath
	 * @param titles
	 * @return
	 * @throws BPLException
	 */
	public String createRateDiscrepancyToExcel(SearchContractTariffPriceListVO searchContractTariffPriceListVO,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = contractTariffPriceListDao.getRateDiscrepancyDataForExcelPageNo(searchContractTariffPriceListVO);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "Master Inventory");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchContractTariffPriceListVO.setPageNo(1);
				searchContractTariffPriceListVO.setRecPerPage((int) count);
				list = contractTariffPriceListDao.getRateDiscrepancyDataForExcel(searchContractTariffPriceListVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchContractTariffPriceListVO.setPageNo(j + 1);
					searchContractTariffPriceListVO.setRecPerPage(recPerPage);
					list = contractTariffPriceListDao.getRateDiscrepancyDataForExcel(searchContractTariffPriceListVO);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 30, 30, 60, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25});
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