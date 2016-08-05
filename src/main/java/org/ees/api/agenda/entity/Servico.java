package org.ees.api.agenda.entity;

import com.google.gson.annotations.Expose;
import org.ees.api.agenda.resource.ServicoResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;
import java.math.BigDecimal;
import java.net.URI;
import java.sql.Time;

public class Servico {

	@Expose
	private Integer id;

	@Expose
	private String descricao;

	@Expose
	private Time duracao;

	@Expose
	private BigDecimal valorMinimo;

	@Expose
	private BigDecimal valorMaximo;

	@InjectLink(value = "/salao/{salaoId}/servico/${instance.id}", rel = "self")
	@Expose
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
