package com.saninco.ccm.service.vendor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IVendorInventoryDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.JasperReportUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchVendorInventoryVO;

public class VendorInventoryServiceImpl implements IVendorInventoryService {
	private IVendorInventoryDao vendorInventoryDao;
	private final Logger logger = Logger.getLogger(this.getClass());
	private static int DOWNLOAD_EXCEL_RECPERPAGE = 1000;
	private static int DOWNLOAD_EXCEL_MAXSIZE = 60000;
	
	public List<MapEntryVO<String, String>> getConditionProductList() throws BPLException {
		
		logger.info("Enter method getConditionProductList.");
		List<MapEntryVO<String, String>> l = null;
		List productList = null;
		try {
			productList = vendorInventoryDao.getConditionProductList();
			l = new ArrayList<MapEntryVO<String, String>>(productList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object p : productList){
            Object[] product = (Object[])p;
            if(!((product[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)product[0]).toString(), (String)product[1]);
	            l.add(m);
        	}	
        }
		logger.info("Exit method getConditionProductList.");
		return l;
	}
	
	public List<MapEntryVO<String, String>> getConditionAccessList() throws BPLException {
		
		logger.info("Enter method getConditionAccessList.");
		List<MapEntryVO<String, String>> l = null;
		List accessList = null;
		try {
			accessList = vendorInventoryDao.getConditionAccessList();
			l = new ArrayList<MapEntryVO<String, String>>(accessList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object p : accessList){
            Object[] access = (Object[])p;
            if(!((access[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)access[0]).toString(), (String)access[1]);
	            l.add(m);
        	}	
        }
		logger.info("Exit method getConditionAccessList.");
		return l;
	}

	public List<MapEntryVO<String, String>> getConditionBandwidthList() throws BPLException {
	
		logger.info("Enter method getConditionBandwidthList.");
		List<MapEntryVO<String, String>> l = null;
		List bandwidthList = null;
		try {
			bandwidthList = vendorInventoryDao.getConditionBandwidthList();
			l = new ArrayList<MapEntryVO<String, String>>(bandwidthList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	    for(Object p : bandwidthList){
	        Object[] bandwidth = (Object[])p;
	        if(!((bandwidth[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)bandwidth[0]).toString(), (String)bandwidth[1]);
	            l.add(m);
	    	}	
	    }
		logger.info("Exit method getConditionBandwidthList.");
		return l;
	}

	public List<MapEntryVO<String, String>> getConditionQosList() throws BPLException {
		
		logger.info("Enter method getConditionQosList.");
		List<MapEntryVO<String, String>> l = null;
		List qodList = null;
		try {
			qodList = vendorInventoryDao.getConditionQosList();
			l = new ArrayList<MapEntryVO<String, String>>(qodList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	    for(Object p : qodList){
	        Object[] qos = (Object[])p;
	        if(!((qos[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)qos[0]).toString(), (String)qos[1]);
	            l.add(m);
	    	}	
	    }
		logger.info("Exit method getConditionQosList.");
		return l;
	}
	public List<MapEntryVO<String, String>> getConditionCountryList() throws BPLException {
		
		logger.info("Enter method getConditionCountryList.");
		List<MapEntryVO<String, String>> l = null;
		List Country = null;
		try {
			Country = vendorInventoryDao.getConditionCountryList();
			l = new ArrayList<MapEntryVO<String, String>>(Country.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	    for(Object c : Country){
	        Object[] country = (Object[])c;
	        if(!((country[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)country[0]).toString(), (String)country[1]);
	            l.add(m);
	    	}	
	    }
		logger.info("Exit method getConditionCountryList.");
		return l;
	}
	public String getListByProvinceId(int cId) throws BPLException {
		logger.info("Enter method getListByProvinceId.");
		StringBuffer sb = new StringBuffer("[");
		List Province;
		try {
			Province = vendorInventoryDao.getConditionProvinceList(cId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object b : Province){
            Object[] ban = (Object[])b;
			if(sb.length()>1) sb.append(",");
			sb.append("{id:"+((Integer)ban[0]).toString());
			sb.append(",no:\""+(String)ban[1]+"\"}");
        }
		sb.append("]");
		logger.info("Exit method getListByProvinceId.");
		return sb.toString();
	}
	public String getListByCityId(int pId) throws BPLException {
		logger.info("Enter method getListByCityId.");
		StringBuffer sb = new StringBuffer("[");
		List City;
		try {
			City = vendorInventoryDao.getListByCityId(pId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object b : City){
            Object[] ban = (Object[])b;
			if(sb.length()>1) sb.append(",");
			sb.append("{id:"+((Integer)ban[0]).toString());
			sb.append(",no:\""+(String)ban[1]+"\"}");
        }
		sb.append("]");
		logger.info("Exit method getListByCityId.");
		return sb.toString();
	}
	
	
	public List<MapEntryVO<String, String>> getConditionTermList() throws BPLException {
		
		logger.info("Enter method getConditionTermList.");
		List<MapEntryVO<String, String>> l = null;
		List termList = null;
		try {
			termList = vendorInventoryDao.getConditionTermList();
			l = new ArrayList<MapEntryVO<String, String>>(termList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	    for(Object p : termList){
	        Object[] term = (Object[])p;
	        if(!((term[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)term[0]).toString(), (String)term[1]);
	            l.add(m);
	    	}	
	    }
		logger.info("Exit method getConditionTermList.");
		return l;
	}
	public String getInventorySearchTotalPageNo(SearchVendorInventoryVO sv) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Inventory Search Total Page No", sv));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = vendorInventoryDao.getNumberOfVendorInventory(sv);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		//Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double)count / (double)sv.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	public String searchVendorInventory(SearchVendorInventoryVO sv) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Vendor Inventory View List", sv));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = vendorInventoryDao.searchVendorInventory(sv);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(l!=null&&l.size()>0){
			sb.append("{begin:");
			sb.append(sv.getStartIndex()+1);
			sb.append(",end:");
			int size = l.size();
			sb.append(sv.getStartIndex()+size);
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
	
	public String createVendorInventoryToExcel(SearchVendorInventoryVO sv,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			int pageNumber = 0;
			long count = vendorInventoryDao.getNumberOfVendorInventory(sv);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "Circuit Details");
			jExcelUtil.writeTitle(0, titles);
			if (count <= DOWNLOAD_EXCEL_RECPERPAGE) {
				if (sv.getRecPerPage() == 0) {
					// if download all
					sv.setPageNo(1);
					sv.setRecPerPage((int) count);
				}
				list = vendorInventoryDao.getVendorInventoryDataForExcel(sv);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for (int j = 0; j < totlePage; j++) {
					sv.setPageNo(j + 1);
					sv.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					list = vendorInventoryDao.getVendorInventoryDataForExcel(sv);
					
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
	
	public String createVendorInventoryToLetter(SearchVendorInventoryVO sv,String pdfDirPath, String jasperPath, List<String> tem) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Map> list = vendorInventoryDao.getVendorInventoryDataForLetter(sv);
			Map parameter = new HashMap();
			parameter.put("top_image_path",jasperPath+"rogers_pdf_logo.png");
			JasperReportUtil.pdfExport(pdfDirPath, jasperPath, tem , parameter, list);
		} catch (Throwable e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException( ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return pdfDirPath;
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
	
	public IVendorInventoryDao getVendorInventoryDao() {
		return vendorInventoryDao;
	}

	public void setVendorInventoryDao(IVendorInventoryDao vendorInventoryDao) {
		this.vendorInventoryDao = vendorInventoryDao;
	}
}