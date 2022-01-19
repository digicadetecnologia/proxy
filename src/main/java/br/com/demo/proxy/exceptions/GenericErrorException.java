package br.com.demo.proxy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GenericErrorException extends RuntimeException {

	private static final long serialVersionUID = -6145785659466159281L;

	public GenericErrorException(String message) {
		super(message);
	}

	public GenericErrorException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
