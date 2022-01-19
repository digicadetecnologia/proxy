package br.com.demo.proxy.exceptions;

public class ConfigErrorException extends Exception {

	private static final long serialVersionUID = -6145785659466159281L;

	public ConfigErrorException(String message) {
		super(message);
	}

	public ConfigErrorException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
