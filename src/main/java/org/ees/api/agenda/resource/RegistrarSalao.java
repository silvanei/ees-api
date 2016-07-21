package org.ees.api.agenda.resource;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.Conexao;
import org.jose4j.lang.JoseException;

@Path("/registrar-salao")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class RegistrarSalao {

	@POST
	public Salao addSalao(Salao salao) throws JoseException {
		return salao;
	}
}
