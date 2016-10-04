package org.ees.api.agenda.infra.db;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.ees.api.agenda.infra.db.exceptions.AcessoADadosException;

public class DB {

	private static Connection con = null;

	public static Connection conexao() {
		//if (con == null) {
			try {

				URI dbUri = new URI(System.getenv("CLEARDB_DATABASE_URL"));

				String username = dbUri.getUserInfo().split(":")[0];
				String password = dbUri.getUserInfo().split(":")[1];
				String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();

				con = DriverManager.getConnection(dbUrl, username, password);
				
			} catch (SQLException ex) {
				throw new AcessoADadosException("Erro ao conectar: " + ex.getMessage());
			} catch (URISyntaxException e) {
				throw new AcessoADadosException(e.getMessage());
			}
		//}
		
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
	
	public static int foundRows() {
		PreparedStatement stmt = DB.preparedStatement("select found_rows() as total");
		ResultSet rs;
		try {
			rs = stmt.executeQuery();
			Integer total = 0;
	        if(rs.next()) {
	        	total = rs.getInt("total");
	        }
	        
	        return total;
		} catch (SQLException e) {
			throw new AcessoADadosException("Erro ao select found_rows(): " + e.getMessage());
		}
	}

}
