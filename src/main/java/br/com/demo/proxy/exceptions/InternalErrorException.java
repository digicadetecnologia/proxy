package br.com.demo.proxy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalErrorException extends RuntimeException {

	private static final long serialVersionUID = -8065603218059327213L;

	public InternalErrorException(String message) {
		super(message);
	}

	public InternalErrorException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
