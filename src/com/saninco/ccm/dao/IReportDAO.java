package com.saninco.ccm.dao;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.model.Role;
import com.saninco.ccm.model.Rtag;
import com.saninco.ccm.model.RtagRole;
import com.saninco.ccm.vo.SearchReportUserVO;
import com.saninco.ccm.vo.ViewReportAdminVO;
import com.saninco.ccm.vo.ViewSecurityIpVo;

public interface IReportDAO {
	
	public long findTagsAndRolesNumber(ViewReportAdminVO viewReportAdminVO);
	
	public List getBANMetricsList(String sql);	
	
	public long getTagsNumberByRtagRole(ViewReportAdminVO viewReportAdminVO);
	
	public List<String> getReportAdminTags(ViewReportAdminVO viewReportAdminVO);

	/*
	 * By hongtao 2011-09-20
	 */
	public void deleteReport(String createdReportId,String deleteReport);
	
	public void deleteTagsInTagsAndRoles(ViewReportAdminVO viewReportAdminVO);
	
	public long getNumberOfReportAdminTags(ViewReportAdminVO viewReportAdminVO);
	
	public List<String> getReportAdminTagsAndRoles(ViewReportAdminVO viewReportAdminVO);
	
	public long getNumberOfReportAdminTagsAndRoles(ViewReportAdminVO viewReportAdminVO);
	
	public void saveRtag(Rtag rtag);

	public Rtag findRtagById(Integer id);
	
	public void saveRtagRole(RtagRole rtagRole);

	public RtagRole findRtagRoleById(Integer id);
	
	public Role findRoleById(Integer id);
	
	public void saveIReportExcel(String jasperName,String excelPath);
	
	public void saveIReportExcel(String[] jasperNames,String excelPath);
	
	public String formatPath(String fileName);
	
	public List<Object[]> getIReportInfos(String runType);
	
	public List searchReportNameList(SearchReportUserVO rvo);
	
	public Integer getReportNameTotailNO(SearchReportUserVO rvo);
	
	public List<String> SearchRtargColorByReportId(Object rid);
	
	public Integer getNameIsRepeat(String tableName,String fieldName,String name);
	
	public void updateCreatedReport(Object crid,Object stutas);
	
	public void updatePathAndName(Object path,Object name,Object id);
	
	public Object getObject(Class c , Integer id);
	
	public void saveObject(Object o);	
	
	public void updateObject(Object o);
	
	public List searchViewReportName(SearchReportUserVO rvo);
	
	public Integer getViewReportNameTotailNo(SearchReportUserVO rvo);
	
	public List<String> SearchViewColor(Object rid);
	
	public List<String> SearchViewNameReport(SearchReportUserVO rvo,String loginUser);
	
	public Integer getViewNameReportTotailNo(SearchReportUserVO rvo);

	public java.lang.Character getUserMaxLevel(String reportId);
	
	public List getReportParameter(String reportId);

	public long getTeamTotalPageNo(ViewReportAdminVO viewReportAdminVO);

	public List<String> searchTeams(ViewReportAdminVO viewReportAdminVO);
	
	public void delTeam(int parseInt);

	public void updateTeam(int parseInt, int parseInt2, int parseInt3);
	
	public void addTeam(int parseInt, int parseInt2);

	public Integer findTeamByfromIdAndToId(int parseInt, int parseInt2);	
	
	public Map<String, String> selectFileDownLoad(int id);
	
	public Map<String, String> selectFileDownLoadVerifyValue(int id);
	
	public Integer findfileDownloadJournal (int id);
	
	public void savefileDownloadJournal(String loginFlag,int fileDownloadId,String ip);
}
