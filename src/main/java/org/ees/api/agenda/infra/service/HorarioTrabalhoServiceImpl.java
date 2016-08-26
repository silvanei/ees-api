package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.HorarioTrabalho;
import org.ees.api.agenda.repository.HorarioTrabalhoRepository;
import org.ees.api.agenda.service.HorarioTrabalhoService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by silvanei on 26/08/2016.
 */
public class HorarioTrabalhoServiceImpl implements HorarioTrabalhoService {

    private HorarioTrabalhoRepository horarioTrabalhoRepository;

    @Inject
    public HorarioTrabalhoServiceImpl(HorarioTrabalhoRepository horarioTrabalhoRepository) {
        this.horarioTrabalhoRepository = horarioTrabalhoRepository;
    }

    @Override
    public List<HorarioTrabalho> findByIdFuncionario(Integer idfuncionario) {
        return horarioTrabalhoRepository.findByIdFuncionario(idfuncionario);
    }
}
