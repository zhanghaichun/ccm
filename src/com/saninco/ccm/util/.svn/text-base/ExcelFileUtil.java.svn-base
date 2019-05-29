/**
 * 
 */
package com.saninco.ccm.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;

public class ExcelFileUtil {
	public static final String EXTENSION_XLS = "xls";
	public static final String EXTENSION_XLSX = "xlsx";
	public static final int MAX_DATA_LENGTH = 10000;
	public static final int START_INDEX = 1;
	public static final int END_INDEX = 72;
    
	public static Map<String,Object> getExcelDataList(File file,String fileName){
		Workbook readwb = null;
		InputStream instream = null;
		try    
        {   
			List<String> columnList = new ArrayList<String>();
			List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();
			instream = new FileInputStream(file);
    		if (fileName.endsWith(EXTENSION_XLS)) {
    			try {
    				readwb = new HSSFWorkbook(instream);
    	        } catch (Exception ex) {
    	        	instream = new FileInputStream(file);
    	        	readwb = WorkbookFactory.create(instream);
    	        }
    		} 
    		//2007 excel
    		else if (fileName.endsWith(EXTENSION_XLSX)) {
    			try {
    				readwb = WorkbookFactory.create(instream);
    	        } catch (Exception ex) {
    	        	instream = new FileInputStream(file);
    	        	readwb = new HSSFWorkbook(instream);
    	        }
    		}
			if(readwb!=null){
				Sheet sheet = readwb.getSheetAt(0);
				int firstRowIndex = sheet.getFirstRowNum();
				int lastRowIndex = sheet.getLastRowNum();
				//获取所有列名
				Row firstRow = sheet.getRow(0);
				int firstColumnIndex = firstRow.getFirstCellNum();
				int lastColumnIndex = firstRow.getLastCellNum();
				for (int columnIndex = firstColumnIndex; columnIndex <= lastColumnIndex; columnIndex++) {
					Cell currentCell = firstRow.getCell(columnIndex);// 当前单元格
					if(currentCell!=null && currentCell.getStringCellValue()!=null && !"".equalsIgnoreCase(currentCell.getStringCellValue())){
						columnList.add(getCellValue(currentCell, true));
					}
				}
				//循环数据，将数据放入dataList
				for (int rowIndex = firstRowIndex+1; rowIndex <= lastRowIndex; rowIndex++) {
					Row currentRow = sheet.getRow(rowIndex);// 当前行
					if(!isRowEmpty(currentRow) && currentRow!=null){
						Map<String,String> map = new HashMap<String,String>();
						for (int columnIndex = 0; columnIndex < columnList.size(); columnIndex++) {
							Cell currentCell = currentRow.getCell(columnIndex);// 当前单元格
							String currentCellValue = "";
							if(currentCell!=null){
								currentCellValue = getCellValue(currentCell, true);// 当前单元格的值
							}
							map.put(columnList.get(columnIndex), currentCellValue);
						}
						dataList.add(map);
					}
				}
				Map<String,Object> map = new HashMap<String,Object>();
				map.put("dataList", dataList);
				map.put("columnList", columnList);
				return map;
			}
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	if (instream != null) {
                try {
                	instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
		return null;
	}
	
	/**
	 * 删除Excel文件中不必要的sheet。
	 */
	private static void deleteUnnecessarySheet(Workbook readwb) {
		
		// getNumberOfSheets 方法获取Excel文档中的sheet数目。
		while( readwb.getNumberOfSheets() > 1) {
			readwb.removeSheetAt(1);
		}
	}
	
	public static void createErrorFile(File file,String fileName,List<String> columnList,List<Map<String,Object>> errorList,String errorFilePath){
		Workbook readwb = null;
		InputStream instream = null;
		try    
        {
			instream = new FileInputStream(file);
			if (fileName.endsWith(EXTENSION_XLS)) {
    			try {
    				readwb = new HSSFWorkbook(instream);
    	        } catch (Exception ex) {
    	        	instream = new FileInputStream(file);
    	        	readwb = WorkbookFactory.create(instream);
    	        }
    		} 
    		//2007 excel
    		else if (fileName.endsWith(EXTENSION_XLSX)) {
    			try {
    				readwb = WorkbookFactory.create(instream);
    	        } catch (Exception ex) {
    	        	instream = new FileInputStream(file);
    	        	readwb = new HSSFWorkbook(instream);
    	        }
    		}
			
			errorList = orderByErrorListForColumn(columnList,errorList);
			
			if(readwb!=null){
				
				// Excel 文档默认会创建三个sheets, 删除不必要的sheet。
				deleteUnnecessarySheet(readwb);
				
				Sheet sheet = readwb.getSheetAt(0);
				if(errorList.size()>0){
					//创建error sheet
					Sheet errorSheet = readwb.createSheet("error");
					//创建error sheet的列头
					Row errorFirstRow = errorSheet.createRow(0);
					Cell columnNumberCell = errorFirstRow.createCell(0);
					columnNumberCell.setCellValue("Error Column");
					Cell rowNumberCell = errorFirstRow.createCell(1);
					rowNumberCell.setCellValue("Row Number");
					Cell errorMessageCell = errorFirstRow.createCell(2);
					errorMessageCell.setCellValue("Error Message");
					
					for(int i=0;i<errorList.size();i++){
						//row_number 为临时表中的自增长id 对应数据的行号。
						int rowNumber = Integer.valueOf(errorList.get(i).get("row_number")+"");
						Row row = sheet.getRow(rowNumber);
						//创建error行
						Row errorRow = errorSheet.getRow(i+1);
						if(errorRow == null) {
							errorRow = errorSheet.createRow(i+1);
						}
						
						if(row!=null){
							Cell currentCell = null;
							String columnNo = "";
							//根据列名获取相对应的列
							for(int j=0;j<columnList.size();j++){
								if(columnList.get(j)!=null && errorList.get(i)!=null && columnList.get(j).equalsIgnoreCase(errorList.get(i).get("field")+"")){
									currentCell = row.getCell(j);
									if(currentCell == null){
										currentCell = row.createCell(j);
									}
									columnNo = getCorrespondingLabel(currentCell.getColumnIndex()+1);
									break;
								}
							}
							
							//将error数据插入到error的sheet中。
							Cell columnNumberErrorCell = errorRow.createCell(0);
							columnNumberErrorCell.setCellValue(columnNo);
							Cell rowNumberErrorCell = errorRow.createCell(1);
							rowNumberErrorCell.setCellValue(rowNumber+1);
							Cell errorMessageErrorCell = errorRow.createCell(2);
							errorMessageErrorCell.setCellValue(errorList.get(i).get("note")+"");
							
							//更改列的背景色
							if(currentCell!=null){
								CellStyle style = null;
								if (fileName.endsWith(EXTENSION_XLS)) {
									style = (HSSFCellStyle) readwb.createCellStyle();
									style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
									style.setFillForegroundColor(HSSFColor.RED.index);
								} else if (fileName.endsWith(EXTENSION_XLSX)) {
									style = (XSSFCellStyle) readwb.createCellStyle();
									style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
									style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
								}
								currentCell.setCellStyle(style);
							}
						}
					}
					//将文件保存
					FileOutputStream stream = null;
					try {
						stream = new FileOutputStream(errorFilePath);
						readwb.write(stream);
					} catch (IOException e) {
						e.printStackTrace();
					} finally {
						try {
							stream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
        } catch (Exception e) {
        	e.printStackTrace();
        } finally {
        	if (instream != null) {
                try {
                	instream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	private static String getCellValue(Cell cell, boolean treatAsStr) {
        if (cell == null) {
            return "";
        }
        if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
        	if (DateUtil.isCellDateFormatted(cell)) {
        		if(cell.getDateCellValue()!=null && !"".equalsIgnoreCase(cell.getDateCellValue()+"")){
        			Date d = cell.getDateCellValue();
        			DateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
        			return formater.format(d);
        		}
            }
        	cell.setCellType(Cell.CELL_TYPE_STRING);
            return String.valueOf(cell.getStringCellValue()!=null?cell.getStringCellValue():"");
        } else {
        	cell.setCellType(Cell.CELL_TYPE_STRING);
            return String.valueOf(cell.getStringCellValue()!=null?cell.getStringCellValue():"");
        }
    }
	
	public static String getCorrespondingLabel(int columnNo){
	    if(columnNo<1)
	        throw new IllegalArgumentException();
	    String[] sources = new String[]{"A","B","C","D","E","F","G","H","I","J","K","L","M" 
	        ,"N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	    StringBuilder sb = new StringBuilder(5);
	    int remainder = columnNo%26;    //求最右边的字母  
	    if(remainder==0){   //说明(num_n-1)=26，第26个字母是Z  
	        sb.append("Z");
	        remainder = 26; //因为接下来W-(num_n-1)也就是columnNo-remainder,所以需要把remainder赋值回26  
	    }
	    else{  //如果最右边字母不是Z的话，就去sources数组相应的位置取字母，remainder不用变  
	      sb.append(sources[remainder-1]);
	    }  
	    columnNo = (columnNo-remainder)/26-1;   //用来判断接下来是否还有其他字母  
	  
	    //当 当前循环是求最后一个字母时（从右往左），(columnNo-remainder)/26就会是0，再减1也就是-1。  
	    //因此通过判断(columnNo-remainder)/26-1是否大于-1来判断结束  
	    while(columnNo>-1){
	        remainder = columnNo%26;
	        sb.append(sources[remainder]);
	        columnNo = (columnNo-remainder)/26-1;
	    }
	    return sb.reverse().toString(); //因为是从右往左解析的 所以需要反转  
	}
	
	//判断是否是空行
	public static boolean isRowEmpty(Row row) {
	   for (int c = row.getFirstCellNum(); c < row.getLastCellNum(); c++) {
	       Cell cell = row.getCell(c);
	       if (cell != null && cell.getCellType() != Cell.CELL_TYPE_BLANK)
	           return false;
	   }
	   return true;
	}
	
	//根据列排序
	public static List<Map<String,Object>> orderByErrorListForColumn(List<String> columnList,List<Map<String,Object>> errorList) {
		for(int i=0;i<errorList.size();i++){
			for(int j=0;j<columnList.size();j++){
				if(columnList.get(j).equalsIgnoreCase(errorList.get(i).get("field")+"")){
					errorList.get(i).put("columnNo", j);
				}
			}
		}
		Collections.sort(errorList, new Comparator<Map<String, Object>>() {
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                int map1No = (Integer) o1.get("row_number");
                int map2No = (Integer) o2.get("row_number");
                if(map1No != map2No){
                	return map1No - map2No;
                }else{
                	int map1value = (Integer) o1.get("columnNo");
                    int map2value = (Integer) o2.get("columnNo");
                    return map1value - map2value;
                }
                
            }
        });
		return errorList;
	}
}
