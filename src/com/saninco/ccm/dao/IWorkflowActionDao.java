package com.saninco.ccm.dao;

import java.util.List;

import com.saninco.ccm.model.WorkflowAction;

public interface IWorkflowActionDao {

	public abstract void save(WorkflowAction transientInstance);

	public abstract void delete(WorkflowAction persistentInstance);

	public abstract WorkflowAction findById(java.lang.Integer id);

	public abstract List findByExample(WorkflowAction instance);

	public abstract List findByProperty(String propertyName, Object value);

	public abstract List findByWorkflowActionName(Object workflowActionName);

	public abstract List findAll();

	public abstract WorkflowAction merge(WorkflowAction detachedInstance);

	public abstract void attachDirty(WorkflowAction instance);

	public abstract void attachClean(WorkflowAction instance);

	public abstract WorkflowAction load(int i);

}