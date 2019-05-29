package com.saninco.ccm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * 报表专用Map对象，实现了jasperreport中的通用接口，用来保存单条记录信息
 *
 */
public class ReportResult implements JRDataSource {
	/** 记录行数 */
	private int index = -1;
	
	/** 内部报表Map对象 */
	private List<Map> reportList;
	
	/**
	 * 构造器
	 */
	public ReportResult() {
		reportList = new ArrayList<Map>();
	}
	
	/**
	 * 构造器
	 */
	public ReportResult(List<Map> reportList) {
		this.reportList = reportList;
	}
	
	/**
	 * 取得当前报表指定的参数值
	 */
	public Object getFieldValue(JRField field) throws JRException {
		// 取得其中一行记录
		Map reportRecord = reportList.get(index);
		if (reportRecord != null) return reportRecord.get(field.getName().toUpperCase());
		else return null;
	}
	
	/** 逐行递进 */
	public boolean next() throws JRException {
		index++;
		return (index < this.reportList.size());    
	}
	
	/**
	 * 可以修改当前报表数据结果的对象
	 * @param reportList
	 */
	public void setReportList(List<Map> reportList) {
		this.reportList = reportList;
	}
}
