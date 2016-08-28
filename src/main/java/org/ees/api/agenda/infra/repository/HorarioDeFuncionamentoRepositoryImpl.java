package org.ees.api.agenda.infra.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.ees.api.agenda.entity.HorarioDeFuncionamento;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.HorarioDeFuncionamentoRepository;

/**
 * Created by silvanei on 28/07/16.
 */
public class HorarioDeFuncionamentoRepositoryImpl implements HorarioDeFuncionamentoRepository {

	@Override
	public Integer inserirHorarioDeFuncionamento(HorarioDeFuncionamento horarioDeFuncionamento) {
		String sql = "INSERT INTO horario_funcionamento (hora_inicio, hora_fim, segunda, terca, quarta, quinta, sexta, sabado, domingo) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setTime(1, horarioDeFuncionamento.getHorarioInicio());
			stmt.setTime(2, horarioDeFuncionamento.getHorarioFinal());
			stmt.setBoolean(3, horarioDeFuncionamento.isSegunda());
			stmt.setBoolean(4, horarioDeFuncionamento.isTerca());
			stmt.setBoolean(5, horarioDeFuncionamento.isQuarta());
			stmt.setBoolean(6, horarioDeFuncionamento.isQuinta());
			stmt.setBoolean(7, horarioDeFuncionamento.isSexta());
			stmt.setBoolean(8, horarioDeFuncionamento.isSabado());
			stmt.setBoolean(9, horarioDeFuncionamento.isDomingo());

			if (stmt.executeUpdate() > 0) {
				ResultSet rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					return rs.getInt(1);
				}
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(HorarioDeFuncionamentoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error o horário de funcionamento de um Salão");
		}
	}

	@Override
	public HorarioDeFuncionamento byIdSalao(Integer idSalao) {
		String sql = "SELECT id, salao_id, hora_inicio, hora_fim, segunda, terca, quarta, quinta, sexta, sabado, domingo FROM horario_funcionamento WHERE salao_id = ?";

		try {

			HorarioDeFuncionamento horarioDeFuncionamento = new HorarioDeFuncionamento();

			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setInt(1, idSalao);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				horarioDeFuncionamento.setId(rs.getInt("id"));
				horarioDeFuncionamento.setHorarioInicio(rs.getTime("hora_inicio"));
				horarioDeFuncionamento.setHorarioFinal(rs.getTime("hora_fim"));
				horarioDeFuncionamento.setSegunda(rs.getBoolean("segunda"));
				horarioDeFuncionamento.setTerca(rs.getBoolean("terca"));
				horarioDeFuncionamento.setQuarta(rs.getBoolean("quarta"));
				horarioDeFuncionamento.setQuinta(rs.getBoolean("quinta"));
				horarioDeFuncionamento.setSexta(rs.getBoolean("sexta"));
				horarioDeFuncionamento.setSabado(rs.getBoolean("sabado"));
				horarioDeFuncionamento.setDomingo(rs.getBoolean("domingo"));
			}

			return horarioDeFuncionamento;

		} catch (SQLException ex) {
			Logger.getLogger(HorarioDeFuncionamentoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Erro ao buscar o horario de funcionamento de um salao");
		}
	}

	@Override
	public Integer atualizarHorarioDeFuncionamento(HorarioDeFuncionamento horarioDeFuncionamento) {
		String sql = "UPDATE  horario_funcionamento "
				+ "SET hora_inicio  = ?, hora_fim  = ?, segunda  = ?, terca  = ?, quarta  = ?, quinta  = ?, sexta  = ?, sabado  = ?, domingo  = ? "
				+ "WHERE  id  = ?";

		try {
			PreparedStatement stmt = DB.preparedStatement(sql);
			stmt.setTime(1, horarioDeFuncionamento.getHorarioInicio());
			stmt.setTime(2, horarioDeFuncionamento.getHorarioFinal());
			stmt.setBoolean(3, horarioDeFuncionamento.isSegunda());
			stmt.setBoolean(4, horarioDeFuncionamento.isTerca());
			stmt.setBoolean(5, horarioDeFuncionamento.isQuarta());
			stmt.setBoolean(6, horarioDeFuncionamento.isQuinta());
			stmt.setBoolean(7, horarioDeFuncionamento.isSexta());
			stmt.setBoolean(8, horarioDeFuncionamento.isSabado());
			stmt.setBoolean(9, horarioDeFuncionamento.isDomingo());
			stmt.setInt(10, horarioDeFuncionamento.getId());

			if (stmt.executeUpdate() > 0) {
				return horarioDeFuncionamento.getId();
			}

			return null;

		} catch (SQLException ex) {
			Logger.getLogger(HorarioDeFuncionamentoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
			throw new AcessoADadosException("Error ao atualizar o horario de funcionamento de um Salão");
		}
	}

}
