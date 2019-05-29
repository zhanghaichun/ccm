package com.saninco.ccm.model;

import java.math.BigDecimal;
import java.util.Date;

public class TargetResult extends AbstractTargetResult{

	public TargetResult(Integer id, Integer targetId, String period,
			Integer allowedPeriodShift, Double actualAmount,
			Double actualChangeAmount, BigDecimal actualChangePercentage,
			BigDecimal actualVariationPercentage, String resultFlag,
			String remark, String recActiveFlag, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy) {
		super(id, targetId, period, allowedPeriodShift, actualAmount,
				actualChangeAmount, actualChangePercentage, actualVariationPercentage,
				resultFlag, remark, recActiveFlag, createdTimestamp, createdBy,
				modifiedTimestamp, modifiedBy);
		// TODO Auto-generated constructor stub
		
	}

	public TargetResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2209049517369057585L;
	
	
	private Integer order;


	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}
	
	
	
}
