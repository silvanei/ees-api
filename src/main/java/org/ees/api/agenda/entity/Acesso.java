package org.ees.api.agenda.entity;

public class Acesso {

	private Integer id;
	private String perfil;
	private String email;
	private String senha;

    private Integer salaoId;
    private Integer funcionarioId;
    private Integer clienteId;

	public Acesso() {
		super();
	}

	public Acesso(String perfil, String email, String senha) {
		super();
		this.perfil = perfil;
		this.email = email;
		this.senha = senha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

    public Integer getSalaoId() {
        return salaoId;
    }

    public void setSalaoId(Integer salaoId) {
        this.salaoId = salaoId;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }
}
