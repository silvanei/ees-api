package org.ees.api.agenda.infra.repository;

import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.FuncionarioRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FuncionarioRepositoryImpl implements FuncionarioRepository {

    @Override
    public Integer insert(Integer idSalao, Funcionario funcionario) {

        String sql = "INSERT INTO funcionario (nome, apelido, telefone, celular, salao_id) VALUES (?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getApelido());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getCelular());
            stmt.setInt(5, idSalao);

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return null;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um Funcionario");
        }
    }

    @Override
    public Integer insert(Integer idSalao, Funcionario funcionario, Integer acessoId) {
        String sql = "INSERT INTO funcionario (nome, apelido, telefone, celular, salao_id, acesso_id) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getApelido());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getCelular());
            stmt.setInt(5, idSalao);
            stmt.setInt(6, acessoId);

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return null;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um Funcionario com acesso");
        }
    }

    @Override
    public Funcionario findById(Integer idSalao, Integer idFuncionario) {
        String sql = "SELECT id, nome, apelido, telefone, celular FROM funcionario WHERE salao_id = ? AND id = ? AND deletado = 0";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, idSalao);
            stmt.setInt(2, idFuncionario);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(resultSet.getInt("id"));
                funcionario.setNome(resultSet.getString("nome"));
                funcionario.setApelido(resultSet.getString("apelido"));
                funcionario.setTelefone(resultSet.getString("telefone"));
                funcionario.setCelular(resultSet.getString("celular"));

                return funcionario;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Erro ao buscar um funcionario pelo id");
        }
    }

    @Override
    public CollectionPaginated<Funcionario> findByIdSalao(Integer salaoId, int limit, int offset) {
        String sql = "SELECT SQL_CALC_FOUND_ROWS id, nome, apelido, telefone, celular " +
                "FROM funcionario " +
                "WHERE salao_id = ? AND deletado = 0 " +
                "ORDER BY id " +
                "LIMIT ? OFFSET ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            ResultSet rs = stmt.executeQuery();

            List<Funcionario> funcionarios = new ArrayList<>();

            while (rs.next()) {
                Funcionario funcionario = new Funcionario();
                funcionario.setId(rs.getInt("id"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setApelido(rs.getString("apelido"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setCelular(rs.getString("celular"));
                funcionarios.add(funcionario);
            }

            return new CollectionPaginated<Funcionario>(funcionarios, limit, offset, DB.foundRows());

        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar funcionarios pelo idSalao");
        }
    }

    @Override
    public Integer update(Integer salaoId, Integer funcionarioId, Funcionario funcionario) {
        String sql = "UPDATE funcionario SET  nome = ?, apelido = ?, telefone = ?, celular = ? WHERE salao_id = ? AND id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getApelido());
            stmt.setString(3, funcionario.getTelefone());
            stmt.setString(4, funcionario.getCelular());
            stmt.setInt(5, salaoId);
            stmt.setInt(6, funcionarioId);

            if (stmt.executeUpdate() > 0) {
                return funcionarioId;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao atualizar o funcionario de um Salão");
        }
    }

    @Override
    public Integer delete(Integer salaoId, Integer funcionarioId) {
        String sql = "UPDATE funcionario SET  deletado = ? WHERE salao_id = ? AND id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setBoolean(1, true);
            stmt.setInt(2, salaoId);
            stmt.setInt(3, funcionarioId);

            if (stmt.executeUpdate() > 0) {
                return funcionarioId;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao deletar o funcionario de um Salão");
        }
    }

    @Override
    public Integer addServico(Integer salaoId, Integer funcionarioId, Integer servicoId) {
        String sql = "INSERT INTO funcionario_presta_servico (funcionario_id, funcionario_salao_id, servico_id) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, funcionarioId);
            stmt.setInt(2, salaoId);
            stmt.setInt(3, servicoId);

            if (stmt.executeUpdate() > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }

            return null;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um servico para o Funcionario");
        }

    }

    @Override
    public Integer removeServico(Integer salaoId, Integer funcionarioId, Integer servicoId) {
        String sql = "DELETE FROM funcionario_presta_servico WHERE funcionario_id = ? AND funcionario_salao_id = ? AND servico_id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, funcionarioId);
            stmt.setInt(2, salaoId);
            stmt.setInt(3, servicoId);

            if(stmt.executeUpdate() > 0) {
                return servicoId;
            }

            return null;
        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao excluir um servico de um Funcionario");
        }
    }

    @Override
    public Integer addAcesso(Integer salaoId, Integer funcionarioId, Integer acessoId) {
        String sql = "UPDATE funcionario SET  acesso_id = ? WHERE salao_id = ? AND id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, acessoId);
            stmt.setInt(2, salaoId);
            stmt.setInt(3, funcionarioId);

            if (stmt.executeUpdate() > 0) {
                return funcionarioId;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao adicionar acesso para o funcionario de um Salão");
        }
    }

    @Override
    public Boolean hasAcesso(Integer salaoId, Integer funcionarioId) {
        String sql = "SELECT count(acesso_id) as total FROM funcionario WHERE salao_id = ? AND id = ? AND deletado = 0";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setInt(2, funcionarioId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                int total = resultSet.getInt("total");
                return (total > 0);
            }

            return false;

        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Erro ao buscar um funcionario pelo id");
        }
    }
}
