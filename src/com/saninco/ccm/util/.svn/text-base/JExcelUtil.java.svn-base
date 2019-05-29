package com.saninco.ccm.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.write.DateFormat;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;



/**
 * xinyu.Liu
 * 
 */
public class JExcelUtil {
	
	private List<String> itemTypeList = new ArrayList<String>();
	private List<String> doubleFormatList = new ArrayList<String>(
			Arrays.asList("Payment","Dispute","Credit","EO Direct Quantity","Quantity","Number of Calls","Mileage",
					"Payment Amount","Dispute Amount","Credit Amount","Amount","Outstanding Amount","Reconciliation Amount","Unit Cost",
					"Expected MRC","Usage","Tax and Other Fees","LPC","MRC","OCC","Adjustment","Current Due","Face Page Difference",
					"Prievious Balance","Previous Payment","Balance Forward","Total Due","Misc Adjustment","Monthly Rate","Charge","Item Amount",
					"Net Charge","AMOUNT","pre_tax_amount","Invoice Amount","Outstanding Dispute Amount","Outstanding Dispute Balance",
					"Billing Amount","Discount","Base Amount"));
	
	/*
	"Discount", "Multiplier", "Base Amount", "Quantity Begin", "Quantity End"
	 */
	private List<String> doubleList = new ArrayList<String>(
			Arrays.asList("Rate","Minutes","Average Duration","Hold Min.","Usage Min.","UPC","ASR","Billing Rate","Rate Multiplier","Actual Rate","Expect Rate"));
	
	private List<String> intList = new ArrayList<String>(
			Arrays.asList("Seized","Answered","Count"));

	// Excel 表格中的 Title 是如下的内容， 列中的值就是有对齐的。
	private List<String> alignRightList = new ArrayList<String>( Arrays.asList("Discount", "Multiplier", "Base Amount", "Qty Begin", "Qty End","Actual","Expect","Sum of Actual","Sum of Expect","Invoice Date") );
	
