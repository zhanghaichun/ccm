package com.saninco.ccm.model;

/**
 * AbstractUserCrossApp entity provides the base persistence definition of the
 * UserCrossApp entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserCrossApp implements java.io.Serializable {

	// Fields

	private Integer id;
	private User user;
	private String wikiUsername;
	private String wikiPassword;

	// Constructors

	/** default constructor */
	public AbstractUserCrossApp() {
	}

	/** minimal constructor */
	public AbstractUserCrossApp(Integer id) {
		this.id = id;
	}

	/** full constructor */
	public AbstractUserCrossApp(Integer id, User user, String wikiUsername,
			String wikiPassword) {
		this.id = id;
		this.user = user;
		this.wikiUsername = wikiUsername;
		this.wikiPassword = wikiPassword;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getWikiUsername() {
		return this.wikiUsername;
	}

	public void setWikiUsername(String wikiUsername) {
		this.wikiUsername = wikiUsername;
	}

	public String getWikiPassword() {
		return this.wikiPassword;
	}

	public void setWikiPassword(String wikiPassword) {
		this.wikiPassword = wikiPassword;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AbstractUserCrossApp [");
		if (id != null)
			builder.append("id=").append(id).append(", ");
		if (user != null)
			builder.append("user=").append(user).append(", ");
		if (wikiPassword != null)
			builder.append("wikiPassword=").append(wikiPassword).append(", ");
		if (wikiUsername != null)
			builder.append("wikiUsername=").append(wikiUsername);
		builder.append("]");
		return builder.toString();
	}
}