package org.ees.api.agenda.infra.rest;

import org.ees.api.agenda.infra.service.ServicoServiceFactory;
import org.ees.api.agenda.service.ServicoService;
import org.glassfish.hk2.utilities.binding.AbstractBinder;

public class ApplicationBinder extends AbstractBinder {

	@Override
	protected void configure() {
		bindFactory(ServicoServiceFactory.class)
			.to(ServicoService.class);		
	}

}
