package com.saninco.ccm.dao;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

public class ApDaoImpl extends HibernateDaoSupport implements IApDao {
	
	private final Logger logger = Logger.getLogger(this.getClass());
	public ApDaoImpl() {
	}
	

	private String getHeaderQueryString(){
		logger.info("Enter method getHeaderQueryString.");
		//Removed if(vendor_name is null,'',upper(vendor_name)),after vendor_num, if(terms_name is null,'',terms_name), before description.
		//Removed ,if(invoice_currency_code is null,'',invoice_currency_code) after replace(format(payment_amount,2), ',', ''))),'|'.
		StringBuffer sb = new StringBuffer("select concat('H','||',if(invoice_number is null,'',invoice_number),'|',if(payment_amount<0,'CREDIT','STANDARD'),'|',if(invoice_date is null,'',invoice_date),'|||',");						
		sb.append("if(vendor_num is null,'',vendor_num),'|','||',if(vendor_site_code is null,'',upper(vendor_site_code)),'|',");
		sb.append("if(payment_amount is null,'',concat(if(payment_amount<0,'','+'),replace(format(payment_amount,2), ',', ''))),'|','|||||','IMMEDIATE|',if(description is null,'',description),'|||||||||||||||||||',");
		sb.append("'TEMS','|',if(ban is null,'',ban),'||||||||||||||||||||||||||','TEMS','|',if(payment_method_lookup_code = 'WIRE','HOLD',''),'|||||||||','|',if(pay_group_lookup_code is null,'',pay_group_lookup_code),'||',if(invoice_receive_date is null,'',invoice_receive_date),'|',curdate(),'|||||||||||||||||||||||||||||||||||||||||||||||') a ");
		sb.append("from ap_header_view i ");
		sb.append("where i.payment_status_id = 20 ");  
		sb.append("order by i.payment_id asc;");

		logger.info("Exit method getHeaderQueryString.");
		return sb.toString();
	}

	private String getDetailQueryString(int currentPaymentId){
		logger.info("Enter method getDetailQueryString.");
		
		StringBuffer sb = new StringBuffer("select concat('D','|||',");
		sb.append("'|',if(line_type_lookup_code is null,'',upper(line_type_lookup_code)),'||',if(amount is null,'',concat(if(amount<0,'','+'),replace(format(amount,2), ',', ''))),'||',if(description is null,'',description),'|||',");						
		sb.append("if(((tax_code is null) or (tax_code = 'USTAX')),'',tax_code),'||||||||||||||||||',if(scoa is null,'',scoa),'||||||||||||||||||||','TEMS','|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||') a ");
		sb.append("from ap_detail_view i ");
		sb.append("where i.payment_id = " + currentPaymentId +" ");  
		sb.append("order by i.payment_detail_id asc;");
		
		logger.info("Exit method getDetailQueryString.");
		return sb.toString();
	}
	
	private String getTrailerQueryString(){
		logger.info("Enter method getTrailerQueryString.");
		
		StringBuffer sb = new StringBuffer("select concat('T','|',count(distinct payment_id),'|',if(sum(payment_amount) is null,'',concat(if(sum(payment_amount)<0,'','+'),replace(format(sum(payment_amount),2), ',', ''))),'|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||') a ");
		sb.append("from ap_header_view i ");
		sb.append("where i.payment_status_id = 21 ");  
		sb.append("order by i.vendor_name asc;");

		logger.info("Exit method getTrailerQueryString.");
		return sb.toString();
	}
	
	
	
