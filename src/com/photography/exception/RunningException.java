package com.photography.exception;

import java.text.MessageFormat;

/**
 * 运行时异常， 旨在解决实现第三方接口时，某些异常无法自己处理，而又不允许抛出接口限定类型之外的异常。这时，
 * 需要在接口实现方法中捕获异常并封装成 此类异常，并将之抛出。（这类型异常需要自己抛出，自己捕获处理）
 * 
 * @author Zhao Zhihong
 * @since 2011-10-20
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class RunningException extends RuntimeException {

	private static final long serialVersionUID = -6301455808110295753L;

	private int errorCode = 0;
	private String errorMessage;
	
	
	public RunningException(int errorCode, Throwable cause) {
		super(cause);
		this.errorCode = errorCode;
		setErrorMessage(errorCode,null);
	}
	
	public RunningException(int errorCode) {
		super();
		this.errorCode = errorCode;
		setErrorMessage(errorCode,null);
	}
	
	public RunningException(String message) {
		super();
		errorMessage = message;
	}
	
	
	public RunningException(int errorCode, Throwable cause,Object... param) {
		super(cause);
		this.errorCode = errorCode;
		setErrorMessage(errorCode,param);
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

	protected void setErrorMessage(int errorCode, Object[] param){
		if (param == null || param.length == 0){
			this.errorMessage = ErrorMessage.get(errorCode);
		} else {
			String message = ErrorMessage.get(errorCode);
			MessageFormat format = new MessageFormat(message);
			message = format.format(param);
			this.errorMessage = message;
		}
	}
	
	

}
