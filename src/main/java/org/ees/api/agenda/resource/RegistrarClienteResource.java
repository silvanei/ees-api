package org.ees.api.agenda.resource;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.ClienteApp;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.resource.bean.RegistrarClienteBean;
import org.ees.api.agenda.service.RegistrarClienteService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.*;

@Path("/v1/registrar-cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrarClienteResource {

	private Integer clienteId;

	@Inject
	private RegistrarClienteService clienteService;

    @Context
    private UriInfo uriInfo;

	@POST
	public Response registarCliente(RegistrarClienteBean registrarClienteBean) {

		ClienteApp clienteApp = new ClienteApp();
		clienteApp.setNome(registrarClienteBean.getNome());
		clienteApp.setTelefone(registrarClienteBean.getTelefone());

		Acesso acesso = new Acesso(Perfil.CLIENTE, registrarClienteBean.getEmail(), registrarClienteBean.getSenha());

		ClienteApp newCliente = clienteService.registrarCliente(clienteApp, acesso);

		this.clienteId = newCliente.getId();

        UriBuilder builder = uriInfo.getBaseUriBuilder();
        builder.path("/v1/cliente/" + Integer.toString(newCliente.getId()));
        return Response.created(builder.build()).entity(newCliente).build();

	}

	public Integer getClienteId() {
		return clienteId;
	}

	public void setClienteId(Integer clienteId) {
		this.clienteId = clienteId;
	}
}
