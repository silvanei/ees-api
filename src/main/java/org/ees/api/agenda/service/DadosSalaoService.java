package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.resource.bean.DadosSalao;

/**
 * Created by silvanei on 28/07/16.
 */
public interface DadosSalaoService {

    public Salao findById(Integer salaoId);

    public Salao atualizaDadosSalao(Integer salaoId, DadosSalao dadosSalao);
}
