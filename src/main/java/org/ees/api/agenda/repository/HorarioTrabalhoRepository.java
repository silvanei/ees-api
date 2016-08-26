package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.HorarioTrabalho;

import java.util.List;

/**
 * Created by silvanei on 26/08/2016.
 */
public interface HorarioTrabalhoRepository {

    public List<HorarioTrabalho> findByIdFuncionario(Integer idfuncionario);
}
