package org.ees.api.agenda.resource.bean;

import javax.ws.rs.PathParam;

/**
 * Created by silvanei on 30/07/16.
 */
public class ServicoFilterBean extends PaginatedFilterBean{
    private @PathParam("salaoId") Integer idSalao;


    public Integer getIdSalao() {
        return idSalao;
    }

    public void setIdSalao(Integer idSalao) {
        this.idSalao = idSalao;
    }
}
