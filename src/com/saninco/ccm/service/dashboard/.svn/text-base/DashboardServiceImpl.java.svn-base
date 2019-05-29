/**
 * 
 */
package com.saninco.ccm.service.dashboard;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.Layer;
import org.jfree.ui.TextAnchor;

import com.saninco.ccm.dao.IBIAuditReferenceDao;
import com.saninco.ccm.dao.IBIBudgetDao;
import com.saninco.ccm.dao.IBIDashboardControlDao;
import com.saninco.ccm.dao.IBIDashboardListbyDao;
import com.saninco.ccm.dao.IBIDashboardModuleDao;
import com.saninco.ccm.dao.IBIProductDao;
import com.saninco.ccm.dao.IBIProvinceDao;
import com.saninco.ccm.dao.IBITimePeriodDao;
import com.saninco.ccm.dao.IBIUserDashboardDao;
import com.saninco.ccm.dao.IBIUserDashboardModuleDao;
import com.saninco.ccm.dao.IBIVendorDao;
import com.saninco.ccm.dao.IBIVendorSpendDashboardDao;
import com.saninco.ccm.dao.IDashboardDao;
import com.saninco.ccm.dao.IInvoiceDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Dashboard;
import com.saninco.ccm.model.bi.BIAuditReference;
import com.saninco.ccm.model.bi.BIBudget;
import com.saninco.ccm.model.bi.BIDashboardControl;
import com.saninco.ccm.model.bi.BIDashboardListby;
import com.saninco.ccm.model.bi.BIDashboardModule;
import com.saninco.ccm.model.bi.BIProduct;
import com.saninco.ccm.model.bi.BIProvince;
import com.saninco.ccm.model.bi.BIQuoteAging;
import com.saninco.ccm.model.bi.BIQuoteIssuedStatus;
import com.saninco.ccm.model.bi.BIQuoteNetType;
import com.saninco.ccm.model.bi.BIQuoteProduct;
import com.saninco.ccm.model.bi.BIQuoteVendor;
import com.saninco.ccm.model.bi.BIQuoteVendorResponseStatus;
import com.saninco.ccm.model.bi.BITimePeriod;
import com.saninco.ccm.model.bi.BIUserDashboard;
import com.saninco.ccm.model.bi.BIUserDashboardModule;
import com.saninco.ccm.model.bi.BIVendor;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.DashboardVO;
import com.saninco.ccm.vo.dashboard.DashboardChartDataVO;
import com.saninco.ccm.vo.dashboard.DashboardControlVO;
import com.saninco.ccm.vo.dashboard.DashboardListbyVO;
import com.saninco.ccm.vo.dashboard.DashboardModuleVO;
import com.saninco.ccm.vo.dashboard.UserDashboardModuleVO;
import com.saninco.ccm.vo.dashboard.UserDashboardVO;

/**
 * @see CodeReview By Chao.Liu on the 2010/08/24 PM
 * @author Jian.Dong
 * @see Modified Logger by Chao.liu 10/08/23 AM
 */
public class DashboardServiceImpl implements IDashboardService {

	private final Logger logger = Logger.getLogger(this.getClass());
	public final static String CONTROL_TIMEPERIOD = "timePeriod";
	public final static String CONTROL_PRODUCT = "product";
	public final static String CONTROL_PRODUCT_LABEL = "productLabel";
	public final static String CONTROL_BUDGET = "budget";
	public final static String CONTROL_BUDGET_OWNER = "budgetOwner";
	public final static String CONTROL_QUOTE_PRODUCT = "quoteProduct";
	public final static String CONTROL_QUOTE_PRODUCT_LABEL = "quoteProductLabel";
	public final static String[] CONTROLS = { "vendor", CONTROL_TIMEPERIOD,
			"region", CONTROL_PRODUCT, CONTROL_PRODUCT_LABEL, "cto",
			CONTROL_BUDGET, CONTROL_BUDGET_OWNER, CONTROL_QUOTE_PRODUCT,
			CONTROL_QUOTE_PRODUCT_LABEL, "quoteVendor", "quoteIssuedStatus",
			"quoteVendorResponseStatus", "quoteAging", "quoteNetType" };
	public final static String LISTBY = "listBy"; 
	public final static String LIMIT = "limit";
	public final static String LISTBY_TABLE = "listByTable"; 
	public final static String VIEW_MODE = "viewMode";
	
	
	private IInvoiceDao invoiceDao;
	private IDashboardDao dashboardDao;
	private IBIUserDashboardDao userDashboardDao;
	private IBITimePeriodDao timePeriodDao;
	private IBIVendorDao biVendorDao;
	private IBIProductDao biProductDao;
	private IBIProvinceDao biProvinceDao;
	private IBIBudgetDao biBudgetDao;
	private IBIAuditReferenceDao biAuditReferenceDao;
	private IBIUserDashboardModuleDao userDashboardModuleDao;
	private IBIVendorSpendDashboardDao vendorSpendDashboardDao;
	private IBIDashboardControlDao dashboardControlDao;
	private IBIDashboardListbyDao dashboardListbyDao;
	private IBIDashboardModuleDao dashboardModuleDao;
	
	public DashboardServiceImpl() { }
	
	
	public UserDashboardVO getUserDashboard() {
		
		List<BIUserDashboard> userDashboardList = userDashboardDao.findByUser(SystemUtil.getCurrentUserId(), "Y");
		UserDashboardVO uv = new UserDashboardVO(userDashboardList.get(0));
		return uv;
	}
	
	public UserDashboardVO updateAndGetUserDashboardById(Integer id) {
		
		BIUserDashboard ud = userDashboardDao.findById(id);
		if ("N".equals(ud.getDefaultFlag())) {
			userDashboardDao.updateDefaultFlagById(id, SystemUtil.getCurrentUserId());
		}
		UserDashboardVO uv = new UserDashboardVO(ud);
		return uv;
	}
	
