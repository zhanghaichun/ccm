/**
 * 
 */
package com.saninco.ccm.service.masterInventory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.saninco.ccm.dao.IMasterInventoryDao;
import com.saninco.ccm.util.ExcelFileUtil;
import com.saninco.ccm.util.JExcelFileUtil;
import com.saninco.ccm.util.ReadExcel;
/**
 * @author xinyu.Liu
 * 
 */
public class MasterInventoryServiceImpl implements IMasterInventoryService {
	private final Logger logger = Logger.getLogger(this.getClass());

	private IMasterInventoryDao masterInventoryDao;
	
	public Map<String,String> uploadMasterInventory(File quoteFile,String fileName,String errorExcelDirPath){
		String batchNo = UUID.randomUUID().toString();
		List<Map<String,String>> dataList = null;
		List<String> columnList = null;
		Map<String,String> returnMap = new HashMap<String,String>();
		try    
        {   
			Map map = null;
			if(fileName.endsWith(ExcelFileUtil.EXTENSION_XLS)){
				map = ExcelFileUtil.getExcelDataList(quoteFile, fileName);
			}else{
				ReadExcel re = new ReadExcel(quoteFile);
//				ReadExcel re = new ReadExcel();
				map = re.getMyDataList();
			}
			if (map!=null) {
				columnList = (List<String>) map.get("columnList");
				dataList = (List<Map<String, String>>) map.get("dataList");
				//将数据插入临时表
				masterInventoryDao.insertMasterInventoryTemporary(dataList,columnList,batchNo);
				//调用存储过程，验证数据,将错误数据插入error表。如果验证通过，将数据直接更新到quote表。
				masterInventoryDao.callMasterInventoryVerification(batchNo);
				//查询error表
				List<Map<String,Object>> errorList = masterInventoryDao.queryTmpMasterInventoryError();
				if(errorList!=null && errorList.size()>0){
					if(fileName.endsWith(ExcelFileUtil.EXTENSION_XLS)){
						ExcelFileUtil.createErrorFile(quoteFile, fileName,columnList,errorList,errorExcelDirPath);
					}else{
						errorList = ExcelFileUtil.orderByErrorListForColumn(columnList,errorList);
						JExcelFileUtil jUtil = new JExcelFileUtil();
						jUtil.createExcelFile(errorExcelDirPath,columnList,"Master Inventory");
						for(int i = 0;i<dataList.size();i++){
							Map<String, String> errorMap = new HashMap<String,String>();
							for(int j=0;j<errorList.size();j++){
								if(errorList.get(j)!=null && (i+1) == Integer.valueOf(errorList.get(j).get("row_number")+"")){
									errorMap.put(errorList.get(j).get("field")+"", "Y");
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
    
	public IMasterInventoryDao getMasterInventoryDao() {
		return masterInventoryDao;
	}

	public void setMasterInventoryDao(IMasterInventoryDao masterInventoryDao) {
		this.masterInventoryDao = masterInventoryDao;
	}

}
