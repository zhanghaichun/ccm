package com.saninco.ccm.service.rtag;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IRtagDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Report;
import com.saninco.ccm.model.Rtag;
import com.saninco.ccm.model.RtagReport;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ViewReportAdminVO;

/**
 * 
 * @author Chao.Liu (Optimization of complete by xinyu.Liu)
 *
 */
public class RtagServiceImpl implements IRtagService {
	
	private static final long serialVersionUID = -7778316099624206431L;
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IRtagDao rtagDao;

	/**
	 * @author chao.Liu (viewRtagReportAdmin.js)
	 * Search Reports Option List Info 
	 * 
	 */
	public String getReportsOptionList(ViewReportAdminVO rvo) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Search Reports Option List Info ", rvo));
		StringBuffer sb = new StringBuffer();
		List<String> rl = null;
		String s1 = "";
		String s2 = "";
		try {
			rl = rtagDao.getRoprtsNameList();
			List<String> rrl = rtagDao.getRtagRoprtsNameList(rvo.getRtagId());
			for(int i=0; i<rrl.size(); i++){
				for(int j=0; j<rl.size(); j++){
					if(rl.get(j).equals(rrl.get(i))){
						rl.remove(j);
						break;
					}
				}
				if(i!=0) s2+=",";
				s2+=rrl.get(i);
			}
			for(int i=0; i<rl.size(); i++){
				if(i!=0) s1+=",";
				s1+=rl.get(i);
			}
			sb.append("{s1:["+s1+"],s2:["+s2+"]}");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author chao.Liu (viewRtagReportAdmin.js)
	 * Save RtagReports
	 * 
	 */
	public String saveReports(ViewReportAdminVO rvo){
		logger.info(CcmFormat.formatEnterLog("Save RtagReports", rvo));
		StringBuffer sb = new StringBuffer();
		try {
			List<RtagReport> rrl = rtagDao.getRtagReportListByRtid(rvo.getRtagId());
			if (!rvo.getOptionParamer().equals("DelAll")) {
				String[] reids = rvo.getOptionParamer().split(",");
				for (int i = 0; i < reids.length; i++) {
					Integer count = rtagDao.getRtagAndReportCount(rvo
							.getRtagId(), reids[i]);
					if (count == 0) {
						RtagReport rr = new RtagReport();
						rr.setRtag(new Rtag(rvo.getRtagId()));
						rr.setReport(new Report(new Integer(reids[i])));
						rr.setCreatedBy(SystemUtil.getCurrentUserId());
						rr.setCreatedTimestamp(new Date());
						rr.setModifiedBy(SystemUtil.getCurrentUserId());
						rr.setModifiedTimestamp(new Date());
						rr.setRecActiveFlag("Y");
						rtagDao.saveObject(rr);
						reids[i] = null;
					}
				}
				for (int i = 0; i < rrl.size(); i++) {
					for (int j = 0; j < reids.length; j++) {
						if (reids[j] == null) {
							continue;
						}
						RtagReport rr = (RtagReport) rtagDao.getObject(
								RtagReport.class, rrl.get(i).getId());
						int reid = new Integer(reids[j]);
						if (rr.getReport().getId() == reid) {
							rrl.remove(i);
						}
					}
				}
			}
			for (int i = 0; i < rrl.size(); i++) {
				RtagReport rr = (RtagReport) rtagDao.getObject(
						RtagReport.class, rrl.get(i).getId());
				rr.setRecActiveFlag("N");
			}
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
	 * @author chao.Liu (viewRtagReportAdmin.js)
	 * Search Tags Name List Info
	 * 
	 */
	public String getTagsNameList() throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Search Tags Name List Info"));
		StringBuffer sb = new StringBuffer("[");
		sb.append("{key:\"isNull\",value:\"-Choose-\"}");
		List<Object[]> rList = null;
		try {
			rList = rtagDao.getRtag();
			for(Object r : rList){
	            Object[] rtag = (Object[])r;
	            sb.append(",{key:"+rtag[0].toString()+",value:\""+rtag[1].toString()+"\"}");
	        }
			sb.append("]");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public RtagServiceImpl() {
	}

	public void setRtagDao(IRtagDao rtagDao) {
		this.rtagDao = rtagDao;
	}
}
