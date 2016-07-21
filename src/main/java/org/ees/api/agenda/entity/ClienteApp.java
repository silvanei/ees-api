package org.ees.api.agenda.entity;

import java.util.List;

public class ClienteApp extends Cliente {

	private List<Salao> favoritos;

	public List<Salao> getFavoritos() {
		return favoritos;
	}

	public void setFavoritos(List<Salao> favoritos) {
		this.favoritos = favoritos;
	}

}
