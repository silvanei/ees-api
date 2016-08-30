package org.ees.api.agenda.infra.repository;

import org.ees.api.agenda.entity.ClienteSalao;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.ClienteSalaoRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by silvanei on 29/08/16.
 */
public class ClienteSalaoRepositoryImpl implements ClienteSalaoRepository {
    @Override
    public ClienteSalao get(Integer salaoId, Integer clienteSalaoId) {
        String sql = "SELECT c.id, c.nome, c.nome, c.email FROM cliente c INNER JOIN salao s ON (s.id = c.salao_id) WHERE s.id = ? AND c.id = ? AND deletado = 0";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setInt(2, clienteSalaoId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                ClienteSalao clienteSalao = new ClienteSalao();
                clienteSalao.setId(resultSet.getInt("id"));
                clienteSalao.setNome(resultSet.getString("nome"));
                clienteSalao.setEmail(resultSet.getString("email"));

                return clienteSalao;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(ClienteSalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Erro ao buscar um funcionario pelo id");
        }
    }
}
