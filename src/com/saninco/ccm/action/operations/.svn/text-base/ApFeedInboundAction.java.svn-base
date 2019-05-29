/**
 * 
 */
package com.saninco.ccm.action.operations;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.ap.IApService;
import org.apache.log4j.Logger;

/**
 * @author Qiao
 *
 */
public class ApFeedInboundAction extends CcmActionSupport  {

	private static final long serialVersionUID = -8543607489568483743L;
	//AP Feed In Bound Directory:
    private String apFeedInboundDir;
	private IApService apService;
	private final Logger logger = Logger.getLogger(this.getClass());
	private int loadRequest = 0;
	
	public ApFeedInboundAction() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * goto main page
	 * */
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		//loadAPRemittance();
		logger.info("Exit method exec.");
		return SUCCESS;
	}

	public void loadAPRemittance() throws Exception{
		
		
		
		/**
		 * Load Oracle A/P -> CCM remittance files (Inbound) to payment related tables in the database;
		 * First download them to server dir.
		 * Then load them into db. 
		 * 
		 * Handle exceptions later.
		 * @author Qiao.Yang
		 */
		
		logger.info("Enter method loadAPRemittance.");
		
		try {
			
			if(loadRequest == 1){
			//Get the outbound directory:
			logger.info("OS name: "+System.getProperty("os.name")); 
			logger.info("OS arch: "+System.getProperty("os.arch")); 
			logger.info("OS version: "+System.getProperty("os.version")); 
		    apFeedInboundDir = apService.getApInboundFeedDirectory();
            
            /* /ccm/ap/outbound/ in Linux*/
		    logger.info("AP Inbound Directory under Linux is:" + apFeedInboundDir);
            
            /* \ccm\ap\outbound\ in Windows*/
            if (System.getProperty("os.name").toLowerCase().indexOf("Win".toLowerCase()) >= 0 ){
            	
            	String[] array = apFeedInboundDir.toString().split("/", -1);   
            	apFeedInboundDir = "C:" + File.separator;
            	for (int i =1; i<array.length-1; i++){
            		apFeedInboundDir = apFeedInboundDir +array[i]+File.separator;
            	}
            	apFeedInboundDir = apFeedInboundDir +array[array.length-1];
            }
            
              
            logger.info("AP Inbound Directory under Windows is:" + apFeedInboundDir);
            
		    //The directory should already be existing
            //The file should already be there
            
            //Find the latest file. Open a read stream
            // Open the file that is the first 
            // command line parameter
           
            // The list of files can also be retrieved as File objects
          
    		Calendar cal = Calendar.getInstance();
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    		String inboundFileHeader = "APINT48_REM_"+sdf.format(cal.getTime());
    		logger.info("Inbound file header is:" + inboundFileHeader);
    		
            File dir = new File(apFeedInboundDir);
            File[] files = dir.listFiles();
            int currentFile = 0;
            
            for(int i = 0; i< files.length; i++){
                logger.info("Current file name is: " + files[i].getName());
            	if((files[i].getName().startsWith(inboundFileHeader))&&(files[i].getName().endsWith(".output")))
            		{
            		  currentFile = i;
            		}
            }
            
            
            logger.info("Inbound file name is:" + files[currentFile].getName());
            
            FileInputStream fstream = new FileInputStream(files[currentFile]);

            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            int numberOfRemittance = 0;
            //Read File Line By Line
            while ((strLine = br.readLine()) != null)   {
              // Print the content on the console
              logger.info("Loading remittance line: " + strLine);
              //updateRemittance(String remittanceRow)
              if(strLine.startsWith("D"))
              {
                  //Start from line 2
                  //To the end of the D lines
                  //updateRemittance(String[] remittanceRow)
                  //Update remittance status to "Loaded", 1
          		  String[] remittanceRow = strLine.split("\\|",-1);
          		  //remittanceRow[1],[2],[3],[4],[5]
          		  //remittance_status = 1,status_timestamp,create and modified timestamp,created and modified by
          		  //rec_active_flag       		  
            	  apService.updateRemittance(remittanceRow);
            	  numberOfRemittance = numberOfRemittance + 1;
              }
            }
            //Close the input stream
            in.close();

            
	        //Load the remittance file like this:
            /*
            H|15|||
            D|0001720001AU10|133331|210870409|287.62|20100831
            D|0001522601AU10|133331|210870409|86.99|20100831
            D|0001526901AU10|133331|210870409|64.02|20100831
            D|0001521801AU10|133331|210870409|69.74|20100831
            D|0001526701AU10|133331|210870409|42.72|20100831
            D|00037181703247675AU10|101712|210870840|67.72|20100831
            D|707111963104AU10|130885|210870715|98.75|20100831
            D|707111960100AU10|130885|210870715|89.37|20100831
            D|707111969101AU10|130885|210870715|71.76|20100831
            D|707111962106AU10|130885|210870715|97.1|20100831
            D|707111959102AU10|130885|210870715|77.26|20100831
            D|707111964102AU10|130885|210870715|124.99|20100831
            D|707111961108AU10|130885|210870715|122.31|20100831
            D|707111958104AU10|130885|210870715|75.04|20100831
            D|707220508105AU10|130885|210870715|95.66|20100831
            T|15|||
            */
            

            //Get the list of exceptions, throw them out using logger.error

            
            //(i.invoice_number ! = r.invoice_number) 
            
            int [] invoiceExceptionIds = apService.getAPExceptionInvoice();     
            if(invoiceExceptionIds.length!=0){
            		apService.updateRemittanceStatus(invoiceExceptionIds,"2", "Invoice Number Does Not Match");
            }
            
            //p.payment_stauts_id ! = 22 when r.remittance_status = "1"
            
            int [] statusExceptionIds = apService.getAPExceptionStatus();
            int [] statusExceptionReIds = apService.getAPExceptionStatusRe();
            if(statusExceptionIds.length!=0){
            	apService.updatePaymentStatus(statusExceptionIds, 25);
            	apService.updateRemittanceStatus(statusExceptionReIds,"2","Existing Payment Status Does Not Match");
            }
           
            //or (p.amount !  = r.amount)  
            //updatePaymentStatus(int[] pids, int currentStatus, int newStatus)
            //updatePaymentStatus(pids, 22, 25), 25 is AP Exception
            //updateRemittanceStatus(1,2), to "AP Exception" 2
            //updateRemittanceDesc("Paid Amount Does Not Match");

            int [] amountExceptionIds = apService.getAPExceptionAmount();
            int [] amountExceptionReIds = apService.getAPExceptionAmountRe();
            if(amountExceptionIds.length!=0){
            	apService.updatePaymentStatus(amountExceptionIds, 25);
            	apService.updateRemittanceStatus(amountExceptionReIds,"2","Paid Amount Does Not Match");
            }
            
            //or (b.ap_supplier_number !  = r.ap_supplier_number)  
            int [] supplierExceptionIds = apService.getAPExceptionSupplier();  
            int [] supplierExceptionReIds = apService.getAPExceptionSupplierRe(); 
            if(supplierExceptionIds.length!=0){       	
            	apService.updatePaymentStatus(supplierExceptionIds, 25);
            	apService.updateRemittanceStatus(supplierExceptionReIds,"2","Supplier Number Does Not Match");
            }

            
            //Get the remaining validated payment ids
              
            
            //public int[] getAPConfirmations()     
           
            int[] confirmedPayments = apService.getAPConfirmations();
            
            //public void updatePaymentStatus(int[] pids, int newStatus)
            
            apService.updatePaymentStatus(confirmedPayments,23);
            
            //To "AP Confirmed: 3"
            //public void updateRemittanceStatus (String oldStatus, String newStatus)
            apService.updateRemittanceStatus("1","3");
            
                    
            //Get the checkNumber, store it back in payment.payment_reference_code, updatePaymentReference
            
            //public void updateCheckNumbers(int[] confirmedPayments)
            apService.updateCheckNumbers(confirmedPayments);
            
            //To "Closed: 4"
            apService.updateRemittanceStatus("3","4");
				
			}
			else {
				logger.info("Did not get the loading request.");
			}
		}catch (BPLException e) {
			logger.error("Business error: ", e);
			
		}
		catch (IOException e1){
		 
			logger.error("IOException: ", e1);
		}
		catch (ParseException e2){
		 
			logger.error("ParseException: ", e2);
		}
		
		
		logger.info("Exit method loadAPRemittance.");

	}


	/**
	 * @return the apFeedInboundDir
	 */
	public String getApFeedInboundDir() {
		return apFeedInboundDir;
	}


	/**
	 * @param apFeedInboundDir the apFeedInboundDir to set
	 */
	public void setApFeedInboundDir(String apFeedInboundDir) {
		this.apFeedInboundDir = apFeedInboundDir;
	}


	/**
	 * @return the apService
	 */
	public IApService getApService() {
		return apService;
	}


	/**
	 * @param apService the apService to set
	 */
	public void setApService(IApService apService) {
		this.apService = apService;
	}
	public int getLoadRequest() {
		return loadRequest;
	}
	public void setLoadRequest(int loadRequest) {
		this.loadRequest = loadRequest;
	}

}
