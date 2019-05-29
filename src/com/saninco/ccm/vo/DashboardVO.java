/**
 * 
 */
package com.saninco.ccm.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.saninco.ccm.util.SystemUtil;

/**
 * @author Chao.Liu
 */
public class DashboardVO extends SearchVO implements Serializable {
		
	private String dashboardPosition;
	private List<Integer[]> sortList; 
	
	public DashboardVO() { }
	
	
	public List<Integer[]> getSortList() {
		List<Integer[]> sl = new ArrayList<Integer[]>();
		if(this.getDashboardPosition() == null){ return sl; }
		String[] strs = this.getDashboardPosition().split("#");
		
		Integer[] is = null;
		int cx = 0;
		int row = 0;
		for(int i=0; i<strs.length; i++){
			String[] str = strs[i].split(":");
			str[0] = str[0].substring(str[0].lastIndexOf("_")+1,str[0].indexOf("D"));
			
			cx = i==0?new Integer(str[1]):cx;
			
			is = new Integer[2];
			is[0] = new Integer(str[0]);
			Integer x = new Integer(str[1]);
			if((x+50) > cx && (x-50) < cx){
				is[1] = row;
			}else{
				cx = x;
				row++;
				
				is[1] = row;
			}
			
			sl.add(is);
		}
		
		return sl;
	}

	public String getDashboardPosition() {
		return dashboardPosition;
	}
	public void setDashboardPosition(String dashboardPosition) {
		this.dashboardPosition = dashboardPosition;
	}
	
	
}
