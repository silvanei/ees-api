package org.ees.api.agenda.entity;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by silvanei on 12/09/16.
 */
public class HorarioDisponivel {

    private DateTime dia;
    private List<DateTime> horarios;

    public HorarioDisponivel() {
        horarios = new ArrayList<>();
    }

    public HorarioDisponivel(DateTime dia) {
        this();
        this.dia = dia;
    }

    public DateTime getDia() {
        return dia;
    }

    public void setDia(DateTime dia) {
        this.dia = dia;
    }

    public List<DateTime> getHorarios() {
        return horarios;
    }

    public void setHorarios(List<DateTime> horarios) {
        this.horarios = horarios;
    }
}
