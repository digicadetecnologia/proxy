package br.com.demo.proxy.dto;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import lombok.Data;

@Data
public class EnderecoDto {
	private String endereco;
	private Double longitude;
	private Double latitude;
	private String uf;
	private String estado;
	private String municipio;
	private String bairro;
	private String logradouro;
	private Integer numero;
	private Integer idTipoRetorno;
	private String descricaoTipoRetorno;
	private String precisao;
	private String cep;
	private String geometry;
	
	public EnderecoDto() {
	}
	
	@SuppressWarnings("unchecked")
	public EnderecoDto(JSONObject jsonObject) {
		this.endereco = !jsonObject.getOrDefault("endereco", null).equals("null") ? (String) jsonObject.getOrDefault("endereco", null) : "";
		this.longitude = !jsonObject.getOrDefault("longitude", null).equals("null") ? (Double) jsonObject.getOrDefault("longitude", null) : 0;
		this.latitude = !jsonObject.getOrDefault("latitude", null).equals("null") ? (Double) jsonObject.getOrDefault("latitude", null) : 0;
		this. uf = !jsonObject.getOrDefault("uf", null).equals("null") ? (String) jsonObject.getOrDefault("uf", null) : "";
		this.estado = !jsonObject.getOrDefault("estado", null).equals("null") ? (String) jsonObject.getOrDefault("estado", null) : "";
		this.municipio = !jsonObject.getOrDefault("municipio", null).equals("null") ? (String) jsonObject.getOrDefault("municipio", null) : "";
		this.bairro = !jsonObject.getOrDefault("bairro", null).equals("null") ? (String) jsonObject.getOrDefault("bairro", null) : "";
		this.logradouro = !jsonObject.getOrDefault("logradouro", null).equals("null") ? (String) jsonObject.getOrDefault("logradouro", null) : "";
		this.numero = !jsonObject.getOrDefault("numero", null).equals("null") ? (Integer) jsonObject.getOrDefault("numero", null) : 0;
		this.idTipoRetorno = !jsonObject.getOrDefault("idTipoRetorno", null).equals("null") ? (Integer) jsonObject.getOrDefault("idTipoRetorno", null) : 0;
		this.descricaoTipoRetorno = !jsonObject.getOrDefault("descricaoTipoRetorno", null).equals("null") ? (String) jsonObject.getOrDefault("descricaoTipoRetorno", null) : "";
		this.precisao = !jsonObject.getOrDefault("precisao", null).equals("null") ? (String) jsonObject.getOrDefault("precisao", null) : "";
		this.cep = !jsonObject.getOrDefault("cep", null).equals("null") ? (String) jsonObject.getOrDefault("cep", null) : "";
		this.geometry = !jsonObject.getOrDefault("geometry", null).equals("null") ? (String) jsonObject.getOrDefault("geometry", null) : "";
	}
	
	
	
}
