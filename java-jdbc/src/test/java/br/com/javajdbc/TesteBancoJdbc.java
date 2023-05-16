package br.com.javajdbc;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import br.com.javajdbc.conexao.SingleConnection;
import br.com.javajdbc.dao.UserPosDAO;
import br.com.javajdbc.model.BeanUserFone;
import br.com.javajdbc.model.Telefone;
import br.com.javajdbc.model.UserPosJava;

public class TesteBancoJdbc {

	@Test
	public void initSalvar() {
		
		UserPosDAO userPosDAO = new UserPosDAO();
		UserPosJava userPosJava = new UserPosJava();
			
		userPosJava.setNome("Ppepepp");
		userPosJava.setEmail("pepeppep@gmail.com");
		
		userPosDAO.salvar(userPosJava);
	}
	
	
	
	@Test
	public void initListar() {
		try {
			UserPosDAO dao = new UserPosDAO();
			List<UserPosJava> list = dao.listar();
			
			for (UserPosJava userposjava : list) {
				System.out.println(userposjava);
				System.out.println("----------");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initBuscar() {
		UserPosDAO dao = new UserPosDAO();
		
		try {
			UserPosJava userposjava = dao.buscar(1L);
			
			System.out.println(userposjava);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void initAtualizar() throws Exception {
		UserPosDAO dao = new UserPosDAO();
		UserPosJava usuario = dao.buscar(1L);
		
		usuario.setNome("Atualizado");
		
		dao.atualizar(usuario);
		
		System.out.println("Usuario atualizado!");
	}
	
	@Test
	public void initDeletar() throws Exception {
		UserPosDAO dao = new UserPosDAO();
		
		dao.deletar(2L);
	}
	
	@Test
	public void initSalvarTelefone() {
		UserPosDAO dao = new UserPosDAO();
		Telefone telefone = new Telefone();
		
		telefone.setNumero("2414-4441");
		telefone.setTipo("Celular");
		telefone.setUsuario(10L);
		
		dao.salvarTelefone(telefone);
		
	}
	
	@Test
	public void testeCarregaFonesUser() {
		UserPosDAO dao = new UserPosDAO();
		
		try {
			
			List<BeanUserFone> list = dao.listarUserFone(10L);
			
			for (BeanUserFone beanUserFone : list) {
				System.out.println(beanUserFone);
				System.out.println("----------");
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testeDeletarUsuarioFone() {
		UserPosDAO dao = new UserPosDAO();
		
		try {
			dao.deletarFonesPorUsuario(10L);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
