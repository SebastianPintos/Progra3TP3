package grupo;

import static org.junit.Assert.*;

import org.junit.Test;

import persona.Trabajador;

public class TestGrupo {

	@Test
	public void GrupoTestVacio() {
		Grupo g = new Grupo();
		assertEquals(0, g.tamano());
	}

	@Test
	public void agregarPersonatTest() {
		Grupo g = new Grupo();
		Trabajador pedro = new Trabajador("Pedro", 20, 4043555, "Programador");
		
		g.agregar(pedro);
		
		assertTrue(g.existe(pedro));
	}

	@Test
	public void existePersonaNoAgregada() {
		Grupo g = new Grupo();
		
		assertFalse(g.existe(new Trabajador("Pedro",20,5345345,"Programador")));
	}

	@Test
	public void tamanoTest() {
		Trabajador pedro = new Trabajador("Pedro", 20, 4043555, "Programador");
		Trabajador juan = new Trabajador("Juan", 34, 3543555, "Arquitecto");
		
		Grupo g = new Grupo();
		
		g.agregar(juan);
		g.agregar(pedro);
		
		assertEquals(g.tamano(), 2);
	}

	@Test
	public void eliminarTest() {
		Trabajador pedro = new Trabajador("Pedro", 20, 4043555, "Programador");
		Trabajador juan = new Trabajador("Juan", 34, 3543555, "Arquitecto");
		
		Grupo g = new Grupo();
		
		g.agregar(juan);
		g.agregar(pedro);
		
		g.eliminar(pedro);
		
		assertFalse(g.existe(pedro));
	}

}
