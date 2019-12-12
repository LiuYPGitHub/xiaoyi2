package com.xiaoyi.bis.user.controller.common.exception;

/**
 * 自定义异常
 *
 *
 */
public class CustomException extends RuntimeException {
	private static final long serialVersionUID = 1891573510753921917L;

	public CustomException() {
		super();
	}  

	public CustomException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CustomException(String message, Throwable cause) {
		super(message, cause);
	}

	public CustomException(String message) {
		super(message);
	}

	public CustomException(Throwable cause) {
		super(cause);
	}
}
