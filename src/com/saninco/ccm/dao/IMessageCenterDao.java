package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.vo.SearchMessageCenterVO;
import com.saninco.ccm.vo.SearchVendorInventoryVO;

public interface IMessageCenterDao {
	public List<Object[]> getMessageReferenceList();
	public List<Object[]> getMessageCreatedByList();
	public void updatMessageFavoriteFlag(int id,String favoriteFlag,int userId);
	public long getNumberOfMessageCenter(SearchMessageCenterVO sv, int userId);
	public List<Object[]> searchMessageCenter(SearchMessageCenterVO sv, int userId);
	public List<Object[]> getMessageCenterDataForExcel(SearchMessageCenterVO sv, int userId);
	
}
