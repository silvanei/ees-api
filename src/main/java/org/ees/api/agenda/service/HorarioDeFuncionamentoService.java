package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.HorarioDeFuncionamento;

/**
 * Created by silvanei on 28/07/16.
 */
public interface HorarioDeFuncionamentoService {

    public Integer inserirHorarioDeFuncionamento(Integer salaoId, HorarioDeFuncionamento horarioDeFuncionamento);
	
    public Integer atualizarHorarioDeFuncionamento(HorarioDeFuncionamento horarioDeFuncionamento);
	
    public HorarioDeFuncionamento byIdSalao(Integer idSalao);
}
