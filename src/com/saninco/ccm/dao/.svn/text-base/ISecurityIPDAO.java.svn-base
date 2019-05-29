package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.SecurityIp;
import com.saninco.ccm.vo.ViewSecurityIpVo;

public interface ISecurityIPDAO {
	public long getSeacurityTotalPageNo(ViewSecurityIpVo viewSecurityIpVo);
	
	public List<String> searchSecurityIp(ViewSecurityIpVo viewSecurityIpVo);
	
	public void updateSecurity(ViewSecurityIpVo viewSecurityIpVo);
	
	public void reactiveSecurity(ViewSecurityIpVo viewSecurityIpVo);
	
	public void delSecurityIp(ViewSecurityIpVo viewSecurityIpVo);
	
	public void addSecurityIp(ViewSecurityIpVo viewSecurityIpVo);

	public Integer findSecurityIp(String securityIp,String securityStartDate,String securityEndDate);
	
	public List<SecurityIp> checkSecuirtyRecActiveFlageN(ViewSecurityIpVo viewSecurityIpVo);
	
	public void addSecurityIpTrueOrFalse(String securityIp,String securityStartDate,String securityEndDate,String securityUserName);
}
