package com.saninco.ccm.service.message;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IMessageCenterDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchMessageCenterVO;
import com.saninco.ccm.vo.SearchVendorInventoryVO;

public class MessageCenterServiceImpl implements IMessageCenterService{
	private final Logger logger = Logger.getLogger(this.getClass());
	private IMessageCenterDao messageCenterDao;
	private static int DOWNLOAD_EXCEL_RECPERPAGE = 1000;
	private static int DOWNLOAD_EXCEL_MAXSIZE = 60000;
	public List<MapEntryVO<String, String>> getMessageReferenceList() throws BPLException {
		
		logger.info("Enter method getMessageReferenceList.");
		List<MapEntryVO<String, String>> l = null;
		List ReferenceList = null;
		try {
			ReferenceList = messageCenterDao.getMessageReferenceList();
			l = new ArrayList<MapEntryVO<String, String>>(ReferenceList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	    for(Object p : ReferenceList){
	        Object[] term = (Object[])p;
	        if(!((term[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)term[0]).toString(), (String)term[1]);
	            l.add(m);
	    	}	
	    }
		logger.info("Exit method getMessageReferenceList.");
		return l;
	}
	
	public List<MapEntryVO<String, String>> getMessageCreatedByList() throws BPLException {
		
		logger.info("Enter method getMessageCreatedByList.");
		List<MapEntryVO<String, String>> l = null;
		List CreatedBy = null;
		try {
			CreatedBy = messageCenterDao.getMessageCreatedByList();
			l = new ArrayList<MapEntryVO<String, String>>(CreatedBy.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	    for(Object p : CreatedBy){
	        Object[] term = (Object[])p;
	   //     if(!((term[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)term[0]).toString(), (String)term[1]);
	            l.add(m);
	    //	}	
	    }
		logger.info("Exit method getMessageCreatedByList.");
		return l;
	}
	//search count 
	public String getMessageCenterSearchTotalPageNo(SearchMessageCenterVO sv) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Message Search Total Page No", sv));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = messageCenterDao.getNumberOfMessageCenter(sv,SystemUtil.getCurrentUserId());
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
	//search
	public String searchMessageCenter(SearchMessageCenterVO sv) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("search Message View List", sv));
		StringBuffer sb = new StringBuffer();
		List<Object[]> l = null;
		try {
			l = messageCenterDao.searchMessageCenter(sv,SystemUtil.getCurrentUserId());
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
	
	public String createMessageCenterToExcel(SearchMessageCenterVO sv,String excelDirPath, List<String> titles) throws BPLException {
		logger.info(CcmFormat.formatEnterLog(""));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			int pageNumber = 0;
			long count = messageCenterDao.getNumberOfMessageCenter(sv,SystemUtil.getCurrentUserId());
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "Message Center");
			jExcelUtil.writeTitle(0, titles);
			if (count <= DOWNLOAD_EXCEL_RECPERPAGE) {
				if (sv.getRecPerPage() == 0) {
					// if download all
					sv.setPageNo(1);
					sv.setRecPerPage((int) count);
				}
				list = messageCenterDao.getMessageCenterDataForExcel(sv,SystemUtil.getCurrentUserId());
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for (int j = 0; j < totlePage; j++) {
					sv.setPageNo(j + 1);
					sv.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					list = messageCenterDao.getMessageCenterDataForExcel(sv,SystemUtil.getCurrentUserId());
					
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
	public void updatMessageFavoriteFlag(int id,String favoriteFlag) throws BPLException{
		logger.info("Enter method updatMessageFavoriteFlag.");
		try {
			messageCenterDao.updatMessageFavoriteFlag(id,favoriteFlag,SystemUtil.getCurrentUserId());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method updatMessageFavoriteFlag.");
	}
	
	
	
	
	
	
	
	public IMessageCenterDao getMessageCenterDao() {
		return messageCenterDao;
	}

	public void setMessageCenterDao(IMessageCenterDao messageCenterDao) {
		this.messageCenterDao = messageCenterDao;
	}


	
}
