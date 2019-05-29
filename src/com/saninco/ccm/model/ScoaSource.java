package com.saninco.ccm.model;
import java.util.Set;

public class ScoaSource extends AbstractScoaSource {
	// Constructors

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** default constructor */
	public ScoaSource() {
	}

	/** full constructor */
	public ScoaSource(Integer id, String scoaSourceName,Set proposals) {
		super(id, scoaSourceName,proposals);
	}
}
