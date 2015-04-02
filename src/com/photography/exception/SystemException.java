package com.photography.exception;


/**
 * 系统异常（记录日志，提示给用户统一的错误消息）
 * 
 * @author 赵志宏 
 * @since 2011-11-9 
 * 
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class SystemException extends BaseException {

	private static final long serialVersionUID = 1L;

	/**
	 * 根据错误码构造系统异常（自动填充错误消息）
	 * <p>
	 * <b>注意：</b>务必在ErrorMessage.java中录入对应的错误消息
	 * </p>
	 * @param errorCode 错误码
	 */
	public SystemException(int errorCode) {
		super(errorCode, ErrorMessage.get(errorCode));
	}

	/**
	 * 根据错误码构造系统异常（自动填充错误消息，并按照参数进行格式化）
	 * <p>
	 * <b>注意：</b>务必在ErrorMessage.java中录入对应的错误消息<br/>
	 * <b>说明：</b>如果格式化消息的参数有多个，可以依次传入<br/>
	 * <b>举例：</b>new Service(errorCode, param1, param2 ...)
	 * </p>
	 * @param errorCode 错误码
	 * @param param 用于格式化错误消息的参数
	 */
	public SystemException(int errorCode, Object... param) {
		super(errorCode);
		setErrorMessage(errorCode, param);
	}

	/**
	 * 根据原因构造系统异常（自动填充错误消息）
	 * <p>
	 * <b>注意：</b>务必在ErrorMessage.java中录入对应的错误消息
	 * </p>
	 * @param errorCode 错误码
	 * @param cause 异常的原因
	 */
	public SystemException(int errorCode, Throwable cause) {
		super(errorCode, cause);
		setErrorMessage(ErrorMessage.get(errorCode));
	}

}
