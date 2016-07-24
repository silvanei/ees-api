package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Perfil;

import java.util.List;

/**
 * Created by silvanei on 24/07/16.
 */
public interface PerfilService {

    public Perfil findById(Integer idPerfil);

    public List<Perfil> findAll();
}
