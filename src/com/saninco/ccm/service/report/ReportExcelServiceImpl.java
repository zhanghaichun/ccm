package com.saninco.ccm.service.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.saninco.ccm.dao.IReportDAO;
import com.saninco.ccm.dao.ISysConfigDAO;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.CreatedReport;
import com.saninco.ccm.model.SysConfig;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemEmailConfig;
import com.saninco.ccm.util.SystemUtil;
/**
 * @author Chao.Liu
 */
public class ReportExcelServiceImpl implements IReportExcelService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private IReportDAO reportDAO;
	private ISysConfigDAO sysConfigDAO;
	
	private String subject = "Saninco Report Excel";
	private String text = "This is the file you just specify the generation. Date:"+new Date();
	private Object toEmail = null;
	private List<String[]> fileList = new ArrayList<String[]>();
	
	public ReportExcelServiceImpl() {
	}
	
	/**
	 * @author Chao.Liu
	 * TimerOut
	 */
	public void saveIReportExcelA() throws BPLException{
		this.saveIReportExcel("A");
	}
	public void saveIReportExcelD() throws BPLException{
		this.saveIReportExcel("D");
	}
	public void saveIReportExcelM() throws BPLException{
		this.saveIReportExcel("M");
	}
	
	/**
	 * @author Chao.Liu
	 * @param jasperName
	 * @param excelPath
	 * @see os[0]:ReportId,os[1]:CreatdReportId,os[2]:CreatdReportName,
	 * @see os[3]:FilePath,os[4]:FileName,
	 * @see os[5]:CreatedById,os[6]:UserEmail,os[7]:sendEmail.
	 */
	public void saveIReportExcel(String runType) throws BPLException{
		logger.info(CcmFormat.formatEnterLog("Down Excel And Send Email", runType));
		
		try {
			List<Object[]> los = reportDAO.getIReportInfos(runType); // Search Run Info
			
			if(los != null && los.size() > 0){
				String folderPath = this.getFolderPath(runType);         // Created Folder
				List<Element> lrs = this.getReportChildren();            // Read XML
				
//				Object groupUser = los.get(0)[5]; // init User Group
//				toEmail = los.get(0)[6];          // init User Email
//				int index = 1;
				
				for (Object[] os : los) {
					String[] jasperNames = this.getJasperNames(lrs.get(new Integer(os[0].toString())-1));
					if(jasperNames != null){
						// Star Down Excel [Report Status]
						reportDAO.updateCreatedReport(os[1], "3");
						
						String excelPath = os[3]!=null?os[3].toString():(folderPath+"\\").replaceAll("\\\\", "/");
						this.createdFolder(excelPath);
						String excelName = os[4]!=null?os[4].toString():(os[2].toString()+os[1]+".xls");
						
						reportDAO.saveIReportExcel(jasperNames, excelPath+excelName);
						
						// Down Excel Success [Report Status]
						reportDAO.updateCreatedReport(os[1], "4");
						if(os[3]==null || os[4]==null){
							reportDAO.updatePathAndName(excelPath, excelName, os[1]);
						}
						
						// Send Email | Don't Delete
//						if(groupUser.equals(os[5])){
//							if(os[6] != null && os[7].equals("Y")){
//								fileList.add(new String[]{excelName,excelPath+excelName});
//							}
//						}else{
//							groupUser = os[5];
//							if(fileList.size() > 0){
//								this.sendEmail(subject, text, toEmail.toString(), fileList);
//							}
//							toEmail = os[6];
//							fileList = new ArrayList<String[]>();
//							if(os[6] != null && os[7].equals("Y")){
//								fileList.add(new String[]{excelName,excelPath+excelName});
//							}
//						}
//						
//						if(index == los.size()){
//							if(fileList.size() > 0){
//								this.sendEmail(subject, text, toEmail.toString(), fileList);
//							}
//						}
					}
//					index++;
				}
			}
		} catch (RuntimeException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
//		catch (MessagingException e) {
//			logger.error(CcmFormat.formatErrorLog(e));
//			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
//			throw bpe;
//		}

		logger.info(CcmFormat.formatExitLog());
	}
	private String getFolderPath(String runType){
		String basicPath = sysConfigDAO.getReportFilePath();
		File folder = new File(basicPath+"\\"+runType+" "+CcmFormat.formatDate(new Date()));
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		return folder.getPath();
	}
	private void createdFolder(String path){
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}
	private List<Element> getReportChildren() throws BPLException{
		String xmlPath = reportDAO.formatPath("iReportRoster.xml");
		Vector xmlVector = null;
		FileInputStream fis = null;
		Element reports = null;
		
		try {
			
			fis = new FileInputStream(xmlPath);
			SAXBuilder builder = new SAXBuilder(
					"org.apache.xerces.parsers.SAXParser");
			Document doc = builder.build(xmlPath);
			reports = doc.getRootElement();
			
		} catch (IOException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} catch (JDOMException e) {
			logger.error(CcmFormat.formatErrorLog(e));
			BPLException bpe = new BPLException(ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		} 
		
		return reports.getChildren();
	}
	private String[] getJasperNames(Element e){
		if(!e.getChild("able").getTextTrim().toLowerCase().equals("yes")){
			return null;
		}
		if(new Integer(e.getChild("has").getTextTrim()) > 1){
			return e.getChild("ireportPath").getTextTrim().split(",");
		}
		return new String[]{e.getChild("ireportPath").getTextTrim()};
	}
	/**
	 * @author Chao.Liu
	 * Send Email
	 * @param subject
	 * @param text
	 * @param toEmail
	 * @param filePath
	 * @throws MessagingException 
	 */
	public void sendEmail(String subject,String text,String toEmail,List<String[]> fileList) throws MessagingException{
		SystemEmailConfig sec = sysConfigDAO.getSystemEmailConfig();
		
		Properties props = new Properties();
		props.setProperty("mail.smtp.auth", "true");
		props.setProperty("mail.transport.protocol", "smtp");
		Session session = Session.getInstance(props);
//		session.setDebug(true);
		
		Message msg = new MimeMessage(session);
		msg.setSubject(subject); // indefinite
		
		MimeMultipart msgMultipart = new MimeMultipart("mixed");
		this.addEmailAttch(msgMultipart,fileList); // Add Email Attch
		msg.setContent(msgMultipart);
		
		// Email Text
		MimeBodyPart content = new MimeBodyPart();
		MimeMultipart bodyMultipart = new MimeMultipart("related");
		MimeBodyPart emailText = new MimeBodyPart();			
		emailText.setContent(text,"text/html;charset=gbk");
		
		bodyMultipart.addBodyPart(emailText);
		content.setContent(bodyMultipart);
		msgMultipart.addBodyPart(content);
		
		msg.setFrom(new InternetAddress(""+sec.getSystemEmailAdress()+"")); 
		msg.saveChanges();

		Transport transport = session.getTransport();
		transport.connect(sec.getSystemEmailSmtpServer(), sec.getSystemEmailSmtpServerPort(), sec.getSystemEmailUserName(), sec.getSystemEmailPassWord());
		transport.sendMessage(msg, new Address[]{new InternetAddress(toEmail)});  // indefinite
		transport.close();
	}
	private void addEmailAttch(MimeMultipart msgMultipart,List<String[]> fileList) throws MessagingException{
		// strs[0] : File Name , strs[1] : File Path
		for(String[] strs : fileList){
			MimeBodyPart attch = new MimeBodyPart();	
			
			DataSource ds = new FileDataSource(strs[1]);
			DataHandler dh = new DataHandler(ds );
			attch.setDataHandler(dh);
			attch.setFileName(strs[0]);
			
			msgMultipart.addBodyPart(attch);
		}
	}
	/**
	 * Setter And Getter
	 */
	public IReportDAO getReportDAO() { return reportDAO; }
	public void setReportDAO(IReportDAO reportDAO) { this.reportDAO = reportDAO; }
 
	public ISysConfigDAO getSysConfigDAO() { return sysConfigDAO; } 
	public void setSysConfigDAO(ISysConfigDAO sysConfigDAO) { this.sysConfigDAO = sysConfigDAO; }

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Object getToEmail() {
		return toEmail;
	}

	public void setToEmail(Object toEmail) {
		this.toEmail = toEmail;
	}

	public List<String[]> getFileList() {
		return fileList;
	}

	public void setFileList(List<String[]> fileList) {
		this.fileList = fileList;
	}
	
	
}
