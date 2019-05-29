package com.saninco.ccm.model;

import java.util.Set;

/**
 * Currency entity.
 * 
 * @author MyEclipse Persistence Tools
 */
public class Currency extends AbstractCurrency implements java.io.Serializable {

	// Constructors

	/** default constructor */
	public Currency() {
	}

	/** full constructor */
	public Currency(String currencyName, String currencyDescription, Set bans) {
		super(currencyName, currencyDescription, bans);
	}
	/**
	 * 
	 * @Auchor Chao.Liu | On Date: Nov 8, 2010
	 * @param Id
	 */
	public Currency(Integer id) {
		this.setId(id);
	}

}
