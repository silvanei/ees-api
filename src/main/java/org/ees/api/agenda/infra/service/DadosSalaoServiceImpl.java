package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Endereco;
import org.ees.api.agenda.entity.HorarioDeFuncionamento;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.infra.repository.SalaoRepositoryImpl;
import org.ees.api.agenda.repository.SalaoRepository;
import org.ees.api.agenda.resource.bean.DadosSalao;
import org.ees.api.agenda.service.DadosSalaoService;
import org.ees.api.agenda.service.EnderecoService;
import org.ees.api.agenda.service.HorarioDeFuncionamentoService;

/**
 * Created by silvanei on 28/07/16.
 */
public class DadosSalaoServiceImpl implements DadosSalaoService {

    SalaoRepository salaoRepository = new SalaoRepositoryImpl();
    EnderecoService enderecoService = new EnderecoServiceImpl();
    HorarioDeFuncionamentoService horarioDeFuncionamentoService = new HorarioDeFuncionamentoServiceImpl();

    @Override
    public Salao atualizaDadosSalao(Integer idSalao, DadosSalao dadosSalao) {

        try{
            DB.beginTransaction();
        	
            Endereco endereco = enderecoService.byIdSalao(idSalao);
            endereco.setRua(dadosSalao.getEndereco().getRua());
            endereco.setEstado(dadosSalao.getEndereco().getEstado());
            endereco.setCidade(dadosSalao.getEndereco().getCidade());
            endereco.setBairro(dadosSalao.getEndereco().getBairro());
            endereco.setNumero(dadosSalao.getEndereco().getNumero());
            endereco.setCep(dadosSalao.getEndereco().getCep());
            if(null == endereco.getId()) {
            	Integer idEndereco = enderecoService.inserirEndereco(endereco);
            	endereco.setId(idEndereco);
            } else {
            	enderecoService.atualizarEndereco(endereco);
            }
            
            HorarioDeFuncionamento horarioDeFuncionamento = horarioDeFuncionamentoService.byIdSalao(idSalao);
            horarioDeFuncionamento.setHorarioInicio(dadosSalao.getHorarioDeFuncionamento().getHorarioInicio());
            horarioDeFuncionamento.setHorarioFinal(dadosSalao.getHorarioDeFuncionamento().getHorarioFinal());
            horarioDeFuncionamento.setSegunda(dadosSalao.getHorarioDeFuncionamento().isSegunda());
            horarioDeFuncionamento.setTerca(dadosSalao.getHorarioDeFuncionamento().isTerca());
            horarioDeFuncionamento.setQuarta(dadosSalao.getHorarioDeFuncionamento().isQuarta());
            horarioDeFuncionamento.setQuinta(dadosSalao.getHorarioDeFuncionamento().isQuinta());
            horarioDeFuncionamento.setSexta(dadosSalao.getHorarioDeFuncionamento().isSexta());
            horarioDeFuncionamento.setSabado(dadosSalao.getHorarioDeFuncionamento().isSabado());
            horarioDeFuncionamento.setDomingo(dadosSalao.getHorarioDeFuncionamento().isDomingo());
            if(null == horarioDeFuncionamento.getId()) {
            	Integer idHorarioDeFuncionamento = horarioDeFuncionamentoService.inserirHorarioDeFuncionamento(horarioDeFuncionamento);
            	horarioDeFuncionamento.setId(idHorarioDeFuncionamento);
            } else {
            	horarioDeFuncionamentoService.atualizarHorarioDeFuncionamento(horarioDeFuncionamento);
            }
            
            Salao salao = new Salao();
            salao.setId(idSalao);
            salao.setNome(dadosSalao.getNome());
            salao.setVisivelNoApp(dadosSalao.isVisivelNoApp());
            salao.setTelefone(dadosSalao.getTelefone());
            salao.setCelular(dadosSalao.getCelular());
            salao.setEndereco(endereco);
            salao.setHorarioDeFuncionamento(horarioDeFuncionamento);
            
            salaoRepository.update(salao);

            DB.commit();

            return salao;
        } catch (AcessoADadosException e) {
            DB.rollback();
            throw e;
        }
    }
}
