package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.SalaoRepository;

public class SalaoRepositoryImpl implements SalaoRepository {

	@Override
	public Integer insert(Salao salao) {
		
		String sql = "INSERT INTO salao (nome, visivel_no_app, telefone) VALUES (?, ?, ?)";

        try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, salao.getNome());
        	stmt.setBoolean(2, salao.isVisivelNoApp());
        	stmt.setString(3, salao.getTelefone());
            
            if(stmt.executeUpdate()>0){
            	ResultSet rs = stmt.getGeneratedKeys();
            	if (rs.next()){
            	    return rs.getInt(1);
            	}
            }
            
            return null;
            
        }catch (SQLException ex){
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um Salão");
        }
	}

	@Override
	public Integer update(Salao salao) {
		String sql = "UPDATE salao SET nome = ?, visivel_no_app = ?, telefone = ?, celular = ? WHERE id = ?";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, salao.getNome());
			stmt.setBoolean(2, salao.isVisivelNoApp());
			stmt.setString(3, salao.getTelefone());
			stmt.setString(4, salao.getCelular());
			stmt.setInt(5, salao.getId());

			if(stmt.executeUpdate()>0){
				return salao.getId();
			}

			return null;

		}catch (SQLException ex){
			Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao atualizar um Salão");
		}
	}

	@Override
	public Salao findById(Integer idSalao) {
		String sql = "SELECT id, nome, visivel_no_app, telefone, celular FROM salao WHERE id = ?";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, idSalao);
			ResultSet resultSet = stmt.executeQuery();

			if(resultSet.next()){
				Salao salao = new Salao();
				salao.setId(resultSet.getInt("id"));
				salao.setNome(resultSet.getString("nome"));
				salao.setVisivelNoApp(resultSet.getBoolean("visivel_no_app"));
				salao.setTelefone(resultSet.getString("telefone"));
				salao.setCelular(resultSet.getString("celular"));

				return salao;
			}

			return null;

		}catch (SQLException ex){
			Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao inserir um Salão");
		}

	}
}