	public UserDashboardVO addNewDashboard() {
		
		BIUserDashboard ud = new BIUserDashboard();
		Integer number = userDashboardDao.getNewDashboardNumber(SystemUtil.getCurrentUserId());
		ud.setDashboardName("New Dashboard "+(number != null && number > 0 ? number + 1 : 1));
		ud.setUserId(SystemUtil.getCurrentUserId());
		ud.setCreatedTimestamp(new Date());
		ud.setCreatedBy(SystemUtil.getCurrentUserId());
		ud.setRecActiveFlag("Y");
		userDashboardDao.save(ud);
		
		return new UserDashboardVO(ud);
	}
	
	public String updateUserDashboardName(Integer id, String dashboardName) {
		String result = "success";
		Integer sameNameCount = userDashboardDao.countSameDashboardName(id, dashboardName, SystemUtil.getCurrentUserId());
		if (sameNameCount > 0) {
			result = "sameName";
		} else {
			BIUserDashboard ud = userDashboardDao.findById(id);
			ud.setDashboardName(dashboardName);
			userDashboardDao.save(ud);
		}
		return result;
	}
	
	public List<BITimePeriod> getAllTimePeriod() {
		
		return timePeriodDao.findAll();
	}
	
	public List<BITimePeriod> hqlFindTimePeriodList(String listType){
		return timePeriodDao.hqlFindTimePeriodList(listType);
	}
	
	public List<BIVendor> getAllVendor() {
		
		return biVendorDao.findAll();
	}
	
	public List<BIBudget> getAllBudget() {
		
		return biBudgetDao.findAll();
	}
	
	public List<BIProduct> getAllProduct() {
		
		return biProductDao.findAll();
	}
	
	public List<BIProvince> getAllProvince() {
		
		return biProvinceDao.findAll();
	}
	
	public List<BIAuditReference> getAllAuditReference() {
		
		return biAuditReferenceDao.findAll();
	}
	
	public List<BIQuoteAging> getAllQuoteAging() {
		
		return userDashboardModuleDao.findAllQuoteAging();
	}
	
	public List<BIQuoteIssuedStatus> getAllQuoteIssuedStatus() {
		
		return userDashboardModuleDao.findAllQuoteIssuedStatus();
	}
	
	public List<BIQuoteNetType> getAllQuoteNetType() {
	
		return userDashboardModuleDao.findAllQuoteNetType();
	}
	public List<BIQuoteProduct> getAllQuoteProduct() {
	
		return userDashboardModuleDao.findAllQuoteProduct();
	}			

	public List<BIQuoteVendor> getAllQuoteVendor() {
	
		return userDashboardModuleDao.findAllQuoteVendor();
	}
	
	public List<BIQuoteVendorResponseStatus> getAllQuoteVendorResponseStatus() {
	
		return userDashboardModuleDao.findAllQuoteVendorResponseStatus();
	}
	
	public List<DashboardModuleVO> getAllDashboardModule() {
		
		List<BIDashboardModule> bList = dashboardModuleDao.findAll();
		List<DashboardModuleVO> result = new ArrayList<DashboardModuleVO>();
		for (BIDashboardModule b : bList) {
			DashboardModuleVO v = new DashboardModuleVO(b);
			result.add(v);
		}
		
		return result;
	}
	
	public List<DashboardControlVO> getGlobalDashboardControl() {
		List<DashboardControlVO> result = new ArrayList<DashboardControlVO>();
		List l = dashboardControlDao.findAll();
		for (int i = 0; i < l.size(); i++) {
			BIDashboardControl bdc = (BIDashboardControl)l.get(i);
			if ("Y".equals(bdc.getGlobalFlag())) {
				DashboardControlVO dv = new DashboardControlVO(bdc);
				result.add(dv);
			}
		}
		return result;
	}
	
	public UserDashboardModuleVO saveControlData(Integer userDashboardModuleId, String conditionKey, Object[] conditionValue) {
		String canUseCheckKey = conditionKey;
		if (CONTROL_PRODUCT_LABEL.equals(canUseCheckKey))
			canUseCheckKey = CONTROL_PRODUCT;
		if (CONTROL_QUOTE_PRODUCT_LABEL.equals(canUseCheckKey))
			canUseCheckKey = CONTROL_QUOTE_PRODUCT;
		if (CONTROL_BUDGET_OWNER.equals(canUseCheckKey))
			canUseCheckKey = CONTROL_BUDGET;
		
		BIUserDashboardModule dashboardModule = userDashboardModuleDao.findById(userDashboardModuleId);
		boolean isCanSave = userDashboardModuleDao.isCanUseCondition(userDashboardModuleId, canUseCheckKey);
		
		if (isCanSave) {
			
			String controlData = dashboardModule.getControlData();
			JSONObject jo = JSONObject.fromObject(controlData);
			if (CONTROL_PRODUCT.equals(conditionKey) || CONTROL_PRODUCT_LABEL.equals(conditionKey)) {
				if (jo.has(CONTROL_PRODUCT))
					jo.remove(CONTROL_PRODUCT);
				if (jo.has(CONTROL_PRODUCT_LABEL))
					jo.remove(CONTROL_PRODUCT_LABEL);
			}
			if (CONTROL_QUOTE_PRODUCT.equals(conditionKey) || CONTROL_QUOTE_PRODUCT_LABEL.equals(conditionKey)) {
				if (jo.has(CONTROL_QUOTE_PRODUCT))
					jo.remove(CONTROL_QUOTE_PRODUCT);
				if (jo.has(CONTROL_QUOTE_PRODUCT_LABEL))
					jo.remove(CONTROL_QUOTE_PRODUCT_LABEL);
			}
			
			if (CONTROL_BUDGET.equals(conditionKey) || CONTROL_BUDGET_OWNER.equals(conditionKey)) {
				if (jo.has(CONTROL_BUDGET))
					jo.remove(CONTROL_BUDGET);
				if (jo.has(CONTROL_BUDGET_OWNER))
					jo.remove(CONTROL_BUDGET_OWNER);
			}
			
			if (conditionValue == null || conditionValue.length == 0) {
				if (jo.has(conditionKey))
					jo.remove(conditionKey);
			} else {
				jo.put(conditionKey, conditionValue);
			}
			
			jo = getListbyFromDashboardListby(jo, conditionKey, conditionValue);
			dashboardModule.setControlData(jo.toString());
			userDashboardModuleDao.save(dashboardModule);
		}
		return new UserDashboardModuleVO(dashboardModule);
	}
	
