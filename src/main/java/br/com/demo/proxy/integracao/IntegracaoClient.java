package br.com.demo.proxy.integracao;

import java.util.List;

import br.com.demo.proxy.dto.EnderecoDto;
import br.com.demo.proxy.service.dto.TokenResponseDTO;
import feign.Body;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

public interface IntegracaoClient {

	@Headers("Content-Type: application/x-www-form-urlencoded")
	@RequestLine("POST {url}")
	@Body(value = "client_id={clientKey}&client_secret={clientSecret}")
	TokenResponseDTO requestToken(@Param("url") String url, @Param("clientKey") String clientKey,
			@Param("clientSecret") String clientSecret);

	@Headers("Content-Type: application/x-www-form-urlencoded")
	@RequestLine("POST {url}")
	@Body(value = "client_id={clientId}&client_secret={clientSecret}&refresh_token={refreshToken}&grant_type=refresh_token")
	TokenResponseDTO refreshToken(@Param("url") String url, @Param("clientId") String clientId,
			@Param("clientSecret") String clientSecret, @Param("refreshToken") String refreshToken);
	
	
	@Headers({"Content-Type: */*", "Authorization: Bearer {token}"})
	@RequestLine("GET {url}?endereco={endereco}")
	List<EnderecoDto> getEndereco(@Param("url") String url, @Param("endereco") String endereco, @Param("token") String token);

}
