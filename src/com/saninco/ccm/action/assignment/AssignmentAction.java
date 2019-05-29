package com.saninco.ccm.action.assignment;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.assignment.IAssignmentService;
import com.saninco.ccm.service.quicklink.IQuicklinkService;
import com.saninco.ccm.vo.AssignmentVO;
import com.saninco.ccm.vo.MapEntryVO;

/**
 * 
 * @author pengfei.wang
 *
 */
public class AssignmentAction extends CcmActionSupport {

	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IQuicklinkService quicklinkService;
	private IAssignmentService assignmentService;
	
	private AssignmentVO asvo  ;
	private String selVendorId;
	private int deleteId;
	private int usersId;
	
	//Some list data ¡ý
	private List<MapEntryVO<String, String>> vendorList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> banList = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> analystByRoleId = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> users = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> Approver1 = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> Approver2 = new ArrayList<MapEntryVO<String, String>>();
	private List<MapEntryVO<String, String>> Approver3 = new ArrayList<MapEntryVO<String, String>>();
	
	//And some of the field as well as Vo ¡ý
	private String anAssignmentId;
	private String vendorId;
	private String banId;
	private String analystId;
	private String approver1Id;
	private String approver2Id;
	private String approver3Id;

	public String exec() throws Exception {
		populateReconcileSearchLookupList();//List will be loaded into pages
		return SUCCESS;
	}
	
	//CommonLookupService obtained from the list
	private void populateReconcileSearchLookupList() throws Exception {
		logger.info("Enter method populateReconcileSearchLookupList.");
		this.vendorList = commonLookupService.getUserPreviledgedVendors();
		this.analystByRoleId = commonLookupService.getAnalystByRoleId();
		logger.info("Exit method populateReconcileSearchLookupList.");
	}
	
	/**
	 * Drop-down list data
	 * */
	public String getEnditList() throws Exception {
		logger.info("Enter method searchInvoiceAssignment.");
		String result;
		try{
			result = commonLookupService.getUsersJsonStrBySupervisorUserId(usersId);
		}catch(Exception e){
			logger.error("getEnditList error: ", e);
			result = "{error:\"getEnditList error: "+e.getMessage()+"\"}";
		}
	    this.writeOutputStream(result);
		logger.info("Exit method searchInvoiceAssignment.");
		return null;
	}
	
	/**
	 * Assignment of data query that accords with a condition
	 * */
	public String searchAssignment() throws Exception {
		logger.info("Enter method searchAssignment.");
		String result;
		try{
			result = assignmentService.searchAssignment(asvo);
		}catch(Exception e){
			logger.error("searchAssignment error: ", e);
			result = "{error:\"searchAssignment error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchAssignment.");
		return null;
	}
	
	/**
	 * Statistical eligible Assignment
	 * */
	public String searchAssignmentTotalNo() throws Exception {
		logger.info("Enter method searchAssignmentTotalNo.");
		String result;
		try{
			result = assignmentService.getAssignmentSearchTotalPageNo(asvo);
		}catch(Exception e){
			logger.error("searchAssignmentTotalNo error: ", e);
			result = "{error:\"searchAssignmentTotalNo error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method searchAssignmentTotalNo.");
		return null;
	}
	
	/**
	 * This Assignment will be deleted
	 * */
	public void getDeleteAssignment() throws Exception {
		logger.info("Enter method getDeleteAssignment.");
		try{
			assignmentService.delete(deleteId);
		}catch(Exception e){
			logger.error("getDeleteAssignment error: ", e);
		}
		logger.info("Exit method getDeleteAssignment.");	
	}
	
	/**
	 * This Assignment will be save
	 * */
	public void getAddAssignment() throws Exception {
		logger.info("Enter method getAddAssignment.");
		AssignmentVO asvo =new AssignmentVO();
		asvo.setVendorId(vendorId);
		asvo.setBanId(banId);
		asvo.setAnalystId(analystId);
		asvo.setApprover1Id(approver1Id);
		asvo.setApprover2Id(approver2Id);
		asvo.setApprover3Id(approver3Id);
		assignmentService.save(asvo);
		logger.info("Exit method getAddAssignment.");
	}
	
