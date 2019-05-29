package com.saninco.ccm.dao;

import java.util.List;
import java.util.Map;

import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceNotes;
import com.saninco.ccm.model.UserQuicklink;
import com.saninco.ccm.vo.InvoiceNoteVO;

public interface IIvoiceNoteDao {
	long getInvoiceNoteTotalPageNo(InvoiceNoteVO invoiceNoteVO,
			Integer currentUserId);
	
	List<String> searchInvoiceNote(InvoiceNoteVO invoiceNoteVO,
			Integer currentUserId);

	List<Object[]> doGetUserUri();
	List<Object[]> doGetRoleUri();
	
	public abstract void saveInvoiceNote(InvoiceNotes invoiceNote);
	
	public abstract String saveMsMessage(String msChannelId , String privateFlag,
										 String noteUserId, String noteRoleId,String noteNotes,
										 Integer pointId,String msReferenceId,String msReferenceNumber,String msReferenceTypeId);
	
	public List<Object[]> queryNotifyUsers(String noteUserId, String noteRoleId);
	
	public List<Integer> queryMsChannelId(String filterUserId);
	
	public String createMsChannel(String msChannelTypeId,String filterUserIds,String filterUserNames,String currentUserId);
	
	public void createMsChannelUser(String msChannelId,String userId,String currentUserId);
	
	public void saveMsFavorite(String userId,String msMessageId);
	
	public void updateLatestMsMessageId(String msChannelId,String msMessageId);
	public void updateUnreadFlag(String msChannelId);
	
	Invoice invoice(Integer invoice_id);
	
	Ban ban(Integer ban_id);
}