	private String getDirectoryQueryString(){
		logger.info("Enter method getDirectoryQueryString.");
		
		/* Example of Directory: sys_config, ap_outbound_file_path
		*/ 
		StringBuffer sb = new StringBuffer("select value ");
		sb.append("from sys_config s "); 
		sb.append("where parameter = 'ap_outbound_file_path';"); 
		logger.info("Exit method getDirectoryQueryString.");
		return sb.toString();
	}
	
	
	private String getInboundDirectoryQueryString(){
		logger.info("Enter method getInboundDirectoryQueryString.");
		
		/* Example of Directory: sys_config, ap_outbound_file_path
		*/ 
		StringBuffer sb = new StringBuffer("select value ");
		sb.append("from sys_config s "); 
		sb.append("where parameter = 'ap_inbound_file_path';"); 
		logger.info("Exit method getInboundDirectoryQueryString.");
		return sb.toString();
	}
	
	
	private String getBatchIdQueryString(){
		logger.info("Enter method getBatchIdQueryString.");	

		StringBuffer sb = new StringBuffer("select id ");
		sb.append("from payment_batch pb "); 
		sb.append("where created_timestamp = (select max(created_timestamp) from payment_batch);"); 
		logger.info("Exit method getBatchIdQueryString.");
		return sb.toString();
	}
	
	
	private String getAPPaymentIdsQueryString(int currentPaymentStatus){
		logger.info("Enter method getAPPaymentIdsQueryString.");
 
		StringBuffer sb = new StringBuffer("select payment_id ");
		sb.append("from ap_header_view i ");
		sb.append("where i.payment_status_id = " +currentPaymentStatus+ " ");  
		sb.append("order by i.payment_id asc;");
		
		logger.info("Exit method getAPPaymentIdsQueryString.");
		return sb.toString();
	}
	
	private String getPaymentDetailIdsQueryString(int currentPaymentId){
		logger.info("Enter method getPaymentDetailIdsQueryString.");
 
		StringBuffer sb = new StringBuffer("select payment_detail_id ");
		sb.append("from ap_detail_view i ");
		sb.append("where i.payment_id = " +currentPaymentId +" "); 
		sb.append("order by i.payment_detail_id asc;");
		logger.info("Exit method getPaymentDetailIdsQueryString.");
		return sb.toString();
	}
	
	

	private String getAPConfirmationsQueryString(){
		logger.info("Enter method getAPConfirmationsQueryString.");
		StringBuffer sb = new StringBuffer("select arv.payment_id ");
		sb.append("from ap_remittance_view arv ");
		sb.append("order by arv.payment_id asc;");
		logger.info("Exit method getAPConfirmationsQueryString.");
		return sb.toString();
		
	}

	private String getRemittanceIdsQueryString(String currentStatus){
		logger.info("Enter method getRemittanceIdsQueryString.");
		StringBuffer sb = new StringBuffer("select id ");
		sb.append("from remittance re ");
		sb.append("where re.remittance_status = " +currentStatus +" "); 
		sb.append("order by re.id asc;");
		logger.info("Exit method getRemittanceIdsQueryString.");
		return sb.toString();
	}

	private String getCheckNumberByIdQueryString(int currentPaymentId){
		logger.info("Enter method getCheckNumberByIdQueryString.");
		StringBuffer sb = new StringBuffer("select distinct r.payment_reference_code as payment_reference_code ");
		sb.append("from ((payment p join invoice i on (p.invoice_id = i.id)) join remittance r on (r.invoice_number = i.invoice_number)) ");
		sb.append("where (p.rec_active_flag = 'Y') and (p.payment_status_id = 23) and (r.remittance_status = 3) and (p.id = " +currentPaymentId +"); "); 
		logger.info("Exit method getCheckNumberByIdQueryString.");
		return sb.toString();
	}
	
	private String getPaidDateByIdQueryString(int currentPaymentId){
		logger.info("Enter method getPaidDateByIdQueryString.");
		StringBuffer sb = new StringBuffer("select distinct r.paid_date as paid_date ");
		sb.append("from ((payment p join invoice i on (p.invoice_id = i.id)) join remittance r on (r.invoice_number = i.invoice_number)) ");
		sb.append("where (p.rec_active_flag = 'Y') and (p.payment_status_id = 23) and (r.remittance_status = 3) and (p.id = " +currentPaymentId +"); "); 
		logger.info("Exit method getPaidDateByIdQueryString.");
		return sb.toString();
	}
	
	
	private String getAPExceptionInvoiceQueryString(){
		logger.info("Enter method getAPConfirmationsQueryString.");
		StringBuffer sb = new StringBuffer("select distinct aiev.remittance_id ");
		sb.append("from ap_invoice_exception_view aiev ");
		sb.append("where aiev.payment_id is null ");
		sb.append("order by aiev.remittance_id asc;");
		logger.info("Exit method getAPConfirmationsQueryString.");
		return sb.toString();
	}