	public void updateUserDashboardViewMode(Integer userDashboardModuleId, String viewMode) {
		
		BIUserDashboardModule dashboardModule = userDashboardModuleDao.findById(userDashboardModuleId);
		String controlData = dashboardModule.getControlData();
		JSONObject jo = JSONObject.fromObject(controlData);
		jo.put(VIEW_MODE, viewMode);
		dashboardModule.setControlData(jo.toString());
		userDashboardModuleDao.save(dashboardModule);
	}
	
	public UserDashboardModuleVO updateUserDashboardListBy(Integer userDashboardModuleId, String controlKey, String controlFilterKey) {
		BIUserDashboardModule dashboardModule = userDashboardModuleDao.findById(userDashboardModuleId);
		String controlData = dashboardModule.getControlData();
		JSONObject jo = JSONObject.fromObject(controlData);
		if (jo.has(LISTBY)) {
			JSONObject listBy = jo.getJSONObject(LISTBY);
			for (String control : CONTROLS) {
				if (listBy.has(control)){
					JSONArray listControls = listBy.getJSONArray(control);
					Iterator<JSONObject> listControl = listControls.iterator();
					while (listControl.hasNext()) {
						JSONObject listByControl = listControl.next();
						if (listByControl.has("isSelected") && "Y".equals(listByControl.getString("isSelected"))) {
							listByControl.put("isSelected", "N");
							break;
						}
					}
				}
			}
			
			if (listBy.has(controlKey)){
				JSONArray listControls = listBy.getJSONArray(controlKey);
				Iterator<JSONObject> listControl = listControls.iterator();
				while (listControl.hasNext()) {
					JSONObject listByControl = listControl.next();
					if (listByControl.has("key") && controlFilterKey.equals(listByControl.getString("key"))) {
						listByControl.put("isSelected", "Y");
						break;
					}
				}
			}
		}
		dashboardModule.setControlData(jo.toString());
		userDashboardModuleDao.save(dashboardModule);
		return new UserDashboardModuleVO(dashboardModule);
	}
	
	public UserDashboardModuleVO addNewDashboarModule(Integer userDashboardId, Integer dashboardModuleId) {
		
		List<DashboardListbyVO> resultList = null;
		BIUserDashboard userDashboard = userDashboardDao.findById(userDashboardId);
		BIDashboardModule dashboardModule = dashboardModuleDao.findById(dashboardModuleId);
		String defaultName = dashboardModule.getModuleName();
		BIUserDashboardModule udm = new BIUserDashboardModule();
		udm.setBIDashboardModule(dashboardModule);
		Integer number = userDashboardModuleDao.getNewUserDashboardModuleNumber(userDashboard.getId(), defaultName);
		udm.setName(defaultName+" "+(number != null && number > 0 ? number + 1 : 1));
		udm.setBIUserDashboard(userDashboard);
		udm.setSortNo(userDashboardModuleDao.getNextSortNo(userDashboardId));
		List<BIDashboardListby> bListby = dashboardListbyDao.getAllNoConditionListby();
		JSONObject listby = new JSONObject();
		for (String control : CONTROLS) {
			resultList = new ArrayList<DashboardListbyVO>();
			for (BIDashboardListby bl : bListby) {
				if (control.equals(bl.getControlKey())) {
					DashboardListbyVO dv = new DashboardListbyVO(bl);
					resultList.add(dv);
				}
			}
			if (resultList.size() > 0) {
				listby.put(control, resultList);
			}
		}
		
		JSONObject jo = new JSONObject();
		jo.put(CONTROL_TIMEPERIOD, new Integer[]{1});
		jo.put(LISTBY, listby);
		jo.put(VIEW_MODE, "pie");
		System.out.println(jo.toString());
		setDefaultListBy(jo);
		
		udm.setControlData(jo.toString());
		userDashboardModuleDao.save(udm);
		
		return new UserDashboardModuleVO(udm);
	}

	public String updateUserDashboardModuleName(Integer id, String userDashboardModuleName) {
		String result = "success";
		BIUserDashboardModule dashboardModule = userDashboardModuleDao.findById(id);
		
		if (dashboardModule != null) {
			Integer sameNameCount = userDashboardModuleDao.countSameUserDashboardModuleName(userDashboardModuleName, dashboardModule.getBIUserDashboard().getId(), id);
			if (sameNameCount > 0) {
				result = "sameName";
			} else {
				dashboardModule.setName(userDashboardModuleName);
				userDashboardModuleDao.save(dashboardModule);
			}
		}
		
		return result;
	}
	
	public void updateUserDashboardModuleShowPercent(Integer id, String moduleShowPercentFlag) {
		BIUserDashboardModule dashboardModule = userDashboardModuleDao.findById(id);
		
		if (dashboardModule != null) {
			dashboardModule.setShowPercentFlag(moduleShowPercentFlag);
				userDashboardModuleDao.save(dashboardModule);
		}
		
	}
	
	public void updateAndDeleteUserDashboardModule(Integer userDashboardModuleId) {
		BIUserDashboardModule dashboardModule = userDashboardModuleDao.findById(userDashboardModuleId);
		if (dashboardModule != null) {
			userDashboardModuleDao.updateSort(dashboardModule.getBIUserDashboard().getId(), dashboardModule.getSortNo());
			userDashboardModuleDao.delete(dashboardModule);
		}
	}
	
	public void updateUserDashboardModuleMoveNewSort(Integer userDashboardModuleId, Integer sortNo) {
		BIUserDashboardModule dashboardModule = userDashboardModuleDao.findById(userDashboardModuleId);
		if (dashboardModule != null) {
			userDashboardModuleDao.updateSortNoByDashboardAndSortNo(dashboardModule.getBIUserDashboard().getId(), sortNo, dashboardModule.getSortNo());
			dashboardModule.setSortNo(sortNo);
			userDashboardModuleDao.save(dashboardModule);
		}
	}
	
