package com.saninco.ccm.action.ban;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.service.ban.IBanService;
import com.saninco.ccm.vo.BanVO;

/**
 * @author chao.Liu (Optimization of complete by xinyu.Liu)
 */
public class BanAction extends CcmActionSupport {
	
	private static final long serialVersionUID = -7778316099624206431L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IBanService banService;
	private String lineOfBussiness;
	private String invoiceChannel;
	
	private List<String> titles;
	
	private BanVO bvo = new BanVO();
	
	public BanAction() {
		
	}
	
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		logger.info("Exit method exec.");
		return SUCCESS;
	}
	
	/**
	 * @auchor chao.Liu (ccm/ban/banAdminTab.js)
	 * Get Ban Total Number
	 * 
	 */
	public String getBanTotalNO()throws Exception {
		logger.info("Enter method getBanTotalNO.");
		String result = null;
		try{
			result = banService.getBanTotalNO(bvo);
		}catch(Exception e){
			logger.error("getBanTotalNO error: ", e);
			result = "{error:\"Get Ban Total NO Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getBanTotalNO.");
		return null;
	}
	
	/**
	 * @auchor chao.Liu (ccm/ban/banAdminTab.js)
	 * Search Ban List Info
	 * 
	 */
	public String searchBanList()throws Exception {
		logger.info("Enter method searchBanList.");
		String result = null;
		try{
			result = banService.searchBanList(bvo);
		}catch(Exception e){
			logger.error("searchBanList error: ", e);
			result = "{error:\"Search Ban List Info Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method searchBanList.");
		return null;
	}
	/**
	 * @auchor dongjian (ccm/ban/banAdminTab.js)
	 * Search Ban List Info
	 * 
	 */
	public String getInvoiceChannelAndFormatAndScoaJsonByLineOsBussiness()throws Exception {
		logger.info("Enter method getInvoiceChannelAndFormatJsonByLineOsBussiness.");
		String result = null;
		try{
			result = banService.getInvoiceChannelAndFormatAndScoaJsonByLineOsBussiness(lineOfBussiness);
		}catch(Exception e){
			logger.error("searchBanList error: ", e);
			result = "{error:\"getInvoiceChannel And invoice Format list Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInvoiceChannelAndFormatJsonByLineOsBussiness.");
		return null;
	}
	/**
	 * @auchor dongjian (ccm/ban/banAdminTab.js)
	 * Search Ban List Info
	 * 
	 */
	public String getInvoiceFormatsByLineOsBussinessAndChannel()throws Exception {
		logger.info("Enter method getInvoiceFormatsByLineOsBussinessAndChannel.");
		String result = null;
		try{
			result = banService.getInvoiceFormatsByLineOsBussinessAndChannel(lineOfBussiness,invoiceChannel);
		}catch(Exception e){
			logger.error("searchBanList error: ", e);
			result = "{error:\"getInvoice Format list Error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method getInvoiceFormatsByLineOsBussinessAndChannel.");
		return null;
	}
	
	/**
	 * @auchor chao.Liu (ccm/ban/banAdminTab.js)
	 * DownLoad Ban Excel
	 * 
	 */
	public String saveDownloadBanToExcel()throws Exception {
		logger.info("Enter method saveDownloadBanToExcel.");
		FileInputStream fis = null;
		try {
			String fileName = "banExcel.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = banService.saveDownloadBanToExcel(bvo,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch(Exception e){
			logger.error("saveDownloadBanToExcel error: ", e);
		}
		logger.info("Exit method saveDownloadBanToExcel.");
		return null;
	}
	
	/**
	 * @auchor chao.Liu (ccm/ban/banAdminTab.js)
	 * Delete Ban By Ban Id
	 * 
	 */
	public String deleteBanById()throws Exception {
		logger.info("Enter method deleteBanById.");
		String result = null;
		try{
			result = banService.deleteBanById(bvo);
		}catch(Exception e){
			logger.error("deleteBanById error: ", e);
			result = "{error:\"Delete ban by ban id error!\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method deleteBanById.");
		return null;
	}
	
	public BanVO getBvo() {
		return bvo;
	}
	
	public void setBvo(BanVO bvo) {
		this.bvo = bvo;
	}

	public IBanService getBanService() {
		return banService;
	}

	public void setBanService(IBanService banService) {
		this.banService = banService;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public String getLineOfBussiness() {
		return lineOfBussiness;
	}

	public void setLineOfBussiness(String lineOfBussiness) {
		this.lineOfBussiness = lineOfBussiness;
	}

	public String getInvoiceChannel() {
		return invoiceChannel;
	}

	public void setInvoiceChannel(String invoiceChannel) {
		this.invoiceChannel = invoiceChannel;
	}
	
}
