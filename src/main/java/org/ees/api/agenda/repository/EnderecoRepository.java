package org.ees.api.agenda.repository;

import org.ees.api.agenda.entity.Cidade;
import org.ees.api.agenda.entity.Endereco;
import org.ees.api.agenda.entity.Estado;

import java.util.List;

/**
 * Created by silvanei on 28/07/16.
 */
public interface EnderecoRepository {

    public Integer inserirEndereco(Endereco endereco);

	public Endereco byIdSalao(Integer idSalao);

	public Integer atualizarEndereco(Endereco endereco);

	public List<Estado> getEstados();

	public Estado getEstado(Integer estadoId);

	public List<Cidade> getCidades(Integer estadoId);

	public Endereco byIdCliente(Integer id);
}
