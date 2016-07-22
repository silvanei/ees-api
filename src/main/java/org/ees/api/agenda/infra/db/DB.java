package org.ees.api.agenda.infra.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;

public class DB {

	private static final String banco = "jdbc:mysql://localhost/ees-agenda?user=root&password=83406310";
	private static final String driver = "com.mysql.jdbc.Driver";
	private static Connection con = null;

	public static Connection conexao() {
		if (con == null) {
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(banco);
				
			} catch (ClassNotFoundException ex) {
				throw new AcessoADadosException("Não encontrou o driver :"+ driver);
				
			} catch (SQLException ex) {
				throw new AcessoADadosException("Erro ao conectar: " + ex.getMessage());
				
			}
		}
		
		return con;
	}

	public static PreparedStatement preparedStatement(String sql) {
		if (con == null) {
			con = conexao();
		}
		
		try {
			return con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
		} catch (SQLException e) {
			throw new AcessoADadosException("Erro ao preparar o sql: " + e.getMessage());
			
		}		
	}

	public static void beginTransaction() {
		if (con == null) {
			con = conexao();
		}

		try {
			con.setAutoCommit(false);
			System.out.println("Begin Transaction");
		} catch (SQLException e) {
			throw new AcessoADadosException("Erro ao Iniciar uma transação: " + e.getMessage());
		}
	}

	public static void commit() {
		if (con == null) {
			con = conexao();
		}

		try {
			con.commit();
			con.setAutoCommit(true);
			System.out.println("Commit Transaction");
		} catch (SQLException e) {
			throw new AcessoADadosException("Erro ao Commitar uma transação: " + e.getMessage());
		}
	}
	
	public static void rollback() {
		if (con == null) {
			con = conexao();
		}

		try {
			con.rollback();
			con.setAutoCommit(true);
			System.out.println("Rollback Transaction");
		} catch (SQLException e) {
			throw new AcessoADadosException("Erro ao Desfazer uma transação: " + e.getMessage());
		}
	}

}
