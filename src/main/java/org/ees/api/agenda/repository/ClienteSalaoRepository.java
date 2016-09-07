package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.ClienteSalao;
import org.ees.api.agenda.infra.db.CollectionPaginated;

/**
 * Created by silvanei on 29/08/16.
 */
public interface ClienteSalaoRepository {

    public ClienteSalao get(Integer salaoId, Integer clienteSalaoId);

    public CollectionPaginated<ClienteSalao> get(Integer salaoId);

    public CollectionPaginated<ClienteSalao> get(Integer salaoId, int limit, int offset);

    public Integer create(Integer salaoId, ClienteSalao clienteSalao);
}
