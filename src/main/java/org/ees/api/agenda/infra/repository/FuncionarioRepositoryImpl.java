package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.Funcionario;
import org.ees.api.agenda.infra.Conexao;
import org.ees.api.agenda.repository.FuncionarioRepository;

public class FuncionarioRepositoryImpl implements FuncionarioRepository {

	@Override
	public Funcionario insert(Funcionario funcionario, Integer idSalao) {
		
		String sql = "INSERT INTO funcionario (nome, apelido, salao_id) VALUES (?, ?, ?)";
        PreparedStatement stmt = Conexao.getPreparedStatement(sql);
        try{
        	stmt.setString(1, funcionario.getNome());
        	stmt.setString(2, funcionario.getApelido());
        	stmt.setInt(3, idSalao);
            
            if(stmt.executeUpdate()>0){
            	ResultSet rs = stmt.getGeneratedKeys();
            	if (rs.next()){
            		funcionario.setId(rs.getInt(1));
            	}
                return funcionario;
            }
        
        }catch (SQLException ex){
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
		return null;
	}

}