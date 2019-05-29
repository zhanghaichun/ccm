package com.saninco.ccm.action.circuit;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.Contract;
import com.saninco.ccm.model.Tariff;
import com.saninco.ccm.model.TariffLink;
import com.saninco.ccm.model.VendorCircuit;
import com.saninco.ccm.service.circuit.ICircuitDetailService;
import com.saninco.ccm.vo.SearchCircuitVO;
import com.saninco.ccm.vo.SearchInvoiceVO;

public class CircuitDetailAction extends CcmActionSupport{
	
	private static final long serialVersionUID = -4144323307345549784L;
	
	private ICircuitDetailService circuitDetailService;
	private VendorCircuit vendorCircuit = new VendorCircuit();
	private SearchCircuitVO searchCircuitVO = new SearchCircuitVO();
	private Integer vendorCircuitId;
	private TariffLink tariffLink = new TariffLink();
	private Tariff tariff = new Tariff();	
	private Contract contract = new Contract();	
	
	private List<String> titles;
	
	private List<HashMap<String, String>> logicalPathList;
	private Map<String, Object> Attachment = new HashMap<String, Object>();;
	
	private List<File> uploads;
	private List<String> uploadsFileName;
	private String uploadNotes;
	private String proposalId;
	private Integer tfID;
	private SearchInvoiceVO svo;
	
	public String exec() throws Exception {
		logger.info("Enter method exec");
		vendorCircuit = circuitDetailService.findVendorCircuitById(vendorCircuitId);
		doSearchLogicalPathList();
		logger.info("Exit method exec");
		return SUCCESS;
	}
	
	public String findCircuitDetail() throws Exception{
		logger.info("Enter method findCircuitDetail.");
		try{
			vendorCircuit = circuitDetailService.findCircuitDetail(proposalId);
			if(vendorCircuit != null && !"".equals(vendorCircuit)){
				vendorCircuitId = vendorCircuit.getId();
				doSearchLogicalPathList();
			}
		}catch(Exception e){
			logger.error("findCircuitDetail error: ", e);
		}
		logger.info("Exit method findCircuitDetail.");
		return SUCCESS;
	}
	
	public String doSearchSCOATotalPageNo() throws Exception{
		logger.info("Enter method doSearchSCOATotalPageNo.");
		String result = null;
		try{
			result = circuitDetailService.searchSCOATotalPageNo(searchCircuitVO);
		}catch(Exception e){
			logger.error("doSearchSCOATotalPageNo error: ", e);
			result = "{error:\"doSearchSCOATotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchSCOATotalPageNo.");
		return null;
	}
	public String doSearchSCOAList()throws Exception {
		logger.info("Enter method doSearchSCOAList.");
		String result = null;
		try{
			result = circuitDetailService.searchSCOAList(searchCircuitVO);
		}catch(Exception e){
			logger.error("doSearchSCOAList error: ", e);
			result = "{error:\"doSearchSCOAList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchSCOAList.");
		return null;
	}
	
	public String doDownloadScoaCsv()throws Exception{
		logger.info("Enter method doDownloadScoaCsv.");
		FileInputStream fis = null;
		try {
			String fileName = "CircuitSCOA.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = circuitDetailService.createScoaToExcel(searchCircuitVO,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("doDownloadScoaCsv error: ", e);
		}
		logger.info("Exit method doDownloadScoaCsv.");
		return null;
	}
	
	public String doSearchCostTotalPageNo() throws Exception{
		logger.info("Enter method doSearchCostTotalPageNo.");
		String result = null;
		try{
			result = circuitDetailService.searchCostTotalPageNo(searchCircuitVO);
		}catch(Exception e){
			logger.error("doSearchCostTotalPageNo error: ", e);
			result = "{error:\"doSearchCostTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchCostTotalPageNo.");
		return null;
	}
	public String doSearchCostList()throws Exception {
		logger.info("Enter method doSearchCostList.");
		String result = null;
		try{
			result = circuitDetailService.searchCostList(searchCircuitVO);
		}catch(Exception e){
			logger.error("doSearchCostList error: ", e);
			result = "{error:\"doSearchCostList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchCostList.");
		return null;
	}
	
	public String doDownloadCostCsv()throws Exception{
		logger.info("Enter method doDownloadCostCsv.");
		FileInputStream fis = null;
		try {
			String fileName = "CostHistory.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = circuitDetailService.createCostToExcel(searchCircuitVO,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("doDownloadCostCsv error: ", e);
		}
		logger.info("Exit method doDownloadCostCsv.");
		return null;
	}
	
