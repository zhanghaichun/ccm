/**
 * 
 */
package com.saninco.ccm.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.hibernate.lob.SerializableBlob;

import com.saninco.ccm.dao.IAccountCodelDao;
import com.saninco.ccm.dao.IAttachmentFileDao;
import com.saninco.ccm.dao.IBanDao;
import com.saninco.ccm.dao.ICircuitDao;
import com.saninco.ccm.dao.IContactDao;
import com.saninco.ccm.dao.ICreatedByDao;
import com.saninco.ccm.dao.ICreditDao;
import com.saninco.ccm.dao.ICreditStatusDao;
import com.saninco.ccm.dao.ICurrencyDao;
import com.saninco.ccm.dao.IDisputeDao;
import com.saninco.ccm.dao.IDisputeReasonDao;
import com.saninco.ccm.dao.IDisputeStatusDao;
import com.saninco.ccm.dao.IEventJournalDao;
import com.saninco.ccm.dao.IFunctionDao;
import com.saninco.ccm.dao.IInvoiceDao;
import com.saninco.ccm.dao.IInvoiceStDao;
import com.saninco.ccm.dao.IInvoiceStatusDao;
import com.saninco.ccm.dao.ILabelDao;
import com.saninco.ccm.dao.IMasterInventoryDao;
import com.saninco.ccm.dao.IOriginatorDao;
import com.saninco.ccm.dao.IPaymentDao;
import com.saninco.ccm.dao.IPaymentStatusDao;
import com.saninco.ccm.dao.IPriorityDao;
import com.saninco.ccm.dao.IReportDAO;
import com.saninco.ccm.dao.IRoleDao;
import com.saninco.ccm.dao.IRtagDao;
import com.saninco.ccm.dao.ISecurityManagementDao;
import com.saninco.ccm.dao.ISeverityDao;
import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.dao.ITariffLinkDao;
import com.saninco.ccm.dao.ITicketStatusDao;
import com.saninco.ccm.dao.IUserActionLogDao;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.dao.IUserDelegationDao;
import com.saninco.ccm.dao.IVendorDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AccountType;
import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Contact;
import com.saninco.ccm.model.CreditStatus;
import com.saninco.ccm.model.Currency;
import com.saninco.ccm.model.DisputeReason;
import com.saninco.ccm.model.DisputeStatus;
import com.saninco.ccm.model.EventJournal;
import com.saninco.ccm.model.Function;
import com.saninco.ccm.model.InvoiceChannel;
import com.saninco.ccm.model.InvoiceFormat;
import com.saninco.ccm.model.InvoiceSt;
import com.saninco.ccm.model.InvoiceStatus;
import com.saninco.ccm.model.Label;
import com.saninco.ccm.model.PaymentGroup;
import com.saninco.ccm.model.PaymentMethod;
import com.saninco.ccm.model.PaymentStatus;
import com.saninco.ccm.model.PaymentTerm;
import com.saninco.ccm.model.Priority;
import com.saninco.ccm.model.Severity;
import com.saninco.ccm.model.TariffLink;
import com.saninco.ccm.model.Theme;
import com.saninco.ccm.model.TicketStatus;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.UserActionLog;
import com.saninco.ccm.model.UserPrivilege;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.model.VendorStatus;
import com.saninco.ccm.util.BlobUtil;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.FileUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.WorkspaceVO;

/**
 * @author joe.yang
 * 
 */
