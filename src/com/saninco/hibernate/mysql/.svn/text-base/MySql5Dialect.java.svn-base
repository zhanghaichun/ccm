/**
 * 
 */
package com.saninco.hibernate.mysql;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQL5Dialect;

/**
 * Hibernate dialect created for MySql database.
 * 
 * @author Joe.Yang
 *
 */
public class MySql5Dialect extends MySQL5Dialect {

	/**
	 * 
	 */
	public MySql5Dialect() {
		super();
		registerHibernateType( Types.LONGVARBINARY, Hibernate.BLOB.getName() ); 
		registerHibernateType( Types.LONGVARCHAR, Hibernate.TEXT.getName() ); 
		registerHibernateType( Types.BOOLEAN, Hibernate.BOOLEAN.getName() ); 
	}

}
