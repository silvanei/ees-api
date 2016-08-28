package org.ees.api.agenda.resource.bean;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.infra.auth.Digest;

import javax.ws.rs.BadRequestException;

public class RegistrarSalaoBean {

	private String nomeSalao;
	private String telefoneSalao;

	private String nomeAdministradorSalao;
	private String emailAdministradorSalao;
	private String senhaAdministradorSalao;

	public String getNomeSalao() {
		return nomeSalao;
	}

	public void setNomeSalao(String nomeSalao) {
		this.nomeSalao = nomeSalao;
	}

	public String getTelefoneSalao() {
		return telefoneSalao;
	}

	public void setTelefoneSalao(String telefoneSalao) {
		this.telefoneSalao = telefoneSalao;
	}

	public String getNomeAdministradorSalao() {
		return nomeAdministradorSalao;
	}

	public void setNomeAdministradorSalao(String nomeAdministradorSalao) {
		this.nomeAdministradorSalao = nomeAdministradorSalao;
	}

	public String getEmailAdministradorSalao() {
		return emailAdministradorSalao;
	}

	public void setEmailAdministradorSalao(String emailAdministradorSalao) {
		this.emailAdministradorSalao = emailAdministradorSalao;
	}

	public String getSenhaAdministradorSalao() {
        try {
            return Digest.generate(this.senhaAdministradorSalao);
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException("Não foi possível criar a senha");
        } catch (UnsupportedEncodingException e) {
            throw new BadRequestException("Não foi possível criar a senha");
        }
    }

	public void setSenhaAdministradorSalao(String senhaAdministradorSalao) {
		try {
			this.senhaAdministradorSalao = Digest.generate(senhaAdministradorSalao);
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
			Logger.getLogger(RegistrarSalaoBean.class.getName()).log(Level.SEVERE, null, ex);
		}
		
	}

}
