/**
 * 
 */
package com.saninco.ccm.util;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

public class JExcelFileUtil {
    private Sheet writableSheet = null;
    private Workbook readwb = null;
    private String fileName = null;
    private CellStyle dateCellStyle = null;
    private CellStyle doubleCellStyle = null;
    private CellStyle double5CellStyle = null;
    private CellStyle cellStyle = null;
    private CellStyle titleCellStyle = null;
    private SimpleDateFormat inSdf = new SimpleDateFormat("yyyy-mm-dd");
    private SimpleDateFormat outSdf = new SimpleDateFormat("m/d/yyyy");
    private List<String> itemTypeList = new ArrayList<String>();
    private int minWidth = 5000;
    private List<String> doubleList = new ArrayList<String>(
			Arrays.asList("Multiplier","Payment","Dispute","Credit","EO Direct Quantity","Quantity","Number of Calls","Mileage",
					"Payment Amount","Dispute Amount","Credit Amount","Amount","Outstanding Amount","Reconciliation Amount","Unit Cost",
					"Expected MRC","Usage","Tax and Other Fees","LPC","MRC","OCC","Adjustment","Current Due","Face Page Difference",
					"Prievious Balance","Previous Payment","Balance Forward","Total Due","Misc Adjustment","Monthly Rate","Charge","Item Amount",
					"Net Charge","AMOUNT","pre_tax_amount","Invoice Amount","Outstanding Dispute Amount","Outstanding Dispute Balance","Billing Amount",
					"Discount","Base Amount"));
    private List<String> double5List = new ArrayList<String>(
    		Arrays.asList("Rate","Billing Rate","Rate Multiplier","Minutes"));
    private List<String> dateList = new ArrayList<String>(
			Arrays.asList("Invoice Date","Revenue Match Date","Install Date","First Invoice Date","Disconnection Date","Last Signoff Date",
					"Rate Effective Date","Expiry Date","Modified Date","Circuit Contract Start Date","Tariff Effective Date","Effective Date"));
	public void createExcelFile(String fileName,List<String> columnList,String sheetName){
		InputStream instream = null;
		try    
        {
			readwb = new SXSSFWorkbook(100);
			this.fileName = fileName;
			if(readwb!=null){
				itemTypeList = new ArrayList<String>();
				writableSheet = readwb.createSheet(sheetName != null ? sheetName : "Master Inventory");
				doubleCellStyle = readwb.createCellStyle();
				cellStyle = readwb.createCellStyle();
				dateCellStyle = readwb.createCellStyle();
				double5CellStyle = readwb.createCellStyle();
				CreationHelper createHelper = readwb.getCreationHelper();
				dateCellStyle.setDataFormat( createHelper.createDataFormat().getFormat("m/d/yyyy"));
				Font font = readwb.createFont();
				font.setFontHeightInPoints((short) 10);
				font.setFontName("ARIAL");
		        cellStyle.setFont(font);
		        doubleCellStyle.setFont(font);
		        double5CellStyle.setFont(font);
		        dateCellStyle.setFont(font);
		        DataFormat df = readwb.createDataFormat();
		        doubleCellStyle.setDataFormat(df.getFormat("0.00"));
		        double5CellStyle.setDataFormat(df.getFormat("0.00000"));
				Row firstRow = writableSheet.createRow(0);
				
				titleCellStyle = readwb.createCellStyle();
				Font headerFont = readwb.createFont();
		        headerFont.setFontHeightInPoints((short) 10);
		        headerFont.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
		        headerFont.setFontName("ARIAL");
		        titleCellStyle.setFont(headerFont);
				
				for(int i=0;i<columnList.size();i++){
					Cell columnNumberCell = firstRow.createCell(i);
					columnNumberCell.setCellValue(columnList.get(i));
					columnNumberCell.setCellStyle(titleCellStyle);
					writableSheet.setDefaultRowHeight((short) (350));
					writableSheet.setColumnWidth(i, minWidth > columnList.get(i).getBytes().length*2*256 ? minWidth : columnList.get(i).getBytes().length*2*256);
					if(doubleList.contains(columnList.get(i))){
						itemTypeList.add("Double");
					}else if(double5List.contains(columnList.get(i))){
						itemTypeList.add("Double5");
					}else if(dateList.contains(columnList.get(i))){
						itemTypeList.add("Date");
					}else{
						itemTypeList.add("String");
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
	
	
	public void writeLine(int lineIndex1, Object[] objs) throws RowsExceededException, WriteException {
		Row row = writableSheet.createRow(lineIndex1);
		if(row!=null){
			for(int j=0;j<objs.length;j++){
				if(objs[j] != null && !"".equals(objs[j])){
					Cell currentCell = row.createCell(j);
					String value = objs[j].toString();
					if(itemTypeList.get(j).equals("Double") && !value.equals("")){
//						DataFormat df = readwb.createDataFormat();
//						CellStyle cellStyle = readwb.createCellStyle();
//						cellStyle.setDataFormat(df.getFormat("0.00"));
//						currentCell.setCellType(Cell.CELL_TYPE_NUMERIC);
//						currentCell.setCellStyle(cellStyle);
						try{
							currentCell.setCellValue(Double.valueOf(value.replace(",", "")));
							currentCell.setCellStyle(doubleCellStyle);
						} catch (NumberFormatException e) {
							currentCell.setCellValue(value);
							currentCell.setCellStyle(doubleCellStyle);
						}
						
					}else if (itemTypeList.get(j).equals("Double5") && !value.equals("")) {
						try{
							currentCell.setCellValue(Double.valueOf(value.replace(",", "")));
							currentCell.setCellStyle(double5CellStyle);
						} catch (NumberFormatException e) {
							currentCell.setCellValue(value);
							currentCell.setCellStyle(double5CellStyle);
						}
					}else if(itemTypeList.get(j).equals("Date") && !value.equals("")){
						try {
							currentCell.setCellValue(outSdf.format(inSdf.parse(value)));
							currentCell.setCellStyle(dateCellStyle);
							
						} catch (ParseException e) {
							currentCell.setCellValue(value);
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						currentCell.setCellValue(value);
						currentCell.setCellStyle(cellStyle);
					}
				}
			}
		}
	}
	
	public void writeLine(int lineIndex1, Map<String, String> data,List<String> columnList,Map<String, String> attribute) throws RowsExceededException, WriteException {
		Row row = writableSheet.createRow(lineIndex1);
		if(row!=null){
			for(int i=0;i<columnList.size();i++){
				if(columnList.get(i) != null && !"".equals(columnList.get(i))){
					Cell currentCell = row.createCell(i);
					String column = columnList.get(i).toString();
					String value = data.get(column) == null ? "" : data.get(column);
					if(value!=null && itemTypeList.get(i).equals("Double") && !value.equals("")){
						try{
							currentCell.setCellValue(Double.valueOf(value.replace(",", "")));
							currentCell.setCellStyle(cellStyle);
						} catch (NumberFormatException e) {
							currentCell.setCellValue(value);
							currentCell.setCellStyle(cellStyle);
						}
					}else if(value!=null && itemTypeList.get(i).equals("Date") && !value.equals("")){
						try {
							CellStyle cellStyle = readwb.createCellStyle();
							CreationHelper createHelper = readwb.getCreationHelper();
//							cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yyyy"));
							currentCell.setCellValue(outSdf.format(inSdf.parse(value)));
							currentCell.setCellStyle(dateCellStyle);
						} catch (ParseException e) {
							currentCell.setCellValue(value);
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}else{
						currentCell.setCellValue(value);
						currentCell.setCellStyle(cellStyle);
					}
					if(attribute!=null && attribute.get(column.toUpperCase())!=null && !"".equals(attribute.get(column.toUpperCase()))){
						CellStyle style = null;
						style = (XSSFCellStyle) readwb.createCellStyle();
						style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
						style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
						currentCell.setCellStyle(style);
					}
				}
			}
		}
	}
	
	public void createErrorSheet(List<Map<String,Object>> errorList,List<String> columnList){
		writableSheet = readwb.createSheet("Error");
		Row firstRow = writableSheet.createRow(0);
		Cell columnNumberCell = firstRow.createCell(0);
		columnNumberCell.setCellValue("Error Column");
		Cell rowNumberCell = firstRow.createCell(1);
		rowNumberCell.setCellValue("Row Number");
		Cell errorMessageCell = firstRow.createCell(2);
		errorMessageCell.setCellValue("Error Message");
		int maxLengthError = 0;
		if(errorList.size()>=ExcelFileUtil.MAX_DATA_LENGTH){
			Row lastRow = writableSheet.createRow(1);
			Cell columnNumberErrorCell = lastRow.createCell(0);
			columnNumberErrorCell.setCellValue("");
			Cell rowNumberErrorCell = lastRow.createCell(1);
			rowNumberErrorCell.setCellValue("");
			Cell errorMessageErrorCell = lastRow.createCell(2);
			errorMessageErrorCell.setCellValue("The maximum number of record displayed is 10,000.");
			CellStyle style = null;
			style = (XSSFCellStyle) readwb.createCellStyle();
			style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
			errorMessageErrorCell.setCellStyle(style);
			maxLengthError = 1;
		}
		for(int i=0;i<errorList.size();i++){
			Row row = writableSheet.createRow(i + maxLengthError + 1);
			if(row!=null){
				Cell columnNumberErrorCell = row.createCell(0);
				String columnNo = "";
				for(int j=0;j<columnList.size();j++){
					if(columnList.get(j).equalsIgnoreCase(errorList.get(i).get("field")+"")){
						columnNo = ExcelFileUtil.getCorrespondingLabel(j+1);
						break;
					}
				}
				columnNumberErrorCell.setCellValue(columnNo);
				Cell rowNumberErrorCell = row.createCell(1);
				rowNumberErrorCell.setCellValue(Integer.valueOf(errorList.get(i).get("row_number")+"")+1);
				Cell errorMessageErrorCell = row.createCell(2);
				errorMessageErrorCell.setCellValue(errorList.get(i).get("note")+"");
			}
		}
		
	}
	
	public void setDropDownBox(int firstRow, int lastRow,int firstCol,int lastCol,String sheetName,int endNo){
		DataValidationHelper dvHelper = null;
	    DataValidationConstraint dvConstraint = null;
	    DataValidation validation = null;
	    CellRangeAddressList addressList = null;
	    
	    dvHelper = writableSheet.getDataValidationHelper();
	    dvConstraint = dvHelper.createFormulaListConstraint("'"+sheetName+"'!$A$2:$A$"+endNo);
	    addressList = new CellRangeAddressList(firstRow,lastRow,firstCol,lastCol);
	    validation = dvHelper.createValidation(
	                        dvConstraint, addressList);
	    writableSheet.addValidationData(validation);
    }
	
	public void createNewSheet(String sheetName){
		readwb.createSheet(sheetName);
	}
	
	public void createSheetTitle(List<String> columnList){
		Row firstRow = writableSheet.createRow(0);
		for(int i=0;i<columnList.size();i++){
			Cell columnNumberCell = firstRow.createCell(i);
			columnNumberCell.setCellValue(columnList.get(i));
			columnNumberCell.setCellStyle(titleCellStyle);
			writableSheet.setDefaultRowHeight((short) (350));
			writableSheet.setColumnWidth(i, minWidth > columnList.get(i).getBytes().length*2*256 ? minWidth : columnList.get(i).getBytes().length*2*256);
		}
	}
	
	public void setSheet(int index){
		writableSheet = readwb.getSheetAt(index);
	}
	
	public void createSheetColumn(){
		
	}
	
	public void write() throws IOException{
		//将文件保存
		FileOutputStream stream = null;
		try {
			stream = new FileOutputStream(fileName);
			readwb.write(stream);
			((SXSSFWorkbook) readwb).dispose();
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
