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
    public ClienteSalao get(Integer salaoId, Integer clienteSalaoId) {
        ClienteSalao clienteSalao = clienteSalaoRepository.get(salaoId, clienteSalaoId);
        if (null == clienteSalao) {
            throw new DataNotFoundException("Cliente não encontrado");
        }

        return clienteSalao;
    }

    @Override
    public ClienteSalao create(Integer salaoId, ClienteSalao clienteSalao) {
        Integer clienteId = clienteSalaoRepository.create(salaoId, clienteSalao);

        return get(salaoId, clienteId);
    }
}
