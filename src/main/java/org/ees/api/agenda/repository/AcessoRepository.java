package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Acesso;

public interface AcessoRepository {

	public Integer insert(Acesso acesso);

	public Acesso findByEmail(String email);
}
