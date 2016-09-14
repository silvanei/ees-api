package org.ees.api.agenda.entity;

import org.ees.api.agenda.resource.AgendaResource;
import org.ees.api.agenda.resource.DadosResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.ws.rs.core.Link;

/**
 * Created by silvanei on 10/09/16.
 */
public class Event {

    private Integer id;
    private Integer clienteId;
    private Integer servicoId;
    private Integer funcionarioId;
    private String observacao;
    private Integer status = 1;
    private String title;
    private DateTime start;
    private DateTime end;

    @InjectLink(
            resource = AgendaResource.class,
            method = "evento",
            style = InjectLink.Style.ABSOLUTE,
            bindings = {
                    @Binding(name = "salaoId", value = "${resource.salaoId}"),
                    @Binding(name = "dia", value = "${instance.dia}"),
                    @Binding(name = "eventId", value = "${instance.id}")
            },
            rel = "self"
    )
    private Link link;

    public Event() {
    }

    public Event(Integer funcionarioId, String title, DateTime start, DateTime end) {
        setFuncionarioId(funcionarioId);
        setTitle(title);
        setStart(start);
        setEnd(end);
    }

    public Event(Integer id, Integer resourceId, String title, DateTime start, DateTime end) {
        this(resourceId, title, start, end);
        setId(id);
    }

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

    public Integer getServicoId() {
        return servicoId;
    }

    public void setServicoId(Integer servicoId) {
        this.servicoId = servicoId;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public DateTime getStart() {
        return start;
    }

    public void setStart(DateTime start) {
        this.start = start;
    }

    public DateTime getEnd() {
        return end;
    }

    public void setEnd(DateTime end) {
        this.end = end;
    }

    public String getDia() {
        DateTimeFormatter dtfPadrao = DateTimeFormat.forPattern("yyyy-MM-dd");
        return start.toString(dtfPadrao);
    }
}
