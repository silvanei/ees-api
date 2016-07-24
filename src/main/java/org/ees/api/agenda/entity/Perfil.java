package org.ees.api.agenda.entity;

/**
 * Created by silvanei on 24/07/16.
 */
public class Perfil {

    private Integer id;
    private String descricao;

    public Perfil() {
    }

    public Perfil(String descricao) {
        this.descricao = descricao;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {

        if ((this.descricao == null && object != null) || (this.descricao != null && !this.descricao.equals(object))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.ees.api.agenda.entity.Perfil[ id=" + id + " ]";
    }
}
