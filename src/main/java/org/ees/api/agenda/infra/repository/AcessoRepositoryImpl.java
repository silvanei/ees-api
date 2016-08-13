package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
	public Acesso findById(Integer id) {

		String sql = "SELECT a.id, a.email, a.senha, a.perfil, f.salao_id " +
				"FROM acesso a " +
                "LEFT JOIN funcionario f ON (f.acesso_id = a.id) " +
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

				return acesso;
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(AcessoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar um acesso pelo id");
		}
	}

	@Override
	public Acesso findByEmail(String email) {

		String sql = "SELECT a.id, a.email, a.senha, a.perfil, f.salao_id " +
                "FROM acesso a " +
                "LEFT JOIN funcionario f ON (f.acesso_id = a.id) " +
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

				return acesso;
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(AcessoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar um acesso pelo e-mail");
		}
	}

}
