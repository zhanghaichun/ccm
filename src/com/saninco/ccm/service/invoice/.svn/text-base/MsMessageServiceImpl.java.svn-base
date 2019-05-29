package com.saninco.ccm.service.invoice;

import java.util.List;

import com.saninco.ccm.dao.DaoUtil;
import com.saninco.ccm.dao.IIvoiceNoteDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.util.SystemUtil;

public class MsMessageServiceImpl{
	
	public String saveMsMessage(IIvoiceNoteDao invoiceNoteDao,String privateFlag, String starFlag, String noteUserId, String noteRoleId,String noteNotes,Integer pointId,
							   String noteInvoiceId, Boolean addBanNoteFlag)
			throws BPLException {
		
		//noteNotes = DaoUtil.ccmEscape2(noteNotes);
		String msChannelId = "";
		String filterUserIds = "";
		String filterUserNames = "";
		String msChannelTypeId = "";
		String msMessageId = "";
		String msReferenceId = "";
		String msReferenceTypeId = "";
		String msReferenceNumber = "";
		boolean atMyselfFlag = false;
		
		Integer invoiceId=Integer.parseInt(noteInvoiceId);
		Invoice invoice=invoiceNoteDao.invoice(invoiceId);
		
		String userId = SystemUtil.getCurrentUserId().toString();
		
		atMyselfFlag = noteUserId.contains(userId);
		
		if("".equals(noteUserId) || noteUserId == null){
			noteUserId = userId;
		}else{
			noteUserId = userId+","+noteUserId;
		}
		
		
		if(addBanNoteFlag){
			msReferenceId = invoice.getBan().getId().toString();
			msReferenceNumber = invoice.getBan().getAccountNumber();
			msReferenceTypeId = "2";
		}else{
			msReferenceId = invoice.getId().toString();
			msReferenceNumber = invoice.getInvoiceNumber();
			msReferenceTypeId = "1";
		}
		
		List<Object[]> notifyUserList = invoiceNoteDao.queryNotifyUsers(noteUserId, noteRoleId);
		
		// verification channel type
		if("on".equals(privateFlag)){
			msChannelTypeId = "3";
		}else if(notifyUserList.size() == 1 && atMyselfFlag){
			msChannelTypeId = "3";
		}else if(notifyUserList.size() == 2){
			msChannelTypeId = "1";
		}else if(notifyUserList.size()> 2){
			msChannelTypeId = "2";
		}
		
		if(msChannelTypeId != null && !"".equals(msChannelTypeId)){
			for(Object[] itemUser : notifyUserList){
				filterUserIds = filterUserIds + itemUser[0]+",";
				filterUserNames = filterUserNames + itemUser[1]+",";
			}
			if(!"".equals(filterUserIds)){
				filterUserIds = filterUserIds.substring(0,filterUserIds.lastIndexOf(","));
			}
			if(!"".equals(filterUserIds)){
				filterUserNames = filterUserNames.substring(0,filterUserNames.lastIndexOf(","));
			}
			
			List<Integer> channelIdList = invoiceNoteDao.queryMsChannelId(filterUserIds);
			
			// verification channel id
			if(channelIdList.size()>0){
				msChannelId = channelIdList.get(0).toString();
			}else{
				msChannelId = invoiceNoteDao.createMsChannel(msChannelTypeId,filterUserIds,filterUserNames,userId);
				for(Object[] itemUser : notifyUserList){
					invoiceNoteDao.createMsChannelUser(msChannelId,itemUser[0].toString(),userId);
				}
				
			}
			msMessageId = invoiceNoteDao.saveMsMessage(msChannelId,privateFlag, noteUserId, noteRoleId,
													   noteNotes,pointId,msReferenceId,msReferenceNumber,msReferenceTypeId);
			
			if("Y".equals(starFlag)){
				invoiceNoteDao.saveMsFavorite(userId,msMessageId);
			}
			invoiceNoteDao.updateLatestMsMessageId(msChannelId,msMessageId);
			invoiceNoteDao.updateUnreadFlag(msChannelId);
		}
		return msMessageId+","+msChannelId;
	}
	
}
