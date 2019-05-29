package com.saninco.ccm.model;

import java.math.BigDecimal;
import java.util.Date;

public class Target extends AbstractTarget{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7178195338406809511L;

	public Target() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Target(Integer id, Integer vendorId, Integer banId,
			String strippedCircuitNumber, Integer chargeTypeId,
			Integer periodFrom, Integer periodTo, Integer allowedPeriodShift,
			Double targetAmount, Double changeAmount,
			BigDecimal changePercentage, BigDecimal allowedVariationPercentage,
			String source, String recActiveFlag, Date createdTimestamp,
			Integer createdBy, Date modifiedTimestamp, Integer modifiedBy) {
		super(id, vendorId, banId, strippedCircuitNumber, chargeTypeId, periodFrom,
				periodTo, allowedPeriodShift, targetAmount, changeAmount,
				changePercentage, allowedVariationPercentage, source, recActiveFlag,
				createdTimestamp, createdBy, modifiedTimestamp, modifiedBy);
		// TODO Auto-generated constructor stub
	}

}
