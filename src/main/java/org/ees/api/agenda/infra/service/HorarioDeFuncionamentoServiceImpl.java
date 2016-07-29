package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.HorarioDeFuncionamento;
import org.ees.api.agenda.infra.repository.HorarioDeFuncionamentoRepositoryImpl;
import org.ees.api.agenda.repository.HorarioDeFuncionamentoRepository;
import org.ees.api.agenda.service.HorarioDeFuncionamentoService;

/**
 * Created by silvanei on 28/07/16.
 */
public class HorarioDeFuncionamentoServiceImpl implements HorarioDeFuncionamentoService{

    private HorarioDeFuncionamentoRepository horarioDeFuncionamentoRepostory = new HorarioDeFuncionamentoRepositoryImpl();

    @Override
    public Integer inserirHorarioDeFuncionamento(HorarioDeFuncionamento horarioDeFuncionamento) {
        return horarioDeFuncionamentoRepostory.inserirHorarioDeFuncionamento(horarioDeFuncionamento);
    }

	@Override
	public HorarioDeFuncionamento byIdSalao(Integer idSalao) {
		return horarioDeFuncionamentoRepostory.byIdSalao(idSalao);
	}

	@Override
	public Integer atualizarHorarioDeFuncionamento(HorarioDeFuncionamento horarioDeFuncionamento) {
		return horarioDeFuncionamentoRepostory.atualizarHorarioDeFuncionamento(horarioDeFuncionamento);
	}
    
    
}
