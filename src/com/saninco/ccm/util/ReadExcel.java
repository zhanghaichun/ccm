package com.saninco.ccm.util;
import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.BuiltinFormats;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.model.StylesTable;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
  
/** 
 * XSSF and SAX (Event API) basic example. 
 * See {@link XLSX2CSV} for a fuller example of doing 
 *  XSLX processing with the XSSF Event code. 
 */  
public class ReadExcel {  
      
        public List<Map<String,String>> dataList = new ArrayList<Map<String,String>>();  
        private final int startRow = 1;  
        private final int endRow = 50000;  
        private int currentRow = 0;  
        private final File file;  
        private Map<String,String> rowData;  
        private List<String> columnList = new ArrayList<String>();
        private String regEx="[^0-9]";
        private StylesTable stylesTable;
        private String formatString;
        private DataFormatter formatter;
        private short formatIndex;
        private Map<String,String> columnMap = new HashMap<String,String>();
        public ReadExcel(File file) throws Exception{  
            this.file = file;  
            processFirstSheet();
        }  
        /** 
         * 指定获取第一个sheet 
         * @param filename 
         * @throws Exception 
         */  
        private void processFirstSheet() throws Exception {  
            OPCPackage pkg = OPCPackage.open(file);  
            XSSFReader r = new XSSFReader( pkg );  
            stylesTable = r.getStylesTable();
            SharedStringsTable sst = r.getSharedStringsTable();  
  
            XMLReader parser = fetchSheetParser(sst);  
  
            // To look up the Sheet Name / Sheet Order / rID,  
            //  you need to process the core Workbook stream.  
            // Normally it's of the form rId# or rSheet#  
            InputStream sheet1 = r.getSheet("rId1");  
            InputSource sheetSource = new InputSource(sheet1);  
            parser.parse(sheetSource);  
            sheet1.close();  
        }  
          
        private void processAllSheet() throws Exception {  
        	OPCPackage pkg = OPCPackage.open(file);  
            XSSFReader r = new XSSFReader( pkg );  
            SharedStringsTable sst = r.getSharedStringsTable();  
              
            XMLReader parser = fetchSheetParser(sst);  
      
            Iterator<InputStream> sheets = r.getSheetsData();  
            while(sheets.hasNext()) {  
                System.out.println("Processing new sheet:\n");  
                InputStream sheet = sheets.next();  
                InputSource sheetSource = new InputSource(sheet);  
                parser.parse(sheetSource);  
                sheet.close();  
                System.out.println("");  
            }
        }
        private XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {  
            XMLReader parser =  
                XMLReaderFactory.createXMLReader(  
                        "org.apache.xerces.parsers.SAXParser"  
                );  
            ContentHandler handler = new PagingHandler(sst);  
            parser.setContentHandler(handler);  
            return parser;  
        }  
  
        /**  
         * See org.xml.sax.helpers.DefaultHandler javadocs  
         */  
        private  class PagingHandler extends DefaultHandler {  
            private SharedStringsTable sst;  
            private String lastContents;  
            private boolean nextIsString;  
            private boolean nextIsDate;  
            private String index = null;  
              
            private PagingHandler(SharedStringsTable sst) {  
                this.sst = sst;  
            }  
            /** 
             * 每个单元格开始时的处理 
             */  
            @Override  
            public void startElement(String uri, String localName, String name,  
                    Attributes attributes) throws SAXException {  
                // c => cell  
                if(name.equals("c")) {  
                    // Print the cell reference  
//                  System.out.print(attributes.getValue("r") + " - ");  
                      
                    index = attributes.getValue("r");  
                    Pattern p = Pattern.compile(regEx);   
                    Matcher m = p.matcher(index);
                    //这是一个新行  
                    if(Integer.valueOf(m.replaceAll("").trim()) != currentRow){  
                          
                        //存储上一行数据  
                        if(rowData!=null&&isAccess()&&!rowData.isEmpty()){  
                            dataList.add(rowData);  
                        }  
                        rowData = new HashMap<String,String>();//新行要先清除上一行的数据  
                        currentRow++;//当前行+1  
                    }  
                    if(isAccess()){  
                    	
                    	nextIsString = false;  
                        nextIsDate = false;
                    	
                        // Figure out if the value is an index in the SST  
                        String cellType = attributes.getValue("t");  
                        if("s".equals(cellType) ) {  
                            nextIsString = true;  
                        }else if("b".equals(cellType) ) {  
                        	nextIsString = false;  
                            nextIsDate = false;
                        }else if("str".equals(cellType) ) {  
                        	nextIsString = false;  
                            nextIsDate = false;
                        }else {
                        	nextIsDate = nextIsDate(attributes);
                        } 
                        
                    }  
                  
                }  
                
                // Clear contents cache  
                lastContents = "";  
            }  
            
            
            /**
             * 处理数据类型
             * 
             * @param attributes
             */
            public boolean nextIsDate(Attributes attributes) {
            	formatter = new DataFormatter();
            	formatIndex = -1;
                formatString = null;
                String cellStyleStr = attributes.getValue("s");

                // 判断是日期类型.则格式化.
                if(cellStyleStr != null){
//                if ("1".equals(cellStyleStr) || "3".equals(cellStyleStr) || "6".equals(cellStyleStr) ) {
                    int styleIndex = Integer.parseInt(cellStyleStr);
                    XSSFCellStyle style = stylesTable.getStyleAt(styleIndex);
                    formatIndex = style.getDataFormat();
                    formatString = style.getDataFormatString();

                    if ("m/d/yy" == formatString) {
                        formatString = "mm/dd/yyyy";
                    }

                    if (formatString == null) {
                        formatString = BuiltinFormats.getBuiltinFormat(formatIndex);
                    }
                    return true;
                }else{
                	return false;
                }
            }
//            
            
