package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Perfil;

import java.util.List;

public interface PerfilRepository {

	public Perfil findById(Integer idPerfil);

	public List<Perfil> findAll();
}
