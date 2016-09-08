package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.ClienteSalao;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.repository.ClienteSalaoRepository;
import org.ees.api.agenda.service.ClienteSalaoService;

import javax.inject.Inject;

/**
 * Created by silvanei on 29/08/16.
 */
public class ClienteSalaoServiceImpl implements ClienteSalaoService {

    @Inject
    private ClienteSalaoRepository clienteSalaoRepository;

    @Override
    public CollectionPaginated<ClienteSalao> get(Integer salaoId) {
        return clienteSalaoRepository.get(salaoId);
    }

    @Override
    public CollectionPaginated<ClienteSalao> get(Integer salaoId, int limit, int offset) {
        return clienteSalaoRepository.get(salaoId, limit, offset);
    }

    @Override
    public ClienteSalao findById(Integer salaoId, Integer clienteSalaoId) {
        ClienteSalao clienteSalao = clienteSalaoRepository.get(salaoId, clienteSalaoId);
        if (null == clienteSalao) {
            throw new DataNotFoundException("Cliente não encontrado");
        }

        return clienteSalao;
    }

    @Override
    public ClienteSalao create(Integer salaoId, ClienteSalao clienteSalao) {
        Integer clienteId = clienteSalaoRepository.create(salaoId, clienteSalao);

        return findById(salaoId, clienteId);
    }

    @Override
    public ClienteSalao update(Integer salaoId, Integer clienteSalaoId, ClienteSalao data) {
        findById(salaoId, clienteSalaoId);

        clienteSalaoRepository.update(salaoId, clienteSalaoId, data);

        return findById(salaoId, clienteSalaoId);
    }

    @Override
    public Integer delete(Integer salaoId, Integer clienteSalaoId) {
        findById(salaoId, clienteSalaoId);

        return clienteSalaoRepository.delete(salaoId, clienteSalaoId);
    }
}
