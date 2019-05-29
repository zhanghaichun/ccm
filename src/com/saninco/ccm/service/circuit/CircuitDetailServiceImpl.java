package com.saninco.ccm.service.circuit;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.ICircuitDetailDao;
import com.saninco.ccm.dao.ITariffLinkDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Contract;
import com.saninco.ccm.model.Tariff;
import com.saninco.ccm.model.TariffLink;
import com.saninco.ccm.model.VendorCircuit;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.vo.SearchCircuitVO;

public class CircuitDetailServiceImpl implements ICircuitDetailService  {
	
	private ICircuitDetailDao circuitDetailDao;
	private ITariffLinkDao tariffLinkDao;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	public VendorCircuit findVendorCircuitById(Integer vendorCircuitId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice View List", vendorCircuitId));
		VendorCircuit vendorCircuit = null;
		try {
			vendorCircuit = circuitDetailDao.findVendorCircuitById(vendorCircuitId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return vendorCircuit;
	}
	
	public VendorCircuit findCircuitDetail(String proposalId) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("search Vendor Circuit Detail By invoice id", proposalId));
		VendorCircuit vendorCircuit = null;
		Integer vendorCircuitId = null;
		try {
			
			vendorCircuitId = circuitDetailDao.findVendorCircuitId(proposalId);
			if(vendorCircuitId != null){
				vendorCircuit = circuitDetailDao.findVendorCircuitById(vendorCircuitId);
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return vendorCircuit;
	}
	
	public String searchSCOATotalPageNo(SearchCircuitVO searchCircuitVO)
		throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Circuit SCOA Total Page No", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = circuitDetailDao.searchSCOATotalPageNo(searchCircuitVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)searchCircuitVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public String searchSCOAList(SearchCircuitVO searchCircuitVO)
			throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice View List", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = circuitDetailDao.searchSCOAList(searchCircuitVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(searchCircuitVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchCircuitVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if(i!=0) sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	public String searchCostTotalPageNo(SearchCircuitVO searchCircuitVO)
	throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Cost Total Page No", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = circuitDetailDao.searchCostTotalPageNo(searchCircuitVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)searchCircuitVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String searchCostList(SearchCircuitVO searchCircuitVO)
	throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Cost List", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = circuitDetailDao.searchCostList(searchCircuitVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(searchCircuitVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchCircuitVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if(i!=0) sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public String searchTariffTotalPageNo(SearchCircuitVO searchCircuitVO)
	throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Tariff Total Page No", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = circuitDetailDao.searchTariffTotalPageNo(searchCircuitVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)searchCircuitVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	public String doSearchMappingPageNo(SearchCircuitVO searchCircuitVO)
	throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Mapping Total Page No", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = circuitDetailDao.doSearchMappingPageNo(searchCircuitVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)searchCircuitVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	public String doSearchMappingList(SearchCircuitVO searchCircuitVO)
	throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Mapping List", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {			
			l = circuitDetailDao.doSearchMappingList(searchCircuitVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(searchCircuitVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchCircuitVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if(i!=0) sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	public String searchTariffList(SearchCircuitVO searchCircuitVO)
	throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Tariff List", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {			
			l = circuitDetailDao.searchTariffList(searchCircuitVO);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(searchCircuitVO.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchCircuitVO.getStartIndex()+size);
			sb.append(",data:[");
			for(int i = 0; i<size; i++){
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if(i!=0) sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		}else{
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	private String getBlobContent(Blob b) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedInputStream bis = new BufferedInputStream(b.getBinaryStream());
			int len = (int) b.length();
			byte[] bt = new byte[len];
			int readLen = 0;
			while ((readLen = bis.read(bt)) != -1) {
				sb.append(new String(bt, 0, readLen));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
	
	public void deleteTariffOfVendorCircuit(TariffLink tariffLink) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("deleteAttachmentFile"));
		try {
			tariffLinkDao.deleteTariffLinkAndFile(tariffLink);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	public String createScoaToExcel(SearchCircuitVO svo,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "SCOA");
			jExcelUtil.writeTitle(0, titles);
			List<Object[]> list = circuitDetailDao.getSCOADataForExcel(svo);
			for (int i = 0; i < list.size(); i++) {
				jExcelUtil.writeLine(i + 1, list.get(i));
			}
			jExcelUtil.setColumnView(new int[] { 30,30,30,30,30,30,30,30,30,30,30,30,30,30});
			jExcelUtil.write();
			jExcelUtil.close();
			jExcelUtil = null;
			list = null;
			System.gc();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	
	public String createCostToExcel(SearchCircuitVO svo,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "Cost History");
			jExcelUtil.writeTitle(0, titles);
			List<Object[]> list = circuitDetailDao.getCostDataForExcel(svo);
			for (int i = 0; i < list.size(); i++) {
				jExcelUtil.writeLine(i + 1, list.get(i));
			}
			jExcelUtil.setColumnView(new int[] { 30,30,30,30,30,30,30,30,30,30,30,30,30,30});
			jExcelUtil.write();
			jExcelUtil.close();
			jExcelUtil = null;
			list = null;
			System.gc();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	
	public String createTariffToExcel(SearchCircuitVO svo,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "Tariff & Contract");
			jExcelUtil.writeTitle(0, titles);
			List<Object[]> list = circuitDetailDao.getTariffDataForExcel(svo);
			for (int i = 0; i < list.size(); i++) {
				jExcelUtil.writeLine(i + 1, list.get(i));
			}
			jExcelUtil.setColumnView(new int[] { 30,30,30,30,30,30,30,30,30,30,30,30,30,30});
			jExcelUtil.write();
			jExcelUtil.close();
			jExcelUtil = null;
			list = null;
			System.gc();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	
	public void setCircuitDetailDao(ICircuitDetailDao circuitDetailDao) {
		this.circuitDetailDao = circuitDetailDao;
	}

	public void setTariffLinkDao(ITariffLinkDao tariffLinkDao) {
		this.tariffLinkDao = tariffLinkDao;
	}

	public List<HashMap<String, String>> doSearchLogicalPathList(Integer vendorCircuitId)
	throws BPLException {
		StringBuffer sb = new StringBuffer();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		List<Object[]> l = null;
		try {
			l = circuitDetailDao.doSearchLogicalPathList(vendorCircuitId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		for(int i=0 ; i<l.size();i++){
			Object[] o = l.get(i);
			HashMap<String,String> m = new HashMap<String,String>();
			m.put("logical_path_circuit_id", o[0].toString());
			m.put("logical_path_circuit_number", o[1].toString());
			m.put("circuit_type_name", o[2].toString());
			list.add(m);
		}
		
		logger.info(CcmFormat.formatExitLog());
		return list;
		
	}
	public Tariff doSearchTariff(Integer id) throws BPLException{
		logger.info(CcmFormat.formatEnterLog(""));
		Tariff tariff = null;
		try{
		    tariff = circuitDetailDao.doSearchTariff(id);
		}catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return tariff;
		
	}
	public Contract SearchContract(Integer id) throws BPLException{
		logger.info(CcmFormat.formatEnterLog(""));
		Contract contract = null;
		try{
			contract = circuitDetailDao.SearchContract(id);
		}catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return contract;
		
	}
}