	public String doDownloadTariffCsv()throws Exception{
		logger.info("Enter method doDownloadTariffCsv.");
		FileInputStream fis = null;
		try {
			String fileName = "Tariff&Contract.xls";
			String excelDirPath = request.getSession().getServletContext().getRealPath(File.separator + Thread.currentThread().getName()+"-"+fileName);
			String excelPath = circuitDetailService.createTariffToExcel(searchCircuitVO,excelDirPath,titles);
			fis = new FileInputStream(excelPath);
			writeFile(fileName,fis);
		}catch (Throwable e) {
			logger.error("doDownloadTariffCsv error: ", e);
		}
		logger.info("Exit method doDownloadTariffCsv.");
		return null;
	}
	
	public String doSearchTariffTotalPageNo() throws Exception{
		logger.info("Enter method doSearchTariffTotalPageNo.");
		String result = null;
		try{
			result = circuitDetailService.searchTariffTotalPageNo(searchCircuitVO);
		}catch(Exception e){
			logger.error("doSearchTariffTotalPageNo error: ", e);
			result = "{error:\"doSearchTariffTotalPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchTariffTotalPageNo.");
		return null;
	}
	//doSearchMapingPageNo
	public String doSearchMappingPageNo() throws Exception{
		logger.info("Enter method doSearchMapingPageNo.");
		String result = null;
		try{
			result = circuitDetailService.doSearchMappingPageNo(searchCircuitVO);
		}catch(Exception e){
			logger.error("doSearchMapingPageNo error: ", e);
			result = "{error:\"doSearchMapingPageNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchMapingPageNo.");
		return null;
	}
	//doSearchMappingList
	public String doSearchMappingList()throws Exception {
		logger.info("Enter method doSearchMappingList.");
		String result = null;
		try{
			result = circuitDetailService.doSearchMappingList(searchCircuitVO);
		}catch(Exception e){
			logger.error("doSearchMappingList error: ", e);
			result = "{error:\"doSearchMappingList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchMappingList.");
		return null;
	}
	public String doSearchTariffList()throws Exception {
		logger.info("Enter method doSearchTariffList.");
		String result = null;
		try{
			result = circuitDetailService.searchTariffList(searchCircuitVO);
		}catch(Exception e){
			logger.error("doSearchTariffList error: ", e);
			result = "{error:\"doSearchTariffList: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);	
		logger.info("Exit method doSearchTariffList.");
		return null;
	}
	
	private String doSearchLogicalPathList()throws Exception {
		logger.info("Enter method doSearchLogicalPathList.");
		try{
			logicalPathList = circuitDetailService.doSearchLogicalPathList(vendorCircuitId);
		}catch(Exception e){
			logger.error("doSearchLogicalPathList error: ", e);
		}
		logger.info("Exit method doSearchLogicalPathList.");
		return null;
	}
	
	public String doUploadAttachFile() {
		logger.info("Enter method doUploadAttachFile.");
		try {
			
			TariffLink parmas = new TariffLink();
			parmas.setCircuitId(vendorCircuitId);
			parmas.setNotes(uploadNotes);
			commonLookupService.saveUploadFileToTariffLink(uploads,uploadsFileName, parmas);
			
		} catch (Exception e) {
			logger.error("doUploadAttachFile error: ", e);
			return INPUT;
		}
		logger.info("Exit method doUploadAttachFile.");
		return SUCCESS;
	}
	
	public String deleteTariffOfVendorCircuit() {
		logger.info("Enter method deleteTariffOfVendorCircuit.");
		try {
			circuitDetailService.deleteTariffOfVendorCircuit(tariffLink);
		} catch (Exception e) {
			logger.error("deleteTariffOfVendorCircuit error: ", e);
			return INPUT;
		}
		logger.info("Exit method deleteTariffOfVendorCircuit.");
		return SUCCESS;
	}
	public String doSearchTariff() throws Exception{
		logger.info("Enter method doSearchTariff.");
		List<AttachmentFile> list =new ArrayList<AttachmentFile>();		
		List result = null;
		svo=new SearchInvoiceVO();
		try{
			 tariff = circuitDetailService.doSearchTariff(tfID);
			 if(tariff.getAttachment_point_id()==null){
				 logger.info("Exit method doSearchTariff.");
				 return SUCCESS;
			 }else{
			 
			 svo.setBoxInId(tariff.getAttachment_point_id()+""); 
			 result = commonLookupService.searchAttachmentPointIdList(svo);
			  for(int i=0;i<result.size();i++){
				  AttachmentFile af = new AttachmentFile();
				  Object[] obj = (Object[])result.get(i); 
				  af.setFileName(obj[0].toString());
				  af.setFilePath(obj[1].toString());
                   list.add(af);             	
	         }
			  Attachment.put("file", list);	
			 }

		}catch(Exception e){
			logger.error("doSearchTariff error: ", e);
		}
		logger.info("Exit method doSearchTariff.");
		return SUCCESS;
	}
	public String SearchContract() throws Exception{
		logger.info("Enter method SearchContract.");
		List<AttachmentFile> list =new ArrayList<AttachmentFile>();		
		List result = null;
		svo=new SearchInvoiceVO();
		try{
			contract = circuitDetailService.SearchContract(tfID);
			 if(contract.getAttachmentPointId()==null){
				 logger.info("Exit method SearchContract.");
				 return SUCCESS;
			 }else{
			 
			 svo.setBoxInId(contract.getAttachmentPointId()+""); 
			 result = commonLookupService.searchAttachmentPointIdList(svo);
			  for(int i=0;i<result.size();i++){
				  AttachmentFile af = new AttachmentFile();
				  Object[] obj = (Object[])result.get(i); 
				  af.setFileName(obj[0].toString());
				  af.setFilePath(obj[1].toString());
                   list.add(af);             	
	         }
			  Attachment.put("file", list);	
			 }

		}catch(Exception e){
			logger.error("SearchContract error: ", e);
		}
		logger.info("Exit method doSearchTariff.");
		return SUCCESS;
	}
	public Integer getVendorCircuitId() {
		return vendorCircuitId;
	}

	public void setVendorCircuitId(Integer vendorCircuitId) {
		this.vendorCircuitId = vendorCircuitId;
	}

	public VendorCircuit getVendorCircuit() {
		return vendorCircuit;
	}

	public void setCircuitDetailService(ICircuitDetailService circuitDetailService) {
		this.circuitDetailService = circuitDetailService;
	}

	public SearchCircuitVO getSearchCircuitVO() {
		return searchCircuitVO;
	}

	public void setSearchCircuitVO(SearchCircuitVO searchCircuitVO) {
		this.searchCircuitVO = searchCircuitVO;
	}

	public List<File> getUploads() {
		return uploads;
	}

	public void setUploads(List<File> uploads) {
		this.uploads = uploads;
	}

	public List<String> getUploadsFileName() {
		return uploadsFileName;
	}

	public void setUploadsFileName(List<String> uploadsFileName) {
		this.uploadsFileName = uploadsFileName;
	}

	public String getUploadNotes() {
		return uploadNotes;
	}

	public void setUploadNotes(String uploadNotes) {
		this.uploadNotes = uploadNotes;
	}

	public String getProposalId() {
		return proposalId;
	}

	public void setProposalId(String proposalId) {
		this.proposalId = proposalId;
	}

	public void setTariffLink(TariffLink tariffLink) {
		this.tariffLink = tariffLink;
	}

	public TariffLink getTariffLink() {
		return tariffLink;
	}

	public List<String> getTitles() {
		return titles;
	}

	public void setTitles(List<String> titles) {
		this.titles = titles;
	}

	public List<HashMap<String, String>> getLogicalPathList() {
		return logicalPathList;
	}

	public void setLogicalPathList(List<HashMap<String, String>> logicalPathList) {
		this.logicalPathList = logicalPathList;
	}

	public Integer getTfID() {
		return tfID;
	}

	public void setTfID(Integer tfID) {
		this.tfID = tfID;
	}

	public Tariff getTariff() {
		return tariff;
	}

	public void setTariff(Tariff tariff) {
		this.tariff = tariff;
	}

	public SearchInvoiceVO getSvo() {
		return svo;
	}

	public void setSvo(SearchInvoiceVO svo) {
		this.svo = svo;
	}

	public Map<String, Object> getAttachment() {
		return Attachment;
	}

	public void setAttachment(Map<String, Object> attachment) {
		Attachment = attachment;
	}

	public Contract getContract() {
		return contract;
	}

	public void setContract(Contract contract) {
		this.contract = contract;
	}
	
}
