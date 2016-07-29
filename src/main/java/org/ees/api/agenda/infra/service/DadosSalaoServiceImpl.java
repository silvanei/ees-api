package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Endereco;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.infra.repository.SalaoRepositoryImpl;
import org.ees.api.agenda.repository.SalaoRepository;
import org.ees.api.agenda.resource.bean.DadosSalao;
import org.ees.api.agenda.service.DadosSalaoService;
import org.ees.api.agenda.service.EnderecoService;

/**
 * Created by silvanei on 28/07/16.
 */
public class DadosSalaoServiceImpl implements DadosSalaoService {

    SalaoRepository salaoRepository = new SalaoRepositoryImpl();
    EnderecoService enderecoService = new EnderecoServiceImpl();

    @Override
    public Salao atualizaDadosSalao(Integer idSalao, DadosSalao dadosSalao) {

        try{
            DB.beginTransaction();

            Endereco endereco = new Endereco();
            endereco.setRua(dadosSalao.getEndereco().getRua());
            endereco.setEstado(dadosSalao.getEndereco().getEstado());
            endereco.setCidade(dadosSalao.getEndereco().getCidade());
            endereco.setBairro(dadosSalao.getEndereco().getBairro());
            endereco.setNumero(dadosSalao.getEndereco().getNumero());
            endereco.setCep(dadosSalao.getEndereco().getCep());
            Integer idEndereco = enderecoService.inserirEndereco(endereco);
            endereco.setId(idEndereco);


            Salao salao = new Salao();
            salao.setId(idSalao);
            salao.setNome(dadosSalao.getNome());
            salao.setVisivelNoApp(dadosSalao.isVisivelNoApp());
            salao.setTelefone(dadosSalao.getTelefone());
            salao.setCelular(dadosSalao.getCelular());
            salao.setEndereco(endereco);

            salaoRepository.update(salao);

            DB.commit();

            return salao;
        } catch (AcessoADadosException e) {
            DB.rollback();
            throw e;
        }
    }
}
