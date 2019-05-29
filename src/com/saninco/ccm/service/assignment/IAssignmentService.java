package com.saninco.ccm.service.assignment;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.vo.AssignmentVO;
/**
 * 
 * @author wei.su
 *
 */
public interface IAssignmentService {
	
	public String searchAssignment(AssignmentVO assignmentVO) throws BPLException;
	public String getAssignmentSearchTotalPageNo(AssignmentVO assignmentVO) throws BPLException;
	public void save(AssignmentVO assignmentVO) throws RuntimeException;
	public void update(AssignmentVO assignmentVO) throws Exception;
	public void delete(int id) throws RuntimeException;
	public String getNumberOfAssignmentByVendorIdAndBanId(AssignmentVO assignmentVO)throws BPLException;
}
