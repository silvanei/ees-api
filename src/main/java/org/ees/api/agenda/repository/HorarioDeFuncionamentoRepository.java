package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.HorarioDeFuncionamento;

/**
 * Created by silvanei on 28/07/16.
 */
public interface HorarioDeFuncionamentoRepository {

    public Integer inserirHorarioDeFuncionamento(HorarioDeFuncionamento horarioDeFuncionamento);
    
    public Integer atualizarHorarioDeFuncionamento(HorarioDeFuncionamento horarioDeFuncionamento);
    
	public HorarioDeFuncionamento byIdSalao(Integer idSalao);
}