	private WritableWorkbook writableWorkbook = null;
	private WritableSheet[] writableSheets = new WritableSheet[50];
	private WritableSheet writableSheet = null;
	private WritableCellFormat wcf12 = new WritableCellFormat(new jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD, false));
	private WritableCellFormat wcf11 = new WritableCellFormat(new jxl.write.NumberFormat("0.00"));
	private WritableCellFormat wcf13 = new WritableCellFormat(new jxl.write.NumberFormat("0.00000"));
	private WritableCellFormat wcf14 = new WritableCellFormat(NumberFormats.INTEGER);
	private WritableCellFormat wcf10 = new WritableCellFormat(new jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false));
	private WritableCellFormat wcfDate = new WritableCellFormat(new DateFormat("yyyy-MM-dd HH:mm"));

	private WritableCellFormat wcfAlignRight = new WritableCellFormat(
													new jxl.write.WritableFont(WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false));

	
	public JExcelUtil() throws WriteException {
		super();
		wcf12.setAlignment(Alignment.LEFT);
		wcfDate.setAlignment(Alignment.LEFT);
		wcf10.setAlignment(Alignment.LEFT);

		// 初始化数据的方式为右对齐
		wcfAlignRight.setAlignment(Alignment.RIGHT);
	}
	///1
	public void createWritableWorkbook(String excelDirPath) throws IOException {
		writableWorkbook = Workbook.createWorkbook(new File(excelDirPath));
	}
	///2
	public void createWritableSheet(int index,String sheetName) {
		WritableSheet ws = writableWorkbook.createSheet(sheetName, index);
		writableSheets[index] = ws;
		if(this.writableSheet == null){
			this.writableSheet = ws;
		}
	}
	//3
	public void setWritableSheet(int index) {
		this.writableSheet = writableSheets[index];
	}
	///3
	public void writeLine(int lineIndex1, Object[] objs) throws RowsExceededException, WriteException {
		Label labelC = null;
		jxl.write.Number labelNumber = null;
		for(int i = 0; i < objs.length; i++){
			
			if(objs[i] == null){
				labelC = new Label(i, lineIndex1, "");
				labelC.setCellFormat(wcf10);
				writableSheet.addCell(labelC);
			}else{
				
				String value = objs[i].toString();

				if(objs[i] instanceof Date || "Date".equals(itemTypeList.get(i))){

					String ds = "";
					try {
						ds = CcmFormat.format((Date)objs[i],"yyyy-MM-dd HH:mm");
					} catch (RuntimeException e) {
						//e.printStackTrace();
					}
					labelC = new Label(i, lineIndex1,ds,wcfDate);
					writableSheet.addCell(labelC);

				}else if("Double".equals(itemTypeList.get(i))){

					if("".equals(value)){
						labelNumber = new jxl.write.Number(i, lineIndex1, Double.valueOf("0.00"),wcf11);
					}else{
						labelNumber = new jxl.write.Number(i, lineIndex1, Double.valueOf(value.replace(",", "")),wcf13);
					}
					writableSheet.addCell(labelNumber);

				}else if((objs[i] instanceof Double) || "DoubleFormat".equals(itemTypeList.get(i))){

					if("".equals(value)){
						labelNumber = new jxl.write.Number(i, lineIndex1, Double.valueOf("0.00"),wcf11);
					}else{
						labelNumber = new jxl.write.Number(i, lineIndex1, Double.valueOf(value.replace(",", "")),wcf11);
					}
					writableSheet.addCell(labelNumber);

				}else if((objs[i] instanceof Number)){
					
					if("".equals(value)){
						labelNumber = new jxl.write.Number(i, lineIndex1, Double.valueOf("0.00"),wcf11);
					}else{
						labelNumber = new jxl.write.Number(i, lineIndex1, Double.valueOf(value.replace(",", "")),wcf11);
					}
					writableSheet.addCell(labelNumber);
//					labelNumber = new jxl.write.Number(i, lineIndex1, ((Number)objs[i]).doubleValue(),wcf11);
//					writableSheet.addCell(labelNumber);
//					
				}else if("Intger".equals(itemTypeList.get(i))){

					if("".equals(value)){
						labelNumber = new jxl.write.Number(i, lineIndex1, 0,wcf14);
					}else{
						labelNumber = new jxl.write.Number(i, lineIndex1, Integer.valueOf(value),wcf14);
					}
					writableSheet.addCell(labelNumber);

				} else if ("alignRight".equals(itemTypeList.get(i))){

					labelC = new Label(i, lineIndex1, value, wcfAlignRight);
					writableSheet.addCell(labelC);
				}
				else{
					labelC = new Label(i, lineIndex1, value.replace("</br>", "\r\n"), wcf10);
					writableSheet.addCell(labelC);
				} 
			}
		} 
		writableSheet.setRowView(lineIndex1, 400);
	}

	/**
	 * 首先excel表格中的每一列都会有个列头
	 * 该方法将根据列头判断记录的类型
	 * 在writeLine 方法中判断该如何显示这列数据。
	 */
	public void writeTitle(int lineIndex, List<String> titles) throws RowsExceededException, WriteException, IOException {
		itemTypeList = new ArrayList<String>();
		for(int i = 0; i < titles.size(); i++){
			
			String itemTitle = titles.get(i);
			
			Label labelC = new Label(i, lineIndex, itemTitle);
			labelC.setCellFormat(wcf12);
			writableSheet.addCell(labelC);
			
			if(doubleFormatList.contains(itemTitle)){
				itemTypeList.add("DoubleFormat");
			}
			else if(doubleList.contains(itemTitle)) {
				itemTypeList.add("Double");
			}
			else if(intList.contains(itemTitle)) {
				itemTypeList.add("Intger");
			}
			else if ( alignRightList.contains(itemTitle) ) { // Align right items.
				itemTypeList.add("alignRight");
			}
			else{
				itemTypeList.add("String");
			}
			
		}
		
		writableSheet.setRowView(lineIndex, 455);
		
	}
	///4
	public void setColumnView(int[] cols) throws IOException{
		for(int i = 0; i < cols.length; i++){ 
			writableSheet.setColumnView(i, cols[i]);
		}
	}
	///4
	public void write() throws IOException{
		writableWorkbook.write();
	}
	///5
	public void close() throws WriteException, IOException{
		writableWorkbook.close();
	}
	///6
	public void setProtected() {
		writableSheet.setProtected(true);
	}
	
	public void removeFieldName(List<String> titles) {
		for(int i = 0; i < titles.size(); i++){
			if("".equals(titles.get(i)) || "Delete".equals(titles.get(i)) || "Edit".equals(titles.get(i)) || "Attachment".equals(titles.get(i))){
				titles.remove(i);
				i--;
			}
		}
	}
	
	
}