package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.Acesso;
import org.ees.api.agenda.entity.Perfil;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.AcessoRepository;

public class AcessoRepositoryImpl implements AcessoRepository{

	@Override
	public Integer insert(Acesso acesso) {

		String sql = "INSERT INTO acesso (email, senha, perfil_id) VALUES (?, ?, ?)";

        try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, acesso.getEmail());
        	stmt.setString(2, acesso.getSenha());
        	stmt.setInt(3, acesso.getPerfil().getId());

            if(stmt.executeUpdate()>0){
            	ResultSet rs = stmt.getGeneratedKeys();
            	if (rs.next()){
					return rs.getInt(1);
            	}
            }
            
            return null;
        
        }catch (SQLException ex){
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um Acesso");
        }
	}

	@Override
	public Acesso findById(Integer id) {

		String sql = "SELECT a.id, a.email, a.senha, p.id as perfil_id, p.descricao " +
				"FROM acesso a " +
				"INNER JOIN perfil p ON (p.id = a.perfil_id)" +
				"WHERE a.id = ?";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, id);
			ResultSet resultSet = stmt.executeQuery();

			if(resultSet.next()){
				Acesso acesso = new Acesso();
				acesso.setId(resultSet.getInt("id"));
				acesso.setEmail(resultSet.getString("email"));
				acesso.setSenha(resultSet.getString("senha"));

				Perfil perfil = new Perfil();
				perfil.setId(resultSet.getInt("perfil_id"));
				perfil.setDescricao(resultSet.getString("descricao"));
				acesso.setPerfil(perfil);

				return acesso;
			}

			return null;

		}catch (SQLException ex){
			Logger.getLogger(AcessoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar um acesso");
		}
	}

	@Override
	public Acesso findByEmail(String email) {

		String sql = "SELECT a.id, a.email, a.senha, p.id as perfil_id, p.descricao " +
				"FROM acesso a " +
				"INNER JOIN perfil p ON (p.id = a.perfil_id)" +
				"WHERE a.email = ?";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, email);
			ResultSet resultSet = stmt.executeQuery();

			if(resultSet.next()){
				Acesso acesso = new Acesso();
				acesso.setId(resultSet.getInt("id"));
				acesso.setEmail(resultSet.getString("email"));
				acesso.setSenha(resultSet.getString("senha"));

				Perfil perfil = new Perfil();
				perfil.setId(resultSet.getInt("perfil_id"));
				perfil.setDescricao(resultSet.getString("descricao"));
				acesso.setPerfil(perfil);

				return acesso;
			}

			return null;

		}catch (SQLException ex){
			Logger.getLogger(AcessoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar um acesso");
		}
	}

}
