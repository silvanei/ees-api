package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.ClienteApp;
import org.ees.api.agenda.repository.ClienteAppRepository;
import org.ees.api.agenda.service.ClienteAppService;

import javax.inject.Inject;

/**
 * Created by silvanei on 20/09/2016.
 */
public class ClienteAppServiceImpl implements ClienteAppService {

    private ClienteAppRepository clienteAppRepository;

    @Inject
    public ClienteAppServiceImpl(ClienteAppRepository clienteAppRepository) {
        this.clienteAppRepository = clienteAppRepository;
    }

    @Override
    public ClienteApp findById(Integer clienteId) {
        return clienteAppRepository.findById(clienteId);
    }

    @Override
    public ClienteApp create(ClienteApp cliente, Integer acessoId) {

        Integer clienteId = clienteAppRepository.create(cliente, acessoId);

        return findById(clienteId);
    }
}
