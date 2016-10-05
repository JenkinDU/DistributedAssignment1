package ca.concordia.dfrs.utils;

import java.io.Serializable;

public class Result implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6472516829725438812L;
	private boolean success;
	private String content;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
