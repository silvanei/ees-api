package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.DiaDaSemana;
import org.ees.api.agenda.entity.HorarioTrabalho;

import java.util.List;

/**
 * Created by silvanei on 26/08/2016.
 */
public interface HorarioTrabalhoService {

    public HorarioTrabalho findByDiaSemana(Integer salaoId, Integer funcionarioId, DiaDaSemana diaDaSemana);

    public List<HorarioTrabalho> findByIdFuncionario(Integer idfuncionario);

    public DiaDaSemana add(Integer salaoId, Integer funcionarioId,DiaDaSemana diaDaSemana, HorarioTrabalho horario);

    public DiaDaSemana update(Integer salaoId, Integer funcionarioId, DiaDaSemana diaDaSemana, HorarioTrabalho horario);

    public DiaDaSemana deleteHorario(Integer salaoId, Integer funcionarioId, DiaDaSemana diaDaSemana);
}
