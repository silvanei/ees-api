package org.ees.api.agenda.entity;

import java.sql.Time;

public class HorarioTrabalho {

    private DiaDaSemana diaDaSemana;
    private Time entrada1;
    private Time saida1;
    private Time entrada2;
    private Time saida2;

    public HorarioTrabalho() {
    }

    public HorarioTrabalho(DiaDaSemana diaDaSemana, Time entrada1, Time saida1, Time entrada2, Time saida2) {
        this.diaDaSemana = diaDaSemana;
        this.entrada1 = entrada1;
        this.saida1 = saida1;
        this.entrada2 = entrada2;
        this.saida2 = saida2;
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
