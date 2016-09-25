package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.ClienteApp;
import org.ees.api.agenda.entity.Salao;

import java.util.List;

/**
 * Created by silvanei on 20/09/2016.
 */
public interface ClienteAppRepository {

    public Integer create(ClienteApp cliente, Integer acessoId);

    public ClienteApp findById(Integer clienteId);

    public Integer addFavorito(Integer clienteId, Integer salaoId, Integer acessoId);

    public Integer removeFavorito(Integer clienteId, Integer salaoId);
}
