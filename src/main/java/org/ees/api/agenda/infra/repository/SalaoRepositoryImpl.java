package org.ees.api.agenda.infra.repository;

import org.ees.api.agenda.entity.Salao;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.SalaoRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
		String sql = "UPDATE salao SET nome = ?, visivel_no_app = ?, telefone = ?, celular = ?, endereco_id = ? WHERE id = ?";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, salao.getNome());
			stmt.setBoolean(2, salao.isVisivelNoApp());
			stmt.setString(3, salao.getTelefone());
			stmt.setString(4, salao.getCelular());
			if(null == salao.getEndereco().getId()) {
				stmt.setNull(5, Types.INTEGER);
			} else {
				stmt.setInt(5, salao.getEndereco().getId());
			}
			stmt.setInt(6, salao.getId());

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
			Logger.getLogger(SalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao inserir um Salão");
		}
	}

	@Override
	public Salao findById(Integer idSalao, boolean visivelNoApp) {
		String sql = "SELECT s.id, s.nome, s.telefone, s.celular, s.visivel_no_app, IF(f.salao_id > 0 , 1, 0) as favorito FROM salao s LEFT JOIN favorito f ON (f.salao_id = s.id) WHERE s.id = ? AND s.visivel_no_app = ?";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, idSalao);
			stmt.setBoolean(2, visivelNoApp);
			ResultSet resultSet = stmt.executeQuery();

			if(resultSet.next()){
				Salao salao = new Salao();
				salao.setId(resultSet.getInt("id"));
				salao.setNome(resultSet.getString("nome"));
				salao.setTelefone(resultSet.getString("telefone"));
				salao.setCelular(resultSet.getString("celular"));
				salao.setVisivelNoApp(resultSet.getBoolean("visivel_no_app"));
				salao.setFavorito(resultSet.getBoolean("favorito"));

				return salao;
			}

			return null;

		}catch (SQLException ex){
			Logger.getLogger(SalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao buscar um Salão");
		}
	}

	@Override
	public List<Salao> findByClienteId(Integer clienteId) {
		String sql = "SELECT s.id, s.nome, s.telefone, s.celular, s.visivel_no_app, IF(f.salao_id > 0 , 1, 0) as favorito FROM favorito f INNER JOIN salao s ON (f.salao_id = s.id) WHERE f.cliente_id = ? AND s.visivel_no_app = ? ";

		try{
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, clienteId);
			stmt.setBoolean(2, true);
			ResultSet resultSet = stmt.executeQuery();

			List<Salao> saloes = new ArrayList<>();

			while(resultSet.next()){
				Salao salao = new Salao();
				salao.setId(resultSet.getInt("id"));
				salao.setNome(resultSet.getString("nome"));
				salao.setTelefone(resultSet.getString("telefone"));
				salao.setCelular(resultSet.getString("celular"));
                salao.setVisivelNoApp(resultSet.getBoolean("visivel_no_app"));
                salao.setFavorito(resultSet.getBoolean("favorito"));

				saloes.add(salao);
			}

			return saloes;

		}catch (SQLException ex){
			Logger.getLogger(SalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error buscar os saloões favoritos de um cliente");
		}
	}

	@Override
	public List<Salao> findAll(String nomeSalao) {
        String sql = "SELECT s.id, s.nome, s.telefone, s.celular, s.visivel_no_app, IF(f.salao_id > 0 , 1, 0) as favorito FROM salao s LEFT JOIN favorito f ON (f.salao_id = s.id) WHERE s.visivel_no_app = ? AND s.nome like ? ";

		if(null == nomeSalao) {
			nomeSalao = "";
		}

        try{
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setBoolean(1, true);
            stmt.setString(2, "%"+ nomeSalao +"%");
            ResultSet resultSet = stmt.executeQuery();

            List<Salao> saloes = new ArrayList<>();

            while(resultSet.next()){
                Salao salao = new Salao();
                salao.setId(resultSet.getInt("id"));
                salao.setNome(resultSet.getString("nome"));
                salao.setTelefone(resultSet.getString("telefone"));
                salao.setCelular(resultSet.getString("celular"));
                salao.setVisivelNoApp(resultSet.getBoolean("visivel_no_app"));
                salao.setFavorito(resultSet.getBoolean("favorito"));
                saloes.add(salao);
            }

            return saloes;

        }catch (SQLException ex){
            Logger.getLogger(SalaoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error buscar os saloões favoritos de um cliente");
        }
	}
}
