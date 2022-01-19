package br.com.demo.proxy.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UsuarioInfoDTO {
	
	private String sub;
	private String nome;
	@JsonProperty("e-mail")
	private String email;
	@JsonProperty("userId")
	private Long id;	
	private String validaGeocoder;
	private Boolean eqValidacao;
	@JsonProperty("ID")
	private String login;
	
	public UsuarioInfoDTO() {

	}
	
	public UsuarioInfoDTO(String nome, String email, Long id, Boolean eqValidacao) {
		super();
		this.nome = nome;
		this.email = email;
		this.id = id;
		this.eqValidacao = eqValidacao;
	}
	
	public String getSub() {
		return sub;
	}
	public void setSub(String sub) {
		this.sub = sub;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getValidaGeocoder() {
		return validaGeocoder;
	}

	public void setValidaGeocoder(String validaGeocoder) {
		this.validaGeocoder = validaGeocoder;
	}
	
	public Boolean pertenceEquipeValidacao() {
		if (this.validaGeocoder == null || this.validaGeocoder.isEmpty()) {
			return false; 
		}else if(this.validaGeocoder.equals("TRUE")) {
			return true;			
		}
		
		return false;
	}

	public Boolean getEqValidacao() {
		return eqValidacao;
	}

	public void setEqValidacao(Boolean eqValidacao) {
		this.eqValidacao = eqValidacao;
	}
	

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}
	
	
}
