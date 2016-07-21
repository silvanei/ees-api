package org.ees.api.agenda.entity;

public class Salao {

	private String nome;
	private String telefone;
	private boolean visivelNoApp;
	private Endereco endereco;
	
	public Salao() {
		super();
	}
	
	public Salao(String nome, String telefone) {
		super();
		this.nome = nome;
		this.telefone = telefone;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
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
