package org.ees.api.agenda.service;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.entity.Salao;

public interface RegistrarSalaoService {
	
	public Salao registrarSalao(Salao salao, Funcionario administrador, Acesso acesso);
}
