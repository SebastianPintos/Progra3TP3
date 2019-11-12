package selectorGrupo;

import static org.junit.Assert.*;

import org.junit.Test;

import grupo.Grupo;
import persona.Trabajador;

public class TestSelector {

	@Test
	public void tamanoTest() {
		Grupo grupo = new Grupo();
		
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		assertEquals(0, selector.tamano());
	}

	@Test
	public void tamanoSelectorConUnaPersonaTest() {
		Grupo grupo = new Grupo();
		
		grupo.agregar(new Trabajador("Juan", 43, 52435454, "Arquitecto"));
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		assertEquals(1, selector.tamano());
	}

	@Test
	public void agregarRestriccionTest() {
		Grupo grupo = JuanPedroMartaArquitectos();
		
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		selector.agregarRestriccion("Arquitecto", 0, 2);
		assertTrue(selector.existeRestriccion("Arquitecto", 0, 2));
	}
	
	@Test
	public void agregarRestriccionMaximoInvalidoTest() {
		Grupo grupo = JuanPedroMartaArquitectos();
		
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		selector.agregarRestriccion("Arquitecto", 0, 10);
		assertFalse(selector.existeRestriccion("Arquitecto", 0, 10));
	}

	@Test
	public void agregarRestriccionMinimoSuperaMaximoTest() {
		Grupo grupo = JuanPedroMartaArquitectos();
		
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		selector.agregarRestriccion("Arquitecto",3,2);
		assertFalse(selector.existeRestriccion("Arquitecto",3,2));
	}

	@Test
	public void agregarRestriccionMaximoSuperaLimiteTest() {
		Grupo grupo = JuanPedroMartaArquitectos();
		
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		selector.agregarRestriccion("Arquitecto", 0, 4);
		assertFalse(selector.existeRestriccion("Arquitecto", 0, 4));
	}
	
	@Test
	public void agregarRestriccionMinimoNegativoTest() {
		Grupo grupo = JuanPedroMartaArquitectos();
		
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		selector.agregarRestriccion("Arquitecto", -1, 2);
		assertFalse(selector.existeRestriccion("Arquitecto", -1, 2));
	}
	
	@Test
	public void agregarRestriccionMaximoNegativoTest() {
		Grupo grupo = JuanPedroMartaArquitectos();
		
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		selector.agregarRestriccion("Arquitecto", 0, -1);
		assertFalse(selector.existeRestriccion("Arquitecto", 0, -1));
	}
	
	@Test
	public void agregarRestriccionAmbosNegativosTest() {
		Grupo grupo = JuanPedroMartaArquitectos();
		
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		selector.agregarRestriccion("Arquitecto", -1,-1);
		assertFalse(selector.existeRestriccion("Arquitecto", -1, -1));
	}

	@Test
	public void encontrarGrupoPosible_conIncompatibles() {
		Grupo grupo = JuanPedroMartaArquitectos();
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		selector.agregarIncompatibilidad(grupo.obtener(0), grupo.obtener(1));
		selector.agregarRestriccion("Arquitecto", 0, 3);
		selector.resolver();
		
		assertEquals(selector.getGrupoResultante().tamano(), 2);
	}

	@Test
	public void encontrarGrupoPosible_sinIncompatibles() {
		Grupo grupo = JuanPedroMartaArquitectos();
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		selector.agregarRestriccion("Arquitecto", 0, 3);

		selector.resolver();
		assertEquals(selector.getGrupoResultante().tamano(), 3);
	}

	@Test
	public void calcularGrupoNoPosible() {
		Grupo grupo = JuanPedroMartaArquitectos();
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		hacerTodosIncompatibles(grupo,selector);
		
		selector.agregarRestriccion("Arquitecto", 3, 3);
		
		selector.resolver();
		assertEquals(selector.getGrupoResultante().tamano(), 0);
	}
	
	
	@Test
	public void calcularGrupoSinIntegrantes() {
		Grupo grupo = new Grupo();
		SelectorGrupo selector = new SelectorGrupo(grupo);
		
		selector.agregarRestriccion("Arquitecto", 2, 2);
		
		selector.resolver();
		assertEquals(selector.getGrupoResultante().tamano(), 0);
	}
	
	@Test
	public void calcularGrupoUnoDeCadaPuesto() {
		Grupo grupo = unaPersonaDeCadaPuesto();
		
		SelectorGrupo selector = new SelectorGrupo(grupo);
		selector.agregarRestriccion("Arquitecto", 0, 1);
		selector.agregarRestriccion("Lider de Proyecto", 0, 1);
		selector.agregarRestriccion("Programador", 0, 1);
		selector.agregarRestriccion("Tester", 0, 1);
		
		selector.resolver();
		assertEquals(selector.getGrupoResultante().tamano(), 4);
	}
	
	private Grupo JuanPedroMartaArquitectos() {
		Grupo grupo=new Grupo();
		grupo.agregar(new Trabajador("Juan",34,23235345,"Arquitecto"));
		grupo.agregar(new Trabajador("Pedro",53,54353455,"Arquitecto"));
		grupo.agregar(new Trabajador("Marta",55,23424534,"Arquitecto"));
		return grupo;
	}
	
	private Grupo unaPersonaDeCadaPuesto() {
		Grupo grupo=new Grupo();
		grupo.agregar(new Trabajador("Juan",34,23235345,"Arquitecto"));
		grupo.agregar(new Trabajador("Pedro",53,54353455,"Lider de proyecto"));
		grupo.agregar(new Trabajador("Marta",55,23424534,"Programador"));
		grupo.agregar(new Trabajador("Lucia",23,25453454,"Tester"));
		return grupo;
	}
	
	private void hacerTodosIncompatibles(Grupo grupo,SelectorGrupo selector) {
		for(int i=0;i<grupo.tamano();i++) 
			for(int j=0;j<grupo.tamano() && i!=j;j++)
				selector.agregarIncompatibilidad(grupo.obtener(i), grupo.obtener(j));
	}


}

