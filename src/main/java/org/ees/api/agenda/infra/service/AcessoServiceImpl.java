package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.infra.exceptions.ConflictException;
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
    public Integer insert(Acesso acesso) {

        if(null != acessoRepository.findByEmail(acesso.getEmail())) {
            throw new ConflictException("E-mail já existente no sistema");
        }

        return acessoRepository.insert(acesso);
    }

    @Override
    public Integer removeAcesso(Integer salaoId, Integer funcionarioId, Integer acessoId) {
        return acessoRepository.removeAcesso(salaoId, funcionarioId, acessoId);
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
