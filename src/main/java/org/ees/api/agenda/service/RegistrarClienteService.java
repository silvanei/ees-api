package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.ClienteApp;

public interface RegistrarClienteService {
	
	public ClienteApp registrarCliente(ClienteApp clienteApp, Acesso acesso);
}
