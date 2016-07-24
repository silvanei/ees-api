package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Acesso;

/**
 * Created by silvanei on 24/07/16.
 */
public interface AcessoService {

    public Acesso findByEmail(String email);

}
