package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.HorarioTrabalho;

import java.util.List;

/**
 * Created by silvanei on 26/08/2016.
 */
public interface HorarioTrabalhoService {

    public List<HorarioTrabalho> findByIdFuncionario(Integer idfuncionario);
}
