package com.saninco.ccm.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExcelBatchHelper {
	private ExcelBatchDaoDelegate excelBatchDaoDelegate;
	
	private Map<String,Object> excelDataMap = null;

	private File quoteFile;
	private String fileName;
	
	public ExcelBatchHelper(File file,String fileName){
		excelDataMap = getExcelDataMap(file,fileName);
		this.quoteFile = file;
		this.fileName = fileName;
	}
	
	private Map<String,Object> getExcelDataMap(File quoteFile,String fileName){
		Map<String,Object> map = null;
		try    
        {   
			if(fileName.endsWith(ExcelFileUtil.EXTENSION_XLS)){
				map = ExcelFileUtil.getExcelDataList(quoteFile, fileName);
			}else{
				ReadExcel re = new ReadExcel(quoteFile);
				map = re.getMyDataList();
			}
        }catch (Exception e) {
        	e.printStackTrace();
        }
		return map;
	}
	
	public Map<String,String> uploadExcelBatchHelper(String errorExcelDirPath){
		// batch no , 通过 UUID 生成的一个任意的
		// 并且不重复的字符串
		String batchNo = UUID.randomUUID().toString();
		List<Map<String,String>> dataList = null;
		List<String> columnList = null;
		Map<String,String> returnMap = new HashMap<String,String>();
		try    
        {   
			if (excelBatchDaoDelegate!=null && excelDataMap!=null) {
				// 获取列头
				columnList = (List<String>) excelDataMap.get("columnList");
				// 获取数据列
				dataList = (List<Map<String, String>>) excelDataMap.get("dataList");
				//将数据插入临时表
				excelBatchDaoDelegate.insertInventoryTemporary(dataList,columnList,batchNo);
				//调用存储过程，验证数据,将错误数据插入error表。如果验证通过，将数据直接更新到quote表。
				excelBatchDaoDelegate.callInventoryVerification(batchNo);
				//查询error表
				List<Map<String,Object>> errorList = excelBatchDaoDelegate.queryTmpInventoryError(batchNo);
				if(errorList!=null && errorList.size()>0){
					if(fileName.endsWith(ExcelFileUtil.EXTENSION_XLS)){
						ExcelFileUtil.createErrorFile(quoteFile, fileName,columnList,errorList,errorExcelDirPath);
					}else{
						errorList = ExcelFileUtil.orderByErrorListForColumn(columnList,errorList);
						JExcelFileUtil jUtil = new JExcelFileUtil();
						jUtil.createExcelFile(errorExcelDirPath,columnList,"Rate");
						for(int i = 0;i<dataList.size();i++){
							Map<String, String> errorMap = new HashMap<String,String>();
							for(int j=0;j<errorList.size();j++){
								if(errorList.get(j)!=null && (i+1) == Integer.valueOf(errorList.get(j).get("row_number")+"")){
									errorMap.put(errorList.get(j).get("field").toString().toUpperCase(), "Y");
								}
							}
							jUtil.writeLine(i + 1, dataList.get(i),columnList,errorMap);
						}
						jUtil.createErrorSheet(errorList,columnList);
						jUtil.write();
					}
//					ExcelFileUtil.createErrorFile(quoteFile, fileName,columnList,errorList,errorExcelDirPath);
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
	
	public void setExcelBatchDaoDelegate(ExcelBatchDaoDelegate excelBatchDaoDelegate){
		this.excelBatchDaoDelegate = excelBatchDaoDelegate;
	}

	public Map<String, Object> getExcelDataMap() {
		return excelDataMap;
	}
}
