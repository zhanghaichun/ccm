package com.saninco.ccm.service.report;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.ViewReportAdminVO;
import com.saninco.ccm.vo.ViewSecurityIpVo;

public interface IReportAdminService {
	
	public String splitList(List<MapEntryVO<String, String>> list);
	
	public String searchReportAdminTags(ViewReportAdminVO viewReportAdminVO) throws BPLException;
	
	public String getReportAdminTagsTotalPageNo(ViewReportAdminVO viewReportAdminVO) throws BPLException;
	
	public void deleteReportTags(String rtagId);
	
	public void associationDeleteTagsInTagsAndRoles(ViewReportAdminVO viewReportAdminVO);
	
	public void addReportTags(ViewReportAdminVO viewReportAdminVO);
	
	public void updateReportTags(String rtagId, ViewReportAdminVO viewReportAdminVO);
	
	public String searchReportAdminTagsAndRoles(ViewReportAdminVO viewReportAdminVO) throws BPLException;
	
	public String getReportAdminTagsAndRolesTotalPageNo(ViewReportAdminVO viewReportAdminVO) throws BPLException;
	
	public boolean validationTagsData(ViewReportAdminVO viewReportAdminVO) throws BPLException;
	
	public void deleteReportTagsAndRoles(String rtagRoleId);
	
	public void addReportTagsAndRoles(ViewReportAdminVO viewReportAdminVO);
	
	public String judgementDataTagsAndRoles(ViewReportAdminVO viewReportAdminVO) throws BPLException ;
	
	public void updateReportTagsAndRoles(String rtagRoleId, ViewReportAdminVO viewReportAdminVO);

	public String doValidateUserReportFunction(String reportId)throws BPLException ;
	
	public String getTeamTotalPageNo(ViewReportAdminVO viewReportAdminVO)throws BPLException;
	
	public String searchTeams(ViewReportAdminVO viewReportAdminVO)throws BPLException;
	
	public void delTeam(String rtagRoleId)throws BPLException;
	
	public String updateTeam(String teamId, String fromId, String toId)throws BPLException;
	
	public String addTeam(String fromId, String toId)throws BPLException;
		
	public String checkTeamDuplicate(String fromId, String toId);
	
}
