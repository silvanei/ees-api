package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Acesso;

import java.util.List;

public interface AcessoRepository {

	public Integer insert(Acesso acesso);

	public Acesso findById(Integer id);

	public Acesso findByEmail(String email);

	public List<Acesso> findByIdSalao(Integer salaoId);
}
