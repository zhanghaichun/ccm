/**
 * 
 */
package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.AttachmentFile;
import com.saninco.ccm.model.AttachmentPoint;
import com.saninco.ccm.vo.SearchCreditVO;
import com.saninco.ccm.vo.SearchInvoiceVO;


/**
 * @author Jian.Dong
 */
public interface IAttachmentFileDao {

	public Integer createPoint(AttachmentPoint ap);

	public AttachmentPoint loadPoint(Integer apid);

	public void save(AttachmentFile af);
	
	public long getAttachmentPointIdCount(SearchInvoiceVO svo);

	public List<String> searchAttachmentPointId(SearchInvoiceVO svo);
	public List searchAttachmentPointIdList(SearchInvoiceVO svo);
	
	
	
	public long getDisputeAttachmentPointIdCountDao(SearchInvoiceVO svo);

	public List<String> getDisputeSearchAttachmentPointIdDao(SearchInvoiceVO svo);
	
	public void delete(AttachmentFile persistentInstance);
	
	public AttachmentFile findById(java.lang.Integer id);
	
	public AttachmentFile merge(AttachmentFile detachedInstance);
	
	public List<AttachmentFile> findAllByAttchmentPiontId(java.lang.Integer id);
	
	public List<AttachmentFile> findAllByAttchmentPiontIdPiontId(java.lang.Integer id);
	
	public void deleteAttchmentPiont(Integer attachmentPointId);
	
}
