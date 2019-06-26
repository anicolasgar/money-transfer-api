package com.revolut.exception;

public class RestClientException extends ApiException {

	private static final String CODE = "RC";

	public RestClientException(String description) {
		super(CODE, description);
	}

	public RestClientException(String description, Integer statusCode) {
		super(CODE, description, statusCode);
	}

	public RestClientException(String description, Integer statusCode, Throwable cause) {
		super(CODE, description, statusCode, cause);
	}

	public RestClientException(String description, Throwable cause) {
		super(CODE, description, cause);
	}
}
