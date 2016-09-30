package org.ees.api.agenda.entity;

import org.ees.api.agenda.resource.ServicoResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.math.BigDecimal;
import java.sql.Time;

public class Servico {

	private Integer id;

	private String descricao;

	private Integer duracao;

	private BigDecimal valorMinimo;

	private BigDecimal valorMaximo;


	@InjectLink(
			resource = ServicoResource.class,
			method = "servico",
			style = InjectLink.Style.ABSOLUTE,
			bindings = {
					@Binding(name = "servicoId", value = "${instance.id}")
			},
			rel = "self",
			condition = "${resource.salaoId != null}"
	)
	private Link link;

	public Servico() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	
	public Integer getDuracao() {
		return duracao;
	}

	public void setDuracao(Integer duracao) {
		this.duracao = duracao;
	}

	public BigDecimal getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(BigDecimal valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public BigDecimal getValorMaximo() {
		return valorMaximo;
	}

	public void setValorMaximo(BigDecimal valorMaximo) {
		this.valorMaximo = valorMaximo;
	}
}
