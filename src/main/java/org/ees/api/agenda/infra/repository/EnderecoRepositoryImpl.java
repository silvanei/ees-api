package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.Endereco;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.EnderecoRepository;

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
            Logger.getLogger(EnderecoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir o endereco de um Salão");
        }
    }

	@Override
	public Endereco byIdSalao(Integer idSalao) {
		String sql = "SELECT e.id, e.rua, e.numero, e.estado, e.cidade, e.bairro, e.cep "
				+ "FROM endereco e "
				+ "INNER JOIN salao s ON (s.endereco_id = e.id) "
				+ "WHERE s.id = ?";

		try {	
			
			Endereco endereco = new Endereco();
			
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, idSalao);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				endereco.setId(resultSet.getInt("id"));
				endereco.setRua(resultSet.getString("rua"));
				endereco.setNumero(resultSet.getInt("estado"));
				endereco.setCidade(resultSet.getInt("cidade"));
				endereco.setBairro(resultSet.getInt("bairro"));
				endereco.setCep(resultSet.getString("cep"));
			}

			return endereco;

		} catch (SQLException ex) {
			Logger.getLogger(EnderecoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar o endereço de um salao");
		}
	}

	@Override
	public Integer atualizarEndereco(Endereco endereco) {
		String sql = "UPDATE endereco SET rua = ?, numero = ?, estado = ?, cidade = ?, bairro = ?, cep = ? WHERE id = ?";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, endereco.getRua());
			stmt.setInt(2, endereco.getNumero());
			stmt.setInt(3, endereco.getEstado());
			stmt.setInt(4, endereco.getCidade());
			stmt.setInt(5, endereco.getBairro());
			stmt.setString(6, endereco.getCep());
			stmt.setInt(7, endereco.getId());

			if(stmt.executeUpdate()>0){
				return endereco.getId();
			}

			return null;

		}catch (SQLException ex){
			Logger.getLogger(EnderecoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao atualizar o endereço de um Salão");
		}
	}
	
	
	
}
