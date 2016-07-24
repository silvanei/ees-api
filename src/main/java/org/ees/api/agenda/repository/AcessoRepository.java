package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Acesso;

public interface AcessoRepository {

	public Integer insert(Acesso acesso);

	public Acesso findById(Integer id);

	public Acesso findByEmail(String email);
}
