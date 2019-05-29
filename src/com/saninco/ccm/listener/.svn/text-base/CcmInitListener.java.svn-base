package com.saninco.ccm.listener;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.util.SystemUtil;

public class CcmInitListener implements ServletContextListener {

	private final Logger logger = Logger.getLogger(this.getClass());
	
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	public void contextInitialized(ServletContextEvent arg0) {
		try {
//			printMemory();
			WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(arg0.getServletContext());
			ICommonLookupService commonLookupService = (ICommonLookupService)ctx.getBean("commonLookupService");
			CcmFormat.setCtx(ctx);
			List<MapEntryVO<String, String>> systemThemes = commonLookupService.getThemes();
			ServletContext cts = arg0.getServletContext();
			cts.setAttribute("CCM_SYSTEM_THEMES", systemThemes);
			Map<String,String> sysConfigMap = commonLookupService.findSysConfig();
			SystemUtil.setSysConfigMap(sysConfigMap);
			/**InvoiceAction*/
////			List<MapEntryVO<String, String>> vendorList = commonLookupService.getUserPreviledgedVendors();//////users
//			List<MapEntryVO<String, String>> paymentStatusList = commonLookupService.getPaymentStatus();
//			List<MapEntryVO<String, String>> analystList = commonLookupService.getUsers();
//			List<MapEntryVO<String, String>> currencyList = commonLookupService.getCurrency();
//			List<MapEntryVO<String, String>> invoiceStatusList = commonLookupService.getInvoiceStatus();
////			cts.setAttribute("vendorList", vendorList);//////users
//			cts.setAttribute("paymentStatusList", paymentStatusList);
//			cts.setAttribute("analystList", analystList);
//			cts.setAttribute("currencyList", currencyList);
//			cts.setAttribute("invoiceStatusList", invoiceStatusList);
//			/**InvoiceDetailAction*/
//			List<MapEntryVO<String, String>> allLabelList = commonLookupService.getLabels();
//			List<MapEntryVO<String, String>> disputeReasonList = commonLookupService.getDisputeReason();
//			List<MapEntryVO<String, String>> originatorList=commonLookupService.getAllOriginators();
//			List<MapEntryVO<String, String>> disputeStatusList=commonLookupService.getDisputeStatus();
//			List<MapEntryVO<String, String>> scoaCodeList = commonLookupService.getAccountCode();
//			cts.setAttribute("allLabelList", allLabelList);
//			cts.setAttribute("disputeReasonList", disputeReasonList);
//			cts.setAttribute("originatorList", originatorList);
//			cts.setAttribute("disputeStatusList", disputeStatusList);
//			cts.setAttribute("scoaCodeList", scoaCodeList);
//			/**DisputeAction*/
////			List<MapEntryVO<String, String>> disputeReasonList = commonLookupService.getDisputeReason();
////			List<MapEntryVO<String, String>> disputeStatusList = commonLookupService.getDisputeStatus();
//			List<MapEntryVO<String, String>> createdByList = commonLookupService.getCreatedBy();
//			cts.setAttribute("createdByList", createdByList);
// 
			
		} catch (BPLException e) { 
			e.printStackTrace(); 
		} 
	}

	private void printMemory() {
		new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						StringBuilder sb = new StringBuilder();
						long heapSize = Runtime.getRuntime().totalMemory();   
						long heapMaxSize = Runtime.getRuntime().maxMemory();   
						long heapFreeSize = Runtime.getRuntime().freeMemory();
						long unitK = 1024;
						long unitM = 1024*1024;
						sb.append("�ڴ����ֵ=");
						sb.append(heapMaxSize/unitK);
						sb.append("K[");
						sb.append(heapMaxSize/unitM);
						sb.append("M],��ǰʹ��=");
						sb.append(heapSize/unitK);
						sb.append("K[");
						sb.append(heapSize/unitM);
						sb.append("M],�����ڴ��С=");
						sb.append(heapFreeSize/unitK);
						sb.append("K[");
						sb.append(heapFreeSize/unitM);
						sb.append("M]");
						logger.info(sb.toString());
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
