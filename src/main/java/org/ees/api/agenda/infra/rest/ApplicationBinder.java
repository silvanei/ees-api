package org.ees.api.agenda.infra.rest;

import org.ees.api.agenda.infra.repository.AcessoRepositoryImpl;
import org.ees.api.agenda.infra.repository.FuncionarioRepositoryImpl;
import org.ees.api.agenda.infra.repository.SalaoRepositoryImpl;
import org.ees.api.agenda.infra.repository.ServicoRepositoryImpl;
import org.ees.api.agenda.infra.service.AcessoServiceImpl;
import org.ees.api.agenda.infra.service.RegistrarSalaoServiceImpl;
import org.ees.api.agenda.infra.service.ServicoServiceFactory;
import org.ees.api.agenda.infra.service.ServicoServiceImpl;
import org.ees.api.agenda.repository.AcessoRepository;
import org.ees.api.agenda.repository.FuncionarioRepository;
import org.ees.api.agenda.repository.SalaoRepository;
import org.ees.api.agenda.repository.ServicoRepository;
import org.ees.api.agenda.service.AcessoService;
import org.ees.api.agenda.service.RegistrarSalaoService;
import org.ees.api.agenda.service.ServicoService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		//bindFactory(ServicoServiceFactory.class).to(ServicoService.class);
		bind(RegistrarSalaoServiceImpl.class).to(RegistrarSalaoService.class);

		bind(SalaoRepositoryImpl.class).to(SalaoRepository.class);
		bind(FuncionarioRepositoryImpl.class).to(FuncionarioRepository.class);

		bind(AcessoRepositoryImpl.class).to(AcessoRepository.class);
		bind(AcessoServiceImpl.class).to(AcessoService.class);

		bind(ServicoRepositoryImpl.class).to(ServicoRepository.class);
		bind(ServicoServiceImpl.class).to(ServicoService.class);
	}

}
