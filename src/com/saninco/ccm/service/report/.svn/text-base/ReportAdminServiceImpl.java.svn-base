package com.saninco.ccm.service.report;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IReportDAO;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.Rtag;
import com.saninco.ccm.model.RtagRole;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ViewReportAdminVO;
import com.saninco.ccm.vo.ViewSecurityIpVo;

/**
 * @author xinyu.Liu (Optimization of complete by xinyu.Liu)
 * 
 */
public class ReportAdminServiceImpl implements IReportAdminService {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	
	private IReportDAO reportDAO;
	
	public ReportAdminServiceImpl() {
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Verify whether the tag data to be used
	 * 
	 */
	public boolean validationTagsData(ViewReportAdminVO viewReportAdminVO) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Verify whether the tag data to be used.", viewReportAdminVO));
		boolean bo = false;
		try {
			long count = reportDAO.getTagsNumberByRtagRole(viewReportAdminVO);
			if(count == 0) bo= true;
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return bo;
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Revises a data in the rtag table  
	 * 
	 */
	public void updateReportTags(String rtagId, ViewReportAdminVO viewReportAdminVO){
		logger.info(CcmFormat.formatEnterLog("Revises a data in the rtag table.", rtagId ,viewReportAdminVO));
		Rtag rtag = new Rtag();
		try {
			rtag = reportDAO.findRtagById(Integer.parseInt(rtagId));	
			rtag.setRtagName(viewReportAdminVO.getTagsName());
			rtag.setRtagColor(viewReportAdminVO.getTagsColor());
			rtag.setModifiedBy(SystemUtil.getCurrentUserId());
			rtag.setModifiedTimestamp(new Date());
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	
	public String updateTeam(String teamId, String fromId, String toId)
	throws BPLException {
		String result;
		try {
			result = checkTeamDuplicate(fromId,toId);
			if("success".equals(result)){
				reportDAO.updateTeam(Integer.parseInt(teamId),Integer.parseInt(fromId),Integer.parseInt(toId));	
				return "{}";
			}else{
				return "{msg:'Team already exists!'}";
			}
				
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	
	}
	
	public String addTeam(String fromId, String toId)
	throws BPLException {
		String result;
		try {
			result = checkTeamDuplicate(fromId,toId);
			if("success".equals(result)){
				reportDAO.addTeam(Integer.parseInt(fromId),Integer.parseInt(toId));	
				return "{}";
			}else{
				return "{msg:'Team already exists!'}";
			}
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	
	}

	public String checkTeamDuplicate(String fromId, String toId){
		try {
			Integer count = reportDAO.findTeamByfromIdAndToId(Integer.parseInt(fromId),Integer.parseInt(toId));	
			
			if(count.intValue() != 0 ){
				return "error";
			}
			else{
				return "success";
			}
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Increases a data in the rtag table  
	 *  
	 */
	public void addReportTags(ViewReportAdminVO viewReportAdminVO) {
		logger.info(CcmFormat.formatEnterLog("Increases a data in the rtag table.", viewReportAdminVO));
		Rtag rtag = new Rtag();
		try {
			rtag.setRtagName(viewReportAdminVO.getTagsName());
			rtag.setRtagColor(viewReportAdminVO.getTagsColor());
			rtag.setCreatedBy(SystemUtil.getCurrentUserId());
			rtag.setCreatedTimestamp(new Date());
			rtag.setModifiedBy(SystemUtil.getCurrentUserId());
			rtag.setModifiedTimestamp(new Date());
			rtag.setRecActiveFlag("Y");
			reportDAO.saveRtag(rtag);
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Deletes a data in the rtag table  
	 * rtag.setRecActiveFlag("N");
	 */
	public void deleteReportTags(String rtagId) {
		logger.info(CcmFormat.formatEnterLog(" Deletes a data in the rtag table .", rtagId));
		Rtag rtag = new Rtag();
		try {
			rtag = reportDAO.findRtagById(Integer.parseInt(rtagId));
			rtag.setModifiedBy(SystemUtil.getCurrentUserId());
			rtag.setModifiedTimestamp(new Date());
			rtag.setRecActiveFlag("N");
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Association Delete Tags.
	 * 
	 */
	public void associationDeleteTagsInTagsAndRoles(ViewReportAdminVO viewReportAdminVO) {
		logger.info(CcmFormat.formatEnterLog("Association Delete Tags.", viewReportAdminVO));
		try {
			reportDAO.deleteTagsInTagsAndRoles(viewReportAdminVO);
			deleteReportTags(viewReportAdminVO.getRtagId().toString());
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * Split List to String
	 */
	public String splitList(List<MapEntryVO<String, String>> list){
		logger.info(CcmFormat.formatEnterLog("Split List to String.", list));
		StringBuffer sb = new StringBuffer();
		sb.append("{data:[");
		for(int i = 0; i < list.size(); i++){
			if(i != 0){
				sb.append(",");
			}
			MapEntryVO<String, String> mapEntryVO = list.get(i);
			String id = mapEntryVO.getKey().toString();
			String name = mapEntryVO.getValue().toString();
			sb.append("{id:" + id + ",name:\"" + name + "\" }");
		}
		sb.append("]}");
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Search Delegation by ViewSecurityVO.
	 * Return JSON string.
	 * 
	 */
	public String searchReportAdminTags(ViewReportAdminVO viewReportAdminVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Delegation by ViewSecurityVO.", viewReportAdminVO));
		StringBuffer sb = new StringBuffer();
		try {
			List<String> list = reportDAO.getReportAdminTags(viewReportAdminVO);
			sb.append(viewReportAdminVO.getListJsonCompatible(list));
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	
	public String searchTeams(ViewReportAdminVO viewReportAdminVO)
	throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Delegation by ViewSecurityVO.", viewReportAdminVO));
		StringBuffer sb = new StringBuffer();
		try {
			List<String> list = reportDAO.searchTeams(viewReportAdminVO);
			sb.append(viewReportAdminVO.getListJsonCompatible(list));
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Get total page number and result number.
	 * Return JSON string.
	 * 
	 */
	public String getReportAdminTagsTotalPageNo(ViewReportAdminVO viewReportAdminVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get total page number and result number.", viewReportAdminVO));
		StringBuffer sb = new StringBuffer();
		try {
			long count = reportDAO.getNumberOfReportAdminTags(viewReportAdminVO);
			sb.append(viewReportAdminVO.getTotalPageNoJson(count));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String getTeamTotalPageNo(ViewReportAdminVO viewReportAdminVO)
	throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get total page number and result number.", viewReportAdminVO));
		StringBuffer sb = new StringBuffer();
		try {
			long count = reportDAO.getTeamTotalPageNo(viewReportAdminVO);
			sb.append(viewReportAdminVO.getTotalPageNoJson(count));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Revises a data in the rtag and role table  
	 * 
	 */
	public void updateReportTagsAndRoles(String rtagRoleId, ViewReportAdminVO viewReportAdminVO) {
		logger.info(CcmFormat.formatEnterLog("Revises a data in the rtag and role table.", viewReportAdminVO));
		Rtag rtag = new Rtag();
		Role role = new Role();
		RtagRole rtagRole = new RtagRole();
		try {
			rtagRole = reportDAO.findRtagRoleById(Integer.parseInt(rtagRoleId));	
			rtag = reportDAO.findRtagById(viewReportAdminVO.getRtagId());
			role = reportDAO.findRoleById(Integer.parseInt(viewReportAdminVO.getRoleId()));
			
			rtagRole.setRtag(rtag);
			rtagRole.setRole(role);
//			rtagRole.setAccessLevel(viewReportAdminVO.getAccessLevel());
			rtagRole.setAccessLevel("3");
//			rtagRole.setSendEmail(viewReportAdminVO.getEmail());
			rtagRole.setSendEmail("3");
			rtagRole.setModifiedBy(SystemUtil.getCurrentUserId());
			rtagRole.setModifiedTimestamp(new Date());
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * The same data already exists.
	 * 
	 */
	public String judgementDataTagsAndRoles(ViewReportAdminVO viewReportAdminVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("The same data already exists.", viewReportAdminVO));
		String str = "{data:true}";
		try {
			long count = reportDAO.findTagsAndRolesNumber(viewReportAdminVO);
			if(count != 0) str = "{data:false}";
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return str;
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Increases a data in the rtagRole table  
	 * 
	 */
	public void addReportTagsAndRoles(ViewReportAdminVO viewReportAdminVO) {
		logger.info(CcmFormat.formatEnterLog("Increases a data in the rtagRole table.", viewReportAdminVO));
		Rtag rtag = new Rtag();
		Role role = new Role();
		RtagRole rtagRole = new RtagRole();
		try{
			rtag = reportDAO.findRtagById(viewReportAdminVO.getRtagId());
			role = reportDAO.findRoleById(Integer.parseInt(viewReportAdminVO.getRoleId()));
			
			rtagRole.setRtag(rtag);
			rtagRole.setRole(role);
//			rtagRole.setAccessLevel(viewReportAdminVO.getAccessLevel()); -- 功能取消,默认赋值3
			rtagRole.setAccessLevel("3");
//			rtagRole.setSendEmail(viewReportAdminVO.getEmail());
			rtagRole.setSendEmail("3");
			rtagRole.setCreatedBy(SystemUtil.getCurrentUserId());
			rtagRole.setCreatedTimestamp(new Date());
			rtagRole.setModifiedBy(SystemUtil.getCurrentUserId());
			rtagRole.setModifiedTimestamp(new Date());
			rtagRole.setRecActiveFlag("Y");
			reportDAO.saveRtagRole(rtagRole);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Deletes a data in the rtagRole table  
	 * rtagRole.setRecActiveFlag("N");
	 * 
	 */
	public void deleteReportTagsAndRoles(String rtagRoleId) {
		logger.info(CcmFormat.formatEnterLog("Deletes a data in the rtagRole table .", rtagRoleId));
		RtagRole rtagRole = new RtagRole();
		try{
			rtagRole = reportDAO.findRtagRoleById(Integer.parseInt(rtagRoleId));
			rtagRole.setModifiedBy(SystemUtil.getCurrentUserId());
			rtagRole.setModifiedTimestamp(new Date());
			rtagRole.setRecActiveFlag("N");
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}
	
	public void delTeam(String teamId) throws BPLException {
		try{
			reportDAO.delTeam(Integer.parseInt(teamId));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
	}

	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Search Delegation by ViewSecurityVO.
	 * Return JSON string.
	 * 
	 */
	public String searchReportAdminTagsAndRoles(ViewReportAdminVO viewReportAdminVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Search Delegation by ViewSecurityVO.", viewReportAdminVO));
		StringBuffer sb = new StringBuffer();
		try {
			List<String> list = reportDAO.getReportAdminTagsAndRoles(viewReportAdminVO);
			sb.append(viewReportAdminVO.getListJsonCompatible(list));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	/**
	 * @author xinyu.Liu (viewReportAdmin.js)
	 * Get total page number and result number.
	 * Return JSON string.
	 * 
	 */
	public String getReportAdminTagsAndRolesTotalPageNo(ViewReportAdminVO viewReportAdminVO) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get total page number and result number.", viewReportAdminVO));
		StringBuffer sb = new StringBuffer();
		try {
			long count = reportDAO.getNumberOfReportAdminTagsAndRoles(viewReportAdminVO);
			sb.append(viewReportAdminVO.getTotalPageNoJson(count));
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String doValidateUserReportFunction(String reportId) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("doValidate User Report Function.", reportId));
		String r = "{data:false}";
		try{
			java.lang.Character c = reportDAO.getUserMaxLevel(reportId);
			if(c == null) return "{data:false}";
			r = (Integer.parseInt(new StringBuilder().append(c).toString()) > 2) ? "{data:true}" : "{data:false}";
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage(),e);
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return r;
	}

	/**
	 * Blob compiler
	 * */
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

	public IReportDAO getReportDAO() {
		return reportDAO;
	}

	public void setReportDAO(IReportDAO reportDAO) {
		this.reportDAO = reportDAO;
	}






	

	

}
