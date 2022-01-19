package br.com.demo.proxy.integracao;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.demo.proxy.dto.EnderecoDto;
import br.com.demo.proxy.exceptions.UnauthorizedException;
import br.com.demo.proxy.service.dto.TokenResponseDTO;
import feign.Feign;
import feign.FeignException;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IntegracaoService {

	@Value("${demo.auth.server.url}")
	private String hostMapa;

	@Value("${demo.auth.server.acces_token_url}")
	private String acessTokenUrl;
	
	@Value("${demo.auth.server.refresh_token_url}")
	private String refreshTokenUrl;

	@Value("${demo.auth.client_id}")
	private String clientId;

	@Value("${demo.auth.client_secret}")
	private String clientSecret;

	public TokenResponseDTO getAcessToken() throws FeignException, UnauthorizedException {
		try {
			return getAuthClient().requestToken(acessTokenUrl, clientId, clientSecret);
		} catch (FeignException e) {
			log.error("getUserInfo - Falha ao obter o access token para o client-id "+clientId+" e client-secret "+clientSecret+": ", e);
			if (e.getMessage().contains("401")) {
				throw new UnauthorizedException();
			} else {
				throw e;
			}
		}
	}
	
	public TokenResponseDTO getRefreshToken(String refreshToken) throws FeignException, UnauthorizedException {
		try {
			return getAuthClient().refreshToken(refreshTokenUrl, clientId, clientSecret, refreshToken);
		} catch (FeignException e) {
			log.error("getUserInfo - Falha ao obter o refresh token para o client-id {} e client {}: " + clientId, clientSecret, e);
			if (e.getMessage().contains("401")) {
				throw new UnauthorizedException();
			} else {
				throw e;
			}
		}
	}
	
	public List<EnderecoDto> getEndereco(String url, String endereco, String token) throws FeignException, UnauthorizedException {
		try {
			return getAuthClient().getEndereco(url, endereco, token);
		} catch (FeignException e) {
			log.error("getUserInfo - Falha ao consulta o endereço {} " + endereco, e);
			if (e.getMessage().contains("401")) {
				throw new UnauthorizedException();
			} else {
				throw e;
			}
		}
	}

	private IntegracaoClient getAuthClient() {
		return buildDefaultFeignClient(IntegracaoClient.class);
	}

	private <T> T buildDefaultFeignClient(Class<T> clazz) {
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);

		return Feign.builder().client(new OkHttpClient()).decoder(new JacksonDecoder(mapper))
				.logger(new Slf4jLogger(clazz)).logLevel(Logger.Level.FULL).target(clazz, hostMapa);
	}

}
