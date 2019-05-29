/**
 * 
 */
package com.saninco.ccm.model.type;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * @author Joe.Yang
 *
 */
public class BlobType implements UserType, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -75868516178142989L;

	/**
	 * 
	 */
	public BlobType() {
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#assemble(java.io.Serializable, java.lang.Object)
	 */
	
	public Object assemble(Serializable cached, Object owner)
			throws HibernateException {
		return((BlobType)cached); 
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#deepCopy(java.lang.Object)
	 */
	public Object deepCopy(Object value) throws HibernateException {
		if (value == null) return null; 

		byte[] bytes = (byte[]) value; 
		byte[] result = new byte[bytes.length]; 
		System.arraycopy(bytes, 0, result, 0, bytes.length); 

		return result; 
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#disassemble(java.lang.Object)
	 */
	public Serializable disassemble(Object value) throws HibernateException {
		byte[] bytes = (byte[]) value; 
		return(bytes); 
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#equals(java.lang.Object, java.lang.Object)
	 */
	public boolean equals(Object x, Object y) throws HibernateException {
		return (x == y) 
		|| (x != null 
		&& y != null 
		&& java.util.Arrays.equals((byte[]) x, (byte[]) y)); 
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#hashCode(java.lang.Object)
	 */
	public int hashCode(Object arg0) throws HibernateException {
		return (0); 
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#isMutable()
	 */
	public boolean isMutable() {
		return true; 
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeGet(java.sql.ResultSet, java.lang.String[], java.lang.Object)
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner)
			throws HibernateException, SQLException {
		//Blob blob = rs.getBlob(names[0]); 
		//return blob.getBytes(1, (int) blob.length()); 
        InputStream blobReader = rs.getBinaryStream(names[0]);
        if (blobReader == null) return null;
        byte[] b = new byte[1024];

        ByteArrayOutputStream os = new ByteArrayOutputStream();

        try {
            while ((blobReader.read(b)) != -1) 
                os.write(b);
        } catch (IOException e) {
            throw new SQLException(e.toString());
        } finally {
            try {
                os.close();
            } catch (IOException e) {
            }
        }
        return os.toByteArray();
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#nullSafeSet(java.sql.PreparedStatement, java.lang.Object, int)
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index)
			throws HibernateException, SQLException {
		//st.setBlob(index, Hibernate.createBlob((byte[]) value));
		if(value==null){
			st.setNull(index, sqlTypes()[0]);
			return;
		}else{
			Blob b=Hibernate.createBlob((byte[]) value);
			st.setBlob(index, b); 
		}
/*		try{
			C3P0NativeJdbcExtractor cp30NativeJdbcExtractor = new C3P0NativeJdbcExtractor(); 
			Connection conn = (Connection) cp30NativeJdbcExtractor.getNativeConnection(st.getConnection()); 
			OutputStream tempBlobOutputStream = null;
            Blob tempBlob = new Blob();// .createTemporary(conn, true, BLOB.DURATION_SESSION);
            try {
                tempBlob.open(BLOB.MODE_READWRITE);
                tempBlobOutputStream = tempBlob.getBinaryOutputStream();
                tempBlobOutputStream.write((byte[])value);
                tempBlobOutputStream.flush();
            } finally {
                if (tempBlobOutputStream != null)
                    tempBlobOutputStream.close();
                tempBlobOutputStream.close();
            }
            st.setBlob(index, (Blob) tempBlob);
        } catch (IOException e) {
            throw new HibernateException(e);
        }*/
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#replace(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public Object replace(Object original, Object target, Object owner)
			throws HibernateException {
		return(original); 
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#returnedClass()
	 */
	public Class returnedClass() {
		return byte[].class; 
	}

	/* (non-Javadoc)
	 * @see org.hibernate.usertype.UserType#sqlTypes()
	 */
	public int[] sqlTypes() {
		return new int[] { Types.BLOB }; 
	}

}
