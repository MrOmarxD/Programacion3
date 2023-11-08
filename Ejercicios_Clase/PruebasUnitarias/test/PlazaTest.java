import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import es.deusto.prog3.parking.Plaza;

public class PlazaTest {
	private Plaza p;
	
	@Before
	public void setUp() {
		p = new Plaza("2", 2f);
	}

	@Test
	public void testGetId() { 
		assertEquals("2", p.getId());
	}
	
	@Test
	public void testGetPrecioHora() {
		assertEquals(2, p.getPrecioHora(), 0.1);
	}
	
	@Test
	public void testGetOcupada() {
		assertFalse(p.getOcupada());
	}
	
	@Test
	public void testSetPrecioHora() {
		p.setPrecioHora(3f);
		assertEquals(3f, p.getPrecioHora(), 0.0001);
	}
	
	 @Test
    public void testSetOcupada() {
    	// ahora vamos a comprobar a cambiar el estado de la plaza
    	// y ver si se ha cambiado correctamente;
    	p.setOcupada(true);
    	assertTrue(p.getOcupada());
    }
    
    @Test
    public void testToString() {
    	// comprobamos que el string construido sea el correcto
    	assertEquals("2: 2", p.toString());
    }
}
