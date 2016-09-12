package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.HorarioDisponivel;

import java.util.List;

/**
 * Created by silvanei on 12/09/16.
 */
public interface HorarioDisponivelService {
    public List<HorarioDisponivel> findBy(Integer salaoId, Integer servicoId, Integer funcionarioId);
}
