package com.saninco.ccm.model;

import java.util.Date;

import com.saninco.ccm.model.AbstractAuditMemory;

public class AuditMemory extends AbstractAuditMemory implements java.io.Serializable {

	
	public AuditMemory() {
	}
	
	public AuditMemory(Integer banId,String chargeType,String circuitNumber,
			String strippedCircuitNumber,String usoc,String itemName,String description,String lineItemCode,Double rate,Integer productId,
			Integer productComponentId,Integer provinceId,Date createdTimestamp,Integer createdBy,Date modifiedTimestamp,Integer modifiedBy,String recActiveFlag) {
		
		super(banId,chargeType,circuitNumber,strippedCircuitNumber,
				usoc,itemName,description,lineItemCode,rate,
				productId,productComponentId,provinceId,createdTimestamp,createdBy,modifiedTimestamp,modifiedBy,recActiveFlag);
	}
}
