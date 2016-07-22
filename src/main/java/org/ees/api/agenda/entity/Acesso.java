package org.ees.api.agenda.entity;

public class Acesso {

	private Integer id;
	private Funcionario funcionario;
	private Integer perfil;
	private String email;
	private String senha;

	public Acesso() {
		super();
	}

	public Acesso(Integer perfil, String email, String senha) {
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

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public void setFuncionario(Funcionario funcionario) {
		this.funcionario = funcionario;
	}

	public Integer getPerfil() {
		return perfil;
	}

	public void setPerfil(Integer perfil) {
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

}