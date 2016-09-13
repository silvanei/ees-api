package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.HorarioDisponivel;
import org.ees.api.agenda.resource.bean.DateParam;
import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by silvanei on 12/09/16.
 */
public interface HorarioDisponivelService {
    public List<HorarioDisponivel> findBy(Integer salaoId, Integer servicoId, Integer funcionarioId, DateTime dia);
}
