package com.saninco.ccm.target.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GetUploadExcel {
	
	
	public static  List<List<Object>> read2007Excel(File file)
			throws IOException {

		List<List<Object>> list = new LinkedList<List<Object>>();
		// String path = System.getProperty("user.dir") +
		// System.getProperty("file.separator")+"dd.xlsx";
		// System.out.println("·����"+path);
		// ���� XSSFWorkbook ����strPath �����ļ�·��
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));

		// ��ȡ��һ�±������
		XSSFSheet sheet = xwb.getSheetAt(0);
		Object value = null;
		XSSFRow row = null;
		XSSFCell cell = null;
		System.out.println("��ȡoffice 2007 excel�������£�");
		for (int i = sheet.getFirstRowNum(); i <= sheet
				.getPhysicalNumberOfRows(); i++) {
			row = sheet.getRow(i);
			if (row == null) {
				continue;
			}
			List<Object> linked = new LinkedList<Object>();
			for (int j = row.getFirstCellNum(); j <= row.getLastCellNum(); j++) {
				cell = row.getCell(j);
				if (cell == null) {
					continue;
				}
				DecimalFormat df = new DecimalFormat("0");// ��ʽ�� number String
				// �ַ�
				SimpleDateFormat sdf = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");// ��ʽ�������ַ���
				DecimalFormat nf = new DecimalFormat("0.00");// ��ʽ������

				switch (cell.getCellType()) {
				case XSSFCell.CELL_TYPE_STRING:
					// System.out.println(i + "��" + j + " �� is String type");
					value = cell.getStringCellValue();
					System.out.print("  " + value + "  ");
					break;
				case XSSFCell.CELL_TYPE_NUMERIC:
					// System.out.println(i + "��" + j
					// + " �� is Number type ; DateFormt:"
					// + cell.getCellStyle().getDataFormatString());
					if ("@".equals(cell.getCellStyle().getDataFormatString())) {
						value = df.format(cell.getNumericCellValue());

					} else if ("General".equals(cell.getCellStyle()
							.getDataFormatString())) {
						value = nf.format(cell.getNumericCellValue());
					} else {
						value = sdf.format(HSSFDateUtil.getJavaDate(cell
								.getNumericCellValue()));
					}
					System.out.print("  " + value + "  ");
					break;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					// System.out.println(i + "��" + j + " �� is Boolean type");
					value = cell.getBooleanCellValue();
					System.out.print("  " + value + "  ");
					break;
				case XSSFCell.CELL_TYPE_BLANK:
					// System.out.println(i + "��" + j + " �� is Blank type");
					value = "";
					// System.out.println(value);
					break;
				default:
					// System.out.println(i + "��" + j + " �� is default type");
					value = cell.toString();
					System.out.print("  " + value + "  ");
				}
				if (value == null || "".equals(value)) {
					continue;
				}
				linked.add(value);
			}
			System.out.println("");
			list.add(linked);
		}
		return list;
	}

}
