package br.com.demo.proxy.handler;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {
	
	public static final String MSG_NOT_FOUND = "Registro não encontrado";
	public static final String MSG_BAD_REQUEST = "Falha ao processar a requisição";
	public static final String MSG_INTERNAL_ERROR = "Erro inesperado.";

	/**
	 * 
	 */
	private static final long serialVersionUID = 2908221924531420072L;
	
	private Long timestamp;
	private Integer status;
	private String error;
	private String message;
	private String path;
}
