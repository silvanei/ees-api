package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Salao;

public interface SalaoRepository {

	public Integer insert(Salao salao);

	public Salao findById(Integer idSalao);
	
}
