package com.saninco.ccm.service.pricelist;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.pricelist.IPricelistDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.pricelist.SearchPricelistVO;

public class PricelistServiceImpl implements IPricelistService {

	private final Logger logger = Logger.getLogger(this.getClass());

	private IPricelistDao pricelistDao;

	/**
	 * @return the pricelistDao
	 */
	public IPricelistDao getPricelistDao() {
		return pricelistDao;
	}

	/**
	 * @param pricelistDao the pricelistDao to set
	 */
	public void setPricelistDao(
			IPricelistDao pricelistDao) {
		this.pricelistDao = pricelistDao;
	}

	public PricelistServiceImpl() { }

	@Override
	/**
	 * 查询  band list.
	 */
	public List<MapEntryVO<String, String>> getBandListMap()
			throws BPLException {
		
		logger.info("Enter method getBandListMap.");
		List<MapEntryVO<String, String>> bandListMap = null;
		List<String> bandList = null;
		
		try {
			
			bandList = pricelistDao.getBandList();
			bandListMap = new ArrayList<MapEntryVO<String, String>>(bandList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : bandList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            bandListMap.add(m);
        	}	
        }
		
		logger.info("Exit method getBandListMap.");
		return bandListMap;
	}

	@Override
	/**
	 * 查询 service type list.
	 */
	public List<MapEntryVO<String, String>> getServiceTypeListMap()
			throws BPLException {

		logger.info("Enter method getServiceTypeListMap.");
		List<MapEntryVO<String, String>> serviceTypeListMap = null;
		List<String> serviceTypeList = null;
		
		try {
			
			serviceTypeList = pricelistDao.getServiceTypeList();
			serviceTypeListMap = new ArrayList<MapEntryVO<String, String>>(serviceTypeList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : serviceTypeList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            serviceTypeListMap.add(m);
        	}	
        }
		
		logger.info("Exit method getServiceTypeListMap.");
		return serviceTypeListMap;
	}

	@Override
	/**
	 * Pricelist 列表分页信息。
	 */
	public String getPricelistViewListPageNo(SearchPricelistVO searchPricelistVO) throws BPLException {

		logger.info(CcmFormat.formatEnterLog("Get Pricelist View List Page No", searchPricelistVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			// contract 记录的条数。
			count = pricelistDao.getPricelistViewListPageNo(searchPricelistVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		sb.append( searchPricelistVO.getTotalPageNoJson(count) );
		
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	@Override
	/**
	 * 查询 price list 列表信息。
	 */
	public String searchPricelistList(SearchPricelistVO searchPricelistVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Pricelist View List", searchPricelistVO));
		StringBuffer sb = new StringBuffer();
		List<String> pricelistList = null;
		try {
			
			pricelistList = pricelistDao.searchPricelistList(searchPricelistVO);
			sb.append(searchPricelistVO.getListJsonCompatible(pricelistList));
		} catch (Exception e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * 下载 pricelist。
	 * @param  searchPricelistVO [description]
	 * @param  excelDirPath      [description]
	 * @param  titles            [description]
	 * @return                   [description]
	 * @throws BPLException      [description]
	 */
	public String downloadPricelistToExcel(SearchPricelistVO searchPricelistVO,String excelDirPath, List<String> titles) throws BPLException {

		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			// 获取当前查询的总条数。
			count = pricelistDao.getPricelistViewListPageNo(searchPricelistVO);
			
			jExcelUtil.createWritableWorkbook(excelDirPath);
			// 设置第一个sheet的名字
			jExcelUtil.createWritableSheet(0, "Pricelist");
			
			// 将列表中的titles写入到Excel中。
			jExcelUtil.writeTitle(0, titles);
			
			if (count <= recPerPage) {
				searchPricelistVO.setPageNo(1);
				searchPricelistVO.setRecPerPage((int) count);
				list = pricelistDao.downloadPricelistToExcel(searchPricelistVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchPricelistVO.setPageNo(j + 1);
					searchPricelistVO.setRecPerPage(recPerPage);
					list = pricelistDao.downloadPricelistToExcel(searchPricelistVO);
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
}