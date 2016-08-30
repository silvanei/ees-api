package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.ClienteSalao;

/**
 * Created by silvanei on 29/08/16.
 */
public interface ClienteSalaoService {

    public ClienteSalao get(Integer salaoId);

    public ClienteSalao get(Integer salaoId, Integer clienteSalaoId);

}
