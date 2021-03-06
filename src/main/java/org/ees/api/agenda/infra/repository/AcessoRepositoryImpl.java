package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.AcessoRepository;

public class AcessoRepositoryImpl implements AcessoRepository {

	@Override
	public Integer insert(Acesso acesso) {

		String sql = "INSERT INTO acesso (email, senha, perfil) VALUES (?, ?, ?)";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, acesso.getEmail());
			stmt.setString(2, acesso.getSenha());
			stmt.setString(3, acesso.getPerfil());

			if (stmt.executeUpdate() > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao inserir um Acesso");
		}
	}

	@Override
	public Integer removeAcesso(Integer acessoId) {
		String sql = "DELETE FROM acesso WHERE id = ?";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, acessoId);

			if(stmt.executeUpdate() > 0) {
				return acessoId;
			}

			return null;
		} catch (SQLException ex) {
			Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao excluir um acesso de um Funcionario");
		}
	}

	@Override
	public Acesso findById(Integer id) {

		String sql = "SELECT a.id, a.email, a.senha, a.perfil, f.salao_id, c.cliente_id " +
				"FROM acesso a " +
                "LEFT JOIN funcionario f ON (f.acesso_id = a.id) " +
				"LEFT JOIN cliente_app c ON (c.acesso_id = a.id) " +
                "WHERE a.id = ?";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, id);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				Acesso acesso = new Acesso();
				acesso.setId(resultSet.getInt("id"));
				acesso.setEmail(resultSet.getString("email"));
				acesso.setSenha(resultSet.getString("senha"));
				acesso.setPerfil(resultSet.getString("perfil"));
                acesso.setSalaoId(resultSet.getInt("salao_id"));
				acesso.setClienteId(resultSet.getInt("cliente_id"));

				return acesso;
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(AcessoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar um acesso pelo id");
		}
	}

	@Override
	public Acesso findByFuncionario(Integer funcionarioId) {
		String sql = "SELECT a.id, a.email, a.senha, a.perfil, f.salao_id " +
				"FROM acesso a " +
				"INNER JOIN funcionario f ON (f.acesso_id = a.id) " +
				"WHERE F.id = ?";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, funcionarioId);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				Acesso acesso = new Acesso();
				acesso.setId(resultSet.getInt("id"));
				acesso.setEmail(resultSet.getString("email"));
				acesso.setSenha(resultSet.getString("senha"));
				acesso.setPerfil(resultSet.getString("perfil"));
				acesso.setSalaoId(resultSet.getInt("salao_id"));

				return acesso;
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(AcessoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar um acesso pelo id DO FUNCIONARIO");
		}
	}

	public Acesso findByCliente(Integer clienteId) {
		String sql = "SELECT a.id, a.email, a.senha, a.perfil, c.cliente_id " +
				"FROM acesso a " +
				"INNER JOIN cliente_app c ON (c.acesso_id = a.id) " +
				"WHERE c.cliente_id = ?";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, clienteId);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				Acesso acesso = new Acesso();
				acesso.setId(resultSet.getInt("id"));
				acesso.setEmail(resultSet.getString("email"));
				acesso.setSenha(resultSet.getString("senha"));
				acesso.setPerfil(resultSet.getString("perfil"));
				acesso.setClienteId(resultSet.getInt("cliente_id"));

				return acesso;
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(AcessoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar um acesso pelo id DO FUNCIONARIO");
		}
	}

	@Override
	public Acesso findByEmail(String email) {

		String sql = "SELECT a.id, a.email, a.senha, a.perfil, f.salao_id, c.cliente_id " +
                "FROM acesso a " +
                "LEFT JOIN funcionario f ON (f.acesso_id = a.id) " +
                "LEFT JOIN cliente_app c ON (c.acesso_id = a.id) " +
                "WHERE a.email = ? ";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, email);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				Acesso acesso = new Acesso();
				acesso.setId(resultSet.getInt("id"));
				acesso.setEmail(resultSet.getString("email"));
				acesso.setSenha(resultSet.getString("senha"));
				acesso.setPerfil(resultSet.getString("perfil"));
				acesso.setSalaoId(resultSet.getInt("salao_id"));
				acesso.setClienteId(resultSet.getInt("cliente_id"));

				return acesso;
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(AcessoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar um acesso pelo e-mail");
		}
	}

	@Override
	public List<Acesso> findByIdSalao(Integer salaoId) {
		String sql = "SELECT a.id, a.email, a.senha, a.perfil, f.salao_id, f.id as funcionarioId " +
				"FROM acesso a " +
				"LEFT JOIN funcionario f ON (f.acesso_id = a.id) " +
				"WHERE f.salao_id = ? ";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, salaoId);
			ResultSet resultSet = stmt.executeQuery();

			List<Acesso> acessos = new ArrayList<>();

			while (resultSet.next()) {
				Acesso acesso = new Acesso();
				acesso.setId(resultSet.getInt("id"));
				acesso.setEmail(resultSet.getString("email"));
				acesso.setPerfil(resultSet.getString("perfil"));
				acesso.setSalaoId(resultSet.getInt("salao_id"));
				acesso.setFuncionarioId(resultSet.getInt("funcionarioId"));
				acessos.add(acesso);

			}

			return acessos;

		} catch (SQLException ex) {
			Logger.getLogger(AcessoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar os acessos de um salão");
		}
	}
}
