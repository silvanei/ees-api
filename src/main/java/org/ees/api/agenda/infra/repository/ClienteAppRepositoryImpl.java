package org.ees.api.agenda.infra.repository;

import org.ees.api.agenda.entity.ClienteApp;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.ClienteAppRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by silvanei on 20/09/2016.
 */
public class ClienteAppRepositoryImpl implements ClienteAppRepository {

    @Override
    public Integer create(ClienteApp cliente, Integer acessoId) {
        String insertCliente = "INSERT INTO cliente (nome, telefone) VALUES (?, ?) ";
        String insertClienteApp = "INSERT INTO cliente_app (acesso_id, cliente_id) VALUES (?, ?)";

        try{
            PreparedStatement stmtCliente = DB.preparedStatement(insertCliente);
            stmtCliente.setString(1, cliente.getNome());
            stmtCliente.setString(2, cliente.getTelefone());
            Integer insertId = null;
            if(stmtCliente.executeUpdate()>0){
                ResultSet rs = stmtCliente.getGeneratedKeys();
                if (rs.next()){
                    insertId = rs.getInt(1);

                    PreparedStatement stmtClienteApp = DB.preparedStatement(insertClienteApp);
                    stmtClienteApp.setInt(1, acessoId);
                    stmtClienteApp.setInt(2, insertId);
                    stmtClienteApp.executeUpdate();
                } else {
                    return null;
                }
            }
            return insertId;

        }catch (SQLException ex){
            Logger.getLogger(ClienteAppRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um cliente do aplicativo");
        }
    }

    @Override
    public ClienteApp findById(Integer clienteId) {
        String sql = "SELECT c.id, c.nome, c.telefone FROM cliente c INNER JOIN cliente_app ca on (c.id = ca.cliente_id) WHERE c.id = ? AND c.deletado = 0";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, clienteId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                ClienteApp clienteApp = new ClienteApp();
                clienteApp.setId(resultSet.getInt("id"));
                clienteApp.setNome(resultSet.getString("nome"));
                clienteApp.setTelefone(resultSet.getString("telefone"));

                return clienteApp;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(ClienteAppRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Erro ao buscar um cliente do aplicativo pelo id");
        }
    }
}
