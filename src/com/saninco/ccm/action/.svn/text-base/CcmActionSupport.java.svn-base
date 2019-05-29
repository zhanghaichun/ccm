package com.saninco.ccm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.util.ServletContextAware;

import com.opensymphony.xwork2.ActionSupport;
import com.saninco.ccm.service.ICommonLookupService;
import com.saninco.ccm.util.CcmUtil;
import com.saninco.ccm.vo.MapEntryVO;

/**
 * Base class for ActionSupport.
 * 
 * @author Joe.Yang
 * 
 */
public abstract class CcmActionSupport extends ActionSupport implements
		ServletRequestAware, ServletResponseAware,ServletContextAware {

	private static final long serialVersionUID = 1L;
	protected boolean testWeb = false;

	protected static final String FAILURE = "failure";
	public static final String MOBILE = "mobile";

	public static final SimpleDateFormat defaulDateFormater = new SimpleDateFormat("yyyy/MM/dd");

	public abstract String exec() throws Exception;

	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected ServletContext servletContext;
	protected ICommonLookupService commonLookupService; 
	private static List<MapEntryVO<String, String>> systemThemes = null;
	
	/** Logger available to subclasses */
	protected Logger logger = Logger.getLogger(this.getClass());
	
	
 
	public String execute() throws Exception { 
		String result = exec();
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.struts2.interceptor.ServletRequestAware#setServletRequest(javax.servlet.http.HttpServletRequest)
	 */
	public void setServletRequest(HttpServletRequest arg0) {
		request = arg0;
	}

	public void setServletResponse(HttpServletResponse arg0) {
		response = arg0;
	}

	public void writeOutputStream(String str) throws IOException {
		logger.info("Enter method writeOutputStream.");
		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		logger.debug("String to output stream: \r\n" + str);
		str = CcmUtil.string2Json(str);
		logger.debug("String encoding to output stream: \r\n" + str);
		response.getWriter().write(str);
		response.getWriter().close();
		logger.info("Exit method writeOutputStream.");
	}

	/**
	 * Output blob format picture
	 */
	public void writeBlobImage(Blob b) throws IOException {
		logger.info("Enter method writeImage.");
		long timeStart = System.currentTimeMillis();
		try {
			response.setContentType("image/jpeg");
//			response.setHeader("Cache-Control", "no-cache");
			InputStream is = b.getBinaryStream();
			OutputStream outs = response.getOutputStream();
			long len_ = b.length();
			int rlen = (len_ > 1024*1024)? 1024*500 : 1024*50;
			byte[] blobbytes = new byte[rlen];
			
			int bytesRead = 0;
			while ((bytesRead = is.read(blobbytes)) != -1) {
				outs.write(blobbytes, 0, bytesRead);
			}
			is.close();
			outs.flush();
			outs.close();
		} catch (SQLException e) {
			logger.error("writeImage error",e);
		}
		long timeEnd = System.currentTimeMillis();
		logger.info("Exit method writeImage,Time-consuming:" + (timeEnd-timeStart) + " MS");
	}
	
	public List<MapEntryVO<String, String>> getSystemThemes() {
		return CcmActionSupport.systemThemes;
	}
 
	public void writeFile(String fileName,InputStream fis) throws IOException {
		logger.info("Enter method writeFile.");
		
		try { 
			
			String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
			
			if ("pdf".equalsIgnoreCase(prefix)) {
				response.setContentType("application/pdf");
			} else if ("csv".equalsIgnoreCase(prefix)) {
				response.setContentType("application/csv");
			} else if ("xls".equalsIgnoreCase(prefix) || "xlsx".equalsIgnoreCase(prefix)) {
				response.setContentType("application/vnd.ms-excel");
			} else if ("txt".equalsIgnoreCase(prefix)) {
				response.setContentType("text/plain");
			} else if ("docx".equalsIgnoreCase(prefix) || "doc".equalsIgnoreCase(prefix)){
				response.setContentType("application/vnd.ms-word");
			} else {
				response.setContentType("application/octet-stream");
			}
//			response.addHeader("Content-Disposition", "inline;filename=\"" + fileName+"\"");
			if(fis.available() > 10499065){
				response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName+"\"");
			}else{
				response.addHeader("Content-Disposition", "inline;filename=\"" + fileName+"\"");
			}
//			response.setContentType("application/octet-stream");
//			response.addHeader("Content-Disposition", "attachment;filename=\"" + fileName+"\"");
			OutputStream os = response.getOutputStream();

			byte[] buff = new byte[1024 * 10];
			int len = 0;
			while ((len = fis.read(buff)) != -1) {
				os.write(buff, 0, len);
			}
		} catch (Exception e) {
			logger.error("writeFile error: ", e);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		logger.info("Exit method writeFile");
	}
	
	public String previewFile(String originalFilePath) throws IOException {
		logger.info("Enter method previewFile.");
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
			File file = new File(originalFilePath);
			String previewFileName = df.format(new Date())+"_"+file.getName();
			String previewFilePath = request.getSession().getServletContext().getRealPath("/")+ "previews\\" + previewFileName;
			String outputFilePath = "previews/" + previewFileName;
			if(this.copyFile(originalFilePath,previewFilePath)){
				return outputFilePath;
			};
		} catch (Exception e) {  
            e.printStackTrace();  
        }
		logger.info("Exit method previewFile");
		return null;
	}
	
	protected String tifToPdf(String originalFilePath){
		logger.info("Enter method tifToPdf.");
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		File file = new File(originalFilePath);
		String previewFileName = df.format(new Date())+"_"+file.getName().toUpperCase().replace("TIF", "PDF");;
		String previewFilePath = request.getSession().getServletContext().getRealPath("/")+ "previews/" + previewFileName;
		logger.info(previewFilePath);
		try {  
	        String cmd = "convert" + " " + originalFilePath + " " + previewFilePath;
	        Process process = Runtime.getRuntime().exec(cmd);  
	        int status = process.waitFor();
	        if(status != 0){  
	            return null; 
	        }  
	    }  
	    catch (Exception e){  
	        e.printStackTrace();  
	    }
	    logger.info("Exit method tifToPdf.");
		return previewFilePath;
	}
	
	/** 
	* copy file
	* @param oldPath String
	* @param newPath String
	*/ 
	public boolean copyFile(String oldPath, String newPath) { 
		try { 
			int bytesum = 0; 
			int byteread = 0; 
			File oldfile = new File(oldPath); 
			if (oldfile.exists()) { 
				InputStream inStream = new FileInputStream(oldPath); 
				FileOutputStream fs = new FileOutputStream(newPath); 
				byte[] buffer = new byte[1444]; 
				while ( (byteread = inStream.read(buffer)) != -1) { 
					bytesum += byteread; 
					System.out.println(bytesum); 
					fs.write(buffer, 0, byteread); 
				} 
				inStream.close(); 
				return true;
			}
		} 
		catch (Exception e) { 
			e.printStackTrace(); 
		} 
		return false;

	} 

	/**
	 * @return the request
	 */
	protected HttpServletRequest getRequest() {
		return request;
	}
	
	protected HttpServletResponse getResponse()
	{
		return  response;
	}

	public static Date parseDate(String dateStr) {
		try {
			return defaulDateFormater.parse(dateStr);
		} catch (Exception ex) {
			return null;
		}

	}

	public static String parseString(Date date) {
		try {
			return defaulDateFormater.format(date);
		} catch (Exception ex) {
			// ignore
		}

		return "";
	}

	/*
	 * public String getUserName(){ return
	 * SystemUtil.getCurrentUser().getUsername(); } public String
	 * getLonginUserRealName(){ String firstName =
	 * SystemUtil.getCurrentUser().getFirstName(); String lastName =
	 * SystemUtil.getCurrentUser().getLastName(); StringBuffer realNameBuf = new
	 * StringBuffer(); realNameBuf.append(firstName).append("
	 * ").append(lastName); return realNameBuf.toString(); } public String
	 * getUserTel(){ return SystemUtil.getCurrentUser().getPhoneNo(); }
	 */

	public ServletContext getServletContext() {
		return servletContext;
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}

	/**
	 * validate a string is a valid date and in MM/dd/yyyy format
	 * 
	 * @param strDate
	 * @return
	 */
	public boolean isValidDate(String strDate) {

		Date tmpDate = null;

		try {
			tmpDate = defaulDateFormater.parse(strDate);

		} catch (ParseException e) {
			return false;
		}

		if (!defaulDateFormater.format(tmpDate).equals(strDate)) {
			return false;
		}

		return true;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	/**
	 * @return the commonLookupService
	 */
	public ICommonLookupService getCommonLookupService() {
		return commonLookupService;
	}

	/**
	 * @param commonLookupService the commonLookupService to set
	 */
	public void setCommonLookupService(ICommonLookupService commonLookupService) {
		this.commonLookupService = commonLookupService;
	}
	
	
}