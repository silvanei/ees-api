package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.ClienteApp;
import org.ees.api.agenda.entity.Salao;

import java.util.List;

/**
 * Created by silvanei on 29/08/16.
 */
public interface ClienteAppService {

    public ClienteApp findById(Integer clienteId);

    public List<Salao> getFavoritos(Integer clienteId);

    public ClienteApp create(ClienteApp cliente, Integer acessoId);

    public Integer addFavorito(Integer clienteId, Integer salaoId);

    public void removeFavorito(Integer clienteId, Integer salaoId);
}
