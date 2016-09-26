package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Salao;

import java.util.List;

public interface SalaoRepository {

	public Integer insert(Salao salao);

	public Integer update(Salao salao);

	public Salao findById(Integer idSalao);

    public List<Salao> findByClienteId(Integer clienteId);

	public List<Salao> findAll(String nomeSalao);
}
