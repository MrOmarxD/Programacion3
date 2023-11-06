import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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
		assertEquals(p.getPrecioHora(), 3f, 0.1);
	}
}
