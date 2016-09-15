package org.ees.api.agenda.infra.repository;

import org.ees.api.agenda.entity.Event;
import org.ees.api.agenda.entity.Resource;
import org.ees.api.agenda.infra.db.DB;
import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;
import org.ees.api.agenda.repository.AgendaRepository;
import org.ees.api.agenda.resource.bean.Agendamento;
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

    private DateTimeFormatter dtfPadrao = DateTimeFormat.forPattern("yyyy-MM-dd");
    private DateTimeFormatter dtfTimeStamp = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<Resource> findResource(Integer salaoId, DateTime start, DateTime end) {

        String sql = "SELECT f.id, f.apelido " +
                "FROM agenda a  " +
                "INNER JOIN funcionario f ON (f.id = a.funcionario_id) " +
                "WHERE a.salao_id = ? AND DATE(a.inicio)  between ? AND ?  " +
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

        String sql = "SELECT a.id, f.id AS funcionarioId, CONCAT(c.nome, ' - ' ,sa.descricao) AS title, a.inicio AS start, a.fim AS end, c.id as clienteId, s.id as servicoId, a.observacao, a.status_agendamento_id as status " +
                "FROM agenda a " +
                "INNER JOIN funcionario f ON (f.id = a.funcionario_id) " +
                "INNER JOIN servico s ON (s.id = a.servico_id) " +
                "INNER JOIN cliente c ON (c.id = a.cliente_id) " +
                "INNER JOIN status_agendamento sa ON (sa.id = a.status_agendamento_id) " +
                "WHERE a.salao_id = ? AND DATE(a.inicio) between ? AND ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setString(2, start.toString(dtfPadrao));
            stmt.setString(3, end.toString(dtfPadrao));
            ResultSet resultSet = stmt.executeQuery();

            List<Event> events = new ArrayList<>();

            while (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getInt("funcionarioId"),
                        resultSet.getString("title"),
                        new DateTime(resultSet.getTimestamp("start")),
                        new DateTime(resultSet.getTimestamp("end"))
                );

                event.setClienteId(resultSet.getInt("clienteId"));
                event.setServicoId(resultSet.getInt("servicoId"));
                event.setObservacao(resultSet.getString("observacao"));
                event.setStatus(resultSet.getInt("status"));


                events.add(event);
            }

            return events;

        } catch (SQLException ex) {
            Logger.getLogger(AgendaRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar eventos pelo idSalao");
        }
    }

    @Override
    public Event findEvent(Integer salaoId, DateTime dia, Integer eventId) {
        String sql = "SELECT a.id, f.id AS funcionarioId, CONCAT(c.nome, ' - ' ,sa.descricao) AS title, a.inicio AS start, a.fim AS end, c.id as clienteId, s.id as servicoId, a.observacao, a.status_agendamento_id as status " +
                "FROM agenda a " +
                "INNER JOIN funcionario f ON (f.id = a.funcionario_id) " +
                "INNER JOIN servico s ON (s.id = a.servico_id) " +
                "INNER JOIN cliente c ON (c.id = a.cliente_id) " +
                "INNER JOIN status_agendamento sa ON (sa.id = a.status_agendamento_id) " +
                "WHERE a.salao_id = ? AND DATE(a.inicio) = ? AND a.id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setString(2, dia.toString(dtfPadrao));
            stmt.setInt(3, eventId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getInt("funcionarioId"),
                        resultSet.getString("title"),
                        new DateTime(resultSet.getTimestamp("start")),
                        new DateTime(resultSet.getTimestamp("end"))
                );

                event.setClienteId(resultSet.getInt("clienteId"));
                event.setServicoId(resultSet.getInt("servicoId"));
                event.setObservacao(resultSet.getString("observacao"));
                event.setStatus(resultSet.getInt("status"));

                return event;
            }

            return null;

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
                "WHERE a.salao_id = ? AND DATE(a.inicio)  between ? AND ? AND f.id = ? " +
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
        String sql = "SELECT a.id, f.id AS resourceId, CONCAT(c.nome, ' - ' ,sa.descricao) AS title, a.inicio AS start, a.fim AS end " +
                "FROM agenda a " +
                "INNER JOIN funcionario f ON (f.id = a.funcionario_id) " +
                "INNER JOIN servico s ON (s.id = a.servico_id) " +
                "INNER JOIN cliente c ON (c.id = a.cliente_id) " +
                "INNER JOIN status_agendamento sa ON (sa.id = a.status_agendamento_id) " +
                "WHERE a.salao_id = ? AND DATE(a.inicio) between ? AND ? AND f.id = ? ";

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
                        resultSet.getInt("id"),
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

    @Override
    public Event findById(Integer salaoId, Integer agendaId) {
        String sql = "SELECT a.id, f.id AS funcionarioId, CONCAT(c.nome, ' - ' ,sa.descricao) AS title, a.inicio AS start, a.fim AS end, c.id as clienteId, s.id as servicoId, a.observacao, a.status_agendamento_id as status " +
                "FROM agenda a " +
                "INNER JOIN funcionario f ON (f.id = a.funcionario_id) " +
                "INNER JOIN servico s ON (s.id = a.servico_id) " +
                "INNER JOIN cliente c ON (c.id = a.cliente_id) " +
                "INNER JOIN status_agendamento sa ON (sa.id = a.status_agendamento_id) " +
                "WHERE a.salao_id = ? AND a.id = ?";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setInt(2, agendaId);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                Event event = new Event(
                        resultSet.getInt("id"),
                        resultSet.getInt("funcionarioId"),
                        resultSet.getString("title"),
                        new DateTime(resultSet.getTimestamp("start")),
                        new DateTime(resultSet.getTimestamp("end"))
                );

                event.setClienteId(resultSet.getInt("clienteId"));
                event.setServicoId(resultSet.getInt("servicoId"));
                event.setObservacao(resultSet.getString("observacao"));
                event.setStatus(resultSet.getInt("status"));

                return event;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(AgendaRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao buscar eventos pelo idSalao");
        }
    }

    @Override
    public Integer add(Integer salaoId, Event event) {
        String sql = "INSERT INTO agenda (salao_id, cliente_id, servico_id, funcionario_id, inicio, fim, observacao) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try{
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, salaoId);
            stmt.setInt(2, event.getClienteId());
            stmt.setInt(3, event.getServicoId());
            stmt.setInt(4, event.getFuncionarioId());
            stmt.setString(5, event.getStart().toString(dtfTimeStamp));
            stmt.setString(6, event.getEnd().toString(dtfTimeStamp));
            stmt.setString(7, event.getObservacao());

            if(stmt.executeUpdate()>0){
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()){
                    return rs.getInt(1);
                }
            }

            return null;

        }catch (SQLException ex){
            Logger.getLogger(AgendaRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao inserir um agendamento de um Salão");
        }
    }

    @Override
    public Integer update(Integer salaoId, Integer agendaId, Event event) {
        String sql = "UPDATE agenda SET servico_id = ?, funcionario_id = ?, inicio = ?, fim = ?, observacao = ?, status_agendamento_id = ? WHERE salao_id = ? AND id = ? ";

        try {
            PreparedStatement stmt = DB.preparedStatement(sql);
            stmt.setInt(1, event.getServicoId());
            stmt.setInt(2, event.getFuncionarioId());
            stmt.setString(3, event.getStart().toString(dtfTimeStamp));
            stmt.setString(4, event.getEnd().toString(dtfTimeStamp));
            stmt.setString(5, event.getObservacao());
            stmt.setInt(6, event.getStatus());
            stmt.setInt(7, salaoId);
            stmt.setInt(8, agendaId);

            if (stmt.executeUpdate() > 0) {
                return agendaId;
            }

            return null;

        } catch (SQLException ex) {
            Logger.getLogger(AgendaRepositoryImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new AcessoADadosException("Error ao atualizar um evento");
        }
    }
}
