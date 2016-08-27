package org.ees.api.agenda.infra.repository;

import org.ees.api.agenda.entity.DiaDaSemana;
import org.ees.api.agenda.entity.HorarioTrabalho;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.HorarioTrabalhoRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by silvanei on 26/08/2016.
 */
public class HorarioTrabalhoRepositoryImpl implements HorarioTrabalhoRepository {

    @Override
    public HorarioTrabalho findById(Integer salaoId, Integer funcionarioId, DiaDaSemana diaDaSemana) {
        String sql = "SELECT funcionario_id, funcionario_salao_id, dia_da_semana, entrada1, saida1, entrada2, saida2 FROM horario_trabalho  WHERE funcionario_id = ? AND funcionario_salao_id = ? AND dia_da_semana = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, funcionarioId);
            stmt.setInt(2, salaoId);
            stmt.setInt(3, diaDaSemana.dia());
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                HorarioTrabalho horarioTrabalho = new HorarioTrabalho(
                        new DiaDaSemana(resultSet.getInt("dia_da_semana")),
                        resultSet.getTime("entrada1"),
                        resultSet.getTime("saida1"),
                        resultSet.getTime("entrada2"),
                        resultSet.getTime("saida2")
                );

                return horarioTrabalho;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(HorarioTrabalhoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Erro ao buscar um horario de trabalho pelo id");
        } catch (Exception e) {
            Logger.getLogger(HorarioTrabalhoRepositoryImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new AcessoADadosException(e.getMessage());
        }
    }

    @Override
    public List<HorarioTrabalho> findByIdFuncionario(Integer idfuncionario) {
        String sql = "SELECT dia_da_semana, entrada1, saida1, entrada2, saida2 FROM horario_trabalho WHERE funcionario_id = ?";

        try{
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, idfuncionario);
            ResultSet resultSet = stmt.executeQuery();

            List<HorarioTrabalho> horarioTrabalhoList = new ArrayList<>();

            while (resultSet.next()) {
                HorarioTrabalho horarioTrabalho = new HorarioTrabalho(
                    new DiaDaSemana(resultSet.getInt("dia_da_semana")),
                    resultSet.getTime("entrada1"),
                    resultSet.getTime("saida1"),
                    resultSet.getTime("entrada2"),
                    resultSet.getTime("saida2")
                );

                horarioTrabalhoList.add(horarioTrabalho);
            }

            return horarioTrabalhoList;

        }catch (SQLException ex){
            Logger.getLogger(HorarioTrabalhoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar o horario de trabalho de um funcionario");
        } catch (Exception e) {
            Logger.getLogger(HorarioTrabalhoRepositoryImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new AcessoADadosException(e.getMessage());
        }
    }

    @Override
    public DiaDaSemana addHorario(Integer salaoId, Integer funcionarioId, DiaDaSemana diaDaSemana, HorarioTrabalho horario) {
        String sql = "INSERT INTO horario_trabalho (funcionario_id, funcionario_salao_id, dia_da_semana, entrada1, saida1, entrada2, saida2) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try {

            PreparedStatement stmt = DB.preparedStatement(sql);


            stmt.setInt(1, funcionarioId);
            stmt.setInt(2, salaoId);
            stmt.setInt(3, diaDaSemana.dia());
            stmt.setTime(4, horario.getEntrada1());
            stmt.setTime(5, horario.getSaida1());
            stmt.setTime(6, horario.getEntrada2());
            stmt.setTime(7, horario.getSaida2());

            if (stmt.executeUpdate() > 0) {
                return diaDaSemana;
            }

            return null;
        } catch (SQLException ex) {
            Logger.getLogger(HorarioTrabalhoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um horario de trabalho para o Funcionario");
        }

    }

    @Override
    public DiaDaSemana update(Integer salaoId, Integer funcionarioId, DiaDaSemana diaDaSemana, HorarioTrabalho horario) {
        String sql = "UPDATE horario_trabalho SET entrada1 = ?, saida1 = ?, entrada2 = ?, saida2 = ? WHERE dia_da_semana = ? AND funcionario_id = ? AND funcionario_salao_id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setTime(1, horario.getEntrada1());
            stmt.setTime(2, horario.getSaida1());
            stmt.setTime(3, horario.getEntrada2());
            stmt.setTime(4, horario.getSaida2());
            stmt.setInt(5, diaDaSemana.dia());
            stmt.setInt(6, funcionarioId);
            stmt.setInt(7, salaoId);

            if (stmt.executeUpdate() > 0) {
                return diaDaSemana;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao atualizar um horario de trabalho de um funcionario");
        } catch (Exception e) {
            Logger.getLogger(HorarioTrabalhoRepositoryImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new AcessoADadosException(e.getMessage());
        }
    }

    @Override
    public DiaDaSemana deleteHorario(Integer salaoId, Integer funcionarioId, DiaDaSemana diaDaSemana) {
        String sql = "DELETE FROM horario_trabalho  WHERE dia_da_semana = ? AND funcionario_id = ? AND funcionario_salao_id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, diaDaSemana.dia());
            stmt.setInt(2, funcionarioId);
            stmt.setInt(3, salaoId);

            if(stmt.executeUpdate() > 0) {
                return diaDaSemana;
            }

            return null;
        } catch (SQLException ex) {
            Logger.getLogger(HorarioTrabalhoRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao excluir um horario de um Funcionario");
        }
    }
}
