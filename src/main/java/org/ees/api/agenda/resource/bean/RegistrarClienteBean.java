package org.ees.api.agenda.resource.bean;

import org.ees.api.agenda.infra.auth.Digest;

import javax.ws.rs.BadRequestException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by silvanei on 20/09/2016.
 */
public class RegistrarClienteBean {

    private String nome;
    private String telefone;
    private String email;
    private String senha;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        try {
            return Digest.generate(this.senha);
        } catch (NoSuchAlgorithmException e) {
            throw new BadRequestException("Não foi possível criar a senha");
        } catch (UnsupportedEncodingException e) {
            throw new BadRequestException("Não foi possível criar a senha");
        }
    }

    public void setSenha(String senha) {
        try {
            this.senha = Digest.generate(senha);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex) {
            Logger.getLogger(RegistrarSalaoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