	public JSONObject getListbyFromDashboardListby(JSONObject jo, String conditionKey, Object[] conditionValue) {
		List<BIDashboardListby> bListby = null;
		List<DashboardListbyVO> resultList = null;
		String listbyKey = conditionKey;
		DashboardListbyVO selectedDashboardListby = null;
		
		if (conditionValue == null || conditionValue.length == 0) {
			if (CONTROL_PRODUCT_LABEL.equals(conditionKey))
				listbyKey = CONTROL_PRODUCT;
			
			if (CONTROL_QUOTE_PRODUCT_LABEL.equals(conditionKey))
				listbyKey = CONTROL_QUOTE_PRODUCT;
				
			if (CONTROL_BUDGET_OWNER.equals(conditionKey))
				listbyKey = CONTROL_BUDGET;
			
			bListby = dashboardListbyDao.getListbyByControlKey(listbyKey);
		} else {
			if (CONTROL_PRODUCT_LABEL.equals(conditionKey)) {
				listbyKey = CONTROL_PRODUCT;
				List<BIProduct> products = biProductDao.findByLabel(conditionValue[0].toString());
				bListby = dashboardListbyDao.getListbyByMinNumber(listbyKey, products.size());
			} else if (CONTROL_BUDGET_OWNER.equals(conditionKey)) {
				listbyKey = CONTROL_BUDGET;
				List<BIBudget> budgets = biBudgetDao.findByOwner(conditionValue[0].toString());
				bListby = dashboardListbyDao.getListbyByMinNumber(listbyKey, budgets.size());
			} else if (CONTROL_TIMEPERIOD.equals(conditionKey)) {
				bListby = new ArrayList<BIDashboardListby>();
				bListby.add(new BIDashboardListby(CONTROL_TIMEPERIOD, null, "year_and_month", "List by month", 0, 0, "Y", "year_and_month"));
				int timePeriodId = Integer.parseInt(conditionValue[0].toString());
				switch (timePeriodId) {
				case 3:
					bListby.add(new BIDashboardListby(CONTROL_TIMEPERIOD, null, "quarter", "List by quarter", 0, 0, "Y", "quarter"));
					break;
				case 4:
					bListby.add(new BIDashboardListby(CONTROL_TIMEPERIOD, null, "quarter", "List by quarter", 0, 0, "Y", "quarter"));
					break;
				case 5:
					bListby.add(new BIDashboardListby(CONTROL_TIMEPERIOD, null, "quarter", "List by quarter", 0, 0, "Y", "quarter"));
					bListby.add(new BIDashboardListby(CONTROL_TIMEPERIOD, null, "year", "List by year", 0, 0, "Y", "year"));
					break;
				}
			} else {
				bListby = dashboardListbyDao.getListbyByMinNumber(listbyKey, conditionValue.length);
			}
		}
		
		if (jo.has(LISTBY)) {
			JSONObject listBy = jo.getJSONObject(LISTBY);
			if (listBy.has(listbyKey)){
				JSONArray listControls = listBy.getJSONArray(listbyKey);
				Iterator<JSONObject> listControl = listControls.iterator();
				while (listControl.hasNext()) {
					JSONObject listByControl = listControl.next();
					if (listByControl.has("isSelected") && "Y".equals(listByControl.getString("isSelected"))) {
						selectedDashboardListby = (DashboardListbyVO)JSONObject.toBean(listByControl, DashboardListbyVO.class);
						break;
					}
				}
			}
		}
		
		if (bListby != null && bListby.size() > 0) {
			resultList = new ArrayList<DashboardListbyVO>();
			for (BIDashboardListby bl : bListby) {
				DashboardListbyVO dv = new DashboardListbyVO(bl);
				if (selectedDashboardListby != null && selectedDashboardListby.getKey() == dv.getKey()) {
					dv.setIsSelected("Y");
				}
				resultList.add(dv);
			}
		}
		if (resultList != null) {
			if (jo.has(LISTBY)) {
				JSONObject listBy = jo.getJSONObject(LISTBY);
				listBy.put(listbyKey, resultList);
			} else {
				jo.put(LISTBY, new JSONObject().put(listbyKey, resultList));
			}
		} else {
			if (jo.has(LISTBY)) {
				JSONObject listBy = jo.getJSONObject(LISTBY);
				listBy.remove(listbyKey);
			}
		}
		setDefaultListBy(jo);
		return jo;
	}
	
	private void setDefaultListBy(JSONObject jo) {
		
		boolean isHasListby = false;
		
		if (jo.has(LISTBY)) {
			JSONObject listBy = jo.getJSONObject(LISTBY);
			for (String control : CONTROLS) {
				if (listBy.has(control)){
					JSONArray listControls = listBy.getJSONArray(control);
					Iterator<JSONObject> listControl = listControls.iterator();
					while (listControl.hasNext()) {
						JSONObject listByControl = listControl.next();
						if (listByControl.has("isSelected") && "Y".equals(listByControl.getString("isSelected"))) {
							isHasListby = true;
							break;
						}
					}
				}
			}
			if (!isHasListby) {
				if (listBy.has(CONTROL_TIMEPERIOD)) {
					listBy.getJSONArray(CONTROL_TIMEPERIOD).getJSONObject(0).put("isSelected", "Y");
				} else {
					DashboardListbyVO dl = new DashboardListbyVO();
					dl.setChartShowName("year_and_month");
					dl.setIsSelected("Y");
					dl.setKey("year_and_month");
					dl.setName("List by month");
					listBy.put(CONTROL_TIMEPERIOD, new DashboardListbyVO[]{dl});
				}
			}
		} else {
			DashboardListbyVO dl = new DashboardListbyVO();
			dl.setChartShowName("year_and_month");
			dl.setIsSelected("Y");
			dl.setKey("year_and_month");
			dl.setName("List by month");
			jo.put(LISTBY, new JSONObject().put(CONTROL_TIMEPERIOD, new DashboardListbyVO[]{dl}));
		}
	}
	
