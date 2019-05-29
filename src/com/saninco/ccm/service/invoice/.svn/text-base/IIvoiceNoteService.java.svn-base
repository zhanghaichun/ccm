package com.saninco.ccm.service.invoice;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.vo.InvoiceNoteVO;

public interface IIvoiceNoteService {
	String getInvoiceNoteTotalPageNo(InvoiceNoteVO invoiceNoteVO)throws BPLException ;
	
	String searchInvoiceNote(InvoiceNoteVO invoiceNoteVO) throws BPLException;
	
	String doGetUserAndRoleUri() throws BPLException;
	
	String saveInvoiceNoteAndMsMessage(String noteInvoiceId,String noteBanId,String noteNotes,AttachmentPoint point , Boolean addBanNoteFlag,
									   String privateFlag, String starFlag, String noteUserId, String noteRoleId)throws BPLException;
	
}