	private String getAPExceptionStatusQueryString(){
		logger.info("Enter method getAPExceptionStatusQueryString.");
		StringBuffer sb = new StringBuffer("select distinct auev.payment_id ");
		sb.append("from ap_status_exception_view auev ");
		sb.append("order by auev.payment_id asc;");
		logger.info("Exit method getAPExceptionStatusQueryString.");
		return sb.toString();
	}

	
	private String getAPExceptionAmountQueryString(){
		logger.info("Enter method getAPConfirmationsQueryString.");
		StringBuffer sb = new StringBuffer("select distinct arv.payment_id ");
		sb.append("from ap_remittance_view arv ");
		sb.append("where arv.feed_payment_amount <> arv.re_payment_amount ");
		sb.append("order by arv.payment_id asc;");
		logger.info("Exit method getAPConfirmationsQueryString.");
		return sb.toString();
	}

	private String getAPExceptionSupplierQueryString(){
		
		logger.info("Enter method getAPConfirmationsQueryString.");
		StringBuffer sb = new StringBuffer("select distinct asev.payment_id ");
		sb.append("from ap_supplier_exception_view asev ");
		sb.append("order by asev.payment_id asc;");
		logger.info("Exit method getAPConfirmationsQueryString.");
		return sb.toString();
	}

	private String getAPExceptionStatusReQueryString(){
		logger.info("Enter method getAPExceptionStatusReQueryString.");
		StringBuffer sb = new StringBuffer("select distinct auev.remittance_id ");
		sb.append("from ap_status_exception_view auev ");
		sb.append("order by auev.remittance_id asc;");
		logger.info("Exit method getAPExceptionStatusReQueryString.");
		return sb.toString();
	}

	
	private String getAPExceptionMultiPayReQueryString(){
		logger.info("Enter method getAPExceptionMultiPayReQueryString.");
		StringBuffer sb = new StringBuffer("select distinct arv.remittance_id ");
		sb.append("from ap_remittance_view arv ");
		sb.append("group by arv.re_invoice_number ");
		sb.append("having count(payment_id) >1 ");
		sb.append("order by arv.remittance_id asc;");
		logger.info("Exit method getAPExceptionMultiPayReQueryString.");
		return sb.toString();
	}

	
	private String getAPExceptionAmountReQueryString(){
		logger.info("Enter method getAPConfirmationsReQueryString.");
		StringBuffer sb = new StringBuffer("select distinct arv.remittance_id ");
		sb.append("from ap_remittance_view arv ");
		sb.append("where arv.feed_payment_amount <> arv.re_payment_amount ");
		sb.append("order by arv.remittance_id asc;");
		logger.info("Exit method getAPConfirmationsReQueryString.");
		return sb.toString();
	}

	private String getAPExceptionSupplierReQueryString(){
		
		logger.info("Enter method getAPConfirmationsReQueryString.");
		StringBuffer sb = new StringBuffer("select distinct asev.remittance_id ");
		sb.append("from ap_supplier_exception_view asev ");
		sb.append("order by asev.remittance_id asc;");
		logger.info("Exit method getAPConfirmationsReQueryString.");
		return sb.toString();
	}
	

