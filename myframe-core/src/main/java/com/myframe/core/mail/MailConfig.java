package com.myframe.core.mail;

/**
 * 邮件发送参数
 */
public class MailConfig {

	private String host; // 地址
	private int port;    // 端口
    private String from; // 发送方
	private String userName; // 用户名
	private String password; //密码
    private boolean isSsl = false;   // 是否是ssl

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

    public boolean isSsl() {
        return isSsl;
    }

    public void setSsl(boolean isSsl) {
        this.isSsl = isSsl;
    }

}
