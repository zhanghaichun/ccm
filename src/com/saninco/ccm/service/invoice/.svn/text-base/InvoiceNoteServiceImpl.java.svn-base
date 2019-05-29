package com.saninco.ccm.service.invoice;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import com.saninco.ccm.dao.DaoUtil;
import com.saninco.ccm.dao.IIvoiceNoteDao;
import com.saninco.ccm.dao.IUserDao;
import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Invoice;
import com.saninco.ccm.model.InvoiceNotes;
import com.saninco.ccm.model.User;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.InvoiceNoteVO;

public class InvoiceNoteServiceImpl extends MsMessageServiceImpl implements IIvoiceNoteService {
	IIvoiceNoteDao invoiceNoteDao;
	IUserDao userDao;

    

	public IIvoiceNoteDao getInvoiceNoteDao() {
		return invoiceNoteDao;
	}



	public void setInvoiceNoteDao(IIvoiceNoteDao invoiceNoteDao) {
		this.invoiceNoteDao = invoiceNoteDao;
	}
	



	public IUserDao getUserDao() {
		return userDao;
	}



	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}



	public String getInvoiceNoteTotalPageNo(InvoiceNoteVO invoiceNoteVO)
			throws BPLException {
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = invoiceNoteDao.getInvoiceNoteTotalPageNo(invoiceNoteVO,
					SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		// Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count
				/ (double) invoiceNoteVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");

		return sb.toString();
	}



	public String searchInvoiceNote(InvoiceNoteVO invoiceNoteVO)
			throws BPLException {
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = invoiceNoteDao.searchInvoiceNote(invoiceNoteVO, SystemUtil
					.getCurrentUserId());
		} catch (RuntimeException e) {
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(invoiceNoteVO.getStartIndex() + 1); 
			sb.append(",end:");
			int size = l.size();
			sb.append(invoiceNoteVO.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		return sb.toString();
	}
	
	public String doGetUserAndRoleUri()
	throws BPLException {
		StringBuffer sb = new StringBuffer();
		List<Object[]> userList = null;
		List<Object[]> roleList = null;
		try {
			userList = invoiceNoteDao.doGetUserUri();
			roleList = invoiceNoteDao.doGetRoleUri();
		} catch (RuntimeException e) {
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		
		sb.append("{userList:[");
		for (int i = 0; i < userList.size(); i++) {
			Object[] str = userList.get(i);
			sb.append("{\"id\":\""+str[0]+"\",");
			sb.append("\"name\":\""+str[1]+"\"},");
		}
		sb.append("]");
		
		sb.append(",roleList:[");
		for (int i = 0; i < roleList.size(); i++) {
			Object[] str = roleList.get(i);
			sb.append("{\"id\":\""+str[0]+"\",");
			sb.append("\"name\":\""+str[1]+"\"},");
		}
		sb.append("]");
		sb.append("}");
		return sb.toString();
	}
	
	private String getBlobContent(Blob b) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedInputStream bis = new BufferedInputStream(b
					.getBinaryStream());
			int len = (int) b.length();
			byte[] bt = new byte[len];
			int readLen = 0;
			while ((readLen = bis.read(bt)) != -1) {
				sb.append(new String(bt, 0, readLen));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public String saveInvoiceNoteAndMsMessage(String noteInvoiceId,String noteBanId,String noteNotes,AttachmentPoint point , Boolean addBanNoteFlag,
			   								  String privateFlag, String starFlag, String noteUserId, String noteRoleId)throws BPLException {
		
		String resultMessage = "";
		saveInvoiceNote(noteInvoiceId,noteBanId,noteNotes,point,addBanNoteFlag,privateFlag);
//		noteNotes = DaoUtil.ccmEscape(noteNotes);
		if("on".equals(privateFlag) || !"".equals(noteUserId) || !"".equals(noteRoleId)){
			Integer pointId = point != null ? point.getId() : null;
			resultMessage = super.saveMsMessage(invoiceNoteDao,privateFlag, starFlag, noteUserId, noteRoleId,noteNotes,pointId,noteInvoiceId,addBanNoteFlag);
		}
		
		return resultMessage;
	}
	
	private void saveInvoiceNote(String noteInvoiceId,String noteBanId,String noteNotes,AttachmentPoint point , Boolean addBanNoteFlag,String privateFlag)
			throws BPLException {
		InvoiceNotes invoiceNote =new InvoiceNotes();
		int userId = SystemUtil.getCurrentUserId();
		User user = userDao.getUser(userId);
		invoiceNote.setUser(user);
		invoiceNote.setCreatedTimestamp(new Date());
		invoiceNote.setCreatedBy(userId);
		invoiceNote.setModifiedBy(userId);
		invoiceNote.setModifiedTimestamp(new Date());
		invoiceNote.setRec_active_flag("Y");
		if("on".equals(privateFlag)){
			invoiceNote.setPrivateFlag("Y");
		}else{
			invoiceNote.setPrivateFlag("N");
		}
		if(point!= null)
		invoiceNote.setAttachment_point_id(point.getId());
		if(addBanNoteFlag){
			Integer banId=Integer.parseInt(noteBanId);
			Ban ban=invoiceNoteDao.ban(banId);
			invoiceNote.setBan(ban);
		}else{
			Integer invoiceId=Integer.parseInt(noteInvoiceId);
			Invoice invoice=invoiceNoteDao.invoice(invoiceId);
			invoiceNote.setInvoice(invoice);
		}
//		noteNotes=noteNotes.replaceAll("\"", "\\\\u0022");
		noteNotes=DaoUtil.ccmEscape(noteNotes);
		invoiceNote.setNotes(noteNotes.trim());
		this.invoiceNoteDao.saveInvoiceNote(invoiceNote);
	}

}
