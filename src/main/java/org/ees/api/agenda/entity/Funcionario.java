package org.ees.api.agenda.entity;

import org.ees.api.agenda.resource.FuncionarioResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Funcionario {

	private Integer id;
	private String nome;
	private String apelido;
	private String telefone;
	private String celular;
	private Cargo cargo;
	private Acesso acesso;
	private Salao salao;

    @InjectLink(
            resource = FuncionarioResource.class,
            method = "funcionario",
            style = InjectLink.Style.ABSOLUTE,
            bindings = {
                    @Binding(name = "funcionarioId", value = "${instance.id}")
            },
            rel = "self"
    )
    private Link link;

	public Funcionario() {
		super();
	}

	public Funcionario(String nome) {
		super();
		this.nome = nome;
	}

	public Funcionario(String nome, Acesso acesso) {
		super();
		this.nome = nome;
		this.acesso = acesso;
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

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Acesso getAcesso() {
		return acesso;
	}

	public void setAcesso(Acesso acesso) {
		this.acesso = acesso;
	}

	public Salao getSalao() {
		return salao;
	}

	public void setSalao(Salao salao) {
		this.salao = salao;
	}
}
