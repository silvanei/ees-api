package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.ClienteApp;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.infra.exceptions.ConflictException;
import org.ees.api.agenda.service.AcessoService;
import org.ees.api.agenda.service.ClienteAppService;
import org.ees.api.agenda.service.EnderecoService;
import org.ees.api.agenda.service.RegistrarClienteService;

import javax.inject.Inject;

/**
 * Created by silvanei on 20/09/2016.
 */
public class RegistrarClienteServiceImpl implements RegistrarClienteService {

    private AcessoService acessoService;
    private ClienteAppService clienteAppService;

    @Inject
    public RegistrarClienteServiceImpl(AcessoService acessoService, ClienteAppService clienteAppService) {
        this.acessoService = acessoService;
        this.clienteAppService = clienteAppService;
    }

    @Override
    public ClienteApp registrarCliente(ClienteApp clienteApp, Acesso acesso) {

        if(null != acessoService.findByEmail(acesso.getEmail())) {
            throw new ConflictException("E-mail já existente no sistema");
        }

        try {
            DB.beginTransaction();

            Integer acessoId = acessoService.insert(acesso);
            ClienteApp newCliente = clienteAppService.create(clienteApp, acessoId);

            DB.commit();

            return newCliente;

        } catch (AcessoADadosException e) {
            DB.rollback();
            throw e;
        }
    }
}
