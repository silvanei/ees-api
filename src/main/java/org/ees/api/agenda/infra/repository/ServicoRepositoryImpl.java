package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.Servico;
import org.ees.api.agenda.infra.db.CollectionPaginated;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.ServicoRepository;

public class ServicoRepositoryImpl implements ServicoRepository {

	@Override
	public Integer insert(Integer idSalao, Servico servico) {
		String sql = "INSERT INTO servico (descricao, duracao, valor_minimo, valor_maximo, salao_id) VALUES (?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setString(1, servico.getDescricao());
			stmt.setTime(2, servico.getDuracao());
			stmt.setBigDecimal(3, servico.getValorMinimo());
			stmt.setBigDecimal(4, servico.getValorMaximo());
			stmt.setInt(5, idSalao);

			if (stmt.executeUpdate() > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(HorarioDeFuncionamentoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao inserirum servico de um Salão");
		}
	}

	@Override
	public Integer update(Servico servico) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer delete(Servico servico) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Servico findById(Integer idSalao, Integer idServico) {
		String sql = "SELECT id, descricao, duracao, valor_minimo, valor_maximo FROM servico WHERE id = ? AND salao_id = ?";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, idServico);
			stmt.setInt(2, idSalao);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				Servico servico = new Servico();
				servico.setId(rs.getInt("id"));
				servico.setDescricao(rs.getString("descricao"));
				servico.setDuracao(rs.getTime("duracao"));
				servico.setValorMinimo(rs.getBigDecimal("valor_minimo"));
				servico.setValorMaximo(rs.getBigDecimal("valor_maximo"));
				return servico;
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(ServicoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao buscar um servico pelo id");
		}
	}

	@Override
	public CollectionPaginated<Servico> findByIdSalao(Integer idSalao, int limit, int offset) {
		String sql = "SELECT SQL_CALC_FOUND_ROWS id, descricao, duracao, valor_minimo, valor_maximo " +
				"FROM servico " +
				"WHERE salao_id = ? " +
				"ORDER BY id " +
				"LIMIT ? OFFSET ?";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, idSalao);
			stmt.setInt(2, limit);
			stmt.setInt(3, offset);
			ResultSet rs = stmt.executeQuery();
			
			List<Servico> servicos = new ArrayList<Servico>();
			
			while (rs.next()) {
				Servico servico = new Servico();
				servico.setId(rs.getInt("id"));
				servico.setDescricao(rs.getString("descricao"));
				servico.setDuracao(rs.getTime("duracao"));
				servico.setValorMinimo(rs.getBigDecimal("valor_minimo"));
				servico.setValorMaximo(rs.getBigDecimal("valor_maximo"));
				servicos.add(servico);
			}

			return new CollectionPaginated<Servico>(servicos, limit, offset, DB.foundRows());

		} catch (SQLException ex) {
			Logger.getLogger(ServicoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao buscar um servico pelo idSalao");
		}
	}
	
	
	
}
