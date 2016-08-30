package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.ClienteSalao;

/**
 * Created by silvanei on 29/08/16.
 */
public interface ClienteSalaoRepository {

    public ClienteSalao get(Integer salaoId, Integer clienteSalaoId);
}
