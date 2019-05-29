package com.saninco.ccm.service.report;

import java.util.List;

import javax.mail.MessagingException;

import com.saninco.ccm.dao.IReportDAO;
import com.saninco.ccm.exception.BPLException;
/**
 * @author Chao.Liu
 */
public interface IReportExcelService {
	/**
	 * @author Chao.Liu
	 * @param jasperName
	 * @param excelPath
	 */
	public void saveIReportExcel(String runType) throws BPLException;
	/**
	 * @author Chao.Liu
	 * TimerOut
	 */
	public void saveIReportExcelA() throws BPLException;
	public void saveIReportExcelD() throws BPLException;
	public void saveIReportExcelM() throws BPLException;
	/**
	 * @author Chao.Liu
	 * Send Email
	 * @param subject
	 * @param text
	 * @param toEmail
	 * @param filePath
	 * @throws MessagingException
	 */
	public void sendEmail(String subject,String text,String toEmail,List<String[]> fileList) throws MessagingException;
}
