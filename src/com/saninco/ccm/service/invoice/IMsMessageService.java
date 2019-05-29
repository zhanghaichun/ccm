/**
 * 
 */
package com.saninco.ccm.service.invoice;

import com.saninco.ccm.exception.BPLException;


/**
 * @author xinyu.Liu
 * 
 */
public interface IMsMessageService {
	public void saveMsMessage(String privateFlag, String starFlag, String noteUserId, String noteRoleId,String noteNotes,Integer pointId,
			   String noteInvoiceId, Boolean addBanNoteFlag)throws BPLException;
}
