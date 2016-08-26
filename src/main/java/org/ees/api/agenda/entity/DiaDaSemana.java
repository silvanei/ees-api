package org.ees.api.agenda.entity;


/**
 * Created by silvanei on 26/08/2016.
 */
public class DiaDaSemana {

    public static final int DOMINGO = 0;
    public static final int SEGUNDA = 1;
    public static final int TERCA = 2;
    public static final int QUARTA = 3;
    public static final int QUINTA = 4;
    public static final int SEXTA = 5;
    public static final int SABADO = 6;

    private int dia;

    public DiaDaSemana(int dia) throws Exception {

        if (dia < DOMINGO || dia > SABADO) {
            throw new Exception("O dia da deve estar entre 0 a 6");
        }

        this.dia = dia;
    }

    public int dia() {
        return dia;
    }

    @Override
    public String toString() {

        switch (dia) {
            case DOMINGO : return "Domingo";
            case SEGUNDA : return "Segunda feira";
            case TERCA : return "Terça feira";
            case QUARTA : return "Quarta feira";
            case QUINTA : return "Quinta feira";
            case SEXTA : return "Sexta feira";
            case SABADO : return "Sábado";
            default: return "";
        }
    }
}
