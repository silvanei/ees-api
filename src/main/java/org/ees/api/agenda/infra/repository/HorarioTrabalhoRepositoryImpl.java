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
    public List<HorarioTrabalho> findByIdFuncionario(Integer idfuncionario) {
        String sql = "SELECT id, dia_da_semana, entrada1, saida1, entrada2, saida2 FROM horario_trabalho WHERE funcionario_id = ?";

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
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar o horario de trabalho de um funcionario");
        } catch (Exception e) {
            Logger.getLogger(FuncionarioRepositoryImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new AcessoADadosException(e.getMessage());
        }
    }
}
