package com.saninco.ccm.util;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;

import org.apache.log4j.Logger;

public class ReceivedEmailUtil {
	
	private MimeMessage mimeMessage = null;
	private Message[] messages = null;
    private StringBuffer bodyText = null;//存放邮件text内容   
    private StringBuffer bodyHtml = null;//存放邮件html内容   
    public final String EMAIL_ADDRESS_TO = "to"; //收件人
    public final String EMAIL_ADDRESS_CC = "cc"; //抄送人
    public final String EMAIL_ADDRESS_BCC = "bcc"; //密送人
    
    private List<Map<String ,Object>> attachmentList = new ArrayList<Map<String ,Object>>();
    private final Logger logger = Logger.getLogger(this.getClass());
    
	public ReceivedEmailUtil() {
		
	}
	
    public ReceivedEmailUtil(Message[] messages) {
        this.messages = messages;
    }
    public ReceivedEmailUtil(MimeMessage mimeMessage) {
    	setMimeMessage(mimeMessage);
    }
    public void setMessages(Message[] messages) {
        this.messages = messages;
    }
  
    public void setMimeMessage(MimeMessage mimeMessage) {
		this.mimeMessage = mimeMessage;
//		receivedEmail = new ReceivedEmail();
        try {
        	
        	bodyText = new StringBuffer();
	        bodyHtml = new StringBuffer();
	        getMailContent((Part) mimeMessage);
	        
	        attachmentList = new ArrayList<Map<String , Object>>();
	        saveAttachment((Part) mimeMessage);
	        
	       
	        /*
	         * 暂时不需要
			receivedEmail.setTo(getMailAddress(EMAIL_ADDRESS_TO));
			receivedEmail.setBcc(getMailAddress(EMAIL_ADDRESS_BCC));
	        receivedEmail.setCc(getMailAddress(EMAIL_ADDRESS_CC));
	        receivedEmail.setContentHtml(getContentHtml());
	        receivedEmail.setContentText(getContentText());
	        receivedEmail.setSubject(getSubject());
	        receivedEmail.setSentDatetime(getSentDate());
	        receivedEmail.setReplySign(getReplySign() ? "Y" : "N");
	        receivedEmail.setMessageId(getMessageId());
	        receivedEmail.setFrom(getFrom());
	        receivedEmail.setDescription(getDescription());
	        receivedEmail.setInReplyTo(getInReplyTo());
	        receivedEmail.setReferences(getReferences());
	        
	        receivedEmailList.add(receivedEmail);
	        */
		} catch (Exception e) {
			e.printStackTrace();
		}
        
	}

	/**
     * 获取发件人
     * @return String
     * @throws Exception
     */
    public String getFrom() throws Exception {
        InternetAddress address[] = (InternetAddress[]) mimeMessage.getFrom();
        String from = address[0].getAddress();
        if (from == null)   
            from = "";
        String personal = address[0].getPersonal();
        if (personal == null)   
            personal = "";
        String fromAddress = personal + "(" + from + ")";
        return fromAddress;
    }
  
    /**
     * 获取reply to
     * @return String
     * @throws MessagingException
     */
	public String getReplyTo() throws MessagingException {
		
		InternetAddress address[] = (InternetAddress[]) mimeMessage.getReplyTo();
		String replyTo = address[0].getAddress();
		if (replyTo == null)
			replyTo = "";
		String personal = address[0].getPersonal();
		if (personal == null)
			personal = "";
		String replyToAddress = personal + "(" + replyTo + ")";
		return replyToAddress;
	}
    
