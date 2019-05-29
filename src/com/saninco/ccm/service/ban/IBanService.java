package com.saninco.ccm.service.ban;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.vo.BanVO;

public interface IBanService {
	
	public String serachAccountNumberRepeat(BanVO bvo)throws BPLException;
	
	public String getBanTotalNO(BanVO bvo)throws BPLException;
	
	public String searchBanList(BanVO bvo)throws BPLException;
	
	public String saveDownloadBanToExcel(BanVO bvo,String excelDirPath,List<String> titles) throws BPLException;
	
	public String deleteBanById(BanVO bvo);
	
	public Ban getBanById(BanVO bvo)throws BPLException;
	
	public String saveBanToBanDetailPage(Ban b);
	
	public String getIsBackupFlag(Ban b) throws BPLException;
	
	public String getContactByVendorId(BanVO bvo)throws BPLException;

	public String doValidationAccountExis(String accNoName)throws BPLException;

	public String getInvoiceChannelAndFormatAndScoaJsonByLineOsBussiness(
			String lineOfBussiness)throws BPLException;

	public String getInvoiceFormatsByLineOsBussinessAndChannel(
			String lineOfBussiness, String invoiceChannel)throws BPLException;
	
	public String getMaxApprovalLevel() throws BPLException;
	
	public void updateBanStatusId(Integer banId,Integer banStatusId,String rejectNotes)throws BPLException;
	
	public void deleteBanById(Integer banId)throws BPLException;
}
