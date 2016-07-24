package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.infra.repository.AcessoRepositoryImpl;
import org.ees.api.agenda.repository.AcessoRepository;
import org.ees.api.agenda.service.AcessoService;

/**
 * Created by silvanei on 24/07/16.
 */
public class AcessoServiceImpl implements AcessoService {

    private AcessoRepository acessoRepository = new AcessoRepositoryImpl();

    @Override
    public Acesso findById(Integer id) {
        return acessoRepository.findById(id);
    }

    @Override
    public Acesso findByEmail(String email) {

        return acessoRepository.findByEmail(email);
    }
}
