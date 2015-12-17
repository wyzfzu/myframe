package com.myframe.core.mail;

import java.util.List;

/**     
 * 邮件对象。
 *
 * @author wyzfzu (wyzfzu@qq.com)
 */   
public class MailBean {

	// 要发送Mail地址
	private List<String> to = null;
	// 要暗抄送Mail地址。
	private List<String> bcc = null;
	// 要抄送Mail地址;
	private List<String> cc = null;

	// Mail发送的起始地址
	private String from = null;
	// Mail发送的起始地址
	private String fromName = "";
	// 附件地址列表
	private List<String> attachedFileList;

	// Mail主题
	private String subject;
	// Mail内容
	private String content;
	// 编码格式
	private String messageContentMimeType = "text/html; charset=utf-8";

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public List<String> getBcc() {
		return bcc;
	}

	public void setBcc(List<String> bcc) {
		this.bcc = bcc;
	}

	public List<String> getCc() {
		return cc;
	}

	public void setCc(List<String> cc) {
		this.cc = cc;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public List<String> getAttachedFileList() {
		return attachedFileList;
	}

	public void setAttachedFileList(List<String> attachedFileList) {
		this.attachedFileList = attachedFileList;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMessageContentMimeType() {
		return messageContentMimeType;
	}

	public void setMessageContentMimeType(String messageContentMimeType) {
		this.messageContentMimeType = messageContentMimeType;
	}
}
