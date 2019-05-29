package com.saninco.ccm.action.operations;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;

import com.saninco.ccm.action.CcmActionSupport;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.service.ap.IApService;

public class ApFeedOutboundAction extends CcmActionSupport {
	
	private static final long serialVersionUID = -6840754523718434776L;
	//AP Feed Out Bound Directory:
    private String apFeedOutboundDir;
	private IApService apService;
	private final Logger logger = Logger.getLogger(this.getClass());
    private int sendRequest = 0;
	
	public ApFeedOutboundAction() {
	}
	
	/**
	 * goto main page
	 * */
	public String exec() throws Exception {
		logger.info("Enter method exec.");
		//sendOutboundFeedToAP();
		logger.info("Exit method exec.");
		return SUCCESS;
		}

	public void sendOutboundFeedToAP() throws Exception{
		
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
			
			if(sendRequest == 1){
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
			}
			
			else{
				logger.info("Did not get the sending request.");
			}
		    
		}catch (BPLException e) {
			logger.error("Business error when processing : " + currentPaymentId, e);
			
		}
		
		catch (IOException e1){
		 
			logger.error("IOException when processing : " + currentPaymentId, e1);
		}
		
		logger.info("Exit method sendOutboundFeedToAP.");
		
	}

	/*
	public String getInvoiceNumber(StringBuffer pds){
		String invoiceNum = new String();
		//get the 3rd element.
		String[] array = pds.toString().split("\\|",-1);
		invoiceNum = array[2];
		return invoiceNum;
	}
	*/
	
	//setSequenceNumber
	
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

	public IApService getApService() {
		return apService;
	}

	public void setApService(IApService apService) {
		this.apService = apService;
	}

	public Logger getLogger() {
		return logger;
	}

	public int getSendRequest() {
		return sendRequest;
	}

	public void setSendRequest(int sendRequest) {
		this.sendRequest = sendRequest;
	}

}
