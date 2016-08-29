package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Acesso;

import java.util.List;

/**
 * Created by silvanei on 24/07/16.
 */
public interface AcessoService {

    public Integer insert(Acesso acesso);

    public Acesso findById(Integer id);

    public Acesso findByEmail(String email);

    public Acesso findByFuncionario(Integer funcionarioId);

    public List<Acesso> findByIdSalao(Integer salaoId);

    public Integer removeAcesso(Integer acessoId);
}
