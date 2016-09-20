package org.ees.api.agenda.infra.application;

import org.ees.api.agenda.infra.repository.*;
import org.ees.api.agenda.infra.service.*;
import org.ees.api.agenda.repository.*;
import org.ees.api.agenda.service.*;
import org.glassfish.hk2.utilities.binding.AbstractBinder;


public class Binder extends AbstractBinder {

	@Override
	protected void configure() {
		bind(DadosSalaoServiceImpl.class).to(DadosSalaoService.class);

		bind(RegistrarSalaoServiceImpl.class).to(RegistrarSalaoService.class);

		bind(EnderecoServiceImpl.class).to(EnderecoService.class);
		bind(EnderecoRepositoryImpl.class).to(EnderecoRepository.class);

		bind(HorarioDeFuncionamentoServiceImpl.class).to(HorarioDeFuncionamentoService.class);
		bind(HorarioDeFuncionamentoRepositoryImpl.class).to(HorarioDeFuncionamentoRepository.class);

		bind(SalaoRepositoryImpl.class).to(SalaoRepository.class);
		bind(FuncionarioServiceImpl.class).to(FuncionarioService.class);
		bind(FuncionarioRepositoryImpl.class).to(FuncionarioRepository.class);

		bind(AcessoRepositoryImpl.class).to(AcessoRepository.class);
		bind(AcessoServiceImpl.class).to(AcessoService.class);

		bind(ServicoRepositoryImpl.class).to(ServicoRepository.class);
		bind(ServicoServiceImpl.class).to(ServicoService.class);

		bind(HorarioTrabalhoRepositoryImpl.class).to(HorarioTrabalhoRepository.class);
		bind(HorarioTrabalhoServiceImpl.class).to(HorarioTrabalhoService.class);

		bind(ClienteSalaoRepositoryImpl.class).to(ClienteSalaoRepository.class);
		bind(ClienteSalaoServiceImpl.class).to(ClienteSalaoService.class);

		bind(AgendaRepositoryImpl.class).to(AgendaRepository.class);
		bind(AgendaServiceImpl.class).to(AgendaService.class);

		bind(HorarioDisponivelServiceImpl.class).to(HorarioDisponivelService.class);

		bind(ImageFileServiceImpl.class).to(ImageFileService.class);

		bind(ClienteAppRepositoryImpl.class).to(ClienteAppRepository.class);
		bind(ClienteAppServiceImpl.class).to(ClienteAppService.class);
		bind(RegistrarClienteServiceImpl.class).to(RegistrarClienteService.class);
	}

}
