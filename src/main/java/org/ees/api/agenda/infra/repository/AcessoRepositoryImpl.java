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

public class AcessoRepositoryImpl implements AcessoRepository{

	@Override
	public Acesso insert(Acesso acesso, Integer idFuncionario) {

		String sql = "INSERT INTO acesso (funcionario_id, email, senha) VALUES (?, ?, ?)";
        PreparedStatement stmt = DB.preparedStatement(sql);
        try{
        	stmt.setInt(1, idFuncionario);
        	stmt.setString(2, acesso.getEmail());
        	stmt.setString(3, acesso.getSenha());
            
            if(stmt.executeUpdate()>0){
            	ResultSet rs = stmt.getGeneratedKeys();
            	if (rs.next()){
            		acesso.setId(rs.getInt(1));
            	}
                return acesso;
            }
            
            return null;
        
        }catch (SQLException ex){
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um Acersso");
        }
	}

}
