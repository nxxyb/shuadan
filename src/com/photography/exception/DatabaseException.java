package com.photography.exception;


/**
 * 涉及JDBC和Hibernate等操作所发生的异常，都转换成该异常，系统捕获处理。
 *
 * @since 2011-11-10
 * @author Zhao Zhihong
 *
 * @Copyright (C) 2012 天大求实电力新技术股份有限公司 版权所有
 */
public class DatabaseException extends SystemException {

	private static final long serialVersionUID = -4269415950446034553L;

	public DatabaseException(int errorCode) {
		super(errorCode);
	}
	
	public DatabaseException(Throwable cause) {
		super(ErrorCode.DATABASE_ERROR, cause);
	}
	
	public DatabaseException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}
}
