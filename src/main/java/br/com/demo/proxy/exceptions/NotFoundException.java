package br.com.demo.proxy.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import br.com.demo.proxy.handler.StandardError;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

	private static final long serialVersionUID = 6692055143766721781L;

	/**
	 * Construtor padrão utiliza a mensagem "Não encontrado" como
	 * padrão.
	 */
	public NotFoundException() {
		super(StandardError.MSG_NOT_FOUND);
	}

	/**
	 * Construtor que permite a personalização da mensagem de retorno através
	 * do @param message.
	 * 
	 * @param message
	 */
	public NotFoundException(String message) {
		super(message);
	}

	public NotFoundException(String message, Throwable throwable) {
		super(message, throwable);
	}

}
