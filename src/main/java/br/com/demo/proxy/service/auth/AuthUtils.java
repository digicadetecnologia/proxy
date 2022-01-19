package br.com.demo.proxy.service.auth;

import br.com.demo.proxy.service.dto.TokenResponseDTO;

public class AuthUtils {
	
	private static TokenResponseDTO token;
	
	private static AuthUtils instance;
	
	private AuthUtils() {
	}
	
	public static synchronized AuthUtils getInstance() {
		if(instance == null)
			instance = new AuthUtils();
		return instance;
	}
	
	public static TokenResponseDTO getToken() {
		return token;
	}
	
	public static void setToken(TokenResponseDTO token) {
		AuthUtils.token = token;
	}

}
