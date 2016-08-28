package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.infra.repository.AcessoRepositoryImpl;
import org.ees.api.agenda.repository.AcessoRepository;
import org.ees.api.agenda.service.AcessoService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by silvanei on 24/07/16.
 */
public class AcessoServiceImpl implements AcessoService {

    private AcessoRepository acessoRepository;

    @Inject
    public AcessoServiceImpl(AcessoRepository acessoRepository) {
        this.acessoRepository = acessoRepository;
    }

    @Override
    public Acesso findById(Integer id) {
        return acessoRepository.findById(id);
    }

    @Override
    public Acesso findByEmail(String email) {

        return acessoRepository.findByEmail(email);
    }

    @Override
    public List<Acesso> findByIdSalao(Integer salaoId) {
        return acessoRepository.findByIdSalao(salaoId);
    }
}
