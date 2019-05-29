package com.saninco.ccm.service.dashboard;

import java.util.List;
import java.util.Map;

import org.jfree.chart.JFreeChart;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.bi.BIAuditReference;
import com.saninco.ccm.model.bi.BIBudget;
import com.saninco.ccm.model.bi.BIProduct;
import com.saninco.ccm.model.bi.BIProvince;
import com.saninco.ccm.model.bi.BIQuoteAging;
import com.saninco.ccm.model.bi.BIQuoteIssuedStatus;
import com.saninco.ccm.model.bi.BIQuoteNetType;
import com.saninco.ccm.model.bi.BIQuoteProduct;
import com.saninco.ccm.model.bi.BIQuoteVendor;
import com.saninco.ccm.model.bi.BIQuoteVendorResponseStatus;
import com.saninco.ccm.model.bi.BITimePeriod;
import com.saninco.ccm.model.bi.BIVendor;
import com.saninco.ccm.vo.DashboardVO;
import com.saninco.ccm.vo.dashboard.DashboardChartDataVO;
import com.saninco.ccm.vo.dashboard.DashboardControlVO;
import com.saninco.ccm.vo.dashboard.DashboardModuleVO;
import com.saninco.ccm.vo.dashboard.UserDashboardModuleVO;
import com.saninco.ccm.vo.dashboard.UserDashboardVO;

public interface IDashboardService {
	
	/**
	 * @author Chao.Liu
	 * Search Dashboarc HTML
	 * @return
	 */
	public String saveAndGetPageHtml();
	/**
	 * @author Chao.Liu
	 * @param dvo
	 * @return
	 */
	public String saveDashboardPosition(DashboardVO dvo);
	public JFreeChart getInvoiceAtTransactionDayChart() throws BPLException;

	/**
	 * 查询 Dashboard List 相关信息的抽象方法。
	 * @param pageRecords 
	 * @param currentPage 
	 * @param dateCreated 
	 * @param dashboardName 
	 * @return [description]
	 * @throws BPLException  [description]
	 */
	public List<Map<String,Object>> searchDashboardListInfo(String dashboardNameSort, String dateCreatedSort, Integer currentPage, Integer pageRecords) throws BPLException;

	public JFreeChart getUserInvoiceAtTransactionDayChart()throws BPLException;

	public JFreeChart getInvoiceAtDashletDayChart()throws BPLException;

	public JFreeChart getUserInvoiceAtDashletDayChart()throws BPLException;
	
	public UserDashboardVO getUserDashboard();
	public List<BITimePeriod> getAllTimePeriod();
	/**
	 * 查询 timePeriod List
	 * @param listType globalType | unQuoteType | quoteType
	 * @return
	 */
	public List<BITimePeriod> hqlFindTimePeriodList(String listType);
	public List<BIVendor> getAllVendor();
	public List<BIProduct> getAllProduct();
	public List<BIProvince> getAllProvince();
	public List<BIBudget> getAllBudget();
	public List<BIQuoteAging> getAllQuoteAging();
	public List<BIQuoteIssuedStatus> getAllQuoteIssuedStatus();
	public List<BIQuoteNetType> getAllQuoteNetType();
	public List<BIQuoteProduct> getAllQuoteProduct();
	public List<BIQuoteVendor> getAllQuoteVendor();
	public List<BIQuoteVendorResponseStatus> getAllQuoteVendorResponseStatus(); 
	public List<DashboardModuleVO> getAllDashboardModule();
	public List<BIAuditReference> getAllAuditReference();
	public List<DashboardChartDataVO> getUserDashboardModuleData(Integer userDashboardModuleId);
	public UserDashboardVO updateAndGetUserDashboardById(Integer id);
	public UserDashboardModuleVO saveControlData(Integer userDashboardModuleId, String controlKey, Object[] controlValue);
	public List<DashboardControlVO> getGlobalDashboardControl();
	public void updateUserDashboardViewMode(Integer userDashboardModuleId, String viewMode);
	public UserDashboardModuleVO updateUserDashboardListBy(Integer userDashboardModuleId, String controlKey, String controlFilterKey);
	public String deleteDashboardListItem(Integer userDashboardId);
	public UserDashboardModuleVO addNewDashboarModule(Integer userDashboardId, Integer dashboardModuleId);
	public UserDashboardVO addNewDashboard();
	public void updateAndDeleteUserDashboardModule(Integer userDashboardModuleId);
	public String updateUserDashboardName(Integer id, String dashboardName);
	public void updateUserDashboardModuleMoveNewSort(Integer userDashboardModuleId, Integer sortNo);
	public Integer getTotalRecordsNo();
	public String updateUserDashboardModuleName(Integer id, String userDashboardModuleName);
	public void updateUserDashboardModuleShowPercent(Integer id, String moduleShowPercentFlag);
	public String searchDashboardListByConditions(String dashboardNameSort, String dateCreatedSort, Integer currentPage, Integer pageRecords) throws Exception;
}