	public List<DashboardChartDataVO> getUserDashboardModuleData(Integer userDashboardModuleId) {
		BIUserDashboardModule dashboardModule = userDashboardModuleDao.findById(userDashboardModuleId);
		Integer moduleId = dashboardModule.getBIDashboardModule().getId();
		/*
		 * Example: {"timePeriod":[1],"vendor":[33,34],"listBy":{"vendor":[{"listbyTable":"vendor","chartShowName":"vendor_name","key":"id","name":"List by vendor","isSelected":"Y","limit":10}]},"viewMode":"pie"}
		 */
		String controlData = dashboardModule.getControlData();
		JSONObject jo = JSONObject.fromObject(controlData);
		
		List<DashboardChartDataVO> result =  searchChartDataByJson(jo, moduleId);
		
		return result;
	}
	
	private List<DashboardChartDataVO> searchChartDataByJson(JSONObject jo, Integer moduleId) {
		
		List<DashboardChartDataVO> result = new ArrayList<DashboardChartDataVO>();
		Map<String, String[]> condition = getCotrolDataFromJson(jo);
		DashboardListbyVO bl = getListbyFromJson(jo);
		double total = userDashboardModuleDao.searchTotalChartData(condition, bl, moduleId);
		
		if (total > 0) {
			
			result = userDashboardModuleDao.searchChartData(condition, bl, moduleId, total);
		} 
		
		return result;
	}
	
	private DashboardListbyVO getListbyFromJson(JSONObject jo) {
		DashboardListbyVO bl = new DashboardListbyVO();
		bl.setKey("year_and_month");
		if (jo.has(LISTBY)) {
			JSONObject listBy = jo.getJSONObject(LISTBY);
			for (String control : CONTROLS) {
				if (listBy.has(control)){
					JSONArray listControls = listBy.getJSONArray(control);
					Iterator<JSONObject> listControl = listControls.iterator();
					while (listControl.hasNext()) {
						JSONObject listByControl = listControl.next();
						if (listByControl.has("isSelected") && "Y".equals(listByControl.getString("isSelected"))) {
							bl = (DashboardListbyVO)JSONObject.toBean(listByControl, DashboardListbyVO.class);
							break;
						}
					}
				}
			}
		}
		return bl;
	}
	/**
	 * 查询 Dashboard List 相关信息的抽象方法的实现类。
	 * @return [description]
	 * @throws BPLException  [description]
	 */
	public List<Map<String,Object>> searchDashboardListInfo(String dashboardNameSort, String dateCreatedSort, Integer currentPage, Integer pageRecords) throws BPLException {

		List<Map<String,Object>> dashboardListInfoList = new ArrayList<Map<String,Object>>();

		// 根据userId来获取 Dashboard List 相关信息。
		Integer userId = SystemUtil.getCurrentUserId(); 
		List<Object[]> dashboardListInfo = userDashboardDao.searchDashboardListInfo( userId, dashboardNameSort, dateCreatedSort, currentPage, pageRecords );

		// 遍历返回的数据，存储到list集合中。
		for (Object[] dli : dashboardListInfo) {
			Map<String,Object> map = new HashMap<String,Object>();
			
			
			map.put("id", dli[0]);
			map.put("dashboardName", dli[1]);
			map.put("createdTimestamp", new SimpleDateFormat("yyyy/MM/dd").format(dli[2])); // 对日期进行格式化。
			
			dashboardListInfoList.add(map);
		}

		return dashboardListInfoList;
	}

	/**
	 * 使用特殊的条件来查询 dashboard 列表项。
	 * @param  dashboardNameSort [description]
	 * @param  dateCreatedSort   [description]
	 * @param  currentPage       [description]
	 * @param  pageRecords       [description]
	 * @return                   [description]
	 * @throws BPLException      [description]
	 */
	public String searchDashboardListByConditions(String dashboardNameSort, String dateCreatedSort, Integer currentPage, Integer pageRecords) throws Exception {

		String listItems = null;
		Integer userId = SystemUtil.getCurrentUserId();
		try{
			listItems = userDashboardDao.searchDashboardListByConditions( userId, dashboardNameSort, dateCreatedSort, currentPage, pageRecords);
		}catch(Exception e) {
			System.out.println("根据排序条件和分页条件查找 dashboard list item 时出错， error message: " + e.getMessage() );
		}
		
		return listItems;

	}

	/**
	 * 删除 dashboard 列表项。
	 * @param  userDashboardId [description]
	 * @return                 [description]
	 */
	public String deleteDashboardListItem(Integer userDashboardId) {
		
		try {
			BIUserDashboard ud =  userDashboardDao.findById(userDashboardId);
			ud.setRecActiveFlag("N");
			userDashboardDao.save(ud);

		} catch(Exception e) {
			System.out.println("删除 dashboard 列表中的项出错， error message: " + e.getMessage() );
		}
		
		return null;
	}
	
	/**
	 * 获得数据库中相关 dashboard list item 的总条数。
	 * @return
	 */
	public Integer getTotalRecordsNo() {
		logger.info("Enter method getTotalRecordsNo.");
		Integer resultCount = null;
		Integer userId = SystemUtil.getCurrentUserId();
		try{
			resultCount = userDashboardDao.getTotalRecordsNo(userId);
		} catch( Exception e  ) {
			System.out.println("查找 dashboard List item 的总条数出错， error message: " + e.getMessage() );
		}
		
		
		logger.info("Exit method getTotalRecordsNo.");
		return resultCount;
	}
	
	private Map<String, String[]> getCotrolDataFromJson(JSONObject jo) {
		
		Map<String, String[]> result = new HashMap<String, String[]>();
		for (String control : CONTROLS) {
			if (jo.has(control)){
				result.put(control, getCotrolDataArrayFromJson(jo.getJSONArray(control), control));
			}
		}
		return result;
	}
	
	private String[] getCotrolDataArrayFromJson(JSONArray jsonArray, String key) {
		String[] array = new String[jsonArray.size()];
		for (int i = 0; i < jsonArray.size(); i++) {
			array[i] = jsonArray.getString(i);
		}
		return  array;
	}
	
