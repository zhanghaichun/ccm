/**
 * @author pengfei.wang
 * */
package com.saninco.ccm.service.vendor;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.DaoUtil;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.dao.IVendorDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Contact;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.model.VendorStatus;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.ContactVO;
import com.saninco.ccm.vo.SearchInvoiceVO;
import com.saninco.ccm.vo.SearchVendorVo;

public class VendorMainServiceImpl implements IVendorMainService {
	private IVendorDao vendorDao;
	private IUserDao userDao;
	private final Logger logger = Logger.getLogger(this.getClass());
	private static int DOWNLOAD_EXCEL_RECPERPAGE = 1000;
	private static int DOWNLOAD_EXCEL_MAXSIZE = 60000;
	
	/**
	 * @author pengfei.wang
	 * */
	public Integer getCoutactVendorId() throws BPLException {
		Integer contacrVendortoId;
		contacrVendortoId=vendorDao.findMaxId();
		return contacrVendortoId;
	}
	
	/**
	 * @author pengfei.wang
	 * Add a new vendor, will be added into the vendor's data
	 * */
	public String saveVendor(String vendorName, String vendorAcronym,String summartVendorName, int vendorStatus,String channel,String lpcRate) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("add vendor Data By string", vendorName));
		User muser = new User();
		User cuser = new User();
		String vendorJson = "";
		try {
			cuser = userDao.findById(SystemUtil.getCurrentUserId());
			muser = userDao.findById(SystemUtil.getCurrentUserId());
			Vendor vendor = new Vendor();
			Integer maxId = vendorDao.findMaxId();
			vendor.setId(maxId + 1);
			VendorStatus vendorStatusId = vendorDao.findByStatusId(vendorStatus);
			vendor.setVendorName(vendorName);
			vendor.setVendorAcronym(vendorAcronym);
			vendor.setSummaryVendorName(DaoUtil.ccmEscape4(summartVendorName));
			vendor.setChannel(channel);
			if (!"".equals(lpcRate)) {
				vendor.setLpcRate(Double.parseDouble(lpcRate));
			}
			vendor.setVendorStatus(vendorStatusId);
			vendor.setModifiedBy(SystemUtil.getCurrentUserId());
			vendor.setModifiedTimestamp(new Date());
			vendor.setCreatedBy(SystemUtil.getCurrentUserId());
			vendor.setCreatedTimestamp(new Date());
			vendor.setStatusTimestamp(new Date());
			vendor.setRecActiveFlag("Y");
			vendorDao.saveVendor(vendor);
			vendorJson="{vendorName:\""+(vendor.getVendorName()==null?"":vendor.getVendorName())+"\",vendorAcronym:\""+(vendor.getVendorAcronym()==null?"":vendor.getVendorAcronym())+"\",vendorId:\""+vendor.getId()+"\",summartVendorname:\""+(vendor.getSummaryVendorName()==null?"":vendor.getSummaryVendorName())+"\",channel:\""+(vendor.getChannel()==null?"":vendor.getChannel())+"\",vendorStatus:\""+(vendor.getVendorStatus().getId()==null?"":vendor.getVendorStatus().getId())+"\",statusTimestamp:\""+(vendor.getStatusTimestamp()==null?"":vendor.getStatusTimestamp())+"\",createdBy:\""+(cuser.getFirstName()==null?"":cuser.getFirstName()+" "+cuser.getLastName())+"\",createdTime:\""+(vendor.getCreatedTimestamp()==null?"":vendor.getCreatedTimestamp())+"\",modifiedTime:\""+(vendor.getModifiedTimestamp()==null?"":vendor.getModifiedTimestamp())+"\",modifiedBy:\""+(muser.getFirstName()==null?"":muser.getFirstName()+" "+muser.getLastName())+"\"}";
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return vendorJson;
	}
	
	/**
	 * @author pengfei.wang
	 * To meet this id Contact, modify the data into
	 * */
	public void updateContact(ContactVO cvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("update group Contact Data By id", cvo));
		try {
			vendorDao.editContact(cvo);		
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		
	}
	
	public String searchContact(ContactVO cvo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Verify contact already exists", cvo));
		String result = "{count:0}";
		try {
			long count = vendorDao.getContactCount(cvo);	
			if(count != 0) result = "{count:1}";
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());	
		return result;
	}
	
	/**
	 * @author pengfei.wang
	 * Add a new Contact, and will be added into the data of Contact
	 * */
	public String addConcate(ContactVO cvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("add contact Data By ContactVO", cvo));
		StringBuffer sb = new StringBuffer();
		try {
			Contact contact = new Contact();
			Vendor vendor = new Vendor();
			vendor = vendorDao.findById(Integer.parseInt(cvo.getVendor()));
			contact.setFirstName(cvo.getFirstName());
			contact.setLastName(cvo.getLastName());
			contact.setAttentionName(cvo.getAttentionName());
			contact.setCoName(cvo.getCoName());
			contact.setAddress1(cvo.getAddress1());
			contact.setAddress2(cvo.getAddress2());
			contact.setCity(cvo.getCity());
			contact.setCountry(cvo.getCountry());
			contact.setPrimaryPhone(cvo.getPrimaryPhone());
			contact.setPostalCode(cvo.getPostalCode());
			contact.setOtherPhone(cvo.getOtherPhone());
			contact.setOfficePhone(cvo.getOfficePhone());
			contact.setCellPhone(cvo.getCellPhone());
			contact.setFaxNumber(cvo.getFaxNumber());
			contact.setEmail(cvo.getEmail());
			contact.setVendor(vendor);
			contact.setProvince(cvo.getProvince());
			contact.setRecActiveFlag("Y");
			contact.setCreatedBy(SystemUtil.getCurrentUserId());
			contact.setCreatedTimestamp(new Date());
			contact.setModifiedBy(SystemUtil.getCurrentUserId());
			contact.setModifiedTimestamp(new Date());
			vendorDao.save(contact);
			sb.append("{error:false}");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author pengfei.wang
	 * According to the Contact id deleted
	 * */
	public void deleteConcate(Integer concateId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("delete group contact Data By vendorId", concateId));
		try {
			vendorDao.delectContact(concateId);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		
	}
	/**
	 * @author pengfei.wang
	 * The vendor shall conform to the id of the modification of data to the data
	 * */
	public void updateVendorMainData(String UpvendorvendorNameTxt,Integer vendorId,String vendorAcronymM, String summaryVendorNameM,Integer vendorStatusM,String channel,String lpcRate) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("update group vendor Data By vendorId", vendorId));
		try {
			vendorDao.updateVendorMainData(UpvendorvendorNameTxt,vendorId, vendorAcronymM, summaryVendorNameM, vendorStatusM, channel,lpcRate);			
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author pengfei.wang
	 * With the id of data query
	 * */
	public String getVendorByvendorId(Integer id) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get Vendor By VendorId", id));
		String vendorJson = "";
		Vendor vendor = new Vendor();
		User cuser = new User();
		User muser = new User();
		try {
			vendor = vendorDao.findById(id);
			cuser = userDao.findById(vendor.getCreatedBy());
			muser = userDao.findById(vendor.getModifiedBy());
			vendorJson="{vendorName:\""+(vendor.getVendorName()==null?"":vendor.getVendorName())+"\",summaryVendorName:\""+(vendor.getSummaryVendorName()==null?"":vendor.getSummaryVendorName())+"\",vendorAcronym:\""+(vendor.getVendorAcronym()==null?"":vendor.getVendorAcronym())+"\",vendorId:\""+vendor.getId()+"\",channel:\""+(vendor.getChannel()==null?"":vendor.getChannel())+"\",lpcRate:\""+(vendor.getLpcRate()==null?"":vendor.getLpcRate())+"\",vendorStatus:\""+(vendor.getVendorStatus().getId()==null?"":vendor.getVendorStatus().getId())+"\",statusTimestamp:\""+(vendor.getStatusTimestamp()==null?"":vendor.getStatusTimestamp())+"\",createdBy:\""+(cuser.getFirstName()==null?"":cuser.getFirstName()+" "+cuser.getLastName())+"\",createdTime:\""+(vendor.getCreatedTimestamp()==null?"":vendor.getCreatedTimestamp())+"\",modifiedTime:\""+(vendor.getModifiedTimestamp()==null?"":vendor.getModifiedTimestamp())+"\",modifiedBy:\""+(muser.getFirstName()==null?"":muser.getFirstName()+" "+muser.getLastName())+"\"}";
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return vendorJson;
	}
	
	public String getTariffList(ContactVO cvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Tariff by SearchInvoiceVO", cvo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = vendorDao.searchTariff(cvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(cvo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(cvo.getStartIndex() + size);
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

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getSearchBanExemptionCertificate(ContactVO cvo,String invoiceDate) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Search BanExemptionCertificate", cvo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = vendorDao.searchBanExemptionCertificate(cvo,invoiceDate);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(cvo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(cvo.getStartIndex() + size);
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

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
		
	}
	
	public void deleteBanExemptionCertificate(Integer id)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("deleteBanExemptionCertificate"));
		try {
			vendorDao.deleteBanExemptionCertificate(id);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author pengfei.wang
	 * Contact with vendor's query data in the list
	 * */
	public String SearchVendorList(ContactVO cvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search MiscList ", cvo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = vendorDao.searchVendorContact(cvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(cvo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(cvo.getStartIndex() + size);
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
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * Contact with vendor statistics of the table
	 * */
	public String getVendorTotalPageNo(ContactVO cvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get getVendorTotalPageNo ", cvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = vendorDao.getVendorContactNO(cvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) cvo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getTariffslPageNo(ContactVO cvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get getTariffslPageNo ", cvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = vendorDao.getTariffsNO(cvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) cvo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	public String getBanExemptionCertificateTotalPageNo(ContactVO cvo,String invoiceDate) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("get getBanExemptionCertificateTotalPageNo ", cvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = vendorDao.getBanExemptionCertificateNO(cvo,invoiceDate);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) cvo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	public String getAllChargeTotalPageNo(SearchVendorVo svvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get getTariffslPageNo ", svvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = vendorDao.getAllChargeTotal(svvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svvo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getAllChargesList(SearchVendorVo svvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Tariff by getAllChargesList", svvo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = vendorDao.searchAllCharges(svvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(svvo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(svvo.getStartIndex() + size);
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

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getRecurringChargeTotalPageNo(SearchVendorVo svvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get getTariffslPageNo ", svvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = vendorDao.getRecurringChargeTotal(svvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svvo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getRecurringChargeList(SearchVendorVo svvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Tariff by searchRecurringCharge", svvo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = vendorDao.searchRecurringCharge(svvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(svvo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(svvo.getStartIndex() + size);
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
		
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getSearchSortProposalTotalPageNo(SearchVendorVo svvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get getSearchSortProposalTotalPageNo ", svvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = vendorDao.getSearchSortProposalTotalPageNo(svvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svvo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getSearchSortProposalTable(SearchVendorVo svvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Tariff by getSearchSortProposalTable", svvo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = vendorDao.getSearchSortProposalTable(svvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(svvo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(svvo.getStartIndex() + size);
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
		
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getSearchPaymentTotalPageNo(SearchVendorVo svvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get getSearchPaymentTotalPageNo ", svvo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = vendorDao.getSearchPaymentTotalPageNo(svvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count / (double) svvo.getRecPerPage());
		sb.append(tp);
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getSearchPaymentTable(SearchVendorVo svvo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Tariff by getSearchPaymentTable", svvo));
		StringBuffer sb = new StringBuffer();
		List<String> list = null;
		try {
			list = vendorDao.getSearchPaymentTable(svvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(svvo.getStartIndex() + 1);
			sb.append(",end:");
			int size = list.size();
			sb.append(svvo.getStartIndex() + size);
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
	
	/**
	 * @author pengfei.wang
	 * This data is stored and already Excel to download
	 * ps:This Contact Vendor page for the Contact general admin download
	 * */
	@SuppressWarnings("unchecked")
	public String saveExcelByContact(ContactVO cvo,String excelDirPath,List<String> titles) throws BPLException  {
		logger.info(CcmFormat.formatEnterLog("Down Excel by Contact ", cvo));
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = vendorDao.getVendorContactNO(cvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,"VendorContact");
			
			jExcelUtil.removeFieldName(titles);
			
			jExcelUtil.writeTitle(0, titles);
			if(count <= recPerPage){
				cvo.setPageNo(1);
				cvo.setRecPerPage((int)count);
				list = (List)vendorDao.contactList(cvo);
				for(int i=0;i<list.size();i++){
					Object[] o = list.get(i);
//					o[1] = CcmFormat.formatCurrency(new Double(o[1].toString()));
//					o[3] = "";
					jExcelUtil.writeLine(i+1, o); 
				}
			}else{
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for(int j = 0 ; j < totlePage; j++){
					cvo.setPageNo(j + 1);
					cvo.setRecPerPage(recPerPage);
					list = (List)vendorDao.contactList(cvo);
					for(int i = 0; i < list.size(); i++){
						Object[] o = list.get(i);
//						o[1] = CcmFormat.formatCurrency(new Double(o[1].toString()));
//						o[3] = "";
						jExcelUtil.writeLine(j * recPerPage + i + 1, o); 
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 52, 20, 20, 60, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25});
			//jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Exception  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	/**
	 * @author pengfei.wang
	 * delete vendor by id
	 * */
	public void deleteVendor(Integer id) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("delete group Vendor Data By id", id));
		try {
			vendorDao.deleteVendor(id);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * create All Charges To Excel
	 */
	public String createAllChargesToExcel(SearchVendorVo svvo,String excelDirPath,List<String> titles) throws BPLException  {
		logger.info(CcmFormat.formatEnterLog("create Invoice To Excel", svvo,excelDirPath,titles));
		int pageNumber = 0;
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			count = vendorDao.getAllChargeTotal(svvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,"invoice0"); 
			jExcelUtil.writeTitle(0, titles);
			if(count <= DOWNLOAD_EXCEL_RECPERPAGE){
				svvo.setPageNo(1);
				svvo.setRecPerPage((int)count);
				list = vendorDao.searchAllChargesToExcel(svvo);
				for(int i=0;i<list.size();i++){
					jExcelUtil.writeLine(i+1, list.get(i)); 
				}
			}else{
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for (int j = 0; j < totlePage; j++) {
					svvo.setPageNo(j + 1);
					svvo.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					list = vendorDao.searchAllChargesToExcel(svvo);
					if(pageNumber > 0){
						if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
							jExcelUtil.setColumnView(new int[]{27,42,40,25,31,22,22,20,27,27,35,25,25,25,25,25,25,25,25,25,25,40,40});
							jExcelUtil.createWritableSheet(pageNumber,"invoice"+pageNumber); 
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
			jExcelUtil.setColumnView(new int[]{27,42,40,25,31,22,22,20,27,27,35,25,25,25,25,25,25,25,25,25,25,40,40});
			//jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	public String createRecurringChargeToExcel(SearchVendorVo svvo,String excelDirPath,List<String> titles) throws BPLException  {
		logger.info(CcmFormat.formatEnterLog("create Invoice To Excel", svvo,excelDirPath,titles));
		int pageNumber = 0;
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			count = vendorDao.getRecurringChargeTotal(svvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,"invoice0"); 
			jExcelUtil.writeTitle(0, titles);
			if(count <= DOWNLOAD_EXCEL_RECPERPAGE){
				svvo.setPageNo(1);
				svvo.setRecPerPage((int)count);
				list = vendorDao.searchRecurringChargeToExcel(svvo);
				for(int i=0;i<list.size();i++){
					jExcelUtil.writeLine(i+1, list.get(i)); 
				}
			}else{
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for (int j = 0; j < totlePage; j++) {
					svvo.setPageNo(j + 1);
					svvo.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					list = vendorDao.searchRecurringChargeToExcel(svvo);
					if(pageNumber > 0){
						if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
							jExcelUtil.setColumnView(new int[]{27,42,40,25,31,22,22,20,27,27,35,25,25,25,25,25,25,25,25,25,25,40,40});
							jExcelUtil.createWritableSheet(pageNumber,"invoice"+pageNumber); 
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
			jExcelUtil.setColumnView(new int[]{27,42,40,25,31,22,22,20,27,27,35,25,25,25,25,25,25,25,25,25,25,40,40});
			//jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	public String createSortProposalToExcel(SearchVendorVo svvo,String excelDirPath,List<String> titles) throws BPLException  {
		logger.info(CcmFormat.formatEnterLog("create Invoice To Excel", svvo,excelDirPath,titles));
		int pageNumber = 0;
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			count = vendorDao.getSearchSortProposalTotalPageNo(svvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,"invoice0"); 
			jExcelUtil.writeTitle(0, titles);
			if(count <= DOWNLOAD_EXCEL_RECPERPAGE){
				svvo.setPageNo(1);
				svvo.setRecPerPage((int)count);
				list = vendorDao.searchSortProposalToExcel(svvo);
				for(int i=0;i<list.size();i++){
					jExcelUtil.writeLine(i+1, list.get(i)); 
				}
			}else{
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for (int j = 0; j < totlePage; j++) {
					svvo.setPageNo(j + 1);
					svvo.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					list = vendorDao.searchSortProposalToExcel(svvo);
					if(pageNumber > 0){
						if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
							jExcelUtil.setColumnView(new int[]{27,42,40,25,31,22,22,20,27,27,35,25,25,25,25,25,25,25,25,25,25,40,40});
							jExcelUtil.createWritableSheet(pageNumber,"invoice"+pageNumber); 
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
			jExcelUtil.setColumnView(new int[]{27,42,40,25,31,22,22,20,27,27,35,25,25,25,25,25,25,25,25,25,25,40,40});
			//jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	public String createPaymentlToExcel(SearchVendorVo svvo,String excelDirPath,List<String> titles) throws BPLException  {
		logger.info(CcmFormat.formatEnterLog("create Invoice To Excel", svvo,excelDirPath,titles));
		int pageNumber = 0;
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			count = vendorDao.getSearchPaymentTotalPageNo(svvo);
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0,"invoice0"); 
			jExcelUtil.writeTitle(0, titles);
			if(count <= DOWNLOAD_EXCEL_RECPERPAGE){
				svvo.setPageNo(1);
				svvo.setRecPerPage((int)count);
				list = vendorDao.searchPaymentToExcel(svvo);
				for(int i=0;i<list.size();i++){
					jExcelUtil.writeLine(i+1, list.get(i)); 
				}
			}else{
				long totlePage = (count + DOWNLOAD_EXCEL_RECPERPAGE - 1) / DOWNLOAD_EXCEL_RECPERPAGE;
				for (int j = 0; j < totlePage; j++) {
					svvo.setPageNo(j + 1);
					svvo.setRecPerPage(DOWNLOAD_EXCEL_RECPERPAGE);
					pageNumber = ((j * DOWNLOAD_EXCEL_RECPERPAGE) / DOWNLOAD_EXCEL_MAXSIZE);
					list = vendorDao.searchPaymentToExcel(svvo);
					if(pageNumber > 0){
						if(((j * DOWNLOAD_EXCEL_RECPERPAGE) % DOWNLOAD_EXCEL_MAXSIZE) == 0 ){
							jExcelUtil.setColumnView(new int[]{27,42,40,25,31,22,22,20,27,27,35,25,25,25,25,25,25,25,25,25,25,40,40});
							jExcelUtil.createWritableSheet(pageNumber,"invoice"+pageNumber); 
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
			jExcelUtil.setColumnView(new int[]{27,42,40,25,31,22,22,20,27,27,35,25,25,25,25,25,25,25,25,25,25,40,40});
			//jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable  e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return excelDirPath;
	}
	
	public VendorMainServiceImpl(){
		
	}
	
	public IVendorDao getVendorDao() {
		return vendorDao;
	}

	public void setVendorDao(IVendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}

	public IUserDao getUserDao() {
		return userDao;
	}

	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}
	

}
