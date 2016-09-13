package org.ees.api.agenda.infra.repository;

import org.ees.api.agenda.entity.Event;
import org.ees.api.agenda.entity.Resource;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.AgendaRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by silvanei on 10/09/16.
 */
public class AgendaRepositoryImpl implements AgendaRepository {

    DateTimeFormatter dtfPadrao = DateTimeFormat.forPattern("yyyy-MM-dd");

    @Override
    public List<Resource> findResource(Integer salaoId, DateTime start, DateTime end) {

        String sql = "SELECT f.id, f.apelido " +
                "FROM agenda a  " +
                "INNER JOIN funcionario f ON (f.id = a.funcionario_id) " +
                "WHERE a.salao_id = ? AND DATE(a.data)  between ? AND ?  " +
                "GROUP BY f.id , f.apelido";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setString(2, start.toString(dtfPadrao));
            stmt.setString(3, end.toString(dtfPadrao));
            ResultSet resultSet = stmt.executeQuery();

            List<Resource> resources = new ArrayList<>();

            while (resultSet.next()) {
                resources.add(new Resource(
                    resultSet.getInt("id"),
                    resultSet.getString("apelido")
                ));
            }

            return  resources;

        } catch (SQLException ex) {
            Logger.getLogger(AgendaRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar recursos pelo idSalao");
        }
    }

    @Override
    public List<Event> findEvents(Integer salaoId, DateTime start, DateTime end) {

        String sql = "SELECT f.id AS resourceId, c.nome AS title, a.data AS start, (a.data + INTERVAL s.duracao MINUTE) AS end " +
                "FROM agenda a " +
                "INNER JOIN funcionario f ON (f.id = a.funcionario_id) " +
                "INNER JOIN servico s ON (s.id = a.servico_id) " +
                "INNER JOIN cliente c ON (c.id = a.cliente_id) " +
                "WHERE a.salao_id = ? AND DATE(a.data) between ? AND ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setString(2, start.toString(dtfPadrao));
            stmt.setString(3, end.toString(dtfPadrao));
            ResultSet resultSet = stmt.executeQuery();

            List<Event> events = new ArrayList<>();

            while (resultSet.next()) {
                events.add(new Event(
                    resultSet.getInt("resourceId"),
                    resultSet.getString("title"),
                    new DateTime(resultSet.getTimestamp("start")),
                    new DateTime(resultSet.getTimestamp("end"))
                ));
            }

            return events;

        } catch (SQLException ex) {
            Logger.getLogger(AgendaRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar eventos pelo idSalao");
        }
    }

    @Override
    public List<Resource> findResourceByFuncionarioId(Integer salaoId, Integer funcionarioId, DateTime start, DateTime end) {
        String sql = "SELECT f.id, f.apelido " +
                "FROM agenda a  " +
                "INNER JOIN funcionario f ON (f.id = a.funcionario_id) " +
                "WHERE a.salao_id = ? AND DATE(a.data)  between ? AND ? AND f.id = ? " +
                "GROUP BY f.id , f.apelido";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setString(2, start.toString(dtfPadrao));
            stmt.setString(3, end.toString(dtfPadrao));
            stmt.setInt(4, funcionarioId);
            ResultSet resultSet = stmt.executeQuery();

            List<Resource> resources = new ArrayList<>();

            while (resultSet.next()) {
                resources.add(new Resource(
                        resultSet.getInt("id"),
                        resultSet.getString("apelido")
                ));
            }

            return  resources;

        } catch (SQLException ex) {
            Logger.getLogger(AgendaRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar recursos pelo funcionario");
        }
    }

    @Override
    public List<Event> findEventsByFuncionarioId(Integer salaoId, Integer funcionarioId, DateTime start, DateTime end) {
        String sql = "SELECT f.id AS resourceId, c.nome AS title, a.data AS start, (a.data + INTERVAL s.duracao MINUTE) AS end " +
                "FROM agenda a " +
                "INNER JOIN funcionario f ON (f.id = a.funcionario_id) " +
                "INNER JOIN servico s ON (s.id = a.servico_id) " +
                "INNER JOIN cliente c ON (c.id = a.cliente_id) " +
                "WHERE a.salao_id = ? AND DATE(a.data) between ? AND ? AND f.id = ? ";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setString(2, start.toString(dtfPadrao));
            stmt.setString(3, end.toString(dtfPadrao));
            stmt.setInt(4, funcionarioId);
            ResultSet resultSet = stmt.executeQuery();

            List<Event> events = new ArrayList<>();

            while (resultSet.next()) {
                events.add(new Event(
                        resultSet.getInt("resourceId"),
                        resultSet.getString("title"),
                        new DateTime(resultSet.getTimestamp("start")),
                        new DateTime(resultSet.getTimestamp("end"))
                ));
            }

            return events;

        } catch (SQLException ex) {
            Logger.getLogger(AgendaRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar eventos pelo funcionario");
        }
    }
}
