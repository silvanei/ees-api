package org.ees.api.agenda.entity;

import java.util.ArrayList;
import java.util.List;

public class Salao {

	private Integer id;
	private String nome;
	private String telefone;
	private String celular;
	private boolean visivelNoApp;
	private Endereco endereco;
	private HorarioDeFuncionamento horarioDeFuncionamento;
	private List<Funcionario> funcionarios = new ArrayList<Funcionario>();
	private List<Servico> servicos = new ArrayList<Servico>();
	private List<Cliente> clientes = new ArrayList<Cliente>();

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

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public boolean isVisivelNoApp() {
		return visivelNoApp;
	}

	public void setVisivelNoApp(boolean visivelNoApp) {
		this.visivelNoApp = visivelNoApp;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public HorarioDeFuncionamento getHorarioDeFuncionamento() {
		return horarioDeFuncionamento;
	}

	public void setHorarioDeFuncionamento(HorarioDeFuncionamento horarioDeFuncionamento) {
		this.horarioDeFuncionamento = horarioDeFuncionamento;
	}

	public List<Funcionario> getFuncionarios() {
		return funcionarios;
	}

	public void setFuncionarios(List<Funcionario> funcionarios) {
		this.funcionarios = funcionarios;
	}

	public List<Servico> getServicos() {
		return servicos;
	}

	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
}
