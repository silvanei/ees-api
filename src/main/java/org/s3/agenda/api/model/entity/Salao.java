package org.s3.agenda.api.model.entity;

public class Salao {

	private String nome;
	private String email;
	private boolean visivelNoApp;
	private String telefone;
	private Endereco endereco;

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

	public boolean isVisivelNoApp() {
		return visivelNoApp;
	}

	public void setVisivelNoApp(boolean visivelNoApp) {
		this.visivelNoApp = visivelNoApp;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

}
