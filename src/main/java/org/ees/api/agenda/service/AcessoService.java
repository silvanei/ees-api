package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Acesso;

import java.util.List;

/**
 * Created by silvanei on 24/07/16.
 */
public interface AcessoService {

    public Acesso findById(Integer id);

    public Acesso findByEmail(String email);

    public List<Acesso> findByIdSalao(Integer salaoId);
}