            /** 
             * 每个单元格结束时的处理 
             */  
            @Override  
            public void endElement(String uri, String localName, String name)  
                    throws SAXException {
                if(isAccess()){
                    // Process the last contents as required.  
                    // Do now, as characters() may be called more than once  
                    if(nextIsString) {
                        int idx = Integer.parseInt(lastContents);
                        lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
                        nextIsString = false;
                    }
  
                    // v => contents of a cell  
                    // Output after we've seen the string contents  
                    if(name.equals("v")) {
                       if(currentRow == 1){
                    	   columnList.add(lastContents);
                    	   columnMap.put(index.replaceAll("\\d+",""), lastContents);
                       }else{
                    	   if(nextIsDate){
                    		   lastContents = getDateValue(lastContents.trim(), ""); 
                    	   }
                		   rowData.put(columnMap.get(index.replaceAll("\\d+","")), lastContents);
                       }
                    }
                }
            }
            
            @Override  
            public void characters(char[] ch, int start, int length)  
                    throws SAXException {  
                if(isAccess()){  
                    lastContents += new String(ch, start, length);  
                }  
                  
            }  
            /** 
             * 如果文档结束后，发现读取的末尾行正处在当前行中，存储下这行 
             * （存在这样一种情况，当待读取的末尾行正好是文档最后一行时，最后一行无法存到集合中， 
             * 因为最后一行没有下一行了，所以不为启动starElement()方法， 
             * 当然我们可以通过指定最大列来处理，但不想那么做，扩展性不好） 
             */  
            @Override  
            public void endDocument ()throws SAXException{  
                if(rowData!=null&&isAccess()&&!rowData.isEmpty()){  
                    dataList.add(rowData);  
                }  
                    
            }  
          
        }
        
        public String getDateValue(String value, String thisStr)
        {
        	if(formatString.toUpperCase().indexOf("YY")>=0 && formatString.toUpperCase().indexOf("M")>=0 && formatString.toUpperCase().indexOf("D")>=0){
        		thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, "m/d/yyyy");
        	}else{
	        	thisStr = formatter.formatRawCellContents(Double.parseDouble(value), formatIndex, formatString);
        	}
        	
            // 对日期字符串作特殊处理
            thisStr = thisStr.replace(" ", "T");
            return thisStr;
        }
        private boolean isAccess(){  
            if(currentRow>=startRow&&startRow<=endRow){  
                return true;  
            }  
            return false;  
        }
        /** 
         * 获取真实的数据（处理空格） 
         * @return 
         * @throws Exception  
         */  
        public Map<String,Object> getMyDataList() throws Exception{  
              
        	Map<String,Object> myData = new HashMap<String,Object>();
            myData.put("columnList", columnList);
            myData.put("dataList", dataList);
            return myData;  
        }  
        
        public static void main(String[] args) throws Exception {  
              
//        	ReadExcel reader = new ReadExcel(new File("D://Master Circuit Inventory (16).xlsx"),1,72);  
//        	ReadExcel reader = new ReadExcel(new File("C://Users/Administrator/Desktop/tems/rate update/20190219/Contract Rate Template.xlsx"));  
        	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-mm-dd");
        	SimpleDateFormat simpleDateFormat1=new SimpleDateFormat("m/d/yyyy");
        	Date date=simpleDateFormat.parse("2018-05-18");
        	System.out.println(simpleDateFormat1.format(simpleDateFormat.parse("yyyy-mm-dd")));
            System.out.println(simpleDateFormat1.format(date));
//            System.out.println("\n---"+reader.getMyDataList());  
        }  
}
