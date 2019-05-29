package com.saninco.ccm.service.circuit;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.hibernate.lob.SerializableBlob;

import com.saninco.ccm.dao.ICircuitDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.BlobUtil;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchCircuitVO;

public class CircuitServiceImpl implements ICircuitService  {
	private ICircuitDao circuitDao;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private static int DOWNLOAD_EXCEL_RECPERPAGE = 1000;
	private static int DOWNLOAD_EXCEL_MAXSIZE = 60000;
	
	public CircuitServiceImpl() {
	}
	
	public String searchCircuit(SearchCircuitVO searchCircuitVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Invoice View List", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = circuitDao.searchCircuit(searchCircuitVO, SystemUtil.getCurrentUserId());
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
	public String getCircuitsSearchTotalPageNo(SearchCircuitVO searchCircuitVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Circuit Search Total Page No", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = circuitDao.getNumberOfCircuits(searchCircuitVO, SystemUtil.getCurrentUserId());
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
	
	public String getDataToExcel(SearchCircuitVO svo,String sheetName, String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, sheetName);
			jExcelUtil.writeTitle(0, titles);
			List<Object[]> list = circuitDao.getCircuitDataForExcel(svo, SystemUtil.getCurrentUserId());
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
	public String getAllDataToExcel(SearchCircuitVO svo, String excelDirPath, List<String> titles) throws BPLException{
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			JExcelUtil jExcelUtil = new JExcelUtil();
			jExcelUtil.createWritableWorkbook(excelDirPath);
			svo.setFilter(null);	// clear filter
			Map<String, Integer> pageOffset = new HashMap<String, Integer>();
			pageOffset.put("offset", Integer.valueOf(0));
			writeToExcel(svo,0,"All",jExcelUtil, titles, pageOffset);
			svo.setItemType("MRC");
			writeToExcel(svo,1,"MRC",jExcelUtil, titles, pageOffset);
			svo.setItemType("OCC");
			writeToExcel(svo,2,"OCC",jExcelUtil, titles, pageOffset);
			svo.setItemType("Usage");
			writeToExcel(svo,3,"Usage",jExcelUtil, titles, pageOffset);
			svo.setItemType("Tax");
			writeToExcel(svo,4,"Tax",jExcelUtil, titles, pageOffset);
			svo.setItemType("Adjustment");
			writeToExcel(svo,5,"Adjustment",jExcelUtil, titles, pageOffset);
			svo.setItemType("Credit");
			writeToExcel(svo,6,"Credit",jExcelUtil, titles, pageOffset);
			svo.setItemType("Previous Adjustment");
			writeToExcel(svo,7,"Previous Adjustment",jExcelUtil, titles, pageOffset);
			svo.setItemType("LPC");
			writeToExcel(svo,8,"LPC",jExcelUtil, titles, pageOffset);
			svo.setItemType("Payment");
			writeToExcel(svo,9,"Payment",jExcelUtil, titles, pageOffset);
			jExcelUtil.write();
			jExcelUtil.close();
			jExcelUtil = null;
			System.gc();
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	private void writeToExcel(SearchCircuitVO svo,int index,String sheetName,JExcelUtil jExcelUtil, List<String> titles, Map<String, Integer> pageOffset)throws BPLException{
		int pageNumber = 0;
		try {
			List<Object[]> list = null;
			int sheetIndex = index + pageOffset.get("offset");
			long count = 0;
			count = circuitDao.getNumberOfCircuits(svo, SystemUtil.getCurrentUserId());
			if(count >0 ){
				jExcelUtil.createWritableSheet(sheetIndex, sheetName);
				jExcelUtil.setWritableSheet(sheetIndex);
				jExcelUtil.writeTitle(0, titles);
				if (count <= DOWNLOAD_EXCEL_RECPERPAGE) {
					svo.setPageNo(1);
					svo.setRecPerPage((int) count);
					list = circuitDao.getCircuitDataForExcel(svo, SystemUtil.getCurrentUserId());
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(i + 1, list.get(i));
					}
				} else {
					long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
					for (int j = 0; j < totlePage; j++) {
						svo.setPageNo(j + 1);
						svo.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
						pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
						list = circuitDao.getCircuitDataForExcel(svo, SystemUtil.getCurrentUserId());
						if(pageNumber > 0){
							if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
								jExcelUtil.setColumnView(new int[]{30,30,30,30,40,40,30,30,30,30,50,30,30,30,30,30,30,30,30,30,30,30,30,30});
								jExcelUtil.createWritableSheet(sheetIndex + pageNumber, sheetName + (pageNumber + 1));
								jExcelUtil.setWritableSheet(sheetIndex + pageNumber);
								jExcelUtil.writeTitle(0, titles);
							}
							for(int i = 0; i < list.size(); i++){
								jExcelUtil.writeLine((j * DOWNLOAD_EXCEL_RECPERPAGE + i + 1) - (DOWNLOAD_EXCEL_MAXSIZE * pageNumber), list.get(i)); 
							}
						}else{
							for(int i = 0; i < list.size(); i++){
								jExcelUtil.writeLine(j * DOWNLOAD_EXCEL_RECPERPAGE + i + 1, list.get(i));
							}
						}
					}
				}
				jExcelUtil.setColumnView(new int[] { 30,30,30,30,30,30,30,30,40,30,30,30,30,50});
			}
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		pageOffset.put("offset", pageOffset.get("offset")+ pageNumber);
	}
	public String getTabs(SearchCircuitVO svo)throws BPLException{
		logger.info("Enter method getTabs.");
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
		l=circuitDao.getTabs(svo,  SystemUtil.getCurrentUserId());
		sb.append(getListJson(l));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getTabs.");
		return sb.toString();
	}
	public String getCircuitDateHyperlink(SearchCircuitVO svo)throws BPLException{
		logger.info("Enter method getCircuitDateHyperlink.");
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
		l=circuitDao.getCircuitDateHyperlink(svo,  SystemUtil.getCurrentUserId());
		sb.append(getListJson(l));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getCircuitDateHyperlink.");
		return sb.toString();
	}
	private String getListJson(List l){
		StringBuilder sb = new StringBuilder();
		if (l != null && l.size() > 0 ) {
			int size = l.size();
			sb.append("{data:[");
			for (int i = 0; i < size; i++) {
				Object str = l.get(i);
				if (str == null) {
					break;
				} else {
					if (str instanceof SerializableBlob) {
						str = BlobUtil.getBlobString((SerializableBlob) str);
					}
					if (i != 0)
						sb.append(",");
					sb.append(str.toString());
				}
			}
			sb.append("]");
		} else {
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		return sb.toString();
	}
	public String searchVendorCircuit(SearchCircuitVO searchCircuitVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Vendor Circuit View List", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = circuitDao.searchVendorCircuit(searchCircuitVO, SystemUtil.getCurrentUserId());
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
	
	public String getVendorCircuitSearchTotalPageNo(SearchCircuitVO searchCircuitVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Vendor Circuit Search Total Page No", searchCircuitVO));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = circuitDao.getNumberOfVendorCircuits(searchCircuitVO, SystemUtil.getCurrentUserId());
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
	
	public List searchReportDownloadNumAndMaxNum(String param){
		return circuitDao.searchReportDownloadNumAndMaxNum(param);
	}
	
	public void updateReportDownloadNum(Boolean finishFlag){
		circuitDao.updateReportDownloadNum(finishFlag);
	}
	
	public String createVendorCircuitToExcel(SearchCircuitVO svo,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			int pageNumber = 0;
			long count = circuitDao.getNumberOfVendorCircuits(svo, SystemUtil.getCurrentUserId());
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "Circuit Details");
			jExcelUtil.writeTitle(0, titles);
			if (count <= DOWNLOAD_EXCEL_RECPERPAGE) {
				if (svo.getRecPerPage() == 0) {
					// if download all
					svo.setPageNo(1);
					svo.setRecPerPage((int) count);
				}
				list = circuitDao.getVendorCircuitDataForExcel(svo);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for (int j = 0; j < totlePage; j++) {
					svo.setPageNo(j + 1);
					svo.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					list = circuitDao.getVendorCircuitDataForExcel(svo);
					
					if(pageNumber > 0){
						if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
							jExcelUtil.setColumnView(new int[]{30,30,30,30,40,40,30,30,30,30,50,30,30,30,30,30,30,30,30,30,30,30,30,30});
							jExcelUtil.createWritableSheet(pageNumber, "Circuit Details" + (pageNumber + 1));
							jExcelUtil.setWritableSheet(pageNumber);
							jExcelUtil.writeTitle(0, titles);
						}
						for(int i = 0; i < list.size(); i++){
							jExcelUtil.writeLine((j * DOWNLOAD_EXCEL_RECPERPAGE + i + 1) - (DOWNLOAD_EXCEL_MAXSIZE * pageNumber), list.get(i)); 
						}
					}else{
						for(int i = 0; i < list.size(); i++){
							jExcelUtil.writeLine(j * DOWNLOAD_EXCEL_RECPERPAGE + i + 1, list.get(i));
						}
					}
				}
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
	public ICircuitDao getCircuitDao() {
		return circuitDao;
	}

	public void setCircuitDao(ICircuitDao circuitDao) {
		this.circuitDao = circuitDao;
	}
	
}
