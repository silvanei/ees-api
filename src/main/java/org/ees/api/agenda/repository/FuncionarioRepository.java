package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Funcionario;

public interface FuncionarioRepository {

	public Integer insert(Funcionario funcionario, Integer idSalao, Integer idAcesso);

	public Funcionario findById(Integer idFuncionario);
	
}
