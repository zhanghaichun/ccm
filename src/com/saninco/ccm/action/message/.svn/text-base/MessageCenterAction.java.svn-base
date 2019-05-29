package com.saninco.ccm.action.message;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.message.IMessageCenterService;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchMessageCenterVO;
import com.saninco.ccm.vo.SearchVendorInventoryVO;

public class MessageCenterAction extends CcmActionSupport{
	private IMessageCenterService messageCenterService;
	private List<MapEntryVO<String, String>> ReferenceList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> CreatedByList = new ArrayList<MapEntryVO<String, String>>();
	private SearchMessageCenterVO searchMessageCenterVO = new SearchMessageCenterVO();
	private List<String> titles;
	private Integer messageId;
	private String favoriteFlag;
	
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		this.ReferenceList = messageCenterService.getMessageReferenceList();
		this.CreatedByList = messageCenterService.getMessageCreatedByList();
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
//search count() 
	public String getMessageCenterSearchTotalPageNo() throws Exception{
		logger.info("Enter method getMessageCenterSearchTotalPageNo.");
		String result = null;
		try{
			result = messageCenterService.getMessageCenterSearchTotalPageNo(searchMessageCenterVO);
		}catch(Exception e){
			logger.error("getMessageCenterSearchTotalPageNo error: ", e);
			result = "{error:\"getMessageCenterSearchTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getMessageCenterSearchTotalPageNo.");
		return null;
	}
//search	
	public String searchMessageCenter()throws Exception {
		logger.info("Enter method searchMessageCenter.");
		String result = null;
		try{
			result = messageCenterService.searchMessageCenter(searchMessageCenterVO);
		}catch(Exception e){
			logger.error("searchMessageCenter error: ", e);
			result = "{error:\"searchMessageCenter: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchMessageCenter.");
		return null;
	}
	
	
	
	public String doDownloadMessageCenterCsv()throws Exception{
		logger.info("Enter method doDownloadMessageCenterCsv.");
		FileInputStream fis = null;
		try {
			String fileName = "MessageCenter.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = messageCenterService.createMessageCenterToExcel(searchMessageCenterVO,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("doDownloadMessageCenterCsv error: ", e);
		}
		logger.info("Exit method doDownloadMessageCenterCsv.");
		return null;
	}
	
    public void updatMessageFavoriteFlag() throws Exception{
    	logger.info("Enter method updatMessageFavoriteFlag.");
    	String result = null;
		try {
			messageCenterService.updatMessageFavoriteFlag(messageId,favoriteFlag);
			result = "success";
		}catch (Throwable e) {
			result = "{error:\"updatMessageFavoriteFlag: "+e.getMessage()+"\"}";
			
		}	
		this.writeOutputStream(result);
    	logger.info("Exit method updatMessageFavoriteFlag.");
    }
    
    
	public List<MapEntryVO<String, String>> getReferenceList() {
		return ReferenceList;
	}

	public void setReferenceList(List<MapEntryVO<String, String>> referenceList) {
		ReferenceList = referenceList;
	}

	public IMessageCenterService getMessageCenterService() {
		return messageCenterService;
	}

	public void setMessageCenterService(IMessageCenterService messageCenterService) {
		this.messageCenterService = messageCenterService;
	}

	public List<MapEntryVO<String, String>> getCreatedByList() {
		return CreatedByList;
	}

	public void setCreatedByList(List<MapEntryVO<String, String>> createdByList) {
		CreatedByList = createdByList;
	}

	public SearchMessageCenterVO getSearchMessageCenterVO() {
		return searchMessageCenterVO;
	}

	public void setSearchMessageCenterVO(SearchMessageCenterVO searchMessageCenterVO) {
		this.searchMessageCenterVO = searchMessageCenterVO;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public Integer getMessageId() {
		return messageId;
	}

	public void setMessageId(Integer messageId) {
		this.messageId = messageId;
	}

	public String getFavoriteFlag() {
		return favoriteFlag;
	}

	public void setFavoriteFlag(String favoriteFlag) {
		this.favoriteFlag = favoriteFlag;
	}

}
