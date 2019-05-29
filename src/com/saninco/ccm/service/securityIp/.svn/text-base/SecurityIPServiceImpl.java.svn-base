package com.saninco.ccm.service.securityIp;

import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.ISecurityIPDAO;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.SecurityIp;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.vo.ViewSecurityIpVo;

public class SecurityIPServiceImpl implements ISecurityIPService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private ISecurityIPDAO securityIPDAO;
	public String getSeacurityTotalPageNo(ViewSecurityIpVo viewSecurityIpVo) throws BPLException {
		logger.info(CcmFormat.formatEnterLog("Get total page number and result number.", viewSecurityIpVo));

		StringBuffer sb = new StringBuffer();
		try {
			long count = securityIPDAO.getSeacurityTotalPageNo(viewSecurityIpVo);
			sb.append(viewSecurityIpVo.getTotalPageNoJson(count));
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
		
	}
	
	public String searchSecurityIp(ViewSecurityIpVo viewSecurityIpVo)
	throws BPLException {
		logger.info(CcmFormat.formatEnterLog("SearchSecurityIp..Service", viewSecurityIpVo));
		StringBuffer sb = new StringBuffer();
		try {
			List<String> list = securityIPDAO.searchSecurityIp(viewSecurityIpVo);
			sb.append(viewSecurityIpVo.getListJsonCompatible(list));
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	
	public String updateSecurity(ViewSecurityIpVo viewSecurityIpVo)
	throws BPLException {
		String result;
		try {
				result = updateCheckSecuirty(viewSecurityIpVo);
				if("success".equals(result)){
					securityIPDAO.updateSecurity(viewSecurityIpVo);	
					return "{success:'success'}";
				}
				return result;
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	
	}
	
	public String reactiveSecurity(ViewSecurityIpVo viewSecurityIpVo)
	throws BPLException {
		String result;
		try {
				securityIPDAO.reactiveSecurity(viewSecurityIpVo);	
				return "{}";
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	
	}
	
	public void delSecurityIp(ViewSecurityIpVo viewSecurityIpVo) throws BPLException {
		try{
			securityIPDAO.delSecurityIp(viewSecurityIpVo);
		} catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		logger.info(CcmFormat.formatExitLog());
		
	}
	
	public String addSecurityIp(ViewSecurityIpVo viewSecurityIpVo) throws BPLException {
		String result;
		try {
			result = checkSecuirtyRecActiveFlageN(viewSecurityIpVo);
			if("success".equals(result)){
				securityIPDAO.addSecurityIp(viewSecurityIpVo);	
				return "{success:'success'}";
			}
			return result;
		}catch (Exception e) {
			logger.error(CcmFormat.formatErrorLog(e));
			RuntimeException bpe = new RuntimeException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
	}
	
	private String checkSecuirtyRecActiveFlageN(ViewSecurityIpVo viewSecurityIpVo){
		List<SecurityIp> list = securityIPDAO.checkSecuirtyRecActiveFlageN(viewSecurityIpVo);
		
		if(list.size() > 0){
			SecurityIp item = list.get(0);
			if("Y".equals(item.getRecActiveFlag())){
				return "{msg:'IP already exists! Do you want to update it?',securityIp:'"+item.getId()+"'}";
			}else{
				return "{msg:'This IP is inactive, do you want to activate it?',securityIp:'"+item.getId()+"'}";
			}
		}
		return "success";
	}
	
	private String updateCheckSecuirty(ViewSecurityIpVo viewSecurityIpVo){
		List<SecurityIp> list = securityIPDAO.checkSecuirtyRecActiveFlageN(viewSecurityIpVo);
		
		if(list.size() > 0){
			SecurityIp item = list.get(0);
			
			if(!viewSecurityIpVo.getSecurityId().equals(item.getId().toString())){
				if("Y".equals(item.getRecActiveFlag())){
					return "{msg:'IP already exists!'}";
				}else{
					return "{msg:'This IP address is inactive. Do you want to reactive it now?',securityIp:'"+item.getId()+"'}";
				}
			}
		}
		return "success";
	}
	
	public String addSecurityIpTrueOrFalse(String securityIp,
			String securityStartDate, String securityEndDate,String securityUserName)
			throws BPLException {
		securityIPDAO.addSecurityIpTrueOrFalse(securityIp,securityStartDate,securityEndDate,securityUserName);	
		return "{}";
	}
	
	public ISecurityIPDAO getSecurityIPDAO() {
		return securityIPDAO;
	}
	public void setSecurityIPDAO(ISecurityIPDAO securityIPDAO) {
		this.securityIPDAO = securityIPDAO;
	}
}
