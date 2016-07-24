package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.FuncionarioRepository;

public class FuncionarioRepositoryImpl implements FuncionarioRepository {

	@Override
	public Integer insert(Funcionario funcionario, Integer idSalao, Integer idAcesso) {
		
		String sql = "INSERT INTO funcionario (nome, apelido, salao_id, acesso_id) VALUES (?, ?, ?, ?)";

        try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, funcionario.getNome());
        	stmt.setString(2, funcionario.getApelido());
        	stmt.setInt(3, idSalao);
        	stmt.setInt(4, idAcesso);

            if(stmt.executeUpdate()>0){
            	ResultSet rs = stmt.getGeneratedKeys();
            	if (rs.next()){
            		return rs.getInt(1);
            	}
            }
            
            return null;
        }catch (SQLException ex){
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um Funcionario");
        }
	}

	@Override
	public Funcionario findById(Integer idFuncionario) {
		String sql = "SELECT id, nome, apelido, salao_id, acesso_id FROM funcionario WHERE id = ?";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, idFuncionario);
			ResultSet resultSet = stmt.executeQuery();

			if(resultSet.next()){
				Funcionario funcionario = new Funcionario();
				funcionario.setId(resultSet.getInt("id"));
				funcionario.setNome(resultSet.getString("nome"));
				funcionario.setApelido(resultSet.getString("apelido"));

				return funcionario;
			}

			return null;

		}catch (SQLException ex){
			Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar um funcionario");
		}

	}
}
