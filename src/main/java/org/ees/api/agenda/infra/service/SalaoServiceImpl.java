package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.repository.AcessoRepositoryImpl;
import org.ees.api.agenda.infra.repository.FuncionarioRepositoryImpl;
import org.ees.api.agenda.infra.repository.SalaoRepositoryImpl;
import org.ees.api.agenda.repository.FuncionarioRepository;
import org.ees.api.agenda.repository.SalaoRepository;
import org.ees.api.agenda.service.SalaoService;

public class SalaoServiceImpl implements SalaoService {
	
	private SalaoRepository salaoRepository = new SalaoRepositoryImpl();
	private FuncionarioRepository funcionarioRepository = new FuncionarioRepositoryImpl();
	private AcessoRepositoryImpl acessoRepository = new AcessoRepositoryImpl();

	@Override
	public Salao registrarSalao(Salao salao, Funcionario administrador) {
		
		
		
		return null;
	}


}
