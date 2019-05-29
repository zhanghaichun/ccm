package com.saninco.ccm.service.report;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;


import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.DaoUtil;
import com.saninco.ccm.dao.IBanDao;
import com.saninco.ccm.dao.IReportDAO;
import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.CreatedReport;
import com.saninco.ccm.model.CreatedReportParameter;
import com.saninco.ccm.model.Report;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.SearchReportUserVO;

public class ReportUserServiceImpl implements IReportUserService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private IReportDAO reportDAO;
	private IBanDao banDao;
	private ISysConfigDAO sysConfigDAO;
	
	public ReportUserServiceImpl(){
	}
	
	/**
	 * By hongtao 2011-09-20
	 */
	public String deleteReport(String createdReportId) throws BPLException {
		logger.info(CcmFormat
				.formatEnterLog("delete created_Report By CreatedReportId"));
		// getUserId
		int currentUserId = SystemUtil.getCurrentUserId();
		StringBuffer sb = new StringBuffer();
		try {
		reportDAO.deleteReport(createdReportId, String
					.valueOf(currentUserId));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * Search Report Name And Rtag Color Totail Number [Chao.Liu]
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String getReportNameAndColorTotailNO(SearchReportUserVO rvo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Report Name And Rtag Color Totail Number", rvo));
		
		StringBuffer sb = new StringBuffer();
		
		try {
			Integer totalResult = reportDAO.getReportNameTotailNO(rvo);
			sb.append(rvo.getTotalPageNoJson(totalResult));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * Search Report Name And Rtag Color Info List [Chao.Liu]
	 * @param rvo
	 * @return
	 * @throws BPLException
	 */
	public String searchReportNameAndColorList(SearchReportUserVO rvo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Report Name And Rtag Color Info List", rvo));
		
		StringBuffer sb = new StringBuffer();
		
		List ridl = null;
		try {
			ridl = reportDAO.searchReportNameList(rvo);
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (ridl != null && ridl.size() > 0) {
			sb.append("{begin:");
			sb.append(rvo.getStartIndex() + 1);
			sb.append(",end:");
			int size = ridl.size();
			sb.append(rvo.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				if (i != 0) sb.append(",");
				Object[] o = (Object[]) ridl.get(i);
//				sb.append("{");
//				sb.append("rid:"+o[0].toString()+",");
//				sb.append("rname:\""+o[1].toString()+"\",");
//				sb.append("alevel:\""+o[2].toString()+"\",");
//				sb.append("sharing:\""+(new Integer(o[2].toString()) == 3?"Y":"N")+"\",");
//				sb.append("fromDate:\""+(o[3].equals("Y")?"":"none")+"\",");
//				sb.append("toDate:\""+(o[4].equals("Y")?"":"none")+"\",");
//				sb.append("analyst:\""+(o[5].equals("Y")?"":"none")+"\",");
//				sb.append("onlyUser:\""+(o[6].equals("Y")?"Y":"N")+"\",");
//				sb.append("pBanId:\""+(o[7].equals("Y")?"Y":"N")+"\",");
//				sb.append("cData:[");
				
				sb.append("{");
				sb.append("rid:"+o[0].toString()+",");
				sb.append("rname:\""+o[1].toString()+"\",");
				sb.append("fromDate:\""+(o[2].equals("Y")?"":"none")+"\",");
				sb.append("toDate:\""+(o[3].equals("Y")?"":"none")+"\",");
				sb.append("analyst:\""+(o[4].equals("Y")?"":"none")+"\",");
				sb.append("onlyUser:\""+(o[5].equals("Y")?"Y":"N")+"\",");
				sb.append("pBanId:\""+(o[6].equals("Y")?"Y":"N")+"\",");
				sb.append("keyWord:\""+o[7].toString()+"\",");
				
				//wenbo.zhang 20190305 start
				sb.append("isLogin:\""+(o[8].equals("Y")?"Y":"N")+"\",");
				sb.append("isEffectiveHour:\""+(o[9].equals("Y")?"Y":"N")+"\",");
				sb.append("isDownloadTimes:\""+(o[10].equals("Y")?"Y":"N")+"\",");
				sb.append("isEmail:\""+(o[11].equals("Y")?"Y":"N")+"\",");				
				sb.append("userEmail:\""+SystemUtil.getCurrentEmail()+"\",");
				//wenbo.zhang 20190305 end
				
				sb.append("cData:[");
				
				List<String> rcls = reportDAO.SearchRtargColorByReportId(o[0]);
				for (int j = 0; j < rcls.size(); j++) {
					if (j != 0) sb.append(",");
					sb.append(rcls.get(j));
				}
				
				sb.append("]");
				sb.append("}");
			}
			sb.append("]");
		} else {
			sb.append("{error:\"no data.\"");
		}
		sb.append("}");

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * Created New Created Report Class [Chao.Liu]
	 * @param rvo
	 * @return
	 */
	public String saveCreatedReport(SearchReportUserVO rvo)throws RuntimeException{
		logger.info(CcmFormat.formatEnterLog("Created New Created Report Class", rvo));
		
		StringBuffer sb = new StringBuffer();
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(!(rvo.getStrJson().equals("")||rvo.getStrJson()== null)){
			String jsonStr = rvo.getStrJson();  
	        JSONObject strJson = JSONObject.fromObject(jsonStr);		        
	        Iterator ite = strJson.keys();			
	        while (ite.hasNext()) {
	            String key = ite.next().toString();
	            String value = strJson.get(key).toString();
	            map.put(key, value);
	        }
	        
		}
		
		try {
			int currenUserId = SystemUtil.getCurrentUserId();			
			Report r = new Report();
			r.setId(rvo.getReportId());
			CreatedReport cr = new CreatedReport();
			cr.setKeyWord(rvo.getKeyWord());
			cr.setReport(r);
			cr.setCreatedReportName(rvo.getCreatedReportNameNew());
			cr.setSharingFlag(rvo.getSharingNew());
			cr.setSendEmail(rvo.getSendEmailNew());
			cr.setEmail(rvo.getEmail());
			cr.setIsLogin(rvo.getIsLogin());
			cr.setEffectiveHour(rvo.getEffectiveHour().equals("") ? null : Double.parseDouble(rvo.getEffectiveHour()));
			cr.setDownloadTimes(rvo.getDownloadTimes().equals("") ? null : rvo.getDownloadTimes());
			cr.setReportStatus("1");
			cr.setStatusTimestamp(new Date());
			cr.setFromDate(rvo.getFdate().equals("") ? null : new Date(rvo
					.getFdate()));
			cr.setToDate(rvo.getTdate().equals("") ? null : new Date(rvo
					.getTdate()));
			cr.setReportFormat("csv");
			if (rvo.getOnlyUser().equals("N")) {
				if (rvo.getAnalystId() != null) {
					cr.setAnalystId(rvo.getAnalystId());
				} else {
					cr.setAnalystId(0);
				}
			} else {
				cr.setAnalystId(currenUserId);
			}
			cr.setOnlyUser(rvo.getOnlyUser());
			if (rvo.getPBanId() != null) {
				cr.setPbanId(rvo.getPBanId());
			}
			cr.setCreatedBy(currenUserId);
			cr.setCreatedTimestamp(new Date());
			cr.setModifiedBy(currenUserId);
			cr.setModifiedTimestamp(new Date());
			cr.setRecActiveFlag("Y");
			reportDAO.saveObject(cr);
			String basicPath = sysConfigDAO.getReportFilePath();
			cr.setFilePath(basicPath + "A_" + CcmFormat.formatDate(new Date())
					+ "/");
			cr.setFileName(r.getId() + "_" + cr.getId() + "_"
					+ cr.getCreatedReportName());
			reportDAO.updateObject(cr);
			Set set = map.entrySet();  
			Map.Entry[] entries = (Map.Entry[])set.toArray(new Map.Entry[set.size()]);  
			for (int i=0;i<entries.length;i++) {
				CreatedReportParameter crp = new CreatedReportParameter();
				crp.setReport(cr.getId());
				crp.setTableFiled(entries[i].getKey().toString());
				crp.setValue(entries[i].getValue().toString());
				crp.setCreatedBy(currenUserId);
				crp.setCreatedTimestamp(new Date());
				crp.setModifiedBy(currenUserId);
				crp.setModifiedTimestamp(new Date());
				crp.setRecActiveFlag("Y");
				reportDAO.saveObject(crp);
			}
			sb.append("{error:false}");
		}catch(RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException rte = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw rte;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * Check Name Is Repeat
	 * @param rvo
	 * @return
	 */
	public String getNameIsRepeat(SearchReportUserVO rvo){
		logger.info(CcmFormat.formatEnterLog("Check Name Is Repeat", rvo));
		
		StringBuffer sb = new StringBuffer();
		if(rvo.getCreatedReportNameNew().equals("")){
			sb.append("{isRepeat:\"Created Report Name Is Not Null!\"}");
		}else{
			Integer count = reportDAO.getNameIsRepeat("created_report", "created_report_name", rvo.getCreatedReportNameNew());
			
			if(count > 0){
				sb.append("{isRepeat:\"Report name is duplicated, please enter a new report name!\"}");
			}else{
				sb.append("{isRepeat:false}");
			}
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * Get Vendor Name By Ban Id
	 * @Author Chao.Liu Date: Nov 3, 2010
	 * @Belong To ReportUserAction.getVendorNameByBanId()
	 * @param rvo
	 * @return
	 */
	public String getVendorNameByBanId(SearchReportUserVO rvo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get Vendor Name By Ban Id", rvo));
		
		StringBuffer sb = new StringBuffer();
		try {
				sb.append(banDao.getVendorNameByBanId(rvo));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public IReportDAO getReportDAO() {
		return reportDAO;
	}
	public void setReportDAO(IReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}
	/**
	 * @author pengfei.wang
	 * Search Report Name And Rtag Color Totail Number
	 * */
	public String getViewZReportNameAndColorTotailNo(SearchReportUserVO rvo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Statistics show that color ", rvo));
		StringBuffer sb = new StringBuffer();		
		Integer totalResult = reportDAO.getViewReportNameTotailNo(rvo);
		sb.append(rvo.getTotalPageNoJson(totalResult));

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * Search Report Name And Rtag Color Info List
	 * */
	public String serchViewReportNameColorList(SearchReportUserVO rvo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Statistics show that color ", rvo));
		StringBuffer sb = new StringBuffer();
		
		List ridl = null;
		try {
			ridl = reportDAO.searchViewReportName(rvo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (ridl != null && ridl.size() > 0) {
			sb.append("{begin:");
			sb.append(rvo.getStartIndex() + 1);
			sb.append(",end:");
			int size = ridl.size();
			sb.append(rvo.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				if (i != 0) sb.append(",");
				Object[] o = (Object[]) ridl.get(i);
				sb.append("{");
				sb.append("id:"+o[0].toString()+",");
				sb.append("rname:\""+o[1].toString()+"\",");
				sb.append("cData:[");
				
				List<String> rcls = reportDAO.SearchViewColor(o[0]);
				for (int j = 0; j < rcls.size(); j++) {
					if (j != 0) sb.append(",");
					sb.append(rcls.get(j));
				}
				
				sb.append("]");
				sb.append("}");
			}
			sb.append("]");
		} else {
			sb.append("{error:\"no data.\"");
		}
		sb.append("}");
		logger.info(CcmFormat.formatExitLog());
		logger.info("theResultString:"+sb.toString());
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * Created_report inquiry statistics
	 * */
	public String getViewNameTotalPageNo(SearchReportUserVO svo)throws BPLException {
		logger.info(CcmFormat.formatEnterLog("The number of ReportUser ViewName",svo));
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = reportDAO.getViewNameReportTotailNo(svo);
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
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * @author pengfei.wang
	 * Created_report query
	 * */
	public String searcgViewNameList(SearchReportUserVO svo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("ReportUser inquires the ViewName data",svo));
		StringBuffer sb = new StringBuffer();
		int loginUser=SystemUtil.getCurrentUserId();
		List<String> list = null;
		try {
			list = reportDAO.SearchViewNameReport(svo,String.valueOf(loginUser));
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

	public String doReportParameterFunction(String reportId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("doValidate Report Parameter Function.", reportId));
		StringBuffer sb = new StringBuffer();
		List list = null;
		try{
			list  = reportDAO.getReportParameter(reportId);

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if(list!=null&&list.size()>0){
			int size = list.size();
			sb.append("{data:[");
			for(int i = 0; i<size; i++){
				Object str = list.get(i);
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
	
	public ISysConfigDAO getSysConfigDAO() {
		return sysConfigDAO;
	}

	public void setSysConfigDAO(ISysConfigDAO sysConfigDAO) {
		this.sysConfigDAO = sysConfigDAO;
	}

	public IBanDao getBanDao() {
		return banDao;
	}

	public void setBanDao(IBanDao banDao) {
		this.banDao = banDao;
	}
	
}
