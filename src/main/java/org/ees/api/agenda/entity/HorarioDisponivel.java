package org.ees.api.agenda.entity;

import org.joda.time.DateTime;

/**
 * Created by silvanei on 12/09/16.
 */
public class HorarioDisponivel {

    private DateTime horario;

    public HorarioDisponivel(DateTime horario) {
        this.horario = horario;
    }

    public DateTime getHorario() {
        return horario;
    }
}
