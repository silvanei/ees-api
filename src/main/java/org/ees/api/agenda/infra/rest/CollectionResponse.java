package org.ees.api.agenda.infra.rest;

import org.ees.api.agenda.entity.Servico;

import javax.xml.bind.annotation.XmlList;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by silvanei on 30/07/16.
 */

@XmlRootElement
public class CollectionResponse {

    private ResultSet metadata;

    private List<Servico> result;

    public ResultSet getMetadata() {
        return metadata;
    }

    public void setMetadata(ResultSet metadata) {
        this.metadata = metadata;
    }

    public List<Servico> getResult() {
        return result;
    }

    public void setResult(List<Servico> result) {
        this.result = result;
    }
}
