package org.ees.api.agenda.entity;

import javax.xml.bind.annotation.XmlRootElement;

import org.eclipse.persistence.oxm.annotations.XmlReadOnly;

@XmlRootElement
public class Acesso {

	private Integer id;
	private String perfil;
	private String email;
	private String senha;

	public Acesso() {
		super();
	}

	public Acesso(String perfil, String email, String senha) {
		super();
		this.perfil = perfil;
		this.email = email;
		this.senha = senha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@XmlReadOnly
	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
