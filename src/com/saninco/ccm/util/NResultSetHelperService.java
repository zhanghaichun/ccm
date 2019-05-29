package com.saninco.ccm.util;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import au.com.bytecode.opencsv.ResultSetHelper;


public class NResultSetHelperService implements ResultSetHelper {


    public static final int CLOBBUFFERSIZE = 2048;
    private static final int NVARCHAR = -9;
    private static final int NCHAR = -15;
    private static final int LONGNVARCHAR = -16;
    private static final int NCLOB = 2011;
    
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
	public String[] getColumnNames(ResultSet resultset) throws SQLException {
		ArrayList arraylist = new ArrayList();
		ResultSetMetaData resultsetmetadata = resultset.getMetaData();
		for (int i = 0; i < resultsetmetadata.getColumnCount(); i++)
			arraylist.add(resultsetmetadata.getColumnLabel(i + 1));

		String as[] = new String[arraylist.size()];
		return (String[]) arraylist.toArray(as);
	}
	

    public String[] getColumnValues(ResultSet resultset)
        throws SQLException, IOException
    {
        ArrayList arraylist = new ArrayList();
        ResultSetMetaData resultsetmetadata = resultset.getMetaData();
        for(int i = 0; i < resultsetmetadata.getColumnCount(); i++)
            arraylist.add(getColumnValue(resultset, resultsetmetadata.getColumnType(i + 1), i + 1));

        String as[] = new String[arraylist.size()];
        return (String[])arraylist.toArray(as);
    }

    private String handleObject(Object obj)
    {
        return obj != null ? String.valueOf(obj) : "";
    }

    private String handleBigDecimal(BigDecimal bigdecimal)
    {
        return bigdecimal != null ? bigdecimal.toString() : "";
    }

    private String handleLong(ResultSet resultset, int i)
        throws SQLException
    {
        long l = resultset.getLong(i);
        return resultset.wasNull() ? "" : Long.toString(l);
    }

    private String handleInteger(ResultSet resultset, int i)
        throws SQLException
    {
        int j = resultset.getInt(i);
        return resultset.wasNull() ? "" : Integer.toString(j);
    }

    private String handleDate(ResultSet resultset, int i)
        throws SQLException
    {
        Date date = resultset.getDate(i);
        String s = null;
        if(date != null)
        {
            s = DATE_FORMAT.format(date);
        }
        return s;
    }

    private String handleTime(Time time)
    {
        return time != null ? time.toString() : null;
    }

    private String handleTimestamp(Timestamp timestamp)
    {
        return timestamp != null ? DATETIME_FORMAT.format(timestamp) : null;
    }

    private String getColumnValue(ResultSet resultset, int i, int j)
        throws SQLException, IOException
    {
        String s = "";
        switch(i)
        {
        case -7: 
        case 2000: 
            s = handleObject(resultset.getObject(j));
            break;

        case 16: // '\020'
            boolean flag = resultset.getBoolean(j);
            s = Boolean.valueOf(flag).toString();
            break;

        case 2005: 
        case NCLOB: 
            Clob clob = resultset.getClob(j);
            if(clob != null)
                s = read(clob);
            break;

        case -5: 
            s = handleLong(resultset, j);
            break;

        case 2: // '\002'
        case 3: // '\003'
        case 6: // '\006'
        case 7: // '\007'
        case 8: // '\b'
            s = handleBigDecimal(resultset.getBigDecimal(j));
            break;

        case -6: 
        case 4: // '\004'
        case 5: // '\005'
            s = handleInteger(resultset, j);
            break;

        case 91: // '['
            s = handleDate(resultset, j);
            break;

        case 92: // '\\'
            s = handleTime(resultset.getTime(j));
            break;

        case 93: // ']'
            s = handleTimestamp(resultset.getTimestamp(j));
            break;

        case LONGNVARCHAR: 
        case NCHAR: 
        case NVARCHAR: 
        case -1: 
        case 1: // '\001'
        case 12: // '\f'
            s = resultset.getString(j);
            break;

        default:
            s = "";
            break;
        }
        if(s == null)
            s = "";
        return s;
    }

    private static String read(Clob clob)
        throws SQLException, IOException
    {
        StringBuilder stringbuilder = new StringBuilder((int)clob.length());
        Reader reader = clob.getCharacterStream();
        char ac[] = new char[CLOBBUFFERSIZE];
        int i;
        while((i = reader.read(ac, 0, ac.length)) != -1) 
            stringbuilder.append(ac, 0, i);
        return stringbuilder.toString();
    }

}
