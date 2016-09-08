package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.ClienteSalao;
import org.ees.api.agenda.infra.db.CollectionPaginated;

/**
 * Created by silvanei on 29/08/16.
 */
public interface ClienteSalaoService {

    public ClienteSalao findById(Integer salaoId, Integer clienteSalaoId);

    public CollectionPaginated<ClienteSalao> get(Integer salaoId);

    public CollectionPaginated<ClienteSalao> get(Integer salaoId, int limit, int offset);

    public ClienteSalao create(Integer salaoId, ClienteSalao clienteSalao);

    public ClienteSalao update(Integer salaoId, Integer clienteSalaoId, ClienteSalao data);

    public Integer delete(Integer salaoId, Integer clienteSalaoId);
}
