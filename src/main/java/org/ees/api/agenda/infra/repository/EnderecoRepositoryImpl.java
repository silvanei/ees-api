package org.ees.api.agenda.infra.repository;

import org.ees.api.agenda.entity.Endereco;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.EnderecoRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by silvanei on 28/07/16.
 */
public class EnderecoRepositoryImpl implements EnderecoRepository{

    @Override
    public Integer inserirEndereco(Endereco endereco) {
        String sql = "INSERT INTO endereco(rua, numero, estado, cidade, bairro, cep) VALUES (?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setString(1, endereco.getRua());
            stmt.setInt(2, endereco.getNumero());
            stmt.setInt(3, endereco.getEstado());
            stmt.setInt(4, endereco.getCidade());
            stmt.setInt(5, endereco.getBairro());
            stmt.setString(6, endereco.getCep());

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
}