	/**
	 * Endit the Assignment
	 * */
	public void getEnditAssignment() throws Exception {
		logger.info("Enter method getEnditAssignment.");
		AssignmentVO asvo =new AssignmentVO();
		asvo.setBanAssignmentId(anAssignmentId);
		asvo.setVendorId(vendorId);
		asvo.setBanId(banId);
		asvo.setAnalystId(analystId);
		asvo.setApprover1Id(approver1Id);
		asvo.setApprover2Id(approver2Id);
		asvo.setApprover3Id(approver3Id);
		assignmentService.update(asvo);
		logger.info("Exit method getEnditAssignment.");
	}
	
	/***
	 * @return
	 * @throws Exception
	 */
	public String getNumberOfAssignmentByVendorIdAndBanId() throws Exception{
		logger.info("Enter method findInvoiceSt.");
		String result;
		try {
			AssignmentVO asvo =new AssignmentVO();
			asvo.setVendorId(vendorId);
			asvo.setBanId(banId);
			result = assignmentService.getNumberOfAssignmentByVendorIdAndBanId(asvo);
		} catch (BPLException e) {
			logger.error("findInvoiceSt error: ", e);
			result = "{error:\"findInvoiceSt error: "+e.getMessage()+"\"}";
		}
		this.writeOutputStream(result);
		logger.info("Exit method findInvoiceSt.");
		return null;
	}
	
	public IQuicklinkService getQuicklinkService() {
		return quicklinkService;
	}

	public void setQuicklinkService(IQuicklinkService quicklinkService) {
		this.quicklinkService = quicklinkService;
	}

	public String getSelVendorId() {
		return selVendorId;
	}

	public void setSelVendorId(String selVendorId) {
		this.selVendorId = selVendorId;
	}

	public IAssignmentService getAssignmentService() {
		return assignmentService;
	}

	public void setAssignmentService(IAssignmentService assignmentService) {
		this.assignmentService = assignmentService;
	}

	public AssignmentVO getAsvo() {
		return asvo;
	}

	public void setAsvo(AssignmentVO asvo) {
		this.asvo = asvo;
	}

	public List<MapEntryVO<String, String>> getVendorList() {
		return vendorList;
	}

	public void setVendorList(List<MapEntryVO<String, String>> vendorList) {
		this.vendorList = vendorList;
	}

	public List<MapEntryVO<String, String>> getBanList() {
		return banList;
	}

	public void setBanList(List<MapEntryVO<String, String>> banList) {
		this.banList = banList;
	}

	public int getDeleteId() {
		return deleteId;
	}

	public List<MapEntryVO<String, String>> getAnalystByRoleId() {
		return analystByRoleId;
	}

	public void setAnalystByRoleId(
			List<MapEntryVO<String, String>> analystByRoleId) {
		this.analystByRoleId = analystByRoleId;
	}

	public List<MapEntryVO<String, String>> getUsers() {
		return users;
	}

	public void setUsers(List<MapEntryVO<String, String>> users) {
		this.users = users;
	}

	public int getUsersId() {
		return usersId;
	}

	public void setUsersId(int usersId) {
		this.usersId = usersId;
	}

	public List<MapEntryVO<String, String>> getApprover1() {
		return Approver1;
	}

	public void setApprover1(List<MapEntryVO<String, String>> approver1) {
		Approver1 = approver1;
	}

	public List<MapEntryVO<String, String>> getApprover2() {
		return Approver2;
	}

	public void setApprover2(List<MapEntryVO<String, String>> approver2) {
		Approver2 = approver2;
	}

	public String getAnAssignmentId() {
		return anAssignmentId;
	}

	public void setAnAssignmentId(String anAssignmentId) {
		this.anAssignmentId = anAssignmentId;
	}

	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getBanId() {
		return banId;
	}

	public void setBanId(String banId) {
		this.banId = banId;
	}

	public String getAnalystId() {
		return analystId;
	}

	public void setAnalystId(String analystId) {
		this.analystId = analystId;
	}

	public String getApprover1Id() {
		return approver1Id;
	}

	public void setApprover1Id(String approver1Id) {
		this.approver1Id = approver1Id;
	}

	public String getApprover2Id() {
		return approver2Id;
	}

	public void setApprover2Id(String approver2Id) {
		this.approver2Id = approver2Id;
	}

	public String getApprover3Id() {
		return approver3Id;
	}

	public void setApprover3Id(String approver3Id) {
		this.approver3Id = approver3Id;
	}

	public void setDeleteId(int deleteId) {
		this.deleteId = deleteId;
	}

	public List<MapEntryVO<String, String>> getApprover3() {
		return Approver3;
	}

	public void setApprover3(List<MapEntryVO<String, String>> approver3) {
		Approver3 = approver3;
	}

}
