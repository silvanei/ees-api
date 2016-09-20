package org.ees.api.agenda.infra.repository;

import org.ees.api.agenda.entity.ClienteSalao;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.ClienteSalaoRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by silvanei on 29/08/16.
 */
public class ClienteSalaoRepositoryImpl implements ClienteSalaoRepository {
    @Override
    public ClienteSalao get(Integer salaoId, Integer clienteSalaoId) {
        String sql = "SELECT c.id, c.nome, c.telefone, cs.email FROM cliente c INNER JOIN cliente_salao cs on (c.id = cs.cliente_id) WHERE cs.salao_id = ? AND c.id = ? AND c.deletado = 0";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setInt(2, clienteSalaoId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                ClienteSalao clienteSalao = new ClienteSalao();
                clienteSalao.setId(resultSet.getInt("id"));
                clienteSalao.setNome(resultSet.getString("nome"));
                clienteSalao.setTelefone(resultSet.getString("telefone"));
                clienteSalao.setEmail(resultSet.getString("email"));

                return clienteSalao;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(ClienteSalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Erro ao buscar um cliente pelo id");
        }
    }

    @Override
    public CollectionPaginated<ClienteSalao> get(Integer salaoId) {
        String sql = "SELECT c.id, c.nome, c.telefone, cs.email FROM cliente c INNER JOIN cliente_salao cs on (c.id = cs.cliente_id) WHERE cs.salao_id = ? AND c.deletado = 0";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            ResultSet resultSet = stmt.executeQuery();

            List<ClienteSalao> funcionarios = new ArrayList<>();

            while (resultSet.next()) {
                ClienteSalao clienteSalao = new ClienteSalao();
                clienteSalao.setId(resultSet.getInt("id"));
                clienteSalao.setNome(resultSet.getString("nome"));
                clienteSalao.setTelefone(resultSet.getString("telefone"));
                clienteSalao.setEmail(resultSet.getString("email"));
                funcionarios.add(clienteSalao);
            }

            int total = funcionarios.size();

            return new CollectionPaginated<ClienteSalao>(funcionarios, total, 0, total);

        } catch (SQLException ex) {
            Logger.getLogger(ClienteSalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar clientes pelo idSalao");
        }
    }

    @Override
    public CollectionPaginated<ClienteSalao> get(Integer salaoId, int limit, int offset) {
        String sql = "SELECT SQL_CALC_FOUND_ROWS c.id, c.nome, c.telefone, cs.email FROM cliente c INNER JOIN cliente_salao cs on (c.id = cs.cliente_id) WHERE cs.salao_id = ? AND c.deletado = 0 LIMIT ? OFFSET ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            ResultSet resultSet = stmt.executeQuery();
            Integer foundRows = DB.foundRows();

            List<ClienteSalao> funcionarios = new ArrayList<>();

            while (resultSet.next()) {
                ClienteSalao clienteSalao = new ClienteSalao();
                clienteSalao.setId(resultSet.getInt("id"));
                clienteSalao.setNome(resultSet.getString("nome"));
                clienteSalao.setTelefone(resultSet.getString("telefone"));
                clienteSalao.setEmail(resultSet.getString("email"));
                funcionarios.add(clienteSalao);
            }

            return new CollectionPaginated<ClienteSalao>(funcionarios, limit, offset, foundRows);

        } catch (SQLException ex) {
            Logger.getLogger(ClienteSalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar clientes paginado pelo idSalao");
        }
    }

    @Override
    public Integer create(Integer salaoId, ClienteSalao clienteSalao) {
        String insertCliente = "INSERT INTO cliente (nome, telefone) VALUES (?, ?) ";
        String insertClienteSalao = "INSERT INTO cliente_salao (salao_id, cliente_id, email) VALUES (?, ?, ?)";

        try{
            DB.beginTransaction();
            PreparedStatement stmtCliente = DB.preparedStatement(insertCliente);
            stmtCliente.setString(1, clienteSalao.getNome());
            stmtCliente.setString(2, clienteSalao.getTelefone());
            Integer insertId = null;
            if(stmtCliente.executeUpdate()>0){
                ResultSet rs = stmtCliente.getGeneratedKeys();
                if (rs.next()){
                    insertId = rs.getInt(1);

                    PreparedStatement stmtClienteSalao = DB.preparedStatement(insertClienteSalao);
                    stmtClienteSalao.setInt(1, salaoId);
                    stmtClienteSalao.setInt(2, insertId);
                    stmtClienteSalao.setString(3, clienteSalao.getEmail());
                    stmtClienteSalao.executeUpdate();
                } else {
                    DB.rollback();
                    return null;
                }
            }

            DB.commit();
            return insertId;

        }catch (SQLException ex){
            DB.rollback();
            Logger.getLogger(ClienteSalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um cliente de um Salão");
        }
    }

    @Override
    public Integer update(Integer salaoId, Integer clienteSalaoId, ClienteSalao clienteSalao) {
        String sql = "UPDATE cliente c INNER JOIN cliente_salao cs on (c.id = cs.cliente_id) SET c.nome = ?, c.telefone = ?, cs.email = ? WHERE cs.salao_id = ? AND c.id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setString(1, clienteSalao.getNome());
            stmt.setString(2, clienteSalao.getTelefone());
            stmt.setString(3, clienteSalao.getEmail());
            stmt.setInt(4,salaoId);
            stmt.setInt(5,clienteSalaoId);

            if (stmt.executeUpdate() > 0) {
                return clienteSalaoId;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(ClienteSalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao atualizar o cliente de um Salão");
        }
    }

    @Override
    public Integer delete(Integer salaoId, Integer clienteSalaoId) {
        String sql = "UPDATE cliente c INNER JOIN cliente_salao cs on (c.id = cs.cliente_id) SET c.deletado = ? WHERE cs.salao_id = ? AND c.id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setBoolean(1, true);
            stmt.setInt(2, salaoId);
            stmt.setInt(3, clienteSalaoId);

            if (stmt.executeUpdate() > 0) {
                return clienteSalaoId;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(ClienteSalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao deletar o cliente de um Salão");
        }
    }
}
