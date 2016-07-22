package org.ees.api.agenda.infra.db.exceptions;

public class AcessoADadosException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public AcessoADadosException () {

    }

    public AcessoADadosException (String message) {
        super (message);
    }

    public AcessoADadosException (Throwable cause) {
        super (cause);
    }

    public AcessoADadosException (String message, Throwable cause) {
        super (message, cause);
    }

}
