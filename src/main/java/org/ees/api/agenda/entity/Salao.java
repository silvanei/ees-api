package org.ees.api.agenda.entity;

import java.util.ArrayList;
import java.util.List;

public class Salao {

	private Integer id;
	private String nome;
	private String telefone;
	private boolean visivelNoApp;
	private Endereco endereco;
	
	private List<Funcionario> funcionarios = new ArrayList<Funcionario>();

	public Salao() {
		super();
	}

	public Salao(String nome, String telefone) {
		super();
		this.nome = nome;
		this.telefone = telefone;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}
	
	

}
