package com.saninco.ccm.util;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.context.WebApplicationContext;

//import com.saninco.ccm.service.ICommonLookupService;

public final class CcmFormat {
	private CcmFormat() {
		
	}; 
	
	private static WebApplicationContext ctx = null;
	
	private static final SimpleDateFormat dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
	private static final NumberFormat currency = new DecimalFormat("#,##0.00");
	private static final NumberFormat bigDouble = new DecimalFormat("#0.00");

	public static String formatDateTime(Date date) {
		if(date == null) return null;
		return dateTime.format(date);
	}

	public static String formatDate(Date d) {
		return date.format(d);
	}

	public static String format(Date d, String pa) {
		return new SimpleDateFormat(pa).format(d);
	}

	public static Date paseDateTime(String date) throws ParseException {
		return dateTime.parse(date);
	}

	public static Date paseDate(String d) {
		try {
			return date.parse(d);
		} catch (Exception e) {
			return null;
		}
	}

	public static Date pase(String d, String pa) {
		try {
			return new SimpleDateFormat(pa).parse(d);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String formatCurrency(Number db) {
		if (db == null)
			return "0.00";
		try {
			if (db.doubleValue() == 0)
				return "0.00";
		} catch (RuntimeException e) {
		}
		return currency.format(db);
	}

	public static String formatBigDouble(Number db) {
		if (db == null)
			return "0.00";
		try {
			if (db.doubleValue() == 0)
				return "0.00";
		} catch (RuntimeException e) {
		}
		return bigDouble.format(db);
	}

	public static Number paseCurrency(String db) throws ParseException {
		return currency.parse(db);
	}

	/**
	 * @param methodName methodName
	 * @param description description
	 * @param datas datas
	 * */
	public static String formatEnterLog(String description, Object... datas) {
		StringBuilder sb = new StringBuilder();
		sb.append("[Enter] - ");
		sb.append("User: ");
		sb.append(SystemUtil.getCurrentUserId());
		
		if (description != null && !"".equals(description)) {
			sb.append(", Description: ");
			sb.append(description);
		}
		
//		StringBuilder keyData = new StringBuilder();
		StringBuilder eventData = new StringBuilder();
		if(datas!=null && datas.length>0){
			for(Object o : datas){
				eventData.append(o);
				eventData.append(",");
//				keyData.append(o == null ? "null" : o.getClass().getSimpleName());
//				keyData.append(",");
			}
			eventData.deleteCharAt(eventData.length()-1);
//			keyData.deleteCharAt(keyData.length()-1);
		}
		sb.append(", Data:["+eventData.toString()+" ]");
		
//		String getMethodName = new Exception().getStackTrace()[1].getMethodName();
//		saveInfoEvent("Enter-"+getMethodName+": "+description,keyData.toString(),eventData.toString());
		return sb.toString();
	}

	public static String formatExitLog() {
//		String getMethodName = new Exception().getStackTrace()[1].getMethodName();
//		saveInfoEvent("Exit-"+getMethodName,null,null);
		return "[Exit].";
	}
	
	public static Throwable formatErrorLog(Throwable e) {
//		String getMethodName = new Exception().getStackTrace()[1].getMethodName();
//		saveErrorEvent(e,getMethodName);
		return e;
	}

	private static void saveErrorEvent(Throwable e,String methodName) {
//		if(getCommonLookupService()!=null){
//			getCommonLookupService().saveEvent("ERROR","Exception-"+methodName+": "+e,null,null);
//		}
	}

	private static void saveInfoEvent(String eventMessage,String keyData,String eventData ) {
//		if(getCommonLookupService()!=null){
//			getCommonLookupService().saveEvent("INFO",eventMessage,keyData,eventData);
//		}
	}

//	public static ICommonLookupService getCommonLookupService() {
//		return (ICommonLookupService)ctx.getBean("commonLookupService");
//	}
 
	public static void setCtx(WebApplicationContext ctx) {
		CcmFormat.ctx = ctx;
	}
	
	public static WebApplicationContext getCtx() {
		return ctx;
	}
	
	public static String formatEmailContent(String content){
		
		String result = content.replaceAll("\\r\\n", "<br/>").replaceAll("\\n", "<br/>").replaceAll("\\r", "<br/>");
		
		return result;
	}
}
