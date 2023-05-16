package br.com.javajdbc.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.javajdbc.conexao.SingleConnection;
import br.com.javajdbc.model.BeanUserFone;
import br.com.javajdbc.model.Telefone;
import br.com.javajdbc.model.UserPosJava;

/*
 * O dado telefone pertence ao usuario, então
 * faz sentido realizar o DAO do telefone dentro da
 * classe DAO do usuário!
 */
public class UserPosDAO {

	private Connection connection;

	public UserPosDAO() {
		connection = SingleConnection.getConnection();
	}

	// CREATE
	public void salvar(UserPosJava userPosJava) {
		try {
			String sql = "INSERT INTO userposjava (nome,email) values (?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, userPosJava.getNome());
			insert.setString(2, userPosJava.getEmail());
			insert.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// CREATE
	public void salvarTelefone(Telefone tel) {
		try {
			String sql = "INSERT INTO telefoneuser(numero,tipo,usuariopessoa) values (?,?,?)";
			PreparedStatement insert = connection.prepareStatement(sql);
			insert.setString(1, tel.getNumero());
			insert.setString(2, tel.getTipo());
			insert.setLong(3, tel.getUsuario());
			insert.execute();
			connection.commit();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// READ
	public List<UserPosJava> listar() throws Exception {
		List<UserPosJava> list = new ArrayList<UserPosJava>();

		String sql = "select * from userposjava";

		PreparedStatement statement = connection.prepareStatement(sql);
		ResultSet resultado = statement.executeQuery();

		while (resultado.next()) {
			UserPosJava userPosJava = new UserPosJava();
			userPosJava.setId(resultado.getLong("id"));
			userPosJava.setNome(resultado.getString("nome"));
			userPosJava.setEmail(resultado.getString("email"));

			list.add(userPosJava);
		}

		return list;
	}

	// READ
	public UserPosJava buscar(Long id) throws Exception {

		UserPosJava usuario = new UserPosJava();

		String sql = "SELECT * FROM userposjava WHERE id = ?";

		PreparedStatement statement = connection.prepareStatement(sql);
		statement.setLong(1, id);

		ResultSet resultado = statement.executeQuery();
		resultado = statement.executeQuery();

		if (resultado.next()) { // Retorna 1 ou nenhum!
			usuario.setId(resultado.getLong("id"));
			usuario.setNome(resultado.getString("nome"));
			usuario.setEmail(resultado.getString("email"));
		}

		return usuario;
	}
	
	public List<BeanUserFone> listarUserFone(Long idUsuario) throws SQLException{
		
		List<BeanUserFone> beanUserFones = new ArrayList<BeanUserFone>();
		
		String sql = "select nome, numero, email from telefoneuser as fone";
		sql += " inner join userposjava as userp";
		sql += " on fone.usuariopessoa = userp.id";
		sql += " where userp.id = " + idUsuario;
		
		PreparedStatement pstm = connection.prepareStatement(sql);
		ResultSet resultSet = pstm.executeQuery();
		
		while(resultSet.next()) {
			BeanUserFone userFone = new BeanUserFone();
			
			userFone.setEmail(resultSet.getString("email"));
			userFone.setNome(resultSet.getString("nome"));
			userFone.setNumero(resultSet.getString("numero"));
			
			beanUserFones.add(userFone);
		}
		
		return beanUserFones;
		
	}

	// UPDATE
	public void atualizar(UserPosJava userPosJava) throws Exception {

		try {
			String sql = "UPDATE userposjava SET nome = ? WHERE id = ?";

			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, userPosJava.getNome());
			statement.setLong(2, userPosJava.getId());

			statement.execute();
			connection.commit();

		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		}
	}

	// DELETE
	public void deletar(Long id) throws SQLException {
		try {
			String sql = "DELETE FROM userposjava WHERE id = ?";
			
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			statement.execute();
			
			connection.commit();
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		}
	}
	
	// DELETE
	public void deletarFonesPorUsuario(Long idUsuario) throws SQLException {
		try {
			String sqlFone = "DELETE FROM telefoneuser WHERE usuariopessoa = " + idUsuario;
			String sqlUsuario = "delete from userposjava where id =" + idUsuario;
			
			PreparedStatement statement = connection.prepareStatement(sqlFone);
			statement.executeUpdate();
			connection.commit();
			
			statement = connection.prepareStatement(sqlUsuario);
			statement.executeUpdate();
			connection.commit();
			
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		}
	}
}
