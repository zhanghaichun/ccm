package com.saninco.ccm.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;



public class JasperReportUtil{
	
	//Html导出
	@SuppressWarnings("unchecked")
	public static void htmlExport(HttpServletRequest request, HttpServletResponse response,String reportFilePath,Map params,List list)throws Exception{
		
		try {
			 response.setContentType("text/html;charset=UTF-8");   
		
			byte output[];
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();
			JasperReport jasperReport = (JasperReport)JRLoader.loadObject (reportFilePath); 
			//得到jasperPrint对象
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,params,new ReportResult(list));
			
			request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
			//使用JRHtmlExporter导出html格式
			JRHtmlExporter htmlExport = new JRHtmlExporter();
			htmlExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			htmlExport.setParameter(JRExporterParameter.OUTPUT_STREAM, byteOutput);
			
			//htmlExport.setParameter(JRHtmlExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			//htmlExport.setParameter(JRHtmlExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			htmlExport.setParameter(JRHtmlExporterParameter.IMAGES_URI, "servlets/image?image=");
			
			htmlExport.exportReport();
			output = byteOutput.toByteArray();
			
			
			HttpServletResponse res = response;
			
			OutputStream os = res.getOutputStream();
			
			os.write(output);
			
			
			os.close();
		}catch(JRException e){ 
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//excel导出
	public static void excelExport(String outputFilePath , List<String> reportFilePathList,Map params,List<JRDataSource> dataSourceList,String excelFileName, String[] sheetName)throws Exception
	{
		
		try {
//			 response.setContentType("text/html;charset=UTF-8");
			 byte output[];
			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

			List<JasperPrint> printList = new ArrayList<JasperPrint>();
			for(int i = 0 ; i < reportFilePathList.size() ; i++ )
			{
				String reportFilePath = reportFilePathList.get(i);
				JRDataSource dataSource = dataSourceList.get(i);

				JasperReport jasperReport = (JasperReport)JRLoader.loadObject (reportFilePath); 
				//得到jasperPrint对象
				JasperPrint jasperPrint = JasperFillManager.fillReport (jasperReport, params,dataSource);
				
				printList.add(jasperPrint);
			}
		
			/*
			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST,jasperPrintList);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);
			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,"BIG5");
			exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE , Boolean.TRUE);
			exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
			exporter.exportReport();
			*/
			
			
            
			//使用JRXlsExporter导出excel格式
            JRXlsExporter excelExport = new JRXlsExporter(); // Excel
			
            //excelExport.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            excelExport.setParameter(JRXlsExporterParameter.JASPER_PRINT_LIST, printList);
            excelExport.setParameter(JRXlsExporterParameter.SHEET_NAMES, sheetName);
            //excelExport.setParameter(JRExporterParameter.OUTPUT_STREAM, byteOutput);
            excelExport.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            excelExport.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            excelExport.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFilePath + excelFileName);
            excelExport.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,Boolean.FALSE);
            
            excelExport.exportReport();

		}catch(JRException e){ 
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	//动态生成投放终端明细报表（JasperDesign）
	@SuppressWarnings("deprecation")
	public static JasperDesign getJasperDesign(String fileDir,String[] strArray){
		int  FINAL_LEFT = 2670;
		int  FINAL_WIDTH = 80;
		File file = new File(fileDir);
		JasperDesign  jasperDesign = new JasperDesign();
		try {
			jasperDesign = JRXmlLoader.load(file);

			JRDesignBand  columnHeaderBand = (JRDesignBand )jasperDesign.getColumnHeader();//获取ColumnHeader拦 
			JRDesignBand  detailBand = (JRDesignBand )jasperDesign.getDetail();//获取Detail拦 
			for(int i= 0;i<strArray.length;i++){
				
				JRDesignStaticText staticText = (JRDesignStaticText)(((JRDesignBand)jasperDesign.getColumnHeader()).getElementByKey("staticText-41")).clone();

				//生成列头
				staticText.setX(FINAL_LEFT+FINAL_WIDTH*i);
				staticText.setY(0);
				staticText.setWidth(FINAL_WIDTH);
				staticText.setHeight(20);
				if(strArray[i].equals("F3")){
					staticText.setText("委托日期");
				}else if(strArray[i].equals("F4")){
					staticText.setText("委托创建时间");
				}else if(strArray[i].equals("F6")){
					staticText.setText("终端开通时间");
				}else if(strArray[i].equals("F10")){
					staticText.setText("任务下单时间");
				}else if(strArray[i].equals("F11")){
					staticText.setText("商户要求完成时间");
				}else if(strArray[i].equals("F12")){
					staticText.setText("任务预审时间");
				}else if(strArray[i].equals("F13")){
					staticText.setText("派工时间");
				}else if(strArray[i].equals("F14")){
					staticText.setText("派工要求完成日期");
				}else if(strArray[i].equals("F15")){
					staticText.setText("服务领用时间");
				}else if(strArray[i].equals("F16")){
					staticText.setText("电话销单时间");
				}else if(strArray[i].equals("F17")){
					staticText.setText("服务回收时间");
				}else if(strArray[i].equals("F18")){
					staticText.setText("设备销单时间");
				}else if(strArray[i].equals("F19")){
					staticText.setText("实际服务日期");
				}else if(strArray[i].equals("F20")){
					staticText.setText("服务单录入时间");
				}else if(strArray[i].equals("F21")){
					staticText.setText("服务销单时间");
				}else if(strArray[i].equals("F50")){
					staticText.setText("TMS上传时间");
				}
				
			    columnHeaderBand.addElement(staticText);//添加静态文本
			    //添加detail内容
			    String field = "$F{"+strArray[i]+"}";

			    //JRDesignTextField   textField  = (JRDesignTextField )detailBand.getElementByKey("textField-40").clone();//
			    JRDesignTextField textField = (JRDesignTextField)(((JRDesignBand)jasperDesign.getDetail()).getElementByKey("textField-41")).clone();

			     textField.setX(FINAL_LEFT+FINAL_WIDTH*i);
			     textField.setPattern("yyyy/MM/dd");
			     JRDesignExpression expression = new JRDesignExpression();
			     expression.setValueClass(java.sql.Timestamp.class);
			     expression.setText(field);
			     textField.setExpression(expression);
			     detailBand.addElement(textField);

			}
			//删除模板（staticText-40,textField-40的模板）
			JRDesignStaticText staticText = (JRDesignStaticText)(((JRDesignBand)jasperDesign.getColumnHeader()).getElementByKey("staticText-41"));
			JRDesignTextField textField = (JRDesignTextField)(((JRDesignBand)jasperDesign.getDetail()).getElementByKey("textField-41"));
			columnHeaderBand.removeElement(staticText);	
			detailBand.removeElement(textField);
			
		} catch (JRException e) {
			e.printStackTrace();
		}
		return jasperDesign;
	}
	
	//PDF
	public static void pdfExport(String outputFilePath ,String jasperPath, List<String> templateList,Map parameter,List dataSourceList)throws Exception
	{
		try {

			JRPdfExporter jrpdfExporter = new JRPdfExporter();   
			List<JasperPrint> jasperPrintList = new ArrayList<JasperPrint>();
			for (String jasperName : templateList) {
				JRDataSource ds = new JRBeanCollectionDataSource(dataSourceList);
				JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(jasperPath + jasperName);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, parameter, ds);
				
				jasperPrintList.add(jasperPrint);
			}
            //设JasperPrint参数    生成PDF文件格式
            jrpdfExporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,jasperPrintList);//加载报表集合
            jrpdfExporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, outputFilePath);//指定导出的报表的文件名和路径
            jrpdfExporter.exportReport();   //执行

		} catch (JRException jre) {
			throw new RuntimeException(jre);
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List abc = new ArrayList();
		Map a = new HashMap();
		a.put("vendorId", 1);
		a.put("quoteNumber", "1231111");
		abc.add(a);
		List<String> tem = new ArrayList<String>();
		tem.add("VendoryInventoryReport.jasper");
		try {
			pdfExport("E://abc.pdf", "E://Workspaces//MyEclipse 10//ccm2.0//webroot//WEB-INF//classes//iReportXML//", tem , null, abc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
