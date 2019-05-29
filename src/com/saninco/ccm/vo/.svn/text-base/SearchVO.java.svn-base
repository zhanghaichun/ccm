package com.saninco.ccm.vo;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Jian.Dong
 */
public class SearchVO implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	// private final String characterSet = "UTF-8";

	// the record number per page
	private int recPerPage;
	// the page number
	private int pageNo;
	// the sorting field
	private String sortField;
	// the sorting order
	private String sortingDirection;

	private String filter;

	/**
	 * @return the filter
	 */
	public String getFilter() {
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 */
	public void setFilter(String filter) {
		this.filter = filter;
	}

	public SearchVO() {
	}

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * @return the startIndex
	 */
	public int getStartIndex() {
		return (this.pageNo - 1) * this.recPerPage;
	}

	/**
	 * get the totalPageNoJson
	 */
	public String getTotalPageNoJson(long totalResult) {
		StringBuilder sb = new StringBuilder();
		sb.append("{totalResultNo:");
		sb.append(totalResult);
		sb.append(",totalPageNo:");
		int tp = (int) Math.ceil((double) totalResult
				/ (double) this.recPerPage);
		sb.append(tp);
		sb.append("}");
		return sb.toString();
	};

	/**
	 * get the seatchResultJson
	 */
	public String getListJson(List<String> list) {
		StringBuilder sb = new StringBuilder();
		clearList(list);
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(this.getStartIndex() + 1);
			sb.append(",end:");
			sb.append(this.getStartIndex() + list.size());
			sb.append(",data:[");
			for (int i = 0; i < list.size(); i++) {
				if (i != 0)
					sb.append(",");
				sb.append(list.get(i).toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * by one page
	 */
	public String getListJsonCompatibleByAll(List list) throws Exception {
		StringBuilder sb = new StringBuilder();
		clearList(list);
		if (list != null && list.size() > 0) {
			sb.append("{");
			sb.append("data:[");
			for (int i = 0; i < list.size(); i++) {
				Object str = list.get(i);
				if (str instanceof Blob) {
					str = getBlobString((Blob) str);
				} else if (str instanceof String) {
					// String is defult
				} else {
					throw new Exception("This type is not compatible");
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		return sb.toString();
	}

	private void clearList(List list) {
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) == null) {
				list.remove(i);
				i--;
			}
		}
	}

	/**
	 * get the seatchResultJson<br/>
	 * The type currently allowed is List<String>,List<Blob>
	 * 
	 * @throws IOException
	 * @throws SQLException
	 */
	public String getListJsonCompatible(List list) throws Exception {
		StringBuilder sb = new StringBuilder();
		clearList(list);
		if (list != null && list.size() > 0) {
			sb.append("{begin:");
			sb.append(this.getStartIndex() + 1);
			sb.append(",end:");
			sb.append(this.getStartIndex() + list.size());
			sb.append(",data:[");
			for (int i = 0; i < list.size(); i++) {
				Object str = list.get(i);
				if (str instanceof Blob) {
					str = getBlobString((Blob) str);
				} else if (str instanceof String) {
					// String is defult
				} else {
					throw new Exception("This type is not compatible");
				}
				if (i != 0)
					sb.append(",");
				sb.append(str.toString());
			}
			sb.append("]");
		} else {
			sb.append("{count:0");
		}
		sb.append("}");
		return sb.toString();
	}

	/**
	 * get the OrderBy Cause
	 */
	public String getOrderByCause(String alias) {
		if (this.getSortField() != null && !"".equals(this.getSortField())
				&& this.getSortingDirection() != null
				&& !"".equals(this.getSortingDirection())) {
			
			if("invoice_label".equals(this.getSortField())){
				
				return " order by (select count(1) from invoice_label il where il.invoice_id = i.id and il.rec_active_flag = 'Y') "
				+ this.getSortingDirection() + " ";
			}else{
				if (alias != null && !"".equals(alias) && getSortField().indexOf(".") == -1) {
					return " order by " + alias + "." + this.getSortField() + " "
					+ this.getSortingDirection() + " ";
				} else {
					return " order by " + this.getSortField() + " "
					+ this.getSortingDirection() + " ";
				}
			}
			
		} else {
			return " ";
		}
	}
	/**
	 * get the OrderBy Cause
	 */
	public String getOrderBy(String alias) {
		if (this.getSortField() != null && !"".equals(this.getSortField())
				&& this.getSortingDirection() != null
				&& !"".equals(this.getSortingDirection())) {
			if (alias != null && !"".equals(alias)) {
				return alias + "." + this.getSortField() + " "
						+ this.getSortingDirection();
			} else {
				return this.getSortField() + " "
						+ this.getSortingDirection() + " ";
			}
		} else {
			return " ";
		}
	}
	/**
	 * 
	 * @author Chao.Liu Date:Sep 8, 2010
	 * @param string
	 * @return
	 */
	public String getBlobContent(Blob b) {
		StringBuilder sb = new StringBuilder();
		try {
			BufferedInputStream bis = new BufferedInputStream(b.getBinaryStream());
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
	
	/**
	 * get the Limit Cause
	 */
	public String getLimitCause() {
		return " LIMIT " + this.getStartIndex() + "," + this.getRecPerPage();
	}

	public static final String getBlobString(Blob b) throws SQLException,
			IOException {
		StringBuilder sb = new StringBuilder();
		BufferedInputStream bis = new BufferedInputStream(b.getBinaryStream());
		int len = (int) b.length();
		byte[] bt = new byte[len];
		int readLen = 0;
		while ((readLen = bis.read(bt)) != -1) {
			sb.append(new String(bt, 0, readLen));
		}
		return sb.toString();
	}

	public int getRecPerPage() {
		return recPerPage;
	}

	public void setRecPerPage(int recPerPage) {
		this.recPerPage = recPerPage;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getSortingDirection() {
		return sortingDirection;
	}

	public void setSortingDirection(String sortingDirection) {
		this.sortingDirection = sortingDirection;
	}

	/**
	 * 此方法中使用的一些参数的值都是从前台获取到的。
	 * @return [description]
	 */
	public String toString() {
		return "SearchVO [filter=" + filter + ", pageNo=" + pageNo
				+ ", recPerPage=" + recPerPage + ", sortField=" + sortField
				+ ", sortingDirection=" + sortingDirection + "]";
	}

}
