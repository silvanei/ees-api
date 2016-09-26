package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.Cidade;
import org.ees.api.agenda.entity.Endereco;
import org.ees.api.agenda.entity.Estado;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.EnderecoRepository;

/**
 * Created by silvanei on 28/07/16.
 */
public class EnderecoRepositoryImpl implements EnderecoRepository{

    @Override
    public Integer inserirEndereco(Endereco endereco) {
        String sql = "INSERT INTO endereco(rua, numero, cidade_id) VALUES (?, ?, ?)";

        try{
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setString(1, endereco.getRua());
            if (null == endereco.getNumero()) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(2, endereco.getNumero());
            }
            if(null == endereco.getCidade()) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, endereco.getCidade().getId());
            }

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
		String sql = "SELECT e.id, e.rua, e.numero, es.id as estado_id, es.uf, es.nome as estado, ci.id as cidade_id, ci.nome as cidade " +
				"FROM endereco e LEFT JOIN cidade ci ON (ci.id = e.cidade_id) " +
                "LEFT JOIN estado es ON (es.id = ci.estado_id) " +
                "INNER JOIN salao s ON (s.endereco_id = e.id) " +
                "WHERE s.id = ?";

		try {	
			
			Endereco endereco = new Endereco();
			
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, idSalao);
			ResultSet resultSet = stmt.executeQuery();

			if (resultSet.next()) {
				endereco.setId(resultSet.getInt("id"));
				endereco.setRua(resultSet.getString("rua"));
				endereco.setNumero(resultSet.getInt("numero"));
				endereco.setCidade(
                        new Cidade(
                            resultSet.getInt("cidade_id"),
                            resultSet.getString("cidade")
                        )
                );
                endereco.setEstado(
                        new Estado(
                                resultSet.getInt("estado_id"),
                                resultSet.getString("estado"),
                                resultSet.getString("uf")
                        )
                );
			}

			return endereco;

		} catch (SQLException ex) {
			Logger.getLogger(EnderecoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar o endereço de um salao");
		}
	}

	@Override
	public Integer atualizarEndereco(Endereco endereco) {
		String sql = "UPDATE endereco SET rua = ?, numero = ?, cidade_id = ? WHERE id = ?";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, endereco.getRua());
            if (null == endereco.getNumero()) {
                stmt.setNull(2, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(2, endereco.getNumero());
            }
			if(null == endereco.getCidade().getId()) {
                stmt.setNull(3, java.sql.Types.INTEGER);
            } else {
                stmt.setInt(3, endereco.getCidade().getId());
            }


			stmt.setInt(4, endereco.getId());

			if(stmt.executeUpdate()>0){
				return endereco.getId();
			}

			return null;

		}catch (SQLException ex){
			Logger.getLogger(EnderecoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao atualizar o endereço de um Salão");
		}
	}

    @Override
    public List<Estado> getEstados() {
        String sql = "select id, nome, uf from estado";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            List<Estado> estados = new ArrayList<>();

            while (resultSet.next()) {
                estados.add(new Estado(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("uf")
                ));
            }

            return estados;
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar os estados");
        }

    }

    @Override
    public Estado getEstado(Integer estadoId) {
        String sql = "select id, nome, uf from estado where id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, estadoId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                return new Estado(
                        resultSet.getInt("id"),
                        resultSet.getString("nome"),
                        resultSet.getString("uf")
                );
            }

            return null;
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar um estado pelo ID");
        }
    }

    @Override
    public List<Cidade> getCidades(Integer estadoId) {
        String sql = "select id, nome from cidade where estado_id = ? ";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, estadoId);
            ResultSet resultSet = stmt.executeQuery();

            List<Cidade> cidades = new ArrayList<>();

            while (resultSet.next()) {
                cidades.add(new Cidade(
                        resultSet.getInt("id"),
                        resultSet.getString("nome")
                ));
            }

            return cidades;
        } catch (SQLException ex) {
            Logger.getLogger(EnderecoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar as cidades de um estado");
        }
    }
}
