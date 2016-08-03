package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.AcessoRepository;
import org.ees.api.agenda.repository.FuncionarioRepository;
import org.ees.api.agenda.repository.SalaoRepository;
import org.ees.api.agenda.service.RegistrarSalaoService;

import javax.annotation.Resource;
import javax.inject.Inject;

@Resource
public class RegistrarSalaoServiceImpl implements RegistrarSalaoService {

	private SalaoRepository salaoRepository;
	private FuncionarioRepository funcionarioRepository;
	private AcessoRepository acessoRepository;

	@Inject
	public RegistrarSalaoServiceImpl(SalaoRepository salaoRepository, FuncionarioRepository funcionarioRepository, AcessoRepository acessoRepository) {
		this.salaoRepository = salaoRepository;
		this.funcionarioRepository = funcionarioRepository;
		this.acessoRepository = acessoRepository;
	}

	@Override
	// TODO Adicionar camada de serviço e não acessar diretamente o repositorio
	public Salao registrarSalao(Salao salao, Funcionario administrador, Acesso acesso) {

		try {
			DB.beginTransaction();

			Integer idSalao = salaoRepository.insert(salao);
			Integer idAcesso = acessoRepository.insert(acesso);
			Integer idFuncionario = funcionarioRepository.insert(administrador, idSalao, idAcesso);

			Salao newSalao = salaoRepository.findById(idSalao);
			Funcionario newFuncionario = funcionarioRepository.findById(idFuncionario);
			Acesso newAcesso = acessoRepository.findById(idAcesso);
			newFuncionario.setAcesso(newAcesso);
			newSalao.addFuncionarios(newFuncionario);

			DB.commit();

			return newSalao;

		} catch (AcessoADadosException e) {
			DB.rollback();
			throw e;
		}
	}

}
