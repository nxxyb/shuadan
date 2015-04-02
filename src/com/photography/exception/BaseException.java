package com.photography.exception;

import java.text.MessageFormat;

/**
 * 
 * 
 * @author 巩向兵 
 * @since 2011-5-17 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public abstract class BaseException extends Exception {

	private static final long serialVersionUID = 1L;
	private int errorCode;
	private String errorMessage;

	protected BaseException(int errorCode) {
		this.setErrorCode(errorCode);
	}

	protected BaseException(int errorCode, Throwable cause) {
		super(cause);
		this.setErrorCode(errorCode);
	}

	protected BaseException(int errorCode, String errorMessage) {
		this.setErrorCode(errorCode);
		this.setErrorMessage(errorMessage);
	}

	public int getErrorCode() {
		return errorCode;
	}

	protected void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	protected void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	protected void setErrorMessage(int errorCode, Object[] param){
		if (param == null || param.length == 0){
			setErrorMessage(ErrorMessage.get(errorCode));
		} else {
			String message = ErrorMessage.get(errorCode);
			MessageFormat format = new MessageFormat(message);
			message = format.format(param);
			setErrorMessage(message);
		}
	}
}
