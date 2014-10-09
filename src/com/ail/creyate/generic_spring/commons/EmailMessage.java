package com.ail.creyate.generic_spring.commons;

import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

public class EmailMessage {
	
	private String fromAddress;
	
	private String fromPassword;
	
	private String toAddress;
	
	private String[] toAddressList;
	
	private String subject;
	
	private String message;
	
	private String[] ccList;
	
	private String[] bccList;
	
	public EmailMessage(String fromAddress, String fromPassword, String toAddress, String subject, String message) {
		this.fromAddress = fromAddress;
		this.fromPassword = fromPassword;
		this.toAddress = toAddress;
		this.subject = subject;
		this.message = message;
	}
	
	public EmailMessage(String fromAddress, String fromPassword, String[] toAddressList, String subject, String message) {
		this.fromAddress = fromAddress;
		this.fromPassword = fromPassword;
		this.toAddressList = toAddressList;
		this.subject = subject;
		this.message = message;
	}
	
	public EmailMessage(String fromAddress, String fromPassword, String toAddress, String subject, String message, String[] ccList, String[] bccList) {
		this.fromAddress = fromAddress;
		this.fromPassword = fromPassword;
		this.toAddress = toAddress;
		this.subject = subject;
		this.message = message;
		if(ccList != null) {
			this.ccList = ccList;
		}
		if(bccList != null) {
			this.bccList = bccList;
		}
	}
	
	public EmailMessage(String fromAddress, String fromPassword, String[] toAddressList, String subject, String message, String[] ccList, String[] bccList) {
		this.fromAddress = fromAddress;
		this.fromPassword = fromPassword;
		this.toAddressList = toAddressList;
		this.subject = subject;
		this.message = message;
		if(ccList != null) {
			this.ccList = ccList;
		}
		if(bccList != null) {
			this.bccList = bccList;
		}
	}
	
	public String getFromAddress() {
		return fromAddress;
	}

	public String getFromPassword() {
		return fromPassword;
	}

	public String getToAddress() {
		return toAddress;
	}

	public String getSubject() {
		return subject;
	}

	public String getMessage() {
		return message;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public void setFromPassword(String fromPassword) {
		this.fromPassword = fromPassword;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String[] getToAddressList() {
		return toAddressList;
	}

	public String[] getCcList() {
		return ccList;
	}

	public String[] getBccList() {
		return bccList;
	}

	public void setToAddressList(String[] toAddressList) {
		this.toAddressList = toAddressList;
	}

	public void setCcList(String[] ccList) {
		this.ccList = ccList;
	}

	public void setBccList(String[] bccList) {
		this.bccList = bccList;
	}

	public MimeMessagePreparator getMimeMessage() {
		return new MimeMessagePreparator() {

			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				MimeMessageHelper messageHelper = new MimeMessageHelper(
						mimeMessage, true, "UTF-8");
				if(StringUtils.trimToNull(toAddress) != null) {
					messageHelper.setTo(toAddress);
				}
				if(toAddressList != null && toAddressList.length > 0) {
					messageHelper.setTo(toAddressList);
				}
				messageHelper.setSubject(subject);
				messageHelper.setText(message, true);
				if(bccList != null) {
					messageHelper.setBcc(bccList);
				}
				if(ccList != null) {
					messageHelper.setCc(ccList);
				}
				messageHelper.setFrom(fromAddress);

				// determines if there is an upload file, attach it to the
				// e-mail
				/*
				 * String attachName = attachFile.getOriginalFilename(); if
				 * (!attachFile.equals("")) {
				 * 
				 * messageHelper.addAttachment(attachName, new
				 * InputStreamSource() {
				 * 
				 * @Override public InputStream getInputStream() throws
				 * IOException { return attachFile.getInputStream(); } }); }
				 */

			}

		};
	}
	
}
