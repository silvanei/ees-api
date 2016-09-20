package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.ClienteApp;

/**
 * Created by silvanei on 29/08/16.
 */
public interface ClienteAppService {

    public ClienteApp findById(Integer clienteId);

//    public CollectionPaginated<ClienteSalao> get(Integer salaoId);
//
//    public CollectionPaginated<ClienteSalao> get(Integer salaoId, int limit, int offset);

    public ClienteApp create(ClienteApp cliente, Integer acessoId);

//    public ClienteSalao update(Integer salaoId, Integer clienteSalaoId, ClienteSalao data);
//
//    public Integer delete(Integer salaoId, Integer clienteSalaoId);
}
