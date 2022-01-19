package br.com.demo.proxy.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import br.com.demo.proxy.exceptions.GenericErrorException;
import br.com.demo.proxy.exceptions.InternalErrorException;
import br.com.demo.proxy.exceptions.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice
public class RestControllerExceptionHandler {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<StandardError> notFound(NotFoundException exception, HttpServletRequest httpRequest) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),
				StandardError.MSG_NOT_FOUND, exception.getMessage(), httpRequest.getRequestURI());
		log.error(err.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}

	@ExceptionHandler(GenericErrorException.class)
	public ResponseEntity<StandardError> badRequest(GenericErrorException exception, HttpServletRequest httpRequest) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),
				StandardError.MSG_BAD_REQUEST, exception.getMessage(), httpRequest.getRequestURI());
		log.error(err.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
	}

	@ExceptionHandler(InternalErrorException.class)
	public ResponseEntity<StandardError> internalError(InternalErrorException exception,
			HttpServletRequest httpRequest) {

		StandardError err = new StandardError(System.currentTimeMillis(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				StandardError.MSG_INTERNAL_ERROR, exception.getMessage(), httpRequest.getRequestURI());
		log.error(err.getMessage(), exception);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(err);
	}

}
