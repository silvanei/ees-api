package org.ees.api.agenda.entity;

import org.ees.api.agenda.resource.ClienteResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.util.List;

public class ClienteApp extends Cliente {

	private List<Salao> favoritos;

	public List<Salao> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Salao> favoritos) {
		this.favoritos = favoritos;
	}

	@InjectLink(
			resource = ClienteResource.class,
			method = "cliente",
			style = InjectLink.Style.ABSOLUTE,
			bindings = {
					@Binding(name = "clienteId", value = "${instance.id}")
			},
			rel = "self"
	)
	private Link link;

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}
}
