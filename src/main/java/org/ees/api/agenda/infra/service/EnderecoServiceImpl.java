package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Endereco;
import org.ees.api.agenda.entity.Estado;
import org.ees.api.agenda.repository.EnderecoRepository;
import org.ees.api.agenda.service.EnderecoService;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by silvanei on 28/07/16.
 */
public class EnderecoServiceImpl implements EnderecoService{

    private EnderecoRepository enderecoRepository;

	@Inject
	public EnderecoServiceImpl(EnderecoRepository enderecoRepository) {
		this.enderecoRepository = enderecoRepository;
	}

	@Override
    public Integer inserirEndereco(Endereco endereco) {
        return enderecoRepository.inserirEndereco(endereco);
    }

	@Override
	public Endereco byIdSalao(Integer idSalao) {
		return enderecoRepository.byIdSalao(idSalao);
	}

	@Override
	public Integer atualizarEndereco(Endereco endereco) {
		return enderecoRepository.atualizarEndereco(endereco);
	}

	@Override
	public List<Estado> getEstados() {
		return enderecoRepository.getEstados();
	}

	@Override
	public Estado getEstado(Integer estadoId) {
		Estado estado = enderecoRepository.getEstado(estadoId);

		estado.setCidades(enderecoRepository.getCidades(estadoId));

		return estado;
	}

	@Override
	public Endereco byIdCliente(Integer id) {
		return enderecoRepository.byIdCliente(id);
	}
}
