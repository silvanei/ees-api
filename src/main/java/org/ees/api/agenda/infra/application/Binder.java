package org.ees.api.agenda.infra.application;

import org.ees.api.agenda.infra.repository.*;
import org.ees.api.agenda.infra.service.*;
import org.ees.api.agenda.repository.*;
import org.ees.api.agenda.service.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


public class Binder extends AbstractBinder {

	@Override
	protected void configure() {
		//bindFactory(ServicoServiceFactory.class).to(ServicoService.class);
		bind(DadosSalaoServiceImpl.class).to(DadosSalaoService.class);

		bind(RegistrarSalaoServiceImpl.class).to(RegistrarSalaoService.class);

		bind(EnderecoServiceImpl.class).to(EnderecoService.class);
		bind(EnderecoRepositoryImpl.class).to(EnderecoRepository.class);

		bind(HorarioDeFuncionamentoServiceImpl.class).to(HorarioDeFuncionamentoService.class);
		bind(HorarioDeFuncionamentoRepositoryImpl.class).to(HorarioDeFuncionamentoRepository.class);

		bind(SalaoRepositoryImpl.class).to(SalaoRepository.class);
		bind(FuncionarioRepositoryImpl.class).to(FuncionarioRepository.class);

		bind(AcessoRepositoryImpl.class).to(AcessoRepository.class);
		bind(AcessoServiceImpl.class).to(AcessoService.class);

		bind(ServicoRepositoryImpl.class).to(ServicoRepository.class);
		bind(ServicoServiceImpl.class).to(ServicoService.class);
	}

}
