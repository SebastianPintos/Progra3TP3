package restriccion;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RestriccionTest {
	
	@Test
	public void minimoNegativoTest() {
		Restriccion r=new Restriccion(3);
		assertFalse(r.esValida(-1, 2));
	}
	
	@Test
	public void maximoNegativoTest() {
		Restriccion r=new Restriccion(3);
		assertFalse(r.esValida(0, -1));
	}
	
	@Test
	public void minimoMayorAMaximo() {
		Restriccion r=new Restriccion(3);
		assertFalse(r.esValida(2, 1));
	}

	@Test
	public void maximoMayorALimiteTest() {
		Restriccion r=new Restriccion(3);
		assertFalse(r.esValida(0, 4));
	}
	
	@Test
	public void iguales() {
		Restriccion r1=new Restriccion(3);
		r1.set(0, 2);
		
		Restriccion r2=new Restriccion(3);
		r2.set(0, 2);
		
		assertTrue(r1.equals(r2));
	}

}
