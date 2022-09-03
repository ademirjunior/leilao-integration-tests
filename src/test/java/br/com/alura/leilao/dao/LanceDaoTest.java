package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class LanceDaoTest {

	private LanceDao dao;
	private LeilaoDao daoLeilao;
	private EntityManager em;

	@BeforeEach
	public void beforeEach() {
		em = JPAUtil.getEntityManager();
		this.dao = new LanceDao(em);
		this.daoLeilao = new LeilaoDao(em);
		em.getTransaction().begin();
		
		
	}

	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}

	@Test
	void deveriaCadastrarUmLance() {
		Lance lance = criarLance();
		Lance salvo = dao.buscarPorId(lance.getId());
		assertEquals(lance, salvo);
	}
	
	@Test
	void deveriaPegaLanceAlto() {
		Lance lance = criarLance();
		Lance lanceAlto = criarLanceAlto();
		Lance salvo = dao.buscarMaiorLanceDoLeilao(lance.getLeilao());
		assertEquals(lanceAlto, salvo);
	}

	private Lance criarLance() {
		Usuario usuario = criarUsuario();
		Lance lance = new Lance (usuario, new BigDecimal("80.0"));
		Leilao leilao = criarLeilao();
		lance.setLeilao(leilao);
		return  dao.salvar(lance);
	}
	
	private Lance criarLanceAlto() {
		Usuario usuario = criarUsuario();
		Lance lance = new Lance (usuario, new BigDecimal("100.0"));
		Leilao leilao = criarLeilao();
		lance.setLeilao(leilao);
		return  dao.salvar(lance);
	}
	
	private Usuario criarUsuario() {
		Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
		em.persist(usuario);
		return usuario;
	}
	
	private Leilao criarLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao ("mochila", new BigDecimal("70.0"), LocalDate.now(), usuario);
		return  daoLeilao.salvar(leilao);
	}
	
}
