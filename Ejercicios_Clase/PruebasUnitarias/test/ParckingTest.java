import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

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
	public void testReservarLibre() throws PlazaNoEncontrada {
		// comprobamos el número de plazas libres (todas)
		assertEquals(20, p.getPlazasLibres());
		
		// prueba a reservar una plaza libre y comprueba que
		// devuelve true, indicando que ha sido reservada
		// la excepción no debe producirse en esta situación
		// por lo que se relanza en el test para ignorarla
		boolean reservada = p.reservar("05");
		assertTrue(reservada);
		
		// el número de plazas libres debe haberse reducido
		assertEquals(19, p.getPlazasLibres());
	}
	
	@Test
	public void testReservarOcupada() throws PlazaNoEncontrada {
		// comprobamos el número de plazas libres (todas)
		assertEquals(20, p.getPlazasLibres());
		
		// reservamos una plaza 
		// la excepción no debe producirse en esta situación
		// por lo que se relanza en el test para ignorarla
		assertTrue(p.reservar("05"));
		
		// el número de plazas libres debe haberse reducido
		assertEquals(19, p.getPlazasLibres());
		
		// prueba a reservar una plaza ocupada y comprueba que ha
		// devuelve false, indicando que no ha podido ser reservada
		boolean reservada = p.reservar("05");
		assertFalse(reservada);
		
		// el número de plazas libres debe haberse reducido
		assertEquals(19, p.getPlazasLibres());
	}
	
	@Test(expected=PlazaNoEncontrada.class)
	public void testReservarPlazaInexistente() throws PlazaNoEncontrada {
		// comprobamos que reservar una plaza inexistente devuelve false
		// en este caso la excepción producida se lanza pero se captura
		// en el test ya que se espera que se produzca
		p.reservar("55");
		// Otra froma, con landa
		// assertThrows(PlazaNoEncontrada.class, p.reservar("55"));
	}
	
	@Test
	public void testLiberarPlazaOcupada() throws PlazaNoEncontrada {
		// reservamos una plaza
		assertTrue(p.reservar("05"));
		
		// comprobamos que el número de plazas sea el esperado
		assertEquals(19, p.getPlazasLibres());
		
		// liberamos una plaza e indicamos el número de horas que ha
		// estado ocupada para comprobar si el cálculo es correcto
		// al comparar floats debemos indicar una precisión
		assertEquals(25.0f, p.liberar("05", 10), 0.01);
		
		// comprobamos que la plaza se ha liberado
		assertEquals(20, p.getPlazasLibres());
	}

	@Test
	public void testLiberarPlazaLibre() throws PlazaNoEncontrada {
		// reservamos una plaza
		assertTrue(p.reservar("05"));
		
		// comprobamos que el número de plazas sea el esperado
		assertEquals(19, p.getPlazasLibres());
		
		// ahora probamos a liberar otra plaza (no ocupada)
		assertEquals(0.0f, p.liberar("09", 10), 0.01);
		
		// comprobamos que el número de plazas sea el esperado
		assertEquals(19, p.getPlazasLibres());
	}
	
	@Test
	public void testLiberarPlazaInexistente() throws PlazaNoEncontrada {
		// reservamos una plaza
		assertTrue(p.reservar("05"));
		
		// comprobamos que el número de plazas sea el esperado
		assertEquals(19, p.getPlazasLibres());
		
		// intentamos liberar una plaza inexistente
		assertEquals(0.0f, p.liberar("99", 10), 0.01);
		
		// comprobamos que el número de plazas sea el esperado
		assertEquals(19, p.getPlazasLibres());
	}
}
