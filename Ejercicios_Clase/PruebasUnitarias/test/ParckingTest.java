import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import es.deusto.prog3.parking.Parking;
import es.deusto.prog3.parking.PlazaNoEncontrada;

public class ParckingTest {
	private Parking p;
	
	@Before
	public void setup() {
		p = new Parking("parking 1");
	}

	@Test
	public void testGetNombre() {
		assertEquals("parking 1", p.getNombre());
	}
	
	@Test
	public void testGetPlazasLibresTodoLibre() {
		assertEquals(20, p.getPlazasLibres());
	}
	
	@Test
	public void testGetPlazasLibresAlgunaOcupada() throws PlazaNoEncontrada {
		p.reservar("01");
		assertEquals(19, p.getPlazasLibres());
	}
}
