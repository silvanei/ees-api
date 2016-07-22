package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.infra.repository.AcessoRepositoryImpl;
import org.ees.api.agenda.infra.repository.FuncionarioRepositoryImpl;
import org.ees.api.agenda.infra.repository.SalaoRepositoryImpl;
import org.ees.api.agenda.repository.FuncionarioRepository;
import org.ees.api.agenda.repository.SalaoRepository;
import org.ees.api.agenda.service.RegistrarSalaoService;

public class RegistrarSalaoServiceImpl implements RegistrarSalaoService {

	private SalaoRepository salaoRepository = new SalaoRepositoryImpl();
	private FuncionarioRepository funcionarioRepository = new FuncionarioRepositoryImpl();
	private AcessoRepositoryImpl acessoRepository = new AcessoRepositoryImpl();

	@Override
	// TODO Adicionar camada de serviço e não acessar diretamente o repositorio
	public Salao registrarSalao(Salao salao, Funcionario administrador) {

		try {
			DB.beginTransaction();
			
			Salao newSalao = salaoRepository.insert(salao);
			Funcionario newFuncionario = funcionarioRepository.insert(administrador, newSalao.getId());
			Acesso newAcesso = acessoRepository.insert(administrador.getAcesso(), newFuncionario.getId());

			newFuncionario.setAcesso(newAcesso);
			newSalao.getFuncionarios().add(newFuncionario);

			DB.commit();

			return salao;
			
		} catch (AcessoADadosException e) {
			DB.rollback();
			throw e;
		}
	}

}
