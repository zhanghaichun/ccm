package com.saninco.ccm.circuitold.dao;

import java.util.List;

import com.saninco.ccm.circuitold.model.SearchCircuitVO;


public interface ICircuitDao {
	public List<Object[]> searchCircuit(SearchCircuitVO sio, int userId);
	public long getNumberOfCircuits(SearchCircuitVO sio, int userId);
	public List<Object[]> getCircuitDataForExcel(SearchCircuitVO svo, int userId);
	public List<String> getTabs(SearchCircuitVO svo,int userId);
	public List<String> getCircuitDateHyperlink(SearchCircuitVO svo,int userId);
}
