package org.ees.api.agenda.entity;

import org.ees.api.agenda.resource.EstadoResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.util.List;

/**
 * Created by silvanei on 25/09/16.
 */
public class Estado {

    private Integer id;
    private String nome;
    private String uf;

    private List<Cidade> cidades;

    @InjectLink(
            resource = EstadoResource.class,
            method = "cidade",
            style = InjectLink.Style.ABSOLUTE,
            bindings = {
                    @Binding(name = "estadoId", value = "${instance.id}")
            },
            rel = "self"
    )
    private Link link;

    public Estado() {}

    public Estado(Integer id, String nome, String uf) {
        this.id = id;
        this.nome = nome;
        this.uf = uf;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public List<Cidade> getCidades() {
        return cidades;
    }

    public void setCidades(List<Cidade> cidades) {
        this.cidades = cidades;
    }
}
