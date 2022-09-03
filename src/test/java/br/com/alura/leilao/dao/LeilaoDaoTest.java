package br.com.alura.leilao.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;
import br.com.alura.leilao.util.JPAUtil;

class LeilaoDaoTest {

	private LeilaoDao dao;
	private EntityManager em;

	@BeforeEach
	public void beforeEach() {
		em = JPAUtil.getEntityManager();
		this.dao = new LeilaoDao(em);
		em.getTransaction().begin();
	}

	@AfterEach
	public void afterEach() {
		em.getTransaction().rollback();
	}

	@Test
	void deveriaCadastrarUmLeilao() {
		Leilao leilao = criarLeilao();
		Leilao salvo = dao.buscarPorId(leilao.getId());
		assertEquals(leilao, salvo);
	}
	
	@Test
	void deveriaAtualizarUmLeilao() {
		Leilao leilao = criarLeilao();
		leilao.setNome("Celular");
		leilao.setValorInicial(new BigDecimal("700.0"));
		leilao = dao.salvar(leilao);
		
		Leilao salvo = dao.buscarPorId(leilao.getId());
		
		assertEquals("Celular", salvo.getNome());
	}

	private Leilao criarLeilao() {
		Usuario usuario = criarUsuario();
		Leilao leilao = new Leilao ("mochila", new BigDecimal("70.0"), LocalDate.now(), usuario);
		return  dao.salvar(leilao);
	}
	
	private Usuario criarUsuario() {
		Usuario usuario = new Usuario("fulano", "fulano@email.com", "12345678");
		em.persist(usuario);
		return usuario;
	}
}