	@SuppressWarnings("unchecked")
	public String getApOutboundFeedDirectory() throws SQLException{
		logger.info("Enter method getApOutboundFeedDirectory.");
		final String sql = this.getDirectoryQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getApOutboundFeedDirectory.");
		return l.get(0).toString();
	}
	

	
	@SuppressWarnings("unchecked")
    public int getAPBatchId() throws SQLException{
		
		logger.info("Enter method getAPBatchId.");
		final String sql = this.getBatchIdQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getAPBatchId.");
		return l.get(0);
	}
		
	
	@SuppressWarnings("unchecked")
    public int[] getAPPaymentIds(int currentPaymentStatus) throws SQLException{
		
		logger.info("Enter method getAPPaymentIds.");
		
		final String sql = this.getAPPaymentIdsQueryString(currentPaymentStatus);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		
		int[] apPaymentIds = new int[l.size()];

		//List<String> l to int[]
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      apPaymentIds[index] = item;
		    }
		logger.info("Exit method getAPPaymentIds.");
		return apPaymentIds;
		
		
	}
	

	@SuppressWarnings("unchecked")
	public List<String> getListOfHeaders()throws SQLException{
		logger.info("Enter method getListOfHeaders.");
		/*
		int numOfHeaders =0;
		List <String> result  =  new  ArrayList <String>();
		*/ 
		final String sql = this.getHeaderQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		/*
		numOfHeaders = l.size();
		
		for(int i = 0; i<numOfHeaders; i++){
			       java.sql.Blob wrapBlob = l.get(i).getWrappedBlob();
			       byte[] bdata = wrapBlob.getBytes(1, (int) wrapBlob.length());
			       String text = new String(bdata);
		           System.out.println(text);
		           result.add(i,text);
		}
		*/
		logger.info("Exit method getListOfHeaders.");
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getListOfDetails(int invoice_id)throws SQLException{
		logger.info("Enter method getListOfDetails.");
		final String sql = this.getDetailQueryString(invoice_id);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		
		logger.info("Exit method getListOfDetails.");
		return l;
	}
	
	@SuppressWarnings("unchecked")
	public int[] getPaymentDetailIds(int invoice_id) throws SQLException{
		
		logger.info("Enter method getPaymentDetailIds.");
		final String sql = this.getPaymentDetailIdsQueryString(invoice_id);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});

		//List<String> l to int[]
		int[] paymentDetailIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      paymentDetailIds[index] = item;
		    }
        logger.info("Exit method getPaymentDetailIds.");
		return paymentDetailIds;
		
	}
	
	
	@SuppressWarnings("unchecked")
	public String getTrailer()throws SQLException{
		logger.info("Enter method getListOfTrailers.");
		final String sql = this.getTrailerQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getListOfTrailers.");
		return l.get(0).toString();
	}
	
	@SuppressWarnings("unchecked")
	public String getApInboundFeedDirectory() throws SQLException{
		logger.info("Enter method getApInboundFeedDirectory.");
		final String sql = this.getInboundDirectoryQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getApInboundFeedDirectory.");
		return l.get(0).toString();
	}
	
	@SuppressWarnings("unchecked")
	public int[] getAPConfirmations() throws SQLException{
		logger.info("Enter method getAPConfirmations.");
		final String sql = this.getAPConfirmationsQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		//List<String> l to int[]
		int[] paymentIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      paymentIds[index] = item;
		}
		logger.info("Exit method getAPConfirmations.");
		return paymentIds;
	}
	
	@SuppressWarnings("unchecked")
	public int[] getRemittanceIdsByStatus(String currentStatus) throws SQLException{
		logger.info("Enter method getRemittanceIdsByStatus.");
		final String sql = this.getRemittanceIdsQueryString(currentStatus);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		//List<String> l to int[]
		int[] remittanceIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      remittanceIds[index] = item;
		}
		logger.info("Exit method getRemittanceIdsByStatus.");
		return remittanceIds;
	}
	
	@SuppressWarnings("unchecked")
	public String getCheckNumberById(int currentPaymentId) throws SQLException{
		logger.info("Enter method getCheckNumberById.");
		final String sql = this.getCheckNumberByIdQueryString(currentPaymentId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<String> l = (List<String>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getCheckNumberById.");
		return l.get(0).toString();
	}
	
	
	
	@SuppressWarnings("unchecked")
	public Date getPaidDateById(int currentPaymentId) throws SQLException, ParseException{
		logger.info("Enter method getPaidDateById.");
		//SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		final String sql = this.getPaidDateByIdQueryString(currentPaymentId);
		HibernateTemplate template = this.getHibernateTemplate();
		List<Date> l = (List<Date>) template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		logger.info("Exit method getPaidDateById with date:" + l.get(0));
		return l.get(0);
	}
	
	
	@SuppressWarnings("unchecked")
	public int[] getAPExceptionInvoice() throws SQLException{
		logger.info("Enter method getAPExceptionInvoice.");
		final String sql = this.getAPExceptionInvoiceQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		//List<String> l to int[]
		int[] remittanceIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      remittanceIds[index] = item;
		}
		logger.info("Exit method getAPExceptionInvoice.");
		return remittanceIds;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public int[] getAPExceptionStatus() throws SQLException{
		logger.info("Enter method getAPExceptionStatus.");
		final String sql = this.getAPExceptionStatusQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		//List<String> l to int[]
		int[] paymentIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      paymentIds[index] = item;
		}
		logger.info("Exit method getAPExceptionStatus.");
		return paymentIds;
	}
	
	
	@SuppressWarnings("unchecked")
	public int[] getAPExceptionAmount() throws SQLException{
		logger.info("Enter method getAPExceptionAmount.");
		final String sql = this.getAPExceptionAmountQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		//List<String> l to int[]
		int[] paymentIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      paymentIds[index] = item;
		}
		logger.info("Exit method getAPExceptionAmount.");
		return paymentIds;
	}
	
	
	@SuppressWarnings("unchecked")
	public int[] getAPExceptionSupplier() throws SQLException{
		logger.info("Enter method getAPExceptionSupplier.");
		final String sql = this.getAPExceptionSupplierQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		//List<String> l to int[]
		int[] paymentIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      paymentIds[index] = item;
		}
		logger.info("Exit method getAPExceptionSupplier.");
		return paymentIds;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public int[] getAPExceptionStatusRe() throws SQLException{
		logger.info("Enter method getAPExceptionStatusRe.");
		final String sql = this.getAPExceptionStatusReQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		//List<String> l to int[]
		int[] remittanceIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      remittanceIds[index] = item;
		}
		logger.info("Exit method getAPExceptionStatusRe.");
		return remittanceIds;
	}
	
	
	
	@SuppressWarnings("unchecked")
	public int[] getAPExceptionMultiPayRe() throws SQLException{
		logger.info("Enter method getAPExceptionMultiPayRe.");
		final String sql = this.getAPExceptionMultiPayReQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		//List<String> l to int[]
		int[] remittanceIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      remittanceIds[index] = item;
		}
		logger.info("Exit method getAPExceptionMultiPayRe.");
		return remittanceIds;
	}
	
	@SuppressWarnings("unchecked")
	public int[] getAPExceptionAmountRe() throws SQLException{
		logger.info("Enter method getAPExceptionAmountRe.");
		final String sql = this.getAPExceptionAmountReQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		//List<String> l to int[]
		int[] remittanceIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      remittanceIds[index] = item;
		}
		logger.info("Exit method getAPExceptionAmountRe.");
		return remittanceIds;
	}
	
	
	@SuppressWarnings("unchecked")
	public int[] getAPExceptionSupplierRe() throws SQLException{
		logger.info("Enter method getAPExceptionSupplierRe.");
		final String sql = this.getAPExceptionSupplierReQueryString();
		HibernateTemplate template = this.getHibernateTemplate();
		List<Integer> l = (List<Integer>)template.execute(new HibernateCallback() {
		    public Object doInHibernate(Session session) throws HibernateException, SQLException {
		    	return session.createSQLQuery(sql).list();
		    }
		});
		//List<String> l to int[]
		int[] remittanceIds = new int[l.size()];
        for(int index =0; index<l.toArray().length; index++){
		      Integer item = l.get(index);
		      remittanceIds[index] = item;
		}
		logger.info("Exit method getAPExceptionSupplierRe.");
		return remittanceIds;
	}
	


}
