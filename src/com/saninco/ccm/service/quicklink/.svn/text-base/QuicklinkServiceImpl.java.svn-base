/**
 * 
 */
package com.saninco.ccm.service.quicklink;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IBanDao;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.dao.IUserQuicklinkDao;
import com.saninco.ccm.dao.IVendorDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.User;
import com.saninco.ccm.model.UserQuicklink;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.EnumQuicklinkType;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;

/**
 * @author Joe.Yang
 *
 */
public class QuicklinkServiceImpl implements IQuicklinkService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private IUserQuicklinkDao userQuicklinkDao;
	private IVendorDao vendorDao;
	private IBanDao banDao;
	private IUserDao userDao;

	/**
	 * 
	 */
	public QuicklinkServiceImpl() {
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.service.quicklink.IQuicklinkService#deleteQuicklink(java.lang.Integer)
	 */
	public void deleteQuicklink(Integer id) throws BPLException {
		logger.info("Enter method deleteQuicklink.");
		UserQuicklink uql = this.userQuicklinkDao.findById(id);
		if(uql!=null){
			this.userQuicklinkDao.delete(uql);
		}

		logger.info("Exit method deleteQuicklink.");
	}
	
	/**
	 * Save search quicklink.
	 */
	public String saveSearchQuicklink(String quicklinkName,
			String queryString, EnumQuicklinkType quicklinkType) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Save search quicklink."));
		if(userQuicklinkDao.findByQuicklinkName(quicklinkName,quicklinkType.getName()).size()>0){
			logger.error("User quicklink "+ quicklinkName+" existed.");
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_USER_QUICKLINK_NAME_EXISTED);
			throw bpe;
		}
		UserQuicklink uql = new UserQuicklink();
		uql.setQuicklinkName(quicklinkName);
		uql.setRequestString(queryString);
		uql.setQuicklinkType(quicklinkType.getCode());
		int userId = SystemUtil.getCurrentUserId();
		User user = userDao.getUser(userId);
		uql.setUser(user);
		uql.setRecActiveFlag("Y");
		uql.setCreatedBy(userId);
		uql.setCreatedTimestamp(new Date());
		uql.setModifiedBy(userId);
		uql.setModifiedTimestamp(new Date());
		this.userQuicklinkDao.save(uql);
		logger.info("Exit method saveInvoiceSearchQuicklink.");
		return "{id:"+uql.getId()+",name:\""+quicklinkName+"\",quicklinkType:\""+uql.getQuicklinkType() + "\"}";
	}
	
	public void saveSearchQuicklink(String quicklinkName,String queryString)throws BPLException {
		logger.info("Enter method saveInvoiceSearchQuicklink.");
		userQuicklinkDao.delete();
		
		UserQuicklink uql = new UserQuicklink();
		uql.setQuicklinkName(quicklinkName);
		uql.setRequestString(queryString);
		uql.setQuicklinkType("mark");
		uql.setRecActiveFlag("N");
		
		this.userQuicklinkDao.save(uql);
		logger.info("Exit method saveInvoiceSearchQuicklink.");
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.service.quicklink.IQuicklinkService#getQuicklink(java.lang.Integer)
	 * Modified By Chao.Liu
	 */
	public String getQuicklink(Integer id) throws BPLException {
		logger.info("Enter method getQuicklink.");
		String s = null;
		try {
			UserQuicklink uql = this.userQuicklinkDao.findById(id);
			s = this.makeJson(uql);
		} catch (Exception e) {
			logger.error("getQuicklink error:",e);
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getQuicklink.");
		return s;
	}
	/**
	 * [This is method no use]
	 * @author Chao.Liu Date:Aug 29, 2010
	 * @param quicklinkName
	 * @return
	 * @throws BPLException
	 */
	public String updateQuicklink(String quicklinkName)throws BPLException {
		logger.info("Enter method getQuicklink.");
		UserQuicklink uql;
		try {
			uql = userQuicklinkDao.find();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		String s = "";
		if(uql != null){
			if(uql.getQuicklinkName().equals(quicklinkName)){
				if(uql.getQuicklinkType().equals("mark_delete")){
					s = this.makeJson(uql);
				}
				userQuicklinkDao.delete();
			}else{
				if(uql.getQuicklinkType().equals("mark_delete")){
					userQuicklinkDao.delete();
				}else{
					uql.setQuicklinkType("mark_delete");
					userQuicklinkDao.merge(uql);
				}
			}
		}
		
		logger.info("Exit method getQuicklink.");
		return s;
	}
	/**
	 * Browser back function
	 * @Auchor Chao.Liu | On Date: Nov 15, 2010
	 * @Belong To QuicklinkAction.action [getLocation()]
	 * @param quicklinkName
	 * @param strs
	 * @return url
	 * @throws BPLException
	 */
	public String updateQuicklink(String quicklinkName,Object o)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Browser back function.",quicklinkName,o));
		String s = "";
		String[] strs = null;
		try {
			if(o != null){
				strs = (String[]) o;
				UserQuicklink uql = new UserQuicklink();
				uql.setRequestString(strs[1]);
				if(quicklinkName.equals(strs[0])){
					if(strs[2].equals("mark_delete")){
						s = this.makeJson(uql);
					}
				}else{
					if(!strs[2].equals("mark_delete")){
						s = "mark_delete";
					}
				}
			}
			
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getQuicklink.");
		return s;
	}
	
	private String makeJson(UserQuicklink uql) throws BPLException{
		StringBuffer sb = null;
		try {
//			String [] kvps = null;
			String q = uql.getRequestString();
			String [] kvpsCheck = q.split("&");
//			int kvpsLength = 0;
//			for(int i = 0; i< kvpsCheck.length; i++){
//				if(kvpsCheck[i] != null && !kvpsCheck[i].equals("")){
//					kvpsLength ++;
//				}
//			}
//			kvps = new String[kvpsLength];
//			

		//get vendorId and banId from saved query string
//		String vendorId = null;
//		String banId = null;
//		for(int i=0; i<kvps.length; i++){
//			String [] kv = kvps[i].split("=");
//			if(kv[0].indexOf(Constants.VENDOR_ID_PROP_NAME_IN_SEARCH_INVOICE_VO)!=-1)
//				vendorId = kv[1];
//			if(kv[0].indexOf(Constants.BAN_ID_PROP_NAME_IN_SEARCH_INVOICE_VO)!=-1)
//				banId = kv[1];
//		}

		//get user previledged vendor JSON array
		/**
		int userId = SystemUtil.getCurrentUserId();
		List<Object[]> vl = this.vendorDao.getUserPreviledgedVendors(userId);
		if(vl==null||vl.isEmpty()){
			logger.error("User ("+userId+") has no access to any vendors.");
			throw new BPLException(ErrorCodeConstants.EC_USER_HAS_NO_ACCESS_TO_ANY_VENDORS);
		}
		String vlJson = assembleVendorListJsonArray(vl);
		*/
		//get user previledged ban JSON array
		//vendorId is not null and also not existed in current user previledged vendor list, use default value "all"
		//to query, otherwise get user previledged Ban list and compose Ban JSON array, then if banId is not null and 
		//not in ban list set banId to null
		/**
		String blJson = null;
		if(vendorId!=null){
			if(!isVendorInUserPreviledgedVendorList(vendorId, vl)){
				vendorId = null;
				banId = null;
			}else{
				List<Object[]> bl = banDao.getUserPreviledgedBanByVendorId(userId, new Integer(vendorId));
				blJson = assembleBanListJsonArray(bl);
				if(banId!=null){
					if(!isBanInUserPreviledgedBanList(banId, bl)){
						banId = null;
					}
				}
			}
		}else{
			if(banId!=null) banId = null;
		}
		*/
		//get saved query string JSON array
			sb = new StringBuffer("{query:{");
			for(int i = 0,j = 0; i< kvpsCheck.length; i++){
				if(kvpsCheck[i] != null && !kvpsCheck[i].equals("")){
					String[] kv = kvpsCheck[i].split("=");
					if (j != 0){
						sb.append(",");
					}
					sb.append("\"" + kv[0] + "\"" + ":" + "\"" + kv[1] + "\"");
					j++;
				}
			}
			sb.append("}}");
		
		} catch (Exception e) {
			e.printStackTrace();
		}
//		String s = "{vendors:" + vlJson + ",bans:"+(blJson==null?"[]":blJson)
//				+ ",query:" + sb.toString()+"}";
		
		return sb.toString();
	}
	
	private boolean isVendorInUserPreviledgedVendorList(String vendorId, List<Object[]> vl) {	
		for(Object[] os : vl){
			if(os[0].toString().equalsIgnoreCase(vendorId)) return true;
		}
		return false;
	}
	
	private boolean isBanInUserPreviledgedBanList(String banId, List<Object[]> bl) {	
		for(Object[] b : bl){
			if(b[0].toString().equalsIgnoreCase(banId)) return true;
		}
		return false;
	}
	
	private String assembleVendorListJsonArray(List<Object[]> vl) {
		StringBuffer sb1 = new StringBuffer();
		sb1.append("[");	
		for(Object[] os : vl){
			if(sb1.length()>1) sb1.append(",");
			sb1.append("{id:"+os[0]);
			sb1.append(",name:\""+os[1]+"\"}");
		}
		sb1.append("]");
		return sb1.toString();
	}
	
	private String assembleBanListJsonArray(List<Object[]> bl) {
		StringBuffer sb1 = new StringBuffer();
		sb1.append("[");	
		for(Object[] b : bl){
			if(sb1.length()>1) sb1.append(",");
			sb1.append("{id:"+b[0]);
			sb1.append(",no:\""+b[1]+"\"}");
		}
		sb1.append("]");
		return sb1.toString();
	}

	/* (non-Javadoc)
	 * @see com.saninco.ccm.service.quicklink.IQuicklinkService#getUserQuicklin()
	 */
	public List<MapEntryVO<String, String>> getUserQuicklinks(EnumQuicklinkType quicklinkType) throws BPLException {
		logger.info("Enter method getUserQuicklinks.");
		int userId = SystemUtil.getCurrentUserId();
		List l = null;
		try {
			l = this.userQuicklinkDao.findQuicklinks(quicklinkType.getCode(), userId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		List<MapEntryVO<String, String>> r = new ArrayList<MapEntryVO<String, String>>();
		for(int i = 0; i<l.size(); i++){
			UserQuicklink uql = (UserQuicklink)l.get(i);
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(uql.getId().toString(), uql.getQuicklinkName());
			r.add(m);
		}
		logger.info("Exit method getUserQuicklink.");
		return r;
	}
	/**
	 * @author Chao.Liu
	 * @return
	 * @throws BPLException
	 */
	public List<MapEntryVO<String, String>> getUserQuicklinks() throws BPLException {
		logger.info("Enter method getUserQuicklinks.");
		int userId = SystemUtil.getCurrentUserId();
		List<Object[]> l = null;
		try {
			l = this.userQuicklinkDao.findQuicklinks(userId);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		List<MapEntryVO<String, String>> r = new ArrayList<MapEntryVO<String, String>>();
		for(Object u : l){
			Object[] userQuicklink = (Object[])u;
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(userQuicklink[0].toString(), userQuicklink[1].toString());
			r.add(m);
		}
		logger.info("Exit method getUserQuicklink.");
		return r;
	}
	/**
	 * @author Chao.Liu
	 * Get Page Type
	 * @param quicklinkId
	 * @return
	 * @throws BPLException
	 */
	public String getThisPageName(int quicklinkId)throws BPLException {
		logger.info("Enter method getThisPageName.");
		String s = null;
		try {
			s = this.userQuicklinkDao.getPageName(quicklinkId).trim();
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info("Exit method getThisPageName.");
		return "{id:'"+quicklinkId+"',type:'"+this.getPageType(s)+"',quick_type:'"+s+"'}";
	}
	private String getPageType(String s){
		if(s.equals("Invoice")){
			return "searchInvoice.action";
		}else if(s.equals("Dispute")){
			return "searchDispute.action";
		}else if(s.equals("Ticket")){
			return "searchTicket.action";
			//add by Donghao.guo 2011/09/22
		} else if(s.equals("VendorInventory")){
			return "viewVendorInventory.action";
		} if(s.equals("Circuit")){
			return "searchCircuit.action";
		} if(s.equals("OldCircuit")){
			return "searchCircuitOld.action";
		} else if (s.equals("ContractTariffPriceList")){
			return "searchContractTariffPriceList.action";
		} else if (s.equals("QuoteInventory")) {
			return "searchQuoteInventory.action";
		} else if ("MasterInventory".equals(s)) {
			return "searchInventoryDashboard.action";
		}
			
			
		
//		if(s.equals("Circuit") 
//				|| s.equals("OldCircuit") 
//				|| s.equals("VendorInventory") 
//				|| s.equals("MasterInventory")){
//			return "searchCircuitPageView.action";
//
//		}

		return null;
	}
	/**
	 * @return the userQuicklinkDao
	 */
	public IUserQuicklinkDao getUserQuicklinkDao() {
		return userQuicklinkDao;
	}

	/**
	 * @param userQuicklinkDao the userQuicklinkDao to set
	 */
	public void setUserQuicklinkDao(IUserQuicklinkDao userQuicklinkDao) {
		this.userQuicklinkDao = userQuicklinkDao;
	}

	/**
	 * @return the userDao
	 */
	public IUserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao the userDao to set
	 */
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}

	/**
	 * @return the vendorDao
	 */
	public IVendorDao getVendorDao() {
		return vendorDao;
	}

	/**
	 * @param vendorDao the vendorDao to set
	 */
	public void setVendorDao(IVendorDao vendorDao) {
		this.vendorDao = vendorDao;
	}

	/**
	 * @return the banDao
	 */
	public IBanDao getBanDao() {
		return banDao;
	}

	/**
	 * @param banDao the banDao to set
	 */
	public void setBanDao(IBanDao banDao) {
		this.banDao = banDao;
	}

	
}
