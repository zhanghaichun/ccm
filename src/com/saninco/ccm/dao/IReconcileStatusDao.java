/**
 * 
 */
package com.saninco.ccm.dao;

import com.saninco.ccm.model.ReconcileStatus;

/**
 * @author Joe.Yang
 *
 */
public interface IReconcileStatusDao{
	public ReconcileStatus findById(java.lang.Integer id);

	public ReconcileStatus load(int i);
}
