package org.ees.api.agenda.entity;

import org.ees.api.agenda.resource.ServicoResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Time;

public class Servico {
	
	private Integer id;
	
	private String descricao;
	
	private Time duracao;

	private BigDecimal valorMinimo;

	private BigDecimal valorMaximo;

	@InjectLink("/salao/{salaoId}/servico/${instance.id}")
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

	
	public Time getDuracao() {
		return duracao;
	}

	public void setDuracao(Time duracao) {
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

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}
}