    /**
     * 获取收邮件人信息
     * @param type EMAIL_ADDRESS_TO|EMAIL_ADDRESS_CC|EMAIL_ADDRESS_BCC
     * @return
     * @throws Exception
     */
    public String getMailAddress(String type) throws Exception {
    	
        String mailAddress = "";
        InternetAddress[] address = null;
        if (type.equals(EMAIL_ADDRESS_TO) || type.equals(EMAIL_ADDRESS_CC)|| type.equals(EMAIL_ADDRESS_BCC)) {
            if (type.equals(EMAIL_ADDRESS_TO)) {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.TO);
            } else if (type.equals(EMAIL_ADDRESS_CC)) {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.CC);
            } else {
                address = (InternetAddress[]) mimeMessage.getRecipients(Message.RecipientType.BCC);
            }
            if (address != null) {
                for (int i = 0; i < address.length; i++) {
                    String email = address[i].getAddress();
                    if (email == null)   
                        email = "";
                    else {
                        email = MimeUtility.decodeText(email);
                    }
                    String personal = address[i].getPersonal();
                    if (personal == null)   
                        personal = "";
                    else {
                        personal = MimeUtility.decodeText(personal);
                    }
                    String compositeto = personal + "(" + email + ")";
                    mailAddress += "," + compositeto;
                }
                mailAddress = mailAddress.substring(1);
            }
        } else {
            throw new Exception("Error email address type!");
        }
        return mailAddress;
    }
  
    /**
     * 获取邮件标题  
     * @return String
     * @throws MessagingException
     */
    public String getSubject() throws MessagingException {
        String subject = "";
        
        try {
            subject = MimeUtility.decodeText(mimeMessage.getSubject());
        } catch (Exception exce) {}
        
        return subject;
    }
  
    /**
     * 获取邮件发送日期 
     * @return Timestamp
     * @throws Exception
     */
    public Timestamp getSentDate() throws Exception {
        Timestamp sentDate = new Timestamp(mimeMessage.getSentDate().getTime());
        return sentDate;
    }
  
    /**
     * 获取正文文本格式 
     * @return String
     */
    public String getContentText() {
        return bodyText.toString();
    }
  
    /**
     * 获取正文HTML格式 
     * @return String
     */
    public String getContentHtml() {
        return bodyHtml.toString();
    }
   
       
    /**
     * 解析邮件，把得到的邮件内容保存到一个StringBuffer对象中，解析邮件 主要是根据MimeType类型的不同执行不同的操作，一步一步的解析  
     * @param part
     * @throws Exception
     */
    public void getMailContent(Part part) throws Exception {
        String contenttype = part.getContentType();
        int nameindex = contenttype.indexOf("name");
        boolean conname = false;
        if (nameindex != -1)   
            conname = true;
        
        if (part.isMimeType("text/plain") && !conname) {
        	String partItem = part.getContent().toString();
        	bodyText.append(partItem);
        } 
        else if (part.isMimeType("text/html") && !conname) {
        	String partItem = part.getContent().toString();
        	bodyHtml.append(partItem);
        } 
        else if (part.isMimeType("multipart/*")) {
            Multipart multipart = (Multipart) part.getContent();
            int counts = multipart.getCount();
            for (int i = 0; i < counts; i++) {
            	BodyPart bp = multipart.getBodyPart(i);
            	if (bp.getDisposition() == null) {
            		getMailContent(bp);
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
            getMailContent((Part) part.getContent());
        }
    }
  
    /**
     * 获取message header In-Reply-To
     * @return String
     * @throws MessagingException
     */
    public String getInReplyTo() throws MessagingException {
    	StringBuffer inReplyTo = new StringBuffer();
    	if (mimeMessage.getHeader("In-Reply-To") != null) {
    		for (String inReplyToContent : mimeMessage.getHeader("In-Reply-To")) {
    			inReplyTo.append(inReplyToContent);
    		}
        }
    	return inReplyTo.toString();
    }
    
    /**
     * 获取message description
     * @return String
     * @throws MessagingException
     */
    public String getDescription() throws MessagingException {
    	
    	String description = null;
    	if (mimeMessage.getDescription() != null) {
    		description = mimeMessage.getDescription();
        }
    	return description;
    }
    
    /**
     * 获取message description
     * @return String
     * @throws MessagingException
     */
    public String getReferences() throws MessagingException {
    	StringBuffer references = new StringBuffer();
    	if (mimeMessage.getHeader("References") != null) {
        	for (String reference : mimeMessage.getHeader("References")) {
        		references.append(reference);
        	}
        }
    	return references.toString();
    }
    
    /**
     * 判断此邮件是否需要回执，如果需要回执返回"true",否则返回"false"    
     * @return Boolean
     * @throws MessagingException
     */
    public boolean getReplySign() throws MessagingException {
        boolean replySign = false;
        String needreply[] = mimeMessage   
                .getHeader("Disposition-Notification-To");
        if (needreply != null) {
        	replySign = true;
        }
        return replySign;
    }
    
    /**
     * 判断此邮件是否为系统自动回执，如果是返回"true",否则返回"false"    
     * @return Boolean
     * @throws MessagingException
     */
    public boolean getAutoReplySign() throws MessagingException {
    	boolean replySign = false;
    	String autoreply[] = mimeMessage.getHeader("X-Autoreply");
    	if (autoreply != null) {
    		replySign = true;
    	}
    	return replySign;
    }
  
    /**
     * 获取此邮件的Message-ID  
     * @return String
     * @throws MessagingException
     */
    public String getMessageId() throws MessagingException {
        return mimeMessage.getMessageID();
    }
  
    /**
     * 判断此邮件是否已读，如果未读返回返回false,反之返回true
     * @return boolean
     * @throws MessagingException
     */
    public boolean isNew() throws MessagingException {
        boolean isnew = false;
        
        Flags flags = mimeMessage.getFlags();
        Flags.Flag[] flag = flags.getSystemFlags();
        System.out.println("flags's length: " + flag.length);
        for (int i = 0; i < flag.length; i++) {
            if (flag[i] == Flags.Flag.SEEN) {
                isnew = true;
                System.out.println("seen Message.......");
                break;
            }
        }
        return isnew;
    }
  
//    /**
//     * 判断此邮件是否包含附件  
//     * @param part
//     * @return boolean
//     * @throws Exception
//     */
//    public boolean isContainAttach(Part part) throws Exception {
//        boolean attachflag = false;
//        if (part.isMimeType("multipart/*")) {
//            Multipart mp = (Multipart) part.getContent();
//            for (int i = 0; i < mp.getCount(); i++) {
//                BodyPart mpart = mp.getBodyPart(i);
//                String disposition = mpart.getDisposition();
//                if ((disposition != null)   
//                        && ((disposition.equals(Part.ATTACHMENT)) || (disposition   
//                                .equals(Part.INLINE))))   
//                    attachflag = true;
//                else if (mpart.isMimeType("multipart/*")) {
//                    attachflag = isContainAttach((Part) mpart);
//                } else {
//                    String contype = mpart.getContentType();
//                    if (contype.toLowerCase().indexOf("application") != -1)   
//                        attachflag = true;
//                    if (contype.toLowerCase().indexOf("name") != -1)   
//                        attachflag = true;
//                }
//            }
//        } else if (part.isMimeType("message/rfc822")) {
//            attachflag = isContainAttach((Part) part.getContent());
//        }
//        return attachflag;
//    }
  
    /**
     * 保存附件  
     * @param part
     * @throws Exception
     */
    public void saveAttachment(Part part) throws Exception {
        String fileName = "";
        if (part.isMimeType("multipart/*")) {
            Multipart mp = (Multipart) part.getContent();
            for (int i = 0; i < mp.getCount(); i++) {
                BodyPart mpart = mp.getBodyPart(i);
                String disposition = mpart.getDisposition();
                
                if (disposition != null){
                	
                	fileName = mpart.getFileName();
                    if (fileName != null) {
                        fileName = MimeUtility.decodeText(fileName);
                    }else{
                    	fileName = "noname";
                    }
                    
                	if (Part.ATTACHMENT.equalsIgnoreCase(disposition)){
                		
//                		if (fileName == null || "".equals(fileName)) {
//                			saveFile(fileName, mpart.getInputStream(),"N");
//                		} else {
//                			saveFile(fileName, mpart.getInputStream(),"Y");
//                		}
                		
                		saveFile(fileName, mpart.getInputStream(),"Y");
                        
                	}else if(Part.INLINE.equalsIgnoreCase(disposition)){
                		
//                        if ( fileName == null || "".equals(fileName) || fileName.startsWith("image")){
//                        	saveFile(fileName, mpart.getInputStream(),"N");
//                        } else {
//                        	saveFile(fileName, mpart.getInputStream(),"Y");
//                        }
                        
                        saveFile(fileName, mpart.getInputStream(),"Y");
                	}
                    
                } else if (mpart.isMimeType("multipart/*")) {
                	saveAttachment(mpart);
                }
            }
        } else if (part.isMimeType("message/rfc822")) {
        	saveAttachment((Part) part.getContent());
        }
    }
  
    /**
     * 把附件文件名和文件流添加到list 
     * @param fileName
     * @param in
     * @throws Exception
     */
    private void saveFile(String fileName, InputStream in , String activeFlag) throws Exception {
    	
    	HashMap<String,Object> attachmentMap = new HashMap<String,Object>();
    	attachmentMap.put("fileName", fileName);
    	attachmentMap.put("inputStream", in);
    	attachmentMap.put("activeFlag", activeFlag);
    	attachmentList.add(attachmentMap);
    }  
 
    /**
     * PraseMimeMessage类测试  
     * @param args
     * @throws Exception
     */
    public static void main(String args[]) throws Exception {  
        Properties props = System.getProperties();
        
        props.put("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.pop3.socketFactory.fallback", "false");
        props.put("mail.pop3.port", "995");
        props.put("mail.pop3.socketFactory.port", "995");
        props.setProperty("mail.imap.partialfetch", "false");
        Session session = Session.getDefaultInstance(props);
        Store store = session.getStore("pop3");
        
        store.connect("pop.gmail.com", "donghaot@gmail.com", "saninco123"); 
        
        for (Folder f : store.getSharedNamespaces()) {
        	System.out.println(f);
        }
        
        Folder folder = store.getFolder("INBOX");
        folder.open(Folder.READ_ONLY);
        Message message[] = folder.getMessages();
        System.out.println("Messages's length: " + message.length);
        for (int i = 0; i < message.length; i++) {
        	ReceivedEmailUtil pmm = new ReceivedEmailUtil((MimeMessage)message[i]);
//        	ReceivedEmail receivedEmail = new ReceivedEmail();
//        	ReceivedEmailUtil pmm = new ReceivedEmailUtil((MimeMessage)message[i]);
//        	receivedEmail.setToAddress(pmm.getMailAddress(pmm.EMAIL_ADDRESS_TO));
//			receivedEmail.setBccAddress(pmm.getMailAddress(pmm.EMAIL_ADDRESS_BCC));
//	        receivedEmail.setCcAddress(pmm.getMailAddress(pmm.EMAIL_ADDRESS_CC));
//	        receivedEmail.setContentHtml(pmm.getContentHtml());
//	        receivedEmail.setContentText(pmm.getContentText());
//	        receivedEmail.setSubject(pmm.getSubject());
//	        receivedEmail.setSentDatetime(pmm.getSentDate());
//	        receivedEmail.setReplySign(pmm.getReplySign() ? "Y" : "N");
//	        receivedEmail.setMessageId(pmm.getMessageId());
//	        receivedEmail.setFromAddress(pmm.getFrom());
//	        receivedEmail.setDescription(pmm.getDescription());
//	        receivedEmail.setInReplyTo(pmm.getInReplyTo());
//	        receivedEmail.setMsgReferences(pmm.getReferences());
		}
        
//        pmm.getReceivedEmailDetails();
    }

	public List<Map<String, Object>> getAttachmentList() {
		return attachmentList;
	}

    
    
}
