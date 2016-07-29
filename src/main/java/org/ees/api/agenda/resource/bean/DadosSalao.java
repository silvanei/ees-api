package org.ees.api.agenda.resource.bean;

import org.ees.api.agenda.entity.Endereco;
import org.ees.api.agenda.entity.HorarioDeFuncionamento;

/**
 * Created by silvanei on 28/07/16.
 */
public class DadosSalao {

    private String nome;
    private String telefone;
    private String celular;
    private boolean visivelNoApp;
    private Endereco endereco;
    private HorarioDeFuncionamento horarioDeFuncionamento;

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

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public boolean isVisivelNoApp() {
        return visivelNoApp;
    }

    public void setVisivelNoApp(boolean visivelNoApp) {
        this.visivelNoApp = visivelNoApp;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public HorarioDeFuncionamento getHorarioDeFuncionamento() {
        return horarioDeFuncionamento;
    }

    public void setHorarioDeFuncionamento(HorarioDeFuncionamento horarioDeFuncionamento) {
        this.horarioDeFuncionamento = horarioDeFuncionamento;
    }
}
