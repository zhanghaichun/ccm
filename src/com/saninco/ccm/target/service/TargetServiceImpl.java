package com.saninco.ccm.target.service;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts2.ServletActionContext;

import com.saninco.ccm.exception.BPLException;
import com.saninco.ccm.model.Ban;
import com.saninco.ccm.model.Target;
import com.saninco.ccm.model.TargetChargeType;
import com.saninco.ccm.model.TargetResult;
import com.saninco.ccm.model.Vendor;
import com.saninco.ccm.target.dao.ITargetDao;
import com.saninco.ccm.target.dao.ITargetDao2;
import com.saninco.ccm.target.model.SearchTargetVO;
import com.saninco.ccm.util.CcmFormat;
import com.saninco.ccm.util.ErrorCodeConstants;
import com.saninco.ccm.util.JExcelUtil;
import com.saninco.ccm.util.ListSortUtil;
import com.saninco.ccm.util.SystemUtil;
import com.saninco.ccm.vo.MapEntryVO;

public class TargetServiceImpl implements ITargetService {
	ITargetDao targetDao;
	ITargetDao2 targetDao2;

	public ITargetDao2 getTargetDao2() {
		return targetDao2;
	}

	public void setTargetDao2(ITargetDao2 targetDao2) {
		this.targetDao2 = targetDao2;
	}

