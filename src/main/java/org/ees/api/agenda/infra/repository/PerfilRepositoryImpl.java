package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.PerfilRepository;

public class PerfilRepositoryImpl implements PerfilRepository {

	@Override
	public Perfil findById(Integer idPerfil) {
		String sql = "SELECT id, descricao FROM perfil WHERE id = ?";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, idPerfil);
			ResultSet resultSet = stmt.executeQuery();

			if(resultSet.next()){
				Perfil perfil = new Perfil();
				perfil.setId(resultSet.getInt("id"));
				perfil.setDescricao(resultSet.getString("descricao"));

				return perfil;
			}

			return null;

		}catch (SQLException ex){
			Logger.getLogger(PerfilRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar um perfil");
		}

	}

	@Override
	public List<Perfil> findAll() {
		String sql = "SELECT id, descricao FROM perfil";

		List<Perfil> list = new ArrayList<Perfil>();

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			ResultSet resultSet = stmt.executeQuery();

			while(resultSet.next()){
				Perfil perfil = new Perfil();
				perfil.setId(resultSet.getInt("id"));
				perfil.setDescricao(resultSet.getString("descricao"));

				list.add(perfil);
			}

			return list;

		}catch (SQLException ex){
			Logger.getLogger(PerfilRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar lista de perfil");
		}
	}
}
