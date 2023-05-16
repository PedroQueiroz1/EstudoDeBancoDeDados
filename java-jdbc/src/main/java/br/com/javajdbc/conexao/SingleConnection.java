package br.com.javajdbc.conexao;

import java.sql.Connection;
import java.sql.DriverManager;

public class SingleConnection {
		
	private static String url = "jdbc:postgresql://localhost:5433/posjava";
	private static String password = "admin";
	private static String user = "postgres";
	private static Connection connection = null;
	
	static {
		conectar();
	}
	
	public SingleConnection() {
		conectar();
	}
	
	/*
	 * Conexão é aberto e fechado somente UMA vez!
	 * O que é aberto e fechado depois são as sessões no banco de dados
	 */
	private static void conectar() {
		try {
			
			if(connection == null) {
				Class.forName("org.postgresql.Driver"); // POSTGRESQL
				connection = DriverManager.getConnection(url, user, password);
				connection.setAutoCommit(false); // Não salvar automaticamente
				System.out.println("Conectou com sucesso!");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Connection getConnection() {
		return connection;
	}
}
