/**
 * 
 */
package com.saninco.ccm.job;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.ap.IApService;

/**
 * @author Joe.Yang
 *
 */
public class ApMain {
	private final Logger logger = Logger.getLogger(this.getClass());
	private String apFeedInboundDir;
	private String apFeedOutboundDir;
	public IApService apService;
	
	/**
	 * @param apService the apService to set
	 */
	public void setApService(IApService apService) {
		this.apService = apService;
	}
	/**
	 * call apService inbound service method
	 */
	public void invokeInboundApService(){
		//TODO: call apService inbound service method
		//inbound action methods
		

		
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
            int currentFile = -1;
            
            for(int i = 0; i< files.length; i++){
                logger.info("Current file name is: " + files[i].getName());
            	if((files[i].getName().startsWith(inboundFileHeader))&&(files[i].getName().endsWith(".output")))
            		{
            		  currentFile = i;
            		} 
            }
            
            if(currentFile != -1){
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
            
            //int [] statusExceptionIds = apService.getAPExceptionStatus();
            int [] statusExceptionReIds = apService.getAPExceptionStatusRe();
            if(statusExceptionReIds.length!=0){
            	//apService.updatePaymentStatus(statusExceptionIds, 25);
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

            
            //or multiple payments with status 22 (sent to A/P) for the same invoice number. 
            //Must choose one payment to close. For the other payments, just change payment_status_id = 24. 

            int [] multiPayExceptionReIds = apService.getAPExceptionMultiPayRe();
            if(multiPayExceptionReIds.length!=0){
            	apService.updateRemittanceStatus(multiPayExceptionReIds,"2","Multiple Payments Sent to AP for This Invoice");
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
            	logger.info("Can not find any remittance file with current date.");
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
	 * call apService outbound service method
	 */
	public void invokeOutboundApService(){
		
		//TODO: call apService outbound service method
		//outbound action methods
		
		/**
		 * Generate CCM -> Oracle A/P Feed files (Outbound) according to payment related data in the database;
		 * First generate them under local dir.
		 * Then generate them under sftp dir. 
		 * 
		 * Handle exceptions later.
		 * @author Qiao.Yang
		 */
		
		logger.info("Enter method sendOutboundFeedToAP.");
		
		int currentPaymentId = 0;
		
		try {
			
			//Get the outbound directory:
			logger.info("OS name: "+System.getProperty("os.name")); 
			logger.info("OS arch: "+System.getProperty("os.arch")); 
			logger.info("OS version: "+System.getProperty("os.version")); 
            apFeedOutboundDir = apService.getApOutboundFeedDirectory();
            
            /* /ccm/ap/outbound/ in Linux*/
            logger.info("AP Outbound Directory under Linux is:" + apFeedOutboundDir);
            
            /* \ccm\ap\outbound\ in Windows*/
            if (System.getProperty("os.name").toLowerCase().indexOf("Win".toLowerCase()) >= 0 ){
            	
            	String[] array = apFeedOutboundDir.toString().split("/", -1);   
            	apFeedOutboundDir = "C:" + File.separator;
            	for (int i =1; i<array.length-1; i++){
            		apFeedOutboundDir = apFeedOutboundDir +array[i]+File.separator;
            	}
            	apFeedOutboundDir = apFeedOutboundDir +array[array.length-1];
            }
            
              
            logger.info("AP Outbound Directory under Windows is:" + apFeedOutboundDir);
		    //Create the A/P outbound feed directory if it's not existing.		
	        //Create a directory tree.
	        File dirs = new File(apFeedOutboundDir);
            if(!dirs.exists()){
	        dirs.mkdirs();
	        logger.info("apFeedOutboundDir established.");
	        }
            else{
            	logger.info("apFeedOutboundDir already exists.");
            }
				
		//Format file name. File name format: ROGERSTEMS.20100602181530.OUTPUT	
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String outboundFileName = "ROGERSTEMS."+sdf.format(cal.getTime())+".OUTPUT";
		String outboundFINFileName = "ROGERSTEMS."+sdf.format(cal.getTime())+".OUTPUT.FIN";
		
		//Create a new file. Set output stream and print stream.
		File outboundFile = new File(apFeedOutboundDir, outboundFileName);
		FileOutputStream out; 
        PrintStream p;
        out = new FileOutputStream(outboundFile);
        p = new PrintStream(out);

		//Write A/P outbound feed content to the file 
		List<String> listOfHeaders = null;
		String sTrailer = new String();
		List<String> listOfDetails = null;
		
		StringBuffer pipeDelimitedHeader = new StringBuffer();
		StringBuffer pipeDelimitedDetail = new StringBuffer();
		int numOfDetails = 0;
		int numOfHeaders = 0;
		
			//At payment level:
			//Get a list of header records.
			listOfHeaders= apService.getListOfHeaders();
			numOfHeaders = listOfHeaders.size();
			int[] readyPaymentIds = new int[numOfHeaders];
			readyPaymentIds = apService.getAPPaymentIds(20);
			
			//After getting the header records, create a payment_batch record
			//Set batch_status = 1 (Created) in the batch
			//Set created_timestamp = cal.getTime()
			//Set created_by = 0
			//Commit
			
			apService.createPaymentBatch();	
			
			//The ap_header_view also select the payment_ids of selected payments
			//Every payment belongs to the batch just created
			//Select the last created batch, max(created_timestamp). Get the batch id
			//New method here: get the latest created payment_batch id: int pbid = getAPBatchId()
			
		    int pbid = apService.getAPBatchId();

			//New method here: get the selected payments from ap_header_view: int[] apPaymentIds = getAPPaymentIds()
			//Update the payments one by one: using the batch id (loop)
			//Payment p(n) = paymentDao.findById(apPaymentIds(n))
			//Update payment_batch_id = pbid
			//Set modified_timestamp = cal.getTime()
			//Set modified_by = 0
			//Commit
			
		    apService.updateAPBatchId(pbid);
		    
		    
			//Payment_Batch pb = paymentBatchDao.findById(id)
			//Update the payment_batch record pb
			//set batch_status =2 (Transactions collected)
			//Set modified_timestamp = cal.getTime()
			//Set modified_by = 0
			//Commit
				
		    apService.updateAPBatchStatus("2", pbid);
		    
		    //Sending to AP:
		    apService.updatePaymentStatus(20, 21);
		
			for(int i = 0; i<numOfHeaders; i++){
				
				//Actions at payment level.
				
				//Clear StringBuffer and write the i th header.
				
				int length = pipeDelimitedHeader.length();
				pipeDelimitedHeader.delete(0,length);
				logger.info("Writing following Header Record:");
				logger.info(listOfHeaders.get(i));
				pipeDelimitedHeader.append(listOfHeaders.get(i));
				p.println(pipeDelimitedHeader);

				//Get current_payment_id. Use it to search related paymentdetails and getListofDetails().				
				currentPaymentId = readyPaymentIds[i];
				
				//Get related payment details.
				listOfDetails = apService.getListOfDetails(currentPaymentId);

				//Get related payment detail ids.
				int[] paymentDetailIds = apService.getPaymentDetailIds(currentPaymentId);

				
				//Print out payment details lines for each payment one by one.
				numOfDetails = listOfDetails.size();
				for (int j =0; j<numOfDetails; j++){
				   //get the jth Detail:
					int detailLength = pipeDelimitedDetail.length();
					pipeDelimitedDetail.delete(0,detailLength);
					logger.info("Writing following Detail Record:");
					logger.info(listOfDetails.get(j));
					pipeDelimitedDetail.append(listOfDetails.get(j));
				   
					//Add Sequence Number to the 4th element and print:
				    p.println(setSequenceNumber(pipeDelimitedDetail, j));
					
				   
				    //Update this sequence number back to the payment_details.
				    //Within the loop.
				    apService.updateLineNumber(paymentDetailIds[j], j+1);
				   
				}
			}

			//At the very end, write the trailer for all payments to the A/P file.
			sTrailer = apService.getTrailer();	
			p.print(sTrailer);
			p.close();
			File outboundFINFile = new File(apFeedOutboundDir, outboundFINFileName);
		    outboundFINFile.createNewFile();
		    
		    
			//Put this txt file under sftp dir (already);
			
			//After generating the file,
			//Update the payment_batch record pb
			//set batch_status =3 (File created)
			//Set modified_timestamp = sdf.format(cal.getTime())
			//Set modified_by = 0
			//Commit
					
		    apService.updateAPBatchStatus("3", pbid);
			
			//For each payments sent, update status.
			//Update the payments one by one: using the batch id (loop)
			//Payment p(n) = paymentDao.findById(apPaymentIds(n))
			//Update payment_status_id = 22 (Sent to AP)
			//Set modified_timestamp = sdf.format(cal.getTime())
			//Set modified_by = 0
			//Commit
			
		    apService.updatePaymentStatus(21, 22);
		    
			//After payments all sent to AP,
			//Update the payment_batch record pb
			//set batch_status =4 (File sent)
			//Set modified_timestamp = sdf.format(cal.getTime())
			//Set modified_by = 0
			//Commit	
		    
		    apService.updateAPBatchStatus("4", pbid);

		}catch (BPLException e) {
			logger.error("Business error when processing : " + currentPaymentId, e);
			
		}
		
		catch (IOException e1){
		 
			logger.error("IOException when processing : " + currentPaymentId, e1);
		}
		
		logger.info("Exit method sendOutboundFeedToAP.");
		
		
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{
		"applicationContext-master.xml", "applicationContext-dao.xml",
		"applicationContext-hibernate.xml", "applicationContext-jdbc.xml"
		});
		ApMain apMain = (ApMain)ctx.getBean("apMain");
		apMain.invokeInboundApService();
		apMain.invokeOutboundApService();
	}
	
	
	public StringBuffer setSequenceNumber(StringBuffer pds, int sequenceNumber){
		StringBuffer result = new StringBuffer();
		//get the 4th element.
		String[] array = pds.toString().split("\\|",-1);
		array[3] = Integer.toString(sequenceNumber + 1);
		int length = array.length;
		for (int i =0; i< length - 1; i++){
			result.append(array[i]);
			result.append("|");
		}
		result.append(array[length-1]);
		return result;
	}
	
	
    public String getApFeedOutboundDir() {
		return apFeedOutboundDir;
	}

	public void setApFeedOutboundDir(String apFeedOutboundDir) {
		this.apFeedOutboundDir = apFeedOutboundDir;
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

	public Logger getLogger() {
		return logger;
	}

}
