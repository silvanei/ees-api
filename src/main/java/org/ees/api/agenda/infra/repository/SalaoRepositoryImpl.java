package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.db.Conexao;
import org.ees.api.agenda.repository.SalaoRepository;

public class SalaoRepositoryImpl implements SalaoRepository {

	@Override
	public Salao insert(Salao salao) {
		
		String sql = "INSERT INTO salao (nome, visivel_no_app, telefone) VALUES (?, ?, ?)";
        PreparedStatement stmt = Conexao.preparedStatement(sql);
        try{
        	stmt.setString(1, salao.getNome());
        	stmt.setBoolean(2, salao.isVisivelNoApp());
        	stmt.setString(3, salao.getTelefone());
            
            if(stmt.executeUpdate()>0){
            	ResultSet rs = stmt.getGeneratedKeys();
            	if (rs.next()){
            	    salao.setId(rs.getInt(1));
            	}
                return salao;
            }
        
        }catch (SQLException ex){
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
		return null;
	}

}