	/**
	 * @author Chao.Liu
	 * Search Dashboarc HTML
	 * @return
	 */
	public String saveAndGetPageHtml(){
		logger.info(CcmFormat.formatEnterLog("Search Dashboarc HTML."));
		
		StringBuffer sb = new StringBuffer(); 
		List<Dashboard> dl = dashboardDao.getDashboardByUserId();
		
		if(dl.size() < 1){
			dl = this.saveDashboardPageInfo();
		}
		
		for(int i=0; i<2; i++){
			sb.append("<DIV id='column"+i+"Div' class='column ui-sortable' unselectable='on'> ");
			List<Dashboard> dlRow = getDashboardListByIndex(dl,i);
			
			for(Dashboard d : dlRow){
				sb.append("	<DIV id='portlet"+d.getIdStrCenter()+"_"+d.getId()+"Div'class='portlet ui-widget ui-widget-content ui-helper-clearfix ui-corner-all'> ");
				sb.append("		<DIV class='portlet-header ui-widget-header ui-corner-all'> ");
//				sb.append("			<span id='edit"+d.getIdStrCenter()+"' class='ui-icon ui-icon-gear'></span> ");
				sb.append("			<span id='oc"+d.getIdStrCenter()+"' class='"+d.getIsOpen()+"'></span>");
				sb.append("			"+d.getTitle()+" ");
				sb.append("		</DIV> ");
				sb.append("		<DIV class='portlet-content'> ");
				sb.append("			<img id='edit"+d.getIdStrCenter()+"IMG' src='"+d.getImgSrc()+"' />");
				sb.append("		</DIV> ");
				sb.append(" 	<input name='sort' type='hidden' value='"+d.getId()+"' />");
				sb.append("	</DIV> ");
			}
			
			sb.append("</DIV> ");
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}

	
	
	private List<Dashboard> getDashboardListByIndex(List<Dashboard> dl,int row){
		List<Dashboard> l = new ArrayList<Dashboard>();
		
		for(int i=0; i<dl.size(); i++){
			if(dl.get(i).getRow() == row){
				l.add(dl.get(i));
			}
		}
		
		return l;
	}
	
	private List<Dashboard> saveDashboardPageInfo(){
		List<Dashboard> l = new ArrayList<Dashboard>();
		
		Dashboard d1 = new Dashboard();
		d1.setUserId(SystemUtil.getCurrentUserId());
		d1.setRow(0);
		d1.setTitle("User(Time)");
		d1.setIdStrCenter("userTime");
		d1.setImgSrc("userInvoiceAtTransactionDayChart.action");
		d1.setIsOpen("ui-icon ui-icon-minusthick");
		d1.setSort(0);
		d1.setCreatedTimestamp(new Date());
		d1.setModifiedTimestamp(new Date());
		
		dashboardDao.save(d1);
		l.add(d1);
		
		Dashboard d2 = new Dashboard();
		d2.setUserId(SystemUtil.getCurrentUserId());
		d2.setRow(0);
		d2.setTitle("System(Time)");
		d2.setIdStrCenter("systemTime");
		d2.setImgSrc("invoiceAtTransactionDayChart.action");
		d2.setIsOpen("ui-icon ui-icon-minusthick");
		d2.setSort(1);
		d2.setCreatedTimestamp(new Date());
		d2.setModifiedTimestamp(new Date());
		
		dashboardDao.save(d2);
		l.add(d2);
		
		Dashboard d3 = new Dashboard();
		d3.setUserId(SystemUtil.getCurrentUserId());
		d3.setRow(1);
		d3.setTitle("User(Time Scale)");
		d3.setIdStrCenter("userTimeScale");
		d3.setImgSrc("userInvoiceAtDashletDayChart.action");
		d3.setIsOpen("ui-icon ui-icon-minusthick");
		d3.setSort(2);
		d3.setCreatedTimestamp(new Date());
		d3.setModifiedTimestamp(new Date());
		
		dashboardDao.save(d3);
		l.add(d3);
		
		Dashboard d4 = new Dashboard();
		d4.setUserId(SystemUtil.getCurrentUserId());
		d4.setRow(1);
		d4.setTitle("System(Time Scale)");
		d4.setIdStrCenter("systemTimeScale");
		d4.setImgSrc("invoiceAtDashletDayChart.action");
		d4.setIsOpen("ui-icon ui-icon-minusthick");
		d4.setSort(3);
		d4.setCreatedTimestamp(new Date());
		d4.setModifiedTimestamp(new Date());
		
		dashboardDao.save(d4);
		l.add(d4);
		
		
		return l;
	}
	/**
	 * @see Save DIV Position
	 * @author Chao.Liu
	 * @param dvo
	 * @return
	 */
	public String saveDashboardPosition(DashboardVO dvo){
		logger.info(CcmFormat.formatEnterLog("Save DIV Position.", dvo));
		
		StringBuffer sb = new StringBuffer();
		
		List<Integer[]> il = dvo.getSortList();
		
		for(int i=0; i<il.size(); i++){
			Dashboard d = (Dashboard) dashboardDao.get(Dashboard.class, il.get(i)[0]);
			d.setRow(il.get(i)[1]);
			d.setSort(i);
			d.setModifiedTimestamp(new Date());
		}

		logger.info(CcmFormat.formatExitLog());
		return sb.toString();
	}
	/**
	 * System Time
	 * @author Jian.Dong
	 */
	public JFreeChart getInvoiceAtTransactionDayChart() throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get day in status user chart."));
		
		CategoryDataset data = getInvoiceAtTransactionDayChartDataSet();
		JFreeChart chart = ChartFactory.createBarChart3D(
				"Days in Process (System)", "Days", "Count", data,
				PlotOrientation.VERTICAL, false, true, false);
		setBar3DStyle(chart,new InvoiceAtTransactionBarRenderer3D());

		logger.info(CcmFormat.formatExitLog());
		return chart;
	}

