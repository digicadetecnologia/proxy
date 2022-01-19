package br.com.demo.proxy.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.demo.proxy.dto.EnderecoDto;
import br.com.demo.proxy.exceptions.ConfigErrorException;
import br.com.demo.proxy.exceptions.GenericErrorException;
import br.com.demo.proxy.exceptions.InternalErrorException;
import br.com.demo.proxy.exceptions.NotFoundException;
import br.com.demo.proxy.exceptions.UnauthorizedException;
import br.com.demo.proxy.integracao.IntegracaoService;
import br.com.demo.proxy.service.auth.AuthUtils;
import br.com.demo.proxy.service.dto.TokenResponseDTO;
import feign.FeignException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Api(value = "Mapa")
@RestController(value = "/")
@RequestMapping(produces = MediaType.IMAGE_PNG_VALUE)
@Slf4j
public class ProxyController {

	private static final String AUTH_HEADER = "authorization";
	private static final String HTTP_GET = "GET";
	private static final String AUTH_TYPE = "Bearer ";

	@Value("${geoserver.url}")
	private String urlGeoserver;

	@Value("${demo.auth.server.url}")
	private String host;

	@Value("${demo.auth.server.api_uri}")
	private String apiUri;

	@Autowired
	private HttpServletRequest req;

	@Autowired
	private HttpServletResponse response;

	@Autowired
	private IntegracaoService service;

	@ApiOperation(value = "estatico")
	@GetMapping(path = "/mapa-demo/estatico", produces = MediaType.IMAGE_PNG_VALUE)
	public void getMapa() {
		requestMap();
	}

	@ApiOperation(value = "dinamico")
	@GetMapping(path = "/mapa-demo/dinamico", produces = MediaType.IMAGE_PNG_VALUE)
	public void getMapaDinamico() {
		requestMap();
	}

	@SuppressWarnings("unchecked")
	@ApiOperation(value = "geocode")
	@GetMapping(path = "/geocode/{formato}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<EnderecoDto>> getGeocode(@PathVariable("formato") String formato, @RequestParam("endereco") String endereco) {
		JSONArray coordenadasEndereco = geocodePorCoordenada(formato, endereco);
		List<EnderecoDto> listaEndereco = new ArrayList<>();
		
		if (coordenadasEndereco != null) {
			coordenadasEndereco.forEach(item -> {
				JSONObject jsonObject = (JSONObject) item;
					listaEndereco.add(new EnderecoDto(jsonObject));
				});
		}

		return ResponseEntity.ok(listaEndereco);
	}

	@SuppressWarnings("static-access")
	private void requestMap() {
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = getURL(urlGeoserver);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod(HTTP_GET);

			if (AuthUtils.getInstance().getToken() == null) {
				TokenResponseDTO acessToken = service.getAcessToken();
				AuthUtils.getInstance().setToken(acessToken);
			}

			StringBuilder basicAuth = new StringBuilder(AUTH_TYPE)
					.append(AuthUtils.getInstance().getToken().getAccess_token());
			connection.setRequestProperty(AUTH_HEADER, basicAuth.toString());

			InputStream inputStream = connection.getInputStream();
			OutputStream outputStream = response.getOutputStream();

			IOUtils.copy(inputStream, outputStream);
			response.flushBuffer();
		} catch (ConfigErrorException e) {
			log.error(e.getMessage(), e);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			sendErro(connection);
		}
	}

	private void sendErro(HttpURLConnection connection) {
		try {
			if (connection != null) {
				InputStream inputStream = connection.getErrorStream();
				OutputStream outputStream = response.getOutputStream();

				IOUtils.copy(inputStream, outputStream);
				response.sendError(connection.getResponseCode());
			}
		} catch (IOException ioException) {
			log.error(ioException.getMessage(), ioException);
		}
	}

	@SuppressWarnings("deprecation")
	private URL getURL(String url) throws MalformedURLException, ConfigErrorException {
		if (StringUtils.isEmpty(url))
			throw new ConfigErrorException("URL Geoserver não informada");

		return new URL(url + "?" + req.getQueryString());
	}


	@SuppressWarnings("static-access")
	public JSONArray geocodePorCoordenada(String formato, String endereco) {
		Optional<JSONArray> retorno = Optional.empty();
		String ur = apiUri+"/geocode/"+formato;
		try {
			List<EnderecoDto> enderecos = service.getEndereco(ur, endereco, AuthUtils.getInstance().getToken().getAccess_token());
			retorno = Optional.of(JSONArray.fromObject(enderecos));
		} catch (FeignException | UnauthorizedException e) {
			e.printStackTrace();
		}
		return retorno.orElseThrow(NotFoundException::new);
	}

	@SuppressWarnings("unused")
	private Optional<JSONArray> requestArray(StringBuilder url) {

		try {
			String string = request(url);
			JSONArray jsonArray = JSONArray.fromObject(string);

			if (!jsonArray.isEmpty())
				return Optional.of(jsonArray);

		} catch (Exception e) {
			log.error("", e);
		}

		return Optional.empty();
	}

	private String request(StringBuilder url) {
		HttpURLConnection connection = null;
		InputStream input;
		try {
			connection = getConnection(url.toString());

			if (connection.getResponseCode() == HttpServletResponse.SC_OK
					|| connection.getResponseCode() == HttpServletResponse.SC_NO_CONTENT) {
				input = connection.getInputStream();

				if (input != null)
					return IOUtils.toString(input, StandardCharsets.UTF_8);
				else
					input = connection.getErrorStream();

			}

			if (connection.getResponseCode() == HttpServletResponse.SC_UNAUTHORIZED)
				throw new GenericErrorException("Falha na autenticação " + connection.getResponseCode());
		} catch (Exception e) {
			log.error("Falha ao realizar o Geocode", e);
		} finally {
			IOUtils.close(connection);
			if (connection != null)
				connection.disconnect();
		}

		throw new GenericErrorException("Falha ao realizar o Geocode");
	}

	@SuppressWarnings("static-access")
	private HttpURLConnection getConnection(String url) {
		HttpURLConnection connection;
		try {
			if (AuthUtils.getInstance().getToken() == null) {
				TokenResponseDTO acessToken = service.getAcessToken();
				AuthUtils.getInstance().setToken(acessToken);
			}
			StringBuilder basicAuth = new StringBuilder(AUTH_TYPE)
					.append(AuthUtils.getInstance().getToken().getAccess_token());
			URL targetURL = getURL(url);

			connection = (HttpURLConnection) targetURL.openConnection();
			connection.setRequestMethod(HTTP_GET);
			connection.addRequestProperty(AUTH_HEADER, basicAuth.toString());

		} catch (GenericErrorException | InternalErrorException e) {
			throw e;
		} catch (IOException e) {
			log.error("Não foi possível ao estabelecer conexão com o endereço: {}");
			throw new InternalErrorException("");
		} catch (Exception e) {
			throw new InternalErrorException("", e);
		}

		return connection;
	}
}
