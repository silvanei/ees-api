package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Endereco;

/**
 * Created by silvanei on 28/07/16.
 */
public interface EnderecoRepository {

    public Integer inserirEndereco(Integer salaoId, Endereco endereco);

	public Endereco byIdSalao(Integer idSalao);

	public Integer atualizarEndereco(Endereco endereco);
}
