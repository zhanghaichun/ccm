package com.saninco.ccm.service.message;

import java.util.List;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.MapEntryVO;
import com.saninco.ccm.vo.SearchMessageCenterVO;

public interface IMessageCenterService {

	public List<MapEntryVO<String, String>> getMessageReferenceList() throws BPLException;
	public List<MapEntryVO<String, String>> getMessageCreatedByList() throws BPLException;
	public String getMessageCenterSearchTotalPageNo(SearchMessageCenterVO sv) throws BPLException;
	public String searchMessageCenter(SearchMessageCenterVO sv) throws BPLException;
	public void updatMessageFavoriteFlag(int id,String favoriteFlag) throws BPLException;
	public String createMessageCenterToExcel(SearchMessageCenterVO sv,String excelDirPath, List<String> titles) throws BPLException;
}
