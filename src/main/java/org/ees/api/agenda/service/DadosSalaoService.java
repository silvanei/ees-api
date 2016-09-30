package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.resource.bean.DadosSalao;

import java.util.List;

/**
 * Created by silvanei on 28/07/16.
 */
public interface DadosSalaoService {

    public Salao findById(Integer salaoId);

    public Salao atualizaDadosSalao(Integer salaoId, DadosSalao dadosSalao);

    public List<Salao> findByClienteId(Integer clienteId);

    public List<Salao> findAll(String nomeSalao);

    public Salao servicos(Integer salaoId);
}
