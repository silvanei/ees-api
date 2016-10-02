package org.ees.api.agenda.entity;

import org.ees.api.agenda.resource.ClienteResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.joda.time.DateTime;

import javax.ws.rs.core.Link;

/**
 * Created by silvanei on 02/10/16.
 */
public class ReservaCliente {

    private Integer id;
    private Integer clienteId;
    private String profissional;
    private String salao;
    private String servico;
    private DateTime data;

    @InjectLink(
            resource = ClienteResource.class,
            method = "reserva",
            style = InjectLink.Style.ABSOLUTE,
            bindings = {
                    @Binding(name = "id", value = "${instance.clienteId}"),
                    @Binding(name = "reservaId", value = "${instance.id}")
            },
            rel = "self"
    )
    private Link link;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getProfissional() {
        return profissional;
    }

    public void setProfissional(String profissional) {
        this.profissional = profissional;
    }

    public String getSalao() {
        return salao;
    }

    public void setSalao(String salao) {
        this.salao = salao;
    }

    public String getServico() {
        return servico;
    }

    public void setServico(String servico) {
        this.servico = servico;
    }

    public DateTime getData() {
        return data;
    }

    public void setData(DateTime data) {
        this.data = data;
    }
}