	private CategoryDataset getInvoiceAtTransactionDayChartDataSet() {
		List list = invoiceDao.getInvoiceAtTransaction();
		return buildInvoiceAtTransactionDayDataSet(list);
	}
	/**
	 * User Time
	 * @author Jian.Dong
	 */
	public JFreeChart getUserInvoiceAtTransactionDayChart() throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get day in status chart." ));
		
		CategoryDataset data = getUserInvoiceAtTransactionDayChartDataSet();
		JFreeChart chart = ChartFactory.createBarChart3D(
				"Days in Process (User)", "Days", "Count", data,
				PlotOrientation.VERTICAL, false, true, false);
		setBar3DStyle(chart,new InvoiceAtTransactionBarRenderer3D());

		logger.info(CcmFormat.formatExitLog());
		return chart;
	}

	private CategoryDataset getUserInvoiceAtTransactionDayChartDataSet() {
		List list = invoiceDao.getUserInvoiceAtTransaction();
		return buildInvoiceAtTransactionDayDataSet(list);
	}

	private CategoryDataset buildInvoiceAtTransactionDayDataSet(List list) {
		int sum1 = 0;
		int sum2 = 0;
		int sum3 = 0;
		int sum4 = 0;
		int sum5 = 0;
		int sum7 = 0;
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Calendar due = Calendar.getInstance();
		for (Object d : list) {
			Date idate = (Date) d;
			due.setTime(idate);
			due.set(Calendar.HOUR_OF_DAY, 0);
			due.set(Calendar.MINUTE, 0);
			due.set(Calendar.SECOND, 1);
			int days = (int) ((now.getTimeInMillis() - due.getTimeInMillis()) / 86400000);
			if(days <= 1){
				sum1++;
			}
			if(days > 1 && days <= 2){
				sum2++;
			}
			if(days > 2 && days <= 3){
				sum3++;
			}
			if(days > 3 && days <= 4){
				sum4++;
			}
			if(days > 4 && days <= 5){
				sum5++;
			}
			if(days > 5){
				sum7++;
			}
		}
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(sum1, "1", "1");
		dataset.setValue(sum2, "2", "2");
		dataset.setValue(sum3, "3", "3");
		dataset.setValue(sum4, "4", "4");
		dataset.setValue(sum5, "5", "5");
		dataset.setValue(sum7, ">5", ">5");
		return dataset;
	}

	private void setBar3DStyle(JFreeChart localJFreeChart,BarRenderer3D localCustomBarRenderer3D) {
		CategoryPlot localCategoryPlot = (CategoryPlot) localJFreeChart.getPlot();
		localCustomBarRenderer3D.setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
		localCustomBarRenderer3D.setBaseItemLabelsVisible(true);
		localCustomBarRenderer3D.setItemLabelAnchorOffset(10.0D);
		localCustomBarRenderer3D.setBasePositiveItemLabelPosition(new ItemLabelPosition(ItemLabelAnchor.OUTSIDE12, TextAnchor.BASELINE_LEFT));
		localCustomBarRenderer3D.setBaseItemLabelsVisible(true);
 		localCustomBarRenderer3D.setMaximumBarWidth(0.05D);
 		localCustomBarRenderer3D.setItemMargin(-1) ;
		localCategoryPlot.setRenderer(localCustomBarRenderer3D);
		ValueMarker localValueMarker = new ValueMarker(0.7D, new Color(200,200, 255), new BasicStroke(1.0F), 
				new Color(200, 200, 255),new BasicStroke(1.0F), 1.0F);
		localCategoryPlot.addRangeMarker(localValueMarker, Layer.BACKGROUND);
		NumberAxis localNumberAxis = (NumberAxis) localCategoryPlot.getRangeAxis();
		localNumberAxis.setNumberFormatOverride(NumberFormat.getIntegerInstance());
		localNumberAxis.setUpperMargin(0.12);
		localNumberAxis.setLowerBound(0);
		ChartUtilities.applyCurrentTheme(localJFreeChart);
	}
	/**
	 * User Time Scale
	 * @author Jian.Dong
	 */
	public JFreeChart getUserInvoiceAtDashletDayChart() throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get day to dueDate user chart." ));
		
		CategoryDataset data = getInvoiceAtDashletDayChartDataSet(false);
//		CategoryDataset data = getUserInvoiceAtDashletDayChartDataSet();
		JFreeChart chart = ChartFactory.createBarChart3D("Days to Due (User)","Due date", "Count", data, 
				PlotOrientation.VERTICAL, false, true, false);
		setBar3DStyle(chart,new InvoiceAtDashletBarRenderer3D());

		logger.info(CcmFormat.formatExitLog());
		return chart;
	}

	private CategoryDataset getUserInvoiceAtDashletDayChartDataSet() {
		List list = invoiceDao.getUserInvoiceAtDashlet();
		return buildInvoiceAtDashletDayDataSet(list);
	}
	/**
	 * System Tiem Scale
	 */
	public JFreeChart getInvoiceAtDashletDayChart() throws BPLException {
		logger.info(CcmFormat.formatEnterLog("get day to dueDate chart." ));
		CategoryDataset data = getInvoiceAtDashletDayChartDataSet(true);
		JFreeChart chart = ChartFactory.createBarChart3D(
				"Days to Due (System)", "Due date", "Count", data,
				PlotOrientation.VERTICAL, false, true, false);
		setBar3DStyle(chart,new InvoiceAtDashletBarRenderer3D());

		logger.info(CcmFormat.formatExitLog());
		return chart;
	}
	/**
	 * This is no use
	 * @Author Chao.Liu Date: Oct 11, 2010
	 * @return
	 */
