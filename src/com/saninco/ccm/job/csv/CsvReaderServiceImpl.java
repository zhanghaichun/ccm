package com.saninco.ccm.job.csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.LineNumberReader;
import java.nio.channels.FileChannel;

import org.apache.poi.util.SystemOutLogger;

public class CsvReaderServiceImpl implements CsvReaderService {
	public static String folderDir = "E:/CCM";
	
	public static void main(String[] args) {
		CsvReaderServiceImpl  cs=new CsvReaderServiceImpl();
		cs.csvReader();
	}

	public void csvReader() {
		 try {
	            BufferedReader reader = new BufferedReader(new FileReader("C:/Account_Summary.CSV"));//For your name
	            BufferedReader readerActivity = new BufferedReader(new FileReader("C:/Activity.CSV"));
	            BufferedReader readerPayments = new BufferedReader(new FileReader("C:/Adjustments_and_Payments.CSV"));
	            BufferedReader readerUsage = new BufferedReader(new FileReader("C:/Call Usage.csv"));
	            BufferedReader readerSubscriberAccount = new BufferedReader(new FileReader("C:/charge_by_SubscriberAccount.CSV"));
	            BufferedReader readerDirectory = new BufferedReader(new FileReader("C:/Directory_Advertising.CSV"));
	            BufferedReader readerEquipment = new BufferedReader(new FileReader("C:/Equipment Reccuring.csv"));
	            BufferedReader readerInternet = new BufferedReader(new FileReader("C:/Internet_and_messaging_usage.CSV"));
	            BufferedReader readerOCCOTC = new BufferedReader(new FileReader("C:/OCCOTC.csv"));
	            BufferedReader readerSummary = new BufferedReader(new FileReader("C:/Summary of Charge.csv"));
	            BufferedReader readerToll = new BufferedReader(new FileReader("C:/Toll_Plan.CSV"));
	            reader.readLine();//The first line of information, information, not for headlines, if needed, commented
	            String line = null;
	            CsvReaderServiceImpl ccmFolder = new CsvReaderServiceImpl();  
	            ccmFolder.newFolder(folderDir);              
	            while((line=reader.readLine())!=null){
	                String item[] = line.split(",");//CSV format file descriptors for comma-separated files, according to a comma here           
	                String last = item[item.length-item.length];//This is your data
	                last = last.replaceAll("\"", ""); //The data will be in double quotes
	                CsvReaderServiceImpl mulutest = new CsvReaderServiceImpl();  
	                mulutest.newFolder(""+folderDir+"/"+last+" "); //Establish folder                  
	                CsvReaderServiceImpl cw = new CsvReaderServiceImpl();                  
	                
	                String csvFile = ""+folderDir+"/"+last+"/Account_Summary.CSV"; 
	               // String writeStringAccount=reader.readLine();
	                cw.createCSV(csvFile);
	                
	                String csvFile1 = ""+folderDir+"/"+last+"/Activity.CSV"; 
	                cw.createCSV(csvFile1);
	               // String csvOldera="C:/Activity.CSV";
	               // copyCSV(csvOldera,csvFile1);
	                

	                
	                String csvFile2 = ""+folderDir+"/"+last+"/Adjustments_and_Payments.CSV";
	               // String writeStringAdjustments=readerPayments.readLine();
	                cw.createCSV(csvFile2);
	                
	                String csvFile3 = ""+folderDir+"/"+last+"/Call Usage.csv"; 
	                //String writeStringCall=readerUsage.readLine();
	                cw.createCSV(csvFile3);
	                
	                String csvFile4 = ""+folderDir+"/"+last+"/charge_by_SubscriberAccount.CSV"; 
	                //String writeStringcharge=readerSubscriberAccount.readLine();
	                cw.createCSV(csvFile4);
	                
	                String csvFile5 = ""+folderDir+"/"+last+"/Directory_Advertising.CSV"; 
	                //String writeStringDirectory=readerDirectory.readLine();
	                cw.createCSV(csvFile5);
	                
	                String csvFile6 = ""+folderDir+"/"+last+"/Equipment Reccuring.csv"; 
	                //String writeStringEquipment=readerEquipment.readLine();
	                cw.createCSV(csvFile6);
	                
	                String csvFile7 = ""+folderDir+"/"+last+"/Internet_and_messaging_usage.CSV"; 
	               // String writeStringInternet=readerInternet.readLine();
	                cw.createCSV(csvFile7);
	                
	                String csvFile8 = ""+folderDir+"/"+last+"/OCCOTC.csv"; 
	                //String writeStringOCCOTC=readerOCCOTC.readLine();
	                cw.createCSV(csvFile8);
	                
	                String csvFile9 = ""+folderDir+"/"+last+"/Summary of Charge.csv"; 
	               // String writeStringSummary=readerSummary.readLine();
	                cw.createCSV(csvFile9);
	                
	                String csvFile10 = ""+folderDir+"/"+last+"/Toll_Plan.CSV"; 
	               // String writeStringToll_Plan=readerToll.readLine();
	                cw.createCSV(csvFile10);
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }

	}
	
	/**
	 * Create folders
	 *  CsvReader mulutest = new CsvReader();  
                mulutest.newFolder("E:\\CCM\\"+last+" ");   
	 * */
	public void newFolder(String folderPath)
	{
	  String filePath= folderPath;
	  filePath =filePath.toString();   
	  java.io.File myFilePath =new java.io.File(filePath);
	  try{
	   if(myFilePath.isDirectory()){
	    //System.out.println("the directory is exists!");
	   }
	   else{
	    myFilePath.mkdir();
	   // System.out.println("New catalogue");
	   }
	  }
	  catch   (Exception   e){
	  // System.out.println("New directory operating errors");
	   e.printStackTrace();
	  }
	} 	
	
	/**  
     * Create CSV files
     * @param csvFile csv
     * */  
    public void createCSV(String csvFile){   
        FileWriter fw = null;   
        try{   
            fw = new FileWriter(csvFile);
            //fw.write("ID,CODE,NAME,EMAIL,TEL\r\n");  
            //fw.write(writeString+"\r\n");
               
            fw.flush();   
            fw.close();   
        } catch (IOException e){   
            e.printStackTrace();   
        }finally{   
            if(null != fw){   
                try{   
                    fw.close();   
                } catch (IOException e){   
                    e.printStackTrace();   
                }   
            }   
        }             
    }   
    /**  
     * Copy CSV files
     * @param source The source file  
     * @param dest   Target files  
     * */  
    public void copyCSV(String source, String dest){   
        try{   
            FileChannel in = new FileInputStream(source).getChannel();   
            FileChannel out = new FileOutputStream(dest).getChannel();                 
          in.transferTo(0, in.size(), out);   
            out.transferFrom(in, 0, in.size());   
               
            in.close();   
            out.close();   
        } catch (Exception e){   
            e.printStackTrace();   
        }   
    }
    
    public void writeCsv(String yourCSVPath,String yourCSVInsertData ){
    	try{
    		FileReader r=new FileReader(yourCSVPath);
    		BufferedReader i=new BufferedReader(r);
    		String s   ;
    		StringBuffer sb=new StringBuffer();
    		while((s=i.readLine())!=null){
    		sb.append(s);
    		sb.append( "\r\n");
    		}
    		i.close();
    		FileWriter w=new FileWriter(yourCSVPath);
    		BufferedWriter o=new BufferedWriter(w);
    		o.write(sb.toString());
    		o.write(yourCSVInsertData);
    		o.close();

    		}catch(Exception   e){System.out.println(e.toString());}
    }
}
