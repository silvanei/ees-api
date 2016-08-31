package org.ees.api.agenda.entity;

import org.ees.api.agenda.resource.ClienteSalaoResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;

public class ClienteSalao extends Cliente {

	@InjectLink(
			resource = ClienteSalaoResource.class,
			method = "cliente",
			style = InjectLink.Style.ABSOLUTE,
			bindings = {
					@Binding(name = "clienteSalaoId", value = "${instance.id}"),
					@Binding(name = "salaoId", value = "${resource.salaoId}")
			},
			rel = "self"
	)
	private Link link;

	private Salao salao;

	public Salao getSalao() {
		return salao;
	}

	public void setSalao(Salao salao) {
		this.salao = salao;
	}

}
