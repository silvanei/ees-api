package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Endereco;
import org.ees.api.agenda.infra.repository.EnderecoRepositoryImpl;
import org.ees.api.agenda.repository.EnderecoRepository;
import org.ees.api.agenda.service.EnderecoService;

/**
 * Created by silvanei on 28/07/16.
 */
public class EnderecoServiceImpl implements EnderecoService{

    private EnderecoRepository enderecoRepository = new EnderecoRepositoryImpl();

    @Override
    public Integer inserirEndereco(Endereco endereco) {
        return enderecoRepository.inserirEndereco(endereco);
    }
}
