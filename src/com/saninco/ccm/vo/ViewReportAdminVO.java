/**
 * 
 */
package com.saninco.ccm.vo;

import java.io.Serializable;

/**
 * @author xinyu.Liu
 */
public class ViewReportAdminVO extends SearchVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4891774932870661027L;

	private String tagsId;
	private String tagsName;
	private String tagsColor;

	private String roleId;
	private String accessLevel;
	private String email;

	private Integer rtagId;
	private String optionParamer;

	public ViewReportAdminVO() {
	}

	public Integer getRtagId() {
		return rtagId;
	}

	public void setRtagId(Integer rtagId) {
		this.rtagId = rtagId;
	}

	public String getOptionParamer() {
		return optionParamer;
	}

	public void setOptionParamer(String optionParamer) {
		this.optionParamer = optionParamer;
	}

	public String getTagsName() {
		return tagsName;
	}

	public void setTagsName(String tagsName) {
		this.tagsName = tagsName;
	}

	public String getTagsColor() {
		return tagsColor;
	}

	public void setTagsColor(String tagsColor) {
		this.tagsColor = tagsColor;
	}

	public String getTagsId() {
		return tagsId;
	}

	public void setTagsId(String tagsId) {
		this.tagsId = tagsId;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getAccessLevel() {
		return accessLevel;
	}

	public void setAccessLevel(String accessLevel) {
		this.accessLevel = accessLevel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String toString() {
		return "ViewReportAdminVO [accessLevel=" + accessLevel + ", email="
				+ email + ", optionParamer=" + optionParamer + ", roleId="
				+ roleId + ", rtagId=" + rtagId + ", tagsColor=" + tagsColor
				+ ", tagsId=" + tagsId + ", tagsName=" + tagsName + "]";
	}

}