public class CommonLookupServiceImpl implements ICommonLookupService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	private ICurrencyDao currencyDao;
	private IInvoiceStatusDao invoiceStatusDao;
	private IDisputeStatusDao disputeStatusDao;
	private IPaymentStatusDao paymentStatusDao;
	private IDisputeReasonDao disputeReasonDao;
	private ICreditStatusDao creditStatusDao;
	private ITicketStatusDao ticketStatusDao;
	private IPriorityDao priorityDao;
	private ISeverityDao severityDao;
	private ICreatedByDao createdByDao;
	private IVendorDao vendorDao;
	private IMasterInventoryDao masterInventoryDao;
	private ICircuitDao circuitDao;
	private IBanDao banDao;
	private IUserDao userDao;
	private IContactDao contactDao;
	private IRoleDao roleDao;
	private IInvoiceStDao invoiceStDao;
	private ILabelDao labelDao;
	private IFunctionDao functionDao;
	private IAccountCodelDao accountCodelDao;
	private ISysConfigDAO sysConfigDAO;
	private IEventJournalDao eventJournalDao;
	
	private IUserDelegationDao userDelegationDao;
	private ISecurityManagementDao securityManagementDao;
	private IInvoiceDao invoiceDao;
	private IDisputeDao disputeDao;
	private IPaymentDao paymentDao;
	private ICreditDao creditDao;
	
	private IRtagDao rtagDao;
	
	private IAttachmentFileDao attachmentFileDao;
	
	private IOriginatorDao originatorDao;
	
	private IUserActionLogDao userActionLogDao;
	private ITariffLinkDao tariffLinkDao;
	
	private IReportDAO reportDAO;
	
	
	public IReportDAO getReportDAO() {
		return reportDAO;
	}

	public void setReportDAO(IReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	
	public IUserActionLogDao getUserActionLogDao() {
		return userActionLogDao;
	}

	public void setUserActionLogDao(IUserActionLogDao userActionLogDao) {
		this.userActionLogDao = userActionLogDao;
	}

	public IInvoiceStDao getInvoiceStDao() {
		return invoiceStDao;
	}

	public void setInvoiceStDao(IInvoiceStDao invoiceStDao) {
		this.invoiceStDao = invoiceStDao;
	}

	public CommonLookupServiceImpl() {
	}
	
	/**
	 * Get active Account Code list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getAccountCode() throws BPLException {
		logger.info("Enter method getAccountCode.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List dList = null;
		try {
			dList = accountCodelDao.getAccountCode();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : dList){
            Object[] accountCode = (Object[])b;
            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)accountCode[0]).toString(), (String)accountCode[1]);
            list.add(m);
        }
		logger.info("Exit method getAccountCode.");
		return list;
	}
	
	/**
	 * Get all Account Code list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getAllAccountCode() throws BPLException {
		logger.info("Enter method getAllAccountCode.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List dList = null;
		try {
			dList = accountCodelDao.getAllAccountCode();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : dList){
			Object[] accountCode = (Object[])b;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)accountCode[0]).toString(), (String)accountCode[1]);
			list.add(m);
		}
		logger.info("Exit method getAllAccountCode.");
		return list;
	}
	
	/**
	 * Get all Account Code list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getChargeType() throws BPLException {
		logger.info("Enter method getChargeType.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List dList = null;
		try {
			dList = accountCodelDao.getChargeType();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : dList){
			Object[] accountCode = (Object[])b;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)accountCode[0]).toString(), (String)accountCode[1]);
			list.add(m);
		}
		logger.info("Exit method getChargeType.");
		return list;
	}
	
	/**
	 * Get all Tax Code list.
	 * @author mingyang.Li
	 */
	public List<MapEntryVO<String, String>> getTaxCode() throws BPLException {
		logger.info("Enter method getTaxCode.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List dList = null;
		try {
			dList = accountCodelDao.getTaxCode();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : dList){
			Object[] accountCode = (Object[])b;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)accountCode[0]).toString(), (String)accountCode[1]);
			list.add(m);
		}
		logger.info("Exit method getTaxCode.");
		return list;
	}
	
	
	/**
	 * Get all Function list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getAllFunctions() throws BPLException {
		logger.info("Enter method getAllFunctions.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Function> dList = null;
		try {
			dList = functionDao.getFunction();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Function r : dList){
			if(!((r.getFunctionName().toString()).equals("ALL"))){
				MapEntryVO<String, String> m = new MapEntryVO<String, String>(r.getId().toString(), r.getFunctionName());
				list.add(m);
			}
		}
		logger.info("Exit method getAllFunctions.");
		return list;
	}
	
	/**
	 * Get all Label list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getLabels() throws BPLException {
		logger.info("Enter method getLabels.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Label> dList = null;
		try {
			dList = labelDao.getLabel();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Label r : dList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(r.getId().toString(), r.getLabelName());
			list.add(m);
		}
		logger.info("Exit method getLabels.");
		return list;
	}
	
	/**
	 * Get all invoice_st list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getInvoiceSt() throws BPLException {
		logger.info("Enter method getInvoiceSt.");
		StringBuffer sb = new StringBuffer();
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<InvoiceSt> dList = null;
		try {
			dList = invoiceStDao.getInvoiceSt();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(InvoiceSt r : dList){
			sb.delete(0, sb.length());
			sb.append(r.getVendor() == null ? "null" : r.getVendor().getVendorName().toString());
			sb.append("___");
			sb.append(r.getBan() == null ? "null" : r.getBan().getAccountNumber().toString());
			sb.append("___");
			sb.append(r.getInvoiceDate() == null ? "null" : r.getInvoiceDate().toString());
			sb.append("___");
			sb.append(r.getInvoiceCurrentDue() == null ? "null" : r.getInvoiceCurrentDue().toString());
			sb.append("___");
			sb.append(r.getInvoiceTotalDue() == null ? "null" : r.getInvoiceTotalDue().toString());
			sb.append("___");
			sb.append(r.getId().toString());
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(sb.toString(), 
					r.getBarCode() == null ? "" : r.getBarCode());
			list.add(m);
		}
		logger.info("Exit method getInvoiceSt.");
		return list;
	}
	
	/**
	 * Get all Role list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getRole() throws BPLException {
		logger.info("Enter method getRole.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> dList = null;
		try {
			dList = roleDao.getRole();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : dList){
			Object[] role = (Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(role[0].toString(), role[1].toString());
			list.add(m);
		}
		logger.info("Exit method getRole.");
		return list;
	}
	
	/**
	 * Get all severity list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getSeverity() throws BPLException {
		logger.info("Enter method getSeverity.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Severity> dList = null;
		try {
			dList = severityDao.getSeverity();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Severity i : dList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getSeverityName());
			list.add(m);
		}
		logger.info("Exit method getSeverity.");
		return list;
	}
	
	/**
	 * Get all priority list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getPriority() throws BPLException {
		logger.info("Enter method getPriority.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Priority> dList = null;
		try {
			dList = priorityDao.getPriority();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Priority i : dList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getPriorityName());
			list.add(m);
		}
		logger.info("Exit method getPriority.");
		return list;
	}
	
	/**
	 * Get all ticket status list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getTicketStatus() throws BPLException {
		logger.info("Enter method getTicketStatus.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<TicketStatus> dList = null;
		try {
			dList = (List<TicketStatus>)ticketStatusDao.findAll();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(TicketStatus i : dList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getTicketStatusName());
			list.add(m);
		}
		logger.info("Exit method getTicketStatus.");
		return list;
	}
	
	/**
	 * Get all credit status list.
	 * @author xinyu.Liu
	 */
	public List<MapEntryVO<String, String>> getCreditStatus() throws BPLException {
		logger.info("Enter method getCreditStatus.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<CreditStatus> dList = null;
		try {
			dList = creditStatusDao.getCreditStatus();
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(CreditStatus i : dList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getCreditStatusName());
			list.add(m);
		}
		logger.info("Exit method getCreditStatus.");
		return list;
	}
	
	
	/**
	 * Get all analyst(user) list.
	 * @author wei.su
	 */
	public List<MapEntryVO<String, String>> getAnalyst() throws BPLException {
		logger.info("Enter method getAnalyst.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> dList = null;
		try {
			dList = userDao.getAnalyst();
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : dList){
			Object[]user=(Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(user[0].toString(), user[1].toString());
			list.add(m);
		}
		logger.info("Exit method getAnalyst.");
		return list;
	}
	/**
	 * Get all analyst(user) list.
	 * @author wei.su
	 */
	public List<MapEntryVO<String, String>> getAnalystByRoleId() throws BPLException {
		logger.info("Enter method getAnalystByRoleId.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> dList = null;
		try {
			dList = userDao.getAnalystByRoleId();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : dList){
			Object[] user = (Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(user[0].toString(), user[1].toString() + " " + user[2].toString());
			list.add(m);
		}
		logger.info("Exit method getAnalystByRoleId.");
		return list;
	}
	/**
	 * Get all analyst(user) list.
	 * @author wei.su
	 */
	public List<MapEntryVO<String, String>> getApprover1ByRoleId() throws BPLException {
		logger.info("Enter method getApprover1ByRoleId.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> dList = null;
		try {
			dList = userDao.getApprover1ByRoleId();
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : dList){
			Object[] user = (Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(user[0].toString(), user[1].toString() + " " + user[2].toString());
			list.add(m);
		}
		logger.info("Exit method getApprover1ByRoleId.");
		return list;
	}
	/**
	 * Get all analyst(user) list.
	 * @author wei.su
	 */
	public List<MapEntryVO<String, String>> getApprover2ByRoleId() throws BPLException {
		logger.info("Enter method getApprover2ByRoleId.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> dList = null;
		try {
			dList = userDao.getApprover2ByRoleId();
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : dList){
			Object[] user = (Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(user[0].toString(), user[1].toString() + " " + user[2].toString());
			list.add(m);
		}
		logger.info("Exit method getApprover2ByRoleId.");
		return list;
	}
	/**
	 * Get all analyst(user) list.
	 * @author wei.su
	 */
	public List<MapEntryVO<String, String>> getApprover3ByRoleId() throws BPLException {
		logger.info("Enter method getApprover3ByRoleId.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> dList = null;
		try {
			dList = userDao.getApprover3ByRoleId();
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : dList){
			Object[] user = (Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(user[0].toString(), user[1].toString() + " " + user[2].toString());
			list.add(m);
		}
		logger.info("Exit method getApprover3ByRoleId.");
		return list;
	}
	/**
	 * Get all analyst(user) list BY SupervisorUserId.
	 * @author wei.su
	 */
	@SuppressWarnings("unchecked")
	public String getUsersJsonStrBySupervisorUserId(int supervisorUserId) throws BPLException {
		logger.info("Enter method getUsersJsonStrBySupervisorUserId.");
		StringBuffer sb = new StringBuffer("[");
		List userList;
		try {
			userList = userDao.getUsersBySupervisorUserId(supervisorUserId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : userList){
			Object[] user = (Object[])u;
			if(sb.length()>1) sb.append(",");
			sb.append("{id:"+user[0].toString());
			sb.append(",username:\""+user[1].toString()+" "+user[2].toString()+"\"}");
		}
		sb.append("]");
		logger.info("Exit method getUsersJsonStrBySupervisorUserId.");
		return sb.toString();
	}
	
	/**
	 * Get all dispute reason list.
	 */
	public List<MapEntryVO<String, String>> getDisputeReason() throws BPLException {
		logger.info("Enter method getDisputeReason.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<DisputeReason> dList = null;
		try {
			dList = disputeReasonDao.getDisputeReason();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(DisputeReason i : dList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getDisputeReasonName());
			list.add(m);
		}
		logger.info("Exit method getDisputeReason.");
		return list;
	}

	/**
	 * Get all BANs by vendor id.
	 * jian.dong
	 */
	public List<MapEntryVO<String, String>> getAllBansByVendorId(Integer vendorId)  throws BPLException{
		logger.info("Enter method getAllBansByVendorId with Vendor Id"+vendorId+".");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List banList = null;
		try {
			banList = banDao.getBanByVendorId(vendorId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object b : banList){
            Object[] ban = (Object[])b;
            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)ban[0]).toString(), (String)ban[1]);
            l.add(m);
        }
		logger.info("Exit method getAllBansByVendorId.");
		return l;
	}
	
	/**
	 * Get user previledged BANs list by vendor id. 
	 */
	public List<MapEntryVO<String, String>> getUserPreviledgedBanByVendorId(Integer vendorId) throws BPLException {
		logger.info("Enter method getUserPreviledgedBanByVendorId with Vendor Id"+vendorId+".");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List banList = null;
		try {
			banList = banDao.getUserPreviledgedBanByVendorId(SystemUtil.getCurrentUserId(), vendorId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object b : banList){
            Object[] ban = (Object[])b;
            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)ban[0]).toString(), (String)ban[1]);
            l.add(m);
        }
		logger.info("Exit method getUserPreviledgedBanByVendorId.");
		return l;
	}

	/**
	 * Get user previledged BANs JSON string by vendor id.
	 */
	public String getUserPreviledgedBansJsonStrByVendorId(Integer vendorId) throws BPLException {
		logger.info("Enter method getUserPreviledgedBansJsonStrByVendorId with Vendor Id"+vendorId+".");
		StringBuffer sb = new StringBuffer("[");
		List banList;
		try {
			banList = banDao.getUserPreviledgedBanByVendorId(SystemUtil.getCurrentUserId(), vendorId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object b : banList){
            Object[] ban = (Object[])b;
			if(sb.length()>1) sb.append(",");
			sb.append("{id:"+((Integer)ban[0]).toString());
			sb.append(",no:\""+(String)ban[1]+"\"}");
        }
		sb.append("]");
		logger.info("Exit method getUserPreviledgedBansJsonStrByVendorId.");
		return sb.toString();
	}
	
	/**
	 * yongzhen.li
	 */
	public String getUserPreviledgedBansJsonStrByVendorId0(Integer vendorId) throws BPLException {
		logger.info("Enter method getUserPreviledgedBansJsonStrByVendorId0 with Vendor Id"+vendorId+".");
		StringBuffer sb = new StringBuffer("{results: [");
		List banList;
		try {
			banList = banDao.getUserPreviledgedBanByVendorId(SystemUtil.getCurrentUserId(), vendorId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{id:\"all\"");
		sb.append(",name:\"\"}");
        for(Object b : banList){
            Object[] ban = (Object[])b;
//			if(sb.length()>29) sb.append(",");
			sb.append(",{id:"+((Integer)ban[0]).toString());
			sb.append(",name:\""+(String)ban[1]+"\"}");
        }
		sb.append("]}");
		logger.info("Exit method getUserPreviledgedBansJsonStrByVendorId0.");
		return sb.toString();
	}
	
	/**
	 * Get all BANs JSON string by vendor Id.
	 */
	public String getAllBansJsonStrByVendorId(Integer vendorId) throws BPLException {
		logger.info("Enter method getAllBansJsonStrByVendorId with Vendor Id"+vendorId+".");
		StringBuffer sb = new StringBuffer("[");
		List banList = null;
		try {
			banList = banDao.getBanByVendorId(vendorId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object b : banList){
            Object[] ban = (Object[])b;
			if(sb.length()>1) sb.append(",");
			sb.append("{id:"+((Integer)ban[0]).toString());
			sb.append(",no:\""+(String)ban[1]+"\"}");
        }
		sb.append("]");
		logger.info("Exit method getAllBansJsonStrByVendorId.");
		return sb.toString();
	}
	
	/**
	 * Get all vendors list.
	 */
	public List<MapEntryVO<String, String>> getAllVendors() throws BPLException {
		logger.info("Enter method getAllVendors.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List vendorList = null;
		try {
			vendorList = vendorDao.getVendors();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object v : vendorList){
            Object[] vendor = (Object[])v;
            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)vendor[0]).toString(), (String)vendor[1]);
            l.add(m);
        }
		logger.info("Exit method getAllVendors.");
		return l;
	}
	
	/**
	 * Get user previledged vendor list.
	 */
	public List<MapEntryVO<String, String>> getUserPreviledgedVendors() throws BPLException {
		logger.info("Enter method getUserPreviledgedVendors.");
		List<MapEntryVO<String, String>> l = null;
		List vendorList = null;
		try {
			vendorList = vendorDao.getUserPreviledgedVendors(SystemUtil.getCurrentUserId());
			l = new ArrayList<MapEntryVO<String, String>>(vendorList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object v : vendorList){
            Object[] vendor = (Object[])v;
            if(!((vendor[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)vendor[0]).toString(), (String)vendor[1]);
	            l.add(m);
        	}	
        }
		logger.info("Exit method getUserPreviledgedVendors.");
		return l;
	}
	
	public List<MapEntryVO<String, String>> getVendorsStatus() throws BPLException {
		logger.info("Enter method getVendorsStatus.");
		List<MapEntryVO<String, String>> l = null;
		List<VendorStatus> vendorStatusList = null;
		try {
			vendorStatusList = vendorDao.getVendorStatus();
			l = new ArrayList<MapEntryVO<String, String>>(vendorStatusList.size());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(VendorStatus v : vendorStatusList){
				MapEntryVO<String, String> m = new MapEntryVO<String, String>(v.getId().toString(), v.getVendorStatusName());
				l.add(m);
		}
		logger.info("Exit method getVendorsStatus.");
		return l;
	}
	
	/**
	 * Get user previledged vendors JSON string.
	 */
	public String getUserPreviledgedVendorsJson() throws BPLException {
		logger.info("Enter method getUserPreviledgedVendorsJson.");
		StringBuffer sb = new StringBuffer("[");
		List vendorList = null;
		try {
			vendorList = vendorDao.getUserPreviledgedVendors(SystemUtil.getCurrentUserId());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object v : vendorList){
            Object[] vendor = (Object[])v;
			if(!((((Integer)vendor[0]).toString()).equals("0"))){
				if(sb.length()>1) sb.append(",");
				sb.append("{id:"+((Integer)vendor[0]).toString());
				sb.append(",name:\""+(String)vendor[1]+"\"}");
			}
        }
		sb.append("]");
		logger.info("Exit method getUserPreviledgedVendorsJson.");
		return sb.toString();
	}

	/**
	 * Get all currency list.
	 */
	public List<MapEntryVO<String, String>> getCurrency() throws BPLException {
		logger.info("Enter method getCurrency.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List<Currency> cList = null;
		try {
			cList = currencyDao.getCurrency();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Currency c : cList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(c.getId().toString(), c.getCurrencyName());
			l.add(m);
		}
		logger.info("Exit method getCurrency.");
		return l;
	}
	
	/**
	 * Get all invoice status list.
	 */
	public List<MapEntryVO<String, String>> getInvoiceStatus() throws BPLException {
		logger.info("Enter method getInvoiceStatus.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List<InvoiceStatus> iList = null;
		try {
			iList = invoiceStatusDao.getInvoiceStatus();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(InvoiceStatus i : iList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getInvoiceStatusName());
			l.add(m);
		}
		logger.info("Exit method getInvoiceStatus.");
		return l;
	}
	
	/**
	 * Get all dispute status list.
	 */
	public List<MapEntryVO<String, String>> getDisputeStatus() throws BPLException {
		logger.info("Enter method getDisputeStatus.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<DisputeStatus> dList = null;
		try {
			dList = disputeStatusDao.getDisputeStatus();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(DisputeStatus i : dList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getDisputeStatusName());
			list.add(m);
		}
		logger.info("Exit method getDisputeStatus.");
		return list;
	}
	/**
	 * Get Rtags Name List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getRtagsNameList() throws BPLException {
		logger.info("Enter method getRtagsNameList.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> rList = null;
		try {
			rList = rtagDao.getRtag();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : rList){
			Object[] rtag = (Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(rtag[0].toString(), rtag[1].toString());
			list.add(m);
		}
		logger.info("Exit method getRtagsNameList.");
		return list;
	}
	/**
	 * Get Ban Status List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getBanStatus(Integer banStatusId) throws BPLException {
		logger.info("Enter method getBanStatus.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> bList = null;
		try {
			bList = banDao.getBanStatus(banStatusId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : bList){
			Object[] banStatus = (Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(banStatus[0].toString(), banStatus[1].toString());
			list.add(m);
		}
		logger.info("Exit method getBanStatus.");
		return list;
	}
	/**
	 * Get Invoice Channel List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getInvoiceChannel() throws BPLException {
		logger.info("Enter method getInvoiceChannel.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<InvoiceChannel> icList = null;
		try {
			icList = invoiceDao.getInvoiceChannel();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(InvoiceChannel i : icList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getInvoiceChannelName());
			list.add(m);
		}
		logger.info("Exit method getInvoiceChannel.");
		return list;
	}
	/**
	 * Get Payment Method List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getPaymentMethod() throws BPLException {
		logger.info("Enter method getPaymentMethod.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<PaymentMethod> pmList = null;
		try {
			pmList = paymentDao.getPaymentMethod();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(PaymentMethod i : pmList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getPaymentMethodCode());
			list.add(m);
		}
		logger.info("Exit method getPaymentMethod.");
		return list;
	}
	/**
	 * Get Payment Method List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getInvoiceFormat() throws BPLException {
		logger.info("Enter method getInvoiceFormat.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<InvoiceFormat> ifList = null;
		try {
			ifList = invoiceDao.getInvoiceFormat();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(InvoiceFormat i : ifList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getInvoiceFormatCode());
			list.add(m);
		}
		logger.info("Exit method getInvoiceFormat.");
		return list;
	}
	/**
	 * Get Contact List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getContact() throws BPLException {
		logger.info("Enter method getContact.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Contact> cList = null;
		try {
			cList = contactDao.findAll();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Contact i : cList){
			if(!i.getRecActiveFlag().equals("Y")){break;}
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getFirstName()+" "+i.getLastName()+" "+i.getEmail());
			list.add(m);
		}
		logger.info("Exit method getContact.");
		return list;
	}
	/**
	 * Get Account Type List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getAccountType() throws BPLException {
		logger.info("Enter method getAccountType.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<AccountType> atList = null;
		try {
			atList = banDao.getAccountType();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(AccountType i : atList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getAccountTypeName());
			list.add(m);
		}
		logger.info("Exit method getAccountType.");
		return list;
	}
	/**
	 * Get Payment Term List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getPaymentTerm() throws BPLException {
		logger.info("Enter method getPaymentTerm.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<PaymentTerm> ptList = null;
		try {
			ptList = banDao.getPaymentTerm();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(PaymentTerm i : ptList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getPaymentTermCode());
			list.add(m);
		}
		logger.info("Exit method getPaymentTerm.");
		return list;
	}
	/**
	 * Get Payment Group List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getPaymentGroup() throws BPLException {
		logger.info("Enter method getPaymentGroup.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<PaymentGroup> pgList = null;
		try {
			pgList = banDao.getPaymentGroup();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(PaymentGroup i : pgList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getPaymentGroupCode());
			list.add(m);
		}
		logger.info("Exit method getPaymentGroup.");
		return list;
	}
	/**
	 * Get Line of business  List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getLineofBbusiness() throws BPLException {
		logger.info("Enter method getLineofBbusiness.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List os = null;
		try {
			os = banDao.getLineofBbusiness();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object i : os){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.toString(), i.toString());
			list.add(m);
		}
		logger.info("Exit method getLineofBbusiness.");
		return list;
	}
	
	/**
	 * Get Line of business  List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getBanInactiveReason() throws BPLException {
		logger.info("Enter method getLineofBbusiness.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List os = null;
		try {
			os = banDao.getBanInactiveReason();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : os){
			Object[] banInactiveReason = (Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(banInactiveReason[0].toString(), banInactiveReason[1].toString());
			list.add(m);
		}
		logger.info("Exit method getLineofBbusiness.");
		return list;
	}
	
	/**
	 * Get Line of Invoice Frequency List. [Chao.Liu]
	 */
	public List<MapEntryVO<String, String>> getInvoiceFrequency() throws BPLException {
		logger.info("Enter method getLineofBbusiness.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List os = null;
		try {
			os = banDao.getInvoiceFrequency();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object i : os){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.toString(), i.toString());
			list.add(m);
		}
		logger.info("Exit method getLineofBbusiness.");
		return list;
	}
	/**
	 * Get all payment status list.
	 */
	public List<MapEntryVO<String, String>> getPaymentStatus() throws BPLException {
		logger.info("Enter method getPaymentStatus.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List<PaymentStatus> iList = null;
		try {
			iList = paymentStatusDao.getPaymentStatus();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(PaymentStatus i : iList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i.getId().toString(), i.getPaymentStatusName());
			l.add(m);
		}
		logger.info("Exit method getPaymentStatus.");
		return l;
	}
	
	public List<MapEntryVO<String, String>> getAnalysts() throws BPLException{
		logger.info("Enter method getAnalysts.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List iList = null;
		try {
			iList = userDao.findAll();
	        for(Object b : iList){
	            Object[] user = (Object[])b;
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)user[0]).toString(), (String)user[1] + " " + (String)user[2]);
	            l.add(m);
	        }
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getAnalysts.");
		return l;
	}
	
	public List<MapEntryVO<String, String>> getCreatedBy() throws BPLException {	
		logger.info("Enter method getPaymentStatus.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List iList = null;
		try {
			iList = createdByDao.getCreatedBy();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : iList){
			Object[] i = (Object[])b; 
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)i[0]).toString(),(String)i[1]);
			l.add(m);
		}
		logger.info("Exit method getPaymentStatus.");
		return l;
	}
	
	public List<MapEntryVO<String, String>> getAllUserList() throws BPLException {	
		logger.info("Enter method getPaymentStatus.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List iList = null;
		try {
			iList = createdByDao.getAllUserList();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : iList){
			Object[] i = (Object[])b; 
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)i[0]).toString(),(String)i[1]);
			l.add(m);
		}
		logger.info("Exit method getPaymentStatus.");
		return l;
	}
	
	/**
	 * Get user workspace JSON string.
	 */
	public String getUserWorcspaceJson() throws BPLException {
		logger.info("Enter method getUserWorcspaceJson.");
		StringBuilder sb = new StringBuilder("[");
		int currentUserId = SystemUtil.getCurrentUserId();
		String username;
		Integer userid;
		long invoiceAssignmentCount = 0;
		long disputeAssignmentCount = 0;
		long paymentAssignmentCount = 0;
		long creditAssignmentCount = 0;
		try {
			invoiceAssignmentCount = invoiceDao.getAssignmentCount(currentUserId);
			disputeAssignmentCount = disputeDao.getAssignmentCount(currentUserId);
			paymentAssignmentCount = paymentDao.getAssignmentCount(currentUserId);
			creditAssignmentCount = creditDao.getAssignmentCount(currentUserId);
			sb.append(toUserWorkspaceJson("My",currentUserId,invoiceAssignmentCount,disputeAssignmentCount,paymentAssignmentCount,creditAssignmentCount));
			List users = userDelegationDao.findMyDesignateUsers(currentUserId);
			for(Object user : users){
				Object[] userFields = (Object[])user;
				userid = (Integer)userFields[0];
				username = (String)userFields[1];
				invoiceAssignmentCount = invoiceDao.getAssignmentCount(userid);
				disputeAssignmentCount = disputeDao.getAssignmentCount(userid);
				paymentAssignmentCount = paymentDao.getAssignmentCount(userid);
				creditAssignmentCount = creditDao.getAssignmentCount(userid);
				sb.append(",");
				sb.append(toUserWorkspaceJson(username,userid,invoiceAssignmentCount,disputeAssignmentCount,paymentAssignmentCount,creditAssignmentCount));
			}
			sb.append("]");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
//			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			BPLException bpe = new BPLException(e.getMessage());
			throw bpe;
		}
		logger.info("Exit method getUserWorcspaceJson.");
		return sb.toString();
	}
	
	
	private String toUserWorkspaceJson(String username,int userid, long invoiceAssignmentCount, long disputeAssignmentCount, long paymentAssignmentCount, long creditAssignmentCount) {
		StringBuilder sb = new StringBuilder("{");
		sb.append("userName:");
		sb.append("\""+username+"\"");
		sb.append(",userId:");
		sb.append(userid);
		sb.append(",invoiceAssignmentCount:");
		sb.append(invoiceAssignmentCount);
		sb.append(",disputeAssignmentCount:");
		sb.append(disputeAssignmentCount);
		sb.append(",paymentAssignmentCount:");
		sb.append(paymentAssignmentCount);
		sb.append(",creditAssignmentCount:");
		sb.append(creditAssignmentCount);
		sb.append("}");
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * To show for all the User name Backup of the list
	 * */
	public String getMyWorkspaceAllCountArray()throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Execute and deliver inquires about the Myworkspace all data"));
		StringBuilder sb = new StringBuilder("{");
		WorkspaceVO rvo = new WorkspaceVO();
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redPastDue=invoiceDao.getPastDueCount(rvo)+"";
		//Less than 5 days of data query
		rvo.setUid(SystemUtil.getCurrentUserId());
		rvo.setDueDay(97);
		String redInvoiceCountr = invoiceDao.getInvoiceWorkCount(rvo) + "";
		//5-10 days to query data between
		rvo.setDueDay(915);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCounty = invoiceDao.getInvoiceWorkCount(rvo) + "";
		// 1-15 due days  //Add by wenbo.zhang 2015-3-18
		rvo.setDueDay(90);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCountr0 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(1);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCountr1 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(2);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCountr2 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(3);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCountr3 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(4);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCountr4 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(5);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCountr5 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(6);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCountr6 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(7);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCounty7 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(8);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCounty8 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(9);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCounty9 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(10);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCounty10 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(11);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCounty11 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(12);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCounty12 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(13);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCounty13 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(14);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCounty14 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(15);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCounty15 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		//More than 10 days of data query
		rvo.setDueDay(111);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String redInvoiceCountg = invoiceDao.getInvoiceWorkCount(rvo) + "";
		//
		String messis=invoiceDao.getWorkspaseCount(rvo) + "";
		String unknownInvoicesCount=invoiceDao.getUnknownInvoicesCount(rvo) + "";
		//Add by donghao.guo 2011-09-19
		String preloadInvoice=invoiceDao.getPreloadInvoiceWorkCount(rvo) + "";
		//Add by mingyang.li 2012-03-10
		String invoiceReject=invoiceDao.getInvoiceRejectWorkCount(rvo) + "";
		//
		String paymentInProcessCount=invoiceDao.getWorkspasePaymentInProcessCount(rvo) + "";
		String disputesInProcess=invoiceDao.getWorkspaseDisputesInProcess(rvo) + "";
		String paymentInException=invoiceDao.getWorkspasePaymentInException(rvo) + "";
		String banApproveCount=banDao.getBanWorkCountNOPaymentInException(rvo,4) + "";
		String banRejectCount=banDao.getBanWorkCountNOPaymentInException(rvo,5) + "";

		// External Approve Invoice Count.
		String externalApproveInvoiceCount = invoiceDao.getWorkspaceExternalApproveInvoiceCount(rvo) + "";
		//Encapsulated into closure mode transmitting data
		try {
			sb.append("redPastDue:"+redPastDue+",");
			sb.append("redInvoiceCount:"+redInvoiceCountr+",");
			sb.append("yellowInvoiceCount:"+redInvoiceCounty+",");
			sb.append("greenInvoiceCount:"+redInvoiceCountg+",");
			sb.append("unknownInvoicesCount:"+unknownInvoicesCount+",");
			sb.append("missingInvoicesCount:"+messis+",");
			sb.append("paymentInProcessCount:"+paymentInProcessCount+",");
			sb.append("disputesInProcessCount:"+disputesInProcess+",");
			sb.append("paymentInExceptionCount:"+paymentInException+",");
			sb.append("banApproveCount:"+banApproveCount+",");
			sb.append("banRejectCount:"+banRejectCount+",");
			//Add by donghao.guo 2011-09-19
			sb.append("preloadInvoicesCount:"+preloadInvoice+",");
			//add by wenbo.zhang 2015-3-18
			sb.append("redInvoiceCountr0:"+redInvoiceCountr0+",");
			sb.append("redInvoiceCountr1:"+redInvoiceCountr1+",");
			sb.append("redInvoiceCountr2:"+redInvoiceCountr2+",");
			sb.append("redInvoiceCountr3:"+redInvoiceCountr3+",");
			sb.append("redInvoiceCountr4:"+redInvoiceCountr4+",");
			sb.append("redInvoiceCountr5:"+redInvoiceCountr5+",");
			sb.append("redInvoiceCountr6:"+redInvoiceCountr6+",");
			sb.append("redInvoiceCounty7:"+redInvoiceCounty7+",");
			sb.append("redInvoiceCounty8:"+redInvoiceCounty8+",");
			sb.append("redInvoiceCounty9:"+redInvoiceCounty9+",");
			sb.append("redInvoiceCounty10:"+redInvoiceCounty10+",");
			sb.append("redInvoiceCounty11:"+redInvoiceCounty11+",");
			sb.append("redInvoiceCounty12:"+redInvoiceCounty12+",");
			sb.append("redInvoiceCounty13:"+redInvoiceCounty13+",");
			sb.append("redInvoiceCounty14:"+redInvoiceCounty14+",");
			sb.append("redInvoiceCounty15:"+redInvoiceCounty15+",");
			//Add by mingyang.li 2012-03-10
			sb.append("invoiceRejectCount:"+invoiceReject + ",");
			sb.append("externalApproveInvoiceCount:" + externalApproveInvoiceCount + " ");
			

			
			sb.append("}");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(e.getMessage());
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author pengfei.wang
	 * To show for all the User name Backup of the list
	 * */
	public String getDisputesBucketCount()throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Execute and deliver inquires about the Myworkspace all data"));
		StringBuilder sb = new StringBuilder("{");
		WorkspaceVO rvo = new WorkspaceVO();
		
		rvo.setUid(SystemUtil.getCurrentUserId());
		rvo.setDisputeDay(30);
		String first30Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(60);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String range31To60Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(90);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String range61To90Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(120);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String range91To120Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(180);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String range121To180Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(0);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String over180Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(-1);
		rvo.setUid(SystemUtil.getCurrentUserId());
		String totalDisputesBucket = disputeDao.searchDisputeCountByDays(rvo) + "";
		//Encapsulated into closure mode transmitting data
		try {
			sb.append("totalDisputesBucket:"+totalDisputesBucket+",");
			sb.append("first30Disputes:"+first30Disputes+",");
			sb.append("range31To60Disputes:"+range31To60Disputes+",");
			sb.append("range61To90Disputes:"+range61To90Disputes+",");
			sb.append("range91To120Disputes:"+range91To120Disputes+",");
			sb.append("range121To180Disputes:"+range121To180Disputes+",");
			sb.append("over180Disputes:"+over180Disputes);
			sb.append("}");
			
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(e.getMessage());
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * To show for all the User name Backup of the list
	 * */
	@SuppressWarnings("unchecked")
	public String getMyWorkspaceBackMockup() {
		logger.info(CcmFormat.formatEnterLog("Execute and deliver inquires about the Myworkspace all data"));
		StringBuffer sb = new StringBuffer("[");
		String usname;
		Integer usid;
		int i=0;
		WorkspaceVO brvo = new WorkspaceVO();
		List users = userDelegationDao.findMyWorkSparsUsers();
		for(Object user : users){
			Object[] userFields = (Object[])user;
			usid = (Integer)userFields[0];
			usname = (String)userFields[1];
			if(!SystemUtil.getCurrentUserId().equals(usid)){

				if(!sb.toString().equals("[")){
					sb.append(",");
				}
				
				sb.append("{uid:"+usid+",uname:\""+usname+"\",");
//				brvo.setUid(usid);
////				brvo.setUid(SystemUtil.getCurrentUserId());
//				String redPastDue = invoiceDao.getPastDueCount(brvo) + "";
//				//Less than 5 days of data query
//				brvo.setDueDay(97);
//				String redInvoiceCountr = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				//5-10 days to query data between
//				brvo.setDueDay(915);
//				String redInvoiceCounty = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(90);
//				brvo.setUid(usid);
//				String redInvoiceCountr0 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(1);
//				brvo.setUid(usid);
//				String redInvoiceCountr1 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(2);
//				brvo.setUid(usid);
//				String redInvoiceCountr2 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(3);
//				brvo.setUid(usid);
//				String redInvoiceCountr3 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(4);
//				brvo.setUid(usid);
//				String redInvoiceCountr4 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(5);
//				brvo.setUid(usid);
//				String redInvoiceCountr5 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(6);
//				brvo.setUid(usid);
//				String redInvoiceCountr6 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(7);
//				brvo.setUid(usid);
//				String redInvoiceCounty7 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(8);
//				brvo.setUid(usid);
//				String redInvoiceCounty8 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(9);
//				brvo.setUid(usid);
//				String redInvoiceCounty9 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(10);
//				brvo.setUid(usid);
//				String redInvoiceCounty10 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(11);
//				brvo.setUid(usid);
//				String redInvoiceCounty11 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(12);
//				brvo.setUid(usid);
//				String redInvoiceCounty12 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(13);
//				brvo.setUid(usid);
//				String redInvoiceCounty13 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(14);
//				brvo.setUid(usid);
//				String redInvoiceCounty14 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				brvo.setDueDay(15);
//				brvo.setUid(usid);
//				String redInvoiceCounty15 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				//More than 10 days of data query
//				brvo.setDueDay(111);
//				String redInvoiceCountg = invoiceDao.getInvoiceWorkCount(brvo) + "";
//				String unknownInvoicesCount=invoiceDao.getUnknownInvoicesCount(brvo) + "";
//				//missingInvoicesCount
//				String messis=invoiceDao.getWorkspaseCount(brvo) + "";
//				//preloadInvoiceCount
//				//Add by donghao.guo 2011-09-19
//				String preloadInvoice=invoiceDao.getPreloadInvoiceWorkCount(brvo) + "";
//				//Add by mingyang.li 2012-03-10
//				String invoiceReject=invoiceDao.getInvoiceRejectWorkCount(brvo) + "";
//				//invoiceInProcessCount
//				String paymentInProcessCount=invoiceDao.getWorkspasePaymentInProcessCount(brvo) + "";
//				String disputesInProcess=invoiceDao.getWorkspaseDisputesInProcess(brvo) + "";
//				String paymentInException=invoiceDao.getWorkspasePaymentInException(brvo) + "";
//				String banApproveCount=banDao.getBanWorkCountNOPaymentInException(brvo,4) + "";
//				String banRejectCount=banDao.getBanWorkCountNOPaymentInException(brvo,5) + "";
//				// External Approve Invoice Count.
//				String externalApproveInvoiceCount = invoiceDao.getWorkspaceExternalApproveInvoiceCount(brvo) + "";
//				
//				
//				sb.append("redPastDue:"+redPastDue+",");
//				sb.append("redInvoiceCount:"+redInvoiceCountr+",");
//				sb.append("yellowInvoiceCount:"+redInvoiceCounty+",");
//				sb.append("greenInvoiceCount:"+redInvoiceCountg+",");
//				sb.append("unknownInvoicesCount:"+unknownInvoicesCount+",");
//				sb.append("missingInvoicesCount:"+messis+",");
//				sb.append("paymentInProcessCount:"+paymentInProcessCount+",");
//				sb.append("disputesInProcessCount:"+disputesInProcess+",");
//				sb.append("paymentInExceptionCount:"+paymentInException+",");
//				sb.append("banApproveCount:"+banApproveCount+",");
//				sb.append("banRejectCount:"+banRejectCount+",");
//				//add by wenbo.zhang 2015-3-18
//				sb.append("redInvoiceCountr0:"+redInvoiceCountr0+",");
//				sb.append("redInvoiceCountr1:"+redInvoiceCountr1+",");
//				sb.append("redInvoiceCountr2:"+redInvoiceCountr2+",");
//				sb.append("redInvoiceCountr3:"+redInvoiceCountr3+",");
//				sb.append("redInvoiceCountr4:"+redInvoiceCountr4+",");
//				sb.append("redInvoiceCountr5:"+redInvoiceCountr5+",");
//				sb.append("redInvoiceCountr6:"+redInvoiceCountr6+",");
//				sb.append("redInvoiceCounty7:"+redInvoiceCounty7+",");
//				sb.append("redInvoiceCounty8:"+redInvoiceCounty8+",");
//				sb.append("redInvoiceCounty9:"+redInvoiceCounty9+",");
//				sb.append("redInvoiceCounty10:"+redInvoiceCounty10+",");
//				sb.append("redInvoiceCounty11:"+redInvoiceCounty11+",");
//				sb.append("redInvoiceCounty12:"+redInvoiceCounty12+",");
//				sb.append("redInvoiceCounty13:"+redInvoiceCounty13+",");
//				sb.append("redInvoiceCounty14:"+redInvoiceCounty14+",");
//				sb.append("redInvoiceCounty15:"+redInvoiceCounty15+",");
//				//Add by donghao.guo 2011-09-19
//				sb.append("preloadInvoicesCount:"+preloadInvoice+",");
//				//Add by mingyang.li 2012-03-10
//				sb.append("invoiceRejectCount:"+invoiceReject+",");
//				sb.append("externalApproveInvoiceCount:" + externalApproveInvoiceCount + ", ");
//				
//				WorkspaceVO rvo = new WorkspaceVO();
//				
//				rvo.setUid(usid);
//				rvo.setDisputeDay(30);
//				String first30Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//				rvo.setDisputeDay(60);
//				String range31To60Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//				rvo.setDisputeDay(90);
//				String range61To90Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//				rvo.setDisputeDay(120);
//				String range91To120Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//				rvo.setDisputeDay(180);
//				String range121To180Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//				rvo.setDisputeDay(0);
//				String over180Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//				rvo.setDisputeDay(-1);
//				String totalDisputesBucket = disputeDao.searchDisputeCountByDays(rvo) + "";
//				sb.append("totalDisputesBucket:"+totalDisputesBucket+",");
//				sb.append("first30Disputes:"+first30Disputes+",");
//				sb.append("range31To60Disputes:"+range31To60Disputes+",");
//				sb.append("range61To90Disputes:"+range61To90Disputes+",");
//				sb.append("range91To120Disputes:"+range91To120Disputes+",");
//				sb.append("range121To180Disputes:"+range121To180Disputes+",");
//				sb.append("over180Disputes:"+over180Disputes);
				
				sb.append("}");
				i++;
			}
		}
		sb.append("]");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * According to the current user Id the user's delegation to the user and all the delegation user Id invoece all the users and encapsulation into json request directly
	 * @author wei.su
	 * @return JSON
	 */
	@SuppressWarnings("unchecked")
	public String getUserDelegationList(WorkspaceVO workspaceVO) throws BPLException{
		logger.info("Enter method getUserDelegationList.");
		StringBuffer sb = new StringBuffer("[");
		List<Object[]> list = null;

		try {
			list = userDelegationDao.getMyDesignateUsers();
			
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(list!=null&&list.size()>0){
			int size = list.size();
			for(int i = 0; i<size; i++){
				if(i<size-1)
				{
					Object[] objects = list.get(i);
					sb.append("{uid:");
					sb.append(objects[0].toString()+",");
					sb.append("uname:\"");
					sb.append(objects[1].toString()+"\",");
					sb.append(this.getMyWorkspaceDelegationAllCountArray(Integer.parseInt(objects[0].toString()))+"}"+",");
				}
				else
				{
					Object[] objects = list.get(i);
					sb.append("{uid:");
					sb.append(objects[0].toString()+",");
					sb.append("uname:\"");
					sb.append(objects[1].toString()+"\",");
					sb.append(this.getMyWorkspaceDelegationAllCountArray(Integer.parseInt(objects[0].toString()))+"}");
					
				}
				
			}
			
		}else{
			//sb.append("{error:\"failed to query.\"");
		}
		sb.append("]");
		logger.info("Exit method getUserDelegationList.");
		return sb.toString();
	}

	/**
	 * According to the user's user Id invoece all and return
	 * @param userId
	 * @return
	 * @throws BPLException
	 */
	private String getMyWorkspaceDelegationAllCountArray(int userId)throws BPLException {
		logger.info("Enter method getMyWorkspaceDelegationAllCountArray.");
		StringBuilder sb = new StringBuilder();
		WorkspaceVO rvo = new WorkspaceVO();
//		rvo.setUid(	SystemUtil.getCurrentUserId());
		rvo.setUid(userId);
		String redPastDue = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(97);
		rvo.setUid(userId);
		String redInvoiceCountr = invoiceDao.getInvoiceWorkCount(rvo) + "";

		rvo.setDueDay(915);
		rvo.setUid(userId);
		String redInvoiceCounty = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(90);
		rvo.setUid(userId);
		String redInvoiceCountr0 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(1);
		rvo.setUid(userId);
		String redInvoiceCountr1 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(2);
		rvo.setUid(userId);
		String redInvoiceCountr2 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(3);
		rvo.setUid(userId);
		String redInvoiceCountr3 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(4);
		rvo.setUid(userId);
		String redInvoiceCountr4 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(5);
		rvo.setUid(userId);
		String redInvoiceCountr5 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(6);
		rvo.setUid(userId);
		String redInvoiceCountr6 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(7);
		rvo.setUid(userId);
		String redInvoiceCounty7 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(8);
		rvo.setUid(userId);
		String redInvoiceCounty8 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(9);
		rvo.setUid(userId);
		String redInvoiceCounty9 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(10);
		rvo.setUid(userId);
		String redInvoiceCounty10 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(11);
		rvo.setUid(userId);
		String redInvoiceCounty11 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(12);
		rvo.setUid(userId);
		String redInvoiceCounty12 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(13);
		rvo.setUid(userId);
		String redInvoiceCounty13 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(14);
		rvo.setUid(userId);
		String redInvoiceCounty14 = invoiceDao.getInvoiceWorkCount(rvo) + "";
		rvo.setDueDay(15);
		rvo.setUid(userId);
		String redInvoiceCounty15 = invoiceDao.getInvoiceWorkCount(rvo) + "";
	
		rvo.setDueDay(111);
		rvo.setUid(userId);
		String redInvoiceCountg = invoiceDao.getInvoiceWorkCount(rvo) + "";
		//Add by donghao.guo 2011-09-19
		String preloadInvoice=invoiceDao.getPreloadInvoiceWorkCount(rvo) + "";
		//Add by mingyang.li 2012-03-11
		String invoiceReject=invoiceDao.getInvoiceRejectWorkCount(rvo) + "";
		String unknownInvoicesCount=invoiceDao.getUnknownInvoicesCount(rvo) + "";
		//missingInvoicesCount
		String messis=invoiceDao.getWorkspaseCount(rvo) + "";
		//invoiceInProcessCount
		String paymentInProcessCount=invoiceDao.getWorkspasePaymentInProcessCount(rvo) + "";
		String disputesInProcess=invoiceDao.getWorkspaseDisputesInProcess(rvo) + "";
		String paymentInException=invoiceDao.getWorkspasePaymentInException(rvo) + "";
		String banApproveCount=banDao.getBanWorkCountNOPaymentInException(rvo,4) + "";
		String banRejectCount=banDao.getBanWorkCountNOPaymentInException(rvo,5) + "";
		// External Approve Invoice Count.
		String externalApproveInvoiceCount = invoiceDao.getWorkspaceExternalApproveInvoiceCount(rvo) + "";

		
		try {
			sb.append("redPastDue:"+redPastDue+",");
			sb.append("redInvoiceCount:"+redInvoiceCountr+",");
			sb.append("yellowInvoiceCount:"+redInvoiceCounty+",");
			sb.append("greenInvoiceCount:"+redInvoiceCountg+",");
			sb.append("unknownInvoicesCount:"+unknownInvoicesCount+",");
			sb.append("missingInvoicesCount:"+messis+",");
			sb.append("paymentInProcessCount:"+paymentInProcessCount+",");
			sb.append("disputesInProcessCount:"+disputesInProcess+",");
			sb.append("paymentInExceptionCount:"+paymentInException+",");
			sb.append("banApproveCount:"+banApproveCount+",");
			sb.append("banRejectCount:"+banRejectCount+",");
			//add by wenbo.zhang 2015-3-18
			sb.append("redInvoiceCountr0:"+redInvoiceCountr0+",");
			sb.append("redInvoiceCountr1:"+redInvoiceCountr1+",");
			sb.append("redInvoiceCountr2:"+redInvoiceCountr2+",");
			sb.append("redInvoiceCountr3:"+redInvoiceCountr3+",");
			sb.append("redInvoiceCountr4:"+redInvoiceCountr4+",");
			sb.append("redInvoiceCountr5:"+redInvoiceCountr5+",");
			sb.append("redInvoiceCountr6:"+redInvoiceCountr6+",");
			sb.append("redInvoiceCounty7:"+redInvoiceCounty7+",");
			sb.append("redInvoiceCounty8:"+redInvoiceCounty8+",");
			sb.append("redInvoiceCounty9:"+redInvoiceCounty9+",");
			sb.append("redInvoiceCounty10:"+redInvoiceCounty10+",");
			sb.append("redInvoiceCounty11:"+redInvoiceCounty11+",");
			sb.append("redInvoiceCounty12:"+redInvoiceCounty12+",");
			sb.append("redInvoiceCounty13:"+redInvoiceCounty13+",");
			sb.append("redInvoiceCounty14:"+redInvoiceCounty14+",");
			sb.append("redInvoiceCounty15:"+redInvoiceCounty15+",");
			//Add by donghao.guo 2011-09-19
			sb.append("preloadInvoicesCount:"+preloadInvoice+",");
			//Add by mingyang.li 2012-03-10
			sb.append("invoiceRejectCount:"+invoiceReject + ",");
			sb.append("externalApproveInvoiceCount:" + externalApproveInvoiceCount + " "); // External Approve
			
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(e.getMessage());
			throw bpe;
		}
		logger.info("Exit method getMyWorkspaceDelegationAllCountArray.");
		return sb.toString();
	}

	
	/**
	 * @author pengfei.wang
	 * With his Supervisor to show all of the User name list
	 * */
	public String getMyWorkspaceSupervisor() {
		logger.info(CcmFormat.formatEnterLog("Execute and deliver inquires about the WorkSpace Supervisor all data"));
		StringBuffer sb = new StringBuffer("[");
		String usname;
		Integer usid;
		int i=0;
		WorkspaceVO brvo = new WorkspaceVO();
		List users = userDelegationDao.findMyWorkSparsSerUsers();
		for(Object user : users){
			Object[] userFields = (Object[])user;
			usid = (Integer)userFields[0];
			usname = (String)userFields[1];
		sb.append("{uid:"+usid+",uname:\""+usname+"\",");
//		brvo.setUid(usid);
//		//Less than 5 days of data query
////		brvo.setUid(SystemUtil.getCurrentUserId());
//		String redPastDue = invoiceDao.getPastDueCount(brvo) + "";
//		brvo.setDueDay(97);
//		String redInvoiceCountr = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		//5-10 days to query data between
//		brvo.setDueDay(915);
//		String redInvoiceCounty = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		// 1-15 due days  //Add by wenbo.zhang 2015-3-18
//		brvo.setDueDay(90);
//		brvo.setUid(usid);
//		String redInvoiceCountr0 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(1);
//		brvo.setUid(usid);
//		String redInvoiceCountr1 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(2);
//		brvo.setUid(usid);
//		String redInvoiceCountr2 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(3);
//		brvo.setUid(usid);
//		String redInvoiceCountr3 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(4);
//		brvo.setUid(usid);
//		String redInvoiceCountr4 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(5);
//		brvo.setUid(usid);
//		String redInvoiceCountr5 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(6);
//		brvo.setUid(usid);
//		String redInvoiceCountr6 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(7);
//		brvo.setUid(usid);
//		String redInvoiceCounty7 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(8);
//		brvo.setUid(usid);
//		String redInvoiceCounty8 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(9);
//		brvo.setUid(usid);
//		String redInvoiceCounty9 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(10);
//		brvo.setUid(usid);
//		String redInvoiceCounty10 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(11);
//		brvo.setUid(usid);
//		String redInvoiceCounty11 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(12);
//		brvo.setUid(usid);
//		String redInvoiceCounty12 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(13);
//		brvo.setUid(usid);
//		String redInvoiceCounty13 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(14);
//		brvo.setUid(usid);
//		String redInvoiceCounty14 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		brvo.setDueDay(15);
//		brvo.setUid(usid);
//		String redInvoiceCounty15 = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		//More than 10 days of data query
//		brvo.setDueDay(111);
//		String redInvoiceCountg = invoiceDao.getInvoiceWorkCount(brvo) + "";
//		//missingInvoicesCount
//		String messis=invoiceDao.getWorkspaseCount(brvo) + "";
//		//Add by donghao.guo 2011-09-19
//		String preloadInvoice=invoiceDao.getPreloadInvoiceWorkCount(brvo) + "";
//		//Add by mingyang.li 2012-03-10
//		String invoiceReject=invoiceDao.getInvoiceRejectWorkCount(brvo) + "";
//		String unknownInvoicesCount=invoiceDao.getUnknownInvoicesCount(brvo) + "";
//		//invoiceInProcessCount
//		String paymentInProcessCount=invoiceDao.getWorkspasePaymentInProcessCount(brvo) + "";
//		String disputesInProcess=invoiceDao.getWorkspaseDisputesInProcess(brvo) + "";
//		String paymentInException=invoiceDao.getWorkspasePaymentInException(brvo) + "";
//		String banApproveCount=banDao.getBanWorkCountNOPaymentInException(brvo,4) + "";
//		String banRejectCount=banDao.getBanWorkCountNOPaymentInException(brvo,5) + "";
//
//		// External Approve Invoice Count.
//		String externalApproveInvoiceCount = invoiceDao.getWorkspaceExternalApproveInvoiceCount(brvo) + "";
//
//		
//		sb.append("redPastDue:"+redPastDue+",");
//		sb.append("redInvoiceCount:"+redInvoiceCountr+",");
//		sb.append("yellowInvoiceCount:"+redInvoiceCounty+",");
//		sb.append("greenInvoiceCount:"+redInvoiceCountg+",");
//		sb.append("unknownInvoicesCount:"+unknownInvoicesCount+",");
//		sb.append("missingInvoicesCount:"+messis+",");
//		sb.append("paymentInProcessCount:"+paymentInProcessCount+",");
//		sb.append("disputesInProcessCount:"+disputesInProcess+",");
//		sb.append("paymentInExceptionCount:"+paymentInException+",");
//		sb.append("banApproveCount:"+banApproveCount+",");
//		sb.append("banRejectCount:"+banRejectCount+",");
//		//add by wenbo.zhang 2015-3-18
//		sb.append("redInvoiceCountr0:"+redInvoiceCountr0+",");
//		sb.append("redInvoiceCountr1:"+redInvoiceCountr1+",");
//		sb.append("redInvoiceCountr2:"+redInvoiceCountr2+",");
//		sb.append("redInvoiceCountr3:"+redInvoiceCountr3+",");
//		sb.append("redInvoiceCountr4:"+redInvoiceCountr4+",");
//		sb.append("redInvoiceCountr5:"+redInvoiceCountr5+",");
//		sb.append("redInvoiceCountr6:"+redInvoiceCountr6+",");
//		sb.append("redInvoiceCounty7:"+redInvoiceCounty7+",");
//		sb.append("redInvoiceCounty8:"+redInvoiceCounty8+",");
//		sb.append("redInvoiceCounty9:"+redInvoiceCounty9+",");
//		sb.append("redInvoiceCounty10:"+redInvoiceCounty10+",");
//		sb.append("redInvoiceCounty11:"+redInvoiceCounty11+",");
//		sb.append("redInvoiceCounty12:"+redInvoiceCounty12+",");
//		sb.append("redInvoiceCounty13:"+redInvoiceCounty13+",");
//		sb.append("redInvoiceCounty14:"+redInvoiceCounty14+",");
//		sb.append("redInvoiceCounty15:"+redInvoiceCounty15+",");
//		//Add by donghao.guo 2011-09-19
//		sb.append("preloadInvoicesCount:"+preloadInvoice+",");
//		//Add by mingyang.li 2012-03-10
//		sb.append("invoiceRejectCount:"+invoiceReject+",");
//		sb.append("externalApproveInvoiceCount:" + externalApproveInvoiceCount + ", ");
//		
//		WorkspaceVO rvo = new WorkspaceVO();
//		
//		rvo.setUid(usid);
//		rvo.setDisputeDay(30);
//		String first30Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//		rvo.setDisputeDay(60);
//		String range31To60Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//		rvo.setDisputeDay(90);
//		String range61To90Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//		rvo.setDisputeDay(120);
//		String range91To120Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//		rvo.setDisputeDay(180);
//		String range121To180Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//		rvo.setDisputeDay(0);
//		String over180Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
//		rvo.setDisputeDay(-1);
//		String totalDisputesBucket = disputeDao.searchDisputeCountByDays(rvo) + "";
//		sb.append("totalDisputesBucket:"+totalDisputesBucket+",");
//		sb.append("first30Disputes:"+first30Disputes+",");
//		sb.append("range31To60Disputes:"+range31To60Disputes+",");
//		sb.append("range61To90Disputes:"+range61To90Disputes+",");
//		sb.append("range91To120Disputes:"+range91To120Disputes+",");
//		sb.append("range121To180Disputes:"+range121To180Disputes+",");
//		sb.append("over180Disputes:"+over180Disputes);
		sb.append("}");
		
		i++;
		if(i < users.size()){
			sb.append(",");
		}
		}
		sb.append("]");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getMyWorkspaceMyTeamUserCounts(WorkspaceVO brvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("MyWorkspace MyTeam User Counts"));
		StringBuffer sb = new StringBuffer("{");

		//Less than 5 days of data query
//		brvo.setUid(SystemUtil.getCurrentUserId());
		String redPastDue = invoiceDao.getPastDueCount(brvo) + "";
		brvo.setDueDay(97);
		String redInvoiceCountr = invoiceDao.getInvoiceWorkCount(brvo) + "";
		//5-10 days to query data between
		brvo.setDueDay(915);
		String redInvoiceCounty = invoiceDao.getInvoiceWorkCount(brvo) + "";
		// 1-15 due days  //Add by wenbo.zhang 2015-3-18
		brvo.setDueDay(90);

		String redInvoiceCountr0 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(1);

		String redInvoiceCountr1 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(2);

		String redInvoiceCountr2 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(3);

		String redInvoiceCountr3 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(4);

		String redInvoiceCountr4 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(5);

		String redInvoiceCountr5 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(6);

		String redInvoiceCountr6 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(7);

		String redInvoiceCounty7 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(8);

		String redInvoiceCounty8 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(9);

		String redInvoiceCounty9 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(10);

		String redInvoiceCounty10 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(11);

		String redInvoiceCounty11 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(12);

		String redInvoiceCounty12 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(13);

		String redInvoiceCounty13 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(14);

		String redInvoiceCounty14 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		brvo.setDueDay(15);

		String redInvoiceCounty15 = invoiceDao.getInvoiceWorkCount(brvo) + "";
		//More than 10 days of data query
		brvo.setDueDay(111);
		String redInvoiceCountg = invoiceDao.getInvoiceWorkCount(brvo) + "";
		//missingInvoicesCount
		String messis=invoiceDao.getWorkspaseCount(brvo) + "";
		//Add by donghao.guo 2011-09-19
		String preloadInvoice=invoiceDao.getPreloadInvoiceWorkCount(brvo) + "";
		//Add by mingyang.li 2012-03-10
		String invoiceReject=invoiceDao.getInvoiceRejectWorkCount(brvo) + "";
		String unknownInvoicesCount=invoiceDao.getUnknownInvoicesCount(brvo) + "";
		//invoiceInProcessCount
		String paymentInProcessCount=invoiceDao.getWorkspasePaymentInProcessCount(brvo) + "";
		String disputesInProcess=invoiceDao.getWorkspaseDisputesInProcess(brvo) + "";
		String paymentInException=invoiceDao.getWorkspasePaymentInException(brvo) + "";
		String banApproveCount=banDao.getBanWorkCountNOPaymentInException(brvo,4) + "";
		String banRejectCount=banDao.getBanWorkCountNOPaymentInException(brvo,5) + "";

		// External Approve Invoice Count.
		String externalApproveInvoiceCount = invoiceDao.getWorkspaceExternalApproveInvoiceCount(brvo) + "";

		
		sb.append("redPastDue:"+redPastDue+",");
		sb.append("redInvoiceCount:"+redInvoiceCountr+",");
		sb.append("yellowInvoiceCount:"+redInvoiceCounty+",");
		sb.append("greenInvoiceCount:"+redInvoiceCountg+",");
		sb.append("unknownInvoicesCount:"+unknownInvoicesCount+",");
		sb.append("missingInvoicesCount:"+messis+",");
		sb.append("paymentInProcessCount:"+paymentInProcessCount+",");
		sb.append("disputesInProcessCount:"+disputesInProcess+",");
		sb.append("paymentInExceptionCount:"+paymentInException+",");
		sb.append("banApproveCount:"+banApproveCount+",");
		sb.append("banRejectCount:"+banRejectCount+",");
		//add by wenbo.zhang 2015-3-18
		sb.append("redInvoiceCountr0:"+redInvoiceCountr0+",");
		sb.append("redInvoiceCountr1:"+redInvoiceCountr1+",");
		sb.append("redInvoiceCountr2:"+redInvoiceCountr2+",");
		sb.append("redInvoiceCountr3:"+redInvoiceCountr3+",");
		sb.append("redInvoiceCountr4:"+redInvoiceCountr4+",");
		sb.append("redInvoiceCountr5:"+redInvoiceCountr5+",");
		sb.append("redInvoiceCountr6:"+redInvoiceCountr6+",");
		sb.append("redInvoiceCounty7:"+redInvoiceCounty7+",");
		sb.append("redInvoiceCounty8:"+redInvoiceCounty8+",");
		sb.append("redInvoiceCounty9:"+redInvoiceCounty9+",");
		sb.append("redInvoiceCounty10:"+redInvoiceCounty10+",");
		sb.append("redInvoiceCounty11:"+redInvoiceCounty11+",");
		sb.append("redInvoiceCounty12:"+redInvoiceCounty12+",");
		sb.append("redInvoiceCounty13:"+redInvoiceCounty13+",");
		sb.append("redInvoiceCounty14:"+redInvoiceCounty14+",");
		sb.append("redInvoiceCounty15:"+redInvoiceCounty15+",");
		//Add by donghao.guo 2011-09-19
		sb.append("preloadInvoicesCount:"+preloadInvoice+",");
		//Add by mingyang.li 2012-03-10
		sb.append("invoiceRejectCount:"+invoiceReject+",");
		sb.append("externalApproveInvoiceCount:" + externalApproveInvoiceCount + ", ");
		
		WorkspaceVO rvo = new WorkspaceVO();
		rvo.setUid(brvo.getUid());
		rvo.setDisputeDay(30);
		String first30Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(60);
		String range31To60Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(90);
		String range61To90Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(120);
		String range91To120Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(180);
		String range121To180Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(0);
		String over180Disputes = disputeDao.searchDisputeCountByDays(rvo) + "";
		rvo.setDisputeDay(-1);
		String totalDisputesBucket = disputeDao.searchDisputeCountByDays(rvo) + "";
		sb.append("totalDisputesBucket:"+totalDisputesBucket+",");
		sb.append("first30Disputes:"+first30Disputes+",");
		sb.append("range31To60Disputes:"+range31To60Disputes+",");
		sb.append("range61To90Disputes:"+range61To90Disputes+",");
		sb.append("range91To120Disputes:"+range91To120Disputes+",");
		sb.append("range121To180Disputes:"+range121To180Disputes+",");
		sb.append("over180Disputes:"+over180Disputes);
		
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	};
	
		/**
		 * The data into the closure circulation data
		 * */
		public String searchWorkspaceMissing(WorkspaceVO workspaceVO)throws BPLException {
			logger.info("Enter method searchWorkspaceMissing.");
			StringBuffer sb = new StringBuffer();
			List<String> list = null;
			try {
				list = invoiceDao.searchWorkspase(workspaceVO);
			} catch (RuntimeException e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			if(list!=null&&list.size()>0){
				sb.append("{begin:");
				sb.append(workspaceVO.getStartIndex()+1);
				sb.append(",end:");
				int size = list.size();
				sb.append(workspaceVO.getStartIndex()+size);
				sb.append(",data:[");
				for(int i = 0; i<size; i++){
					Object str = list.get(i);
					if(str == null){
						break;
					}else {
						if(str instanceof SerializableBlob){
							str = BlobUtil.getBlobString((SerializableBlob)str);
						}
						if(i!=0) sb.append(",");
						sb.append(str.toString());
					}
				}
				sb.append("]");
			}else{
				sb.append("{error:\"failed to query.\"");
			}
			sb.append("}");
			logger.info("Exit method searchWorkspaceMissing.");
			return sb.toString();
		}
		/**
		 * @author pengfei.wang
		 * The data into the closure circulation data
		 * */
		public String searchWorkspaceProsing(WorkspaceVO workspaceVO)throws BPLException {
			logger.info(CcmFormat.formatEnterLog("Execute and deliver inquires about the WorkSpace Process all data"));
			StringBuffer sb = new StringBuffer();
			List<String> list = null;
			try {
				if(workspaceVO.getDueDay() == -1){
					list=invoiceDao.searchWorkspasePaymentInProcess(workspaceVO);
				}else if(workspaceVO.getDueDay() == -2){
					list=invoiceDao.searchWorkspaseDisputesInProcess(workspaceVO);
				}else if(workspaceVO.getDueDay() == -3){
					list=invoiceDao.searchWorkspasePaymentInException(workspaceVO);
				}else{
					System.out.println("s");
				}			
				//list = invoiceDao.searchWorkspaseProcess(workspaceVO);
			} catch (Exception e) {
				logger.error(CcmFormat.formatErrorLog(e));
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
			}
			if(list!=null&&list.size()>0){
				sb.append("{begin:");
				sb.append(workspaceVO.getStartIndex()+1);
				sb.append(",end:");
				int size = list.size();
				sb.append(workspaceVO.getStartIndex()+size);
				sb.append(",data:[");
				for(int i = 0; i<size; i++){
					Object str = list.get(i);
					if(str == null){
						break;
					}else {
						if(str instanceof SerializableBlob){
							str = BlobUtil.getBlobString((SerializableBlob)str);
						}
						if(i!=0) sb.append(",");
						sb.append(str.toString());
					}
				}
				sb.append("]");
			}else{
				sb.append("{error:\"failed to query.\"");
			}
			sb.append("}");
			logger.info(CcmFormat.formatExitLog());
			return sb.toString();
		}
		
	/**
	 * @param currencyDao the currencyDao to set
	 */
	public void setCurrencyDao(ICurrencyDao currencyDao) {
		this.currencyDao = currencyDao;
	}

	/**
	 * @param invoiceStatusDao the invoiceStatusDao to set
	 */
	public void setInvoiceStatusDao(IInvoiceStatusDao invoiceStatusDao) {
		this.invoiceStatusDao = invoiceStatusDao;
	}

	
	public void setDisputeStatusDao(IDisputeStatusDao disputeStatusDao) {
		this.disputeStatusDao = disputeStatusDao;
	}
	
	public void setDisputeReasonDao(IDisputeReasonDao disputeReasonDao) {
		this.disputeReasonDao = disputeReasonDao;
	}

	/**
	 * @param paymentStatusDao the paymentStatusDao to set
	 */
	public void setPaymentStatusDao(IPaymentStatusDao paymentStatusDao) {
		this.paymentStatusDao = paymentStatusDao;
	}
	/**
	 * @param vendorDao the vendorDao to set
	 */
	public void setVendorDao(IVendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}

	/**
	 * @param banDao the banDao to set
	 */
	public void setBanDao(IBanDao banDao) {
		this.banDao = banDao;
	}
	/**
	 * @param masterInventoryDao the masterInventoryDao to set
	 */
	public void setMasterInventoryDao(IMasterInventoryDao masterInventoryDao) {
		this.masterInventoryDao = masterInventoryDao;
	}

	public void setUserDelegationDao(IUserDelegationDao userDelegationDao) {
		this.userDelegationDao = userDelegationDao;
	}

	public void setInvoiceDao(IInvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	public void setDisputeDao(IDisputeDao disputeDao) {
		this.disputeDao = disputeDao;
	}

	public void setPaymentDao(IPaymentDao paymentDao) {
		this.paymentDao = paymentDao;
	}

	public ICreditStatusDao getCreditStatusDao() {
		return creditStatusDao;
	}

	public void setCreditStatusDao(ICreditStatusDao creditStatusDao) {
		this.creditStatusDao = creditStatusDao;
	}

	/**
	 * @param createdByDao the createdByDao to set
	 */
	public void setCreatedByDao(ICreatedByDao createdByDao) {
		this.createdByDao = createdByDao;
	}

	public void setCreditDao(ICreditDao creditDao) {
		this.creditDao = creditDao;
	}

	public ITicketStatusDao getTicketStatusDao() {
		return ticketStatusDao;
	}

	public void setTicketStatusDao(ITicketStatusDao ticketStatusDao) {
		this.ticketStatusDao = ticketStatusDao;
	}

	public IPriorityDao getPriorityDao() {
		return priorityDao;
	}

	public void setPriorityDao(IPriorityDao priorityDao) {
		this.priorityDao = priorityDao;
	}

	public ISeverityDao getSeverityDao() {
		return severityDao;
	}

	public void setSeverityDao(ISeverityDao severityDao) {
		this.severityDao = severityDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	public Logger getLogger() {
		return logger;
	}

	public ICurrencyDao getCurrencyDao() {
		return currencyDao;
	}

	public IInvoiceStatusDao getInvoiceStatusDao() {
		return invoiceStatusDao;
	}

	public IDisputeStatusDao getDisputeStatusDao() {
		return disputeStatusDao;
	}

	public IPaymentStatusDao getPaymentStatusDao() {
		return paymentStatusDao;
	}

	public IDisputeReasonDao getDisputeReasonDao() {
		return disputeReasonDao;
	}

	public ICreatedByDao getCreatedByDao() {
		return createdByDao;
	}

	public IVendorDao getVendorDao() {
		return vendorDao;
	}

	public IBanDao getBanDao() {
		return banDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public IUserDelegationDao getUserDelegationDao() {
		return userDelegationDao;
	}

	public IInvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	public IDisputeDao getDisputeDao() {
		return disputeDao;
	}

	public IPaymentDao getPaymentDao() {
		return paymentDao;
	}

	public ICreditDao getCreditDao() {
		return creditDao;
	}

	public IRoleDao getRoleDao() {
		return roleDao;
	}

	public void setRoleDao(IRoleDao roleDao) {
		this.roleDao = roleDao;
	}

	public ILabelDao getLabelDao() {
		return labelDao;
	}

	public void setLabelDao(ILabelDao labelDao) {
		this.labelDao = labelDao;
	}

	public IAccountCodelDao getAccountCodelDao() {
		return accountCodelDao;
	}

	public void setAccountCodelDao(IAccountCodelDao accountCodelDao) {
		this.accountCodelDao = accountCodelDao;
	}

	public IFunctionDao getFunctionDao() {
		return functionDao;
	}

	public void setFunctionDao(IFunctionDao functionDao) {
		this.functionDao = functionDao;
	}

	public void setRtagDao(IRtagDao rtagDao) {
		this.rtagDao = rtagDao;
	}

	public IAttachmentFileDao getAttachmentFileDao() {
		return attachmentFileDao;
	}

	public void setAttachmentFileDao(IAttachmentFileDao attachmentFileDao) {
		this.attachmentFileDao = attachmentFileDao;
	}

	public IRtagDao getRtagDao() {
		return rtagDao;
	}
	
	

	public IOriginatorDao getOriginatorDao() {
		return originatorDao;
	}

	public void setOriginatorDao(IOriginatorDao originatorDao) {
		this.originatorDao = originatorDao;
	}

	/**
	 * Jian.Dong
	 * */
	public void saveUploadFile(String tableName, Integer id,List<File> files,List<String> fileNames) {
		logger.info("Enter method saveUploadFile.");
		if(files!=null && files.size()>0){
			Integer apid = userDao.findAttachmentPointId(tableName,id);
			if(apid == null){
				AttachmentPoint ap = new AttachmentPoint();
				ap.setTableName(tableName);
				ap.setTableIdValue(id);
				ap.setCreatedBy(SystemUtil.getCurrentUserId());
				ap.setCreatedTimestamp(new Date());
				ap.setModifiedBy(SystemUtil.getCurrentUserId());
				ap.setModifiedTimestamp(new Date());
				ap.setRecActiveFlag("Y");
				Integer newId = attachmentFileDao.createPoint(ap);
				userDao.updateAttachmentPointId(tableName,id,newId);
				apid = newId;
			}
			String dirPath = getFilePath();
			File dir = new File(dirPath);
			if(dir.exists() && !dir.isDirectory()){
				dir.delete();
			}
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			for(int i=0;i<files.size();i++){
				File in = files.get(i);
				File out = null;
				String outPath = null;
				String fname = null;
				if(fileNames != null){
					fname = (fileNames.get(i)==null) ? in.getName() : fileNames.get(i);
					outPath = dirPath + "/" + UUID.randomUUID().toString()+fname;
					out = new File(outPath);
				}
				try {
					FileUtil.copyFile(in, out);
				} catch (IOException e) {
					logger.error(CcmFormat.formatErrorLog(e));
					throw new RuntimeException("file copy error!");
				}
				
				AttachmentFile af = new AttachmentFile();
				af.setFilePath(outPath);
				af.setFileName(fname);
				af.setAttachmentPoint(attachmentFileDao.loadPoint(apid));
				af.setCreatedBy(SystemUtil.getCurrentUserId());
				af.setCreatedTimestamp(new Date());
				af.setModifiedBy(SystemUtil.getCurrentUserId());
				af.setModifiedTimestamp(new Date());
				af.setRecActiveFlag("Y");
				attachmentFileDao.save(af);
			}
		}
		logger.info("Exit method saveUploadFile.");
	}
	
	/**
	 * add by mingyang.li on 2012-11-22
	 * */
	public void saveUploadFileToTariffLinkByVendor (List<File> files,List<String> fileNames, TariffLink params ) {
		logger.info("Enter method saveUploadFileToTariffLinkByVendor.");
		if(files!=null && files.size()>0){
			String dirPath = getTariffFilePath();
			SimpleDateFormat sdfInputSS = new SimpleDateFormat("yyyyMMdd");
			String datess=sdfInputSS.format(new Date());
			File dir = new File(dirPath + "/" +datess);
			if(dir.exists() && !dir.isDirectory()){
				dir.delete();
			}
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			for(int i=0;i<files.size();i++){
				File in = files.get(i);
				String outPath = null;
				String fname = null;
				if(fileNames != null){
					fname = (fileNames.get(i)==null) ? in.getName() : fileNames.get(i);
					outPath = dirPath + "/" +datess+"/"+ UUID.randomUUID().toString()+fname;
					params.setTariffName(fname);
					params.setTariffPath(outPath);
				}
				saveUploadFileAndTariffLink(in,params);
			}
		}
		logger.info("Exit method saveUploadFileToTariffLinkByVendor.");
	}
	
	/**
	 * add by mingyang.li on 2012-11-22
	 * */
	private void saveUploadFileAndTariffLink(File in ,TariffLink params) {
		logger.info("Enter method saveUploadFileAndTariffLink.");
				File out = null;
				if(params.getTariffPath() != null){
					out = new File(params.getTariffPath());
				}
				try {
					FileUtil.copyFile(in, out);
					TariffLink tariffLink = new TariffLink();
					if (params.getVendorId() != null) {
						tariffLink.setVendorId(params.getVendorId());
					}
					if (params.getBanId() != null) {
						tariffLink.setBanId(params.getBanId());
					}
					if (params.getCircuitId() != null) {
						tariffLink.setCircuitId(params.getCircuitId());
					}
					tariffLink.setTariffName(params.getTariffName());
					tariffLink.setTariffPath(params.getTariffPath());
					tariffLink.setNotes(params.getNotes());
					tariffLink.setCreatedBy(SystemUtil.getCurrentUserId());
					tariffLink.setCreatedTimestamp(new Date());
					tariffLink.setModifiedBy(SystemUtil.getCurrentUserId());
					tariffLink.setModifiedTimestamp(new Date());
					tariffLink.setRecActiveFlag("Y");
					tariffLinkDao.save(tariffLink);
				} catch (IOException e) {
					logger.error(CcmFormat.formatErrorLog(e));
					throw new RuntimeException("file copy error!");
				}
				
		logger.info("Exit method saveUploadFileAndTariffLink.");
	}
	
	public void saveUploadAttachFileByBanExemption (List<File> files,List<String> fileNames, Map<String, String> banExemption ) {
		logger.info("Enter method saveUploadAttachFileByBanExemption.");
		if(files!=null && files.size()>0){
			String dirPath = getTariffFilePath();
			SimpleDateFormat sdfInputSS = new SimpleDateFormat("yyyyMMdd");
			String datess=sdfInputSS.format(new Date());
			File dir = new File(dirPath + "/" +datess);
			if(dir.exists() && !dir.isDirectory()){
				dir.delete();
			}
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			for(int i=0;i<files.size();i++){
				File in = files.get(i);
				String outPath = null;
				String fname = null;
				if(fileNames != null){
					fname = (fileNames.get(i)==null) ? in.getName() : fileNames.get(i);
					outPath = dirPath + "/" +datess+"/"+ UUID.randomUUID().toString()+fname;
					banExemption.put("certificateName", fname);
					banExemption.put("filePath", outPath);
				}
				saveUploadFileAndBanExemptionLink(in,banExemption);
			}
		}
		logger.info("Exit method saveUploadAttachFileByBanExemption.");
	}
	
	public void uploadExemption (Map<String, String> banExemption ) throws BPLException{
		logger.info("Enter method uploadExemption.");
		
		
		try {
			banDao.updateBanExemption(banExemption);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		logger.info("Exit method uploadExemption.");
	}
	
	private void saveUploadFileAndBanExemptionLink(File in ,Map<String, String> banExemption) {
		logger.info("Enter method saveUploadFileAndBanExemptionLink.");
				File out = null;
				out = new File(banExemption.get("filePath"));
				try {
					FileUtil.copyFile(in, out);
					banDao.saveBanExemption(banExemption);
				} catch (IOException e) {
					logger.error(CcmFormat.formatErrorLog(e));
					throw new RuntimeException("file copy error!");
				}
				
		logger.info("Exit method saveUploadFileAndBanExemptionLink.");
	}
	
	/**
	 * add by xin.huang on 2012-9-25
	 * */
	public List<TariffLink> saveUploadFileToTariffLink(List<File> files,List<String> fileNames, TariffLink params) {
		logger.info("Enter method saveUploadFileToTariffLink.");
		List<TariffLink> result = new ArrayList<TariffLink>();
		if(files!=null && files.size()>0){
			String dirPath = getFilePath();
			File dir = new File(dirPath);
			if(dir.exists() && !dir.isDirectory()){
				dir.delete();
			}
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			for(int i=0;i<files.size();i++){
				File in = files.get(i);
				File out = null;
				String outPath = null;
				String fname = null;
				if(fileNames != null){
					fname = (fileNames.get(i)==null) ? in.getName() : fileNames.get(i);
					outPath = dirPath + "/" + UUID.randomUUID().toString()+fname;
					out = new File(outPath);
				}
				try {
					FileUtil.copyFile(in, out);
					TariffLink tariffLink = new TariffLink();
					if (params.getVendorId() != null) {
						tariffLink.setVendorId(params.getVendorId());
					}
					if (params.getBanId() != null) {
						tariffLink.setBanId(params.getBanId());
					}
					if (params.getCircuitId() != null) {
						tariffLink.setCircuitId(params.getCircuitId());
					}
					tariffLink.setTariffName(fname);
					tariffLink.setTariffPath(outPath);
					tariffLink.setNotes(params.getNotes());
					tariffLink.setCreatedBy(SystemUtil.getCurrentUserId());
					tariffLink.setCreatedTimestamp(new Date());
					tariffLink.setModifiedBy(SystemUtil.getCurrentUserId());
					tariffLink.setModifiedTimestamp(new Date());
					tariffLink.setRecActiveFlag("Y");
					tariffLinkDao.save(tariffLink);
					result.add(tariffLink);
				} catch (IOException e) {
					logger.error(CcmFormat.formatErrorLog(e));
					throw new RuntimeException("file copy error!");
				}
				
			}
		}
		logger.info("Exit method saveUploadFileToTariffLink.");
		return result;
	}
	
	public AttachmentPoint createAttachmentPoint(String tableName){
		AttachmentPoint ap = new AttachmentPoint();
		ap.setTableName(tableName);
		ap.setCreatedBy(SystemUtil.getCurrentUserId());
		ap.setCreatedTimestamp(new Date());
		ap.setModifiedBy(SystemUtil.getCurrentUserId());
		ap.setModifiedTimestamp(new Date());
		ap.setRecActiveFlag("Y");
		attachmentFileDao.createPoint(ap);
		return ap;
	}
	
	/**
	 * Jian.Dong
	 * */
	public AttachmentPoint saveUploadFileAndGetPointId(String tableName,List<File> files,List<String> fileNames) {
		return saveUploadFileAndGetPointId(tableName,files,fileNames,null);
	}
	public AttachmentPoint saveUploadFileAndGetPointId(String tableName,List<File> files,List<String> fileNames,String attachmentPointId) {
		logger.info("Enter method saveUploadFileAndGetPointId.");
		AttachmentPoint ap = null;
		if(files!=null && files.size()>0){
			ap = new AttachmentPoint();
			if(attachmentPointId != null && !"".equals(attachmentPointId)){
				ap.setId(Integer.valueOf(attachmentPointId).intValue());
			}else{			
				ap = createAttachmentPoint(tableName);
			}
			String dirPath = getFilePath();
			File dir = new File(dirPath);
			if(dir.exists() && !dir.isDirectory()){
				dir.delete();
			}
			if(!dir.exists()){
				dir.mkdirs();
			}
			
			for(int i=0;i<files.size();i++){
				File in = files.get(i);
				File out = null;
				String outPath = null;
				String fname = null;
				if(fileNames != null){
					fname = (fileNames.get(i)==null) ? in.getName() : fileNames.get(i);
					String fileType = "";
					if(fname.lastIndexOf(".") != -1){
						fileType = fname.substring(fname.lastIndexOf("."),fname.length());
					}
					String fileName = UUID.randomUUID().toString() + fileType;
					outPath = dirPath + "/" + fileName;
					out = new File(outPath);
				}
				try {
					FileUtil.copyFile(in, out);
					AttachmentFile af = new AttachmentFile();
					af.setFilePath(outPath);
					af.setFileName(fname);
					af.setAttachmentPoint(ap);
					af.setCreatedBy(SystemUtil.getCurrentUserId());
					af.setCreatedTimestamp(new Date());
					af.setModifiedBy(SystemUtil.getCurrentUserId());
					af.setModifiedTimestamp(new Date());
					af.setRecActiveFlag("Y");
					attachmentFileDao.save(af);
				} catch (IOException e) {
					logger.error(CcmFormat.formatErrorLog(e));
					throw new RuntimeException("file copy error!");
				}
				
			}
		}
		logger.info("Exit method saveUploadFileAndGetPointId.");
		return ap;
	}
	/**
	 * Jian.Dong
	 * */
	public List<AttachmentFile> saveUploadFileToPointId(AttachmentPoint ap,List<File> files,List<String> fileNames) {
		logger.info("Enter method saveUploadFileAndGetPointId.");
		List<AttachmentFile> attachmentFile = new ArrayList<AttachmentFile>();
		if(files!=null && files.size()>0){
			String dirPath = getFilePath();
			File dir = new File(dirPath);
			
			if(dir.exists() && !dir.isDirectory()){
				dir.delete();
			}
			if(!dir.exists()){
				dir.mkdirs();
			}
		
			for(int i=0;i<files.size();i++){
				File in = files.get(i);
				File out = null;
				String outPath = null;
				String fname = null;
				if(fileNames != null){
					fname = (fileNames.get(i)==null) ? in.getName() : fileNames.get(i);
					outPath = dirPath + "/" + UUID.randomUUID().toString()+fname;
					out = new File(outPath);
				}
				try {
					FileUtil.copyFile(in, out);
					AttachmentFile af = new AttachmentFile();
					af.setFilePath(outPath);
					af.setFileName(fname);
					af.setAttachmentPoint(ap);
					af.setCreatedBy(SystemUtil.getCurrentUserId());
					af.setCreatedTimestamp(new Date());
					af.setModifiedBy(SystemUtil.getCurrentUserId());
					af.setModifiedTimestamp(new Date());
					af.setRecActiveFlag("Y");
					attachmentFileDao.save(af);
					attachmentFile.add(af);
				} catch (IOException e) {
					logger.error(CcmFormat.formatErrorLog(e));
					throw new RuntimeException("file copy error!");
				}
				
			}
		}
		logger.info("Exit method saveUploadFileAndGetPointId.");
		return attachmentFile;
	}

	public String getFilePath(){
		return sysConfigDAO.getUploadFilePath();
	}
	
	private String getTariffFilePath(){
		return sysConfigDAO.getTariffFilePath();
	}
	
	/**
	 * @author wei.su
	 * Get all Originator list.
	 */
	public List<MapEntryVO<String, String>> getAllOriginators() throws BPLException {
		logger.info("Enter method getAllOriginators.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> originatorList = null;
		try {
			originatorList = originatorDao.findAll();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object o : originatorList){
			Object[] originator = (Object[])o;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(originator[0].toString(), originator[1].toString());
			l.add(m);
		}
		logger.info("Exit method getAllOriginators.");
		return l;
	}
	
	/**
	 * @author wei.su
	 * Get all dispute list.
	 */
	@SuppressWarnings("unchecked")
	public List<MapEntryVO<String, String>> getDisputeByInvoiceId(int invoiceId) throws BPLException {
		logger.info("Enter method getDisputeByInvoiceId.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List disputeList = null;
		try {
			disputeList = disputeDao.findByInvoiceId(invoiceId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object d : disputeList){
			Object[] dispute = (Object[])d;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(dispute[0].toString(), dispute[1].toString());
			l.add(m);
		}
		logger.info("Exit method getDisputeByInvoiceId.");
		return l;
	}
 
	/**
	 * Get All Ban
	 * @Author Chao.Liu Date: Nov 1, 2010
	 * @return
	 * @throws BPLException
	 */
	public List<MapEntryVO<String, String>> getAllBans()  throws BPLException{
		logger.info("Enter method getAllBans.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List banList = null;
		try {
			banList = banDao.getAllBan();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : banList){
			Object[] ban = (Object[])b; 
			String key = ((Integer)ban[0]).toString();
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(key, (String)ban[1]);
			l.add(m);
		}
		logger.info("Exit method getAllBans.");
		return l;
	}

	public List<MapEntryVO<String, String>> getThemes() throws BPLException {
		logger.info("Enter method getThemes.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List<Theme> themeList = null;
		try {
			themeList = userDao.findThemes();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Theme d : themeList){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(d.getId().toString(), d.getThemeName());
			l.add(m);
		}
		logger.info("Exit method getThemes.");
		return l;
	}
	
 
	/**
	 * @author pengfei.wang
	 * Statistics for the number of requirements with inquires
	 * */
	public String getAttachmentPointIdCountService(SearchInvoiceVO svo) throws BPLException {
		logger.info("Enter method getAttachmentPointIdCountService.");
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = attachmentFileDao.getAttachmentPointIdCount(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info("Exit method getAttachmentPointIdCountService.");
		return sb.toString();
	}
	public String getDisputeAttachmentPointIdCountService(SearchInvoiceVO svo) throws BPLException {
		logger.info("Enter method getDisputeAttachmentPointIdCountService.");
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = attachmentFileDao.getDisputeAttachmentPointIdCountDao(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info("Exit method getDisputeAttachmentPointIdCountService.");
		return sb.toString();
	}
	public String getDisputeSearchAttachmentPointIdService(SearchInvoiceVO svo)throws BPLException {
		logger.info("Enter method getDisputeSearchAttachmentPointIdService.");
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = attachmentFileDao.getDisputeSearchAttachmentPointIdDao(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(svo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(svo.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				Object str = list.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"Not found info to DB.\"");
		}
		sb.append("}");
		logger.info("Exit method getDisputeSearchAttachmentPointIdService.");
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * To meet the conditions of the data into List
	 * */
	public String searchAttachmentPointIdService(SearchInvoiceVO svo)throws BPLException {
		logger.info("Enter method searchAttachmentPointIdService.");
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = attachmentFileDao.searchAttachmentPointId(svo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(svo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(svo.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				Object str = list.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"Not found info to DB.\"");
		}
		sb.append("}");
		logger.info("Exit method searchAttachmentPointIdService.");
		return sb.toString();
	}
	public List searchAttachmentPointIdList(SearchInvoiceVO svo)throws BPLException {
		logger.info("Enter method searchAttachmentPointIdList.");
		List list =null;
		try {
			 list = attachmentFileDao.searchAttachmentPointIdList(svo);

		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}

		logger.info("Exit method searchAttachmentPointIdList.");
		return list;
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

	public Theme getUserTheme() throws BPLException {
		return userDao.getTheme();
	}

	public String changeUserTheme(Integer themeId) {
		try {
			Random rd1 = new Random();
			Integer randowThemeId = (rd1.nextInt(19)+1);
			Theme t = userDao.updateUserTheme(themeId,randowThemeId);
			SystemUtil.getCurrentUser().setTheme(t);
			if(themeId.intValue() == 0)
				SystemUtil.getCurrentUser().setRandomtheme(userDao.findThemesById(randowThemeId));
			return "1";
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			throw e;
		}
	}

	public ISysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(ISysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	public IEventJournalDao getEventJournalDao() {
		return eventJournalDao;
	}

	public void setEventJournalDao(IEventJournalDao eventJournalDao) {
		this.eventJournalDao = eventJournalDao;
	}
	
	public void saveEvent(String messageType,String eventMessage,String keyData,String eventData){
		EventJournal ej = new EventJournal();
		ej.setMessageType(messageType);
		ej.setKeyData(keyData);
		ej.setEventMessage(eventMessage);
		ej.setEventData(eventData);
		ej.setIpAddress(SystemUtil.getCurrentUser().getIpAddress());
		ej.setCreatedTimestamp(new Date());
		ej.setCreatedBy(SystemUtil.getCurrentUserId());
		eventJournalDao.save(ej);
	}
	
	/**
	 * Verify whether the user to BAN, permissions
	 * */
	public boolean validatePreviledgeByBan(Ban ban){
		if(ban == null) return false;
		UserPrivilege up = securityManagementDao.findUserPrivilegeByBan(ban);
		if(up != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Verify whether the user to VENDOR, permissions
	 * */
	public boolean validatePreviledgeByVendor(Vendor vendor){
		if(vendor == null) return false;
		UserPrivilege up = securityManagementDao.findUserPrivilegeByVendor(vendor);
		if(up != null){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * find User Privilege
	 * */
	public boolean findUserPrivilege(){
		UserPrivilege up = securityManagementDao.findUserPrivilege();
		if(up != null){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Verify whether the user to BAN has designated VENDOR, permissions
	 * */
	public boolean validatePreviledgeByVendorAndBan(Ban ban, Vendor vendor){
		if(validatePreviledgeByBan(ban)){
			return true;
		}else if(validatePreviledgeByVendor(vendor)){
			return true;
		}else{
			return findUserPrivilege();
		}
	}
	
	/**
	 * Through the Delegation, validating user permissions
	 * */
	public boolean validateBackupAndDelegation(String currentAssignmentId){
		if(validateBackupByCurrentAnalystId(currentAssignmentId)){
			return true;
		}else if(validateDelegationByCurrentAnalystId(currentAssignmentId)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * Through the Delegation, validating user permissions
	 * */
	public boolean validateDelegationByCurrentAnalystId(String currentAssignmentId){
		long count = userDelegationDao.findDelegationToUserId(currentAssignmentId);
		if(count != 0) {
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * Through the Backup, validating user permissions
	 * */
	public boolean validateBackupByCurrentAnalystId(String currentAssignmentId){
		User user = userDao.findById(SystemUtil.getCurrentUserId());
		Integer backupUserId = user.getBackupUserId();
		if(backupUserId!=null && backupUserId.intValue() == Integer.parseInt(currentAssignmentId)){
			return true;
		}else{
			return false;
		} 
	}

	public ISecurityManagementDao getSecurityManagementDao() {
		return securityManagementDao;
	}

	public void setSecurityManagementDao(
			ISecurityManagementDao securityManagementDao) {
		this.securityManagementDao = securityManagementDao;
	}

	public IContactDao getContactDao() {
		return contactDao;
	}

	public void setContactDao(IContactDao contactDao) {
		this.contactDao = contactDao;
	}


	public List<MapEntryVO<String, String>> getNYList() throws BPLException {
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		MapEntryVO<String, String> n = new MapEntryVO<String, String>("N", "N");
		MapEntryVO<String, String> y = new MapEntryVO<String, String>("Y", "Y");
		l.add(n);
		l.add(y);
		return l;
	}

	public List<MapEntryVO<String, String>> getYNList() throws BPLException {
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		MapEntryVO<String, String> n = new MapEntryVO<String, String>("N", "N");
		MapEntryVO<String, String> y = new MapEntryVO<String, String>("Y", "Y");
		l.add(y);
		l.add(n);
		return l;
	}
	
	public List<MapEntryVO<String, String>> getMNList() throws BPLException {
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		MapEntryVO<String, String> m = new MapEntryVO<String, String>("", " ");
		MapEntryVO<String, String> n = new MapEntryVO<String, String>("N", "N");
		MapEntryVO<String, String> y = new MapEntryVO<String, String>("Y", "Y");
		l.add(m);
		l.add(y);
		l.add(n);
		return l;
	}

	public List<MapEntryVO<String, String>> scoaCodeTypeList() throws BPLException {
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		MapEntryVO<String, String> none = new MapEntryVO<String, String>("", "");
		MapEntryVO<String, String> CIRCUIT_SCOA_CODE = new MapEntryVO<String, String>("CIRCUIT_SCOA_CODE", "CIRCUIT_SCOA_CODE");
		MapEntryVO<String, String> BAN_SINGLE_CODE = new MapEntryVO<String, String>("BAN_SINGLE_CODE", "BAN_SINGLE_CODE");
		MapEntryVO<String, String> BAN_ITEM_TYPE = new MapEntryVO<String, String>("BAN_ITEM_TYPE", "BAN_ITEM_TYPE");
		MapEntryVO<String, String> LINE_OF_BUSINESS = new MapEntryVO<String, String>("LINE_OF_BUSINESS", "LINE_OF_BUSINESS");
		MapEntryVO<String, String> LOB_ITEM_TYPE = new MapEntryVO<String, String>("LOB_ITEM_TYPE", "LOB_ITEM_TYPE");
		l.add(none);
		l.add(CIRCUIT_SCOA_CODE);
		l.add(BAN_SINGLE_CODE);
		l.add(BAN_ITEM_TYPE);
		l.add(LINE_OF_BUSINESS);
		l.add(LOB_ITEM_TYPE);
		return l;
	}

	public List<MapEntryVO<String, String>> getAccountCodeListByDisputeProposals(Integer id) throws BPLException {
		logger.info("Enter method getAccountCodeListByDisputeProposals.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List dList = null;
		try {
			dList = accountCodelDao.getAccountCodeListByDisputeProposals(id);
		} catch (RuntimeException e) {
			logger.error(e.getMessage(),e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : dList){
            Object[] accountCode = (Object[])b;
            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)accountCode[0]).toString(), (String)accountCode[1]);
            list.add(m);
        }
		logger.info("Exit method getAccountCodeListByDisputeProposals.");
		return list;
	}
	
	public void saveInvoiceUserActionLog(Integer invoiceId) throws BPLException {
		UserActionLog userActionLog = new UserActionLog();
		userActionLog.setActivityType("View Invoice Detail");
		userActionLog.setCreatedBy(0);
		userActionLog.setCreatedTimestamp(new Date());
//		userActionLog.setDisputeId(disputeId);
		userActionLog.setInvoiceId(invoiceId);
		userActionLog.setRecActiveFlag("Y");
		userActionLog.setUrl("viewInvoiceDetails.action?invoiceId="+invoiceId);
		userActionLog.setUserId(SystemUtil.getCurrentUserId());
		userActionLogDao.save(userActionLog);
	}

	public void saveDisputeUserActionLog(Integer disputeId) throws BPLException {
		UserActionLog userActionLog = new UserActionLog();
		userActionLog.setActivityType("View Dispute Detail");
		userActionLog.setCreatedBy(0);
		userActionLog.setCreatedTimestamp(new Date());
//		userActionLog.setDisputeId(disputeId);
		userActionLog.setDisputeId(disputeId);
		userActionLog.setRecActiveFlag("Y");
		userActionLog.setUrl("viewDisputeDetails.action?disputeId="+disputeId);
		userActionLog.setUserId(SystemUtil.getCurrentUserId());
		userActionLogDao.save(userActionLog);
	}

	public void saveLogOutUserActionLog() throws BPLException {
		UserActionLog userActionLog = new UserActionLog();
		userActionLog.setActivityType("Log Out");
		userActionLog.setCreatedBy(0);
		userActionLog.setCreatedTimestamp(new Date());
//		userActionLog.setDisputeId(disputeId);
//		userActionLog.setInvoiceId(disputeId);
		userActionLog.setRecActiveFlag("Y");
//		userActionLog.setUrl("j_spring_security_logout");
		userActionLog.setUserId(SystemUtil.getCurrentUserId());
		userActionLogDao.save(userActionLog);
	}
	/**
	 * add by donghao.guo 2011/10/25
	 * Get All ban list.
	 */
	public List<MapEntryVO<String, String>> getSearchConditionBans()  throws BPLException{
		logger.info("Enter method getAllBans.");
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List banList = null;
		try {
			banList = banDao.getSearchConditionBan();
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object b : banList){
			Object[] ban = (Object[])b; 
			String key = ((Integer)ban[0]).toString();
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(key, (String)ban[1]);
			l.add(m);
		}
		logger.info("Exit method getAllBans.");
		return l;
	}
	/**
	 * add by donghao.guo 2011/10/25
	 * Get All vendor list.
	 */
	public List<MapEntryVO<String, String>> getSearchConditionVendors() throws BPLException {
		logger.info("Enter method getUserPreviledgedVendors.");
		List<MapEntryVO<String, String>> l = null;
		List vendorList = null;
		try {
			vendorList = vendorDao.getSearchConditionVendors();
			l = new ArrayList<MapEntryVO<String, String>>(vendorList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object v : vendorList){
            Object[] vendor = (Object[])v;
            if(!((vendor[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)vendor[0]).toString(), (String)vendor[1]);
	            l.add(m);
        	}	
        }
		logger.info("Exit method getUserPreviledgedVendors.");
		return l;
	}
	
	
	/**
	 * add by donghao.guo 2011/10/25
	 * Get All vendor list.
	 */
	public List<MapEntryVO<String, String>> getSearchConditionSummaryVendors() throws BPLException {
		logger.info("Enter method getSearchConditionSummaryVendors.");
		List<MapEntryVO<String, String>> l = null;
		List summaryVendorList = null;
		try {
			summaryVendorList = vendorDao.getSearchConditionSummaryVendors();
			l = new ArrayList<MapEntryVO<String, String>>(summaryVendorList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object v : summaryVendorList){
            Object[] vendor = (Object[])v;
            if(!((vendor[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)vendor[0]).toString(), (String)vendor[1]);
	            l.add(m);
        	}	
        }
		logger.info("Exit method getSearchConditionSummaryVendors.");
		return l;
	}
	
	
	public List<MapEntryVO<String, String>> getSearchConditioncustomers() throws BPLException {
		logger.info("Enter method getSearchConditioncustomers.");
		List<MapEntryVO<String, String>> l = null;
		List customerList = null;
		try {
			customerList = circuitDao.getSearchConditioncustomers(); 
			l = new ArrayList<MapEntryVO<String, String>>(customerList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object v : customerList){
            Object[] customer = (Object[])v;
            if(!((customer[1].toString().equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(((Integer)customer[0]).toString(), (String)customer[1]);
	            l.add(m);
        	}	
        }
		logger.info("Exit method getSearchConditioncustomers.");
		return l;
		
		
		
	}
	public List<MapEntryVO<String, String>> getApprover4ByRoleId()
			throws BPLException {
		logger.info("Enter method getApprover4ByRoleId.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> dList = null;
		try {
			dList = userDao.getApprover4ByRoleId();
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(Object u : dList){
			Object[] user = (Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(user[0].toString(), user[1].toString() + " " + user[2].toString());
			list.add(m);
		}
		logger.info("Exit method getApprover4ByRoleId.");
		return list;
	}
	
	public List<MapEntryVO<String, String>> getAutoPayCountList()
	throws BPLException {
		logger.info("Enter method getAutoPayCountList.");
		List<MapEntryVO<String, String>> list = new ArrayList<MapEntryVO<String, String>>();
		List<Object[]> dList = null;
		String autoPayMaxCount = "";
		try {
			autoPayMaxCount = banDao.getAutoPayMaxCount();
		} catch (RuntimeException e) {
			e.printStackTrace();
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for(int i=0;i<Integer.parseInt(autoPayMaxCount)+1;i++){
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(""+i, ""+i);
			list.add(m);
		}
		logger.info("Exit method getAutoPayCountList.");
		return list;
	}
	
	@Override
	public List<MapEntryVO<String, String>> getSearchConditionProduct()
			throws BPLException {
		logger.info("Enter method getSearchConditionProduct.");
		List<MapEntryVO<String, String>> l = null;
		List productList = null;
		try {
			productList = masterInventoryDao.getSearchConditionProduct();
			l = new ArrayList<MapEntryVO<String, String>>(productList.size());
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
        for(Object p : productList){
            String product = (String)p;
            if(!((product.equals("ALL")))){
	            MapEntryVO<String, String> m = new MapEntryVO<String, String>(product, product);
	            l.add(m);
        	}	
        }
		logger.info("Exit method getSearchConditionProduct.");
		return l;
	}
	public String isFileDownLoad (int id,String token) throws BPLException{
		
		logger.info("Enter method isFileDownLoad.");
		String value = "";
		try {
		     Map <String,String> fileDownloadMap = reportDAO.selectFileDownLoadVerifyValue(id);
		     
		     if ("Y".equals(fileDownloadMap.get("loginFlag"))) {
					
		    	 Integer currentUserId = SystemUtil.getCurrentUserId();
		    	 if (currentUserId != null) {
				     value = "Download";
		    	 }else{
		    		 value = "Login";
		    		 return value;
		    	 }

		     }else{
		    	 value = "Download";
		     } 
		     
		     if(fileDownloadMap.get("hour") != null){
	    		 if ("Y".equals(fileDownloadMap.get("isHour"))) {
	    			 value = "Download";
	    		 }else {
	    			 value = "Timeout";
	    			 return value;
	    		 }
	    	 }
		
		
	    	 if (fileDownloadMap.get("token").equals(token)) {
	    		 value = "Download";
	    	 }else {
	    		 value = "TokenError";
	    		 return value;
	    	 }		 
			
		} catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}    
		logger.info("Exit method isFileDownLoad.");
		
		return value;
	}
	
	public Map <String,String> selectFileDownLoad (int id) {
		Map <String,String> fileDownloadMap = reportDAO.selectFileDownLoad(id);
		return fileDownloadMap;
	}
	public Integer findfileDownloadJournal (int id) throws BPLException{
		logger.info("Enter method findfileDownloadJournal.");
		Integer count = null;
		try {
		    count = reportDAO.findfileDownloadJournal(id);
		}
		catch (Exception e) {
			logger.error("",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}    
		logger.info("Exit method findfileDownloadJournal.");
		return count;
	}
	
	
	public void savefileDownloadJournal(String loginFlag,int fileDownloadId,String ip) throws BPLException {

		try {
			logger.info("Enter method savefileDownloadJournal.");
		        reportDAO.savefileDownloadJournal(loginFlag,fileDownloadId,ip);
		}
		 catch (Exception e) {
				logger.error("",e);
				BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
				throw bpe;
		}  
		 logger.info("Exit method savefileDownloadJournal.");
	}
	

	public void setTariffLinkDao(ITariffLinkDao tariffLinkDao) {
		this.tariffLinkDao = tariffLinkDao;
	}

	public Map<String, String> findSysConfig() {
		return sysConfigDAO.findSysConfig();
	}

	public ICircuitDao getCircuitDao() {
		return circuitDao;
	}

	public void setCircuitDao(ICircuitDao circuitDao) {
		this.circuitDao = circuitDao;
	}
}
