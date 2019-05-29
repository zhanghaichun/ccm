/**
 * 
 */
package com.saninco.ccm.service.quote;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.saninco.ccm.dao.IQuoteDao;
import com.saninco.ccm.util.ExcelFileUtil;

public class QuoteServiceImpl implements IQuoteService {
	private final Logger logger = Logger.getLogger(this.getClass());

	private IQuoteDao quoteDao;
	
	public Map<String,String> uploadQuote(File quoteFile,String fileName,String errorExcelDirPath){
		String batchNo = UUID.randomUUID().toString();
		List<Map<String,String>> dataList = null;
		List<String> columnList = null;
		Map<String,String> returnMap = new HashMap<String,String>();
		try    
        {   
			Map map = ExcelFileUtil.getExcelDataList(quoteFile, fileName);
			if (map!=null) {
				columnList = (List<String>) map.get("columnList");
				dataList = (List<Map<String, String>>) map.get("dataList");
				//将数据插入临时表
				quoteDao.insertQuoteTemporary(dataList,columnList,batchNo);
				//调用存储过程，验证数据,将错误数据插入error表。如果验证通过，将数据直接更新到quote表。
				quoteDao.callQuoteVerification(batchNo);
				//查询error表
				List<Map<String,Object>> errorList = quoteDao.queryTmpQuoteError();
				if(errorList!=null && errorList.size()>0){
					ExcelFileUtil.createErrorFile(quoteFile, fileName,columnList,errorList,errorExcelDirPath);
					returnMap.put("isSuccess", "N");
					returnMap.put("errorFilePath", errorExcelDirPath);
				}else{
					returnMap.put("isSuccess", "Y");
				}
			}
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return returnMap;
	}
	
	public IQuoteDao getQuoteDao() {
		return quoteDao;
	}

	public void setQuoteDao(IQuoteDao quoteDao) {
		this.quoteDao = quoteDao;
	}

}
