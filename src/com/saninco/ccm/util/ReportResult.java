package com.saninco.ccm.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;

/**
 * ����ר��Map����ʵ����jasperreport�е�ͨ�ýӿڣ��������浥����¼��Ϣ
 *
 */
public class ReportResult implements JRDataSource {
	/** ��¼���� */
	private int index = -1;
	
	/** �ڲ�����Map���� */
	private List<Map> reportList;
	
	/**
	 * ������
	 */
	public ReportResult() {
		reportList = new ArrayList<Map>();
	}
	
	/**
	 * ������
	 */
	public ReportResult(List<Map> reportList) {
		this.reportList = reportList;
	}
	
	/**
	 * ȡ�õ�ǰ����ָ���Ĳ���ֵ
	 */
	public Object getFieldValue(JRField field) throws JRException {
		// ȡ������һ�м�¼
		Map reportRecord = reportList.get(index);
		if (reportRecord != null) return reportRecord.get(field.getName().toUpperCase());
		else return null;
	}
	
	/** ���еݽ� */
	public boolean next() throws JRException {
		index++;
		return (index < this.reportList.size());    
	}
	
	/**
	 * �����޸ĵ�ǰ�������ݽ���Ķ���
	 * @param reportList
	 */
	public void setReportList(List<Map> reportList) {
		this.reportList = reportList;
	}
}
