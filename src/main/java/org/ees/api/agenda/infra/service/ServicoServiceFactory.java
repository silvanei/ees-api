package org.ees.api.agenda.infra.service;

import org.ees.api.agenda.service.ServicoService;
import org.glassfish.hk2.api.Factory;

public class ServicoServiceFactory implements Factory<ServicoService> {

	public ServicoServiceFactory() {
		System.out.println("AQUI");
	}

	@Override
	public void dispose(ServicoService arg0) {
		// TODO Auto-generated method stub
		System.out.println("ServicoService dispose");
		
	}

	@Override
	public ServicoService provide() {
		System.out.println("ServicoService provide");
		//return new ServicoServiceImpl();
		return null;
	}
	
}