	public String searchTarget(SearchTargetVO searchTargetVO)
			throws BPLException {
		StringBuffer sb = new StringBuffer();
		List<String> l = null;
		try {
			l = targetDao.searchTarget(searchTargetVO, SystemUtil
					.getCurrentUserId());
		} catch (RuntimeException e) {
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		if (l != null && l.size() > 0) {
			sb.append("{begin:");
			sb.append(searchTargetVO.getStartIndex() + 1);
			sb.append(",end:");
			int size = l.size();
			sb.append(searchTargetVO.getStartIndex() + size);
			sb.append(",data:[");
			for (int i = 0; i < size; i++) {
				Object str = l.get(i);
				if (str instanceof Blob) {
					str = this.getBlobContent((Blob) str);
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{error:\"failed to query.\"");
		}
		sb.append("}");
		return sb.toString();
	}

	private String getBlobContent(Blob b) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedInputStream bis = new BufferedInputStream(b
					.getBinaryStream());
			int len = (int) b.length();
			byte[] bt = new byte[len];
			int readLen = 0;
			while ((readLen = bis.read(bt)) != -1) {
				sb.append(new String(bt, 0, readLen));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public ITargetDao getTargetDao() {
		return targetDao;
	}

	public void setTargetDao(ITargetDao targetDao) {
		this.targetDao = targetDao;
	}

	public String getCircuitList(Integer banId) throws BPLException {

		StringBuffer sb = new StringBuffer("[");
		List banList;
		try {
			banList = targetDao.getCircuitList(SystemUtil.getCurrentUserId(),
					banId);
		} catch (Exception e) {
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for (Object b : banList) {
			Object[] ban = (Object[]) b;
			if (sb.length() > 1)
				sb.append(",");
			sb.append("{id:" + String.valueOf(ban[0]));
			sb.append(",no:\"" + String.valueOf(ban[1]) + "\"}");
		}
		sb.append("]");

		return sb.toString();
	}

	public List<MapEntryVO<String, String>> chargeTypeList()
			throws BPLException {
		List<MapEntryVO<String, String>> l = new ArrayList<MapEntryVO<String, String>>();
		List<TargetChargeType> iList = null;
		try {
			iList = targetDao.chargeTypeList();
		} catch (RuntimeException e) {
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		for (TargetChargeType i : iList) {
			MapEntryVO<String, String> m = new MapEntryVO<String, String>(i
					.getId().toString(), i.getChargeTypeName());
			l.add(m);
		}

		return l;
	}

	public String getTragetSearchTotalPageNo(SearchTargetVO searchTargetVO)
			throws BPLException {
		StringBuffer sb = new StringBuffer();
		long count = 0;
		try {
			count = targetDao.getTragetSearchTotalPageNo(searchTargetVO,
					SystemUtil.getCurrentUserId());
		} catch (RuntimeException e) {
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		// Example: "{totalResultNo:4,totalPageNo:1}"
		sb.append("{totalResultNo:");
		sb.append(count);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) count
				/ (double) searchTargetVO.getRecPerPage());
		sb.append(tp);
		sb.append("}");

		return sb.toString();
	}

	public String createTargetToExcel(SearchTargetVO searchTargetVO,
			String excelDirPath, List<String> titles,List<Integer> targetIds) throws BPLException {
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();
			long count = 0;
			int recPerPage = 100;
			count = targetDao.getTragetSearchTotalPageNo(searchTargetVO,
					SystemUtil.getCurrentUserId());
			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "invoice0");
			jExcelUtil.writeTitle(0, titles);
			if (count <= recPerPage) {
				searchTargetVO.setPageNo(1);
				searchTargetVO.setRecPerPage((int) count);
				list = targetDao.searchTargetByObject(searchTargetVO,
						SystemUtil.getCurrentUserId(),targetIds);
				for (int i = 0; i < list.size(); i++) {
					jExcelUtil.writeLine(i + 1, list.get(i));
				}
			} else {
				long totlePage = (count + recPerPage - 1) / recPerPage;
				for (int j = 0; j < totlePage; j++) {
					searchTargetVO.setPageNo(j + 1);
					searchTargetVO.setRecPerPage(recPerPage);
					list = targetDao.searchTargetByObject(searchTargetVO,
							SystemUtil.getCurrentUserId(),targetIds);
					for (int i = 0; i < list.size(); i++) {
						jExcelUtil.writeLine(j * recPerPage + i + 1, list
								.get(i));
					}
				}
			}
			jExcelUtil.setColumnView(new int[] { 27, 42, 40, 25, 31, 22, 22,
					20, 27, 27, 35, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 40,
					40 });
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		return excelDirPath;
	}
	/**
	 * @param type (3代表vefication进来)
	 */
	public String saveTarget(List<Target> targetList, int type) throws BPLException {
		for (int i = 0; i < targetList.size(); i++) {
			if (targetList.get(i) == null) {
				continue;
			}
			targetDao.saveTarget(targetList.get(i),type);
		}
		return null;
	}
	
	public String deleteResultType(List<Target> targetList, int type) throws BPLException {
		for (int i = 0; i < targetList.size(); i++) {
			if (targetList.get(i) == null) {
				continue;
			}
			targetDao.deleteTargetResult(targetList.get(i),type);
		}
		return null;
	}

	public String queryCopyTargetList(List<Target> targetList)
			throws BPLException {
		StringBuffer sb = new StringBuffer("[");
		List<String> l = null;
		try {
			l = targetDao.queryCopyTargetList(targetList);
			for (int i = 0; i < l.size(); i++) {
				if (i != 0)
					sb.append(",");
				sb.append(l.get(i));
			}
			sb.append("]");
		} catch (RuntimeException e) {
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		System.out.println(sb.toString());
		return sb.toString();
	}
/**
 * ��������
 */
	public String common(List<Target> targetList,int type) 
	throws BPLException, ParseException {
		boolean newTargetResult = false;
		if (targetList.get(0).getId() == null) {
			saveTarget(targetList,type);
		}else{
			deleteResultType(targetList,type);
		}
		
		StringBuffer sb = new StringBuffer("[");
		for (int i = 0; i < targetList.size(); i++) {
			
			if (targetList.get(i) == null) {
				continue;
			
			}
			Target target = targetList.get(i);
			Boolean pass = false;
		if(2!=type){	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			Calendar cal = Calendar.getInstance();
			List<TargetResult> trList = new ArrayList<TargetResult>();
			Integer countNumber=0;
			if(target.getAllowedPeriodShift()!=null && !"".equals(target.getAllowedPeriodShift())){      
				//����:AllowedPeriodShift ��ǰ����Ʋ�ѯ���·�,����: PeriodFrom=201306,PeriodTo=201308,AllowedPeriodShift=2
			    //���:201212-201302,201303-201305,201306-201308,201311-201309,201402-201312
				//ԭ��:��Ϊ��6-8��,ǰ���ȡ6��.
				
				int yearFrom = Integer.parseInt(target.getPeriodFrom().toString().substring(0, 4));
				int monthFrom = Integer.parseInt(target.getPeriodFrom().toString().substring(4, 6));
				int yearTo = Integer.parseInt(target.getPeriodTo().toString().substring(0, 4));
				int monthTo = Integer.parseInt(target.getPeriodTo().toString().substring(4, 6));
				int monthDistance = monthTo >= monthFrom ? (Math.abs(yearTo-yearFrom)*12 + Math.abs(monthTo-monthFrom)) : Math.abs(Math.abs(yearTo-yearFrom)*12 - Math.abs(monthTo-monthFrom));
				
				
				for (int j = 1; j <= target.getAllowedPeriodShift(); j++) {
					
					int totleMonth = monthDistance*j+j;
					
					List list = targetDao.findTargetPeriod(target,totleMonth);
					
					String newFromDateLeft = "";
					String newToDateLeft = "";
					String newFromDateRi = "";
					String newToDateLeRi = "";
					
					if(list.size() > 0){
						
						Object[] o = (Object[]) list.get(0);
						
						newFromDateLeft = o[0].toString();
						newToDateLeft = o[1].toString();
						newFromDateRi = o[2].toString();
						newToDateLeRi = o[3].toString();
					}
					
					//计算月份的差值
					
	
					Date oldFromDate = sdf.parse(target.getPeriodFrom() + "");
					Date oldToDate = sdf.parse(target.getPeriodTo() + "");
	
	
					TargetResult tr1 = targetDao.findTargetResultByTargetIdAndPeriod(target
							.getId(), (newFromDateLeft+"-"+newToDateLeft));
					if (tr1==null) {
						tr1 =new TargetResult();
						newTargetResult = true;
					}
					tr1.setAllowedPeriodShift(target.getAllowedPeriodShift());
					tr1.setRecActiveFlag("Y");
					tr1.setResultFlag("N");
					tr1.setOrder(-j);
					tr1.setTargetId(target.getId());
					tr1.setPeriod((newFromDateLeft+"-"+newToDateLeft));
	
 					TargetResult tr2 = targetDao.findTargetResultByTargetIdAndPeriod(target
							.getId(), (newFromDateRi+"-"+newToDateLeRi));
					if (tr2==null) {
  						tr2= new TargetResult();
 						newTargetResult = true;
					}
 					tr2.setAllowedPeriodShift(target.getAllowedPeriodShift());
 					tr2.setRecActiveFlag("Y");
					tr2.setResultFlag("N");
 					tr2.setOrder(j);
					tr2.setTargetId(target.getId());
					tr2.setPeriod((newFromDateRi+"-"+newToDateLeRi));
					//target_charge_type(9-18)
					Double itemAmountLeft=0.0;
					Double itemAmountRt=0.0;
					if(1<=target.getChargeTypeId()&&target.getChargeTypeId()<=8){
						 itemAmountLeft = targetDao.getInvoiceItemAmountByTarget(
								target, newFromDateLeft, newToDateLeft);
						 itemAmountRt = targetDao.getInvoiceItemAmountByTarget(
									target,newFromDateRi, newToDateLeRi );
					}else{
						 itemAmountLeft=targetDao.getInvoiceAmountByTarget(target, newFromDateLeft,newToDateLeft);
						 itemAmountRt = targetDao.getInvoiceAmountByTarget(
									target, newFromDateRi,newToDateLeRi );
					}
	
					tr1.setActualAmount(itemAmountLeft);
					tr2.setActualAmount(itemAmountRt);
					trList.add(tr1);
					trList.add(tr2);
					
					countNumber=j;
					
					if((target!=null && target.getChangeAmount()!=null && j==target.getAllowedPeriodShift())
							|| (target!=null && target.getChangePercentage()!=null && j==target.getAllowedPeriodShift())){
						cal.setTime(oldFromDate);
						
//						list = targetDao.findTargetPeriod(target,totleMonth);
//						
//						if(list.size() > 0){
//							newFromDateLeft = list.get(0).toString();
//							newToDateLeft = list.get(1).toString();
//							newFromDateRi = list.get(2).toString();
//							newToDateLeRi = list.get(3).toString();
//						}
						
						TargetResult tr3 = targetDao.findTargetResultByTargetIdAndPeriod(target
								.getId(), (newFromDateLeft + "-"
								+ newToDateLeft));
						if (tr3==null) {
							tr3 =new TargetResult();
							newTargetResult = true;
						}
						tr3.setAllowedPeriodShift(target.getAllowedPeriodShift());
						tr3.setRecActiveFlag("Y");
						tr3.setResultFlag("N");
						tr3.setOrder(-(j+1));
						tr3.setTargetId(target.getId());
						tr3.setPeriod((newFromDateLeft + "-"
								+ newToDateLeft));
						Double itemAmountLeft1=0.0;
						
						if(1<=target.getChargeTypeId()&&target.getChargeTypeId()<=8){
							itemAmountLeft1 = targetDao.getInvoiceItemAmountByTarget(
									target, newFromDateLeft, newToDateLeft);
						}else{
							itemAmountLeft1=targetDao.getInvoiceAmountByTarget(target, newFromDateLeft, newToDateLeft);
						}
						tr3.setActualAmount(itemAmountLeft1);
						trList.add(tr3);
					}
				}
			}
			Double itemAmout=0.0;
			if(null!=target.getChargeTypeId()&&!"".equals(target.getChargeTypeId())&&1<=target.getChargeTypeId()&&target.getChargeTypeId()<=8){
				itemAmout = targetDao.getInvoiceItemAmountByTarget(target,
						target.getPeriodFrom() + "", target.getPeriodTo() + "");
			}else {
				itemAmout = targetDao.getInvoiceAmountByTarget(target,
						target.getPeriodFrom() + "", target.getPeriodTo() + "");
			}

			TargetResult tr = targetDao.findTargetResultByTargetIdAndPeriod(target
					.getId(), target.getPeriodFrom() + "-"
					+ target.getPeriodTo());
			if (tr == null) {
				tr = new TargetResult();
				newTargetResult = true;
			}
			tr.setPeriod(target.getPeriodFrom() + "-" + target.getPeriodTo());
			tr.setAllowedPeriodShift(target.getAllowedPeriodShift());
			tr.setRecActiveFlag("Y");
			tr.setResultFlag("N");
			tr.setOrder(0);
			tr.setTargetId(target.getId());
			tr.setActualAmount(itemAmout);
			trList.add(tr);
			if (itemAmout != null) {
				if (target.getTargetAmount() != null) {
					
					for (TargetResult tarR : trList) {
						if (target.getTargetAmount().doubleValue() == Math.abs(tarR.getActualAmount().doubleValue())) {
							tarR.setResultFlag("Y");
							tarR.setActualAmount(tarR.getActualAmount().doubleValue());
							continue;
						}
						if (target.getAllowedVariationPercentage() != null) {
							double allowedDistance =  target.getAllowedVariationPercentage()
											.doubleValue()/100;
							double actualFormula = Math.abs(tarR.getActualAmount()-target.getTargetAmount().doubleValue())
															/target.getTargetAmount().doubleValue();
							
							if(actualFormula <= allowedDistance) {
								tarR.setResultFlag("Y");
							}
						}
						tarR.setActualAmount(tarR.getActualAmount().doubleValue());
					}

				} else if (target.getChangePercentage() != null) {
					
					for (TargetResult tarR : trList) {
						if(tarR.getOrder()==countNumber){
							trList=backNearObject(tarR,trList,target);
							break;
						}
					}
					

				}else if (target.getChangeAmount() != null) {
					for (TargetResult tarR : trList) {
						if(tarR.getOrder()==countNumber){
							trList=backNearObject(tarR,trList,target);
							break;
						}
					}
				}
			}else{
				if(target.getChangeAmount() != null || target.getChangeAmount() != null){
					trList.remove(trList.size() - 1);
				}
			}


			for (TargetResult tar : trList) {
				if (newTargetResult) {
					tar.setCreatedBy(SystemUtil.getCurrentUserId());
					tar.setCreatedTimestamp(new Date());
				}
				tar.setModifiedBy(SystemUtil.getCurrentUserId());
				tar.setModifiedTimestamp(new Date());
				targetDao.save(tar);
			}
		}

			List<TargetResult> list = targetDao
					.queryTarResultListByTargetId(target.getId());

			String s = targetDao.queryTargetById(target.getId());
			s = s.replace("}", ",titles:'");
			for (TargetResult t : list) {
				s += t.getPeriod();
				s += "!";
				if ("Y".equals(t.getResultFlag())) {
			      pass = true;
				}
			}
			s += "',flags:'";
			for (TargetResult t : list) {
				if("Y".equals(t.getResultFlag())){
					s += "Passed";
					s += "!";
				}else {
					s += "Failed";
					s += "!";
				}
				
				
			}
			s +="',create:'";
			for(TargetResult t : list){
				String result=targetDao.seacherUsernameByUserId(t.getModifiedBy());
				s+=result;
				s+="!";
			}
			s+="',createdate:'";
            for(TargetResult t :list){
            	s+=CcmFormat.formatDateTime(t.getCreatedTimestamp());
            	s+="!";
            }			
			s += "',result:";
			if (pass) {
				s += "'Passed'";
			} else {
				s += "'Failed'";
			}
			s += "}";
			sb.append(s);
			if (i != targetList.size() - 1) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
		
		
		
	}
	
	
	private List<TargetResult> backNearObject(TargetResult tr,List<TargetResult> targetResultList,Target target){
		List<TargetResult> rewritetarRList = new ArrayList<TargetResult>();
		Integer countNumber = tr.getOrder();
		
		 ListSortUtil<TargetResult> sortList = new ListSortUtil<TargetResult>();  
	        sortList.sort(targetResultList, "order", "asc");  
		
		for(int i = targetResultList.size()-1;i>=0;i-- ){
			TargetResult trResult = new TargetResult();
			//上月
			if(i!=0){
				trResult =targetResultList.get(i-1);
				countNumber = countNumber-1;
				if(trResult.getOrder() == countNumber){
					//本月
					TargetResult trResult2 = new TargetResult();
					trResult2=targetResultList.get(i);
					if(target.getChangeAmount() != null){
						double twoMonthsDifference = Math.abs(trResult2.getActualAmount()-trResult.getActualAmount());
						java.math.BigDecimal b = new java.math.BigDecimal(twoMonthsDifference);  
						twoMonthsDifference = b.setScale(4, java.math.BigDecimal.ROUND_HALF_UP).doubleValue();  
//						trResult2.setPeriod(trResult2.getPeriod()+trResult.getPeriod());
						if(twoMonthsDifference<=target.getChangeAmount()){
							trResult2.setResultFlag("Y");
							trResult2.setActualChangeAmount(twoMonthsDifference);
						}
						else
							trResult2.setActualChangeAmount(twoMonthsDifference);
					}
					if(target.getChangePercentage() != null){
						DecimalFormat df = new DecimalFormat("#0.0000");
//						trResult2.setPeriod(trResult2.getPeriod()+trResult.getPeriod());
						if(0!=(target.getChangePercentage().doubleValue())&&0.00!=trResult2.getActualAmount().doubleValue()
									&&0.00!=trResult.getActualAmount().doubleValue()){
							double twoMonthsDifference =new Double( df.format( Math.abs((trResult2.getActualAmount().doubleValue()-trResult.getActualAmount().doubleValue())
										/trResult.getActualAmount().doubleValue())));
							
							
							if (twoMonthsDifference <= (target.getChangePercentage()
									.doubleValue()/100)) {
								trResult2.setResultFlag("Y");
								
							}
							
							trResult2.setActualChangePercentage(new BigDecimal(Math
									.abs((trResult.getActualAmount().doubleValue() - trResult2.getActualAmount().doubleValue())
											/ trResult.getActualAmount().doubleValue())));
							
							
						}else if(0==trResult2.getActualAmount().doubleValue()&&0.00!=trResult.getActualAmount().doubleValue()){
							trResult2.setResultFlag("Y");
						}else if(0==trResult.getActualAmount().doubleValue()&&0.00!=trResult2.getActualAmount().doubleValue()){
							trResult2.setResultFlag("N");
						}else{
							trResult2.setResultFlag("Y");
						}
						
					}
					rewritetarRList.add(trResult2);
				}
				
			}
		}
		return rewritetarRList;
	}
	
	
	//procedure����verification
	public String ProcedureProcessVerification(List<Target> targets) throws SQLException, BPLException{
//		Map<String, Object> map = new Hashtable<String, Object>();
//		map.put("topics", targets);
//		String jsonData=JSONObject.fromObject(map).toString();
//		targetDao2.getProcedureVerfication(jsonData);
//		System.out.println(jsonData);
//		System.out.println("--------------------------------------");
		StringBuffer sb = new StringBuffer();
		for(int i=0;i<targets.size();i++){
			if(targets.get(i).getId()==null){
				int result = targetDao2.saveTarget(targets.get(i));
				sb.append(","+result);
			}else {
				targetDao2.updateTarget(targets.get(i));
				sb.append(","+targets.get(i).getId());
			}
		}
		sb.replace(0, 1, "");
		targetDao2.getProcedureVerfication(sb.toString());
		return null;
	}
	
	
	
	public String downLoadResultToExcel(List<Integer> targetIds,
			String excelDirPath, List<String> titles) throws BPLException {
		try {
			List<Object[]> list = null;
			JExcelUtil jExcelUtil = new JExcelUtil();

			jExcelUtil.createWritableWorkbook(excelDirPath);
			jExcelUtil.createWritableSheet(0, "invoice0");
			jExcelUtil.writeTitle(0, titles);

			list = targetDao.searchTargetResultByObject(targetIds, SystemUtil
					.getCurrentUserId());
			for (int i = 0; i < list.size(); i++) {
				jExcelUtil.writeLine(i + 1, list.get(i));
			}

			jExcelUtil.setColumnView(new int[] { 27, 42, 40, 25, 31, 22, 22,
					20, 27, 27, 35, 25, 25, 25, 25, 25, 25, 25, 25, 25, 25, 40,
					40 });
			// jExcelUtil.protect();
			jExcelUtil.write();
			jExcelUtil.close();
		} catch (Throwable e) {
			BPLException bpe = new BPLException(
					ErrorCodeConstants.EC_HIBERNATE_FAILED_ACCESS_DB);
			throw bpe;
		}
		return excelDirPath;
	}

	
	/**
	 * ������
	 */
	public String commonResult(List<Integer> targetIds,int type)
	        throws BPLException, ParseException {
		List<Target> targetList = new ArrayList<Target>();
		for (Integer id : targetIds) {
			Target t = new Target();
			t = targetDao.findById(id);
			targetList.add(t);
		}
		return common(targetList, type);
	}


	public String excelUp(File file) throws SQLException, FileNotFoundException,
			IOException, ParseException {
		HSSFWorkbook workbook;
		workbook = new HSSFWorkbook(new FileInputStream(file));
		if (file == null) {
			return null;
		}
		HSSFSheet sheet = workbook.getSheet("Sheet1");
		int rows = sheet.getPhysicalNumberOfRows();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		try {
			for (int r = 1; r < rows; r++) {
				HSSFRow row = sheet.getRow(r);
				if (row != null) {
					String t1 = "";
					HSSFCell t1cell = row.getCell((short) 0);
					if (t1cell != null) {
						t1 = t1cell.getNumericCellValue()+"";
					} else {
						t1 = null;
					}

				}
			}// for
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		return null;
	}
	
	
	
	
	/**
	 * �¼ӵĻ�ȡexcel�������
	 * @throws IOException 
	 * @throws BPLException 
	 * 
	 */
	
	public String read2007Excel(File file,String fileName) throws IOException, BPLException {
		ReadExcel.saveFile(file, fileName);
		
		SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
		String pathString=ServletActionContext.getServletContext().getRealPath
		("/pic");
		
		File file2= new File(pathString+"/" + fileName);
		//  �ж�excel������
		
		String vailidata=validateExcelContext(file2,fileName);
		if(!"".equals(vailidata)){
			BPLException bpl = new BPLException(vailidata);
			throw bpl;
		}
		//
		
		 List<List<Object>> list = ReadExcel.readExcel(file2,fileName);
		
		 List<Target> targetList=new ArrayList<Target>();
		 StringBuffer sb =new StringBuffer();
		 int count=0;
		 
		 for(List l:list){
			 if(count==0){
				 sb.append("[");
			 }
			++count;
			if(count>=2){
				sb.append(",");
			}
			List list2=l;
			System.out.println(list2.size());
			 Target target = new Target();
			 for(int i =0;i<=l.size();i++){
				 switch(i){
				 case 0:
					 String vendorName=l.get(i).toString();
					 Integer vendorId=queryByVendorId(vendorName);
					 target.setVendorId(vendorId);
					 sb.append("{vendorName:\""+vendorName).append("\"");
					 sb.append(",vendorId:\""+vendorId).append("\"");
					 break;
				 case 1:
					 String accountNumber=l.get(i).toString();
					 Integer banId=quyeryBanId(accountNumber);
					 target.setBanId(banId);
					 sb.append(",accountNumber:\""+accountNumber).append("\"");
					 sb.append(",banId:\""+banId).append("\"");
					 break;
				 case 2:
					 target.setStrippedCircuitNumber(l.get(i).toString());
					 sb.append(",CircuitNumber:\""+l.get(i).toString()).append("\"");
					 break;
				 case 3:
					 if("".equals(l.get(i))){
						 sb.append(",PeriodFrom:\""+l.get(i).toString()).append("\"");
						 break;
					 }
					 target.setPeriodFrom(Integer.parseInt(l.get(i).toString()));
					 sb.append(",PeriodFrom:\""+l.get(i).toString()).append("\"");
					 break;
				 case 4:
					 if("".equals(l.get(i))){
						 sb.append(",PeriodTo:\""+l.get(i).toString()).append("\"");
						 break;
					 }
					 target.setPeriodTo(Integer.parseInt(l.get(i).toString()));
					 sb.append(",PeriodTo:\""+l.get(i).toString()).append("\"");
					 break;
				 case 5:
					 if("".equals(l.get(i))){
						 sb.append(",AllowedPeriodShift:\""+l.get(i).toString()).append("\"");
						 break;
					 }
					 target.setAllowedPeriodShift(Integer.parseInt(l.get(i).toString()));
					 sb.append(",AllowedPeriodShift:\""+l.get(i).toString()).append("\"");
					 break;
				 case 6:
					 String chargeTypeName=l.get(i).toString();
					 Integer chargeTypeId =queryCharyTypeID(chargeTypeName);
					 target.setChargeTypeId(chargeTypeId);
					 sb.append(",chargeTypeName:\""+chargeTypeName).append("\"");
					 sb.append(",chargeTypeId:\""+chargeTypeId).append("\"");
					 break;
				 case 7:
					 if("".equals(l.get(i))){
						 sb.append(",TargetAmount:\""+l.get(i).toString()).append("\"");
						 break;
					 }
					 target.setTargetAmount(Double.parseDouble(l.get(i).toString()));
					 sb.append(",TargetAmount:\""+l.get(i).toString()).append("\"");
					 break;
				 case 8:
					 if("".equals(l.get(i))){
						 sb.append(",ChangeAmount:\""+l.get(i).toString()).append("\"");
						 break;
					 }
					 target.setChangeAmount(Double.parseDouble(l.get(i).toString()));
					 sb.append(",ChangeAmount:\""+l.get(i).toString()).append("\"");
					 break;
				 case 9:
					 if("".equals(l.get(i))){
						 sb.append(",ChangePercentage:\""+l.get(i).toString()).append("\"");
						 break;
					 }
					 target.setChangePercentage(BigDecimal.valueOf(Long.parseLong(l.get(i).toString())));
					 sb.append(",ChangePercentage:\""+l.get(i).toString()).append("\"");
					 break;
				 case 10:
					 if("".equals(l.get(i))){
						 sb.append(",AllowedVariationPercentage:\""+l.get(i).toString()).append("\"");
						 break;
					 }
					 target.setAllowedVariationPercentage(BigDecimal.valueOf(Long.parseLong(l.get(i).toString())));
					 sb.append(",AllowedVariationPercentage:\""+l.get(i).toString()).append("\"");
					 
					 break;
				 case 11:
					 sb.append(",Source:\""+pathString+"\\\\"+"\\\\"+fileName+"\"");
					 sb.append("}");
					 break;
				 }
			 }
			
			 targetList.add(target);
			 
		 }
		 sb.append("]");
		 
		 
	     JSONObject jsonObject=JSONObject.fromObject(getBanidByVendor0(targetList));//���vendor��ȡBan
	     JSONArray jsonArray =new JSONArray();
	     jsonArray.add(jsonObject);
	     
	     JSONObject jsonObject2=JSONObject.fromObject(getCircuitByBanid0(targetList));//���ban��ȡcircuit
	     JSONArray jsonArray1 =new JSONArray();
	     jsonArray1.add(jsonObject2);
	    
	     
	     JSONObject jsonObject3=JSONObject.fromObject(getCircuitByBanidAndVendorId(targetList));//���banandvendor��ȡcircuit
	     JSONArray jsonArray2 =new JSONArray();
	     jsonArray2.add(jsonObject3);	
	     
	     
	     
		return sb.toString()+"!"+jsonArray.toString()+"!"+jsonArray1.toString()+"!"+jsonArray2.toString();
	}
	
	private String validateExcelContext(File file ,String filename) throws IOException{
		XSSFWorkbook xwb = new XSSFWorkbook(new FileInputStream(file));
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row = null;
		XSSFRow row1 = null;
		row = sheet.getRow(sheet.getFirstRowNum());
		row1 = sheet.getRow(sheet.getFirstRowNum()+1);
		StringBuffer sb= new StringBuffer("");
		if(row==null | row1==null){
			sb.append("excel is null ");
		}
		if(row!=null){
			XSSFCell cell = null;
			if(!"Vendor".equals((row.getCell(0).getStringCellValue().toString()).trim())     
				| !"Ban".equals((row.getCell(1).getStringCellValue().toString()).trim())
				| !"Circuit Number".equals((row.getCell(2).getStringCellValue().toString()).trim())
				| !"Period from(YYYYmm)".equals((row.getCell(3).getStringCellValue().toString()).trim())
				| !"Period to(YYYYmm)".equals((row.getCell(4).getStringCellValue().toString()).trim())
				| !"Allowed Period Shift".equals((row.getCell(5).getStringCellValue().toString()).trim())
				| !"Charge Type".equals((row.getCell(6).getStringCellValue().toString()).trim())
				| !"Target Amount".equals((row.getCell(7).getStringCellValue().toString()).trim())
				| !"Change Amount".equals((row.getCell(8).getStringCellValue().toString()).trim())
				| !"Change Percentage(%)".equals((row.getCell(9).getStringCellValue().toString()).trim())
				| !"AllowedVariation Percentage(%)".equals((row.getCell(10).getStringCellValue().toString()).trim())
			){
				sb.append("| Upload failed due to data errors found in the file");
			}
		}
		return sb.toString();
	}
	
	private Integer queryByVendorId(String vendorName) {
		if(targetDao2.modifyObject(vendorName, "Vendor")==null){
			return null;
		}
		Vendor v=(Vendor)targetDao2.modifyObject(vendorName, "Vendor");
		return v.getId();
	}

	private Integer quyeryBanId(String accountNumber) {
		if(targetDao2.modifyObject(accountNumber, "Ban")==null){
			return null;
		}
		Ban b= (Ban)targetDao2.modifyObject(accountNumber, "Ban");
		return b.getId();
	}

	private Integer queryCharyTypeID(String chargeTypeName) {
		if(targetDao2.modifyObject(chargeTypeName, "CharyType")==null){
			return null;
		}
		TargetChargeType t= (TargetChargeType)targetDao2.modifyObject(chargeTypeName, "CharyType");
		return t.getId();
	}
	
	private Map<Integer, List> getBanidByVendor0(List<Target> targetList){
		Map<Integer, List> map=new HashMap<Integer, List>();         //���vendor�����е�ban�����
		for(Target target : targetList){
			 if(target.getVendorId()!=null&&!map.keySet().toString().equals("["+target.getVendorId()+"]")){
					List banList;
						banList = targetDao2.getBanIdByVendorId(target.getVendorId());
				 map.put(target.getVendorId(), banList);
			 }
		 }
		return map;
	}
	private Map<Integer, List> getCircuitByBanid0(List<Target> targetList){
		Map<Integer, List> map=new HashMap<Integer, List>();        //���Ban��ȡCircuit
		 for(Target target : targetList){
			 if(target.getBanId()!=null&&!map.keySet().toString().equals("["+target.getBanId()+"]")){
					List Circuit;
					Circuit = targetDao2.getCircuitList(target.getBanId());
					map.put(target.getBanId(), Circuit);
			 }
	      }
		 System.out.println(map.size());
		 return map;
	}
	
	
	private Map<Integer, List> getCircuitByBanidAndVendorId(List<Target> targetList){
		Map<Integer, List> map=new HashMap<Integer, List>();        //���Ban and vendor��ȡCircuit
		 for(Target target : targetList){
			 if(target.getVendorId()!=null&&target.getBanId()!=null&&!map.keySet().toString().equals("["+target.getVendorId()+"]")&&!map.keySet().toString().equals("["+target.getBanId()+"]")){
					List Circuit;
					Circuit = targetDao2.getCircuitListByBanVendor(target.getBanId(),target.getVendorId());
					map.put(target.getBanId(), Circuit);
			 }
	      }
		 System.out.println(map.size());
		 return map;
	}
	
	public String saveTG(List<Target> targetList,int type)	throws BPLException, ParseException{
		boolean newTargetResult = false;
		if (targetList.get(0).getId() == null) {
			saveTarget(targetList,type);
		}else{
			deleteResultType(targetList,type);
		}
		
		for (int i = 0; i < targetList.size(); i++) {
			
			if (targetList.get(i) == null) {
				continue;
			
			}
			Target target = targetList.get(i);
			Boolean pass = false;
		if(2!=type){	
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
			Calendar cal = Calendar.getInstance();
			List<TargetResult> trList = new ArrayList<TargetResult>();
			Integer countNumber=0;
			if(target.getAllowedPeriodShift()!=null && !"".equals(target.getAllowedPeriodShift())){      
				//����:AllowedPeriodShift ��ǰ����Ʋ�ѯ���·�,����: PeriodFrom=201306,PeriodTo=201308,AllowedPeriodShift=2
			    //���:201212-201302,201303-201305,201306-201308,201311-201309,201402-201312
				//ԭ��:��Ϊ��6-8��,ǰ���ȡ6��.
				
				int yearFrom = Integer.parseInt(target.getPeriodFrom().toString().substring(0, 4));
				int monthFrom = Integer.parseInt(target.getPeriodFrom().toString().substring(4, 6));
				int yearTo = Integer.parseInt(target.getPeriodTo().toString().substring(0, 4));
				int monthTo = Integer.parseInt(target.getPeriodTo().toString().substring(4, 6));
				int monthDistance = monthTo >= monthFrom ? (Math.abs(yearTo-yearFrom)*12 + Math.abs(monthTo-monthFrom)) : Math.abs(Math.abs(yearTo-yearFrom)*12 - Math.abs(monthTo-monthFrom));
				
				
				for (int j = 1; j <= target.getAllowedPeriodShift(); j++) {
					
					int totleMonth = monthDistance*j+j;
					
					List list = targetDao.findTargetPeriod(target,totleMonth);
					
					String newFromDateLeft = "";
					String newToDateLeft = "";
					String newFromDateRi = "";
					String newToDateLeRi = "";
					
					if(list.size() > 0){
						
						Object[] o = (Object[]) list.get(0);
						
						newFromDateLeft = o[0].toString();
						newToDateLeft = o[1].toString();
						newFromDateRi = o[2].toString();
						newToDateLeRi = o[3].toString();
					}
					
					//计算月份的差值
					
	
					Date oldFromDate = sdf.parse(target.getPeriodFrom() + "");
					Date oldToDate = sdf.parse(target.getPeriodTo() + "");
	
	
					TargetResult tr1 = targetDao.findTargetResultByTargetIdAndPeriod(target
							.getId(), (newFromDateLeft+"-"+newToDateLeft));
					if (tr1==null) {
						tr1 =new TargetResult();
						newTargetResult = true;
					}
					tr1.setAllowedPeriodShift(target.getAllowedPeriodShift());
					tr1.setRecActiveFlag("Y");
					tr1.setResultFlag("N");
					tr1.setOrder(-j);
					tr1.setTargetId(target.getId());
					tr1.setPeriod((newFromDateLeft+"-"+newToDateLeft));
	
 					TargetResult tr2 = targetDao.findTargetResultByTargetIdAndPeriod(target
							.getId(), (newFromDateRi+"-"+newToDateLeRi));
					if (tr2==null) {
  						tr2= new TargetResult();
 						newTargetResult = true;
					}
 					tr2.setAllowedPeriodShift(target.getAllowedPeriodShift());
 					tr2.setRecActiveFlag("Y");
					tr2.setResultFlag("N");
 					tr2.setOrder(j);
					tr2.setTargetId(target.getId());
					tr2.setPeriod((newFromDateRi+"-"+newToDateLeRi));
					//target_charge_type(9-18)
					Double itemAmountLeft=0.0;
					Double itemAmountRt=0.0;
					if(1<=target.getChargeTypeId()&&target.getChargeTypeId()<=8){
						 itemAmountLeft = targetDao.getInvoiceItemAmountByTarget(
								target, newFromDateLeft, newToDateLeft);
						 itemAmountRt = targetDao.getInvoiceItemAmountByTarget(
									target,newFromDateRi, newToDateLeRi );
					}else{
						 itemAmountLeft=targetDao.getInvoiceAmountByTarget(target, newFromDateLeft,newToDateLeft);
						 itemAmountRt = targetDao.getInvoiceAmountByTarget(
									target, newFromDateRi,newToDateLeRi );
					}
	
					tr1.setActualAmount(itemAmountLeft);
					tr2.setActualAmount(itemAmountRt);
					trList.add(tr1);
					trList.add(tr2);
					
					countNumber=j;
					
					if((target!=null && target.getChangeAmount()!=null && j==target.getAllowedPeriodShift())
							|| (target!=null && target.getChangePercentage()!=null && j==target.getAllowedPeriodShift())){
						cal.setTime(oldFromDate);
						
//						list = targetDao.findTargetPeriod(target,totleMonth);
//						
//						if(list.size() > 0){
//							newFromDateLeft = list.get(0).toString();
//							newToDateLeft = list.get(1).toString();
//							newFromDateRi = list.get(2).toString();
//							newToDateLeRi = list.get(3).toString();
//						}
						
						TargetResult tr3 = targetDao.findTargetResultByTargetIdAndPeriod(target
								.getId(), (newFromDateLeft + "-"
								+ newToDateLeft));
						if (tr3==null) {
							tr3 =new TargetResult();
							newTargetResult = true;
						}
						tr3.setAllowedPeriodShift(target.getAllowedPeriodShift());
						tr3.setRecActiveFlag("Y");
						tr3.setResultFlag("N");
						tr3.setOrder(-(j+1));
						tr3.setTargetId(target.getId());
						tr3.setPeriod((newFromDateLeft + "-"
								+ newToDateLeft));
						Double itemAmountLeft1=0.0;
						
						if(1<=target.getChargeTypeId()&&target.getChargeTypeId()<=8){
							itemAmountLeft1 = targetDao.getInvoiceItemAmountByTarget(
									target, newFromDateLeft, newToDateLeft);
						}else{
							itemAmountLeft1=targetDao.getInvoiceAmountByTarget(target, newFromDateLeft, newToDateLeft);
						}
						tr3.setActualAmount(itemAmountLeft1);
						trList.add(tr3);
					}
				}
			}
			Double itemAmout=0.0;
			if(null!=target.getChargeTypeId()&&!"".equals(target.getChargeTypeId())&&1<=target.getChargeTypeId()&&target.getChargeTypeId()<=8){
				itemAmout = targetDao.getInvoiceItemAmountByTarget(target,
						target.getPeriodFrom() + "", target.getPeriodTo() + "");
			}else {
				itemAmout = targetDao.getInvoiceAmountByTarget(target,
						target.getPeriodFrom() + "", target.getPeriodTo() + "");
			}

			TargetResult tr = targetDao.findTargetResultByTargetIdAndPeriod(target
					.getId(), target.getPeriodFrom() + "-"
					+ target.getPeriodTo());
			if (tr == null) {
				tr = new TargetResult();
				newTargetResult = true;
			}
			tr.setPeriod(target.getPeriodFrom() + "-" + target.getPeriodTo());
			tr.setAllowedPeriodShift(target.getAllowedPeriodShift());
			tr.setRecActiveFlag("Y");
			tr.setResultFlag("N");
			tr.setOrder(0);
			tr.setTargetId(target.getId());
			tr.setActualAmount(itemAmout);
			trList.add(tr);
			if (itemAmout != null) {
				if (target.getTargetAmount() != null) {
					
					for (TargetResult tarR : trList) {
						if (target.getTargetAmount().doubleValue() == Math.abs(tarR.getActualAmount().doubleValue())) {
							tarR.setResultFlag("Y");
							tarR.setActualAmount(tarR.getActualAmount().doubleValue());
							continue;
						}
						if (target.getAllowedVariationPercentage() != null) {
							double allowedDistance =  target.getAllowedVariationPercentage()
											.doubleValue()/100;
							double actualFormula = Math.abs(tarR.getActualAmount()-target.getTargetAmount().doubleValue())
															/target.getTargetAmount().doubleValue();
							
							if(actualFormula <= allowedDistance) {
								tarR.setResultFlag("Y");
							}
						}
						tarR.setActualAmount(tarR.getActualAmount().doubleValue());
					}

				} else if (target.getChangePercentage() != null) {
					
					for (TargetResult tarR : trList) {
						if(tarR.getOrder()==countNumber){
							trList=backNearObject(tarR,trList,target);
							break;
						}
					}
					

				}else if (target.getChangeAmount() != null) {
					for (TargetResult tarR : trList) {
						if(tarR.getOrder()==countNumber){
							trList=backNearObject(tarR,trList,target);
							break;
						}
					}
				}
			}else{
				if(target.getChangeAmount() != null || target.getChangeAmount() != null){
					trList.remove(trList.size() - 1);
				}
			}


			for (TargetResult tar : trList) {
				if (newTargetResult) {
					tar.setCreatedBy(SystemUtil.getCurrentUserId());
					tar.setCreatedTimestamp(new Date());
				}
				tar.setModifiedBy(SystemUtil.getCurrentUserId());
				tar.setModifiedTimestamp(new Date());
				targetDao.save(tar);
			}
		}	
		}
		return null;
	}
	
}
