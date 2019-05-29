package com.saninco.ccm.target.dao;

import java.util.List;

import com.saninco.ccm.model.Target;
import com.saninco.ccm.model.TargetChargeType;
import com.saninco.ccm.model.TargetResult;
import com.saninco.ccm.target.model.SearchTargetVO;

public interface ITargetDao {

	List<String> searchTarget(SearchTargetVO searchTargetVO,
			Integer currentUserId);

	List getCircuitList(Integer currentUserId, Integer banId);

	List<TargetChargeType> chargeTypeList();

	long getTragetSearchTotalPageNo(SearchTargetVO searchTargetVO,
			Integer currentUserId);

	List<Object[]> searchTargetByObject(SearchTargetVO searchTargetVO,
			Integer currentUserId,List<Integer> targetIds);

	Target saveTarget(Target target,int type);
	
	Target deleteTargetResult(Target target,int type);
	
	public void save(Object o);

	List<String> queryCopyTargetList(List<Target> targetList);

	Double getInvoiceItemAmountByTarget(Target target, String string,
			String string2);

	List<TargetResult> queryTarResultListByTargetId(Integer id);

	public void updateObject(Object o);

	void deleteTargetResultByTargetId(Integer id);

	String queryTargetById(Integer id);

	List<Object[]> searchTargetResultByObject(List<Integer> targetIds,
			Integer currentUserId);

	Target findById(Integer id);

	TargetResult findTargetResultByTargetIdAndPeriod(Integer id, String string);
	
	
	public List findTargetPeriod(Target target,int totleMonth);
	
	String seacherUsernameByUserId(Integer id);
	
	Double getInvoiceAmountByTarget(Target target, String string,
			String string2);
}
