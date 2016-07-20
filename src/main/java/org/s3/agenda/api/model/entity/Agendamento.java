package org.s3.agenda.api.model.entity;

import java.util.Date;

public class Agendamento {

	private Cliente cliente;
	private Salao salao;
	private Date data;
	private Date hora;
	private Funcionario profissional;
	private Servico servico;
	private String observacao;
	private Integer status;

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Salao getSalao() {
		return salao;
	}

	public void setSalao(Salao salao) {
		this.salao = salao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public Funcionario getProfissional() {
		return profissional;
	}

	public void setProfissional(Funcionario profissional) {
		this.profissional = profissional;
	}

	public Servico getServico() {
		return servico;
	}

	public void setServico(Servico servico) {
		this.servico = servico;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
