package com.saninco.ccm.service.imports;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.saninco.ccm.dao.IInvoiceDao;

/**
 * 
 * @author wei.su
 * Modified Logger by Chao.liu 10/08/23 AM
 */

public class ImportFromXMLImplService {
	private final Logger logger = Logger.getLogger(this.getClass());
	private IInvoiceDao invoiceDao;
	private static final String filepath="C:\\Documents and Settings\\Administrator\\ccm";
	
	public ImportFromXMLImplService() {
	}
	
	public IInvoiceDao getInvoiceDao() {
		return invoiceDao;
	}

	public void setInvoiceDao(IInvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}


	/**
	 * According to the table name list of different table sqlstring
	 * @param list
	 * @return
	 */

	@SuppressWarnings({ "unchecked", "unchecked" })
	public void insertDateByXML(File f) {
		SAXReader reader = new SAXReader();
		Document document;
		StringBuffer sBuffer;
		List columns = new ArrayList();
		List values = new ArrayList();
		try {
		
			document = reader.read(f);
			Element root = document.getRootElement();
			String table = root.attributeValue("table");
			sBuffer=new StringBuffer();
			sBuffer.append("insert into ");
			sBuffer.append(table);
			sBuffer.append(" "+"(");
			Element rootElm = document.getRootElement();
			
			for (Iterator itp = rootElm.elementIterator(); itp.hasNext();) {
				Element chiElement = (Element) itp.next();

				for (Iterator itc = chiElement.elementIterator(); itc.hasNext();) {
					Element notes = (Element) itc.next();
					String column = notes.element("column").getText();
					columns.add(column);
					String value = notes.element("value").getText();
					values.add(value);
				

				}
			}
			
			for (int i=0;i<columns.size();i++)
			{
				if(i==columns.size()-1)
				{
					sBuffer.append(columns.get(i));
					break;
				}
				sBuffer.append(columns.get(i)+",");
			}
			
			sBuffer.append(") values(\"");
			
			for (int i=0;i<values.size();i++)
			{
				
				if(i==values.size()-1)
				{
					sBuffer.append(values.get(i));
					break;
				}
				sBuffer.append(values.get(i)+",");
				
				
			}
			
			sBuffer.append("\")");
			invoiceDao.saveDataByXml(sBuffer.toString());
			//System.out.println(sBuffer.toString());
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}

		logger.info("Exit method insertDateByXML.");
	}
	
	/**
	 * According to the table name list of different table sqlstring
	 * @param list
	 * @return
	 */
	public void refreshFileList() {
		File dir = new File(filepath);
		File[] files = dir.listFiles();

		for (int i = 0; i < files.length; i++) {
			insertDateByXML(files[i]);
		}
		logger.info("Exit method refreshFileList.");
	}

}
