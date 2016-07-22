package org.ees.api.agenda.infra.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Conexao {

	private static final String banco = "jdbc:mysql://localhost/ees-agenda?user=root&password=83406310";
	private static final String driver = "com.mysql.jdbc.Driver";
	private static Connection con = null;

	public static Connection getConexao() {
		if (con == null) {
			try {
				Class.forName(driver);
				con = DriverManager.getConnection(banco);
				
			} catch (ClassNotFoundException ex) {
				System.out.println("Não encontrou o driver");
				
			} catch (SQLException ex) {
				System.out.println("Erro ao conectar: " + ex.getMessage());
				
			}
		}
		
		return con;
	}

	public static PreparedStatement preparedStatement(String sql) {
		if (con == null) {
			con = getConexao();
		}
		
		try {
			return con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
		} catch (SQLException e) {
			System.out.println("Erro de sql: " + e.getMessage());
		}
		
		return null;
	}

	public static void beginTransaction() {
		if (con == null) {
			con = getConexao();
		}

		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void commit() {
		if (con == null) {
			con = getConexao();
		}

		try {
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void rollback() {
		if (con == null) {
			con = getConexao();
		}

		try {
			con.rollback();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
