package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.infra.repository.PerfilRepositoryImpl;
import org.ees.api.agenda.repository.PerfilRepository;
import org.ees.api.agenda.service.PerfilService;

import java.util.List;

/**
 * Created by silvanei on 24/07/16.
 */
public class PerfilServiceImpl implements PerfilService{

    private PerfilRepository perfilRepository = new PerfilRepositoryImpl();
    @Override
    public Perfil findById(Integer idPerfil) {
        return perfilRepository.findById(idPerfil);
    }

    @Override
    public List<Perfil> findAll() {
        return perfilRepository.findAll();
    }
}
