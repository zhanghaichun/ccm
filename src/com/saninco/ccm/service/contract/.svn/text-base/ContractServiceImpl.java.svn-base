package com.saninco.ccm.service.contract;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.contract.IContractDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.contract.SearchContractVO;

public class ContractServiceImpl implements IContractService {

	private final Logger logger = Logger.getLogger(this.getClass());

	private IContractDao contractDao;
	
	/**
	 * @return the contractDao
	 */
	public IContractDao getContractDao() {
		return contractDao;
	}

	/**
	 * @param contractDao the contractDao to set
	 */
	public void setContractDao(IContractDao contractDao) {
		this.contractDao = contractDao;
	}

	public ContractServiceImpl() { }

	/**
	 * 获取 carrier entity name list map.用于前台的 carrier entity name 下拉列表过滤信息。
	 */
	public List<MapEntryVO<String, String>> getCarrierEntityNameListMap() throws BPLException {
		logger.info("Enter method getCarrierEntityNameListMap.");
		List<MapEntryVO<String, String>> carrierEntityNameListMap = null;
		List<String> carrierEntityNameList = null;
		
		try {
			
			carrierEntityNameList = contractDao.getCarrierEntityNameList();
			carrierEntityNameListMap = new ArrayList<MapEntryVO<String, String>>(carrierEntityNameList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : carrierEntityNameList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            carrierEntityNameListMap.add(m);
        	}	
        }
		
		logger.info("Exit method getCarrierEntityNameListMap.");
		return carrierEntityNameListMap;
	}

	/**
	 * 获取 carrier type list map.用于前台的 Carrier Type 下拉列表过滤信息。
	 */
	public List<MapEntryVO<String, String>> getCarrierTypeListMap() throws BPLException {
		logger.info("Enter method getCarrierTypeListMap.");
		List<MapEntryVO<String, String>> carrierTypeListMap = null;
		List<String> carrierTypeList = null;
		
		try {
			
			carrierTypeList = contractDao.getCarrierTypeList();
			carrierTypeListMap = new ArrayList<MapEntryVO<String, String>>(carrierTypeList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : carrierTypeList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            carrierTypeListMap.add(m);
        	}	
        }
		
		logger.info("Exit method getCarrierTypeListMap.");
		return carrierTypeListMap;
	}

	/**
	 * 获取 agreement type list map.用于前台的 agreement type 下拉列表过滤信息。
	 */
	public List<MapEntryVO<String, String>> getAgreementTypeListMap() throws BPLException {
		logger.info("Enter method getAgreementTypeListMap.");
		List<MapEntryVO<String, String>> agreementTypeListMap = null;
		List<String> agreementTypeList = null;
		
		try {
			
			agreementTypeList = contractDao.getAgreementTypeList();
			agreementTypeListMap = new ArrayList<MapEntryVO<String, String>>(agreementTypeList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : agreementTypeList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            agreementTypeListMap.add(m);
        	}	
        }
		
		logger.info("Exit method getAgreementTypeListMap.");
		return agreementTypeListMap;
	}

	/**
	 * 获取 products/services list map.用于前台的 products/services 下拉列表过滤信息。
	 */
	public List<MapEntryVO<String, String>> getProductsServicesListMap() throws BPLException {
		logger.info("Enter method getProductsServicesListMap.");
		List<MapEntryVO<String, String>> productsServicesListMap = null;
		List<String> productsServicesList = null;
		
		try {
			
			productsServicesList = contractDao.getProductsServicesList();
			productsServicesListMap = new ArrayList<MapEntryVO<String, String>>(productsServicesList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : productsServicesList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            productsServicesListMap.add(m);
        	}	
        }
		
		logger.info("Exit method getProductsServicesListMap.");
		return productsServicesListMap;
	}

	/**
	 * 获取 term combined list map.用于前台的 TERM 下拉列表过滤信息。
	 */
	public List<MapEntryVO<String, String>> getTermCombinedListMap() throws BPLException {
		logger.info("Enter method getTermCombinedListMap.");
		List<MapEntryVO<String, String>> termCombinedListMap = null;
		List<String> termCombinedList = null;
		
		try {
			
			termCombinedList = contractDao.getTermCombinedList();
			termCombinedListMap = new ArrayList<MapEntryVO<String, String>>(termCombinedList.size());
		} catch(Exception e) {
			
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(String listItem : termCombinedList){
			
            if(! ( listItem.equals("ALL") ) ){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(listItem, listItem);
	            termCombinedListMap.add(m);
        	}	
        }
		
		logger.info("Exit method getTermCombinedListMap.");
		return termCombinedListMap;
	}

	/**
	 * 获取 Contract 列表分页显示信息。
	 */
	public String getContractViewListPageNo(SearchContractVO searchContractVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get Contract View List Page No", searchContractVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			// contract 记录的条数。
			count = contractDao.getContractViewListPageNo(searchContractVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		sb.append( searchContractVO.getTotalPageNoJson(count) );
		
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * 获取contract 列表中的数据信息。
	 * @param  searchContractVO [description]
	 * @return                  [description]
	 * @throws BPLException     [description]
	 */
	public String searchContractList(SearchContractVO searchContractVO) throws BPLException {

		logger.info(CcmFormat.formatEnterLog("Search Contract View List", searchContractVO));
		StringBuffer sb = new StringBuffer();
		List<String> contractList = null;
		try {
			
			contractList = contractDao.searchContractList(searchContractVO);
			sb.append(searchContractVO.getListJsonCompatible(contractList));
		} catch (Exception e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * 获取 TerminationPenalty 列表分页显示信息。
	 */
	public String getTerminationPenaltyListNo(SearchContractVO searchContractVO) throws BPLException {
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			// TerminationPenalty 记录的条数。
			try {
				count = contractDao.getTerminationPenaltyListNo(searchContractVO);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		sb.append( searchContractVO.getTotalPageNoJson(count) );
		
		return sb.toString();
	}

	/**
	 * 获取 TerminationPenalty 列表中的数据信息。
	 * @param  searchContractVO [description]
	 * @return                  [description]
	 * @throws BPLException     [description]
	 */
	public String searchTerminationPenaltyList(SearchContractVO searchContractVO) throws BPLException {

		StringBuffer sb = new StringBuffer();
		List<String> terminationPenaltyList = null;
		try {
			
			terminationPenaltyList = contractDao.searchTerminationPenaltyList(searchContractVO);
			sb.append(searchContractVO.getListJsonCompatible(terminationPenaltyList));
		} catch (Exception e) {
			
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		return sb.toString();
	}

	/**
	 * 下载contract列表
	 */
	public String downloadContractToExcel(SearchContractVO searchContractVO,String excelDirPath, List<String> titles) throws BPLException {

		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			// 获取当前查询的总条数。
			count = contractDao.getContractViewListPageNo(searchContractVO);
			
			jExcelUtil.createWritableWorkbook(excelDirPath);
			// 设置第一个sheet的名字
			jExcelUtil.createWritableSheet(0, "Contract");
			
			// 将列表中的titles写入到Excel中。
			jExcelUtil.writeTitle(0, titles);
			
			if (count <= recPerPage) {
				searchContractVO.setPageNo(1);
				searchContractVO.setRecPerPage((int) count);
				list = contractDao.downloadContractToExcel(searchContractVO);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchContractVO.setPageNo(j + 1);
					searchContractVO.setRecPerPage(recPerPage);
					list = contractDao.downloadContractToExcel(searchContractVO);
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