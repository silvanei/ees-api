package org.ees.api.agenda.entity;

import org.ees.api.agenda.resource.FuncionarioResource;
import org.glassfish.jersey.linking.Binding;
import org.glassfish.jersey.linking.InjectLink;

import javax.ws.rs.core.Link;
import java.sql.Time;

public class HorarioTrabalho {

    private Integer funcionarioId;
    private DiaDaSemana diaDaSemana;
    private Time entrada1;
    private Time saida1;
    private Time entrada2;
    private Time saida2;

    @InjectLink(
            resource = FuncionarioResource.class,
            method = "adicionarHorario",
            style = InjectLink.Style.ABSOLUTE,
            bindings = {
                    @Binding(name = "diaDaSemana", value = "${instance.diaDaSemana.dia}")
            },
            rel = "self"
    )
    private Link link;

    public HorarioTrabalho() {
    }

    public HorarioTrabalho(Integer funcionarioId, DiaDaSemana diaDaSemana, Time entrada1, Time saida1, Time entrada2, Time saida2) {
        this.funcionarioId = funcionarioId;
        this.diaDaSemana = diaDaSemana;
        this.entrada1 = entrada1;
        this.saida1 = saida1;
        this.entrada2 = entrada2;
        this.saida2 = saida2;
    }

    public Integer getFuncionarioId() {
        return funcionarioId;
    }

    public void setFuncionarioId(Integer funcionarioId) {
        this.funcionarioId = funcionarioId;
    }

    public DiaDaSemana getDiaDaSemana() {
        return diaDaSemana;
    }

    public Time getEntrada1() {
        return entrada1;
    }

    public Time getSaida1() {
        return saida1;
    }

    public Time getEntrada2() {
        return entrada2;
    }

    public Time getSaida2() {
        return saida2;
    }

    public void setDiaDaSemana(DiaDaSemana diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public void setEntrada1(Time entrada1) {
        this.entrada1 = entrada1;
    }

    public void setSaida1(Time saida1) {
        this.saida1 = saida1;
    }

    public void setEntrada2(Time entrada2) {
        this.entrada2 = entrada2;
    }

    public void setSaida2(Time saida2) {
        this.saida2 = saida2;
    }
}
