package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.ClienteApp;
import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.infra.exceptions.DataNotFoundException;
import org.ees.api.agenda.repository.ClienteAppRepository;
import org.ees.api.agenda.service.*;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by silvanei on 20/09/2016.
 */
public class ClienteAppServiceImpl implements ClienteAppService {

    private ClienteAppRepository clienteAppRepository;
    private DadosSalaoService dadosSalaoService;
    private ImageFileService imageFileService;
    private AcessoService acessoService;
    private EnderecoService enderecoService;

    @Inject
    public ClienteAppServiceImpl(
            ClienteAppRepository clienteAppRepository,
            DadosSalaoService dadosSalaoService,
            ImageFileService imageFileService,
            AcessoService acessoService,
            EnderecoService enderecoService
    ) {
        this.clienteAppRepository = clienteAppRepository;
        this.dadosSalaoService = dadosSalaoService;
        this.imageFileService = imageFileService;
        this.acessoService = acessoService;
        this.enderecoService = enderecoService;
    }

    @Override
    public ClienteApp findById(Integer clienteId) {
        ClienteApp clienteApp = clienteAppRepository.findById(clienteId);

        if (null == clienteApp) {
            throw new DataNotFoundException("Cliente não encontrado");
        }

        clienteApp.setEndereco(enderecoService.byIdCliente(clienteApp.getId()));

        return clienteApp;
    }

    @Override
    public List<Salao> getFavoritos(Integer clienteId) {

        List<Salao> saloes = dadosSalaoService.findByClienteId(clienteId);

        for (Salao salao : saloes) {
            String encodedString = imageFileService.base64Encode(salao.getId());
            if (null == encodedString) {
                encodedString = ImageFileService.IMG_DEFAULT;
            }

            encodedString = "data:image/jpeg;base64," + encodedString;

            salao.setImgBase64(encodedString);
        }

        return saloes;
    }

    @Override
    public ClienteApp create(ClienteApp cliente, Integer acessoId) {

        if(null == cliente.getEndereco().getId()) {
            Integer idEndereco = enderecoService.inserirEndereco(cliente.getEndereco());
            cliente.getEndereco().setId(idEndereco);
        } else {
            enderecoService.atualizarEndereco(cliente.getEndereco());
        }

        Integer clienteId = clienteAppRepository.create(cliente, acessoId);

        return findById(clienteId);
    }

    @Override
    public Integer addFavorito(Integer clienteId, Integer salaoId) {

        Acesso acesso = acessoService.findByCliente(clienteId);

        return clienteAppRepository.addFavorito(clienteId, salaoId, acesso.getId());
    }

    @Override
    public void removeFavorito(Integer clienteId, Integer salaoId) {
        clienteAppRepository.removeFavorito(clienteId, salaoId);
    }

    @Override
    public ClienteApp update(Integer clienteId, ClienteApp data) {
        findById(clienteId);

        try {
            DB.beginTransaction();

            enderecoService.atualizarEndereco(data.getEndereco());
            clienteAppRepository.update(clienteId, data);

            DB.commit();

            return findById(clienteId);
        } catch (AcessoADadosException e ) {
            DB.rollback();
            throw e;
        }
    }
}
