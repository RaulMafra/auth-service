package com.auth.handler;

import java.io.Serial;

public class BusinessException extends RuntimeException {

	@Serial
    private static final long serialVersionUID = 1L;

	public BusinessException(String msg) {
		super(msg);
	}

	public BusinessException(String msg, Object... params) {
		super(String.format(msg, params));
	}

}
