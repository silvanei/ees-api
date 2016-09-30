package org.ees.api.agenda.entity;

import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.resource.DadosResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
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
	private List<Funcionario> funcionarios;
	private List<Servico> servicos;
	private List<Cliente> clientes;
	private List<Acesso> acessos;
	private String imgBase64;
	private boolean favorito;

	@InjectLink(
			resource = DadosResource.class,
			method = "dadosSalao",
			style = InjectLink.Style.ABSOLUTE,
            bindings = {
                    @Binding(name = "salaoId", value = "${instance.id}")
            },
			rel = "self",
            condition = "${resource.salaoId != null}"
	)
	private Link link;

	public Salao() {
		super();
	}
	
	public Salao(Integer idSalao) {
		super();
		this.id = idSalao;
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
	
	public void addFuncionarios(Funcionario funcionarios) {
		if(null == this.funcionarios) {
			this.funcionarios = new ArrayList<Funcionario>();
		}
		this.funcionarios.add(funcionarios);
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

    public List<Acesso> getAcessos() {
        return acessos;
    }

    public void setAcessos(List<Acesso> acessos) {
        this.acessos = acessos;
    }

	public String getImgBase64() {
		return imgBase64;
	}

	public void setImgBase64(String imgBase64) {
		this.imgBase64 = imgBase64;
	}

	public boolean isFavorito() {
		return favorito;
	}

	public void setFavorito(boolean favorito) {
		this.favorito = favorito;
	}
}