//	private CategoryDataset getInvoiceAtDashletDayChartDataSet() {
//		List list = invoiceDao.getInvoiceAtDashlet();
//		return buildInvoiceAtDashletDayDataSet(list);
//	}
	private CategoryDataset getInvoiceAtDashletDayChartDataSet(boolean userFlag) {
		int sum7 = invoiceDao.getTimeScaleCount(userFlag, 7);
		int sum15 = invoiceDao.getTimeScaleCount(userFlag, 15);
		int sumAll = invoiceDao.getTimeScaleCount(userFlag, -1);
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(sum7, "<7", "<7");
		dataset.setValue(sum15, "<15", "<15");
		dataset.setValue(sumAll, "All Invoices ", "All Invoices");
		return dataset;
	}
	private CategoryDataset buildInvoiceAtDashletDayDataSet(List list) {
		int sum7 = 0;
		int sum15 = 0;
		int sumAll = 0;
		Calendar now = Calendar.getInstance();
		now.set(Calendar.HOUR_OF_DAY, 0);
		now.set(Calendar.MINUTE, 0);
		now.set(Calendar.SECOND, 0);
		Calendar due = Calendar.getInstance();
		for (Object d : list) {
			Date idate = (Date) d;
			due.setTime(idate);
			due.set(Calendar.HOUR_OF_DAY, 0);
			due.set(Calendar.MINUTE, 0);
			due.set(Calendar.SECOND, 0);
			int t = (int) ((due.getTimeInMillis() - now.getTimeInMillis()) / 86400000);
			if (t < 7) {
				sum7++;
			}
			if (t < 15) {
				sum15++;
			}
			sumAll++;
		}
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		dataset.setValue(sum7, "<7", "<7");
		dataset.setValue(sum15, "<15", "<15");
		dataset.setValue(sumAll, "All Invoices ", "All Invoices");
		return dataset;
	}
	
	
	static class InvoiceAtTransactionBarRenderer3D extends BarRenderer3D {
		private static final long serialVersionUID = 1L;
		
		public Paint getItemPaint(int paramInt1, int index) {
			Paint paint = Color.black;
			if(index == 0){
				paint = Color.decode("#00FF00");
			}
			if(index == 1){
				paint = Color.decode("#33CC00");
			}
			if(index == 2){
				paint = Color.decode("#009900");
			}
			if(index == 3){
				paint = Color.decode("#FFD700");
			}
			if(index == 4){
				paint = Color.decode("#FF9900");
			}
			if(index == 5){
				paint = Color.decode("#FF6600");
			}
			if(index == 6){
				paint = Color.decode("#FF3366");
			}
			if(index == 7){
				paint = Color.decode("#FF0033");
			}
			if(index == 8){
				paint = Color.decode("#FF0000");
			}
			return paint;
		}
	}
 
	static class InvoiceAtDashletBarRenderer3D extends BarRenderer3D {
		private static final long serialVersionUID = 2L;

		public Paint getItemPaint(int paramInt1, int index) {
			Paint paint = Color.black;
			if (index == 0) {
				paint = Color.red;
			}
			if (index == 1) {
				paint = Color.orange;
			}
			if (index == 2) {
				paint = Color.green;
			}
			return paint;
		}
	}

	
	public IInvoiceDao getInvoiceDao() { return invoiceDao; } 
	public void setInvoiceDao(IInvoiceDao invoiceDao) { this.invoiceDao = invoiceDao; }
	public IDashboardDao getDashboardDao() { return dashboardDao; } 
	public void setDashboardDao(IDashboardDao dashboardDao) { this.dashboardDao = dashboardDao; }


	public IBIUserDashboardDao getUserDashboardDao() {
		return userDashboardDao;
	}


	public void setUserDashboardDao(IBIUserDashboardDao userDashboardDao) {
		this.userDashboardDao = userDashboardDao;
	}


	public IBITimePeriodDao getTimePeriodDao() {
		return timePeriodDao;
	}


	public void setTimePeriodDao(IBITimePeriodDao timePeriodDao) {
		this.timePeriodDao = timePeriodDao;
	}


	public IBIVendorDao getBiVendorDao() {
		return biVendorDao;
	}


	public void setBiVendorDao(IBIVendorDao biVendorDao) {
		this.biVendorDao = biVendorDao;
	}


	public IBIProductDao getBiProductDao() {
		return biProductDao;
	}


	public void setBiProductDao(IBIProductDao biProductDao) {
		this.biProductDao = biProductDao;
	}


	public IBIProvinceDao getBiProvinceDao() {
		return biProvinceDao;
	}


	public void setBiProvinceDao(IBIProvinceDao biProvinceDao) {
		this.biProvinceDao = biProvinceDao;
	}


	public IBIAuditReferenceDao getBiAuditReferenceDao() {
		return biAuditReferenceDao;
	}


	public void setBiAuditReferenceDao(IBIAuditReferenceDao biAuditReferenceDao) {
		this.biAuditReferenceDao = biAuditReferenceDao;
	}


	public IBIUserDashboardModuleDao getUserDashboardModuleDao() {
		return userDashboardModuleDao;
	}


	public void setUserDashboardModuleDao(
			IBIUserDashboardModuleDao userDashboardModuleDao) {
		this.userDashboardModuleDao = userDashboardModuleDao;
	}


	public IBIVendorSpendDashboardDao getVendorSpendDashboardDao() {
		return vendorSpendDashboardDao;
	}


	public void setVendorSpendDashboardDao(
			IBIVendorSpendDashboardDao vendorSpendDashboardDao) {
		this.vendorSpendDashboardDao = vendorSpendDashboardDao;
	}


	public IBIDashboardControlDao getDashboardControlDao() {
		return dashboardControlDao;
	}


	public void setDashboardControlDao(IBIDashboardControlDao dashboardControlDao) {
		this.dashboardControlDao = dashboardControlDao;
	}


	public IBIDashboardListbyDao getDashboardListbyDao() {
		return dashboardListbyDao;
	}


	public void setDashboardListbyDao(IBIDashboardListbyDao dashboardListbyDao) {
		this.dashboardListbyDao = dashboardListbyDao;
	}


	public IBIDashboardModuleDao getDashboardModuleDao() {
		return dashboardModuleDao;
	}


	public void setDashboardModuleDao(IBIDashboardModuleDao dashboardModuleDao) {
		this.dashboardModuleDao = dashboardModuleDao;
	}


	public IBIBudgetDao getBiBudgetDao() {
		return biBudgetDao;
	}


	public void setBiBudgetDao(IBIBudgetDao biBudgetDao) {
		this.biBudgetDao = biBudgetDao;
	}

	
}
