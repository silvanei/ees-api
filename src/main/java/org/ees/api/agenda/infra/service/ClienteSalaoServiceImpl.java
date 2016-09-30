package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.ClienteSalao;
import org.ees.api.agenda.entity.Endereco;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.repository.ClienteSalaoRepository;
import org.ees.api.agenda.service.ClienteSalaoService;
import org.ees.api.agenda.service.EnderecoService;

import javax.inject.Inject;

/**
 * Created by silvanei on 29/08/16.
 */
public class ClienteSalaoServiceImpl implements ClienteSalaoService {

    private ClienteSalaoRepository clienteSalaoRepository;
    private EnderecoService enderecoService;

    @Inject
    public ClienteSalaoServiceImpl(ClienteSalaoRepository clienteSalaoRepository, EnderecoService enderecoService) {
        this.clienteSalaoRepository = clienteSalaoRepository;
        this.enderecoService = enderecoService;
    }

    @Override
    public CollectionPaginated<ClienteSalao> get(Integer salaoId) {
        CollectionPaginated<ClienteSalao> clientes =  clienteSalaoRepository.get(salaoId);

        for (ClienteSalao cliente: clientes.getItems()) {
            cliente.setEndereco(enderecoService.byIdCliente(cliente.getId()));
        }

        return clientes;
    }

    @Override
    public CollectionPaginated<ClienteSalao> get(Integer salaoId, int limit, int offset) {
        CollectionPaginated<ClienteSalao> clientes =  clienteSalaoRepository.get(salaoId, limit, offset);

        for (ClienteSalao cliente: clientes.getItems()) {
            cliente.setEndereco(enderecoService.byIdCliente(cliente.getId()));
        }

        return clientes;
    }

    @Override
    public ClienteSalao findById(Integer salaoId, Integer clienteSalaoId) {
        ClienteSalao clienteSalao = clienteSalaoRepository.get(salaoId, clienteSalaoId);
        if (null == clienteSalao) {
            throw new DataNotFoundException("Cliente não encontrado");
        }

        clienteSalao.setEndereco(enderecoService.byIdCliente(clienteSalao.getId()));

        return clienteSalao;
    }

    @Override
    public ClienteSalao create(Integer salaoId, ClienteSalao clienteSalao) {
        try {
            DB.beginTransaction();

            if(null == clienteSalao.getEndereco()) {
                clienteSalao.setEndereco(new Endereco());
            }

            if(null == clienteSalao.getEndereco().getId()) {
                Integer idEndereco = enderecoService.inserirEndereco(clienteSalao.getEndereco());
                clienteSalao.getEndereco().setId(idEndereco);
            } else {
                enderecoService.atualizarEndereco(clienteSalao.getEndereco());
            }

            Integer clienteId = clienteSalaoRepository.create(salaoId, clienteSalao);

            DB.commit();

            return findById(salaoId, clienteId);
        } catch (AcessoADadosException e ) {
            DB.rollback();
            throw e;
        }
    }

    @Override
    public ClienteSalao update(Integer salaoId, Integer clienteSalaoId, ClienteSalao data) {
        findById(salaoId, clienteSalaoId);

        try {
            DB.beginTransaction();

            if(null == data.getEndereco().getId()) {
                Integer idEndereco = enderecoService.inserirEndereco(data.getEndereco());
                data.getEndereco().setId(idEndereco);
            } else {
                enderecoService.atualizarEndereco(data.getEndereco());
            }

            clienteSalaoRepository.update(salaoId, clienteSalaoId, data);

            DB.commit();

            return findById(salaoId, clienteSalaoId);
        } catch (AcessoADadosException e ) {
            DB.rollback();
            throw e;
        }
    }

    @Override
    public Integer delete(Integer salaoId, Integer clienteSalaoId) {
        findById(salaoId, clienteSalaoId);

        return clienteSalaoRepository.delete(salaoId, clienteSalaoId);
    }
}
